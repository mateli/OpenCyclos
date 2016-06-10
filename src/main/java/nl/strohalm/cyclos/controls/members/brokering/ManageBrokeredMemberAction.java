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

import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.elements.BrokeringService;
import nl.strohalm.cyclos.services.elements.ChangeBrokerDTO;
import nl.strohalm.cyclos.services.elements.exceptions.CircularBrokeringException;
import nl.strohalm.cyclos.services.elements.exceptions.MemberAlreadyInBrokeringsException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.ResponseHelper.Status;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to add a member to a broker
 * @author luis
 */
public class ManageBrokeredMemberAction extends BaseFormAction {

    private DataBinder<ChangeBrokerDTO> dataBinder;
    private BrokeringService            brokeringService;

    public BrokeringService getBrokeringService() {
        return brokeringService;
    }

    public DataBinder<ChangeBrokerDTO> getDataBinder() {
        if (dataBinder == null) {
            dataBinder = ChangeBrokerAction.changeBrokerDTODataBinder();
        }
        return dataBinder;
    }

    @Inject
    public void setBrokeringService(final BrokeringService brokeringService) {
        this.brokeringService = brokeringService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final ChangeBrokerForm form = context.getForm();
        final ChangeBrokerDTO dto = getDataBinder().readFromString(form);
        try {
            brokeringService.changeBroker(dto);
            context.sendMessage("changeBroker.changed");
        } catch (final MemberAlreadyInBrokeringsException e) {
            context.sendMessage("brokering.error.memberAlreadyInBrokering");
        } catch (final CircularBrokeringException e) {
            context.sendMessage("brokering.error.circularBrokering");
        }
        long brokerId = form.getBrokerId();
        if (brokerId <= 0L) {
            brokerId = form.getNewBrokerId();
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "memberId", brokerId);
    }

    @Override
    protected ActionForward handleValidation(final ActionContext context) {
        try {
            final ChangeBrokerForm form = context.getForm();
            final ChangeBrokerDTO dto = getDataBinder().readFromString(form);
            brokeringService.validate(dto);
            final Member member = elementService.load(dto.getMember().getId(), Member.Relationships.BROKER);
            final Map<String, Object> fields = new HashMap<String, Object>();
            final Member broker = member.getBroker();
            final Member newBroker = (Member) (dto.getNewBroker() == null ? null : elementService.load(dto.getNewBroker().getId()));
            fields.put("currentBroker", broker == null ? "" : broker.getName());
            fields.put("newBroker", newBroker == null ? "" : newBroker.getName());
            fields.put("member", elementService.load(dto.getMember().getId()).getName());
            responseHelper.writeStatus(context.getResponse(), Status.SUCCESS, fields);
        } catch (final ValidationException e) {
            responseHelper.writeValidationErrors(context.getResponse(), e);
        }
        return null;
    }
}
