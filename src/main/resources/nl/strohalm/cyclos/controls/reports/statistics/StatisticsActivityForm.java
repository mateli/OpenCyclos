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
package nl.strohalm.cyclos.controls.reports.statistics;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.entities.reports.StatisticsWhatToShow;
import nl.strohalm.cyclos.entities.reports.ThroughTimeRange;

import org.apache.struts.action.ActionMapping;

/**
 * form used for showing statistics
 * @author rinke
 * 
 */
public class StatisticsActivityForm extends StatisticsForm {

    private static final long serialVersionUID = -5819021670000637378L;

    public StatisticsActivityForm() {
        super();
    }

    @Override
    public void reset(final ActionMapping mapping, final HttpServletRequest request) {
        super.reset(mapping, request);
        this.setQuery("grossProduct", false);
        this.setQuery("grossProductGraph", false);
        this.setQuery("grossProductTopten", false);
        this.setQuery("numberTransactions", false);
        this.setQuery("numberTransactionsGraph", false);
        this.setQuery("numberTransactionsTopten", false);
        this.setQuery("percentageNoTrade", false);
        this.setQuery("percentageNoTradeGraph", false);
        this.setQuery("loginTimes", false);
        this.setQuery("loginTimesGraph", false);
        this.setQuery("loginTimesTopten", false);
        this.setQuery("whatToShow", StatisticsWhatToShow.COMPARE_PERIODS);
        this.setQuery("throughTimeRange", ThroughTimeRange.MONTH);
        this.setQuery("groups", Collections.emptyList());
    }

}
