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

import java.io.Serializable;

/**
 * Base class for entities exported on web services
 * @author luis
 */
public abstract class EntityVO implements Serializable {
    private static final long serialVersionUID = 8416556223478615460L;
    private Long              id;

    @Override
    public boolean equals(final Object obj) {
        if (!getClass().isInstance(obj)) {
            return false;
        }
        final EntityVO vo = (EntityVO) obj;
        return id == null || vo.id == null ? this == obj : id.equals(vo.id);
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : id.hashCode();
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getName() + "#" + id;
    }
}
