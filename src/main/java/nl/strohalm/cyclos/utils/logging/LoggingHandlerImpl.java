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

import static nl.strohalm.cyclos.utils.logging.LogFormatter.SEPARATOR;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.PaymentDirection;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFeeLog;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.LogSettings;
import nl.strohalm.cyclos.entities.settings.LogSettings.AccountFeeLevel;
import nl.strohalm.cyclos.entities.settings.LogSettings.ScheduledTaskLevel;
import nl.strohalm.cyclos.entities.settings.LogSettings.TraceLevel;
import nl.strohalm.cyclos.entities.settings.LogSettings.TransactionLevel;
import nl.strohalm.cyclos.entities.settings.LogSettings.WebServiceLevel;
import nl.strohalm.cyclos.entities.settings.events.LogSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LogSettingsEvent;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.FileUnits;
import nl.strohalm.cyclos.utils.FormatObject;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * This class contains methods for logging actions
 * @author luis
 */
public class LoggingHandlerImpl implements LoggingHandler, LogSettingsChangeListener {

    private static class WebServiceLogPrepareResult {
        private final Level   logLevel;
        private final boolean logParameters;

        public WebServiceLogPrepareResult(final Level logLevel, final boolean logParameters) {
            this.logLevel = logLevel;
            this.logParameters = logParameters;
        }
    }

    private static final Log     LOG = LogFactory.getLog(LoggingHandlerImpl.class);
    private Logger               traceLogger;
    private boolean              traceWritesOnly;
    private Logger               webServiceLogger;
    private Logger               transactionLogger;
    private Logger               accountFeeLogger;
    private Logger               scheduledTaskLogger;
    private Logger               restLogger;
    private LogFormatter         logFormatter;

    private SettingsServiceLocal settingsService;

    @Override
    public boolean isRestParametersLogEnabled() {
        final Level detailed = WebServiceLevel.DETAILED.getLevel();
        return getRestLogger().isLoggable(detailed);
    }

    /**
     * Log a tax error
     */
    @Override
    public void logAccountFeeError(final AccountFeeLog feeLog, final Throwable error) {
        final Logger logger = getAccountFeeLogger();
        final Level level = AccountFeeLevel.ERRORS.getLevel();
        if (logger.isLoggable(level)) {
            try {
                logger.log(level, "Error on " + feeLog.getAccountFee().getName(), error);
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getAccountFeeFile());
            }
        }
    }

    @Override
    public void logAccountFeeFinished(final AccountFeeLog feeLog) {
        final Logger logger = getAccountFeeLogger();
        final Level level = AccountFeeLevel.STATUS.getLevel();
        if (logger.isLoggable(level)) {
            try {
                logger.log(level, feeLog.getAccountFee().getName() + ": charging has finished");
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getAccountFeeFile());
            }
        }
    }

    /**
     * Log an account fee transfer
     */
    @Override
    public void logAccountFeeInvoice(final Invoice invoice) {
        final Logger logger = getAccountFeeLogger();
        final Level level = AccountFeeLevel.DETAILED.getLevel();
        if (logger.isLoggable(level)) {
            final UnitsConverter unitsConverter = settingsService.getLocalSettings().getUnitsConverter(invoice.getTransferType().getFrom().getCurrency().getPattern());
            final String message = "Sent invoice of %s from %s";
            final Object[] params = { unitsConverter.toString(invoice.getAmount()), invoice.getToMember().getUsername() };
            try {
                logger.log(level, String.format(message, params));
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getAccountFeeFile());
            }
        }
    }

    /**
     * Log an account fee transfer
     */
    @Override
    public void logAccountFeePayment(final Transfer transfer) {
        final Logger logger = getAccountFeeLogger();
        final Level level = AccountFeeLevel.DETAILED.getLevel();
        if (logger.isLoggable(level)) {
            final AccountFeeLog feeLog = transfer.getAccountFeeLog();
            final AccountFee fee = feeLog.getAccountFee();
            final UnitsConverter unitsConverter = settingsService.getLocalSettings().getUnitsConverter(transfer.getFrom().getType().getCurrency().getPattern());
            String message;
            Object[] params;
            if (fee.getPaymentDirection() == PaymentDirection.TO_SYSTEM) {
                message = "Charged %s from %s";
                params = new Object[] { unitsConverter.toString(transfer.getAmount()), transfer.getFrom().getOwnerName() };
            } else {
                message = "Paid %s to %s";
                params = new Object[] { unitsConverter.toString(transfer.getAmount()), transfer.getTo().getOwnerName() };
            }
            try {
                logger.log(level, String.format(message, params));
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getAccountFeeFile());
            }
        }
    }

    @Override
    public void logAccountFeeStarted(final AccountFeeLog feeLog) {
        final Logger logger = getAccountFeeLogger();
        final Level level = AccountFeeLevel.STATUS.getLevel();
        if (logger.isLoggable(level)) {
            try {
                logger.log(level, feeLog.getAccountFee().getName() + ": charging has started");
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getAccountFeeFile());
            }
        }
    }

    @Override
    public void logScheduledTaskError(final String taskName, final Calendar hour, final Exception error) {
        final Logger logger = getScheduledTaskLogger();
        final Level level = ScheduledTaskLevel.ERRORS.getLevel();
        if (logger.isLoggable(level)) {
            try {
                logger.log(level, "Exception on scheduled task: " + taskName + " for hour " + FormatObject.formatObject(hour), error);
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getScheduledTaskFile());
            }
        }
    }

    @Override
    public void logScheduledTaskTrace(final String taskName, final Calendar hour, final long timeTaken) {
        final Logger logger = getScheduledTaskLogger();
        final Level level = ScheduledTaskLevel.DETAILED.getLevel();
        if (logger.isLoggable(level)) {
            final MathContext mathContext = settingsService.getLocalSettings().getMathContext();
            final String formattedTime = settingsService.getLocalSettings().getNumberConverter().toString(new BigDecimal(timeTaken).divide(new BigDecimal(1000), mathContext));
            try {
                logger.log(level, String.format("Scheduled task '%s' for hour %s ran on %s seconds", taskName, FormatObject.formatObject(hour), formattedTime));
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getScheduledTaskFile());
            }
        }
    }

    @Override
    public void logSchedulingTrace(final Calendar hour, final long timeTaken) {
        final Logger logger = getScheduledTaskLogger();
        final Level level = ScheduledTaskLevel.INFO.getLevel();
        if (logger.isLoggable(level)) {
            final MathContext mathContext = settingsService.getLocalSettings().getMathContext();
            final String formattedTime = settingsService.getLocalSettings().getNumberConverter().toString(new BigDecimal(timeTaken).divide(new BigDecimal(1000), mathContext));
            try {
                logger.log(level, String.format("Scheduled tasks for hour %s ran on %s seconds", FormatObject.formatObject(hour), formattedTime));
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getScheduledTaskFile());
            }
        }
    }

    /**
     * Log a successful transfer
     */
    @Override
    public void logTransfer(final Transfer transfer) {
        final Logger logger = getTransactionLogger();
        final Level detailed = TransactionLevel.DETAILED.getLevel();
        final Level normal = TransactionLevel.NORMAL.getLevel();
        final boolean detailedLoggable = logger.isLoggable(detailed);
        final boolean normalLoggable = logger.isLoggable(normal);
        final boolean willLog = detailedLoggable || normalLoggable;
        // Generate log if, at least, normal level is enabled
        if (willLog) {
            // transfer = fetchService.fetch(transfer, RelationshipHelper.nested(Payment.Relationships.FROM, Account.Relationships.TYPE,
            // AccountType.Relationships.CURRENCY), Payment.Relationships.TO);
            Level level;
            final LocalSettings localSettings = settingsService.getLocalSettings();
            final UnitsConverter unitsConverter = localSettings.getUnitsConverter(transfer.getFrom().getType().getCurrency().getPattern());
            String message;
            Object[] args;
            // Get the specific level arguments
            String loggedUser = LoggedUser.hasUser() ? LoggedUser.user().getUsername() : "<no logged user>";
            if (detailedLoggable) {
                final TransferType type = transfer.getType();
                level = detailed;
                message = "logged user: %s, id: %s, date: %s, type: %s (%s), amount: %s, from: %s, to: %s, by: %s, tx#: %s, description: %s";
                final Element by = transfer.getBy();
                args = new Object[] { loggedUser, transfer.getId(), localSettings.getDateTimeConverter().toString(transfer.getDate()), type.getId(), type.getName(), unitsConverter.toString(transfer.getAmount()), transfer.getFrom().getOwnerName(), transfer.getTo().getOwnerName(), by == null ? "<null>" : by.getUsername(), StringUtils.defaultIfEmpty(transfer.getTransactionNumber(), "<null>"), StringUtils.replace(transfer.getDescription(), "\n", "\\n") };
            } else {
                level = normal;
                message = "logged user: %s, id: %s, amount: %s, from: %s, to: %s";
                args = new Object[] { loggedUser, transfer.getId(), unitsConverter.toString(transfer.getAmount()), transfer.getFrom().getOwnerName(), transfer.getTo().getOwnerName() };
            }
            try {
                logger.log(level, String.format(message, args));
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getTransactionFile());
            }
        }
    }

    @Override
    public synchronized void onLogSettingsUpdate(final LogSettingsEvent event) {
        // Invalidate the loggers, forcing them to be recreated on the next time
        close(traceLogger);
        traceLogger = null;
        close(webServiceLogger);
        webServiceLogger = null;
        close(transactionLogger);
        transactionLogger = null;
        close(accountFeeLogger);
        accountFeeLogger = null;
        close(scheduledTaskLogger);
        scheduledTaskLogger = null;
    }

    public void setLogFormatter(final LogFormatter logFormatter) {
        this.logFormatter = logFormatter;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal service) {
        settingsService = service;
        service.addListener(this);
    }

    @Override
    public void trace(final TraceLogDTO params) {
        final Logger logger = getTraceLogger();
        boolean isError = params.getError() != null;
        final Level detailed = TraceLevel.DETAILED.getLevel();
        final boolean detailedLoggable = logger.isLoggable(detailed);
        boolean logParameters = detailedLoggable;
        Level logLevel;
        if (isError) {
            final Level error = TraceLevel.ERRORS.getLevel();
            final boolean errorLoggable = logger.isLoggable(error);
            logLevel = errorLoggable ? error : null;
        } else {
            if (traceWritesOnly && !params.isHasDatabaseWrites()) {
                return;
            }
            final Level normal = TraceLevel.SIMPLE.getLevel();
            final boolean normalLoggable = logger.isLoggable(normal);
            logLevel = detailedLoggable ? detailed : normalLoggable ? normal : null;
        }
        if (logLevel != null) {
            final String message = buildActionString(params, logParameters);
            try {
                logger.log(logLevel, message, params.getError());
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getTraceFile());
            }
        }

    }

    /**
     * Logs an user login
     */
    @Override
    public void traceLogin(final TraceLogDTO params) {
        params.setRequestMethod("LOGIN");
        traceLoginLogout(params);
    }

    /**
     * Logs an user logout
     */
    @Override
    public void traceLogout(final TraceLogDTO params) {
        params.setRequestMethod("LOGOUT");
        traceLoginLogout(params);
    }

    @Override
    public void traceRest(final RestLogDTO params) {
        Logger logger = getRestLogger();
        boolean isError = params.getError() != null;
        WebServiceLogPrepareResult result = prepareWebService(logger, isError);
        if (result.logLevel != null) {
            final String message = buildRestString(params, result.logParameters);
            try {
                logger.log(result.logLevel, message, params.getError());
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getWebServiceFile());
            }
        }
    }

    @Override
    public void traceWebService(final WebServiceLogDTO params) {
        final Logger logger = getWebServiceLogger();
        boolean isError = params.getError() != null || StringUtils.isNotEmpty(params.getErrorMessage());
        WebServiceLogPrepareResult result = prepareWebService(logger, isError);
        if (result.logLevel != null) {
            final String message = buildWebServiceString(params, result.logParameters);
            try {
                logger.log(result.logLevel, message, params.getError());
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getWebServiceFile());
            }
        }
    }

    /**
     * Appends all property values on the given {@link StringBuilder}
     */
    private void appendPropertyValues(final StringBuilder sb, final Object bean) {
        if (bean == null) {
            sb.append("<null>");
            return;
        } else if (bean instanceof String) {
            sb.append(bean);
            return;
        }
        BeanWrapper bw = new BeanWrapperImpl(bean);
        boolean first = true;
        for (PropertyDescriptor desc : bw.getPropertyDescriptors()) {
            if (desc.getWriteMethod() == null) {
                // Only log readable and writable properties
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            String name = desc.getName();
            sb.append(name).append('=');
            Object value = bw.getPropertyValue(name);
            appendValue(sb, FormatObject.maskIfNeeded(name, value));
        }
    }

    /**
     * Appends a value to the given {@link StringBuilder}
     */
    private void appendValue(final StringBuilder sb, final String value) {
        if (value == null) {
            return;
        }
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '\t':
                    sb.append("\\t");
                    continue;
                case '\r':
                    sb.append("\\r");
                    continue;
                case '\n':
                    sb.append("\\n");
                    continue;
                default:
                    sb.append(c);
            }
        }
    }

    /**
     * Builds an action string, appending parameters if detailed logging is enabled
     */
    private String buildActionString(final TraceLogDTO params, final boolean logParameters) {
        final String remoteAddress = params.getRemoteAddress();
        final User user = params.getUser();
        final String requestMethod = params.getRequestMethod();
        final String sessionId = params.getSessionId();
        final String path = params.getPath();
        final Map<String, String[]> parameters = params.getParameters();

        final StringBuilder sb = new StringBuilder();
        appendValue(sb, remoteAddress);
        sb.append(SEPARATOR);
        appendValue(sb, sessionId);
        sb.append(SEPARATOR);
        sb.append(user == null ? "<unknown user>" : user.getUsername()).append(SEPARATOR);
        sb.append(StringUtils.rightPad(requestMethod == null ? "" : requestMethod, 6, ' ')).append(SEPARATOR);
        if (path != null) {
            sb.append(path);
            if (logParameters) {
                // Append the arguments
                sb.append(SEPARATOR);
                boolean first = true;
                for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                    String name = entry.getKey();
                    if (first) {
                        first = false;
                    } else {
                        sb.append(", ");
                    }
                    String[] value = entry.getValue();
                    if (ArrayUtils.isEmpty(value)) {
                        // Nothing to log
                        continue;
                    }
                    sb.append(name).append('=');

                    if (FormatObject.shouldMask(name)) {
                        sb.append(FormatObject.MASKED_VALUE);
                    } else {
                        if (value.length == 1) {
                            appendValue(sb, value[0]);
                        } else {
                            sb.append('[');
                            for (int i = 0; i < value.length; i++) {
                                if (i > 0) {
                                    sb.append(',');
                                }
                                appendValue(sb, value[i]);
                            }
                            sb.append(']');
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    private String buildRestString(final RestLogDTO params, final boolean logParameters) {
        final StringBuilder sb = new StringBuilder();
        appendValue(sb, params.getRemoteAddress());
        sb.append(SEPARATOR);
        appendValue(sb, params.getMember() == null ? "" : params.getMember().getUsername());
        sb.append(SEPARATOR);
        appendValue(sb, params.getMethod() == null ? "" : params.getMethod());
        sb.append(SEPARATOR);
        appendValue(sb, params.getUri() == null ? "" : params.getUri());
        if (logParameters) {
            sb.append(SEPARATOR);
            appendPropertyValues(sb, params.getQueryString() == null ? "" : params.getQueryString());
            sb.append(SEPARATOR);
            appendPropertyValues(sb, params.getRequestBody() == null ? "" : params.getRequestBody());
        }
        return sb.toString();
    }

    private String buildWebServiceString(final WebServiceLogDTO params, final boolean logParameters) {
        final String remoteAddress = params.getRemoteAddress();
        Pos pos = params.getPos();
        ServiceClient serviceClient = params.getServiceClient();
        String message = params.getMessage();
        String serviceName = params.getServiceName();
        String methodName = params.getMethodName();
        String errorMessage = params.getErrorMessage();
        Object parameter = params.getParameter();

        final StringBuilder sb = new StringBuilder();
        appendValue(sb, remoteAddress);
        sb.append(SEPARATOR);
        String clientDescription = "";
        Member member = null;
        String channel = null;
        if (pos != null) {
            clientDescription = "POS<" + pos.getPosId() + ">";
            try {
                member = pos.getMemberPos().getMember();
            } catch (NullPointerException e) {
                // Ignore
            }
            channel = Channel.POS;
        } else if (serviceClient != null) {
            clientDescription = "CLIENT<" + serviceClient.getName() + ">";
            member = serviceClient.getMember();
            try {
                channel = serviceClient.getChannel().getInternalName();
            } catch (NullPointerException e) {
                // Ignore
            }
        }
        sb.append(channel == null ? "" : channel).append(SEPARATOR);
        sb.append(clientDescription).append(SEPARATOR);
        sb.append(member == null ? "" : member.getUsername()).append(SEPARATOR);
        sb.append(message != null ? message : errorMessage != null ? errorMessage : "").append(SEPARATOR);
        if (serviceName != null && methodName != null) {
            sb.append(serviceName).append('.').append(methodName);
            if (logParameters) {
                // Append the parameter
                sb.append(SEPARATOR);
                appendPropertyValues(sb, parameter);
            }
        }
        return sb.toString();
    }

    /**
     * Closes all handlers for the given logger
     * @param logger
     */
    private void close(final Logger logger) {
        if (logger == null) {
            return;
        }
        for (final Handler handler : logger.getHandlers()) {
            try {
                handler.close();
            } catch (final Exception e) {
                LOG.warn("Error while closing log handler - Ignoring", e);
            }
        }
    }

    private Logger getAccountFeeLogger() {
        if (accountFeeLogger == null) {
            final LogSettings logSettings = settingsService.getLogSettings();
            accountFeeLogger = init(logSettings.getAccountFeeLevel().getLevel(), logSettings.getAccountFeeFile());
        }
        return accountFeeLogger;
    }

    private Logger getRestLogger() {
        if (restLogger == null) {
            final LogSettings logSettings = settingsService.getLogSettings();
            restLogger = init(logSettings.getRestLevel().getLevel(), logSettings.getRestFile());
        }
        return restLogger;
    }

    private Logger getScheduledTaskLogger() {
        if (scheduledTaskLogger == null) {
            final LogSettings logSettings = settingsService.getLogSettings();
            scheduledTaskLogger = init(logSettings.getScheduledTaskLevel().getLevel(), logSettings.getScheduledTaskFile());
        }
        return scheduledTaskLogger;
    }

    private Logger getTraceLogger() {
        if (traceLogger == null) {
            final LogSettings logSettings = settingsService.getLogSettings();
            traceLogger = init(logSettings.getTraceLevel().getLevel(), logSettings.getTraceFile());
            traceWritesOnly = logSettings.isTraceWritesOnly();
        }
        return traceLogger;
    }

    private Logger getTransactionLogger() {
        if (transactionLogger == null) {
            final LogSettings logSettings = settingsService.getLogSettings();
            transactionLogger = init(logSettings.getTransactionLevel().getLevel(), logSettings.getTransactionFile());
        }
        return transactionLogger;
    }

    private Logger getWebServiceLogger() {
        if (webServiceLogger == null) {
            final LogSettings logSettings = settingsService.getLogSettings();
            webServiceLogger = init(logSettings.getWebServiceLevel().getLevel(), logSettings.getWebServiceFile());
        }
        return webServiceLogger;
    }

    /**
     * Creates a new logger
     */
    private Logger init(final Level level, final String file) {
        final LogSettings logSettings = settingsService.getLogSettings();
        final Logger logger = Logger.getAnonymousLogger();
        logger.setLevel(level);
        logger.setUseParentHandlers(false);
        try {
            final FileUnits units = logSettings.getMaxLengthPerFileUnits();
            final FileHandler fileHandler = new FileHandler(file, units.calculate(logSettings.getMaxLengthPerFile()), logSettings.getMaxFilesPerLog(), true);
            fileHandler.setFormatter(logFormatter);
            fileHandler.setEncoding(settingsService.getLocalSettings().getCharset());
            logger.addHandler(fileHandler);
        } catch (final Exception e) {
            final ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(logFormatter);
            try {
                consoleHandler.setEncoding(settingsService.getLocalSettings().getCharset());
            } catch (final Exception e1) {
                // Just ignore
            }
            logger.addHandler(consoleHandler);
            logger.log(Level.WARNING, "Unable to create logger for file " + file);
        }
        return logger;
    }

    private WebServiceLogPrepareResult prepareWebService(final Logger logger, final boolean isError) {
        Level logLevel;
        final Level detailed = WebServiceLevel.DETAILED.getLevel();
        final boolean detailedLoggable = logger.isLoggable(detailed);
        boolean logParameters = detailedLoggable;
        if (isError) {
            final Level error = WebServiceLevel.ERRORS.getLevel();
            final boolean errorLoggable = logger.isLoggable(error);
            logLevel = errorLoggable ? error : null;
        } else {
            final Level normal = WebServiceLevel.SIMPLE.getLevel();
            final boolean normalLoggable = logger.isLoggable(normal);
            logLevel = detailedLoggable ? detailed : normalLoggable ? normal : null;
        }
        return new WebServiceLogPrepareResult(logLevel, logParameters);
    }

    private void traceLoginLogout(final TraceLogDTO params) {
        final Logger logger = getTraceLogger();
        final Level level = TraceLevel.SIMPLE.getLevel();
        if (logger.isLoggable(level)) {
            try {
                logger.log(level, buildActionString(params, false));
            } catch (final Exception e) {
                System.out.println("Error generating log on " + settingsService.getLogSettings().getTraceFile());
            }
        }
    }
}
