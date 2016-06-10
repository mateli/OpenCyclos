<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:set var="isScheduledPayment" value="${not empty transfer.rootTransfer.scheduledPayment}" />

<c:if test="${param.showConfirmation == 'true'}">
	<div align="center" class="printLabel" style="text-align:center !important">
		<cyclos:escapeHTML><bean:message key="payment.performed"/></cyclos:escapeHTML>
	</div>
	
</c:if>
<center>
<table border="1" style="border-collapse:collapse;width:95%">
	<tr>
		<td colspan="2" class="printTitle"><bean:message key="transfer.title.print"/></td>
	</tr>
	<tr>
		<td width="50%" valign="top">
			<span class="printLabel"><bean:message key="transfer.from"/>:</span>
			<span class="printData">${transfer.actualFrom.ownerName}</span>
			<div class="printData">
				<c:if test="${!transfer.actuallyFromSystem}">
					${transfer.actualFromOwner.name}
				</c:if>
			</div>
		</td>
		<td width="50%" valign="top">
			<span class="printLabel"><bean:message key="transfer.to"/>:</span>
			<span class="printData">${transfer.actualTo.ownerName}</span>
			<div class="printData">
				<c:if test="${!transfer.actuallyToSystem}">
					${transfer.actualToOwner.name}
				</c:if>
			</div>
		</td>
	</tr>
	<c:if test="${showBy}">
		<tr>
			<td colspan="2">
				<span class="printLabel" width="25%"><bean:message key="transfer.by"/>:</span>
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
	<c:if test="${not empty transfer.transactionNumber}">
		<tr>
			<td colspan="2">
				<span class="printLabel" width="25%"><bean:message key="transfer.transactionNumber"/>:</span>
				<span class="printData">${transfer.transactionNumber}</span>
			</td>
		</tr>
	</c:if>
	<c:choose><c:when test="${not empty transfer.processDate and transfer.processedAtDifferentDate}">
		<tr>
			<td colspan="2">
				<span class="printLabel" width="25%"><bean:message key="${isScheduledPayment ? 'transfer.scheduledFor' : 'transfer.submitDate'}"/>:</span>
	            <%--if the transfer is a scheduled one then the date is stored without the hours/mins that is why we must use the raw date --%>				
				<span class="printData"><cyclos:format dateTime="${isScheduledPayment ? null : transfer.date}" rawDate="${isScheduledPayment ? transfer.date : null}"/></span>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<span class="printLabel" width="25%"><bean:message key="transfer.processDate"/>:</span>
				<span class="printData"><cyclos:format dateTime="${transfer.processDate}"/></span>
			</td>
		</tr>
	</c:when><c:otherwise>
		<tr>
			<td colspan="2">
				<span class="printLabel" width="25%"><bean:message key="${isScheduledPayment ? 'transfer.scheduledFor' : 'transfer.submitDate'}"/>:</span>
	            <%--if the transfer is a scheduled one then the date is stored without the hours/mins that is why we must use the raw date --%>				
				<span class="printData"><cyclos:format dateTime="${isScheduledPayment ? null : transfer.date}" rawDate="${isScheduledPayment ? transfer.date : null}"/></span>
			</td>
		</tr>
	</c:otherwise></c:choose>
	<tr>
		<td colspan="2">
			<span class="printLabel"><bean:message key="transfer.type"/>:</span>
			<span class="printData">${transfer.type.name}</span>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<span class="printLabel"><bean:message key="transfer.amount"/>:</span>
			<span class="printData"><cyclos:format number="${transfer.actualAmount}" unitsPattern="${transfer.type.from.currency.pattern}"/></span>
		</td>
	</tr>
 	<c:if test="${not empty scheduledPayment}">
		<td colspan="2">
			<span class="printLabel"><bean:message key="transfer.scheduling"/>:</span>
			<span class="printData">
				<c:set var="formattedScheduledPaymentAmount"><cyclos:format number="${scheduledPayment.amount}" unitsPattern="${unitsPattern}" /></c:set>
	 			<bean:message key="transfer.schedulingDetails" arg0="${scheduledPaymentNumber}" arg1="${scheduledPaymentCount}" arg2="${formattedScheduledPaymentAmount}" />
			</span>
		</td>
 	</c:if>
	<c:forEach var="entry" items="${customFields}">
        <c:set var="field" value="${entry.field}"/>
        <c:set var="value" value="${entry.value}"/>
        <c:if test="${not empty value.value}">
			<tr>
				<td colspan="2">
					<span class="printLabel">${field.name}:</span>
					<span class="printData"><cyclos:customField field="${field}" value="${value}" textOnly="true" editable="false" /></span>
				</td>
			</tr>
		</c:if>
    </c:forEach>
	<c:if test="${transfer.type.requiresAuthorization}">
		<tr>
			<td colspan="2">
				<span class="printLabel"><bean:message key="payment.status"/>:</span>
				<span class="printData"><bean:message key="payment.status.${transfer.status}"/></span>
			</td>
		</tr>
	</c:if>
	<c:if test="${not empty transfer.description}">
		<tr>
			<td colspan="2">
				<span class="printLabel"><bean:message key="transfer.description"/>:</span>
				<span class="printData"><cyclos:escapeHTML>${transfer.description}</cyclos:escapeHTML></span>
			</td>
		</tr>
	</c:if>
</table>

<c:if test="${not empty parent}">
	<br>
	<table border="1" style="border-collapse:collapse;width:95%">
		<tr>
			<td colspan="2" class="printTitle"><bean:message key="transfer.title.parent"/></td>
		</tr>
		<tr>
			<td width="50%" valign="top">
				<span class="printLabel"><bean:message key="transfer.from"/>:</span>
				<span class="printData">${parent.actualFrom.ownerName}</span>
				<div class="printData">
					<c:if test="${!parent.actuallyFromSystem}">
						${parent.actualFromOwner.name}
					</c:if>
				</div>
			</td>
			<td width="50%" valign="top">
				<span class="printLabel"><bean:message key="transfer.to"/>:</span>
				<span class="printData">${parent.actualTo.ownerName}</span>
				<div class="printData">
					<c:if test="${!parent.actuallyToSystem}">
						${parent.actualToOwner.name}
					</c:if>
				</div>
			</td>
		</tr>
		<c:if test="${not empty parent.transactionNumber}">
			<tr>
				<td colspan="2">
					<span class="printLabel"><bean:message key="transfer.transactionNumber"/>:</span>
					<span class="printData">${parent.transactionNumber}</span>
				</td>
			</tr>
		</c:if>
		<c:choose><c:when test="${not empty parent.processDate and parent.processedAtDifferentDate}">
			<tr>
				<td colspan="2">
					<span class="printLabel" width="25%"><bean:message key="${isScheduledPayment ? 'transfer.scheduledFor' : 'transfer.submitDate'}"/>:</span>
	            	<%--if the transfer is a scheduled one then the date is stored without the hours/mins that is why we must use the raw date --%>					
					<span class="printData"><cyclos:format dateTime="${isScheduledPayment ? null : parent.date}" rawDate="${isScheduledPayment ? parent.date : null}"/></span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<span class="printLabel" width="25%"><bean:message key="transfer.processDate"/>:</span>
					<span class="printData"><cyclos:format dateTime="${parent.processDate}"/></span>
				</td>
			</tr>
		</c:when><c:otherwise>
			<tr>
				<td colspan="2">
					<span class="printLabel" width="25%"><bean:message key="${isScheduledPayment ? 'transfer.scheduledFor' : 'transfer.submitDate'}"/>:</span>
	            	<%--if the transfer is a scheduled one then the date is stored without the hours/mins that is why we must use the raw date --%>					
					<span class="printData"><cyclos:format dateTime="${isScheduledPayment ? null : parent.date}" rawDate="${isScheduledPayment ? parent.date : null}"/></span>
				</td>
			</tr>
		</c:otherwise></c:choose>
		<tr>
			<td colspan="2">
				<span class="printLabel"><bean:message key="transfer.amount"/>:</span>
				<span class="printData"><cyclos:format number="${parent.actualAmount}"/></span>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<span class="printLabel"><bean:message key="transfer.type"/>:</span>
				<span class="printData">${parent.type.name}</span>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<span class="printLabel"><bean:message key="transfer.description"/>:</span>
				<span class="printData"><cyclos:escapeHTML>${parent.description}</cyclos:escapeHTML></span>
			</td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty children}">
	<br>
	<table border="1" style="border-collapse:collapse;width:95%">
		<tr>
			<td colspan="5" class="printTitle"><bean:message key="transfer.title.children"/></td>
		</tr>
		<tr>
			<td width="15%"><span class="printLabel"><bean:message key="transfer.from"/></span></td>
			<td width="15%"><span class="printLabel"><bean:message key="transfer.to"/></span></td>
			<td width="15%"><span class="printLabel"><bean:message key="transfer.date"/></span></td>
			<td width="40%"><span class="printLabel"><bean:message key="transfer.description"/></span></td>
			<td width="15%"><span class="printLabel"><bean:message key="transfer.amount"/></span></td>
		</tr>
		<c:forEach var="childTransfer" items="${children}">
			<tr>
				<td width="15%" valign="top">
					<span class="printData">${childTransfer.actualFrom.ownerName}</span>
				</td>
				<td width="15%" valign="top">
					<span class="printData">${childTransfer.actualTo.ownerName}</span>
				</td>
				<td width="25%" valign="top">
	            	<%--if the transfer is a scheduled one then the date is stored without the hours/mins that is why we must use the raw date --%>					
					<span class="printData"><cyclos:format dateTime="${isScheduledPayment ? null : childTransfer.date}" rawDate="${isScheduledPayment ? childTransfer.date : null}"/></span>				
				</td>
				<td width="30%" valign="top">
					<span class="printData"><cyclos:escapeHTML>${childTransfer.description}</cyclos:escapeHTML></span>
				</td>
				<td width="15%" valign="top">
					<span class="printData"><cyclos:format number="${childTransfer.actualAmount}"/></span>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
</center>