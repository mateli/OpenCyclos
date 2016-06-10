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
package nl.strohalm.cyclos.controls.access.channels;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.services.access.ChangePinDTO;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.CredentialsAlreadyUsedException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;

import org.apache.struts.action.ActionForward;

/**
 * Action used to change a member's pin
 * @author Jefferson Magno
 */
public class ChangePinAction extends BaseFormAction {

    private DataBinder<ChangePinDTO> dataBinder;

    public DataBinder<ChangePinDTO> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<ChangePinDTO> binder = BeanBinder.instance(ChangePinDTO.class);
            binder.registerBinder("user", PropertyBinder.instance(MemberUser.class, "memberId"));
            binder.registerBinder("credentials", PropertyBinder.instance(String.class, "credentials"));
            binder.registerBinder("newPin", PropertyBinder.instance(String.class, "newPin"));
            binder.registerBinder("newPinConfirmation", PropertyBinder.instance(String.class, "newPinConfirmation"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final ChangePinDTO params = resolveDto(context);
        MemberUser user = params.getUser();

        try {
            user = accessService.changePin(params);
            ActionForward forward;
            if (context.getUser().equals(user)) {
                forward = context.getSuccessForward();
            } else {
                forward = ActionHelper.redirectWithParam(request, context.getSuccessForward(), "memberId", user.getId());
            }
            context.sendMessage("changePin.modified");
            return forward;
        } catch (final InvalidCredentialsException e) {
            final String key = "changePin.error." + (e.getCredentialsType() == Credentials.TRANSACTION_PASSWORD ? "invalidTransactionPassword" : "invalidPassword");
            return context.sendError(key);
        } catch (final BlockedCredentialsException e) {
            if (e.getCredentialsType() == Credentials.TRANSACTION_PASSWORD) {
                context.getSession().setAttribute("returnTo", context.getPathPrefix() + "/manageExternalAcccess");
                return context.sendError("changePin.error.blockedTransactionPassword");
            } else {
                request.getSession().invalidate();
                return context.sendError("changePin.error.userBlocked");
            }
        } catch (final CredentialsAlreadyUsedException e) {
            return context.sendError("changePin.error.alreadyUsed");
        }
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final ChangePinDTO params = resolveDto(context);
        accessService.validateChangePin(params);
    }

    private ChangePinDTO resolveDto(final ActionContext context) {
        final ChangePinForm form = context.getForm();
        final ChangePinDTO params = getDataBinder().readFromString(form);
        if (params.getUser() == null) {
            params.setUser(context.<MemberUser> getUser());
        }
        return params;
    }
}
