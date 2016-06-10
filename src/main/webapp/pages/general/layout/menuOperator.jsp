<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:menu url="/do/operator/home" key="menu.operator.home" />
<cyclos:menu key="menu.operator.personal">
	<cyclos:menu url="/do/operator/operatorProfile" key="menu.operator.personal.profile" />
	<cyclos:menu url="/do/operator/changePassword" key="menu.operator.personal.changePassword" />
</cyclos:menu>

<!-- check this menu -- change it -->
<cyclos:menu key="menu.operator.member" >
	<cyclos:menu url="/do/operator/memberAds" key="menu.operator.member.ads" permission="${OperatorPermission.ADS_PUBLISH}" />
	<cyclos:menu url="/do/operator/searchMessages" key="menu.operator.member.messages" permission="${OperatorPermission.MESSAGES_VIEW}" />
	<cyclos:menu url="/do/operator/contacts" key="menu.operator.member.contacts" permission="${OperatorPermission.CONTACTS_VIEW}" />
	<c:if test="${loggedMemberHasGeneralReferences}">
		<cyclos:menu url="/do/operator/references?nature=GENERAL" key="menu.operator.member.references" permission="${OperatorPermission.REFERENCES_MANAGE_MEMBER_REFERENCES}" />
	</c:if>
	<c:if test="${loggedMemberHasTransactionFeedbacks}">
		<cyclos:menu url="/do/operator/references?nature=TRANSACTION" key="menu.operator.member.transactionFeedbacks" permission="${OperatorPermission.REFERENCES_MANAGE_MEMBER_TRANSACTION_FEEDBACKS}" />
	</c:if>
	<cyclos:menu url="/do/operator/activities" key="menu.operator.member.activities" permission="${OperatorPermission.REPORTS_VIEW_MEMBER}" />
	<cyclos:menu url="/do/operator/listReceiptPrinterSettings" key="menu.operator.preferences.receiptPrinterSettings" permission="${MemberPermission.PREFERENCES_MANAGE_RECEIPT_PRINTER_SETTINGS}"/>
</cyclos:menu>

<!-- Operator Menu -->
<cyclos:menu key="menu.operator.guarantees">
	<c:if test="${cyclos:granted(OperatorPermission.GUARANTEES_ISSUE_CERTIFICATIONS) || cyclos:granted(OperatorPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)}">
		<cyclos:menu url="/do/operator/searchCertifications" key="menu.operator.guarantees.searchCertifications"/>
	</c:if>
	<c:if test="${loggedMemberHasGuarantees}">
		<cyclos:menu url="/do/operator/searchGuarantees" key="menu.operator.guarantees.searchGuarantees" />
	</c:if>
	<c:if test="${cyclos:granted(OperatorPermission.GUARANTEES_SELL_WITH_PAYMENT_OBLIGATIONS) || cyclos:granted(OperatorPermission.GUARANTEES_BUY_WITH_PAYMENT_OBLIGATIONS)}">
		<cyclos:menu url="/do/operator/searchPaymentObligations" key="menu.operator.guarantees.searchPaymentObligations" />
	</c:if>
</cyclos:menu>

<c:if test="${loggedMemberHasAccounts}">
	<%-- Show the account menu only if the member has at least one account --%>
	<cyclos:menu key="menu.operator.account">
		<cyclos:menu url="/do/operator/accountOverview" key="menu.operator.account.accountInformation" />
		<c:if test="${cyclos:granted(OperatorPermission.PAYMENTS_AUTHORIZE)}">
			<cyclos:menu url="/do/operator/transfersAwaitingAuthorization" key="menu.operator.account.transfersAwaitingAuthorization" />
			<cyclos:menu url="/do/operator/searchTransferAuthorizations" key="menu.operator.account.transfersAuthorizations" />
		</c:if>	
		<cyclos:menu url="/do/operator/searchScheduledPayments" key="menu.operator.account.scheduledPayments" permission="${OperatorPermission.ACCOUNT_SCHEDULED_INFORMATION}" />
		<cyclos:menu url="/do/operator/searchInvoices" key="menu.operator.account.invoices" permission="${OperatorPermission.INVOICES_VIEW}"/>
		<cyclos:menu url="/do/operator/searchLoans" key="menu.operator.account.loans" permission="${OperatorPermission.LOANS_VIEW}"/>
		<cyclos:menu url="/do/operator/payment?selectMember=true" key="menu.operator.account.memberPayment" />
		<cyclos:menu url="/do/operator/payment?toSystem=true" key="menu.operator.account.systemPayment" permission="${OperatorPermission.PAYMENTS_PAYMENT_TO_SYSTEM}" />
		<cyclos:menu url="/do/operator/selfPayment" key="menu.operator.account.selfPayment" permission="${OperatorPermission.PAYMENTS_PAYMENT_TO_SELF}" />
		<cyclos:menu url="/do/operator/requestPayment" key="menu.operator.account.requestPayment" permission="${OperatorPermission.PAYMENTS_REQUEST}"  />
		<cyclos:menu url="/do/operator/sendInvoice?selectMember=true" key="menu.operator.account.memberInvoice" permission="${OperatorPermission.INVOICES_SEND_TO_MEMBER}"/>
		<cyclos:menu url="/do/operator/sendInvoice?toSystem=true" key="menu.operator.account.systemInvoice" permission="${OperatorPermission.INVOICES_SEND_TO_SYSTEM}" />
	</cyclos:menu>
</c:if>

<cyclos:menu key="menu.operator.search">
	<cyclos:menu url="/do/operator/searchMembers" key="menu.operator.search.members" permission="${MemberPermission.PROFILE_VIEW}" />
	<cyclos:menu url="/do/operator/searchAds" key="menu.operator.search.ads" permission="${MemberPermission.ADS_VIEW}" />
</cyclos:menu>

<cyclos:menu key="menu.operator.help">
	<cyclos:menu url="/do/operator/contactUs" key="menu.contact" />
	<cyclos:menu url="/do/operator/manual" key="menu.operator.help.manual" />
	<cyclos:menu url="/do/operator/about" key="menu.about" />
</cyclos:menu>

<cyclos:menu url="/do/logout" key="menu.operator.logout" confirmationKey="menu.logout.confirmationMessage"/>
