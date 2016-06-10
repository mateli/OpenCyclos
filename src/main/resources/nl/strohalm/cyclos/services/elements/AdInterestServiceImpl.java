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

import nl.strohalm.cyclos.dao.members.AdInterestDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.adInterests.AdInterest;
import nl.strohalm.cyclos.entities.members.adInterests.AdInterestQuery;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Service implementation for advertisements
 * @author luis
 */
public class AdInterestServiceImpl implements AdInterestServiceLocal {

    private AdInterestDAO adInterestDao;

    @Override
    public AdInterest load(final Long id, final Relationship... fetch) {
        return adInterestDao.load(id, fetch);
    }

    @Override
    public int remove(final Long[] ids) {
        return adInterestDao.delete(ids);
    }

    @Override
    public AdInterest save(AdInterest adInterest) {
        // It there is not a price range, set currency to null
        if (adInterest.getInitialPrice() == null && adInterest.getFinalPrice() == null) {
            adInterest.setCurrency(null);
        }

        // Validates before saving
        validate(adInterest);

        if (adInterest.isTransient()) {
            adInterest = adInterestDao.insert(adInterest);
        } else {
            adInterest = adInterestDao.update(adInterest);
        }
        return adInterest;
    }

    @Override
    public List<AdInterest> search(final AdInterestQuery query) {
        return adInterestDao.search(query);
    }

    public void setAdInterestDao(final AdInterestDAO adInterestDao) {
        this.adInterestDao = adInterestDao;
    }

    @Override
    public void validate(final AdInterest adInterest) throws ValidationException {
        getValidator().validate(adInterest);
    }

    private Validator getValidator() {
        final Validator validator = new Validator("adInterest");
        validator.property("title").required().maxLength(100);
        validator.property("type").required();
        validator.property("initialPrice").key("adInterest.priceRange").positive();
        validator.property("finalPrice").key("adInterest.priceRange").positiveNonZero();
        return validator;
    }
}
