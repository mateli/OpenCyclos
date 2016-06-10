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
import nl.strohalm.cyclos.entities.accounts.external.ExternalAccount;
import nl.strohalm.cyclos.entities.accounts.external.filemapping.FileMapping;
import nl.strohalm.cyclos.services.accounts.external.filemapping.FileMappingService;
import nl.strohalm.cyclos.utils.ActionHelper;

import org.apache.struts.action.ActionForward;

/**
 * Action used to reset a file mapping
 * @author jefferson
 */
public class ResetFileMappingAction extends BaseAction {

    private FileMappingService fileMappingService;

    @Inject
    public void setFileMappingService(final FileMappingService fileMappingService) {
        this.fileMappingService = fileMappingService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final ResetFileMappingForm form = context.getForm();
        final Long fileMappingId = form.getFileMappingId();
        final FileMapping fileMapping = fileMappingService.load(fileMappingId, FileMapping.Relationships.EXTERNAL_ACCOUNT);
        final ExternalAccount externalAccount = fileMapping.getAccount();
        fileMappingService.reset(fileMapping);
        context.sendMessage("fileMapping.removed");
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "externalAccountId", externalAccount.getId());
    }

}
