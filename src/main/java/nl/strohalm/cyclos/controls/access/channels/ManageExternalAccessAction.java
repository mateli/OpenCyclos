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
package nl.strohalm.cyclos.controls.access.channels;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.User.TransactionPasswordStatus;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.utils.RelationshipHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to prepare the external access screen, where an admin can change the channel access for individual users.
 * @author Jefferson Magno
 */
public class ManageExternalAccessAction extends BaseAction {

    private ChannelService channelService;

    @Inject
    public void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final ManageExternalAccessForm form = context.getForm();

        boolean myAccess = false;
        boolean memberCanHavePin = false;

        // Get the member
        Member member;
        final long memberId = form.getMemberId();
        if (memberId > 0) {
            member = elementService.load(memberId, Element.Relationships.USER, Member.Relationships.CHANNELS, RelationshipHelper.nested(Element.Relationships.GROUP, MemberGroup.Relationships.CHANNELS));
            if (context.getElement().equals(member)) {
                myAccess = true;
            }

        } else {
            // Member managing his/her own external access settings
            member = elementService.load(context.getElement().getId(), Element.Relationships.USER, Member.Relationships.CHANNELS, RelationshipHelper.nested(Element.Relationships.GROUP, MemberGroup.Relationships.CHANNELS));
            myAccess = true;
        }

        // Check if the member can have pin
        memberCanHavePin = groupService.usesPin(member.getMemberGroup());

        final MemberGroup memberGroup = member.getMemberGroup();

        // If the pin is blocked, check the permission to unblock it
        if (memberCanHavePin) {
            final boolean pinBlocked = accessService.isPinBlocked(member.getMemberUser());
            if (pinBlocked) {
                final boolean canUnblockPin = permissionService
                        .permission(member)
                        .admin(AdminMemberPermission.ACCESS_UNBLOCK_PIN)
                        .broker(BrokerPermission.MEMBER_ACCESS_UNBLOCK_PIN)
                        .member(MemberPermission.ACCESS_UNBLOCK_PIN)
                        .hasPermission();
                request.setAttribute("canUnblockPin", canUnblockPin);
            }
        }

        // Check if the group of member uses a transaction password
        if (myAccess) {
            final boolean usesTransactionPassword = memberGroup.getBasicSettings().getTransactionPassword().isUsed();
            if (usesTransactionPassword) {
                request.setAttribute("usesTransactionPassword", usesTransactionPassword);
                final TransactionPasswordStatus transactionPasswordStatus = elementService.reloadUser(context.getUser().getId()).getTransactionPasswordStatus();
                if (transactionPasswordStatus == TransactionPasswordStatus.BLOCKED) {
                    request.setAttribute("transactionPasswordBlocked", true);
                } else if (transactionPasswordStatus.isGenerationAllowed()) {
                    request.setAttribute("transactionPasswordPending", true);
                }
            }
        }

        final boolean canChangePin = memberCanHavePin && permissionService
                .permission(member)
                .admin(AdminMemberPermission.ACCESS_CHANGE_PIN)
                .broker(BrokerPermission.MEMBER_ACCESS_CHANGE_PIN)
                .member()
                .hasPermission();

        // Channels that the group of member have access
        final Channel webChannel = channelService.loadByInternalName(Channel.WEB);

        final Collection<Channel> memberGroupChannels = new ArrayList<Channel>(memberGroup.getChannels());
        // The "web" channel can not be customized by the user, so it should not be sent to the JSP page
        // We need to clone the channels collection because it's associated to the hibernate session
        memberGroupChannels.remove(webChannel);

        // When the SMS channel is in use, it is not added / removed from this page, but from notifications
        final Channel smsChannel = channelService.getSmsChannel();
        boolean memberCanHaveSmsChannel = false;
        if (smsChannel != null) {
            memberCanHaveSmsChannel = memberGroupChannels.remove(smsChannel);
        }

        // Store member and settings in the request
        request.setAttribute("member", member);
        request.setAttribute("myAccess", myAccess);
        request.setAttribute("channels", memberGroupChannels);
        request.setAttribute("memberCanHavePin", memberCanHavePin);
        request.setAttribute("canChangePin", canChangePin);

        final boolean hasPermission = permissionService.permission(member)
                .admin(AdminMemberPermission.PREFERENCES_MANAGE_NOTIFICATIONS)
                .member(MemberPermission.PREFERENCES_MANAGE_NOTIFICATIONS)
                .broker(BrokerPermission.PREFERENCES_MANAGE_NOTIFICATIONS)
                .hasPermission();

        request.setAttribute("canManagePreferences", memberCanHaveSmsChannel && hasPermission);

        final boolean canChangeChannelAccess = accessService.canChangeChannelsAccess(member);
        request.setAttribute("canChangeChannelAccess", canChangeChannelAccess);

        return context.getInputForward();
    }
}
