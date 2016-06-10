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
package nl.strohalm.cyclos.controls.access;

import org.apache.struts.action.ActionForm;

public class ChangePasswordForm extends ActionForm {

    private static final long serialVersionUID = 9111250060669683936L;
    private long              userId;
    private String            oldPassword;
    private String            newPassword;
    private String            newPasswordConfirmation;
    private boolean           forceChange;
    private boolean           embed;

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public long getUserId() {
        return userId;
    }

    public boolean isEmbed() {
        return embed;
    }

    public boolean isForceChange() {
        return forceChange;
    }

    public void setEmbed(final boolean embed) {
        this.embed = embed;
    }

    public void setForceChange(final boolean forceChange) {
        this.forceChange = forceChange;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNewPasswordConfirmation(final String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }
}
