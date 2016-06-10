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
package nl.strohalm.cyclos.entities.accounts;

import nl.strohalm.cyclos.utils.FormatObject;

/**
 * The parameters needed for a currency when A-rate is enabled.
 * 
 * @author Rinke
 * 
 */
public class ARateParameters extends InitializableRateParameters {

    private static final long serialVersionUID = -2829229672899471137L;

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(getId());
        result.append(" - initValue = ");
        result.append(FormatObject.formatObject(getInitValue()));
        result.append(", initDate = ");
        result.append(FormatObject.formatObject(getInitDate()));
        result.append(", creationValue = ");
        result.append(FormatObject.formatObject(getCreationValue()));
        return result.toString();
    }

}
