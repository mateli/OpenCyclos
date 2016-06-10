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
package nl.strohalm.cyclos.utils;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.PendingEmailChange;

/**
 * This class can generate links for specific
 * @author luis
 */
public interface LinkGenerator {

    /**
     * Generates a link for the application root address
     */
    public String generateForApplicationRoot(Element element);

    /**
     * Generates a link for details of a given entity
     */
    public String generateLinkFor(Element element, Entity entity);

    /**
     * Returns a link for the mail change validation using the given validation key
     */
    public String generateLinkForMailChangeValidation(PendingEmailChange change);

    /**
     * Returns a link for the mail validation using the given validation key
     */
    public String generateLinkForMailValidation(MemberGroup group, String key);

    /**
     * Returns an url for validating an e-mail change using the given validation key
     */
    public String getMailChangeValidationUrl(PendingEmailChange change);

    /**
     * Returns an url for validating an e-mail using the given validation key
     */
    public String getMailValidationUrl(MemberGroup group, String key);

    /**
     * Returns the url for the application root for the given element
     */
    public String getRootUrl(Element element);

}
