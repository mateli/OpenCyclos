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
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.imports.MemberImport;
import nl.strohalm.cyclos.services.elements.MemberImportService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.csv.UnknownColumnException;
import nl.strohalm.cyclos.utils.validation.RequiredError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

/**
 * Action used to import members
 * 
 * @author luis
 */
public class ImportMembersAction extends BaseFormAction {

    private MemberImportService      memberImportService;
    private DataBinder<MemberImport> dataBinder;

    @Inject
    public void setMemberImportService(final MemberImportService memberImportService) {
        this.memberImportService = memberImportService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final ImportMembersForm form = context.getForm();
        final FormFile upload = form.getUpload();
        if (upload == null || upload.getFileSize() == 0) {
            throw new ValidationException("upload", "memberImport.file", new RequiredError());
        }
        MemberImport memberImport = getDataBinder().readFromString(form.getImport());
        try {
            memberImport = memberImportService.importMembers(memberImport, upload.getInputStream());
            return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "importId", memberImport.getId());
        } catch (final UnknownColumnException e) {
            return context.sendError("general.error.csv.unknownColumn", e.getColumn());
        } finally {
            upload.destroy();
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();

        // Get the possible groups
        final GroupQuery groupQuery = new GroupQuery();
        groupQuery.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
        groupQuery.setManagedBy(context.<AdminGroup> getGroup());
        groupQuery.setStatus(Group.Status.NORMAL);
        final List<? extends Group> groups = groupService.search(groupQuery);
        request.setAttribute("groups", groups);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final ImportMembersForm form = context.getForm();
        final MemberImport memberImport = getDataBinder().readFromString(form.getImport());
        memberImportService.validate(memberImport);
    }

    private DataBinder<MemberImport> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<MemberImport> binder = BeanBinder.instance(MemberImport.class);
            binder.registerBinder("group", PropertyBinder.instance(MemberGroup.class, "group"));
            binder.registerBinder("accountType", PropertyBinder.instance(MemberAccountType.class, "accountType"));
            binder.registerBinder("initialDebitTransferType", PropertyBinder.instance(TransferType.class, "initialDebitTransferType"));
            binder.registerBinder("initialCreditTransferType", PropertyBinder.instance(TransferType.class, "initialCreditTransferType"));
            dataBinder = binder;
        }
        return dataBinder;
    }

}
