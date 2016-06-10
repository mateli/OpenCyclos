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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class for relationships manipulation
 * @author luis
 */
public final class RelationshipHelper {

    /**
     * Return a relationship for the given name
     */
    public static Relationship forName(final String name) {
        return new Relationship() {

            @Override
            public boolean equals(final Object obj) {
                return obj == null ? false : toString().equals(obj.toString());
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public int hashCode() {
                return toString().hashCode();
            }

            @Override
            public String toString() {
                return name;
            }

        };
    }

    /**
     * Return a nested relationship of N levels the given relationship. Example: X has a relationship PARENT: nested(3, PARENT) = PARENT.PARENT.PARENT
     */
    public static Relationship nested(final int times, final Relationship relationship) {
        if (times <= 0 || times > 9) {
            return null;
        }
        final Relationship[] relationships = new Relationship[times];
        Arrays.fill(relationships, relationship);
        return nested(relationships);
    }

    /**
     * Return a relationship representing the nested parts
     */
    public static Relationship nested(final Relationship... relationships) {
        final List<String> path = new LinkedList<String>();
        for (final Relationship relationship : relationships) {
            path.add(relationship.getName());
        }
        return forName(StringUtils.join(path.iterator(), '.'));
    }
}
