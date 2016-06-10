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
package nl.strohalm.cyclos.controls.members.sms;

import java.util.Collections;
import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

/**
 * Form used to send an SMS mailing
 * @author luis
 */
public class SendSmsMailingForm extends BaseBindingForm {

    private static final long serialVersionUID = 5650987984776735957L;

    private boolean           isSingleMember;

    public SendSmsMailingForm() {
        setSmsMailing("groups", Collections.emptyList());
    }

    public Map<String, Object> getSmsMailing() {
        return values;
    }

    public Object getSmsMailing(final String key) {
        return values.get(key);
    }

    public boolean isSingleMember() {
        return isSingleMember;
    }

    public void setSingleMember(final boolean isSingleMember) {
        this.isSingleMember = isSingleMember;
    }

    public void setSmsMailing(final Map<String, Object> values) {
        this.values = values;
    }

    public void setSmsMailing(final String key, final Object value) {
        values.put(key, value);
    }

}
