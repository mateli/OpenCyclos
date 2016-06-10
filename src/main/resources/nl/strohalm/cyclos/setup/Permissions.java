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

/**
 * Defines all modules on the system
 * @author luis
 */
// TODO: remove this class
public class Permissions {

    // /* COMMON PERMISSIONS */
    // buildModule(modules, ModuleType.BASIC, "basic", "login", "inviteMember", "quickAccess");
    //
    // /* ADMINISTRATOR PERMISSIONS */
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemCurrencies", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemAccounts", "manage", "view", "information", "authorizedInformation",
    // "scheduledInformation");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemSettings", "manageLocal", "manageAlert", "manageAccess", "manageMail", "manageLog",
    // "view", "file");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemCustomizedFiles", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemCustomImages", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemCustomFields", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemTranslation", "manage", "view", "file", "manageMailTranslation", "manageNotification");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemThemes", "select", "remove", "import", "export");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemPayments", "payment", "authorize", "cancel", "chargeback");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemAccountFees", "view", "charge");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemAdCategories", "manage", "view", "file");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemMessageCategories", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemAlerts", "manageMemberAlerts", "manageSystemAlerts", "viewMemberAlerts",
    // "viewSystemAlerts");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemErrorLog", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemGroups", "manageMember", "manageBroker", "manageAdmin");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemRegistrationAgreements", "view", "manage");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemAdminGroups", "view", "manageAdminCustomizedFiles");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemGroupFilters", "manage", "view", "manageCustomizedFiles");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemLoanGroups", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemReports", "current", "memberList", "smsLogs", "statistics", "simulations",
    // "dRateConfigSimulation", "aRateConfigSimulation");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemTasks", "onlineState", "manageIndexes");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemStatus", "view", "viewConnectedAdmins", "viewConnectedBrokers", "viewConnectedMembers",
    // "viewConnectedOperators");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemExternalAccounts", "manage", "view", "details", "processPayment", "checkPayment",
    // "managePayment");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemMemberRecordTypes", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemServiceClients", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemChannels", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemGuaranteeTypes", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemCardTypes", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_SYSTEM, "systemInfoTexts", "manage", "view");
    //
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMembers", "view", "register", "managePending", "changeProfile", "changeName",
    // "changeUsername", "remove", "changeGroup", "import");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberAccess", "changePassword", "resetPassword", "transactionPassword", "disconnect",
    // "disconnectOperator", "enableLogin", "changePin", "unblockPin", "changeChannelsAccess");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberBrokerings", "changeBroker", "viewMembers", "viewLoans", "manageCommissions");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberAccounts", "information", "authorizedInformation", "scheduledInformation",
    // "simulateConversion", "creditLimit");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberGroups", "view", "manageAccountSettings", "manageMemberCustomizedFiles");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberReports", "view", "showAccountInformation");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberPayments", "payment", "directPayment", "paymentWithDate", "paymentAsMemberToMember",
    // "paymentAsMemberToSelf", "paymentAsMemberToSystem", "authorize", "cancelAuthorizedAsMember", "cancelScheduledAsMember",
    // "blockScheduledAsMember", "chargeback");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberInvoices", "send", "directSend", "view", "accept", "cancel", "deny",
    // "sendAsMemberToMember", "sendAsMemberToSystem", "acceptAsMemberFromMember", "acceptAsMemberFromSystem", "denyAsMember", "cancelAsMember");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberAds", "view", "manage", "import");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberReferences", "view", "manage");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberTransactionFeedbacks", "view", "manage");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberLoans", "view", "viewAuthorized", "grant", "grantWithDate", "discard", "repay",
    // "repayWithDate", "manageExpiredStatus");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberLoanGroups", "manage", "view");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberMessages", "view", "sendToMember", "sendToGroup", "manage");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberDocuments", "details", "manageDynamic", "manageStatic", "manageMember");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberRecords", "view", "create", "modify", "delete");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberBulkActions", "changeGroup", "changeBroker", "generateCard");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberSms", "view");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberSmsMailings", "view", "freeSmsMailings", "paidSmsMailings");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberGuarantees", "viewPaymentObligations", "viewCertifications", "viewGuarantees",
    // "registerGuarantees", "cancelCertificationsAsMember", "cancelGuaranteesAsMember", "acceptGuaranteesAsMember");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberCards", "view", "generate", "cancel", "block", "unblock", "changeCardSecurityCode",
    // "unblockSecurityCode");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberPos", "view", "manage", "assign", "block", "discard", "unblockPin", "changePin",
    // "changeParameters");
    // buildModule(modules, ModuleType.ADMIN_MEMBER, "adminMemberPreferences", "manageNotifications");
    //
    // buildModule(modules, ModuleType.ADMIN_ADMIN, "adminAdmins", "view", "register", "changeProfile", "changeGroup", "remove");
    // buildModule(modules, ModuleType.ADMIN_ADMIN, "adminAdminAccess", "changePassword", "transactionPassword", "disconnect", "enableLogin");
    // buildModule(modules, ModuleType.ADMIN_ADMIN, "adminAdminRecords", "view", "create", "modify", "delete");
    //
    // /* MEMBER PERMISSIONS */
    // buildModule(modules, ModuleType.MEMBER, "memberProfile", "view", "changeUsername", "changeName");
    // buildModule(modules, ModuleType.MEMBER, "memberAccess", "unblockPin");
    // buildModule(modules, ModuleType.MEMBER, "memberAccount", "authorizedInformation", "scheduledInformation", "simulateConversion");
    // buildModule(modules, ModuleType.MEMBER, "memberPayments", "paymentToSelf", "paymentToMember", "directPaymentToMember", "paymentToSystem",
    // "ticket", "authorize", "cancelAuthorized", "cancelScheduled", "blockScheduled", "request", "chargeback");
    // buildModule(modules, ModuleType.MEMBER, "memberInvoices", "view", "sendToMember", "directSendToMember", "sendToSystem");
    // buildModule(modules, ModuleType.MEMBER, "memberReferences", "view", "give");
    // buildModule(modules, ModuleType.MEMBER, "memberDocuments", "view");
    // buildModule(modules, ModuleType.MEMBER, "memberLoans", "view", "repay");
    // buildModule(modules, ModuleType.MEMBER, "memberAds", "view", "publish");
    // buildModule(modules, ModuleType.MEMBER, "memberPreferences", "manageNotifications", "manageAdInterests");
    // buildModule(modules, ModuleType.MEMBER, "memberReports", "view", "showAccountInformation");
    // buildModule(modules, ModuleType.MEMBER, "memberMessages", "view", "sendToMember", "sendToAdministration", "manage");
    // buildModule(modules, ModuleType.MEMBER, "memberOperators", "manage");
    // buildModule(modules, ModuleType.MEMBER, "memberCommissions", "view"); // this permission is only used to show/hide the menu
    // buildModule(modules, ModuleType.MEMBER, "memberSms", "view");
    // buildModule(modules, ModuleType.MEMBER, "memberGuarantees", "issueGuarantees", "issueCertifications", "buyWithPaymentObligations",
    // "sellWithPaymentObligations");
    // buildModule(modules, ModuleType.MEMBER, "memberCards", "view", "block", "unblock", "changeCardSecurityCode");
    //
    // /* BROKER PERMISSIONS */
    // buildModule(modules, ModuleType.BROKER, "brokerMembers", "register", "managePending", "changeProfile", "changeName", "changeUsername",
    // "manageDefaults", "manageContracts");
    // buildModule(modules, ModuleType.BROKER, "brokerAccounts", "information", "authorizedInformation", "scheduledInformation",
    // "brokerSimulateConversion");
    // buildModule(modules, ModuleType.BROKER, "brokerReports", "view", "showAccountInformation");
    // buildModule(modules, ModuleType.BROKER, "brokerAds", "view", "manage");
    // buildModule(modules, ModuleType.BROKER, "brokerReferences", "manage");
    // buildModule(modules, ModuleType.BROKER, "brokerInvoices", "view", "sendAsMemberToMember", "sendAsMemberToSystem", "acceptAsMemberFromMember",
    // "acceptAsMemberFromSystem", "denyAsMember", "cancelAsMember");
    // buildModule(modules, ModuleType.BROKER, "brokerLoans", "view");
    // buildModule(modules, ModuleType.BROKER, "brokerLoanGroups", "view");
    // buildModule(modules, ModuleType.BROKER, "brokerDocuments", "view", "viewMember", "manageMember");
    // buildModule(modules, ModuleType.BROKER, "brokerMessages", "sendToMembers");
    // buildModule(modules, ModuleType.BROKER, "brokerMemberAccess", "changePassword", "resetPassword", "transactionPassword", "changePin",
    // "unblockPin", "changeChannelsAccess");
    // buildModule(modules, ModuleType.BROKER, "brokerMemberPayments", "paymentAsMemberToMember", "paymentAsMemberToSelf", "paymentAsMemberToSystem",
    // "authorize", "cancelAuthorizedAsMember", "cancelScheduledAsMember", "blockScheduledAsMember");
    // buildModule(modules, ModuleType.BROKER, "brokerMemberRecords", "view", "create", "modify", "delete");
    // buildModule(modules, ModuleType.BROKER, "brokerMemberSms", "view");
    // buildModule(modules, ModuleType.BROKER, "brokerCards", "view", "generate", "cancel", "block", "unblock", "changeCardSecurityCode",
    // "unblockSecurityCode");
    // buildModule(modules, ModuleType.BROKER, "brokerPos", "view", "manage", "assign", "block", "discard", "unblockPin", "changePin",
    // "changeParameters");
    // buildModule(modules, ModuleType.BROKER, "brokerSmsMailings", "freeSmsMailings", "paidSmsMailings");
    // buildModule(modules, ModuleType.BROKER, "brokerPreferences", "manageNotifications");
    //
    // /* OPERATOR PERMISSIONS */
    // buildModule(modules, ModuleType.OPERATOR, "operatorAccount", "authorizedInformation", "scheduledInformation", "accountInformation",
    // "simulateConversion");
    // buildModule(modules, ModuleType.OPERATOR, "operatorPayments", "paymentToSelf", "paymentToMember", "directPaymentToMember", "paymentToSystem",
    // "externalMakePayment", "externalReceivePayment", "authorize", "cancelAuthorized", "cancelScheduled", "blockScheduled", "request");
    // buildModule(modules, ModuleType.OPERATOR, "operatorInvoices", "view", "sendToMember", "directSendToMember", "sendToSystem", "manage");
    // buildModule(modules, ModuleType.OPERATOR, "operatorReferences", "view", "manageMemberReferences", "manageMemberTransactionFeedbacks");
    // buildModule(modules, ModuleType.OPERATOR, "operatorLoans", "view", "repay");
    // buildModule(modules, ModuleType.OPERATOR, "operatorAds", "publish");
    // buildModule(modules, ModuleType.OPERATOR, "operatorReports", "viewMember");
    // buildModule(modules, ModuleType.OPERATOR, "operatorContacts", "manage", "view");
    // buildModule(modules, ModuleType.OPERATOR, "operatorGuarantees", "issueGuarantees", "issueCertifications", "buyWithPaymentObligations",
    // "sellWithPaymentObligations");
    // buildModule(modules, ModuleType.OPERATOR, "operatorMessages", "view", "sendToMember", "sendToAdministration", "manage");
    //
    // return modules;
}
