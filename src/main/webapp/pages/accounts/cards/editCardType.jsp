<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/cards/editCardType.js" />
<script>
var hasCardGenerated = ${hasCardGenerated}
</script>

<ssl:form method="post" action="${formAction}">

<html:hidden property="cardType(id)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="cardType.title.${isInsert ? 'insert' : 'modify'}"/></td>
        <cyclos:help page="access_devices#edit_card_type"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
   				<tr>
            		<td class="label" width="35%"><bean:message key="cardType.name"/></td>
            		<td><html:text property="cardType(name)" maxlength="100" readonly="true" styleClass="large InputBoxDisabled"/></td>
            	</tr>
				<tr>
            		<td class="label"><bean:message key="cardType.cardFormatNumber"/></td>
            		<td><html:text property="cardType(cardFormatNumber)" maxlength="20" readonly="true" styleClass="large InputBoxDisabled"/></td>
            	</tr>
				<tr>
            		<td class="label"><bean:message key="cardType.defaultExpiration"/></td>
            		<td>
	                    	<html:text styleId="recurrenceNumberText" property="cardType(defaultExpiration).number" maxlength="2" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<html:select styleId="recurrenceFieldSelect" property="cardType(defaultExpiration).field" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="field" items="${defaultExpirations}">
		                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    
	                    <span class="fieldDecoration"><bean:message key="settings.neverExpiresMessage"/></span>
					</td>            		
            	</tr>
            	<tr>
            		<td class="label" valign="top"><bean:message key="cardType.ignoreDayInExpirationDate"/></td>
            		<td><html:checkbox property="cardType(ignoreDayInExpirationDate)" disabled="true" styleClass="checkbox" value="true"/></td>
            	</tr>            	
            	<tr>
            		<td class="label"><bean:message key="cardType.cardSecurityCode"/></td>
            		<td>
            			<c:forEach var="cardSecurityCode" items="${cardSecurityCodes}">
                    		<html:radio property="cardType(cardSecurityCode)" value="${cardSecurityCode}" styleClass="radio cardSecurityCodeSelect" disabled="true"/>
                    		<bean:message key="cardType.cardSecurityCode.${cardSecurityCode}"/>
                   		</c:forEach>            			
            		</td>
            	</tr>
            	<!-- Show security code is only used if CardSecurityCode is MANUAL -->
            	<tr class="trManualSecCode" style="display:none">
            		<td class="label"><bean:message key="cardType.showCardSecurityCode"/></td>
            		<td><html:checkbox property="cardType(showCardSecurityCode)" styleId="showCardSecurityCode" styleClass="checkbox" value="true" disabled="true"/></td>
            	</tr>
            	<!-- MaxSecurityCodeTries and SecurityCodeBlockTime depends of CardSecurityCode => only used if CardSecurityCode is different from NOT_USED-->
            	<tr class="trSecCode" style="display:none">
            		<td class="label"><bean:message key="cardType.maxSecurityCodeTries"/></td>
            		<td><html:text property="cardType(maxSecurityCodeTries)" maxlength="2" readonly="true" styleClass="large InputBoxDisabled"/></td>
            	</tr>
            	<tr class="trSecCode" style="display:none">
            		<td class="label"><bean:message key="cardType.securityCodeBlockTime"/></td>
            		<td>
	            		<html:text styleId="recurrenceNumberText" property="cardType(securityCodeBlockTime).number" maxlength="2" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                   	<html:select styleId="recurrenceFieldSelect" property="cardType(securityCodeBlockTime).field" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="field" items="${securityCodeBlockTime}">
	                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
	                   		</c:forEach>
	                   	</html:select>
	                </td>
            	</tr>
            	<tr class="trSecCode" style="display:none">
            		<td class="label">
            			<bean:message key="cardType.cardSecurityCodeLength" />&nbsp;<bean:message key="global.min" />
            		</td>
            		<td>
            			<html:text property="cardType(cardSecurityCodeLength).min" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/>&nbsp;
            			<span class="inlineLabel"><bean:message key="global.max" /></span>
            			<html:text property="cardType(cardSecurityCodeLength).max" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/>
            		</td>
            	</tr>
            	<c:if test="${editable}">
				<tr>
					<td colspan="3" align="right">
						
						<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">&nbsp;
						<input type="submit" id="saveButton" class="ButtonDisabled" disabled value="<bean:message key="global.submit"/>">
					</td>
				</tr>
				</c:if>
            </table>
        </td>
    </tr>
</table>
</ssl:form>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
