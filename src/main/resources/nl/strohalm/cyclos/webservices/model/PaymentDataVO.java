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

import java.util.List;
import java.util.Map;

/**
 * Represents the information needed to prepare a payment
 * @author jcomas
 */

public class PaymentDataVO extends TransferTypeVO {
    private static final long    serialVersionUID = 1L;
    List<DetailedTransferTypeVO> transferTypes;
    Map<Long, AccountStatusVO>   accountsStatus;
    MemberVO                     toMember;

    public Map<Long, AccountStatusVO> getAccountsStatus() {
        return accountsStatus;
    }

    public MemberVO getToMember() {
        return toMember;
    }

    public List<DetailedTransferTypeVO> getTransferTypes() {
        return transferTypes;
    }

    public void setAccountsStatus(final Map<Long, AccountStatusVO> accountsStatus) {
        this.accountsStatus = accountsStatus;
    }

    public void setToMember(final MemberVO toMember) {
        this.toMember = toMember;
    }

    public void setTransferTypes(final List<DetailedTransferTypeVO> transferTypes) {
        this.transferTypes = transferTypes;
    }

    @Override
    public String toString() {
        return "PaymentDataVO [transferTypes=" + transferTypes + ", accountsStatus=" + accountsStatus + ", toMember=" + toMember + "]";
    }

}
