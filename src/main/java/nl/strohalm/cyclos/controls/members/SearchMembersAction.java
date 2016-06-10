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
package nl.strohalm.cyclos.controls.members;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.elements.SearchElementsAction;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.GroupFilterQuery;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.groups.GroupFilterService;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;

import org.apache.commons.collections.CollectionUtils;

/**
 * Action to search members
 * @author luis, Jefferson Magno
 */
public class SearchMembersAction extends SearchElementsAction<FullTextMemberQuery> {

    public static DataBinder<FullTextMemberQuery> memberQueryDataBinder(final LocalSettings settings) {
        return elementQueryDataBinder(settings, FullTextMemberQuery.class, MemberCustomFieldValue.class);
    }

    protected MemberCustomFieldService memberCustomFieldService;
    protected GroupFilterService       groupFilterService;

    protected CustomFieldHelper        customFieldHelper;

    public GroupFilterService getGroupFilterService() {
        return groupFilterService;
    }

    public MemberCustomFieldService getMemberCustomFieldService() {
        return memberCustomFieldService;
    }

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setGroupFilterService(final GroupFilterService groupFilterService) {
        this.groupFilterService = groupFilterService;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    protected boolean allowRemovedGroups() {
        return true;
    }

    @Override
    protected Class<? extends CustomFieldValue> getCustomFieldValueClass() {
        return MemberCustomFieldValue.class;
    }

    @Override
    protected Class<FullTextMemberQuery> getQueryClass() {
        return FullTextMemberQuery.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final FullTextMemberQuery memberQuery = (FullTextMemberQuery) super.prepareForm(context);
        memberQuery.fetch(Member.Relationships.IMAGES);

        // Retrieve the custom fields that will be used on the search
        final List<MemberCustomField> fields = customFieldHelper.onlyForMemberSearch(memberCustomFieldService.list());
        request.setAttribute("customFields", customFieldHelper.buildEntries(fields, memberQuery.getCustomValues()));

        final GroupFilterQuery groupFilterQuery = new GroupFilterQuery();
        if (context.isAdmin()) {
            final AdminGroup adminGroup = context.getGroup();
            groupFilterQuery.setAdminGroup(adminGroup);

            // Store the member groups for admins
            final GroupQuery groupQuery = new GroupQuery();
            groupQuery.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
            if (!allowRemovedGroups()) {
                groupQuery.setStatus(Group.Status.NORMAL);
            }
            groupQuery.setManagedBy(adminGroup);
            groupQuery.setGroupFilters(memberQuery.getGroupFilters());
            final List<MemberGroup> groups = (List<MemberGroup>) groupService.search(groupQuery);
            request.setAttribute("groups", groups);
            if (CollectionUtils.isEmpty(groups)) {
                memberQuery.setGroups(groups);
            }

            if (permissionService.hasPermission(AdminMemberPermission.MEMBERS_REGISTER)) {
                final Collection<MemberGroup> possibleNewGroups = new ArrayList<MemberGroup>();
                for (final MemberGroup memberGroup : groups) {
                    if (Group.Status.NORMAL.equals(memberGroup.getStatus())) {
                        possibleNewGroups.add(memberGroup);
                    }
                }
                request.setAttribute("possibleNewGroups", possibleNewGroups);
            }
        } else {
            MemberGroup memberGroup;
            if (context.isMember()) {
                memberGroup = context.getGroup();
            } else {
                final Operator operator = context.getElement();
                memberGroup = operator.getMember().getMemberGroup();
            }
            groupFilterQuery.setViewableBy(memberGroup);
        }
        final Collection<GroupFilter> groupFilters = groupFilterService.search(groupFilterQuery);
        if (CollectionUtils.isNotEmpty(groupFilters)) {
            request.setAttribute("groupFilters", groupFilters);
        }

        final Member broker = memberQuery.getBroker() == null ? null : (Member) elementService.load(memberQuery.getBroker().getId(), Element.Relationships.USER);
        memberQuery.setBroker(broker);

        return memberQuery;
    }

}
