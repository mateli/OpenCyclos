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

import nl.strohalm.cyclos.dao.members.ReceiptPrinterSettingsDAO;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.printsettings.ReceiptPrinterSettings;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation for {@link ReceiptPrinterSettingsService}
 * 
 * @author luis
 */
public class ReceiptPrinterSettingsServiceImpl implements ReceiptPrinterSettingsServiceLocal {

    private ReceiptPrinterSettingsDAO receiptPrinterSettingsDao;
    private Validator                 validator;

    @Override
    public boolean belongsToTheLoggedUser(final Long id) {
        if (id == null) {
            return false;
        }
        try {
            ReceiptPrinterSettings settings = receiptPrinterSettingsDao.load(id, ReceiptPrinterSettings.Relationships.MEMBER);
            return settings.getMember().equals(LoggedUser.member());
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    public List<ReceiptPrinterSettings> list() {
        Member member = LoggedUser.hasUser() ? LoggedUser.member() : null;
        return receiptPrinterSettingsDao.listByMember(member);
    }

    @Override
    public ReceiptPrinterSettings load(final Long id) {
        return receiptPrinterSettingsDao.load(id);
    }

    @Override
    public void remove(final Long id) {
        receiptPrinterSettingsDao.delete(id);
    }

    @Override
    public ReceiptPrinterSettings save(final ReceiptPrinterSettings receiptPrinterSettings) {
        validate(receiptPrinterSettings);
        if (receiptPrinterSettings.isPersistent()) {
            return receiptPrinterSettingsDao.update(receiptPrinterSettings);
        } else {
            return receiptPrinterSettingsDao.insert(receiptPrinterSettings);
        }
    }

    public void setReceiptPrinterSettingsDao(final ReceiptPrinterSettingsDAO receiptPrinterSettingsDao) {
        this.receiptPrinterSettingsDao = receiptPrinterSettingsDao;
    }

    @Override
    public void validate(final ReceiptPrinterSettings receiptPrinterSettings) throws ValidationException {
        getValidator().validate(receiptPrinterSettings);
    }

    private Validator getValidator() {
        if (validator == null) {
            Validator validator = new Validator("receiptPrinterSettings");
            validator.property("member").required();
            validator.property("name").required().maxLength(100);
            validator.property("printerName").required().maxLength(100);
            validator.property("beginOfDocCommand").maxLength(100);
            validator.property("endOfDocCommand").maxLength(100);
            validator.property("paymentAdditionalMessage").maxLength(500);
            this.validator = validator;
        }
        return validator;
    }

}
