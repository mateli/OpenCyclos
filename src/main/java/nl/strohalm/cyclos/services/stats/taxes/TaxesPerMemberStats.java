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
package nl.strohalm.cyclos.services.stats.taxes;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.accounts.transactions.TransferDAO;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.reports.StatisticalDTO;
import nl.strohalm.cyclos.entities.reports.StatisticalTaxesQuery;
import nl.strohalm.cyclos.utils.Pair;
import nl.strohalm.cyclos.utils.Period;

/**
 * Class with common helper functions on retrieving statistics about the (sum of) payments per member, for taxes stats. Currently only filled with
 * mock data, as it was still under construction and not finished.
 * @author Rinke
 */
public class TaxesPerMemberStats extends TransferVolumePerMemberStats {

    private final Period                      period;
    private final Collection<? extends Group> groups;
    private final TransferDAO                 transferDao;
    private final PaymentFilter               paymentFilter;

    public TaxesPerMemberStats(final StatisticalTaxesQuery queryParameters, final Period period, final TransferDAO transferDao) {
        this.transferDao = transferDao;
        this.period = period;
        groups = queryParameters.getGroups();
        paymentFilter = queryParameters.getPaymentFilter();
    }

    public Number[] getNotPaidSumsPerAccountFee() {
        final Number[] result = new Number[20];
        Arrays.fill(result, 2.23);
        return result;
    }

    public Integer[] getNumberOfCharges() {
        final Integer[] result = new Integer[20];
        Arrays.fill(result, 2);
        return result;
    }

    public Integer[] getNumberOfTransactions() {
        final Integer[] result = new Integer[20];
        Arrays.fill(result, 20935097);
        return result;
    }

    @Override
    public List<Pair<Member, BigDecimal>> getSumOfTransfersPerTrader() {
        final StatisticalDTO dto = new StatisticalDTO(period, paymentFilter, groups);
        return transferDao.getPaymentsPerMember(dto);
    }

    public Number[] getSumsPerFee() {
        final Number[] result = new Number[20];
        Arrays.fill(result, 223907.32);
        return result;
    }

}
