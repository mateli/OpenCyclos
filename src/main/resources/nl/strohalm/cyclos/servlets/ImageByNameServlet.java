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
package nl.strohalm.cyclos.servlets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.Image.Nature;
import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.ImageHelper.ImageType;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.WebImageHelper;
import nl.strohalm.cyclos.utils.customizedfile.CustomizedFileHandler;
import nl.strohalm.cyclos.utils.customizedfile.ImageChangeListener;

import org.apache.commons.lang.StringUtils;

/**
 * Servlet used to display system images
 * @author luis
 */
public class ImageByNameServlet extends HttpServlet {

    private static final long         serialVersionUID = 8368765800788965163L;

    private ServletContext            context;
    private Nature                    nature;
    private File                      imagePath;
    private File                      thumbnailPath;
    private final Map<String, String> resolvedNames    = new HashMap<String, String>();
    private WebImageHelper            webImageHelper;
    private ResponseHelper            responseHelper;

    @Override
    public synchronized void init(final ServletConfig config) throws ServletException {
        super.init(config);

        context = getServletContext();
        webImageHelper = SpringHelper.bean(context, WebImageHelper.class);
        responseHelper = SpringHelper.bean(context, ResponseHelper.class);

        // Whenever the image is updated, we have to invalidate the cache
        SpringHelper.bean(context, CustomizedFileHandler.class).addImageChangeListener(new ImageChangeListener() {
            @Override
            public void onImageChanged(final Image image) {
                if (nature.equals(image.getNature())) {
                    removeFromCache(image.getName());
                }
            }
        });

        final String natureParameter = config.getInitParameter("nature");
        try {
            nature = Nature.valueOf(natureParameter);
        } catch (final Exception e) {
            context.log("Invalid nature for " + ClassHelper.getClassName(getClass()) + ": " + natureParameter);
        }

        imagePath = webImageHelper.imagePath(nature);
        thumbnailPath = webImageHelper.thumbnailPath(nature);
    }

    public void removeFromCache(final String name) {
        if (StringUtils.isEmpty(name)) {
            return;
        }
        synchronized (resolvedNames) {
            final Iterator<Map.Entry<String, String>> iterator = resolvedNames.entrySet().iterator();
            while (iterator.hasNext()) {
                final Entry<String, String> entry = iterator.next();
                final String current = entry.getKey();
                if (name.equals(current) || name.startsWith(current + ".")) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
            render(request, response);
        } catch (final Exception e) {
            return;
        }
    }

    /**
     * Render the image
     */
    private void render(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final File file = resolveFile(request);
        if (file == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            ImageType type;
            try {
                type = ImageType.getByFileName(file.getName());
            } catch (final Exception e) {
                type = null;
            }
            write(request, response, file, type);
        }
    }

    private File resolveFile(final boolean thumbnail, final String name) {
        if (name == null) {
            return null;
        }
        final File file = new File(thumbnail ? thumbnailPath : imagePath, name);
        if (!file.exists()) {
            throw new IllegalArgumentException("Invalid image file: " + file);
        }
        return file;
    }

    /**
     * Resolve the image file
     */
    private File resolveFile(final HttpServletRequest request) {
        final boolean thumbnail = "true".equals(request.getParameter("thumbnail"));
        final String name = resolveName(request);
        return resolveFile(thumbnail, name);
    }

    /**
     * Resolve the image name, searching for an image with or without extension
     */
    private String resolveName(final HttpServletRequest request) {
        final String name = request.getParameter("image");
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        synchronized (resolvedNames) {
            String resolved = resolvedNames.get(name);
            if (resolved == null) {
                final File path = webImageHelper.imagePath(nature);
                for (final String image : path.list()) {
                    if (image.equals(name) || image.startsWith(name + ".")) {
                        resolved = image;
                        break;
                    }
                }
                resolvedNames.put(name, resolved);
            }
            return resolved;
        }
    }

    /**
     * Write the image file to the response
     */
    private void write(final HttpServletRequest request, final HttpServletResponse response, final File file, final ImageType type) throws IOException {
        final long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if (ifModifiedSince > 0 && file.lastModified() <= ifModifiedSince) {
            // The contents have not changed
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }
        // The contents are modified, or not in browser's cache. Render it
        if (type != null) {
            response.setContentType(type.getContentType());
        }
        responseHelper.writeFile(response, file);
        response.flushBuffer();
    }
}
