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
package nl.strohalm.cyclos.webservices.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Base class for web services entity VOs which holds custom fields
 * @author luis
 */
public abstract class EntityWithCustomFieldsVO extends EntityVO {

    private static final long   serialVersionUID = 1L;
    @XmlTransient
    private Map<String, String> fieldsMap;
    private List<FieldValueVO>  fields;

    @JsonProperty("customValues")
    public List<FieldValueVO> getFields() {
        return fields;
    }

    @JsonIgnore
    public Map<String, String> getFieldsMap() {
        if (fieldsMap == null) {
            if (fields != null) {
                fieldsMap = new HashMap<String, String>();
                for (final FieldValueVO vo : fields) {
                    fieldsMap.put(vo.getInternalName(), vo.getValue());
                }
            } else {
                fieldsMap = Collections.emptyMap();
            }
        }
        return fieldsMap;
    }

    public void setFields(final List<FieldValueVO> fields) {
        this.fields = fields;
        fieldsMap = null;
    }

}
