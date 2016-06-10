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
package nl.strohalm.cyclos.webservices.accounts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.jws.WebService;

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccountOwner;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.access.AccessServiceLocal;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountServiceLocal;
import nl.strohalm.cyclos.services.customization.PaymentCustomFieldServiceLocal;
import nl.strohalm.cyclos.services.settings.SettingsServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentServiceLocal;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.webservices.PrincipalParameters;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.WebServiceFaultsEnum;
import nl.strohalm.cyclos.webservices.model.AccountHistoryTransferVO;
import nl.strohalm.cyclos.webservices.model.MemberAccountVO;
import nl.strohalm.cyclos.webservices.model.TransferTypeVO;
import nl.strohalm.cyclos.webservices.utils.AccountHelper;
import nl.strohalm.cyclos.webservices.utils.MemberHelper;
import nl.strohalm.cyclos.webservices.utils.WebServiceHelper;

import org.apache.commons.lang.StringUtils;

/**
 * Implementation for account web service
 * @author luis
 */
@WebService(name = "account", serviceName = "account")
public class AccountWebServiceImpl implements AccountWebService {

    private AccountServiceLocal            accountServiceLocal;
    private AccessServiceLocal             accessServiceLocal;
    private PaymentServiceLocal            paymentServiceLocal;
    private TransferTypeServiceLocal       transferTypeServiceLocal;
    private AccountHelper                  accountHelper;
    private MemberHelper                   memberHelper;
    private WebServiceHelper               webServiceHelper;
    private PaymentCustomFieldServiceLocal paymentCustomFieldServiceLocal;
    private SettingsServiceLocal           settingsServiceLocal;

    @Override
    @SuppressWarnings("unchecked")
    public List<MemberAccountVO> getMemberAccounts(final PrincipalParameters params) {
        final Member member = memberHelper.resolveMember(params);
        if (member == null) {
            return Collections.emptyList();
        }
        final List<MemberAccount> accounts = (List<MemberAccount>) accountServiceLocal.getAccounts(member, Account.Relationships.TYPE);
        return accountHelper.toVOs(accounts);
    }

    @Override
    public AccountHistoryTransferVO loadTransfer(final LoadTransferParameters params) {
        // Load the transfer
        Transfer transfer;
        try {
            transfer = paymentServiceLocal.load(params.getTransferId(), Payment.Relationships.FROM, Payment.Relationships.TO, Payment.Relationships.TYPE, Payment.Relationships.CUSTOM_VALUES);
        } catch (final RuntimeException e) {
            webServiceHelper.error(e);
            throw e;
        }

        if (LoggedUser.isUnrestrictedClient() && WebServiceContext.getClient().isCredentialsRequired()) {
            if (StringUtils.isEmpty(params.getPrincipal())) {
                throw WebServiceHelper.fault(WebServiceFaultsEnum.INVALID_PARAMETERS);
            } else if (StringUtils.isEmpty(params.getCredentials())) {
                throw WebServiceHelper.fault(WebServiceFaultsEnum.INVALID_CREDENTIALS);
            }
        }

        // Get and validate the member
        final Member member = memberHelper.resolveMember(params);
        if (member != null) {
            if (!member.equals(transfer.getFromOwner()) && !member.equals(transfer.getToOwner())) {
                throw new PermissionDeniedException();
            }
            // Ensure the channel is enabled for the given member
            memberHelper.checkChannelEnabledForMember(member);

            // Check credentials if required
            final String credentials = params.getCredentials();
            checkCredentialsIfNeeded(member, credentials);
        }
        // Convert to VO
        final List<PaymentCustomField> customFields = paymentCustomFieldServiceLocal.list(transfer.getType(), true);
        final AccountOwner owner = member == null ? SystemAccountOwner.instance() : member;
        return accountHelper.toVO(owner, transfer, customFields);
    }

    @Override
    public AccountHistoryResultPage searchAccountHistory(final AccountHistorySearchParameters params) {
        // Get the member and ensure it is valid
        final Member member = memberHelper.resolveMember(params.getPrincipalType(), params.getPrincipal());
        if (member == null) {
            throw WebServiceHelper.fault(WebServiceFaultsEnum.INVALID_PARAMETERS, "The member is null");
        }
        // Ensure the channel is enabled for the given member
        memberHelper.checkChannelEnabledForMember(member);

        // Check credentials if required
        final String credentials = params.getCredentials();
        checkCredentialsIfNeeded(member, credentials);

        // Get the query and account type
        final TransferQuery query = accountHelper.toQuery(params, member);

        final boolean isSmsChannel = WebServiceContext.getChannel().getInternalName().equals(settingsServiceLocal.getLocalSettings().getSmsChannelName());
        if (isSmsChannel) {
            query.setExcludeTransferType(((MemberGroup) member.getGroup()).getMemberSettings().getSmsChargeTransferType());
        }

        // Perform the search and get the status
        try {
            final List<Transfer> transfers = paymentServiceLocal.search(query);
            final AccountHistoryResultPage page = accountHelper.toAccountHistoryResultPage(member, transfers);
            final AccountStatus status = accountServiceLocal.getCurrentStatus(new AccountDTO(member, query.getType()));
            page.setAccountStatus(accountHelper.toVO(status));
            return page;
        } catch (final Exception e) {
            webServiceHelper.error(e);
            return null;
        }
    }

    @Override
    public List<TransferTypeVO> searchTransferTypes(final TransferTypeSearchParameters params) {
        // Get the allowed transfer types
        final ServiceClient client = WebServiceContext.getClient();
        final Collection<TransferType> allowedTTs = new ArrayList<TransferType>();
        allowedTTs.addAll(client.getDoPaymentTypes());
        allowedTTs.addAll(client.getReceivePaymentTypes());
        if (allowedTTs.isEmpty()) {
            return Collections.emptyList();
        }

        // Search according to the query
        final TransferTypeQuery query = accountHelper.toQuery(params);
        final List<TransferType> transferTypes = transferTypeServiceLocal.search(query);
        // Ensure only the allowed are returned
        transferTypes.retainAll(allowedTTs);
        return accountHelper.toTransferTypeVOs(transferTypes);
    }

    public void setAccessServiceLocal(final AccessServiceLocal accessService) {
        accessServiceLocal = accessService;
    }

    public void setAccountHelper(final AccountHelper accountHelper) {
        this.accountHelper = accountHelper;
    }

    public void setAccountServiceLocal(final AccountServiceLocal accountService) {
        accountServiceLocal = accountService;
    }

    public void setMemberHelper(final MemberHelper memberHelper) {
        this.memberHelper = memberHelper;
    }

    public void setPaymentCustomFieldServiceLocal(final PaymentCustomFieldServiceLocal paymentCustomFieldService) {
        paymentCustomFieldServiceLocal = paymentCustomFieldService;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        paymentServiceLocal = paymentService;
    }

    public void setSettingsServiceLocal(final SettingsServiceLocal settingsService) {
        settingsServiceLocal = settingsService;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        transferTypeServiceLocal = transferTypeService;
    }

    public void setWebServiceHelper(final WebServiceHelper webServiceHelper) {
        this.webServiceHelper = webServiceHelper;
    }

    private void checkCredentialsIfNeeded(final Member member, final String credentials) {
        final ServiceClient client = WebServiceContext.getClient();
        if (client.isCredentialsRequired()) {
            if (StringUtils.isEmpty(credentials)) {
                throw WebServiceHelper.fault(WebServiceFaultsEnum.INVALID_CREDENTIALS);
            }
            accessServiceLocal.checkCredentials(WebServiceContext.getChannel(), member.getMemberUser(), credentials, WebServiceContext.getRequest().getRemoteAddr(), WebServiceContext.getMember());
        }
    }

}
