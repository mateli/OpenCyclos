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
package nl.strohalm.cyclos.services.sms;

import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.sms.SmsLog;
import nl.strohalm.cyclos.entities.sms.SmsType;

/**
 * Local interface. It must be used only from other services.
 */
public interface SmsLogServiceLocal extends SmsLogService {

    /**
     * It loads a sms type by code. That code must be defined in the sms type catalog
     * @throws EntityNotFoundException if the code is not defined.
     */
    SmsType loadSmsTypeByCode(String code) throws EntityNotFoundException;

    /**
     * Inserts a SMS log into the database
     */
    SmsLog save(SmsLog smsLog);
}
