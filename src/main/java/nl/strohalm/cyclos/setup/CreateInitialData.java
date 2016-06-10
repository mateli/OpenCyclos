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
package nl.strohalm.cyclos.setup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountLock;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.Currency;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.MemberGroupAccountSettings;
import nl.strohalm.cyclos.entities.accounts.SystemAccount;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.InvoiceMode;
import nl.strohalm.cyclos.entities.accounts.fees.account.AccountFee.PaymentDirection;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission.When;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission.WhichBroker;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Subject;
import nl.strohalm.cyclos.entities.accounts.loans.Loan;
import nl.strohalm.cyclos.entities.accounts.loans.LoanParameters;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType.Context;
import nl.strohalm.cyclos.entities.ads.AdCategory;
import nl.strohalm.cyclos.entities.customization.fields.CustomField;
import nl.strohalm.cyclos.entities.customization.fields.CustomFieldPossibleValue;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField.Indexing;
import nl.strohalm.cyclos.entities.customization.fields.MemberRecordCustomField;
import nl.strohalm.cyclos.entities.customization.fields.PaymentCustomField;
import nl.strohalm.cyclos.entities.customization.fields.Validation;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Reference.Level;
import nl.strohalm.cyclos.entities.members.messages.MessageCategory;
import nl.strohalm.cyclos.entities.members.records.MemberRecordType;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.TimePeriod;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Creates a set of default account related data, as well as other default data. These are the generated account types:
 * <ol>
 * <li>Debit account</li>
 * <li>Community account</li>
 * <li>Voucher account</li>
 * <li>Organization account</li>
 * <li>Member account</li>
 * </ol>
 * These are the generated transfer types: <li>Debit to community</li> <li>Voucher creation</li> <li>Debit to organization</li> <li>Community to debit
 * </li> <li>Community to voucher</li> <li>Community to organization</li> <li>Voucher destruction</li> <li>Voucher to community</li> <li>Voucher to
 * organization</li> <li>Organization to debit</li> <li>Organization to community</li> <li>Organization to voucher</li> <li>Trade transfer</li> <li>
 * Debit to member</li> <li>Community to member</li> <li>Voucher cashing</li> <li>Organization to member</li> <li>Member to community</li> <li>Voucher
 * buying</li> <li>Member to organization</li> <li>Loan repayment</li> <li>Loan</li> <li>Initial credit</li> <li>Money conversion</li> <li>Transaction
 * tax payment</li> <li>Broker commission payment</li> <li>Contribution payment</li> <li>Liquidity tax payment</li> <li>Trade transfer from mobile</li>
 * <li>External trade transfer</li> </ol>
 * @author luis
 */
public class CreateInitialData implements Runnable {

    private final ResourceBundle           bundle;
    private Currency                       units;
    private SystemAccountType              community;
    private SystemAccountType              debit;
    private MemberAccountType              member;
    private SystemAccountType              organization;
    private final Session                  session;
    private final Map<String, AccountType> systemTypes;
    private SystemAccountType              voucher;
    private TransferType                   trade;
    private TransferType                   mobileTrade;
    private TransferType                   externalTrade;
    private TransferType                   liquidityTaxPayment;
    private TransferType                   contributionPayment;
    private TransferType                   transactionTaxPayment;
    private TransferType                   brokerCommissionPayment;
    private TransferType                   initialCredit;
    private TransferType                   loan;
    private TransferType                   loanRepayment;
    private TransferType                   moneyConversion;
    private Collection<TransferType>       systemToSystem;
    private Collection<TransferType>       systemToMember;
    private Collection<TransferType>       memberToSystem;
    private Collection<TransferType>       memberVoucher;
    private Collection<TransferType>       memberDebit;
    private Collection<TransferType>       memberCommunity;
    private Collection<TransferType>       memberOrganization;
    private Collection<TransferType>       debitCommunity;
    private Collection<TransferType>       debitOrganization;
    private Collection<TransferType>       debitVoucher;
    private Collection<TransferType>       organizationVaucher;
    private Collection<TransferType>       organizationCommunity;
    private Collection<TransferType>       communityVaucher;
    private AdminGroup                     systemAdmins;
    private AdminGroup                     accountAdmins;
    private MemberGroup                    fullMembers;
    private MemberGroup                    inactiveMembers;
    private MemberGroup                    demoMembers;
    private BrokerGroup                    fullBrokers;
    private Collection<AdminGroup>         enabledAdminGroups;
    private Collection<MemberGroup>        enabledMemberGroups;

    public CreateInitialData(final Setup setup) {
        session = setup.getSession();
        bundle = setup.getBundle();
        systemTypes = new LinkedHashMap<String, AccountType>();
    }

    /**
     * CreateInitialData Create an initial database
     */
    @Override
    public void run() {
        Setup.out.println(bundle.getString("initial-data.start"));
        session.clear();

        systemAdmins = (AdminGroup) session.createCriteria(AdminGroup.class).add(Restrictions.eq("name", bundle.getString("group.system-admins.name"))).uniqueResult();
        accountAdmins = (AdminGroup) session.createCriteria(AdminGroup.class).add(Restrictions.eq("name", bundle.getString("group.account-admins.name"))).uniqueResult();
        fullMembers = (MemberGroup) session.createCriteria(MemberGroup.class).add(Restrictions.eq("name", bundle.getString("group.full-members.name"))).uniqueResult();
        inactiveMembers = (MemberGroup) session.createCriteria(MemberGroup.class).add(Restrictions.eq("name", bundle.getString("group.inactive-members.name"))).uniqueResult();
        fullBrokers = (BrokerGroup) session.createCriteria(BrokerGroup.class).add(Restrictions.eq("name", bundle.getString("group.full-brokers.name"))).uniqueResult();

        enabledAdminGroups = Arrays.asList(systemAdmins, accountAdmins);
        enabledMemberGroups = Arrays.asList(fullMembers, fullBrokers);

        createCurrencies();
        createSystemAccountTypes();
        createMemberAccountTypes();
        createSystemAccounts();
        createTransferTypes();
        createFees();
        createAccountFees();
        createPaymentFilters();
        createMemberAccountSettings();
        createExampleAdCategory();
        createExampleMessageCategory();
        createCustomFields();
        createRemarks();

        session.flush();

        Setup.out.println(bundle.getString("initial-data.end"));
    }

    private void addPossibleValues(final CustomField field, final String... keys) {
        for (final String key : keys) {
            final CustomFieldPossibleValue pv = new CustomFieldPossibleValue();
            pv.setField(field);
            pv.setValue(bundle.getString(key));
            session.save(pv);
        }
    }

    private void associateTransferTypeToGroups(final TransferType transferType, final Collection<MemberGroup> memberGroups) {
        for (final MemberGroup memberGroup : memberGroups) {
            memberGroup.getTransferTypes().add(transferType);
        }
    }

    private void createAccountFees() {

        final AccountFee contribution = new AccountFee();
        contribution.setName(bundle.getString("tax.contribution.name"));
        contribution.setDescription(bundle.getString("tax.contribution.description"));
        contribution.setAccountType(member);
        contribution.setChargeMode(AccountFee.ChargeMode.FIXED);
        contribution.setPaymentDirection(PaymentDirection.TO_SYSTEM);
        contribution.setAmount(new BigDecimal(5));
        contribution.setRunMode(AccountFee.RunMode.SCHEDULED);
        contribution.setRecurrence(new TimePeriod(1, TimePeriod.Field.MONTHS));
        contribution.setEnabled(false);
        contribution.setDay((byte) 1);
        contribution.setHour((byte) 3);
        contribution.setInvoiceMode(InvoiceMode.NOT_ENOUGH_CREDITS);
        contribution.setTransferType(contributionPayment);
        contribution.setGroups(new HashSet<MemberGroup>(enabledMemberGroups));
        session.save(contribution);

        final AccountFee liquidity = new AccountFee();
        liquidity.setName(bundle.getString("tax.liquidity.name"));
        liquidity.setDescription(bundle.getString("tax.liquidity.description"));
        liquidity.setAccountType(member);
        liquidity.setChargeMode(AccountFee.ChargeMode.VOLUME_PERCENTAGE);
        liquidity.setPaymentDirection(PaymentDirection.TO_SYSTEM);
        liquidity.setAmount(new BigDecimal(1));
        liquidity.setRunMode(AccountFee.RunMode.SCHEDULED);
        liquidity.setRecurrence(new TimePeriod(1, TimePeriod.Field.MONTHS));
        liquidity.setEnabled(false);
        liquidity.setDay((byte) 1);
        liquidity.setHour((byte) 1);
        liquidity.setInvoiceMode(InvoiceMode.NEVER);
        liquidity.setTransferType(liquidityTaxPayment);
        liquidity.setGroups(new HashSet<MemberGroup>(enabledMemberGroups));
        session.save(liquidity);
    }

    private void createCurrencies() {
        units = new Currency();
        units.setName("Units");
        units.setPattern("#amount# units");
        units.setSymbol("units");
        session.save(units);
    }

    private void createCustomFields() {
        final Collection<MemberGroup> memberGroups = Arrays.asList(fullMembers, demoMembers, inactiveMembers, fullBrokers);

        // Birthday
        final MemberCustomField birthday = new MemberCustomField();
        birthday.setName(bundle.getString("field.birthday"));
        birthday.setInternalName("birthday");
        birthday.setControl(CustomField.Control.TEXT);
        birthday.setSize(CustomField.Size.SMALL);
        birthday.setType(CustomField.Type.DATE);
        birthday.setMemberCanHide(true);
        birthday.setOrder(0);
        birthday.setIndexing(Indexing.NONE);
        birthday.setShowInPrint(true);
        birthday.setGroups(new ArrayList<MemberGroup>(memberGroups));
        session.save(birthday);

        // Gender
        final MemberCustomField gender = new MemberCustomField();
        gender.setName(bundle.getString("field.gender"));
        gender.setInternalName("gender");
        gender.setControl(CustomField.Control.RADIO);
        gender.setSize(CustomField.Size.LARGE);
        gender.setType(CustomField.Type.ENUMERATED);
        gender.setMemberCanHide(true);
        gender.setOrder(1);
        gender.setShowInPrint(true);
        gender.setGroups(new ArrayList<MemberGroup>(memberGroups));
        gender.setIndexing(Indexing.NONE);
        session.save(gender);
        addPossibleValues(gender, "field.gender.male", "field.gender.female");

        // Address
        final MemberCustomField address = new MemberCustomField();
        address.setName(bundle.getString("field.address"));
        address.setInternalName("address");
        address.setControl(CustomField.Control.TEXT);
        address.setSize(CustomField.Size.LARGE);
        address.setType(CustomField.Type.STRING);
        address.setMemberCanHide(true);
        address.setOrder(2);
        address.setIndexing(Indexing.MEMBERS_AND_ADS);
        address.setGroups(new ArrayList<MemberGroup>(memberGroups));
        session.save(address);

        // Postal code
        final MemberCustomField postalCode = new MemberCustomField();
        postalCode.setName(bundle.getString("field.postalCode"));
        postalCode.setInternalName("postalCode");
        postalCode.setControl(CustomField.Control.TEXT);
        postalCode.setSize(CustomField.Size.LARGE);
        postalCode.setType(CustomField.Type.STRING);
        postalCode.setMemberCanHide(true);
        postalCode.setOrder(3);
        postalCode.setIndexing(Indexing.NONE);
        postalCode.setGroups(new ArrayList<MemberGroup>(memberGroups));
        session.save(postalCode);

        // City
        final MemberCustomField city = new MemberCustomField();
        city.setName(bundle.getString("field.city"));
        city.setInternalName("city");
        city.setControl(CustomField.Control.TEXT);
        city.setSize(CustomField.Size.LARGE);
        city.setType(CustomField.Type.STRING);
        city.setMemberCanHide(true);
        city.setOrder(4);
        city.setIndexing(Indexing.MEMBERS_AND_ADS);
        city.setGroups(new ArrayList<MemberGroup>(memberGroups));
        session.save(city);

        // Area
        final MemberCustomField area = new MemberCustomField();
        area.setName(bundle.getString("field.area"));
        area.setInternalName("area");
        area.setControl(CustomField.Control.SELECT);
        area.setType(CustomField.Type.ENUMERATED);
        area.setMemberCanHide(true);
        area.setOrder(5);
        area.setGroups(new ArrayList<MemberGroup>(memberGroups));
        area.setIndexing(Indexing.MEMBERS_AND_ADS);
        session.save(area);
        addPossibleValues(area, "area.name");

        // Phone
        final MemberCustomField phone = new MemberCustomField();
        phone.setName(bundle.getString("field.phone"));
        phone.setInternalName("phone");
        phone.setControl(CustomField.Control.TEXT);
        phone.setSize(CustomField.Size.LARGE);
        phone.setType(CustomField.Type.STRING);
        phone.setMemberCanHide(true);
        phone.setOrder(6);
        phone.setIndexing(Indexing.NONE);
        phone.setGroups(new ArrayList<MemberGroup>(memberGroups));
        session.save(phone);

        // Mobile phone
        final MemberCustomField mobilePhone = new MemberCustomField();
        mobilePhone.setName(bundle.getString("field.mobilePhone"));
        mobilePhone.setInternalName("mobilePhone");
        mobilePhone.setControl(CustomField.Control.TEXT);
        mobilePhone.setSize(CustomField.Size.LARGE);
        mobilePhone.setType(CustomField.Type.STRING);
        mobilePhone.setMemberCanHide(true);
        mobilePhone.setOrder(7);
        mobilePhone.setIndexing(Indexing.NONE);
        mobilePhone.setGroups(new ArrayList<MemberGroup>(memberGroups));
        mobilePhone.setValidation(new Validation(false, true));
        session.save(mobilePhone);

        // Fax
        final MemberCustomField fax = new MemberCustomField();
        fax.setName(bundle.getString("field.fax"));
        fax.setInternalName("fax");
        fax.setControl(CustomField.Control.TEXT);
        fax.setSize(CustomField.Size.LARGE);
        fax.setType(CustomField.Type.STRING);
        fax.setMemberCanHide(true);
        fax.setOrder(8);
        fax.setIndexing(Indexing.NONE);
        fax.setGroups(new ArrayList<MemberGroup>(memberGroups));
        session.save(fax);

        // URL
        final MemberCustomField url = new MemberCustomField();
        url.setName(bundle.getString("field.url"));
        url.setInternalName("url");
        url.setControl(CustomField.Control.TEXT);
        url.setSize(CustomField.Size.LARGE);
        url.setType(CustomField.Type.URL);
        url.setMemberCanHide(true);
        url.setOrder(9);
        url.setIndexing(Indexing.NONE);
        url.setGroups(new ArrayList<MemberGroup>(memberGroups));
        session.save(url);

        // Loan identifier
        final PaymentCustomField loanIdentifier = new PaymentCustomField();
        loanIdentifier.setTransferType(loan);
        loanIdentifier.setName(bundle.getString("field.loanIdentifier"));
        loanIdentifier.setInternalName("identifier");
        loanIdentifier.setControl(CustomField.Control.TEXT);
        loanIdentifier.setSize(CustomField.Size.SMALL);
        loanIdentifier.setType(CustomField.Type.STRING);
        loanIdentifier.setSearchAccess(PaymentCustomField.Access.FROM_ACCOUNT);
        loanIdentifier.setOrder(0);
        session.save(loanIdentifier);
    }

    /**
     * Create some example category
     */
    private void createExampleAdCategory() {
        final AdCategory category = new AdCategory();
        category.setActive(true);
        category.setName(bundle.getString("ad-category.name"));
        session.save(category);
    }

    /**
     * Create some message categories
     */
    private void createExampleMessageCategory() {
        final List<MessageCategory> allCategories = new ArrayList<MessageCategory>();

        final MessageCategory support = new MessageCategory();
        support.setName(bundle.getString("message-category.support.name"));
        session.save(support);
        allCategories.add(support);

        final MessageCategory administration = new MessageCategory();
        administration.setName(bundle.getString("message-category.administration.name"));
        session.save(administration);
        allCategories.add(administration);

        final MessageCategory loanRequest = new MessageCategory();
        loanRequest.setName(bundle.getString("message-category.loan-request.name"));
        session.save(loanRequest);
        allCategories.add(loanRequest);

        systemAdmins.setMessageCategories(new ArrayList<MessageCategory>(allCategories));
        accountAdmins.setMessageCategories(new ArrayList<MessageCategory>(allCategories));
        fullMembers.setMessageCategories(new ArrayList<MessageCategory>(allCategories));
        fullBrokers.setMessageCategories(new ArrayList<MessageCategory>(allCategories));
    }

    private void createFees() {
        final SimpleTransactionFee transactionTax = new SimpleTransactionFee();
        transactionTax.setName(bundle.getString("fee.transaction-tax.name"));
        transactionTax.setDescription(bundle.getString("fee.transaction-tax.description"));
        transactionTax.setOriginalTransferType(trade);
        transactionTax.setGeneratedTransferType(transactionTaxPayment);
        transactionTax.setAmount(Amount.fixed(new BigDecimal(5)));
        transactionTax.setEnabled(false);
        transactionTax.setPayer(Subject.SOURCE);
        transactionTax.setReceiver(Subject.SYSTEM);
        session.save(transactionTax);

        final BrokerCommission brokerCommission = new BrokerCommission();
        brokerCommission.setName(bundle.getString("fee.broker-commission.name"));
        brokerCommission.setDescription(bundle.getString("fee.broker-commission.description"));
        brokerCommission.setOriginalTransferType(trade);
        brokerCommission.setGeneratedTransferType(brokerCommissionPayment);
        brokerCommission.setAmount(Amount.percentage(new BigDecimal(2.5F)));
        brokerCommission.setEnabled(false);
        brokerCommission.setPayer(Subject.SYSTEM);
        brokerCommission.setWhichBroker(WhichBroker.SOURCE);
        brokerCommission.setWhen(When.COUNT);
        brokerCommission.setCount(20);
        session.save(brokerCommission);
    }

    private void createMemberAccountSettings() {
        MemberGroupAccountSettings mgas = new MemberGroupAccountSettings();
        for (final MemberGroup group : enabledMemberGroups) {
            mgas = new MemberGroupAccountSettings();
            mgas.setAccountType(member);
            mgas.setDefault(true);
            mgas.setDefaultCreditLimit(BigDecimal.ZERO);
            mgas.setGroup(group);
            mgas.setInitialCreditTransferType(initialCredit);
            mgas.setInitialCredit(BigDecimal.ZERO);
            mgas.setLowUnits(new BigDecimal(20));
            mgas.setLowUnitsMessage(bundle.getString("account.member.low-units"));
            session.save(mgas);
        }
    }

    private void createMemberAccountTypes() {
        member = new MemberAccountType();
        member.setCurrency(units);
        member.setName(bundle.getString("account.member.name"));
        member.setDescription(bundle.getString("account.member.description"));
        session.save(member);
    }

    @SuppressWarnings("unchecked")
    private void createPaymentFilter(final AccountType accountType, final String key, final Object... arguments) {
        final PaymentFilter filter = new PaymentFilter();
        filter.setAccountType(accountType);
        final String name = bundle.getString("payment-filter." + key);
        filter.setName(name);
        filter.setDescription(name);
        filter.setShowInAccountHistory(true);
        filter.setShowInReports(true);
        final Collection<TransferType> transferTypes = new HashSet<TransferType>();
        for (Object object : arguments) {
            if (object instanceof TransferType) {
                transferTypes.add((TransferType) object);
            } else if (object instanceof Collection) {
                transferTypes.addAll((Collection<TransferType>) object);
            } else {
                throw new IllegalArgumentException("Invalid argument passed to createPaymentFilter: " + object);
            }
        }
        filter.setTransferTypes(transferTypes);
        final Collection<Group> groups = new HashSet<Group>();
        if (accountType instanceof SystemAccountType) {
            groups.addAll(enabledAdminGroups);
        } else {
            groups.addAll(enabledMemberGroups);
        }
        filter.setGroups(groups);
        session.save(filter);
    }

    private void createPaymentFilters() {
        createPaymentFilter(member, "member-payments", trade, mobileTrade, externalTrade);
        createPaymentFilter(member, "member-loans", loan, loanRepayment);
        createPaymentFilter(member, "member-fees", liquidityTaxPayment, contributionPayment, transactionTaxPayment);
        createPaymentFilter(member, "member-commission", brokerCommissionPayment);
        createPaymentFilter(member, "member-voucher", memberVoucher);
        createPaymentFilter(member, "member-system", memberToSystem);

        createPaymentFilter(debit, "debit-community", debitCommunity);
        createPaymentFilter(debit, "debit-voucher", debitVoucher);
        createPaymentFilter(debit, "debit-organization", debitOrganization);
        createPaymentFilter(debit, "debit-member", memberDebit);

        createPaymentFilter(community, "community-voucher", communityVaucher);
        createPaymentFilter(community, "community-debit", debitCommunity);
        createPaymentFilter(community, "community-organization", organizationCommunity);
        createPaymentFilter(community, "community-contribution", contributionPayment);
        createPaymentFilter(community, "community-liquidity", liquidityTaxPayment);
        createPaymentFilter(community, "community-broker", brokerCommissionPayment);
        createPaymentFilter(community, "community-loan", loan, loanRepayment);
        createPaymentFilter(community, "community-member", memberCommunity);

        createPaymentFilter(voucher, "voucher-debit", debitVoucher);
        createPaymentFilter(voucher, "voucher-community", communityVaucher);
        createPaymentFilter(voucher, "voucher-organization", organizationVaucher);
        createPaymentFilter(voucher, "voucher-member", memberVoucher);

        createPaymentFilter(organization, "organization-debit", debitOrganization);
        createPaymentFilter(organization, "organization-community", organizationCommunity);
        createPaymentFilter(organization, "organization-voucher", organizationVaucher);
        createPaymentFilter(organization, "organization-member", memberOrganization);
    }

    @SuppressWarnings("unchecked")
    private MemberRecordType createRemarks() {
        final Collection<Group> allGroups = session.createCriteria(Group.class).list();

        // Create member record type "remarks"
        final MemberRecordType memberRecordType = new MemberRecordType();
        memberRecordType.setName(bundle.getString("remarks.name"));
        memberRecordType.setLabel(bundle.getString("remarks.label"));
        memberRecordType.setDescription(bundle.getString("remarks.description"));
        memberRecordType.setGroups(allGroups);
        memberRecordType.setViewableByAdminGroups(enabledAdminGroups);
        memberRecordType.setViewableByBrokerGroups(Collections.singletonList(fullBrokers));
        memberRecordType.setLayout(MemberRecordType.Layout.FLAT);
        memberRecordType.setShowMenuItem(true);
        session.save(memberRecordType);

        // Add permissions to administrators groups (system and account) and full brokers group
        systemAdmins.getViewAdminRecordTypes().add(memberRecordType);
        systemAdmins.getCreateAdminRecordTypes().add(memberRecordType);

        systemAdmins.getViewMemberRecordTypes().add(memberRecordType);
        systemAdmins.getCreateMemberRecordTypes().add(memberRecordType);

        accountAdmins.getViewMemberRecordTypes().add(memberRecordType);
        accountAdmins.getCreateMemberRecordTypes().add(memberRecordType);

        fullBrokers.getBrokerMemberRecordTypes().add(memberRecordType);
        fullBrokers.getBrokerCreateMemberRecordTypes().add(memberRecordType);

        // Create custom field "comments" for the type "remarks"
        final MemberRecordCustomField commentsField = new MemberRecordCustomField();
        commentsField.setName(bundle.getString("remarks.comments"));
        commentsField.setInternalName(bundle.getString("remarks.comments.internalName"));
        commentsField.setDescription(bundle.getString("remarks.comments.description"));
        commentsField.setType(CustomField.Type.STRING);
        commentsField.setControl(CustomField.Control.TEXTAREA);
        commentsField.setSize(CustomField.Size.FULL);
        commentsField.setShowInList(true);
        commentsField.setValidation(new Validation(true));
        commentsField.setOrder(0);
        commentsField.setMemberRecordType(memberRecordType);
        commentsField.setBrokerAccess(MemberRecordCustomField.Access.EDITABLE);
        session.save(commentsField);

        return memberRecordType;
    }

    private SystemAccount createSystemAccount(final SystemAccountType type) {
        // Save the account
        final SystemAccount account = new SystemAccount();
        account.setCreditLimit(type.getCreditLimit());
        account.setCreationDate(Calendar.getInstance());
        account.setType(type);
        account.setOwnerName(type.getName());
        session.save(account);

        // Save the account lock
        session.save(new AccountLock(account));

        type.setAccount(account);
        return account;
    }

    private void createSystemAccounts() {
        createSystemAccount(debit);
        createSystemAccount(community);
        createSystemAccount(voucher);
        createSystemAccount(organization);
    }

    @SuppressWarnings("unchecked")
    private void createSystemAccountTypes() {
        debit = new SystemAccountType();
        debit.setCreditLimit(null);
        debit.setName(bundle.getString("account.debit.name"));
        debit.setDescription(bundle.getString("account.debit.description"));
        debit.setCurrency(units);

        session.save(debit);
        systemTypes.put("debit", debit);

        community = new SystemAccountType();
        community.setCurrency(units);
        community.setName(bundle.getString("account.community.name"));
        community.setDescription(bundle.getString("account.community.description"));
        community.setCreditLimit(BigDecimal.ZERO);
        session.save(community);
        systemTypes.put("community", community);

        voucher = new SystemAccountType();
        voucher.setCurrency(units);
        voucher.setName(bundle.getString("account.voucher.name"));
        voucher.setDescription(bundle.getString("account.voucher.description"));
        voucher.setCreditLimit(BigDecimal.ZERO);
        session.save(voucher);
        systemTypes.put("voucher", voucher);

        organization = new SystemAccountType();
        organization.setCurrency(units);
        organization.setName(bundle.getString("account.organization.name"));
        organization.setDescription(bundle.getString("account.organization.description"));
        organization.setCreditLimit(BigDecimal.ZERO);
        session.save(organization);
        systemTypes.put("organization", organization);

        // Allow the adminstrator group to view all system account types
        final List<SystemAccountType> allSystemAccountTypes = session.createCriteria(SystemAccountType.class).list();
        systemAdmins.setViewInformationOf(new ArrayList<SystemAccountType>(allSystemAccountTypes));
        accountAdmins.setViewInformationOf(new ArrayList<SystemAccountType>(allSystemAccountTypes));
    }

    private void createTransferTypes() {

        final Channel web = (Channel) session.createCriteria(Channel.class).add(Restrictions.eq("internalName", Channel.WEB)).uniqueResult();

        memberDebit = new ArrayList<TransferType>();
        memberCommunity = new ArrayList<TransferType>();
        memberOrganization = new ArrayList<TransferType>();
        memberVoucher = new ArrayList<TransferType>();
        debitCommunity = new ArrayList<TransferType>();
        debitOrganization = new ArrayList<TransferType>();
        debitVoucher = new ArrayList<TransferType>();
        organizationVaucher = new ArrayList<TransferType>();
        organizationCommunity = new ArrayList<TransferType>();
        communityVaucher = new ArrayList<TransferType>();

        // Insert all combinations of from-to between system accounts
        systemToSystem = new ArrayList<TransferType>();
        for (final Map.Entry<String, AccountType> fromEntry : systemTypes.entrySet()) {
            final String fromName = fromEntry.getKey();
            final AccountType from = fromEntry.getValue();
            for (final Map.Entry<String, AccountType> toEntry : systemTypes.entrySet()) {
                final String toName = toEntry.getKey();
                final AccountType to = toEntry.getValue();
                if (from.equals(to)) {
                    // Ignore the same from and to
                    continue;
                }

                final TransferType tt = new TransferType();
                tt.setName(bundle.getString("transfer-type." + fromName + "-" + toName + ".name"));
                tt.setDescription(bundle.getString("transfer-type." + fromName + "-" + toName + ".description"));
                tt.getContext().setSelfPayment(true);
                tt.setFrom(from);
                tt.setTo(to);
                session.save(tt);
                systemAdmins.getTransferTypes().add(tt);
                systemToSystem.add(tt);
                if ((from == debit && to == community) || (from == community && to == debit)) {
                    debitCommunity.add(tt);
                } else if ((from == debit && to == voucher) || (from == voucher && to == debit)) {
                    debitVoucher.add(tt);
                } else if ((from == debit && to == organization) || (from == organization && to == debit)) {
                    debitOrganization.add(tt);
                } else if ((from == organization && to == voucher) || (from == voucher && to == organization)) {
                    organizationVaucher.add(tt);
                } else if ((from == organization && to == community) || (from == community && to == organization)) {
                    organizationCommunity.add(tt);
                } else if ((from == community && to == voucher) || (from == voucher && to == community)) {
                    communityVaucher.add(tt);
                }
            }
        }

        // Insert the trade transfer
        trade = new TransferType();
        trade.setName(bundle.getString("transfer-type.trade.name"));
        trade.setDescription(bundle.getString("transfer-type.trade.description"));
        trade.getContext().setPayment(true);
        trade.setFrom(member);
        trade.setTo(member);
        trade.setMaxAmountPerDay(new BigDecimal(1000));
        trade.setChannels(Collections.singletonList(web));
        trade.setAllowsScheduledPayments(true);
        setFeedbackParameters(trade);
        session.save(trade);
        associateTransferTypeToGroups(trade, enabledMemberGroups);

        // Insert all combinations of from:system / to:member
        systemToMember = new ArrayList<TransferType>();
        for (final Map.Entry<String, AccountType> entry : systemTypes.entrySet()) {
            final String name = entry.getKey();
            final AccountType from = entry.getValue();

            final TransferType tt = new TransferType();
            tt.setName(bundle.getString("transfer-type." + name + "-member.name"));
            tt.setDescription(bundle.getString("transfer-type." + name + "-member.description"));
            tt.getContext().setPayment(true);
            tt.setFrom(from);
            tt.setTo(member);
            tt.setChannels(Collections.singletonList(web));
            session.save(tt);
            systemAdmins.getTransferTypes().add(tt);
            systemToMember.add(tt);
            if (from == debit) {
                memberDebit.add(tt);
            } else if (from == community) {
                memberCommunity.add(tt);
            } else if (from == organization) {
                memberOrganization.add(tt);
            } else if (from == voucher) {
                memberVoucher.add(tt);
            }
        }

        // Insert types from member to voucher, community and organization (no direct payment to debit)
        final Map<String, AccountType> toSystem = new LinkedHashMap<String, AccountType>(systemTypes);
        toSystem.remove("debit");
        memberToSystem = new ArrayList<TransferType>();
        for (final Map.Entry<String, AccountType> entry : toSystem.entrySet()) {
            final String name = entry.getKey();
            final AccountType to = entry.getValue();

            final TransferType tt = new TransferType();
            tt.setName(bundle.getString("transfer-type.member-" + name + ".name"));
            tt.setDescription(bundle.getString("transfer-type.member-" + name + ".description"));
            tt.getContext().setPayment(true);
            tt.setFrom(member);
            tt.setTo(to);
            tt.setChannels(Collections.singletonList(web));
            session.save(tt);
            associateTransferTypeToGroups(tt, enabledMemberGroups);
            memberToSystem.add(tt);
            if (to == community) {
                memberCommunity.add(tt);
            } else if (to == organization) {
                memberOrganization.add(tt);
            } else if (to == voucher) {
                memberVoucher.add(tt);
            }
        }

        // Insert the loan repayment and grant
        loanRepayment = new TransferType();
        loanRepayment.setName(bundle.getString("transfer-type.loan-repayment.name"));
        loanRepayment.setDescription(bundle.getString("transfer-type.loan-repayment.description"));
        loanRepayment.setFrom(member);
        loanRepayment.setTo(debit);
        session.save(loanRepayment);

        loan = new TransferType();
        loan.setName(bundle.getString("transfer-type.loan.name"));
        loan.setDescription(bundle.getString("transfer-type.loan.description"));
        loan.setFrom(debit);
        loan.setTo(member);
        loan.setContext(Context.payment());
        final LoanParameters loanParameters = new LoanParameters();
        loan.setLoan(loanParameters);
        loanParameters.setType(Loan.Type.SINGLE_PAYMENT);
        loanParameters.setRepaymentType(loanRepayment);
        loanParameters.setRepaymentDays(30);
        session.save(loan);
        systemAdmins.getTransferTypes().add(loan);
        memberDebit.add(loan);

        // Add the initial credit
        initialCredit = new TransferType();
        initialCredit.setName(bundle.getString("transfer-type.initial-credit.name"));
        initialCredit.setDescription(bundle.getString("transfer-type.initial-credit.description"));
        initialCredit.setFrom(debit);
        initialCredit.setTo(member);
        session.save(initialCredit);
        memberDebit.add(initialCredit);

        // Add the money conversion
        moneyConversion = new TransferType();
        moneyConversion.setName(bundle.getString("transfer-type.money-conversion.name"));
        moneyConversion.setDescription(bundle.getString("transfer-type.money-conversion.description"));
        moneyConversion.setFrom(debit);
        moneyConversion.setTo(member);
        moneyConversion.getContext().setPayment(true);
        session.save(moneyConversion);
        systemAdmins.getTransferTypes().add(moneyConversion);

        // Add the transaction tax payment
        transactionTaxPayment = new TransferType();
        transactionTaxPayment.setName(bundle.getString("transfer-type.transaction-tax-payment.name"));
        transactionTaxPayment.setDescription(bundle.getString("transfer-type.transaction-tax-payment.description"));
        transactionTaxPayment.setFrom(member);
        transactionTaxPayment.setTo(community);
        session.save(transactionTaxPayment);

        // Add the broker commission payment
        brokerCommissionPayment = new TransferType();
        brokerCommissionPayment.setName(bundle.getString("transfer-type.broker-commission-payment.name"));
        brokerCommissionPayment.setDescription(bundle.getString("transfer-type.broker-commission-payment.description"));
        brokerCommissionPayment.setFrom(community);
        brokerCommissionPayment.setTo(member);
        session.save(brokerCommissionPayment);

        // Insert the contribution payment
        contributionPayment = new TransferType();
        contributionPayment.setName(bundle.getString("transfer-type.contribution-payment.name"));
        contributionPayment.setDescription(bundle.getString("transfer-type.contribution-payment.description"));
        contributionPayment.setFrom(member);
        contributionPayment.setTo(community);
        session.save(contributionPayment);

        // Insert the liquidity tax payment
        liquidityTaxPayment = new TransferType();
        liquidityTaxPayment.setName(bundle.getString("transfer-type.liquidity-tax-payment.name"));
        liquidityTaxPayment.setDescription(bundle.getString("transfer-type.liquidity-tax-payment.description"));
        liquidityTaxPayment.setFrom(member);
        liquidityTaxPayment.setTo(community);
        session.save(liquidityTaxPayment);

        // Get mobile channels
        final Channel restChannel = (Channel) session.createCriteria(Channel.class).add(Restrictions.eq("internalName", Channel.REST)).uniqueResult();
        final Channel wap2Channel = (Channel) session.createCriteria(Channel.class).add(Restrictions.eq("internalName", Channel.WAP2)).uniqueResult();
        final Channel wap1Channel = (Channel) session.createCriteria(Channel.class).add(Restrictions.eq("internalName", Channel.WAP1)).uniqueResult();
        final List<Channel> mobileChannels = new ArrayList<Channel>();
        mobileChannels.add(restChannel);
        mobileChannels.add(wap2Channel);
        mobileChannels.add(wap1Channel);

        // Add the mobile trade transfer
        mobileTrade = new TransferType();
        mobileTrade.setName(bundle.getString("transfer-type.mobile-trade.name"));
        mobileTrade.setDescription(bundle.getString("transfer-type.mobile-trade.description"));
        mobileTrade.setFrom(member);
        mobileTrade.setTo(member);
        mobileTrade.getContext().setPayment(true);
        mobileTrade.setChannels(mobileChannels);
        mobileTrade.setMaxAmountPerDay(new BigDecimal(500));
        mobileTrade.setGroups(new HashSet<Group>(enabledMemberGroups));
        setFeedbackParameters(mobileTrade);
        session.save(mobileTrade);
        associateTransferTypeToGroups(mobileTrade, enabledMemberGroups);

        // Add the external trade transfer
        final Channel poswebChannel = (Channel) session.createCriteria(Channel.class).add(Restrictions.eq("internalName", Channel.POSWEB)).uniqueResult();
        final Channel webshopChannel = (Channel) session.createCriteria(Channel.class).add(Restrictions.eq("internalName", Channel.WEBSHOP)).uniqueResult();
        final List<Channel> externalTradeTransferChannels = Arrays.asList(webshopChannel, poswebChannel);
        externalTrade = new TransferType();
        externalTrade.setName(bundle.getString("transfer-type.external-trade.name"));
        externalTrade.setDescription(bundle.getString("transfer-type.external-trade.description"));
        externalTrade.setFrom(member);
        externalTrade.setTo(member);
        externalTrade.setChannels(externalTradeTransferChannels);
        externalTrade.getContext().setPayment(true);
        externalTrade.setMaxAmountPerDay(new BigDecimal(500));
        externalTrade.setGroups(new HashSet<Group>(enabledMemberGroups));
        session.save(externalTrade);
        associateTransferTypeToGroups(externalTrade, enabledMemberGroups);
    }

    private void setFeedbackParameters(final TransferType transferType) {
        transferType.setRequiresFeedback(true);
        transferType.setDefaultFeedbackComments(bundle.getString("transfer-type.feedback.default-comments"));
        transferType.setDefaultFeedbackLevel(Level.NEUTRAL);
        transferType.setFeedbackEnabledSince(Calendar.getInstance());
        transferType.setFeedbackExpirationTime(new TimePeriod(2, TimePeriod.Field.WEEKS));
        transferType.setFeedbackReplyExpirationTime(new TimePeriod(2, TimePeriod.Field.WEEKS));
    }

}
