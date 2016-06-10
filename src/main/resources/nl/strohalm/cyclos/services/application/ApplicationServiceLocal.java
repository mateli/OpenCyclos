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
package nl.strohalm.cyclos.services.application;

import java.util.Calendar;

import nl.strohalm.cyclos.entities.Application.PasswordHash;
import nl.strohalm.cyclos.entities.accounts.LockedAccountsOnPayments;
import nl.strohalm.cyclos.scheduling.polling.PollingTask;
import nl.strohalm.cyclos.utils.transaction.TransactionCommitListener;

/**
 * Local interface. It must be used only from other services.
 */
public interface ApplicationServiceLocal extends ApplicationService {

    /**
     * Attempts to awake a {@link PollingTask} with the given type. Will have no effect if cyclos.disableScheduling = true in cyclos.properties
     */
    void awakePollingTask(Class<? extends PollingTask> type);

    /**
     * Adds a {@link TransactionCommitListener} which invokes {@link #awakePollingTask(Class)}
     */
    void awakePollingTaskOnTransactionCommit(Class<? extends PollingTask> type);

    /**
     * Returns the date since when the account status is activated
     */
    Calendar getAccountStatusEnabledSince();

    /**
     * Returns which accounts should be locked on payments
     */
    LockedAccountsOnPayments getLockedAccountsOnPayments();

    /**
     * Returns the hash algorithm that should be used for passwords
     */
    PasswordHash getPasswordHash();

    /**
     * Returns whether the Cyclos application is fully initialized
     */
    boolean isInitialized();

    /**
     * Returns whether this Cyclos instance should run scheduled tasks
     */
    boolean isRunScheduling();

    /**
     * Purges old index operations considering the given time
     */
    void purgeIndexOperations(Calendar time);

}
