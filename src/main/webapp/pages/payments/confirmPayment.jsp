<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/payments/confirmPayment.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="selectMember" />
<html:hidden property="from" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="payment.title.confirm"/></td>
        <cyclos:help page="payments#transaction_confirm"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTable">
            <table class="defaultTable">
            	<tr>
					<td colspan="2" class="label" style="text-align:center;padding-bottom:5px">
						<cyclos:escapeHTML><bean:message key="payment.confirmation.header${isScheduled ? '.scheduled' : wouldRequireAuthorization ? '.withAuthorization' : ''}"/></cyclos:escapeHTML>
					</td>
				</tr>
				<tr>
					<td class="headerLabel" width="35%"><bean:message key='transfer.type'/></td>
					<td class="headerField">${payment.transferType.name}</td>
				</tr>
				<c:if test="${not empty fromMember}">
					<tr>
						<td class="headerLabel" width="35%"><bean:message key='transfer.from'/></td>
						<td class="headerField">${fromMember.username} - ${fromMember.name}</td>
					</tr>
				</c:if>
				<tr>
					<td class="headerLabel" width="35%"><bean:message key='transfer.to'/></td>
					<td class="headerField">
						<c:choose><c:when test="${not empty toMember}">
							${toMember.username} - ${toMember.name}
						</c:when><c:otherwise>
							<bean:message key='global.system'/>
						</c:otherwise></c:choose>
					</td>
				</tr>
				<c:if test="${not empty payment.date}"> 
					<tr>
						<td class="headerLabel"><bean:message key='transfer.date'/></td>
						<td class="headerField"><cyclos:format rawDate="${payment.date}"/></td>
					</tr>
				</c:if>
				<tr>
					<td class="headerLabel"><bean:message key='transfer.amount'/></td>
					<td class="headerField"><cyclos:format number="${finalAmount}" unitsPattern="${unitsPattern}" /></td>
				</tr>
				<c:choose>
					<c:when test="${empty payment.payments}">
						<%-- Any handlings for empty payments? --%>
					</c:when>
					<c:when test="${fn:length(payment.payments) == 1}">
						<c:set var="firstPayment" value="${payment.payments[0]}" />
						<tr>
							<td class="headerLabel"><bean:message key='transfer.scheduledFor'/></td>
							<td class="headerField"><cyclos:format rawDate="${firstPayment.date}" /></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td class="headerLabel" valign="top"><bean:message key='scheduledPayment.parcels'/></td>
							<td class="headerField">
								<table class="nested">
									<tr>
										<th class="tdHeaderContents" width="20%"><bean:message key='transfer.number'/></th>
										<th class="tdHeaderContents" width="40%"><bean:message key='transfer.date'/></th>
										<th class="tdHeaderContents" width="40%"><bean:message key='transfer.amount'/></th>
									</tr>
									<c:forEach var="current" items="${payment.payments}" varStatus="status">
										<tr>
											<th class="tdHeaderContents">${status.count}</th>
											<td align="center" nowrap="nowrap"><cyclos:format rawDate="${current.date}" /></td>
											<td align="center" nowrap="nowrap"><cyclos:format number="${current.finalAmount}" unitsPattern="${unitsPattern}" /></td>
										</tr>									
									</c:forEach>
								</table>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<c:if test="${not empty totalAmount}">
					<tr>
						<td class="headerLabel"><bean:message key='transfer.totalAmount'/></td>
						<td class="headerField"><cyclos:format number="${totalAmount}" unitsPattern="${unitsPattern}" /></td>
					</tr>
				</c:if>
				<c:if test="${not empty fees}">
	               	<tr>
						<td class="headerLabel" valign="top"><bean:message key="payment.confirmation.appliedFees"/></td>
						<td class="headerField">
							<c:forEach var="fee" items="${fees}" varStatus="status">
								<span style="font-style:italic">${fee.key.name}</span>.&nbsp;
								<span class="inlineLabel"><bean:message key='transfer.amount'/>:</span>
								<cyclos:format number="${fee.value}" unitsPattern="${fee.key.generatedTransferType.from.currency.pattern}"/>
								<c:if test="${!status.last}"><br></c:if>
							</c:forEach>
						</td>
					</tr>            	
				</c:if>

			    <c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
			        <c:if test="${not empty value.value}">
			            <tr>
			                <td class="headerLabel">${field.name}</td>
			   				<td class="headerField" colspan="2">
			   					<cyclos:customField field="${field}" value="${value}" textOnly="true"/>
			   				</td>
						</tr>
					</c:if>
			    </c:forEach>
				<c:if test="${not empty payment.description}">
					<tr>
						<td class="headerLabel" valign="top"><bean:message key='transfer.description'/></td>
						<td class="headerField"><cyclos:escapeHTML>${payment.description}</cyclos:escapeHTML></td>
					</tr>
				</c:if>
				
				<c:set var="confirmationMessage" value="${payment.transferType.confirmationMessage}"/>
				<c:if test="${not empty confirmationMessage}">
					<tr>
						<td colspan="2" class="label" style="text-align:center;padding-bottom:5px">
							<hr>
							
							<cyclos:escapeHTML>${confirmationMessage}</cyclos:escapeHTML>
						</td>
					</tr>
				</c:if>
				
				<c:choose><c:when test="${requestTransactionPassword}">
					<tr>
						<td colspan="2" class="label" style="text-align:center;padding-bottom:10px">
							<hr>
							
							<cyclos:escapeHTML><bean:message key='payment.confirmation.transactionPassword'/></cyclos:escapeHTML>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center"><jsp:include page="/do/transactionPassword" /></td>
					</tr>
				</c:when><c:otherwise>
					<tr>
						<td colspan="2" align="right"><input type="submit" class="button" value="<bean:message key='global.submit'/>"></td>
					</tr>
				</c:otherwise></c:choose>
            </table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td>
			<input type="button" class="button" id="backButton" value="<bean:message key='global.back'/>">
		</td>
	</tr>
</table>
</ssl:form>