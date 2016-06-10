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

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Security implementation for {@link ChannelService}
 * 
 * @author Rinke
 */
public class ChannelServiceSecurity extends BaseServiceSecurity implements ChannelService {

    private ChannelServiceLocal channelService;

    @Override
    public boolean allowsPaymentRequest(final String channel) {
        permissionService.permission().admin(AdminSystemPermission.CHANNELS_VIEW).check();
        return channelService.allowsPaymentRequest(channel);
    }

    @Override()
    public Set<Credentials> getPossibleCredentials(final Channel channel) {
        permissionService.permission().admin(AdminSystemPermission.CHANNELS_VIEW).check();
        return channelService.getPossibleCredentials(channel);
    }

    @Override
    public Channel getSmsChannel() {
        // called from ManageExternalAccessAction and from NotificationPreferenceAction
        permissionService.permission()
                .admin(AdminSystemPermission.CHANNELS_VIEW, AdminMemberPermission.PREFERENCES_MANAGE_NOTIFICATIONS, AdminMemberPermission.ACCESS_CHANGE_CHANNELS_ACCESS)
                .member()
                .broker(BrokerPermission.MEMBER_ACCESS_CHANGE_CHANNELS_ACCESS, BrokerPermission.PREFERENCES_MANAGE_NOTIFICATIONS)
                .check();
        return channelService.getSmsChannel();
    }

    @Override
    public boolean isBuiltin(final String channel) {
        permissionService.permission().admin(AdminSystemPermission.CHANNELS_VIEW).check();
        return channelService.isBuiltin(channel);
    }

    @Override
    public List<Channel> list() {
        permissionService.permission()
                .admin(AdminMemberPermission.BULK_ACTIONS_CHANGE_CHANNELS,
                        AdminSystemPermission.CHANNELS_VIEW,
                        AdminSystemPermission.GROUPS_MANAGE_MEMBER, // called by and EditGroup too
                        AdminSystemPermission.GROUPS_MANAGE_BROKER, // called by and EditGroup too
                        AdminSystemPermission.ACCOUNTS_MANAGE) // called by EditTransferType too
                .check();
        return channelService.list();
    }

    @Override
    public List<Channel> listBuiltin() {
        permissionService.permission().admin(AdminSystemPermission.CHANNELS_VIEW, AdminSystemPermission.SETTINGS_VIEW).check();
        return channelService.listBuiltin();
    }

    @Override
    public List<Channel> listNonBuiltin() {
        permissionService.permission().admin(AdminSystemPermission.CHANNELS_VIEW, AdminSystemPermission.SETTINGS_VIEW).check();
        return channelService.listNonBuiltin();
    }

    @Override
    public Channel load(final Long id) throws EntityNotFoundException {
        permissionService.permission().admin(AdminSystemPermission.CHANNELS_VIEW).check();
        return channelService.load(id);
    }

    @Override
    public Channel loadByInternalName(final String name) throws EntityNotFoundException {
        // no permissions check needed because (it's called with and without a logged user)
        return channelService.loadByInternalName(name);
    }

    @Override
    public List<MemberCustomField> possibleCustomFieldsAsPrincipal() {
        // called by EditChannelAction and EditLocalSettingsAction.
        permissionService.permission().admin(AdminSystemPermission.CHANNELS_VIEW, AdminSystemPermission.SETTINGS_VIEW).check();
        return channelService.possibleCustomFieldsAsPrincipal();
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission().admin(AdminSystemPermission.CHANNELS_MANAGE).check();
        return channelService.remove(ids);
    }

    @Override
    public PrincipalType resolvePrincipalType(final String principalType) {
        permissionService.permission().admin(AdminSystemPermission.CHANNELS_MANAGE).check();
        return channelService.resolvePrincipalType(principalType);
    }

    @Override
    public PrincipalType resolvePrincipalType(final String channelName, final String principalType) {
        // no permissions check needed because (it's called with and without a logged user)
        return channelService.resolvePrincipalType(channelName, principalType);
    }

    @Override
    public Channel save(final Channel channel) {
        permissionService.permission().admin(AdminSystemPermission.CHANNELS_MANAGE).check();
        return channelService.save(channel);
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        this.channelService = channelService;
    }

    @Override
    public void validate(final Channel channel) throws ValidationException {
        // no permissions on validation
        channelService.validate(channel);
    }
}
