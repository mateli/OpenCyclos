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

import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.adInterests.AdInterest;
import nl.strohalm.cyclos.entities.members.adInterests.AdInterestQuery;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link AdInterestService}
 * 
 * @author Rinke
 */
public class AdInterestServiceSecurity extends BaseServiceSecurity implements AdInterestService {

    private AdInterestServiceLocal adInterestService;

    @Override
    public AdInterest load(final Long id, final Relationship... fetch) {
        // called by AdInterestAction, is definitely needed there
        AdInterest adInterest = checkManages(id, fetch);
        return adInterest;
    }

    @Override
    public int remove(final Long[] ids) {
        for (Long id : ids) {
            checkManages(id);
        }
        return adInterestService.remove(ids);
    }

    @Override
    public AdInterest save(final AdInterest adInterest) {
        checkManages(adInterest.getOwner());
        return adInterestService.save(adInterest);
    }

    @Override
    public List<AdInterest> search(final AdInterestQuery query) {
        checkManages(query.getOwner());
        return adInterestService.search(query);
    }

    public void setAdInterestServiceLocal(final AdInterestServiceLocal adInterestService) {
        this.adInterestService = adInterestService;
    }

    @Override
    public void validate(final AdInterest adInterest) throws ValidationException {
        // no permission on validation
        adInterestService.validate(adInterest);
    }

    /**
     * private method checks if this element can be managed by its owner
     * @param id the id of the AdInterest
     * @return the fetched AdInterest; has the member initialized
     * 
     */
    private AdInterest checkManages(final Long id, final Relationship... fetch) {
        AdInterest adInterest = adInterestService.load(id, fetch);
        checkManages(adInterest.getOwner());
        return adInterest;
    }

    /**
     * checks the member on MemberPermission.PREFERENCES_MANAGE_AD_INTERESTS
     * @param member
     */
    private void checkManages(final Member member) {
        if (member == null) {
            throw new PermissionDeniedException();
        }
        // operators can never manage this
        permissionService.permission(member).member(MemberPermission.PREFERENCES_MANAGE_AD_INTERESTS).check();
    }
}
