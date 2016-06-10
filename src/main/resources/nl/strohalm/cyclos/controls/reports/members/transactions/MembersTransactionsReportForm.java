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
package nl.strohalm.cyclos.controls.reports.members.transactions;

import java.util.Collections;
import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

public class MembersTransactionsReportForm extends BaseBindingForm {

    private static final long serialVersionUID = 7838138941605556886L;

    public MembersTransactionsReportForm() {
        setMembersTransactionsReport("period", new MapBean("begin", "end"));
        setMembersTransactionsReport("accountTypes", Collections.emptyList());
        setMembersTransactionsReport("memberGroups", Collections.emptyList());
        setMembersTransactionsReport("transactionsPaymentFilters", Collections.emptyList());
        setMembersTransactionsReport("detailsLevel", MembersTransactionsReportDTO.DetailsLevel.SUMMARY.name());
    }

    public Map<String, Object> getMembersTransactionsReport() {
        return values;
    }

    public Object getMembersTransactionsReport(final String key) {
        return values.get(key);
    }

    public void setMembersTransactionsReport(final Map<String, Object> membersTransactionsReport) {
        values = membersTransactionsReport;
    }

    public void setMembersTransactionsReport(final String key, final Object value) {
        values.put(key, value);
    }

}
