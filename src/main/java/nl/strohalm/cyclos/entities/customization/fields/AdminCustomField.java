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
package nl.strohalm.cyclos.entities.customization.fields;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.groups.AdminGroup;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ManyToMany;
import java.util.Collection;

/**
 * A custom field for administrators
 * @author luis
 */
@DiscriminatorValue("admin")
@javax.persistence.Entity
public class AdminCustomField extends CustomField {
    public static enum Relationships implements Relationship {
        GROUPS("groups");

        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long      serialVersionUID = 8634927821795430531L;

    @ManyToMany(mappedBy = "adminCustomFields")
	private Collection<AdminGroup> groups;

    protected AdminCustomField() {
	}

	public Collection<AdminGroup> getGroups() {
        return groups;
    }

    public void setGroups(final Collection<AdminGroup> groups) {
        this.groups = groups;
    }
}
