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
package nl.strohalm.cyclos.controls.accounts.external.externaltransfers;

import org.apache.struts.action.ActionForm;

/**
 * Form used to change the status of external transfers
 * @author Jefferson Magno
 */
public class ChangeExternalTransferForm extends ActionForm {

    private static final long serialVersionUID = -3059103570368807370L;
    private long              transferImportId;
    private long              externalAccountId;
    private Long[]            externalTransferId;
    private String            action;

    public String getAction() {
        return action;
    }

    public long getExternalAccountId() {
        return externalAccountId;
    }

    public Long[] getExternalTransferId() {
        return externalTransferId;
    }

    public long getTransferImportId() {
        return transferImportId;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public void setExternalAccountId(final long externalAccountId) {
        this.externalAccountId = externalAccountId;
    }

    public void setExternalTransferId(final Long[] externalTransferId) {
        this.externalTransferId = externalTransferId;
    }

    public void setTransferImportId(final long transferImportId) {
        this.transferImportId = transferImportId;
    }

}
