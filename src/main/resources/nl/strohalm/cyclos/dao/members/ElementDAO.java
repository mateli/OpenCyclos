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
package nl.strohalm.cyclos.dao.members;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.dao.BaseDAO;
import nl.strohalm.cyclos.dao.DeletableDAO;
import nl.strohalm.cyclos.dao.IndexedDAO;
import nl.strohalm.cyclos.dao.InsertableDAO;
import nl.strohalm.cyclos.dao.UpdatableDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.DaoException;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.ElementQuery;
import nl.strohalm.cyclos.entities.members.FullTextElementQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.MemberQuery;
import nl.strohalm.cyclos.entities.members.RegistrationAgreement;
import nl.strohalm.cyclos.entities.members.RegistrationAgreementLog;
import nl.strohalm.cyclos.utils.Period;

/**
 * Data access object interface for elements
 * @author rafael
 */
public interface ElementDAO extends BaseDAO<Element>, InsertableDAO<Element>, UpdatableDAO<Element>, DeletableDAO<Element>, IndexedDAO<Element> {

    /**
     * Activates all inactive members on the given group
     */
    void activateMembersOfGroup(MemberGroup group);

    /**
     * Create a {@link RegistrationAgreementLog} for each member in the given group for the given agreement
     */
    void createAgreementForAllMembers(RegistrationAgreement registrationAgreement, MemberGroup group);

    /**
     * Search elements using a full-text query.
     */
    List<? extends Element> fullTextSearch(FullTextElementQuery params);

    /**
     * Counts, per group, the number of members in that group
     */
    Map<Long, Integer> getCountPerGroup(Collection<MemberGroup> groups);

    /**
     * Counts, per group, the number of members in that group at the given time point
     */
    Map<Long, Integer> getCountPerGroup(Collection<MemberGroup> groups, Calendar timePoint);

    /**
     * Returns the date of the first member activation of the system
     */
    Calendar getFirstMemberActivationDate();

    /**
     * Returns the number of new members through the years, during a date interval The key of the returned Map is the year The value of the returned
     * Map is the number of members
     */
    List<Number[]> getNewMembersCountThroughTheTime(Collection<? extends Group> groups, Period period) throws DaoException;

    /**
     * gets the number of members which were in the specified group at any moment during the specified period. Used by Activity Stats: gross Product,
     * number of transactions and % not trading for compare periods, Histogram, Single Period, and by Key dev stats: number of members
     * 
     * @param groups the set of groups in which the members must be counted.
     * @param period the period in which they should be part of any of the groups
     * @return an int indicating the number of members during that period in that set of groups
     */
    int getNumberOfMembersInGroupsInPeriod(Collection<? extends Group> groups, Period period);

    /**
     * Returns the number of removed members through the years, during a date interval. The count is specific for each year (not a sum with the number
     * of previous years). The key of the returned Map is the year The value of the returned Map is the number of members
     * @param groups limit the count for members of that groups
     * @throws DaoException
     */
    List<Number[]> getRemovedMembersCountThroughTheTime(Collection<? extends Group> groups, Period period) throws DaoException;

    /**
     * Returns whether the given member has value for the given field
     */
    boolean hasValueForField(Member member, MemberCustomField field);

    /**
     * Iterates over the members of the given groups either ordered by name or by no expected order
     */
    Iterator<Member> iterateMembers(boolean ordered, MemberGroup... groups);

    /**
     * Lists members that were changed to the given group (or created on it) before the given date
     */
    List<Member> listMembersRegisteredBeforeOnGroup(Calendar date, MemberGroup group);

    /**
     * Loads a member by custom field
     */
    Member loadByCustomField(MemberCustomField customField, String principal, Relationship[] fetch);

    /**
     * Loads an element by e-mail
     */
    Element loadByEmail(String email, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Removes the given channels from all members in the given group
     */
    void removeChannelsFromMembers(MemberGroup group, Collection<Channel> channels);

    /**
     * Search elements, ordering results by username. If no entity can be found, returns an empty list. If any exception is thrown by the underlying
     * implementation it should be wrapped by DaoException.
     */
    List<? extends Element> search(ElementQuery params) throws DaoException;

    /**
     * Search members in history in a point date; search in the remarks and groups, if needed, ordering results by username. If no entity can be
     * found, returns an empty list. If any exception is thrown by the underlying implementation it should be wrapped by DaoException.
     */
    List<Element> searchAtDate(MemberQuery query, Calendar date);

    /**
     * Search new elements in history in a period; search in the remarks and groups, if needed, ordering results by username. If no entity can be
     * found, returns an empty list. If any exception is thrown by the underlying implementation it should be wrapped by DaoException. Used by Stats
     * Key Dev > number new members
     */
    List<Element> searchHistoryNew(ElementQuery query);

    /**
     * Search the removed elements in history in a period; search in the remarks and groups, if needed, ordering results by username. If no entity can
     * be found, returns an empty list. If any exception is thrown by the underlying implementation it should be wrapped by DaoException. Used by
     * Stats Key Developments > number of (disappeared) members
     */
    List<Element> searchHistoryRemoved(ElementQuery query);
}
