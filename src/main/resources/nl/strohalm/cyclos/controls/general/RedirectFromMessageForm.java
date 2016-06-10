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
package nl.strohalm.cyclos.controls.general;

import org.apache.struts.action.ActionForm;

/**
 * Form used to redirect the user to the correct page when coming from a message or e-mail link
 * 
 * @author luis
 */
public class RedirectFromMessageForm extends ActionForm {

    private static final long serialVersionUID = 9086219273465683165L;
    private long              userId;
    private String            path;

    public String getPath() {
        return path;
    }

    public long getUserId() {
        return userId;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

}
