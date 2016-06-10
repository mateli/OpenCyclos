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
package nl.strohalm.cyclos.controls.customization.fields;

import java.util.Collections;
import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to edit a custom field
 * @author luis
 */
public class EditCustomFieldForm extends BaseBindingForm {

    private static final long serialVersionUID = -8084173806334074967L;
    private long              fieldId;
    private long              parentValueId;
    private String            nature;

    public EditCustomFieldForm() {
        final MapBean validation = new MapBean("required", "unique");
        validation.set("lengthConstraint", new MapBean("min", "max"));
        setField("validation", validation);
        setField("groups", Collections.emptyList());
    }

    public Map<String, Object> getField() {
        return values;
    }

    public Object getField(final String name) {
        return values.get(name);
    }

    public long getFieldId() {
        return fieldId;
    }

    public long getMemberRecordTypeId() {
        try {
            return (Long) getField("memberRecordType");
        } catch (final Exception e) {
            return 0;
        }
    }

    public String getNature() {
        return nature;
    }

    public long getParentValueId() {
        return parentValueId;
    }

    public long getTransferTypeId() {
        try {
            return (Long) getField("transferType");
        } catch (final Exception e) {
            return 0;
        }
    }

    public void setField(final Map<String, Object> field) {
        values = field;
    }

    public void setField(final String name, final Object value) {
        values.put(name, value);
    }

    public void setFieldId(final long fieldId) {
        this.fieldId = fieldId;
    }

    public void setMemberRecordTypeId(final long memberRecordTypeId) {
        setField("memberRecordType", memberRecordTypeId);
    }

    public void setNature(final String nature) {
        this.nature = nature;
    }

    public void setParentValueId(final long parentValueId) {
        this.parentValueId = parentValueId;
    }

    public void setTransferTypeId(final long transferTypeId) {
        setField("transferType", transferTypeId);
    }
}
