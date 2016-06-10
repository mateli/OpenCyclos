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

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.exceptions.ApplicationException;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionEndListener;
import nl.strohalm.cyclos.webservices.WebServiceContext;

import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.commons.logging.Log;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Base filter for web services transaction management
 * @author luis
 */
public abstract class BaseWebServiceTransactionFilter extends OncePerRequestFilter {

    protected TransactionHelper transactionHelper;
    protected Log               log = getLog();

    @Inject
    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    /**
     * Indicates whether the response state should be applied on transaction rollback as well
     */
    protected abstract boolean applyResponseStateOnRollback();

    @Override
    protected void execute(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        // Run the request in a new transaction
        try {
            transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(final TransactionStatus status) {
                    // When debug is enabled, log the transaction begin and add a listener for transaction end log
                    prepareDebugLog();

                    // Execute the workflow
                    try {
                        onBeforeRunInTransaction(request, response);
                        doRunInTransaction(request, response, chain, status);
                    } catch (final Throwable t) {
                        try {
                            onError(request, response, t);
                        } catch (final IOException e) {
                            throw new NestableRuntimeException(e);
                        }
                    } finally {
                        try {
                            onTransactionEnd(request, response);
                        } catch (final IOException e) {
                            throw new NestableRuntimeException(e);
                        }
                    }
                }
            });
        } catch (final NestableRuntimeException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                throw (IOException) cause;
            }
            if (cause instanceof ServletException) {
                throw (ServletException) cause;
            }
            throw e;
        } finally {
            // Cleanup the thread locals
            WebServiceContext.cleanup();
            LoggedUser.cleanup();
        }
    }

    /**
     * Returns the logger
     */
    protected abstract Log getLog();

    /**
     * Returns the service name, which is used on logs
     */
    protected abstract String getServiceName();

    /**
     * Should be implemented in order to determine whether silenced errors from services will be rethrown
     */
    protected abstract boolean handleSilencedErrors();

    /**
     * Callback invoked before starting the transaction
     */
    protected void onBeforeRunInTransaction(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
    }

    /**
     * Callback invoked when the processing results in error
     */
    protected void onError(final HttpServletRequest request, final HttpServletResponse response, final Throwable t) throws IOException {
    }

    /**
     * Callback invoked when the transaction has ended
     */
    protected void onTransactionEnd(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
    }

    /**
     * Runs the filter chain inside a transaction
     */
    private void doRunInTransaction(final HttpServletRequest request, final HttpServletResponse servletResponse, final FilterChain chain, final TransactionStatus status) throws Throwable {
        try {
            // As both web services implementations (CXF for SOAP and Jackson for REST) manually flushes the response before we have the time to end
            // the transaction, we must use a custom response wrapper, which never flushes the buffer until we really want it
            final ResettableHttpServletResponse response = new ResettableHttpServletResponse(servletResponse);

            // Apply the response state after end
            CurrentTransactionData.addTransactionEndListener(new TransactionEndListener() {
                @Override
                protected void onTransactionEnd(final boolean commit) {
                    if (commit || applyResponseStateOnRollback()) {
                        response.applyState();
                    }
                }
            });

            // Process the filter chain
            chain.doFilter(request, response);

            // Handle silenced errors
            final Throwable error = CurrentTransactionData.getError();
            final Integer sc = response.getStatus();
            if (error != null && (sc == null || sc == HttpServletResponse.SC_OK)) {
                throw error;
            }

        } catch (final ApplicationException e) {
            if (e.isShouldRollback()) {
                status.setRollbackOnly();
            }
            if (handleSilencedErrors()) {
                throw e;
            }
        } catch (final Throwable t) {
            status.setRollbackOnly();
            if (handleSilencedErrors()) {
                throw t;
            }
        }
    }

    /**
     * Both logs the transaction begin and adds a transaction end listener to log commits / rollbacks. No-op if log debug is not enabled.
     */
    private void prepareDebugLog() {
        if (log.isDebugEnabled()) {
            log.debug("Running " + getServiceName() + " in a new transaction");
            CurrentTransactionData.addTransactionEndListener(new TransactionEndListener() {
                @Override
                protected void onTransactionEnd(final boolean commit) {
                    if (commit) {
                        log.debug("Committed " + getServiceName() + " transaction");
                    } else {
                        log.debug("Rolled back " + getServiceName() + " transaction");
                    }
                }
            });
        }
    }

}
