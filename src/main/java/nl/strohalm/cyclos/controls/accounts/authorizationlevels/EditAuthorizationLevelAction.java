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
package nl.strohalm.cyclos.controls.accounts.authorizationlevels;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.transactions.AuthorizationLevel;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.transfertypes.AuthorizationLevelService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;

import org.apache.struts.action.ActionForward;

public class EditAuthorizationLevelAction extends BaseFormAction implements LocalSettingsChangeListener {

    private AuthorizationLevelService      authorizationLevelService;
    private TransferTypeService            transferTypeService;
    private DataBinder<AuthorizationLevel> dataBinder;
    private ReadWriteLock                  lock = new ReentrantReadWriteLock(true);

    public DataBinder<AuthorizationLevel> getDataBinder() {
        try {
            lock.readLock().lock();
            if (dataBinder == null) {
                final LocalSettings localSettings = settingsService.getLocalSettings();

                final BeanBinder<AuthorizationLevel> binder = BeanBinder.instance(AuthorizationLevel.class);
                binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
                binder.registerBinder("amount", PropertyBinder.instance(BigDecimal.class, "amount", localSettings.getNumberConverter()));
                binder.registerBinder("level", PropertyBinder.instance(Integer.class, "level"));
                binder.registerBinder("authorizer", PropertyBinder.instance(AuthorizationLevel.Authorizer.class, "authorizer"));
                binder.registerBinder("transferType", PropertyBinder.instance(TransferType.class, "transferType", ReferenceConverter.instance(TransferType.class)));
                binder.registerBinder("adminGroups", SimpleCollectionBinder.instance(AdminGroup.class, "adminGroups"));
                dataBinder = binder;
            }
            return dataBinder;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            dataBinder = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Inject
    public void setAuthorizationLevelService(final AuthorizationLevelService authorizationLevelService) {
        this.authorizationLevelService = authorizationLevelService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditAuthorizationLevelForm form = context.getForm();
        AuthorizationLevel authorizationLevel = getDataBinder().readFromString(form.getAuthorizationLevel());
        final boolean isInsert = authorizationLevel.isTransient();
        authorizationLevel = authorizationLevelService.save(authorizationLevel);
        context.sendMessage(isInsert ? "authorizationLevel.inserted" : "authorizationLevel.modified");
        final Map<String, Object> params = new HashMap<String, Object>();
        TransferType transferType = authorizationLevel.getTransferType();
        transferType = transferTypeService.load(transferType.getId(), TransferType.Relationships.FROM);
        final Long accountTypeId = transferType.getFrom().getId();
        params.put("transferTypeId", transferType.getId());
        params.put("accountTypeId", accountTypeId);
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditAuthorizationLevelForm form = context.getForm();
        final AuthorizationLevel authorizationLevel = getDataBinder().readFromString(form.getAuthorizationLevel());
        authorizationLevelService.validate(authorizationLevel);
    }

}
