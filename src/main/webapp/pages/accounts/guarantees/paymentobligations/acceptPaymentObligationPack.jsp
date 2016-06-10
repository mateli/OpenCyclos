<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<cyclos:script src="/pages/accounts/guarantees/paymentobligations/acceptPaymentObligationPack.js" />

<c:set var="titleKey" value="paymentObligation.selectPaymentObligations"/>
<c:set var="helpPage" value="guarantees#accept_payment_obligation_pack"/>
<script>
	var arrPaymentObligations = ${paymentObligationIds};
</script>
<ssl:form action="${formAction}">
	<html:hidden property="selectIssuer" value="true"/>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0" style="float: none">
		<td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
		<cyclos:help page="${helpPage}" />
		<tr>
		    <td colspan="6" align="left" class="tdContentTableForms">
	    	  	<table class="defaultTable" name="potable">
					<tr>
						<td class="tdHeaderContents"><bean:message key="paymentObligation.buyer" /></td>
						<td class="tdHeaderContents"><bean:message key="paymentObligation.expire" /></td>
						<td class="tdHeaderContents"><bean:message key="paymentObligation.amount" /></td>
						<td class="tdHeaderContents">&nbsp;</td>
					</tr>
					<c:if test="${not empty paymentObligations}"></c:if>				
						<c:forEach var="po" items="${paymentObligations}">
							<tr id="po_${po.id}" class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
								<html:hidden property="paymentObligationIds" value="${po.id}"/>
								<td><cyclos:escapeHTML value="${po.buyer.name}"/></td>
								<td><cyclos:format rawDate="${po.expirationDate}"/></td>
								<td><cyclos:format number="${po.amount}" unitsPattern="${po.currency.pattern}"/></td>
								<td align="center">
									<img class="removePaymentObligation" src="<c:url value="/pages/images/delete.gif" />" poid="${po.id}" />
								</td>
							</tr>
						</c:forEach>
				</table>
			</td>
		</tr>
	</table>
	<br/>
	<table>
		<tr>
			<td align="left">
				<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
			</td>		
		</tr>
	</table>	
	<c:if test="${not empty issuers}">
		<br/>
		<fieldset>
			<legend><bean:message key="paymentObligation.pack.details.title"/></legend>	
			<table width="100%">			
				<tr>		
					<td>
						<span class="label"><bean:message key="paymentObligation.pack.totalAmount"/></span>						
					</td>
					<td><span><cyclos:format number="${paymentObligationsTotalAmount}" unitsPattern="${certification.guaranteeType.currency.pattern}" /></span></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3"></td>
				</tr>
				<tr>
					<td align="left">
						<span class="label"><bean:message key="paymentObligation.selectIssuer"/></span>
					</td>
					<td>
						<html:select property="issuerId" styleId="issuerId" styleClass="medium">
							<c:forEach var="issuer_item" items="${issuers}">
								<html:option value="${issuer_item.id}">${issuer_item.name}</html:option>
							</c:forEach>			
						</html:select>
					</td>
					<td>&nbsp;</td>				
				</tr>
				<tr>
					<td><span class="label"><bean:message key="paymentObligation.pack.paymentObligationPeriod"/></span></td>
					<td><span>${certification.guaranteeType.paymentObligationPeriod.number}	<bean:message key="global.timePeriod.${certification.guaranteeType.paymentObligationPeriod.field}"/></span></td>
					<td>&nbsp;</td>	
				</tr>
				<c:if test="${paymentObligationExceeded}">
					<tr>
						<td colspan="3">
							<bean:message key="paymentObligation.pack.details" />
						</td>
					</tr>	
				</c:if>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="right">
						<input id="acceptPaymentObligation" type="submit" class="button" value="<bean:message key="global.submit"/>">
					</td>
				</tr>																
			</table>	
		</fieldset>
	</c:if>
</ssl:form>