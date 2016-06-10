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
package nl.strohalm.cyclos.services.customization;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import nl.strohalm.cyclos.dao.customizations.TranslationMessageDAO;
import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessage;
import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessageQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.cache.Cache;
import nl.strohalm.cyclos.utils.cache.CacheAdapter;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.cache.CacheManager;
import nl.strohalm.cyclos.utils.conversion.LocaleConverter;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionCommitListener;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Implementation for message service
 * @author luis
 */
public class TranslationMessageServiceImpl implements TranslationMessageServiceLocal, InitializingBean, InitializingService {

    private static final String                   PROPERTIES_KEY = "_PROPERTIES";

    private TranslationMessageDAO                 translationMessageDao;
    private FetchServiceLocal                     fetchService;
    private CacheManager                          cacheManager;
    private SettingsServiceLocal                  settingsService;
    private TransactionHelper                     transactionHelper;

    private final List<TranslationChangeListener> listeners      = new ArrayList<TranslationChangeListener>();

    @Override
    public void addTranslationChangeListener(final TranslationChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        getCache().addListener(new CacheAdapter() {
            @Override
            public void onCacheCleared(final Cache cache) {
                transactionHelper.runInCurrentThread(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(final TransactionStatus status) {
                        notifyListeners(exportAsProperties());
                    }
                });
            }
        });
    }

    @Override
    public synchronized Properties exportAsProperties() {
        return getCache().get(PROPERTIES_KEY, new CacheCallback() {
            @Override
            public Object retrieve() {
                return translationMessageDao.listAsProperties();
            }
        });
    }

    @Override
    public void importFromProperties(final Properties properties, MessageImportType importType) {
        // Delete all messages if we will replace with the new file
        if (importType == MessageImportType.REPLACE) {
            translationMessageDao.deleteAll();
            importType = MessageImportType.ONLY_NEW;
        }

        if (importType == MessageImportType.ONLY_NEW) {
            importOnlyNewProperties(properties);
        } else {
            final boolean emptyOnly = importType == MessageImportType.NEW_AND_EMPTY;
            importNewAndModifiedProperties(properties, emptyOnly);
        }
        clearCacheOnCommit();
    }

    @Override
    public void initializeService() {
        final LocalSettings localSettings = settingsService.getLocalSettings();
        Properties properties = readFile(localSettings.getLocale());
        importFromProperties(properties, MessageImportType.NEW_AND_EMPTY);
    }

    @Override
    public TranslationMessage load(final Long id) {
        return translationMessageDao.load(id);
    }

    @Override
    public Properties readFile(final Locale locale) {
        final String language = LocaleConverter.instance().toString(locale);
        final String propertiesName = "ApplicationResources_" + language + ".properties";
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + propertiesName);
        final Properties properties = new Properties();
        try {
            final Reader reader = new InputStreamReader(in, "UTF-8");
            properties.load(reader);
        } catch (final Exception e) {
            // Ignore
        } finally {
            IOUtils.closeQuietly(in);
        }
        return properties;
    }

    @Override
    public int remove(final Long... ids) {
        final int count = translationMessageDao.delete(ids);
        clearCacheOnCommit();
        return count;
    }

    @Override
    public TranslationMessage save(TranslationMessage translationMessage) {
        validate(translationMessage);
        if (translationMessage.isTransient()) {
            translationMessage = translationMessageDao.insert(translationMessage);
        } else {
            translationMessage = translationMessageDao.update(translationMessage);
        }

        clearCacheOnCommit();

        return translationMessage;
    }

    @Override
    public List<TranslationMessage> search(final TranslationMessageQuery query) {
        return translationMessageDao.search(query);
    }

    public void setCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void setTranslationMessageDao(final TranslationMessageDAO translationMessageDao) {
        this.translationMessageDao = translationMessageDao;
    }

    @Override
    public void validate(final TranslationMessage translationMessage) {
        getValidator().validate(translationMessage);
    }

    private void clearCacheOnCommit() {
        CurrentTransactionData.addTransactionCommitListener(new TransactionCommitListener() {
            @Override
            public void onTransactionCommit() {
                getCache().clear();
            }
        });
    }

    private Cache getCache() {
        return cacheManager.getCache("cyclos.TranslationMessages");
    }

    private Validator getValidator() {
        final Validator validator = new Validator();
        validator.property("key").required().maxLength(100);
        validator.property("value").maxLength(4000);
        return validator;
    }

    private void importNewAndModifiedProperties(final Properties properties, final boolean emptyOnly) {
        // Process existing messages. This is done with Object[], otherwise hibernate will load each message with a separate select
        final Iterator<Object[]> existing = translationMessageDao.listData();
        try {
            while (existing.hasNext()) {
                final Object[] data = existing.next();
                final String key = (String) data[1];
                final String currentValue = (String) data[2];
                final String newValue = properties.getProperty(key);
                if (newValue != null) {
                    final boolean shallUpdate = !newValue.equals(currentValue) && (!emptyOnly || StringUtils.isEmpty(currentValue));
                    if (shallUpdate) {
                        final TranslationMessage message = new TranslationMessage();
                        message.setId((Long) data[0]);
                        message.setKey(key);
                        message.setValue(newValue);
                        translationMessageDao.update(message, false);
                    }
                    properties.remove(key);
                }
            }
        } finally {
            DataIteratorHelper.close(existing);
        }
        fetchService.clearCache();

        // Only those who have to be inserted are left in properties
        insertAll(properties);
    }

    private void importOnlyNewProperties(final Properties properties) {
        final Iterator<String> allKeys = translationMessageDao.listAllKeys();
        try {
            while (allKeys.hasNext()) {
                final String key = allKeys.next();
                properties.remove(key);
            }
        } finally {
            DataIteratorHelper.close(allKeys);
        }

        // Only new keys are left on the properties object
        insertAll(properties);
    }

    private void insertAll(final Properties properties) {
        final CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            final String key = (String) entry.getKey();
            final String value = (String) entry.getValue();
            final TranslationMessage translationMessage = new TranslationMessage();
            translationMessage.setKey(key);
            translationMessage.setValue(value);

            try {
                // Try to load first
                final TranslationMessage existing = translationMessageDao.load(key);
                // Existing - update
                existing.setValue(value);
                translationMessageDao.update(existing, false);
            } catch (final EntityNotFoundException e) {
                // Not found - insert
                translationMessageDao.insert(translationMessage);
            }
            // Clear the entity cache to avoid an explosion of messages in cache
            cacheCleaner.clearCache();
        }
    }

    private void notifyListeners(final Properties properties) {
        for (TranslationChangeListener listener : listeners) {
            listener.onTranslationsChanged(properties);
        }
    }
}
