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
package nl.strohalm.cyclos.controls.accounts.transactionfees;

import org.apache.struts.action.ActionForm;

/**
 * Form used to list generated transfer types for a transaction fee
 * @author Jefferson Magno
 */
public class ListGeneratedTypesAjaxForm extends ActionForm {

    private static final long serialVersionUID = -2471894870191458777L;

    private long              transferTypeId;
    private String            nature;
    private String            payer;
    private String            receiver;
    private String            whichBroker;
    private boolean           allowAnyAccount;

    public String getNature() {
        return nature;
    }

    public String getPayer() {
        return payer;
    }

    public String getReceiver() {
        return receiver;
    }

    public long getTransferTypeId() {
        return transferTypeId;
    }

    public String getWhichBroker() {
        return whichBroker;
    }

    public boolean isAllowAnyAccount() {
        return allowAnyAccount;
    }

    public void setAllowAnyAccount(final boolean allowAnyAccount) {
        this.allowAnyAccount = allowAnyAccount;
    }

    public void setNature(final String nature) {
        this.nature = nature;
    }

    public void setPayer(final String payer) {
        this.payer = payer;
    }

    public void setReceiver(final String receiver) {
        this.receiver = receiver;
    }

    public void setTransferTypeId(final long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public void setWhichBroker(final String whichBroker) {
        this.whichBroker = whichBroker;
    }

}
