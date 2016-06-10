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
package nl.strohalm.cyclos.controls.accounts.external.filemappings;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * Form used to edit a field mapping
 * @author jefferson
 */
public class EditFieldMappingForm extends BaseBindingForm {

    private static final long serialVersionUID = 3701447529857499076L;
    private long              fileMappingId;
    private long              fieldMappingId;

    public Map<String, Object> getFieldMapping() {
        return values;
    }

    public Object getFieldMapping(final String key) {
        return values.get(key);
    }

    public long getFieldMappingId() {
        return fieldMappingId;
    }

    public long getFileMappingId() {
        return fileMappingId;
    }

    public void setFieldMapping(final Map<String, Object> map) {
        values = map;
    }

    public void setFieldMapping(final String key, final Object value) {
        values.put(key, value);
    }

    public void setFieldMappingId(final long fieldMappingId) {
        this.fieldMappingId = fieldMappingId;
    }

    public void setFileMappingId(final long fileMappingId) {
        this.fileMappingId = fileMappingId;
    }

}
