<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<div class="printTitle"><bean:message key="scheduledPayments.title.print" arg0="${type.name}"/></div>


<table width="100%" cellspacing="0">
	<tr>
		<td class="printLabel" width="20%"><bean:message key="scheduledPayments.searchType"/>:&nbsp;</td>
		<td class="printData" colspan="1"><bean:message key="scheduledPayments.searchType.${query.searchType}"/></td>
		<td class="printLabel" width="20%"><bean:message key="payment.status"/>:&nbsp;</td>
		<td class="printData" colspan="3"><bean:message key="payment.statusGroup.${query.statusGroup}"/></td>
	</tr>
	<c:if test="${not empty query.accountType}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="scheduledPayment.accountType"/>:&nbsp;</td>
			<td class="printData" colspan="5">${query.accountType.name}</td>
		</tr>
	</c:if>
	<c:if test="${not empty query.member}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="member.member"/>:&nbsp;</td>
			<td class="printData" colspan="5">${query.member.username} - ${query.member.name}</td>
		</tr>
	</c:if>
	<c:if test="${not empty query.period}">
		<tr>
		  	<c:if test="${not empty query.period.begin}">
	 			<td class="printLabel" width="20%"><bean:message key="accountHistory.period.begin"/>:&nbsp;</td>
				<td class="printData" colspan="${empty query.period.end ? 5 : 1}"><cyclos:format date="${query.period.begin}"/></td>
		   	</c:if>
	  		<c:if test="${not empty query.period.end}">
	 			<td class="printLabel" width="20%"><bean:message key="accountHistory.period.end"/>:&nbsp;</td>
				<td class="printData" colspan="${empty query.period.begin ? 5 : 3 }"><cyclos:format date="${query.period.end}"/></td>
		   	</c:if>
	   	</tr>
	</c:if>
	<tr>
		<td class="printLabel" width="20%"><bean:message key="scheduledPayments.totalAmount"/>:&nbsp;</td>
		<td class="printData" width="13%" id="totalAmount"></td>
		<td class="printLabel" width="20%"><bean:message key="scheduledPayments.totalPaid"/>:&nbsp;</td>
		<td class="printData" width="13%" id="totalPaid"></td>
		<td class="printLabel" width="20%"><bean:message key="scheduledPayments.totalRemaining"/>:&nbsp;</td>
		<td class="printData" width="14% "id="totalRemaining"></td>
	</tr>
</table>


<c:set var="totalAmount" value="${0}" />
<c:set var="totalPaid" value="${0}" />

<table width="100%" class="printBorder">
	<tr>
		<td class="printLabel" style="text-align:center" width="15%"><bean:message key="scheduledPayment.date"/></td>
		<td class="printLabel" style="text-align:center" width="20%"><bean:message key="transfer.from"/></td>
		<td class="printLabel" style="text-align:center" width="20%"><bean:message key="member.username"/></td>
		<td class="printLabel" style="text-align:center" width="15%"><bean:message key="scheduledPayment.amount"/></td>
		<td class="printLabel" style="text-align:center" width="15%"><bean:message key="scheduledPayment.parcels"/></td>
		<td class="printLabel" style="text-align:center" width="15%"><bean:message key="payment.status"/></td>
	</tr>
	<c:forEach var="payment" items="${payments}">
		
		<c:set var="relatedAccount" value="${payment.fromOwner == accountOwner ? payment.to : payment.from}"/>
		<c:set var="processedPayments" value="${payment.processedPaymentCount}"/>
		<c:set var="totalPayments" value="${fn:length(payment.transfers)}"/>
		
		<c:set var="totalAmount" value="${totalAmount + payment.amount}" />
		<c:set var="totalPaid" value="${totalPaid + payment.processedPaymentAmount}" />
		<c:if test="${ localSettings.maxIteratorResults == 0 || counter.index < localSettings.maxIteratorResults }">
		<tr>
			<td style="text-align:center" class="printData"><cyclos:format date="${payment.actualDate}"/></td>
			<td style="text-align:center" class="printData">${payment.from.type.name}</td>
			<td style="text-align:center" class="printData">
				<c:choose><c:when test="${fn:contains(relatedAccount['class'].name, 'System')}">
					${relatedAccount.ownerName}
				</c:when><c:otherwise>
					${relatedAccount.ownerName}
				</c:otherwise></c:choose>
			</td>
			<td style="text-align:center" class="printData"><cyclos:format number="${payment.amount}" /></td>
			<td style="text-align:center" class="printData"><c:if test="${processedPayments < totalPayments}">${processedPayments} / </c:if>${totalPayments}</td>
			<td style="text-align:center" class="printData"><bean:message key="payment.status.${payment.status}"/></td>
		</tr>
		<cyclos:clearCache />
		</c:if>		
		<c:if test="${ localSettings.maxIteratorResults > 0 && counter.index == localSettings.maxIteratorResults }">
		   	<tr>
   				<td class="tdPrintHeader" colspan="4"><bean:message key="reports.print.limitation" arg0="${localSettings.maxIteratorResults}"/></td>
   			</tr>
   		</c:if>
	</c:forEach>
	<c:set var="totalRemaining" value="${totalAmount - totalPaid}" />
</table>

<script>
$('totalAmount').innerHTML = "<cyclos:format number="${totalAmount}" />";
$('totalPaid').innerHTML = "<cyclos:format number="${totalPaid}" />";
$('totalRemaining').innerHTML = "<cyclos:format number="${totalRemaining}" />";
</script>
