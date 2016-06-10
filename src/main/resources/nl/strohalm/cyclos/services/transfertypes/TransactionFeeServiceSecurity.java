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
package nl.strohalm.cyclos.services.transfertypes;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.PermissionCheck;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.AccountOwner;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee.ARateRelation;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.ChargeType;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Nature;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Subject;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFeeQuery;
import nl.strohalm.cyclos.entities.accounts.transactions.Invoice;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.BaseServiceSecurity;
import nl.strohalm.cyclos.services.groups.GroupServiceLocal;
import nl.strohalm.cyclos.services.transactions.InvoiceServiceLocal;
import nl.strohalm.cyclos.services.transactions.PaymentServiceLocal;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.access.PermissionHelper;
import nl.strohalm.cyclos.webservices.model.TransactionFeeVO;

import org.apache.commons.collections.CollectionUtils;

/**
 * Security implementation for {@link TransactionFeeService}
 * 
 * @author ameyer
 * @author jcomas
 */
public class TransactionFeeServiceSecurity extends BaseServiceSecurity implements TransactionFeeService {

    private TransactionFeeServiceLocal transactionFeeService;
    private TransferTypeServiceLocal   transferTypeService;
    private InvoiceServiceLocal        invoiceService;
    private PaymentServiceLocal        paymentService;
    private GroupServiceLocal          groupService;

    @Override
    public Collection<ChargeType> getPossibleChargeType(final TransferType originalTransferType, final Nature feeNature) {
        permissionService.permission()
                .admin(AdminSystemPermission.ACCOUNTS_MANAGE)
                .check();
        return transactionFeeService.getPossibleChargeType(originalTransferType, feeNature);
    }

    @Override
    public Collection<Subject> getPossibleSubjects(final TransferType originalTransferType, final Nature nature) {
        permissionService.permission()
                .admin(AdminSystemPermission.ACCOUNTS_MANAGE)
                .check();
        return transactionFeeService.getPossibleSubjects(originalTransferType, nature);
    }

    @Override
    public List<TransactionFeeVO> getTransactionFeeVOs(final TransactionFeePreviewDTO preview) {
        Map<TransactionFee, BigDecimal> fees = preview.getFees();
        if (fees == null) {
            return null;
        }
        for (TransactionFee k : fees.keySet()) {
            checkView(k);
        }
        return transactionFeeService.getTransactionFeeVOs(preview);
    }

    @Override
    public TransactionFee load(final Long id, final Relationship... fetch) {
        TransactionFee fee = transactionFeeService.load(id, fetch);
        checkView(fee);
        return fee;
    }

    @Override
    public TransactionFeePreviewDTO preview(final AccountOwner from, final AccountOwner to, final TransferType transferType, final BigDecimal amount) {
        if (!paymentService.canMakePayment(from, to, transferType)) {
            throw new PermissionDeniedException();
        }
        return transactionFeeService.preview(from, to, transferType, amount);
    }

    @Override
    public TransactionFeePreviewDTO preview(final Invoice invoice) {
        if (!invoiceService.canAccept(invoice)) {
            throw new PermissionDeniedException();
        }
        return transactionFeeService.preview(invoice);
    }

    @Override
    public int remove(final Long... ids) {
        permissionService.permission()
                .admin(AdminSystemPermission.ACCOUNTS_MANAGE)
                .check();

        return transactionFeeService.remove(ids);
    }

    @Override
    public BrokerCommission save(final BrokerCommission brokerCommission) {
        permissionService.permission()
                .admin(AdminSystemPermission.ACCOUNTS_MANAGE)
                .check();
        checkTransactionFee(brokerCommission);
        return transactionFeeService.save(brokerCommission);
    }

    @Override
    public SimpleTransactionFee save(final SimpleTransactionFee transactionFee, final ARateRelation aRateRelation) {
        permissionService.permission()
                .admin(AdminSystemPermission.ACCOUNTS_MANAGE)
                .check();
        checkTransactionFee(transactionFee);
        return transactionFeeService.save(transactionFee, aRateRelation);
    }

    @Override
    public List<? extends TransactionFee> search(final TransactionFeeQuery query) {
        if (query.getMemberGroup() != null) {
            PermissionHelper.checkContains(permissionService.getManagedMemberGroups(), query.getMemberGroup());
        }
        if (query.getBrokerGroup() != null) {
            PermissionHelper.checkContains(permissionService.getManagedMemberGroups(), query.getBrokerGroup());
        }
        checkView(null);
        return transactionFeeService.search(query);
    }

    @Override
    public List<TransferType> searchGeneratedTransferTypes(final TransactionFee fee, final boolean allowAnyAccount, final boolean onlyFromSystem) {
        permissionService.permission()
                .admin(AdminSystemPermission.ACCOUNTS_MANAGE)
                .check();
        return transactionFeeService.searchGeneratedTransferTypes(fee, allowAnyAccount, onlyFromSystem);
    }

    public void setGroupServiceLocal(final GroupServiceLocal groupService) {
        this.groupService = groupService;
    }

    public void setInvoiceServiceLocal(final InvoiceServiceLocal invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void setPaymentServiceLocal(final PaymentServiceLocal paymentService) {
        this.paymentService = paymentService;
    }

    public void setTransactionFeeServiceLocal(final TransactionFeeServiceLocal transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    public void validate(final BrokerCommission brokerCommission) {
        transactionFeeService.validate(brokerCommission);
    }

    @Override
    public void validate(final SimpleTransactionFee transactionFee, final ARateRelation aRateRelation) {
        transactionFeeService.validate(transactionFee, aRateRelation);
    }

    /**
     * Checks that the given transactionFee comes with an allowed generated transfer type. It is assumed that the method @see
     * {@link #checkReadOnlyValues(TransactionFee)} is called before
     * @param transactionFee
     */
    private void checkGeneratedTransferType(final TransactionFee transactionFee) {
        List<TransferType> allowedGeneratedTransferTypes;

        TransferType generatedTransferType = transferTypeService.load(transactionFee.getGeneratedTransferType().getId());
        transactionFee.setGeneratedTransferType(generatedTransferType);
        if (!transactionFee.isTransient() && transactionFee.isFromSystem()) {
            allowedGeneratedTransferTypes = transactionFeeService.searchGeneratedTransferTypes(transactionFee, true, true);
        } else {
            allowedGeneratedTransferTypes = transactionFeeService.searchGeneratedTransferTypes(transactionFee, true, false);
        }
        PermissionHelper.checkContains(allowedGeneratedTransferTypes, transactionFee.getGeneratedTransferType());
    }

    private void checkGroups(final TransactionFee transactionFee) {
        TransferType transferType = fetchService.fetch(transactionFee.getOriginalTransferType(), TransferType.Relationships.FROM, TransferType.Relationships.TO);

        // If from member, search for groups related to the from account type...
        if (transferType.isFromMember() && !transactionFee.isFromAllGroups() && CollectionUtils.isNotEmpty(transactionFee.getFromGroups())) {
            checkGroups(transferType, transactionFee.getFromGroups(), false, true);
        }

        // ... same for to
        if (transferType.isToMember() && !transactionFee.isToAllGroups() && CollectionUtils.isNotEmpty(transactionFee.getToGroups())) {
            checkGroups(transferType, transactionFee.getToGroups(), false, false);
        }

        // For broker groups, list only active
        if (transactionFee.getNature() == Nature.BROKER) {
            BrokerCommission brokerCommission = (BrokerCommission) transactionFee;
            if (!brokerCommission.isAllBrokerGroups() && CollectionUtils.isNotEmpty(brokerCommission.getBrokerGroups())) {
                checkGroups(transferType, brokerCommission.getBrokerGroups(), true, null);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void checkGroups(final TransferType transferType, final Collection<? extends MemberGroup> groups, final boolean activeBrokersOnly, final Boolean from) {
        final GroupQuery groupQuery = new GroupQuery();
        if (activeBrokersOnly) {
            groupQuery.setNature(Group.Nature.BROKER);
            groupQuery.setOnlyActive(true);
        } else {
            groupQuery.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
        }
        groupQuery.setStatus(Group.Status.NORMAL);
        if (from != null) {
            groupQuery.setMemberAccountType((MemberAccountType) (from ? transferType.getFrom() : transferType.getTo()));
        }
        List<MemberGroup> allowedGroups = (List<MemberGroup>) groupService.search(groupQuery);
        PermissionHelper.checkSelection(allowedGroups, (Collection<MemberGroup>) groups);
    }

    /**
     * Ensures that certain values didn't changed when an already existing transaction fee is modified.
     * @param modifiedTF The transaction fee being modified.
     */
    private void checkReadOnlyValues(final TransactionFee modifiedTF) {
        if (modifiedTF.isPersistent()) {
            TransactionFee savedTF = load(modifiedTF.getId());
            PermissionHelper.checkEquals(savedTF.getOriginalTransferType(), modifiedTF.getOriginalTransferType());
            PermissionHelper.checkEquals(savedTF.getPayer(), modifiedTF.getPayer());

            if (savedTF.getNature() == Nature.SIMPLE) {
                SimpleTransactionFee modifiedSTF = (SimpleTransactionFee) modifiedTF;
                SimpleTransactionFee savedSTF = (SimpleTransactionFee) savedTF;
                PermissionHelper.checkEquals(savedSTF.getReceiver(), modifiedSTF.getReceiver());
                PermissionHelper.checkEquals(savedSTF.getFromFixedMember(), modifiedSTF.getFromFixedMember());
                PermissionHelper.checkEquals(savedSTF.getToFixedMember(), modifiedSTF.getToFixedMember());
            } else if (savedTF.getNature() == Nature.BROKER) {
                BrokerCommission modifiedBC = (BrokerCommission) modifiedTF;
                BrokerCommission savedBC = (BrokerCommission) savedTF;
                if (!savedTF.getGeneratedTransferType().isFromSystem()) {
                    PermissionHelper.checkEquals(savedTF.getGeneratedTransferType(), modifiedTF.getGeneratedTransferType());
                }
                PermissionHelper.checkEquals(savedBC.getWhichBroker(), modifiedBC.getWhichBroker());
            }
        }
    }

    /**
     * Check that the subjects are the allowed.
     */
    private void checkSubjects(final TransactionFee transactionFee) {
        Collection<Subject> possibleSubjects = transactionFeeService.getPossibleSubjects(transactionFee.getOriginalTransferType(), transactionFee.getNature());

        // check that the payer is a possible subject
        PermissionHelper.checkContains(possibleSubjects, transactionFee.getPayer());

        if (transactionFee.getNature() == Nature.SIMPLE) {
            // check that the receiver is a possible subject
            Subject receiver = ((SimpleTransactionFee) transactionFee).getReceiver();
            PermissionHelper.checkContains(possibleSubjects, receiver);
        }

    }

    private void checkTransactionFee(final TransactionFee transactionFee) {

        checkReadOnlyValues(transactionFee);

        // Check if manages the from fixed member
        if (transactionFee.getFromFixedMember() != null) {
            permissionService.checkManages(transactionFee.getFromFixedMember());
        }

        // Check if manages the to fixed member
        if (transactionFee.getNature() == Nature.SIMPLE) {
            Member toFixedMember = ((SimpleTransactionFee) transactionFee).getToFixedMember();
            if (toFixedMember != null) {
                permissionService.checkManages(toFixedMember);
            }
        }
        checkGroups(transactionFee);
        checkGeneratedTransferType(transactionFee);
        checkSubjects(transactionFee);

        // Check the charge type
        PermissionHelper.checkContains(transactionFeeService.getPossibleChargeType(transactionFee.getOriginalTransferType(), transactionFee.getNature()), transactionFee.getChargeType());
    }

    private void checkView(final TransactionFee fee) {
        PermissionCheck check = permissionService.permission()
                .admin(
                        AdminMemberPermission.BROKERINGS_MANAGE_COMMISSIONS, AdminSystemPermission.ACCOUNTS_VIEW)
                .broker(BrokerPermission.MEMBERS_MANAGE_DEFAULTS, BrokerPermission.MEMBERS_MANAGE_CONTRACTS);

        boolean hasPermission = check.hasPermission();

        if (!hasPermission) {
            if (fee != null) {
                // we allow access if the fee's from account type is associated to the member's group
                AccountType fromAccType = fee.getOriginalTransferType().getFrom();
                AccountOwner accountOwner = LoggedUser.accountOwner();
                if (accountOwner instanceof Member) { // member, broker or operator
                    MemberGroup mGroup = ((Member) accountOwner).getMemberGroup();
                    Collection<MemberGroupAccountSettings> mgaccSettings = mGroup.getAccountSettings();
                    if (mgaccSettings != null) {
                        for (MemberGroupAccountSettings mgaccSetting : mGroup.getAccountSettings()) {
                            if (mgaccSetting.getAccountType().equals(fromAccType)) {
                                return; // ok
                            }
                        }
                    }
                }
            }
            throw new PermissionDeniedException();
        }
    }
}
