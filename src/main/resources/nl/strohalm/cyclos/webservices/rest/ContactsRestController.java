/*
   This file is part of Cyclos.

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
package nl.strohalm.cyclos.webservices.rest;

import java.util.List;

import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Contact;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.elements.ContactService;
import nl.strohalm.cyclos.services.elements.MemberService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.webservices.model.ContactVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller which handles /contacts paths
 * 
 * @author luis
 */
@Controller
public class ContactsRestController extends BaseRestController {

    /**
     * JSON class which holds notes for contacts
     * @author luis
     */
    public static class ContactNotes {
        private String notes;

        public String getNotes() {
            return notes;
        }

        public void setNotes(final String notes) {
            this.notes = notes;
        }
    }

    private ContactService contactService;
    private MemberService  memberService;

    /**
     * Handles the contact list
     */
    @RequestMapping(value = "contacts", method = RequestMethod.GET)
    @ResponseBody
    public List<ContactVO> listContacts() {
        Member member = LoggedUser.element();
        List<Contact> contacts = contactService.list(member);
        return contactService.getContactVOs(contacts, true, true);
    }

    /**
     * Gets a contact by id
     */
    @RequestMapping(value = "contacts/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ContactVO loadById(@PathVariable final Long id) {
        return contactService.getContactVO(id, true, true);
    }

    /**
     * Saves a contact, finding the member by id
     */
    @RequestMapping(value = "contacts/byMemberId/{memberId}", method = RequestMethod.GET)
    @ResponseBody
    public ContactVO loadByMemberId(@PathVariable final Long memberId) {
        return getContactVO(memberId, null);
    }

    /**
     * Saves a contact, finding the member by principal
     */
    @RequestMapping(value = "contacts/byMemberPrincipal/{principal}", method = RequestMethod.GET)
    @ResponseBody
    public ContactVO loadByMemberPrincipal(@PathVariable final String principal) {
        return getContactVO(null, principal);
    }

    /**
     * Removes a contact by id
     */
    @RequestMapping(value = "contacts/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeById(@PathVariable final Long id) {
        Contact contact;
        try {
            contact = contactService.load(id, RelationshipHelper.nested(Contact.Relationships.CONTACT, Element.Relationships.GROUP));
        } catch (Exception e) {
            throw new EntityNotFoundException(Contact.class);
        }
        remove(contact);
    }

    /**
     * Removes a contact, finding the member by id
     */
    @RequestMapping(value = "contacts/byMemberId/{memberId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeByMemberId(@PathVariable final Long memberId) {
        Contact contact = getContact(memberId, null);
        remove(contact);
    }

    /**
     * Removes a contact, finding the member by principal
     */
    @RequestMapping(value = "contacts/byMemberPrincipal/{principal}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeByMemberPrincipal(@PathVariable final String principal) {
        Contact contact = getContact(null, principal);
        remove(contact);
    }

    /**
     * Saves a contact, finding the member by id
     */
    @RequestMapping(value = "contacts/byMemberId/{memberId}", method = RequestMethod.POST)
    @ResponseBody
    public ContactVO saveByMemberId(@PathVariable final Long memberId, @RequestBody final ContactNotes params) {
        return saveContact(memberId, null, getNotes(params));
    }

    /**
     * Saves a contact, finding the member by principal
     */
    @RequestMapping(value = "contacts/byMemberPrincipal/{principal}", method = RequestMethod.POST)
    @ResponseBody
    public ContactVO saveByMemberPrincipal(@PathVariable final String principal, @RequestBody final ContactNotes params) {
        return saveContact(null, principal, getNotes(params));
    }

    public void setContactService(final ContactService contactService) {
        this.contactService = contactService;
    }

    public void setMemberService(final MemberService memberService) {
        this.memberService = memberService;
    }

    private Contact getContact(final Long memberId, final String memberPrincipal) {
        Member member = memberService.loadByIdOrPrincipal(memberId, null, memberPrincipal);
        if (member == null) {
            throw new EntityNotFoundException(Member.class);
        }
        Member loggedMember = LoggedUser.member();
        Contact contact = contactService.load(loggedMember, member);
        return contact;
    }

    private ContactVO getContactVO(final Long memberId, final String memberPrincipal) {
        Contact contact = getContact(memberId, memberPrincipal);
        return contactService.getContactVO(contact.getId(), true, true);
    }

    private String getNotes(final ContactNotes notes) {
        return notes == null ? null : notes.getNotes();
    }

    private void remove(final Contact contact) {
        contactService.remove(contact.getId());
    }

    private ContactVO saveContact(final Long memberId, final String memberPrincipal, final String notes) {
        Member member = memberService.loadByIdOrPrincipal(memberId, null, memberPrincipal);
        if (member == null) {
            throw new EntityNotFoundException(Member.class);
        }
        Member loggedMember = LoggedUser.member();
        Contact contact = contactService.loadIfExists(loggedMember, member);
        if (contact == null) {
            contact = new Contact();
            contact.setOwner(loggedMember);
            contact.setContact(member);
        }
        contact.setNotes(notes);
        Contact savedContact = contactService.save(contact);
        return contactService.getContactVO(savedContact.getId(), true, true);
    }
}
