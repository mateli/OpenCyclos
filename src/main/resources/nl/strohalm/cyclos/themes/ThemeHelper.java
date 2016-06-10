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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletContext;

import nl.strohalm.cyclos.themes.exceptions.ThemeException;
import nl.strohalm.cyclos.themes.exceptions.ThemeNotFoundException;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.SpringHelper;

import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.StringUtils;

/**
 * Helper class for theme handling
 * @author luis
 */
public class ThemeHelper {

    private static final String         THEME_PROPERTIES_ENTRY = "/theme.properties";
    private static final FilenameFilter FILENAME_FILTER        = new SuffixFileFilter(".theme");
    private static final String         THEMES_PATH            = "/WEB-INF/themes/";

    /**
     * Lists available themes
     */
    public static List<Theme> listThemes(final ServletContext context) {
        final String path = context.getRealPath(THEMES_PATH);
        final File[] files = new File(path).listFiles(FILENAME_FILTER);
        final List<Theme> themes = new ArrayList<Theme>(files.length);
        for (final File file : files) {
            try {
                themes.add(read(file));
            } catch (final ThemeException e) {
                // Skip this theme
            }
        }
        return themes;
    }

    /**
     * Returns the real file for the theme
     */
    public static File realFile(final ServletContext context, final String fileName) {
        final String path = context.getRealPath(THEMES_PATH);
        return new File(path, fileName);
    }

    /**
     * Removes the given theme
     */
    public static void remove(final ServletContext context, final String fileName) throws ThemeNotFoundException {
        final File file = realFile(context, fileName);
        if (!file.exists()) {
            throw new ThemeNotFoundException(fileName);
        }

        final CustomizationHelper customizationHelper = SpringHelper.bean(context, CustomizationHelper.class);
        customizationHelper.deleteFile(file);
    }

    /**
     * Selects the given theme for use
     */
    public static void select(final ServletContext context, final String fileName) throws ThemeException {
        try {
            final File file = realFile(context, fileName);
            if (!file.exists()) {
                throw new ThemeNotFoundException(fileName);
            }
            final ZipFile zipFile = new ZipFile(file);

            // Ensure the properties entry exists
            properties(zipFile);

            // Read the files
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry entry = entries.nextElement();
                if (entry.getName().startsWith("/images")) {

                }
            }
        } catch (final ThemeException e) {
            throw e;
        } catch (final Exception e) {
            throw new ThemeException(e);
        }
    }

    /**
     * Reads properties into a Theme object
     */
    private static Theme fromProperties(final Properties properties) {
        final Theme theme = new Theme();
        theme.setTitle(StringUtils.trimToNull(properties.getProperty("title")));
        theme.setAuthor(StringUtils.trimToNull(properties.getProperty("author")));
        theme.setVersion(StringUtils.trimToNull(properties.getProperty("version")));
        theme.setDescription(StringUtils.trimToNull(properties.getProperty("description")));
        return theme;
    }

    /**
     * Return the properties for the given zip file
     */
    private static Properties properties(final ZipFile zipFile) throws IOException {
        final ZipEntry propertiesEntry = zipFile.getEntry(THEME_PROPERTIES_ENTRY);
        if (propertiesEntry == null) {
            throw new FileNotFoundException(THEME_PROPERTIES_ENTRY);
        }
        final Properties properties = new Properties();
        properties.load(zipFile.getInputStream(propertiesEntry));
        return properties;
    }

    /**
     * Reads a theme from file
     */
    private static Theme read(final File file) throws ThemeException {
        try {
            if (!file.exists()) {
                throw new ThemeNotFoundException(file.getName());
            }
            final ZipFile zipFile = new ZipFile(file);
            final Properties properties = properties(zipFile);
            final Theme theme = fromProperties(properties);
            theme.setFilename(file.getName());
            return theme;
        } catch (final Exception e) {
            throw new ThemeException(e);
        }
    }
}
