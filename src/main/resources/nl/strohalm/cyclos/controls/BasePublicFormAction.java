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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.ResponseHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action to be used where no logged user is expected, but a BaseFormAction-like behavior is expected
 * 
 * @author luis
 */
public class BasePublicFormAction extends BasePublicAction {

    protected ResponseHelper responseHelper;

    @Inject
    public void setResponseHelper(final ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    @Override
    protected final ActionForward executeAction(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        if (RequestHelper.isGet(request)) {
            return handleDisplay(mapping, actionForm, request, response);
        } else if (RequestHelper.isValidation(request)) {
            return handleValidation(mapping, actionForm, request, response);
        } else {
            return handleSubmit(mapping, actionForm, request, response);
        }
    }

    /**
     * Simple for called on a POST request, without returning the forward
     */
    protected void formAction(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    }

    /**
     * Handles GET requests, by default calling the {@link #prepareForm(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)} method
     * and returning the input forward
     */
    protected ActionForward handleDisplay(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        prepareForm(mapping, actionForm, request, response);
        return mapping.getInputForward();
    }

    /**
     * Handles POST requests, by default calling the {@link #formAction(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)} method
     * and returning a forward named 'success'
     */
    protected ActionForward handleSubmit(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        formAction(mapping, actionForm, request, response);
        return mapping.findForward("success");
    }

    /**
     * Handles Ajax validation requests, by default calling the
     * {@link #validateForm(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)} method and returning null
     */
    protected ActionForward handleValidation(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) {
        try {
            validateForm(mapping, actionForm, request, response);
            responseHelper.writeValidationSuccess(response);
        } catch (final ValidationException e) {
            responseHelper.writeValidationErrors(response, e);
        }
        return null;
    }

    /**
     * Simple for called on a GET request, without returning the forward
     */
    protected void prepareForm(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    }

    /**
     * Simple for called on an Ajax validation request, which is expected to throw ValidationException on validation errors
     */
    protected void validateForm(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws ValidationException {
    }

}
