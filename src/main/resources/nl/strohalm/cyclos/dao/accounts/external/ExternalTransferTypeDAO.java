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
package nl.strohalm.cyclos.dao.accounts.external;

import java.util.List;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.ExternalTransferType;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;

public interface ExternalTransferTypeDAO extends BaseDAO<ExternalTransferType>, InsertableDAO<ExternalTransferType>, UpdatableDAO<ExternalTransferType>, DeletableDAO<ExternalTransferType> {

    /**
     * Lists all External Transfer Types, ordering results by name
     */
    List<ExternalTransferType> listAll();

    /**
     * Lists External Transfer Types By Account, ordering results by name
     */
    List<ExternalTransferType> listByAccount(ExternalAccount account);

    /**
     * Loads an external account type by account and code
     */
    ExternalTransferType load(ExternalAccount account, String code, Relationship[] fetch) throws EntityNotFoundException;

}
