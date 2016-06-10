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
package nl.strohalm.cyclos.controls.members.brokering;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.remarks.Remark;
import nl.strohalm.cyclos.services.elements.BrokeringService;
import nl.strohalm.cyclos.services.elements.ChangeBrokerDTO;
import nl.strohalm.cyclos.services.elements.RemarkService;
import nl.strohalm.cyclos.services.elements.exceptions.CircularBrokeringException;
import nl.strohalm.cyclos.services.elements.exceptions.MemberAlreadyInBrokeringsException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to change a member's broker
 * @author luis
 */
public class ChangeBrokerAction extends BaseFormAction {

    public static DataBinder<ChangeBrokerDTO> changeBrokerDTODataBinder() {
        final BeanBinder<ChangeBrokerDTO> binder = BeanBinder.instance(ChangeBrokerDTO.class);
        binder.registerBinder("member", PropertyBinder.instance(Member.class, "memberId"));
        binder.registerBinder("newBroker", PropertyBinder.instance(Member.class, "newBrokerId"));
        binder.registerBinder("comments", PropertyBinder.instance(String.class, "comments"));
        binder.registerBinder("suspendCommission", PropertyBinder.instance(Boolean.TYPE, "suspendCommission"));
        return binder;
    }

    private BrokeringService            brokeringService;
    private DataBinder<ChangeBrokerDTO> dataBinder;
    private RemarkService               remarkService;

    public BrokeringService getBrokeringService() {
        return brokeringService;
    }

    public DataBinder<ChangeBrokerDTO> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = changeBrokerDTODataBinder();
        }
        return dataBinder;
    }

    public RemarkService getRemarkService() {
        return remarkService;
    }

    @Inject
    public void setBrokeringService(final BrokeringService brokeringService) {
        this.brokeringService = brokeringService;
    }

    @Inject
    public void setRemarkService(final RemarkService remarkService) {
        this.remarkService = remarkService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final ChangeBrokerForm form = context.getForm();
        final ChangeBrokerDTO dto = getDataBinder().readFromString(form);
        try {
            brokeringService.changeBroker(dto);
        } catch (final MemberAlreadyInBrokeringsException e) {
            return context.sendError("brokering.error.memberAlreadyInBrokering");
        } catch (final CircularBrokeringException e) {
            return context.sendError("brokering.error.circularBrokering");
        }
        context.sendMessage("changeBroker.changed");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "memberId", form.getMemberId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final ChangeBrokerForm form = context.getForm();
        Member member = null;
        if (form.getMemberId() > 0L) {
            final Element element = elementService.load(form.getMemberId(), Element.Relationships.USER, RelationshipHelper.nested(Member.Relationships.BROKER, Element.Relationships.USER));
            if (element instanceof Member) {
                member = (Member) element;
            }
        }
        if (member == null) {
            throw new ValidationException();
        }
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("member", member);

        final List<? extends Remark> history = remarkService.listBrokerRemarksFor(member);
        request.setAttribute("history", history);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final ChangeBrokerForm form = context.getForm();
        final ChangeBrokerDTO dto = getDataBinder().readFromString(form);
        brokeringService.validate(dto);
    }
}
