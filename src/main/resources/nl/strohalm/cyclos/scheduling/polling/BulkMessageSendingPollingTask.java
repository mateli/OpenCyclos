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
package nl.strohalm.cyclos.scheduling.polling;

import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.services.elements.MessageServiceLocal;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionRollbackListener;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * A {@link PollingTask} which sends bulk messages
 * @author luis
 */
public class BulkMessageSendingPollingTask extends PollingTask {

    private MessageServiceLocal messageService;
    private TransactionHelper   transactionHelper;

    public void setMessageServiceLocal(final MessageServiceLocal messageService) {
        this.messageService = messageService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    @Override
    protected boolean runTask() {
        final Message message = messageService.nextToSend();
        if (message == null) {
            // Nothing to do - force a sleep
            return false;
        }

        CurrentTransactionData.addTransactionRollbackListener(new TransactionRollbackListener() {
            @Override
            public void onTransactionRollback() {
                transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(final TransactionStatus arg0) {
                        Message msg = messageService.load(message.getId());
                        msg.setEmailSent(true);
                    }
                });
            }
        });

        // Send the e-mail
        messageService.sendEmailIfNeeded(message);

        // When there was a server error while sendind the e-mail, sleep a bit, so the same message will be retried
        if (CurrentTransactionData.getMailError() != null) {
            return false;
        }

        // Mark the message as e-mail sent
        message.setEmailSent(true);

        // Immediately process the next record
        return true;
    }

}
