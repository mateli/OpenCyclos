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
package nl.strohalm.cyclos.entities.members;

import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for searching payments that hasn't been qualified yet
 * @author luis
 */
public class PaymentsAwaitingFeedbackQuery extends QueryParameters {

    private static final long serialVersionUID = -6530426435356739281L;
    private Member            member;
    private Boolean           expired;

    public Boolean getExpired() {
        return expired;
    }

    public Member getMember() {
        return member;
    }

    public void setExpired(final Boolean expired) {
        this.expired = expired;
    }

    public void setMember(final Member member) {
        this.member = member;
    }
}
