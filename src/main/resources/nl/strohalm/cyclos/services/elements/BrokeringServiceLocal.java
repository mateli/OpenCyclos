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

import java.util.Calendar;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Local interface. It must be used only from other services.
 */
public interface BrokeringServiceLocal extends BrokeringService {

    public Brokering getActiveBrokering(Member member);

    /**
     * Create a new brokering relation. Intented to be called when a broker registers a member.
     */
    Brokering create(Member broker, Member brokered);

    /**
     * Loads the brokering by id, fetching the specified relationships
     */
    Brokering load(Long id, Relationship... fetch);

    /**
     * Marks the given brokering relationship as finished
     * @throws UnexpectedEntityException When the brokered is no longer active
     */
    Brokering remove(Brokering brokering, String remark) throws UnexpectedEntityException;

    /**
     * Removes all brokering relations that are expired
     */
    void removeExpiredBrokerings(Calendar time);

    /**
     * Validates the specified brokering
     * @param brokering Brokering to be validated
     * @throws ValidationException if validation fails.
     */
    void validate(Brokering brokering) throws ValidationException;

}
