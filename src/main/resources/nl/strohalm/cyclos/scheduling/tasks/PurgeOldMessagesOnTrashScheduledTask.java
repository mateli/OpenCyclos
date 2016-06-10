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
package nl.strohalm.cyclos.scheduling.tasks;

import java.util.Calendar;

import nl.strohalm.cyclos.services.elements.MessageService;
import nl.strohalm.cyclos.services.elements.MessageServiceLocal;

/**
 * Scheduled task used to purge messages removed after a given time period
 * @see MessageService#purgeExpiredMessagesOnTrash(Calendar)
 * 
 * @author luis
 */
public class PurgeOldMessagesOnTrashScheduledTask extends BaseScheduledTask {

    private MessageServiceLocal messageService;

    public PurgeOldMessagesOnTrashScheduledTask() {
        super("Purge old messages on trash", false);
    }

    public void setMessageServiceLocal(final MessageServiceLocal messageService) {
        this.messageService = messageService;
    }

    @Override
    protected void doRun(final Calendar time) {
        messageService.purgeExpiredMessagesOnTrash(time);
    }

}
