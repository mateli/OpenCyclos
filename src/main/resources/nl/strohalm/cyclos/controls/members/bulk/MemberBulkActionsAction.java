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
package nl.strohalm.cyclos.controls.members.bulk;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.members.SearchMembersAction;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to apply bulk actions on members that match the given filter
 * @author luis
 */
public class MemberBulkActionsAction extends SearchMembersAction {

    private ChannelService channelService;

    @Inject
    public final void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    protected boolean allowRemovedGroups() {
        return false;
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final FullTextMemberQuery memberQuery = (FullTextMemberQuery) super.prepareForm(context);
        memberQuery.setEnabled(true);
        final HttpServletRequest request = context.getRequest();

        // Retrieve the custom fields that will be used on the search
        final List<MemberCustomField> fields = customFieldHelper.onlyForMemberSearch(memberCustomFieldService.list());
        request.setAttribute("customFields", customFieldHelper.buildEntries(fields, memberQuery.getCustomValues()));

        // Prepare data for specific actions
        MemberBulkChangeGroupAction.prepare(context, groupService);

        if (permissionService.hasPermission(AdminMemberPermission.BULK_ACTIONS_CHANGE_CHANNELS)) {
            MemberBulkChangeChannelsAction.prepare(context, channelService);
        }

        return memberQuery;
    }

}
