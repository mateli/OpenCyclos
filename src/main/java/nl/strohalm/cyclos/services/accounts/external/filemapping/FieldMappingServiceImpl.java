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
package nl.strohalm.cyclos.services.accounts.external.filemapping;

import nl.strohalm.cyclos.dao.FetchDAO;
import nl.strohalm.cyclos.dao.accounts.external.filemapping.FieldMappingDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FieldMapping;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMapping;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMappingWithFields;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Service implementation for field mappings
 * @author Jefferson Magno
 */
public class FieldMappingServiceImpl implements FieldMappingServiceLocal {

    /**
     * Validates the field mapping before saving. If the field is related to a member custom field, the member custom field is required
     * @author jefferson
     */
    public class MemberFieldValidation implements GeneralValidation {

        private static final long serialVersionUID = -6340054946112781001L;

        @Override
        public ValidationError validate(final Object object) {
            final FieldMapping fieldMapping = (FieldMapping) object;

            if (fieldMapping.getField() == FieldMapping.Field.MEMBER_CUSTOM_FIELD) {
                if (fieldMapping.getMemberField() == null) {
                    String field = messageResolver.message("fieldMapping.memberField");
                    return new RequiredError(field);
                }
            }
            return null;
        }
    }

    /**
     * Validates the field mapping before saving. If we are updating a persistent field mapping the order of the field is required
     * @author jefferson
     */
    public class OrderValidation implements GeneralValidation {

        private static final long serialVersionUID = -1936877851448398480L;

        @Override
        public ValidationError validate(final Object object) {
            final FieldMapping fieldMapping = (FieldMapping) object;

            if (fieldMapping.isPersistent()) {
                if (fieldMapping.getOrder() <= 0) {
                    return new ValidationError("fieldMapping.order.error.required");
                }
            }
            return null;
        }
    }

    private FetchDAO                fetchDao;
    private FileMappingServiceLocal fileMappingService;
    private FieldMappingDAO         fieldMappingDao;
    private MessageResolver         messageResolver;

    @Override
    public FieldMapping load(final Long id, final Relationship... fetch) {
        return fieldMappingDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        for (final Long id : ids) {
            final FieldMapping field = fieldMappingDao.load(id, FieldMapping.Relationships.FILE_MAPPING);
            final FileMappingWithFields fileMapping = (FileMappingWithFields) fileMappingService.load(field.getFileMapping().getId(), FileMappingWithFields.Relationships.FIELDS);
            boolean toEnumerate = false;

            for (final FieldMapping enumerate : fileMapping.getFields()) {
                if (enumerate.equals(field)) {
                    toEnumerate = true;
                } else if (toEnumerate) {
                    enumerate.setOrder(enumerate.getOrder() - 1);
                    fieldMappingDao.update(enumerate);
                }
            }
        }

        return fieldMappingDao.delete(ids);
    }

    @Override
    public FieldMapping save(final FieldMapping fieldMapping) {
        validate(fieldMapping);
        if (fieldMapping.isTransient()) {
            FileMapping fileMapping = fieldMapping.getFileMapping();
            fileMapping = fetchDao.fetch(fileMapping, FileMappingWithFields.Relationships.FIELDS);
            final FileMappingWithFields fileMappingWithFields = (FileMappingWithFields) fileMapping;
            final int order = fileMappingWithFields.getFields().size() + 1;
            fieldMapping.setOrder(order);
            return fieldMappingDao.insert(fieldMapping);
        } else {
            return fieldMappingDao.update(fieldMapping);
        }
    }

    public void setFetchDao(final FetchDAO fetchDao) {
        this.fetchDao = fetchDao;
    }

    public void setFieldMappingDao(final FieldMappingDAO fieldMappingDao) {
        this.fieldMappingDao = fieldMappingDao;
    }

    public void setFileMappingServiceLocal(final FileMappingServiceLocal fileMappingService) {
        this.fileMappingService = fileMappingService;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @Override
    public void setOrder(final Long[] fieldMappingsIds) {
        int index = 0;
        for (final Long fieldMappingId : fieldMappingsIds) {
            final FieldMapping fieldMapping = load(fieldMappingId);
            fieldMapping.setOrder(++index);
            fieldMappingDao.update(fieldMapping);
        }
    }

    @Override
    public void validate(final FieldMapping fieldMapping) {
        getValidator().validate(fieldMapping);
    }

    private Validator getValidator() {
        final Validator validator = new Validator("fieldMapping");
        validator.property("fileMapping").required();
        validator.property("name").required();
        validator.property("field").required();
        validator.general(new OrderValidation(), new MemberFieldValidation());
        return validator;
    }

}
