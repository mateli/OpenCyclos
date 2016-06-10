<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/agreements/editRegistrationAgreement.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="registrationAgreement(id)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="registrationAgreement.title.${isInsert ? 'insert' : 'modify'}"/></td>
        <cyclos:help page="groups#registration_agreement"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
					<td class="label" width="25%"><bean:message key='registrationAgreement.name'/></td>
					<td><html:text property="registrationAgreement(name)" readonly="true" styleClass="full InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label" width="25%"><bean:message key='registrationAgreement.contents'/></td>
					<td><cyclos:richTextArea name="registrationAgreement(contents)" disabled="true" value="${registrationAgreement.contents}" /></td>
				</tr>
				<c:if test="${cyclos:granted(AdminSystemPermission.REGISTRATION_AGREEMENTS_MANAGE)}">
					<tr>
						<td align="right" colspan="2">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
				</c:if>
			</table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
</ssl:form>
