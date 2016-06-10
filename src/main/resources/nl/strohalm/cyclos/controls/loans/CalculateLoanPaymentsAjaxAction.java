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
package nl.strohalm.cyclos.controls.loans;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAjaxAction;
import nl.strohalm.cyclos.entities.accounts.loans.LoanPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.transactions.LoanService;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.ProjectionDTO;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Ajax action used to calculate the payment dates of a multi-payment loan
 * @author luis
 */
public class CalculateLoanPaymentsAjaxAction extends BaseAjaxAction implements LocalSettingsChangeListener {

    private DataBinder<ProjectionDTO>           dtoBinder;
    private LoanService                         loanService;
    private DataBinder<Collection<LoanPayment>> paymentBinder;
    private PaymentService                      paymentService;
    private TransferTypeService                 transferTypeService;
    private ReadWriteLock                       lock = new ReentrantReadWriteLock(true);

    public DataBinder<ProjectionDTO> getDtoBinder() {
        try {
            lock.readLock().lock();
            if (dtoBinder == null) {
                final LocalSettings localSettings = settingsService.getLocalSettings();
                final BeanBinder<ProjectionDTO> binder = BeanBinder.instance(ProjectionDTO.class);
                binder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "transferType"));
                binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
                binder.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", localSettings.getRawDateConverter()));
                binder.registerBinder("firstExpirationDate", PropertyBinder.instance(Calendar.class, "firstExpirationDate", localSettings.getRawDateConverter()));
                binder.registerBinder("paymentCount", PropertyBinder.instance(Integer.TYPE, "paymentCount"));
                dtoBinder = binder;
            }
            return dtoBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

    public LoanService getLoanService() {
        return loanService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public TransferTypeService getTransferTypeService() {
        return transferTypeService;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            dtoBinder = null;
            paymentBinder = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Inject
    public void setLoanService(final LoanService loanService) {
        this.loanService = loanService;
    }

    @Inject
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.XML;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        try {
            final CalculateLoanPaymentsAjaxForm form = context.getForm();
            final ProjectionDTO dto = getDtoBinder().readFromString(form);
            final List<LoanPayment> payments = loanService.calculatePaymentProjection(dto);

            final Map<String, Object> values = new HashMap<String, Object>();
            values.put("payments", getPaymentBinder().readAsString(payments));

            responseHelper.writeStatus(context.getResponse(), ResponseHelper.Status.SUCCESS, values);
        } catch (final ValidationException e) {
            responseHelper.writeValidationErrors(context.getResponse(), e);
        }
    }

    private DataBinder<Collection<LoanPayment>> getPaymentBinder() {
        try {
            lock.readLock().lock();
            if (paymentBinder == null) {
                final LocalSettings localSettings = settingsService.getLocalSettings();
                final BeanBinder<LoanPayment> binder = BeanBinder.instance(LoanPayment.class);
                binder.registerBinder("expirationDate", PropertyBinder.instance(Calendar.class, "expirationDate", localSettings.getRawDateConverter()));
                binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));

                paymentBinder = BeanCollectionBinder.instance(binder);
            }
            return paymentBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

}
