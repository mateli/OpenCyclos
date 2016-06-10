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
package nl.strohalm.cyclos.controls.channels;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a channel
 * @author luis
 */
public class EditChannelAction extends BaseFormAction {

    private ChannelService      channelService;
    private DataBinder<Channel> dataBinder;

    @Inject
    public void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        Channel channel = resolveChannel(context);
        final boolean isInsert = channel.isTransient();
        channel = channelService.save(channel);
        if (isInsert) {
            context.sendMessage("channel.inserted");
        } else {
            context.sendMessage("channel.modified");
        }
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "channelId", channel.getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditChannelForm form = context.getForm();
        final long id = form.getChannelId();
        Channel channel;
        boolean isBuiltin;
        boolean allowsPaymentRequest;
        if (id <= 0) {
            channel = new Channel();
            isBuiltin = false;
            allowsPaymentRequest = true;
        } else {
            channel = channelService.load(id);
            final String internalName = channel.getInternalName();
            isBuiltin = channelService.isBuiltin(internalName);
            allowsPaymentRequest = channelService.allowsPaymentRequest(internalName);
        }

        final LocalSettings localSettings = settingsService.getLocalSettings();

        // Find the possible principal types
        final Map<PrincipalType, String> possiblePrincipalTypes = new LinkedHashMap<PrincipalType, String>();
        final List<MemberCustomField> customFields = channelService.possibleCustomFieldsAsPrincipal();
        for (final Principal principal : Principal.values()) {
            if (principal == Principal.CUSTOM_FIELD) {
                for (final MemberCustomField customField : customFields) {
                    possiblePrincipalTypes.put(new PrincipalType(customField), customField.getName());
                }
            } else {
                if (principal == Principal.EMAIL && !localSettings.isEmailUnique()) {
                    // Skip e-mail when it is not unique
                    continue;
                }
                final String label = context.message(principal.getKey());
                possiblePrincipalTypes.put(new PrincipalType(principal), label);
            }
        }
        final Set<Credentials> possibleCredentials = channelService.getPossibleCredentials(channel);
        getDataBinder().writeAsString(form.getChannel(), channel);
        request.setAttribute("channel", channel);
        request.setAttribute("isBuiltin", isBuiltin);
        request.setAttribute("possiblePrincipalTypes", possiblePrincipalTypes);
        request.setAttribute("possibleCredentials", possibleCredentials);
        request.setAttribute("singleCredential", possibleCredentials.size() == 1 ? possibleCredentials.iterator().next() : null);
        request.setAttribute("allowsPaymentRequest", allowsPaymentRequest);
        request.setAttribute("canManage", permissionService.hasPermission(AdminSystemPermission.CHANNELS_MANAGE));
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final Channel channel = resolveChannel(context);
        channelService.validate(channel);
    }

    private DataBinder<Channel> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<Channel> binder = BeanBinder.instance(Channel.class);
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("internalName", PropertyBinder.instance(String.class, "internalName"));
            binder.registerBinder("displayName", PropertyBinder.instance(String.class, "displayName"));
            binder.registerBinder("credentials", PropertyBinder.instance(Credentials.class, "credentials"));
            binder.registerBinder("paymentRequestWebServiceUrl", PropertyBinder.instance(String.class, "paymentRequestWebServiceUrl"));
            dataBinder = binder;
        }
        return dataBinder;
    }

    private Channel resolveChannel(final ActionContext context) {
        final EditChannelForm form = context.getForm();
        final Channel channel = getDataBinder().readFromString(form.getChannel());
        if (form.getPrincipalTypes() != null) {
            try {
                final PrincipalType defaultPrincipalType = channelService.resolvePrincipalType(form.getDefaultPrincipalType());
                final Set<PrincipalType> principalTypes = new HashSet<PrincipalType>();
                if (form.getPrincipalTypes() != null) {
                    for (final String principalTypeString : form.getPrincipalTypes()) {
                        if (StringUtils.isNotEmpty(principalTypeString)) {
                            principalTypes.add(channelService.resolvePrincipalType(principalTypeString));
                        }
                    }
                }
                channel.setPrincipalTypes(principalTypes, defaultPrincipalType);
            } catch (final Exception e) {
                throw new ValidationException();
            }
        }
        return channel;
    }

}
