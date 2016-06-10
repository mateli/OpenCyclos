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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import nl.strohalm.cyclos.entities.customization.images.Image;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ServletContextAware;

/**
 * Base implementation for a customized file handler
 * 
 * @author luis
 */
public abstract class BaseCustomizedFileHandler implements ServletContextAware, CustomizedFileHandler {

    protected static final Log        LOG                  = LogFactory.getLog(BaseCustomizedFileHandler.class);

    private ServletContext            context;
    private List<ImageChangeListener> imageChangeListeners = new ArrayList<ImageChangeListener>();

    @Override
    public void addImageChangeListener(final ImageChangeListener listener) {
        imageChangeListeners.add(listener);
    }

    @Override
    public void setServletContext(final ServletContext servletContext) {
        context = servletContext;
    }

    protected final synchronized void deleteLocally(final String path) {
        final String realPath = context.getRealPath(path);
        final File file = new File(realPath);
        file.delete();
        LOG.debug("Deleted file " + file);
        // Delete the parent directory if empty
        final File dir = file.getParentFile();
        if (ArrayUtils.isEmpty(dir.listFiles())) {
            LOG.debug("Deleted empty dir " + dir);
            dir.delete();
        }
    }

    protected void notifyImageChangeListenersLocally(final Image image) {
        for (final ImageChangeListener listener : imageChangeListeners) {
            listener.onImageChanged(image);
        }
    }

    protected final synchronized void writeLocally(final String path, final long lastModified, final byte[] contents) {
        final String realPath = context.getRealPath(path);
        final File file = new File(realPath);
        file.getParentFile().mkdirs();
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(contents, 0, contents.length);
            file.setLastModified(lastModified);
            LOG.debug("Wrote local file " + file);
        } catch (final Exception e) {
            LOG.error("Error writing local file", e);
        }
    }

}
