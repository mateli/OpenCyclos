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
package nl.strohalm.cyclos.utils.customizedfile;

import java.io.Serializable;

/**
 * Contains the operation executed by the handler
 * 
 * @author luis
 */
public class CustomizedFileOperation implements Serializable {

    public static enum OperationType {
        DELETE, WRITE
    }

    private static final long serialVersionUID = 800316107511763109L;

    public static CustomizedFileOperation forDelete(final String path) {
        final CustomizedFileOperation operation = new CustomizedFileOperation();
        operation.type = OperationType.DELETE;
        operation.path = path;
        return operation;
    }

    public static CustomizedFileOperation forWrite(final String path, final long lastModified, final byte[] contents) {
        final CustomizedFileOperation operation = new CustomizedFileOperation();
        operation.type = OperationType.WRITE;
        operation.path = path;
        operation.lastModified = lastModified;
        operation.contents = contents;
        return operation;
    }

    private CustomizedFileOperation.OperationType type;
    private String                                path;
    private long                                  lastModified;
    private byte[]                                contents;

    public byte[] getContents() {
        return contents;
    }

    public long getLastModified() {
        return lastModified;
    }

    public String getPath() {
        return path;
    }

    public CustomizedFileOperation.OperationType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + " " + path;
    }
}
