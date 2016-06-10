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
package nl.strohalm.cyclos.controls.reports.members.list;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.controls.reports.members.list.MembersListReportDTO.PeriodType;

import org.apache.struts.action.ActionMapping;

public class MembersListReportForm extends BaseBindingForm {

    private static final long serialVersionUID = -5447300477471532616L;

    public MembersListReportForm() {
        setMembersListReport("groups", Collections.emptyList());
    }

    public Map<String, Object> getMembersListReport() {
        return values;
    }

    public Object getMembersListReport(final String key) {
        return values.get(key);
    }

    @Override
    public void reset(final ActionMapping mapping, final HttpServletRequest request) {
        super.reset(mapping, request);
        values.put("periodType", PeriodType.PERIOD_CURRENT);
    }

    public void setMembersListReport(final Map<String, Object> membersReport) {
        values = membersReport;
    }

    public void setMembersListReport(final String key, final Object value) {
        values.put(key, value);
    }

}
