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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.access.ChannelPrincipal;
import nl.strohalm.cyclos.entities.accounts.MemberAccountType;
import nl.strohalm.cyclos.entities.accounts.SystemAccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.PaymentFilter;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.customization.fields.MemberCustomField;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings;
import nl.strohalm.cyclos.entities.infotexts.InfoText;
import nl.strohalm.cyclos.entities.members.messages.Message;
import nl.strohalm.cyclos.entities.members.messages.Message.Type;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.services.ServiceOperation;
import nl.strohalm.cyclos.entities.settings.Setting;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class CreateSmsData implements Runnable {

    private final ResourceBundle    bundle;
    private final Session           session;
    private MemberGroup             fullMembers;
    private MemberGroup             inactiveMembers;
    private BrokerGroup             fullBrokers;
    private Channel                 smsChannel;
    private TransferType            smsTradeTT;
    private TransferType            smsChargeTT;
    private MemberCustomField       mobileCustomField;
    private MemberAccountType       memberAccType;
    private SystemAccountType       organizationAccType;
    private Collection<MemberGroup> enabledMemberGroups;

    public CreateSmsData(final Setup setup) {
        session = setup.getSession();
        bundle = setup.getBundle();
    }

    @Override
    public void run() {
        Setup.out.println(bundle.getString("sms-data.start"));

        fullMembers = (MemberGroup) session.createCriteria(MemberGroup.class).add(Restrictions.eq("name", bundle.getString("group.full-members.name"))).uniqueResult();
        inactiveMembers = (MemberGroup) session.createCriteria(MemberGroup.class).add(Restrictions.eq("name", bundle.getString("group.inactive-members.name"))).uniqueResult();
        fullBrokers = (BrokerGroup) session.createCriteria(BrokerGroup.class).add(Restrictions.eq("name", bundle.getString("group.full-brokers.name"))).uniqueResult();

        enabledMemberGroups = Arrays.asList(fullMembers, fullBrokers);

        mobileCustomField = (MemberCustomField) session.createCriteria(MemberCustomField.class).add(Restrictions.eq("internalName", "mobilePhone")).uniqueResult();

        memberAccType = (MemberAccountType) session.createCriteria(MemberAccountType.class).add(Restrictions.eq("name", bundle.getString("account.member.name"))).uniqueResult();
        organizationAccType = (SystemAccountType) session.createCriteria(SystemAccountType.class).add(Restrictions.eq("name", bundle.getString("account.organization.name"))).uniqueResult();

        createChannel();
        createTransfers();
        createServiceClient();
        createLocalSettings();
        creatInfoText();
        updateMemberGroup();
        updatePaymentFilter();
        customizeFullMembersGroup();
        customizeInactiveMembersGroup();

        session.flush();
        Setup.out.println(bundle.getString("sms-data.end"));
    }

    private void createChannel() {
        List<ChannelPrincipal> principals = new ArrayList<ChannelPrincipal>();

        smsChannel = new Channel();
        smsChannel.setDisplayName("SMS");
        smsChannel.setInternalName("sms");
        smsChannel.setCredentials(Credentials.PIN);
        smsChannel.setPaymentRequestWebServiceUrl("http://localhost:8080/sms/services/paymentRequest");
        smsChannel.setPrincipals(principals);

        final ChannelPrincipal userPrincipal = new ChannelPrincipal();
        userPrincipal.setChannel(smsChannel);
        userPrincipal.setPrincipal(Principal.USER);
        userPrincipal.setDefault(true);
        principals.add(userPrincipal);

        final ChannelPrincipal mobilePrincipal = new ChannelPrincipal();
        mobilePrincipal.setChannel(smsChannel);
        mobilePrincipal.setPrincipal(Principal.CUSTOM_FIELD);
        mobilePrincipal.setCustomField(mobileCustomField);
        principals.add(mobilePrincipal);

        session.save(smsChannel);
        session.save(userPrincipal);
        session.save(mobilePrincipal);
    }

    private void createLocalSettings() {
        CreateBasicData.createSetting(session, Setting.Type.LOCAL, "cyclosId", "cyclos");
        CreateBasicData.createSetting(session, Setting.Type.LOCAL, "smsEnabled", "true");
        CreateBasicData.createSetting(session, Setting.Type.LOCAL, "smsCustomFieldId", mobileCustomField.getId().toString());
        CreateBasicData.createSetting(session, Setting.Type.LOCAL, "smsChannelName", smsChannel.getInternalName());
        CreateBasicData.createSetting(session, Setting.Type.LOCAL, "sendSmsWebServiceUrl", "http://localhost:8080/sms/services/smsSender");
    }

    private void createServiceClient() {
        ServiceClient client = new ServiceClient();
        client.setName(bundle.getString("service-client.controller.name"));
        client.setHostname("127.0.0.1");
        client.setAddressBegin("127.000.000.001");
        client.setAddressEnd("127.000.000.001");
        client.setChannel(smsChannel);
        client.setCredentialsRequired(true);
        client.setIgnoreRegistrationValidations(true);

        client.setDoPaymentTypes(Collections.singleton(smsTradeTT));
        client.setChargebackPaymentTypes(Collections.singleton(smsTradeTT));
        client.setManageGroups(new HashSet<MemberGroup>(enabledMemberGroups));

        Set<ServiceOperation> permissions = new HashSet<ServiceOperation>();
        permissions.add(ServiceOperation.DO_PAYMENT);
        permissions.add(ServiceOperation.CHARGEBACK);
        permissions.add(ServiceOperation.MEMBERS);
        permissions.add(ServiceOperation.MANAGE_MEMBERS);
        permissions.add(ServiceOperation.ACCOUNT_DETAILS);
        permissions.add(ServiceOperation.ACCESS);
        permissions.add(ServiceOperation.SMS);
        permissions.add(ServiceOperation.INFO_TEXTS);

        client.setPermissions(permissions);

        session.save(client);
    }

    private void createTransfers() {
        // Add the sms trade transfer
        smsTradeTT = new TransferType();
        smsTradeTT.setName(bundle.getString("transfer-type.sms-trade.name"));
        smsTradeTT.setDescription(bundle.getString("transfer-type.sms-trade.description"));
        smsTradeTT.setFrom(memberAccType);
        smsTradeTT.setTo(memberAccType);
        smsTradeTT.getContext().setPayment(true);
        smsTradeTT.setChannels(Collections.singleton(smsChannel));
        // smsTradeTT.setMaxAmountPerDay(new BigDecimal(500));
        smsTradeTT.setAllowSmsNotification(true);
        smsTradeTT.setGroups(new HashSet<Group>(enabledMemberGroups));

        session.save(smsTradeTT);

        // Add the sms charging transfer
        smsChargeTT = new TransferType();
        smsChargeTT.setName(bundle.getString("transfer-type.sms-charge.name"));
        smsChargeTT.setDescription(bundle.getString("transfer-type.sms-charge.description"));
        smsChargeTT.setFrom(memberAccType);
        smsChargeTT.setTo(organizationAccType);

        session.save(smsChargeTT);

    }

    private void creatInfoText() {
        InfoText info = new InfoText();
        info.setAliases(Collections.singleton("info"));
        info.setBody(bundle.getString("info-text.sample.body"));
        info.setSubject(bundle.getString("info-text.sample.subject"));
        info.setEnabled(true);

        session.save(info);
    }

    private void customizeFullMembersGroup() {
        fullMembers.setInitialGroup(true);
        fullMembers.getPermissions().add(MemberPermission.SMS_VIEW);
    }

    private void customizeInactiveMembersGroup() {
        inactiveMembers.setInitialGroup(false);
    }

    private void updateMemberGroup() {
        for (MemberGroup mGrp : enabledMemberGroups) {

            CreateBasicData.associateGroupToChannel(smsChannel, mGrp);

            // Default messages sent by SMS
            Collection<Message.Type> smsMessages = mGrp.getDefaultSmsMessages();
            if (smsMessages == null) {
                smsMessages = new ArrayList<Message.Type>();
                mGrp.setDefaultSmsMessages(smsMessages);
            }
            smsMessages.add(Message.Type.PAYMENT);

            // SMS allowed messages
            final List<Type> smsMessageTypes = new ArrayList<Type>(Arrays.asList(Message.Type.values()));
            CollectionUtils.filter(smsMessageTypes, new Predicate() {
                @Override
                public boolean evaluate(final Object object) {
                    final Message.Type type = (Message.Type) object;
                    switch (type) {
                        case FROM_MEMBER:
                        case FROM_ADMIN_TO_MEMBER:
                        case FROM_ADMIN_TO_GROUP:
                            return false;
                        default:
                            return true;
                    }
                }
            });
            mGrp.setSmsMessages(smsMessageTypes);
            mGrp.setDefaultAllowChargingSms(true);
            mGrp.setDefaultAcceptFreeMailing(true);
            mGrp.setDefaultAcceptPaidMailing(true);

            MemberGroupSettings mSettings = mGrp.getMemberSettings();
            mSettings.setSmsChargeTransferType(smsChargeTT);
            mSettings.setSmsChargeAmount(new BigDecimal(1));

            // Add the sms trade transfer to the member-to-member permission collection
            mGrp.getTransferTypes().add(smsTradeTT);
            session.save(mGrp);
        }
    }

    private void updatePaymentFilter() {
        PaymentFilter filter = (PaymentFilter) session.createCriteria(PaymentFilter.class).
                add(Restrictions.eq("name", bundle.getString("payment-filter.member-payments"))).
                add(Restrictions.eq("accountType", memberAccType))
                .uniqueResult();
        filter.getTransferTypes().add(smsTradeTT);

        filter = (PaymentFilter) session.createCriteria(PaymentFilter.class).
                add(Restrictions.eq("name", bundle.getString("payment-filter.member-payments"))).
                add(Restrictions.eq("accountType", organizationAccType))
                .uniqueResult();
        filter.getTransferTypes().add(smsChargeTT);

        session.save(filter);
    }
}
