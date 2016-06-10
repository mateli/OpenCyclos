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
package nl.strohalm.cyclos.controls.accounts.external.filemappings;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMapping;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMappingWithFields;
import nl.strohalm.cyclos.services.accounts.external.filemapping.FieldMappingService;
import nl.strohalm.cyclos.services.accounts.external.filemapping.FileMappingService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to set the field mappings order
 * @author Jefferson Magno
 */
public class SetFieldMappingsOrderAction extends BaseFormAction {

    private FieldMappingService fieldMappingService;
    private FileMappingService  fileMappingService;

    @Inject
    public void setFieldMappingService(final FieldMappingService fieldMappingService) {
        this.fieldMappingService = fieldMappingService;
    }

    @Inject
    public void setFileMappingService(final FileMappingService fileMappingService) {
        this.fileMappingService = fileMappingService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final SetFieldMappingsOrderForm form = context.getForm();
        final long fileMappingId = form.getFileMappingId();
        final FileMapping fileMapping = fileMappingService.load(fileMappingId, FileMapping.Relationships.EXTERNAL_ACCOUNT);
        final Long externalAccountId = fileMapping.getAccount().getId();
        fieldMappingService.setOrder(form.getFieldsIds());
        context.sendMessage("fieldMapping.orderModified");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "externalAccountId", externalAccountId);
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final SetFieldMappingsOrderForm form = context.getForm();
        final long fileMappingId = form.getFileMappingId();
        if (fileMappingId <= 0) {
            throw new ValidationException();
        }
        final FileMappingWithFields fileMapping = (FileMappingWithFields) fileMappingService.load(fileMappingId, FileMappingWithFields.Relationships.FIELDS);
        request.setAttribute("fieldMappings", fileMapping.getFields());
        request.setAttribute("fileMappingId", fileMappingId);
    }

}
