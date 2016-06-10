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
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.FullTextElementQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.RegisteredMember;
import nl.strohalm.cyclos.entities.members.RegistrationAgreement;
import nl.strohalm.cyclos.entities.services.ServiceClient;

/**
 * Local interface. It must be used only from other services.
 */
public interface ElementServiceLocal extends ElementService {

    /**
     * Returns true if the query values are allowed. Assigns some values to execute the query in a secured range of values.
     */
    boolean applyQueryRestrictions(FullTextElementQuery query);

    /**
     * Updates a member profile by web services
     */
    Member changeMemberProfileByWebService(ServiceClient client, Member member);

    /**
     * Marks all members in the given group as if they had accepted the given registration agreement
     */
    void createAgreementForAllMembers(RegistrationAgreement registrationAgreement, MemberGroup group);

    /**
     * Creates a member, through internal procedures (like imports)
     */
    Member insertMember(Member member, boolean ignoreActivationMail, boolean validatePassword);

    /**
     * Loads an operator user, fetching the specified relationships
     * @throws EntityNotFoundException When the given member and operatorUsername combination does not exist
     */
    OperatorUser loadOperatorUser(Member member, String operatorUsername, Relationship... fetch) throws EntityNotFoundException;

    /**
     * Changes the group for all members which has stayed for more than a given time period on a group
     */
    int processMembersExpirationForGroups(Calendar time);

    /**
     * Purges old pending public registrations (whose member haven't activated them after some time) and mail change validations
     */
    void purgeOldEmailValidations(Calendar time);

    /**
     * Register a member by Web Services
     */
    // FIXME - Don't forget that the client should be obtained by LoggedUser.serviceClient()
    RegisteredMember registerMemberByWebService(ServiceClient client, Member member, String remoteAddress);

}
