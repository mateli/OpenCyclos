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
package nl.strohalm.cyclos.utils.logging;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;

/**
 * Defines methods used to log actions
 * @author luis
 */
public interface LoggingHandler {

    /**
     * Returns whether the REST log will include detailed parameters
     */
    boolean isRestParametersLogEnabled();

    /**
     * Log an account fee error
     */
    void logAccountFeeError(AccountFeeLog feeLog, Throwable error);

    /**
     * Log tha an account fee charge has been finished
     */
    void logAccountFeeFinished(AccountFeeLog feeLog);

    /**
     * Log an account fee transfer
     */
    void logAccountFeeInvoice(Invoice invoice);

    /**
     * Log an account fee transfer
     */
    void logAccountFeePayment(Transfer transfer);

    /**
     * Log tha an account fee charge has been started
     */
    void logAccountFeeStarted(AccountFeeLog feeLog);

    /**
     * Log a scheduled task error
     */
    void logScheduledTaskError(String taskName, Calendar hour, Exception error);

    /**
     * Logs a scheduled task execution
     */
    void logScheduledTaskTrace(String taskName, Calendar hour, long timeTaken);

    /**
     * Logs an scheduling group execution
     */
    void logSchedulingTrace(Calendar hour, long timeTaken);

    /**
     * Log a successful transfer
     */
    void logTransfer(Transfer transfer);

    /**
     * Logs an action trace
     */
    void trace(TraceLogDTO params);

    /**
     * Logs an user login
     */
    void traceLogin(TraceLogDTO params);

    /**
     * Logs an user logout
     */
    void traceLogout(TraceLogDTO params);

    /**
     * Logs a REST service access
     */
    void traceRest(RestLogDTO params);

    /**
     * Logs a web service trace
     */
    void traceWebService(WebServiceLogDTO params);
}
