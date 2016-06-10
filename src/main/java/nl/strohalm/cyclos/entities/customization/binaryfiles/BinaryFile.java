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
package nl.strohalm.cyclos.entities.customization.binaryfiles;

import java.sql.Blob;
import java.util.Calendar;

import nl.strohalm.cyclos.entities.Entity;

/**
 * Stores a binary file
 * @author Jefferson Magno
 */
public class BinaryFile extends Entity {

    private static final long serialVersionUID = -5332038464288668149L;
    private String            contentType;
    private String            name;
    private Integer           size;
    private Calendar          lastModified;
    private Blob              contents;

    public Blob getContents() {
        return contents;
    }

    public String getContentType() {
        return contentType;
    }

    public Calendar getLastModified() {
        return lastModified;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public void setContents(final Blob contents) {
        this.contents = contents;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public void setLastModified(final Calendar lastModified) {
        this.lastModified = lastModified;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSize(final Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return getId() == null ? "" : getId().toString();
    }

}
