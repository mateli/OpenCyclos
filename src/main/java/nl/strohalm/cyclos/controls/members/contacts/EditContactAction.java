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
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.members.Contact;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.elements.ContactService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;

/**
 * Base action for contact edition
 * @author luis
 */
public class EditContactAction extends BaseFormAction {

    private ContactService      contactService;
    private DataBinder<Contact> dataBinder;

    public EditContactAction() {
    }

    public ContactService getContactService() {
        return contactService;
    }

    public DataBinder<Contact> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<Contact> binder = BeanBinder.instance(Contact.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("contact", PropertyBinder.instance(Member.class, "contact", ReferenceConverter.instance(Member.class)));
            binder.registerBinder("owner", PropertyBinder.instance(Member.class, "owner", ReferenceConverter.instance(Member.class)));
            binder.registerBinder("notes", PropertyBinder.instance(String.class, "notes"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setContactService(final ContactService memberService) {
        contactService = memberService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final ContactForm form = context.getForm();
        final Contact contact = dataBinder.readFromString(form.getContact());
        contactService.save(contact);
        context.sendMessage("contact.modified");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final ContactForm form = context.getForm();
        final Contact contact = contactService.load(form.getId(), RelationshipHelper.nested(Contact.Relationships.CONTACT, Element.Relationships.USER));
        getDataBinder().writeAsString(form.getContact(), contact);
        context.getRequest().setAttribute("contact", contact.getContact());
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final ContactForm form = context.getForm();
        final Contact contact = dataBinder.readFromString(form.getContact());
        contactService.validate(contact);
    }
}
