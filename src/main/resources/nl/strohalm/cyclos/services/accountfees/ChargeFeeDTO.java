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
package nl.strohalm.cyclos.services.accountfees;

import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.Period;

/**
 * Parameters used to charge an account fee
 * @author luis
 */
public class ChargeFeeDTO extends DataObject {

    private static final long serialVersionUID = 4545707011129986802L;

    private AccountFee        fee;
    private AccountFeeLog     feeLog;
    private Period            period;

    public AccountFee getFee() {
        return fee;
    }

    public AccountFeeLog getFeeLog() {
        return feeLog;
    }

    public Period getPeriod() {
        return period;
    }

    public void setFee(final AccountFee fee) {
        this.fee = fee;
    }

    public void setFeeLog(final AccountFeeLog feeLog) {
        this.feeLog = feeLog;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }
}
