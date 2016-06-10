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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import nl.strohalm.cyclos.entities.settings.LocalSettings;

/**
 * This class represents the owner for system accounts
 * @author luis
 */
public final class SystemAccountOwner implements AccountOwner, Serializable {

    private static final long               serialVersionUID = 2317168821784165966L;
    private static final SystemAccountOwner INSTANCE         = new SystemAccountOwner();

    public static SystemAccountOwner instance() {
        return INSTANCE;
    }

    private SystemAccountOwner() {
    }

    public Map<String, Object> getVariableValues(final LocalSettings localSettings) {
        final Map<String, Object> values = new HashMap<String, Object>();
        values.put("login", localSettings.getApplicationName());
        values.put("name", localSettings.getApplicationUsername());
        values.put("member", localSettings.getApplicationUsername());
        return values;
    }

    @Override
    public String toString() {
        return "System";
    }

    /**
     * Ensure singleton
     */
    private Object readResolve() {
        return INSTANCE;
    }
}
