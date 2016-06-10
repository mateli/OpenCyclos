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
package nl.strohalm.cyclos.services.accounts.guarantees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.dao.accounts.guarantees.PaymentObligationDAO;
import nl.strohalm.cyclos.dao.accounts.guarantees.PaymentObligationLogDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation.Status;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligationLog;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligationQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.PaymentObligationStatusChangeException;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

public class PaymentObligationServiceImpl implements PaymentObligationServiceLocal, InitializingService {

    private class ExistingActiveCertificationValidation implements GeneralValidation {
        private static final long serialVersionUID = -5784222476641557090L;

        @Override
        public ValidationError validate(final Object object) {
            final PaymentObligation paymentObligation = (PaymentObligation) object;
            final Member buyer = paymentObligation.getBuyer();
            Currency currency = fetchService.fetch(paymentObligation.getCurrency());

            if (buyer == null || currency == null) {
                return null;
            } else if (certificationService.getCertificationIssuers(paymentObligation).isEmpty()) {
                return new ValidationError("paymentObligation.error.certificationActiveNotExists", currency.getName());
            } else {
                return null;
            }
        }
    }

    private class MaxPublicationDateBeforeExpirationDateValidation implements GeneralValidation {

        private static final long serialVersionUID = 1L;

        @Override
        public ValidationError validate(final Object object) {
            final PaymentObligation paymentObligation = (PaymentObligation) object;
            final Calendar maxPublishDate = paymentObligation.getMaxPublishDate();
            final Calendar expirationDate = paymentObligation.getExpirationDate();
            if (maxPublishDate != null && expirationDate != null && maxPublishDate.after(expirationDate)) {
                return new ValidationError("paymentObligation.error.maxPublicationDateAfterExpirationDate");
            }
            return null;
        }
    }

    private PaymentObligationDAO      paymentObligationDao;
    private PaymentObligationLogDAO   paymentObligationLogDao;
    private GuaranteeServiceLocal     guaranteeService;
    private CertificationServiceLocal certificationService;
    private MemberNotificationHandler memberNotificationHandler;

    private FetchServiceLocal         fetchService;

    @Override
    public boolean canChangeStatus(final PaymentObligation paymentObligation, final Status newStatus) {
        boolean isBuyer;
        boolean isSeller;
        switch (newStatus) {
            case PUBLISHED:
                final Calendar today = DateHelper.truncate(Calendar.getInstance());
                final Calendar maxPublishDate = paymentObligation.getMaxPublishDate();
                isBuyer = guaranteeService.isBuyer() && paymentObligation.getBuyer().equals(LoggedUser.accountOwner());
                final boolean validDates = maxPublishDate.equals(today) || maxPublishDate.after(today);
                return isBuyer && validDates && isInSomeStatus(paymentObligation, Status.REGISTERED);
            case REGISTERED: // conceal
                isBuyer = guaranteeService.isBuyer() && paymentObligation.getBuyer().equals(LoggedUser.accountOwner());
                return isBuyer && isInSomeStatus(paymentObligation, Status.PUBLISHED);
            case ACCEPTED:
            case REJECTED:
                isSeller = guaranteeService.isSeller() && paymentObligation.getSeller().equals(LoggedUser.accountOwner());
                return isSeller && isInSomeStatus(paymentObligation, Status.PUBLISHED);
            default:
                throw new PaymentObligationStatusChangeException(newStatus, "Can't change payment obligation's status, unsupported target status: " + newStatus);
        }
    }

    @Override
    public boolean canDelete(final PaymentObligation paymentObligation) {
        return isInSomeStatus(paymentObligation, Status.REGISTERED) && guaranteeService.isBuyer() && paymentObligation.getBuyer().equals(LoggedUser.accountOwner());
    }

    @Override
    public PaymentObligation changeStatus(final Long paymentObligationId, final Status newStatus) {
        final PaymentObligation paymentObligation = load(paymentObligationId);

        final boolean changeAllowed = canChangeStatus(paymentObligation, newStatus);

        if (!changeAllowed) {
            throw new PaymentObligationStatusChangeException(newStatus);
        } else {
            final PaymentObligationLog log = paymentObligation.changeStatus(newStatus, LoggedUser.user().getElement());
            saveLog(log);
            save(paymentObligation, false);

            switch (newStatus) {
                case PUBLISHED:
                    memberNotificationHandler.paymentObligationPublishedNotification(paymentObligation);
                    break;
                case REJECTED:
                    memberNotificationHandler.paymentObligationRejectedNotification(paymentObligation);
                    break;
            }
        }
        return paymentObligation;
    }

    @Override
    public Long[] checkPaymentObligationPeriod(final PaymentObligationPackDTO dto) {
        if (dto.getPaymentObligations() == null || dto.getPaymentObligations().length == 0) {
            return new Long[0];
        }

        final Long[] paymentObligationIds = dto.getPaymentObligations();
        final List<PaymentObligation> paymentObligations = paymentObligationDao.loadOrderedByExpiration(paymentObligationIds);

        // take the first to get the currency and buyer (all belong to the same buyer and have the same currency)
        final PaymentObligation firstPaymentObligation = paymentObligations.get(0);

        final Certification certification = certificationService.getActiveCertification(firstPaymentObligation.getCurrency(), firstPaymentObligation.getBuyer(), dto.getIssuer());
        final TimePeriod paymentObligationPeriod = certification.getGuaranteeType().getPaymentObligationPeriod();

        final Calendar limit = Calendar.getInstance();
        limit.setTimeInMillis(firstPaymentObligation.getExpirationDate().getTimeInMillis());
        limit.add(paymentObligationPeriod.getField().getCalendarValue(), paymentObligationPeriod.getNumber());

        final List<Long> exceeded = new ArrayList<Long>();
        for (final PaymentObligation po : paymentObligations) {
            if (po.getExpirationDate().after(limit)) {
                exceeded.add(po.getId());
            }
        }

        return exceeded.toArray(new Long[exceeded.size()]);
    }

    @Override
    public PaymentObligation.Status[] getSellerStatusToFilter() {
        final PaymentObligation.Status[] status = new PaymentObligation.Status[4];
        status[0] = PaymentObligation.Status.PUBLISHED;
        status[1] = PaymentObligation.Status.ACCEPTED;
        status[2] = PaymentObligation.Status.REJECTED;
        status[3] = PaymentObligation.Status.EXPIRED;

        return status;
    }

    @Override
    public PaymentObligation.Status[] getStatusToFilter() {
        final boolean isSeller = guaranteeService.isSeller();
        final boolean isBuyer = guaranteeService.isBuyer();

        if (isSeller && !isBuyer) {
            return getSellerStatusToFilter();
        } else {
            return PaymentObligation.Status.values();
        }
    }

    @Override
    public void initializeService() {
        processPaymentObligations(Calendar.getInstance());
    }

    @Override
    public PaymentObligation load(final Long id, final Relationship... fetch) {
        return paymentObligationDao.load(id, fetch);
    }

    @Override
    public List<PaymentObligation> loadOrderedByExpiration(final Long... ids) {
        return paymentObligationDao.loadOrderedByExpiration(ids);
    }

    @Override
    public void processPaymentObligations(final Calendar taskTime) {
        final Calendar time = DateHelper.truncate(taskTime);

        final PaymentObligationQuery query = new PaymentObligationQuery();
        time.add(Calendar.DATE, -1); // this is to discard the POs expiring today
        query.setExpiration(Period.endingAt(time));
        query.setResultType(ResultType.ITERATOR);
        query.setApplyExpirationToMaxPublishDate(true);
        final Set<Relationship> fetch = new HashSet<Relationship>();
        fetch.add(PaymentObligation.Relationships.LOGS);
        query.setFetch(fetch);
        query.setStatusList(Arrays.asList(PaymentObligation.Status.PUBLISHED));
        final List<PaymentObligation> paymentObligations = paymentObligationDao.search(query);
        for (final PaymentObligation paymentObligation : paymentObligations) {
            final Calendar expirationDate = DateHelper.truncate(paymentObligation.getExpirationDate());
            // the expiration date or the max publication date are before task's time
            final PaymentObligation.Status newStatus = expirationDate.before(taskTime) ? PaymentObligation.Status.EXPIRED : PaymentObligation.Status.REGISTERED;

            paymentObligation.setStatus(newStatus);
            final PaymentObligationLog log = paymentObligation.changeStatus(newStatus, null);
            saveLog(log);
            save(paymentObligation, false);
        }
    }

    @Override
    public int remove(final Long paymentObligationId) {
        return paymentObligationDao.delete(paymentObligationId);
    }

    @Override
    public PaymentObligation save(PaymentObligation paymentObligation, final boolean validate) {
        if (validate) {
            validate(paymentObligation);
        }
        if (paymentObligation.isTransient()) {
            initialize(paymentObligation);
            paymentObligation = paymentObligationDao.insert(paymentObligation);
            final PaymentObligationLog paymentObligationLog = paymentObligation.getNewLog(paymentObligation.getStatus(), LoggedUser.user().getElement());
            saveLog(paymentObligationLog);
            return paymentObligation;
        } else {
            return paymentObligationDao.update(paymentObligation);
        }
    }

    @Override
    public PaymentObligationLog saveLog(final PaymentObligationLog paymentObligationLog) {
        if (paymentObligationLog.isTransient()) {
            return paymentObligationLogDao.insert(paymentObligationLog);
        } else {
            return paymentObligationLogDao.update(paymentObligationLog);
        }
    }

    @Override
    public List<PaymentObligation> search(final PaymentObligationQuery queryParameters) {
        return paymentObligationDao.search(queryParameters);
    }

    public void setCertificationServiceLocal(final CertificationServiceLocal certificationService) {
        this.certificationService = certificationService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGuaranteeServiceLocal(final GuaranteeServiceLocal guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    public void setMemberNotificationHandler(final MemberNotificationHandler memberNotificationHandler) {
        this.memberNotificationHandler = memberNotificationHandler;
    }

    public void setPaymentObligationDao(final PaymentObligationDAO paymentObligationDao) {
        this.paymentObligationDao = paymentObligationDao;
    }

    public void setPaymentObligationLogDao(final PaymentObligationLogDAO paymentObligationLogDao) {
        this.paymentObligationLogDao = paymentObligationLogDao;
    }

    @Override
    public void validate(final PaymentObligation paymentObligation) {
        // we must run this general validation before to prevent validation process if this fails
        final GeneralValidation val = new ExistingActiveCertificationValidation();
        final ValidationError error = val.validate(paymentObligation);
        if (error != null) {
            final ValidationException vex = new ValidationException();
            vex.addGeneralError(error);
            vex.throwIfHasErrors();
        } else {
            getValidator().validate(paymentObligation);
        }
    }

    private Validator getValidator() {
        final Validator validator = new Validator("paymentObligation");
        // the status is not validated because it is set in at insert
        validator.property("buyer").required().key("paymentObligation.buyerUsername");
        validator.property("seller").required().key("paymentObligation.sellerUsername");
        validator.property("expirationDate").required().futureOrToday();
        validator.property("maxPublishDate").required().futureOrToday();
        validator.property("amount").required().positiveNonZero();
        validator.property("currency").required();
        validator.property("description").required();
        validator.general(new MaxPublicationDateBeforeExpirationDateValidation());
        return validator;
    }

    private void initialize(final PaymentObligation paymentObligation) {
        paymentObligation.setStatus(PaymentObligation.Status.REGISTERED);
        paymentObligation.setRegistrationDate(Calendar.getInstance());
    }

    /**
     * Checks if the payment obligation has some of the specified states
     * @param certification
     * @param status
     */
    private boolean isInSomeStatus(final PaymentObligation paymentObligation, final Status... status) {
        for (final Status s : status) {
            if (paymentObligation.getStatus() == s) {
                return true;
            }
        }
        return false;
    }
}
