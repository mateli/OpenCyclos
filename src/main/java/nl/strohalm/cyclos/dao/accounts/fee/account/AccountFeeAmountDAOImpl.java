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
package nl.strohalm.cyclos.dao.accounts.fee.account;

import static nl.strohalm.cyclos.utils.BigDecimalHelper.nvl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeAmount;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

/**
 * Implementation for {@link AccountFeeAmountDAO}
 * @author luis
 */
public class AccountFeeAmountDAOImpl extends BaseDAOImpl<AccountFeeAmount> implements AccountFeeAmountDAO {

    public AccountFeeAmountDAOImpl() {
        super(AccountFeeAmount.class);
    }

    @Override
    public void deleteOnPeriod(final MemberAccount account, final AccountFeeLog log) {
        final Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("account", account);
        namedParameters.put("log", log);
        final String hql = "delete from AccountFeeAmount c where c.account = :account and c.accountFeeLog = :log";
        bulkUpdate(hql, namedParameters);
    }

    @Override
    public AccountFeeAmount forData(final MemberAccount account, final AccountFeeLog accountFeeLog, final Calendar date) throws EntityNotFoundException {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", account);
        params.put("accountFeeLog", accountFeeLog);
        params.put("date", date);
        final AccountFeeAmount charge = uniqueResult("from AccountFeeAmount c where c.account = :account and c.accountFeeLog = :accountFeeLog and c.period.end = :date", params);
        if (charge == null) {
            throw new EntityNotFoundException(getEntityType());
        }
        return charge;
    }

    @Override
    public BigDecimal totalAmoutForPeriod(final MemberAccount account, final AccountFeeLog accountFeeLog) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", account);
        params.put("accountFeeLog", accountFeeLog);
        final BigDecimal amount = uniqueResult("select sum(c.amount) from AccountFeeAmount c where c.account = :account and c.accountFeeLog = :accountFeeLog", params);
        return nvl(amount);
    }

}
