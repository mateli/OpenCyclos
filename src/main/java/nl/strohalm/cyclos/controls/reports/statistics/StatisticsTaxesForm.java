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

import nl.strohalm.cyclos.services.stats.StatisticalTaxesService;

import org.apache.struts.action.ActionMapping;

/**
 * form used for showing statistics
 * 
 * @author rinke
 * 
 */
public class StatisticsTaxesForm extends StatisticsForm {

    private static final long serialVersionUID = -6908130237946673870L;

    public StatisticsTaxesForm() {
        super();
        this.setQuery("accountFees", Collections.emptyList());
        this.setQuery("transactionFees", Collections.emptyList());
    }

    // Still to be done:
    // Form verification moet nog
    // accountTypefilter content afh v group selection via ajax
    // hide transaction filter als alleen not paid gesel. (en maak leeg)
    // limit for not paid field onzichtbaar als alleen paid
    // als alleen transaction fees: hide hele paid/notpaid gedoe.
    // pas bij uitwerken thru time: extra optie "per charge"

    @Override
    public void reset(final ActionMapping mapping, final HttpServletRequest request) {
        super.reset(mapping, request);
        this.setQuery("volume", false);
        this.setQuery("numberOfMembers", false);
        this.setQuery("relativeToGrossProduct", false);
        this.setQuery("medianPerMember", false);
        this.setQuery("maxMember", false);
        this.setQuery("paidOrNot", StatisticalTaxesService.PaidOrNot.PAID);
        this.setQuery("notPaidLimit", 90);
        this.setQuery("accountFees", Collections.emptyList());
        this.setQuery("transactionFees", Collections.emptyList());
    }

}
