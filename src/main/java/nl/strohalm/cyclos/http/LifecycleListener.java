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
package nl.strohalm.cyclos.http;

import java.util.Collection;
import java.util.concurrent.Callable;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.initializations.LocalWebInitialization;
import nl.strohalm.cyclos.services.access.AccessService;
import nl.strohalm.cyclos.services.access.exceptions.NotConnectedException;
import nl.strohalm.cyclos.services.application.ApplicationService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionEndListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Listener for context events
 * @author luis
 */
public class LifecycleListener implements ServletContextListener, HttpSessionListener {

    private static final Log   LOG = LogFactory.getLog(LifecycleListener.class);

    private AccessService      accessService;
    private TransactionHelper  transactionHelper;
    private SettingsService    settingsService;
    private ApplicationService applicationService;

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(final ServletContextEvent event) {
        LoggedUser.runAsSystem(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    final ServletContext context = event.getServletContext();
                    final LocalSettings settings = settingsService == null ? null : settingsService.getLocalSettings();

                    // Shutdown the application service
                    if (applicationService != null) {
                        applicationService.shutdown();
                    }

                    final String applicationName = settings == null ? null : settings.getApplicationName();
                    context.log(applicationName == null ? "Cyclos" : applicationName + " destroyed");

                    // Suggest a GC as probably there were several released resources
                    System.gc();

                } catch (final Throwable e) {
                    LOG.error("Error on LifecycleListener.contextDestroyed()", e);
                    throw new RuntimeException(e);
                }
                return null; // required by compiler
            }
        });
    }

    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent event) {
        LoggedUser.runAsSystem(new Callable<Void>() {
            @Override
            public Void call() {
                try {
                    final ServletContext context = event.getServletContext();

                    final WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
                    SpringHelper.injectBeans(applicationContext, LifecycleListener.this);

                    applicationService.initialize();

                    context.setAttribute("systemOnline", applicationService.isOnline());
                    context.setAttribute("cyclosVersion", applicationService.getCyclosVersion());

                    // Run web initializations
                    final Collection<LocalWebInitialization> initializations = applicationContext.getBeansOfType(LocalWebInitialization.class).values();
                    runAll(initializations);

                    final LocalSettings settings = settingsService.getLocalSettings();
                    context.log(settings.getApplicationName() + " initialized");

                    // Suggest a GC in order to keep the heap low right after a startup
                    System.gc();
                } catch (final Throwable e) {
                    LOG.error("Error on LifecycleListener.contextInitialized()", e);
                    throw new RuntimeException(e);
                }
                return null; // required by compiler
            }
        });
    }

    /**
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
    @Override
    public void sessionCreated(final HttpSessionEvent event) {
        // Nothing to do
    }

    /**
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    @Override
    public void sessionDestroyed(final HttpSessionEvent event) {
        final HttpSession session = event.getSession();
        final String sessionId = session == null ? null : session.getId();
        if (sessionId == null) {
            return;
        }

        // If there is an active transaction, use an transaction end listener to actually logout
        if (transactionHelper.hasActiveTransaction()) {
            CurrentTransactionData.addTransactionEndListener(new TransactionEndListener() {
                @Override
                protected void onTransactionEnd(final boolean commit) {
                    doLogout(sessionId);
                }
            });
        } else {
            // Logout directly (this is in another TX)
            doLogout(sessionId);
        }
    }

    @Inject
    public void setAccessService(final AccessService accessService) {
        this.accessService = accessService;
    }

    @Inject
    public void setApplicationService(final ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Inject
    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Inject
    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    private void doLogout(final String sessionId) {
        transactionHelper.runInNewTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                try {
                    accessService.logout(sessionId);
                } catch (final NotConnectedException e) {
                    // Ok, just ignore
                } catch (final RuntimeException e) {
                    LOG.warn("Error logging out member on session destroy", e);
                    status.setRollbackOnly();
                }
            }
        });
    }

    /**
     * Run a single initialization inside a transaction if it is required
     */
    private void run(final LocalWebInitialization initialization) {
        LOG.debug(String.format("Running web initialization (%s)...", initialization.getName()));
        transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(final TransactionStatus status) {
                try {
                    initialization.initialize();
                } catch (final RuntimeException e) {
                    LOG.error(String.format("Error running web initialization: %s", initialization.getName()), e);
                    throw e;
                }
            }
        });
    }

    /**
     * Run all given initializations / finalizations
     */
    private void runAll(final Collection<LocalWebInitialization> initializations) {
        try {
            for (final LocalWebInitialization initialization : initializations) {
                run(initialization);
            }
        } finally {
            CurrentTransactionData.cleanup();
        }
    }
}
