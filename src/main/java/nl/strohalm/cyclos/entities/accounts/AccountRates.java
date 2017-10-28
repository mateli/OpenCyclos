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
package nl.strohalm.cyclos.entities.accounts;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Calendar;

@Table(name = "account_rates")
@javax.persistence.Entity
public class AccountRates extends Entity implements Rated {

    public static enum Relationships implements Relationship {
        ACCOUNT("account"), TRANSFER("transfer");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 2572595440698449955L;

    @Column(name = "emission_date")
    private Calendar          emissionDate;

    @Column(name = "expiration_date")
    private Calendar          expirationDate;

    @Column(name = "i_rate", precision = 15, scale = 6)
    private BigDecimal        iRate;

    @Column(name = "rate_balance_correction", precision = 21, scale = 6)
    private BigDecimal        rateBalanceCorrection;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false) // index="ix_accntrts_account_date"
	private Account           account;

    @ManyToOne
    @JoinColumn(name = "transfer_id", nullable = false) // index="ix_accntrts_transfer"
	private Transfer          lastTransfer;

	public Account getAccount() {
        return account;
    }

    @Override
    public Calendar getEmissionDate() {
        return emissionDate;
    }

    @Override
    public Calendar getExpirationDate() {
        return expirationDate;
    }

    @Override
    public BigDecimal getiRate() {
        return iRate;
    }

    public Transfer getLastTransfer() {
        return lastTransfer;
    }

    public BigDecimal getRateBalanceCorrection() {
        return rateBalanceCorrection;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public void setEmissionDate(final Calendar emissionDate) {
        this.emissionDate = emissionDate;
    }

    public void setExpirationDate(final Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setiRate(final BigDecimal iRate) {
        this.iRate = iRate;
    }

    public void setLastTransfer(final Transfer lastTransfer) {
        this.lastTransfer = lastTransfer;
    }

    public void setRateBalanceCorrection(final BigDecimal rateBalanceCorrection) {
        this.rateBalanceCorrection = rateBalanceCorrection;
    }

    @Override
    public String toString() {
        return getId() + ": account " + account.getId();
    }

}
