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
package nl.strohalm.cyclos.controls.payments;

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
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.ProjectionDTO;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentDTO;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.AccountOwnerConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used to generate and validate scheduled payments
 * @author luis
 */
public class CalculatePaymentsAjaxAction extends BaseAjaxAction implements LocalSettingsChangeListener {

    private DataBinder<ProjectionDTO>                   dtoBinder;
    private DataBinder<Collection<ScheduledPaymentDTO>> paymentBinder;
    private PaymentService                              paymentService;
    private ReadWriteLock                               lock = new ReentrantReadWriteLock(true);

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
    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    protected ContentType contentType() {
        return ContentType.XML;
    }

    @Override
    protected void renderContent(final ActionContext context) throws Exception {
        try {
            final CalculatePaymentsAjaxForm form = context.getForm();
            final ProjectionDTO projection = getDtoBinder().readFromString(form);
            if (projection.getFrom() == null) {
                projection.setFrom(context.getAccountOwner());
            }
            final List<ScheduledPaymentDTO> payments = paymentService.calculatePaymentProjection(projection);

            final Map<String, Object> values = new HashMap<String, Object>();
            values.put("payments", getPaymentBinder().readAsString(payments));

            responseHelper.writeStatus(context.getResponse(), ResponseHelper.Status.SUCCESS, values);
        } catch (final ValidationException e) {
            responseHelper.writeValidationErrors(context.getResponse(), e);
        }

    }

    private DataBinder<ProjectionDTO> getDtoBinder() {
        try {
            lock.readLock().lock();
            if (dtoBinder == null) {
                final LocalSettings localSettings = settingsService.getLocalSettings();
                final BeanBinder<ProjectionDTO> binder = BeanBinder.instance(ProjectionDTO.class);
                binder.registerBinder("from", PropertyBinder.instance(AccountOwner.class, "from", AccountOwnerConverter.instance()));
                binder.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", localSettings.getRawDateConverter()));
                binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
                binder.registerBinder("firstExpirationDate", PropertyBinder.instance(Calendar.class, "firstPaymentDate", localSettings.getRawDateConverter()));
                binder.registerBinder("paymentCount", PropertyBinder.instance(Integer.TYPE, "paymentCount"));
                binder.registerBinder("recurrence", DataBinderHelper.timePeriodBinder("recurrence"));
                dtoBinder = binder;
            }
            return dtoBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

    private DataBinder<Collection<ScheduledPaymentDTO>> getPaymentBinder() {
        try {
            lock.readLock().lock();
            if (paymentBinder == null) {
                final LocalSettings localSettings = settingsService.getLocalSettings();
                final BeanBinder<ScheduledPaymentDTO> binder = BeanBinder.instance(ScheduledPaymentDTO.class);
                binder.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", localSettings.getRawDateConverter()));
                binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));

                paymentBinder = BeanCollectionBinder.instance(binder);
            }
            return paymentBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

}
