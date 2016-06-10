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

import java.io.File;
import java.sql.Blob;

import javax.servlet.ServletContext;

import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.servlets.ImageByIdServlet;
import nl.strohalm.cyclos.utils.customizedfile.CustomizedFileHandler;

import org.springframework.web.context.ServletContextAware;

/**
 * Helper class for images
 * @author luis
 */
public final class WebImageHelper implements ServletContextAware {

    public static final String    SYSTEM_IMAGES_PATH     = "/pages/images";
    public static final String    SYSTEM_THUMBNAILS_PATH = "/pages/images/thumbnails";
    public static final String    SYSTEM_IMAGES_MAP_KEY  = "systemImages";
    public static final String    CUSTOM_IMAGES_PATH     = "/pages/images/custom";
    public static final String    CUSTOM_THUMBNAILS_PATH = "/pages/images/custom/thumbnails";
    public static final String    STYLE_IMAGES_PATH      = "/pages/styles";
    public static final String    STYLE_THUMBNAILS_PATH  = "/pages/styles/thumbnails";

    private ServletContext        servletContext;
    private CustomizedFileHandler customizedFileHandler;
    private CustomizationHelper   customizationHelper;

    /**
     * Return the real path for a given image nature
     */
    public File imagePath(final Image.Nature nature) {
        String path = null;
        switch (nature) {
            case SYSTEM:
                path = WebImageHelper.SYSTEM_IMAGES_PATH;
                break;
            case CUSTOM:
                path = WebImageHelper.CUSTOM_IMAGES_PATH;
                break;
            case STYLE:
                path = WebImageHelper.STYLE_IMAGES_PATH;
                break;
            default:
                return null;
        }
        return new File(servletContext.getRealPath(path));
    }

    /**
     * Remove a custom or style image. Other image types are ignored
     */
    public void remove(final Image image) {
        final Image.Nature nature = image.getNature();
        if (nature != Image.Nature.CUSTOM && nature != Image.Nature.STYLE) {
            return;
        }

        // Remove from the disk cache by name
        final File imageFile = new File(imagePath(nature), image.getName());
        customizationHelper.deleteFile(imageFile);
        final File thumbnailFile = new File(thumbnailPath(nature), image.getName());
        customizationHelper.deleteFile(thumbnailFile);

        // Remove from the disk cache by id
        final Long id = image.getId();
        customizedFileHandler.delete(ImageByIdServlet.IMAGES_CACHE_PATH + "/" + id);
        customizedFileHandler.delete(ImageByIdServlet.THUMBNAILS_CACHE_PATH + "/" + id);
    }

    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    public void setCustomizedFileHandler(final CustomizedFileHandler customizedFileHandler) {
        this.customizedFileHandler = customizedFileHandler;
    }

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * Return the real path for thumbnails of a given image nature
     */
    public File thumbnailPath(final Image.Nature nature) {
        String path = null;
        switch (nature) {
            case SYSTEM:
                path = WebImageHelper.SYSTEM_THUMBNAILS_PATH;
                break;
            case CUSTOM:
                path = WebImageHelper.CUSTOM_THUMBNAILS_PATH;
                break;
            case STYLE:
                path = WebImageHelper.STYLE_THUMBNAILS_PATH;
                break;
            default:
                return null;
        }
        return new File(servletContext.getRealPath(path));
    }

    /**
     * Update an image
     */
    public void update(final Image image) {
        final Image.Nature nature = image.getNature();
        if (!updateNature(nature)) {
            return;
        }

        updateImage(false, image, imagePath(nature));
        updateImage(true, image, thumbnailPath(nature));

        customizedFileHandler.notifyImageChangeListeners(image);
    }

    /**
     * Determine if the given image nature will be updated on the file system
     */
    public boolean updateNature(final Image.Nature nature) {
        // Only natures without owner
        return nature != null && nature.getOwnerType() == null;
    }

    /**
     * Update an image or thumbnail
     */
    private void updateImage(final boolean isThumbnail, final Image image, final File dir) {
        // Update nothing if no path is given
        if (dir == null) {
            return;
        }
        final Blob blob = isThumbnail ? image.getThumbnail() : image.getImage();
        byte[] contents;
        try {
            contents = blob.getBytes(1, (int) blob.length());
        } catch (final Exception e) {
            throw new IllegalStateException("Error reading the image contents", e);
        }
        final File file = new File(dir, image.getName());
        final long lastModified = image.getLastModified().getTimeInMillis();
        customizationHelper.updateFile(file, lastModified, contents);
    }
}
