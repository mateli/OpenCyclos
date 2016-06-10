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
package nl.strohalm.cyclos.controls.members.brokering;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.members.BrokeringQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.Brokering;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.transactions.LoanService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;

/**
 * Action used to print a list of brokered members
 * @author luis
 */
public class PrintBrokeringsAction extends ListBrokeringsAction {

    private MemberCustomFieldService memberCustomFieldService;
    private LoanService              loanService;

    public LoanService getLoanService() {
        return loanService;
    }

    public MemberCustomFieldService getMemberCustomFieldService() {
        return memberCustomFieldService;
    }

    @Inject
    public void setLoanService(final LoanService loanService) {
        this.loanService = loanService;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        // We need the results as a list, since we will need to iterate twice over it
        queryParameters.setResultType(ResultType.LIST);

        super.executeQuery(context, queryParameters);
        final HttpServletRequest request = context.getRequest();

        final BrokeringQuery query = (BrokeringQuery) queryParameters;

        // Check if we will display loan data
        boolean showLoanData = false;
        if (query.getStatus() != BrokeringQuery.Status.PENDING) {
            // It is a nonsense to show loans of pending members
            if (context.isAdmin()) {
                showLoanData = permissionService.hasPermission(AdminMemberPermission.BROKERINGS_VIEW_LOANS);
            } else {
                showLoanData = permissionService.hasPermission(BrokerPermission.LOANS_VIEW);
            }
        }

        // Use the custom iterator to retrieve loan data
        final List<Brokering> brokerings = (List<Brokering>) request.getAttribute("brokerings");
        request.setAttribute("brokerings", new BrokeredIterator(brokerings.iterator(), loanService, showLoanData));
        request.setAttribute("showLoanData", showLoanData);
    }

    @Override
    protected Integer pageSize(final ActionContext context) {
        return null;
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();

        final BrokeringQuery query = (BrokeringQuery) super.prepareForm(context);
        query.fetch(RelationshipHelper.nested(Brokering.Relationships.BROKERED, Element.Relationships.GROUP), RelationshipHelper.nested(Brokering.Relationships.BROKERED, Member.Relationships.CUSTOM_VALUES));

        // Store the custom values
        final List<MemberCustomField> customFields = new LinkedList<MemberCustomField>(memberCustomFieldService.list());
        for (final Iterator<MemberCustomField> it = customFields.iterator(); it.hasNext();) {
            final MemberCustomField field = it.next();
            if (!field.isShowInPrint()) {
                it.remove();
            } else {
                final MemberCustomField.Access access = field.getVisibilityAccess();
                if (access != null && !access.grantedToBroker()) {
                    it.remove();
                }
            }
        }
        request.setAttribute("memberFields", customFields);
        return query;
    }
}
