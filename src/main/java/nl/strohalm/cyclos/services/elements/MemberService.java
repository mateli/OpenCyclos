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

import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.services.sms.ISmsContext;
import nl.strohalm.cyclos.services.sms.exceptions.SmsContextInitializationException;
import nl.strohalm.cyclos.webservices.members.FullTextMemberSearchParameters;
import nl.strohalm.cyclos.webservices.members.MemberResultPage;
import nl.strohalm.cyclos.webservices.model.MemberVO;
import nl.strohalm.cyclos.webservices.model.MyProfileVO;

/**
 * Service interface for members. This service is used to control all member operations that are directly related to the member like loans,
 * permissions and groups.
 * @author rafael
 * @author luis
 */
public interface MemberService extends Service {

    /**
     * Returns statistical data regarding the activities of the specified member.
     */
    ActivitiesVO getActivities(Member member);

    /**
     * 
     * @param params
     * @return
     */
    MemberResultPage getMemberResultPage(FullTextMemberSearchParameters params);

    /**
     * Converts a member to VO
     */
    MemberVO getMemberVO(final Member member, boolean useMemberFields, final boolean useImages);

    /**
     * Converts the given member into a {@link MyProfileVO}
     */
    MyProfileVO getMyProfileVO(Member member);

    /**
     * Returns the visible quick access items for the logged member or operator
     */
    QuickAccessVO getQuickAccess();

    /**
     * Returns the sms context for the given member
     */
    ISmsContext getSmsContext(Member member) throws SmsContextInitializationException;

    /**
     * Returns the status for the logged member or operator
     */
    MemberStatusVO getStatus();

    /**
     * Loads a member by its id or by its principal
     */
    Member loadByIdOrPrincipal(final Long id, final String principalType, final String principal);
}
