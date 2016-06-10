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
package nl.strohalm.cyclos.entities.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.utils.StringValuedEnum;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * A channel is an user interface to access cyclos functionality. There are some build-in channels, which cannot be removed, that represents the
 * following channels:
 * <ul>
 * <li>The main web access</li>
 * <li>The wap2 interface (SERVER_ROOT/mobile)</li>
 * <li>The wap1 interface (SERVER_ROOT/wap)</li>
 * <li>The web-shop interface (SERVER_ROOT/do/external/payment</li>
 * <li>The pos-web interface (SERVER_ROOT/operator)</li>
 * </ul>
 * Other channels may be registered at will.
 * @author luis
 */
public class Channel extends Entity implements Comparable<Channel> {

    /**
     * Which information is used to authenticate the user in a channel
     * 
     * @author luis
     */
    public static enum Credentials implements StringValuedEnum {

        /** The default 2 passwords style: login with login password and perform payments with transaction password */
        DEFAULT("D"),

        /** The login password only */
        LOGIN_PASSWORD("L"),

        /** The transaction password only */
        TRANSACTION_PASSWORD("T"),

        /** The external pin only */
        PIN("P"),

        /** The card security code */
        CARD_SECURITY_CODE("C");

        private final String value;

        private Credentials(final String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    /**
     * Which information is used to identify the user in a channel
     * 
     * @author luis
     */
    public static enum Principal implements StringValuedEnum {
        /** By username */
        USER("U", "login.username"),

        /** By e-mail */
        EMAIL("E", "member.email"),

        /** By card number */
        CARD("C", "card.card"),

        /** By custom field */
        CUSTOM_FIELD("F", null); // The custom field's name is what should be used to display

        private final String value;
        private final String key;

        private Principal(final String value, final String key) {
            this.value = value;
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

        public boolean isNumeric() {
            return this == CARD;
        }

    }

    public static enum Relationships implements Relationship {
        PRINCIPALS("principals"), GROUPS("groups");
        private final String name;

        private Relationships(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static final PrincipalType DEFAULT_PRINCIPAL_TYPE = new PrincipalType(Principal.USER);

    public static final String        WEB                    = "web";
    public static final String        WAP1                   = "wap1";
    public static final String        WAP2                   = "wap2";
    public static final String        WEBSHOP                = "webshop";
    public static final String        POSWEB                 = "posweb";
    public static final String        POS                    = "pos";
    public static final String        REST                   = "rest";
    private static final List<String> BUILTIN_CHANNELS;

    private static final long         serialVersionUID       = 1902644598506785461L;

    static {
        final List<String> channels = new ArrayList<String>();
        channels.add(WEB);
        channels.add(WAP1);
        channels.add(WAP2);
        channels.add(WEBSHOP);
        channels.add(POSWEB);
        channels.add(POS);
        channels.add(REST);
        BUILTIN_CHANNELS = Collections.unmodifiableList(channels);
    }

    /**
     * Returns a list of built-in channels
     */
    public static List<String> listBuiltin() {
        return BUILTIN_CHANNELS;
    }

    private String                       internalName;
    private String                       displayName;
    private Credentials                  credentials;
    private String                       paymentRequestWebServiceUrl;
    private Collection<ChannelPrincipal> principals;
    private Collection<MemberGroup>      groups;

    public boolean allows(final PrincipalType principalType) {
        for (final PrincipalType type : getPrincipalTypes()) {
            if (type.equals(principalType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(final Channel o) {
        try {
            return displayName.compareTo(o.displayName);
        } catch (final RuntimeException e) {
            return -1;
        }
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public PrincipalType getDefaultPrincipalType() {
        if (CollectionUtils.isNotEmpty(principals)) {
            ChannelPrincipal defaultPrincipal = null;
            for (final ChannelPrincipal principal : principals) {
                // Find the default
                if (principal.isDefault()) {
                    defaultPrincipal = principal;
                    break;
                }
            }
            if (defaultPrincipal == null) {
                // No explicit default - return the first
                defaultPrincipal = principals.iterator().next();
            }
            return defaultPrincipal.asPrincipalType();
        }
        // When none found, return the default
        return DEFAULT_PRINCIPAL_TYPE;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Collection<MemberGroup> getGroups() {
        return groups;
    }

    public String getInternalName() {
        return internalName;
    }

    @Override
    public String getName() {
        return displayName;
    }

    public String getPaymentRequestWebServiceUrl() {
        return paymentRequestWebServiceUrl;
    }

    public Set<MemberCustomField> getPrincipalCustomFields() {
        final Set<MemberCustomField> fields = new HashSet<MemberCustomField>();
        for (final ChannelPrincipal principal : principals) {
            final MemberCustomField field = principal.getCustomField();
            if (field != null) {
                fields.add(field);
            }
        }
        return fields;
    }

    public Collection<ChannelPrincipal> getPrincipals() {
        return principals;
    }

    public Set<PrincipalType> getPrincipalTypes() {
        if (CollectionUtils.isEmpty(principals)) {
            return Collections.singleton(DEFAULT_PRINCIPAL_TYPE);
        }
        final Set<PrincipalType> principalTypes = new LinkedHashSet<PrincipalType>();
        for (final ChannelPrincipal cp : principals) {
            principalTypes.add(cp.asPrincipalType());
        }
        return principalTypes;
    }

    /**
     * Returns whether payment request is supported by this channel
     */
    public boolean isPaymentRequestSupported() {
        return StringUtils.isNotEmpty(paymentRequestWebServiceUrl);
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public void setGroups(final Collection<MemberGroup> groups) {
        this.groups = groups;
    }

    public void setInternalName(final String internalName) {
        this.internalName = internalName;
    }

    public void setPaymentRequestWebServiceUrl(final String paymentRequestWebServiceUrl) {
        this.paymentRequestWebServiceUrl = paymentRequestWebServiceUrl;
    }

    public void setPrincipals(final Collection<ChannelPrincipal> principals) {
        this.principals = principals;
    }

    public void setPrincipalTypes(final Set<PrincipalType> principalTypes, final PrincipalType defaultPrincipalType) {
        principals = new ArrayList<ChannelPrincipal>();
        for (final PrincipalType principalType : principalTypes) {
            final ChannelPrincipal channelPrincipal = new ChannelPrincipal();
            channelPrincipal.setChannel(this);
            channelPrincipal.setPrincipal(principalType.getPrincipal());
            channelPrincipal.setCustomField(principalType.getCustomField());
            channelPrincipal.setDefault(principalType.equals(defaultPrincipalType));
            principals.add(channelPrincipal);
        }
    }

    @Override
    public String toString() {
        return internalName;
    }

}
