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
package nl.strohalm.cyclos.controls.elements;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

/**
 * Form used to create an element
 * @author luis
 */
public class CreateElementForm extends BaseBindingForm {
    private static final long serialVersionUID = -8054408188398196588L;
    private String            confirmPassword;
    private boolean           forceChangePassword;
    private String            postAction;
    private long              groupId;
    private boolean           manualPassword;

    public CreateElementForm() {
        values.put("user", new MapBean("username", "password"));
        values.put("customValues", new MapBean(true, "field", "value", "hidden"));
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public long getGroupId() {
        return groupId;
    }

    public String getPostAction() {
        return postAction;
    }

    public boolean isForceChangePassword() {
        return forceChangePassword;
    }

    public boolean isManualPassword() {
        return manualPassword;
    }

    public boolean isOpenProfile() {
        return "openProfile".equals(postAction);
    }

    public void setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setForceChangePassword(final boolean forceChangePassword) {
        this.forceChangePassword = forceChangePassword;
    }

    public void setGroupId(final long groupId) {
        this.groupId = groupId;
    }

    public void setManualPassword(final boolean manualPassword) {
        this.manualPassword = manualPassword;
    }

    public void setPostAction(final String postAction) {
        this.postAction = postAction;
    }
}
