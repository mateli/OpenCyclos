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
package nl.strohalm.cyclos.entities.sms;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Contains data for the sms log report
 * 
 * @author luis
 */
public class SmsLogReportVO {

    private Map<SmsLogType, Map<SmsLogStatus, Integer>> totals;
    private List<SmsLog>                                logs;

    public List<SmsLog> getLogs() {
        return logs;
    }

    public int getTotal() {
        if (totals == null) {
            return 0;
        }
        int total = 0;
        for (final Map<SmsLogStatus, Integer> map : totals.values()) {
            for (final Integer value : map.values()) {
                total += value;
            }
        }
        return total;
    }

    public Map<SmsLogType, Map<SmsLogStatus, Integer>> getTotals() {
        return totals;
    }

    public Map<SmsLogStatus, Integer> getTotalsByStatus() {
        if (totals == null) {
            return null;
        }
        final Map<SmsLogStatus, Integer> result = new EnumMap<SmsLogStatus, Integer>(SmsLogStatus.class);
        for (final Map.Entry<SmsLogType, Map<SmsLogStatus, Integer>> outerEntry : totals.entrySet()) {
            for (final Map.Entry<SmsLogStatus, Integer> entry : outerEntry.getValue().entrySet()) {
                final SmsLogStatus status = entry.getKey();
                final Integer current = result.get(status);
                final int value = current == null ? entry.getValue() : current + entry.getValue();
                result.put(status, value);
            }
        }
        return result;
    }

    public Map<SmsLogType, Integer> getTotalsByType() {
        if (totals == null) {
            return null;
        }
        final Map<SmsLogType, Integer> result = new EnumMap<SmsLogType, Integer>(SmsLogType.class);
        for (final Map.Entry<SmsLogType, Map<SmsLogStatus, Integer>> entry : totals.entrySet()) {
            int total = 0;
            for (final Integer value : entry.getValue().values()) {
                total += value;
            }
            result.put(entry.getKey(), total);
        }
        return result;
    }

    public void setLogs(final List<SmsLog> logs) {
        this.logs = logs;
    }

    public void setTotals(final SmsLogType type, final SmsLogStatus status, final int total) {
        if (totals == null) {
            initTotals();
        }
        totals.get(type).put(status, total);
    }

    private void initTotals() {
        totals = new EnumMap<SmsLogType, Map<SmsLogStatus, Integer>>(SmsLogType.class);
        // Fill the maps with zeros
        for (final SmsLogType type : SmsLogType.values()) {
            final Map<SmsLogStatus, Integer> byStatus = new EnumMap<SmsLogStatus, Integer>(SmsLogStatus.class);
            totals.put(type, byStatus);
            for (final SmsLogStatus status : SmsLogStatus.values()) {
                byStatus.put(status, 0);
            }
        }
    }

}
