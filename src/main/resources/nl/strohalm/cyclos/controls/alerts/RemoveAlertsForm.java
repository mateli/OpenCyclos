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
package nl.strohalm.cyclos.controls.alerts;

import org.apache.struts.action.ActionForm;

/**
 * Form used to remove the selected alerts
 * @author luis
 */
public class RemoveAlertsForm extends ActionForm {

    private static final long serialVersionUID = 8287564934575223447L;

    private Long[]            alertIds;
    private String            alertType;

    public Long[] getAlertIds() {
        return alertIds;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertIds(final Long[] alertIds) {
        this.alertIds = alertIds;
    }

    public void setAlertType(final String alertType) {
        this.alertType = alertType;
    }

}
