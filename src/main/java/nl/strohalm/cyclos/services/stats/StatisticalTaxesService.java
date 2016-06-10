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
package nl.strohalm.cyclos.services.stats;

import nl.strohalm.cyclos.entities.reports.StatisticalTaxesQuery;
import nl.strohalm.cyclos.utils.StringValuedEnum;

/**
 * Service interface for Taxes Statistics. It shows the in- and outgoing finances for system accounts.
 * 
 * @author rinke
 */
public interface StatisticalTaxesService extends StatisticalService {

    /**
     * Allows the user to choose between statistics about taxes paid, taxes not paid, or both.
     * @author Rinke
     * 
     */
    public static enum PaidOrNot implements StringValuedEnum {
        PAID("paid"), NOT_PAID("notPaid"), BOTH("both");

        private final String value;

        private PaidOrNot(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    StatisticalResultDTO getComparePeriodsMaxMember(StatisticalTaxesQuery query);

    StatisticalResultDTO getComparePeriodsMedianPerMember(StatisticalTaxesQuery query);

    StatisticalResultDTO getComparePeriodsNumberOfMembers(StatisticalTaxesQuery query);

    StatisticalResultDTO getComparePeriodsRelativeToGrossProduct(StatisticalTaxesQuery query);

    StatisticalResultDTO getComparePeriodsVolume(StatisticalTaxesQuery query);

    StatisticalResultDTO getDistributionMedianPerMember(StatisticalTaxesQuery query);

    StatisticalResultDTO getDistributionRelativeToGrossProduct(StatisticalTaxesQuery query);

    StatisticalResultDTO getSinglePeriodMaxMember(StatisticalTaxesQuery query);

    StatisticalResultDTO getSinglePeriodMedianPerMember(StatisticalTaxesQuery query);

    StatisticalResultDTO getSinglePeriodNumberOfMembers(StatisticalTaxesQuery query);

    StatisticalResultDTO getSinglePeriodRelativeToGrossProduct(StatisticalTaxesQuery query);

    StatisticalResultDTO getSinglePeriodVolume(StatisticalTaxesQuery query);

    StatisticalResultDTO getThroughTimeMaxMember(StatisticalTaxesQuery query);

    StatisticalResultDTO getThroughTimeMedianPerMember(StatisticalTaxesQuery query);

    StatisticalResultDTO getThroughTimeNumberOfMembers(StatisticalTaxesQuery query);

    StatisticalResultDTO getThroughTimeRelativeToGrossProduct(StatisticalTaxesQuery query);

    StatisticalResultDTO getThroughTimeVolume(StatisticalTaxesQuery query);

}
