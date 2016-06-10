<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<div class="printTitle"><bean:message key="card.title.print"/></div>
<table width="100%">
	<c:if test="${not empty query.cardType}">
		<tr>
			<td class="printLabel" width="20%"><bean:message key="card.cardType"/>:&nbsp;</td>
			<td class="printData">${cType.name}</td>
		</tr>
	</c:if>
	<c:if test="${not empty query.number}">
		<tr>
			<td class="printLabel"><bean:message key="card.number"/>:&nbsp;</td>
			<td class="printData">${query.number}</td>
		</tr>
	</c:if>
	
	<c:if test="${not empty query.member}">
		<tr>
			<td class="printLabel"><bean:message key="member.memberName"/>:&nbsp;</td>
			<td class="printData">${query.member.name}</td>
		</tr>
	</c:if>
	
	<c:if test="${not empty query.expiration.begin}">
		<tr>
			<td class="printLabel"><bean:message key="card.expirationPeriod.from"/>:&nbsp;</td>
			<td class="printData"><cyclos:format rawDate="${query.expiration.begin}"/></td>
		</tr>
		<tr>
			<td class="printLabel"><bean:message key="card.expirationPeriod.to"/>:&nbsp;</td>
			<td class="printData"><cyclos:format rawDate="${query.expiration.end}"/></td>
		</tr>
	</c:if>
	
	<c:if test="${not empty query.broker}">
		<tr>
			<td class="printLabel"><bean:message key="member.memberName"/>:&nbsp;</td>
			<td class="printData">${query.broker.name}</td>
		</tr>
	</c:if>
	
	<c:if test="${not empty card.status}">
		<tr>
			<td class="printLabel"><bean:message key="card.status"/>:&nbsp;</td>
			<td class="printData"><bean:message key="card.status.${query.status}"/></td>
		</tr>
	</c:if>
	<c:if test="${not empty query.groups}">
		<tr>
			<td class="printLabel"><bean:message key="member.group"/>:&nbsp;</td>
			<td class="printData">
			<c:forEach var="group" items="${query.groups}" varStatus="status">
				${group.name}<c:if test="${!status.last}">,</c:if>
	        </c:forEach>
	        </td>
		</tr>
	</c:if>
</table>
<hr class="print">
<c:forEach var="card" items="${cards}">
	<cyclos:layout columns="4" className="printContent" width="100%">
		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="card.member"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="40%">${card.owner.name}</cyclos:cell>
		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="card.number"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="40%"><cyclos:format number="${card.cardNumber}" cardNumberPattern="${card.cardType.cardFormatNumber}"/></cyclos:cell>

		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="card.expirationDate"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="40%"><cyclos:format rawDate="${card.expirationDate}"/></cyclos:cell>
		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="card.status"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="40%"><bean:message key="card.status.${card.status}"/></cyclos:cell>
		
	</cyclos:layout>
	<hr class="print">
	
	<cyclos:clearCache />
</c:forEach>