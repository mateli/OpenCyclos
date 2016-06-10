<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/settings/editMailSettings.js" />

<ssl:form method="post" action="${formAction}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="settings.mail.title"/>
        </td>
        <cyclos:help page="settings#mail"/>    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="30%"><bean:message key="settings.mail.fromMail"/></td>
                    <td><html:text property="setting(fromMail)" styleClass="large InputBoxDisabled" readonly="true"/></td>
                </tr>
            </table>
            
            <fieldset>
            	<legend><bean:message key="settings.mail.smtp"/></legend>
            	<table class="nested" width="100%">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.mail.smtpServer"/></td>
	                    <td><html:text property="setting(smtpServer)" styleClass="large InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.mail.smtpPort"/></td>
	                    <td><html:text property="setting(smtpPort)" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.mail.smtpUsername"/></td>
	                    <td><html:text property="setting(smtpUsername)" styleClass="medium InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.mail.smtpPassword"/></td>
	                    <td><html:password property="setting(smtpPassword)" styleClass="medium InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.mail.smtpUseTLS"/></td>
	                    <td><html:checkbox property="setting(smtpUseTLS)" styleClass="checkbox" value="true" disabled="true"/></td>
	                </tr>
	            </table>
	        </fieldset>
	        
	        <c:if test="${cyclos:granted(AdminSystemPermission.SETTINGS_MANAGE_MAIL)}">
	            <table class="defaultTable">
	                <tr>
						<td colspan="2" align="right">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">
							&nbsp;
							<input type="submit" id="saveButton" disabled="disabled" class="ButtonDisabled" value="<bean:message key="global.submit"/>">
						</td>
	                </tr>
				</table>
			</c:if>
		</td>            
    </tr>
</table>
</ssl:form>
