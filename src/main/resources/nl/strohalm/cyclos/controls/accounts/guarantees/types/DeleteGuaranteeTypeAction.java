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
package nl.strohalm.cyclos.controls.accounts.guarantees.types;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeTypeService;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

public class DeleteGuaranteeTypeAction extends BaseAction {
    private GuaranteeTypeService guaranteeTypeService;

    @Override
    public ActionForward executeAction(final ActionContext context) throws Exception {
        final DeleteGuaranteeTypeForm form = context.getForm();
        final long id = form.getGuaranteeTypeId();
        if (id <= 0L) {
            throw new ValidationException();
        }
        try {
            guaranteeTypeService.remove(id);
            context.sendMessage("guaranteeType.removed");
        } catch (final PermissionDeniedException e) {
            throw e;
        } catch (final Exception e) {
            context.sendMessage("guaranteeType.error.removing");
        }
        return context.getSuccessForward();
    }

    @Inject
    public void setGuaranteeTypeService(final GuaranteeTypeService guaranteeTypeService) {
        this.guaranteeTypeService = guaranteeTypeService;
    }

}
