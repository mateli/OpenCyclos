<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/settings/editMailTranslation.js" />

<ssl:form method="post" action="${formAction}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="settings.mailTranslation.title"/>
        </td>
        <cyclos:help page="translation#mail_translation"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <fieldset>
            	<legend><bean:message key="settings.mail.invitation"/></legend>
            	<table class="nested" width="100%">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.mail.subject"/></td>
	                    <td><html:text property="setting(invitationSubject)" styleClass="full InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td valign="top" class="label"><bean:message key="settings.mail.message"/></td>
	                    <td><html:textarea property="setting(invitationMessage)" rows="5" styleClass="full InputBoxDisabled" readonly="true"/></td>
	                </tr>
	            </table>
	        </fieldset>
	        
            <fieldset>
            	<legend><bean:message key="settings.mail.activation"/></legend>
            	<table class="nested" width="100%">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.mail.subject"/></td>
	                    <td><html:text property="setting(activationSubject)" styleClass="full InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td valign="top" class="label"><bean:message key="settings.mail.activationMessageWithPassword"/></td>
	                    <td><html:textarea property="setting(activationMessageWithPassword)" rows="5" styleClass="full InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td valign="top" class="label"><bean:message key="settings.mail.activationMessageWithoutPassword"/></td>
	                    <td><html:textarea property="setting(activationMessageWithoutPassword)" rows="5" styleClass="full InputBoxDisabled" readonly="true"/></td>
	                </tr>
	            </table>
	        </fieldset>
	        
            <fieldset>
            	<legend><bean:message key="settings.mail.mailValidation"/></legend>
            	<table class="nested" width="100%">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.mail.subject"/></td>
	                    <td><html:text property="setting(mailValidationSubject)" styleClass="full InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td valign="top" class="label"><bean:message key="settings.mail.message"/></td>
	                    <td><html:textarea property="setting(mailValidationMessage)" rows="5" styleClass="full InputBoxDisabled" readonly="true"/></td>
	                </tr>
	            </table>
	        </fieldset>
	        
            <fieldset>
            	<legend><bean:message key="settings.mail.resetPassword"/></legend>
            	<table class="nested" width="100%">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.mail.subject"/></td>
	                    <td><html:text property="setting(resetPasswordSubject)" styleClass="full InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td valign="top" class="label"><bean:message key="settings.mail.message"/></td>
	                    <td><html:textarea property="setting(resetPasswordMessage)" rows="5" styleClass="full InputBoxDisabled" readonly="true"/></td>
	                </tr>
	            </table>
	        </fieldset>
	        
	        <c:if test="${cyclos:granted(AdminSystemPermission.TRANSLATION_MANAGE_MAIL_TRANSLATION)}">
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
