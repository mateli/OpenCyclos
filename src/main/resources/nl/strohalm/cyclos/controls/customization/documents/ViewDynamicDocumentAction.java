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

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.customization.documents.DynamicDocument;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.customization.DocumentService;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.EntityWithCustomFieldsWrapper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Form used to view a custom document
 * @author luis
 */

public class ViewDynamicDocumentAction extends BaseAction {

    private DocumentService          documentService;
    private MemberCustomFieldService memberCustomFieldService;
    private CustomizationHelper      customizationHelper;
    private CustomFieldHelper        customFieldHelper;

    public DocumentService getDocumentService() {
        return documentService;
    }

    public MemberCustomFieldService getMemberCustomFieldService() {
        return memberCustomFieldService;
    }

    @Inject
    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    @Inject
    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    @Inject
    public void setDocumentService(final DocumentService documentService) {
        this.documentService = documentService;
    }

    @Inject
    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final ViewDocumentForm form = context.getForm();
        final long memberId = form.getMemberId();
        final long documentId = form.getDocumentId();
        if (memberId <= 0L || documentId <= 0L) {
            throw new ValidationException();
        }
        Member member;
        DynamicDocument document;
        try {
            member = elementService.load(memberId, Element.Relationships.USER, Element.Relationships.GROUP, Member.Relationships.BROKER, Member.Relationships.CUSTOM_VALUES);
            document = (DynamicDocument) documentService.load(documentId);
        } catch (final Exception e) {
            throw new ValidationException();
        }
        if (document.isHasFormPage()) {
            final String formPageName = customizationHelper.formFile(document).getName();
            request.setAttribute("formPage", CustomizationHelper.DOCUMENT_PATH + formPageName);
        }
        final String documentPageName = customizationHelper.documentFile(document).getName();
        request.setAttribute("documentPage", CustomizationHelper.DOCUMENT_PATH + documentPageName);

        request.setAttribute("document", document);

        // Set the member inside a wrapper, allowing access to custom fields the same way as properties
        final List<MemberCustomField> customFields = customFieldHelper.onlyForGroup(memberCustomFieldService.list(), member.getMemberGroup());
        request.setAttribute("member", new EntityWithCustomFieldsWrapper(member, customFields, customFieldHelper));
        request.setAttribute("now", Calendar.getInstance());

        if (document.isHasFormPage() && RequestHelper.isGet(request)) {
            return context.getInputForward();
        } else {
            return context.getSuccessForward();
        }
    }
}
