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
package nl.strohalm.cyclos.dao.accounts;

import java.sql.SQLException;
import java.util.Calendar;

import nl.strohalm.cyclos.dao.BaseDAOImpl;
import nl.strohalm.cyclos.dao.JDBCCallback;
import nl.strohalm.cyclos.entities.accounts.AccountLimitLog;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.utils.JDBCWrapper;
import nl.strohalm.cyclos.utils.access.LoggedUser;

/**
 * Implementation for {@link AccountLimitLogDAO}
 * 
 * @author luis
 */
public class AccountLimitLogDAOImpl extends BaseDAOImpl<AccountLimitLog> implements AccountLimitLogDAO {

    public AccountLimitLogDAOImpl() {
        super(AccountLimitLog.class);
    }

    @Override
    public void insertAfterCreditLimitBulkUpdate(final MemberAccountType accountType, final MemberGroup group) {
        runNative(new JDBCCallback() {
            @Override
            public void execute(final JDBCWrapper jdbc) throws SQLException {
                final StringBuilder sql = new StringBuilder();
                sql.append("insert into account_limit_logs ");
                sql.append("(account_id, date, by_id, credit_limit, upper_credit_limit) ");
                sql.append(" select ");
                sql.append(" a.id, ?, ?, a.credit_limit, a.upper_credit_limit ");
                sql.append(" from accounts a, members m ");
                sql.append(" where a.member_id = m.id ");
                sql.append(" and m.group_id = ? and a.type_id = ? ");
                Calendar date = Calendar.getInstance();
                Long byId = ((Administrator) LoggedUser.element()).getId();
                Long groupId = group.getId();
                Long typeId = accountType.getId();
                jdbc.execute(sql.toString(), date, byId, groupId, typeId);
            }
        });
    }

}
