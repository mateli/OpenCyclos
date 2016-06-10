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
package nl.strohalm.cyclos.themes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import nl.strohalm.cyclos.utils.DataObject;

import org.apache.commons.collections.CollectionUtils;

/**
 * Represents a theme
 * @author luis
 */
public class Theme extends DataObject implements Comparable<Theme> {

    /**
     * Defines which styles compose a theme
     * @author luis
     */
    public static enum Style {
        MAIN("style.css", "ieAdjust.css", "posweb.css"), LOGIN("login.css"), MOBILE("mobile.css");

        /**
         * Returns a set containing the WhatToExport instances according to a list of given file names
         * @param files
         */
        public static Set<Style> resolveFromFiles(final Collection<String> files) {
            final Set<Style> set = EnumSet.noneOf(Style.class);
            if (files != null) {
                for (final Style whatToExport : values()) {
                    if (CollectionUtils.containsAny(whatToExport.files, files)) {
                        set.add(whatToExport);
                    }
                }
            }
            return set;
        }

        private final Collection<String> files;

        private Style(final String... files) {
            this.files = Collections.unmodifiableCollection(Arrays.asList(files));
        }

        public Collection<String> getFiles() {
            return files;
        }
    }

    private static final long serialVersionUID = -1863957027992677264L;
    private String            title;
    private String            author;
    private String            version;
    private String            description;
    private String            filename;
    private Collection<Style> styles;

    public int compareTo(final Theme o) {
        return title == null ? -1 : title.compareTo(o.title);
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getFilename() {
        return filename;
    }

    public Collection<Style> getStyles() {
        return styles;
    }

    public String getTitle() {
        return title;
    }

    public String getVersion() {
        return version;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    public void setStyles(final Collection<Style> styles) {
        this.styles = styles;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setVersion(final String version) {
        this.version = version;
    }
}
