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

import nl.strohalm.cyclos.entities.customization.images.Image;

/**
 * Used to write local files within the web context scope
 * 
 * @author luis
 */
public interface CustomizedFileHandler {

    /**
     * Listener for image change events
     */
    void addImageChangeListener(ImageChangeListener listener);

    /**
     * Propagates the removal of the given file
     */
    void delete(String path);

    /**
     * Notifies registered listeners about changes on the given image. It is important that the received image's contents cannot be directly used, as
     * the DB transaction (and, therefore, the Blob) will be unavailable.
     */
    void notifyImageChangeListeners(Image image);

    /**
     * Propagates the updating of the given file, with the given contents, setting the given last modification timestamp
     */
    void write(String path, long lastModified, byte[] contents);

}
