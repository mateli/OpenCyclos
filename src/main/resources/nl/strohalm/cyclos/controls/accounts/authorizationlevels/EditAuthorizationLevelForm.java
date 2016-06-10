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
package nl.strohalm.cyclos.controls.accounts.authorizationlevels;

import java.util.Collections;
import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * Form used to edit an authorization level
 * @author Jefferson Magno
 */
public class EditAuthorizationLevelForm extends BaseBindingForm {

    private static final long serialVersionUID = 9094195700154068042L;
    private long              authorizationLevelId;

    public EditAuthorizationLevelForm() {
        setAuthorizationLevel("adminGroups", Collections.emptyList());
    }

    public Map<String, Object> getAuthorizationLevel() {
        return values;
    }

    public Object getAuthorizationLevel(final String key) {
        return values.get(key);
    }

    public long getAuthorizationLevelId() {
        return authorizationLevelId;
    }

    public void setAuthorizationLevel(final Map<String, Object> map) {
        values = map;
    }

    public void setAuthorizationLevel(final String key, final Object value) {
        values.put(key, value);
    }

    public void setAuthorizationLevelId(final long authorizationLevelId) {
        this.authorizationLevelId = authorizationLevelId;
    }

}
