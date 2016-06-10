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

import nl.strohalm.cyclos.dao.members.ContactDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Contact;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.elements.exceptions.ContactAlreadyExistException;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;
import nl.strohalm.cyclos.webservices.model.ContactVO;
import nl.strohalm.cyclos.webservices.utils.ContactHelper;

/**
 * Implenting class for contacts service
 * @author rafael
 */
public class ContactServiceImpl implements ContactServiceLocal {

    private ContactDAO    contactDao;
    private ContactHelper contactHelper;

    public ContactDAO getContactDao() {
        return contactDao;
    }

    @Override
    public ContactVO getContactVO(final Long contactId, final boolean useFields, final boolean useImages) {
        if (contactId == null) {
            return null;
        }
        Contact contact = load(contactId);
        return contactHelper.toVO(contact, useFields, useImages);
    }

    @Override
    public List<ContactVO> getContactVOs(final List<Contact> contacts, final boolean useFields, final boolean useImages) {
        return contactHelper.toList(contacts, useFields, useImages);
    }

    @Override
    public List<Contact> list(final Member owner) {
        return contactDao.listByMember(owner);
    }

    @Override
    public Contact load(final Long id, final Relationship... fetch) {
        return contactDao.load(id, fetch);
    }

    @Override
    public Contact load(final Member owner, final Member contact, final Relationship... fetch) {
        return contactDao.load(owner, contact, fetch);
    }

    @Override
    public Contact loadIfExists(final Member owner, final Member member, final Relationship... fetch) {
        try {
            return load(owner, member, fetch);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public int remove(final Long... id) {
        return contactDao.delete(id);
    }

    @Override
    public Contact save(final Contact contact) {
        contact.setOwner((Member) LoggedUser.accountOwner());
        validate(contact);
        if (contact.isTransient()) {
            // Check if the contact already exists
            final List<Contact> list = contactDao.listByMember(contact.getOwner());
            for (final Contact current : list) {
                if (current.getContact().equals(contact.getContact())) {
                    // Already exists
                    throw new ContactAlreadyExistException();
                }
            }
            return contactDao.insert(contact);
        } else {
            return contactDao.update(contact);
        }
    }

    public void setContactDao(final ContactDAO contactDao) {
        this.contactDao = contactDao;
    }

    public void setContactHelper(final ContactHelper contactHelper) {
        this.contactHelper = contactHelper;
    }

    @Override
    public void validate(final Contact contact) {
        getValidator().validate(contact);
    }

    private Validator getValidator() {
        final Validator validator = new Validator("contact");
        validator.property("contact").required().add(new PropertyValidation() {
            private static final long serialVersionUID = -8708862725308048695L;

            @Override
            public ValidationError validate(final Object object, final Object name, final Object value) {
                final Contact contact = (Contact) object;
                // The contact cannot be the owner
                final Element loggedElement = LoggedUser.hasUser() ? LoggedUser.element() : null;
                if (loggedElement != null && loggedElement.equals(contact.getContact())) {
                    return new InvalidError();
                }
                return null;
            }
        });
        validator.property("notes").maxLength(1000);
        return validator;
    }

}
