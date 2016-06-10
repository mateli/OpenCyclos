<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/settings/editLogSettings.js" />

<ssl:form method="post" action="${formAction}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="settings.log.title"/>
        </td>
        <cyclos:help page="settings#log"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="40%"><bean:message key="settings.log.traceLevel"/></td>
                    <td>
                    	<html:select property="setting(traceLevel)" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="level" items="${traceLevels}">
	                    		<html:option value="${level}"><bean:message key="settings.log.traceLevel.${level}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.traceFile"/></td>
                    <td><html:text property="setting(traceFile)" styleClass="large InputBoxDisabled" readonly="true"/></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.traceWritesOnly"/></td>
                    <td><html:checkbox property="setting(traceWritesOnly)" styleClass="checkbox" disabled="true" value="true"/></td>
                </tr>
                <tr>
                	<td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.webServiceLevel"/></td>
                    <td>
                    	<html:select property="setting(webServiceLevel)" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="level" items="${webServiceLevels}">
	                    		<html:option value="${level}"><bean:message key="settings.log.webServiceLevel.${level}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.webServiceFile"/></td>
                    <td><html:text property="setting(webServiceFile)" styleClass="large InputBoxDisabled" readonly="true"/></td>
                </tr>
                <tr>
                	<td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.restLevel"/></td>
                    <td>
                    	<html:select property="setting(restLevel)" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="level" items="${webServiceLevels}">
	                    		<html:option value="${level}"><bean:message key="settings.log.webServiceLevel.${level}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.restFile"/></td>
                    <td><html:text property="setting(restFile)" styleClass="large InputBoxDisabled" readonly="true"/></td>
                </tr>
                <tr>
                	<td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.transactionLevel"/></td>
                    <td>
                    	<html:select property="setting(transactionLevel)" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="level" items="${transactionLevels}">
	                    		<html:option value="${level}"><bean:message key="settings.log.transactionLevel.${level}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.transactionFile"/></td>
                    <td><html:text property="setting(transactionFile)" styleClass="large InputBoxDisabled" readonly="true"/></td>
                </tr>
                <tr>
                	<td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.accountFeeLevel"/></td>
                    <td>
                    	<html:select property="setting(accountFeeLevel)" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="level" items="${accountFeeLevels}">
	                    		<html:option value="${level}"><bean:message key="settings.log.accountFeeLevel.${level}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.accountFeeFile"/></td>
                    <td><html:text property="setting(accountFeeFile)" styleClass="large InputBoxDisabled" readonly="true"/></td>
                </tr>
                <tr>
                	<td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.scheduledTaskLevel"/></td>
                    <td>
                    	<html:select property="setting(scheduledTaskLevel)" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="level" items="${scheduledTaskLevels}">
	                    		<html:option value="${level}"><bean:message key="settings.log.scheduledTaskLevel.${level}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.scheduledTaskFile"/></td>
                    <td><html:text property="setting(scheduledTaskFile)" styleClass="large InputBoxDisabled" readonly="true"/></td>
                </tr>
                <tr>
                	<td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.maxFilesPerLog"/></td>
                    <td><html:text property="setting(maxFilesPerLog)" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="settings.log.maxLengthPerFile"/></td>
                    <td nowrap="nowrap">
                    	<html:text property="setting(maxLengthPerFile)" styleClass="tiny number InputBoxDisabled" readonly="true"/>
                    	<html:select property="setting(maxLengthPerFileUnits)" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="unit" items="${fileUnits}">
	                    		<html:option value="${unit}">${unit.display}</html:option>
	                   		</c:forEach>
                    	</html:select>
                    </td>
                </tr>
                <c:if test="${cyclos:granted(AdminSystemPermission.SETTINGS_MANAGE_LOG)}">
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
