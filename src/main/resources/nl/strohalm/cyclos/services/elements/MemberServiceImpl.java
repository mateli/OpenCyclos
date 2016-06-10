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
package nl.strohalm.cyclos.services.elements;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.OperatorPermission;
import nl.strohalm.cyclos.dao.members.ElementDAO;
import nl.strohalm.cyclos.dao.sms.MemberSmsStatusDAO;
import nl.strohalm.cyclos.entities.access.User;
import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.InvoiceSummaryDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransfersAwaitingAuthorizationQuery;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group.Nature;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.OperatorGroup;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.members.BrokeringQuery;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.entities.members.PaymentsAwaitingFeedbackQuery;
import nl.strohalm.cyclos.entities.members.Reference;
import nl.strohalm.cyclos.entities.members.ReferenceQuery;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContract;
import nl.strohalm.cyclos.entities.members.brokerings.BrokerCommissionContractQuery;
import nl.strohalm.cyclos.entities.members.messages.MessageBox;
import nl.strohalm.cyclos.entities.members.messages.MessageQuery;
import nl.strohalm.cyclos.entities.members.preferences.NotificationPreference;
import nl.strohalm.cyclos.entities.sms.MemberSmsStatus;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.AccessServiceLocal;
import nl.strohalm.cyclos.services.access.ChannelServiceLocal;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.accounts.GetTransactionsDTO;
import nl.strohalm.cyclos.services.accounts.rates.ConversionSimulationDTO;
import nl.strohalm.cyclos.services.accounts.rates.RateServiceLocal;
import nl.strohalm.cyclos.services.ads.AdServiceLocal;
import nl.strohalm.cyclos.services.customization.MemberCustomFieldService;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.services.permissions.PermissionServiceLocal;
import nl.strohalm.cyclos.services.preferences.PreferenceServiceLocal;
import nl.strohalm.cyclos.services.sms.ISmsContext;
import nl.strohalm.cyclos.services.sms.MemberGroupSmsContextImpl;
import nl.strohalm.cyclos.services.sms.exceptions.SmsContextInitializationException;
import nl.strohalm.cyclos.services.transactions.InvoiceServiceLocal;
import nl.strohalm.cyclos.services.transactions.LoanServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionSummaryVO;
import nl.strohalm.cyclos.services.transactions.TransferAuthorizationServiceLocal;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeePreviewForRatesDTO;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.CustomFieldHelper;
import nl.strohalm.cyclos.utils.CustomObjectHandler;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.IteratorListImpl;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.TimePeriod;
import nl.strohalm.cyclos.utils.TransactionHelper;
import nl.strohalm.cyclos.utils.Transactional;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.lock.LockHandler;
import nl.strohalm.cyclos.utils.lock.LockHandlerFactory;
import nl.strohalm.cyclos.utils.query.IteratorList;
import nl.strohalm.cyclos.utils.query.PageHelper;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.members.FullTextMemberSearchParameters;
import nl.strohalm.cyclos.webservices.members.MemberResultPage;
import nl.strohalm.cyclos.webservices.model.MemberVO;
import nl.strohalm.cyclos.webservices.model.MyProfileVO;
import nl.strohalm.cyclos.webservices.utils.MemberHelper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;

/**
 * Implementation for MemberService
 * @author luis
 */
public class MemberServiceImpl implements MemberServiceLocal {

    private FetchServiceLocal                 fetchService;
    private PreferenceServiceLocal            preferenceService;
    private AccountServiceLocal               accountService;
    private InvoiceServiceLocal               invoiceService;
    private PermissionServiceLocal            permissionService;
    private BrokeringServiceLocal             brokeringService;
    private LoanServiceLocal                  loanService;
    private AdServiceLocal                    adService;
    private ReferenceServiceLocal             referenceService;
    private PaymentServiceLocal               paymentService;
    private TransferAuthorizationServiceLocal transferAuthorizationService;
    private MessageServiceLocal               messageService;
    private ElementDAO                        elementDao;
    private RateServiceLocal                  rateService;
    private TransferTypeServiceLocal          transferTypeService;
    private AccessServiceLocal                accessService;
    private ChannelServiceLocal               channelService;
    private MemberSmsStatusDAO                memberSmsStatusDao;
    private GroupServiceLocal                 groupService;
    private LockHandlerFactory                lockHandlerFactory;
    private TransactionHelper                 transactionHelper;
    private CommissionServiceLocal            commissionService;
    private CustomObjectHandler               customObjectHandler;
    private ElementServiceLocal               elementService;
    private MemberHelper                      memberHelper;
    private MemberCustomFieldService          memberCustomFieldService;
    private CustomFieldHelper                 customFieldHelper;

    /**
     * Ensure the sms status has the "allow charging sms" flag set correctly.<br>
     * If there isn't free sms, the additional charged package's size is one and the member accept any feature by sms then the flag is set to true.
     */
    @Override
    public void ensureAllowChargingSms(final MemberSmsStatus memberSmsStatus, final boolean hasNotificationsBySms) {
        // Check if we must set the allowChargingSms flag
        Member member = memberSmsStatus.getMember();
        final ISmsContext smsContext = getSmsContext(member);
        final int smsFree = smsContext.getFreeSms(member);
        final int smsAdditionalCharged = smsContext.getAdditionalChargedSms(member);
        if (smsAdditionalCharged == 1 && smsFree == 0) {
            Member fetchedMember = fetchService.fetch(member, Member.Relationships.CHANNELS);
            final boolean smsChannelEnabled = accessService.getChannelsEnabledForMember(fetchedMember).contains(channelService.getSmsChannel());
            final boolean allowChargingSms = smsChannelEnabled || memberSmsStatus.isAcceptPaidMailing() || hasNotificationsBySms;
            memberSmsStatus.setAllowChargingSms(allowChargingSms);
        }
    }

    @Override
    public ActivitiesVO getActivities(Member member) {
        member = fetchService.fetch(member, Element.Relationships.GROUP);
        return doGetActivities(member);
    }

    @Override
    public Map<MemberGroup, Integer> getGroupMemberCount(final Collection<MemberGroup> groups, final Calendar timePoint) {
        Map<Long, Integer> countPerGroupId;
        if (timePoint == null) {
            countPerGroupId = elementDao.getCountPerGroup(groups);
        } else {
            countPerGroupId = elementDao.getCountPerGroup(groups, timePoint);
        }
        final Map<MemberGroup, Integer> groupMemberCount = new TreeMap<MemberGroup, Integer>();
        // Initialize with zeros
        for (MemberGroup group : groups) {
            group = groupService.load(group.getId());
            groupMemberCount.put(group, 0);
        }
        // Count
        for (final Entry<Long, Integer> entry : countPerGroupId.entrySet()) {
            MemberGroup group = groupService.load(entry.getKey());
            Integer count = entry.getValue();
            groupMemberCount.put(group, count);
        }
        return groupMemberCount;
    }

    @Override
    public MemberResultPage getMemberResultPage(final FullTextMemberSearchParameters params) {
        FullTextMemberQuery query = memberHelper.toFullTextQuery(params);
        if (!elementService.applyQueryRestrictions(query)) {
            throw new PermissionDeniedException();
        }
        @SuppressWarnings("unchecked")
        List<Member> members = (List<Member>) elementService.fullTextSearch(query);
        return memberHelper.toResultPage(members, params.getShowCustomFields(), params.getShowImages());
    }

    @Override
    public MemberVO getMemberVO(final Member member, final boolean useMemberFields, final boolean useImages) {
        List<MemberCustomField> fields = null;
        if (useMemberFields) {
            fields = memberCustomFieldService.list();
            if (!LoggedUser.isUnrestrictedClient()) {
                MemberGroup group = LoggedUser.member().getMemberGroup();
                fields = customFieldHelper.onlyVisibleFields(fields, group);
            }
        }
        return memberHelper.toVO(member, fields, useImages);
    }

    @Override
    public MyProfileVO getMyProfileVO(final Member member) {
        return memberHelper.toMyProfileVO(member);
    }

    @Override
    public QuickAccessVO getQuickAccess() {
        final Member member = (Member) LoggedUser.accountOwner();
        final QuickAccessVO quickAccess = new QuickAccessVO();
        quickAccess.setUpdateProfile(true); // Update profile is always visible
        quickAccess.setSearchMembers(CollectionUtils.isNotEmpty(permissionService.getVisibleMemberGroups()));
        quickAccess.setAccountInformation(permissionService.permission(member).member().operator(OperatorPermission.ACCOUNT_ACCOUNT_INFORMATION).hasPermission() && CollectionUtils.isNotEmpty(accountService.getAccounts(member)));
        if (LoggedUser.isOperator()) {
            quickAccess.setMemberPayment(permissionService.permission().operator(OperatorPermission.PAYMENTS_PAYMENT_TO_MEMBER).hasPermission() && permissionService.permission().operator(OperatorPermission.ACCOUNT_ACCOUNT_INFORMATION).hasPermission());
        } else if (LoggedUser.isMember()) {
            quickAccess.setMemberPayment(permissionService.permission().member(MemberPermission.PAYMENTS_PAYMENT_TO_MEMBER).hasPermission());
        }
        quickAccess.setPublishAd(permissionService.permission().member(MemberPermission.ADS_PUBLISH).operator(OperatorPermission.ADS_PUBLISH).hasPermission());
        quickAccess.setSearchAds(permissionService.permission().member(MemberPermission.ADS_VIEW).operator(MemberPermission.ADS_VIEW).hasPermission());
        quickAccess.setViewMessages(permissionService.permission().member(MemberPermission.MESSAGES_VIEW).operator(OperatorPermission.MESSAGES_VIEW).hasPermission());
        quickAccess.setViewContacts(permissionService.permission().member().operator(OperatorPermission.CONTACTS_VIEW).hasPermission());
        return quickAccess;
    }

    @Override
    public ISmsContext getSmsContext(final Member member) throws SmsContextInitializationException {
        MemberGroup memberGroup = member.getMemberGroup();
        String className = memberGroup.getMemberSettings().getSmsContextClassName();
        if (StringUtils.isEmpty(className)) {
            return MemberGroupSmsContextImpl.getInstance();
        } else {
            try {
                return customObjectHandler.get(className);
            } catch (Exception e) {
                throw new SmsContextInitializationException(memberGroup, className, e.getMessage());
            }
        }
    }

    @Override
    public MemberSmsStatus getSmsStatus(final Member member, final boolean update) {
        boolean isNewTransaction = !update;
        if (isNewTransaction) {
            // Run in a new transaction to support retry if there is a locking exception
            MemberSmsStatus status = transactionHelper.runInNewTransaction(new Transactional<MemberSmsStatus>() {
                @Override
                public MemberSmsStatus afterCommit(final MemberSmsStatus result) {
                    // Ensure the status is attached to the current transaction
                    return fetchService.fetch(result, RelationshipHelper.nested(MemberSmsStatus.Relationships.MEMBER, Element.Relationships.GROUP));
                }

                @Override
                public MemberSmsStatus doInTransaction(final TransactionStatus status) {
                    if (!update) {
                        status.setRollbackOnly();
                    }
                    return performGetSmsStatus(member);
                }
            });

            // because the Tx is rolled back we set the member directly
            status.setMember(member);
            return status;
        } else {
            return performGetSmsStatus(member);
        }
    }

    @Override
    public MemberStatusVO getStatus() {
        final MemberStatusVO status = new MemberStatusVO();

        final Member member = (Member) LoggedUser.accountOwner();
        MemberGroup group = member.getMemberGroup();
        final User user = LoggedUser.user();
        final Calendar lastLogin = user.getLastLogin();
        final boolean isOperator = LoggedUser.isOperator();

        // Count the unread messages
        if (permissionService.hasPermission(isOperator ? OperatorPermission.MESSAGES_VIEW : MemberPermission.MESSAGES_VIEW)) {
            final MessageQuery messages = new MessageQuery();
            messages.setGetter(member);
            messages.setMessageBox(MessageBox.INBOX);
            messages.setRead(false);
            messages.setPageForCount();
            status.setUnreadMessages(PageHelper.getTotalCount(messageService.search(messages)));
        }

        // Count the new payments since the last login
        group = fetchService.fetch(group, MemberGroup.Relationships.ACCOUNT_SETTINGS);
        final Collection<MemberAccountType> accountTypes = group.getAccountTypes();
        if (CollectionUtils.isNotEmpty(accountTypes) && !(isOperator && !permissionService.hasPermission(OperatorPermission.ACCOUNT_ACCOUNT_INFORMATION))) {
            final TransferQuery transfers = new TransferQuery();
            transfers.setRootOnly(true);
            transfers.setToAccountOwner(member);
            transfers.setLoanTransfer(false);
            if (lastLogin != null) {
                transfers.setPeriod(Period.begginingAt(lastLogin).useTime());
            }
            transfers.setPageForCount();
            status.setNewPayments(PageHelper.getTotalCount(paymentService.search(transfers)));
        }

        // Count the new references since the last login
        if (permissionService.hasPermission(isOperator ? OperatorPermission.REFERENCES_VIEW : MemberPermission.REFERENCES_VIEW)) {
            final ReferenceQuery references = new ReferenceQuery();
            references.setNature(Reference.Nature.GENERAL);
            references.setTo(member);
            if (lastLogin != null) {
                references.setPeriod(Period.begginingAt(lastLogin).useTime());
            }
            references.setPageForCount();
            status.setNewReferences(PageHelper.getTotalCount(referenceService.search(references)));
        }

        // Count the open invoices
        if (permissionService.hasPermission(isOperator ? OperatorPermission.INVOICES_VIEW : MemberPermission.INVOICES_VIEW)) {
            final InvoiceQuery invoices = new InvoiceQuery();
            invoices.setOwner(member);
            invoices.setDirection(InvoiceQuery.Direction.INCOMING);
            invoices.setStatus(Invoice.Status.OPEN);
            invoices.setPageForCount();
            status.setOpenInvoices(PageHelper.getTotalCount(invoiceService.search(invoices)));
        }

        // Count the open loans
        if (permissionService.hasPermission(isOperator ? OperatorPermission.LOANS_VIEW : MemberPermission.LOANS_VIEW)) {
            final LoanQuery loans = new LoanQuery();
            loans.setMember(member);
            loans.setStatus(Loan.Status.OPEN);
            loans.setPageForCount();
            status.setOpenLoans(PageHelper.getTotalCount(loanService.search(loans)));
        }

        // Count the payments awaiting feedback
        if (!(isOperator && !permissionService.hasPermission(OperatorPermission.REFERENCES_MANAGE_MEMBER_TRANSACTION_FEEDBACKS))) {
            final PaymentsAwaitingFeedbackQuery awaitingFeedback = new PaymentsAwaitingFeedbackQuery();
            awaitingFeedback.setPageForCount();
            awaitingFeedback.setExpired(false);
            awaitingFeedback.setMember(member);
            status.setPaymentsAwaitingFeedback(PageHelper.getTotalCount(referenceService.searchPaymentsAwaitingFeedback(awaitingFeedback)));
        }

        // Count the payment awaiting authorization
        if (permissionService.hasPermission(isOperator ? OperatorPermission.PAYMENTS_AUTHORIZE : MemberPermission.PAYMENTS_AUTHORIZE)) {
            final TransfersAwaitingAuthorizationQuery awaitingAuthorization = new TransfersAwaitingAuthorizationQuery();
            awaitingAuthorization.setPageForCount();
            status.setPaymentsToAuthorize(PageHelper.getTotalCount(transferAuthorizationService.searchTransfersAwaitingAuthorization(awaitingAuthorization)));
        }

        // Check if there are pending commission contracts
        if (member.getBroker() != null && !isOperator) {
            BrokerCommissionContractQuery contractsQuery = new BrokerCommissionContractQuery();
            contractsQuery.setPageForCount();
            contractsQuery.setMember(member);
            contractsQuery.setStatus(BrokerCommissionContract.Status.PENDING);
            status.setHasPendingCommissionContracts(PageHelper.hasResults(commissionService.searchBrokerCommissionContracts(contractsQuery)));
        }

        return status;
    }

    @Override
    public boolean hasValueForField(final Member member, final MemberCustomField field) {
        return elementDao.hasValueForField(member, field);
    }

    @Override
    public IteratorList<Member> iterateByGroup(final boolean ordered, final MemberGroup... groups) {
        return new IteratorListImpl<Member>(elementDao.iterateMembers(ordered, groups));
    }

    @Override
    public IteratorList<Member> iterateByGroup(final MemberGroup... groups) {
        return iterateByGroup(false, groups);
    }

    @Override
    public Member loadByIdOrPrincipal(final Long id, final String principalType, final String principal) {
        Member member;
        if (id != null) {
            // By id
            try {
                member = (Member) elementService.load(id);
            } catch (ClassCastException e) {
                throw new EntityNotFoundException(Member.class);
            }
        } else if (StringUtils.isNotEmpty(principal)) {
            // By principal
            member = memberHelper.loadByPrincipal(principalType, principal);
        } else {
            // No data
            return null;
        }
        Member restrictedMember = WebServiceContext.getMember();
        if (restrictedMember != null && !permissionService.relatesTo(member)) {
            throw new EntityNotFoundException(Member.class);
        }
        return member;
    }

    public void setAccessServiceLocal(final AccessServiceLocal accessService) {
        this.accessService = accessService;
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        this.accountService = accountService;
    }

    public void setAdServiceLocal(final AdServiceLocal adService) {
        this.adService = adService;
    }

    public void setBrokeringServiceLocal(final BrokeringServiceLocal brokeringService) {
        this.brokeringService = brokeringService;
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        this.channelService = channelService;
    }

    public void setCommissionServiceLocal(final CommissionServiceLocal commissionService) {
        this.commissionService = commissionService;
    }

    public void setCustomFieldHelper(final CustomFieldHelper customFieldHelper) {
        this.customFieldHelper = customFieldHelper;
    }

    public void setCustomObjectHandler(final CustomObjectHandler customObjectHandler) {
        this.customObjectHandler = customObjectHandler;
    }

    public void setElementDao(final ElementDAO elementDao) {
        this.elementDao = elementDao;
    }

    public void setElementServiceLocal(final ElementServiceLocal elementService) {
        this.elementService = elementService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    public void setInvoiceServiceLocal(final InvoiceServiceLocal invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void setLoanServiceLocal(final LoanServiceLocal loanService) {
        this.loanService = loanService;
    }

    public void setLockHandlerFactory(final LockHandlerFactory lockHandlerFactory) {
        this.lockHandlerFactory = lockHandlerFactory;
    }

    public void setMemberCustomFieldService(final MemberCustomFieldService memberCustomFieldService) {
        this.memberCustomFieldService = memberCustomFieldService;
    }

    public void setMemberHelper(final MemberHelper memberHelper) {
        this.memberHelper = memberHelper;
    }

    public void setMemberSmsStatusDao(final MemberSmsStatusDAO memberSmsStatusDao) {
        this.memberSmsStatusDao = memberSmsStatusDao;
    }

    public void setMessageServiceLocal(final MessageServiceLocal messageService) {
        this.messageService = messageService;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setPermissionServiceLocal(final PermissionServiceLocal permissionService) {
        this.permissionService = permissionService;
    }

    public void setPreferenceServiceLocal(final PreferenceServiceLocal preferenceService) {
        this.preferenceService = preferenceService;
    }

    public void setRateServiceLocal(final RateServiceLocal rateService) {
        this.rateService = rateService;
    }

    public void setReferenceServiceLocal(final ReferenceServiceLocal referenceService) {
        this.referenceService = referenceService;
    }

    public void setTransactionHelper(final TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public void setTransferAuthorizationServiceLocal(final TransferAuthorizationServiceLocal transferAuthorizationService) {
        this.transferAuthorizationService = transferAuthorizationService;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    public MemberSmsStatus updateSmsStatus(final MemberSmsStatus memberSmsStatus) {
        return memberSmsStatusDao.update(memberSmsStatus);
    }

    private boolean canViewAccountInformation(final Element element, MemberAccount memberAccount) {
        memberAccount = fetchService.fetch(memberAccount, Account.Relationships.TYPE, RelationshipHelper.nested(MemberAccount.Relationships.MEMBER, Member.Relationships.BROKER));
        if (element instanceof Administrator) {
            return true;
        } else if (element instanceof Operator) {
            final Operator operator = fetchService.fetch((Operator) element, RelationshipHelper.nested(Operator.Relationships.MEMBER, Element.Relationships.GROUP), RelationshipHelper.nested(Element.Relationships.GROUP, OperatorGroup.Relationships.CAN_VIEW_INFORMATION_OF));
            if (memberAccount.getMember().equals(operator.getMember())) {
                if (operator.getOperatorGroup().getCanViewInformationOf().contains(memberAccount.getType())) {
                    return true;
                }
            } else {
                final MemberGroup operatorsMemberGroup = fetchService.fetch(operator.getMember().getMemberGroup(), MemberGroup.Relationships.CAN_VIEW_INFORMATION_OF);
                if (operatorsMemberGroup.getCanViewInformationOf().contains(memberAccount.getType())) {
                    return true;
                }
            }
        } else if (element instanceof Member) {
            final Member member = fetchService.fetch((Member) element, RelationshipHelper.nested(Element.Relationships.GROUP, MemberGroup.Relationships.CAN_VIEW_INFORMATION_OF), RelationshipHelper.nested(Element.Relationships.GROUP, BrokerGroup.Relationships.BROKER_CAN_VIEW_INFORMATION_OF));
            if (member.getGroup() instanceof BrokerGroup) {
                if (member.equals(memberAccount.getMember().getBroker())) {
                    final BrokerGroup brokerGroup = (BrokerGroup) member.getMemberGroup();
                    if (brokerGroup.getBrokerCanViewInformationOf().contains(memberAccount.getType())) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            final MemberGroup memberGroup = member.getMemberGroup();
            if (memberGroup.getCanViewInformationOf().contains(memberAccount.getType())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private ActivitiesVO doGetActivities(final Member member) {
        final ActivitiesVO vo = new ActivitiesVO();

        // Check if account information will be retrieved
        boolean showAccountInformation;
        boolean showAdsInformation = adService.visibleGroupsForAds().contains(member.getGroup());
        boolean showNonActiveAdsInformation = false;

        final Element loggedElement = LoggedUser.element();
        if (permissionService.manages(member)) {
            // A managed member
            showAccountInformation = permissionService.permission(member)
                    .admin(AdminMemberPermission.REPORTS_SHOW_ACCOUNT_INFORMATION)
                    .broker(BrokerPermission.ACCOUNTS_INFORMATION)
                    .member()
                    .operator(OperatorPermission.ACCOUNT_ACCOUNT_INFORMATION)
                    .hasPermission();
            showNonActiveAdsInformation = showAdsInformation;
        } else {
            // Another related member
            showAccountInformation = permissionService.permission()
                    .member(MemberPermission.REPORTS_SHOW_ACCOUNT_INFORMATION)
                    .operator(MemberPermission.REPORTS_SHOW_ACCOUNT_INFORMATION)
                    .hasPermission();
        }

        boolean showReferencesInformation = permissionService.permission()
                .admin(AdminMemberPermission.REFERENCES_VIEW)
                .member(MemberPermission.REFERENCES_VIEW)
                .operator(OperatorPermission.REFERENCES_VIEW)
                .hasPermission();

        boolean showInvoicesInformation = showAccountInformation && permissionService.permission()
                .admin(AdminMemberPermission.INVOICES_VIEW)
                .member(MemberPermission.INVOICES_VIEW)
                .operator(OperatorPermission.INVOICES_VIEW)
                .hasPermission();

        vo.setShowAccountInformation(showAccountInformation);
        vo.setShowInvoicesInformation(showInvoicesInformation);
        vo.setShowReferencesInformation(showReferencesInformation);
        vo.setShowAdsInformation(showAdsInformation);
        vo.setShowNonActiveAdsInformation(showNonActiveAdsInformation);

        // Since active
        vo.setSinceActive(member.getActivationDate());

        // Number of brokered members
        boolean isBroker = member.getGroup().getNature() == Nature.BROKER;
        if (isBroker) {
            final BrokeringQuery query = new BrokeringQuery();
            query.setBroker(member);
            query.setStatus(BrokeringQuery.Status.ACTIVE);
            query.setPageForCount();
            vo.setNumberBrokeredMembers(PageHelper.getTotalCount(brokeringService.search(query)));
        }

        // References
        if (showReferencesInformation) {
            vo.setReceivedReferencesByLevel(referenceService.countReferencesByLevel(Reference.Nature.GENERAL, member, true));
            vo.setGivenReferencesByLevel(referenceService.countReferencesByLevel(Reference.Nature.GENERAL, member, false));
        }

        // Ads
        if (vo.isShowAdsInformation()) {
            vo.setAdsByStatus(adService.getNumberOfAds(null, member));
        }

        final List<MemberAccount> accounts = (List<MemberAccount>) accountService.getAccounts(member);

        // Get invoice information
        if (showInvoicesInformation) {
            // Incoming invoices
            final InvoiceSummaryDTO incomingInvoicesDTO = new InvoiceSummaryDTO();
            incomingInvoicesDTO.setOwner(member);
            incomingInvoicesDTO.setDirection(InvoiceQuery.Direction.INCOMING);
            incomingInvoicesDTO.setStatus(Invoice.Status.OPEN);
            vo.setIncomingInvoices(invoiceService.getSummary(incomingInvoicesDTO));

            // Outgoing invoices
            final InvoiceSummaryDTO summaryDTO = new InvoiceSummaryDTO();
            summaryDTO.setOwner(member);
            summaryDTO.setDirection(InvoiceQuery.Direction.OUTGOING);
            summaryDTO.setStatus(Invoice.Status.OPEN);
            vo.setOutgoingInvoices(invoiceService.getSummary(summaryDTO));
        }

        // 30 days ago
        final Calendar days30 = DateHelper.truncate(Calendar.getInstance());
        days30.add(Calendar.DATE, -30);

        // Account activities; as rate info is NOT subject to permissions, always do the loop
        for (final MemberAccount account : accounts) {
            boolean hasRateInfo = false;

            final GetTransactionsDTO allTime = new GetTransactionsDTO(account);
            // final GetTransactionsDTO last30Days = new GetTransactionsDTO(account, Period.begginingAt(days30));

            // Build an account activities VO
            final AccountActivitiesVO activities = new AccountActivitiesVO();

            // Get the account status
            AccountStatus accountStatus = accountService.getRatedStatus(account, null);
            activities.setAccountStatus(accountStatus);

            // as AccountStatus contains rate info, no need to get it separately
            hasRateInfo = rateService.isAnyRateEnabled(account, null);
            activities.setHasRateInfo(hasRateInfo);

            // get the conversion result
            if (hasRateInfo) {
                // get the relevant transfer type for conversions
                final Collection<TransferType> currencyConversionTTs = transferTypeService.getConversionTTs(account.getType().getCurrency());
                final Collection<TransferType> accountConversionTTs = transferTypeService.getConversionTTs(account.getType());
                TransferType conversionTT = null;
                // there must be only 1 TT available on the account. if more than one, we don't know which to choose so show nothing.
                if (accountConversionTTs.size() == 1) {
                    final Object[] ttArray = accountConversionTTs.toArray();
                    conversionTT = (TransferType) ttArray[0];
                } else if (accountConversionTTs.size() == 0 && currencyConversionTTs.size() == 1) {
                    // OR in case there is none on the account, we will take the only one available on the currency.
                    final Object[] ttArray = currencyConversionTTs.toArray();
                    conversionTT = (TransferType) ttArray[0];
                }
                // if no balance or no TT, there's nothing to convert.
                final BigDecimal balance = accountStatus.getBalance();
                if (balance.compareTo(BigDecimal.ZERO) > 0 && conversionTT != null) {
                    final ConversionSimulationDTO dto = new ConversionSimulationDTO();
                    dto.setTransferType(conversionTT);
                    dto.setAccount(account);
                    dto.setAmount(balance);
                    dto.setUseActualRates(true);
                    dto.setDate(Calendar.getInstance());
                    final TransactionFeePreviewForRatesDTO result = paymentService.simulateConversion(dto);
                    activities.setTotalFeePercentage(result.getRatesAsFeePercentage());
                }
            }

            // rest of the activities info is subject to permissions
            if (showAccountInformation) {
                // Check if user has permission to view information of that account
                if (account.getMember().equals(loggedElement) || canViewAccountInformation(loggedElement, account)) {

                    activities.setShowAccountInfo(true);

                    GetTransactionsDTO txAllTime = new GetTransactionsDTO(account);
                    txAllTime.setRootOnly(true);
                    GetTransactionsDTO tx30Days = new GetTransactionsDTO(account, Period.begginingAt(days30));
                    tx30Days.setRootOnly(true);

                    activities.setCreditsAllTime(accountService.getCredits(txAllTime));
                    activities.setDebitsAllTime(accountService.getDebits(txAllTime));
                    activities.setCreditsLast30Days(accountService.getCredits(tx30Days));
                    activities.setDebitsLast30Days(accountService.getDebits(tx30Days));

                    // Get the broker commission
                    if (isBroker) {
                        activities.setBrokerCommission(accountService.getBrokerCommissions(allTime));
                    }

                    // Calculate the total of remaining loans
                    final LoanQuery loanQuery = new LoanQuery();
                    loanQuery.setMember(member);
                    loanQuery.setStatus(Loan.Status.OPEN);
                    loanQuery.setAccountType(account.getType());
                    int remainingLoans = 0;
                    BigDecimal remainingLoanAmount = BigDecimal.ZERO;
                    final List<Loan> loans = loanService.search(loanQuery);
                    for (final Loan loan : loans) {
                        remainingLoans++;
                        remainingLoanAmount = remainingLoanAmount.add(loan.getRemainingAmount());
                    }
                    activities.setRemainingLoans(new TransactionSummaryVO(remainingLoans, remainingLoanAmount));

                }
            }
            // Store this one, but only if there is info
            if (activities.isShowAccountInfo() || activities.isHasRateInfo()) {
                vo.addAccountActivities(account.getType().getName(), activities);
            }
        }
        return vo;
    }

    /**
     * 
     * @param member
     * @return true if the member has set at least one notification by sms
     */
    private boolean hasNotificationsBySms(final Member member) {
        Collection<NotificationPreference> preferences = preferenceService.load(member);
        if (CollectionUtils.isEmpty(preferences)) {
            return false;
        }

        for (NotificationPreference preference : preferences) {
            if (preference.isSms()) {
                return true;
            }
        }
        return false;
    }

    private MemberSmsStatus performGetSmsStatus(Member member) {
        // First, acquire a pessimistic lock
        LockHandler lockHandler = lockHandlerFactory.getLockHandler();
        lockHandler.lockSmsStatus(member);

        // Try to load the sms status. If none found, a new one will be returned
        member = fetchService.fetch(member, Element.Relationships.GROUP);
        final Calendar today = Calendar.getInstance();
        final Period currentMonth = TimePeriod.ONE_MONTH.currentPeriod(today);
        MemberSmsStatus status;
        try {
            // Try loading the member status
            status = memberSmsStatusDao.load(member);

            // If got to this line, the status exists
            boolean changed = false;
            if (today.after(status.getFreeSmsExpiration())) {
                // The free sms period has expired. Reset.
                status.setFreeSmsSent(0);
                status.setFreeSmsExpiration(currentMonth.getEnd());
                changed = true;
            }
            final Calendar paidSmsExpiration = status.getPaidSmsExpiration();
            if (paidSmsExpiration != null && today.after(paidSmsExpiration)) {
                // The paid sms messages have expired. Reset.
                status.setPaidSmsLeft(0);
                status.setPaidSmsExpiration(null);
                changed = true;
            }
            if (changed) {
                // Update the record if it has changed
                status = memberSmsStatusDao.update(status);
            }
        } catch (final EntityNotFoundException e) {
            // The status does not exist. Create a new one.
            MemberGroup group = member.getMemberGroup();
            status = new MemberSmsStatus();
            status.setMember(member);
            status.setFreeSmsExpiration(currentMonth.getEnd());
            status.setAllowChargingSms(group.isDefaultAllowChargingSms());
            status.setAcceptFreeMailing(group.isDefaultAcceptFreeMailing());
            status.setAcceptPaidMailing(group.isDefaultAcceptPaidMailing());

            ensureAllowChargingSms(status, hasNotificationsBySms(member));

            status = memberSmsStatusDao.insert(status);
        } finally {
            lockHandler.release();
        }
        return status;

    }

}
