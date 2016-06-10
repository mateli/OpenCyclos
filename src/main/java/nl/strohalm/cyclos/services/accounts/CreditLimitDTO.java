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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.utils.DataObject;

import org.apache.commons.beanutils.BeanComparator;

/**
 * Parameters for returning or setting credit limits
 * @author luis
 */
public class CreditLimitDTO extends DataObject {

    /**
     * A credit limit entry, containing the account type and both credit limits
     * @author luis
     */
    public static class Entry {
        private final AccountType accountType;
        private final BigDecimal  creditLimit;
        private final BigDecimal  upperCreditLimit;

        public Entry(final AccountType accountType, final BigDecimal creditLimit, final BigDecimal upperCreditLimit) {
            this.accountType = accountType;
            this.creditLimit = creditLimit;
            this.upperCreditLimit = upperCreditLimit;
        }

        public AccountType getAccountType() {
            return accountType;
        }

        public BigDecimal getCreditLimit() {
            return creditLimit;
        }

        public BigDecimal getUpperCreditLimit() {
            return upperCreditLimit;
        }
    }

    private static final long                      serialVersionUID = 1285604922318613946L;

    private Map<? extends AccountType, BigDecimal> limitPerType;
    private Map<? extends AccountType, BigDecimal> upperLimitPerType;

    /**
     * Build a list of entries containing account types, negative and positive limits
     */
    @SuppressWarnings("unchecked")
    public List<Entry> getEntries() {
        final List<Entry> list = new ArrayList<Entry>();
        final SortedSet<AccountType> accountTypes = new TreeSet<AccountType>(new BeanComparator("name"));
        if (limitPerType != null) {
            for (final AccountType type : limitPerType.keySet()) {
                accountTypes.add(type);
            }
        }
        if (upperLimitPerType != null) {
            for (final AccountType type : upperLimitPerType.keySet()) {
                accountTypes.add(type);
            }
        }
        for (final AccountType type : accountTypes) {
            final BigDecimal limit = limitPerType == null ? null : limitPerType.get(type);
            final BigDecimal upperLimit = upperLimitPerType == null ? null : upperLimitPerType.get(type);
            list.add(new Entry(type, limit, upperLimit));
        }
        return list;
    }

    public Map<? extends AccountType, BigDecimal> getLimitPerType() {
        return limitPerType;
    }

    public Map<? extends AccountType, BigDecimal> getUpperLimitPerType() {
        return upperLimitPerType;
    }

    public void setLimitPerType(final Map<? extends AccountType, BigDecimal> limitPerType) {
        this.limitPerType = limitPerType;
    }

    public void setUpperLimitPerType(final Map<? extends AccountType, BigDecimal> upperLimitPerType) {
        this.upperLimitPerType = upperLimitPerType;
    }

}
