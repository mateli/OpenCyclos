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
package nl.strohalm.cyclos.controls.accounts.transactionfees;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission.WhichBroker;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.SimpleTransactionFee.ARateRelation;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.ChargeType;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Nature;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFee.Subject;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.GroupQuery;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeService;
import nl.strohalm.cyclos.utils.ActionHelper;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.binding.SimpleCollectionBinder;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to edit a transaction fee
 * @author luis
 */
public class EditTransactionFeeAction extends BaseFormAction implements LocalSettingsChangeListener {

    private TransactionFeeService                             transactionFeeService;
    private TransferTypeService                               transferTypeService;
    private Map<Nature, DataBinder<? extends TransactionFee>> dataBinders;
    private ReadWriteLock                                     lock = new ReentrantReadWriteLock(true);

    public DataBinder<? extends TransactionFee> getDataBinder(final Nature nature) {
        try {
            lock.readLock().lock();
            if (dataBinders == null) {
                final LocalSettings localSettings = settingsService.getLocalSettings();
                final EnumMap<Nature, DataBinder<? extends TransactionFee>> temp = new EnumMap<Nature, DataBinder<? extends TransactionFee>>(Nature.class);
                final BeanBinder<SimpleTransactionFee> simpleBinder = BeanBinder.instance(SimpleTransactionFee.class);
                initBasic(simpleBinder, localSettings);
                simpleBinder.registerBinder("receiver", PropertyBinder.instance(Subject.class, "receiver"));
                simpleBinder.registerBinder("toFixedMember", PropertyBinder.instance(Member.class, "toFixedMember"));
                simpleBinder.registerBinder("h", PropertyBinder.instance(BigDecimal.class, "h", localSettings.getHighPrecisionConverter()));
                simpleBinder.registerBinder("aFIsZero", PropertyBinder.instance(BigDecimal.class, "aFIsZero", localSettings.getHighPrecisionConverter()));
                simpleBinder.registerBinder("f1", PropertyBinder.instance(BigDecimal.class, "f1", localSettings.getHighPrecisionConverter()));
                simpleBinder.registerBinder("fInfinite", PropertyBinder.instance(BigDecimal.class, "fInfinite", localSettings.getHighPrecisionConverter()));
                simpleBinder.registerBinder("fMinimal", PropertyBinder.instance(BigDecimal.class, "fMinimal", localSettings.getHighPrecisionConverter()));
                simpleBinder.registerBinder("gFIsZero", PropertyBinder.instance(BigDecimal.class, "gFIsZero", localSettings.getHighPrecisionConverter()));
                temp.put(Nature.SIMPLE, simpleBinder);

                final BeanBinder<BrokerCommission> brokerBinder = BeanBinder.instance(BrokerCommission.class);
                initBasic(brokerBinder, localSettings);
                brokerBinder.registerBinder("whichBroker", PropertyBinder.instance(WhichBroker.class, "whichBroker"));
                brokerBinder.registerBinder("maxFixedValue", PropertyBinder.instance(BigDecimal.class, "maxFixedValue", localSettings.getNumberConverter()));
                brokerBinder.registerBinder("maxPercentageValue", PropertyBinder.instance(BigDecimal.class, "maxPercentageValue", localSettings.getNumberConverter()));
                brokerBinder.registerBinder("when", PropertyBinder.instance(BrokerCommission.When.class, "when"));
                brokerBinder.registerBinder("count", PropertyBinder.instance(Integer.class, "count"));
                brokerBinder.registerBinder("allBrokerGroups", PropertyBinder.instance(Boolean.TYPE, "allBrokerGroups"));
                brokerBinder.registerBinder("brokerGroups", SimpleCollectionBinder.instance(MemberGroup.class, "brokerGroups"));
                temp.put(Nature.BROKER, brokerBinder);
                dataBinders = temp;
            }
            return dataBinders.get(nature);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        try {
            lock.writeLock().lock();
            dataBinders = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Inject
    public void setTransactionFeeService(final TransactionFeeService transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    @Inject
    public void setTransferTypeService(final TransferTypeService transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    protected ActionForward handleDisplay(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();
        final EditTransactionFeeForm form = context.getForm();
        final long accountTypeId = form.getAccountTypeId();
        final long transferTypeId = form.getTransferTypeId();
        if (accountTypeId <= 0L || transferTypeId <= 0L) {
            throw new ValidationException();
        }
        boolean editableGeneratedTT = true;
        final TransferType transferType = transferTypeService.load(transferTypeId, RelationshipHelper.nested(TransferType.Relationships.FROM, AccountType.Relationships.CURRENCY), TransferType.Relationships.TO);
        final long id = form.getTransactionFeeId();
        final boolean isInsert = id <= 0L;
        Nature nature;
        ARateRelation arateRelation = ARateRelation.LINEAR;
        if (isInsert) {
            nature = CoercionHelper.coerce(TransactionFee.Nature.class, form.getNature());
            if (nature == null) {
                throw new ValidationException();
            }
            form.setTransactionFee("fromAllGroups", "true");
            form.setTransactionFee("toAllGroups", "true");
            form.setTransactionFee("allBrokerGroups", "true");

            final Collection<WhichBroker> whichBrokers = (nature == Nature.BROKER) ? EnumSet.allOf(WhichBroker.class) : null;
            final Collection<Subject> possibleSubjects = transactionFeeService.getPossibleSubjects(transferType, nature);
            request.setAttribute("possibleSubjects", possibleSubjects);
            request.setAttribute("whichBrokers", whichBrokers);
            if (possibleSubjects != null & possibleSubjects.size() == 1) {
                request.setAttribute("singleSubject", possibleSubjects.iterator().next());
            }

        } else {
            final TransactionFee transactionFee = transactionFeeService.load(id, TransactionFee.Relationships.ORIGINAL_TRANSFER_TYPE, RelationshipHelper.nested(TransactionFee.Relationships.GENERATED_TRANSFER_TYPE, TransferType.Relationships.FROM));
            final TransferType generatedType = transactionFee.getGeneratedTransferType();
            nature = transactionFee.getNature();

            List<TransferType> possibleGeneratedTypes = null;

            if (nature == Nature.BROKER) {
                editableGeneratedTT = generatedType.isFromSystem();
                possibleGeneratedTypes = Collections.singletonList(generatedType);
            } else {
                final SimpleTransactionFee simpleFee = (SimpleTransactionFee) transactionFee;
                possibleGeneratedTypes = transactionFeeService.searchGeneratedTransferTypes(transactionFee, form.isAllowAnyAccount(), generatedType.isFromSystem());

                // When the possible generated types don't contain the current one, enable the "from any account" flag
                if (!possibleGeneratedTypes.contains(generatedType) && !form.isAllowAnyAccount()) {
                    // Search again, setting the flag as true
                    form.setAllowAnyAccount(true);
                    possibleGeneratedTypes = transactionFeeService.searchGeneratedTransferTypes(transactionFee, form.isAllowAnyAccount(), false);
                }

                arateRelation = simpleFee.getfInfinite() == null ? ARateRelation.LINEAR : ARateRelation.ASYMPTOTICAL;
            }

            request.setAttribute("generatedTransferTypes", possibleGeneratedTypes);
            request.setAttribute("transactionFee", transactionFee);
            getDataBinder(nature).writeAsString(form.getTransactionFee(), transactionFee);
        }
        request.setAttribute("transferType", transferType);
        request.setAttribute("isInsert", isInsert);
        request.setAttribute("editableGeneratedTT", editableGeneratedTT);

        final EnumSet<Subject> subjects = EnumSet.allOf(TransactionFee.Subject.class);
        if (nature == Nature.BROKER) {
            subjects.remove(TransactionFee.Subject.SOURCE_BROKER);
            subjects.remove(TransactionFee.Subject.DESTINATION_BROKER);
        }
        request.setAttribute("subjects", subjects);
        RequestHelper.storeEnum(request, TransactionFee.Nature.class, "natures");
        RequestHelper.storeEnum(request, BrokerCommission.When.class, "whens");
        RequestHelper.storeEnum(request, Amount.Type.class, "amountTypes");

        final Collection<ChargeType> chargeTypes = transactionFeeService.getPossibleChargeType(transferType, nature);

        request.setAttribute("allowARate", chargeTypes.contains(ChargeType.A_RATE) && chargeTypes.contains(ChargeType.MIXED_A_D_RATES));
        request.setAttribute("allowDRate", chargeTypes.contains(ChargeType.D_RATE));

        if (nature == TransactionFee.Nature.SIMPLE) {
            // Rates are allowed only in payments from member to unlimited system accounts
            if (transferType.isFromMember() && transferType.isToSystem() && !transferType.getTo().isLimited()) {
                RequestHelper.storeEnum(request, ARateRelation.class, "aRateRelations");
                form.setTransactionFee("aRateRelation", arateRelation.name());
            }
        }
        request.setAttribute("chargeTypes", chargeTypes);

        // If from member, search for groups related to the from account type...
        if (transferType.isFromMember()) {
            final GroupQuery groupQuery = new GroupQuery();
            groupQuery.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
            groupQuery.setStatus(Group.Status.NORMAL);
            groupQuery.setMemberAccountType((MemberAccountType) transferType.getFrom());
            request.setAttribute("possibleFromGroups", groupService.search(groupQuery));
        }

        // ... same for to
        if (transferType.isToMember()) {
            final GroupQuery groupQuery = new GroupQuery();
            groupQuery.setNatures(Group.Nature.MEMBER, Group.Nature.BROKER);
            groupQuery.setStatus(Group.Status.NORMAL);
            groupQuery.setMemberAccountType((MemberAccountType) transferType.getTo());
            request.setAttribute("possibleToGroups", groupService.search(groupQuery));
        }

        // For broker groups, list only active
        final GroupQuery groupQuery = new GroupQuery();
        groupQuery.setNature(Group.Nature.BROKER);
        groupQuery.setStatus(Group.Status.NORMAL);
        groupQuery.setOnlyActive(true);
        request.setAttribute("brokerGroups", groupService.search(groupQuery));

        switch (nature) {
            case SIMPLE:
                return context.findForward("inputSimpleTransactionFee");
            case BROKER:
                return context.findForward("inputBrokerCommission");
            default:
                return null;
        }
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final EditTransactionFeeForm form = context.getForm();
        TransactionFee transactionFee = retrieveTransactionFee(form);
        final boolean isInsert = transactionFee.getId() == null;
        if (transactionFee instanceof BrokerCommission) {
            final BrokerCommission brokerCommission = (BrokerCommission) transactionFee;
            transactionFee = transactionFeeService.save(brokerCommission);
            context.sendMessage(isInsert ? "brokerCommission.inserted" : "brokerCommission.modified");
        } else {
            final SimpleTransactionFee fee = (SimpleTransactionFee) transactionFee;
            final ARateRelation aRateRelation = PropertyBinder.instance(ARateRelation.class, "aRateRelation").readFromString(form.getTransactionFee());
            transactionFee = transactionFeeService.save(fee, aRateRelation);
            context.sendMessage(isInsert ? "transactionFee.inserted" : "transactionFee.modified");
        }

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountTypeId", form.getAccountTypeId());
        params.put("transferTypeId", form.getTransferTypeId());
        params.put("transactionFeeId", transactionFee.getId());
        return ActionHelper.redirectWithParams(context.getRequest(), context.getSuccessForward(), params);
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final EditTransactionFeeForm form = context.getForm();
        final TransactionFee transactionFee = retrieveTransactionFee(form);
        if (transactionFee instanceof BrokerCommission) {
            final BrokerCommission brokerCommission = (BrokerCommission) transactionFee;
            transactionFeeService.validate(brokerCommission);
        } else {
            final SimpleTransactionFee simpleFee = (SimpleTransactionFee) transactionFee;
            final ARateRelation aRateRelation = PropertyBinder.instance(ARateRelation.class, "aRateRelation").readFromString(form.getTransactionFee());
            transactionFeeService.validate(simpleFee, aRateRelation);
        }
    }

    private void initBasic(final BeanBinder<? extends TransactionFee> binder, final LocalSettings localSettings) {
        binder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
        binder.registerBinder("name", PropertyBinder.instance(String.class, "name"));
        binder.registerBinder("description", PropertyBinder.instance(String.class, "description"));
        binder.registerBinder("enabled", PropertyBinder.instance(Boolean.TYPE, "enabled"));
        binder.registerBinder("generatedTransferType", PropertyBinder.instance(TransferType.class, "generatedTransferType"));
        binder.registerBinder("chargeType", PropertyBinder.instance(ChargeType.class, "chargeType"));
        binder.registerBinder("value", PropertyBinder.instance(BigDecimal.class, "value", localSettings.getHighPrecisionConverter()));
        binder.registerBinder("payer", PropertyBinder.instance(Subject.class, "payer"));
        binder.registerBinder("initialAmount", PropertyBinder.instance(BigDecimal.class, "initialAmount", localSettings.getNumberConverter()));
        binder.registerBinder("finalAmount", PropertyBinder.instance(BigDecimal.class, "finalAmount", localSettings.getNumberConverter()));
        binder.registerBinder("deductAmount", PropertyBinder.instance(Boolean.TYPE, "deductAmount"));
        binder.registerBinder("fromAllGroups", PropertyBinder.instance(Boolean.TYPE, "fromAllGroups"));
        binder.registerBinder("toAllGroups", PropertyBinder.instance(Boolean.TYPE, "toAllGroups"));
        binder.registerBinder("fromGroups", SimpleCollectionBinder.instance(MemberGroup.class, "fromGroups"));
        binder.registerBinder("toGroups", SimpleCollectionBinder.instance(MemberGroup.class, "toGroups"));
        binder.registerBinder("fromFixedMember", PropertyBinder.instance(Member.class, "fromFixedMember"));
    }

    private TransactionFee retrieveTransactionFee(final EditTransactionFeeForm form) {
        Nature nature;
        try {
            nature = Nature.valueOf(form.getNature());
        } catch (final Exception e) {
            throw new ValidationException();
        }
        final TransactionFee transactionFee = getDataBinder(nature).readFromString(form.getTransactionFee());
        transactionFee.setOriginalTransferType(EntityHelper.reference(TransferType.class, form.getTransferTypeId()));
        return transactionFee;
    }

}
