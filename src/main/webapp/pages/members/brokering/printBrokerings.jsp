<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<div class="printTitle"><bean:message key="brokering.title.print"/></div>
<table width="100%">
	<tr>
		<td class="printLabel" width="20%"><bean:message key="member.broker"/>:&nbsp;</td>
		<td class="printData">${broker.name}</td>
	</tr>
	<c:if test="${not empty query.status}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="brokering.status"/>:&nbsp;</td>
			<td class="printData"><bean:message key="brokering.status.${query.status}"/></td>
		</tr>
	</c:if>
</table>
<hr class="print">
<c:forEach var="entry" items="${brokerings}" varStatus="counter">
	<c:set var="member" value="${entry.brokering.brokered}"/>
	<c:set var="loans" value="${entry.loans}"/>
	<c:if test="${ localSettings.maxIteratorResults == 0 || counter.index < localSettings.maxIteratorResults }">
	<cyclos:selectCustomFields allFields="${memberFields}" var="currentMemberFields" group="${member.group}" />
	<cyclos:layout columns="4" className="printContent" width="100%">
		<cyclos:cell className="printLabel" nowrap="nowrap" width="20%"><bean:message key="member.username"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="30%">${member.username}</cyclos:cell>
		<cyclos:cell className="printLabel" nowrap="nowrap" width="20%"><bean:message key="member.email"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="30%">${member.email}</cyclos:cell>
		
		<cyclos:cell className="printLabel" nowrap="nowrap"><bean:message key="member.name"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData">${member.name}</cyclos:cell>
		
		<c:forEach var="field" items="${currentMemberFields}">
			<cyclos:cell className="printLabel" nowrap="nowrap">${field.name}:&nbsp;</cyclos:cell>
			<cyclos:cell className="printData"><cyclos:customField collection="${member.customValues}" field="${field}" textOnly="true" value="${value}"/></cyclos:cell>
		</c:forEach>
		
		<c:if test="${showLoanData}">
			<cyclos:rowBreak/>
			<cyclos:cell className="printLabel"><bean:message key="brokering.loans.count"/>:&nbsp;</cyclos:cell>
			<cyclos:cell className="printData">${loans.count}</cyclos:cell>
			<cyclos:cell className="printLabel"><bean:message key="brokering.loans.amount"/>:&nbsp;</cyclos:cell>
			<cyclos:cell className="printData"><cyclos:format number="${loans.amount}"/></cyclos:cell>
		</c:if>
	</cyclos:layout>
	<hr class="print">

	<cyclos:clearCache />
		</c:if>		
		<c:if test="${ localSettings.maxIteratorResults > 0 && counter.index == localSettings.maxIteratorResults }">
		   	<tr>
   				<td class="tdPrintHeader" colspan="4"><bean:message key="reports.print.limitation" arg0="${localSettings.maxIteratorResults}"/></td>
   			</tr>
   		</c:if>

</c:forEach>