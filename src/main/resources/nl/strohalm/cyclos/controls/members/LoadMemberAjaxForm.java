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
package nl.strohalm.cyclos.controls.members;

import org.apache.struts.action.ActionForm;

/**
 * Form used to load a member, either by username or card number
 * @author luis
 */
public class LoadMemberAjaxForm extends ActionForm {

    private static final long serialVersionUID = -3467757552021943683L;
    private String            channel;
    private String            principalType;
    private String            principal;

    public String getChannel() {
        return channel;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getPrincipalType() {
        return principalType;
    }

    public void setChannel(final String channel) {
        this.channel = channel;
    }

    public void setPrincipal(final String principal) {
        this.principal = principal;
    }

    public void setPrincipalType(final String principalType) {
        this.principalType = principalType;
    }
}
