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
package nl.strohalm.cyclos.controls.customization.documents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.documents.MemberDocument;
import nl.strohalm.cyclos.entities.customization.documents.StaticDocument;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.customization.exceptions.CannotUploadFileException;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

/**
 * Action used to edit a member static document
 * @author Jefferson Magno
 */
public class EditMemberDocumentAction extends EditStaticDocumentAction {

    private DataBinder<MemberDocument> dataBinder;

    @Override
    protected Class<? extends StaticDocument> getEntityType() {
        return MemberDocument.class;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditStaticDocumentForm form = context.getForm();
        MemberDocument document = getDataBinder().readFromString(form.getDocument());
        final boolean isInsert = document.getId() == null;
        try {
            final FormFile upload = form.getUpload();
            document = (MemberDocument) documentService.saveStatic(document, upload.getInputStream(), upload.getFileSize(), upload.getFileName(), upload.getContentType());

            context.sendMessage(isInsert ? "document.inserted" : "document.modified");
            request.setAttribute("document", document);
            return ActionHelper.redirectWithParam(request, context.getSuccessForward(), "documentId", document.getId());
        } catch (final IOException e) {
            throw new CannotUploadFileException(e);
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditMemberDocumentForm form = context.getForm();
        Member member;
        final long documentId = form.getDocumentId();
        MemberDocument document;
        if (documentId > 0L) {
            document = (MemberDocument) documentService.load(documentId);
            member = document.getMember();
        } else {
            final long memberId = form.getMemberId();
            if (memberId < 1) {
                throw new ValidationException();
            }
            member = EntityHelper.reference(Member.class, memberId);
            document = new MemberDocument();
            document.setMember(member);
        }
        final boolean byBroker = context.isBrokerOf(member) && permissionService.hasPermission(BrokerPermission.DOCUMENTS_MANAGE_MEMBER);
        final boolean adminCanManage = context.isAdmin() && permissionService.hasPermission(AdminMemberPermission.DOCUMENTS_MANAGE_MEMBER);

        getDataBinder().writeAsString(form.getDocument(), document);
        request.setAttribute("member", member);
        request.setAttribute("document", document);
        request.setAttribute("byBroker", byBroker);
        request.setAttribute("adminCanManage", adminCanManage);
        final List<MemberDocument.Visibility> visibilities = new ArrayList<MemberDocument.Visibility>();
        visibilities.add(MemberDocument.Visibility.BROKER);
        visibilities.add(MemberDocument.Visibility.MEMBER);
        if (!byBroker) {
            visibilities.add(MemberDocument.Visibility.ADMIN);
        }
        request.setAttribute("visibilities", visibilities);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditStaticDocumentForm form = context.getForm();
        final Document document = getDataBinder().readFromString(form.getDocument());
        documentService.validate(document, false);
    }

    private DataBinder<MemberDocument> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<MemberDocument> beanBinder = EditStaticDocumentAction.getDataBinder(MemberDocument.class);
            beanBinder.registerBinder("member", PropertyBinder.instance(Member.class, "member"));
            beanBinder.registerBinder("visibility", PropertyBinder.instance(MemberDocument.Visibility.class, "visibility"));
            dataBinder = beanBinder;
        }
        return dataBinder;
    }
}
