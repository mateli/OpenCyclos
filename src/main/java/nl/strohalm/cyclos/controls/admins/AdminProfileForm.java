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
package nl.strohalm.cyclos.controls.admins;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form for an admin profile
 * @author luis
 */
public class AdminProfileForm extends BaseBindingForm {

    private static final long serialVersionUID = -16321133527510705L;
    private long              adminId;

    public AdminProfileForm() {
        setAdmin("user", new MapBean("id", "username"));
        setAdmin("group", new MapBean("id", "name"));
        setAdmin("customValues", new MapBean(true, "field", "value", "hidden"));
    }

    public Map<String, Object> getAdmin() {
        return values;
    }

    public Object getAdmin(final String key) {
        return values.get(key);
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdmin(final Map<String, Object> map) {
        values = map;
    }

    public void setAdmin(final String key, final Object value) {
        values.put(key, value);
    }

    public void setAdminId(final long memberId) {
        adminId = memberId;
    }
}
