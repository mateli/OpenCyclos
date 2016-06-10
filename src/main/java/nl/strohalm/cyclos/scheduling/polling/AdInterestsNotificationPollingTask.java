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

import java.util.Iterator;

import nl.strohalm.cyclos.dao.members.AdInterestDAO;
import nl.strohalm.cyclos.entities.ads.Ad;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.adInterests.AdInterest;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.MessageSettings;
import nl.strohalm.cyclos.services.ads.AdServiceLocal;
import nl.strohalm.cyclos.services.elements.MessageServiceLocal;
import nl.strohalm.cyclos.services.elements.SendMessageFromSystemDTO;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.MessageProcessingHelper;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionRollbackListener;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * A {@link PollingTask} which notifies members with matching {@link AdInterest}s to published {@link Ad}s
 * @author luis
 */
public class AdInterestsNotificationPollingTask extends PollingTask {

    private FetchServiceLocal    fetchService;
    private SettingsServiceLocal settingsService;
    private AdServiceLocal       adService;
    private MessageServiceLocal  messageService;
    private TransactionHelper    transactionHelper;
    private AdInterestDAO        adInterestDao;

    public void setAdInterestDao(final AdInterestDAO adInterestDao) {
        this.adInterestDao = adInterestDao;
    }

    public void setAdServiceLocal(final AdServiceLocal adService) {
        this.adService = adService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setMessageServiceLocal(final MessageServiceLocal messageService) {
        this.messageService = messageService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    @Override
    protected boolean runTask() {
        final Ad ad = adService.getNextAdToNotify();
        if (ad == null) {
            // Nothing to do - force a sleep
            return false;
        }

        // In case of errors, mark the ad as notified anyway
        CurrentTransactionData.addTransactionRollbackListener(new TransactionRollbackListener() {
            @Override
            public void onTransactionRollback() {
                transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(final TransactionStatus status) {
                        adService.markMembersNotified(ad);
                    }
                });
            }
        });

        // Notify and mark the ad as notified
        notifyMembers(ad);

        // Mark the ad as members notified on this transaction. But...
        adService.markMembersNotified(ad);

        // Immediately process the next ad
        return true;
    }

    private void notifyMembers(final Ad ad) {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final MessageSettings messageSettings = settingsService.getMessageSettings();
        final String subject = MessageProcessingHelper.processVariables(messageSettings.getAdInterestSubject(), ad, localSettings);
        final String body = MessageProcessingHelper.processVariables(messageSettings.getAdInterestMessage(), ad, localSettings);

        final CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
        final Iterator<Member> iterator = adInterestDao.resolveMembersToNotify(ad);
        try {
            while (iterator.hasNext()) {
                final Member member = iterator.next();

                final SendMessageFromSystemDTO dto = new SendMessageFromSystemDTO();
                dto.setType(Message.Type.AD_INTEREST);
                dto.setToMember(member);
                dto.setSubject(subject);
                dto.setBody(body);
                dto.setEntity(ad);

                // Send message to member
                messageService.sendFromSystem(dto);

                // Ensure the cache is cleared to avoid many objects in memory
                cacheCleaner.clearCache();
            }
        } finally {
            DataIteratorHelper.close(iterator);
        }
    }

}
