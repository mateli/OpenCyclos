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
package nl.strohalm.cyclos.services.settings;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.strohalm.cyclos.dao.settings.SettingDAO;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.settings.events.SettingsChangeListener;
import nl.strohalm.cyclos.utils.ClassHelper;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.cache.Cache;
import nl.strohalm.cyclos.utils.cache.CacheAdapter;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.cache.CacheListener;
import nl.strohalm.cyclos.utils.cache.CacheManager;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionRollbackListener;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Base implementation for settings handler
 * @author luis
 */
public abstract class BaseSettingsHandler<T, L extends SettingsChangeListener> implements SettingsHandler<T, L>, InitializingBean {

    private final Log               LOGGER    = LogFactory.getLog(getClass());
    private final Class<T>          beanClass;
    private final Validator         validator;

    private CacheManager            cacheManager;
    private SettingDAO              settingDao;
    private final Set<L>            listeners = new HashSet<L>();
    final Setting.Type              type;
    final Map<String, Converter<?>> converters;

    protected BaseSettingsHandler(final Setting.Type type, final Class<T> beanClass) {
        this.type = type;
        this.beanClass = beanClass;
        this.converters = Collections.unmodifiableMap(createConverters());
        this.validator = createValidator();
    }

    @Override
    public void addListener(final L listener) {
        listeners.add(listener);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CacheListener listener = new CacheAdapter() {
            @Override
            @SuppressWarnings("unchecked")
            public void onValueAdded(final Cache cache, final Serializable key, final Object value) {
                // As all cache beans are stored on the same cache, let's see if this one is of the type handled by this handler
                if (!beanClass.isInstance(value)) {
                    return;
                }
                T settings = (T) value;
                for (L listener : listeners) {
                    notifyListener(listener, settings);
                }
            }
        };
        getCache().addListener(listener);
    }

    /**
     * Returns the settings bean
     */
    @Override
    public T get() {
        return getCache().<T> get(getCacheKey(), new CacheCallback() {
            @Override
            public Object retrieve() {
                return read();
            }
        });
    }

    /**
     * Returns the settings bean class
     */
    public Class<T> getBeanClass() {
        return beanClass;
    }

    public SettingDAO getSettingDao() {
        return settingDao;
    }

    @Override
    public T importFrom(final Map<String, String> values) {
        final T object = ClassHelper.instantiate(beanClass);
        populate(object, values);
        return update(object);
    }

    @Override
    public List<Setting> listSettings() {
        return buildSettings(get());
    }

    @Override
    public void refresh() {
        getCache().remove(getCacheKey());
    }

    @Override
    public void removeListener(final L listener) {
        listeners.remove(listener);
    }

    public void setCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setSettingDao(final SettingDAO settingsDao) {
        this.settingDao = settingsDao;
    }

    /**
     * Updates the settings bean
     */
    @Override
    public T update(final T newSettings) {
        // If the transaction is rolled back, the cache must be refreshed to restore the old values.
        CurrentTransactionData.addTransactionRollbackListener(new TransactionRollbackListener() {
            @Override
            public void onTransactionRollback() {
                refresh();
            }
        });

        // Validate the bean
        validate(newSettings);

        // Build a list of Setting entities
        final List<Setting> list = buildSettings(newSettings);

        for (final Setting setting : list) {
            Setting loaded = null;
            try {
                loaded = settingDao.load(type, setting.getName());

                // The setting exists: update the value
                loaded.setValue(setting.getValue());
                settingDao.update(loaded);
            } catch (final EntityNotFoundException e) {
                // The setting didn't exists
                settingDao.insert(setting);
            }
        }

        // Re-read the cached instance
        refresh();
        return get();
    }

    /**
     * Validate the settings bean
     */
    @Override
    public void validate(final T settings) {
        validator.validate(settings);
    }

    /**
     * Must be overriden to create the converters
     */
    protected abstract Map<String, Converter<?>> createConverters();

    /**
     * Must be overriden to create the validator
     */
    protected abstract Validator createValidator();

    /**
     * Should be implemented in order to notify the given listener about a setting change
     */
    protected abstract void notifyListener(L listener, T settings);

    /**
     * Read a settings bean from a list of Setting objects
     */
    protected T read() {
        final List<Setting> settingsList = settingDao.listByType(type);
        final Map<String, String> values = new HashMap<String, String>();
        for (final Setting setting : settingsList) {
            values.put(setting.getName(), setting.getValue());
        }

        try {
            final T settings = getBeanClass().newInstance();
            populate(settings, values);
            return settings;
        } catch (final Exception e) {
            LOGGER.warn("Error creating a settings bean", e);
            throw new IllegalStateException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Setting> buildSettings(final T settings) {
        final List<Setting> values = new LinkedList<Setting>();
        for (final Map.Entry<String, Converter<?>> entry : converters.entrySet()) {
            final String name = entry.getKey();
            // Read the value from the settings bean
            final Object value = PropertyHelper.get(settings, name);
            final String valueAsString = PropertyHelper.getAsString(value, (Converter<Object>) entry.getValue());
            // Create a setting entity
            final Setting setting = new Setting();
            setting.setType(type);
            setting.setName(name);
            setting.setValue(valueAsString);
            values.add(setting);
        }
        return values;
    }

    private Cache getCache() {
        return cacheManager.getCache("cyclos.Settings");
    }

    private String getCacheKey() {
        return beanClass.getSimpleName();
    }

    /**
     * Populate a settings object, using a Map of converters, and a Map of values. Only 2 levels of beans are supported, ie, xxxSettings.x.y. If there
     * were a nested bean on x, making it be xxxSettings.x.y.z, z would be ignored
     */
    private void populate(final Object settings, final Map<String, String> values) {
        for (final Map.Entry<String, Converter<?>> entry : converters.entrySet()) {
            final String name = entry.getKey();
            final Converter<?> converter = entry.getValue();
            if (values.containsKey(name)) {
                final String value = values.get(name);
                final Object realValue = converter.valueOf(value);
                // Check if there is a nested object
                if (name.contains(".")) {
                    final String first = PropertyHelper.firstProperty(name);
                    // No bean: instantiate it
                    if (PropertyHelper.get(settings, first) == null) {
                        try {
                            final Class<?> type = PropertyUtils.getPropertyType(settings, first);
                            final Object bean = type.newInstance();
                            PropertyHelper.set(settings, first, bean);
                        } catch (final Exception e) {
                            LOGGER.warn("Error while setting nested settings bean", e);
                            throw new IllegalStateException();
                        }
                    }
                }
                PropertyHelper.set(settings, name, realValue);
            }
        }
    }
}
