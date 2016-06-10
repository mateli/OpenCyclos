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

import java.util.List;

import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.members.imports.ImportedMemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecord;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation for {@link MemberRecordCustomFieldServiceLocal}
 * 
 * @author luis
 */
public class MemberRecordCustomFieldServiceImpl extends BaseCustomFieldServiceImpl<MemberRecordCustomField> implements MemberRecordCustomFieldServiceLocal {

    protected MemberRecordCustomFieldServiceImpl() {
        super(MemberRecordCustomField.class);
    }

    @Override
    public Validator getValueValidator(final MemberRecordType recordType) {
        return getValueValidator(list(recordType));
    }

    @Override
    public List<MemberRecordCustomField> list(final MemberRecordType recordType) {
        return getCache().get("_FIELDS_BY_TYPE_" + recordType.getId(), new CacheCallback() {
            @Override
            public Object retrieve() {
                return customFieldDao.listMemberRecordFields(recordType);
            }
        });
    }

    @Override
    public void saveValues(final ImportedMemberRecord record) {
        getValueValidator(list(record.getType())).validate(record);
        doSaveValues(record);
    }

    @Override
    public void saveValues(final MemberRecord record) {
        getValueValidator(list(record.getType())).validate(record);
        doSaveValues(record);
    }

    @Override
    protected List<MemberRecordCustomField> list(final MemberRecordCustomField field) {
        return list(field.getMemberRecordType());
    }
}
