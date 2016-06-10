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

import java.io.Serializable;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

public class TransferDataVO implements Serializable {

    private static final long        serialVersionUID             = 1L;

    private AccountHistoryTransferVO accountHistoryTransfer;
    private Boolean                  canAddRelatedMemberAsContact = false;

    public TransferDataVO(final AccountHistoryTransferVO accountHistoryTransferVO, final boolean canAddRelatedMemberAsContact) {
        super();
        accountHistoryTransfer = accountHistoryTransferVO;
        this.canAddRelatedMemberAsContact = canAddRelatedMemberAsContact;
    }

    public AccountHistoryTransferVO getAccountHistoryTransfer() {
        return accountHistoryTransfer;
    }

    public boolean getCanAddRelatedMemberAsContact() {
        return ObjectHelper.valueOf(canAddRelatedMemberAsContact);
    }

    @Override
    public String toString() {
        return "TransferDataVO [accountHistoryTransfer=" + accountHistoryTransfer + ", canAddRelatedMemberAsContact=" + canAddRelatedMemberAsContact + "]";
    }

}
