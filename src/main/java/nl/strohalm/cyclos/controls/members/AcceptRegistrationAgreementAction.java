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
package nl.strohalm.cyclos.controls.members;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.controls.BasePublicFormAction;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.entities.members.RegisteredMember;
import nl.strohalm.cyclos.entities.members.RegistrationAgreement;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.LoginHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to accept a registration agreement
 * 
 * @author luis
 */
public class AcceptRegistrationAgreementAction extends BasePublicFormAction {

    @Override
    protected ActionForward handleDisplay(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) {
        // Find the RegisteredMember
        final RegisteredMember registeredMember = findRegisteredMember(actionForm, request);

        // Find the group / agreement
        final RegistrationAgreement registrationAgreement = findAgreement(registeredMember);
        if (registrationAgreement == null) {
            // No agreement?
            throw new ValidationException();
        }

        // Store the attributes
        request.setAttribute("registeredMember", registeredMember);
        request.setAttribute("registrationAgreement", registrationAgreement);

        return mapping.getInputForward();
    }

    @Override
    protected ActionForward handleSubmit(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) {
        // Find the RegisteredMember And agreement
        final RegisteredMember registeredMember = findRegisteredMember(actionForm, request);

        // Ensure the member won't be asked again to accept the agreement
        request.getSession().removeAttribute("shallAcceptRegistrationAgreement");

        // Accept the agreement
        if (registeredMember instanceof PendingMember) {
            // A pending member, probably registered by broker or admin
            final PendingMember pendingMember = (PendingMember) registeredMember;
            try {
                elementService.setRegistrationAgreementAgreed(pendingMember);
            } catch (final DaoException e) {
                // The pending member has been removed
                final String validationKeyMessage = messageHelper.message("pendingMember.validationKey");
                return ActionHelper.sendError(mapping, request, response, "errors.invalid", validationKeyMessage);
            }
            // Go to the regular registration validation
            return ActionHelper.redirectWithParam(request, mapping.findForward("validateRegistration"), "key", pendingMember.getValidationKey());
        } else {
            // A member, probably changed from a group which had another registration agreement
            elementService.acceptAgreement(request.getRemoteAddr());
            return mapping.findForward("login");
        }
    }

    private RegistrationAgreement findAgreement(final RegisteredMember registeredMember) {
        final MemberGroup group = groupService.load(registeredMember.getMemberGroup().getId(), MemberGroup.Relationships.REGISTRATION_AGREEMENT);
        return group.getRegistrationAgreement();
    }

    private RegisteredMember findRegisteredMember(final ActionForm actionForm, final HttpServletRequest request) {
        RegisteredMember registeredMember = null;
        final User loggedUser = LoginHelper.getLoggedUser(request);
        if (loggedUser == null) {
            // No logged user means the user is coming from a public e-mail validation: get a PendingMember by validation key
            final AcceptRegistrationAgreementForm form = (AcceptRegistrationAgreementForm) actionForm;
            try {
                final String validationKey = form.getKey();
                registeredMember = elementService.loadPendingMemberByKey(validationKey);
            } catch (final Exception e) {
                // ignore: will break later
            }
        } else if (loggedUser instanceof MemberUser) {
            registeredMember = ((MemberUser) loggedUser).getMember();
        }

        // Not found
        if (registeredMember == null) {
            throw new ValidationException();
        }
        return registeredMember;
    }

}
