<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<c:set var="unitsPattern" value="${payment.type.from.currency.pattern}" />
<c:set var="singleInstallment" value="${false}" />
<table border="1" style="border-collapse:collapse;width:95%">
	<tr>
		<td colspan="2" class="printTitle"><bean:message key="scheduledPayment.title.print"/></td>
	</tr>
	
	<tr>
		<td width="50%" valign="top">
			<span class="printLabel"><bean:message key="transfer.from"/>:</span>
			<span class="printData">${payment.from.ownerName}</span>
			<div class="printData">
				<c:if test="${!payment.fromSystem}">
					${payment.fromOwner.name}
				</c:if>
			</div>
		</td>
		<td width="50%" valign="top">
			<span class="printLabel"><bean:message key="transfer.to"/>:</span>
			<span class="printData">${payment.to.ownerName}</span>
			<div class="printData">
				<c:if test="${!payment.toSystem}">
					${payment.toOwner.name}
				</c:if>
			</div>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<span class="printLabel"><bean:message key="transfer.submitDate"/>:</span>
			<span class="printData"><cyclos:format dateTime="${payment.date}"/></span>
		</td>
	</tr>
	<c:if test="${not empty payment.processDate}">
		<tr>
			<td colspan="2">
				<span class="printLabel"><bean:message key="transfer.processDate"/>:</span>
				<span class="printData"><cyclos:format dateTime="${payment.processDate}"/></span>
			</td>
		</tr>
	</c:if>
	<c:if test="${empty payment.processDate && fn:length(payment.transfers) == 1}">
		<c:set var="singleInstallment" value="${true}" />
		<tr>
			<td colspan="2">
				<span class="printLabel"><bean:message key="transfer.scheduledFor"/>:</span>
				<span class="printData"><cyclos:format rawDate="${payment.transfers[0].date}"/></span>
			</td>
		</tr>
    </c:if>            	

	<c:if test="${showBy}">
		<tr>
			<td colspan="2">
				<span class="printLabel"><bean:message key="transfer.by"/>:</span>
				<span class="printData">
           			<c:choose>
           				<c:when test="${not empty byMember}">
   	        				${byMember.username} - ${byMember.name}
            			</c:when>
           				<c:when test="${not empty byOperator}">
   	        				${byOperator.username} - ${byOperator.name}
            			</c:when>
            			<c:when test="${not empty byAdmin}">
            				${byAdmin.username} - ${byAdmin.name}
            			</c:when>
            			<c:when test="${bySystem}">
            				${localSettings.applicationUsername}
            			</c:when>
            		</c:choose>
            	</span>
			</td>
		</tr>
	</c:if>
	<tr>
		<td colspan="2">
			<span class="printLabel"><bean:message key="transfer.type"/>:</span>
			<span class="printData">${payment.type.name}</span>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<span class="printLabel"><bean:message key="${singleInstallment ? 'transfer.amount' : 'scheduledPayment.totalAmount'}"/>:</span>
			<span class="printData"><cyclos:format number="${payment.amount}" unitsPattern="${unitsPattern}" /></span>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<span class="printLabel"><bean:message key="payment.status"/>:</span>
			<span class="printData"><bean:message key="payment.status.${payment.status}"/></span>
		</td>
	</tr>
	<c:forEach var="entry" items="${customFields}" varStatus="counter">
		<c:if test="${ localSettings.maxIteratorResults == 0 || counter.index < localSettings.maxIteratorResults }">
        <c:set var="field" value="${entry.field}"/>
        <c:set var="value" value="${entry.value}"/>
        <c:if test="${not empty value.value}">
			<tr>
				<td colspan="2">
					<span class="printLabel">${field.name}:</span>
					<span class="printData"><cyclos:customField field="${field}" value="${value}" textOnly="true"/></span>
				</td>
			</tr>
		</c:if>
		</c:if>		
		<c:if test="${ localSettings.maxIteratorResults > 0 && counter.index == localSettings.maxIteratorResults }">
		   	<tr>
   				<td class="tdPrintHeader"><bean:message key="reports.print.limitation" arg0="${localSettings.maxIteratorResults}"/></td>
   			</tr>
   		</c:if>
    </c:forEach>
	<tr>
		<td colspan="2">
			<span class="printLabel"><bean:message key="transfer.description"/>:</span>
			<span class="printData"><cyclos:escapeHTML>${payment.description}</cyclos:escapeHTML></span>
		</td>
	</tr>
</table>

<c:if test="${fn:length(payment.transfers) > 1}">
<br>
<table border="1" style="border-collapse:collapse;width:95%">
	<tr>
		<td colspan="5" class="printTitle"><bean:message key="scheduledPayment.title.transfers"/></td>
	</tr>
	<tr>
		<th width="33%"><span class="printLabel"><bean:message key="transfer.date"/></span></th>
		<th width="33%"><span class="printLabel"><bean:message key="transfer.amount"/></span></th>
		<th width="34%"><span class="printLabel"><bean:message key="payment.status"/></span></th>
	</tr>
	<c:forEach var="current" items="${payment.transfers}">
		<tr>
			<td align="center" class="printData"><cyclos:format rawDate="${current.date}"/></td>
			<td align="center" class="printData"><cyclos:format number="${current.amount}" unitsPattern="${unitsPattern}"/></td>
			<td align="center" class="printData"><bean:message key="payment.status.${current.status}"/></td>
		</tr>
	</c:forEach>
</table>
</c:if>