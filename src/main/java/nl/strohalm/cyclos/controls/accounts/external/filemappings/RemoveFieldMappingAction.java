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

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FieldMapping;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMapping;
import nl.strohalm.cyclos.services.accounts.external.filemapping.FieldMappingService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove a field mapping
 * @author jefferson
 */
public class RemoveFieldMappingAction extends BaseAction {

    private FieldMappingService fieldMappingService;

    @Inject
    public void setFieldMappingService(final FieldMappingService fieldMappingService) {
        this.fieldMappingService = fieldMappingService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final RemoveFieldMappingForm form = context.getForm();
        final long fieldMappingId = form.getFieldMappingId();
        final FieldMapping fieldMapping = fieldMappingService.load(fieldMappingId, RelationshipHelper.nested(FieldMapping.Relationships.FILE_MAPPING, FileMapping.Relationships.EXTERNAL_ACCOUNT));
        final Long externalAccountId = fieldMapping.getFileMapping().getAccount().getId();
        fieldMappingService.remove(fieldMappingId);
        context.sendMessage("fieldMapping.removed");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "externalAccountId", externalAccountId);
    }

}
