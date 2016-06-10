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
package nl.strohalm.cyclos.webservices.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Contact;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.webservices.model.ContactVO;
import nl.strohalm.cyclos.webservices.model.MemberVO;

import org.apache.commons.collections.CollectionUtils;

/**
 * Utility class for contacts<br>
 * <b>WARN</b>: Be aware that this helper <b>doesn't</b> access the services through the security layer. They are all local services.
 * @author luis
 */
public class ContactHelper {

    private MemberCustomFieldServiceLocal memberCustomFieldService;
    private MemberHelper                  memberHelper;
    private FetchServiceLocal             fetchService;
    private CustomFieldHelper             customFieldHelper;

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    public void setMemberHelper(final MemberHelper memberHelper) {
        this.memberHelper = memberHelper;
    }

    /**
     * Converts a list of contacts into VOs
     */
    public List<ContactVO> toList(final List<Contact> contacts, final boolean useFields, final boolean useImages) {
        if (CollectionUtils.isEmpty(contacts)) {
            return Collections.emptyList();
        }
        List<ContactVO> contactVOs = new ArrayList<ContactVO>(contacts.size());
        List<MemberCustomField> customFields = null;
        if (useFields) {
            customFields = memberCustomFieldService.list();
        }
        Map<Group, List<MemberCustomField>> fieldsPerGroup = new HashMap<Group, List<MemberCustomField>>();
        for (Contact contact : contacts) {
            // Store the fields on a map per group, so we don't need to filter them again for each user on the contact list
            List<MemberCustomField> fields = null;
            if (useFields) {
                Group group = contact.getContact().getGroup();
                fields = fieldsPerGroup.get(group);
                if (fields == null) {
                    fields = customFieldHelper.onlyVisibleFields(customFields, (MemberGroup) fetchService.fetch(contact.getContact().getGroup()));
                    fieldsPerGroup.put(group, fields);
                }
            }
            // Create the contact
            ContactVO contactVO = toVO(contact, fields, useImages);
            contactVOs.add(contactVO);
        }
        return contactVOs;
    }

    public ContactVO toVO(final Contact contact, final boolean useFields, final boolean useImages) {
        Member member = contact.getContact();
        List<MemberCustomField> fields = null;
        if (useFields) {
            fields = customFieldHelper.onlyVisibleFields(memberCustomFieldService.list(), member.getMemberGroup());
        }
        return toVO(contact, fields, useImages);
    }

    private ContactVO toVO(final Contact contact, final List<MemberCustomField> customFields, final boolean useImages) {
        if (contact == null) {
            return null;
        }
        MemberVO memberVO = memberHelper.toVO(contact.getContact(), customFields, useImages);
        ContactVO contactVO = new ContactVO();
        contactVO.setId(contact.getId());
        contactVO.setMember(memberVO);
        contactVO.setNotes(contact.getNotes());
        return contactVO;
    }
}
