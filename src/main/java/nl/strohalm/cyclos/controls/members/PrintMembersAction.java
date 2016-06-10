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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.FullTextElementQuery;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters;

import org.apache.commons.lang.StringUtils;

/**
 * Action used to print members
 * @author luis
 */
public class PrintMembersAction extends SearchMembersAction {

    @Override
    protected Integer pageSize(final ActionContext context) {
        return null;
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();

        final FullTextElementQuery query = (FullTextElementQuery) super.prepareForm(context);
        query.fetch(Member.Relationships.CUSTOM_VALUES, Member.Relationships.LOAN_GROUPS, RelationshipHelper.nested(Member.Relationships.BROKER, Element.Relationships.USER));

        // Store the custom values
        final List<MemberCustomField> customFields = new LinkedList<MemberCustomField>(memberCustomFieldService.list());
        for (final Iterator<MemberCustomField> it = customFields.iterator(); it.hasNext();) {
            final MemberCustomField field = it.next();
            if (!field.isShowInPrint()) {
                it.remove();
            }
        }
        request.setAttribute("memberFields", customFields);

        if (context.isAdmin()) {
            if (query instanceof FullTextMemberQuery) {
                final FullTextMemberQuery memberQuery = (FullTextMemberQuery) query;
                // Calculate the group filter names
                final List<String> groupFilterNames = new LinkedList<String>();
                final Collection<GroupFilter> groupFilters = groupFilterService.load(EntityHelper.toIdsAsList(memberQuery.getGroupFilters()));
                for (final GroupFilter groupFilter : groupFilters) {
                    groupFilterNames.add(groupFilter.getName());
                }
                request.setAttribute("groupFilterNames", StringUtils.join(groupFilterNames.iterator(), ", "));
            }

            // Calculate the group names
            final List<String> groupNames = new LinkedList<String>();
            final Collection<Group> groups = groupService.load(EntityHelper.toIdsAsList(query.getGroups()));
            for (final Group group : groups) {
                groupNames.add(group.getName());
            }
            request.setAttribute("groupNames", StringUtils.join(groupNames.iterator(), ", "));
        }
        return query;
    }
}
