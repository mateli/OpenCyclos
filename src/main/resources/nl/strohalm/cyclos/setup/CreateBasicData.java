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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import nl.strohalm.cyclos.CyclosConfiguration;
import nl.strohalm.cyclos.access.AdminMemberPermission;
import nl.strohalm.cyclos.access.AdminSystemPermission;
import nl.strohalm.cyclos.access.BasicPermission;
import nl.strohalm.cyclos.access.BrokerPermission;
import nl.strohalm.cyclos.access.MemberPermission;
import nl.strohalm.cyclos.access.Module;
import nl.strohalm.cyclos.access.ModuleType;
import nl.strohalm.cyclos.access.Permission;
import nl.strohalm.cyclos.entities.Application;
import nl.strohalm.cyclos.entities.Application.PasswordHash;
import nl.strohalm.cyclos.entities.access.AdminUser;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.access.Channel.Credentials;
import nl.strohalm.cyclos.entities.access.Channel.Principal;
import nl.strohalm.cyclos.entities.access.ChannelPrincipal;
import nl.strohalm.cyclos.entities.groups.AdminGroup;
import nl.strohalm.cyclos.entities.groups.BasicGroupSettings.PasswordPolicy;
import nl.strohalm.cyclos.entities.groups.BrokerGroup;
import nl.strohalm.cyclos.entities.groups.Group;
import nl.strohalm.cyclos.entities.groups.MemberGroup;
import nl.strohalm.cyclos.entities.groups.MemberGroupSettings.EmailValidation;
import nl.strohalm.cyclos.entities.members.Administrator;
import nl.strohalm.cyclos.entities.settings.MessageSettings.MessageSettingsEnum;
import nl.strohalm.cyclos.entities.settings.Setting;
import nl.strohalm.cyclos.entities.sms.SmsType;
import nl.strohalm.cyclos.utils.HashHandler;
import nl.strohalm.cyclos.utils.conversion.LocaleConverter;
import nl.strohalm.cyclos.webservices.sms.SmsTypeCode;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Creates basic data, like application version, permissions, groups, a system administrator and the default settings
 * @author luis
 */
public class CreateBasicData implements Runnable {

    private static List<Permission> unwantedAccountAdminPermissions;
    private static List<Permission> unwantedAdminPermissions;
    private static List<Permission> unwantedMemberPermissions;

    static {
        /* Unwanted account admin permissions */
        unwantedAccountAdminPermissions = Arrays.asList(
                (Permission) AdminMemberPermission.MEMBERS_IMPORT,
                AdminMemberPermission.ADS_IMPORT
                );

        /* Unwanted admin permissions */
        List<Permission> unwantedAdminPermissionsList = Arrays.asList(
                (Permission) BasicPermission.BASIC_INVITE_MEMBER,

                AdminMemberPermission.ACCESS_DISCONNECT_OPERATOR,
                AdminMemberPermission.ACCOUNTS_AUTHORIZED_INFORMATION,
                AdminMemberPermission.GUARANTEES_CANCEL_CERTIFICATIONS_AS_MEMBER,
                AdminMemberPermission.GUARANTEES_CANCEL_GUARANTEES_AS_MEMBER,
                AdminMemberPermission.GUARANTEES_ACCEPT_GUARANTEES_AS_MEMBER,
                AdminMemberPermission.INVOICES_SEND_AS_MEMBER_TO_MEMBER,
                AdminMemberPermission.INVOICES_SEND_AS_MEMBER_TO_SYSTEM,
                AdminMemberPermission.INVOICES_ACCEPT_AS_MEMBER_FROM_MEMBER,
                AdminMemberPermission.INVOICES_ACCEPT_AS_MEMBER_FROM_SYSTEM,
                AdminMemberPermission.INVOICES_DENY_AS_MEMBER,
                AdminMemberPermission.INVOICES_CANCEL_AS_MEMBER,
                AdminMemberPermission.MEMBERS_CHANGE_USERNAME,
                AdminMemberPermission.PAYMENTS_AUTHORIZE,
                AdminMemberPermission.PAYMENTS_PAYMENT_WITH_DATE,
                AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER,
                AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF,
                AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM,
                AdminMemberPermission.PAYMENTS_CANCEL_AUTHORIZED_AS_MEMBER,
                AdminMemberPermission.PAYMENTS_CANCEL_SCHEDULED_AS_MEMBER,
                AdminMemberPermission.PAYMENTS_BLOCK_SCHEDULED_AS_MEMBER,
                AdminMemberPermission.LOANS_GRANT_WITH_DATE,
                AdminMemberPermission.LOANS_REPAY_WITH_DATE,
                AdminMemberPermission.LOANS_VIEW_AUTHORIZED,
                AdminMemberPermission.BROKERINGS_MANAGE_COMMISSIONS,

                AdminSystemPermission.ACCOUNTS_AUTHORIZED_INFORMATION,
                AdminSystemPermission.PAYMENTS_AUTHORIZE,
                AdminSystemPermission.PAYMENTS_CANCEL,
                AdminSystemPermission.REPORTS_SMS_LOGS,
                AdminSystemPermission.STATUS_VIEW_CONNECTED_OPERATORS
                );

        unwantedAdminPermissions = new ArrayList<Permission>();
        unwantedAdminPermissions.addAll(unwantedAdminPermissionsList);
        unwantedAdminPermissions.addAll(Module.ADMIN_MEMBER_DOCUMENTS.getPermissions());
        unwantedAdminPermissions.addAll(Module.ADMIN_MEMBER_GUARANTEES.getPermissions());
        unwantedAdminPermissions.addAll(Module.ADMIN_MEMBER_POS.getPermissions());
        unwantedAdminPermissions.addAll(Module.ADMIN_MEMBER_SMS.getPermissions());
        unwantedAdminPermissions.addAll(Module.SYSTEM_GUARANTEE_TYPES.getPermissions());

        /* Unwanted member permissions */
        List<Permission> unwantedMemberPermissionsList = Arrays.asList(
                (Permission) BasicPermission.BASIC_INVITE_MEMBER,

                MemberPermission.PAYMENTS_PAYMENT_TO_SELF,
                MemberPermission.PAYMENTS_REQUEST,
                MemberPermission.INVOICES_SEND_TO_SYSTEM,
                MemberPermission.ACCOUNT_AUTHORIZED_INFORMATION,
                MemberPermission.PAYMENTS_AUTHORIZE,
                MemberPermission.PAYMENTS_CANCEL_AUTHORIZED,
                MemberPermission.PAYMENTS_CANCEL_SCHEDULED,
                MemberPermission.PREFERENCES_MANAGE_RECEIPT_PRINTER_SETTINGS,

                BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER,
                BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF,
                BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM,
                BrokerPermission.MEMBER_PAYMENTS_CANCEL_AUTHORIZED_AS_MEMBER,
                BrokerPermission.MEMBER_PAYMENTS_CANCEL_SCHEDULED_AS_MEMBER,
                BrokerPermission.MEMBER_PAYMENTS_BLOCK_SCHEDULED_AS_MEMBER,
                BrokerPermission.INVOICES_SEND_AS_MEMBER_TO_MEMBER,
                BrokerPermission.INVOICES_SEND_AS_MEMBER_TO_SYSTEM,
                BrokerPermission.INVOICES_ACCEPT_AS_MEMBER_FROM_MEMBER,
                BrokerPermission.INVOICES_ACCEPT_AS_MEMBER_FROM_SYSTEM,
                BrokerPermission.INVOICES_DENY_AS_MEMBER,
                BrokerPermission.INVOICES_CANCEL_AS_MEMBER,
                BrokerPermission.MEMBERS_CHANGE_USERNAME,
                BrokerPermission.MEMBERS_CHANGE_NAME,
                BrokerPermission.ACCOUNTS_AUTHORIZED_INFORMATION
                );

        unwantedMemberPermissions = new ArrayList<Permission>();
        unwantedMemberPermissions.addAll(unwantedMemberPermissionsList);
        unwantedMemberPermissions.addAll(Module.MEMBER_DOCUMENTS.getPermissions());
        unwantedMemberPermissions.addAll(Module.MEMBER_GUARANTEES.getPermissions());
        unwantedMemberPermissions.addAll(Module.MEMBER_OPERATORS.getPermissions());
        unwantedMemberPermissions.addAll(Module.MEMBER_SMS.getPermissions());
        unwantedMemberPermissions.addAll(Module.BROKER_DOCUMENTS.getPermissions());
        unwantedMemberPermissions.addAll(Module.BROKER_MEMBER_SMS.getPermissions());
    }

    public static void createChannels(final Session session, final ResourceBundle bundle) {
        final List<Channel> builtinChannels = getBuiltinChannels(bundle);
        for (final Channel channel : builtinChannels) {
            Number count = (Number) session.createQuery("select count(*) from Channel c where c.internalName = :name").setString("name", channel.getInternalName()).uniqueResult();
            if (count.intValue() == 0) {
                session.save(channel);
            }
        }
    }

    public static void createSettings(final Session session, final ResourceBundle bundle, final Locale locale, final Properties cyclosProperties) {
        createSetting(session, Setting.Type.LOCAL, "charset", "UTF-8");
        createSetting(session, Setting.Type.LOCAL, "language", LocaleConverter.instance().toString(locale));
        createSetting(session, Setting.Type.LOCAL, "applicationUsername", bundle.getString("settings.local.application-name"));
        createSetting(session, Setting.Type.LOCAL, "defaultExternalPaymentDescription", bundle.getString("settings.local.default-external-payment-description"));
        createSetting(session, Setting.Type.LOCAL, "chargebackDescription", bundle.getString("settings.local.chargeback-description"));
        createSetting(session, Setting.Type.MAIL_TRANSLATION, "invitationSubject", bundle.getString("settings.mail.invitation.subject"));
        createSetting(session, Setting.Type.MAIL_TRANSLATION, "invitationMessage", bundle.getString("settings.mail.invitation.message"));
        createSetting(session, Setting.Type.MAIL_TRANSLATION, "activationSubject", bundle.getString("settings.mail.activation.subject"));
        createSetting(session, Setting.Type.MAIL_TRANSLATION, "activationMessageWithoutPassword", bundle.getString("settings.mail.activationWithoutPassword.message"));
        createSetting(session, Setting.Type.MAIL_TRANSLATION, "activationMessageWithPassword", bundle.getString("settings.mail.activationWithPassword.message"));
        createSetting(session, Setting.Type.MAIL_TRANSLATION, "resetPasswordSubject", bundle.getString("settings.mail.reset-password.subject"));
        createSetting(session, Setting.Type.MAIL_TRANSLATION, "resetPasswordMessage", bundle.getString("settings.mail.reset-password.message"));
        createSetting(session, Setting.Type.MAIL_TRANSLATION, "mailValidationSubject", bundle.getString("settings.mail.mail-validation.subject"));
        createSetting(session, Setting.Type.MAIL_TRANSLATION, "mailValidationMessage", bundle.getString("settings.mail.mail-validation.message"));

        for (final MessageSettingsEnum messageSetting : MessageSettingsEnum.values()) {
            if (messageSetting.messageSettingKey() != null) {
                try {
                    final String body = bundle.getString(messageSetting.messageSettingKey());
                    createSetting(session, Setting.Type.MESSAGE, messageSetting.messageSettingName(), body);
                } catch (final Exception e) {
                    // No message in the bundle. Ignore, as Cyclos will use the default value
                }
            }
            if (messageSetting.subjectSettingKey() != null) {
                try {
                    final String subject = bundle.getString(messageSetting.subjectSettingKey());
                    createSetting(session, Setting.Type.MESSAGE, messageSetting.subjectSettingName(), subject);
                } catch (final Exception e) {
                    // No message in the bundle. Ignore, as Cyclos will use the default value
                }
            }
            if (messageSetting.smsSettingKey() != null) {
                try {
                    final String sms = bundle.getString(messageSetting.smsSettingKey());
                    createSetting(session, Setting.Type.MESSAGE, messageSetting.smsSettingName(), sms);
                } catch (final Exception e) {
                    // No message in the bundle. Ignore, as Cyclos will use the default value
                }
            }
        }

        // Define logs dir
        final String defaultLogDir = cyclosProperties.getProperty("cyclos.default.logDir", "%t");
        final String defaultLogPrefix = cyclosProperties.getProperty("cyclos.default.logPrefix", "cyclos_");
        createSetting(session, Setting.Type.LOG, "traceFile", defaultLogDir + "/" + defaultLogPrefix + "trace%g.log");
        createSetting(session, Setting.Type.LOG, "transactionFile", defaultLogDir + "/" + defaultLogPrefix + "transactions%g.log");
        createSetting(session, Setting.Type.LOG, "accountFeeFile", defaultLogDir + "/" + defaultLogPrefix + "account_fees%g.log");
        createSetting(session, Setting.Type.LOG, "scheduledTaskFile", defaultLogDir + "/" + defaultLogPrefix + "scheduled_task%g.log");
        createSetting(session, Setting.Type.LOG, "webServiceFile", defaultLogDir + "/" + defaultLogPrefix + "webservices%g.log");
        createSetting(session, Setting.Type.LOG, "restFile", defaultLogDir + "/" + defaultLogPrefix + "rest%g.log");
    }

    public static List<Channel> getBuiltinChannels(final ResourceBundle bundle) {
        final List<Channel> channels = new ArrayList<Channel>();
        channels.add(buildChannel(bundle, Channel.WEB, Principal.USER, Credentials.DEFAULT));
        channels.add(buildChannel(bundle, Channel.REST, Principal.USER, Credentials.DEFAULT));
        channels.add(buildChannel(bundle, Channel.WAP2, Principal.USER, Credentials.DEFAULT));
        channels.add(buildChannel(bundle, Channel.WAP1, Principal.USER, Credentials.DEFAULT));
        channels.add(buildChannel(bundle, Channel.WEBSHOP, Principal.USER, Credentials.DEFAULT));
        channels.add(buildChannel(bundle, Channel.POSWEB, Principal.USER, Credentials.PIN));
        channels.add(buildChannel(bundle, Channel.POS, Principal.USER, Credentials.PIN));
        return channels;
    }

    public static List<SmsType> getBuiltinSmsTypes(final ResourceBundle bundle) {
        final List<SmsType> smsTypes = new ArrayList<SmsType>();
        try {
            for (SmsTypeCode smsTypeCode : SmsTypeCode.values()) {
                smsTypes.add(buildSmsType(bundle, smsTypeCode));
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        return smsTypes;
    }

    private static Channel buildChannel(final ResourceBundle resourceBundle, final String internalName, final Principal principal, final Credentials credentials) {
        final Channel channel = new Channel();
        channel.setInternalName(internalName);
        channel.setDisplayName(resourceBundle.getString("channel." + internalName));
        channel.setCredentials(credentials);
        final ChannelPrincipal cp = new ChannelPrincipal();
        cp.setChannel(channel);
        cp.setPrincipal(principal);
        channel.setPrincipals(Collections.singleton(cp));
        return channel;
    }

    private static SmsType buildSmsType(final ResourceBundle resourceBundle, final SmsTypeCode smsTypeCode) {
        final SmsType smsType = new SmsType();
        smsType.setCode(smsTypeCode.code());
        smsType.setOrder(smsTypeCode.ordinal());
        return smsType;
    }

    static void associateGroupToChannel(final Channel channel, final MemberGroup memberGroup) {
        // Get the "channels" collection
        Collection<Channel> channels = memberGroup.getChannels();

        // If the "channels" collection does not exist, create a new one and set it on the member group
        if (channels == null) {
            channels = new ArrayList<Channel>();
            memberGroup.setChannels(channels);
        }

        // Add the channel to the "channels" collection
        channels.add(channel);

        // Get the "default channels" collection
        Collection<Channel> defaultChannels = memberGroup.getDefaultChannels();

        // If the "default channels" collection does not exist, create a new one and set it on the member group
        if (defaultChannels == null) {
            defaultChannels = new ArrayList<Channel>();
            memberGroup.setDefaultChannels(defaultChannels);
        }

        // Add the channel to the "default channels" collection
        defaultChannels.add(channel);
    }

    static void createSetting(final Session session, final Setting.Type type, final String name, final String value) {
        final String newValue = StringUtils.trimToEmpty(value);

        Setting setting = (Setting) session.createQuery("from Setting s where s.type=:type and s.name=:name").setParameter("type", type).setParameter("name", name).uniqueResult();

        if (setting == null) {
            setting = new Setting();
            setting.setType(type);
            setting.setName(name);
        } else {
            if (StringUtils.isNotEmpty(setting.getValue())) {
                // Already contains a value
                return;
            }
        }

        if (!newValue.equals(setting.getValue())) {
            setting.setValue(value);
            session.saveOrUpdate(setting);
        }
    }

    private final ResourceBundle bundle;
    private Properties           cyclosProperties;
    private final Session        session;
    private final boolean        setupOnly;
    private final Locale         locale;

    private AdminGroup           systemAdmins;

    public CreateBasicData(final Setup setup, final boolean setupOnly) {
        this.setupOnly = setupOnly;
        session = setup.getSession();
        bundle = setup.getBundle();
        try {
            cyclosProperties = CyclosConfiguration.getCyclosProperties();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        locale = setup.getLocale() == null ? Locale.getDefault() : setup.getLocale();
    }

    /**
     * Create the basic data
     */
    @Override
    public void run() {
        // Check if the basic data is already there
        if (session.createCriteria(Application.class).uniqueResult() != null) {
            Setup.out.println(bundle.getString("basic-data.error.already"));
            return;
        }

        Setup.out.println(bundle.getString("basic-data.start"));

        createApplication();
        createChannels();
        createSmsTypes();
        if (!setupOnly) {
            createGroups();
            createAdministrator();
        }
        createSettings(session, bundle, locale, cyclosProperties);

        session.flush();

        Setup.out.println(bundle.getString("basic-data.end"));
    }

    private void associateDefaultChannels(final MemberGroup group) {
        associateGroupToChannel(Channel.WEB, group);
        associateGroupToChannel(Channel.REST, group);
        associateGroupToChannel(Channel.WAP2, group);
    }

    private void associateGroupToChannel(final String channelStr, final MemberGroup memberGroup) {
        // Get the channel
        final Channel channel = (Channel) session.createCriteria(Channel.class).add(Restrictions.eq("internalName", channelStr)).uniqueResult();

        associateGroupToChannel(channel, memberGroup);
    }

    private void createAdministrator() {
        final HashHandler hashHandler = new HashHandler();
        final AdminUser user = new AdminUser();
        user.setSalt(hashHandler.newSalt());
        user.setUsername("admin");
        user.setPassword(hashHandler.hash(user.getSalt(), "1234"));
        user.setPasswordDate(Calendar.getInstance());
        final Administrator administrator = new Administrator();
        administrator.setEmail("admin@mail.nl");
        administrator.setName("Administrator");
        administrator.setCreationDate(Calendar.getInstance());
        administrator.setGroup(systemAdmins);
        administrator.setUser(user);
        session.save(administrator);
    }

    private void createApplication() {
        final Version currentVersion = new VersionHistoryReader().read().getCurrent();
        final Application application = new Application();
        application.setVersion(currentVersion.getLabel());
        application.setAccountStatusEnabledSince(Calendar.getInstance());
        application.setPasswordHash(PasswordHash.SHA2_SALT);
        application.setOnline(true);
        session.save(application);
    }

    private void createChannels() {
        createChannels(session, bundle);
    }

    private <G extends Group> G createGroup(final Class<G> groupClass, final Group.Status status, final String keyPart, final ModuleType... moduleTypes) {
        G group = null;
        try {
            group = groupClass.newInstance();
        } catch (final Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        group.setStatus(status);
        group.setName(bundle.getString("group." + keyPart + ".name"));
        group.setDescription(bundle.getString("group." + keyPart + ".description"));
        group.getBasicSettings().setPasswordPolicy(PasswordPolicy.NONE);

        session.save(group);
        if (moduleTypes != null && moduleTypes.length > 0) {
            if (group.getPermissions() == null) {
                group.setPermissions(new HashSet<Permission>());
            }
            for (final ModuleType moduleType : moduleTypes) {
                for (Module module : moduleType.getModules()) {
                    group.getPermissions().addAll(module.getPermissions());
                }
            }

            session.flush();
        }
        return group;
    }

    private void createGroups() {
        systemAdmins = createGroup(AdminGroup.class, Group.Status.NORMAL, "system-admins", ModuleType.BASIC, ModuleType.ADMIN_SYSTEM, ModuleType.ADMIN_ADMIN, ModuleType.ADMIN_MEMBER);
        removeUnwantedAdminPermissions(systemAdmins);

        final AdminGroup accountAdmins = createGroup(AdminGroup.class, Group.Status.NORMAL, "account-admins", ModuleType.BASIC, ModuleType.ADMIN_MEMBER);
        removeUnwantedAdminPermissions(accountAdmins);
        removeUnwantedAccountAdminPermissions(accountAdmins);

        // Create disabled admins group and removed admins group
        createGroup(AdminGroup.class, Group.Status.NORMAL, "disabled-admins");
        createGroup(AdminGroup.class, Group.Status.REMOVED, "removed-admins");

        // Create full members group
        final MemberGroup fullMembers = createGroup(MemberGroup.class, Group.Status.NORMAL, "full-members", ModuleType.BASIC, ModuleType.MEMBER);
        fullMembers.setActive(true);
        fullMembers.getMemberSettings().setEmailValidation(new HashSet<EmailValidation>(Arrays.asList(EmailValidation.USER)));
        removeUnwantedMemberPermissions(fullMembers);
        associateDefaultChannels(fullMembers);

        // Create inactive members group
        final MemberGroup inactiveMembers = createGroup(MemberGroup.class, Group.Status.NORMAL, "inactive-members");
        inactiveMembers.setInitialGroup(true);

        // Crate disabled members group and removed members group
        final MemberGroup disabledMembers = createGroup(MemberGroup.class, Group.Status.NORMAL, "disabled-members");
        final MemberGroup removedMembers = createGroup(MemberGroup.class, Group.Status.REMOVED, "removed-members");

        // Create full brokers group
        final BrokerGroup fullBrokers = createGroup(BrokerGroup.class, Group.Status.NORMAL, "full-brokers", ModuleType.BASIC, ModuleType.MEMBER, ModuleType.BROKER);
        fullBrokers.setActive(true);
        removeUnwantedMemberPermissions(fullBrokers);
        associateDefaultChannels(fullBrokers);

        // Create disabled brokers group and removed brokers group
        final BrokerGroup disabledBrokers = createGroup(BrokerGroup.class, Group.Status.NORMAL, "disabled-brokers");
        final BrokerGroup removedBrokers = createGroup(BrokerGroup.class, Group.Status.REMOVED, "removed-brokers");

        final List<MemberGroup> allMemberGroups = Arrays.asList(fullMembers, inactiveMembers, disabledMembers, removedMembers, fullBrokers, disabledBrokers, removedBrokers);

        // Set the default permissions to manage member group
        systemAdmins.setManagesGroups(new ArrayList<MemberGroup>(allMemberGroups));
        accountAdmins.setManagesGroups(new ArrayList<MemberGroup>(allMemberGroups));

        // Allow admins to see each other
        systemAdmins.setViewConnectedAdminsOf(Arrays.asList(systemAdmins, accountAdmins));
        accountAdmins.setViewConnectedAdminsOf(Arrays.asList(systemAdmins, accountAdmins));

        // Set the default permissions to view profile and ads
        fullMembers.setCanViewAdsOfGroups(Arrays.asList(fullMembers, fullBrokers));
        fullMembers.setCanViewProfileOfGroups(Arrays.asList(fullMembers, fullBrokers));
        fullBrokers.setCanViewAdsOfGroups(Arrays.asList(fullMembers, fullBrokers));
        fullBrokers.setCanViewProfileOfGroups(Arrays.asList(fullMembers, fullBrokers));
    }

    private void createSmsTypes() {
        final List<SmsType> builtinSmsTypes = getBuiltinSmsTypes(bundle);
        for (final SmsType smsType : builtinSmsTypes) {
            session.save(smsType);
        }
    }

    private void removeUnwantedAccountAdminPermissions(final AdminGroup group) {
        final Iterator<Permission> iterator = group.getPermissions().iterator();
        while (iterator.hasNext()) {
            if (unwantedAccountAdminPermissions.contains(iterator.next())) {
                iterator.remove();
            }
        }
    }

    private void removeUnwantedAdminPermissions(final AdminGroup group) {
        final Iterator<Permission> iterator = group.getPermissions().iterator();
        while (iterator.hasNext()) {
            if (unwantedAdminPermissions.contains(iterator.next())) {
                iterator.remove();
            }
        }
    }

    private void removeUnwantedMemberPermissions(final MemberGroup group) {
        final Iterator<Permission> iterator = group.getPermissions().iterator();
        while (iterator.hasNext()) {
            if (unwantedMemberPermissions.contains(iterator.next())) {
                iterator.remove();
            }
        }
    }
}
