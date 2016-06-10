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

import nl.strohalm.cyclos.entities.accounts.Rated;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Stores the rates to be persisted after a transaction has been done. The idea is to call rate stuff before the transfer is persisted (so that the
 * transfer doesn't pollute the accountstatus and balances yet), then insert the transfer, and then save the retrieved rate information together with
 * the tranfer's id.
 * 
 * @author rinke
 */
public class RatesToSave extends DataObject implements Rated {

    private static final long serialVersionUID = 102578018772646764L;
    private RatesResultDTO    toRates;
    private RatesResultDTO    fromRates;
    private Transfer          transfer;

    @Override
    public Calendar getEmissionDate() {
        return fromRates == null ? null : fromRates.getEmissionDate();
    }

    @Override
    public Calendar getExpirationDate() {
        return fromRates == null ? null : fromRates.getExpirationDate();
    }

    public RatesResultDTO getFromRates() {
        return fromRates;
    }

    @Override
    public BigDecimal getiRate() {
        return fromRates == null ? null : fromRates.getiRate();
    }

    public RatesResultDTO getToRates() {
        return toRates;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setFromRates(final RatesResultDTO fromRates) {
        this.fromRates = fromRates;
    }

    public void setToRates(final RatesResultDTO toRates) {
        this.toRates = toRates;
    }

    public void setTransfer(final Transfer transfer) {
        this.transfer = transfer;
    }

}
