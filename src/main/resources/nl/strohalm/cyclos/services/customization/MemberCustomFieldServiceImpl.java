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
package nl.strohalm.cyclos.services.customization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField.Access;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField.Indexing;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.entities.members.RegisteredMember;
import nl.strohalm.cyclos.entities.members.imports.ImportedMember;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation for {@link MemberCustomFieldService}
 * 
 * @author luis
 */
public class MemberCustomFieldServiceImpl extends BaseGlobalCustomFieldServiceImpl<MemberCustomField> implements MemberCustomFieldServiceLocal {

    public MemberCustomFieldServiceImpl() {
        super(MemberCustomField.class, CustomField.Nature.MEMBER);
    }

    @Override
    public Validator getValueValidator(final MemberGroup group, final Access access) {
        final List<MemberCustomField> fields = new ArrayList<MemberCustomField>(list());
        if (access != null) {
            for (final Iterator<MemberCustomField> iterator = fields.iterator(); iterator.hasNext();) {
                final MemberCustomField field = iterator.next();
                if (access == Access.REGISTRATION && (field.getUpdateAccess() == Access.MEMBER_NOT_REGISTRATION || field.getVisibilityAccess() == Access.MEMBER_NOT_REGISTRATION)) {
                    iterator.remove();
                } else if (access.compareTo(field.getUpdateAccess()) > 0 || access.compareTo(field.getVisibilityAccess()) > 0) {
                    iterator.remove();
                }
            }
        }
        return getValueValidator(customFieldHelper.onlyForGroup(fields, group));
    }

    @Override
    public void saveValues(final ImportedMember importedMember) {
        getValueValidator(importedMember.getImport().getGroup(), MemberCustomField.Access.REGISTRATION).validate(importedMember);
        doSaveValues(importedMember);
    }

    @Override
    public void saveValues(RegisteredMember registeredMember) {
        final boolean isPublic = !LoggedUser.hasUser();
        boolean byOwner = false;
        boolean byBroker = false;
        Element element = null;
        Group group = null;
        if (isPublic) {
            group = registeredMember.getMemberGroup();
            byOwner = true;
        } else {
            element = LoggedUser.element();
            group = element.getGroup();
            byOwner = element.equals(registeredMember);
            byBroker = element.equals(registeredMember.getBroker());
        }

        MemberCustomField.Access access = null;
        if (isPublic) {
            access = MemberCustomField.Access.REGISTRATION;
        } else if (byOwner) {
            access = MemberCustomField.Access.MEMBER;
        } else if (byBroker) {
            access = MemberCustomField.Access.BROKER;
        } else if (LoggedUser.hasUser() && LoggedUser.isAdministrator()) {
            access = MemberCustomField.Access.ADMIN;
        }

        final Collection<MemberCustomFieldValue> customValues = registeredMember.getCustomValues();
        registeredMember = (RegisteredMember) fetchService.fetch((Entity) registeredMember, Element.Relationships.GROUP, PendingMember.Relationships.MEMBER_GROUP, Member.Relationships.BROKER);
        getValueValidator(registeredMember.getMemberGroup(), access).validate(registeredMember);

        if (customValues != null) {
            for (final MemberCustomFieldValue value : customValues) {
                final MemberCustomField field = (MemberCustomField) fetchService.fetch(value.getField());
                final Access updateAccess = field.getUpdateAccess();
                if (!updateAccess.granted(group, byOwner, byBroker, isPublic, LoggedUser.isWebService())) {
                    // The member cannot change the value. Ensure the old value will remain
                    CustomFieldValue current;
                    try {
                        current = customFieldValueDao.load(field, registeredMember);
                    } catch (final EntityNotFoundException e) {
                        // There is no saved value still
                        current = new MemberCustomFieldValue();
                    }
                    value.setStringValue(current.getStringValue());
                    value.setPossibleValue(current.getPossibleValue());
                }
            }
        }
        doSaveValues(registeredMember);
    }

    @Override
    protected void appendValidations(final Validator validator) {
        validator.general(new GeneralValidation() {
            private static final long serialVersionUID = -7872725176651230479L;

            @Override
            public ValidationError validate(final Object object) {
                MemberCustomField field = (MemberCustomField) object;
                // We can only allow indexing to happen when the field visibility is to other members
                if (field.getIndexing() != Indexing.NONE && field.getVisibilityAccess() != Access.OTHER) {
                    return new ValidationError("customField.member.error.indexingVisibility");
                }
                return null;
            }
        });
    }

    @Override
    protected Collection<? extends Relationship> resolveAdditionalFetch() {
        return Arrays.asList(MemberCustomField.Relationships.GROUPS);
    }
}
