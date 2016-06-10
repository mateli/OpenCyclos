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
 * Form used to link an existing custom field to another payment type
 * 
 * @author luis
 */
public class LinkPaymentCustomFieldForm extends ActionForm {
    private static final long serialVersionUID = -8016588492822515527L;
    private long              accountTypeId;
    private long              transferTypeId;
    private long              customFieldId;

    public long getAccountTypeId() {
        return accountTypeId;
    }

    public long getCustomFieldId() {
        return customFieldId;
    }

    public long getTransferTypeId() {
        return transferTypeId;
    }

    public void setAccountTypeId(final long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public void setCustomFieldId(final long customFieldId) {
        this.customFieldId = customFieldId;
    }

    public void setTransferTypeId(final long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }
}
