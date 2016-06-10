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
package nl.strohalm.cyclos.services.accounts;

import java.math.BigDecimal;

import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.TimePeriod;

/**
 * Contain parameters for a transaction volume calculation
 */
public class TransactionVolumeDTO extends DataObject {

    private static final long serialVersionUID = -5420305636336659922L;
    private AccountOwner      accountOwner;
    private MemberAccountType accountType;
    private Period            period;
    private BigDecimal        freeBase;
    private TimePeriod        tolerance;
    private boolean           positiveVolume;

    public AccountOwner getAccountOwner() {
        return accountOwner;
    }

    public MemberAccountType getAccountType() {
        return accountType;
    }

    public BigDecimal getFreeBase() {
        return freeBase;
    }

    public Period getPeriod() {
        return period;
    }

    public TimePeriod getTolerance() {
        return tolerance;
    }

    public boolean isPositiveVolume() {
        return positiveVolume;
    }

    public void setAccountOwner(final AccountOwner accountOwner) {
        this.accountOwner = accountOwner;
    }

    public void setAccountType(final MemberAccountType accountType) {
        this.accountType = accountType;
    }

    public void setFreeBase(final BigDecimal freeBase) {
        this.freeBase = freeBase;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setPositiveVolume(final boolean positiveVolume) {
        this.positiveVolume = positiveVolume;
    }

    public void setTolerance(final TimePeriod tolerance) {
        this.tolerance = tolerance;
    }

}
