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
package nl.strohalm.cyclos.webservices.pos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.access.PrincipalType;
import nl.strohalm.cyclos.entities.accounts.AccountStatus;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccount;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.pos.MemberPos;
import nl.strohalm.cyclos.entities.accounts.transactions.Payment;
import nl.strohalm.cyclos.entities.accounts.transactions.Transfer;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferAuthorizationDTO;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.LocalSettings.TransactionNumber;
import nl.strohalm.cyclos.services.access.AccessService;
import nl.strohalm.cyclos.services.access.ChannelService;
import nl.strohalm.cyclos.services.access.exceptions.InvalidUserForChannelException;
import nl.strohalm.cyclos.services.accounts.AccountDTO;
import nl.strohalm.cyclos.services.accounts.AccountService;
import nl.strohalm.cyclos.services.accounts.pos.MemberPosServiceLocal;
import nl.strohalm.cyclos.services.elements.ElementService;
import nl.strohalm.cyclos.services.settings.SettingsService;
import nl.strohalm.cyclos.services.transactions.DoPaymentDTO;
import nl.strohalm.cyclos.services.transactions.PaymentService;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transactions.TransferAuthorizationService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.query.PageParameters;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.webservices.WebServiceContext;
import nl.strohalm.cyclos.webservices.WebServiceFaultsEnum;
import nl.strohalm.cyclos.webservices.accounts.AccountHistoryResultPage;
import nl.strohalm.cyclos.webservices.model.AccountHistoryTransferVO;
import nl.strohalm.cyclos.webservices.model.AccountStatusVO;
import nl.strohalm.cyclos.webservices.model.DetailedAccountTypeVO;
import nl.strohalm.cyclos.webservices.model.PosInitializationVO;
import nl.strohalm.cyclos.webservices.model.TransactionNumberVO;
import nl.strohalm.cyclos.webservices.model.WSPrincipalTypeVO;
import nl.strohalm.cyclos.webservices.model.WSPrincipalTypeVO.WSPrincipal;
import nl.strohalm.cyclos.webservices.payments.ChargebackResult;
import nl.strohalm.cyclos.webservices.payments.ChargebackStatus;
import nl.strohalm.cyclos.webservices.payments.PaymentResult;
import nl.strohalm.cyclos.webservices.payments.PaymentStatus;
import nl.strohalm.cyclos.webservices.utils.AccountHelper;
import nl.strohalm.cyclos.webservices.utils.MemberHelper;
import nl.strohalm.cyclos.webservices.utils.PaymentHelper;
import nl.strohalm.cyclos.webservices.utils.WebServiceHelper;

/**
 * Implementation for PosWebService <br>
 * 
 * IMPORTANT: THIS WEB SERVICE IMPLEMENTATION DOESN'T USE SERVICE CLIENTS <br>
 * THEN IT MUST USE THE REMOTE (SECURITY) SERVICES INSTEAD OF THE LOCAL SERVICES
 * @author luis, ameyer
 */
@WebService(name = "pos", serviceName = "pos")
public class PosWebServiceImpl implements PosWebService {

    private AccessService                accessService;
    private TransferTypeService          transferTypeService;
    private ElementService               elementService;
    private AccountService               accountService;
    private ChannelService               channelService;
    private SettingsService              settingsService;
    private PaymentService               paymentService;
    private AccountHelper                accountHelper;
    private PaymentHelper                paymentHelper;
    private MemberHelper                 memberHelper;
    private WebServiceHelper             webServiceHelper;
    private TransferAuthorizationService transferAuthorizationService;
    private MessageResolver              messageResolver;
    /**
     * The MemberPosService is the only one accessed through the local interface, as it's not going to operate over the pos, but just validate it,
     * through the checkPin method, which is on the local interface only
     */
    private MemberPosServiceLocal        memberPosService;

    @Override
    public ChargebackResult chargeback(final ChargebackParameters parameters) {
        ChargebackStatus status = null;
        final Member member = WebServiceContext.getMember();

        // Find the transfer
        Transfer transfer = null;
        Transfer chargebackTransfer = null;
        try {
            transfer = paymentService.load(parameters.getTransferId());
            // Ensure the member is the one who received the payment
            if (!transfer.getToOwner().equals(member)) {
                throw new EntityNotFoundException();
            }
        } catch (final EntityNotFoundException e) {
            status = ChargebackStatus.TRANSFER_NOT_FOUND;
        }
        // Check if the transfer can be charged back
        if (status == null && !paymentService.canChargeback(transfer, false)) {
            if (transfer.getChargedBackBy() != null) {
                status = ChargebackStatus.TRANSFER_ALREADY_CHARGEDBACK;
            } else {
                if (transfer.getStatus() == Payment.Status.PENDING) {
                    final TransferAuthorizationDTO transferAuthorizationDto = new TransferAuthorizationDTO();
                    transferAuthorizationDto.setTransfer(transfer);
                    transferAuthorizationDto.setShowToMember(false);
                    chargebackTransfer = transferAuthorizationService.cancel(transferAuthorizationDto);
                    status = ChargebackStatus.SUCCESS;
                } else {
                    status = ChargebackStatus.TRANSFER_CANNOT_BE_CHARGEDBACK;
                }
            }
        }

        // Check the amount
        if (status == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();
            if (!localSettings.truncate(transfer.getAmount()).equals(localSettings.truncate(parameters.getAmount()))) {
                status = ChargebackStatus.INVALID_PARAMETERS;
            }
        }

        // Do the chargeback
        if (status == null) {
            chargebackTransfer = paymentService.chargeback(transfer);
            status = ChargebackStatus.SUCCESS;
        }

        if (!status.isSuccessful()) {
            webServiceHelper.error("Chargeback result " + status);
        }

        // Build the result
        if (status == ChargebackStatus.SUCCESS || status == ChargebackStatus.TRANSFER_ALREADY_CHARGEDBACK) {
            final AccountHistoryTransferVO originalVO = accountHelper.toVO(member, transfer, null);
            final AccountHistoryTransferVO chargebackVO = accountHelper.toVO(member, chargebackTransfer, null);
            return new ChargebackResult(status, originalVO, chargebackVO);
        } else {
            return new ChargebackResult(status, null, null);
        }
    }

    @Override
    public AccountStatusVO getAccountStatus(final AccountStatusPosParameters parameters) {
        final Member member = WebServiceContext.getMember();
        final MemberAccountType accountType = EntityHelper.reference(MemberAccountType.class, parameters.getAccountTypeId());
        final AccountStatus accountStatus = accountService.getCurrentStatus(new AccountDTO(member, accountType));
        return accountHelper.toVO(accountStatus);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PosInitializationVO getInitializationData(final InitializationParameters params) {
        final MemberPos memberPos = WebServiceContext.getPos().getMemberPos();
        final Member member = memberPos.getMember();

        final PosInitializationVO initializationData = new PosInitializationVO();

        // Set the owner
        initializationData.setOwner(memberHelper.toVO(memberPos.getMember()));

        // Get the POS channel principal type
        final Channel posChannel = WebServiceContext.getChannel();
        final List<WSPrincipalTypeVO> principalTypes = new ArrayList<WSPrincipalTypeVO>();
        final PrincipalType defaultPrincipalType = posChannel.getDefaultPrincipalType();
        for (final PrincipalType principalType : posChannel.getPrincipalTypes()) {
            final WSPrincipalTypeVO type = new WSPrincipalTypeVO();
            final Principal principal = principalType.getPrincipal();
            type.setPrincipal(WSPrincipal.valueOf(principal.name()));
            type.setDefault(principalType.equals(defaultPrincipalType));
            final MemberCustomField customField = principalType.getCustomField();
            if (customField != null) {
                type.setCustomFieldInternalName(customField.getInternalName());
                type.setLabel(customField.getName());
            } else {
                type.setLabel(messageResolver.message(principal.getKey()));
            }

            principalTypes.add(type);
        }
        initializationData.setPrincipalTypes(principalTypes);

        // Get the member accounts
        final List<DetailedAccountTypeVO> accountVOs = new ArrayList<DetailedAccountTypeVO>();
        for (final MemberAccount memberAccount : (List<MemberAccount>) accountService.getAccounts(member)) {
            if (memberAccount.getStatus() == MemberAccount.Status.ACTIVE) { // only add the active accounts
                accountVOs.add(accountHelper.toDetailedTypeVO(Channel.POS, memberAccount));
            }
        }
        initializationData.setAccountTypes(accountVOs);

        // Get data from settings
        final LocalSettings localSettings = settingsService.getLocalSettings();
        final TransactionNumber transactionNumber = localSettings.getTransactionNumber();
        if (transactionNumber != null) {
            initializationData.setTransactionNumber(new TransactionNumberVO(transactionNumber.getPrefix(), transactionNumber.getPadLength(), transactionNumber.getSuffix()));
        }
        initializationData.setDecimalDigits(localSettings.getPrecision().getValue());

        // Get data from MemberPos
        initializationData.setMaxSchedulingPayments(memberPos.getMaxSchedulingPayments());
        initializationData.setNumberOfCopies(memberPos.getNumberOfCopies());
        initializationData.setResultPageSize(memberPos.getResultPageSize());
        initializationData.setAllowMakePayment(memberPos.isAllowMakePayment());

        return initializationData;
    }

    @Override
    public PaymentResult makePayment(final MakePaymentParameters params) {
        PaymentStatus status = null;
        final MemberPos memberPos = WebServiceContext.getPos().getMemberPos();
        DoPaymentDTO dto = null;
        Member toMember = null;
        TransferType transferType = null;
        try {
            // In that case the pos pin is not validated by the interceptor (MakePaymentParameters not implements the IPosPinParameter interface)
            // because we want to set a status indicating the error
            checkPin(params.getPosPin());
        } catch (final Exception e) {
            webServiceHelper.error(e);
            status = paymentHelper.toStatus(e);
        }
        if (status == null) {
            // Ensure make payment is enabled
            if (!memberPos.isAllowMakePayment()) {
                throw WebServiceHelper.fault(WebServiceFaultsEnum.UNAUTHORIZED_ACCESS, "Make payment is not allowed");
            }
            // Get the parameters
            try {
                final PrincipalType principalType = channelService.resolvePrincipalType(Channel.POS, params.getToMemberPrincipalType());
                toMember = elementService.loadByPrincipal(principalType, params.getToMemberPrincipal());
                transferType = transferTypeService.load(params.getTransferTypeId());
            } catch (final Exception e) {
                webServiceHelper.error(e);
                status = PaymentStatus.INVALID_PARAMETERS;
            }
        }
        // Ok so far: set the transfer DTO
        if (status == null) {
            dto = new DoPaymentDTO();
            dto.setContext(TransactionContext.PAYMENT);
            dto.setChannel(Channel.POS);
            dto.setAmount(params.getAmount());
            dto.setFrom(memberPos.getMember());
            dto.setTo(toMember);
            dto.setTransferType(transferType);
        }
        // Perform the payment
        AccountHistoryTransferVO transferVO = null;
        if (status == null) {
            try {
                final Transfer transfer = (Transfer) paymentService.doPayment(dto);
                status = paymentHelper.toStatus(transfer);
                transferVO = accountHelper.toVO(dto.getFrom(), transfer, null);
            } catch (final Exception e) {
                webServiceHelper.error(e);
                status = paymentHelper.toStatus(e);
            }
        }
        return new PaymentResult(status, transferVO);
    }

    @Override
    public PaymentResult receivePayment(final ReceivePaymentParameters params) {

        // Get the member pos
        final Member member = WebServiceContext.getMember();

        // Get the parameters
        PaymentStatus status = null;
        Member fromMember = null;
        TransferType transferType = null;
        AccountHistoryTransferVO transferVO = null;

        try {
            final HttpServletRequest request = WebServiceContext.getRequest();
            final String credentials = params.getFromMemberCredentials();
            final String remoteAddress = request.getRemoteAddr();
            fromMember = memberHelper.loadByPrincipal(params.getFromMemberPrincipalType(), params.getFromMemberPrincipal());

            if (fromMember == null) {
                throw new EntityNotFoundException(Member.class);
            }
            // Check if the POS channel is enabled for the payer:
            if (!accessService.isChannelEnabledForMember(Channel.POS, fromMember)) {
                throw new InvalidUserForChannelException(fromMember.getUsername());
            }

            accessService.checkCredentials(WebServiceContext.getChannel(), fromMember.getMemberUser(), credentials, remoteAddress, member);
            transferType = transferTypeService.load(params.getTransferTypeId());

            final DoPaymentDTO dto = new DoPaymentDTO();
            dto.setContext(TransactionContext.PAYMENT);
            dto.setChannel(Channel.POS);
            dto.setAmount(params.getAmount());
            dto.setFrom(fromMember);
            dto.setTo(member);
            dto.setTransferType(transferType);

            // run as the payer
            final Transfer transfer = LoggedUser.runAs(fromMember.getUser(), new Callable<Transfer>() {
                @Override
                public Transfer call() throws Exception {
                    return (Transfer) paymentService.doPayment(dto);
                }
            });
            status = paymentHelper.toStatus(transfer);
            transferVO = accountHelper.toVO(member, transfer, null);

        } catch (final Exception e) {
            webServiceHelper.error(e);
            status = paymentHelper.toStatus(e);
        }

        return new PaymentResult(status, transferVO);
    }

    @Override
    public AccountHistoryResultPage searchAccountHistory(final AccountHistoryPosParameters parameters) {
        final MemberPos memberPos = WebServiceContext.getPos().getMemberPos();

        // Prepare the parameters
        final Member member = memberPos.getMember();
        final int pageSize = memberPos.getResultPageSize();
        final int currentPage = parameters.getCurrentPage();
        final AccountType accountType = EntityHelper.reference(AccountType.class, parameters.getAccountTypeId());

        // Query the transfers
        final TransferQuery query = new TransferQuery();
        query.setOwner(member);
        query.setType(accountType);
        query.setResultType(ResultType.PAGE);
        query.setPageParameters(new PageParameters(pageSize, currentPage));
        final List<Transfer> transfers = paymentService.search(query);

        return accountHelper.toAccountHistoryResultPage(member, transfers);
    }

    public void setAccessService(final AccessService accessService) {
        this.accessService = accessService;
    }

    public void setAccountHelper(final AccountHelper accountHelper) {
        this.accountHelper = accountHelper;
    }

    public void setAccountService(final AccountService accountService) {
        this.accountService = accountService;
    }

    public void setChannelService(final ChannelService channelService) {
        this.channelService = channelService;
    }

    public void setElementService(final ElementService elementService) {
        this.elementService = elementService;
    }

    public void setMemberHelper(final MemberHelper memberHelper) {
        this.memberHelper = memberHelper;
    }

    public void setMemberPosServiceLocal(final MemberPosServiceLocal memberPosService) {
        this.memberPosService = memberPosService;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setPaymentHelper(final PaymentHelper paymentHelper) {
        this.paymentHelper = paymentHelper;
    }

    public void setPaymentService(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void setSettingsService(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public void setTransferAuthorizationService(final TransferAuthorizationService transferAuthorizationService) {
        this.transferAuthorizationService = transferAuthorizationService;
    }

    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    public void setWebServiceHelper(final WebServiceHelper webServiceHelper) {
        this.webServiceHelper = webServiceHelper;
    }

    private void checkPin(final String posPin) {
        memberPosService.checkPin(WebServiceContext.getPos().getMemberPos(), posPin);
    }
}
