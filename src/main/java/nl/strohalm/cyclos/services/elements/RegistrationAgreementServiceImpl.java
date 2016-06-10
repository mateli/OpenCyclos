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
package nl.strohalm.cyclos.services.elements;

import java.util.List;

import nl.strohalm.cyclos.dao.members.RegistrationAgreementDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.RegistrationAgreement;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation for {@link RegistrationAgreementService}
 * 
 * @author luis
 */
public class RegistrationAgreementServiceImpl implements RegistrationAgreementServiceLocal {

    private RegistrationAgreementDAO registrationAgreementDao;

    public List<RegistrationAgreement> listAll() {
        return registrationAgreementDao.listAll();
    }

    public RegistrationAgreement load(final Long id, final Relationship... fetch) {
        return registrationAgreementDao.load(id, fetch);
    }

    public boolean remove(final Long id) {
        return registrationAgreementDao.delete(id) == 1;
    }

    public RegistrationAgreement save(final RegistrationAgreement registrationAgreement) {
        validate(registrationAgreement);
        if (registrationAgreement.isTransient()) {
            return registrationAgreementDao.insert(registrationAgreement);
        } else {
            return registrationAgreementDao.update(registrationAgreement);
        }
    }

    public void setRegistrationAgreementDao(final RegistrationAgreementDAO registrationAgreementDao) {
        this.registrationAgreementDao = registrationAgreementDao;
    }

    public void validate(final RegistrationAgreement registrationAgreement) throws ValidationException {
        getValidator().validate(registrationAgreement);
    }

    private Validator getValidator() {
        final Validator validator = new Validator("registrationAgreement");
        validator.property("name").required().maxLength(50);
        validator.property("contents").required();
        return validator;
    }

}
