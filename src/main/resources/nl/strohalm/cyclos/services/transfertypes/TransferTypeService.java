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
package nl.strohalm.cyclos.services.transfertypes;

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.PaymentDirection;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.webservices.model.TransferTypeVO;

/**
 * Service interface for transfer types
 * @author luis
 */
public interface TransferTypeService extends Service {

    /**
     * Returns the "authorizable" transfer types that are allowed to the logged user.
     * @return
     */
    List<TransferType> getAuthorizableTTs();

    /**
     * Returns the allowed payment and self payment transfer types for the logged user. The logged user must be an administrator.
     */
    List<TransferType> getPaymentAndSelfPaymentTTs();

    /**
     * Possible transfer types of the given account type and payment direction
     * @param accountType
     * @param paymentDirection
     * @return
     */
    List<TransferType> getPosibleTTsForAccountFee(MemberAccountType accountType, PaymentDirection paymentDirection);

    /**
     * Returns a transferTypeVO for the given transfer type id. If the extended flag is true, a DetailedTransferTypeVO is returned.
     */
    TransferTypeVO getTransferTypeVO(Long transferTypeId, boolean extended);

    /**
     * get a list with transferTypes having A-rated Transaction Fees
     */
    List<TransferType> listARatedTTs();

    /**
     * Loads the specified transfer type, fetching the specified relationships
     */
    TransferType load(Long id, Relationship... fetch);

    /**
     * Removes the specified transfer types
     */
    int remove(Long... ids);

    /**
     * Saves the transfer type, returning the resulting object
     */
    TransferType save(TransferType transferType);

    /**
     * Search the existing transfer types
     */
    List<TransferType> search(TransferTypeQuery query);

    /**
     * Validate the specified transfer type
     */
    void validate(TransferType transferType);
}
