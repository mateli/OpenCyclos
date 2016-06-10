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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseAction;
import nl.strohalm.cyclos.entities.members.RegistrationAgreement;
import nl.strohalm.cyclos.services.elements.RegistrationAgreementService;

import org.apache.struts.action.ActionForward;

/**
 * Action used to list all registration agreements
 * @author luis
 */

public class ListRegistrationAgreementsAction extends BaseAction {

    private RegistrationAgreementService registrationAgreementService;

    @Inject
    public void setRegistrationAgreementService(final RegistrationAgreementService registrationAgreementService) {
        this.registrationAgreementService = registrationAgreementService;
    }

    @Override
    protected ActionForward executeAction(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final List<RegistrationAgreement> registrationAgreements = registrationAgreementService.listAll();
        request.setAttribute("registrationAgreements", registrationAgreements);
        request.setAttribute("editable", permissionService.hasPermission(AdminSystemPermission.REGISTRATION_AGREEMENTS_MANAGE));
        return context.getInputForward();
    }
}
