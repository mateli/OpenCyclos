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
package nl.strohalm.cyclos.services.preferences;

import java.util.List;

import nl.strohalm.cyclos.entities.members.printsettings.ReceiptPrinterSettings;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for receipt printers
 * 
 * @author luis
 */
public interface ReceiptPrinterSettingsService extends Service {

    /**
     * Returns whether the given id represents a valid receipt printer for the logged user
     * @return <code>true</code> when logged as the member owner of the receipt printer or one of his operators. <code>false</code> otherwise.
     */
    boolean belongsToTheLoggedUser(Long id);

    /**
     * Lists all receipt printer settings for the currently logged Member
     */
    List<ReceiptPrinterSettings> list();

    /**
     * Load a receipt printer settings by id
     */
    ReceiptPrinterSettings load(Long id);

    /**
     * Removes a receipt printer settings by id
     */
    void remove(Long id);

    /**
     * Saves a receipt printer settings
     */
    ReceiptPrinterSettings save(ReceiptPrinterSettings receiptPrinterSettings);

    /**
     * Validates a receipt printer settings
     */
    void validate(ReceiptPrinterSettings receiptPrinterSettings) throws ValidationException;
}
