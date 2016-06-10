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
package nl.strohalm.cyclos.entities.accounts.transactions;

import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for searching form transfer authorizations
 * @author luis
 */
public class TransferAuthorizationQuery extends QueryParameters {
    private static final long            serialVersionUID = 3709956950475279517L;
    private Period                       period;
    private Member                       member;
    private TransferAuthorization.Action action;
    private Element                      by;
    private boolean                      byAdministration;
    private TransferType                 transferType;

    public TransferAuthorization.Action getAction() {
        return action;
    }

    public Element getBy() {
        return by;
    }

    public Member getMember() {
        return member;
    }

    public Period getPeriod() {
        return period;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public boolean isByAdministration() {
        return byAdministration;
    }

    public void setAction(final TransferAuthorization.Action action) {
        this.action = action;
    }

    public void setBy(final Element by) {
        this.by = by;
    }

    public void setByAdministration(final boolean bySystem) {
        this.byAdministration = bySystem;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }
}
