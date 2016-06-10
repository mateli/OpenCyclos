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
package nl.strohalm.cyclos.dao.sms;

import nl.strohalm.cyclos.entities.sms.SmsLogStatus;
import nl.strohalm.cyclos.entities.sms.SmsLogType;

/**
 * Contains data for the sms log report
 * 
 * @author luis
 */
public class SmsLogReportTotal {

    private final SmsLogType   type;
    private final SmsLogStatus status;
    private final int          total;

    public SmsLogReportTotal(final SmsLogType type, final SmsLogStatus status, final int total) {
        this.type = type;
        this.status = status;
        this.total = total;
    }

    public SmsLogReportTotal(final String type, final String status, final int total) {
        this(SmsLogType.valueOf(type), SmsLogStatus.valueOf(status), total);
    }

    public SmsLogStatus getStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }

    public SmsLogType getType() {
        return type;
    }

}
