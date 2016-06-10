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
package nl.strohalm.cyclos.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.access.MemberUser;
import nl.strohalm.cyclos.entities.access.OperatorUser;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.guarantees.GuaranteeType;
import nl.strohalm.cyclos.entities.accounts.loans.LoanGroupQuery;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.entities.customization.documents.Document;
import nl.strohalm.cyclos.entities.customization.documents.DocumentQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Reference.Nature;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.entities.members.records.MemberRecordTypeQuery;
import nl.strohalm.cyclos.entities.settings.AccessSettings;
import nl.strohalm.cyclos.exceptions.AccessDeniedException;
import nl.strohalm.cyclos.exceptions.LoggedOutException;
import nl.strohalm.cyclos.services.access.AccessService;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.services.access.exceptions.LoginException;
import nl.strohalm.cyclos.services.access.exceptions.NotConnectedException;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.accounts.guarantees.GuaranteeService;
import nl.strohalm.cyclos.services.customization.DocumentService;
import nl.strohalm.cyclos.services.elements.CommissionService;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.services.elements.MemberRecordTypeService;
import nl.strohalm.cyclos.services.elements.ReferenceService;
import nl.strohalm.cyclos.services.groups.GroupService;
import nl.strohalm.cyclos.services.loangroups.LoanGroupService;
import nl.strohalm.cyclos.services.permissions.PermissionService;
import nl.strohalm.cyclos.services.preferences.ReceiptPrinterSettingsService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.query.PageHelper;

import org.apache.commons.lang.StringUtils;

/**
 * Helper class for login
 * @author luis
 */
public class LoginHelper {

    /**
     * Returns the currently logged user, or null if none
     */
    public static User getLoggedUser(final HttpServletRequest request) {
        return (User) request.getAttribute("loggedUser");
    }

    private ChannelService                channelService;
    private ElementService                elementService;
    private AccessService                 accessService;
    private GroupService                  groupService;
    private AccountService                accountService;
    private PermissionService             permissionService;
    private DocumentService               documentService;
    private LoanGroupService              loanGroupService;
    private ReferenceService              referenceService;
    private MemberRecordTypeService       memberRecordTypeService;
    private GuaranteeService              guaranteeService;
    private SettingsService               settingsService;
    private CommissionService             commissionService;
    private ReceiptPrinterSettingsService receiptPrinterSettingsService;
    private boolean                       newSessionAfterLogin;

    /**
     * Perform the login itself
     */
    public User login(final Class<? extends User> requiredUserClass, final String principalTypeString, final String memberUsername, final String principal, final String password, final String channel, final HttpServletRequest request, final HttpServletResponse response) throws LoginException {
        final String remoteAddress = request.getRemoteAddr();

        final PrincipalType principalType = channelService.resolvePrincipalType(channel, principalTypeString);

        // Validate the user
        String usernameToVerify = principal;
        if (principalType.getPrincipal() != Principal.USER) {
            try {
                Member member;
                member = elementService.loadByPrincipal(principalType, principal, Element.Relationships.USER, Element.Relationships.GROUP);
                usernameToVerify = member.getUsername();
            } catch (final EntityNotFoundException e) {
                usernameToVerify = "";
            }
        }
        final User user = accessService.verifyLogin(memberUsername, usernameToVerify, remoteAddress);
        if (!requiredUserClass.isInstance(user)) {
            throw new AccessDeniedException();
        }

        // Find the user nature
        final Group group = user.getElement().getGroup();
        final boolean isAdmin = group instanceof AdminGroup;
        final boolean isMember = group instanceof MemberGroup;
        final boolean isBroker = group instanceof BrokerGroup;
        final boolean isOperator = group instanceof OperatorGroup;
        final boolean isPosWeb = RequestHelper.isPosWeb(request);

        final AccessSettings accessSettings = settingsService.getAccessSettings();

        // Check if the administrator is allowed to login
        if (isAdmin && !accessSettings.getAdministrationWhitelistValidator().isAllowed(request.getRemoteHost(), request.getRemoteAddr())) {
            throw new AccessDeniedException();
        }

        // According to the cyclos.properties flag, create a new session or use the current one
        HttpSession session;
        if (newSessionAfterLogin) {
            session = createNewSessionForlogin(request);
        } else {
            session = request.getSession();
        }

        // Login the user
        accessService.login(user, password, channel, isPosWeb, remoteAddress, session.getId());

        // Apply the session timeout
        final TimePeriod timeout = isPosWeb ? accessSettings.getPoswebTimeout() : isMember ? accessSettings.getMemberTimeout() : accessSettings.getAdminTimeout();
        int timeoutSeconds = (int) timeout.getValueIn(TimePeriod.Field.SECONDS);
        if (timeoutSeconds <= 0) {
            timeoutSeconds = -1;
        }
        session.setMaxInactiveInterval(timeoutSeconds);

        // If is a member, determine if the member has accounts, documents, loan groups and memberPos
        boolean hasAccounts = false;
        boolean singleAccount = false;
        boolean hasDocuments = false;
        boolean hasLoanGroups = false;
        boolean hasGeneralReferences = false;
        boolean hasTransactionFeedbacks = false;
        boolean hasPin = false;
        boolean hasExternalChannels = false;
        boolean hasCards = false;
        boolean hasPos = false;
        boolean hasCommissionContracts = false;
        if (isMember || isOperator) {
            Member member;
            if (isMember) {
                member = ((MemberUser) user).getMember();

                // Get the accessible channels
                final MemberGroup memberGroup = groupService.load(member.getMemberGroup().getId(), MemberGroup.Relationships.CHANNELS);
                hasPin = groupService.usesPin(memberGroup);
                for (final Channel current : memberGroup.getChannels()) {
                    if (!Channel.WEB.equals(current.getInternalName())) {
                        hasExternalChannels = true;
                        break;
                    }
                }

                if (!member.getPosDevices().isEmpty()) {
                    hasPos = true;
                    if (member.getPosDevices().size() == 1) {
                        final Collection<MemberPos> memberPos = member.getPosDevices();
                        for (final MemberPos mpos : memberPos) {
                            session.setAttribute("uniqueMemberPosId ", mpos.getPos().getId());
                        }
                    }
                }

            } else {
                member = ((OperatorUser) user).getOperator().getMember();
            }
            // Fetch broker
            member = elementService.load(member.getId(), Member.Relationships.BROKER);
            final MemberGroup memberGroup = member.getMemberGroup();

            // Check if the member has accounts
            final List<? extends Account> accounts = accountService.getAccounts(member);
            hasAccounts = !accounts.isEmpty();
            singleAccount = accounts.size() == 1;
            if (isMember) {
                // Check if the member has documents
                if (permissionService.hasPermission(MemberPermission.DOCUMENTS_VIEW)) {
                    hasDocuments = true;
                } else {
                    final DocumentQuery documentQuery = new DocumentQuery();
                    documentQuery.setNatures(Collections.singleton(Document.Nature.MEMBER));
                    documentQuery.setMember(member);
                    documentQuery.setPageForCount();
                    hasDocuments = PageHelper.hasResults(documentService.search(documentQuery));
                }
                // Check if the member has loan groups
                final LoanGroupQuery lgq = new LoanGroupQuery();
                lgq.setPageForCount();
                lgq.setMember(member);
                hasLoanGroups = PageHelper.hasResults(loanGroupService.search(lgq));

                // Check if the member has commission contracts
                hasCommissionContracts = commissionService.hasBrokerCommissionContracts();
            }
            // Check if the user has references
            final Collection<Nature> referenceNatures = referenceService.getNaturesByGroup(memberGroup);
            hasGeneralReferences = referenceNatures.contains(Nature.GENERAL);
            hasTransactionFeedbacks = referenceNatures.contains(Nature.TRANSACTION);

            // Check if the user can have guarantees
            try {
                final Collection<GuaranteeType.Model> guaranteeModels = guaranteeService.getRelatedGuaranteeModels();
                session.setAttribute("loggedMemberHasGuarantees", guaranteeModels.size() > 0);
            } catch (final Exception e) {
                // Ignore
            }

            // Check if the user has cards
            hasCards = member.getCards().isEmpty() ? false : true;
        }

        if (isAdmin || isBroker) {
            // Retrieve the member record types the logged user can see on the menu
            final MemberRecordTypeQuery query = new MemberRecordTypeQuery();
            if (isAdmin) {
                query.setViewableByAdminGroup((AdminGroup) group);
            } else {
                query.setViewableByBrokerGroup((BrokerGroup) group);
            }
            query.setShowMenuItem(true);
            final List<MemberRecordType> types = memberRecordTypeService.search(query);
            session.setAttribute("memberRecordTypesInMenu", types);
        }

        // When a receipt printer cookie is set, and the printer no longer exists, or belongs to someone else, clear the cookie
        final String receiptPrinterId = RequestHelper.getCookieValue(request, "receiptPrinterId");
        if (StringUtils.isNotEmpty(receiptPrinterId)) {
            final Long id = IdConverter.instance().valueOf(receiptPrinterId);
            if (!receiptPrinterSettingsService.belongsToTheLoggedUser(id)) {
                final Cookie cookie = new Cookie("receiptPrinterId", "");
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
            }
        }

        final String actionPrefix = "/" + (isAdmin ? "admin" : isMember ? "member" : "operator");

        // Set the request attributes
        request.setAttribute("loggedUser", user);
        request.setAttribute("loggedElement", user.getElement());

        // Set the session attributes
        session.setAttribute("loggedUserId", user.getId());
        session.setAttribute("isAdmin", isAdmin);
        session.setAttribute("isMember", isMember);
        session.setAttribute("isBroker", isBroker);
        session.setAttribute("isOperator", isOperator);
        session.setAttribute("isBuyer", guaranteeService.isBuyer());
        session.setAttribute("isSeller", guaranteeService.isSeller());
        session.setAttribute("isIssuer", guaranteeService.isIssuer());
        session.setAttribute("loggedMemberHasAccounts", hasAccounts);
        session.setAttribute("loggedMemberHasSingleAccount", singleAccount);
        session.setAttribute("loggedMemberHasDocuments", hasDocuments);
        session.setAttribute("loggedMemberHasLoanGroups", hasLoanGroups);
        session.setAttribute("loggedMemberHasGeneralReferences", hasGeneralReferences);
        session.setAttribute("loggedMemberHasTransactionFeedbacks", hasTransactionFeedbacks);
        session.setAttribute("hasPin", hasPin);
        session.setAttribute("hasCards", hasCards);
        session.setAttribute("hasPos", hasPos);
        session.setAttribute("hasCommissionContracts", hasCommissionContracts);
        session.setAttribute("hasExternalChannels", hasExternalChannels);
        session.setAttribute("actionPrefix", actionPrefix);
        session.setAttribute("pathPrefix", "/do" + actionPrefix);
        session.setAttribute("navigation", Navigation.get(session));

        // Return the logged user
        return user;
    }

    public void setAccessService(final AccessService accessService) {
        this.accessService = accessService;
    }

    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    public void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    public void setCommissionService(final CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    public void setCyclosProperties(final Properties cyclosProperties) {
        newSessionAfterLogin = Boolean.parseBoolean(cyclosProperties.getProperty("cyclos.newSessionAfterLogin", "true"));
    }

    public void setDocumentService(final DocumentService documentService) {
        this.documentService = documentService;
    }

    public void setElementService(final ElementService elementService) {
        this.elementService = elementService;
    }

    public void setGroupService(final GroupService groupService) {
        this.groupService = groupService;
    }

    public void setGuaranteeService(final GuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    public void setLoanGroupService(final LoanGroupService loanGroupService) {
        this.loanGroupService = loanGroupService;
    }

    public void setMemberRecordTypeService(final MemberRecordTypeService memberRecordTypeService) {
        this.memberRecordTypeService = memberRecordTypeService;
    }

    public void setPermissionService(final PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public void setReceiptPrinterSettingsService(final ReceiptPrinterSettingsService receiptPrinterSettingsService) {
        this.receiptPrinterSettingsService = receiptPrinterSettingsService;
    }

    public void setReferenceService(final ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    /**
     * Returns the currently logged user, ensuring there is one
     */
    public User validateLoggedUser(final HttpServletRequest request) {
        final HttpSession session = request.getSession();

        // Find the logged user
        final User user = getLoggedUser(request);
        if (user == null) {
            throw new LoggedOutException();
        }
        // Find the registered logged user for the session id
        User serviceUser;
        try {
            serviceUser = accessService.getLoggedUser(session.getId());
        } catch (final NotConnectedException e) {
            throw new LoggedOutException();
        }
        // The web container session indicates there is an user, but there's no tracked session: invalidate the session's user
        if (user != null && serviceUser == null) {
            session.removeAttribute("loggedUser");
            throw new LoggedOutException();
        } else {
            // Ensure they match
            final boolean valid = user != null && user.equals(serviceUser);
            if (!valid) {
                session.invalidate();
                throw new AccessDeniedException();
            }
        }
        return user;
    }

    @SuppressWarnings("rawtypes")
    private HttpSession createNewSessionForlogin(final HttpServletRequest request) {
        HttpSession session = request.getSession();

        // retrieve the current attributes
        final Map<String, Object> attrMap = new HashMap<String, Object>();
        for (final Enumeration e = session.getAttributeNames(); e.hasMoreElements();) {
            final String attrName = (String) e.nextElement();
            attrMap.put(attrName, session.getAttribute(attrName));
        }

        // invalidates and creates a new session
        session.invalidate();
        session = request.getSession();

        // copy the previous attributes to the new session
        for (final Map.Entry<String, Object> entry : attrMap.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }

        return session;
    }
}
