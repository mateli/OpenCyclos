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
package nl.strohalm.cyclos.services.loangroups;

import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroupQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.loangroups.exceptions.MemberAlreadyInListException;
import nl.strohalm.cyclos.services.loangroups.exceptions.MemberNotInListException;

/**
 * Service interface for (micro finance) loan groups.
 * @author luis
 */
public interface LoanGroupService extends Service {

    /**
     * Adds a member to the loan group
     * @throws MemberAlreadyInListException When the member is already on the given loan group
     */
    void addMember(Member member, LoanGroup loanGroup) throws MemberAlreadyInListException;

    /**
     * Loads a loan group
     */
    LoanGroup load(Long id, Relationship... fetch);

    /**
     * Removes the loan groups, returning the number of removed objects
     */
    int remove(Long... ids);

    /**
     * Removes a member to the loan group
     * @throws MemberNotInListException When the member isn't already on the given loan group
     */
    void removeMember(Member member, LoanGroup loanGroup) throws MemberNotInListException;

    /**
     * Saves the loan group, returning the resulting object
     */
    LoanGroup save(LoanGroup loanGroup);

    /**
     * Searches the loan groups defined in the system
     */
    List<LoanGroup> search(LoanGroupQuery query);

    /**
     * Validates the specified loan group
     */
    void validate(LoanGroup loanGroup);

}
