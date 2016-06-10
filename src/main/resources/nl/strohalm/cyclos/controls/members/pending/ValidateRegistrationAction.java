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
package nl.strohalm.cyclos.controls.members.pending;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.BasePublicAction;
import nl.strohalm.cyclos.entities.customization.files.CustomizedFile.Type;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.PendingMember;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.services.elements.exceptions.RegistrationAgreementNotAcceptedException;
import nl.strohalm.cyclos.services.groups.GroupFilterService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.CustomizationHelper;
import nl.strohalm.cyclos.utils.CustomizationHelper.CustomizationData;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used to validate a public registration
 * @author luis
 */
public class ValidateRegistrationAction extends BasePublicAction {

    private GroupFilterService  groupFilterService;
    private CustomizationHelper customizationHelper;

    @Inject
    public void setCustomizationHelper(final CustomizationHelper customizationHelper) {
        this.customizationHelper = customizationHelper;
    }

    @Inject
    public void setGroupFilterService(final GroupFilterService groupFilterService) {
        this.groupFilterService = groupFilterService;
    }

    @Override
    protected ActionForward executeAction(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        // The only interesting session attribute for us is inContainerPage. All others will be cleared out
        final HttpSession session = request.getSession(false);
        @SuppressWarnings("unchecked")
        final Enumeration<String> attributeNames = session.getAttributeNames();
        final ArrayList<String> attributesToBeRemoved = new ArrayList<String>();
        // in order to prevent ConcurrentModificationException, first store the attribs to be removed
        while (attributeNames.hasMoreElements()) {
            final String name = attributeNames.nextElement();
            if (!"inContainerPage".equals(name)) {
                attributesToBeRemoved.add(name);
            }
        }
        // and after that delete them
        for (final String name : attributesToBeRemoved) {
            session.removeAttribute(name);
        }

        final String validationKeyMessage = messageHelper.message("pendingMember.validationKey");
        final ValidateRegistrationForm form = (ValidateRegistrationForm) actionForm;
        final String key = form.getKey();

        // Load the pending member, in order to check the container url
        PendingMember pendingMember;
        try {
            pendingMember = elementService.loadPendingMemberByKey(key, PendingMember.Relationships.MEMBER_GROUP);
        } catch (final EntityNotFoundException e) {
            // The key is invalid
            return ActionHelper.sendError(mapping, request, response, "errors.invalid", validationKeyMessage);
        }

        // Check for the container url
        final String containerUrl = findContainerUrl(pendingMember);
        if (containerUrl != null) {
            final boolean inContainerPage = Boolean.TRUE.equals(session.getAttribute("inContainerPage"));
            if (!inContainerPage) {
                session.setAttribute("inContainerPage", true);
                session.setAttribute("instantRedirectTo", request.getContextPath() + "/do" + mapping.getPath() + "?key=" + key);
                return new ActionForward(containerUrl, true);
            }
        }

        // Check for a non-empty key
        if (StringUtils.isEmpty(key)) {
            return ActionHelper.sendError(mapping, request, response, "errors.required", validationKeyMessage);
        }

        // Process the validation
        try {
            Member member;
            try {
                member = elementService.publicValidateRegistration(key);
            } catch (final EntityNotFoundException e) {
                // The key is invalid
                return ActionHelper.sendError(mapping, request, response, "errors.invalid", validationKeyMessage);
            } catch (final RegistrationAgreementNotAcceptedException e) {
                // He should accept the agreement first
                return ActionHelper.redirectWithParam(request, mapping.findForward("acceptAgreement"), "key", key);
            }

            // Set the correct session attribute for customized login page
            String loginParamName = null;
            Object loginParamValue = null;
            final MemberGroup group = member.getMemberGroup();
            if (StringUtils.isNotEmpty(group.getLoginPageName())) {
                loginParamName = "groupId";
                loginParamValue = group.getId();
            } else {
                // Try by group filter
                for (final GroupFilter filter : group.getGroupFilters()) {
                    if (filter.getLoginPageName() != null) {
                        loginParamName = "groupFilterId";
                        loginParamValue = filter.getId();
                        break;
                    }
                }
            }
            if (loginParamName != null) {
                session.setAttribute("loginParamName", loginParamName);
                session.setAttribute("loginParamValue", loginParamValue);
            }

            // We will send the flow to the error page not to showing an error, but the created message
            final boolean passwordGenerated = member.getMemberUser().isPasswordGenerated();
            String messageKey;
            if (!member.isActive()) {
                messageKey = "createMember.public.awaitingActivation";
            } else if (passwordGenerated) {
                messageKey = "createMember.public.awaitingPassword";
            } else {
                messageKey = "createMember.public.validated";
            }
            ActionHelper.sendError(mapping, request, response, messageKey, member.getUsername());
            return mapping.findForward("confirmation");
        } catch (final ValidationException e) {
            return ActionHelper.handleValidationException(mapping, request, response, e);
        } catch (final Exception e) {
            actionHelper.generateLog(request, getServlet().getServletContext(), e);
            return ActionHelper.sendError(mapping, request, response, null);
        }
    }

    private String findContainerUrl(final PendingMember pendingMember) {
        return LoggedUser.runAsSystem(new Callable<String>() {
            @Override
            public String call() throws Exception {
                final MemberGroup group = pendingMember.getMemberGroup();
                final CustomizationData customization = customizationHelper.findCustomizationOf(Type.STATIC_FILE, group, null, "login.jsp");
                switch (customization.getLevel()) {
                    case GROUP:
                        return group.getContainerUrl();
                    case GROUP_FILTER:
                        final GroupFilter groupFilter = groupFilterService.load(customization.getId());
                        return groupFilter.getContainerUrl();
                }
                // Get the container url if not have from group / group filter already
                final LocalSettings localSettings = settingsService.getLocalSettings();
                return localSettings.getContainerUrl();
            }
        });
    }
}
