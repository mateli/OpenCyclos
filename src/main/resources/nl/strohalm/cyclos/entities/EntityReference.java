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
package nl.strohalm.cyclos.entities;

import java.io.Serializable;

import nl.strohalm.cyclos.utils.EntityHelper;

/**
 * Marker interface for entity references
 * @author luis
 */
public interface EntityReference {

    /**
     * Class used to resolve serialization of references
     * @author luis
     */
    public static class Resolver implements Serializable {

        private static final long             serialVersionUID = 4535356518337361361L;
        private final Class<? extends Entity> entityClass;
        private final Long                    id;

        public Resolver(final Class<? extends Entity> entityClass, final Long id) {
            this.entityClass = entityClass;
            this.id = id;
        }

        @Override
        public String toString() {
            return entityClass.getName() + "#" + id;
        }

        private Object readResolve() {
            return EntityHelper.reference(entityClass, id);
        }
    }
}
