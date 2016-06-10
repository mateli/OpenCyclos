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
package nl.strohalm.cyclos.controls.members.contacts;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * Form used to edit a contact
 * @author luis
 */
public class ContactForm extends BaseBindingForm {
    private static final long serialVersionUID = -1978554899333334864L;
    private long              id;

    public ContactForm() {
    }

    public Map<String, Object> getContact() {
        return values;
    }

    public Object getContact(final String key) {
        return values.get(key);
    }

    public long getId() {
        return id;
    }

    public void setContact(final Map<String, Object> map) {
        values = map;
    }

    public void setContact(final String key, final Object value) {
        values.put(key, value);
    }

    public void setId(final long id) {
        this.id = id;
    }
}
