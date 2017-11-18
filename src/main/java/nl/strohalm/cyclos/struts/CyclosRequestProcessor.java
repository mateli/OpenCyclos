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
package nl.strohalm.cyclos.struts;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.entities.exceptions.LockingException;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.exceptions.ApplicationException;
import nl.strohalm.cyclos.http.ResettableHttpServletRequest;
import nl.strohalm.cyclos.http.ResettableHttpServletResponse;
import nl.strohalm.cyclos.services.access.exceptions.SystemOfflineException;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.ExceptionHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.logging.LoggingHandler;
import nl.strohalm.cyclos.utils.logging.TraceLogDTO;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.SecureTilesRequestProcessor;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Custom struts request processor. Among other things, we control the DB transactions here, opening a read-write transaction for action execution
 * @author luis
 */
public class CyclosRequestProcessor extends SecureTilesRequestProcessor {

    public static class ExecutionResult {
        private boolean       commit;
        private boolean       errorWasSilenced;
        private boolean       hasWrite;
        private boolean       traceLog;
        private boolean       longTransaction;
        private Throwable     error;
        private ActionForward forward;

        public Throwable getError() {
            return error;
        }

        public ActionForward getForward() {
            return forward;
        }

        public boolean isCommit() {
            return commit;
        }

        public boolean isErrorWasSilenced() {
            return errorWasSilenced;
        }

        public boolean isHasWrite() {
            return hasWrite;
        }

        public boolean isLongTransaction() {
            return longTransaction;
        }

        public boolean isTraceLog() {
            return traceLog;
        }
    }

    public static final String        EXECUTION_RESULT_KEY = "cyclos.executionResult";
    public static final String        NO_TRANSACTION_KEY   = "cyclos.noTransactionManagement";

    private static final Log          LOG                  = LogFactory.getLog(CyclosRequestProcessor.class);

    private SettingsService           settingsService;
    private LoggingHandler            loggingHandler;
    private ActionHelper              actionHelper;
    private EntityManagerFactory      entityManagerFactory;

    public SettingsService getSettingsService() {
        return settingsService;
    }

    @Override
    public void init(final ActionServlet servlet, final ModuleConfig moduleConfig) throws ServletException {
        super.init(servlet, moduleConfig);
        SpringHelper.injectBeans(servlet.getServletContext(), this);

        final CyclosControllerConfig config = (CyclosControllerConfig) moduleConfig.getControllerConfig();
        settingsService.addListener(config);
        config.initialize(settingsService.getLocalSettings());
    }

    @Override
    public void process(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        try {
            request.setAttribute(EXECUTION_RESULT_KEY, new ExecutionResult());
            super.process(request, response);
        } catch (final Exception e) {
            if (e instanceof IOException) {
                throw (IOException) e;
            } else if (e instanceof ServletException) {
                throw (ServletException) e;
            } else {
                throw new RuntimeException(e);
            }
        } finally {
            cleanUpTransaction(request);
        }
    }

    @Inject
    public void setActionHelper(final ActionHelper actionHelper) {
        this.actionHelper = actionHelper;
    }

    @Inject
    public void setLoggingHandler(final LoggingHandler loggingHandler) {
        this.loggingHandler = loggingHandler;
    }

    @Inject
    public void setEntityManagerFactory(final EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Inject
    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    /**
     * Override action creation to inject spring beans
     */
    @Override
    protected Action processActionCreate(final HttpServletRequest request, final HttpServletResponse response, final ActionMapping actionMapping) throws IOException {
        synchronized (actions) {
            Action action = (Action) actions.get(actionMapping.getType());
            if (action != null) {
                return action;
            } else {
                action = super.processActionCreate(request, response, actionMapping);

                if (action == null) {
                    return null;
                }

                // Register the action as listener
                if (action instanceof LocalSettingsChangeListener) {
                    settingsService.addListener((LocalSettingsChangeListener) action);
                }

                // Inject the required beans
                try {
                    SpringHelper.injectBeans(getServletContext(), action);
                } catch (final Exception e) {
                    // we must remove the already added action instance (by super.processActionCreate(...))
                    actions.remove(actionMapping.getType());
                    LOG.error("Error injecting beans on " + action, e);
                    throw new IllegalStateException(e);
                }

                return action;
            }
        }
    }

    /**
     * Override form creation to remove the form from session if the request was triggered by the menu
     */
    @Override
    @SuppressWarnings("unchecked")
    protected ActionForm processActionForm(final HttpServletRequest request, final HttpServletResponse response, final ActionMapping actionMapping) {
        final HttpSession session = request.getSession();
        if (StringUtils.isEmpty(actionMapping.getName())) {
            return null;
        }
        if (RequestHelper.isFromMenu(request)) {
            session.removeAttribute(actionMapping.getName());
        }
        // Add form to session
        final ActionForm form = super.processActionForm(request, response, actionMapping);
        if ("session".equals(actionMapping.getScope())) {
            Set<String> sessionForms = (Set<String>) session.getAttribute("sessionForms");
            if (sessionForms == null) {
                sessionForms = new HashSet<String>();
                session.setAttribute("sessionForms", sessionForms);
            }
            sessionForms.add(actionMapping.getName());
        }
        return form;
    }

    /**
     * Here is where the actual action will be invoked. Before it, we open a read-write DB transaction.
     */
    @Override
    protected ActionForward processActionPerform(final HttpServletRequest request, final HttpServletResponse response, final Action action, final ActionForm form, final ActionMapping mapping) throws IOException, ServletException {
        // Clean previous session stored forms
        if (RequestHelper.isFromMenu(request)) {
            cleanSessionForms(request, form);
        }

        // The main processing happens inside a loop, because we need to retry the main execution after a locking exception / deadlock
        final ResettableHttpServletRequest resetableRequest = new ResettableHttpServletRequest(request);
        final ResettableHttpServletResponse resetableResponse = new ResettableHttpServletResponse(response);
        while (true) {
            try {
                final ExecutionResult result = executeAction(resetableRequest, resetableResponse, action, form, mapping);
                // Apply the state
                resetableRequest.applyState();
                resetableResponse.applyState();
                return result.forward;
            } catch (final LockingException e) {
                // Retry the transaction, resetting the state
                resetableRequest.resetState();
                resetableResponse.resetState();
                logDebug(request, "Locking error - re-executing action");
            } catch (final SystemOfflineException e) {
                return ActionHelper.sendError(mapping, request, response, "error.systemOffline");
            }
        }
    }

    /**
     * If the given {@link ForwardConfig} will actually include something (probably a JSP), open a new read-only transaction, so lazy-loading and data
     * iteration will work
     */
    @Override
    protected void processForwardConfig(final HttpServletRequest request, final HttpServletResponse response, final ForwardConfig forward) throws IOException, ServletException {
        final ExecutionResult result = (ExecutionResult) request.getAttribute(EXECUTION_RESULT_KEY);
        final boolean isInclude = forward != null && !forward.getRedirect();
        final boolean needsReadOnlyConnection = isInclude && !result.longTransaction;

        if (needsReadOnlyConnection) {
            // When needed, open a new read-only connection for the include
            openReadOnlyConnection(request);
        }

        // The top-most invocation will manage transaction. Any includes won't
        final boolean managesTransaction = !noTransaction(request);
        if (managesTransaction) {
            request.setAttribute(NO_TRANSACTION_KEY, true);
        }

        try {
            super.processForwardConfig(request, response, forward);
        } catch (final IllegalStateException e) {
            LOG.warn("Error processing the forward to " + forward.getPath());
        } finally {
            if (managesTransaction) {
                // Remove the attribute, otherwise, the top-most invocations of commitOrRollbackTransaction() or rollbackTransaction() will do nothing
                request.removeAttribute(NO_TRANSACTION_KEY);
            }
            if (result.longTransaction) {
                // Close the long running read-write connection
                result.commit = false;
                commitOrRollbackTransaction(request);
            } else if (needsReadOnlyConnection) {
                rollbackReadOnlyConnection(request);
            }
        }
    }

    @Override
    protected HttpServletRequest processMultipart(final HttpServletRequest request) {
        final HttpServletRequest multipartRequest = super.processMultipart(request);
        if (multipartRequest != request) {
            request.setAttribute("multipartRequest", multipartRequest);
        }
        return multipartRequest;
    }

    @Override
    protected void processPopulate(final HttpServletRequest request, final HttpServletResponse response, final ActionForm form, final ActionMapping mapping) throws ServletException {
        try {
            super.processPopulate(request, response, form, mapping);
        } catch (final Exception e) {
            LOG.error("Error populating " + form + " in " + mapping.getPath(), e);
            request.getSession().setAttribute("errorKey", "error.validation");
            final RequestDispatcher rd = request.getRequestDispatcher("/do/error");
            try {
                rd.forward(request, response);
            } catch (final IOException e1) {
                LOG.error("Error while trying to forward to error page", e1);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void cleanSessionForms(final HttpServletRequest request, final ActionForm form) {
        final HttpSession session = request.getSession();
        final Set<String> sessionForms = (Set<String>) session.getAttribute("sessionForms");
        if (sessionForms != null) {
            for (final String name : sessionForms) {
                final ActionForm currentForm = (ActionForm) session.getAttribute(name);
                if (currentForm != form) {
                    session.removeAttribute(name);
                }
            }
        }
    }

    private void cleanUpTransaction(final HttpServletRequest request) {
        if (noTransaction(request)) {
            return;
        }
        logDebug(request, "Cleaning up transaction");

        // Close any open iterators
        DataIteratorHelper.closeOpenIterators();

        // Close the session
        final EntityManagerHolder holder = getEntityManagerHolder();
        if (holder != null) {
            try {
                final EntityManager entityManager = holder.getEntityManager();
                if (entityManager.isOpen()) {
                    entityManager.close();
                }
            } catch (final Exception e) {
                LOG.error("Error closing Hibernate session", e);
            }
            TransactionSynchronizationManager.unbindResourceIfPossible(entityManagerFactory);
        }

        // Cleanup the Spring transaction data
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(false);
        TransactionSynchronizationManager.setActualTransactionActive(false);

        // Cleanup the current transaction data
        CurrentTransactionData.cleanup();

        request.removeAttribute(EXECUTION_RESULT_KEY);
    }

    private void commitOrRollbackTransaction(final HttpServletRequest request) throws IOException, ServletException {
        if (noTransaction(request)) {
            return;
        }
        final ExecutionResult result = (ExecutionResult) request.getAttribute(EXECUTION_RESULT_KEY);
        final EntityManagerHolder holder = getEntityManagerHolder();
        // Commit or rollback the transaction
        boolean runCommitListeners = false;
        boolean lockingException = false;
        if (result.commit) {
            logDebug(request, "Committing transaction");
            runCommitListeners = true; // Marked as commit - should run commit listeners
            try {
                holder.getEntityManager().getTransaction().commit();
            } catch (final Throwable t) {
                // In case of locking exceptions, we must make sure the correct exception type is returned, so the transaction will be retried
                lockingException = ExceptionHelper.isLockingException(t);
                result.error = t;
            }
        } else {
            if (result.error == null && !result.hasWrite) {
                // Transaction was semantically a commit, so, the commit listeners should run.
                // However, as there where no writes the transaction will be rolled back
                runCommitListeners = true;
                logDebug(request, "Nothing written to database. Rolling-back transaction");
            } else {
                logDebug(request, "Rolling-back transaction");
            }
            holder.getEntityManager().getTransaction().rollback();
        }

        if (lockingException) {
            // There was a locking exception - throw it now, so the transaction will be retried
            cleanUpTransaction(request);
            throw new LockingException();
        }

        // Unbind the session holder, so that listeners which should open a new transaction on this same thread won't be messed up
        TransactionSynchronizationManager.unbindResourceIfPossible(entityManagerFactory);

        // Run the transaction listener
        CurrentTransactionData.detachListeners().runListeners(runCommitListeners);

        // Bind the session holder again
        TransactionSynchronizationManager.bindResource(entityManagerFactory, holder);

        // Log the execution if a regular user is logged in and this is not an AJAX request
        if (result.traceLog) {
            traceLog(request, result.error, result.commit);
        }

        // The resulting error was not silenced (i.e, by the BaseAction's try / catch. Log and rethrow
        if (result.error != null && !result.errorWasSilenced) {
            actionHelper.generateLog(request, servlet.getServletContext(), result.error);
            ActionHelper.throwException(result.error);
        }
    }

    private ExecutionResult doExecuteAction(final HttpServletRequest request, final HttpServletResponse response, final Action action, final ActionForm form, final ActionMapping mapping) {
        final ExecutionResult result = (ExecutionResult) request.getAttribute(EXECUTION_RESULT_KEY);
        try {
            result.forward = super.processActionPerform(request, response, action, form, mapping);
            // Get data from CurrentTransactionData
            result.error = CurrentTransactionData.getError();
            result.errorWasSilenced = result.error != null;
            result.longTransaction = DataIteratorHelper.hasOpenIteratorsRequiringOpenConnection();
            if (result.error == null) {
                final EntityManagerHolder holder = getEntityManagerHolder();
                holder.getEntityManager().flush();
            }
            result.hasWrite = CurrentTransactionData.hasWrite();
            if (result.error instanceof ApplicationException) {
                // When there's an ApplicationError, we can still commit, depending on the flag
                result.commit = result.hasWrite && !((ApplicationException) result.error).isShouldRollback();
            } else {
                // The general case: commit if there are no errors
                result.commit = result.hasWrite && !result.errorWasSilenced;
            }
        } catch (final ApplicationException e) {
            result.commit = e.isShouldRollback() ? false : true;
            result.error = e;
        } catch (final Throwable e) {
            result.commit = false;
            result.error = e;
        }
        result.traceLog = generateTraceLog(request);
        return result;
    }

    private ExecutionResult executeAction(final HttpServletRequest request, final HttpServletResponse response, final Action action, final ActionForm form, final ActionMapping mapping) throws IOException, ServletException {
        // Open a new read-write connection
        try {
            openReadWriteConnection(request);
        } catch (final Exception e) {
            throw new SystemOfflineException();
        }

        // Execute the actual action
        final ExecutionResult result = doExecuteAction(request, response, action, form, mapping);

        // Commit or rollback transaction if the current request does not need a long transaction
        if (!result.longTransaction) {
            commitOrRollbackTransaction(request);
        } else {
            logDebug(request, "Keeping connection open because there are open iterators");
        }
        return result;
    }

    private boolean generateTraceLog(final HttpServletRequest request) {
        final String uri = request.getRequestURI();
        return LoggedUser.getAccessType() == LoggedUser.AccessType.USER && !RequestHelper.isAjax(request) && !uri.endsWith("/login") && !uri.endsWith("/logout");
    }

    private EntityManagerHolder getEntityManagerHolder() {
        return (EntityManagerHolder) TransactionSynchronizationManager.getResource(entityManagerFactory);
    }

    private void logDebug(final HttpServletRequest request, final String message) {
        if (LOG.isDebugEnabled()) {
            final String method = RequestHelper.isValidation(request) ? "VALIDATION" : request.getMethod();
            LOG.debug(String.format("%s (%s): %s", request.getRequestURI(), method, message));
        }
    }

    private boolean noTransaction(final HttpServletRequest request) {
        return Boolean.TRUE.equals(request.getAttribute(NO_TRANSACTION_KEY));
    }

    private void openReadOnlyConnection(final HttpServletRequest request) {
        if (noTransaction(request)) {
            return;
        }
        logDebug(request, "Opening read-only transaction for include");

        final EntityManagerHolder holder = getEntityManagerHolder();
        holder.getEntityManager().setFlushMode(FlushModeType.COMMIT);
        if (holder.getEntityManager().getTransaction().isActive()) {
            holder.getEntityManager().getTransaction().setRollbackOnly();
        }
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(true);
    }

    private void openReadWriteConnection(final HttpServletRequest request) throws IOException, ServletException {
        if (noTransaction(request)) {
            return;
        }
        logDebug(request, "Opening a new read-write transaction");
        // Open a read-write transaction
        EntityManager entityManager = null;
        EntityManagerHolder holder = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            holder = new EntityManagerHolder(entityManager);
            entityManager.getTransaction().begin();
            TransactionSynchronizationManager.bindResource(entityManagerFactory, holder);
            holder.setSynchronizedWithTransaction(true);
            TransactionSynchronizationManager.setActualTransactionActive(true);
            TransactionSynchronizationManager.setCurrentTransactionReadOnly(false);
        } catch (final Exception e) {
            TransactionSynchronizationManager.unbindResourceIfPossible(entityManagerFactory);
            LOG.error("Couldn't open a transaction", e);
            ActionHelper.throwException(e);
        }
    }

    private void rollbackReadOnlyConnection(final HttpServletRequest request) {
        if (noTransaction(request)) {
            return;
        }
        final EntityManagerHolder holder = getEntityManagerHolder();
        try {
            logDebug(request, "Rolling back read-only transaction");
            if (holder.getEntityManager().getTransaction().isActive()) {
                holder.getEntityManager().getTransaction().rollback();
            }
        } catch (final PersistenceException e) {
            throw new IllegalStateException(e);
        }
    }

    private void traceLog(final HttpServletRequest request, final Throwable error, final boolean hasWrite) {
        final HttpServletRequest multipartRequest = (HttpServletRequest) request.getAttribute("multipartRequest");
        final HttpServletRequest req = multipartRequest == null ? request : multipartRequest;
        final TraceLogDTO params = new TraceLogDTO();
        params.setUser(LoggedUser.user());
        params.setRemoteAddress(req.getRemoteAddr());
        params.setRequestMethod(req.getMethod());
        params.setPath(req.getRequestURI());
        params.setParameters(ActionHelper.getParameterMap(req));
        final HttpSession session = req.getSession(false);
        params.setSessionId(session == null ? null : session.getId());
        params.setError(error);
        params.setHasDatabaseWrites(hasWrite);
        loggingHandler.trace(params);
    }
}
