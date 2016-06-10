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
package nl.strohalm.cyclos.entities.accounts.external;

import java.math.BigDecimal;

import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Query parameters for external transfer search
 * @author luis
 */
public class ExternalTransferQuery extends QueryParameters {
    private static final long              serialVersionUID = 3149800403089654662L;
    private ExternalAccount                account;
    private ExternalTransfer.SummaryStatus status;
    private ExternalTransferType           type;
    private ExternalTransferImport         transferImport;
    private Member                         member;
    private Period                         period;
    private BigDecimal                     initialAmount;
    private BigDecimal                     finalAmount;
    private boolean                        onlyWithValidTypes;

    public ExternalAccount getAccount() {
        return account;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public Member getMember() {
        return member;
    }

    public Period getPeriod() {
        return period;
    }

    public ExternalTransfer.SummaryStatus getStatus() {
        return status;
    }

    public ExternalTransferImport getTransferImport() {
        return transferImport;
    }

    public ExternalTransferType getType() {
        return type;
    }

    public boolean isOnlyWithValidTypes() {
        return onlyWithValidTypes;
    }

    public void setAccount(final ExternalAccount account) {
        this.account = account;
    }

    public void setFinalAmount(final BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public void setInitialAmount(final BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public void setOnlyWithValidTypes(final boolean onlyWithValidTypes) {
        this.onlyWithValidTypes = onlyWithValidTypes;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setStatus(final ExternalTransfer.SummaryStatus status) {
        this.status = status;
    }

    public void setTransferImport(final ExternalTransferImport transferImport) {
        this.transferImport = transferImport;
    }

    public void setType(final ExternalTransferType type) {
        this.type = type;
    }

}
