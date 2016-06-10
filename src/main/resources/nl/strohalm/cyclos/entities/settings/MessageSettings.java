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
package nl.strohalm.cyclos.entities.settings;

import nl.strohalm.cyclos.utils.DataObject;

import org.apache.commons.lang.StringUtils;

/**
 * Groups messages settings
 * @author Jefferson Magno
 * @author luis
 */
public class MessageSettings extends DataObject {
    public enum MessageSettingsCategoryEnum {
        GENERAL, MEMBER, ADMIN;
    }

    public enum MessageSettingsEnum {
        LOGIN_BLOCKED(MessageSettingsCategoryEnum.MEMBER,
                "Your login is temporarily blocked",
                "You have reached the maximum number of wrong login tries.<br>Now, your login is temporarily blocked.",
                "You have reached the maximum number of wrong login tries. Now, your login is temporarily blocked."),

        PIN_BLOCKED(MessageSettingsCategoryEnum.MEMBER,
                "Your PIN is temporarily blocked",
                "You have reached the maximum number of wrong PIN tries.<br>Now, your PIN is temporarily blocked.",
                "You have reached the maximum number of wrong PIN tries. Now, your PIN is temporarily blocked."),

        POS_PIN_BLOCKED(MessageSettingsCategoryEnum.MEMBER,
                "Your POS pin is temporarily blocked",
                "You have reached the maximum number of wrong PIN tries.<br>Now, your POS is temporarily blocked.",
                "You have reached the maximum number of wrong PIN tries, and your POS is temporarily blocked."),

        CARD_SECURITY_CODE_BLOCKED(MessageSettingsCategoryEnum.MEMBER,
                "Your card security code is temporarily blocked",
                "You have reached the maximum number of wrong card security code tries.<br>Now, your card is temporarily blocked.",
                "You have reached the maximum number of wrong card security code tries, and your card is temporarily blocked."),

        BROKERING_EXPIRATION(MessageSettingsCategoryEnum.MEMBER,
                "Brokering relation expired",
                "The brokering relation between you and #member# (#login#) has expired",
                "The brokering relation between you and #login# has expired"),

        BROKERING_REMOVED(MessageSettingsCategoryEnum.MEMBER,
                "Brokering relation removed",
                "The brokering relation between you and #member# (#login#) has been removed",
                "The brokering relation between you and #login# has been removed"),

        REMOVED_FROM_BROKER_GROUP(MessageSettingsCategoryEnum.MEMBER,
                "You were removed from the broker group",
                "The administration removed you from the broker group",
                "The administration removed you from the broker group"),

        NEW_COMMISSION_CONTRACT(MessageSettingsCategoryEnum.MEMBER,
                "Broker commission contract registered",
                "The broker #broker# registered a new contract that is awaiting your approval:<br>Start date: #start_date#<br>End date: #end_date#<br>Amount: #amount#<br>#link# for more details",
                "The broker #brokerUsername# registered a new contract that is awaiting your approval. Start date: #start_date#, end date: #end_date#, amount: #amount#"),

        COMMISSION_CONTRACT_ACCEPTED(MessageSettingsCategoryEnum.MEMBER,
                "Broker commission contract accepted",
                "The member #member# accepted the broker commission contract:<br>Start date: #start_date#<br>End date: #end_date#<br>Amount: #amount#<br>#link# for more details",
                "The member #login# accepted the broker commission contract. Start date: #start_date#, end date: #end_date#, amount: #amount#"),

        COMMISSION_CONTRACT_CANCELLED(MessageSettingsCategoryEnum.MEMBER,
                "Broker commission contract cancelled",
                "The broker #broker# cancelled a broker commission contract:<br>Start date: #start_date#<br>End date: #end_date#<br>Amount: #amount#<br>#link# for more details",
                "The broker #brokerUsername# cancelled a broker commission contract. Start date: #start_date#, end date: #end_date#, amount: #amount#"),

        COMMISSION_CONTRACT_DENIED(MessageSettingsCategoryEnum.MEMBER,
                "Broker commission contract denied",
                "The member #member# denied the broker commission contract:<br>Start date: #start_date#<br>End date: #end_date#<br>Amount: #amount#<br>#link# for more details",
                "The member #login# denied the broker commission contract. Start date: #start_date#, end date: #end_date#, amount: #amount#"),

        LOW_UNITS(MessageSettingsCategoryEnum.MEMBER,
                "Low units notification",
                "You are reaching the credit limit",
                "You are reaching the credit limit of #credit_limit#"),

        AD_EXPIRATION(MessageSettingsCategoryEnum.MEMBER,
                "Your ad has expired",
                "Your ad with title \"#title#\" has expired.<br>#link# for more details",
                "Your ad expired. Title: #title#"),

        AD_INTEREST(MessageSettingsCategoryEnum.MEMBER,
                "New ad published matching your interests",
                "New ad published matching your interests, with title \"#title#\".<br>#link# for more details",
                "New ad published matching your interests, with title: #title#"),

        INVOICE_RECEIVED(MessageSettingsCategoryEnum.MEMBER,
                "You have received an invoice",
                "You have received an invoice from #member# (#login#).<br>#link# for more details",
                "You have received an invoice from #login#"),

        INVOICE_ACCEPTED(MessageSettingsCategoryEnum.MEMBER,
                "Your invoice was accepted",
                "#member# (#login#) has accepted your invoice with:<br>Date: #date#<br>Amount: #amount#<br>Description: #description#.<br>#link# for more details",
                "#login# has accepted your invoice with date: #date#, amount: #amount#"),

        INVOICE_CANCELLED(MessageSettingsCategoryEnum.MEMBER,
                "Your invoice was canceled",
                "#member# (#login#) has canceled the invoice with:<br>Date: #date#<br>Amount: #amount#<br>Description: #description#.<br>#link# for more details",
                "#login# has canceled the invoice with date: #date#, amount: #amount#"),

        INVOICE_DENIED(MessageSettingsCategoryEnum.MEMBER,
                "Your invoice was denied",
                "#member# (#login#) has denied your invoice with:<br>Date: #date#<br>Amount: #amount#<br>Description: #description#.<br>#link# for more details",
                "#login# has denied your invoice with date: #date#, amount: #amount#"),

        RECEIVED_INVOICE_EXPIRED(MessageSettingsCategoryEnum.MEMBER,
                "An invoice you received has expired",
                "An invoice you received from #member# (#login#) of #amount# has expired.<br>#link# for more details",
                "An invoice you received from #login# of #amount# has expired"),

        SENT_INVOICE_EXPIRED(MessageSettingsCategoryEnum.MEMBER,
                "An invoice you sent has expired",
                "An invoice you sent to #member# (#login#) of #amount# has expired.<br>#link# for more details",
                "An invoice you sent to #login# of #amount# has expired"),

        LOAN_EXPIRATION(MessageSettingsCategoryEnum.MEMBER,
                "Loan has expired",
                "A loan granted at #grant_date# (#amount#) has expired.<br>#link# for more details",
                "A loan granted at #grant_date# (#amount#) has expired"),

        LOAN_GRANTED(MessageSettingsCategoryEnum.MEMBER,
                "Loan granted",
                "A loan of #amount# has been granted.<br>#link# for more details",
                "A loan of #amount# has been granted"),

        PAYMENT_RECEIVED(MessageSettingsCategoryEnum.MEMBER,
                "Payment received",
                "A payment of #amount# was received from #member# (#login#). <br>#link# for more details",
                "A payment of #amount# was received from #login#. Your new balance is #balance#"),

        PENDING_PAYMENT_RECEIVED(MessageSettingsCategoryEnum.MEMBER,
                "A payment awaiting authorization was received",
                "A payment awaiting authorization of #amount# was received from #member# (#login#).<br>#link# for more details",
                "A payment awaiting authorization of #amount# was received from #login#"),

        NEW_PENDING_PAYMENT_BY_RECEIVER(MessageSettingsCategoryEnum.MEMBER,
                "You have received a payment you should authorize",
                "A payment of #amount# was received from #member# (#login#), and you should authorize it.<br>#link# for more details",
                "A payment of #amount# was received from #login#, and you should authorize it"),

        NEW_PENDING_PAYMENT_BY_PAYER(MessageSettingsCategoryEnum.MEMBER,
                "A payment you have performed is now awaiting your authorization",
                "A payment of #amount# that you performed to #member# (#login#) now needs your authorization to be processed.<br>#link# for more details",
                "A payment of #amount# that you performed to #login# now needs your authorization to be processed"),

        NEW_PENDING_PAYMENT_BY_BROKER(MessageSettingsCategoryEnum.MEMBER,
                "Payment authorization request",
                "A payment of #amount# was made by #member# (#login#), and you should authorize it.<br>#link# for more details",
                "A payment of #amount# was made by #login#, and you should authorize it"),

        PENDING_PAYMENT_AUTHORIZED(MessageSettingsCategoryEnum.MEMBER,
                "A pending payment has been authorized",
                "A payment submitted at #date# of #amount# was authorized.<br>#link# for more details",
                "A payment submitted at #date# of #amount# was authorized"),

        PENDING_PAYMENT_DENIED(MessageSettingsCategoryEnum.MEMBER,
                "A pending payment has been denied",
                "A payment submitted at #date# of #amount# was denied.<br>#link# for more details",
                "A payment submitted at #date# of #amount# was denied"),

        PENDING_PAYMENT_CANCELED(MessageSettingsCategoryEnum.MEMBER,
                "A pending payment has been canceled",
                "A payment submitted at #date# of #amount# was canceled.<br>#link# for more details",
                "A payment submitted at #date# of #amount# was canceled"),

        SCHEDULED_PAYMENTS_CANCELLED_TO_OTHER(MessageSettingsCategoryEnum.MEMBER,
                "Scheduled payments cancelled",
                "All scheduled payments related to #member# (#login#) were cancelled because that member can not make/accept that kind of payments.<br>#link# for more details",
                "All scheduled payments related to #login# were cancelled"),

        SCHEDULED_PAYMENTS_CANCELLED(MessageSettingsCategoryEnum.MEMBER,
                "Scheduled payments cancelled",
                "All of yours scheduled payments were cancelled because you do not have the needed accounts: #accounts#.<br>#link# for more details",
                "All of yours scheduled payments were cancelled"),

        SCHEDULED_PAYMENT_PROCESSED(MessageSettingsCategoryEnum.MEMBER,
                "Scheduled payment successful",
                "A scheduled payment to #member# (#login#) of #amount# was successfully processed.<br>#link# for more details",
                "A scheduled payment to #login# of #amount# was successfully processed"),

        SCHEDULED_PAYMENT_FAILED_TO_PAYER(MessageSettingsCategoryEnum.MEMBER,
                "Scheduled payment has failed",
                "A scheduled payment to #member# (#login#) of #amount# has failed.<br>#link# for more details",
                "A scheduled payment to #login# of #amount# has failed"),

        SCHEDULED_PAYMENT_FAILED_TO_PAYEE(MessageSettingsCategoryEnum.MEMBER,
                "An incoming scheduled payment has failed",
                "A scheduled payment from #member# (#login#) of #amount# has failed.<br>#link# for more details",
                "A scheduled payment from #login# of #amount# has failed"),

        EXTERNAL_CHANNEL_PAYMENT_PERFORMED(MessageSettingsCategoryEnum.MEMBER,
                "External channel payment performed",
                "You performed payment of #amount# to #member# (#login#) by #channel#.<br>#link# for more details",
                "You performed payment of #amount# to #login# by #channel#"),

        EXTERNAL_CHANNEL_PAYMENT_REQUEST_EXPIRED_PAYER(MessageSettingsCategoryEnum.MEMBER,
                "External channel payment request expired",
                "The #channel# payment of #amount# requested by #to_member# (#to_login#) has expired.<br>#link# for more details",
                "The #channel# payment of #amount# requested by #to_login# has expired"),

        EXTERNAL_CHANNEL_PAYMENT_REQUEST_EXPIRED_RECEIVER(MessageSettingsCategoryEnum.MEMBER,
                "External channel payment request expired",
                "The #channel# payment of #amount# you requested to #from_member# (#from_login#) has expired.<br>#link# for more details",
                "The #channel# payment of #amount# you requested to #from_login# has expired"),

        REFERENCE_RECEIVED(MessageSettingsCategoryEnum.MEMBER,
                "Reference received",
                "A reference was received from #member# (#login#).<br>#link# for more details",
                "A reference was received from #login#."),

        TRANSACTION_FEEDBACK_RECEIVED(MessageSettingsCategoryEnum.MEMBER,
                "Transaction feedback received",
                "A transaction feedback was received from #member# (#login#) for a payment of #amount# at #date#.<br>This feedback may be replied until #limit#<br>#link# for more details",
                "A transaction feedback was received from #login# for a payment of #amount# at #date#. This feedback may be replied until #limit#."),

        TRANSACTION_FEEDBACK_REPLY(MessageSettingsCategoryEnum.MEMBER,
                "Transaction feedback was replied",
                "A transaction feedback from #member# (#login#) for a payment of #amount# at #date# received a reply.<br>#link# for more details",
                "A transaction feedback from #login# for a payment of #amount# at #date# received a reply"),

        TRANSACTION_FEEDBACK_ADMIN_COMMENTS(MessageSettingsCategoryEnum.MEMBER,
                "Transaction feedback was commented by the administration",
                "A transaction feedback for a transaction with #member# (#login#) of #amount# at #date#.<br>#link# for more details",
                "A transaction feedback for a transaction with #login# of #amount# at #date#"),

        TRANSACTION_FEEDBACK_REQUEST(MessageSettingsCategoryEnum.MEMBER,
                "Transaction feedback",
                "Please, give a transaction feedback for the payment to #member# (#login#) of #amount# at #date#.<br>You can give your feedback until #limit#.<br>#link# for more details",
                "Please, give a transaction feedback for the payment to #login# of #amount# at #date#. You can give your feedback until #limit#"),

        ACCOUNT_FEE_RECEIVED(MessageSettingsCategoryEnum.MEMBER,
                "#account_fee# received",
                "#account_fee# of #amount# was received.<br>#link# for more details",
                "#account_fee# of #amount# was received"),

        MAX_TRANSACTION_PASSWORD_TRIES(MessageSettingsCategoryEnum.MEMBER,
                "Maximum number of wrong transaction password tries reached",
                "You have reached the maximum number of wrong transaction password tries.<br>Now, your transaction password is blocked and you must contact the administration.",
                "Maximum number of wrong transaction password tries. Your transaction password is blocked. Contact the administration."),

        CERTIFICATION_ISSUED(MessageSettingsCategoryEnum.MEMBER,
                "A new certification was issued",
                "A new certification of #amount# was issued by #issuer_member# (#issuer_login#).<br>#link# for more details",
                "A new certification of #amount# was issued by #issuer_login#"),

        CERTIFICATION_STATUS_CHANGED(MessageSettingsCategoryEnum.MEMBER,
                "The status of a certification has changed",
                "The status of the certification of #amount# issued by #issuer_member# (#issuer_login#) has changed to \"#status#\".<br>#link# for more details",
                "The status of the certification of #amount# issued by #issuer_login# has changed to \"#status#\"."),

        EXPIRED_CERTIFICATION(MessageSettingsCategoryEnum.MEMBER,
                "A certification has expired",
                "The certification of #amount# issued to #buyer_member# (#buyer_login#) has expired.<br>#link# for more details",
                "The certification of #amount# issued to #buyer_login# has expired."),

        EXPIRED_GUARANTEE(MessageSettingsCategoryEnum.MEMBER,
                "A guarantee has expired",
                "The guarantee of #amount# issued to #buyer_member# (#buyer_login#) has expired.<br>#link# for more details",
                "The guarantee of #amount# issued to #buyer_login# has expired."),

        BUYER_ONLY_GUARANTEE_STATUS_CHANGED(MessageSettingsCategoryEnum.MEMBER,
                "The status of a guarantee has changed",
                "The status of the guarantee of #amount# issued by #issuer_member# (#issuer_login#) has changed to \"#status#\".<br>#link# for more details",
                "The status of the guarantee of #amount# issued by #issuer_login# has changed to \"#status#\"."),

        GUARANTEE_STATUS_CHANGED(MessageSettingsCategoryEnum.MEMBER,
                "The status of a guarantee has changed",
                "The status of the guarantee of #amount# has changed to \"#status#\".<br>Issuer: #issuer_member# (#issuer_login#)<br>Buyer: #buyer_member# (#buyer_login#)<br>Seller: #seller_member# (#seller_login#)<br>#link# for more details",
                "The status of the guarantee of #amount# has changed to \"#status#\". issuer: #issuer_login#, buyer: #buyer_login#, seller: #seller_login#"),

        PENDING_BUYER_ONLY_GUARANTEE_ISSUER(MessageSettingsCategoryEnum.MEMBER,
                "New pending guarantee awaiting authorization",
                "A new guarantee was requested and is awaiting your authorization.<br>Buyer: #buyer_member# (#buyer_login#)<br>Amount: #amount#<br>#link# for more details",
                "A new guarantee was requested and is awaiting your authorization. Buyer: #buyer_login#, amount: #amount#"),

        PENDING_GUARANTEE_ISSUER(MessageSettingsCategoryEnum.MEMBER,
                "New pending guarantee awaiting authorization",
                "A new guarantee was requested and is awaiting your authorization.<br>Buyer: #buyer_member# (#buyer_login#)<br>Seller: #seller_member# (#seller_login#)<br>Amount: #amount#<br>#link# for more details",
                "A new guarantee was requested and is awaiting your authorization. Buyer: #buyer_login#, seller: #seller_login#, amount: #amount#"),

        PAYMENT_OBLIGATION_REGISTERED(MessageSettingsCategoryEnum.MEMBER,
                "A new payment obligation was issued",
                "A new payment obligation of #amount# was issued by #buyer_member# (#buyer_login#).<br>#link# for more details",
                "A new payment obligation of #amount# was issued by #buyer_login#"),

        PAYMENT_OBLIGATION_REJECTED(MessageSettingsCategoryEnum.MEMBER,
                "A payment obligation was rejected",
                "The payment obligation of #amount# was rejected by #seller_member# (#seller_login#). <br>#link# for more details",
                "The payment obligation of #amount# was rejected by #seller_login#."),

        ADMIN_PENDING_BUYER_ONLY_GUARANTEE(MessageSettingsCategoryEnum.ADMIN,
                "New pending guarantee awaiting authorization",
                "A new guarantee was requested and is awaiting authorization.<br>Issuer: #issuer_member# (#issuer_login#)<br>Buyer: #buyer_member# (#buyer_login#)<br>Amount: #amount#<br>#link# for more details"),

        ADMIN_PENDING_GUARANTEE(MessageSettingsCategoryEnum.ADMIN,
                "New pending guarantee awaiting authorization",
                "A new guarantee was requested and is awaiting authorization.<br>Issuer: #issuer_member# (#issuer_login#)<br>Buyer: #buyer_member# (#buyer_login#)<br>Seller: #seller_member# (#seller_login#)<br>Amount: #amount#<br>#link# for more details"),

        ADMIN_APPLICATION_ERROR(MessageSettingsCategoryEnum.ADMIN,
                "Application error",
                "There was an application error on '#path#'.<br>#link# for more details"),

        ADMIN_PAYMENT_FROM_SYSTEM_TO_MEMBER(MessageSettingsCategoryEnum.ADMIN,
                "New payment from #from_account#",
                "#payment_type# of #amount# to #member# (#login#).<br>#link# for more details"),

        ADMIN_PAYMENT_FROM_MEMBER_TO_SYSTEM(MessageSettingsCategoryEnum.ADMIN,
                "New payment to #to_account#",
                "#payment_type# of #amount# from #member# (#login#).<br>#link# for more details"),

        ADMIN_PAYMENT_FROM_SYSTEM_TO_SYSTEM(MessageSettingsCategoryEnum.ADMIN,
                "New payment between #from_account# and #to_account#",
                "#payment_type# of #amount#.<br>#link# for more details"),

        ADMIN_NEW_PENDING_PAYMENT(MessageSettingsCategoryEnum.ADMIN,
                "Payment authorization request",
                "A payment of #amount# was made by #member# (#login#), and you should authorize it.<br>#link# for more details"),

        ADMIN_NEW_MEMBER(MessageSettingsCategoryEnum.ADMIN,
                "New member in #group#",
                "#member# has registered in #group#.<br>#link# for more details"),

        ADMIN_SYSTEM_ALERT(MessageSettingsCategoryEnum.ADMIN,
                "New system alert",
                null),

        ADMIN_MEMBER_ALERT(MessageSettingsCategoryEnum.ADMIN,
                "New member alert for #member# (#login#)",
                null),

        ADMIN_SYSTEM_INVOICE(MessageSettingsCategoryEnum.ADMIN,
                "New system invoice",
                "New system invoice of #amount# from #member# (#login#).<br>#link# for more details"),

        BROKER_REMOVED_REMARK_COMMENTS(MessageSettingsCategoryEnum.GENERAL,
                "The broker #member# (#login#) has been removed"),

        MESSAGE_MAIL_SUBJECT_PREFIX(MessageSettingsCategoryEnum.GENERAL,
                "#system_name#: "),

        MESSAGE_MAIL_SUFFIX_PLAIN(MessageSettingsCategoryEnum.GENERAL,
                "Please, do not reply this mail directly. Login to your #system_name# account to reply your messages"),

        MESSAGE_MAIL_SUFFIX_HTML(MessageSettingsCategoryEnum.GENERAL,
                "<span style='font-size:smaller;color:SlateGray;'>Please, do not reply to this mail directly. Login to your #system_name# account to reply your messages</span>"),

        SMS_MESSAGE_PREFIX(MessageSettingsCategoryEnum.GENERAL,
                "#system_name#: ");

        private String                      defaultSubject;
        private String                      defaultMessage;
        private String                      defaultSms;
        private MessageSettingsCategoryEnum category;

        private MessageSettingsEnum(final MessageSettingsCategoryEnum category, final String message) {
            this(category, null, message, null);
        }

        private MessageSettingsEnum(final MessageSettingsCategoryEnum category, final String subject, final String message) {
            this(category, subject, message, null);
        }

        private MessageSettingsEnum(final MessageSettingsCategoryEnum category, final String subject, final String message, final String sms) {
            defaultSubject = subject;
            defaultMessage = message;
            defaultSms = sms;
            this.category = category;
        }

        public String defaultMessage() {
            return defaultMessage;
        }

        public String defaultSms() {
            return defaultSms;
        }

        public String defaultSubject() {
            return defaultSubject;
        }

        public MessageSettingsCategoryEnum getCategory() {
            return category;
        }

        public String messageSettingKey() {
            return defaultMessage == null ? null : convertToSettingKey(defaultSms == null && defaultSubject == null ? null : "body");
        }

        public String messageSettingName() {
            return convertToSettingName(defaultSms == null && defaultSubject == null ? null : "Message");
        }

        public String settingName() {
            return convertToSettingName(null);
        }

        public String smsSettingKey() {
            return defaultSms == null ? null : convertToSettingKey("sms");
        }

        public String smsSettingName() {
            return defaultSms == null ? null : convertToSettingName("Sms");
        }

        public String subjectSettingKey() {
            return defaultSubject == null ? null : convertToSettingKey("subject");
        }

        public String subjectSettingName() {
            return defaultSubject == null ? null : convertToSettingName("Subject");
        }

        /**
         * Converts this enum name to the form: settings.message.foo-bar.suffix
         */
        private String convertToSettingKey(final String suffix) {
            final StringBuilder result = new StringBuilder("settings.message.");
            result.append(StringUtils.join(StringUtils.split(name(), "_"), "-").toLowerCase());
            if (suffix != null) {
                result.append(".").append(suffix);
            }
            return result.toString();
        }

        /**
         * Converts this enum name to the form: fooBarSuffix
         */
        private String convertToSettingName(final String suffix) {
            final StringBuilder result = new StringBuilder();
            for (final String str : StringUtils.split(name(), "_")) {
                result.append(StringUtils.capitalize(str.toLowerCase()));
            }
            if (suffix != null) {
                result.append(suffix);
            }
            return StringUtils.uncapitalize(result.toString());
        }
    }

    private static final long serialVersionUID                                    = 2371528369677324709L;

    private String            loginBlockedSubject                                 = MessageSettingsEnum.LOGIN_BLOCKED.defaultSubject();
    private String            loginBlockedMessage                                 = MessageSettingsEnum.LOGIN_BLOCKED.defaultMessage();
    private String            loginBlockedSms                                     = MessageSettingsEnum.LOGIN_BLOCKED.defaultSms();

    private String            pinBlockedSubject                                   = MessageSettingsEnum.PIN_BLOCKED.defaultSubject();
    private String            pinBlockedMessage                                   = MessageSettingsEnum.PIN_BLOCKED.defaultMessage();
    private String            pinBlockedSms                                       = MessageSettingsEnum.PIN_BLOCKED.defaultSms();

    private String            posPinBlockedSubject                                = MessageSettingsEnum.POS_PIN_BLOCKED.defaultSubject();
    private String            posPinBlockedMessage                                = MessageSettingsEnum.POS_PIN_BLOCKED.defaultMessage();
    private String            posPinBlockedSms                                    = MessageSettingsEnum.POS_PIN_BLOCKED.defaultSms();

    private String            cardSecurityCodeBlockedSubject                      = MessageSettingsEnum.CARD_SECURITY_CODE_BLOCKED.defaultSubject();
    private String            cardSecurityCodeBlockedMessage                      = MessageSettingsEnum.CARD_SECURITY_CODE_BLOCKED.defaultMessage();
    private String            cardSecurityCodeBlockedSms                          = MessageSettingsEnum.CARD_SECURITY_CODE_BLOCKED.defaultSms();

    private String            brokeringExpirationSubject                          = MessageSettingsEnum.BROKERING_EXPIRATION.defaultSubject();
    private String            brokeringExpirationMessage                          = MessageSettingsEnum.BROKERING_EXPIRATION.defaultMessage();
    private String            brokeringExpirationSms                              = MessageSettingsEnum.BROKERING_EXPIRATION.defaultSms();

    private String            brokeringRemovedSubject                             = MessageSettingsEnum.BROKERING_REMOVED.defaultSubject();
    private String            brokeringRemovedMessage                             = MessageSettingsEnum.BROKERING_REMOVED.defaultMessage();
    private String            brokeringRemovedSms                                 = MessageSettingsEnum.BROKERING_REMOVED.defaultSms();

    private String            removedFromBrokerGroupSubject                       = MessageSettingsEnum.REMOVED_FROM_BROKER_GROUP.defaultSubject();
    private String            removedFromBrokerGroupMessage                       = MessageSettingsEnum.REMOVED_FROM_BROKER_GROUP.defaultMessage();
    private String            removedFromBrokerGroupSms                           = MessageSettingsEnum.REMOVED_FROM_BROKER_GROUP.defaultSms();

    private String            newCommissionContractSubject                        = MessageSettingsEnum.NEW_COMMISSION_CONTRACT.defaultSubject();
    private String            newCommissionContractMessage                        = MessageSettingsEnum.NEW_COMMISSION_CONTRACT.defaultMessage();
    private String            newCommissionContractSms                            = MessageSettingsEnum.NEW_COMMISSION_CONTRACT.defaultSms();

    private String            commissionContractAcceptedSubject                   = MessageSettingsEnum.COMMISSION_CONTRACT_ACCEPTED.defaultSubject();
    private String            commissionContractAcceptedMessage                   = MessageSettingsEnum.COMMISSION_CONTRACT_ACCEPTED.defaultMessage();
    private String            commissionContractAcceptedSms                       = MessageSettingsEnum.COMMISSION_CONTRACT_ACCEPTED.defaultSms();

    private String            commissionContractCancelledSubject                  = MessageSettingsEnum.COMMISSION_CONTRACT_CANCELLED.defaultSubject();
    private String            commissionContractCancelledMessage                  = MessageSettingsEnum.COMMISSION_CONTRACT_CANCELLED.defaultMessage();
    private String            commissionContractCancelledSms                      = MessageSettingsEnum.COMMISSION_CONTRACT_CANCELLED.defaultSms();

    private String            commissionContractDeniedSubject                     = MessageSettingsEnum.COMMISSION_CONTRACT_DENIED.defaultSubject();
    private String            commissionContractDeniedMessage                     = MessageSettingsEnum.COMMISSION_CONTRACT_DENIED.defaultMessage();
    private String            commissionContractDeniedSms                         = MessageSettingsEnum.COMMISSION_CONTRACT_DENIED.defaultSms();

    private String            lowUnitsSubject                                     = MessageSettingsEnum.LOW_UNITS.defaultSubject();
    private String            lowUnitsMessage                                     = MessageSettingsEnum.LOW_UNITS.defaultMessage();
    private String            lowUnitsSms                                         = MessageSettingsEnum.LOW_UNITS.defaultSms();

    private String            adExpirationSubject                                 = MessageSettingsEnum.AD_EXPIRATION.defaultSubject();
    private String            adExpirationMessage                                 = MessageSettingsEnum.AD_EXPIRATION.defaultMessage();
    private String            adExpirationSms                                     = MessageSettingsEnum.AD_EXPIRATION.defaultSms();

    private String            adInterestSubject                                   = MessageSettingsEnum.AD_INTEREST.defaultSubject();
    private String            adInterestMessage                                   = MessageSettingsEnum.AD_INTEREST.defaultMessage();
    private String            adInterestSms                                       = MessageSettingsEnum.AD_INTEREST.defaultSms();

    private String            invoiceReceivedSubject                              = MessageSettingsEnum.INVOICE_RECEIVED.defaultSubject();
    private String            invoiceReceivedMessage                              = MessageSettingsEnum.INVOICE_RECEIVED.defaultMessage();
    private String            invoiceReceivedSms                                  = MessageSettingsEnum.INVOICE_RECEIVED.defaultSms();

    private String            invoiceAcceptedSubject                              = MessageSettingsEnum.INVOICE_ACCEPTED.defaultSubject();
    private String            invoiceAcceptedMessage                              = MessageSettingsEnum.INVOICE_ACCEPTED.defaultMessage();
    private String            invoiceAcceptedSms                                  = MessageSettingsEnum.INVOICE_ACCEPTED.defaultSms();

    private String            invoiceCancelledSubject                             = MessageSettingsEnum.INVOICE_CANCELLED.defaultSubject();
    private String            invoiceCancelledMessage                             = MessageSettingsEnum.INVOICE_CANCELLED.defaultMessage();
    private String            invoiceCancelledSms                                 = MessageSettingsEnum.INVOICE_CANCELLED.defaultSms();

    private String            invoiceDeniedSubject                                = MessageSettingsEnum.INVOICE_DENIED.defaultSubject();
    private String            invoiceDeniedMessage                                = MessageSettingsEnum.INVOICE_DENIED.defaultMessage();
    private String            invoiceDeniedSms                                    = MessageSettingsEnum.INVOICE_DENIED.defaultSms();

    private String            receivedInvoiceExpiredSubject                       = MessageSettingsEnum.RECEIVED_INVOICE_EXPIRED.defaultSubject();
    private String            receivedInvoiceExpiredMessage                       = MessageSettingsEnum.RECEIVED_INVOICE_EXPIRED.defaultMessage();
    private String            receivedInvoiceExpiredSms                           = MessageSettingsEnum.RECEIVED_INVOICE_EXPIRED.defaultSms();

    private String            sentInvoiceExpiredSubject                           = MessageSettingsEnum.SENT_INVOICE_EXPIRED.defaultSubject();
    private String            sentInvoiceExpiredMessage                           = MessageSettingsEnum.SENT_INVOICE_EXPIRED.defaultMessage();
    private String            sentInvoiceExpiredSms                               = MessageSettingsEnum.SENT_INVOICE_EXPIRED.defaultSms();

    private String            loanExpirationSubject                               = MessageSettingsEnum.LOAN_EXPIRATION.defaultSubject();
    private String            loanExpirationMessage                               = MessageSettingsEnum.LOAN_EXPIRATION.defaultMessage();
    private String            loanExpirationSms                                   = MessageSettingsEnum.LOAN_EXPIRATION.defaultSms();

    private String            loanGrantedSubject                                  = MessageSettingsEnum.LOAN_GRANTED.defaultSubject();
    private String            loanGrantedMessage                                  = MessageSettingsEnum.LOAN_GRANTED.defaultMessage();
    private String            loanGrantedSms                                      = MessageSettingsEnum.LOAN_GRANTED.defaultSms();

    private String            paymentReceivedSubject                              = MessageSettingsEnum.PAYMENT_RECEIVED.defaultSubject();
    private String            paymentReceivedMessage                              = MessageSettingsEnum.PAYMENT_RECEIVED.defaultMessage();
    private String            paymentReceivedSms                                  = MessageSettingsEnum.PAYMENT_RECEIVED.defaultSms();

    private String            pendingPaymentReceivedSubject                       = MessageSettingsEnum.PENDING_PAYMENT_RECEIVED.defaultSubject();
    private String            pendingPaymentReceivedMessage                       = MessageSettingsEnum.PENDING_PAYMENT_RECEIVED.defaultMessage();
    private String            pendingPaymentReceivedSms                           = MessageSettingsEnum.PENDING_PAYMENT_RECEIVED.defaultSms();

    private String            newPendingPaymentByReceiverSubject                  = MessageSettingsEnum.NEW_PENDING_PAYMENT_BY_RECEIVER.defaultSubject();
    private String            newPendingPaymentByReceiverMessage                  = MessageSettingsEnum.NEW_PENDING_PAYMENT_BY_RECEIVER.defaultMessage();
    private String            newPendingPaymentByReceiverSms                      = MessageSettingsEnum.NEW_PENDING_PAYMENT_BY_RECEIVER.defaultSms();

    private String            newPendingPaymentByPayerSubject                     = MessageSettingsEnum.NEW_PENDING_PAYMENT_BY_PAYER.defaultSubject();
    private String            newPendingPaymentByPayerMessage                     = MessageSettingsEnum.NEW_PENDING_PAYMENT_BY_PAYER.defaultMessage();
    private String            newPendingPaymentByPayerSms                         = MessageSettingsEnum.NEW_PENDING_PAYMENT_BY_PAYER.defaultSms();

    private String            newPendingPaymentByBrokerSubject                    = MessageSettingsEnum.NEW_PENDING_PAYMENT_BY_BROKER.defaultSubject();
    private String            newPendingPaymentByBrokerMessage                    = MessageSettingsEnum.NEW_PENDING_PAYMENT_BY_BROKER.defaultMessage();
    private String            newPendingPaymentByBrokerSms                        = MessageSettingsEnum.NEW_PENDING_PAYMENT_BY_BROKER.defaultSms();

    private String            pendingPaymentAuthorizedSubject                     = MessageSettingsEnum.PENDING_PAYMENT_AUTHORIZED.defaultSubject();
    private String            pendingPaymentAuthorizedMessage                     = MessageSettingsEnum.PENDING_PAYMENT_AUTHORIZED.defaultMessage();
    private String            pendingPaymentAuthorizedSms                         = MessageSettingsEnum.PENDING_PAYMENT_AUTHORIZED.defaultSms();

    private String            pendingPaymentDeniedSubject                         = MessageSettingsEnum.PENDING_PAYMENT_DENIED.defaultSubject();
    private String            pendingPaymentDeniedMessage                         = MessageSettingsEnum.PENDING_PAYMENT_DENIED.defaultMessage();
    private String            pendingPaymentDeniedSms                             = MessageSettingsEnum.PENDING_PAYMENT_DENIED.defaultSms();

    private String            pendingPaymentCanceledSubject                       = MessageSettingsEnum.PENDING_PAYMENT_CANCELED.defaultSubject();
    private String            pendingPaymentCanceledMessage                       = MessageSettingsEnum.PENDING_PAYMENT_CANCELED.defaultMessage();
    private String            pendingPaymentCanceledSms                           = MessageSettingsEnum.PENDING_PAYMENT_CANCELED.defaultSms();

    private String            scheduledPaymentsCancelledToOtherSubject            = MessageSettingsEnum.SCHEDULED_PAYMENTS_CANCELLED_TO_OTHER.defaultSubject();
    private String            scheduledPaymentsCancelledToOtherMessage            = MessageSettingsEnum.SCHEDULED_PAYMENTS_CANCELLED_TO_OTHER.defaultMessage();
    private String            scheduledPaymentsCancelledToOtherSms                = MessageSettingsEnum.SCHEDULED_PAYMENTS_CANCELLED_TO_OTHER.defaultSms();

    private String            scheduledPaymentsCancelledSubject                   = MessageSettingsEnum.SCHEDULED_PAYMENTS_CANCELLED.defaultSubject();
    private String            scheduledPaymentsCancelledMessage                   = MessageSettingsEnum.SCHEDULED_PAYMENTS_CANCELLED.defaultMessage();
    private String            scheduledPaymentsCancelledSms                       = MessageSettingsEnum.SCHEDULED_PAYMENTS_CANCELLED.defaultSms();

    private String            scheduledPaymentProcessedSubject                    = MessageSettingsEnum.SCHEDULED_PAYMENT_PROCESSED.defaultSubject();
    private String            scheduledPaymentProcessedMessage                    = MessageSettingsEnum.SCHEDULED_PAYMENT_PROCESSED.defaultMessage();
    private String            scheduledPaymentProcessedSms                        = MessageSettingsEnum.SCHEDULED_PAYMENT_PROCESSED.defaultSms();

    private String            scheduledPaymentFailedToPayerSubject                = MessageSettingsEnum.SCHEDULED_PAYMENT_FAILED_TO_PAYER.defaultSubject();
    private String            scheduledPaymentFailedToPayerMessage                = MessageSettingsEnum.SCHEDULED_PAYMENT_FAILED_TO_PAYER.defaultMessage();
    private String            scheduledPaymentFailedToPayerSms                    = MessageSettingsEnum.SCHEDULED_PAYMENT_FAILED_TO_PAYER.defaultSms();

    private String            scheduledPaymentFailedToPayeeSubject                = MessageSettingsEnum.SCHEDULED_PAYMENT_FAILED_TO_PAYEE.defaultSubject();
    private String            scheduledPaymentFailedToPayeeMessage                = MessageSettingsEnum.SCHEDULED_PAYMENT_FAILED_TO_PAYEE.defaultMessage();
    private String            scheduledPaymentFailedToPayeeSms                    = MessageSettingsEnum.SCHEDULED_PAYMENT_FAILED_TO_PAYEE.defaultSms();

    private String            externalChannelPaymentPerformedSubject              = MessageSettingsEnum.EXTERNAL_CHANNEL_PAYMENT_PERFORMED.defaultSubject();
    private String            externalChannelPaymentPerformedMessage              = MessageSettingsEnum.EXTERNAL_CHANNEL_PAYMENT_PERFORMED.defaultMessage();
    private String            externalChannelPaymentPerformedSms                  = MessageSettingsEnum.EXTERNAL_CHANNEL_PAYMENT_PERFORMED.defaultSms();

    private String            externalChannelPaymentRequestExpiredPayerSubject    = MessageSettingsEnum.EXTERNAL_CHANNEL_PAYMENT_REQUEST_EXPIRED_PAYER.defaultSubject();
    private String            externalChannelPaymentRequestExpiredPayerMessage    = MessageSettingsEnum.EXTERNAL_CHANNEL_PAYMENT_REQUEST_EXPIRED_PAYER.defaultMessage();
    private String            externalChannelPaymentRequestExpiredPayerSms        = MessageSettingsEnum.EXTERNAL_CHANNEL_PAYMENT_REQUEST_EXPIRED_PAYER.defaultSms();

    private String            externalChannelPaymentRequestExpiredReceiverSubject = MessageSettingsEnum.EXTERNAL_CHANNEL_PAYMENT_REQUEST_EXPIRED_RECEIVER.defaultSubject();
    private String            externalChannelPaymentRequestExpiredReceiverMessage = MessageSettingsEnum.EXTERNAL_CHANNEL_PAYMENT_REQUEST_EXPIRED_RECEIVER.defaultMessage();
    private String            externalChannelPaymentRequestExpiredReceiverSms     = MessageSettingsEnum.EXTERNAL_CHANNEL_PAYMENT_REQUEST_EXPIRED_RECEIVER.defaultSms();

    private String            referenceReceivedSubject                            = MessageSettingsEnum.REFERENCE_RECEIVED.defaultSubject();
    private String            referenceReceivedMessage                            = MessageSettingsEnum.REFERENCE_RECEIVED.defaultMessage();
    private String            referenceReceivedSms                                = MessageSettingsEnum.REFERENCE_RECEIVED.defaultSms();

    private String            transactionFeedbackReceivedSubject                  = MessageSettingsEnum.TRANSACTION_FEEDBACK_RECEIVED.defaultSubject();
    private String            transactionFeedbackReceivedMessage                  = MessageSettingsEnum.TRANSACTION_FEEDBACK_RECEIVED.defaultMessage();
    private String            transactionFeedbackReceivedSms                      = MessageSettingsEnum.TRANSACTION_FEEDBACK_RECEIVED.defaultSms();

    private String            transactionFeedbackReplySubject                     = MessageSettingsEnum.TRANSACTION_FEEDBACK_REPLY.defaultSubject();
    private String            transactionFeedbackReplyMessage                     = MessageSettingsEnum.TRANSACTION_FEEDBACK_REPLY.defaultMessage();
    private String            transactionFeedbackReplySms                         = MessageSettingsEnum.TRANSACTION_FEEDBACK_REPLY.defaultSms();

    private String            transactionFeedbackAdminCommentsSubject             = MessageSettingsEnum.TRANSACTION_FEEDBACK_ADMIN_COMMENTS.defaultSubject();
    private String            transactionFeedbackAdminCommentsMessage             = MessageSettingsEnum.TRANSACTION_FEEDBACK_ADMIN_COMMENTS.defaultMessage();
    private String            transactionFeedbackAdminCommentsSms                 = MessageSettingsEnum.TRANSACTION_FEEDBACK_ADMIN_COMMENTS.defaultSms();

    private String            transactionFeedbackRequestSubject                   = MessageSettingsEnum.TRANSACTION_FEEDBACK_REQUEST.defaultSubject();
    private String            transactionFeedbackRequestMessage                   = MessageSettingsEnum.TRANSACTION_FEEDBACK_REQUEST.defaultMessage();
    private String            transactionFeedbackRequestSms                       = MessageSettingsEnum.TRANSACTION_FEEDBACK_REQUEST.defaultSms();

    private String            accountFeeReceivedSubject                           = MessageSettingsEnum.ACCOUNT_FEE_RECEIVED.defaultSubject();
    private String            accountFeeReceivedMessage                           = MessageSettingsEnum.ACCOUNT_FEE_RECEIVED.defaultMessage();
    private String            accountFeeReceivedSms                               = MessageSettingsEnum.ACCOUNT_FEE_RECEIVED.defaultSms();

    private String            maxTransactionPasswordTriesSubject                  = MessageSettingsEnum.MAX_TRANSACTION_PASSWORD_TRIES.defaultSubject();
    private String            maxTransactionPasswordTriesMessage                  = MessageSettingsEnum.MAX_TRANSACTION_PASSWORD_TRIES.defaultMessage();
    private String            maxTransactionPasswordTriesSms                      = MessageSettingsEnum.MAX_TRANSACTION_PASSWORD_TRIES.defaultSms();

    private String            certificationIssuedSubject                          = MessageSettingsEnum.CERTIFICATION_ISSUED.defaultSubject();
    private String            certificationIssuedMessage                          = MessageSettingsEnum.CERTIFICATION_ISSUED.defaultMessage();
    private String            certificationIssuedSms                              = MessageSettingsEnum.CERTIFICATION_ISSUED.defaultSms();

    private String            certificationStatusChangedSubject                   = MessageSettingsEnum.CERTIFICATION_STATUS_CHANGED.defaultSubject();
    private String            certificationStatusChangedMessage                   = MessageSettingsEnum.CERTIFICATION_STATUS_CHANGED.defaultMessage();
    private String            certificationStatusChangedSms                       = MessageSettingsEnum.CERTIFICATION_STATUS_CHANGED.defaultSms();

    private String            expiredCertificationSubject                         = MessageSettingsEnum.EXPIRED_CERTIFICATION.defaultSubject();
    private String            expiredCertificationMessage                         = MessageSettingsEnum.EXPIRED_CERTIFICATION.defaultMessage();
    private String            expiredCertificationSms                             = MessageSettingsEnum.EXPIRED_CERTIFICATION.defaultSms();

    private String            expiredGuaranteeSubject                             = MessageSettingsEnum.EXPIRED_GUARANTEE.defaultSubject();
    private String            expiredGuaranteeMessage                             = MessageSettingsEnum.EXPIRED_GUARANTEE.defaultMessage();
    private String            expiredGuaranteeSms                                 = MessageSettingsEnum.EXPIRED_GUARANTEE.defaultSms();

    private String            buyerOnlyGuaranteeStatusChangedSubject              = MessageSettingsEnum.BUYER_ONLY_GUARANTEE_STATUS_CHANGED.defaultSubject();
    private String            buyerOnlyGuaranteeStatusChangedMessage              = MessageSettingsEnum.BUYER_ONLY_GUARANTEE_STATUS_CHANGED.defaultMessage();
    private String            buyerOnlyGuaranteeStatusChangedSms                  = MessageSettingsEnum.BUYER_ONLY_GUARANTEE_STATUS_CHANGED.defaultSms();

    private String            guaranteeStatusChangedSubject                       = MessageSettingsEnum.GUARANTEE_STATUS_CHANGED.defaultSubject();
    private String            guaranteeStatusChangedMessage                       = MessageSettingsEnum.GUARANTEE_STATUS_CHANGED.defaultMessage();
    private String            guaranteeStatusChangedSms                           = MessageSettingsEnum.GUARANTEE_STATUS_CHANGED.defaultSms();

    private String            pendingBuyerOnlyGuaranteeIssuerSubject              = MessageSettingsEnum.PENDING_BUYER_ONLY_GUARANTEE_ISSUER.defaultSubject();
    private String            pendingBuyerOnlyGuaranteeIssuerMessage              = MessageSettingsEnum.PENDING_BUYER_ONLY_GUARANTEE_ISSUER.defaultMessage();
    private String            pendingBuyerOnlyGuaranteeIssuerSms                  = MessageSettingsEnum.PENDING_BUYER_ONLY_GUARANTEE_ISSUER.defaultSms();

    private String            pendingGuaranteeIssuerSubject                       = MessageSettingsEnum.PENDING_GUARANTEE_ISSUER.defaultSubject();
    private String            pendingGuaranteeIssuerMessage                       = MessageSettingsEnum.PENDING_GUARANTEE_ISSUER.defaultMessage();
    private String            pendingGuaranteeIssuerSms                           = MessageSettingsEnum.PENDING_GUARANTEE_ISSUER.defaultSms();

    private String            paymentObligationRegisteredSubject                  = MessageSettingsEnum.PAYMENT_OBLIGATION_REGISTERED.defaultSubject();
    private String            paymentObligationRegisteredMessage                  = MessageSettingsEnum.PAYMENT_OBLIGATION_REGISTERED.defaultMessage();
    private String            paymentObligationRegisteredSms                      = MessageSettingsEnum.PAYMENT_OBLIGATION_REGISTERED.defaultSms();

    private String            paymentObligationRejectedSubject                    = MessageSettingsEnum.PAYMENT_OBLIGATION_REJECTED.defaultSubject();
    private String            paymentObligationRejectedMessage                    = MessageSettingsEnum.PAYMENT_OBLIGATION_REJECTED.defaultMessage();
    private String            paymentObligationRejectedSms                        = MessageSettingsEnum.PAYMENT_OBLIGATION_REJECTED.defaultSms();

    private String            adminPendingBuyerOnlyGuaranteeSubject               = MessageSettingsEnum.ADMIN_PENDING_BUYER_ONLY_GUARANTEE.defaultSubject();
    private String            adminPendingBuyerOnlyGuaranteeMessage               = MessageSettingsEnum.ADMIN_PENDING_BUYER_ONLY_GUARANTEE.defaultMessage();

    private String            adminPendingGuaranteeSubject                        = MessageSettingsEnum.ADMIN_PENDING_GUARANTEE.defaultSubject();
    private String            adminPendingGuaranteeMessage                        = MessageSettingsEnum.ADMIN_PENDING_GUARANTEE.defaultMessage();

    private String            adminApplicationErrorSubject                        = MessageSettingsEnum.ADMIN_APPLICATION_ERROR.defaultSubject();
    private String            adminApplicationErrorMessage                        = MessageSettingsEnum.ADMIN_APPLICATION_ERROR.defaultMessage();

    private String            adminPaymentFromSystemToMemberSubject               = MessageSettingsEnum.ADMIN_PAYMENT_FROM_SYSTEM_TO_MEMBER.defaultSubject();
    private String            adminPaymentFromSystemToMemberMessage               = MessageSettingsEnum.ADMIN_PAYMENT_FROM_SYSTEM_TO_MEMBER.defaultMessage();

    private String            adminPaymentFromMemberToSystemSubject               = MessageSettingsEnum.ADMIN_PAYMENT_FROM_MEMBER_TO_SYSTEM.defaultSubject();
    private String            adminPaymentFromMemberToSystemMessage               = MessageSettingsEnum.ADMIN_PAYMENT_FROM_MEMBER_TO_SYSTEM.defaultMessage();

    private String            adminPaymentFromSystemToSystemSubject               = MessageSettingsEnum.ADMIN_PAYMENT_FROM_SYSTEM_TO_SYSTEM.defaultSubject();
    private String            adminPaymentFromSystemToSystemMessage               = MessageSettingsEnum.ADMIN_PAYMENT_FROM_SYSTEM_TO_SYSTEM.defaultMessage();

    private String            adminNewPendingPaymentSubject                       = MessageSettingsEnum.ADMIN_NEW_PENDING_PAYMENT.defaultSubject();
    private String            adminNewPendingPaymentMessage                       = MessageSettingsEnum.ADMIN_NEW_PENDING_PAYMENT.defaultMessage();

    private String            adminNewMemberSubject                               = MessageSettingsEnum.ADMIN_NEW_MEMBER.defaultSubject();
    private String            adminNewMemberMessage                               = MessageSettingsEnum.ADMIN_NEW_MEMBER.defaultMessage();

    private String            adminSystemAlertSubject                             = MessageSettingsEnum.ADMIN_SYSTEM_ALERT.defaultSubject();

    private String            adminMemberAlertSubject                             = MessageSettingsEnum.ADMIN_MEMBER_ALERT.defaultSubject();

    private String            adminSystemInvoiceSubject                           = MessageSettingsEnum.ADMIN_SYSTEM_INVOICE.defaultSubject();
    private String            adminSystemInvoiceMessage                           = MessageSettingsEnum.ADMIN_SYSTEM_INVOICE.defaultMessage();

    private String            brokerRemovedRemarkComments                         = MessageSettingsEnum.BROKER_REMOVED_REMARK_COMMENTS.defaultMessage();

    private String            messageMailSubjectPrefix                            = MessageSettingsEnum.MESSAGE_MAIL_SUBJECT_PREFIX.defaultMessage();

    private String            messageMailSuffixPlain                              = MessageSettingsEnum.MESSAGE_MAIL_SUFFIX_PLAIN.defaultMessage();

    private String            messageMailSuffixHtml                               = MessageSettingsEnum.MESSAGE_MAIL_SUFFIX_HTML.defaultMessage();

    private String            smsMessagePrefix                                    = MessageSettingsEnum.SMS_MESSAGE_PREFIX.defaultMessage();

    public String getAccountFeeReceivedMessage() {
        return accountFeeReceivedMessage;
    }

    public String getAccountFeeReceivedSms() {
        return accountFeeReceivedSms;
    }

    public String getAccountFeeReceivedSubject() {
        return accountFeeReceivedSubject;
    }

    public String getAdExpirationMessage() {
        return adExpirationMessage;
    }

    public String getAdExpirationSms() {
        return adExpirationSms;
    }

    public String getAdExpirationSubject() {
        return adExpirationSubject;
    }

    public String getAdInterestMessage() {
        return adInterestMessage;
    }

    public String getAdInterestSms() {
        return adInterestSms;
    }

    public String getAdInterestSubject() {
        return adInterestSubject;
    }

    public String getAdminApplicationErrorMessage() {
        return adminApplicationErrorMessage;
    }

    public String getAdminApplicationErrorSubject() {
        return adminApplicationErrorSubject;
    }

    public String getAdminMemberAlertSubject() {
        return adminMemberAlertSubject;
    }

    public String getAdminNewMemberMessage() {
        return adminNewMemberMessage;
    }

    public String getAdminNewMemberSubject() {
        return adminNewMemberSubject;
    }

    public String getAdminNewPendingPaymentMessage() {
        return adminNewPendingPaymentMessage;
    }

    public String getAdminNewPendingPaymentSubject() {
        return adminNewPendingPaymentSubject;
    }

    public String getAdminPaymentFromMemberToSystemMessage() {
        return adminPaymentFromMemberToSystemMessage;
    }

    public String getAdminPaymentFromMemberToSystemSubject() {
        return adminPaymentFromMemberToSystemSubject;
    }

    public String getAdminPaymentFromSystemToMemberMessage() {
        return adminPaymentFromSystemToMemberMessage;
    }

    public String getAdminPaymentFromSystemToMemberSubject() {
        return adminPaymentFromSystemToMemberSubject;
    }

    public String getAdminPaymentFromSystemToSystemMessage() {
        return adminPaymentFromSystemToSystemMessage;
    }

    public String getAdminPaymentFromSystemToSystemSubject() {
        return adminPaymentFromSystemToSystemSubject;
    }

    public String getAdminPendingBuyerOnlyGuaranteeMessage() {
        return adminPendingBuyerOnlyGuaranteeMessage;
    }

    public String getAdminPendingBuyerOnlyGuaranteeSubject() {
        return adminPendingBuyerOnlyGuaranteeSubject;
    }

    public String getAdminPendingGuaranteeMessage() {
        return adminPendingGuaranteeMessage;
    }

    public String getAdminPendingGuaranteeSubject() {
        return adminPendingGuaranteeSubject;
    }

    public String getAdminSystemAlertSubject() {
        return adminSystemAlertSubject;
    }

    public String getAdminSystemInvoiceMessage() {
        return adminSystemInvoiceMessage;
    }

    public String getAdminSystemInvoiceSubject() {
        return adminSystemInvoiceSubject;
    }

    public String getBrokeringExpirationMessage() {
        return brokeringExpirationMessage;
    }

    public String getBrokeringExpirationSms() {
        return brokeringExpirationSms;
    }

    public String getBrokeringExpirationSubject() {
        return brokeringExpirationSubject;
    }

    public String getBrokeringRemovedMessage() {
        return brokeringRemovedMessage;
    }

    public String getBrokeringRemovedSms() {
        return brokeringRemovedSms;
    }

    public String getBrokeringRemovedSubject() {
        return brokeringRemovedSubject;
    }

    public String getBrokerRemovedRemarkComments() {
        return brokerRemovedRemarkComments;
    }

    public String getBuyerOnlyGuaranteeStatusChangedMessage() {
        return buyerOnlyGuaranteeStatusChangedMessage;
    }

    public String getBuyerOnlyGuaranteeStatusChangedSms() {
        return buyerOnlyGuaranteeStatusChangedSms;
    }

    public String getBuyerOnlyGuaranteeStatusChangedSubject() {
        return buyerOnlyGuaranteeStatusChangedSubject;
    }

    public String getCardSecurityCodeBlockedMessage() {
        return cardSecurityCodeBlockedMessage;
    }

    public String getCardSecurityCodeBlockedSms() {
        return cardSecurityCodeBlockedSms;
    }

    public String getCardSecurityCodeBlockedSubject() {
        return cardSecurityCodeBlockedSubject;
    }

    public String getCertificationIssuedMessage() {
        return certificationIssuedMessage;
    }

    public String getCertificationIssuedSms() {
        return certificationIssuedSms;
    }

    public String getCertificationIssuedSubject() {
        return certificationIssuedSubject;
    }

    public String getCertificationStatusChangedMessage() {
        return certificationStatusChangedMessage;
    }

    public String getCertificationStatusChangedSms() {
        return certificationStatusChangedSms;
    }

    public String getCertificationStatusChangedSubject() {
        return certificationStatusChangedSubject;
    }

    public String getCommissionContractAcceptedMessage() {
        return commissionContractAcceptedMessage;
    }

    public String getCommissionContractAcceptedSms() {
        return commissionContractAcceptedSms;
    }

    public String getCommissionContractAcceptedSubject() {
        return commissionContractAcceptedSubject;
    }

    public String getCommissionContractCancelledMessage() {
        return commissionContractCancelledMessage;
    }

    public String getCommissionContractCancelledSms() {
        return commissionContractCancelledSms;
    }

    public String getCommissionContractCancelledSubject() {
        return commissionContractCancelledSubject;
    }

    public String getCommissionContractDeniedMessage() {
        return commissionContractDeniedMessage;
    }

    public String getCommissionContractDeniedSms() {
        return commissionContractDeniedSms;
    }

    public String getCommissionContractDeniedSubject() {
        return commissionContractDeniedSubject;
    }

    public String getExpiredCertificationMessage() {
        return expiredCertificationMessage;
    }

    public String getExpiredCertificationSms() {
        return expiredCertificationSms;
    }

    public String getExpiredCertificationSubject() {
        return expiredCertificationSubject;
    }

    public String getExpiredGuaranteeMessage() {
        return expiredGuaranteeMessage;
    }

    public String getExpiredGuaranteeSms() {
        return expiredGuaranteeSms;
    }

    public String getExpiredGuaranteeSubject() {
        return expiredGuaranteeSubject;
    }

    public String getExternalChannelPaymentPerformedMessage() {
        return externalChannelPaymentPerformedMessage;
    }

    public String getExternalChannelPaymentPerformedSms() {
        return externalChannelPaymentPerformedSms;
    }

    public String getExternalChannelPaymentPerformedSubject() {
        return externalChannelPaymentPerformedSubject;
    }

    public String getExternalChannelPaymentRequestExpiredPayerMessage() {
        return externalChannelPaymentRequestExpiredPayerMessage;
    }

    public String getExternalChannelPaymentRequestExpiredPayerSms() {
        return externalChannelPaymentRequestExpiredPayerSms;
    }

    public String getExternalChannelPaymentRequestExpiredPayerSubject() {
        return externalChannelPaymentRequestExpiredPayerSubject;
    }

    public String getExternalChannelPaymentRequestExpiredReceiverMessage() {
        return externalChannelPaymentRequestExpiredReceiverMessage;
    }

    public String getExternalChannelPaymentRequestExpiredReceiverSms() {
        return externalChannelPaymentRequestExpiredReceiverSms;
    }

    public String getExternalChannelPaymentRequestExpiredReceiverSubject() {
        return externalChannelPaymentRequestExpiredReceiverSubject;
    }

    public String getGuaranteeStatusChangedMessage() {
        return guaranteeStatusChangedMessage;
    }

    public String getGuaranteeStatusChangedSms() {
        return guaranteeStatusChangedSms;
    }

    public String getGuaranteeStatusChangedSubject() {
        return guaranteeStatusChangedSubject;
    }

    public String getInvoiceAcceptedMessage() {
        return invoiceAcceptedMessage;
    }

    public String getInvoiceAcceptedSms() {
        return invoiceAcceptedSms;
    }

    public String getInvoiceAcceptedSubject() {
        return invoiceAcceptedSubject;
    }

    public String getInvoiceCancelledMessage() {
        return invoiceCancelledMessage;
    }

    public String getInvoiceCancelledSms() {
        return invoiceCancelledSms;
    }

    public String getInvoiceCancelledSubject() {
        return invoiceCancelledSubject;
    }

    public String getInvoiceDeniedMessage() {
        return invoiceDeniedMessage;
    }

    public String getInvoiceDeniedSms() {
        return invoiceDeniedSms;
    }

    public String getInvoiceDeniedSubject() {
        return invoiceDeniedSubject;
    }

    public String getInvoiceReceivedMessage() {
        return invoiceReceivedMessage;
    }

    public String getInvoiceReceivedSms() {
        return invoiceReceivedSms;
    }

    public String getInvoiceReceivedSubject() {
        return invoiceReceivedSubject;
    }

    public String getLoanExpirationMessage() {
        return loanExpirationMessage;
    }

    public String getLoanExpirationSms() {
        return loanExpirationSms;
    }

    public String getLoanExpirationSubject() {
        return loanExpirationSubject;
    }

    public String getLoanGrantedMessage() {
        return loanGrantedMessage;
    }

    public String getLoanGrantedSms() {
        return loanGrantedSms;
    }

    public String getLoanGrantedSubject() {
        return loanGrantedSubject;
    }

    public String getLoginBlockedMessage() {
        return loginBlockedMessage;
    }

    public String getLoginBlockedSms() {
        return loginBlockedSms;
    }

    public String getLoginBlockedSubject() {
        return loginBlockedSubject;
    }

    public String getLowUnitsMessage() {
        return lowUnitsMessage;
    }

    public String getLowUnitsSms() {
        return lowUnitsSms;
    }

    public String getLowUnitsSubject() {
        return lowUnitsSubject;
    }

    public String getMaxTransactionPasswordTriesMessage() {
        return maxTransactionPasswordTriesMessage;
    }

    public String getMaxTransactionPasswordTriesSms() {
        return maxTransactionPasswordTriesSms;
    }

    public String getMaxTransactionPasswordTriesSubject() {
        return maxTransactionPasswordTriesSubject;
    }

    public String getMessageMailSubjectPrefix() {
        return messageMailSubjectPrefix;
    }

    public String getMessageMailSuffixHtml() {
        return messageMailSuffixHtml;
    }

    public String getMessageMailSuffixPlain() {
        return messageMailSuffixPlain;
    }

    public String getNewCommissionContractMessage() {
        return newCommissionContractMessage;
    }

    public String getNewCommissionContractSms() {
        return newCommissionContractSms;
    }

    public String getNewCommissionContractSubject() {
        return newCommissionContractSubject;
    }

    public String getNewPendingPaymentByBrokerMessage() {
        return newPendingPaymentByBrokerMessage;
    }

    public String getNewPendingPaymentByBrokerSms() {
        return newPendingPaymentByBrokerSms;
    }

    public String getNewPendingPaymentByBrokerSubject() {
        return newPendingPaymentByBrokerSubject;
    }

    public String getNewPendingPaymentByPayerMessage() {
        return newPendingPaymentByPayerMessage;
    }

    public String getNewPendingPaymentByPayerSms() {
        return newPendingPaymentByPayerSms;
    }

    public String getNewPendingPaymentByPayerSubject() {
        return newPendingPaymentByPayerSubject;
    }

    public String getNewPendingPaymentByReceiverMessage() {
        return newPendingPaymentByReceiverMessage;
    }

    public String getNewPendingPaymentByReceiverSms() {
        return newPendingPaymentByReceiverSms;
    }

    public String getNewPendingPaymentByReceiverSubject() {
        return newPendingPaymentByReceiverSubject;
    }

    public String getPaymentObligationRegisteredMessage() {
        return paymentObligationRegisteredMessage;
    }

    public String getPaymentObligationRegisteredSms() {
        return paymentObligationRegisteredSms;
    }

    public String getPaymentObligationRegisteredSubject() {
        return paymentObligationRegisteredSubject;
    }

    public String getPaymentObligationRejectedMessage() {
        return paymentObligationRejectedMessage;
    }

    public String getPaymentObligationRejectedSms() {
        return paymentObligationRejectedSms;
    }

    public String getPaymentObligationRejectedSubject() {
        return paymentObligationRejectedSubject;
    }

    public String getPaymentReceivedMessage() {
        return paymentReceivedMessage;
    }

    public String getPaymentReceivedSms() {
        return paymentReceivedSms;
    }

    public String getPaymentReceivedSubject() {
        return paymentReceivedSubject;
    }

    public String getPendingBuyerOnlyGuaranteeIssuerMessage() {
        return pendingBuyerOnlyGuaranteeIssuerMessage;
    }

    public String getPendingBuyerOnlyGuaranteeIssuerSms() {
        return pendingBuyerOnlyGuaranteeIssuerSms;
    }

    public String getPendingBuyerOnlyGuaranteeIssuerSubject() {
        return pendingBuyerOnlyGuaranteeIssuerSubject;
    }

    public String getPendingGuaranteeIssuerMessage() {
        return pendingGuaranteeIssuerMessage;
    }

    public String getPendingGuaranteeIssuerSms() {
        return pendingGuaranteeIssuerSms;
    }

    public String getPendingGuaranteeIssuerSubject() {
        return pendingGuaranteeIssuerSubject;
    }

    public String getPendingPaymentAuthorizedMessage() {
        return pendingPaymentAuthorizedMessage;
    }

    public String getPendingPaymentAuthorizedSms() {
        return pendingPaymentAuthorizedSms;
    }

    public String getPendingPaymentAuthorizedSubject() {
        return pendingPaymentAuthorizedSubject;
    }

    public String getPendingPaymentCanceledMessage() {
        return pendingPaymentCanceledMessage;
    }

    public String getPendingPaymentCanceledSms() {
        return pendingPaymentCanceledSms;
    }

    public String getPendingPaymentCanceledSubject() {
        return pendingPaymentCanceledSubject;
    }

    public String getPendingPaymentDeniedMessage() {
        return pendingPaymentDeniedMessage;
    }

    public String getPendingPaymentDeniedSms() {
        return pendingPaymentDeniedSms;
    }

    public String getPendingPaymentDeniedSubject() {
        return pendingPaymentDeniedSubject;
    }

    public String getPendingPaymentReceivedMessage() {
        return pendingPaymentReceivedMessage;
    }

    public String getPendingPaymentReceivedSms() {
        return pendingPaymentReceivedSms;
    }

    public String getPendingPaymentReceivedSubject() {
        return pendingPaymentReceivedSubject;
    }

    public String getPinBlockedMessage() {
        return pinBlockedMessage;
    }

    public String getPinBlockedSms() {
        return pinBlockedSms;
    }

    public String getPinBlockedSubject() {
        return pinBlockedSubject;
    }

    public String getPosPinBlockedMessage() {
        return posPinBlockedMessage;
    }

    public String getPosPinBlockedSms() {
        return posPinBlockedSms;
    }

    public String getPosPinBlockedSubject() {
        return posPinBlockedSubject;
    }

    public String getReceivedInvoiceExpiredMessage() {
        return receivedInvoiceExpiredMessage;
    }

    public String getReceivedInvoiceExpiredSms() {
        return receivedInvoiceExpiredSms;
    }

    public String getReceivedInvoiceExpiredSubject() {
        return receivedInvoiceExpiredSubject;
    }

    public String getReferenceReceivedMessage() {
        return referenceReceivedMessage;
    }

    public String getReferenceReceivedSms() {
        return referenceReceivedSms;
    }

    public String getReferenceReceivedSubject() {
        return referenceReceivedSubject;
    }

    public String getRemovedFromBrokerGroupMessage() {
        return removedFromBrokerGroupMessage;
    }

    public String getRemovedFromBrokerGroupSms() {
        return removedFromBrokerGroupSms;
    }

    public String getRemovedFromBrokerGroupSubject() {
        return removedFromBrokerGroupSubject;
    }

    public String getScheduledPaymentFailedToPayeeMessage() {
        return scheduledPaymentFailedToPayeeMessage;
    }

    public String getScheduledPaymentFailedToPayeeSms() {
        return scheduledPaymentFailedToPayeeSms;
    }

    public String getScheduledPaymentFailedToPayeeSubject() {
        return scheduledPaymentFailedToPayeeSubject;
    }

    public String getScheduledPaymentFailedToPayerMessage() {
        return scheduledPaymentFailedToPayerMessage;
    }

    public String getScheduledPaymentFailedToPayerSms() {
        return scheduledPaymentFailedToPayerSms;
    }

    public String getScheduledPaymentFailedToPayerSubject() {
        return scheduledPaymentFailedToPayerSubject;
    }

    public String getScheduledPaymentProcessedMessage() {
        return scheduledPaymentProcessedMessage;
    }

    public String getScheduledPaymentProcessedSms() {
        return scheduledPaymentProcessedSms;
    }

    public String getScheduledPaymentProcessedSubject() {
        return scheduledPaymentProcessedSubject;
    }

    public String getScheduledPaymentsCancelledMessage() {
        return scheduledPaymentsCancelledMessage;
    }

    public String getScheduledPaymentsCancelledSms() {
        return scheduledPaymentsCancelledSms;
    }

    public String getScheduledPaymentsCancelledSubject() {
        return scheduledPaymentsCancelledSubject;
    }

    public String getScheduledPaymentsCancelledToOtherMessage() {
        return scheduledPaymentsCancelledToOtherMessage;
    }

    public String getScheduledPaymentsCancelledToOtherSms() {
        return scheduledPaymentsCancelledToOtherSms;
    }

    public String getScheduledPaymentsCancelledToOtherSubject() {
        return scheduledPaymentsCancelledToOtherSubject;
    }

    public String getSentInvoiceExpiredMessage() {
        return sentInvoiceExpiredMessage;
    }

    public String getSentInvoiceExpiredSms() {
        return sentInvoiceExpiredSms;
    }

    public String getSentInvoiceExpiredSubject() {
        return sentInvoiceExpiredSubject;
    }

    public String getSmsMessagePrefix() {
        return smsMessagePrefix;
    }

    public String getTransactionFeedbackAdminCommentsMessage() {
        return transactionFeedbackAdminCommentsMessage;
    }

    public String getTransactionFeedbackAdminCommentsSms() {
        return transactionFeedbackAdminCommentsSms;
    }

    public String getTransactionFeedbackAdminCommentsSubject() {
        return transactionFeedbackAdminCommentsSubject;
    }

    public String getTransactionFeedbackReceivedMessage() {
        return transactionFeedbackReceivedMessage;
    }

    public String getTransactionFeedbackReceivedSms() {
        return transactionFeedbackReceivedSms;
    }

    public String getTransactionFeedbackReceivedSubject() {
        return transactionFeedbackReceivedSubject;
    }

    public String getTransactionFeedbackReplyMessage() {
        return transactionFeedbackReplyMessage;
    }

    public String getTransactionFeedbackReplySms() {
        return transactionFeedbackReplySms;
    }

    public String getTransactionFeedbackReplySubject() {
        return transactionFeedbackReplySubject;
    }

    public String getTransactionFeedbackRequestMessage() {
        return transactionFeedbackRequestMessage;
    }

    public String getTransactionFeedbackRequestSms() {
        return transactionFeedbackRequestSms;
    }

    public String getTransactionFeedbackRequestSubject() {
        return transactionFeedbackRequestSubject;
    }

    public void setAccountFeeReceivedMessage(final String accountFeeReceivedMessage) {
        this.accountFeeReceivedMessage = accountFeeReceivedMessage;
    }

    public void setAccountFeeReceivedSms(final String accountFeeReceivedSms) {
        this.accountFeeReceivedSms = accountFeeReceivedSms;
    }

    public void setAccountFeeReceivedSubject(final String accountFeeReceivedSubject) {
        this.accountFeeReceivedSubject = accountFeeReceivedSubject;
    }

    public void setAdExpirationMessage(final String expirationMessage) {
        adExpirationMessage = expirationMessage;
    }

    public void setAdExpirationSms(final String adExpirationSms) {
        this.adExpirationSms = adExpirationSms;
    }

    public void setAdExpirationSubject(final String adExpirationSubject) {
        this.adExpirationSubject = adExpirationSubject;
    }

    public void setAdInterestMessage(final String adInterestMessage) {
        this.adInterestMessage = adInterestMessage;
    }

    public void setAdInterestSms(final String adInterestSms) {
        this.adInterestSms = adInterestSms;
    }

    public void setAdInterestSubject(final String adInterestSubject) {
        this.adInterestSubject = adInterestSubject;
    }

    public void setAdminApplicationErrorMessage(final String adminApplicationErrorMessage) {
        this.adminApplicationErrorMessage = adminApplicationErrorMessage;
    }

    public void setAdminApplicationErrorSubject(final String adminApplicationErrorSubject) {
        this.adminApplicationErrorSubject = adminApplicationErrorSubject;
    }

    public void setAdminMemberAlertSubject(final String adminMemberAlertSubject) {
        this.adminMemberAlertSubject = adminMemberAlertSubject;
    }

    public void setAdminNewMemberMessage(final String adminNewMemberMessage) {
        this.adminNewMemberMessage = adminNewMemberMessage;
    }

    public void setAdminNewMemberSubject(final String adminNewMemberSubject) {
        this.adminNewMemberSubject = adminNewMemberSubject;
    }

    public void setAdminNewPendingPaymentMessage(final String adminNewPendingPaymentMessage) {
        this.adminNewPendingPaymentMessage = adminNewPendingPaymentMessage;
    }

    public void setAdminNewPendingPaymentSubject(final String adminNewPendingPaymentSubject) {
        this.adminNewPendingPaymentSubject = adminNewPendingPaymentSubject;
    }

    public void setAdminPaymentFromMemberToSystemMessage(final String adminPaymentFromMemberToSystemMessage) {
        this.adminPaymentFromMemberToSystemMessage = adminPaymentFromMemberToSystemMessage;
    }

    public void setAdminPaymentFromMemberToSystemSubject(final String adminPaymentFromMemberToSystemSubject) {
        this.adminPaymentFromMemberToSystemSubject = adminPaymentFromMemberToSystemSubject;
    }

    public void setAdminPaymentFromSystemToMemberMessage(final String adminPaymentFromSystemToMemberMessage) {
        this.adminPaymentFromSystemToMemberMessage = adminPaymentFromSystemToMemberMessage;
    }

    public void setAdminPaymentFromSystemToMemberSubject(final String adminPaymentFromSystemToMemberSubject) {
        this.adminPaymentFromSystemToMemberSubject = adminPaymentFromSystemToMemberSubject;
    }

    public void setAdminPaymentFromSystemToSystemMessage(final String adminPaymentFromSystemToSystemMessage) {
        this.adminPaymentFromSystemToSystemMessage = adminPaymentFromSystemToSystemMessage;
    }

    public void setAdminPaymentFromSystemToSystemSubject(final String adminPaymentFromSystemToSystemSubject) {
        this.adminPaymentFromSystemToSystemSubject = adminPaymentFromSystemToSystemSubject;
    }

    public void setAdminPendingBuyerOnlyGuaranteeMessage(final String pendingBuyerOnlyGuaranteeAdminMessage) {
        adminPendingBuyerOnlyGuaranteeMessage = pendingBuyerOnlyGuaranteeAdminMessage;
    }

    public void setAdminPendingBuyerOnlyGuaranteeSubject(final String pendingBuyerOnlyGuaranteeAdminSubject) {
        adminPendingBuyerOnlyGuaranteeSubject = pendingBuyerOnlyGuaranteeAdminSubject;
    }

    public void setAdminPendingGuaranteeMessage(final String pendingGuaranteeAdminMessage) {
        adminPendingGuaranteeMessage = pendingGuaranteeAdminMessage;
    }

    public void setAdminPendingGuaranteeSubject(final String pendingGuaranteeAdminSubject) {
        adminPendingGuaranteeSubject = pendingGuaranteeAdminSubject;
    }

    public void setAdminSystemAlertSubject(final String adminSystemAlertSubject) {
        this.adminSystemAlertSubject = adminSystemAlertSubject;
    }

    public void setAdminSystemInvoiceMessage(final String adminSystemInvoiceMessage) {
        this.adminSystemInvoiceMessage = adminSystemInvoiceMessage;
    }

    public void setAdminSystemInvoiceSubject(final String adminSystemInvoiceSubject) {
        this.adminSystemInvoiceSubject = adminSystemInvoiceSubject;
    }

    public void setBrokeringExpirationMessage(final String brokeringExpirationMessage) {
        this.brokeringExpirationMessage = brokeringExpirationMessage;
    }

    public void setBrokeringExpirationSms(final String brokeringExpirationSms) {
        this.brokeringExpirationSms = brokeringExpirationSms;
    }

    public void setBrokeringExpirationSubject(final String brokeringExpirationSubject) {
        this.brokeringExpirationSubject = brokeringExpirationSubject;
    }

    public void setBrokeringRemovedMessage(final String brokeringRemovedMessage) {
        this.brokeringRemovedMessage = brokeringRemovedMessage;
    }

    public void setBrokeringRemovedSms(final String brokeringRemovedSms) {
        this.brokeringRemovedSms = brokeringRemovedSms;
    }

    public void setBrokeringRemovedSubject(final String brokeringRemovedSubject) {
        this.brokeringRemovedSubject = brokeringRemovedSubject;
    }

    public void setBrokerRemovedRemarkComments(final String brokerRemovedMessage) {
        brokerRemovedRemarkComments = brokerRemovedMessage;
    }

    public void setBuyerOnlyGuaranteeStatusChangedMessage(final String buyerOnlyGuaranteeStatusChangedMessage) {
        this.buyerOnlyGuaranteeStatusChangedMessage = buyerOnlyGuaranteeStatusChangedMessage;
    }

    public void setBuyerOnlyGuaranteeStatusChangedSms(final String buyerOnlyGuaranteeStatusChangedSms) {
        this.buyerOnlyGuaranteeStatusChangedSms = buyerOnlyGuaranteeStatusChangedSms;
    }

    public void setBuyerOnlyGuaranteeStatusChangedSubject(final String buyerOnlyGuaranteeStatusChangedSubject) {
        this.buyerOnlyGuaranteeStatusChangedSubject = buyerOnlyGuaranteeStatusChangedSubject;
    }

    public void setCardSecurityCodeBlockedMessage(final String cardSecurityCodeBlockedMessage) {
        this.cardSecurityCodeBlockedMessage = cardSecurityCodeBlockedMessage;
    }

    public void setCardSecurityCodeBlockedSms(final String cardSecurityCodeBlockedSms) {
        this.cardSecurityCodeBlockedSms = cardSecurityCodeBlockedSms;
    }

    public void setCardSecurityCodeBlockedSubject(final String cardSecurityCodeBlockedSubject) {
        this.cardSecurityCodeBlockedSubject = cardSecurityCodeBlockedSubject;
    }

    public void setCertificationIssuedMessage(final String certificationIssuedMessage) {
        this.certificationIssuedMessage = certificationIssuedMessage;
    }

    public void setCertificationIssuedSms(final String certificationIssuedSms) {
        this.certificationIssuedSms = certificationIssuedSms;
    }

    public void setCertificationIssuedSubject(final String certificationIssuedSubject) {
        this.certificationIssuedSubject = certificationIssuedSubject;
    }

    public void setCertificationStatusChangedMessage(final String certificationStatusChangedBuyerMessage) {
        certificationStatusChangedMessage = certificationStatusChangedBuyerMessage;
    }

    public void setCertificationStatusChangedSms(final String certificationStatusChangedSms) {
        this.certificationStatusChangedSms = certificationStatusChangedSms;
    }

    public void setCertificationStatusChangedSubject(final String certificationStatusChangedBuyerSubject) {
        certificationStatusChangedSubject = certificationStatusChangedBuyerSubject;
    }

    public void setCommissionContractAcceptedMessage(final String commissionContractAcceptedMessage) {
        this.commissionContractAcceptedMessage = commissionContractAcceptedMessage;
    }

    public void setCommissionContractAcceptedSms(final String commissionContractAcceptedSms) {
        this.commissionContractAcceptedSms = commissionContractAcceptedSms;
    }

    public void setCommissionContractAcceptedSubject(final String commissionContractAcceptedSubject) {
        this.commissionContractAcceptedSubject = commissionContractAcceptedSubject;
    }

    public void setCommissionContractCancelledMessage(final String commissionContractCancelledMessage) {
        this.commissionContractCancelledMessage = commissionContractCancelledMessage;
    }

    public void setCommissionContractCancelledSms(final String commissionContractCancelledSms) {
        this.commissionContractCancelledSms = commissionContractCancelledSms;
    }

    public void setCommissionContractCancelledSubject(final String commissionContractCancelledSubject) {
        this.commissionContractCancelledSubject = commissionContractCancelledSubject;
    }

    public void setCommissionContractDeniedMessage(final String commissionContractDeniedMessage) {
        this.commissionContractDeniedMessage = commissionContractDeniedMessage;
    }

    public void setCommissionContractDeniedSms(final String commissionContractDeniedSms) {
        this.commissionContractDeniedSms = commissionContractDeniedSms;
    }

    public void setCommissionContractDeniedSubject(final String commissionContractDeniedSubject) {
        this.commissionContractDeniedSubject = commissionContractDeniedSubject;
    }

    public void setExpiredCertificationMessage(final String certificationStatusChangedIssuerMessage) {
        expiredCertificationMessage = certificationStatusChangedIssuerMessage;
    }

    public void setExpiredCertificationSms(final String expiredCertificationSms) {
        this.expiredCertificationSms = expiredCertificationSms;
    }

    public void setExpiredCertificationSubject(final String certificationStatusChangedIssuerSubject) {
        expiredCertificationSubject = certificationStatusChangedIssuerSubject;
    }

    public void setExpiredGuaranteeMessage(final String expiredGuaranteeMessage) {
        this.expiredGuaranteeMessage = expiredGuaranteeMessage;
    }

    public void setExpiredGuaranteeSms(final String expiredGuaranteeSms) {
        this.expiredGuaranteeSms = expiredGuaranteeSms;
    }

    public void setExpiredGuaranteeSubject(final String expiredGuaranteeSubject) {
        this.expiredGuaranteeSubject = expiredGuaranteeSubject;
    }

    public void setExternalChannelPaymentPerformedMessage(final String externalChannelPaymentPerformedMessage) {
        this.externalChannelPaymentPerformedMessage = externalChannelPaymentPerformedMessage;
    }

    public void setExternalChannelPaymentPerformedSms(final String externalChannelPaymentPerformedSms) {
        this.externalChannelPaymentPerformedSms = externalChannelPaymentPerformedSms;
    }

    public void setExternalChannelPaymentPerformedSubject(final String externalChannelPaymentPerformedSubject) {
        this.externalChannelPaymentPerformedSubject = externalChannelPaymentPerformedSubject;
    }

    public void setExternalChannelPaymentRequestExpiredPayerMessage(final String externalChannelPaymentRequestExpiredPayerMessage) {
        this.externalChannelPaymentRequestExpiredPayerMessage = externalChannelPaymentRequestExpiredPayerMessage;
    }

    public void setExternalChannelPaymentRequestExpiredPayerSms(final String externalChannelPaymentRequestExpiredPayerSms) {
        this.externalChannelPaymentRequestExpiredPayerSms = externalChannelPaymentRequestExpiredPayerSms;
    }

    public void setExternalChannelPaymentRequestExpiredPayerSubject(final String externalChannelPaymentRequestExpiredPayerSubject) {
        this.externalChannelPaymentRequestExpiredPayerSubject = externalChannelPaymentRequestExpiredPayerSubject;
    }

    public void setExternalChannelPaymentRequestExpiredReceiverMessage(final String externalChannelPaymentRequestExpiredReceiverMessage) {
        this.externalChannelPaymentRequestExpiredReceiverMessage = externalChannelPaymentRequestExpiredReceiverMessage;
    }

    public void setExternalChannelPaymentRequestExpiredReceiverSms(final String externalChannelPaymentRequestExpiredReceiverSms) {
        this.externalChannelPaymentRequestExpiredReceiverSms = externalChannelPaymentRequestExpiredReceiverSms;
    }

    public void setExternalChannelPaymentRequestExpiredReceiverSubject(final String externalChannelPaymentRequestExpiredReceiverSubject) {
        this.externalChannelPaymentRequestExpiredReceiverSubject = externalChannelPaymentRequestExpiredReceiverSubject;
    }

    public void setGuaranteeStatusChangedMessage(final String guaranteeStatusChangedMessage) {
        this.guaranteeStatusChangedMessage = guaranteeStatusChangedMessage;
    }

    public void setGuaranteeStatusChangedSms(final String guaranteeStatusChangedSms) {
        this.guaranteeStatusChangedSms = guaranteeStatusChangedSms;
    }

    public void setGuaranteeStatusChangedSubject(final String guaranteeStatusChangedSubject) {
        this.guaranteeStatusChangedSubject = guaranteeStatusChangedSubject;
    }

    public void setInvoiceAcceptedMessage(final String invoiceAcceptedMessage) {
        this.invoiceAcceptedMessage = invoiceAcceptedMessage;
    }

    public void setInvoiceAcceptedSms(final String invoiceAcceptedSms) {
        this.invoiceAcceptedSms = invoiceAcceptedSms;
    }

    public void setInvoiceAcceptedSubject(final String invoiceAcceptedSubject) {
        this.invoiceAcceptedSubject = invoiceAcceptedSubject;
    }

    public void setInvoiceCancelledMessage(final String invoiceCancelledMessage) {
        this.invoiceCancelledMessage = invoiceCancelledMessage;
    }

    public void setInvoiceCancelledSms(final String invoiceCancelledSms) {
        this.invoiceCancelledSms = invoiceCancelledSms;
    }

    public void setInvoiceCancelledSubject(final String invoiceCancelledSubject) {
        this.invoiceCancelledSubject = invoiceCancelledSubject;
    }

    public void setInvoiceDeniedMessage(final String invoiceDeniedMessage) {
        this.invoiceDeniedMessage = invoiceDeniedMessage;
    }

    public void setInvoiceDeniedSms(final String invoiceDeniedSms) {
        this.invoiceDeniedSms = invoiceDeniedSms;
    }

    public void setInvoiceDeniedSubject(final String invoiceDeniedSubject) {
        this.invoiceDeniedSubject = invoiceDeniedSubject;
    }

    public void setInvoiceReceivedMessage(final String invoiceReceivedMessage) {
        this.invoiceReceivedMessage = invoiceReceivedMessage;
    }

    public void setInvoiceReceivedSms(final String invoiceReceivedSms) {
        this.invoiceReceivedSms = invoiceReceivedSms;
    }

    public void setInvoiceReceivedSubject(final String invoiceReceivedSubject) {
        this.invoiceReceivedSubject = invoiceReceivedSubject;
    }

    public void setLoanExpirationMessage(final String loanExpirationMessage) {
        this.loanExpirationMessage = loanExpirationMessage;
    }

    public void setLoanExpirationSms(final String loanExpirationSms) {
        this.loanExpirationSms = loanExpirationSms;
    }

    public void setLoanExpirationSubject(final String loanExpirationSubject) {
        this.loanExpirationSubject = loanExpirationSubject;
    }

    public void setLoanGrantedMessage(final String loanGrantedMessage) {
        this.loanGrantedMessage = loanGrantedMessage;
    }

    public void setLoanGrantedSms(final String loanGrantedSms) {
        this.loanGrantedSms = loanGrantedSms;
    }

    public void setLoanGrantedSubject(final String loanGrantedSubject) {
        this.loanGrantedSubject = loanGrantedSubject;
    }

    public void setLoginBlockedMessage(final String loginBlockedMessage) {
        this.loginBlockedMessage = loginBlockedMessage;
    }

    public void setLoginBlockedSms(final String loginBlockedSms) {
        this.loginBlockedSms = loginBlockedSms;
    }

    public void setLoginBlockedSubject(final String loginBlockedSubject) {
        this.loginBlockedSubject = loginBlockedSubject;
    }

    public void setLowUnitsMessage(final String lowUnitsMessage) {
        this.lowUnitsMessage = lowUnitsMessage;
    }

    public void setLowUnitsSms(final String lowUnitsSms) {
        this.lowUnitsSms = lowUnitsSms;
    }

    public void setLowUnitsSubject(final String lowUnitsSubject) {
        this.lowUnitsSubject = lowUnitsSubject;
    }

    public void setMaxTransactionPasswordTriesMessage(final String maxTransactionPasswordTriesMessage) {
        this.maxTransactionPasswordTriesMessage = maxTransactionPasswordTriesMessage;
    }

    public void setMaxTransactionPasswordTriesSms(final String maxTransactionPasswordTriesSms) {
        this.maxTransactionPasswordTriesSms = maxTransactionPasswordTriesSms;
    }

    public void setMaxTransactionPasswordTriesSubject(final String maxTransactionPasswordTriesSubject) {
        this.maxTransactionPasswordTriesSubject = maxTransactionPasswordTriesSubject;
    }

    public void setMessageMailSubjectPrefix(final String messageMailSubjectPrefix) {
        this.messageMailSubjectPrefix = messageMailSubjectPrefix;
    }

    public void setMessageMailSuffixHtml(final String messageMailSuffixHtml) {
        this.messageMailSuffixHtml = messageMailSuffixHtml;
    }

    public void setMessageMailSuffixPlain(final String messageMailSuffixPlain) {
        this.messageMailSuffixPlain = messageMailSuffixPlain;
    }

    public void setNewCommissionContractMessage(final String newCommissionContractMessage) {
        this.newCommissionContractMessage = newCommissionContractMessage;
    }

    public void setNewCommissionContractSms(final String newCommissionContractSms) {
        this.newCommissionContractSms = newCommissionContractSms;
    }

    public void setNewCommissionContractSubject(final String newCommissionContractSubject) {
        this.newCommissionContractSubject = newCommissionContractSubject;
    }

    public void setNewPendingPaymentByBrokerMessage(final String newPendingPaymentByBrokerMessage) {
        this.newPendingPaymentByBrokerMessage = newPendingPaymentByBrokerMessage;
    }

    public void setNewPendingPaymentByBrokerSms(final String newPendingPaymentByBrokerSms) {
        this.newPendingPaymentByBrokerSms = newPendingPaymentByBrokerSms;
    }

    public void setNewPendingPaymentByBrokerSubject(final String newPendingPaymentByBrokerSubject) {
        this.newPendingPaymentByBrokerSubject = newPendingPaymentByBrokerSubject;
    }

    public void setNewPendingPaymentByPayerMessage(final String newPendingPaymentByPayerMessage) {
        this.newPendingPaymentByPayerMessage = newPendingPaymentByPayerMessage;
    }

    public void setNewPendingPaymentByPayerSms(final String newPendingPaymentByPayerSms) {
        this.newPendingPaymentByPayerSms = newPendingPaymentByPayerSms;
    }

    public void setNewPendingPaymentByPayerSubject(final String newPendingPaymentByPayerSubject) {
        this.newPendingPaymentByPayerSubject = newPendingPaymentByPayerSubject;
    }

    public void setNewPendingPaymentByReceiverMessage(final String newPendingPaymentByReceiverMessage) {
        this.newPendingPaymentByReceiverMessage = newPendingPaymentByReceiverMessage;
    }

    public void setNewPendingPaymentByReceiverSms(final String newPendingPaymentByReceiverSms) {
        this.newPendingPaymentByReceiverSms = newPendingPaymentByReceiverSms;
    }

    public void setNewPendingPaymentByReceiverSubject(final String newPendingPaymentByReceiverSubject) {
        this.newPendingPaymentByReceiverSubject = newPendingPaymentByReceiverSubject;
    }

    public void setPaymentObligationRegisteredMessage(final String paymentObligationRegisteredMessage) {
        this.paymentObligationRegisteredMessage = paymentObligationRegisteredMessage;
    }

    public void setPaymentObligationRegisteredSms(final String paymentObligationRegisteredSms) {
        this.paymentObligationRegisteredSms = paymentObligationRegisteredSms;
    }

    public void setPaymentObligationRegisteredSubject(final String paymentObligationRegisteredSubject) {
        this.paymentObligationRegisteredSubject = paymentObligationRegisteredSubject;
    }

    public void setPaymentObligationRejectedMessage(final String paymentobligationRejectedMessage) {
        paymentObligationRejectedMessage = paymentobligationRejectedMessage;
    }

    public void setPaymentObligationRejectedSms(final String paymentObligationRejectedSms) {
        this.paymentObligationRejectedSms = paymentObligationRejectedSms;
    }

    public void setPaymentObligationRejectedSubject(final String paymentObligationRejectedSubject) {
        this.paymentObligationRejectedSubject = paymentObligationRejectedSubject;
    }

    public void setPaymentReceivedMessage(final String paymentReceivedMessage) {
        this.paymentReceivedMessage = paymentReceivedMessage;
    }

    public void setPaymentReceivedSms(final String paymentReceivedSms) {
        this.paymentReceivedSms = paymentReceivedSms;
    }

    public void setPaymentReceivedSubject(final String paymentReceivedSubject) {
        this.paymentReceivedSubject = paymentReceivedSubject;
    }

    public void setPendingBuyerOnlyGuaranteeIssuerMessage(final String pendingBuyerOnlyGuaranteeIssuerMessage) {
        this.pendingBuyerOnlyGuaranteeIssuerMessage = pendingBuyerOnlyGuaranteeIssuerMessage;
    }

    public void setPendingBuyerOnlyGuaranteeIssuerSms(final String pendingBuyerOnlyGuaranteeIssuerSms) {
        this.pendingBuyerOnlyGuaranteeIssuerSms = pendingBuyerOnlyGuaranteeIssuerSms;
    }

    public void setPendingBuyerOnlyGuaranteeIssuerSubject(final String pendingBuyerOnlyGuaranteeIssuerSubject) {
        this.pendingBuyerOnlyGuaranteeIssuerSubject = pendingBuyerOnlyGuaranteeIssuerSubject;
    }

    public void setPendingGuaranteeIssuerMessage(final String pendingGuaranteeIssuerMessage) {
        this.pendingGuaranteeIssuerMessage = pendingGuaranteeIssuerMessage;
    }

    public void setPendingGuaranteeIssuerSms(final String pendingGuaranteeIssuerSms) {
        this.pendingGuaranteeIssuerSms = pendingGuaranteeIssuerSms;
    }

    public void setPendingGuaranteeIssuerSubject(final String pendingGuaranteeIssuerSubject) {
        this.pendingGuaranteeIssuerSubject = pendingGuaranteeIssuerSubject;
    }

    public void setPendingPaymentAuthorizedMessage(final String pendingPaymentAuthorizedMessage) {
        this.pendingPaymentAuthorizedMessage = pendingPaymentAuthorizedMessage;
    }

    public void setPendingPaymentAuthorizedSms(final String pendingPaymentAuthorizedSms) {
        this.pendingPaymentAuthorizedSms = pendingPaymentAuthorizedSms;
    }

    public void setPendingPaymentAuthorizedSubject(final String pendingPaymentAuthorizedSubject) {
        this.pendingPaymentAuthorizedSubject = pendingPaymentAuthorizedSubject;
    }

    public void setPendingPaymentCanceledMessage(final String pendingPaymentCanceledMessage) {
        this.pendingPaymentCanceledMessage = pendingPaymentCanceledMessage;
    }

    public void setPendingPaymentCanceledSms(final String pendingPaymentCanceledSms) {
        this.pendingPaymentCanceledSms = pendingPaymentCanceledSms;
    }

    public void setPendingPaymentCanceledSubject(final String pendingPaymentCanceledSubject) {
        this.pendingPaymentCanceledSubject = pendingPaymentCanceledSubject;
    }

    public void setPendingPaymentDeniedMessage(final String pendingPaymentDeniedMessage) {
        this.pendingPaymentDeniedMessage = pendingPaymentDeniedMessage;
    }

    public void setPendingPaymentDeniedSms(final String pendingPaymentDeniedSms) {
        this.pendingPaymentDeniedSms = pendingPaymentDeniedSms;
    }

    public void setPendingPaymentDeniedSubject(final String pendingPaymentDeniedSubject) {
        this.pendingPaymentDeniedSubject = pendingPaymentDeniedSubject;
    }

    public void setPendingPaymentReceivedMessage(final String pendingPaymentReceivedMessage) {
        this.pendingPaymentReceivedMessage = pendingPaymentReceivedMessage;
    }

    public void setPendingPaymentReceivedSms(final String pendingPaymentReceivedSms) {
        this.pendingPaymentReceivedSms = pendingPaymentReceivedSms;
    }

    public void setPendingPaymentReceivedSubject(final String pendingPaymentReceivedSubject) {
        this.pendingPaymentReceivedSubject = pendingPaymentReceivedSubject;
    }

    public void setPinBlockedMessage(final String pinBlockedMessage) {
        this.pinBlockedMessage = pinBlockedMessage;
    }

    public void setPinBlockedSms(final String pinBlockedSms) {
        this.pinBlockedSms = pinBlockedSms;
    }

    public void setPinBlockedSubject(final String pinBlockedSubject) {
        this.pinBlockedSubject = pinBlockedSubject;
    }

    public void setPosPinBlockedMessage(final String posPinBlockedMessage) {
        this.posPinBlockedMessage = posPinBlockedMessage;
    }

    public void setPosPinBlockedSms(final String posPinBlockedSms) {
        this.posPinBlockedSms = posPinBlockedSms;
    }

    public void setPosPinBlockedSubject(final String posPinBlockedSubject) {
        this.posPinBlockedSubject = posPinBlockedSubject;
    }

    public void setReceivedInvoiceExpiredMessage(final String receivedInvoiceExpiredMessage) {
        this.receivedInvoiceExpiredMessage = receivedInvoiceExpiredMessage;
    }

    public void setReceivedInvoiceExpiredSms(final String receivedInvoiceExpiredSms) {
        this.receivedInvoiceExpiredSms = receivedInvoiceExpiredSms;
    }

    public void setReceivedInvoiceExpiredSubject(final String receivedInvoiceExpiredSubject) {
        this.receivedInvoiceExpiredSubject = receivedInvoiceExpiredSubject;
    }

    public void setReferenceReceivedMessage(final String referenceReceivedMessage) {
        this.referenceReceivedMessage = referenceReceivedMessage;
    }

    public void setReferenceReceivedSms(final String referenceReceivedSms) {
        this.referenceReceivedSms = referenceReceivedSms;
    }

    public void setReferenceReceivedSubject(final String referenceReceivedSubject) {
        this.referenceReceivedSubject = referenceReceivedSubject;
    }

    public void setRemovedFromBrokerGroupMessage(final String removedFromBrokerGroupMessage) {
        this.removedFromBrokerGroupMessage = removedFromBrokerGroupMessage;
    }

    public void setRemovedFromBrokerGroupSms(final String removedFromBrokerGroupSms) {
        this.removedFromBrokerGroupSms = removedFromBrokerGroupSms;
    }

    public void setRemovedFromBrokerGroupSubject(final String removedFromBrokerGroupSubject) {
        this.removedFromBrokerGroupSubject = removedFromBrokerGroupSubject;
    }

    public void setScheduledPaymentFailedToPayeeMessage(final String scheduledPaymentFailedToPayeeMessage) {
        this.scheduledPaymentFailedToPayeeMessage = scheduledPaymentFailedToPayeeMessage;
    }

    public void setScheduledPaymentFailedToPayeeSms(final String scheduledPaymentFailedToPayeeSms) {
        this.scheduledPaymentFailedToPayeeSms = scheduledPaymentFailedToPayeeSms;
    }

    public void setScheduledPaymentFailedToPayeeSubject(final String scheduledPaymentFailedToPayeeSubject) {
        this.scheduledPaymentFailedToPayeeSubject = scheduledPaymentFailedToPayeeSubject;
    }

    public void setScheduledPaymentFailedToPayerMessage(final String scheduledPaymentFailedToPayerMessage) {
        this.scheduledPaymentFailedToPayerMessage = scheduledPaymentFailedToPayerMessage;
    }

    public void setScheduledPaymentFailedToPayerSms(final String scheduledPaymentFailedToPayerSms) {
        this.scheduledPaymentFailedToPayerSms = scheduledPaymentFailedToPayerSms;
    }

    public void setScheduledPaymentFailedToPayerSubject(final String scheduledPaymentFailedToPayerSubject) {
        this.scheduledPaymentFailedToPayerSubject = scheduledPaymentFailedToPayerSubject;
    }

    public void setScheduledPaymentProcessedMessage(final String scheduledPaymentProcessedMessage) {
        this.scheduledPaymentProcessedMessage = scheduledPaymentProcessedMessage;
    }

    public void setScheduledPaymentProcessedSms(final String scheduledPaymentProcessedSms) {
        this.scheduledPaymentProcessedSms = scheduledPaymentProcessedSms;
    }

    public void setScheduledPaymentProcessedSubject(final String scheduledPaymentProcessedSubject) {
        this.scheduledPaymentProcessedSubject = scheduledPaymentProcessedSubject;
    }

    public void setScheduledPaymentsCancelledMessage(final String scheduledPaymentsCancelledMessage) {
        this.scheduledPaymentsCancelledMessage = scheduledPaymentsCancelledMessage;
    }

    public void setScheduledPaymentsCancelledSms(final String scheduledPaymentsCancelledSms) {
        this.scheduledPaymentsCancelledSms = scheduledPaymentsCancelledSms;
    }

    public void setScheduledPaymentsCancelledSubject(final String scheduledPaymentsCancelledSubject) {
        this.scheduledPaymentsCancelledSubject = scheduledPaymentsCancelledSubject;
    }

    public void setScheduledPaymentsCancelledToOtherMessage(final String scheduledPaymentsCancelledToOtherMessage) {
        this.scheduledPaymentsCancelledToOtherMessage = scheduledPaymentsCancelledToOtherMessage;
    }

    public void setScheduledPaymentsCancelledToOtherSms(final String scheduledPaymentsCancelledToOtherSms) {
        this.scheduledPaymentsCancelledToOtherSms = scheduledPaymentsCancelledToOtherSms;
    }

    public void setScheduledPaymentsCancelledToOtherSubject(final String scheduledPaymentsCancelledToOtherSubject) {
        this.scheduledPaymentsCancelledToOtherSubject = scheduledPaymentsCancelledToOtherSubject;
    }

    public void setSentInvoiceExpiredMessage(final String sentInvoiceExpiredMessage) {
        this.sentInvoiceExpiredMessage = sentInvoiceExpiredMessage;
    }

    public void setSentInvoiceExpiredSms(final String sentInvoiceExpiredSms) {
        this.sentInvoiceExpiredSms = sentInvoiceExpiredSms;
    }

    public void setSentInvoiceExpiredSubject(final String sentInvoiceExpiredSubject) {
        this.sentInvoiceExpiredSubject = sentInvoiceExpiredSubject;
    }

    public void setSmsMessagePrefix(final String smsMessagePrefix) {
        this.smsMessagePrefix = smsMessagePrefix;
    }

    public void setTransactionFeedbackAdminCommentsMessage(final String transactionFeedbackAdminCommentsMessage) {
        this.transactionFeedbackAdminCommentsMessage = transactionFeedbackAdminCommentsMessage;
    }

    public void setTransactionFeedbackAdminCommentsSms(final String transactionFeedbackAdminCommentsSms) {
        this.transactionFeedbackAdminCommentsSms = transactionFeedbackAdminCommentsSms;
    }

    public void setTransactionFeedbackAdminCommentsSubject(final String transactionFeedbackAdminCommentsSubject) {
        this.transactionFeedbackAdminCommentsSubject = transactionFeedbackAdminCommentsSubject;
    }

    public void setTransactionFeedbackReceivedMessage(final String transactionFeedbackReceivedMessage) {
        this.transactionFeedbackReceivedMessage = transactionFeedbackReceivedMessage;
    }

    public void setTransactionFeedbackReceivedSms(final String transactionFeedbackReceivedSms) {
        this.transactionFeedbackReceivedSms = transactionFeedbackReceivedSms;
    }

    public void setTransactionFeedbackReceivedSubject(final String transactionFeedbackReceivedSubject) {
        this.transactionFeedbackReceivedSubject = transactionFeedbackReceivedSubject;
    }

    public void setTransactionFeedbackReplyMessage(final String transactionFeedbackReplyMessage) {
        this.transactionFeedbackReplyMessage = transactionFeedbackReplyMessage;
    }

    public void setTransactionFeedbackReplySms(final String transactionFeedbackReplySms) {
        this.transactionFeedbackReplySms = transactionFeedbackReplySms;
    }

    public void setTransactionFeedbackReplySubject(final String transactionFeedbackReplySubject) {
        this.transactionFeedbackReplySubject = transactionFeedbackReplySubject;
    }

    public void setTransactionFeedbackRequestMessage(final String transactionFeedbackRequestMessage) {
        this.transactionFeedbackRequestMessage = transactionFeedbackRequestMessage;
    }

    public void setTransactionFeedbackRequestSms(final String transactionFeedbackRequestSms) {
        this.transactionFeedbackRequestSms = transactionFeedbackRequestSms;
    }

    public void setTransactionFeedbackRequestSubject(final String transactionFeedbackRequestSubject) {
        this.transactionFeedbackRequestSubject = transactionFeedbackRequestSubject;
    }

}
