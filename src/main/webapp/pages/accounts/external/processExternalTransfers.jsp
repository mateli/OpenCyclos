<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/external/processExternalTransfers.js" />
<c:set var="amountLabel"><bean:message key="externalTransfer.amount"/></c:set>
<script>
	var noneSelectedMessage = "<cyclos:escapeJS><bean:message key="global.error.nothingSelected"/></cyclos:escapeJS>";
	var amountRequiredMessage = "<cyclos:escapeJS><bean:message key="errors.required" arg0="${amountLabel}"/></cyclos:escapeJS>";
	var processConfirmationMessage = "<cyclos:escapeJS><bean:message key="externalTransferProcess.confirmation"/></cyclos:escapeJS>";
	var actions = $H();
</script>
<ssl:form action="${actionPrefix}/processExternalTransfers" method="post">
<html:hidden property="externalAccountId" value="${externalAccount.id}" />
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="externalTransferProcess.title" arg0="${externalAccount.name}"/></td>
        <cyclos:help page="bookkeeping#external_transfer_process"/>
    </tr>
	<tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                    <th class="tdHeaderContents" width="5%">&nbsp;</th>
                    <th class="tdHeaderContents" width="15%"><bean:message key='externalTransfer.date'/></th>
                    <th class="tdHeaderContents" width="20%"><bean:message key='externalTransfer.member'/></th>
                    <th class="tdHeaderContents" width="40%"><bean:message key='externalTransfer.type'/></th>
                    <th class="tdHeaderContents" width="20%"><bean:message key='externalTransfer.amount'/></th>
                </tr>
				<c:forEach var="transferVO" items="${externalTransfers}" varStatus="status">
					<c:set var="transfer" value="${transferVO.transfer}"/>
					<c:set var="transfersToConciliate" value="${transferVO.transfersToConciliate}"/>
					<c:set var="loansToDiscard" value="${transferVO.loansToDiscard}"/>
					<c:set var="action" value="${cyclos:name(transfer.type.action)}" />
					
	                <tr class="<t:toggle>ClassColor2|ClassColor1</t:toggle>">
	                    <td>&nbsp;
	                		<c:if test="${action == 'IGNORE'}">
	                			<%-- Ignored transfers should just submit all default data --%>
	                			<input type="hidden" name="selection" id="sel_${transfer.id}">
	                			<input type="hidden" name="externalTransfer" id="id_${transfer.id}" value="${transfer.id}">
	                			<input type="hidden" name="date" id="date_${transfer.id}">
                    			<input type="hidden" name="loan" id="loan_${transfer.id}" />
                    			<input type="hidden" name="transfer" id="transfer_${transfer.id}" />
	                		</c:if>
	                    </td>
	                    <td style="padding-left: 5px;"><cyclos:format rawDate="${transfer.date}"/></td>
	                    <td><cyclos:profile elementId="${transfer.member.id}"/></td>
	                    <td align="center">${transfer.type.name}</td>
                    	<td style="padding-right: 5px;" align="right"><cyclos:format number="${transfer.amount}"/></td>
					</tr>
               		<c:choose><c:when test="${action == 'IGNORE'}">
						<%-- Force a toggle on comments to not mess up the row colors --%>
						<!-- <t:toggle>ClassColor2|ClassColor1</t:toggle> -->
					</c:when><c:otherwise>
		                <tr class="<t:toggle>ClassColor2|ClassColor1</t:toggle>">
		                	<td>
		                		<input type="checkbox" id="sel_${transfer.id}" name="selection" class="selection">
		                		<input type="hidden" name="externalTransfer" id="id_${transfer.id}" value="${transfer.id}" disabled="disabled"/>
		                		<script>actions.set("${transfer.id}", "${action}")</script>
		                	</td>
		                    <td nowrap="nowrap" >
		                    	<c:choose><c:when test="${canSetDate && action != 'IGNORE'}">
			                    	<input type="text" name="date" id="date_${transfer.id}" class="InputBoxDisabled small dateNoLabel" value="<cyclos:format rawDate="${transfer.date}"/>" disabled="disabled">
			                    </c:when><c:otherwise>
		                    		<c:if test="${action != 'IGNORE'}">
				                    	${today}
				                    </c:if>
				                    <input type="hidden" name="date" id="date_${transfer.id}" disabled="disabled">
				                </c:otherwise></c:choose>
		                    </td>
		                    <td><div style="overflow:hidden">${transfer.member.name}</div></td>
		                    <td align="center">
		                    	<c:choose>
		                    		<c:when test="${action == 'GENERATE_MEMBER_PAYMENT' || action == 'GENERATE_SYSTEM_PAYMENT'}">
		                    			${transfer.type.transferType.name}
		                    			<input type="hidden" name="loan" id="loan_${transfer.id}" disabled="disabled" />
		                    			<input type="hidden" name="transfer" id="transfer_${transfer.id}" disabled="disabled" />
		                    		</c:when>
		                    		<c:when test="${action == 'DISCARD_LOAN'}">
		                    			<c:choose><c:when test="${empty transferVO.loansToDiscard}">
		                    				<div class="fieldDecoration"><bean:message key="externalTransferProcess.error.noLoanToDiscard"/></div>
		                    				<input type="hidden" id="loan_${transfer.id}" name="loan" disabled="disabled">
			                    			<script>$("sel_${transfer.id}").remove();</script>
			                    		</c:when><c:otherwise>
			                    			<select name="loan" id="loan_${transfer.id}" class="InputBoxDisabled full" disabled="disabled">
			                    				<c:forEach var="loan" items="${transferVO.loansToDiscard}">
			                    					<c:set var="openPayment" value="${loan.firstOpenPayment}"/>
			                    					<c:set var="number" value="${openPayment.number}"/>
			                    					<c:set var="expirationDate"><cyclos:format rawDate="${openPayment.expirationDate}"/></c:set>
			                    					<c:set var="remainingAmount"><cyclos:format number="${openPayment.remainingAmount}"/></c:set>
			                    					<option value="${loan.id}" <c:if test="${openPayment.remainingAmount == transfer.amount}">selected="selected"</c:if>><bean:message key="externalTransferProcess.loanDescription" arg0="${number}" arg1="${expirationDate}" arg2="${remainingAmount}"/></option>
			                    				</c:forEach>
			                    			</select>
			                    		</c:otherwise></c:choose>
		                    			<input type="hidden" name="transfer" id="transfer_${transfer.id}" disabled="disabled"/>
		                    		</c:when>
		                    		<c:when test="${action == 'CONCILIATE_PAYMENT'}">
		                    			<c:choose><c:when test="${empty transferVO.transfersToConciliate}">
		                    				<div class="fieldDecoration"><bean:message key="externalTransferProcess.error.noPaymentToConciliate"/></div>
		                    				<input type="hidden" id="transfer_${transfer.id}" name="transfer" disabled="disabled">
			                    			<script>$("sel_${transfer.id}").remove();</script>
			                    		</c:when><c:otherwise>
			                    			<select name="transfer" id="transfer_${transfer.id}" class="InputBoxDisabled full" disabled="disabled">
			                    				<c:forEach var="payment" items="${transferVO.transfersToConciliate}">
			                    					<c:set var="paymentDate"><cyclos:format date="${payment.date}"/></c:set>
			                    					<c:set var="paymentAmount"><cyclos:format number="${payment.amount}"/></c:set>
			                    					<option value="${payment.id}" <c:if test="${payment.amount == transfer.amount}">selected="selected"</c:if>><bean:message key="externalTransferProcess.paymentDescription" arg0="${paymentDate}" arg1="${paymentAmount}"/></option>
			                    				</c:forEach>
			                    			</select>
			                    		</c:otherwise></c:choose>
		                    			<input type="hidden" name="loan" id="loan_${transfer.id}" disabled="disabled"/>
		                    		</c:when>
		                    	</c:choose>
							</td>
		                    <td align="right">
		                    	<c:choose><c:when test="${action == 'GENERATE_MEMBER_PAYMENT' || action == 'GENERATE_SYSTEM_PAYMENT'}">
		                    		<input type="text" name="amount" id="amount_${transfer.id}" class="InputBoxDisabled full float" style="text-align:right" disabled="disabled" value="<cyclos:format number="${transfer.amount}" />">
	                    		</c:when><c:otherwise>
		                    		<input type="hidden" name="amount" id="amount_${transfer.id}" disabled="disabled">
	                    		</c:otherwise></c:choose>
							</td>
						</tr>
					</c:otherwise></c:choose>
					<c:if test="${not status.last}">
						<tr class="ClassColor1">
							<td colspan="5"><hr></td>
						</tr>
					</c:if>
					<cyclos:clearCache/>
				</c:forEach>                
            </table>
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td>
			<input id="selectAllButton" type="button" class="button" value="<bean:message key="global.selectAll"/>">
			<input id="selectNoneButton" type="button" class="button" value="<bean:message key="global.selectNone"/>">
		</td>
		<td align="right">
			<input type="submit" class="button" value="<bean:message key="global.submit"/>">
		</td>
	</tr>
	<tr>
		<td>
			
			<input id="backButton" type="button" class="button" value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>
</ssl:form>