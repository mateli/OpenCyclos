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

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.entities.settings.AlertSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.LogSettings;
import nl.strohalm.cyclos.entities.settings.MailSettings;
import nl.strohalm.cyclos.entities.settings.MailTranslation;
import nl.strohalm.cyclos.entities.settings.MessageSettings;
import nl.strohalm.cyclos.services.settings.SettingsService;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.web.context.ServletContextAware;

/**
 * Helper class for storing and retrieving settings from the web context
 * @author luis
 */
public class SettingsHelper implements ServletContextAware {

    /**
     * An object which behaves as a Map, but delegates calls to a settings object. This class is used by JSP's EL, which can invoke properties with
     * the same name as the settings object, but the target object itself is always returned by factory methods, like
     * {@link SettingsHelper#getLocalSettings(ServletContext)}, {@link SettingsHelper#getAccessSettings(ServletContext)} and so on.
     * @author luis
     */
    private abstract static class SettingsProxyMap implements Map<String, Object> {

        private class SettingsProxyMapEntry implements Map.Entry<String, Object> {

            private final Object setting;
            private final String property;

            public SettingsProxyMapEntry(final Object setting, final String property) {
                this.setting = setting;
                this.property = property;
            }

            @Override
            public String getKey() {
                return property;
            }

            @Override
            public Object getValue() {
                return PropertyHelper.get(setting, property);
            }

            @Override
            public Object setValue(final Object value) {
                throw new UnsupportedOperationException();
            }

        }

        private Set<String> properties;

        private SettingsProxyMap() {
            // Get the property names
            properties = new HashSet<String>();
            final Object setting = getSetting();
            final PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(setting);
            for (final PropertyDescriptor descriptor : descriptors) {
                // Both readable and writable properties
                if (descriptor.getReadMethod() != null) {
                    properties.add(descriptor.getName());
                }
            }
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(final Object key) {
            return properties.contains(key);
        }

        @Override
        public boolean containsValue(final Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Set<Map.Entry<String, Object>> entrySet() {
            final Set<Map.Entry<String, Object>> entries = new HashSet<Map.Entry<String, Object>>();
            final Object setting = getSetting();
            for (final String property : properties) {
                entries.add(new SettingsProxyMapEntry(setting, property));
            }
            return entries;
        }

        @Override
        public Object get(final Object key) {
            if (!containsKey(key)) {
                throw new IllegalArgumentException("Property " + key + " is not available in " + getSetting().getClass());
            }
            return PropertyHelper.get(getSetting(), key.toString());
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        @Override
        public Set<String> keySet() {
            return Collections.unmodifiableSet(properties);
        }

        @Override
        public Object put(final String key, final Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(final Map<? extends String, ? extends Object> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object remove(final Object key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return properties.size();
        }

        @Override
        public Collection<Object> values() {
            throw new UnsupportedOperationException();
        }

        protected abstract Object getSetting();

    }

    public static final String ACCESS_KEY           = "accessSettings";
    public static final String ALERT_KEY            = "alertSettings";
    public static final String LOCAL_KEY            = "localSettings";
    public static final String LOG_KEY              = "logSettings";
    public static final String MAIL_KEY             = "mailSettings";
    public static final String MAIL_TRANSLATION_KEY = "mailTranslation";
    public static final String MESSAGE_KEY          = "messageSettings";

    private SettingsService    settingsService;

    @Override
    public void setServletContext(final ServletContext servletContext) {
        // Store a proxy for each setting type

        final Object accessProxy = new SettingsProxyMap() {
            @Override
            protected Object getSetting() {
                return getAccessSettings();
            }
        };
        servletContext.setAttribute(ACCESS_KEY, accessProxy);

        final Object alertProxy = new SettingsProxyMap() {
            @Override
            protected Object getSetting() {
                return getAlertSettings();
            }
        };
        servletContext.setAttribute(ALERT_KEY, alertProxy);

        final Object localProxy = new SettingsProxyMap() {
            @Override
            protected Object getSetting() {
                return getLocalSettings();
            }
        };
        servletContext.setAttribute(LOCAL_KEY, localProxy);

        final Object logProxy = new SettingsProxyMap() {
            @Override
            protected Object getSetting() {
                return getLogSettings();
            }
        };
        servletContext.setAttribute(LOG_KEY, logProxy);

        final Object mailProxy = new SettingsProxyMap() {
            @Override
            protected Object getSetting() {
                return getMailSettings();
            }
        };
        servletContext.setAttribute(MAIL_KEY, mailProxy);

        final Object mailTranslationProxy = new SettingsProxyMap() {
            @Override
            protected Object getSetting() {
                return getMailTranslation();
            }
        };
        servletContext.setAttribute(MAIL_TRANSLATION_KEY, mailTranslationProxy);

        final Object messageProxy = new SettingsProxyMap() {
            @Override
            protected Object getSetting() {
                return getMessageSettings();
            }
        };
        servletContext.setAttribute(MESSAGE_KEY, messageProxy);
    }

    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    private AccessSettings getAccessSettings() {
        return settingsService.getAccessSettings();
    }

    private AlertSettings getAlertSettings() {
        return settingsService.getAlertSettings();
    }

    private LocalSettings getLocalSettings() {
        return settingsService.getLocalSettings();
    }

    private LogSettings getLogSettings() {
        return settingsService.getLogSettings();
    }

    private MailSettings getMailSettings() {
        return settingsService.getMailSettings();
    }

    private MailTranslation getMailTranslation() {
        return settingsService.getMailTranslation();
    }

    private MessageSettings getMessageSettings() {
        return settingsService.getMessageSettings();
    }
}
