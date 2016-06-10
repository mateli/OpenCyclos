<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/settings/editAccessSettings.js" />

<ssl:form method="post" action="${formAction}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="settings.access.title"/>
        </td>
        <cyclos:help page="settings#access"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="30%"><bean:message key="settings.access.virtualKeyboardLogin"/></td>
                    <td><html:checkbox property="setting(virtualKeyboard)" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
                </tr>
                <tr>
                    <td class="label" width="30%"><bean:message key="settings.access.virtualKeyboardTransactionPassword"/></td>
                    <td><html:checkbox property="setting(virtualKeyboardTransactionPassword)" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.access.numericPassword"/></td>
                    <td><html:checkbox property="setting(numericPassword)" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.access.allowMultipleLogins"/></td>
                    <td><html:checkbox property="setting(allowMultipleLogins)" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.access.allowOperatorLogin"/></td>
                    <td><html:checkbox property="setting(allowOperatorLogin)" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.access.adminTimeout"/></td>
                    <td>
                    	<html:text property="setting(adminTimeout).number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
                    	<html:select property="setting(adminTimeout).field" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="field" items="${timePeriodFields}">
	                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.access.memberTimeout"/></td>
                    <td>
                    	<html:text property="setting(memberTimeout).number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
                    	<html:select property="setting(memberTimeout).field" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="field" items="${timePeriodFields}">
	                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.access.poswebTimeout"/></td>
                    <td>
                    	<html:text property="setting(poswebTimeout).number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
                    	<html:select property="setting(poswebTimeout).field" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="field" items="${timePeriodFields}">
	                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
                    </td>
                </tr>
                <tr>
                	<td class="label" valign="top"><bean:message key="settings.access.administrationWhitelist"/></td>
                    <td><html:textarea property="setting(administrationWhitelist)" rows="5" styleClass="full InputBoxDisabled" readonly="true"/></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.access.usernameGeneration"/></td>
                    <td>
                    	<html:select property="setting(usernameGeneration)" styleId="generation" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="gen" items="${usernameGenerations}">
	                    		<html:option value="${gen}"><bean:message key="settings.access.usernameGeneration.${gen}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
                    </td>
                </tr>
            	<tr class="trManual" style="display:none">
            		<td class="label">
            			<bean:message key="settings.access.usernameLength" />
            			&nbsp;
            		</td>
            		<td>
            			<span class="label"><bean:message key="global.min" /></span>
            			<html:text property="setting(usernameLength).min" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/>
            			&nbsp;
            			<span class="label"><bean:message key="global.max" /></span>
            			<html:text property="setting(usernameLength).max" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/>
            		</td>
            	</tr>	
            	<tr class="trManual" style="display:none">
                    <td class="label"><bean:message key="settings.access.loginRegex"/></td>
                    <td><html:text property="setting(usernameRegex)" styleClass="medium InputBoxDisabled" readonly="true"/></td>
            	</tr>                
               	<tr class="trGenerate" style="display:none">
                    <td class="label"><bean:message key="settings.access.generatedUsernameLength"/></td>
                    <td><html:text property="setting(generatedUsernameLength)" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.access.transactionPasswordChars"/></td>
                    <td><html:text property="setting(transactionPasswordChars)" styleClass="medium InputBoxDisabled" readonly="true"/></td>
                </tr>
                <c:if test="${cyclos:granted(AdminSystemPermission.SETTINGS_MANAGE_ACCESS)}">
	                <tr>
						<td colspan="2" align="right">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">
							&nbsp;
							<input type="submit" id="saveButton" disabled="disabled" class="ButtonDisabled" value="<bean:message key="global.submit"/>">
						</td>
	                </tr>
	            </c:if>
            </table>
		</td>            
    </tr>
</table>
</ssl:form>
