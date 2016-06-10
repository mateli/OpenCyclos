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
package nl.strohalm.cyclos.controls.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.mobile.exceptions.MobileException;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.exceptions.AccessDeniedException;
import nl.strohalm.cyclos.exceptions.LoggedOutException;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.LoginHelper;
import nl.strohalm.cyclos.utils.MessageHelper;
import nl.strohalm.cyclos.utils.conversion.UnitsConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * A base class for all mobile actions
 * @author luis
 */
public abstract class MobileBaseAction extends Action {

    private static final Log LOG = LogFactory.getLog(MobileBaseAction.class);

    /**
     * Process the forward
     */
    public static ActionForward processForward(final ActionForward forward, final HttpServletRequest request) {
        if (forward == null || !forward.getRedirect()) {
            return forward;
        }
        final ActionForward ret = new ActionForward(forward);
        ret.setPath(ret.getPath() + ";jsessionid=" + request.getSession().getId());
        return ret;
    }

    protected SettingsService settingsService;

    protected LoginHelper     loginHelper;

    protected ActionHelper    actionHelper;
    protected MessageHelper   messageHelper;

    /**
     * Perform some basic validation before calling the action execution itself.
     */
    @Override
    public final ActionForward execute(final ActionMapping actionMapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        try {
            MemberUser user = null;
            // Retrieve the current user
            try {
                user = validate(request, actionMapping);
            } catch (final MobileException e) {
                throw e;
            } catch (final AccessDeniedException e) {
                throw new MobileException("error.accessDenied");
            } catch (final LoggedOutException e) {
                request.getSession().invalidate();
                throw new MobileException("error.loggedOut");
            } catch (final Exception e) {
                actionHelper.generateLog(request, getServlet().getServletContext(), e);
                throw new MobileException();
            }

            MobileHelper.clearException(request);

            // Execute the action
            try {
                final MobileActionContext context = new MobileActionContext(actionMapping, actionForm, request, response, messageHelper, user);
                final ActionForward forward = executeAction(context);
                return processForward(forward, request);
            } catch (final MobileException e) {
                throw e;
            } catch (final Exception e) {
                actionHelper.generateLog(request, getServlet().getServletContext(), e);
                LOG.error("Application error on " + getClass().getName(), e);
                throw new MobileException();
            }
        } catch (final MobileException e) {
            return MobileHelper.sendException(actionMapping, request, e);
        }
    }

    @Inject
    public void setActionHelper(final ActionHelper actionHelper) {
        this.actionHelper = actionHelper;
    }

    @Inject
    public final void setLoginHelper(final LoginHelper loginHelper) {
        this.loginHelper = loginHelper;
    }

    @Inject
    public final void setMessageHelper(final MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @Inject
    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    /**
     * This method should be implemented to perform the action execution
     */
    protected abstract ActionForward executeAction(MobileActionContext context) throws Exception;

    protected UnitsConverter getUnitsConverter(final MobileActionContext context) {
        final LocalSettings settings = settingsService.getLocalSettings();
        return settings.getUnitsConverter(context.getCurrentAccountType().getCurrency().getPattern());
    }

    /**
     * When an error occours, if this method was called, return to it on the 'Back' link
     */
    protected void storeBookmark(final MobileActionContext context) {
        final ActionMapping actionMapping = context.getActionMapping();
        final HttpServletRequest request = context.getRequest();
        request.getSession().setAttribute("mobileBookmark", actionMapping.getPath());
    }

    /**
     * Validates the logged user, returning it
     */
    private MemberUser validate(final HttpServletRequest request, final ActionMapping actionMapping) throws Exception {
        final User user = loginHelper.validateLoggedUser(request);

        if (user instanceof MemberUser) {
            final String path = actionMapping.getPath();
            if (!path.contains("/wap") && !path.contains("/mobile")) {
                throw new AccessDeniedException();
            }
            return (MemberUser) user;
        } else {
            throw new MobileException("login.error.admin");
        }
    }

}
