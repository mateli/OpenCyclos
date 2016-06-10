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
package nl.strohalm.cyclos.services.transfertypes;

import java.math.BigDecimal;
import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.Rated;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * class bundling the parameters for TransactionFeeService.buildTransfer()
 * @author Rinke
 */
public class BuildTransferWithFeesDTO extends DataObject implements Rated {

    private static final long serialVersionUID = 7097541422066866233L;

    private Calendar          date;
    private Account           from;
    private Account           to;
    private BigDecimal        transferAmount;
    private TransactionFee    fee;
    private boolean           simulation;
    private Calendar          emissionDate;
    private Calendar          expirationDate;
    private boolean           showZeroFees;

    public BuildTransferWithFeesDTO() {
        super();
    }

    public BuildTransferWithFeesDTO(final Calendar date, final Account from, final Account to, final BigDecimal transferAmount, final TransactionFee fee, final boolean simulation) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.transferAmount = transferAmount;
        this.fee = fee;
        this.simulation = simulation;
    }

    public Calendar getDate() {
        return date;
    }

    @Override
    public Calendar getEmissionDate() {
        return emissionDate;
    }

    @Override
    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public TransactionFee getFee() {
        return fee;
    }

    public Account getFrom() {
        return from;
    }

    @Override
    public BigDecimal getiRate() {
        // i-rate doesn't have fees, so meaningless here.
        return null;
    }

    public Account getTo() {
        return to;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public boolean isShowZeroFees() {
        return showZeroFees;
    }

    public boolean isSimulation() {
        return simulation;
    }

    public void setDate(final Calendar date) {
        this.date = date;
    }

    public void setEmissionDate(final Calendar emissionDate) {
        this.emissionDate = emissionDate;
    }

    public void setExpirationDate(final Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setFee(final TransactionFee fee) {
        this.fee = fee;
    }

    public void setFrom(final Account from) {
        this.from = from;
    }

    public void setShowZeroFees(final boolean showZeroFees) {
        this.showZeroFees = showZeroFees;
    }

    public void setSimulation(final boolean simulation) {
        this.simulation = simulation;
    }

    public void setTo(final Account to) {
        this.to = to;
    }

    public void setTransferAmount(final BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

}
