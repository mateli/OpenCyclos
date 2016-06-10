<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<jsp:include page="/pages/members/includes/profileOfMemberByMember.jsp"/>
<br/>

<%-- The broker can manage password if any of the below permissions are granted --%>
<c:set var="canManagePasswords" value="${cyclos:granted(BrokerPermission.MEMBER_ACCESS_CHANGE_PASSWORD)}"/>
<c:set var="canManageExternalAccess" value="${cyclos:granted(BrokerPermission.MEMBER_ACCESS_CHANGE_PIN) || cyclos:granted(BrokerPermission.MEMBER_ACCESS_CHANGE_CHANNELS_ACCESS)}"/>
<c:set var="managePasswordsKey" value="profile.action.manageLoginPassword"/>
<c:if test="${member.group.memberSettings.sendPasswordByEmail && cyclos:granted(BrokerPermission.MEMBER_ACCESS_RESET_PASSWORD)}">
	<c:set var="canManagePasswords" value="${true}"/>
</c:if>
<c:if test="${member.group.basicSettings.transactionPassword.used && cyclos:granted(BrokerPermission.MEMBER_ACCESS_TRANSACTION_PASSWORD)}">
	<c:set var="canManagePasswords" value="${true}"/>
	<c:set var="managePasswordsKey" value="profile.action.managePasswords"/>
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="profile.action.byBroker.title" arg0="${member.name}"/></td>
        <cyclos:help page="profiles#actions_for_member_by_broker" />
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
			<cyclos:layout className="defaultTable" columns="4">
				<c:if test="${!removed}">
					<c:choose>
						<c:when test="${cyclos:granted(BrokerPermission.ADS_MANAGE)}">
							<cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageAds"/></cyclos:cell>
							<cyclos:cell align="left"><input type="button" class="linkButton" linkURL="memberAds?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
						</c:when>
		            </c:choose>
				</c:if>
				<c:if test="${cyclos:granted(BrokerPermission.PREFERENCES_MANAGE_NOTIFICATIONS)}">
					<cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageNotifications"/></cyclos:cell>
					<cyclos:cell align="left"><input type="button" class="linkButton" linkURL="notificationPreferences?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>				
				<c:if test="${hasAccounts && cyclos:granted(BrokerPermission.ACCOUNTS_INFORMATION)}">
					<cyclos:cell width="35%" className="label"><bean:message key="profile.action.accountInformation"/></cyclos:cell>
					<cyclos:cell align="left"><input type="button" class="linkButton" linkURL="accountOverview?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${!removed && hasAccounts && cyclos:granted(BrokerPermission.ACCOUNTS_SCHEDULED_INFORMATION)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.scheduledPayments"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="searchScheduledPayments?&memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${hasAccounts && cyclos:granted(BrokerPermission.ACCOUNTS_AUTHORIZED_INFORMATION)}">
					<cyclos:cell width="35%" className="label"><bean:message key="profile.action.transferAuthorizations"/></cyclos:cell>
					<cyclos:cell align="left"><input type="button" class="linkButton" linkURL="searchTransferAuthorizations?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${!removed && hasAccounts && cyclos:granted(BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.paymentAsMemberToMember"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="payment?selectMember=true&from=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${!removed && hasAccounts && cyclos:granted(BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.memberSelfPayment"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="selfPayment?from=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${!removed && hasAccounts && cyclos:granted(BrokerPermission.MEMBER_PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.paymentAsMemberToSystem"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="payment?toSystem=true&from=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${!removed && hasAccounts && cyclos:granted(BrokerPermission.REFERENCES_MANAGE)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageReferences"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="references?nature=GENERAL&memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${!removed && hasAccounts && cyclos:granted(BrokerPermission.LOANS_VIEW)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageLoans"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="searchLoans?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${!removed && hasAccounts && cyclos:granted(BrokerPermission.INVOICES_VIEW)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.invoices"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="searchInvoices?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${!removed && hasAccounts && cyclos:granted(BrokerPermission.INVOICES_SEND_AS_MEMBER_TO_MEMBER)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.invoiceAsMemberToMember"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="sendInvoice?selectMember=true&from=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${!removed && hasAccounts && cyclos:granted(BrokerPermission.INVOICES_SEND_AS_MEMBER_TO_SYSTEM)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.invoiceAsMemberToSystem"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="sendInvoice?toSystem=true&from=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${!removed && cyclos:granted(BrokerPermission.LOAN_GROUPS_VIEW)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.loanGroups"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="memberLoanGroups?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
                <c:if test="${!removed && canManagePasswords}">
                    <cyclos:cell width="35%" className="label"><bean:message key="${managePasswordsKey}"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="managePasswords?userId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
                </c:if>
                <c:if test="${!removed && canManageExternalAccess && memberCanAccessExternalChannels}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageExternalAccess"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="manageExternalAccess?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
                </c:if>
                <c:forEach var="entry" items="${countByRecordType}">
					<c:set var="recordType" value="${entry.key}" />
					<c:set var="recordCount" value="${entry.value}" />
                    <cyclos:cell width="35%" className="label">${recordType.label} (${recordCount})</cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="${cyclos:name(recordType.layout) == 'FLAT' ? 'flatMemberRecords' : 'searchMemberRecords'}?global=false&elementId=${member.id}&typeId=${recordType.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
	            </c:forEach>             
				<c:if test="${!removed && (cyclos:granted(BrokerPermission.DOCUMENTS_VIEW) || cyclos:granted(BrokerPermission.DOCUMENTS_VIEW_MEMBER) || cyclos:granted(BrokerPermission.DOCUMENTS_MANAGE_MEMBER))}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.viewDocuments"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="selectDocument?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${!removed && cyclos:granted(BrokerPermission.MEMBERS_MANAGE_CONTRACTS)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.listBrokerCommissionContracts"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="listBrokerCommissionContracts?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
				<c:if test="${cyclos:granted(BrokerPermission.MEMBER_SMS_VIEW)}">
	                <cyclos:cell width="35%" className="label"><bean:message key="profile.action.smsLogs"/></cyclos:cell>
	                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="searchSmsLogs?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
	            </c:if>
                <c:if test="${cyclos:granted(BrokerPermission.REPORTS_VIEW)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.activities"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="activities?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
                </c:if>	            
	            <c:if test="${!removed && cyclos:granted(BrokerPermission.CARDS_VIEW) && hasCardType && byBroker}">
	                <cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageCards"/></cyclos:cell>
	                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="searchCards?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>	                
	            </c:if>
	            <c:if test="${!removed && cyclos:granted(BrokerPermission.POS_VIEW)}">
	                <cyclos:cell width="35%" className="label"><bean:message key="profile.action.memberPos"/></cyclos:cell>
	                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="memberPos?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
	                <c:set var="show" value="${true}"/>
	            </c:if>
            </cyclos:layout>
       	</td>
   	</tr>
</table>
