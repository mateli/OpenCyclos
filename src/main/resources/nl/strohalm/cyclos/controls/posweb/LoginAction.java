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
package nl.strohalm.cyclos.controls.posweb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.access.LoginForm;
import nl.strohalm.cyclos.controls.posweb.PosWebHelper.Action;
import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.permissions.PermissionService;
import nl.strohalm.cyclos.services.preferences.ReceiptPrinterSettingsService;
import nl.strohalm.cyclos.utils.LoginHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used for posweb login
 * @author luis
 */
public class LoginAction extends nl.strohalm.cyclos.controls.access.LoginAction {

    private PermissionService             permissionService;

    private ReceiptPrinterSettingsService receiptPrinterSettingsService;

    @Inject
    public void setPermissionService(final PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Inject
    public void setReceiptPrinterSettingsService(final ReceiptPrinterSettingsService receiptPrinterSettingsService) {
        this.receiptPrinterSettingsService = receiptPrinterSettingsService;
    }

    @Override
    protected ActionForward alreadyLoggedForward(final ActionMapping mapping, final HttpServletRequest request, final HttpServletResponse response, final LoginForm form, final User user) {
        return loginForward(mapping, request, response, form, user);
    }

    @Override
    protected ActionForward doLogin(final ActionMapping mapping, final HttpServletRequest request, final HttpServletResponse response, final LoginForm form) {
        // Ensure the principal for login will always be user
        form.setPrincipalType(Principal.USER.name());
        HttpSession session = request.getSession();
        session.setAttribute("isPosWeb", true);
        session.setAttribute("isWebShop", false);
        final ActionForward forward = super.doLogin(mapping, request, response, form);
        // we need to get the new session after login
        session = request.getSession();
        // Get which options to show
        final User loggedUser = LoginHelper.getLoggedUser(request);
        if (loggedUser != null) {
            boolean showMake;
            boolean showReceive;
            boolean showTransactions;
            if (loggedUser instanceof OperatorUser) {
                showMake = permissionService.hasPermission(OperatorPermission.PAYMENTS_POSWEB_MAKE_PAYMENT);
                showReceive = permissionService.hasPermission(OperatorPermission.PAYMENTS_POSWEB_RECEIVE_PAYMENT);
                showTransactions = permissionService.hasPermission(OperatorPermission.ACCOUNT_ACCOUNT_INFORMATION);
            } else {
                final Action action = PosWebHelper.getAction(request);
                showMake = action.canPay();
                showReceive = action.canReceive();
                showTransactions = true;
            }
            session.setAttribute("showMake", showMake);
            session.setAttribute("showReceive", showReceive);
            session.setAttribute("showTransactions", showTransactions);

            final boolean hasReceiptPrinters = CollectionUtils.isNotEmpty(receiptPrinterSettingsService.list());
            session.setAttribute("hasReceiptPrinters", hasReceiptPrinters);
        }

        return forward;
    }

    @Override
    protected ActionForward handleDisplay(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) {
        request.setAttribute("isOperator", PosWebHelper.isOperator(request));
        return super.handleDisplay(mapping, actionForm, request, response);
    }

    @Override
    protected boolean isMemberRequired(final HttpServletRequest request) {
        return PosWebHelper.isOperator(request);
    }

    @Override
    protected ActionForward loginForward(final ActionMapping mapping, final HttpServletRequest request, final HttpServletResponse response, final LoginForm form, final User user) {
        boolean canPay;
        boolean canReceive;
        if (user instanceof MemberUser) {
            // Members depend on the entry url
            final Action action = PosWebHelper.getAction(request);
            canPay = action.canPay();
            canReceive = action.canReceive();
            if (elementService.shallAcceptAgreement(((MemberUser) user).getMember())) {
                // Should accept a registration agreement first
                request.getSession().setAttribute("shallAcceptRegistrationAgreement", true);
                return mapping.findForward("poswebAcceptRegistrationAgreement");
            }
        } else {
            // Operators depends on permissions
            canPay = permissionService.hasPermission(OperatorPermission.PAYMENTS_POSWEB_MAKE_PAYMENT);
            canReceive = permissionService.hasPermission(OperatorPermission.PAYMENTS_POSWEB_RECEIVE_PAYMENT);
        }
        // Redirect the operator according to the permissions
        if (!canPay && !canReceive) {
            // An operator with no external permissions is logged!
            request.getSession().invalidate();
            throw new PermissionDeniedException();
        }
        if (accessService.hasPasswordExpired()) {
            // Should change an expired password
            request.getSession().setAttribute("expiredPassword", true);
            return mapping.findForward("poswebChangeExpiredPassword");
        } else if (canReceive) {
            return mapping.findForward("receivePayment");
        } else {
            return mapping.findForward("makePayment");
        }
    }

    @Override
    protected Class<? extends User> requiredUserType(final ActionMapping mapping, final HttpServletRequest request, final HttpServletResponse response, final LoginForm form) {
        if (PosWebHelper.isOperator(request)) {
            return OperatorUser.class;
        } else {
            return MemberUser.class;
        }
    }

    @Override
    protected String resolveErrorReturnTo(final ActionMapping mapping, final HttpServletRequest request, final HttpServletResponse response, final LoginForm form) {
        return PosWebHelper.loginUrl(request);
    }
}
