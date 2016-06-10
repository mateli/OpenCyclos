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

import org.apache.struts.action.ActionForm;

/**
 * Form used to remove a custom field
 * @author luis
 */
public class RemoveCustomFieldForm extends ActionForm {

    private static final long serialVersionUID = 3192437249501465121L;
    private String            nature;
    private long              fieldId;
    private long              memberRecordTypeId;
    private long              transferTypeId;

    public long getFieldId() {
        return fieldId;
    }

    public long getMemberRecordTypeId() {
        return memberRecordTypeId;
    }

    public String getNature() {
        return nature;
    }

    public long getTransferTypeId() {
        return transferTypeId;
    }

    public void setFieldId(final long fieldId) {
        this.fieldId = fieldId;
    }

    public void setMemberRecordTypeId(final long memberRecordTypeId) {
        this.memberRecordTypeId = memberRecordTypeId;
    }

    public void setNature(final String nature) {
        this.nature = nature;
    }

    public void setTransferTypeId(final long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

}
