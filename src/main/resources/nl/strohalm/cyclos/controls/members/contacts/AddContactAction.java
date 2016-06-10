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
package nl.strohalm.cyclos.controls.members.contacts;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.members.Contact;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.elements.ContactService;
import nl.strohalm.cyclos.services.elements.exceptions.ContactAlreadyExistException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Add a contact to the logged member's contact list
 * @author luis
 */
public class AddContactAction extends BaseAction {

    private ContactService contactService;
    private ResponseHelper responseHelper;

    public ContactService getContactService() {
        return contactService;
    }

    @Inject
    public void setContactService(final ContactService contactService) {
        this.contactService = contactService;
    }

    @Inject
    public void setResponseHelper(final ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final AddContactForm form = context.getForm();
        final long memberId = form.getMemberId();

        if (RequestHelper.isValidation(context.getRequest())) {
            try {
                if (memberId <= 0L) {
                    throw new ValidationException("contact", "member.member", new RequiredError());
                }
                responseHelper.writeValidationSuccess(context.getResponse());
            } catch (final ValidationException e) {
                responseHelper.writeValidationErrors(context.getResponse(), e);
            }
            return null;

        }
        if (memberId <= 0L) {
            throw new ValidationException();
        }

        final Contact contact = new Contact();
        final Member member = (Member) context.getAccountOwner();
        contact.setOwner(member);
        contact.setContact(EntityHelper.reference(Member.class, memberId));
        try {
            contactService.save(contact);
            context.sendMessage("contact.inserted");
        } catch (final ContactAlreadyExistException e) {
            context.sendMessage("contact.error.alreadyExists");
        }

        if (form.isDirect()) {
            return context.findForward("backToList");
        } else {
            return ActionHelper.redirectWithParam(context.getRequest(), context.findForward("backToProfile"), "memberId", memberId);
        }
    }

}
