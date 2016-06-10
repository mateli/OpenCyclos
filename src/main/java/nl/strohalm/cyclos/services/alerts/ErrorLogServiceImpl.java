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
package nl.strohalm.cyclos.services.alerts;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import nl.strohalm.cyclos.dao.alerts.ErrorLogEntryDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntry;
import nl.strohalm.cyclos.entities.alerts.ErrorLogEntryQuery;
import nl.strohalm.cyclos.utils.FormatObject;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.notifications.AdminNotificationHandler;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

/**
 * Implementation for error logs service
 * @author luis
 */
public class ErrorLogServiceImpl implements ErrorLogServiceLocal {

    private ErrorLogEntryDAO         errorLogEntryDao;
    private AdminNotificationHandler adminNotificationHandler;
    private TransactionHelper        transactionHelper;

    @Override
    public int getCount() {
        final ErrorLogEntryQuery query = new ErrorLogEntryQuery();
        query.setShowRemoved(false);
        query.setPageForCount();
        return PageHelper.getTotalCount(errorLogEntryDao.search(query));
    }

    @Override
    public Future<ErrorLogEntry> insert(final Throwable t, final String path, final Map<String, ?> parameters) {
        if (t == null) {
            throw new ValidationException("exception", new RequiredError());
        }
        if (StringUtils.isEmpty(path)) {
            throw new ValidationException("path", new RequiredError());
        }
        return transactionHelper.runAsync(new TransactionCallback<ErrorLogEntry>() {
            @Override
            public ErrorLogEntry doInTransaction(final TransactionStatus status) {
                return doInsert(t, path, parameters);
            }
        });
    }

    @Override
    public ErrorLogEntry load(final Long id, final Relationship... fetch) {
        return errorLogEntryDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        return errorLogEntryDao.delete(ids);
    }

    @Override
    public List<ErrorLogEntry> search(final ErrorLogEntryQuery query) {
        return errorLogEntryDao.search(query);
    }

    public void setAdminNotificationHandler(final AdminNotificationHandler adminNotificationHandler) {
        this.adminNotificationHandler = adminNotificationHandler;
    }

    public void setErrorLogEntryDao(final ErrorLogEntryDAO errorLogEntryDao) {
        this.errorLogEntryDao = errorLogEntryDao;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    private ErrorLogEntry doInsert(final Throwable t, final String path, final Map<String, ?> parameters) {
        ErrorLogEntry entry = new ErrorLogEntry();
        // Basic attributes
        entry.setDate(Calendar.getInstance());
        entry.setPath(path);
        // Logged user
        if (LoggedUser.hasUser()) {
            entry.setLoggedUser(LoggedUser.user());
        }
        // Exception
        final StringWriter stackTrace = new StringWriter();
        t.printStackTrace(new PrintWriter(stackTrace));
        entry.setStackTrace(stackTrace.toString());
        // Request parameters
        if (MapUtils.isEmpty(parameters)) {
            entry.setParameters(new HashMap<String, String>());
        } else {
            final Map<String, String> params = new HashMap<String, String>();
            for (final Map.Entry<String, ?> e : parameters.entrySet()) {
                final String name = e.getKey();
                if (StringUtils.isEmpty(name)) {
                    continue;
                }
                // Mask the value if needed
                String value = CoercionHelper.coerce(String.class, e.getValue());
                params.put(name, FormatObject.maskIfNeeded(name, value));
            }
            entry.setParameters(params);
        }
        entry = errorLogEntryDao.insert(entry);
        adminNotificationHandler.notifyApplicationErrors(entry);
        return entry;
    }
}
