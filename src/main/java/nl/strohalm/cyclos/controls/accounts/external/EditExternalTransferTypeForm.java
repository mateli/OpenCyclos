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
package nl.strohalm.cyclos.controls.accounts.external;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * Form used to edit a External Transfer Type
 * @author Lucas Geiss
 */
public class EditExternalTransferTypeForm extends BaseBindingForm {
    private static final long serialVersionUID = 4958639810768991285L;
    private long              externalTransferTypeId;
    private long              account;

    public long getAccount() {
        return account;
    }

    public Map<String, Object> getExternalTransferType() {
        return values;
    }

    public Object getExternalTransferType(final String key) {
        return values.get(key);
    }

    public long getExternalTransferTypeId() {
        return externalTransferTypeId;
    }

    public void setAccount(final long account) {
        this.account = account;
    }

    public void setExternalTransferType(final Map<String, Object> map) {
        values = map;
    }

    public void setExternalTransferType(final String key, final Object value) {
        values.put(key, value);
    }

    public void setExternalTransferTypeId(final long transferTypeId) {
        externalTransferTypeId = transferTypeId;
    }

}
