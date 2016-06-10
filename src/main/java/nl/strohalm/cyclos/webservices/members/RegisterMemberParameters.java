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
package nl.strohalm.cyclos.webservices.members;

import java.io.Serializable;
import java.util.List;

import nl.strohalm.cyclos.webservices.model.RegistrationFieldValueVO;

/**
 * Parameters used to register a new member
 * 
 * @author luis
 */
public class RegisterMemberParameters implements Serializable {
    private static final long              serialVersionUID = 3669149261331815826L;
    private Long                           groupId;
    private String                         username;
    private String                         name;
    private String                         email;
    private String                         loginPassword;
    private String                         pin;
    private String                         credentials;
    private List<RegistrationFieldValueVO> fields;

    public String getCredentials() {
        return credentials;
    }

    public String getEmail() {
        return email;
    }

    public List<RegistrationFieldValueVO> getFields() {
        return fields;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public String getName() {
        return name;
    }

    public String getPin() {
        return pin;
    }

    public String getUsername() {
        return username;
    }

    public void setCredentials(final String credentials) {
        this.credentials = credentials;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setFields(final List<RegistrationFieldValueVO> fields) {
        this.fields = fields;
    }

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public void setLoginPassword(final String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPin(final String pin) {
        this.pin = pin;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "RegisterMemberParameters [email=" + email + ", fields=" + fields + ", groupId=" + groupId + ", loginPassword=****, pin=****" + ", name=" + name + ", username=" + username + ", " + super.toString() + "]";
    }
}
