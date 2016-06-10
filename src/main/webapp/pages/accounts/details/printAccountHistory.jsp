<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<div class="printTitle"><bean:message key="accountHistory.title.print" arg0="${type.name}"/></div>

<table width="100%" cellspacing="0">
	<c:set var="hasCreditLimit" value="${status.creditLimit > 0}"/>
	<c:set var="hasReservedAmount" value="${status.reservedAmount > 0}"/>
	<c:if test="${not empty owner}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="account.owner"/>:&nbsp;</td>
			<td class="printData" colspan="5">${owner.username} - ${owner.name}</td>
		</tr>
	</c:if>
	<c:if test="${not empty type}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="account.type"/>:&nbsp;</td>
			<td class="printData" colspan="${hasCreditLimit ? 1 : 5}">${type.name}</td>
			<c:if test="${hasCreditLimit}">
				<td class="printLabel" width="20%"><bean:message key="account.creditLimit"/>:&nbsp;</td>
				<td class="printData" colspan="3"><cyclos:format number="${status.creditLimit}" unitsPattern="${unitsPattern}" /></td>
			</c:if>
		</tr>
		<tr>
			<td class="printLabel" width="20%"><bean:message key="account.balance"/>:&nbsp;</td>
			<td class="printData" colspan="${colspan}"><cyclos:format number="${status.balance}" unitsPattern="${unitsPattern}" /></td>
			<c:if test="${hasReservedAmount}">
				<td class="printLabel" width="20%"><bean:message key="account.reservedAmount"/>:&nbsp;</td>
				<td class="printData"><cyclos:format number="${status.reservedAmount}"/></td>
			</c:if>
			<c:if test="${hasCreditLimit or hasReservedAmount}">
				<td class="printLabel" width="20%"><bean:message key="account.availableBalance"/>:&nbsp;</td>
				<td class="printData" colspan="${hasReservedAmount ? 1 : 3}"><cyclos:format number="${status.availableBalance}" unitsPattern="${unitsPattern}" /></td>
			</c:if>
		</tr>
	</c:if>
	<c:if test="${not empty query.paymentFilter}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="accountHistory.filter"/>:&nbsp;</td>
			<td class="printData" colspan="5">${query.paymentFilter.name}</td>
		</tr>
	</c:if>
	<c:if test="${not empty query.description}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="transfer.description"/>:&nbsp;</td>
			<td class="printData" colspan="5">${query.description}</td>
		</tr>
	</c:if>
	<c:if test="${not empty query.member}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="member.member"/>:&nbsp;</td>
			<td class="printData" colspan="5">${query.member.username} - ${query.member.name}</td>
		</tr>
	</c:if>
	<c:if test="${not empty query.by}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="transfer.by"/>:&nbsp;</td>
			<td class="printData" colspan="5">${query.by.username} - ${query.by.name}</td>
		</tr>
	</c:if>
	<c:if test="${not empty query.period.begin || not empty query.period.end}">
		<tr>
		  	<c:if test="${not empty query.period.begin}">
	 			<td class="printLabel"><bean:message key="accountHistory.period.begin"/>:&nbsp;</td>
				<td class="printData" colspan="${empty query.period.end ? 5 : 1}"><cyclos:format rawDate="${query.period.begin}"/></td>
		   	</c:if>
	  		<c:if test="${not empty query.period.end}">
	 			<td class="printLabel"><bean:message key="accountHistory.period.end"/>:&nbsp;</td>
				<td class="printData" colspan="${empty query.period.begin ? 5 : 3 }"><cyclos:format rawDate="${query.period.end}"/></td>
		   	</c:if>
	   	</tr>
		<tr>
		  	<c:if test="${not empty query.period.begin}">
				<td class="printLabel" width="20%"><bean:message key="accountHistory.initialBalance"/>:&nbsp;</td>
				<td class="printData" colspan="${empty query.period.end ? 5 : 1}"><cyclos:format number="${initialBalance}" unitsPattern="${unitsPattern}" /></td>
			</c:if>
	  		<c:if test="${not empty query.period.end}">
				<td class="printLabel" width="20%"><bean:message key="accountHistory.finalBalance"/>:&nbsp;</td>
				<td class="printData" colspan="${empty query.period.begin ? 5 : 3 }"><cyclos:format number="${finalBalance}" unitsPattern="${unitsPattern}" /></td>
			</c:if>
		</tr>
	</c:if>
	<c:if test="${not empty query.transactionNumber}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="transfer.transactionNumber"/>:&nbsp;</td>
			<td class="printData" colspan="5">${query.transactionNumber}</td>
		</tr>
	</c:if>
	<c:forEach var="entry" items="${customValueFilters}">
		<tr>
			<td class="printLabel" width="20%">${entry.key}:&nbsp;</td>
			<td class="printData" colspan="5">${entry.value}</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="6"><hr class="print"/></td>
	</tr>
	<tr>
		<td nowrap="nowrap" class="printLabel" width="20%"><bean:message key="accountHistory.credits.count"/>:&nbsp;</td>
		<td class="printData" id="creditCount"></td>
		<td nowrap="nowrap" class="printLabel" width="20%"><bean:message key="accountHistory.debits.count"/>:&nbsp;</td>
		<td class="printData" id="debitCount" colspan="3"></td>
	</tr>
	<tr>
		<td nowrap="nowrap" class="printLabel" width="20%"><bean:message key="accountHistory.credits.amount"/>:&nbsp;</td>
		<td class="printData" id="creditAmount"></td>
		<td nowrap="nowrap" class="printLabel" width="20%"><bean:message key="accountHistory.debits.amount"/>:&nbsp;</td>
		<td class="printData" id="debitAmount" colspan="3"></td>
	</tr>
	<tr>
		<td colspan="6">&nbsp;</td>
	</tr>
	<tr>
		<td  nowrap="nowrap" class="printLabel" width="20%"><bean:message key="accountHistory.creditsDebitsBalance.amount"/>:&nbsp;</td>
		<td class="printData" id="creditsDebitsBalanceAmount" colspan="5"></td>
	</tr>	
</table>

<br/>

<c:set var="creditCount" value="${0}" />
<c:set var="debitCount" value="${0}" />
<c:set var="creditAmount" value="${0}" />
<c:set var="debitAmount" value="${0}" />
<c:set var="creditsDebitsBalanceAmount" value="${0}" />
<c:set var="memberProperty" value="${localSettings.memberResultDisplay.property}"/>
<c:set var="colspan" value="${4}" />

<table width="100%" class="printBorder">
	<tr>
		<td class="printLabel" style="text-align:center" width="15%"><bean:message key="transfer.date"/></td>
		<td class="printLabel" style="text-align:left" width="25%"><bean:message key="transfer.fromOrTo"/></td>
		<td class="printLabel" style="text-align:left"><bean:message key="transfer.description"/></td>
		<c:forEach var="field" items="${customFieldsForList}">
			<td class="printLabel" style="text-align:left">${field.name}</td>
			<c:set var="colspan" value="${colspan + 1}" />
		</c:forEach>
		<td class="printLabel" style="text-align:right" width="15%"><bean:message key="transfer.amount"/></td>
		<c:if test="${not empty localSettings.transactionNumber && localSettings.transactionNumber.valid}">
			<td class="printLabel" style="text-align:center"><bean:message key="transfer.transactionNumber"/></td>
			<c:set var="colspan" value="${colspan + 1}" />
		</c:if>
	</tr>
	<c:forEach var="entry" items="${accountHistory}" varStatus="counter">
	<c:if test="${ localSettings.maxIteratorResults == 0 || counter.index < localSettings.maxIteratorResults }">
		<c:set var="transfer" value="${entry.transfer}"/>
		<c:set var="amount" value="${transfer.actualAmount}"/>
		<c:set var="related" value="${entry.related}"/>
		<c:set var="className" value="${entry.debit ? 'ClassColorDebit' : 'ClassColorCredit'}"/>

		<c:choose><c:when test="${entry.debit}">
			<c:set var="debitCount" value="${debitCount + 1}" />
			<c:set var="debitAmount" value="${debitAmount + amount}" />
			<c:set var="creditsDebitsBalanceAmount" value="${creditsDebitsBalanceAmount - amount}" />
		</c:when><c:otherwise>
			<c:set var="creditCount" value="${creditCount + 1}" />
			<c:set var="creditAmount" value="${creditAmount + amount}" />
			<c:set var="creditsDebitsBalanceAmount" value="${creditsDebitsBalanceAmount + amount}" />			
		</c:otherwise></c:choose>
		
		<tr>
			<td style="text-align:center" class="printData"><cyclos:format date="${transfer.actualDate}"/></td>
			<td style="text-align:left"   class="printData">
				<c:set var="relatedNature" value="${cyclos:name(related.nature)}"/>
				<c:choose><c:when test="${relatedNature == 'SYSTEM'}">
					${related.ownerName}
				</c:when><c:otherwise>
					<cyclos:profile elementId="${entry.relatedMemberId}" onlyShowLabel="true"/>
				</c:otherwise></c:choose>						
			</td>
			<td style="text-align:left;${transfer.chargedBackBy != null ? 'font-style:italic;text-decoration:line-through;' : ''}" class="printData">
				<cyclos:truncate value="${transfer.description}"/>
			</td>
			<c:forEach var="field" items="${customFieldsForList}">
				<td class="printData" style="text-align:left"><cyclos:customField field="${field}" collection="${transfer.customValues}" textOnly="true" editable="false" /></td>
			</c:forEach>
			<td style="text-align:right" class="printData ${className}"><cyclos:format number="${amount * entry.signal}" forceSignal="true"/></td>
			<c:if test="${not empty localSettings.transactionNumber && localSettings.transactionNumber.valid}">
				<td style="text-align:center" class="printData">${transfer.transactionNumber}</td>
			</c:if>
		</tr>
	
		<cyclos:clearCache />
		</c:if>		
		<c:if test="${ localSettings.maxIteratorResults > 0 && counter.index == localSettings.maxIteratorResults }">
		   	<tr>
   				<td class="tdPrintHeader" colspan="${colspan}"><bean:message key="reports.print.limitation" arg0="${localSettings.maxIteratorResults}"/></td>
   			</tr>
   		</c:if>
	</c:forEach>
</table>

<script>
$('debitAmount').innerHTML = "<cyclos:format number="${debitAmount}" />";
$('debitCount').innerHTML = "${debitCount}";
$('creditAmount').innerHTML = "<cyclos:format number="${creditAmount}" />";
$('creditsDebitsBalanceAmount').innerHTML = "<cyclos:format number="${creditsDebitsBalanceAmount}" />";
$('creditCount').innerHTML = "${creditCount}";
</script>