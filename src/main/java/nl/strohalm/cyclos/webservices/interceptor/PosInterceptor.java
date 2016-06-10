/*
    This file is part of Cyclos (www.cyclos.org).
    A project of the Social Trade Organisation (www.socialtrade.org).

    Cyclos is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Cyclos is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cyclos; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */
package nl.strohalm.cyclos.webservices.interceptor;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.ApplicationException;
import nl.strohalm.cyclos.services.access.AccessServiceLocal;
import nl.strohalm.cyclos.services.accounts.pos.MemberPosServiceLocal;
import nl.strohalm.cyclos.services.accounts.pos.PosServiceLocal;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.webservices.WebServiceContext.ContextType;
import nl.strohalm.cyclos.webservices.WebServiceFaultsEnum;
import nl.strohalm.cyclos.webservices.pos.BasePosParameters;
import nl.strohalm.cyclos.webservices.pos.IPosPinParameter;
import nl.strohalm.cyclos.webservices.utils.WebServiceHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

/**
 * Interceptor for POS requests, basically to allow access to the {@link HttpServletRequest}
 * 
 * @author luis
 */
public class PosInterceptor extends AbstractSoapInterceptor {

    private class BlockedPosException extends ApplicationException {
        private static final long serialVersionUID = 1L;

        private BlockedPosException(final String msg) {
            super(msg);
            setShouldRollback(false);
        }
    }

    private PosServiceLocal       posServiceLocal;
    private AccessServiceLocal    accessServiceLocal;
    private MemberPosServiceLocal memberPosServiceLocal;

    public PosInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(final SoapMessage message) throws Fault {
        Pos pos = null;
        try {
            final BasePosParameters params = WebServiceHelper.getParameter(message);
            final String posId = params.getPosId();
            pos = StringUtils.isEmpty(posId) ? null : posServiceLocal.loadByPosId(posId, Member.Relationships.CHANNELS, RelationshipHelper.nested(Pos.Relationships.MEMBER_POS, MemberPos.Relationships.MEMBER), RelationshipHelper.nested(Pos.Relationships.MEMBER_POS, MemberPos.Relationships.POS));
            HttpServletRequest request = WebServiceHelper.requestOf(message);
            request.setAttribute(ContextType.class.getName(), ContextType.POS);
            if (pos == null || pos.getMemberPos() == null) {
                throw WebServiceHelper.fault(WebServiceFaultsEnum.UNAUTHORIZED_ACCESS, "The POS was not assigned to a member");
            } else if (!accessServiceLocal.isChannelEnabledForMember(Channel.POS, pos.getMemberPos().getMember())) { // validates the POS channel
                throw WebServiceHelper.fault(WebServiceFaultsEnum.UNAUTHORIZED_ACCESS, "The POS channel is not enabled for the member: " + pos.getMemberPos().getMember());
            } else if (pos.getMemberPos().getStatus() != MemberPos.Status.ACTIVE) {
                Throwable th;
                if (pos.getMemberPos().getStatus() == MemberPos.Status.BLOCKED) { // generate a system alert if the pos was blocked
                    final String remoteAddress = request.getRemoteAddr();
                    posServiceLocal.notifyBlockedPosUsed(pos, remoteAddress);
                    th = new BlockedPosException("Current POS status: " + pos.getMemberPos().getStatus());
                } else {
                    th = new Exception("Current POS status: " + pos.getMemberPos().getStatus());
                }
                throw WebServiceHelper.fault(WebServiceFaultsEnum.INACTIVE_POS, th);
            } else if (params instanceof IPosPinParameter) { // validate the pos pin too
                final IPosPinParameter posPinParams = (IPosPinParameter) params;
                memberPosServiceLocal.checkPin(pos.getMemberPos(), posPinParams.getPosPin());
            }

            // Initialize the logged user
            LoggedUser.init(pos, request.getRemoteAddr(), null);

            WebServiceHelper.initializeContext(pos, message);

        } catch (final Exception e) {
            WebServiceHelper.initializeContext(message);
            if (e instanceof SoapFault) {
                throw (SoapFault) e;
            } else {
                throw WebServiceHelper.fault(e);
            }
        }
    }

    public void setAccessServiceLocal(final AccessServiceLocal accessService) {
        accessServiceLocal = accessService;
    }

    public void setMemberPosServiceLocal(final MemberPosServiceLocal memberPosService) {
        memberPosServiceLocal = memberPosService;
    }

    public void setPosServiceLocal(final PosServiceLocal posService) {
        posServiceLocal = posService;
    }
}
