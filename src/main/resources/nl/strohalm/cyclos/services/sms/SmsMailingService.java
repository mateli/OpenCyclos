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
package nl.strohalm.cyclos.services.sms;

import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.sms.SmsMailing;
import nl.strohalm.cyclos.entities.sms.SmsMailingQuery;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for SMS mailings
 * @author luis
 */
public interface SmsMailingService extends Service {

    /**
     * Returns a map with the variables that can be used in the SMS mailing text. The elements of the map contains name of the variable and the
     * internal name. e.g: [Mobile Phone, mobilePhone],[User name, username] etc... The resulting variables are found based on the given groups.
     */
    Map<String, String> getSmsTextVariables(List<MemberGroup> groups);

    /**
     * Returns a map with the variables that can be used in the SMS mailing text. The elements of the map contains name of the variable and the
     * internal name. e.g: [Mobile Phone, mobilePhone],[User name, username] etc... The resulting variables are found based on the given member.
     */
    Map<String, String> getSmsTextVariables(Member member);

    /**
     * Search for SMS mailings
     * @return a list of SMS logs
     */
    List<SmsMailing> search(SmsMailingQuery query);

    /**
     * Sends a free/paid SMS mailing to groups or individual members
     */
    SmsMailing send(SmsMailing smsMailing);

    /**
     * Validates the SMS mailing
     */
    void validate(final SmsMailing smsMailing, boolean isMemberRequired) throws ValidationException;

}
