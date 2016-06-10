<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/settings/editAlertSettings.js" />

<ssl:form method="post" action="${formAction}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="settings.alert.title"/>
        </td>
        <cyclos:help page="settings#alerts"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="30%"><bean:message key="settings.alert.givenVeryBadRefs"/></td>
                    <td>
                    	<html:text property="setting(givenVeryBadRefs)" styleClass="tiny number InputBoxDisabled" readonly="true"/>
                    	(<span class="fieldDecoration"><bean:message key="settings.neverAlertMessage"/></span>)
                   	</td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.alert.receivedVeryBadRefs"/></td>
                    <td>
                    	<html:text property="setting(receivedVeryBadRefs)" styleClass="tiny number InputBoxDisabled" readonly="true"/>
                    	(<span class="fieldDecoration"><bean:message key="settings.neverAlertMessage"/></span>)
                   	</td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.alert.idleInvoiceExpiration"/></td>
                    <td>
                    	<html:text property="setting(idleInvoiceExpiration).number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
                    	<html:select property="setting(idleInvoiceExpiration).field" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="field" items="${timePeriodFields}">
	                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
                    	(<span class="fieldDecoration"><bean:message key="settings.neverExpiresMessage"/></span>)
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.alert.amountDeniedInvoices"/></td>
                    <td>
                    	<html:text property="setting(amountDeniedInvoices)" styleClass="tiny number InputBoxDisabled" readonly="true"/>
                    	(<span class="fieldDecoration"><bean:message key="settings.neverAlertMessage"/></span>)
                   	</td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.alert.amountIncorrectLogin"/></td>
                    <td>
                    	<html:text property="setting(amountIncorrectLogin)" styleClass="tiny number InputBoxDisabled" readonly="true"/>
                    	(<span class="fieldDecoration"><bean:message key="settings.neverAlertMessage"/></span>)
                   	</td>
                </tr>
                <c:if test="${cyclos:granted(AdminSystemPermission.SETTINGS_MANAGE_ALERT)}">
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
