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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Helper class for working with the response object
 * @author luis
 */
public class ResponseHelper {

    public static enum Status {
        SUCCESS, ERROR;
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private MessageHelper   messageHelper;
    private SettingsService settingsService;

    /**
     * Adds a cookie with the given name and value set for the context path root
     */
    public Cookie addRootCookie(final HttpServletRequest request, final HttpServletResponse response, final String name, final Object value) {
        final Cookie cookie = new Cookie(name, value == null ? "" : value.toString());
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
        return cookie;
    }

    /**
     * Set the specified response as a file download (attachment)
     */
    public void setDownload(final HttpServletResponse response, final String fileName) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        // In order for IE to find the download, we must remove the cache headers >:-(
        response.setHeader("Pragma", "Public");
        response.setHeader("Cache-Control", "");
    }

    /**
     * Set the correct character encoding (according to the settings)
     */
    public void setEncoding(final HttpServletResponse response) {
        response.setCharacterEncoding(settingsService.getLocalSettings().getCharset());
    }

    /**
     * Set the specified response to open inline (no attachment)
     */
    public void setInline(final HttpServletResponse response) {
        response.setHeader("Content-Disposition", "inline");
    }

    /**
     * Sets the cookies for the guest pages, based on the given group / group filter
     */
    public void setLoginCookies(final HttpServletRequest request, final HttpServletResponse response, final Entity entity) {
        Long groupId = null;
        Long groupFilterId = null;
        if (entity instanceof Group) {
            groupId = entity.getId();
        } else if (entity instanceof GroupFilter) {
            groupFilterId = entity.getId();
        }
        addRootCookie(request, response, "groupId", groupId);
        addRootCookie(request, response, "groupFilterId", groupFilterId);
    }

    public void setMessageHelper(final MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    /**
     * Set the response header to use no cache
     */
    public void setNoCache(final HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate,no-cache,no-store,max-age=0");
        response.setDateHeader("Expires", 1);
    }

    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    /**
     * Set the response as text/plain, with the correct character encoding (according to the settings) and no cache
     */
    public void setTextNoCache(final HttpServletResponse response) {
        response.setContentType("text/plain");
        setEncoding(response);
        setNoCache(response);
    }

    /**
     * Set the response as text/xml, with the correct character encoding (according to the settings) and no cache
     */
    public void setXmlNoCache(final HttpServletResponse response) {
        response.setContentType("text/xml");
        setEncoding(response);
        setNoCache(response);
    }

    /**
     * Writes a file contents to the response
     */
    public void writeFile(final HttpServletResponse response, final File file) throws IOException {
        if (file.exists()) {
            response.setContentLength((int) file.length());
            response.setDateHeader("Last-Modified", file.lastModified());
            final InputStream in = new FileInputStream(file);
            try {
                IOUtils.copy(in, response.getOutputStream());
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
    }

    /**
     * Writes a JSON response
     */
    public void writeJSON(final HttpServletResponse response, final JSONBuilder json) {
        try {
            final PrintWriter out = response.getWriter();
            out.print("{\"result\":");
            json.write(out);
            out.print("}");
        } catch (final Exception e) {
            throw new IllegalStateException("Error writing JSON string", e);
        }
    }

    /**
     * Writes a JSON response
     */
    public void writeJSON(final HttpServletResponse response, final String json) {
        try {
            response.getWriter().print("{\"result\":" + json + "}");
        } catch (final Exception e) {
            throw new IllegalStateException("Error writing JSON string", e);
        }
    }

    /**
     * Write the status to the response as XML
     */
    public void writeStatus(final HttpServletResponse response, final Object status, final Map<String, ?> fields) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='").append(settingsService.getLocalSettings().getCharset()).append("'?>");
        sb.append("<status value='").append(status).append("'>");
        if (fields != null && !fields.isEmpty()) {
            for (final Map.Entry<String, ?> entry : fields.entrySet()) {
                final String tag = entry.getKey();
                sb.append('<').append(tag).append("><![CDATA[");
                sb.append(entry.getValue());
                sb.append("]]></").append(tag).append('>');
            }
        }
        sb.append("</status>");

        setXmlNoCache(response);

        try {
            response.getWriter().print(sb.toString());
        } catch (final IOException e1) {
            throw new IllegalStateException("Error writing status xml: " + e1);
        }
    }

    /**
     * Write the validation errors if there are any. When no errors are found, the response body will be empty.
     */
    public void writeValidationErrors(final HttpServletResponse response, final ValidationException e) {
        final Map<String, Object> fields = new LinkedHashMap<String, Object>();

        final StringBuilder sb = new StringBuilder();
        if (e.hasErrors()) {
            for (final ValidationError error : e.getGeneralErrors()) {
                sb.append(messageHelper.message(error.getKey(), error.getArguments())).append('\n');
            }
            for (final Map.Entry<String, Collection<ValidationError>> entry : e.getErrorsByProperty().entrySet()) {
                final String property = entry.getKey();
                final String key = e.getPropertyKey(property);
                final String displayName = e.getPropertyDisplayName(property);
                String propertyMessage = property;
                if (key != null) {
                    propertyMessage = messageHelper.message(key);
                } else if (displayName != null) {
                    propertyMessage = displayName;
                }

                for (final ValidationError error : entry.getValue()) {
                    final List<Object> args = new ArrayList<Object>();
                    args.add(propertyMessage);
                    if (error.getArguments() != null) {
                        args.addAll(error.getArguments());
                    }
                    sb.append(messageHelper.message(error.getKey(), args.toArray())).append('\n');
                }
            }
        } else {
            sb.append(messageHelper.message("error.validation"));
        }
        fields.put("message", sb.toString());
        fields.put("properties", StringUtils.join(e.getErrorsByProperty().keySet().iterator(), ','));
        writeStatus(response, Status.ERROR, fields);
    }

    /**
     * Used when no validation error occured
     */
    public void writeValidationSuccess(final HttpServletResponse response) {
        writeStatus(response, Status.SUCCESS, null);
    }

}
