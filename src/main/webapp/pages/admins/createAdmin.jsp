<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/admins/createAdmin.js" />
<ssl:form action="${formAction}" method="post">
<html:hidden property="admin(group)" value="${group.id}"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="createAdmin.title"/></td>
        <cyclos:help page="user_management#create_admin"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">

<table class="defaultTable">
	<tr>
		<td class="label" width="30%"><bean:message key="admin.group"/></td>
		<td><input type="text" class="large InputBoxDisabled" readonly="readonly" value="${group.name}"/></td>
    </tr>
	<tr>
		<td class="label"><bean:message key="admin.username"/></td>
		<td><html:text property="admin(user).username" size="20" maxlength="20" styleClass="large InputBoxEnabled required"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="admin.name"/></td>
		<td><html:text property="admin(name)" size="40" styleClass="large InputBoxEnabled required"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="admin.email"/></td>
		<td><html:text property="admin(email)" size="20" styleClass="large InputBoxEnabled required"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="createAdmin.password"/></td>
		<td><input type="password" name="admin(user).password" size="20" class="small InputBoxEnabled required"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="createAdmin.passwordConfirmation"/></td>
		<td><input type="password" name="confirmPassword" size="20" class="small InputBoxEnabled required"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="createMember.forceChangePassword"/></td>
		<td><input type="checkbox" name="forceChangePassword" class="checkbox" value="true"></td>
	</tr>
    <c:forEach var="field" items="${customFields}">
		<tr>
			<td class="label">${field.name}</td>
			<td>
				<cyclos:customField field="${field}" valueName="admin(customValues).value" fieldName="admin(customValues).field"/>
			</td>
		</tr>
    </c:forEach>
	<tr>
		<td colspan="2" align="right">
			<span class="label"><bean:message key="createAdmin.action.saveAndNew"/></span>
			<input type="submit" id="saveAndNewButton" class="button" value="<bean:message key="global.submit"/>">
		</td>
	</tr>
	<tr>
		<td colspan="2" align="right">
			<span class="label"><bean:message key="createAdmin.action.saveAndOpenProfile"/></span>
			<input type="submit" id="saveAndOpenProfileButton" class="button" value="<bean:message key="global.submit"/>">
		</td>
	</tr>
</table>
    	</td>
    </tr>
</table>

<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">

</ssl:form>