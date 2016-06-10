<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<cyclos:script src="/pages/accounts/currencies/editCurrency.js" />

<ssl:form method="post" action="${actionPrefix}/editCurrency">
<html:hidden property="currencyId"/>
<html:hidden property="currency(id)"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="currency.title.${isInsert ? 'insert' : 'modify'}"/></td>
        <cyclos:help page="account_management#currency_details"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
            		<td class="label" width="25%"><bean:message key="currency.name"/></td>
            		<td><html:text property="currency(name)"  maxlength="100" readonly="true" styleClass="large InputBoxDisabled required"/></td>
            	</tr>
				<tr>
            		<td class="label"><bean:message key="currency.symbol"/></td>
            		<td><html:text property="currency(symbol)" maxlength="20" readonly="true" styleClass="large InputBoxDisabled required"/></td>
            	</tr>
				<tr>
            		<td class="label"><bean:message key="currency.pattern"/></td>
            		<td><html:text property="currency(pattern)" maxlength="30" readonly="true" styleClass="large InputBoxDisabled"/></td>
            	</tr>
            	<tr>
            		<td class="label" valign="top"><bean:message key="currency.description"/></td>
            		<td><html:textarea styleId="descriptionText" property="currency(description)" rows="6" readonly="true" styleClass="large InputBoxDisabled"/></td>
            	</tr>

            	<c:if test="${editable}">
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
	            	<tr>
						<td align="right" colspan="2">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">
							&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
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
	<tr>
		<c:if test="${not empty pendingRateInitProgression}">
			<td>
				<br> 
				<div class="fieldDecoration" style="text-align:center">
					<bean:message key="currency.pendingRateInitProgression" />&nbsp;
					<cyclos:format date="${pendingRateInitProgression}"/>
				</div>
			</td>
		</c:if>
	</tr>
</table>
