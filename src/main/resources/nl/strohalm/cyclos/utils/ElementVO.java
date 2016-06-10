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
package nl.strohalm.cyclos.utils;

import nl.strohalm.cyclos.entities.members.Element;

/**
 * A read-only view of an element
 * @author jcomas
 */
public class ElementVO extends EntityVO {

    private static final long serialVersionUID = 1L;
    private String            username;
    private Long              groupId;
    private Element.Nature    nature;

    public ElementVO(final long id, final String name, final String username, final Long groupId, final Element.Nature nature) {
        super(id, name);
        this.username = username;
        this.groupId = groupId;
        this.nature = nature;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ElementVO other = (ElementVO) obj;
        if (groupId == null) {
            if (other.groupId != null) {
                return false;
            }
        } else if (!groupId.equals(other.groupId)) {
            return false;
        }
        if (nature != other.nature) {
            return false;
        }
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        return true;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Element.Nature getNature() {
        return nature;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
        result = prime * result + ((nature == null) ? 0 : nature.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    public void setGroupId(final Long groupId) {
        this.groupId = groupId;
    }

    public void setNature(final Element.Nature nature) {
        this.nature = nature;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

}
