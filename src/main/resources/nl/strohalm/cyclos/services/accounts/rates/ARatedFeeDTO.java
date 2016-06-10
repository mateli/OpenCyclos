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

import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.ChargeType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;

/**
 * DTO for the simulation of the ARate configuration, showing the ARate curve in a graph.
 * @author Rinke
 * 
 */
public class ARatedFeeDTO extends RatedFeeDTO {

    private static final long serialVersionUID = -2371167547711013943L;
    private TransferType      transferType;
    private TransactionFee    transactionFee;
    private ChargeType        chargeType;
    private BigDecimal        h;
    private BigDecimal        aFIsZero;
    private BigDecimal        f1;
    private BigDecimal        fInfinite;
    private BigDecimal        fMinimal;
    private BigDecimal        gFIsZero;
    private boolean           noRangeCheck;

    public ARatedFeeDTO() {
        super();
    }

    public ARatedFeeDTO(final SimpleTransactionFee fee) {
        super();
        chargeType = fee.getChargeType();
        h = fee.getH();
        aFIsZero = fee.getaFIsZero();
        f1 = fee.getF1();
        fInfinite = fee.getfInfinite();
        fMinimal = fee.getfMinimal();
        gFIsZero = fee.getgFIsZero();
        noRangeCheck = true;
    }

    public BigDecimal getaFIsZero() {
        return aFIsZero;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public BigDecimal getF1() {
        return f1;
    }

    public BigDecimal getfInfinite() {
        return fInfinite;
    }

    public BigDecimal getfMinimal() {
        return fMinimal;
    }

    public BigDecimal getgFIsZero() {
        return gFIsZero;
    }

    public BigDecimal getH() {
        return h;
    }

    public TransactionFee getTransactionFee() {
        return transactionFee;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public boolean isNoRangeCheck() {
        return noRangeCheck;
    }

    public void setaFIsZero(final BigDecimal aFIsZero) {
        this.aFIsZero = aFIsZero;
    }

    public void setChargeType(final ChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public void setF1(final BigDecimal f1) {
        this.f1 = f1;
    }

    public void setfInfinite(final BigDecimal fInfinite) {
        this.fInfinite = fInfinite;
    }

    public void setfMinimal(final BigDecimal fMinimal) {
        this.fMinimal = fMinimal;
    }

    public void setgFIsZero(final BigDecimal gFIsZero) {
        this.gFIsZero = gFIsZero;
    }

    public void setH(final BigDecimal h) {
        this.h = h;
    }

    public void setTransactionFee(final TransactionFee transactionFee) {
        this.transactionFee = transactionFee;
    }

    public void setTransferType(final TransferType transferType) {
        this.transferType = transferType;
    }

}
