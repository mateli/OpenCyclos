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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.dao.accounts.guarantees.CertificationDAO;
import nl.strohalm.cyclos.dao.accounts.guarantees.CertificationLogDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification;
import nl.strohalm.cyclos.entities.accounts.guarantees.Certification.Status;
import nl.strohalm.cyclos.entities.accounts.guarantees.CertificationLog;
import nl.strohalm.cyclos.entities.accounts.guarantees.CertificationQuery;
import nl.strohalm.cyclos.entities.accounts.guarantees.Guarantee;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.guarantees.PaymentObligation;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.accounts.guarantees.exceptions.CertificationStatusChangeException;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.notifications.MemberNotificationHandler;
import nl.strohalm.cyclos.utils.query.Page;
import nl.strohalm.cyclos.utils.query.PageImpl;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.PeriodValidation;
import nl.strohalm.cyclos.utils.validation.PeriodValidation.ValidationType;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

public class CertificationServiceImpl implements CertificationServiceLocal, InitializingService {

    private class ExistingActiveCertificationValidation implements GeneralValidation {
        private static final long serialVersionUID = 840449718151754491L;

        @Override
        public ValidationError validate(final Object object) {
            final Certification certification = (Certification) object;
            final GuaranteeType guaranteeType = certification.getGuaranteeType();
            final Member buyer = certification.getBuyer();
            final Member issuer = certification.getIssuer();

            if (guaranteeType == null || buyer == null || issuer == null) {
                return null;
            } else if (willBeActive(certification) && getActiveCertification(guaranteeType, buyer, issuer) != null) {
                return new ValidationError("certification.error.certificationActiveExists");
            } else {
                return null;
            }
        }

        private boolean willBeActive(final Certification certification) {
            Calendar begin = certification.getValidity() == null ? null : certification.getValidity().getBegin();
            Calendar end = certification.getValidity() == null ? null : certification.getValidity().getEnd();

            if (begin == null || end == null) {
                return false;
            } else {
                final Calendar currentDate = DateHelper.truncate(Calendar.getInstance());
                begin = DateHelper.truncate(begin);
                end = DateHelper.truncate(end);

                return (begin.before(currentDate) || begin.equals(currentDate)) && (end.after(currentDate) || end.equals(currentDate));
            }
        }
    }

    private PermissionServiceLocal    permissionService;
    private GuaranteeServiceLocal     guaranteeService;
    private CertificationDAO          certificationDao;
    private CertificationLogDAO       certificationLogDao;
    private MemberNotificationHandler memberNotificationHandler;
    private FetchServiceLocal         fetchService;

    @Override
    public boolean canChangeStatus(final Certification certification, final Certification.Status newStatus) {
        boolean isIssuer;
        switch (newStatus) {
            case ACTIVE:
                final Certification activeCert = getActiveCertification(certification.getGuaranteeType().getCurrency(), certification.getBuyer(), certification.getIssuer());
                isIssuer = guaranteeService.isIssuer() && certification.getIssuer().equals(LoggedUser.accountOwner());
                return isIssuer && isInSomeStatus(certification, Status.SUSPENDED) && (activeCert == null || Status.SCHEDULED == calculateInitialStatus(certification));
            case CANCELLED:
                final boolean hasPermission = permissionService.hasPermission(AdminMemberPermission.GUARANTEES_CANCEL_CERTIFICATIONS_AS_MEMBER);
                return hasPermission && isInSomeStatus(certification, Status.ACTIVE, Status.SUSPENDED, Status.SCHEDULED);
            case SUSPENDED:
                isIssuer = guaranteeService.isIssuer() && certification.getIssuer().equals(LoggedUser.accountOwner());
                return isIssuer && isInSomeStatus(certification, Status.ACTIVE, Status.SCHEDULED);
            default:
                throw new CertificationStatusChangeException(newStatus, "Can't change certification's status, unsupported target status: " + newStatus);
        }
    }

    /**
     * @return true only if the certification's status is CANCELED and the logged user has the right permission granted
     */
    @Override
    public boolean canDelete(Certification certification) {
        certification = fetchService.fetch(certification, Certification.Relationships.ISSUER);
        final boolean hasPermision = permissionService.manages(certification.getIssuer()) && permissionService.hasPermission(AdminMemberPermission.GUARANTEES_CANCEL_CERTIFICATIONS_AS_MEMBER);
        return hasPermision && isInSomeStatus(certification, Certification.Status.CANCELLED);
    }

    @Override
    public void changeStatus(final Long certificationId, Certification.Status newStatus) {
        Certification certification = load(certificationId);

        final boolean changeAllowed = canChangeStatus(certification, newStatus);

        if (!changeAllowed) {
            throw new CertificationStatusChangeException(newStatus);
        } else {
            // if it's an activation we must check by certification's starting date
            if (newStatus == Status.ACTIVE) {
                newStatus = calculateInitialStatus(certification);
            }
            final CertificationLog log = certification.changeStatus(newStatus, LoggedUser.user().getElement());
            saveLog(log);
            certification = save(certification, false);

            switch (newStatus) {
                case CANCELLED:
                    memberNotificationHandler.certificationCanceledNotification(certificationId);
                    break;
                default:
                    memberNotificationHandler.certificationStatusChangedNotification(certification);
            }
        }
    }

    @Override
    public Certification getActiveCertification(final Currency currency, final Member buyer, final Member issuer) {
        final List<Certification> activeCertifications = certificationDao.getActiveCertificationsForBuyer(buyer, currency);

        for (final Certification cert : activeCertifications) {
            if (issuer.equals(cert.getIssuer())) {
                return cert;
            }
        }

        return null;
    }

    @Override
    public List<Member> getCertificationIssuers(final PaymentObligation paymentObligation) {
        final List<Certification> activeCertifications = certificationDao.getActiveCertificationsForBuyer(paymentObligation.getBuyer(), paymentObligation.getCurrency());

        final ArrayList<Member> issuers = new ArrayList<Member>();
        for (final Certification cert : activeCertifications) {
            issuers.add(cert.getIssuer());
        }

        return issuers;
    }

    @Override
    public BigDecimal getUsedAmount(final Certification certification, final boolean includePendingGuarantees) {
        // if we musn't take into account the loan repay then use the DAO implementation
        // return certificationDao.getUsedAmount(certification);

        // this implementation take into account the loan repayment
        final List<Guarantee.Status> statusList = new ArrayList<Guarantee.Status>();
        statusList.add(Guarantee.Status.ACCEPTED);
        if (includePendingGuarantees) {
            statusList.add(Guarantee.Status.PENDING_ADMIN);
            statusList.add(Guarantee.Status.PENDING_ISSUER);
        }
        final List<Guarantee> guarantees = guaranteeService.getGuarantees(certification, PageParameters.all(), statusList);

        BigDecimal notPaidAmount = BigDecimal.ZERO;
        for (final Guarantee g : guarantees) {
            if (g.getStatus() == Guarantee.Status.ACCEPTED && g.getLoan() != null) {
                notPaidAmount = notPaidAmount.add(g.getLoan().getRemainingAmount());
            } else { // if pending there is no associated loan
                notPaidAmount = notPaidAmount.add(g.getAmount());
            }
        }

        return notPaidAmount;
    }

    @Override
    public void initializeService() {
        processCertifications(Calendar.getInstance());
    }

    @Override
    public Certification load(final Long id, final Relationship... fetch) {
        return certificationDao.load(id, fetch);
    }

    @Override
    public void processCertifications(final Calendar taskTime) {
        processCertifications(taskTime, Certification.Status.ACTIVE);
        processCertifications(taskTime, Certification.Status.EXPIRED);
    }

    @Override
    public int remove(final Long certificationId) {
        return certificationDao.delete(certificationId);
    }

    @Override
    public Certification save(final Certification certification) {
        return save(certification, true);
    }

    @Override
    public List<Certification> search(final CertificationQuery queryParameters) {
        return certificationDao.seach(queryParameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CertificationDTO> searchWithUsedAmount(final CertificationQuery queryParameters) {
        final List<Certification> certifications = certificationDao.seach(queryParameters);

        final Transformer transformer = new Transformer() {
            @Override
            public Object transform(final Object input) {
                final Certification certification = (Certification) input;
                return new CertificationDTO(certification, getUsedAmount(certification, false));
            }
        };

        List<CertificationDTO> result = (List<CertificationDTO>) CollectionUtils.collect(certifications, transformer, new ArrayList<CertificationDTO>());
        if (certifications instanceof Page) {
            final Page<Certification> original = (Page<Certification>) certifications;
            final PageParameters pageParameters = new PageParameters(original.getPageSize(), original.getCurrentPage());
            result = new PageImpl<CertificationDTO>(pageParameters, original.getTotalCount(), result);
        }

        return result;
    }

    public void setCertificationDao(final CertificationDAO certificationDao) {
        this.certificationDao = certificationDao;
    }

    public void setCertificationLogDao(final CertificationLogDAO certificationLogDao) {
        this.certificationLogDao = certificationLogDao;
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

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public void validate(final Certification certification) {
        // we must run this general validation before to prevent validation process if this fails
        final GeneralValidation val = new ExistingActiveCertificationValidation();
        final ValidationError error = val.validate(certification);
        if (error != null) {
            final ValidationException vex = new ValidationException();
            vex.addGeneralError(error);
            vex.throwIfHasErrors();
        } else {
            getValidator().validate(certification);
        }
    }

    private Status calculateInitialStatus(final Certification certification) {
        final Calendar currentDate = DateHelper.truncate(Calendar.getInstance());
        return DateHelper.truncate(certification.getValidity().getBegin()).after(currentDate) ? Certification.Status.SCHEDULED : Certification.Status.ACTIVE;
    }

    private Certification getActiveCertification(GuaranteeType guaranteeType, final Member buyer, final Member issuer) {
        guaranteeType = fetchService.fetch(guaranteeType, GuaranteeType.Relationships.CURRENCY);
        return getActiveCertification(guaranteeType.getCurrency(), buyer, issuer);
    }

    private Validator getValidator() {
        final Validator validator = new Validator("certification");
        validator.property("amount").required().positiveNonZero();
        validator.property("guaranteeType").required().key("certification.guaranteeType");
        validator.property("buyer").required().key("certification.buyerUsername");
        validator.property("issuer").required().key("certification.issuerUsername");
        validator.property("validity").add(new PeriodValidation(ValidationType.BOTH_REQUIRED_AND_NOT_EXPIRED)).key("certification.validity");
        return validator;
    }

    private void initialize(final Certification certification) {
        final Status status = calculateInitialStatus(certification);
        certification.setStatus(status);
    }

    /**
     * Checks if the certification has some of the specified states
     */
    private boolean isInSomeStatus(final Certification certification, final Status... status) {
        for (final Status s : status) {
            if (certification.getStatus() == s) {
                return true;
            }
        }
        return false;
    }

    private void processCertifications(Calendar time, Certification.Status newStatus) {
        time = DateHelper.truncate(time);
        final Set<Relationship> fetch = new HashSet<Relationship>();
        fetch.add(Certification.Relationships.BUYER);
        fetch.add(Certification.Relationships.ISSUER);
        fetch.add(Certification.Relationships.LOGS);

        final CertificationQuery query = new CertificationQuery();
        query.setResultType(ResultType.ITERATOR);
        query.setFetch(fetch);

        if (newStatus == Certification.Status.ACTIVE) {
            query.setStartIn(Period.endingAt(time));
            query.setStatusList(Collections.singletonList(Certification.Status.SCHEDULED));
        } else {
            time.add(Calendar.DATE, -1); // this is to discard the certifications expiring today
            query.setEndIn(Period.endingAt(time));
            query.setStatusList(Arrays.asList(Certification.Status.ACTIVE, Certification.Status.SUSPENDED));

        }
        final List<Certification> certifications = search(query);

        for (final Certification certification : certifications) {
            if (newStatus == Certification.Status.ACTIVE) { // we must search for an already active certification
                final Certification alreadyActiveCertification = getActiveCertification(certification.getGuaranteeType(), certification.getBuyer(), certification.getIssuer());
                if (alreadyActiveCertification != null) {
                    newStatus = Certification.Status.SUSPENDED;
                }
            }
            certification.setStatus(newStatus);
            final CertificationLog log = certification.changeStatus(newStatus, null);
            saveLog(log);
            save(certification, false);
            // Notify
            memberNotificationHandler.certificationStatusChangedNotification(certification);
        }
    }

    private Certification save(Certification certification, final boolean validate) {
        if (validate) {
            validate(certification);
        }
        if (certification.isTransient()) {
            initialize(certification);
            certification = certificationDao.insert(certification);
            final CertificationLog log = certification.getNewLog(certification.getStatus(), LoggedUser.user().getElement());
            saveLog(log);
            // Notify
            memberNotificationHandler.certificationIssuedNotification(certification);
            return certification;
        } else {
            return certificationDao.update(certification);
        }
    }

    private CertificationLog saveLog(final CertificationLog certificationLog) {
        if (certificationLog.isTransient()) {
            return certificationLogDao.insert(certificationLog);
        } else {
            return certificationLogDao.update(certificationLog);
        }
    }

}
