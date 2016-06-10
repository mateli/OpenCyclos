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
package nl.strohalm.cyclos.services.accounts.external.filemapping;

import nl.strohalm.cyclos.dao.FetchDAO;
import nl.strohalm.cyclos.dao.accounts.external.ExternalAccountDAO;
import nl.strohalm.cyclos.dao.accounts.external.filemapping.FileMappingDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.CSVFileMapping;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMapping;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMappingWithFields;
import nl.strohalm.cyclos.utils.transactionimport.TransactionFileImport;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Service implementation for file mappings
 * @author Jefferson Magno
 */
public class FileMappingServiceImpl implements FileMappingServiceLocal {

    /**
     * Validates the file mapping before saving. If the number have a fixed amount of places, the number of decimal places is required if the number
     * is based on a decimal separator, it is required
     * @author jefferson
     */
    public class DecimalFormatValidation implements GeneralValidation {

        private static final long serialVersionUID = 7246850448052192489L;

        @Override
        public ValidationError validate(final Object object) {
            final CSVFileMapping fileMapping = (CSVFileMapping) object;

            if (fileMapping.getNumberFormat() == FileMappingWithFields.NumberFormat.FIXED_POSITION) {
                if (fileMapping.getDecimalPlaces() == null) {
                    return new ValidationError("fileMapping.decimalPlaces.error.required");
                }
            } else {
                if (fileMapping.getDecimalSeparator() == null) {
                    return new ValidationError("fileMapping.decimalSeparator.error.required");
                }
            }
            return null;
        }
    }

    private ExternalAccountDAO externalAccountDao;
    private FetchDAO           fetchDao;
    private FileMappingDAO     fileMappingDao;

    @Override
    public FileMapping load(final Long id, final Relationship... fetch) {
        return fileMappingDao.load(id, fetch);
    }

    @Override
    public int remove(final Long... ids) {
        return fileMappingDao.delete(ids);
    }

    @Override
    public void reset(final FileMapping fileMapping) {
        // Clean the file mapping reference on the external account
        final ExternalAccount externalAccount = fileMapping.getAccount();
        externalAccount.setFileMapping(null);
        externalAccountDao.update(externalAccount);

        // Delete the file mapping from the database
        remove(fileMapping.getId());
    }

    @Override
    public FileMapping save(FileMapping fileMapping) {
        validate(fileMapping);
        if (fileMapping.isTransient()) {
            // Insert the file mapping
            fileMapping = fileMappingDao.insert(fileMapping);

            // Set the file mapping reference into the external account and update it
            final ExternalAccount externalAccount = fetchDao.fetch(fileMapping.getAccount());
            externalAccount.setFileMapping(fileMapping);
            externalAccountDao.update(externalAccount);
        } else {
            fileMapping = fileMappingDao.update(fileMapping);
        }
        return fileMapping;
    }

    public void setExternalAccountDao(final ExternalAccountDAO externalAccountDao) {
        this.externalAccountDao = externalAccountDao;
    }

    public void setFetchDao(final FetchDAO fetchDao) {
        this.fetchDao = fetchDao;
    }

    public void setFileMappingDao(final FileMappingDAO fileMappingDao) {
        this.fileMappingDao = fileMappingDao;
    }

    @Override
    public void validate(final FileMapping fileMapping) {
        Validator validator = null;
        if (fileMapping.getNature() == FileMapping.Nature.CSV) {
            validator = getCSVValidator();
        } else {
            validator = getCustomValidator();
        }
        validator.validate(fileMapping);
    }

    private Validator getCSVValidator() {
        final Validator csvValidator = new Validator("fileMapping");
        csvValidator.property("account").required();
        csvValidator.property("numberFormat").required();
        csvValidator.general(new DecimalFormatValidation());
        csvValidator.property("negativeAmountValue").required().maxLength(50);
        csvValidator.property("dateFormat").required();
        csvValidator.property("stringQuote").required().maxLength(1);
        csvValidator.property("columnSeparator").required().maxLength(1);
        csvValidator.property("headerLines").required().positive();
        return csvValidator;
    }

    private Validator getCustomValidator() {
        final Validator customValidator = new Validator("fileMapping");
        customValidator.property("account").required();
        customValidator.property("className").required().instanceOf(TransactionFileImport.class);
        return customValidator;
    }

}
