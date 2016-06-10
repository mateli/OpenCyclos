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

import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for channels
 * @author luis
 */
public interface ChannelService extends Service {
    /**
     * Checks whether the given channel may support external payment
     */
    boolean allowsPaymentRequest(String channel);

    /**
     * Returns the possible credentials types for the given channel
     */
    Set<Credentials> getPossibleCredentials(Channel channel);

    /**
     * Returns the channel specified by {@link LocalSettings#getSmsChannelName()}, or null if no one is used
     */
    Channel getSmsChannel();

    /**
     * Checks whether the given channel is built-in on Cyclos
     */
    boolean isBuiltin(String channel);

    /**
     * Lists all registered channels
     */
    List<Channel> list();

    /**
     * Lists the builtin channels
     */
    List<Channel> listBuiltin();

    /**
     * Lists the non builtin channels
     */
    List<Channel> listNonBuiltin();

    /**
     * Loads a channel using it's identifier
     * @throws EntityNotFoundException No such channel exists
     */
    Channel load(Long id) throws EntityNotFoundException;

    /**
     * Loads a channel using it's internal name
     * @throws EntityNotFoundException No such channel exists
     */
    Channel loadByInternalName(String name) throws EntityNotFoundException;

    /**
     * Lists the member custom fields which could be used as principal
     */
    List<MemberCustomField> possibleCustomFieldsAsPrincipal();

    /**
     * Removes channels by identifiers
     */
    int remove(Long... ids);

    /**
     * Resolves a principal type given a string. The string is interpreted either as the name() of the {@link Principal} enum, or the internal name of
     * a custom field, when the principal should be {@link Principal#CUSTOM_FIELD}
     */
    PrincipalType resolvePrincipalType(String principalType);

    /**
     * Resolves a principal type given a string, validating that it is supported by the given channel. The string is interpreted either as the name()
     * of the {@link Principal} enum, or the internal name of a custom field, when the principal should be {@link Principal#CUSTOM_FIELD}
     */
    PrincipalType resolvePrincipalType(String channelName, String principalType);

    /**
     * Saves the given channel
     */
    Channel save(Channel channel);

    /**
     * Validates the given channel
     */
    void validate(Channel channel) throws ValidationException;

}
