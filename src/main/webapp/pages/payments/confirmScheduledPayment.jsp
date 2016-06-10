<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/payments/confirmScheduledPayment.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="transferId" value="${transfer.id}" />

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
						<cyclos:escapeHTML><bean:message key="payment.confirmation.header${wouldRequireAuthorization ? '.withAuthorization' : ''}"/></cyclos:escapeHTML>
					</td>
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
				<tr>
					<td class="headerLabel"><bean:message key='scheduledPayment.transfer'/></td>
					<td class="headerField"><bean:message key='scheduledPayment.transferNumber' arg0="${transferNumber}" arg1="${numberOfTransfers}"/></td>
				</tr>
				<tr>
					<td class="headerLabel"><bean:message key='transfer.amount'/></td>
					<td class="headerField"><cyclos:format number="${finalAmount}" unitsPattern="${unitsPattern}" /></td>
				</tr>
				<tr>
					<td class="headerLabel"><bean:message key='transfer.scheduledFor'/></td>
					<td class="headerField"><cyclos:format rawDate="${transfer.date}" /></td>
				</tr>
				<tr>
					<td class="headerLabel" valign="top"><bean:message key='transfer.description'/></td>
					<td class="headerField"><cyclos:escapeHTML>${transfer.description}</cyclos:escapeHTML></td>
				</tr>
				<c:if test="${not empty fees}">
	               	<tr>
						<td class="headerLabel"><bean:message key="payment.confirmation.appliedFees"/></td>
						<td class="headerField">
							<c:forEach var="fee" items="${fees}">
								<div>
									<span style="font-style:italic">${fee.key.name}</span>.&nbsp;
									<span class="label"><bean:message key='transfer.amount'/>:</span>
									<cyclos:format number="${fee.value}" unitsPattern="${unitsPattern}"/>
								</div>
							</c:forEach>
						</td>
					</tr>            	
				</c:if>
				
				<c:set var="confirmationMessage" value="${transfer.type.confirmationMessage}"/>
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