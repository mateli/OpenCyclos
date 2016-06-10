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
package nl.strohalm.cyclos.controls.accounts.external.process;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransfer;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.accounts.external.ExternalTransferService;
import nl.strohalm.cyclos.services.accounts.external.ProcessExternalTransferDTO;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to process external transfers
 * @author luis
 */
public class ProcessExternalTransfersAction extends BaseAction implements LocalSettingsChangeListener {
    private ExternalTransferService                            externalTransferService;
    private DataBinder<Collection<ProcessExternalTransferDTO>> transferBinder;

    public DataBinder<Collection<ProcessExternalTransferDTO>> getTransferBinder() {
        if (transferBinder == null) {
            final LocalSettings settings = settingsService.getLocalSettings();
            final BeanBinder<ProcessExternalTransferDTO> binder = BeanBinder.instance(ProcessExternalTransferDTO.class);
            binder.registerBinder("externalTransfer", PropertyBinder.instance(ExternalTransfer.class, "externalTransfer"));
            binder.registerBinder("date", PropertyBinder.instance(Calendar.class, "date", settings.getRawDateConverter()));
            binder.registerBinder("loan", PropertyBinder.instance(Loan.class, "loan"));
            binder.registerBinder("transfer", PropertyBinder.instance(Transfer.class, "transfer"));
            binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", settings.getNumberConverter()));
            transferBinder = BeanCollectionBinder.instance(binder);
        }
        return transferBinder;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        transferBinder = null;
    }

    @Inject
    public void setExternalTransferService(final ExternalTransferService externalTransferService) {
        this.externalTransferService = externalTransferService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        if (!RequestHelper.isPost(request)) {
            throw new ValidationException();
        }
        final ProcessExternalTransfersForm form = context.getForm();
        final Collection<ProcessExternalTransferDTO> transfers = getTransferBinder().readFromString(form);
        try {
            externalTransferService.process(transfers);
        } catch (final UnexpectedEntityException e) {
            throw new ValidationException();
        }
        context.sendMessage("externalTransferProcess.processed");
        return ActionHelper.redirectWithParam(request, context.getSuccessForward(), "externalAccountId", form.getExternalAccountId());
    }
}
