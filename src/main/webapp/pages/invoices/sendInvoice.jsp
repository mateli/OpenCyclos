<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:choose>
	<c:when test="${toSystem}">
		<c:set var="titleKey" value="invoice.title.send.system" />
		<c:set var="helpPage" value="invoices#send_invoice_member_to_system" />
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="invoice.title.send.member" />
		<c:choose>
			<c:when test="${isAdmin}">
				<c:set var="helpPage" value="invoices#send_invoice_system_to_member" />
			</c:when>
			<c:when test="${selectMember}">
				<c:set var="helpPage" value="invoices#send_invoice_member_to_member_select" />
			</c:when>
			<c:otherwise>
				<c:set var="helpPage" value="invoices#send_invoice_member_to_member" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<cyclos:script src="/pages/invoices/sendInvoice.js" />
<script>
	var useTransferType = ${useTransferType};
	var useDestAccountType = !useTransferType;
	var paymentNumberLabel = "<cyclos:escapeJS><bean:message key='transfer.number'/></cyclos:escapeJS>";
	var paymentDateLabel = "<cyclos:escapeJS><bean:message key='transfer.date'/></cyclos:escapeJS>";
	var paymentAmountLabel = "<cyclos:escapeJS><bean:message key='transfer.amount'/></cyclos:escapeJS>";
	var profileMemberId = "${not empty from ? from.id : member.id}";
	var fromId = "${from.id}";
</script>

<ssl:form method="post" action="${formAction}">
<html:hidden property="toSystem"/>
<html:hidden property="selectMember"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
        <cyclos:help page="${helpPage}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<c:if test="${not empty from}">
					<tr>
						<td class="label" width="25%"><bean:message key='invoice.from'/></td>
						<td>
							<html:hidden property="from"/>
							<input class="InputBoxDisabled large" readonly="readonly" value="${from.name}">
						</td>
					</tr>
            	</c:if>
            	<c:choose>
            		<c:when test="${toSystem}">
						<%-- Any special handling for member-to-system invoice? --%>
	            	</c:when>
            		<c:when test="${selectMember}">
						<%-- The user must select a member --%>
						<tr>
							<td class="label" width="25%"><bean:message key='payment.recipientUsername'/></td>
							<td>
								<html:hidden styleId="memberId" property="to"/>
								<input id="memberUsername" class="large">
								<div id="membersByUsername" class="autoComplete"></div>
							</td>
						</tr>
						<tr>
							<td class="label" width="25%"><bean:message key='payment.recipientName'/></td>
							<td>
								<input id="memberName" class="large">
								<div id="membersByName" class="autoComplete"></div>
							</td>
						</tr>
	            	</c:when>
            		<c:otherwise>
						<%-- The member has been already selected from the profile --%>            		
						<tr>
							<td class="label" width="25%"><bean:message key='invoice.to'/></td>
							<td>
								<html:hidden property="to"/>
								<input class="InputBoxDisabled large" readonly="readonly" value="${member.name}">
							</td>
						</tr>
            		</c:otherwise>
            	</c:choose>
				<tr>
					<td class="label"><bean:message key='invoice.amount'/></td>
					<td>
						<html:text styleId="amount" property="amount" styleClass="float medium"/>
						<c:choose><c:when test="${not empty singleCurrency}">
							<html:hidden property="currency" value="${singleCurrency.id}" />
							${singleCurrency.symbol}
						</c:when><c:otherwise>
							<html:select property="currency" styleId="currencySelect">
    	        				<c:forEach var="currency" items="${currencies}">
        	    					<html:option value="${currency.id}">${currency.symbol}</html:option>
            					</c:forEach>
            				</html:select>
						</c:otherwise></c:choose>
					</td>
				</tr>
				<tr id="destTypeRow" style="display:none">
					<td class="label" width="25%"><bean:message key='invoice.destination'/></td>
					<td id="destTypeCell"></td>
				</tr>
				<tr id="typeRow" style="display:none">
					<td class="label"><bean:message key='invoice.transferType'/></td>
					<td id="typeCell"></td>
				</tr>
				<tr id="customValuesRow" style="display:none">
					<td colspan="2" id="customValuesCell" style="padding:0px;">
					</td>
				</tr>
				
				<c:if test="${allowsScheduling}">
					<tr id="trSchedulingType" class="scheduling" style="display:none">
						<td class="label"><bean:message key='transfer.scheduling'/></td>
						<td>
							<select id="schedulingTypeSelect">
								<c:forEach var="type" items="${schedulingTypes}">
									<option value="${type}"><bean:message key='transfer.scheduling.${type}'/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					
					<tr class="scheduling singlePayment" style="display:none">
						<td class="label"><bean:message key='transfer.scheduledFor'/></td>
						<td>
							<input type="text" class="small date" id="scheduleForText">
							<div id="singlePaymentContainer" style="display:none"></div>
						</td>
					</tr>
					
					<c:if test="${allowsMultipleScheduling}">
						<tr class="scheduling multiplePayments" style="display:none">
							<td class="label"><bean:message key='transfer.paymentCount'/></td>
							<td nowrap="nowrap">
								<input type="text" class="tiny number" maxlength="2" id="paymentCountText">
								&nbsp;&nbsp;&nbsp;
								<span class="inlineLabel"><bean:message key='transfer.firstPaymentDate'/></span>
								<input type="text" class="small date" id="firstPaymentDateText">
							</td>
						</tr>
						<tr class="scheduling multiplePayments" style="display:none">
							<td class="label" width="25%"><bean:message key='transfer.paymentEvery'/></td>
							<td nowrap="nowrap">
								<input type="text" class="tiny number" id="recurrenceNumberText">
								<select id="recurrenceFieldSelect">
									<c:forEach var="field" items="${schedulingFields}">
										<option value="${field}"><bean:message key='global.timePeriod.${field}'/></option>
									</c:forEach>
								</select>
								&nbsp;&nbsp;&nbsp;
								<input type="button" id="calculatePaymentsButton" class="button" value="<bean:message key="transfer.calculatePayments"/>">
							</td>
						</tr>
						<tr class="scheduling multiplePayments" style="display:none">
							<td></td>
							<td id="paymentsContainer"></td>
						</tr>
					</c:if>
				</c:if>
				
				<tr>
					<td class="label" valign="top"><bean:message key='invoice.description'/></td>
					<td><html:textarea styleId="description" property="description" styleClass="full" rows="6"/></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="submit" class="button" value="<bean:message key='global.submit'/>"></td>
				</tr>
            </table>
        </td>
    </tr>
</table>
<c:if test="${!(toSystem || selectMember) || not empty from}">
	
	<table class="defaultTableContentHidden">
		<tr>
			<td>
				<input type="button" class="button" id="backButton" value="<bean:message key='global.back'/>">
			</td>
		</tr>
	</table>
</c:if>
</ssl:form>
<script>
<c:choose><c:when test="${useTransferType}">
	var types = [];
	var noTransferTypeMessage = "<cyclos:escapeJS><bean:message key='invoice.error.noTransferType'/></cyclos:escapeJS>";

	<c:forEach var="type" items="${transferTypes}">
		types.push({'id':${type.id}, 'name':'<cyclos:escapeJS>${type.name}</cyclos:escapeJS>', 'currencyId':${type.from.currency.id}});
	</c:forEach>

	if (types.length == 0) {
		types = null;
	} else {
		updateTransferTypes(types);
	}
</c:when><c:otherwise>
	var destTypes = [];
	var noDestAccountTypeMessage = "<cyclos:escapeJS><bean:message key='invoice.error.noAccountType'/></cyclos:escapeJS>";

	<c:forEach var="type" items="${accountTypes}">
		destTypes.push({'id':${type.id}, 'name':'<cyclos:escapeJS>${type.name}</cyclos:escapeJS>', 'allowsScheduledPayments':'type.allowsScheduledPayments', 'currencyId':${type.currency.id}});
	</c:forEach>
	
	if (destTypes.length == 0) {
		destTypes = null;
	} else {
		updateDestAccountTypes(destTypes);
	}
</c:otherwise></c:choose>
</script>