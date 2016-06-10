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
package nl.strohalm.cyclos.controls.members.preferences;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;

public class NotificationPreferenceForm extends BaseBindingForm {

    private static final long serialVersionUID = -7227436418691955032L;
    private boolean           enableSmsOperations;
    private boolean           allowChargingSms;
    private boolean           acceptFreeMailing;
    private boolean           acceptPaidMailing;
    private long              memberId;

    public NotificationPreferenceForm() {
    }

    public long getMemberId() {
        return memberId;
    }

    public Map<String, Object> getNotificationPreference() {
        return values;
    }

    public Object getNotificationPreference(final String key) {
        return values.get(key);
    }

    public boolean isAcceptFreeMailing() {
        return acceptFreeMailing;
    }

    public boolean isAcceptPaidMailing() {
        return acceptPaidMailing;
    }

    public boolean isAllowChargingSms() {
        return allowChargingSms;
    }

    public boolean isEnableSmsOperations() {
        return enableSmsOperations;
    }

    public void setAcceptFreeMailing(final boolean acceptFreeMailing) {
        this.acceptFreeMailing = acceptFreeMailing;
    }

    public void setAcceptPaidMailing(final boolean acceptPaidMailing) {
        this.acceptPaidMailing = acceptPaidMailing;
    }

    public void setAllowChargingSms(final boolean allowChargingSms) {
        this.allowChargingSms = allowChargingSms;
    }

    public void setEnableSmsOperations(final boolean enableSmsOperations) {
        this.enableSmsOperations = enableSmsOperations;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setNotificationPreference(final Map<String, Object> map) {
        values = map;
    }

    public void setNotificationPreference(final String key, final Object value) {
        values.put(key, value);
    }

}
