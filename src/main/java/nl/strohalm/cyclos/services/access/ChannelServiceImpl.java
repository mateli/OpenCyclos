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
package nl.strohalm.cyclos.services.access;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import nl.strohalm.cyclos.dao.access.ChannelDAO;
import nl.strohalm.cyclos.dao.access.ChannelPrincipalDAO;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.access.ChannelPrincipal;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentRequestHandler;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.cache.Cache;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.cache.CacheListener;
import nl.strohalm.cyclos.utils.cache.CacheManager;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * Implementation for channel service
 * @author luis
 */
public class ChannelServiceImpl implements ChannelServiceLocal, InitializingBean, InitializingService {

    private static final String           ALL_KEY = "_ALL_";
    private FetchServiceLocal             fetchService;
    private ChannelDAO                    channelDao;
    private ChannelPrincipalDAO           channelPrincipalDao;
    private MemberCustomFieldServiceLocal memberCustomFieldService;
    private SettingsServiceLocal          settingsService;
    private CacheManager                  cacheManager;
    private PaymentRequestHandler         paymentRequestHandler;
    private CustomFieldHelper             customFieldHelper;

    @Override
    public void afterPropertiesSet() throws Exception {
        // Ensure that whenever the cache is updated, the payment request handler is also invalidated
        getCache().addListener(new CacheListener() {
            @Override
            public void onCacheCleared(final Cache cache) {
                paymentRequestHandler.invalidateCache();
            }

            @Override
            public void onValueAdded(final Cache cache, final Serializable key, final Object value) {
                paymentRequestHandler.invalidateCache();
            }

            @Override
            public void onValueRemoved(final Cache cache, final Serializable key, final Object value) {
                paymentRequestHandler.invalidateCache();
            }
        });
    }

    @Override
    public boolean allowsPaymentRequest(final String channel) {
        return !isBuiltin(channel);
    }

    @Override
    public Set<Credentials> getPossibleCredentials(final Channel channel) {
        final String internalName = channel.getInternalName();
        if (Channel.WEB.equals(internalName)) {
            // Main web allows only default
            return EnumSet.of(Credentials.DEFAULT);
        } else if (Arrays.asList(Channel.WAP1, Channel.WAP2, Channel.WEBSHOP).contains(internalName)) {
            // Wap1/2, and WebShop disallows card security code
            return EnumSet.of(Credentials.DEFAULT, Credentials.LOGIN_PASSWORD, Credentials.TRANSACTION_PASSWORD, Credentials.PIN);
        } else if (Channel.REST.equals(internalName)) {
            return EnumSet.of(Credentials.DEFAULT, Credentials.LOGIN_PASSWORD, Credentials.TRANSACTION_PASSWORD, Credentials.PIN, Credentials.CARD_SECURITY_CODE);
        } else {
            // The others don't allow default, as it must be a single shot, and default has 2 passwords
            return EnumSet.of(Credentials.LOGIN_PASSWORD, Credentials.TRANSACTION_PASSWORD, Credentials.PIN, Credentials.CARD_SECURITY_CODE);
        }
    }

    @Override
    public Channel getSmsChannel() {
        final String name = settingsService.getLocalSettings().getSmsChannelName();
        return StringUtils.isEmpty(name) ? null : loadByInternalName(name);
    }

    @Override
    public void initializeService() {
        Locale locale = settingsService.getLocalSettings().getLocale();
        channelDao.importNewBuiltin(locale);
    }

    @Override
    public boolean isBuiltin(final String channel) {
        try {
            return Channel.listBuiltin().contains(channel);
        } catch (final RuntimeException e) {
            return false;
        }
    }

    @Override
    public List<Channel> list() {
        return getCache().get(ALL_KEY, new CacheCallback() {
            @Override
            public Object retrieve() {
                return channelDao.listAll(Channel.Relationships.PRINCIPALS);
            }
        });
    }

    @Override
    public List<Channel> listBuiltin() {
        return filterChannels(true);
    }

    @Override
    public List<Channel> listExternal() {
        final List<Channel> channels = list();
        channels.remove(loadByInternalName(Channel.WEB));
        return channels;
    }

    @Override
    public List<Channel> listNonBuiltin() {
        return filterChannels(false);
    }

    @Override
    public List<Channel> listSupportingPaymentRequest() {
        final List<Channel> channels = new ArrayList<Channel>();
        for (final Channel channel : list()) {
            if (channel.isPaymentRequestSupported()) {
                channels.add(channel);
            }
        }
        return channels;
    }

    @Override
    public Channel load(final Long id) throws EntityNotFoundException {
        return getCache().get(id, new CacheCallback() {
            @Override
            public Object retrieve() {
                Channel channel = channelDao.load(id, Channel.Relationships.PRINCIPALS);
                channel.setPrincipals(fetchService.fetch(channel.getPrincipals(), ChannelPrincipal.Relationships.CUSTOM_FIELD));
                return channel;
            }
        });
    }

    @Override
    public Collection<Channel> load(final Long[] ids) {
        return channelDao.load(Arrays.asList(ids));
    }

    @Override
    public Channel loadByInternalName(final String name) throws EntityNotFoundException {
        return getCache().get(name, new CacheCallback() {
            @Override
            public Object retrieve() {
                Channel channel = channelDao.loadByInternalName(name, Channel.Relationships.PRINCIPALS);
                channel.setPrincipals(fetchService.fetch(channel.getPrincipals(), ChannelPrincipal.Relationships.CUSTOM_FIELD));
                return channel;
            }
        });
    }

    @Override
    public List<MemberCustomField> possibleCustomFieldsAsPrincipal() {
        final List<MemberCustomField> allMemberFields = memberCustomFieldService.list();
        final List<MemberCustomField> possible = new ArrayList<MemberCustomField>();
        for (final MemberCustomField field : allMemberFields) {
            if (field.getType() == CustomField.Type.STRING && field.getValidation().isUnique()) {
                possible.add(field);
            }
        }
        return possible;
    }

    @Override
    public int remove(final Long... ids) {
        try {
            return channelDao.delete(ids);
        } finally {
            getCache().clear();
        }
    }

    @Override
    public PrincipalType resolvePrincipalType(final String principalTypeString) {
        PrincipalType principalType = null;
        try {
            // Try by principal enum
            final Principal principal = Principal.valueOf(principalTypeString);
            if (principal != Principal.CUSTOM_FIELD) {
                // Custom fields should be resolved by their internal name
                principalType = new PrincipalType(principal);
            }
        } catch (final Exception e) {
            // Try by custom field
            final List<MemberCustomField> possibleFields = possibleCustomFieldsAsPrincipal();
            final MemberCustomField customField = customFieldHelper.findByInternalName(possibleFields, principalTypeString);
            if (customField != null) {
                principalType = new PrincipalType(customField);
            }
        }
        return principalType;
    }

    @Override
    public PrincipalType resolvePrincipalType(final String channelName, final String principalTypeString) {
        final Channel channel = loadByInternalName(channelName);
        PrincipalType principalType = resolvePrincipalType(principalTypeString);
        // Ensure the principal is supported by the channel
        if (!channel.getPrincipalTypes().contains(principalType)) {
            principalType = channel.getDefaultPrincipalType();
        }
        return principalType == null ? Channel.DEFAULT_PRINCIPAL_TYPE : principalType;
    }

    @Override
    public Channel save(Channel channel) {
        validate(channel);

        // Ensure there is a default
        final Collection<ChannelPrincipal> principals = channel.getPrincipals();
        boolean hasDefault = false;
        ChannelPrincipal user = null;
        for (final ChannelPrincipal channelPrincipal : principals) {
            if (channelPrincipal.getPrincipal() == Principal.USER) {
                user = channelPrincipal;
            }
            if (channelPrincipal.isDefault()) {
                hasDefault = true;
            }
        }
        if (!hasDefault) {
            // When no default, set preferentially the USER, or the first one
            if (user != null) {
                user.setDefault(true);
            } else {
                principals.iterator().next().setDefault(true);
            }
        }
        // Ensure that the web channel has USER set, otherwise, admins wouldn't login
        if (Channel.WEB.equals(channel.getInternalName())) {
            if (user == null) {
                user = new ChannelPrincipal();
                user.setChannel(channel);
                user.setPrincipal(Principal.USER);
                principals.add(user);
            }
        }

        // Ensure there's a valid credentials
        final Set<Credentials> possibleCredentials = getPossibleCredentials(channel);
        if (!possibleCredentials.contains(channel.getCredentials())) {
            channel.setCredentials(possibleCredentials.iterator().next());
        }

        try {

            if (channel.isTransient()) {
                // Check that the internal name doesn't exists.
                if (!channelDao.existsChannel(channel.getInternalName())) {
                    channel = channelDao.insert(channel);
                } else {
                    throw new ValidationException("channel.internalNameAlreadyInUse");
                }
            } else {
                // Load the current data to ensure that restrictions are enforced
                final Channel current = channelDao.load(channel.getId());

                // Ensure the internal name does not changes
                final String internalName = current.getInternalName();
                if (!allowsPaymentRequest(internalName)) {
                    // If payment request is not allowed, ensure the WS url is null
                    channel.setPaymentRequestWebServiceUrl(null);
                }
                channel.setPrincipals(null);
                channel = channelDao.update(channel);
                channelPrincipalDao.deleteAllFrom(channel);
            }

            // Insert the channel principals
            for (final ChannelPrincipal channelPrincipal : principals) {
                channelPrincipalDao.insert(channelPrincipal);
            }

        } finally {
            getCache().clear();
        }

        return channel;
    }

    public void setCacheManager(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setChannelDao(final ChannelDAO channelDao) {
        this.channelDao = channelDao;
    }

    public void setChannelPrincipalDao(final ChannelPrincipalDAO channelPrincipalDao) {
        this.channelPrincipalDao = channelPrincipalDao;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setMemberCustomFieldServiceLocal(final MemberCustomFieldServiceLocal memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    public void setPaymentRequestHandler(final PaymentRequestHandler paymentRequestHandler) {
        this.paymentRequestHandler = paymentRequestHandler;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public void validate(final Channel channel) throws ValidationException {
        final Validator validator = new Validator("channel");
        validator.property("internalName").required().maxLength(50);
        validator.property("displayName").required().maxLength(100);
        validator.property("principals").required().add(new PropertyValidation() {
            private static final long serialVersionUID = -2500914238715839704L;

            @Override
            @SuppressWarnings("unchecked")
            public ValidationError validate(final Object object, final Object property, final Object value) {
                final Collection<ChannelPrincipal> principals = (Collection<ChannelPrincipal>) value;
                if (CollectionUtils.isEmpty(principals)) {
                    // Will already fail validation by the required
                    return null;
                }
                for (final ChannelPrincipal channelPrincipal : principals) {
                    if (channelPrincipal.getPrincipal() == null) {
                        return new RequiredError();
                    } else if (channelPrincipal.getPrincipal() == Principal.CUSTOM_FIELD) {
                        final MemberCustomField customField = fetchService.fetch(channelPrincipal.getCustomField());
                        if (customField == null) {
                            return new RequiredError();
                        }
                        if (customField.getType() != CustomField.Type.STRING) {
                            return new InvalidError();
                        }
                        if (!customField.getValidation().isUnique()) {
                            return new InvalidError();
                        }
                    }
                }
                return null;
            }
        });
        final Set<Credentials> possibleCredentials = getPossibleCredentials(channel);
        if (channel.getDefaultPrincipalType().getPrincipal() != Principal.CARD) {
            // Ensure card security code is only possible if principal could be card
            possibleCredentials.remove(Credentials.CARD_SECURITY_CODE);
        }
        if (possibleCredentials.size() > 1) {
            // Only required if there's choice
            validator.property("credentials").required().anyOf(possibleCredentials);
        }
        validator.validate(channel);
    }

    /**
     * @param builtin if it's false then the built-in are removed
     */
    private List<Channel> filterChannels(final boolean builtin) {
        final List<Channel> channels = list();
        // Remove those which are built-in
        for (final Iterator<Channel> iterator = channels.iterator(); iterator.hasNext();) {
            final Channel channel = iterator.next();
            if (builtin && !isBuiltin(channel.getInternalName()) || !builtin && isBuiltin(channel.getInternalName())) {
                iterator.remove();
            }
        }
        return channels;
    }

    private Cache getCache() {
        return cacheManager.getCache("cyclos.Channels");
    }
}
