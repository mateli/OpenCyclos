<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<c:set var="showActions" value="${viewTransactionForm.showActions || !requestTransactionPassword}" />
<c:set var="unitsPattern" value="${payment.type.from.currency.pattern}" />
<c:set var="singleInstallment" value="${false}" />
<cyclos:script src="/pages/accounts/details/viewScheduledPayment.js" />
<script>
	var paymentId = "${payment.id}";
	var payNow = "<bean:message key="payment.payNow"/>";
	<c:if test="${canCancel}">
		var cancelConfirmationMessage = "<cyclos:escapeJS><bean:message key="payment.confirmation.cancel"/></cyclos:escapeJS>";
	</c:if>
	<c:if test="${canBlock}">
		var blockConfirmationMessage = "<cyclos:escapeJS><bean:message key="payment.confirmation.block"/></cyclos:escapeJS>";
	</c:if>
	<c:if test="${canUnblock}">
		var unblockConfirmationMessage = "<cyclos:escapeJS><bean:message key="payment.confirmation.unblock"/></cyclos:escapeJS>";
	</c:if>
</script>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
       		<bean:message key="scheduledPayment.title.details"/>
        </td>
        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
        	<img class="print" paymentId="${payment.id}" src="<c:url value="/pages/images/print.gif" />">
        	<cyclos:help page="payments#view_scheduled_payment" td="false"/>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<tr>
            		<td class="label tdHeaderContents" width="25%"><bean:message key='transfer.submitDate'/></td>
            		<td><cyclos:format dateTime="${payment.date}"/></td>
            	</tr>
            	<c:if test="${not empty payment.processDate}">
	            	<tr>
	            		<td class="label tdHeaderContents"><bean:message key='transfer.processDate'/></td>
	            		<td><cyclos:format dateTime="${payment.processDate}"/></td>
	            	</tr>
            	</c:if>
            	<c:if test="${empty payment.processDate && fn:length(payment.transfers) == 1}">
            		<c:set var="singleInstallment" value="${true}" />
	            	<tr>
	            		<td class="label tdHeaderContents"><bean:message key='transfer.scheduledFor'/></td>
	            		<td><cyclos:format rawDate="${payment.transfers[0].date}"/></td>
	            	</tr>
            	</c:if>            	
            	<tr>
					<td class="label tdHeaderContents"><bean:message key="transfer.from"/></td>
            		<td>
            			<c:choose><c:when test="${payment.fromSystem}">
	            			${payment.from.ownerName}
            			</c:when><c:otherwise>
            				<cyclos:profile elementId="${payment.from.owner.id}"/>
            			</c:otherwise></c:choose>
            		</td>
            	</tr>
            	<tr>
					<td class="label tdHeaderContents"><bean:message key="transfer.to"/></td>
            		<td>
            			<c:choose><c:when test="${payment.toSystem}">
	            			${payment.to.ownerName}
            			</c:when><c:otherwise>
            				<cyclos:profile elementId="${payment.to.owner.id}"/>
            			</c:otherwise></c:choose>
            		</td>
            	</tr>
            	<c:if test="${showBy}">
	            	<tr>
						<td class="label tdHeaderContents"><bean:message key="transfer.by"/></td>
	            		<td>
	            			<c:choose>
	            				<c:when test="${not empty byMember}">
	    	        				<cyclos:profile elementId="${byMember.id}"/>
		            			</c:when>
	            				<c:when test="${not empty byOperator}">
	    	        				<cyclos:profile elementId="${byOperator.id}"/>
		            			</c:when>
		            			<c:when test="${not empty byAdmin}">
		            				<cyclos:profile elementId="${byAdmin.id}"/>
		            			</c:when>
		            			<c:when test="${bySystem}">
		            				${localSettings.applicationUsername}
		            			</c:when>
		            		</c:choose>
	            		</td>
	            	</tr>
	            </c:if>
            	<tr>
            		<td class="label tdHeaderContents"><bean:message key='transfer.type'/></td>
            		<td>${payment.type.name}</td>
            	</tr>
            	<tr>
					<td class="label tdHeaderContents"><bean:message key="${singleInstallment ? 'transfer.amount' : 'scheduledPayment.totalAmount'}"/></td>
            		<td><cyclos:format number="${payment.amount}" unitsPattern="${unitsPattern}" /></td>
            	</tr>
            	<tr>
					<td class="label tdHeaderContents"><bean:message key="payment.status"/></td>
            		<td><bean:message key="payment.status.${payment.status}"/></td>
            	</tr>
				<c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
			        <c:if test="${not empty value.value}">
			            <tr>
			                <td class="label tdHeaderContents">${field.name}</td>
			   				<td colspan="2">
			   					<cyclos:customField field="${field}" value="${value}" textOnly="true"/>
			   				</td>
						</tr>
					</c:if>
			    </c:forEach>
            	<tr>
					<td class="label tdHeaderContents" valign="top"><bean:message key='transfer.description'/></td>
            		<td><cyclos:escapeHTML>${payment.description}</cyclos:escapeHTML></td>
            	</tr>
            	<c:if test="${!showActions && (canBlock || canUnblock || canCancel)}">
	            	<tr id="trActions">
	            		<td colspan="2" align="right">
	            			<a class="default" id="toggleActionsLink">
	            				<bean:message key="payment.actions" />
	            			</a>
	            		</td>
	            	</tr>
            	</c:if>

            	<c:if test="${requestTransactionPassword}">
					<tr class="trAction" style="display:${showActions ? '' : 'none'}">
						<td colspan="2" class="label" style="text-align:center;padding-bottom:10px">
							<hr>
							<cyclos:escapeHTML><bean:message key="payment.cancel.transactionPassword"/></cyclos:escapeHTML>
						</td>
					</tr>
					<tr class="trAction" style="display:${showActions ? '' : 'none'}">
						<td colspan="2" align="center">
							<c:set var="hideSubmit" value="${true}" scope="request"/>
							<c:set var="transactionPasswordField" value="_transactionPassword" scope="request" />
							<jsp:include page="/do/transactionPassword"/>
						</td>
					</tr>
				</c:if>
            	<tr class="trAction" style="display:${showActions ? '' : 'none'}">
					<td colspan="2" align="right">
						<table cellpadding="0" cellspacing="0">
							<tr>
								<c:if test="${canBlock}">
									<td><ssl:form method="post" styleId="blockForm" action="${actionPrefix}/blockScheduledPayment">
										<input type="hidden" name="paymentId" value="${payment.id}"/>
										<html:hidden property="transactionPassword"/>&nbsp;&nbsp;
										<input type="submit" class="button" value="<bean:message key='payment.action.block'/>">
									</ssl:form></td>
								</c:if>
								<c:if test="${canUnblock}">
									<td><ssl:form method="post" styleId="unblockForm" action="${actionPrefix}/unblockScheduledPayment">
										<input type="hidden" name="paymentId" value="${payment.id}"/>
										<html:hidden property="transactionPassword"/>&nbsp;&nbsp;
										<input type="submit" class="button" value="<bean:message key='payment.action.unblock'/>">
									</ssl:form></td>
								</c:if>
								<c:if test="${canCancel}">
									<td><ssl:form method="post" styleId="cancelForm" action="${actionPrefix}/cancelScheduledPayment">
										<input type="hidden" name="paymentId" value="${payment.id}"/>
										<html:hidden property="transactionPassword"/>&nbsp;&nbsp;
										<input type="submit" class="button" value="<bean:message key='payment.action.cancel'/>">
									</ssl:form></td>
								</c:if>
								<c:if test="${canPayNow}">
									<td><input type="button" class="button" id="payNowButton" transferId="${payment.transfers[0].id}" value="<bean:message key='payment.action.payNow'/>"></td>
								</c:if>
							</tr>
						</table>
					</td>
				</tr>
            </table>
        </td>
    </tr>
</table>


<c:if test="${fn:length(payment.transfers) > 1}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="scheduledPayment.title.transfers"/></td>
	        <cyclos:help page="payments#sheduled_payment_transfers"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents" width="25%"><bean:message key='transfer.date'/></th>
	                    <th class="tdHeaderContents" width="25%"><bean:message key='transfer.amount'/></th>
	                    <th class="tdHeaderContents" width="40%"><bean:message key='payment.status'/></th>
	                    <th class="tdHeaderContents" width="10%"></th>
	                </tr>
					<c:forEach var="transfer" varStatus="status" items="${payment.transfers}">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="center"><cyclos:format date="${transfer.date}"/></td>
							<td align="center"><cyclos:format number="${transfer.amount}" unitsPattern="${unitsPattern}"/></td>
							<td align="center"><bean:message key="payment.status.${transfer.status}"/></td>
							<td align="center"><img class="view showTransfer" transferId="${transfer.id}" src="<c:url value="/pages/images/view.gif"/>"/></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
	
</c:if>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left">
			<input class="button" type="button" id="backButton" value="<bean:message key='global.back'/>">
		</td>
	</tr>
</table>