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

import java.util.Collections;
import java.util.List;

import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.printsettings.ReceiptPrinterSettings;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security layer for {@link ReceiptPrinterSettingsService}
 * 
 * @author luis
 */
public class ReceiptPrinterSettingsServiceSecurity extends BaseServiceSecurity implements ReceiptPrinterSettingsService {

    private ReceiptPrinterSettingsServiceLocal receiptPrinterSettingsService;

    @Override
    public boolean belongsToTheLoggedUser(final Long id) {
        // Nothing to check, as relates to the logged user
        return receiptPrinterSettingsService.belongsToTheLoggedUser(id);
    }

    @Override
    public List<ReceiptPrinterSettings> list() {
        boolean hasViewPermission = permissionService
                .permission()
                .member(MemberPermission.PREFERENCES_MANAGE_RECEIPT_PRINTER_SETTINGS)
                .operator(MemberPermission.PREFERENCES_MANAGE_RECEIPT_PRINTER_SETTINGS)
                .hasPermission();
        if (!hasViewPermission) {
            return Collections.emptyList();
        }
        return receiptPrinterSettingsService.list();
    }

    @Override
    public ReceiptPrinterSettings load(final Long id) {
        ReceiptPrinterSettings receiptPrinterSettings = receiptPrinterSettingsService.load(id);
        checkView(receiptPrinterSettings);
        return receiptPrinterSettings;
    }

    @Override
    public void remove(final Long id) {
        ReceiptPrinterSettings receiptPrinterSettings = receiptPrinterSettingsService.load(id);
        checkManagement(receiptPrinterSettings);
        receiptPrinterSettingsService.remove(id);
    }

    @Override
    public ReceiptPrinterSettings save(final ReceiptPrinterSettings receiptPrinterSettings) {
        Member member = receiptPrinterSettings.getMember();
        if (member == null) {
            throw new ValidationException();
        }
        checkManagement(receiptPrinterSettings);
        return receiptPrinterSettingsService.save(receiptPrinterSettings);
    }

    public void setReceiptPrinterSettingsServiceLocal(final ReceiptPrinterSettingsServiceLocal receiptPrinterSettingsService) {
        this.receiptPrinterSettingsService = receiptPrinterSettingsService;
    }

    @Override
    public void validate(final ReceiptPrinterSettings receiptPrinterSettings) throws ValidationException {
        // No permission to check on validate
        receiptPrinterSettingsService.validate(receiptPrinterSettings);
    }

    private void checkManagement(final ReceiptPrinterSettings receiptPrinterSettings) {
        permissionService
                .permission(receiptPrinterSettings.getMember())
                .member(MemberPermission.PREFERENCES_MANAGE_RECEIPT_PRINTER_SETTINGS)
                .check();
    }

    private void checkView(final ReceiptPrinterSettings receiptPrinterSettings) {
        permissionService
                .permission(receiptPrinterSettings.getMember())
                .member(MemberPermission.PREFERENCES_MANAGE_RECEIPT_PRINTER_SETTINGS)
                .operator(MemberPermission.PREFERENCES_MANAGE_RECEIPT_PRINTER_SETTINGS)
                .check();
    }

}
