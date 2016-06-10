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

/**
 * A read-only view of an Entity
 * @author ameyer
 */
public class EntityVO extends DataObject implements Comparable<EntityVO> {
    private static final long serialVersionUID = 1L;

    private long              id;
    private String            name;

    public EntityVO(final long id, final String name) {
        super();
        if (name == null) {
            throw new NullPointerException("Null name for EntityVO");
        }
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(final EntityVO other) {
        if (other == null) {
            throw new NullPointerException();
        }
        if (id == other.id && name.equals(other.name)) {
            return 0;
        } else {
            final int nameComparation = name.compareTo(other.name);
            return nameComparation == 0 ? Long.valueOf(id).compareTo(Long.valueOf(other.id)) : nameComparation;
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntityVO other = (EntityVO) obj;
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
}
