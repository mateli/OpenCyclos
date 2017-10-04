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
package nl.strohalm.cyclos.entities.customization.files;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.utils.StringHelper;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Table;
import java.util.Calendar;

/**
 * A database-stored file
 * @author luis
 */
@Table(name = "files")
@DiscriminatorColumn(name = "subclass", length = 1)
@javax.persistence.Entity
public abstract class File extends Entity {

    private static final long serialVersionUID = 6357271248674624613L;

    @Column(name = "contents", length = 10000000)
    private String            contents;

    @Column(name = "last_modified", nullable = false)
    private Calendar          lastModified;

    @Column(name = "name", nullable = false, length = 100)
    private String            name;

    public String getContents() {
        return contents;
    }

    public Calendar getLastModified() {
        return lastModified;
    }

    public String getName() {
        return name;
    }

    public void setContents(final String contents) {
        this.contents = StringHelper.removeCarriageReturnCharater(contents);
    }

    public void setLastModified(final Calendar lastModified) {
        this.lastModified = lastModified;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getId() + " - " + name;
    }
}
