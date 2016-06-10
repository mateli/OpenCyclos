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
package nl.strohalm.cyclos.webservices.model;

import javax.xml.bind.annotation.XmlType;

import nl.strohalm.cyclos.webservices.utils.ObjectHelper;

/**
 * Represents a member account
 * 
 * @author luis
 */
@XmlType(name = "memberAccount")
public class MemberAccountVO extends EntityVO {
    private static final long serialVersionUID = -1638448468354378740L;
    private Boolean           isDefault        = false;
    private AccountTypeVO     type;

    public boolean getDefault() {
        return ObjectHelper.valueOf(isDefault);
    }

    public AccountTypeVO getType() {
        return type;
    }

    public void setDefault(final boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setType(final AccountTypeVO type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MemberAccountVO[isDefault=" + getDefault() + ", type=" + type + "]";
    }
}
