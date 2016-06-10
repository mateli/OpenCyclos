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
package nl.strohalm.cyclos.controls.operators;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.elements.SearchElementsAction;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldValue;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomFieldValue;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.members.FullTextOperatorQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action to search operatoristrators
 * @author luis
 */
public class SearchOperatorsAction extends SearchElementsAction<FullTextOperatorQuery> {

    @Override
    protected Class<? extends CustomFieldValue> getCustomFieldValueClass() {
        return OperatorCustomFieldValue.class;
    }

    @Override
    protected Class<FullTextOperatorQuery> getQueryClass() {
        return FullTextOperatorQuery.class;
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final Member loggedMember = context.getElement();

        final GroupQuery possibleGroupQuery = new GroupQuery();
        possibleGroupQuery.setNatures(Group.Nature.OPERATOR);
        possibleGroupQuery.setStatus(Group.Status.NORMAL);
        possibleGroupQuery.setMember(loggedMember);
        final List<? extends Group> possibleNewGroups = groupService.search(possibleGroupQuery);
        if (possibleNewGroups.isEmpty()) {
            throw new ValidationException(new ValidationError("operator.noGroup"));
        }

        final FullTextOperatorQuery query = (FullTextOperatorQuery) super.prepareForm(context);
        query.setMember(loggedMember);
        query.setEnabled(null);

        // Store the groups
        final GroupQuery groupQuery = new GroupQuery();
        groupQuery.setNatures(Group.Nature.OPERATOR);
        groupQuery.setMember(loggedMember);
        request.setAttribute("groups", groupService.search(groupQuery));

        // Store the possible groups for new operator
        request.setAttribute("possibleNewGroups", possibleNewGroups);

        return query;
    }
}
