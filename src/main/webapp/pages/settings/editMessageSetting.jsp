<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/settings/editMessageSetting.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="setting" />
<html:hidden property="hasGeneral" />
<html:hidden property="hasSubject" />
<html:hidden property="hasBody" />
<html:hidden property="hasSms" />


<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="settings.message.title.edit"/>
        </td>
        <cyclos:help page="translation#edit_notifications"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="25%"><bean:message key="settings.message.setting"/></td>
                    <td><bean:message key="settings.message.${setting}"/></td>
                </tr>
                <c:if test="${editMessageSettingForm.hasGeneral}">
                	<tr>
                		<td class="label"><bean:message key="settings.message.value"/></td>
                		<td>
                			<c:choose><c:when test="${generalIsHtml}">
	                			<cyclos:richTextArea name="value" disabled="true" styleClass="full" format="${format}" value="${editMessageSettingForm.value}" />
                			</c:when><c:otherwise>
                				<html:textarea disabled="true" property="value" styleClass="InputBoxDisabled full" rows="6" />
                			</c:otherwise></c:choose>
                		</td>
                	</tr>
                </c:if>
                <c:if test="${editMessageSettingForm.hasSubject}">
                	<tr>
                		<td class="label"><bean:message key="settings.message.subject"/></td>
                		<td><html:text disabled="true" property="subject" styleClass="InputBoxDisabled full" /></td>
                	</tr>
                </c:if>
                <c:if test="${editMessageSettingForm.hasBody}">
                	<tr>
                		<td class="label"><bean:message key="settings.message.body"/></td>
                		<td><cyclos:richTextArea name="body" disabled="true" styleClass="full" format="${format}" value="${editMessageSettingForm.body}" /></td>
                	</tr>
                </c:if>
                <c:if test="${editMessageSettingForm.hasSms}">
                	<tr>
                		<td class="label"><bean:message key="settings.message.sms"/></td>
                		<td><html:textarea disabled="true" property="sms" rows="3" styleClass="InputBoxDisabled full" /></td>
                	</tr>
                </c:if>
           </table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
        <c:if test="${cyclos:granted(AdminSystemPermission.TRANSLATION_MANAGE_NOTIFICATION)}">
			<td align="right">
				<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
				<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
			</td>
		</c:if>
	</tr>
</table>

</ssl:form>
