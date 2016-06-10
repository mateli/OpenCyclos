<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<div class="printTitle"><bean:message key="loanPayment.title.print"/></div>
<cyclos:layout columns="4" className="printContent" width="100%">
	<cyclos:cell className="printLabel" width="20%"><bean:message key="loan.status"/>:&nbsp;</cyclos:cell>
	<cyclos:cell className="printData" width="30%">${statusLabel}</cyclos:cell>
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
	<c:if test="${not empty query.repaymentPeriod.begin || not empty query.repaymentPeriod.end}">
		<cyclos:cell className="printLabel" width="20%"><bean:message key="loan.repaymentDate"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="30%">
			<c:if test="${not empty query.repaymentPeriod.begin}">
				<span class="printLabel"><bean:message key="global.range.from"/>:</span>&nbsp;
				<cyclos:format date="${query.repaymentPeriod.begin}"/>&nbsp;
			</c:if>
			<c:if test="${not empty query.repaymentPeriod.end}">
				<span class="printLabel"><bean:message key="global.range.to"/>:</span>&nbsp;
				<cyclos:format date="${query.repaymentPeriod.end}"/>
			</c:if>
		</cyclos:cell>
	</c:if>
</cyclos:layout>
<hr class="print">
<table class="printContent">
	<c:if test="${not empty query.transferType}">
		<tr>
			<td class="printLabel"><bean:message key="loanPayment.summary.receivedPayments"/>:</td>
			<td class="printData" style="text-align:right">${receivedPayments.count}</td>
			<td class="printLabel"><bean:message key="loanPayment.summary.total"/>:</td>
			<td class="printData" style="text-align:right"><cyclos:format number="${receivedPayments.amount}"/></td>
		</tr>
		<tr>
			<td class="printLabel"><bean:message key="loanPayment.summary.discardedPayments"/>:</td>
			<td class="printData" style="text-align:right">${discardedPayments.count}</td>
			<td class="printLabel"><bean:message key="loanPayment.summary.total"/>:</td>
			<td class="printData" style="text-align:right"><cyclos:format number="${discardedPayments.amount}"/></td>
		</tr>
		<tr>
			<td class="printLabel"><bean:message key="loanPayment.summary.paymentsToReceive"/>:</td>
			<td class="printData" style="text-align:right">${paymentsToReceive.count}</td>
			<td class="printLabel"><bean:message key="loanPayment.summary.total"/>:</td>
			<td class="printData" style="text-align:right"><cyclos:format number="${paymentsToReceive.amount}"/></td>
		</tr>
		<tr>
			<td class="printLabel"><bean:message key="loanPayment.summary.inProcessPayments"/>:</td>
			<td class="printData" style="text-align:right">${inProcessPayments.count}</td>
			<td class="printLabel"><bean:message key="loanPayment.summary.total"/>:</td>
			<td class="printData" style="text-align:right"><cyclos:format number="${inProcessPayments.amount}"/></td>
		</tr>
		<tr>
			<td class="printLabel"><bean:message key="loanPayment.summary.recoveredPayments"/>:</td>
			<td class="printData" style="text-align:right">${recoveredPayments.count}</td>
			<td class="printLabel"><bean:message key="loanPayment.summary.total"/>:</td>
			<td class="printData" style="text-align:right"><cyclos:format number="${recoveredPayments.amount}"/></td>
		</tr>
		<tr>
			<td class="printLabel"><bean:message key="loanPayment.summary.unrecoverablePayments"/>:</td>
			<td class="printData" style="text-align:right">${unrecoverablePayments.count}</td>
			<td class="printLabel"><bean:message key="loanPayment.summary.total"/>:</td>
			<td class="printData" style="text-align:right"><cyclos:format number="${unrecoverablePayments.amount}"/></td>
		</tr>		
	</c:if>
</table>

<table class="printBorder" width="100%">
	<tr>
		<c:forEach var="field" items="${loanFieldsForResults}">
			<th class="printLabel">${field.name}</th>
		</c:forEach>
		<th class="printLabel"><bean:message key='member.username'/></th>
		<th width="15%" class="printLabel"><bean:message key='loan.expirationDate'/></th>
		<th width="15%" class="printLabel"><bean:message key='loanPayment.amount'/></th>
		<th width="15%" class="printLabel"><bean:message key='loanPayment.status'/></th>
		<th width="15%" class="printLabel"><bean:message key='loanPayment.search.repaidAmount'/></th>
		<th width="15%" class="printLabel"><bean:message key='loanPayment.search.discardedAmount'/></th>
		<c:forEach var="field" items="${allFields}">
			<th class="printLabel">${field.name}</th>
		</c:forEach>
	</tr>
	<c:forEach var="payment" items="${loanPayments}" varStatus="counter">
		<c:set var="loan" value="${payment.loan}"/>
		<c:set var="unitsPattern" value="${loan.transfer.type.from.currency.pattern}" />
		<c:if test="${ localSettings.maxIteratorResults == 0 || counter.index < localSettings.maxIteratorResults }">
		<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			<c:forEach var="field" items="${loanFieldsForResults}">
				<td class="printData"><cyclos:customField collection="${loan.customValues}" textOnly="true" field="${field}" /></td>
			</c:forEach>
			<td class="printData" align="center">${loan.member.username}</td>
			<td class="printData" nowrap="nowrap" align="center"><cyclos:format rawDate="${payment.expirationDate}"/></td>
			<td class="printData" align="right"><cyclos:format number="${payment.amount}" unitsPattern="${unitsPattern}"/></td>
			<td class="printData" align="center">
               	<bean:message key="loanPayment.status.${payment.status}"/>
               	<c:if test="${cyclos:name(payment.status) == 'REPAID' || cyclos:name(payment.status) == 'DISCARDED'}">
               		<div style="white-space:nowrap">
                		(<cyclos:format date="${payment.repaymentDate}"/>)
               		</div>
               	</c:if>
			</td>
			<td class="printData" align="right"><cyclos:format number="${payment.repaidAmount}" unitsPattern="${unitsPattern}"/></td>
			<td class="printData" align="right"><cyclos:format number="${payment.discardedAmount}" unitsPattern="${unitsPattern}"/></td>
			<c:forEach var="field" items="${allFields}">
				<td class="printData"><cyclos:customField field="${field}" collection="${payment.loan.transfer.customValues}" textOnly="true" /></td>
			</c:forEach>
		</tr>
		<cyclos:clearCache />
		</c:if>		
		<c:if test="${ localSettings.maxIteratorResults > 0 && counter.index == localSettings.maxIteratorResults }">
		   	<tr>
   				<td class="tdPrintHeader" colspan="4"><bean:message key="reports.print.limitation" arg0="${localSettings.maxIteratorResults}"/></td>
   			</tr>
   		</c:if>
	</c:forEach>
</table>