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
package nl.strohalm.cyclos.controls.members.agreements;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.services.elements.RegistrationAgreementService;

import org.apache.struts.action.ActionForward;

/**
 * Action used to remove a registration agreement
 * 
 * @author luis
 */
public class RemoveRegistrationAgreementAction extends BaseAction {

    private RegistrationAgreementService registrationAgreementService;

    @Inject
    public void setRegistrationAgreementService(final RegistrationAgreementService registrationAgreementService) {
        this.registrationAgreementService = registrationAgreementService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final RemoveRegistrationAgreementForm form = context.getForm();
        try {
            registrationAgreementService.remove(form.getRegistrationAgreementId());
            context.sendMessage("registrationAgreement.removed");
        } catch (final Exception e) {
            context.sendMessage("registrationAgreement.error.removing");
        }
        return context.getSuccessForward();
    }

}
