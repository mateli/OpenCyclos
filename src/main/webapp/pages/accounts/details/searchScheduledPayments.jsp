<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/details/searchScheduledPayments.js" />

<ssl:form method="POST" action="${formAction}">
<html:hidden property="memberId" />

<c:choose><c:when test="${not empty memberId}">
	<c:set var="titleKey" value="scheduledPayments.title.list.of"/>
</c:when><c:otherwise>
	<c:set var="titleKey" value="scheduledPayments.title.list"/>
</c:otherwise></c:choose>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="${titleKey}" arg0="${fn:contains(accountOwner['class'].name, 'System') ? '' : accountOwner.name}"/></td>
		<cyclos:help page="payments#scheduled_payments_by_${isAdmin ? 'admin' : 'member'}"/>
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<cyclos:layout columns="4" className="defaultTable">
 
				<c:if test="${!isAdmin}">
					<cyclos:cell className="label" width="20%"><bean:message key="scheduledPayments.searchType"/></cyclos:cell>
					<cyclos:cell nowrap="nowrap" colspan="3">
						<c:forEach var="searchType" items="${searchTypes}">
							<span><label>
								<html:radio style="vertical-align:middle" styleClass="radio searchType" property="query(searchType)" value="${searchType}" />
								<span style="vertical-align:middle"><bean:message key="scheduledPayments.searchType.${searchType}"/></span>
							</label></span>
							&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</cyclos:cell>
				</c:if>

				<c:if test="${fn:length(accountTypes) > 1}">
					<cyclos:cell className="label"><bean:message key="scheduledPayment.accountType"/></cyclos:cell>
					<cyclos:cell nowrap="nowrap" colspan="3">
						<html:select property="query(accountType)" styleId="accountSelect">
							<option value=""><bean:message key="accountType.all"/></option>
							<c:forEach var="accountType" items="${accountTypes}">
								<html:option value="${accountType.id}">${accountType.name}</html:option>
							</c:forEach>
						</html:select>
					</cyclos:cell>
				</c:if>

				<cyclos:cell className="label" width="20%"><bean:message key="payment.status"/></cyclos:cell>
				<cyclos:cell nowrap="nowrap" colspan="3">
					<html:select property="query(statusGroup)" styleId="statusSelect">
						<c:forEach var="statusGroup" items="${statusGroups}">
							<html:option value="${statusGroup}"><bean:message key="payment.statusGroup.${statusGroup}"/></html:option>
						</c:forEach>
					</html:select>
				</cyclos:cell>

				<cyclos:cell className="label" width="20%"><bean:message key="member.username"/></cyclos:cell>
				<cyclos:cell>
					<html:hidden styleId="memberId" property="query(member)"/>
					<input id="memberUsername" class="full" value="${query.member.username}">
					<div id="membersByUsername" class="autoComplete"></div>
				</cyclos:cell>
				<cyclos:cell className="label" width="20%"><bean:message key="member.memberName"/></cyclos:cell>
				<cyclos:cell>
					<input id="memberName" class="full" value="${query.member.name}">
					<div id="membersByName" class="autoComplete"></div>
				</cyclos:cell>
					
				<cyclos:cell className="label"><bean:message key="accountHistory.period.begin"/></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text property="query(period).begin" styleClass="date small"/></cyclos:cell>
				<cyclos:cell className="label"><bean:message key="accountHistory.period.end"/></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text property="query(period).end" styleClass="date small"/></cyclos:cell>
				
   				<tr>
   					<td align="right" colspan="4"><input type="submit" class="button" value="<bean:message key="global.search"/>"></td>
   				</tr>
			</cyclos:layout>
		</td>
	</tr>
</table>
</ssl:form>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		<td class="tdHelpIcon" align="right" width="10%" valign="middle" nowrap="nowrap">
			<img class="print" src="<c:url value="/pages/images/print.gif"/>" border="0">
			<cyclos:help page="payments#scheduled_payments_result" td="false"/>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="left">
			<table class="defaultTable">
				<tr>
					<th class="tdHeaderContents" width="15%"><bean:message key="scheduledPayment.date"/></th>
					<th class="tdHeaderContents" width="15%"><bean:message key="transfer.from"/></th>
					<th class="tdHeaderContents" width="20%"><bean:message key="member.member"/></th>
					<th class="tdHeaderContents" width="15%"><bean:message key="scheduledPayment.amount"/></th>
					<th class="tdHeaderContents" width="10%"><bean:message key="scheduledPayment.parcels"/></th>
					<th class="tdHeaderContents" width="20%"><bean:message key="payment.status"/></th>
					<th class="tdHeaderContents" width="5%">&nbsp;</th>
				</tr>
				<c:forEach var="payment" items="${payments}">
					<c:set var="relatedAccount" value="${payment.fromOwner == accountOwner ? payment.to : payment.from}"/>
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td align="center"><cyclos:format date="${payment.actualDate}"/></td>
						<td align="center">${payment.from.type.name}</td>
						<td align="center">
							<c:choose><c:when test="${fn:contains(relatedAccount['class'].name, 'System')}">
								${relatedAccount.ownerName}
							</c:when><c:otherwise>
								<cyclos:profile elementId="${relatedAccount.member.id}"/>
							</c:otherwise></c:choose>
						</td>
						<td align="center"><cyclos:format number="${payment.amount}" unitsPattern="${payment.type.from.currency.pattern}" /></td>
						<td align="center">
							<c:set var="processedPayments" value="${payment.processedPaymentCount}"/>
							<c:set var="totalPayments" value="${fn:length(payment.transfers)}"/>
							<c:if test="${processedPayments < totalPayments}">${processedPayments} / </c:if>${totalPayments}
						</td>
						<td align="center"><bean:message key="payment.status.${payment.status}"/></td>
						<td align="center"><img paymentId="${payment.id}" class="view" src="<c:url value="/pages/images/view.gif"/>"></td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<c:if test="${not empty memberId}">
			<td><input type="button" class="button" id="backButton" value="<bean:message key="global.back" />"/></td>
		</c:if>
		<td align="right"><cyclos:pagination items="${authorizations}"/></td>
	</tr>
</table>
