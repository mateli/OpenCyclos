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
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContractQuery;
import nl.strohalm.cyclos.entities.members.brokerings.DefaultBrokerCommission;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for broker commissions
 * @author Jefferson Magno
 */
public interface CommissionService extends Service {

    /**
     * A member accepts a pending broker commission contract. The status of the contract is changed to "ACTIVE"
     */
    BrokerCommissionContract acceptBrokerCommissionContract(Long brokerCommissionContractId);

    /**
     * A broker or admin cancels a broker commission contract. The status of the contract is changed to "CANCELLED"
     */
    BrokerCommissionContract cancelBrokerCommissionContract(Long brokerCommissionContractId);

    /**
     * A member denies a pending broker commission contract. The status of the contract is changed to "DENIED"
     */
    BrokerCommissionContract denyBrokerCommissionContract(Long brokerCommissionContractId);

    /**
     * Returns a list of charge status for broker commissions related to a member
     */
    List<CommissionChargeStatusDTO> getCommissionChargeStatus(final Member member);

    /**
     * Returns true if the logged user has at least one commission contract
     * @return
     */
    boolean hasBrokerCommissionContracts();

    /**
     * Returns the possible {@link BrokerCommission}s which can be used for a new contract, given that the logged user is the broker
     */
    List<BrokerCommission> listPossibleCommissionsForNewContract(Member brokered);

    /**
     * Loads a broker commission contraction, fetching the specified relationships
     */
    BrokerCommissionContract loadBrokerCommissionContract(Long id, Relationship... fetch);

    /**
     * Loads the default broker commissions for the given broker
     */
    List<DefaultBrokerCommission> loadDefaultBrokerCommissions(Member broker, Relationship... fetch);

    /**
     * Removes the specified broker commission contracts
     * @return The number of removed broker commission contracts
     */
    int removeBrokerCommissionContracts(Long... ids);

    /**
     * Saves the broker commission contract
     */
    BrokerCommissionContract saveBrokerCommissionContract(BrokerCommissionContract brokerCommissionContract);

    /**
     * Saves the default broker commissions, returning the updated instances
     */
    List<DefaultBrokerCommission> saveDefaultBrokerCommissions(List<DefaultBrokerCommission> commissions);

    /**
     * Searchs for broker commission contracts
     */
    List<BrokerCommissionContract> searchBrokerCommissionContracts(BrokerCommissionContractQuery query);

    /**
     * Sets the default broker commission to 0, close the broker commission contracts and close the brokering commission statuses of the broker
     */
    void stopCommissions(final BrokerCommission brokerCommission, final Member broker, final boolean removeDefaulBrokerCommission);

    /**
     * Suspend the default broker commission and suspend broker commission contracts
     */
    void suspendCommissions(final BrokerCommission brokerCommission, final Member broker);

    /**
     * Unsuspend the default broker commission and unsuspend broker commission contracts
     */
    void unsuspendCommissions(final BrokerCommission brokerCommission, final Member broker);

    /**
     * Validates the specified broker commission contract
     * @throws ValidationException if validation fails.
     */
    void validateBrokerCommissionContract(BrokerCommissionContract brokerCommissionContract) throws ValidationException;

    /**
     * Validates the collection of default broker commissions before saving
     * @throws ValidationException if validation fails.
     */
    void validateDefaultBrokerCommissions(List<DefaultBrokerCommission> defaultBrokerCommissions) throws ValidationException;

}
