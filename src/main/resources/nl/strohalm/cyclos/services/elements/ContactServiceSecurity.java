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
package nl.strohalm.cyclos.services.elements;

import java.util.List;

import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Contact;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.webservices.model.ContactVO;

/**
 * Security implementation for {@link ContactService}
 * 
 * @author Rinke
 */
public class ContactServiceSecurity extends BaseServiceSecurity implements ContactService {

    private ContactServiceLocal contactService;

    @Override
    public ContactVO getContactVO(final Long contactId, final boolean useFields, final boolean useImages) {
        Contact contact = contactService.load(contactId, Contact.Relationships.OWNER);
        checkView(contact.getOwner());
        return contactService.getContactVO(contactId, useFields, useImages);
    }

    @Override
    public List<ContactVO> getContactVOs(final List<Contact> contacts, final boolean useFields, final boolean useImages) {
        for (Contact contact : contacts) {
            contact = contactService.load(contact.getId(), Contact.Relationships.OWNER);
            checkView(contact.getOwner());
        }
        return contactService.getContactVOs(contacts, useFields, useImages);
    }

    @Override
    public List<Contact> list(final Member owner) {
        checkView(owner);
        return contactService.list(owner);
    }

    @Override
    public Contact load(final Long id, final Relationship... fetch) {
        Relationship[] newFetch = addToFetch(fetch, Contact.Relationships.OWNER);
        Contact contact = contactService.load(id, newFetch);
        checkView(contact.getOwner());
        return contact;
    }

    @Override
    public Contact load(final Member owner, final Member member, final Relationship... fetch) {
        Relationship[] newFetch = addToFetch(fetch, Contact.Relationships.OWNER);
        Contact contact = contactService.load(owner, member, newFetch);
        checkView(contact.getOwner());
        return contact;
    }

    @Override
    public Contact loadIfExists(final Member owner, final Member member, final Relationship... fetch) {
        Relationship[] newFetch = addToFetch(fetch, Contact.Relationships.OWNER);
        Contact contact = contactService.loadIfExists(owner, member, newFetch);
        if (contact != null) {
            checkView(contact.getOwner());
        }
        return contact;
    }

    @Override
    public int remove(final Long... id) {
        for (Long tempId : id) {
            Contact contact = contactService.load(tempId, Contact.Relationships.OWNER);
            checkManage(contact.getOwner());
        }
        return contactService.remove(id);
    }

    @Override
    public Contact save(final Contact contact) {
        Member owner = fetchService.fetch(contact.getOwner());
        checkManage(owner);
        permissionService.checkRelatesTo(contact.getContact());
        return contactService.save(contact);
    }

    public void setContactServiceLocal(final ContactServiceLocal contactService) {
        this.contactService = contactService;
    }

    @Override
    public void validate(final Contact contact) throws ValidationException {
        // no permissionschecks on validation
        contactService.validate(contact);
    }

    private void checkManage(final Member member) {
        permissionService.permission(member)
                .member()
                .operator(OperatorPermission.CONTACTS_MANAGE)
                .check();
    }

    private void checkView(final Member member) {
        permissionService.permission(member)
                .member()
                .operator(OperatorPermission.CONTACTS_VIEW)
                .check();
    }

}
