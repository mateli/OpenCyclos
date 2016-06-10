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
package nl.strohalm.cyclos.services.accounts.pos;

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.accounts.pos.PosQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.Service;

/**
 * Service interface for POS
 * @author rodrigo
 */
public interface PosService extends Service {

    /**
     * Assign the POS to a given member
     * @param posId
     * @param member
     */

    public Pos assignPos(final Member member, Long posId);

    /**
     * Delete the POS
     */
    public void deletePos(Long... ids);

    /**
     * Discard the POS - cannot be used again
     */
    public Pos discardPos(Long posId);

    /**
     * Loads a POS, fetching the specified relationships
     */
    public Pos load(Long id, Relationship... fetch);

    /**
     * Loads a POS, fetching the specified relationships
     */
    public Pos loadByPosId(String posId, Relationship... fetch);

    /**
     * Persist the POS
     */
    public Pos save(Pos pos);

    /**
     * Search for POS based on the given query
     * @param query
     */
    public List<Pos> search(PosQuery query);

    /**
     * Unassigns the POS from the member it was assigned
     */
    public Pos unassignPos(Long posId);

    /**
     * Validate the POS been persisted
     */
    public void validate(final Pos pos);

}
