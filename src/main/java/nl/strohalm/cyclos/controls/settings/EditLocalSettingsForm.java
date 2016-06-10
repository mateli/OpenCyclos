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
package nl.strohalm.cyclos.controls.settings;

import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to edit local settings
 * @author luis
 */
public class EditLocalSettingsForm extends BaseSettingsForm {

    private static final long serialVersionUID = -579113590975682414L;

    public EditLocalSettingsForm() {
        setSetting("adminTimeout", new MapBean("field", "number"));
        setSetting("memberTimeout", new MapBean("field", "number"));
        setSetting("deactivationAfterWrongPasswords", new MapBean("field", "number"));
        setSetting("brokeringExpirationPeriod", new MapBean("number", "field"));
        setSetting("deletePendingRegistrationsAfter", new MapBean("number", "field"));
        setSetting("deleteMessagesOnTrashAfter", new MapBean("number", "field"));
        setSetting("transactionNumber", new MapBean("prefix", "padLength", "suffix", "enabled"));
        setSetting("maxChargebackTime", new MapBean("number", "field"));
    }

}
