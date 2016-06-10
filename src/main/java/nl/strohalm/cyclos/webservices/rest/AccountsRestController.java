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
package nl.strohalm.cyclos.webservices.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilterQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilterQuery.Context;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPayment;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPaymentQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPaymentQuery.SearchType;
import nl.strohalm.cyclos.entities.accounts.transactions.ScheduledPaymentQuery.StatusGroup;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Contact;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.accounts.CurrencyService;
import nl.strohalm.cyclos.services.elements.ContactService;
import nl.strohalm.cyclos.services.elements.MemberService;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.ScheduledPaymentService;
import nl.strohalm.cyclos.services.transfertypes.PaymentFilterService;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.conversion.Transformer;
import nl.strohalm.cyclos.webservices.accounts.AccountHistoryResultPage;
import nl.strohalm.cyclos.webservices.accounts.ScheduledPaymentsResultPage;
import nl.strohalm.cyclos.webservices.model.AccountHistoryTransferVO;
import nl.strohalm.cyclos.webservices.model.AccountStatusVO;
import nl.strohalm.cyclos.webservices.model.MemberAccountVO;
import nl.strohalm.cyclos.webservices.model.MemberAccountWithStatusVO;
import nl.strohalm.cyclos.webservices.model.PaymentFilterVO;
import nl.strohalm.cyclos.webservices.model.ScheduledPaymentVO;
import nl.strohalm.cyclos.webservices.model.SearchParameters;
import nl.strohalm.cyclos.webservices.model.TransferDataVO;
import nl.strohalm.cyclos.webservices.payments.AccountHistoryParams;
import nl.strohalm.cyclos.webservices.utils.QueryHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller which handles /accounts paths
 * 
 * @author luis
 */
@Controller
public class AccountsRestController extends BaseRestController {

    /**
     * Parameters for searching scheduled payments
     * @author luis
     */
    public static class SearchScheduledPaymentParams extends SearchParameters {
        private static final long            serialVersionUID = 1L;
        private String                       memberPrincipal;
        private Long                         memberId;
        private Calendar                     beginDate;
        private Calendar                     endDate;
        private Boolean                      incoming         = false;
        private SearchScheduledPaymentStatus status;

        public Calendar getBeginDate() {
            return beginDate;
        }

        public Calendar getEndDate() {
            return endDate;
        }

        public boolean getIncoming() {
            return incoming;
        }

        public Long getMemberId() {
            return memberId;
        }

        public String getMemberPrincipal() {
            return memberPrincipal;
        }

        public SearchScheduledPaymentStatus getStatus() {
            return status;
        }

        public void setBeginDate(final Calendar beginDate) {
            this.beginDate = beginDate;
        }

        public void setEndDate(final Calendar endDate) {
            this.endDate = endDate;
        }

        public void setIncoming(final boolean incoming) {
            this.incoming = incoming;
        }

        public void setMemberId(final Long memberId) {
            this.memberId = memberId;
        }

        public void setMemberPrincipal(final String memberPrincipal) {
            this.memberPrincipal = memberPrincipal;
        }

        public void setStatus(final SearchScheduledPaymentStatus status) {
            this.status = status;
        }
    }

    public static enum SearchScheduledPaymentStatus {
        OPEN, CLOSED_WITHOUT_ERRORS, CLOSED_WITH_ERRORS;
    }

    private static final String         CUSTOM_VALUE_PREFIX = "customValue.";

    private AccountService              accountService;
    private ContactService              contactService;
    private PaymentService              paymentService;
    private QueryHelper                 queryHelper;
    private PaymentFilterService        paymentFilterService;
    private ScheduledPaymentService     scheduledPaymentService;
    private CurrencyService             currencyService;
    private MemberService               memberService;
    private PaymentFieldsRestController paymentFieldsRestController;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "accounts/info", method = RequestMethod.GET)
    @ResponseBody
    public List<MemberAccountWithStatusVO> getAccountsInfo() {
        List<MemberAccount> accounts = (List<MemberAccount>) accountService.getAccounts(LoggedUser.member());
        List<MemberAccountWithStatusVO> result = new ArrayList<MemberAccountWithStatusVO>();
        for (MemberAccount memberAccount : accounts) {
            AccountStatusVO statusVO = accountService.getCurrentAccountStatusVO(new AccountDTO(memberAccount));
            MemberAccountVO accountVO = accountService.getMemberAccountVO(memberAccount.getId());
            MemberAccountWithStatusVO as = new MemberAccountWithStatusVO(accountVO, statusVO);
            result.add(as);
        }
        return result;
    }

    /**
     * Returns the account status for the default account.
     */
    @RequestMapping(value = "accounts/default/status", method = RequestMethod.GET)
    @ResponseBody
    public AccountStatusVO getDefaultStatus() {
        MemberAccount account = accountService.getDefaultAccount();
        return accountService.getCurrentAccountStatusVO(new AccountDTO(account));
    }

    /**
     * Returns the account status for the given account.
     */
    @RequestMapping(value = "accounts/{id}/status", method = RequestMethod.GET)
    @ResponseBody
    public AccountStatusVO getStatus(@PathVariable final Long id) {
        MemberAccount account;
        try {
            account = accountService.load(id);
        } catch (Exception e) {
            throw new EntityNotFoundException(Account.class);
        }
        return accountService.getCurrentAccountStatusVO(new AccountDTO(account));
    }

    /**
     * Returns the authenticated user's accounts
     */
    @RequestMapping(value = "accounts", method = RequestMethod.GET)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public List<MemberAccountVO> listAccounts() {
        List<MemberAccount> accounts = (List<MemberAccount>) accountService.getAccounts(LoggedUser.member());
        List<MemberAccountVO> result = new ArrayList<MemberAccountVO>(accounts.size());
        if (accounts != null) {
            for (MemberAccount memberAccount : accounts) {
                MemberAccountVO memberAccountVO = accountService.getMemberAccountVO(memberAccount.getId());
                result.add(memberAccountVO);
            }
        }
        return result;
    }

    /**
     * Returns the available payment filters for an specific account
     */
    @RequestMapping(value = "accounts/default/paymentFilters", method = RequestMethod.GET)
    @ResponseBody
    public List<PaymentFilterVO> listDefaultPaymentFilters() {
        MemberAccount account = accountService.getDefaultAccount();
        return listPaymentFilters(account);
    }

    /**
     * Returns the available payment filters for an specific account
     */
    @RequestMapping(value = "accounts/{id}/paymentFilters", method = RequestMethod.GET)
    @ResponseBody
    public List<PaymentFilterVO> listPaymentFilters(@PathVariable final Long id) {
        MemberAccount account;
        try {
            account = accountService.load(id);
        } catch (Exception e) {
            throw new EntityNotFoundException(Account.class);
        }
        return listPaymentFilters(account);
    }

    /**
     * Loads data for an account by id. The account must belong to the authenticated user.
     */
    @RequestMapping(value = "accounts/{id}", method = RequestMethod.GET)
    @ResponseBody
    public MemberAccountVO loadAccount(@PathVariable final Long id) {
        return accountService.getMemberAccountVO(id);
    }

    /**
     * Attempts to load a currency by either id or symbol
     */
    public Currency loadCurrencyByIdOrSymbol(final Long id, final String symbol) {
        String idOrSymbol = id == null ? symbol : id.toString();
        if (StringUtils.isNotEmpty(idOrSymbol)) {
            return currencyService.loadBySymbolOrId(idOrSymbol);
        }
        return null;
    }

    /**
     * Loads data for an account by id. The account must belong to the authenticated user.
     */
    @RequestMapping(value = "accounts/default", method = RequestMethod.GET)
    @ResponseBody
    public MemberAccountVO loadDefaultAccount() {
        MemberAccount account = accountService.getDefaultAccount();
        return accountService.getMemberAccountVO(account.getId());
    }

    /**
     * Loads a scheduled payment by id
     */
    @RequestMapping(value = "accounts/scheduledPayment/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ScheduledPaymentVO loadScheduledPayment(@PathVariable final Long id) {
        return scheduledPaymentService.getScheduledPaymentVO(id);
    }

    /**
     * Loads a transfer by id
     */
    @RequestMapping(value = "accounts/transfer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AccountHistoryTransferVO loadTransfer(@PathVariable final Long id) {
        return paymentService.getAccountHistoryTransferVO(id);
    }

    /**
     * Loads a transfer by id and return it with the list of payment custom fields of the transfer type.
     */
    @RequestMapping(value = "accounts/transferData/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TransferDataVO loadTransferData(@PathVariable final Long id) {
        Transfer transfer;
        try {
            transfer = paymentService.load(id);
        } catch (Exception e) {
            throw new EntityNotFoundException(Transfer.class);
        }
        AccountHistoryTransferVO accountHistoryTransferVO = paymentService.getAccountHistoryTransferVO(id);

        AccountOwner relatedAccountOwner = null;
        if (LoggedUser.member().equals(transfer.getFromOwner())) {
            relatedAccountOwner = transfer.getToOwner();
        } else {
            relatedAccountOwner = transfer.getFromOwner();
        }

        boolean canAddRelatedMemberAsContact = false;

        // The related account owner can be added to the logged user's contact list if its a member and it's not already added.
        if (relatedAccountOwner != null && relatedAccountOwner instanceof Member && LoggedUser.member() != relatedAccountOwner) {
            Member m = (Member) relatedAccountOwner;
            Contact contact = contactService.loadIfExists(LoggedUser.member(), m);
            if (contact == null) {
                canAddRelatedMemberAsContact = true;
            }
        }

        return new TransferDataVO(accountHistoryTransferVO, canAddRelatedMemberAsContact);
    }

    /**
     * Searches for account history entries on the given account
     */
    @RequestMapping(value = "accounts/{id}/history", method = RequestMethod.GET)
    @ResponseBody
    public AccountHistoryResultPage searchAccountHistory(@PathVariable final Long id, final AccountHistoryParams params, final HttpServletRequest request) {
        params.setMemberAccountId(id);
        return search(params, request);
    }

    /**
     * Searches for account history entries on the default account
     */
    @RequestMapping(value = "accounts/default/history", method = RequestMethod.GET)
    @ResponseBody
    public AccountHistoryResultPage searchDefaultAccountHistory(final AccountHistoryParams params, final HttpServletRequest request) {
        params.setMemberAccountId(null);
        return search(params, request);
    }

    /**
     * Searches for scheduled payments on the default account
     */
    @RequestMapping(value = "accounts/default/scheduledPayments", method = RequestMethod.GET)
    @ResponseBody
    public ScheduledPaymentsResultPage searchDefaultScheduledPayments(final SearchScheduledPaymentParams params) {
        MemberAccount account = accountService.getDefaultAccount();
        return searchScheduledPayments(account, params);
    }

    /**
     * Searches for scheduled payments on the given account
     */
    @RequestMapping(value = "accounts/{id}/scheduledPayments", method = RequestMethod.GET)
    @ResponseBody
    public ScheduledPaymentsResultPage searchScheduledPayments(@PathVariable final Long id, final SearchScheduledPaymentParams params) {
        MemberAccount account = accountService.load(id);
        return searchScheduledPayments(account, params);
    }

    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    public void setContactService(final ContactService contactService) {
        this.contactService = contactService;
    }

    public void setCurrencyService(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public void setMemberService(final MemberService memberService) {
        this.memberService = memberService;
    }

    public void setPaymentFieldsRestController(final PaymentFieldsRestController paymentFieldsRestController) {
        this.paymentFieldsRestController = paymentFieldsRestController;
    }

    public void setPaymentFilterService(final PaymentFilterService paymentFilterService) {
        this.paymentFilterService = paymentFilterService;
    }

    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void setQueryHelper(final QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    public void setScheduledPaymentService(final ScheduledPaymentService scheduledPaymentService) {
        this.scheduledPaymentService = scheduledPaymentService;
    }

    private List<PaymentFilterVO> listPaymentFilters(final MemberAccount account) {
        PaymentFilterQuery query = new PaymentFilterQuery();
        query.setContext(Context.ACCOUNT_HISTORY);
        query.setAccount(account);
        List<PaymentFilter> paymentFilters = paymentFilterService.search(query);
        return paymentFilterService.getPaymentFilterVOs(paymentFilters);
    }

    private AccountHistoryResultPage search(final AccountHistoryParams params, final HttpServletRequest request) {
        params.setCustomValues(paymentFieldsRestController.requestParametersToFieldValues(request, CUSTOM_VALUE_PREFIX));

        return paymentService.getAccountHistoryResultPage(params);
    }

    private ScheduledPaymentsResultPage searchScheduledPayments(final MemberAccount account, final SearchScheduledPaymentParams params) {
        Member member = memberService.loadByIdOrPrincipal(params.getMemberId(), null, params.getMemberPrincipal());

        // Convert the params to a TransferQuery
        ScheduledPaymentQuery query = new ScheduledPaymentQuery();
        queryHelper.fill(params, query);
        query.setOwner(account.getOwner());
        query.setMember(member);
        query.setPeriod(Period.between(params.getBeginDate(), params.getEndDate()));
        query.setSearchType(params.getIncoming() ? SearchType.INCOMING : SearchType.OUTGOING);
        if (params.getStatus() == null) {
            query.setStatusGroup(StatusGroup.OPEN);
        } else {
            query.setStatusGroup(StatusGroup.valueOf(params.getStatus().name()));
        }

        // Execute the search
        List<ScheduledPayment> scheduledPayments = scheduledPaymentService.search(query);
        return toScheduledResultPage(scheduledPayments);
    }

    private ScheduledPaymentsResultPage toScheduledResultPage(final List<ScheduledPayment> payments) {
        return queryHelper.toResultPage(ScheduledPaymentsResultPage.class, payments, new Transformer<ScheduledPayment, ScheduledPaymentVO>() {
            @Override
            public ScheduledPaymentVO transform(final ScheduledPayment scheduledPayment) {
                return scheduledPaymentService.getScheduledPaymentVO(scheduledPayment.getId());
            }
        });
    }

}
