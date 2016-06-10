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
package nl.strohalm.cyclos.services.infotexts;

import java.util.List;

import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.infotexts.InfoText;
import nl.strohalm.cyclos.entities.infotexts.InfoTextQuery;

/**
 * Local interface. It must be used only from other services.
 */
public interface InfoTextServiceLocal extends InfoTextService {
    public static final String INFO_TEXT_EMPTY_PROPERTY     = "infoText.empty.subject";
    public static final String INFO_TEXT_NOT_MATCH_PROPERTY = "infoText.nomatch.subject";

    /**
     * Gets the info text's subject associated with the alias parameter<br>
     * If the info text couldn't be found then returns the internationalized value for the key <code><b>infoText.nomatch.subject</b></code>.<br>
     * If the alias parameter is null then returns the internationalized value for the key <code><b>infoText.empty.subject</b></code>.
     * @param alias the info texts's alias to search for
     * @return the info text's subject according to the specified alias<br>
     */
    String getInfoTextSubject(String alias);

    /**
     * Loads an info text by alias without permission checks
     */
    InfoText loadByAliasForWebServices(String alias) throws EntityNotFoundException;

    /**
     * Loads an info text without permission checks
     */
    InfoText loadForWebServices(long id) throws EntityNotFoundException;

    /**
     * Searches for matching info texts without permission checks
     */
    List<InfoText> searchForWebServices(InfoTextQuery query);
}
