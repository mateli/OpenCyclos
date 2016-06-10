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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.dao.alerts.AlertDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.alerts.Alert;
import nl.strohalm.cyclos.entities.alerts.Alert.Type;
import nl.strohalm.cyclos.entities.alerts.AlertQuery;
import nl.strohalm.cyclos.entities.alerts.AlertType;
import nl.strohalm.cyclos.entities.alerts.MemberAlert;
import nl.strohalm.cyclos.entities.alerts.SystemAlert;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.notifications.AdminNotificationHandler;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionEndListener;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Implementation class for alerts service interface
 * @author rafael
 */
public class AlertServiceImpl implements AlertServiceLocal {

    private FetchServiceLocal        fetchService;
    private SettingsServiceLocal     settingsService;
    private AlertDAO                 alertDao;
    private AdminNotificationHandler adminNotificationHandler;
    private TransactionHelper        transactionHelper;

    @Override
    public void create(final Member member, final MemberAlert.Alerts alertType, final Object... arguments) {
        MemberAlert alert = new MemberAlert();
        populate(alert, alertType.getValue(), arguments);
        alert.setMember(member);
        save(alert, alertType);
    }

    @Override
    public void create(final SystemAlert.Alerts alertType, final Object... arguments) {
        SystemAlert alert = new SystemAlert();
        populate(alert, alertType.getValue(), arguments);
        save(alert, alertType);
    }

    @Override
    public int getAlertCount(final Type type) {
        if (Alert.Type.MEMBER.equals(type)) {
            if (LoggedUser.hasUser()) {
                AdminGroup adminGroup = LoggedUser.group();
                adminGroup = fetchService.fetch(adminGroup, AdminGroup.Relationships.MANAGES_GROUPS);
                final AlertQuery alertQuery = new AlertQuery();
                alertQuery.setType(type);
                alertQuery.setGroups(adminGroup.getManagesGroups());
                alertQuery.setPageForCount();
                return PageHelper.getTotalCount(alertDao.search(alertQuery));
            } else {
                return alertDao.getCount(type);
            }
        }
        return alertDao.getCount(type);
    }

    @Override
    public Alert load(final Long id, final Relationship... fetch) {
        return alertDao.load(id, fetch);
    }

    @Override
    public int removeAlerts(final Long... ids) {
        return alertDao.delete(ids);
    }

    @Override
    public List<Alert> search(final AlertQuery queryParameters) {
        return alertDao.search(queryParameters);
    }

    public void setAdminNotificationHandler(final AdminNotificationHandler adminNotificationHandler) {
        this.adminNotificationHandler = adminNotificationHandler;
    }

    public void setAlertDao(final AlertDAO alertDao) {
        this.alertDao = alertDao;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    private Alert doSave(final Alert alert) {
        Alert saved = alertDao.insert(alert);
        adminNotificationHandler.notifyAlert(alert);
        return saved;
    }

    private void populate(final Alert alert, final String key, final Object... arguments) {
        String[] args = null;
        if (arguments != null) {
            // Convert each argument to string
            args = new String[arguments.length];
            final LocalSettings localSettings = settingsService.getLocalSettings();
            for (int i = 0; i < args.length; i++) {
                final Object argument = arguments[i];
                String arg;
                if (argument == null) {
                    arg = "";
                } else if (argument instanceof BigDecimal) {
                    arg = localSettings.getNumberConverter().toString((BigDecimal) argument);
                } else if (argument instanceof Calendar) {
                    final Calendar cal = (Calendar) argument;
                    final Calendar trunc = DateHelper.truncate(cal);
                    if (cal.equals(trunc)) {
                        arg = localSettings.getDateConverter().toString(cal);
                    } else {
                        arg = localSettings.getDateTimeConverter().toString(cal);
                    }
                } else {
                    arg = StringUtils.trimToEmpty(argument.toString());
                }
                args[i] = arg;
            }
        }
        alert.setDate(Calendar.getInstance());
        alert.setDescription(key, args);
    }

    private void save(final Alert alert, final AlertType type) {
        if (alert.getDate() == null) {
            alert.setDate(Calendar.getInstance());
        }

        // Only the application shutdown should be saved right away, as the database connection might be disposed right away
        if (SystemAlert.Alerts.APPLICATION_SHUTDOWN.equals(type)) {
            doSave(alert);
        } else {
            // The others are safe to run in the background
            CurrentTransactionData.addTransactionCommitListener(new TransactionEndListener() {
                @Override
                protected void onTransactionEnd(final boolean commit) {
                    transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                        @Override
                        protected void doInTransactionWithoutResult(final TransactionStatus status) {
                            doSave(alert);
                        }
                    });
                }
            });
        }
    }

}
