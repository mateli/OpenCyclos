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
package nl.strohalm.cyclos.entities.accounts.fees.account;

import java.util.Calendar;
import java.util.Collection;

import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Parameters for an account fee query
 * @author luis
 */
public class AccountFeeQuery extends QueryParameters {

    private static final long       serialVersionUID = -857557436214976940L;
    private Collection<MemberGroup> groups;
    private AccountType             accountType;
    private Byte                    day;
    private Byte                    hour;
    private TimePeriod.Field        recurrence;
    private boolean                 returnDisabled;
    private AccountFee.RunMode      type;
    private Calendar                enabledBefore;

    public AccountType getAccountType() {
        return accountType;
    }

    public Byte getDay() {
        return day;
    }

    public Calendar getEnabledBefore() {
        return enabledBefore;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public Byte getHour() {
        return hour;
    }

    public TimePeriod.Field getRecurrence() {
        return recurrence;
    }

    public AccountFee.RunMode getType() {
        return type;
    }

    /**
     * checks if the account fee is disabled
     * 
     * @return true if the account fee is disabled.
     */
    public boolean isReturnDisabled() {
        return returnDisabled;
    }

    public void setAccountType(final AccountType accountType) {
        this.accountType = accountType;
    }

    public void setDay(final Byte day) {
        this.day = day;
    }

    public void setEnabledBefore(final Calendar enabledBefore) {
        this.enabledBefore = enabledBefore;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setHour(final Byte hour) {
        this.hour = hour;
    }

    /**
     * setting this will make the query search for fees matching the given recurrence
     * 
     * @param recurrence a <code>TimePeriod.field</code> indicating how often the fee is to be repeated in time.
     */
    public void setRecurrence(final TimePeriod.Field recurrence) {
        this.recurrence = recurrence;
    }

    /**
     * sets the query to search for enabled/disabled fees
     * 
     * @param returnDisabled if true, search will be for disabled fees
     */
    public void setReturnDisabled(final boolean returnDisabled) {
        this.returnDisabled = returnDisabled;
    }

    public void setType(final AccountFee.RunMode type) {
        this.type = type;
    }

}
