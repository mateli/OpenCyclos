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
package nl.strohalm.cyclos.controls.members.imports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseQueryAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.members.imports.ImportedMember;
import nl.strohalm.cyclos.entities.members.imports.ImportedMemberQuery;
import nl.strohalm.cyclos.entities.members.imports.MemberImport;
import nl.strohalm.cyclos.services.elements.MemberImportService;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.DataBinderHelper;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.query.QueryParameters;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used to show details for imported members
 * @author luis
 */
public class ImportedMembersDetailsAction extends BaseQueryAction {

    private MemberImportService             memberImportService;
    private DataBinder<ImportedMemberQuery> dataBinder;

    public DataBinder<ImportedMemberQuery> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<ImportedMemberQuery> binder = BeanBinder.instance(ImportedMemberQuery.class);
            binder.registerBinder("memberImport", PropertyBinder.instance(MemberImport.class, "memberImport"));
            binder.registerBinder("status", PropertyBinder.instance(ImportedMemberQuery.Status.class, "status"));
            binder.registerBinder("lineNumber", PropertyBinder.instance(Integer.class, "lineNumber"));
            binder.registerBinder("nameOrUsername", PropertyBinder.instance(String.class, "nameOrUsername"));
            binder.registerBinder("pageParameters", DataBinderHelper.pageBinder());
            dataBinder = binder;
        }
        return dataBinder;
    }

    @Inject
    public void setMemberImportService(final MemberImportService memberImportService) {
        this.memberImportService = memberImportService;
    }

    @Override
    protected void executeQuery(final ActionContext context, final QueryParameters queryParameters) {
        final HttpServletRequest request = context.getRequest();
        final ImportedMemberQuery query = (ImportedMemberQuery) queryParameters;
        final List<ImportedMember> members = memberImportService.searchImportedMembers(query);
        request.setAttribute("importedMembers", members);
    }

    @Override
    protected QueryParameters prepareForm(final ActionContext context) {
        final HttpServletRequest request = context.getRequest();
        final ImportedMembersDetailsForm form = context.getForm();
        final ImportedMemberQuery query = getDataBinder().readFromString(form.getQuery());
        final MemberImport memberImport = memberImportService.load(query.getMemberImport().getId(), RelationshipHelper.nested(MemberImport.Relationships.ACCOUNT_TYPE, AccountType.Relationships.CURRENCY));
        if (memberImport == null || query.getStatus() == null) {
            throw new ValidationException();
        }
        query.setMemberImport(memberImport);
        // Check whether account type will be used
        final MemberAccountType accountType = memberImport.getAccountType();
        if (accountType != null) {
            request.setAttribute("unitsPattern", accountType.getCurrency().getPattern());
            request.setAttribute("hasCreditLimit", true);
            // Check whether the initial balance will be used
            if (memberImport.getInitialCreditTransferType() != null || memberImport.getInitialDebitTransferType() != null) {
                request.setAttribute("hasBalance", true);
            }
        }
        request.setAttribute("lowercaseStatus", query.getStatus().name().toLowerCase());
        return query;
    }

    @Override
    protected boolean willExecuteQuery(final ActionContext context, final QueryParameters queryParameters) throws Exception {
        return true;
    }

}
