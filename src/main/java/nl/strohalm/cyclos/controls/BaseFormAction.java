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
package nl.strohalm.cyclos.controls;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used for common for actions, that handler 3 states:
 * <ul>
 * <li>Form preparation: The first request, usually a get2-state case: the first request prepares the form and the second is a form submission
 * @author luis
 */
public abstract class BaseFormAction extends BaseAction {

    protected ResponseHelper responseHelper;

    @Inject
    public void setResponseHelper(final ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    @Override
    protected final ActionForward executeAction(final ActionContext context) throws Exception {
        if (isFormPreparation(context)) {
            return handleDisplay(context);
        } else if (isFormValidation(context)) {
            return handleValidation(context);
        } else if (isFormSubmission(context)) {
            return handleSubmit(context);
        } else {
            return context.sendError("errors.invalid_request");
        }
    }

    /**
     * Method use to handle a form submission
     */
    protected void formAction(final ActionContext context) throws Exception {
    }

    /**
     * Handles form prepare, returning the ActionForward
     */
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {
        prepareForm(context);
        return context.getActionMapping().getInputForward();
    }

    /**
     * Handles form submission, returning the ActionForward
     */
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        formAction(context);
        return context.getSuccessForward();
    }

    /**
     * Handles the form validation, returning the action forward
     */
    protected ActionForward handleValidation(final ActionContext context) {
        try {
            validateForm(context);
            responseHelper.writeValidationSuccess(context.getResponse());
        } catch (final PermissionDeniedException e) {
            responseHelper.writeValidationErrors(context.getResponse(), new ValidationException("error.permissionDenied"));
        } catch (final ValidationException e) {
            responseHelper.writeValidationErrors(context.getResponse(), e);
        }
        return null;
    }

    /**
     * Returns if this request is for form preparing
     */
    protected boolean isFormPreparation(final ActionContext context) {
        return RequestHelper.isGet(context.getRequest());
    }

    /**
     * Returns if the request is a form submission
     */
    protected boolean isFormSubmission(final ActionContext context) {
        return RequestHelper.isPost(context.getRequest());
    }

    /**
     * Returns if this request is for form validation
     */
    protected boolean isFormValidation(final ActionContext context) {
        return RequestHelper.isValidation(context.getRequest());
    }

    /**
     * Method use to prepare a form for being displayed
     */
    protected void prepareForm(final ActionContext context) throws Exception {
    }

    /**
     * Validates the form
     */
    protected void validateForm(final ActionContext context) {
    }
}
