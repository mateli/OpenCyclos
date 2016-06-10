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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Helper class for java.util.Properties
 * @author luis
 */
public class PropertiesHelper {

    private static class PropertiesResourceBundle extends ResourceBundle {

        private Map<String, String> properties;

        @SuppressWarnings({ "unchecked", "rawtypes" })
        private PropertiesResourceBundle(final Properties properties, final PropertiesResourceBundle parent) {
            this.properties = (Map) properties;
            if (parent != null) {
                setParent(parent);
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public Enumeration<String> getKeys() {
            return IteratorUtils.asEnumeration(properties.keySet().iterator());
        }

        @Override
        protected Object handleGetObject(final String key) {
            final String value = properties.get(key);
            return StringUtils.trimToNull(value);
        }

    }

    /**
     * Loads a property file from a base name, like "package.File", which would be translated into a classpath resource "package/File.properties".
     * Returns null if no such file exists
     */
    public static Properties loadFromResource(final String baseName) {
        final String path = baseName.replace('.', '/');
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final InputStream in = loader.getResourceAsStream(path + ".properties");
        if (in == null) {
            return null;
        }
        return loadFromStream(in);
    }

    /**
     * Loads the properties from the input stream using the UTF-8 charset. Since the option to load properties using a specified charset is only
     * available in java 6+, running on older versions will result in ignoring the charset. For that reason, it is recommended that the resource
     * bundle itself be encoded using ascii in production
     */
    public static Properties loadFromStream(final InputStream in) {
        final Properties properties = new Properties();
        try {
            try {
                // The Properties.load(Reader) only exists on Java 6
                final Reader reader = new InputStreamReader(in, "UTF-8");
                MethodUtils.invokeMethod(properties, "load", reader);
            } catch (final NoSuchMethodException e) {
                // Fallback to Properties.load(InputStream)
                properties.load(in);
            }
        } catch (final Exception e) {
            throw new IllegalStateException("Error reading resource bundle", e);
        }
        return properties;
    }

    public static void main(final String[] args) {
        final ResourceBundle bundle = readBundle("nl.strohalm.cyclos.setup.CyclosSetup", new Locale("pt", "BR", "RS"));
        System.out.println(bundle);
    }

    /**
     * Reads a resource bundle from a properties file encoded as utf-8
     */
    public static ResourceBundle readBundle(final String baseName, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }

        final List<String> suffixes = new ArrayList<String>();

        if (locale != null) {
            suffixes.add("");
            if (StringUtils.isNotEmpty(locale.getLanguage())) {
                suffixes.add("_" + locale.getLanguage());
            }
            if (StringUtils.isNotEmpty(locale.getCountry()) && StringUtils.isNotEmpty(locale.getLanguage())) {
                suffixes.add("_" + locale.getLanguage() + "_" + locale.getCountry());
            }
            if (StringUtils.isNotEmpty(locale.getVariant()) && StringUtils.isNotEmpty(locale.getCountry()) && StringUtils.isNotEmpty(locale.getLanguage())) {
                suffixes.add("_" + locale.getLanguage() + "_" + locale.getCountry() + "_" + locale.getVariant());
            }
        }

        PropertiesResourceBundle bundle = null;
        for (final String suffix : suffixes) {
            final Properties properties = loadFromResource(baseName + suffix);
            if (properties != null) {
                final PropertiesResourceBundle current = new PropertiesResourceBundle(properties, bundle);
                bundle = current;
            }
        }
        if (bundle == null) {
            throw new IllegalArgumentException("Error loading properties resource bundle for baseName=" + baseName + " and locale=" + locale);
        }
        return bundle;
    }
}
