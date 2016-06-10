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
package nl.strohalm.cyclos.entities.settings;

import java.util.logging.Level;

import nl.strohalm.cyclos.utils.DataObject;
import nl.strohalm.cyclos.utils.FileUnits;

/**
 * Groups log settings
 * @author luis
 */
public class LogSettings extends DataObject {
    /**
     * Possible log levels for account fee logger
     * @author luis
     */
    public static enum AccountFeeLevel {
        OFF(Level.OFF), ERRORS(Level.SEVERE), STATUS(Level.INFO), DETAILED(Level.FINE);
        private final Level level;

        private AccountFeeLevel(final Level level) {
            this.level = level;
        }

        public Level getLevel() {
            return level;
        }
    }

    /**
     * Possible log levels for scheduled task logger
     * @author luis
     */
    public static enum ScheduledTaskLevel {
        OFF(Level.OFF), ERRORS(Level.SEVERE), INFO(Level.INFO), DETAILED(Level.FINE);
        private final Level level;

        private ScheduledTaskLevel(final Level level) {
            this.level = level;
        }

        public Level getLevel() {
            return level;
        }
    }

    /**
     * Possible log levels for trace logger
     * @author luis
     */
    public static enum TraceLevel {
        OFF(Level.OFF), ERRORS(Level.SEVERE), SIMPLE(Level.INFO), DETAILED(Level.FINE);
        private final Level level;

        private TraceLevel(final Level level) {
            this.level = level;
        }

        public Level getLevel() {
            return level;
        }
    }

    /**
     * Possible log levels for transactions logger
     * @author luis
     */
    public static enum TransactionLevel {
        OFF(Level.OFF), NORMAL(Level.INFO), DETAILED(Level.FINE);
        private final Level level;

        private TransactionLevel(final Level level) {
            this.level = level;
        }

        public Level getLevel() {
            return level;
        }
    }

    /**
     * Possible log levels for web services logger
     * @author luis
     */
    public static enum WebServiceLevel {
        OFF(Level.OFF), ERRORS(Level.SEVERE), SIMPLE(Level.INFO), DETAILED(Level.FINE);
        private final Level level;

        private WebServiceLevel(final Level level) {
            this.level = level;
        }

        public Level getLevel() {
            return level;
        }
    }

    private static final long  serialVersionUID      = -5503413951639533249L;

    private TraceLevel         traceLevel            = TraceLevel.SIMPLE;
    private String             traceFile             = "%t/cyclos_trace%g.log";
    private boolean            traceWritesOnly;
    private WebServiceLevel    webServiceLevel       = WebServiceLevel.SIMPLE;
    private String             webServiceFile        = "%t/cyclos_webservices%g.log";
    private WebServiceLevel    restLevel             = WebServiceLevel.SIMPLE;
    private String             restFile              = "%t/cyclos_rest%g.log";
    private TransactionLevel   transactionLevel      = TransactionLevel.NORMAL;
    private String             transactionFile       = "%t/cyclos_transactions%g.log";
    private AccountFeeLevel    accountFeeLevel       = AccountFeeLevel.STATUS;
    private String             accountFeeFile        = "%t/cyclos_account_fees%g.log";
    private ScheduledTaskLevel scheduledTaskLevel    = ScheduledTaskLevel.INFO;
    private String             scheduledTaskFile     = "%t/cyclos_scheduled_task%g.log";
    private int                maxFilesPerLog        = 5;
    private int                maxLengthPerFile      = 1;
    private FileUnits          maxLengthPerFileUnits = FileUnits.MEGA_BYTES;

    public String getAccountFeeFile() {
        return accountFeeFile;
    }

    public AccountFeeLevel getAccountFeeLevel() {
        return accountFeeLevel;
    }

    public int getMaxFilesPerLog() {
        return maxFilesPerLog;
    }

    public int getMaxLengthPerFile() {
        return maxLengthPerFile;
    }

    public FileUnits getMaxLengthPerFileUnits() {
        return maxLengthPerFileUnits;
    }

    public String getRestFile() {
        return restFile;
    }

    public WebServiceLevel getRestLevel() {
        return restLevel;
    }

    public String getScheduledTaskFile() {
        return scheduledTaskFile;
    }

    public ScheduledTaskLevel getScheduledTaskLevel() {
        return scheduledTaskLevel;
    }

    public String getTraceFile() {
        return traceFile;
    }

    public TraceLevel getTraceLevel() {
        return traceLevel;
    }

    public String getTransactionFile() {
        return transactionFile;
    }

    public TransactionLevel getTransactionLevel() {
        return transactionLevel;
    }

    public String getWebServiceFile() {
        return webServiceFile;
    }

    public WebServiceLevel getWebServiceLevel() {
        return webServiceLevel;
    }

    public boolean isTraceWritesOnly() {
        return traceWritesOnly;
    }

    public void setAccountFeeFile(final String accountFeeFile) {
        this.accountFeeFile = accountFeeFile;
    }

    public void setAccountFeeLevel(final AccountFeeLevel accountFeeLevel) {
        this.accountFeeLevel = accountFeeLevel;
    }

    public void setMaxFilesPerLog(final int maxFilesPerLog) {
        this.maxFilesPerLog = maxFilesPerLog;
    }

    public void setMaxLengthPerFile(final int maxLengthPerFile) {
        this.maxLengthPerFile = maxLengthPerFile;
    }

    public void setMaxLengthPerFileUnits(final FileUnits maxLengthPerFileUnits) {
        this.maxLengthPerFileUnits = maxLengthPerFileUnits;
    }

    public void setRestFile(final String restFile) {
        this.restFile = restFile;
    }

    public void setRestLevel(final WebServiceLevel restLevel) {
        this.restLevel = restLevel;
    }

    public void setScheduledTaskFile(final String scheduledTaskFile) {
        this.scheduledTaskFile = scheduledTaskFile;
    }

    public void setScheduledTaskLevel(final ScheduledTaskLevel scheduledTaskLevel) {
        this.scheduledTaskLevel = scheduledTaskLevel;
    }

    public void setTraceFile(final String traceFile) {
        this.traceFile = traceFile;
    }

    public void setTraceLevel(final TraceLevel traceLevel) {
        this.traceLevel = traceLevel;
    }

    public void setTraceWritesOnly(final boolean traceWritesOnly) {
        this.traceWritesOnly = traceWritesOnly;
    }

    public void setTransactionFile(final String transactionFile) {
        this.transactionFile = transactionFile;
    }

    public void setTransactionLevel(final TransactionLevel transactionLevel) {
        this.transactionLevel = transactionLevel;
    }

    public void setWebServiceFile(final String webServiceFile) {
        this.webServiceFile = webServiceFile;
    }

    public void setWebServiceLevel(final WebServiceLevel webServiceLevel) {
        this.webServiceLevel = webServiceLevel;
    }
}
