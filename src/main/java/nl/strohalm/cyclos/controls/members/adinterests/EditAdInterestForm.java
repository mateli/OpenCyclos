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
package nl.strohalm.cyclos.controls.members.adinterests;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

public class EditAdInterestForm extends BaseBindingForm {

    private static final long serialVersionUID = 7935463105473416796L;

    private long              id;
    private long              memberId;

    public Map<String, Object> getAdInterest() {
        return values;
    }

    public Object getAdInterest(final String key) {
        return values.get(key);
    }

    public long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setAdInterest(final Map<String, Object> map) {
        values = map;
    }

    public void setAdInterest(final String key, final Object value) {
        values.put(key, value);
    }

    public void setId(final long memberId) {
        id = memberId;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

}
