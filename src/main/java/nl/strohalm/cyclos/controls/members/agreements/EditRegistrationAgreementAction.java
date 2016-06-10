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

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.members.RegistrationAgreement;
import nl.strohalm.cyclos.services.elements.RegistrationAgreementService;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.HtmlConverter;
import nl.strohalm.cyclos.utils.conversion.IdConverter;

/**
 * Action used to edit a registration agreement
 * 
 * @author luis
 */
public class EditRegistrationAgreementAction extends BaseFormAction {

    private RegistrationAgreementService      registrationAgreementService;
    private DataBinder<RegistrationAgreement> dataBinder;

    @Inject
    public void setRegistrationAgreementService(final RegistrationAgreementService registrationAgreementService) {
        this.registrationAgreementService = registrationAgreementService;
    }

    @Override
    protected void formAction(final ActionContext context) throws Exception {
        final EditRegistrationAgreementForm form = context.getForm();
        final RegistrationAgreement registrationAgreement = getDataBinder().readFromString(form);
        final boolean isInsert = registrationAgreement.isTransient();
        registrationAgreementService.save(registrationAgreement);
        if (isInsert) {
            context.sendMessage("registrationAgreement.inserted");
        } else {
            context.sendMessage("registrationAgreement.modified");
        }
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditRegistrationAgreementForm form = context.getForm();
        final long id = form.getRegistrationAgreementId();
        RegistrationAgreement registrationAgreement;
        final boolean isInsert = id <= 0L;
        if (isInsert) {
            registrationAgreement = new RegistrationAgreement();
        } else {
            registrationAgreement = registrationAgreementService.load(id);
        }
        getDataBinder().writeAsString(form, registrationAgreement);
        request.setAttribute("registrationAgreement", registrationAgreement);
        request.setAttribute("isInsert", isInsert);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditRegistrationAgreementForm form = context.getForm();
        final RegistrationAgreement registrationAgreement = getDataBinder().readFromString(form);
        registrationAgreementService.validate(registrationAgreement);
    }

    private DataBinder<RegistrationAgreement> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<RegistrationAgreement> binder = BeanBinder.instance(RegistrationAgreement.class, "registrationAgreement");
            binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
            binder.registerBinder("contents", PropertyBinder.instance(String.class, "contents", HtmlConverter.instance()));
            dataBinder = binder;
        }
        return dataBinder;
    }

}
