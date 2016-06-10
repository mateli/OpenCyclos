<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<c:if test="${not empty lastPayment}">
	<cyclos:script src="/pages/posweb/includes/printTransactionReceipt.js" />
	<cyclos:script src="/pages/posweb/includes/lastPayment.js" />
	
	<c:set var="unitsPattern" value="${lastPayment.from.type.currency.pattern}" />
	<div id="printDiv" style="display: none; position: relative;">
	<table cellpadding="0" cellspacing="0" style="width: 100%; height: 100%">
		<tr>
			<td align="center" valign="middle">
				<a class="default" id="printLastReceipt" paymentId="${lastPayment.id}" isScheduled="${lastPaymentIsScheduled}">
					<bean:message key="posweb.printReceipt" />
				</a>
				<br>
				<br>
			<table cellpadding="0" cellspacing="0" style="margin: auto">
				<c:choose>
					<c:when test="${lastPaymentIsScheduled}">
						<tr>
							<td align="right" nowrap="nowrap" width="40%"><b><bean:message key="transfer.submitDate" /></b></td>
							<td><cyclos:format dateTime="${lastPayment.date}" /></td>
						</tr>
					</c:when>
					<c:when test="${not empty lastPayment.processDate and lastPayment.processedAtDifferentDate}">
						<tr>
							<td align="right" nowrap="nowrap" width="40%"><b><bean:message key="transfer.submitDate" /></b></td>
							<td><cyclos:format dateTime="${lastPayment.date}" /></td>
						</tr>
						<tr>
							<td align="right"><b><bean:message key="transfer.processDate" /></b></td>
							<td><cyclos:format dateTime="${lastPayment.processDate}" /></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td align="right" nowrap="nowrap" width="40%"><b><bean:message key="transfer.date" /></b></td>
							<td><cyclos:format dateTime="${lastPayment.date}" /></td>
						</tr>
					</c:otherwise>
				</c:choose>
				<c:if test="${lastPaymentIsScheduled && fn:length(lastPayment.transfers) == 1}">
					<tr>
						<td align="right" nowrap="nowrap"><b><bean:message key="transfer.scheduledFor" /></b></td>
						<td><cyclos:format rawDate="${lastPayment.transfers[0].date}" /></td>
					</tr>
				</c:if>
				<c:if test="${!lastPaymentIsScheduled}">
					<c:if test="${not empty lastPayment.transactionNumber}">
						<tr>
							<td align="right" nowrap="nowrap"><b><bean:message key="transfer.transactionNumber" /></b></td>
							<td>${lastPayment.transactionNumber}</td>
						</tr>
					</c:if>
				</c:if>
				<tr>
					<td align="right" nowrap="nowrap"><b><bean:message key="transfer.from" /></b></td>
					<td>
						<c:set var="from" value="${lastPayment.from.owner}" />
						${from.username} - ${from.name}
					</td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap"><b><bean:message key="transfer.to" /></b></td>
					<td>
						<c:set var="to" value="${lastPayment.to.owner}" />
						${to.username} - ${to.name}
					</td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap"><b><bean:message key='transfer.type' /></b></td>
					<td>${lastPayment.type.name}</td>
				</tr>
				<tr>
					<td align="right" nowrap="nowrap"><b><bean:message key="transfer.amount" /></b></td>
					<td><cyclos:format number="${lastPayment.amount}" unitsPattern="${unitsPattern}" /></td>
				</tr>
				<c:forEach var="entry" items="${lastPaymentCustomValues}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
			        <c:if test="${not empty value.value}">
			            <tr>
			            	<td align="right" nowrap="nowrap" valign="top"><b>${field.name}</b></td>
			   				<td><cyclos:customField field="${field}" value="${value}" textOnly="true"/></td>
						</tr>
					</c:if>
				</c:forEach>
				<c:if test="${lastPaymentIsScheduled && fn:length(lastPayment.transfers) > 1}">
					<tr>
						<td align="right" nowrap="nowrap" valign="top"><b><bean:message key="scheduledPayment.parcels" /></b></td>
						<td>
							<table class="nested">
								<tr>
									<td align="center" nowrap="nowrap"><b><bean:message key='transfer.number'/></b>&nbsp;</td>
									<td align="center" nowrap="nowrap">&nbsp;<b><bean:message key='transfer.date'/></b>&nbsp;</td>
									<td align="center" nowrap="nowrap">&nbsp;<b><bean:message key='transfer.amount'/></b></td>
								</tr>
								<c:forEach var="current" items="${lastPayment.transfers}" varStatus="status">
									<tr>
										<td align="center" nowrap="nowrap">${status.count}&nbsp;</td>
										<td align="center" nowrap="nowrap">&nbsp;<cyclos:format rawDate="${current.date}" />&nbsp;</td>
										<td align="center" nowrap="nowrap">&nbsp;<cyclos:format number="${current.amount}" unitsPattern="${unitsPattern}" /></td>
									</tr>									
								</c:forEach>
							</table>
						</td>
					</tr>
				</c:if>
				<c:if test="${showDescription}">
					<tr>
						<td align="right" nowrap="nowrap"><b><bean:message key="transfer.description" /></b></td>
						<td><cyclos:escapeHTML>${lastPayment.description}</cyclos:escapeHTML></td>
					</tr>
				</c:if>
			</table>
			
			<input type="button" id="closePrint" class="button" value="<bean:message key="global.close" />"></td>
		</tr>
	</table>
	<c:remove var="lastPayment" />
	<c:remove var="lastPaymentIsScheduled" />
	<c:remove var="lastPaymentCustomValues" />
	</div>
</c:if>