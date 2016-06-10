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
package nl.strohalm.cyclos.services.accounts.external;

import java.util.List;

import nl.strohalm.cyclos.dao.accounts.external.ExternalTransferTypeDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferType;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.utils.validation.Validator;

public class ExternalTransferTypeServiceImpl implements ExternalTransferTypeServiceLocal {

    private ExternalTransferTypeDAO externalTransferTypeDao;

    public List<ExternalTransferType> listAll() {
        return externalTransferTypeDao.listAll();
    }

    public List<ExternalTransferType> listByAccount(final ExternalAccount account) {
        return externalTransferTypeDao.listByAccount(account);
    }

    public ExternalTransferType load(final ExternalAccount account, final String code, final Relationship... fetch) throws EntityNotFoundException {
        return externalTransferTypeDao.load(account, code, fetch);
    }

    public ExternalTransferType load(final Long id, final Relationship... fetch) throws EntityNotFoundException {
        return externalTransferTypeDao.load(id, fetch);
    }

    public int remove(final Long... ids) {
        return externalTransferTypeDao.delete(ids);
    }

    public ExternalTransferType save(final ExternalTransferType externalTransferType) {
        validate(externalTransferType);
        if (externalTransferType.isTransient()) {
            return externalTransferTypeDao.insert(externalTransferType);
        } else {
            return externalTransferTypeDao.update(externalTransferType);
        }
    }

    public void setExternalTransferTypeDao(final ExternalTransferTypeDAO externalTransferTypeDao) {
        this.externalTransferTypeDao = externalTransferTypeDao;
    }

    public void validate(final ExternalTransferType externalTransferType) {
        getValidator().validate(externalTransferType);
    }

    private Validator getValidator() {
        final Validator validator = new Validator("externalTransferType");
        validator.property("name").required().maxLength(50);
        validator.property("account").required();
        validator.property("action").required();
        validator.property("code").required().maxLength(20);
        return validator;
    }

}
