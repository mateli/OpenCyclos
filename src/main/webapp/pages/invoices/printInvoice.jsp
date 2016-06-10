<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<c:set var="toMember" value="${invoice.toMember}"/>
<c:set var="fromMember" value="${invoice.fromMember}"/>
<c:set var="amount"><cyclos:format number="${invoice.amount}" unitsPattern="${unitsPattern}" /></c:set>

<table border="1" style="border-collapse:collapse;width:95%" align="center">
	<tr>
		<td colspan="2" class="printTitle"><bean:message key="invoice.title.print"/></td>
	</tr>
	<tr>
		<td width="50%" valign="top">
			<span class="printLabel"><bean:message key="invoice.from"/>:</span>
			<c:choose><c:when test="${empty fromMember}">
				<span class="printData">${localSettings.applicationUsername}</span>
			</c:when><c:otherwise>
				<span class="printData">${fromMember.username}</span>
				<div class="printData">${fromMember.name}</div>
			</c:otherwise></c:choose>
		</td>
		<td width="50%" valign="top">
			<span class="printLabel"><bean:message key="invoice.to"/>:</span>
			<c:choose><c:when test="${empty toMember}">
				<span class="printData">${localSettings.applicationUsername}</span>
			</c:when><c:otherwise>
				<span class="printData">${toMember.username}</span>
				<div class="printData">${toMember.name}</div>
			</c:otherwise></c:choose>
		</td>
	</tr>
	<c:if test="${showSentBy}">
		<tr>
			<td colspan="2">
				<span class="printLabel" width="25%"><bean:message key="invoice.sentBy"/>:</span>
				<span class="printData">
					<c:choose>
           				<c:when test="${not empty sentByMember}">
   	        				${sentByMember.username} - ${sentByMember.name}
            			</c:when>
           				<c:when test="${not empty sentByOperator}">
   	        				${sentByOperator.username} - ${sentByOperator.name}
            			</c:when>
            			<c:when test="${not empty sentByAdmin}">
            				${sentByAdmin.username} - ${sentByAdmin.name}
            			</c:when>
            			<c:when test="${sentBySystem}">
            				${localSettings.applicationUsername}
            			</c:when>
            		</c:choose>
				</span>
			</td>
		</tr>
	</c:if>
	<tr>
		<td colspan="2">
			<span class="printLabel" width="25%"><bean:message key='invoice.date'/>:</span>
			<span class="printData"><cyclos:format dateTime="${invoice.date}"/></span>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<span class="printLabel" width="25%"><bean:message key='invoice.status'/>:</span>
			<span class="printData"><bean:message key='invoice.status.${invoice.status}'/></span>
		</td>
	</tr>
	<c:if test="${showPerformedBy}">
		<tr>
			<td colspan="2">
				<span class="printLabel" width="25%"><bean:message key="invoice.performedBy"/>:</span>
				<span class="printData">
					<c:choose>
	       				<c:when test="${not empty performedByMember}">
	        				${performedByMember.username} - ${performedByMember.name}
	           			</c:when>
	       				<c:when test="${not empty performedByOperator}">
	        				${performedByOperator.username} - ${performedByOperator.name}
	           			</c:when>
	           			<c:when test="${not empty performedByAdmin}">
	           				${performedByAdmin.username} - ${performedByAdmin.name}
	           			</c:when>
	           			<c:when test="${performedBySystem}">
	           				${localSettings.applicationUsername}
	           			</c:when>
	           		</c:choose>
	           	</span>
			</td>
		</tr>
	</c:if>
	<c:if test="${showDestinationAccountType}">
		<tr>
			<td colspan="2">
				<span class="printLabel" width="25%"><bean:message key="invoice.destination"/>:</span>
				<span class="printData">${invoice.destinationAccountType.name}</span>
			</td>
		</tr>
	</c:if>
	<c:if test="${not empty invoice.transferType}">
		<tr>
			<td colspan="2">
				<span class="printLabel" width="25%"><bean:message key="invoice.transferType"/>:</span>
				<span class="printData">${invoice.transferType.name}</span>
			</td>
		</tr>
	</c:if>
	<c:choose>
		<c:when test="${empty invoice.payments}">
			<tr>
				<td colspan="2">
					<span class="printLabel" width="25%"><bean:message key='invoice.amount'/>:</span>
					<span class="printData">${amount}</span>
				</td>
			</tr>
		</c:when>
		<c:when test="${fn:length(invoice.payments) == 1}">
			<c:set var="firstPayment" value="${invoice.payments[0]}" />
			<tr>
				<td colspan="2">
					<span class="printLabel" width="25%"><bean:message key='invoice.amount'/>:</span>
					<span class="printData"><cyclos:format number="${firstPayment.amount}" unitsPattern="${unitsPattern}" /></span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<span class="printLabel" width="25%"><bean:message key='invoice.scheduledFor'/>:</span>
					<span class="printData"><cyclos:format rawDate="${firstPayment.date}" /></span>
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="2">
					<span class="printLabel" width="25%"><bean:message key='invoice.totalAmount'/>:</span>
					<span class="printData"><cyclos:format number="${invoice.amount}" unitsPattern="${unitsPattern}" /></span>
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
	<c:forEach var="entry" items="${customFields}">
        <c:set var="field" value="${entry.field}"/>
        <c:set var="value" value="${entry.value}"/>
        <c:if test="${not empty value.value}">
        	<tr>
	        	<td colspan="2">
					<span class="printLabel" width="25%">${field.name}:</span>
					<span class="printData"><cyclos:customField field="${field}" value="${value}" textOnly="true"/></span>
				</td>
			</tr>
		</c:if>
    </c:forEach>
    <tr>
		<td colspan="2">
			<span class="printLabel" width="25%"><bean:message key="invoice.description"/>:</span>
			<span class="printData"><cyclos:escapeHTML>${invoice.description}</cyclos:escapeHTML></span>
		</td>
	</tr>
</table>
<c:if test="${not empty invoice.payments and fn:length(invoice.payments) > 1}">
	
	<table border="1" style="border-collapse:collapse;width:95%" align="center">
		<tr>
			<td colspan="5" class="printTitle"><bean:message key='invoice.payments'/></td>
		</tr>
		<tr>
			<td width="20%"><span class="printLabel"><bean:message key='transfer.number'/></span></td>
			<td width="40%"><span class="printLabel"><bean:message key='transfer.date'/></span></td>
			<td width="40%"><span class="printLabel"><bean:message key='transfer.amount'/></span></td>
		</tr>
		<c:forEach var="current" items="${invoice.payments}" varStatus="status">
			<tr>
				<td width="20%" valign="top">
					<span class="printData">${status.count}</span>
				</td>
				<td width="40%" valign="top">
					<span class="printData"><cyclos:format rawDate="${current.date}" /></span>
				</td>
				<td width="40%" valign="top">
					<span class="printData"><cyclos:format number="${current.amount}" unitsPattern="${unitsPattern}" /></span>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
