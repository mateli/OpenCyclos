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
package nl.strohalm.cyclos.controls.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.controls.AbstractActionContext;
import nl.strohalm.cyclos.controls.mobile.exceptions.MobileException;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User.TransactionPasswordStatus;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.MessageHelper;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action context for mobile operations
 * @author luis
 */
public class MobileActionContext extends AbstractActionContext {

    private static final long serialVersionUID = -1199519771150330272L;

    public MobileActionContext(final ActionMapping actionMapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response, final MessageHelper messageHelper, final MemberUser user) {
        super(actionMapping, actionForm, request, response, messageHelper, user);
    }

    public String getChannel() {
        return isWap1Request() ? Channel.WAP1 : Channel.WAP2;
    }

    public MemberAccount getCurrentAccount() {
        return (MemberAccount) getSession().getAttribute("mobileAccount");
    }

    public MemberAccountType getCurrentAccountType() {
        return (MemberAccountType) getSession().getAttribute("mobileAccountType");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Member getElement() {
        return super.getElement();
    }

    public Member getMember() {
        return getElement();
    }

    @Override
    @SuppressWarnings("unchecked")
    public MemberUser getUser() {
        return super.getUser();
    }

    public boolean isWap1Request() {
        return MobileHelper.isWap1Request(getRequest());
    }

    public ActionForward sendException(final MobileException e) {
        getSession().setAttribute("mobileException", e);
        return getActionMapping().findForward(isWap1Request() ? "wapError" : "mobileError");
    }

    public void setCurrentAccount(final MemberAccount account) {
        getSession().setAttribute("mobileAccount", account);
    }

    public void setCurrentAccountType(final MemberAccountType type) {
        getSession().setAttribute("mobileAccountType", type);
    }

    @Override
    protected void validateTransactionPassword(final TransactionPasswordStatus tpStatus) {
        switch (tpStatus) {
            case BLOCKED:
                throw new MobileException("mobile.transactionPassword.error.blocked");
            case PENDING:
            case NEVER_CREATED:
                throw new MobileException("mobile.transactionPassword.error.pending");
        }
    }

}
