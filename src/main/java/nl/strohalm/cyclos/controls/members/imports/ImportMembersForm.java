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
package nl.strohalm.cyclos.controls.members.imports;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

import org.apache.struts.upload.FormFile;

/**
 * Form used to import members
 * @author luis
 */
public class ImportMembersForm extends BaseBindingForm {

    private static final long serialVersionUID = 6929243872691745528L;
    private FormFile          upload;

    public Map<String, Object> getImport() {
        return values;
    }

    public Object getImport(final String property) {
        return values.get(property);
    }

    public FormFile getUpload() {
        return upload;
    }

    public void setImport(final Map<String, Object> values) {
        this.values = values;
    }

    public void setImport(final String property, final Object value) {
        values.put(property, value);
    }

    public void setUpload(final FormFile upload) {
        this.upload = upload;
    }

}
