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

import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

public class SmsLogQuery extends QueryParameters {

    private static final long serialVersionUID = -1104624603346955181L;
    private Period            period;
    private Member            member;
    private SmsLogType        type;
    private SmsLogStatus      status;

    public Member getMember() {
        return member;
    }

    public Period getPeriod() {
        return period;
    }

    public SmsLogStatus getStatus() {
        return status;
    }

    public SmsLogType getType() {
        return type;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setStatus(final SmsLogStatus status) {
        this.status = status;
    }

    public void setType(final SmsLogType type) {
        this.type = type;
    }

}
