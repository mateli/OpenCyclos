<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<cyclos:menu url="/do/member/home" key="menu.member.home" />
<cyclos:menu key="menu.member.personal">
	<cyclos:menu url="/do/member/profile" key="menu.member.personal.profile" />
	<cyclos:menu url="/do/member/searchMessages" key="menu.member.personal.messages" permission="${MemberPermission.MESSAGES_VIEW}" />
	<cyclos:menu url="/do/member/memberAds" key="menu.member.personal.ads" permission="${MemberPermission.ADS_PUBLISH}" />
	<cyclos:menu url="/do/member/contacts" key="menu.member.personal.contacts" />
	<c:if test="${loggedMemberHasGeneralReferences}">
		<cyclos:menu url="/do/member/references?nature=GENERAL" key="menu.member.personal.references" />
	</c:if>
	<c:if test="${loggedMemberHasTransactionFeedbacks}">
		<cyclos:menu url="/do/member/references?nature=TRANSACTION" key="menu.member.personal.transactionFeedbacks" />
	</c:if>
	<cyclos:menu url="/do/member/activities" key="menu.member.personal.activities" />
	<c:if test="${loggedMemberHasDocuments}">
		<cyclos:menu url="/do/member/selectDocument" key="menu.member.personal.documents"/>
	</c:if>
	<cyclos:menu url="/do/member/changePassword" key="menu.member.personal.changePassword" />
	<c:if test="${(hasExternalChannels && cyclos:granted(MemberPermission.ACCESS_CHANGE_CHANNELS_ACCESS)) || hasPin}">
		<cyclos:menu url="/do/member/manageExternalAccess" key="${cyclos:granted(MemberPermission.ACCESS_CHANGE_CHANNELS_ACCESS) ? 'menu.member.personal.manageExternalAccess' : 'menu.member.personal.changePin'}" />
	</c:if>
	<cyclos:menu url="/do/member/searchSmsLogs" key="menu.member.personal.sms" permission="${MemberPermission.SMS_VIEW}"/>
	<c:if test="${not empty loggedElement.broker && hasCommissionContracts}">
		<cyclos:menu url="/do/member/listBrokerCommissionContracts" key="menu.member.personal.commissionChargeStatus" />
	</c:if>
	<c:if test="${hasCards}">
		<cyclos:menu url="/do/member/searchCards?memberId=${loggedUser.id}" key="menu.member.personal.cards" permission="${MemberPermission.CARDS_VIEW}"/>
	</c:if>
	<c:if test="${hasPos}">
		<c:choose>
			<c:when test="${uniqueMemberPosId}">
				<cyclos:menu url="/do/member/editPos?id=${uniqueMemberPosId}" key="menu.member.personal.pos.editPos" />
			</c:when>
			<c:otherwise>
				<cyclos:menu url="/do/member/memberPos?memberId=${loggedUser.id}" key="menu.member.personal.pos.memberPos" />
			</c:otherwise>

		</c:choose>
	</c:if>
</cyclos:menu>
<c:if test="${loggedMemberHasAccounts}">
	<%-- Show the account menu only if the member has at least one account --%>
	<cyclos:menu key="menu.member.account">
		<cyclos:menu url="/do/member/accountOverview" key="menu.member.account.accountInformation" />
		<c:if test="${!isBroker && cyclos:granted(MemberPermission.PAYMENTS_AUTHORIZE)}">
			<cyclos:menu url="/do/member/transfersAwaitingAuthorization" key="menu.member.account.transfersAwaitingAuthorization" />
			<cyclos:menu url="/do/member/searchTransferAuthorizations" key="menu.member.account.transfersAuthorizations" />
		</c:if>
		<cyclos:menu url="/do/member/searchScheduledPayments" key="menu.member.account.scheduledPayments" permission="${MemberPermission.ACCOUNT_SCHEDULED_INFORMATION}" />
		<cyclos:menu url="/do/member/searchInvoices" key="menu.member.account.invoices" permission="${MemberPermission.INVOICES_VIEW}"/>
		<cyclos:menu url="/do/member/searchLoans" key="menu.member.account.loans" permission="${MemberPermission.LOANS_VIEW}"/>
		<c:if test="${loggedMemberHasLoanGroups}">
			<cyclos:menu url="/do/member/memberLoanGroups" key="menu.member.account.loanGroups" />
		</c:if>
		<cyclos:menu url="/do/member/payment?selectMember=true" key="menu.member.account.memberPayment" permission="${MemberPermission.PAYMENTS_PAYMENT_TO_MEMBER}"/>
		<cyclos:menu url="/do/member/payment?toSystem=true" key="menu.member.account.systemPayment" permission="${MemberPermission.PAYMENTS_PAYMENT_TO_SYSTEM}"  />
		<cyclos:menu url="/do/member/selfPayment" key="menu.member.account.selfPayment" permission="${MemberPermission.PAYMENTS_PAYMENT_TO_SELF}" />
		<cyclos:menu url="/do/member/requestPayment" key="menu.member.account.requestPayment" permission="${MemberPermission.PAYMENTS_REQUEST}"  />
		<cyclos:menu url="/do/member/sendInvoice?selectMember=true" key="menu.member.account.memberInvoice" permission="${MemberPermission.INVOICES_SEND_TO_MEMBER}"/>
		<cyclos:menu url="/do/member/sendInvoice?toSystem=true" key="menu.member.account.systemInvoice" permission="${MemberPermission.INVOICES_SEND_TO_SYSTEM}" />
	</cyclos:menu>
</c:if>
<cyclos:menu key="menu.member.operators" permission="${MemberPermission.OPERATORS_MANAGE}">
	<cyclos:menu url="/do/member/searchOperators" key="menu.member.operators"/>
	<cyclos:menu url="/do/member/listConnectedUsers" key="menu.member.connectedOperators"/>
	<cyclos:menu url="/do/member/listGroups" key="menu.member.operators.groups"/>
	<cyclos:menu url="/do/member/listCustomFields?nature=OPERATOR" key="menu.member.operators.customFields"/>
</cyclos:menu>

<cyclos:menu key="menu.member.preferences" module="${Module.MEMBER_PREFERENCES}">
	<cyclos:menu url="/do/member/notificationPreferences" key="menu.member.preferences.notification" permission="${MemberPermission.PREFERENCES_MANAGE_NOTIFICATIONS}"/>
	<cyclos:menu url="/do/member/listAdInterests" key="menu.member.preferences.adInterests" permission="${MemberPermission.PREFERENCES_MANAGE_AD_INTERESTS}"/>
	<cyclos:menu url="/do/member/listReceiptPrinterSettings" key="menu.member.preferences.receiptPrinterSettings" permission="${MemberPermission.PREFERENCES_MANAGE_RECEIPT_PRINTER_SETTINGS}"/>
</cyclos:menu>
<cyclos:menu key="menu.member.search">
	<cyclos:menu url="/do/member/searchMembers" key="menu.member.search.members" permission="${MemberPermission.PROFILE_VIEW}" />
	<cyclos:menu url="/do/member/searchAds" key="menu.member.search.ads" permission="${MemberPermission.ADS_VIEW}" />
</cyclos:menu>
<cyclos:menu key="menu.member.guarantees">
	<c:if test="${cyclos:granted(MemberPermission.GUARANTEES_ISSUE_CERTIFICATIONS) || cyclos:granted(MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)}">
		<cyclos:menu url="/do/member/searchCertifications" key="menu.member.guarantees.searchCertifications"/>
	</c:if>
	<c:if test="${loggedMemberHasGuarantees}">
		<cyclos:menu url="/do/member/searchGuarantees" key="menu.member.guarantees.searchGuarantees" />
	</c:if>
	<c:if test="${cyclos:granted(MemberPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS) || cyclos:granted(MemberPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)}">
		<cyclos:menu url="/do/member/searchPaymentObligations" key="menu.member.guarantees.searchPaymentObligations" />
	</c:if>
</cyclos:menu>
<c:if test="${isBroker}">
	<cyclos:menu key="menu.member.broker">
		<cyclos:menu url="/do/member/createMember" key="menu.member.broker.registerMember" permission="${BrokerPermission.MEMBERS_REGISTER}" />
		<cyclos:menu url="/do/member/listBrokerings" key="menu.member.broker.listMembers" />
		<cyclos:menu url="/do/member/searchPendingMembers" key="menu.member.broker.pendingMembers" permission="${BrokerPermission.MEMBERS_MANAGE_PENDING}"  />
		<cyclos:menu url="/do/member/sendMessage?toBrokeredMembers=true" key="menu.member.broker.messageToMembers" permission="${BrokerPermission.MESSAGES_SEND_TO_MEMBERS}"/>
		<c:if test="${cyclos:granted(BrokerPermission.SMS_MAILINGS_FREE_SMS_MAILINGS) || cyclos:granted(BrokerPermission.SMS_MAILINGS_PAID_SMS_MAILINGS)}">
			<cyclos:menu url="/do/member/searchSmsMailings" key="menu.member.broker.smsMailings"/>
		</c:if>
		<c:if test="${cyclos:granted(BrokerPermission.MEMBER_PAYMENTS_AUTHORIZE)}">
			<cyclos:menu url="/do/member/transfersAwaitingAuthorization" key="menu.member.account.transfersAwaitingAuthorization" />
			<cyclos:menu url="/do/member/searchTransferAuthorizations" key="menu.member.account.transfersAuthorizations" />
		</c:if>
		<cyclos:menu url="/do/member/defaultBrokerCommissions" key="menu.member.broker.defaultBrokerCommissions" permission="${BrokerPermission.MEMBERS_MANAGE_DEFAULTS}" />
		<cyclos:menu url="/do/member/searchBrokerCommissionContracts" key="menu.member.broker.brokerCommissionContracts" permission="${BrokerPermission.MEMBERS_MANAGE_CONTRACTS}" />
		<c:forEach var="memberRecordType" items="${memberRecordTypesInMenu}">
			<cyclos:menu url="/do/member/searchMemberRecords?typeId=${memberRecordType.id}&global=true" label="${memberRecordType.label}" />
		</c:forEach>
		<cyclos:menu url="/do/member/searchCardsAsBroker" key="menu.member.personal.cards" permission="${BrokerPermission.CARDS_VIEW}" />
		<cyclos:menu url="/do/member/searchPos" key="menu.admin.accessDevices.pos.search" permission="${BrokerPermission.POS_VIEW}" />
	</cyclos:menu>
</c:if>
<cyclos:menu key="menu.member.help">
	<cyclos:menu url="/do/member/contactUs" key="menu.contact" />
	<cyclos:menu url="/do/member/manual" key="menu.member.help.manual" />
	<cyclos:menu url="/do/member/about" key="menu.about" />
</cyclos:menu>
<cyclos:menu url="/do/logout" key="menu.member.logout" confirmationKey="menu.logout.confirmationMessage"/>
