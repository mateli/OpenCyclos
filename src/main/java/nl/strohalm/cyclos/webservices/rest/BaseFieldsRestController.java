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
package nl.strohalm.cyclos.webservices.rest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.webservices.model.FieldValueVO;

/**
 * Base class for custom fields controllers
 * 
 * @author luis
 */
public abstract class BaseFieldsRestController<CF extends CustomField> extends BaseRestController {

    protected CustomFieldHelper customFieldHelper;

    public List<FieldValueVO> requestParametersToFieldValues(final HttpServletRequest request, final String prefix) {

        List<FieldValueVO> fieldValues = new ArrayList<FieldValueVO>();
        for (@SuppressWarnings("unchecked")
        Enumeration<String> names = request.getParameterNames(); names.hasMoreElements();) {
            String name = names.nextElement();
            if (name.startsWith(prefix)) {
                fieldValues.add(new FieldValueVO(name.substring(prefix.length()), request.getParameter(name)));
            }
        }
        return fieldValues;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }
}
