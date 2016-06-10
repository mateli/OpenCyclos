<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<script>
	var selectTransferTypeMessage = "<cyclos:escapeJS><bean:message key="payment.selectTransferType" /></cyclos:escapeJS>";
	var noTransferTypeMessage = "<cyclos:escapeJS><bean:message key="payment.error.noTransferType" /></cyclos:escapeJS>";
	var invalidPrincipalMessage = "<cyclos:escapeJS><bean:message key="errors.invalid" arg0="${selectedPrincipalLabel}" /></cyclos:escapeJS>";
	var loggedMember = "${loggedMember.id}";
	var principalType = "${selectedPrincipalType}";
	var today = "${today}";
	var schedulingTypes = [];
	<c:forEach var="type" items="${schedulingTypes}">
		schedulingTypes.push({value:"${type}", label:"<cyclos:escapeJS><bean:message key="transfer.scheduling.${type}" /></cyclos:escapeJS>"});
	</c:forEach>
	var firstPaymentDateLabel = "<cyclos:escapeJS><bean:message key='transfer.firstPaymentDate'/></cyclos:escapeJS>";
	var scheduledForLabel = "<cyclos:escapeJS><bean:message key='transfer.scheduledFor'/></cyclos:escapeJS>";
</script>
<cyclos:script src="/pages/posweb/receivePayment.js" />
<ssl:form method="post" action="${formAction}">
<html:hidden styleId="memberId" property="from"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="posweb.title.receivePayment"/></td>
        <td class="tdHelpIcon">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2" align="center" class="tdContentTableForms">
        	
            <table class="defaultTable" id="formTable">
				<tr>
					<td width="35%" class="label"><bean:message key='transfer.to'/></td>
					<td><input class="InputBoxDisabled full" readonly="readonly" value="${loggedMember.name}"></td>
				</tr>
				<tr>
					<td class="label"><bean:message key='transfer.amount'/></td>
					<td style="text-align: left">
						<html:text styleId="amount" property="amount" styleClass="float medium"/>
						<c:choose>
							<c:when test="${not empty singleCurrency}">
								<html:hidden property="currency" value="${singleCurrency.id}" />
								${singleCurrency.symbol}
							</c:when>
							<c:when test="${!loggedUser.element.group.basicSettings.hideCurrencyOnPayments}">
								<html:select property="currency" styleId="currencySelect">
	    	        				<c:forEach var="currency" items="${currencies}">
	        	    					<html:option value="${currency.id}">${currency.symbol}</html:option>
	            					</c:forEach>
	            				</html:select>
							</c:when>
						</c:choose>
					</td>
				</tr>
				<tr id="transferTypeRow" style="display:none">
					<td class="label"><bean:message key="transfer.type"/></td>
					<td><select id="transferType" name="type" class="full"></select></td>
				</tr>
				<c:if test="${showDescription}">
					<tr>
						<td class="label"><bean:message key="transfer.description"/></td>
						<td><html:textarea property="description" styleClass="full" rows="2" /></td>
					</tr>
				</c:if>
				<c:set var="principalSelect">
					<c:choose><c:when test="${fn:length(principalTypes) == 1}">
						${selectedPrincipalLabel}
					</c:when><c:otherwise>
						<html:select property="principalType" styleId="principalTypeSelect">
							<c:forEach var="type" items="${principalTypes}">
								<html:option value="${type.value}">${type.key}</html:option>
							</c:forEach>
						</html:select>
					</c:otherwise></c:choose>
				</c:set>
				<c:choose>
					<c:when test="${cyclos:name(selectedPrincipalType.principal) == 'USER'}">
						<tr>
							<td class="label">${principalSelect}</td>
							<td>
								<input id="memberUsername" class="full" name="principal">
								<div id="membersByUsername" class="autoComplete"></div>
							</td>
						</tr>
						<tr>
							<td class="label"><bean:message key="posweb.client.name"/></td>
							<td>
								<input id="memberName" class="full">
								<div id="membersByName" class="autoComplete"></div>
							</td>
						</tr>
					</c:when>
					<c:when test="${not empty selectedPrincipalType.customField}">
						<tr>
							<td class="label">${principalSelect}</td>
							<td><cyclos:customField field="${selectedPrincipalType.customField}" valueName="principal" styleId="principal" search="true" size="full"/></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td class="label">${principalSelect}</td>
							<td><input class="full ${selectedPrincipalType.principal.numeric ? 'number' : ''}" id="principal" name="principal"></td>
						</tr>
					</c:otherwise>
				</c:choose>
				<c:if test="${cyclos:name(selectedPrincipalType.principal) != 'USER'}">
					<tr>
						<td class="label"><bean:message key="posweb.client.name"/></td>
						<td><input id="memberNameDisplay" class="full InputBoxDisabled" readonly="readonly" tabindex="-1"></td>
					</tr>
				</c:if>
				<tr id="schedulingTypeRow" style="display:none">
					<td class="label"><bean:message key='transfer.scheduling'/></td>
					<td><html:select styleId="schedulingTypeSelect" property="schedulingType" /></td>
				</tr>
				<tr class="scheduling singlePayment" style="display:none">
					<td class="label"><bean:message key='transfer.scheduledFor'/></td>
					<td><html:text property="scheduledFor" styleClass="small date" /></td>
				</tr>
				<tr class="scheduling multiplePayments" class="scheduling" style="display:none">
					<td class="label"><bean:message key='transfer.paymentCount'/></td>
					<td><html:text property="paymentCount" styleClass="tiny number" maxlength="2" /></td>
				</tr>
				<tr class="scheduling multiplePayments" style="display:none">
					<td class="label"><bean:message key='transfer.firstPaymentDate'/></td>
					<td><html:text property="firstPaymentDate" styleClass="small date" /></td>
				</tr>
				<tr id="customValuesRow" style="display:none">
					<td colspan="2" id="customValuesCell" style="padding:0px;">
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="${credentialsKey}"/></td>
					<td><input type="password" name="credentials" class="full ${numericCredentials ? 'digits' : ''}"></td>
				</tr>
				<tr>
					<td>
						
						<input type="button" id="clearButton" style="margin:0px;float:left;" class="button" value="<bean:message key='global.clear'/> (Esc)">
					</td>
					<td align="right">
						
						<input type="submit" id="submitButton" class="button" value="<bean:message key='global.submit'/>">
					</td>
				</tr>
            </table>
            <jsp:include page="includes/lastPayment.jsp"></jsp:include>
        </td>
    </tr>
</table>
</ssl:form>
