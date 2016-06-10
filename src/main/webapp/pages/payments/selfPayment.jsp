<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/payments/basePayment.js" />
<cyclos:script src="/pages/payments/selfPayment.js" />
<script>
	var memberIdSelfPayment = "${member.id}";
	var transferTypes = [];
	<c:forEach var="type" items="${transferTypes}">
		transferTypes.push({'id':${type.id}, 'name':'<cyclos:escapeJS>${type.name}</cyclos:escapeJS>', 'currencyId':'${type.from.currency.id}'});
	</c:forEach>
</script>

<ssl:form method="post" action="${formAction}">
<html:hidden property="from" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}" arg0="${member.name}" /></td>
        <cyclos:help page="${(empty member) && (isAdmin) ? 'payments#to_system' : 'payments#member_self_payments'}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
					<td class="label" width="25%"><bean:message key='transfer.amount'/></td>
					<td>
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
				<tr id="typeRow" style="display:none">
					<td class="label" width="25%"><bean:message key='transfer.type'/></td>
					<td id="typeCell"></td>
				</tr>
				<tr id="customValuesRow" style="display:none">
					<td colspan="2" id="customValuesCell" style="padding:0px;">
					</td>
				</tr>
				<c:if test="${cyclos:granted(AdminMemberPermission.PAYMENTS_PAYMENT_WITH_DATE)}">
					<tr>
						<td class="label" width="25%"><bean:message key='payment.setDate'/></td>
						<td><input type="checkbox" class="checkbox" id="setDateCheck"></td>
					</tr>
					<tr id="pastDate" style="display:none">
						<td class="label"><bean:message key='payment.manualDate'/></td>
						<td><html:text styleId="dateText" property="date" styleClass="small date"/></td>
					</tr>
				</c:if>
				<tr>
					<td class="label" width="25%" valign="top"><bean:message key='transfer.description'/></td>
					<td><html:textarea styleId="description" property="description" styleClass="full" rows="6"/></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="submit" class="button" value="<bean:message key='global.submit'/>"></td>
				</tr>
            </table>
        </td>
    </tr>
</table>
<c:if test="${asMember}">
	
	<table class="defaultTableContentHidden">
		<tr>
			<td>
				<input type="button" class="button" id="backButton" value="<bean:message key='global.back'/>">
			</td>
		</tr>
	</table>
</c:if>

</ssl:form>
