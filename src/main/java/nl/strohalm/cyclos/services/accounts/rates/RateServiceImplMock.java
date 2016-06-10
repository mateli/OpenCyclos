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

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee.ARateRelation;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.services.stats.StatisticalResultDTO;
import nl.strohalm.cyclos.services.transactions.GrantLoanDTO;
import nl.strohalm.cyclos.services.transactions.TransferDTO;
import nl.strohalm.cyclos.utils.validation.Validator;

public class RateServiceImplMock implements RateServiceLocal {

    @Override
    public Validator applyARateFieldsValidation(final Validator validator, final ARatedFeeDTO dto, final ARateRelation arateRelation) {
        return validator;
    }

    @Override
    public RatesResultDTO applyLoan(final TransferDTO transferDto, final GrantLoanDTO params) {
        return null;
    }

    @Override
    public RatesToSave applyTransfer(final Transfer transfer) {
        return new RatesToSave();
    }

    @Override
    public void chargeback(final Transfer original, final Transfer chargeback) {
    }

    @Override
    public Calendar checkPendingRateInitializations(final Currency currency) {
        return null;
    }

    @Override
    public RatesResultDTO dateToRate(final RatesDTO ratesDTO) {
        return getRates(ratesDTO);
    }

    @Override
    public BigDecimal getARatedFeePercentage(final TransactionFee fee, final Calendar rawARate, final Calendar rawDRate, final Calendar date) {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getDRateConversionResult(final RatesDTO setOfUnits) {
        return BigDecimal.ZERO;
    }

    @Override
    public Calendar getEnableDate(final Currency currency, final RateType rateType) {
        return null;
    }

    @Override
    public StatisticalResultDTO getRateConfigGraph(final RatedFeeDTO inputParameters) {
        return null;
    }

    @Override
    public Validator getRateParametersValidator(final Validator validator, final WhatRate whatRate) {
        return validator;
    }

    @Override
    public RatesResultDTO getRates(final RatesDTO ratesDTO) {
        RatesResultDTO dto = new RatesResultDTO();
        dto.setDate(ratesDTO == null ? Calendar.getInstance() : ratesDTO.getDate());
        dto.setEmissionDate(ratesDTO == null ? Calendar.getInstance() : ratesDTO.getEmissionDate());
        dto.setExpirationDate(ratesDTO == null ? Calendar.getInstance() : ratesDTO.getExpirationDate());
        dto.setaRate(BigDecimal.ZERO);
        dto.setdRate(BigDecimal.ZERO);
        dto.setiRate(BigDecimal.ZERO);
        dto.setRateBalanceCorrection(BigDecimal.ZERO);
        dto.setVirtualRatedBalance(BigDecimal.ZERO);
        return dto;
    }

    @Override
    public RatesResultDTO getRatesForTransferFrom(final Account from, final BigDecimal amount, final Calendar date) {
        return getRates(null);
    }

    @Override
    public boolean isAnyRateEnabled(final Account account, final Calendar date) {
        return false;
    }

    @Override
    public boolean isAnyRateEnabled(final Currency currency, final Calendar date) {
        return false;
    }

    @Override
    public void persist(final RatesToSave rates) {
    }

    @Override
    public RatesResultDTO rateToDate(final RatesResultDTO params) {
        return getRates(null);
    }

    @Override
    public void reinitializeRate(final ReinitializeRatesDTO reinitializeRatesDto) {
    }

    @Override
    public void validate(final RatedFeeDTO dto, final ARateRelation rateRelation) {
    }

    @Override
    public void validate(final ReinitializeRatesDTO reinitDto) {
    }

}
