<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<div class="printTitle"><bean:message key="pendingMember.title.print"/></div>
<table width="100%">
	<c:if test="${not empty query.name}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="member.name"/>:&nbsp;</td>
			<td class="printData">${query.name}</td>
		</tr>
	</c:if>
	<c:if test="${not empty groupNames}">
		<tr>
			<td class="printLabel"><bean:message key="member.group"/>:&nbsp;</td>
			<td class="printData">${groupNames}</td>
		</tr>
	</c:if>
	<c:if test="${not empty query.broker}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="member.broker"/>:&nbsp;</td>
			<td class="printData">${query.broker.name} (${query.broker.username})</td>
		</tr>
	</c:if>
  	<c:if test="${not empty query.creationPeriod && (not empty query.creationPeriod.begin || not empty query.creationPeriod.end)}">
		<tr>
 			<td class="printLabel"><bean:message key="pendingMember.search.date"/>&nbsp;</td>
			<td class="printData">
				<c:if test="${not empty query.creationPeriod.begin}">
					<span class="printLabel"><bean:message key="global.range.from"/>:&nbsp;</span>
					<cyclos:format date="${query.creationPeriod.begin}"/>&nbsp;
				</c:if>
				<c:if test="${not empty query.creationPeriod.end}">
					<span class="printLabel"><bean:message key="global.range.to"/>:&nbsp;</span>
					<cyclos:format date="${query.creationPeriod.end}"/>&nbsp;
				</c:if>
			</td>
   		</tr>
   	</c:if>
    <c:forEach var="entry" items="${customFields}">
        <c:set var="field" value="${entry.field}"/>
		<c:set var="value" value="${entry.value.value}"/>
		<c:if test="${not empty value}">
			<tr>
				<td class="printLabel">${field.name}:&nbsp;</td>
				<td class="printData"><cyclos:customField field="${field}" textOnly="true" value="${value}"/></td>
			</tr>
		</c:if>
    </c:forEach>
</table>
<hr class="print">
<c:forEach var="member" items="${pendingMembers}" varStatus="counter">
	<c:if test="${ localSettings.maxIteratorResults == 0 || counter.index < localSettings.maxIteratorResults }">
	<cyclos:selectCustomFields allFields="${memberFields}" var="currentMemberFields" group="${member.memberGroup}" />
	<cyclos:layout columns="4" className="printContent" width="100%">
		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="member.creationDate"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="40%"><cyclos:format dateTime="${member.creationDate}" /></cyclos:cell>
	
		<c:if test="${not empty member.username}">
			<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="member.username"/>:&nbsp;</cyclos:cell>
			<cyclos:cell className="printData" width="40%">${member.username}</cyclos:cell>
		</c:if>
		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="member.email"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="40%">${member.email}</cyclos:cell>
		
		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="member.name"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData">${member.name}</cyclos:cell>
		<c:if test="${fn:length(query.groups) != 1}">
			<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="member.group"/>:&nbsp;</cyclos:cell>
			<cyclos:cell className="printData">${member.memberGroup.name}</cyclos:cell>
		</c:if>

		<c:if test="${not empty member.broker}">
			<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="member.brokerUsername"/>:&nbsp;</cyclos:cell>
			<cyclos:cell className="printData">${member.broker.username}</cyclos:cell>
			<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="member.brokerName"/>:&nbsp;</cyclos:cell>
			<cyclos:cell className="printData">${member.broker.name}</cyclos:cell>
		</c:if>
		
		<c:forEach var="field" items="${currentMemberFields}">
			<c:set var="fieldValue"><cyclos:customField field="${field}" textOnly="true" collection="${member.customValues}"/></c:set>
			<c:if test="${not empty fieldValue}">
				<cyclos:cell className="printLabel" nowrap="nowrap" width="10%">${field.name}:&nbsp;</cyclos:cell>
				<cyclos:cell className="printData" width="40%">${fieldValue}</cyclos:cell>
			</c:if>
		</c:forEach>
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