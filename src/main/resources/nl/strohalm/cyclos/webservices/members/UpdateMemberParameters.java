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
 * Parameters used to update members
 * 
 * @author luis
 */
public class UpdateMemberParameters implements Serializable {

    private static final long              serialVersionUID = -5508819586207313001L;

    private Long                           id;
    private String                         principalType;
    private String                         principal;
    private String                         name;
    private String                         email;
    private List<RegistrationFieldValueVO> fields;

    public String getEmail() {
        return email;
    }

    public List<RegistrationFieldValueVO> getFields() {
        return fields;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getPrincipalType() {
        return principalType;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setFields(final List<RegistrationFieldValueVO> fields) {
        this.fields = fields;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPrincipal(final String principal) {
        this.principal = principal;
    }

    public void setPrincipalType(final String principalType) {
        this.principalType = principalType;
    }

    @Override
    public String toString() {
        return "UpdateMemberParameters [email=" + email + ", fields=" + fields + ", id=" + id + ", name=" + name + ", principal=" + principal + ", principalType=" + principalType + "]";
    }
}
