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

import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.sms.SmsMailing;
import nl.strohalm.cyclos.services.elements.MessageServiceLocal;
import nl.strohalm.cyclos.services.elements.SendSmsDTO;
import nl.strohalm.cyclos.services.sms.SmsMailingServiceLocal;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionEndListener;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * A {@link PollingTask} that sends pending SMS mailing messages
 * @author luis
 */
public class SmsMailingSendingPollingTask extends PollingTask {

    private SmsMailingServiceLocal smsMailingService;
    private MessageServiceLocal    messageService;
    private TransactionHelper      transactionHelper;

    public void setMessageServiceLocal(final MessageServiceLocal messageService) {
        this.messageService = messageService;
    }

    public void setSmsMailingServiceLocal(final SmsMailingServiceLocal smsMailingService) {
        this.smsMailingService = smsMailingService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    @Override
    protected boolean runTask() {
        final SmsMailing smsMailing = smsMailingService.nextToSend();
        if (smsMailing == null) {
            // Nothing to do - force a sleep
            return false;
        }

        final Member member = smsMailingService.nextMemberToSend(smsMailing);
        if (member == null) {
            // No more to send for this sms mailing. Mark as finished and get the next one
            smsMailing.setFinished(true);
            return true;
        }

        // Even on errors, remove the member from pending
        CurrentTransactionData.addTransactionEndListener(new TransactionEndListener() {
            @Override
            protected void onTransactionEnd(final boolean commit) {
                transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(final TransactionStatus status) {
                        smsMailingService.removeMemberFromPending(smsMailing, member);
                    }
                });
            }
        });

        // Send the SMS mailing
        final SendSmsDTO sms = new SendSmsDTO();
        sms.setTargetMember(member);
        if (!smsMailing.isFree()) {
            sms.setChargedMember(member);
        }
        sms.setSmsMailing(smsMailing);
        sms.setText(smsMailing.getText());
        messageService.sendSms(sms);

        return true;
    }

}
