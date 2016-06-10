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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee.ARateRelation;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.ChargeType;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Nature;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Subject;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFeeQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.webservices.model.TransactionFeeVO;

/**
 * Service interface for transaction fees
 * @author luis
 */
public interface TransactionFeeService extends Service {

    /**
     * Returns the possible charge types for a fee based on the transfer type to which the fee is applied and the nature of the fee.
     * @param originalTransferType
     * @param feeNature
     * @return
     */
    Collection<ChargeType> getPossibleChargeType(TransferType originalTransferType, Nature feeNature);

    /**
     * Given the nature of a transaction fee and the transfer type to which the fee is applied, returns the possible subjects
     * @param originalTransferType
     * @param nature
     * @return
     */
    Collection<Subject> getPossibleSubjects(TransferType originalTransferType, Nature nature);

    /**
     * Returns a List<TransactionFeeVO> with the information of the given transactionFees
     */
    List<TransactionFeeVO> getTransactionFeeVOs(TransactionFeePreviewDTO preview);

    /**
     * Loads the specified transaction fee, with the specified fetch
     */
    TransactionFee load(Long id, Relationship... fetch);

    /**
     * Previews which fees would be applied to a transaction of the given type and amount
     */
    TransactionFeePreviewDTO preview(AccountOwner from, AccountOwner to, TransferType transferType, BigDecimal amount);

    /**
     * Previews which fees would be applied if accepting the given invoice
     */
    TransactionFeePreviewDTO preview(Invoice invoice);

    /**
     * Removes the specified transaction fees, unless any of them has already been charged
     * @return The number of removed transaction fees
     */
    int remove(Long... ids);

    /**
     * Saves a broker commission, returning the resulting object
     */
    BrokerCommission save(BrokerCommission brokerCommission);

    /**
     * Saves a simple transaction fee, returning the resulting object
     */
    SimpleTransactionFee save(SimpleTransactionFee transactionFee, ARateRelation aRateRelation);

    /**
     * Searches existing transaction fees, matching the query
     */
    List<? extends TransactionFee> search(TransactionFeeQuery query);

    /**
     * Returns a list of generated transfer types allowed for the given fee.
     * @param fee Fee
     * @param allowAnyAccount
     * @param onlyFromSystem If onlyFromSystem is true, the origin of the generated transfer types will be System.
     * @return
     */
    List<TransferType> searchGeneratedTransferTypes(TransactionFee fee, boolean allowAnyAccount, boolean onlyFromSystem);

    /**
     * Validate the specified broker commission
     */
    void validate(BrokerCommission brokerCommission);

    /**
     * Validate the specified transaction fee
     */
    void validate(SimpleTransactionFee transactionFee, ARateRelation aRateRelation);

}
