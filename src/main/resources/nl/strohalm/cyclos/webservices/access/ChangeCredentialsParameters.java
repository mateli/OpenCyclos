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
package nl.strohalm.cyclos.webservices.access;

import nl.strohalm.cyclos.webservices.PrincipalParameters;

/**
 * Parameters used to change the login password
 * 
 * @author luis
 */
public class ChangeCredentialsParameters extends PrincipalParameters {

    private static final long serialVersionUID = 2709950886565783705L;

    private String            oldCredentials;
    private String            newCredentials;

    public String getNewCredentials() {
        return newCredentials;
    }

    public String getOldCredentials() {
        return oldCredentials;
    }

    public void setNewCredentials(final String newCredentials) {
        this.newCredentials = newCredentials;
    }

    public void setOldCredentials(final String oldCredentials) {
        this.oldCredentials = oldCredentials;
    }
}
