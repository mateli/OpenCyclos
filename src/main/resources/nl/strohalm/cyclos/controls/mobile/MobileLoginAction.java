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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.access.LoginForm;
import nl.strohalm.cyclos.controls.mobile.exceptions.InvalidUserForMobileException;
import nl.strohalm.cyclos.controls.mobile.exceptions.MobileException;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupFilter;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.AccessDeniedException;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.AccessService;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.services.access.exceptions.AlreadyConnectedException;
import nl.strohalm.cyclos.services.access.exceptions.BlockedCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InactiveMemberException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidCredentialsException;
import nl.strohalm.cyclos.services.access.exceptions.InvalidUserForChannelException;
import nl.strohalm.cyclos.services.access.exceptions.LoginException;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.accounts.AccountTypeService;
import nl.strohalm.cyclos.services.accounts.MemberAccountTypeQuery;
import nl.strohalm.cyclos.services.groups.GroupFilterService;
import nl.strohalm.cyclos.services.groups.GroupService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.LoginHelper;
import nl.strohalm.cyclos.utils.MessageHelper;
import nl.strohalm.cyclos.utils.RequestHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action used for login in mobile access
 * @author luis
 */
public class MobileLoginAction extends Action {

    private static final Log   LOG = LogFactory.getLog(MobileLoginAction.class);
    private AccessService      accessService;
    private AccountService     accountService;
    private AccountTypeService accountTypeService;
    private ChannelService     channelService;
    private GroupService       groupService;
    private GroupFilterService groupFilterService;
    protected ActionHelper     actionHelper;
    protected LoginHelper      loginHelper;
    protected MessageHelper    messageHelper;

    /**
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward execute(final ActionMapping actionMapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        final HttpSession session = request.getSession();

        // Test a previously logged user
        final User user = LoginHelper.getLoggedUser(request);
        if (user != null) {
            if (user instanceof MemberUser) {
                final String channel = MobileHelper.mobileChannel(request);
                if (!accessService.isChannelEnabledForMember(channel, ((MemberUser) user).getMember())) {
                    session.invalidate();
                    return MobileHelper.sendException(actionMapping, request, new InvalidUserForMobileException());
                } else {
                    return MobileHelper.getHomeForward(actionMapping, request);
                }
            } else {
                session.invalidate();
                return MobileHelper.sendException(actionMapping, request, new InvalidUserForMobileException());
            }
        }

        // Check for a query string
        final String queryString = StringUtils.trimToNull(request.getQueryString());

        if (queryString != null) {
            // Try to find a group filter
            try {
                final GroupFilter groupFilter = groupFilterService.findByLoginPageName(queryString);
                request.setAttribute("styleGroupFilter", groupFilter);
            } catch (final EntityNotFoundException e) {
                // Try to find a group
                try {
                    final Group group = groupService.findByLoginPageName(queryString);
                    request.setAttribute("styleGroup", group);
                } catch (final EntityNotFoundException e1) {
                    // Ignore
                }
            }
        }
        if (RequestHelper.isGet(request)) { // only in this case we should store the queryString
            session.setAttribute("loginQueryString", queryString);
        }

        ActionForward forward = null;
        try {
            // Check if is a page preparation or execution
            if (RequestHelper.isGet(request)) {
                forward = prepareForm(actionMapping, actionForm, request, response);
            } else if (RequestHelper.isPost(request)) {
                forward = doLogin(actionMapping, actionForm, request, response);
            }
        } catch (final MobileException e) {
            return MobileHelper.sendException(actionMapping, request, e);
        }
        return MobileBaseAction.processForward(forward, request);
    }

    @Inject
    public void setAccessService(final AccessService accessService) {
        this.accessService = accessService;
    }

    @Inject
    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Inject
    public void setAccountTypeService(final AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Inject
    public void setActionHelper(final ActionHelper actionHelper) {
        this.actionHelper = actionHelper;
    }

    @Inject
    public void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @Inject
    public void setGroupFilterService(final GroupFilterService groupFilterService) {
        this.groupFilterService = groupFilterService;
    }

    @Inject
    public void setGroupService(final GroupService groupService) {
        this.groupService = groupService;
    }

    @Inject
    public final void setLoginHelper(final LoginHelper loginHelper) {
        this.loginHelper = loginHelper;
    }

    @Inject
    public void setMessageHelper(final MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    private Map<String, String> createLink(final HttpServletRequest request, final String label, final String paramName, final String paramValue) {
        final Map<String, String> link = new HashMap<String, String>();
        link.put("label", label);
        link.put("paramName", paramName);
        link.put("paramValue", paramValue);
        return link;
    }

    @SuppressWarnings("unchecked")
    private ActionForward doLogin(final ActionMapping actionMapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            final String channelName = MobileHelper.mobileChannel(request);
            final Channel channel = channelService.loadByInternalName(channelName);

            final LoginForm form = (LoginForm) actionForm;
            final String principal = form.getPrincipal();

            // Resolve the credentials
            String credentials = form.getPassword();
            if (channel.getCredentials() == Credentials.TRANSACTION_PASSWORD) {
                // Ensure transaction password is uppercased
                credentials = credentials.toUpperCase();
            }

            // Do the login
            User user;
            try {
                user = loginHelper.login(MemberUser.class, form.getPrincipalType(), null, principal, credentials, channelName, request, response);
                // Get the session again, as a new one might be generated after logging in
                session = request.getSession();
            } catch (final AccessDeniedException e) {
                session.invalidate();
                throw new InvalidUserForMobileException();
            } catch (final AlreadyConnectedException e) {
                return MobileHelper.sendException(actionMapping, request, new MobileException("login.error.alreadyConnected"));
            } catch (final InvalidUserForChannelException e) {
                session.invalidate();
                return MobileHelper.sendException(actionMapping, request, new InvalidUserForMobileException());
            }

            // Prepare account related data
            final Member member = ((MemberUser) user).getMember();
            final MemberGroup memberGroup = member.getMemberGroup();
            final MemberAccountTypeQuery atQuery = new MemberAccountTypeQuery();
            atQuery.fetch(AccountType.Relationships.CURRENCY);
            atQuery.setRelatedToGroup(memberGroup);
            final List<MemberAccountType> accountTypes = (List<MemberAccountType>) accountTypeService.search(atQuery);
            // Validate that the user has an account
            if (accountTypes.isEmpty()) {
                session.invalidate();
                throw new MobileException("mobile.error.inactiveUser");
            }
            boolean multipleAccounts = false;
            if (accountTypes.size() > 1) {
                multipleAccounts = true;
                final Map<Long, MemberAccountType> accountTypesById = new HashMap<Long, MemberAccountType>();
                for (final MemberAccountType accountType : accountTypes) {
                    accountTypesById.put(accountType.getId(), accountType);
                }
                session.setAttribute("accountTypes", accountTypes);
                session.setAttribute("accountTypesById", accountTypesById);
            }
            MemberAccountType accountType = accountTypeService.getDefault(memberGroup, AccountType.Relationships.CURRENCY);
            if (accountType == null) {
                // When no account is the default, use the first one
                accountType = accountTypes.get(0);
            }
            final AccountDTO accountDto = new AccountDTO();
            accountDto.setOwner(member);
            accountDto.setType(accountType);
            final MemberAccount account = (MemberAccount) accountService.getAccount(accountDto);
            session.setAttribute("mobileAccount", account);
            session.setAttribute("mobileAccountType", accountType);
            session.setAttribute("multipleAccounts", multipleAccounts);
            return actionMapping.findForward("success");
        } catch (final InactiveMemberException e) {
            throw new MobileException("login.error.inactive");
        } catch (final BlockedCredentialsException e) {
            final String key = e.getCredentialsType() == Credentials.TRANSACTION_PASSWORD ? "transactionPassword.error.blockedByTrials" : "login.error.blocked";
            throw new MobileException(key);
        } catch (final InvalidCredentialsException e) {
            final String key = e.getCredentialsType() == Credentials.TRANSACTION_PASSWORD ? "transactionPassword.error.invalid" : "login.error";
            throw new MobileException(key);
        } catch (final LoginException e) {
            throw new MobileException("login.error");
        } catch (final PermissionDeniedException e) {
            throw new MobileException("error.accessDenied");
        } catch (final MobileException e) {
            throw e;
        } catch (final Exception e) {
            actionHelper.generateLog(request, getServlet().getServletContext(), e);
            LOG.error("Application error on mobile login action", e);
            throw new MobileException();
        }
    }

    private ActionForward prepareForm(final ActionMapping actionMapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) {
        final Channel channel = channelService.loadByInternalName(MobileHelper.mobileChannel(request));

        // Store the credentials
        Credentials credentials = channel.getCredentials();
        if (credentials == Credentials.DEFAULT) {
            // For login, default credentials == login password
            credentials = Credentials.LOGIN_PASSWORD;
        }
        request.setAttribute("credentials", credentials);

        final LoginForm form = (LoginForm) actionForm;

        // Build the access links
        final List<Map<String, String>> accessLinks = new ArrayList<Map<String, String>>();
        final PrincipalType selectedPrincipalType;
        if (StringUtils.isEmpty(form.getPrincipalType())) {
            selectedPrincipalType = channel.getDefaultPrincipalType();
        } else {
            selectedPrincipalType = channelService.resolvePrincipalType(channel.getInternalName(), form.getPrincipalType());
        }
        // Get the principal types
        final Set<PrincipalType> allPrincipalTypes = channel.getPrincipalTypes();
        for (final PrincipalType principalType : allPrincipalTypes) {
            if (principalType.equals(selectedPrincipalType)) {
                // Don't show the selected principal type as link
                continue;
            }
            final String label = messageHelper.message("mobile.login.accessUsing", resolvePrincipalLabel(principalType));
            accessLinks.add(createLink(request, label, "principalType", principalType.toString()));
        }
        request.setAttribute("selectedPrincipalType", selectedPrincipalType);
        request.setAttribute("selectedPrincipalLabel", resolvePrincipalLabel(selectedPrincipalType));
        request.setAttribute("accessLinks", accessLinks);

        return actionMapping.getInputForward();
    }

    private String resolvePrincipalLabel(final PrincipalType principalType) {
        final Principal principal = principalType.getPrincipal();
        if (principal == Principal.CUSTOM_FIELD) {
            return principalType.getCustomField().getName();
        } else {
            return messageHelper.message(principal.getKey());
        }
    }

}
