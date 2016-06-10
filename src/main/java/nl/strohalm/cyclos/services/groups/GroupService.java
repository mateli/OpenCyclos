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
package nl.strohalm.cyclos.services.groups;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.exceptions.UnexpectedEntityException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.SystemGroup;
import nl.strohalm.cyclos.services.Service;

/**
 * Service interface for groups. The service is use to control group operations like: list, validate, remove, check permissions etc.
 * @author rafael
 * @author luis
 */
public interface GroupService extends Service {

    /**
     * Counts the pending initial credits for the given group and account type
     */
    int countPendingAccounts(MemberGroup group, MemberAccountType accountType);

    /**
     * Finds a group by it's login page name
     */
    SystemGroup findByLoginPageName(String loginPageName);

    /**
     * Lists the possible initial member groups
     */
    List<? extends MemberGroup> getPossibleInitialGroups(GroupFilter groupFilter);

    /**
     * Inserts the specified group
     */
    <G extends Group> G insert(G group, G baseGroup);

    /**
     * Inserts an instance of group settings
     * @throws UnexpectedEntityException When the account is already related to the group
     */
    MemberGroupAccountSettings insertAccountSettings(MemberGroupAccountSettings settings) throws UnexpectedEntityException;

    <T extends Group> Collection<T> load(Collection<Long> ids, Relationship... fetch);

    /**
     * Loads the group, fetching the specified relationships
     * @return The group loaded
     */
    <T extends Group> T load(Long id, Relationship... fetch);

    /**
     * Loads an instance of group settings
     */
    MemberGroupAccountSettings loadAccountSettings(long groupId, long accountTypeId, Relationship... fetch);

    <T extends Group> T reload(Long id, Relationship... fetch);

    /**
     * Removes the specified group
     * @throws EntityNotFoundException The specified group does not exists
     */
    void remove(Long id) throws EntityNotFoundException;

    /**
     * Removes a relationship between a member group and a member accountType. All accounts of group members should be marked as inactive.
     */
    void removeAccountTypeRelationship(MemberGroup group, MemberAccountType type);

    /**
     * Searches for groups that matches the give query
     */
    List<? extends Group> search(GroupQuery query);

    /**
     * Sets the group permissions
     */
    <G extends Group> G setPermissions(GroupPermissionsDTO<G> permissions);

    /**
     * Saves the specified group. The forceMembersToAcceptAgreement is only used for member groups, and means that if there is an agreement, all
     * members will be forced to (re-)accept it
     */
    <G extends Group> G update(G group, boolean forceMembersToAcceptAgreement);

    /**
     * Updates an instance of group settings
     */
    MemberGroupAccountSettings updateAccountSettings(MemberGroupAccountSettings settings, boolean updateAccountLimits);

    /**
     * Check if the member group have access to a channel that uses pin
     */
    boolean usesPin(MemberGroup group);

    /**
     * Validates the group
     */
    void validate(Group group);

    /**
     * Validates the group settings
     */
    void validate(MemberGroupAccountSettings settings);

}
