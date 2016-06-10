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
package nl.strohalm.cyclos.services.accounts.rates;

import java.math.BigDecimal;
import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Contains data used to simulate a conversion
 * 
 * @author luis
 */
public class ConversionSimulationDTO extends DataObject {

    private static final long serialVersionUID = -6215506929430954106L;

    private MemberAccount     account;
    private TransferType      transferType;
    private BigDecimal        amount;
    private boolean           useActualRates   = true;
    private Calendar          date;
    private BigDecimal        arate;
    private BigDecimal        drate;
    private boolean           graph;

    public MemberAccount getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getArate() {
        return arate;
    }

    public Calendar getDate() {
        return date;
    }

    public BigDecimal getDrate() {
        return drate;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public boolean isGraph() {
        return graph;
    }

    public boolean isUseActualRates() {
        return useActualRates;
    }

    public void setAccount(final MemberAccount account) {
        this.account = account;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void setArate(final BigDecimal arate) {
        this.arate = arate;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setDrate(final BigDecimal drate) {
        this.drate = drate;
    }

    public void setGraph(final boolean graph) {
        this.graph = graph;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

    public void setUseActualRates(final boolean useActualRates) {
        this.useActualRates = useActualRates;
    }

}
