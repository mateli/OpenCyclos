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

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccountDetailsVO;
import nl.strohalm.cyclos.services.Service;

/**
 * Service interface for External Accounts
 * @author Lucas Geiss
 */
public interface ExternalAccountService extends Service {
    /**
     * Overview external accounts
     */
    public List<ExternalAccountDetailsVO> externalAccountOverview();

    /**
     * Loads a ExternalAccount by id
     */
    ExternalAccount load(Long id, Relationship... fetch);

    /**
     * Removes the specified External Account
     */
    int remove(Long... ids);

    /**
     * Saves the External Account
     */
    ExternalAccount save(ExternalAccount externalAccount);

    /**
     * Search External Accounts related system account type
     */
    List<ExternalAccount> search();

    /**
     * Validate the specified External Account
     */
    void validate(ExternalAccount externalAccount);

}
