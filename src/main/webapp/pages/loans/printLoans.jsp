<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<div class="printTitle"><bean:message key="loan.title.print"/></div>
<cyclos:layout columns="4" className="printContent" width="100%">
	<c:if test="${not empty query.status}">
		<cyclos:cell className="printLabel" width="20%"><bean:message key="loan.status"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="30%"><bean:message key="loan.status.${query.status}"/></cyclos:cell>
	</c:if>
	<c:if test="${not empty query.transferType}">
		<cyclos:cell className="printLabel" width="20%"><bean:message key="transfer.type"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="30%">${query.transferType.name}</cyclos:cell>
	</c:if>
	<c:if test="${not empty query.member}">
		<cyclos:cell className="printLabel" width="20%"><bean:message key="member.member"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="30%">${query.member.username} - ${query.member.name}</cyclos:cell>
	</c:if>
	<c:if test="${not empty query.broker}">
		<cyclos:cell className="printLabel" width="20%"><bean:message key="member.broker"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="30%">${query.broker.username} - ${query.broker.name}</cyclos:cell>
	</c:if>
	<c:if test="${not empty query.loanGroup}">
		<cyclos:cell className="printLabel" width="20%"><bean:message key="loan.group"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="30%">${query.loanGroup.name}</cyclos:cell>
	</c:if>
    <c:forEach var="entry" items="${memberFieldValues}">
        <c:set var="field" value="${entry.field}"/>
		<c:set var="value" value="${entry.value.value}"/>
		<c:if test="${not empty value}">
			<cyclos:cell className="printLabel" width="20%">${field.name}:&nbsp;</cyclos:cell>
			<cyclos:cell className="printData" width="30%"><cyclos:customField field="${field}" textOnly="true" value="${value}"/></cyclos:cell>
		</c:if>
    </c:forEach>
    <c:forEach var="entry" items="${loanFieldValues}">
        <c:set var="field" value="${entry.field}"/>
        <c:set var="value" value="${entry.value.value}"/>
		<c:if test="${not empty value}">
			<cyclos:cell className="printLabel" width="20%">${field.name}:&nbsp;</cyclos:cell>
			<cyclos:cell className="printData" width="30%"><cyclos:customField field="${field}" textOnly="true" value="${value}"/></cyclos:cell>
		</c:if>
    </c:forEach>
	<c:if test="${not empty query.grantPeriod.begin || not empty query.grantPeriod.end}">
		<cyclos:cell className="printLabel" width="20%"><bean:message key="loan.grantDate"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="30%">
			<c:if test="${not empty query.grantPeriod.begin}">
				<span class="printLabel"><bean:message key="global.range.from"/>:</span>&nbsp;
				<cyclos:format date="${query.grantPeriod.begin}"/>&nbsp;
			</c:if>
			<c:if test="${not empty query.grantPeriod.end}">
				<span class="printLabel"><bean:message key="global.range.to"/>:</span>&nbsp;
				<cyclos:format date="${query.grantPeriod.end}"/>
			</c:if>
		</cyclos:cell>
	</c:if>
	<c:if test="${not empty query.expirationPeriod.begin || not empty query.expirationPeriod.end}">
		<cyclos:cell className="printLabel" width="20%"><bean:message key="loan.expirationDate"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="30%">
			<c:if test="${not empty query.expirationPeriod.begin}">
				<span class="printLabel"><bean:message key="global.range.from"/>:</span>&nbsp;
				<cyclos:format date="${query.expirationPeriod.begin}"/>&nbsp;
			</c:if>
			<c:if test="${not empty query.expirationPeriod.end}">
				<span class="printLabel"><bean:message key="global.range.to"/>:</span>&nbsp;
				<cyclos:format date="${query.expirationPeriod.end}"/>
			</c:if>
		</cyclos:cell>
	</c:if>
	<c:if test="${not empty query.paymentPeriod.begin || not empty query.paymentPeriod.end}">
		<cyclos:cell className="printLabel" width="20%"><bean:message key="loan.paymentDate"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="30%">
			<c:if test="${not empty query.paymentPeriod.begin}">
				<span class="printLabel"><bean:message key="global.range.from"/>:</span>&nbsp;
				<cyclos:format date="${query.paymentPeriod.begin}"/>&nbsp;
			</c:if>
			<c:if test="${not empty query.paymentPeriod.end}">
				<span class="printLabel"><bean:message key="global.range.to"/>:</span>&nbsp;
				<cyclos:format date="${query.paymentPeriod.end}"/>
			</c:if>
		</cyclos:cell>
	</c:if>
</cyclos:layout>
<hr class="print">
<table class="printContent">
	<tr>
		<td width="22%" class="printLabel"><bean:message key="loan.summary.loans"/>:</td>
		<td width="11%" class="printData" id="loanCount"></td>
		<c:if test="${not empty query.transferType}">
			<td width="22%" class="printLabel"><bean:message key="loan.summary.amount"/>:</td>
			<td width="11%" class="printData" id="totalAmount"></td>
			<td width="22%" class="printLabel"><bean:message key="loan.summary.remainingAmount"/>:</td>
			<td width="12%" class="printData" id="totalRemainingAmount"></td>
		</c:if>
	</tr>
</table>
<c:set var="count" value="${0}"/>
<c:set var="totalAmount" value="${0.0}"/>
<c:set var="totalRemainingAmount" value="${0.0}"/>

<table class="printBorder">
	<tr>
		<c:if test="${empty query.member}">
			<th width="15%" class="printLabel"><bean:message key='member.member'/></th>
		</c:if>
		<th class="printLabel"><bean:message key='loan.description'/></th>
		<th width="10%" class="printLabel"><bean:message key='loan.amount'/></th>
		<th width="10%" class="printLabel"><bean:message key='loan.remainingAmount'/></th>
		<th width="10%" class="printLabel"><bean:message key='loan.payments'/></th>
		<c:forEach var="field" items="${allFields}">
			<th class="printLabel">${field.name}</th>
		</c:forEach>
	</tr>
	<c:forEach var="loan" items="${loans}" varStatus="counter">
		
		<c:set var="unitsPattern" value="${loan.transfer.type.from.currency.pattern}" />
		<c:set var="count" value="${count + 1}"/>
		<c:set var="totalAmount" value="${totalAmount + loan.totalAmount}"/>
		<c:set var="totalRemainingAmount" value="${totalRemainingAmount + loan.remainingAmount}"/>
		<c:set var="closedPaymentsCount" value="${loan.closedPaymentsCount}" />
		<c:if test="${ localSettings.maxIteratorResults == 0 || counter.index < localSettings.maxIteratorResults }">
		<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			<c:if test="${fullQuery && empty searchLoansForm.query.member}">
				<td class="printData" align="center">${loan.member.username}</td>
			</c:if>
			<td class="printData" align="left"><cyclos:truncate value="${loan.transfer.description}"/></td>
			<td class="printData" align="right" nowrap="nowrap"><cyclos:format number="${loan.totalAmount}" unitsPattern="${unitsPattern}"/></td>
			<td class="printData" align="right" nowrap="nowrap"><cyclos:format number="${loan.remainingAmount}" unitsPattern="${unitsPattern}"/></td>
			<td class="printData" align="center" nowrap="nowrap">
				${closedPaymentsCount} /					
				${loan.paymentCount}
			</td>
			<c:forEach var="field" items="${allFields}">
				<td class="printData"><cyclos:customField field="${field}" collection="${loan.transfer.customValues}" textOnly="true" /></td>
			</c:forEach>
		</tr>

		<cyclos:clearCache />
		</c:if>		
		<c:if test="${ localSettings.maxIteratorResults > 0 && counter.index == localSettings.maxIteratorResults }">
		   	<tr>
   				<td colspan="4" class="tdPrintHeader"><bean:message key="reports.print.limitation" arg0="${localSettings.maxIteratorResults}"/></td>
   			</tr>
   		</c:if>
	</c:forEach>
</table>
<script>
	$('loanCount').innerHTML = "${count}";
	<c:if test="${not empty query.transferType}">
		$('totalAmount').innerHTML = "<cyclos:format number="${totalAmount}"/>";
		$('totalRemainingAmount').innerHTML = "<cyclos:format number="${totalRemainingAmount}"/>";
	</c:if>
</script>