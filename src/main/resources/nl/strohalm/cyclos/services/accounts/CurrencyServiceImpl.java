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
package nl.strohalm.cyclos.services.accounts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.dao.accounts.ARateParametersDAO;
import nl.strohalm.cyclos.dao.accounts.CurrencyDAO;
import nl.strohalm.cyclos.dao.accounts.DRateParametersDAO;
import nl.strohalm.cyclos.dao.accounts.IRateParametersDAO;
import nl.strohalm.cyclos.dao.accounts.RateParametersDAO;
import nl.strohalm.cyclos.entities.accounts.ARateParameters;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.DRateParameters;
import nl.strohalm.cyclos.entities.accounts.IRateParameters;
import nl.strohalm.cyclos.entities.accounts.InitializableRateParameters;
import nl.strohalm.cyclos.entities.accounts.RateParameters;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.accounts.rates.RateServiceLocal;
import nl.strohalm.cyclos.services.accounts.rates.ReinitializeRatesDTO;
import nl.strohalm.cyclos.services.accounts.rates.WhatRate;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.cache.Cache;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.cache.CacheManager;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Service implementation for currencies.
 * 
 * @author luis
 * @author Rinke
 */
public class CurrencyServiceImpl implements CurrencyServiceLocal {

    /**
     * handles the saving of RateParameters
     * @author rinke
     * @param <R> the subType of RateParameters to be saved (so: ARateParametes, DRateParameters or IRateParameters)
     */
    private class RateParameterSaver<R extends RateParameters> {

        private final Class<R>    rateParametersClass;
        @SuppressWarnings("rawtypes")
        private RateParametersDAO dao;

        private RateParameterSaver(final Class<R> rateParametersClass) {
            this.rateParametersClass = rateParametersClass;
            if (rateParametersClass.equals(ARateParameters.class)) {
                dao = aRateParametersDao;
            }
            if (rateParametersClass.equals(DRateParameters.class)) {
                dao = dRateParametersDao;
            } // rates

            if (rateParametersClass.equals(IRateParameters.class)) {
                dao = iRateParametersDao;
            }
        }

        @SuppressWarnings("unchecked")
        private void disableOldRate(final Currency oldCurrency, final Currency newCurrency, final Situation situation) {
            if (situation == Situation.DISABLED || situation == Situation.CHANGED) {
                R oldRate = getRateParameters(oldCurrency);
                oldRate.setDisabledSince(Calendar.getInstance());
                dao.update(oldRate);
                if (situation == Situation.DISABLED) {
                    this.setRateParameters(newCurrency, null);
                }
            }
        }

        @SuppressWarnings("unchecked")
        private R getRateParameters(final Currency currency) {
            if (rateParametersClass.equals(ARateParameters.class)) {
                return (R) currency.getaRateParameters();
            }
            if (rateParametersClass.equals(DRateParameters.class)) {
                return (R) currency.getdRateParameters();
            }
            if (rateParametersClass.equals(IRateParameters.class)) {
                return (R) currency.getiRateParameters();
            }
            return null;
        }

        private Situation getSituation(final boolean enabled, final Currency currency, final Currency old) {
            if (currency.isTransient()) {
                if (enabled) {
                    return Situation.NEW;
                }
                return Situation.UNCHANGED;
            }
            R oldRate = getRateParameters(old);
            R newRate = (enabled) ? this.getRateParameters(currency) : null;
            return this.hasChanged(oldRate, newRate);
        }

        private Situation hasChanged(final R oldRate, final R newRate) {
            if (newRate != null && oldRate == null) {
                return Situation.NEW;
            } else if (newRate == null && oldRate != null) {
                return Situation.DISABLED;
            } else if (newRate != null && oldRate != null && ClassHelper.isInstance(InitializableRateParameters.class, newRate)
                    && ClassHelper.isInstance(InitializableRateParameters.class, oldRate)) {
                final LocalSettings localSettings = settingsService.getLocalSettings();
                final EqualsBuilder eb = new EqualsBuilder();
                InitializableRateParameters oldInitializableRate = (InitializableRateParameters) oldRate;
                InitializableRateParameters newInitializableRate = (InitializableRateParameters) newRate;
                eb.append(localSettings.round(newInitializableRate.getInitValue()), localSettings.round(oldInitializableRate.getInitValue()));
                eb.append(newInitializableRate.getInitDate(), oldInitializableRate.getInitDate());
                eb.append(localSettings.round(newRate.getCreationValue()), localSettings.round(oldRate.getCreationValue()));
                if (oldRate instanceof DRateParameters && newRate instanceof DRateParameters) {
                    final DRateParameters oldDRate = (DRateParameters) oldRate;
                    final DRateParameters newDRate = (DRateParameters) newRate;
                    eb.append(localSettings.roundHighPrecision(newDRate.getInterest()), localSettings.roundHighPrecision(oldDRate.getInterest()));
                    eb.append(localSettings.round(newDRate.getBaseMalus()), localSettings.round(oldDRate.getBaseMalus()));
                    eb.append(localSettings.round(newDRate.getMinimalD()), localSettings.round(oldDRate.getMinimalD()));
                }
                return (eb.isEquals()) ? Situation.UNCHANGED : Situation.CHANGED;
            }
            return Situation.UNCHANGED;
        }

        private void setRateParameters(final Currency currency, final R rateParameters) {
            if (rateParametersClass.equals(ARateParameters.class)) {
                currency.setaRateParameters((ARateParameters) rateParameters);
            }
            if (rateParametersClass.equals(DRateParameters.class)) {
                currency.setdRateParameters((DRateParameters) rateParameters);
            }
            if (rateParametersClass.equals(IRateParameters.class)) {
                currency.setiRateParameters((IRateParameters) rateParameters);
            }
        }

        @SuppressWarnings("unchecked")
        private void storeNewRate(final Situation situation, final Currency currency) {
            if (situation == Situation.NEW || situation == Situation.CHANGED) {
                R rate = this.getRateParameters(currency);
                if (situation == Situation.CHANGED) {
                    rate = (R) rate.clone();
                }
                Calendar now = Calendar.getInstance();
                rate.setEnabledSince(now);
                rate.setDate(now);
                rate.setCurrency(currency);
                rate.setId(null);
                rate.setDisabledSince(null);
                rate = (R) dao.insert(rate);
                setRateParameters(currency, rate);
            }
        }
    }

    /**
     * the situation for any rateParameter.
     * @author rinke
     */
    private enum Situation {
        DISABLED, NEW, CHANGED, UNCHANGED
    }

    private static final String  ALL_KEY = "_ALL_";
    private CurrencyDAO          currencyDao;
    private ARateParametersDAO   aRateParametersDao;
    private DRateParametersDAO   dRateParametersDao;
    private IRateParametersDAO   iRateParametersDao;
    private RateServiceLocal     rateService;
    private FetchServiceLocal    fetchService;
    private SettingsServiceLocal settingsService;
    private AccountServiceLocal  accountService;
    private CacheManager         cacheManager;

    @Override
    public List<Currency> listAll() {
        return getCache().get(ALL_KEY, new CacheCallback() {
            @Override
            public Object retrieve() {
                return currencyDao.listAll(Currency.Relationships.A_RATE_PARAMETERS, Currency.Relationships.D_RATE_PARAMETERS, Currency.Relationships.I_RATE_PARAMETERS);
            }
        });
    }

    @Override
    public List<Currency> listByMember(final Member member) {
        final List<Currency> currencies = new ArrayList<Currency>();
        final List<? extends Account> accounts = accountService.getAccounts(member, RelationshipHelper.nested(Account.Relationships.TYPE, AccountType.Relationships.CURRENCY));
        for (final Account account : accounts) {
            final Currency currency = account.getType().getCurrency();
            if (!currencies.contains(currency)) {
                currencies.add(currency);
            }
        }
        return currencies;
    }

    @Override
    public List<Currency> listByMemberGroup(final MemberGroup group) {
        List<Currency> currencies = currencyDao.listByMemberGroup(group);
        if (CollectionUtils.isEmpty(currencies)) {
            currencies = currencyDao.listAll();
        }
        return currencies;
    }

    @Override
    public List<Currency> listDRatedCurrencies() {
        final List<Currency> currencies = currencyDao.listAll();
        final List<Currency> ratedCurrencies = new ArrayList<Currency>(currencies.size());
        for (final Currency currency : currencies) {
            if (currency.isEnableDRate()) {
                ratedCurrencies.add(currency);
            }
        }
        return ratedCurrencies;
    }

    @Override
    public Currency load(final Long id) {
        return getCache().get(id, new CacheCallback() {
            @Override
            public Object retrieve() {
                return currencyDao.load(id, Currency.Relationships.A_RATE_PARAMETERS, Currency.Relationships.D_RATE_PARAMETERS);
            }
        });
    }

    @Override
    public Currency loadBySymbolOrId(final String symbolOrId) {
        return getCache().get(symbolOrId, new CacheCallback() {
            @Override
            public Object retrieve() {
                Long id;
                try {
                    id = Long.parseLong(symbolOrId);
                } catch (final Exception e) {
                    id = null;
                }
                if (id != null) {
                    // Try by id
                    try {
                        return currencyDao.load(id, Currency.Relationships.A_RATE_PARAMETERS, Currency.Relationships.D_RATE_PARAMETERS);
                    } catch (EntityNotFoundException e) {
                        // Ignore. Will try by symbol
                    }
                }
                // Try by symbol
                return currencyDao.loadBySymbol(symbolOrId, Currency.Relationships.A_RATE_PARAMETERS, Currency.Relationships.D_RATE_PARAMETERS);
            }
        });
    }

    @Override
    public int remove(final Long... ids) {
        getCache().clear();
        return currencyDao.delete(ids);
    }

    @Override
    public Currency save(Currency currency, final WhatRate whatRate) {
        RateParameterSaver<ARateParameters> aRateSaver = new RateParameterSaver<ARateParameters>(ARateParameters.class);
        RateParameterSaver<DRateParameters> dRateSaver = new RateParameterSaver<DRateParameters>(DRateParameters.class);
        RateParameterSaver<IRateParameters> iRateSaver = new RateParameterSaver<IRateParameters>(IRateParameters.class);
        Currency old = null;
        if (currency.isPersistent()) {
            old = currencyDao.load(currency.getId(), Currency.Relationships.A_RATE_PARAMETERS,
                    Currency.Relationships.D_RATE_PARAMETERS, Currency.Relationships.I_RATE_PARAMETERS);
        }
        Situation aRateSituation = aRateSaver.getSituation(whatRate.isaRate(), currency, old);
        Situation dRateSituation = dRateSaver.getSituation(whatRate.isdRate(), currency, old);
        Situation iRateSituation = iRateSaver.getSituation(whatRate.isiRate(), currency, old);

        validate(currency, aRateSituation, dRateSituation, iRateSituation);

        try {
            if (!whatRate.isaRate()) {
                currency.setaRateParameters(null);
            }
            if (!whatRate.isdRate()) {
                currency.setdRateParameters(null);
            }
            if (!whatRate.isiRate()) {
                currency.setiRateParameters(null);
            }

            if (currency.isTransient()) {
                ARateParameters aRate = currency.getaRateParameters();
                currency.setaRateParameters(null);
                DRateParameters dRate = currency.getdRateParameters();
                currency.setdRateParameters(null);
                IRateParameters iRate = currency.getiRateParameters();
                currency.setiRateParameters(null);
                currency = currencyDao.insert(currency, true);
                // Each storeNewRate() must be invoked before setting other rates, or there will be a TransientObjectException
                currency.setaRateParameters(aRate);
                aRateSaver.storeNewRate(aRateSituation, currency);
                currency.setdRateParameters(dRate);
                dRateSaver.storeNewRate(dRateSituation, currency);
                currency.setiRateParameters(iRate);
                iRateSaver.storeNewRate(iRateSituation, currency);
            } else {
                // Disable the old rates
                aRateSaver.disableOldRate(old, currency, aRateSituation);
                dRateSaver.disableOldRate(old, currency, dRateSituation);
                iRateSaver.disableOldRate(old, currency, iRateSituation);
                // Store the new situations
                aRateSaver.storeNewRate(aRateSituation, currency);
                dRateSaver.storeNewRate(dRateSituation, currency);
                iRateSaver.storeNewRate(iRateSituation, currency);
            }
            currencyDao.update(currency, false);
            // Now force flushing the 1st level cache
            fetchService.clearCache();
            if (iRateSituation == Situation.NEW) {
                // we shouldn't redo any other rate except i
                whatRate.setaRate(false);
                whatRate.setdRate(false);
                ReinitializeRatesDTO reinitDto = new ReinitializeRatesDTO();
                reinitDto.setCurrencyId(currency.getId());
                reinitDto.setWhatRate(whatRate);
                reinitDto.setMaintainPastSettings(true);
                reinitDto.setRequestURI("/cyclos/do/admin/editCurrency");
                rateService.reinitializeRate(reinitDto);
            }
        } finally {
            getCache().clear();
        }
        return currency;
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setaRateParametersDao(final ARateParametersDAO aRateParametersDao) {
        this.aRateParametersDao = aRateParametersDao;
    }

    public void setCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setCurrencyDao(final CurrencyDAO currencyDao) {
        this.currencyDao = currencyDao;
    }

    public void setdRateParametersDao(final DRateParametersDAO dRateParametersDao) {
        this.dRateParametersDao = dRateParametersDao;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setiRateParametersDao(final IRateParametersDAO iRateParametersDao) {
        this.iRateParametersDao = iRateParametersDao;
    }

    public void setRateServiceLocal(final RateServiceLocal rateService) {
        this.rateService = rateService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public void validate(final Currency currency, final WhatRate whatRate) {
        getValidator(whatRate).validate(currency);
    }

    private Validator getBaseValidator() {
        final Validator validator = new Validator("currency");
        validator.property("name").required().maxLength(100);
        validator.property("description").maxLength(2000);
        validator.property("symbol").required().maxLength(20);

        validator.property("pattern").required().maxLength(30).add(new PropertyValidation() {
            private static final long serialVersionUID = 455899399346626634L;

            @Override
            public ValidationError validate(final Object object, final Object name, final Object value) {
                final String pattern = (String) value;
                // Check if units pattern contains #amount#
                if (!StringUtils.isEmpty(pattern) && !pattern.contains("#amount#")) {
                    return new ValidationError("currency.error.pattern");
                }
                return null;
            }
        });
        validator.general(new GeneralValidation() {
            private static final long serialVersionUID = 6441662788591991447L;

            @Override
            public ValidationError validate(final Object object) {
                final Currency currency = (Currency) object;
                if (rateService.checkPendingRateInitializations(currency) != null) {
                    return new ValidationError("rates.error.currency.noEditDuringRateReinit");
                }
                return null;
            }
        });
        return validator;
    }

    private Cache getCache() {
        return cacheManager.getCache("cyclos.Currencies");
    }

    private Validator getValidator(final WhatRate whatRate) {
        Validator validator = getBaseValidator();
        validator = rateService.getRateParametersValidator(validator, whatRate);
        return validator;
    }

    private void validate(final Currency currency, final Situation aRateSituation, final Situation dRateSituation, final Situation iRateSituation) {
        WhatRate whatRate = new WhatRate();
        whatRate.setaRate(aRateSituation == Situation.CHANGED || aRateSituation == Situation.NEW);
        whatRate.setdRate(dRateSituation == Situation.CHANGED || dRateSituation == Situation.NEW);
        whatRate.setiRate(iRateSituation == Situation.CHANGED || iRateSituation == Situation.NEW);
        getValidator(whatRate).validate(currency);
    }
}
