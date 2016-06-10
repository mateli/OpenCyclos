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
package nl.strohalm.cyclos.controls.loangroups;

import java.util.List;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.EntityReference;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroup;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroupQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.loangroups.LoanGroupService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.ReferenceConverter;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Action used to search loan groups
 * @author luis
 */
public class SearchLoanGroupsAction extends BaseQueryAction {

    private LoanGroupService           loanGroupService;
    private DataBinder<LoanGroupQuery> dataBinder;

    public DataBinder<LoanGroupQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<LoanGroupQuery> binder = BeanBinder.instance(LoanGroupQuery.class);
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
            binder.registerBinder("member", PropertyBinder.instance(Member.class, "member", ReferenceConverter.instance(Member.class)));
            dataBinder = binder;
        }
        return dataBinder;
    }

    public LoanGroupService getLoanGroupService() {
        return loanGroupService;
    }

    @Inject
    public void setLoanGroupService(final LoanGroupService loanGroupService) {
        this.loanGroupService = loanGroupService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final LoanGroupQuery query = (LoanGroupQuery) queryParameters;
        final List<LoanGroup> loanGroups = loanGroupService.search(query);
        context.getRequest().setAttribute("loanGroups", loanGroups);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final SearchLoanGroupsForm form = context.getForm();
        final LoanGroupQuery query = getDataBinder().readFromString(form.getQuery());
        if (query.getMember() instanceof EntityReference) {
            query.setMember((Member) elementService.load(query.getMember().getId(), Element.Relationships.USER));
        }
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }
}
