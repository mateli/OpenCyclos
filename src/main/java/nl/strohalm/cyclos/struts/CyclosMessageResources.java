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
package nl.strohalm.cyclos.struts;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.servlet.ServletContext;

import nl.strohalm.cyclos.services.customization.TranslationChangeListener;
import nl.strohalm.cyclos.services.customization.TranslationMessageService;
import nl.strohalm.cyclos.utils.MessageResourcesLoadedListener;
import nl.strohalm.cyclos.utils.access.LoggedUser;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.springframework.web.context.ServletContextAware;

/**
 * Customized message resources used to get data from the service
 * @author luis
 */
public class CyclosMessageResources extends MessageResources implements ServletContextAware {

    private static final long                    serialVersionUID        = 6706539088478972L;

    private ServletContext                       servletContext;
    private TranslationMessageService            translationMessageService;
    private Properties                           properties;
    private List<MessageResourcesLoadedListener> messagesLoadedListeners = new ArrayList<MessageResourcesLoadedListener>();

    public CyclosMessageResources() {
        super(null, null);
    }

    public void addMessagesLoadedListener(final MessageResourcesLoadedListener messagesLoadedListener) {
        messagesLoadedListeners.add(messagesLoadedListener);
    }

    @Override
    public String getMessage(final Locale locale, final String key) {
        String message = getProperties().getProperty(key);
        if (message == null) {
            message = "???" + key + "???";
        }
        return message;
    }

    /**
     * Creates and initializes an instance for the given servlet context
     */
    public void initialize() {
        // Read the messages of the language, creating missing keys
        translationMessageService.addTranslationChangeListener(new TranslationChangeListener() {
            @Override
            public void onTranslationsChanged(final Properties properties) {
                reload(properties);
            }
        });

        // Load the messages
        reload(null);

        // now we can fire an event saying that the translation resources have been loaded
        for (final MessageResourcesLoadedListener messagesLoadedListener : messagesLoadedListeners) {
            messagesLoadedListener.onApplicationResourcesLoaded();
        }
        // Store the resources on the context, so Struts will find it
        servletContext.setAttribute(Globals.MESSAGES_KEY, this);
    }

    @Override
    public void setServletContext(final ServletContext context) {
        servletContext = context;
    }

    public void setTranslationMessageService(final TranslationMessageService translationMessageService) {
        this.translationMessageService = translationMessageService;
    }

    private Properties getProperties() {
        if (properties == null) {
            reload(null);
        }
        return properties;
    }

    private synchronized void reload(final Properties newProperties) {
        properties = LoggedUser.runAsSystem(new Callable<Properties>() {
            @Override
            public Properties call() throws Exception {
                // First, read the English properties, to ensure defaults
                final Properties properties = translationMessageService.readFile(Locale.US);
                // Then load all properties from DB
                final Properties dbProperties = newProperties == null ? translationMessageService.exportAsProperties() : newProperties;
                for (final Map.Entry<Object, Object> entry : dbProperties.entrySet()) {
                    final String key = (String) entry.getKey();
                    final String value = (String) entry.getValue();
                    if (StringUtils.isNotEmpty(value)) {
                        properties.setProperty(key, value);
                    }
                }
                return properties;
            }
        });

        // Clear the Struts cache
        formats.clear();
    }
}
