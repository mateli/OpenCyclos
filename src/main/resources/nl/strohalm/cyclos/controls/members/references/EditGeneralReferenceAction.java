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
package nl.strohalm.cyclos.controls.members.references;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.GeneralReference;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to view / edit a general reference details
 * @author luis
 */
public class EditGeneralReferenceAction extends BaseEditReferenceAction<GeneralReference> {
    private DataBinder<GeneralReference> dataBinder;

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        GeneralReference reference = resolveReference(context);
        final boolean isInsert = reference.isTransient();
        final GeneralReference generalReference = reference;
        reference = referenceService.save(generalReference);
        context.sendMessage("reference." + (isInsert ? "inserted" : "modified"));
        return ActionHelper.redirectWithParam(context.getRequest(), context.getSuccessForward(), "referenceId", reference.getId());
    }

    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final EditReferenceForm form = context.getForm();
        final HttpServletRequest request = context.getRequest();
        final long referenceId = form.getReferenceId();

        // Retrieve the generalReference
        GeneralReference reference;
        final AccountOwner accountOwner = context.getAccountOwner();
        if (referenceId > 0L) {
            if (form.getMemberId() == 0 && (accountOwner instanceof Member)) {
                form.setMemberId(((Member) accountOwner).getId());
            }
            reference = (GeneralReference) referenceService.load(referenceId, Reference.Relationships.FROM, Reference.Relationships.TO);
        } else {
            // Is a new general reference
            // We haven't received a reference id. Find by member
            if (form.getMemberId() <= 0L) {
                throw new ValidationException();
            }
            final Member loggedMember = (Member) accountOwner;
            Member member;
            try {
                member = elementService.load(form.getMemberId());
            } catch (final Exception e) {
                throw new ValidationException();
            }

            try {
                // Load the current reference
                reference = referenceService.loadGeneral(loggedMember, member);
            } catch (final EntityNotFoundException e) {
                // There's no reference from / to the member - we are inserting
                reference = new GeneralReference();
                reference.setFrom(loggedMember);
                reference.setTo(member);
            }
        }
        getDataBinder().writeAsString(form.getReference(), reference);

        final LocalSettings localSettings = settingsService.getLocalSettings();

        // Check whether the reference is editable
        final boolean editable = referenceService.canManage(reference);

        if (reference.isTransient() && !editable) {
            throw new ValidationException();
        }

        request.setAttribute("reference", reference);
        request.setAttribute("levels", localSettings.getReferenceLevelList());
        request.setAttribute("editable", editable);
    }

    @Override
    protected GeneralReference resolveReference(final ActionContext context) {
        final EditReferenceForm form = context.getForm();
        return getDataBinder().readFromString(form.getReference());
    }

    private DataBinder<GeneralReference> getDataBinder() {
        if (dataBinder == null) {
            final BeanBinder<GeneralReference> binder = BeanBinder.instance(GeneralReference.class);
            initBinder(binder);
            dataBinder = binder;
        }
        return dataBinder;
    }
}
