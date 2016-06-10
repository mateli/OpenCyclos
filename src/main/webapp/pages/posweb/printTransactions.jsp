<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<div class="printTitle"><bean:message key="posweb.searchTransactions.title"/></div>
 
<table width="100%" cellspacing="0">
	<tr>
		<td class="printLabel" width="20%"><bean:message key="transfer.date"/>:&nbsp;</td>
		<td class="printData"><cyclos:format rawDate="${date}" /></td>
	</tr>
	<tr>
		<td class="printLabel" width="20%"><bean:message key="member.member"/>:&nbsp;</td>
		<td class="printData">${owner.username} - ${owner.name}</td>
	</tr>
</table>

<br/>

<div class="printTitle"><bean:message key="posweb.searchTransactions.title.transactions"/></div>
<c:choose><c:when test="${empty transfers}">
	<span class="printData"></span><bean:message key="posweb.searchTransactions.noTransactions"/></td>
</c:when><c:otherwise>
	<table width="100%" class="printBorder">
		<tr>
			<td class="printLabel" style="text-align:center"><bean:message key="transfer.hour"/></td>
			<td class="printLabel" style="text-align:center"><bean:message key="transfer.fromOrTo"/></td>
			<c:if test="${not empty localSettings.transactionNumber && localSettings.transactionNumber.valid}">
				<td class="printLabel" style="text-align:center"><bean:message key="transfer.transactionNumber"/></td>
			</c:if>
			<td class="printLabel" style="text-align:right"><bean:message key="transfer.amount"/></td>
		</tr>
		<c:forEach var="entry" items="${transfers}">
			<c:set var="transfer" value="${entry.payment}" />
			<tr>
				<td style="text-align:center" class="printData"><cyclos:format time="${transfer.actualDate}" /></td>
				<td style="text-align:center" class="printData">${entry.relatedAccount.ownerName}</td>
				<c:if test="${localSettings.transactionNumber.valid}">
					<td style="text-align:center" class="printData">${transfer.transactionNumber}</td>
				</c:if>
				<td style="text-align:right" class="printData"><cyclos:format number="${entry.amount}" unitsPattern="${transfer.from.type.currency.pattern}" /></td>
			</tr>
		</c:forEach>
	</table>
</c:otherwise></c:choose>

<c:if test="${not empty scheduledPayments}">
	<br>
	<div class="printTitle"><bean:message key="posweb.searchTransactions.title.scheduledPayments"/></div>
	<table width="100%" class="printBorder">
		<tr>
			<td class="printLabel" style="text-align:center"><bean:message key="transfer.hour"/></td>
			<td class="printLabel" style="text-align:center"><bean:message key="transfer.fromOrTo"/></td>
			<td class="printLabel" style="text-align:center"><bean:message key="scheduledPayment.parcels"/></td>
			<td class="printLabel" style="text-align:center"><bean:message key="transfer.firstPaymentDate"/></td>
			<td class="printLabel" style="text-align:right"><bean:message key="scheduledPayment.amount"/></td>
		</tr>
		<c:forEach var="entry" items="${scheduledPayments}">
			<c:set var="sched" value="${entry.payment}" />
			<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
				<td style="text-align:center" class="printData"><cyclos:format time="${sched.date}" /></td>
				<td style="text-align:center" class="printData">${entry.relatedAccount.ownerName}</td>
				<td style="text-align:center" class="printData">${fn:length(sched.transfers)}</td>
				<td style="text-align:center" class="printData"><cyclos:format rawDate="${sched.actualDate}" /></td>
				<td style="text-align:right" class="printData"><cyclos:format number="${entry.amount}" unitsPattern="${sched.from.type.currency.pattern}" /></td>
			</tr>
		</c:forEach>
	</table>
</c:if>