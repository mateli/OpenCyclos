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
package nl.strohalm.cyclos.services.stats.activity;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.access.LoginHistoryDAO;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.reports.StatisticalActivityQuery;
import nl.strohalm.cyclos.entities.reports.StatisticalDTO;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.statistics.ListOperations;

public class LoginTimesPerMemberStats {

    private final LoginHistoryDAO             loginHistoryDao;
    private final Period                      period;
    private final Collection<? extends Group> groups;

    public LoginTimesPerMemberStats(final StatisticalActivityQuery queryParameters, final Period period, final LoginHistoryDAO loginHistoryDao) {
        this.loginHistoryDao = loginHistoryDao;
        this.period = period;
        groups = queryParameters.getGroups();
    }

    /**
     * gets the logintimes per member as a list. Each element represents a member, and its value is the number of logins of that member
     * @return a List with loginstimes per member
     */
    public List<Number> getListLoginTimes() {
        return ListOperations.getSecondFromPairCollection(getLoginTimesPerMember());
    }

    /**
     * gets the logintimes per member as an array. Each element represents a member, and its value is the number of logins of that member
     * @return an array of ints with loginstimes per member
     */
    public int[] getLoginTimes() {
        final List<Number> listLoginTimes = getListLoginTimes();
        return ListOperations.listToIntArray(listLoginTimes);
    }

    /**
     * gets a list with <code>Pair</code>s, where the first element of each pair is the member id, and the second element is the number of times that
     * member logged in.
     * @return a list with member id's and login times.
     */
    public List<Pair<User, Number>> getLoginTimesPerMember() {
        final StatisticalDTO dto = new StatisticalDTO(period, null, groups);
        return loginHistoryDao.list(dto);
    }

    /**
     * gets a list with <code>Pair</code>s, where the first element of each pair is the member id, and the second element is the number of times that
     * member logged in. Same as <code>getLoginTimesPerMember</code> except that this list has the zero results skipped, as needed by the top ten.
     * @return a list with member id's and login times.
     */
    public List<Pair<User, Number>> getLoginTimesPerMemberWithoutZeros() {
        final List<Pair<User, Number>> result = getLoginTimesPerMember();
        final List<Number> loginTimes = getListLoginTimes();
        final int index = loginTimes.indexOf(new Integer(0));
        return result.subList(0, index);
    }

}
