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
package nl.strohalm.cyclos.controls.members.brokering;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.controls.ActionContext;
import nl.strohalm.cyclos.controls.BaseFormAction;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.BrokerCommission.When;
import nl.strohalm.cyclos.entities.accounts.fees.transaction.TransactionFeeQuery;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.brokerings.DefaultBrokerCommission;
import nl.strohalm.cyclos.entities.settings.LocalSettings;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsChangeListener;
import nl.strohalm.cyclos.entities.settings.events.LocalSettingsEvent;
import nl.strohalm.cyclos.services.elements.CommissionService;
import nl.strohalm.cyclos.services.transfertypes.TransactionFeeService;
import nl.strohalm.cyclos.utils.Amount;
import nl.strohalm.cyclos.utils.Amount.Type;
import nl.strohalm.cyclos.utils.RequestHelper;
import nl.strohalm.cyclos.utils.binding.BeanBinder;
import nl.strohalm.cyclos.utils.binding.BeanCollectionBinder;
import nl.strohalm.cyclos.utils.binding.DataBinder;
import nl.strohalm.cyclos.utils.binding.PropertyBinder;
import nl.strohalm.cyclos.utils.conversion.IdConverter;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.struts.action.ActionForward;

/**
 * Action used to set default broker commissions
 * @author Jefferson Magno
 */
public class DefaultBrokerCommissionsAction extends BaseFormAction implements LocalSettingsChangeListener {

    public static class DefaultBrokerCommissionDTO {

        private Long             id;
        private Member           broker;
        private BrokerCommission brokerCommission;
        private Type             type;
        private BigDecimal       value;
        private Integer          count;
        private When             when;

        public Member getBroker() {
            return broker;
        }

        public BrokerCommission getBrokerCommission() {
            return brokerCommission;
        }

        public Integer getCount() {
            return count;
        }

        public Long getId() {
            return id;
        }

        public Type getType() {
            return type;
        }

        public BigDecimal getValue() {
            return value;
        }

        public When getWhen() {
            return when;
        }

        public void setBroker(final Member broker) {
            this.broker = broker;
        }

        public void setBrokerCommission(final BrokerCommission brokerCommission) {
            this.brokerCommission = brokerCommission;
        }

        public void setCount(final Integer count) {
            this.count = count;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public void setType(final Type type) {
            this.type = type;
        }

        public void setValue(final BigDecimal value) {
            this.value = value;
        }

        public void setWhen(final When when) {
            this.when = when;
        }
    }

    private CommissionService                                  commissionService;
    private TransactionFeeService                              transactionFeeService;

    private DataBinder<Collection<DefaultBrokerCommissionDTO>> dataBinder;

    public DataBinder<Collection<DefaultBrokerCommissionDTO>> getDataBinder() {
        if (dataBinder == null) {
            final LocalSettings localSettings = settingsService.getLocalSettings();

            final BeanBinder<DefaultBrokerCommissionDTO> beanBinder = BeanBinder.instance(DefaultBrokerCommissionDTO.class);
            beanBinder.registerBinder("id", PropertyBinder.instance(Long.class, "id", IdConverter.instance()));
            beanBinder.registerBinder("brokerCommission", PropertyBinder.instance(BrokerCommission.class, "brokerCommission"));
            beanBinder.registerBinder("type", PropertyBinder.instance(Type.class, "type"));
            beanBinder.registerBinder("value", PropertyBinder.instance(BigDecimal.class, "value", localSettings.getNumberConverter()));
            beanBinder.registerBinder("count", PropertyBinder.instance(Integer.class, "count"));
            beanBinder.registerBinder("when", PropertyBinder.instance(BrokerCommission.When.class, "when"));

            final BeanCollectionBinder<DefaultBrokerCommissionDTO> defaultBrokerCommissionsBinder = BeanCollectionBinder.instance(beanBinder, ArrayList.class);
            dataBinder = defaultBrokerCommissionsBinder;
        }
        return dataBinder;
    }

    @Override
    public void onLocalSettingsUpdate(final LocalSettingsEvent event) {
        dataBinder = null;
    }

    @Inject
    public void setCommissionService(final CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    @Inject
    public void setTransactionFeeService(final TransactionFeeService transactionFeeService) {
        this.transactionFeeService = transactionFeeService;
    }

    @Override
    protected ActionForward handleSubmit(final ActionContext context) throws Exception {
        final DefaultBrokerCommissionsForm form = context.getForm();
        final Member broker = context.getElement();
        final List<DefaultBrokerCommissionDTO> defaultBrokerCommissionsDTO = (List<DefaultBrokerCommissionDTO>) getDataBinder().readFromString(form.getDefaultBrokerCommission());
        List<DefaultBrokerCommission> defaultBrokerCommissions = dtoListToEntityList(broker, defaultBrokerCommissionsDTO);
        defaultBrokerCommissions = commissionService.saveDefaultBrokerCommissions(defaultBrokerCommissions);
        context.sendMessage("defaultBrokerCommission.updated");
        return context.getSuccessForward();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void prepareForm(final ActionContext context) throws Exception {
        final HttpServletRequest request = context.getRequest();

        if (!context.isBroker()) {
            throw new ValidationException();
        }

        final Member broker = (Member) context.getElement();
        final BrokerGroup brokerGroup = (BrokerGroup) broker.getGroup();

        // Get broker commission transaction fees (from member) related to the broker group, including the not enabled ones
        final TransactionFeeQuery transactionFeeQuery = new TransactionFeeQuery();
        transactionFeeQuery.setEntityType(BrokerCommission.class);
        transactionFeeQuery.setGeneratedTransferTypeFromNature(AccountType.Nature.MEMBER);
        transactionFeeQuery.setBrokerGroup(brokerGroup);
        transactionFeeQuery.setReturnDisabled(true);
        final List<BrokerCommission> groupCommissions = (List<BrokerCommission>) transactionFeeService.search(transactionFeeQuery);

        // Get current default broker commissions
        final List<DefaultBrokerCommission> currentDefaults = commissionService.loadDefaultBrokerCommissions(broker, DefaultBrokerCommission.Relationships.BROKER_COMMISSION);

        // Prepare list to JSP
        final List<DefaultBrokerCommission> defaultBrokerCommissions = buildCommissions(groupCommissions, currentDefaults);

        request.setAttribute("broker", broker);
        request.setAttribute("defaultBrokerCommissions", defaultBrokerCommissions);
        RequestHelper.storeEnum(request, BrokerCommission.When.class, "whens");
        RequestHelper.storeEnum(request, Amount.Type.class, "amountTypes");
    }

    @Override
    protected void validateForm(final ActionContext context) {
        final DefaultBrokerCommissionsForm form = context.getForm();
        final Member broker = context.getElement();
        final List<DefaultBrokerCommissionDTO> defaultBrokerCommissionsDTO = (List<DefaultBrokerCommissionDTO>) getDataBinder().readFromString(form.getDefaultBrokerCommission());
        final List<DefaultBrokerCommission> defaultBrokerCommissions = dtoListToEntityList(broker, defaultBrokerCommissionsDTO);
        commissionService.validateDefaultBrokerCommissions(defaultBrokerCommissions);
    }

    /*
     * For each broker commission related to the broker group, get the current default broker commission, or create a new one, if it does not exist.
     */
    private List<DefaultBrokerCommission> buildCommissions(final List<BrokerCommission> groupCommissions, final List<DefaultBrokerCommission> currentDefaults) {
        final List<DefaultBrokerCommission> defaultBrokerCommissions = new ArrayList<DefaultBrokerCommission>();
        for (final BrokerCommission groupCommission : groupCommissions) {
            DefaultBrokerCommission defaultBrokerCommission = null;
            for (final DefaultBrokerCommission currentDefault : currentDefaults) {
                if (currentDefault.getBrokerCommission().equals(groupCommission)) {
                    defaultBrokerCommission = currentDefault;
                    break;
                }
            }
            if (defaultBrokerCommission == null) {
                defaultBrokerCommission = new DefaultBrokerCommission();
                defaultBrokerCommission.setBrokerCommission(groupCommission);
                defaultBrokerCommission.setAmount(groupCommission.getAmount());
                defaultBrokerCommission.setWhen(groupCommission.getWhen());
                defaultBrokerCommission.setCount(groupCommission.getCount());
            }
            defaultBrokerCommissions.add(defaultBrokerCommission);
        }
        return defaultBrokerCommissions;
    }

    /*
     * Create a list of default broker commission based on a list of DTOs. The DTOs are based on data coming from the JSP
     */
    private List<DefaultBrokerCommission> dtoListToEntityList(final Member broker, final List<DefaultBrokerCommissionDTO> dtoList) {
        final List<DefaultBrokerCommission> defaultBrokerCommissions = new ArrayList<DefaultBrokerCommission>();
        for (final DefaultBrokerCommissionDTO dto : dtoList) {
            final Amount amount = new Amount();
            amount.setType(dto.getType());
            amount.setValue(dto.getValue());

            final DefaultBrokerCommission defaultBrokerCommission = new DefaultBrokerCommission();
            defaultBrokerCommission.setId(dto.getId());
            defaultBrokerCommission.setBrokerCommission(dto.getBrokerCommission());
            defaultBrokerCommission.setBroker(broker);
            defaultBrokerCommission.setAmount(amount);
            defaultBrokerCommission.setCount(dto.getCount());
            defaultBrokerCommission.setWhen(dto.getWhen());

            defaultBrokerCommissions.add(defaultBrokerCommission);
        }
        return defaultBrokerCommissions;
    }

}
