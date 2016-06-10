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

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.adInterests.AdInterest;
import nl.strohalm.cyclos.entities.members.adInterests.AdInterestQuery;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for ad interests
 * @author jefferson
 */
public interface AdInterestService extends Service {
    /**
     * Loads the ad interest, fetching the specified relationships.
     * @return The ads loaded
     */
    AdInterest load(Long id, Relationship... fetch);

    /**
     * Remove the given ad interests.
     */
    int remove(Long[] ids);

    /**
     * Saves the specified ad interest
     */
    AdInterest save(AdInterest adInterest);

    /**
     * Search the existing ad interests based on the AdInterestQuery object
     * @return a list of ad interests
     */
    List<AdInterest> search(AdInterestQuery query);

    /**
     * Validates the specified ad interest
     * @throws ValidationException if validation fails.
     */
    void validate(AdInterest adInterest) throws ValidationException;

}
