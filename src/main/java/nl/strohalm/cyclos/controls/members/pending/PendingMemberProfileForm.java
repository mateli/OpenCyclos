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
package nl.strohalm.cyclos.controls.members.pending;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to view / edit a pending member profile
 * 
 * @author luis
 */
public class PendingMemberProfileForm extends BaseBindingForm {

    private static final long serialVersionUID = -5787411015934118118L;

    public PendingMemberProfileForm() {
        setPendingMember("customValues", new MapBean(true, "field", "value", "hidden"));
    }

    public Map<String, Object> getPendingMember() {
        return values;
    }

    public Object getPendingMember(final String key) {
        return values.get(key);
    }

    public long getPendingMemberId() {
        try {
            return (Long) getPendingMember("id");
        } catch (final Exception e) {
            return 0L;
        }
    }

    public void setPendingMember(final Map<String, Object> map) {
        values = map;
    }

    public void setPendingMember(final String key, final Object value) {
        values.put(key, value);
    }

    public void setPendingMemberId(final long id) {
        setPendingMember("id", id);
    }
}
