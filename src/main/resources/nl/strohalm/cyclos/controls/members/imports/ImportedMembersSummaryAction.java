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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.members.imports.MemberImport;
import nl.strohalm.cyclos.services.elements.MemberImportService;
import nl.strohalm.cyclos.services.transactions.exceptions.CreditsException;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Action used to view the import summary and to confirm the import
 * 
 * @author luis
 */
public class ImportedMembersSummaryAction extends BaseFormAction {

    private MemberImportService memberImportService;

    @Inject
    public void setMemberImportService(final MemberImportService memberImportService) {
        this.memberImportService = memberImportService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final ImportedMembersSummaryForm form = context.getForm();
        final MemberImport memberImport = getImport(context);
        try {
            memberImportService.processImport(memberImport, form.isSendActivationMail());
        } catch (final CreditsException e) {
            throw new ValidationException(actionHelper.resolveErrorKey(e), actionHelper.resolveParameters(e));
        }
        context.sendMessage("memberImport.processed");
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final MemberImport memberImport = getImport(context);
        final HttpServletRequest request = context.getRequest();
        request.setAttribute("memberImport", memberImport);
        request.setAttribute("summary", memberImportService.getSummary(memberImport));
    }

    private MemberImport getImport(final ActionContext context) {
        final ImportedMembersSummaryForm form = context.getForm();
        return memberImportService.load(form.getImportId(), MemberImport.Relationships.GROUP, RelationshipHelper.nested(MemberImport.Relationships.ACCOUNT_TYPE, AccountType.Relationships.CURRENCY), MemberImport.Relationships.INITIAL_CREDIT_TRANSFER_TYPE, MemberImport.Relationships.INITIAL_DEBIT_TRANSFER_TYPE);
    }

}
