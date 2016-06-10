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

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.entities.access.AdminUser;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.access.TransactionPassword;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.access.User.TransactionPasswordStatus;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.access.AccessService;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.groups.GroupService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.MessageHelper;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.MessageResourcesLoadedListener;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.SpringHelper;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Abstract class for the Struts context
 * @author luis
 */
public abstract class AbstractActionContext implements Serializable, MessageResolver {

    private static final long         serialVersionUID = 7565129435156786075L;

    private final ActionForm          form;
    private final ActionMapping       actionMapping;
    private final HttpServletRequest  request;
    private final HttpServletResponse response;
    private final MessageHelper       messageHelper;
    private final User                user;

    public AbstractActionContext(final ActionMapping actionMapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response, final MessageHelper messageHelper, final User user) {
        if (actionMapping == null || request == null || response == null || messageHelper == null || user == null) {
            throw new NullPointerException();
        }

        this.actionMapping = actionMapping;
        form = actionForm;
        this.request = request;
        this.response = response;
        this.messageHelper = messageHelper;
        this.user = user;
    }

    /**
     * 
     * @see nl.strohalm.cyclos.utils.MessageResolver#addMessageResourcesLoadedListener(nl.strohalm.cyclos.utils.MessageResourcesLoadedListener)
     */
    @Override
    public void addMessageResourcesLoadedListener(final MessageResourcesLoadedListener listener) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks the transaction password for the logged user
     */
    public void checkTransactionPassword(final String transactionPassword) {
        try {
            final AccessService accessService = SpringHelper.bean(getServletContext(), AccessService.class);
            accessService.checkTransactionPassword(transactionPassword);
        } catch (final InvalidCredentialsException e) {
            throw new ValidationException("transactionPassword.error.invalid");
        } catch (final BlockedCredentialsException e) {
            final HttpSession session = getSession();
            session.setAttribute("errorReturnTo", session.getAttribute("pathPrefix") + "/home");
            throw new ValidationException("transactionPassword.error.blockedByTrials");
        } catch (final RuntimeException e) {
            throw e;
        }
    }

    /**
     * Finds a forward with the given name, returning null if not found
     */
    public ActionForward findForward(final String name) {
        return actionMapping.findForward(name);
    }

    /**
     * Returns the logged user's account owner
     */
    public AccountOwner getAccountOwner() {
        try {
            final Element element = getElement();
            return element.getAccountOwner();
        } catch (final NullPointerException e) {
            return null;
        }
    }

    /**
     * Returns the struts action mapping
     */
    public ActionMapping getActionMapping() {
        return actionMapping;
    }

    /**
     * Returns the logged element
     */
    @SuppressWarnings("unchecked")
    public <E extends Element> E getElement() {
        return (E) user.getElement();
    }

    /**
     * Returns the current action's form
     */
    @SuppressWarnings("unchecked")
    public <F extends ActionForm> F getForm() {
        return (F) form;
    }

    /**
     * Returns the logged element's group
     */
    @SuppressWarnings("unchecked")
    public <G extends Group> G getGroup() {
        final Element element = getElement();
        return (G) element.getGroup();
    }

    /**
     * Returns an action forward to the current action's input path
     */
    public ActionForward getInputForward() {
        return actionMapping.getInputForward();
    }

    /**
     * Returns a prefix for paths according to the logged user
     */
    public String getPathPrefix() {
        return RequestHelper.getPathPrefix(request);
    }

    /**
     * Returns the current request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Returns the current response
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Returns the current servlet context
     */
    public ServletContext getServletContext() {
        return getSession().getServletContext();
    }

    /**
     * Returns the current session
     */
    public HttpSession getSession() {
        return request.getSession();
    }

    /**
     * Returns the forward named 'success'
     */
    public ActionForward getSuccessForward() {
        return actionMapping.findForward("success");
    }

    /**
     * Returns the logged user
     */
    @SuppressWarnings("unchecked")
    public <U extends User> U getUser() {
        return (U) user;
    }

    /**
     * Returns the response's writer
     */
    public Writer getWriter() throws IOException {
        return response.getWriter();
    }

    /**
     * Returns whether the logged user is an administrator
     */
    public boolean isAdmin() {
        return user instanceof AdminUser;
    }

    /**
     * Returns whether the logged user is a broker
     */
    public boolean isBroker() {
        if (!isMember()) {
            return false;
        }
        final Member member = getElement();
        return member.getMemberGroup().isBroker();
    }

    /**
     * Returns whether the logged user is a member (a broker is a member too)
     */
    public boolean isMember() {
        return user instanceof MemberUser;
    }

    /**
     * Returns whether the logged user is a member but not a broker
     */
    public boolean isMemberAndNotBroker() {
        return isMember() && !isBroker();
    }

    /**
     * Returns whether the logged user is an operator
     */
    public boolean isOperator() {
        return user instanceof OperatorUser;
    }

    /**
     * Returns whether the transaction password is used for the logged user
     */
    public boolean isTransactionPasswordEnabled() {
        Group loggedGroup = getGroup();
        if (loggedGroup instanceof OperatorGroup) {
            final GroupService groupService = SpringHelper.bean(getServletContext(), GroupService.class);
            loggedGroup = groupService.load(loggedGroup.getId(), RelationshipHelper.nested(OperatorGroup.Relationships.MEMBER, Element.Relationships.GROUP));
        }
        final TransactionPassword transactionPassword = loggedGroup.getBasicSettings().getTransactionPassword();
        return transactionPassword.isUsed();
    }

    public boolean isTransactionPasswordEnabled(final AccountType accountType) {
        if (!isTransactionPasswordEnabled()) {
            return false;
        } else if (isAdmin()) {
            return true; // the group settings is true
        } else { // checks the member-group settings
            final Member member = (Member) getAccountOwner();
            final GroupService groupService = SpringHelper.bean(getServletContext(), GroupService.class);
            try {
                final MemberGroupAccountSettings mgas = groupService.loadAccountSettings(member.getGroup().getId(), accountType.getId());
                return mgas.isTransactionPasswordRequired();
            } catch (final EntityNotFoundException e) {
                return false;
            }
        }
    }

    /**
     * Returns a translation message with the given key / arguments
     */
    @Override
    public String message(final String key, final List<?> args) {
        return messageHelper.message(key, args);
    }

    /**
     * Returns a translation message with the given key / arguments
     */
    @Override
    public String message(final String key, final Object... args) {
        return messageHelper.message(key, args);
    }

    /**
     * Sends a message to the next page, with the given key / arguments
     */
    public void sendMessage(final String key, final Object... arguments) {
        ActionHelper.sendMessage(getRequest(), getResponse(), key, arguments);
    }

    /**
     * Throws a ValidationException if the transaction password is not active
     */
    public void validateTransactionPassword() {
        validateTransactionPassword(getUser().getTransactionPasswordStatus());
    }

    protected void validateTransactionPassword(final TransactionPasswordStatus tpStatus) {
        String errorKey = null;
        switch (tpStatus) {
            case BLOCKED:
                errorKey = "transactionPassword.error.blocked";
                break;
            case PENDING:
            case NEVER_CREATED:
                errorKey = "transactionPassword.error.pending";
                break;
        }
        if (errorKey != null) {
            request.getSession().setAttribute("errorReturnTo", request.getSession().getAttribute("pathPrefix") + "/home");
            request.getSession().setAttribute("errorButtonKey", "global.ok");
            throw new ValidationException(errorKey);
        }
    }
}
