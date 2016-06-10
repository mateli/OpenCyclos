<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<%-- The admin can manage password if any of the below permissions are granted --%>
<c:set var="canManagePasswords" value="${cyclos:granted(AdminMemberPermission.ACCESS_CHANGE_PASSWORD)}"/>
<c:set var="canManageExternalAccess" value="${cyclos:granted(AdminMemberPermission.ACCESS_CHANGE_PIN) || cyclos:granted(AdminMemberPermission.ACCESS_CHANGE_CHANNELS_ACCESS)}" />
<c:set var="managePasswordsKey" value="profile.action.manageLoginPassword"/>
<c:if test="${member.group.memberSettings.sendPasswordByEmail && cyclos:granted(AdminMemberPermission.ACCESS_RESET_PASSWORD)}">
	<c:set var="canManagePasswords" value="${true}"/>
</c:if>
<c:if test="${member.group.basicSettings.transactionPassword.used && cyclos:granted(AdminMemberPermission.ACCESS_TRANSACTION_PASSWORD)}">
	<c:set var="canManagePasswords" value="${true}"/>
	<c:set var="managePasswordsKey" value="profile.action.managePasswords"/>
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="profile.action.title" arg0="${member.name}"/></td>
        <cyclos:help page="profiles#actions_for_member_by_admin" />
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
			<cyclos:layout className="defaultTable" columns="4">
				
				<c:set var="show" value="${false}"/>
				<c:set var="contents">
					<tr>
						<td colspan="4">
							<fieldset>
							<legend><bean:message key="profile.action.accessActions"/></legend>
							<cyclos:layout className="defaultTable" columns="4">
						   	    <c:if test="${disabledLogin && cyclos:granted(AdminMemberPermission.ACCESS_ENABLE_LOGIN)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.allowLogin"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="allowUserLogin?userId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
						        </c:if>
						   	    <c:if test="${isLoggedIn && cyclos:granted(AdminMemberPermission.ACCESS_DISCONNECT)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.disconnect"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="disconnectUser?userId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
						        </c:if>
				                <c:if test="${!removed && canManagePasswords}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="${managePasswordsKey}"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="managePasswords?userId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
				                </c:if>
				                <c:if test="${!removed && canManageExternalAccess && memberCanAccessExternalChannels}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageExternalAccess"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="manageExternalAccess?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
				                </c:if>
				               	<c:if test="${cyclos:granted(AdminMemberPermission.MEMBERS_CHANGE_GROUP)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.changeGroup"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="changeMemberGroup?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
				                </c:if>
						        <c:if test="${cyclos:granted(AdminMemberPermission.SMS_VIEW)}">
					                <cyclos:cell width="35%" className="label"><bean:message key="profile.action.smsLogs"/></cyclos:cell>
					                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="searchSmsLogs?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
					                <c:set var="show" value="${true}"/>
					            </c:if>
					            <c:if test="${!removed && cyclos:granted(AdminMemberPermission.CARDS_VIEW) && hasCardType}">
					                <cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageCards"/></cyclos:cell>
					                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="searchCards?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
					                <c:set var="show" value="${true}"/>
					            </c:if>
					            <c:if test="${!removed && cyclos:granted(AdminMemberPermission.POS_VIEW)}">
					                <cyclos:cell width="35%" className="label"><bean:message key="profile.action.memberPos"/></cyclos:cell>
					                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="memberPos?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
					                <c:set var="show" value="${true}"/>
					            </c:if>
							</cyclos:layout>
							</fieldset>
						</td>
					</tr>
				</c:set>
				<c:if test="${show}">
					${contents}
				</c:if>	

				<c:set var="show" value="${false}"/>
				<c:set var="contents">
					<tr>
						<td colspan="4">
							<fieldset>
							<legend><bean:message key="profile.action.preferencesActions"/></legend>
							<cyclos:layout className="defaultTable" columns="4">
								<c:if test="${!removed && cyclos:granted(AdminMemberPermission.PREFERENCES_MANAGE_NOTIFICATIONS)}">
									<cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageNotifications"/></cyclos:cell>
									<cyclos:cell align="left"><input type="button" class="linkButton" linkURL="notificationPreferences?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
									<c:set var="show" value="${true}"/>
								</c:if>
							</cyclos:layout>
							</fieldset>
						</td>
					</tr>
				</c:set>
				<c:if test="${show}">
					${contents}
				</c:if>
				
				<c:set var="show" value="${false}"/>
				<c:set var="contents">
					<tr>
						<td colspan="4">
							<fieldset>
							<legend><bean:message key="profile.action.advertisementsActions"/></legend>
							<cyclos:layout className="defaultTable" columns="4">
								<c:if test="${!removed && cyclos:granted(AdminMemberPermission.ADS_VIEW)}">
									<cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageAds"/></cyclos:cell>
									<cyclos:cell align="left"><input type="button" class="linkButton" linkURL="memberAds?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
									<c:set var="show" value="${true}"/>
								</c:if>
							</cyclos:layout>
							</fieldset>
						</td>
					</tr>
				</c:set>
				<c:if test="${show}">
					${contents}
				</c:if>
                
                <c:set var="show" value="${false}"/>
				<c:set var="contents">
	                <tr>
						<td colspan="4">
							<fieldset>
							<legend><bean:message key="profile.action.accountsAction"/></legend>
							<cyclos:layout className="defaultTable" columns="4">
								<c:if test="${hasAccounts && cyclos:granted(AdminMemberPermission.ACCOUNTS_INFORMATION)}">
									<cyclos:cell width="35%" className="label"><bean:message key="profile.action.accountInformation"/></cyclos:cell>
									<cyclos:cell align="left"><input type="button" class="linkButton" linkURL="accountOverview?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
									<c:set var="show" value="${true}"/>
								</c:if>
								<c:if test="${!removed && hasAccounts && cyclos:granted(AdminMemberPermission.ACCOUNTS_SCHEDULED_INFORMATION)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.scheduledPayments"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="searchScheduledPayments?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
								</c:if>
								<c:if test="${hasAccounts && cyclos:granted(AdminMemberPermission.ACCOUNTS_AUTHORIZED_INFORMATION)}">
									<cyclos:cell width="35%" className="label"><bean:message key="profile.action.transferAuthorizations"/></cyclos:cell>
									<cyclos:cell align="left"><input type="button" class="linkButton" linkURL="searchTransferAuthorizations?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
									<c:set var="show" value="${true}"/>
								</c:if>
								<c:if test="${!removed && hasAccounts && cyclos:granted(AdminMemberPermission.PAYMENTS_PAYMENT)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.paymentFromSystem"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="payment?to=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
								</c:if>
								<c:if test="${!removed && hasAccounts && cyclos:granted(AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_MEMBER)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.paymentAsMemberToMember"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="payment?selectMember=true&from=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
								</c:if>
								<c:if test="${!removed && hasAccounts && cyclos:granted(AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_SYSTEM)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.paymentAsMemberToSystem"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="payment?toSystem=true&from=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
								</c:if>
								<c:if test="${!removed && hasAccounts && cyclos:granted(AdminMemberPermission.PAYMENTS_PAYMENT_AS_MEMBER_TO_SELF)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.memberSelfPayment"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="selfPayment?from=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
								</c:if>
								<c:if test="${!removed && hasAccounts && cyclos:granted(AdminMemberPermission.INVOICES_VIEW)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.invoices"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="searchInvoices?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
								</c:if>
								<c:if test="${!removed && hasAccounts && cyclos:granted(AdminMemberPermission.INVOICES_SEND)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.invoiceFromSystem"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="sendInvoice?to=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
								</c:if>
								<c:if test="${!removed && hasAccounts && cyclos:granted(AdminMemberPermission.INVOICES_SEND_AS_MEMBER_TO_MEMBER)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.invoiceAsMemberToMember"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="sendInvoice?selectMember=true&from=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
								</c:if>
								<c:if test="${!removed && hasAccounts && cyclos:granted(AdminMemberPermission.INVOICES_SEND_AS_MEMBER_TO_SYSTEM)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.invoiceAsMemberToSystem"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="sendInvoice?toSystem=true&from=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
								</c:if>
					            <c:if test="${!removed && hasAccounts && cyclos:granted(AdminMemberPermission.ACCOUNTS_CREDIT_LIMIT)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.creditLimit"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="editCreditLimit?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
					            </c:if>
							</cyclos:layout>
							</fieldset>
						</td>
					</tr>
				</c:set>
				<c:if test="${show}">
					${contents}
				</c:if>
                
				<tr>
					<td colspan="4">
						<fieldset>
						<legend><bean:message key="profile.action.memberInfoActions"/></legend>
						<cyclos:layout className="defaultTable" columns="4">
							<c:if test="${!removed && cyclos:granted(AdminMemberPermission.REPORTS_VIEW)}">
			                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.activities"/></cyclos:cell>
			                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="activities?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
							</c:if>
							<c:forEach var="entry" items="${countByRecordType}">
								<c:set var="recordType" value="${entry.key}" />
								<c:set var="recordCount" value="${entry.value}" />
			                    <cyclos:cell width="35%" className="label">${recordType.label} (${recordCount})</cyclos:cell>
			                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="${cyclos:name(recordType.layout) == 'FLAT' ? 'flatMemberRecords' : 'searchMemberRecords'}?global=false&elementId=${member.id}&typeId=${recordType.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				            </c:forEach>
							<c:if test="${!removed && cyclos:granted(AdminMemberPermission.REFERENCES_VIEW)}">
			                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageReferences"/></cyclos:cell>
			                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="references?nature=GENERAL&memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
							</c:if>
			               	<c:if test="${hasTransactionFeedbacks && cyclos:granted(AdminMemberPermission.TRANSACTION_FEEDBACKS_VIEW)}">
			                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.transactionFeedbacks"/></cyclos:cell>
			                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="references?nature=TRANSACTION&memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
			                </c:if>
			                <c:if test="${!removed && cyclos:granted(AdminMemberPermission.MESSAGES_SEND_TO_MEMBER)}">
				                <cyclos:cell width="35%" className="label"><bean:message key="profile.action.message"/></cyclos:cell>
				                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="sendMessage?toMemberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
							</c:if>
			                <c:if test="${!removed && not empty member.email}">
								<cyclos:cell width="35%" className="label"><bean:message key="profile.action.mail"/></cyclos:cell>
				                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="mailto:${member.email}" value="<bean:message key="global.submit"/>"></cyclos:cell>
			                </c:if>
			                <cyclos:cell width="35%" className="label"><bean:message key="profile.action.viewDocuments"/></cyclos:cell>
			                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="selectDocument?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
						</cyclos:layout>
						</fieldset>
					</td>
				</tr>
			
				<c:set var="show" value="${false}"/>
				<c:set var="contents">
					<tr>
						<td colspan="4">
							<fieldset>
							<legend><bean:message key="profile.action.brokeringActions"/></legend>
							<cyclos:layout className="defaultTable" columns="4">
				                <c:if test="${!removed && cyclos:granted(AdminMemberPermission.BROKERINGS_CHANGE_BROKER)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.changeBroker"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="changeBroker?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
					            </c:if>
					            <c:if test="${member.group.broker}">
					                <c:if test="${!removed && member.group.broker && cyclos:granted(AdminMemberPermission.BROKERINGS_VIEW_MEMBERS)}">
					                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.viewBrokerings"/></cyclos:cell>
					                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="listBrokerings?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
					                    <c:set var="show" value="${true}"/>
					                </c:if>
					                <c:if test="${!removed && member.group.broker && cyclos:granted(AdminMemberPermission.BROKERINGS_MANAGE_COMMISSIONS)}">
					                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageBrokerCommissions"/></cyclos:cell>
					                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="manageBrokerCommissions?brokerId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
					                    <c:set var="show" value="${true}"/>
					                </c:if>
					            </c:if>
							</cyclos:layout>
							</fieldset>
						</td>
					</tr>
				</c:set>
				<c:if test="${show}">
					${contents}
				</c:if>
				
				<c:set var="show" value="${false}"/>
				<c:set var="contents">
					<tr>
						<td colspan="4">
							<fieldset>
							<legend><bean:message key="profile.action.loansActions"/></legend>
							<cyclos:layout className="defaultTable" columns="4">
								<c:if test="${!removed && hasAccounts && cyclos:granted(AdminMemberPermission.LOANS_VIEW)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.manageLoans"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="searchLoans?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
								</c:if>
								<c:if test="${!removed && hasAccounts && cyclos:granted(AdminMemberPermission.LOANS_GRANT)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.grantLoan"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="grantLoan?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
								</c:if>
								<c:if test="${!removed && cyclos:granted(AdminMemberPermission.LOAN_GROUPS_VIEW)}">
				                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.loanGroups"/></cyclos:cell>
				                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="memberLoanGroups?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				                    <c:set var="show" value="${true}"/>
				                </c:if>
							</cyclos:layout>
							</fieldset>
						</td>
					</tr>
				</c:set>
				<c:if test="${show}">
					${contents}
				</c:if>
				
            </cyclos:layout>
       	</td>
   	</tr>
</table>
