<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/operators/createOperator.js" />
<script>
	<c:set var="passwordLabel"><bean:message key="login.password"/></c:set>
	<c:set var="passwordLength" value="${group.basicSettings.passwordLength}"/>
	var numericPassword = ${accessSettings.numericPassword};
	var mustBeNumericError = "<cyclos:escapeJS><bean:message key="changePassword.error.mustBeNumeric"/></cyclos:escapeJS>";
	var mustBeAlphaNumericError = "<cyclos:escapeJS><bean:message key="changePassword.error.mustBeAlphaNumeric"/></cyclos:escapeJS>";
	var passwordRange = {min:${passwordLength.min}, max:${passwordLength.max}};
	var policy = '${group.basicSettings.passwordPolicy}';
	var passwordTooSmallError = "<cyclos:escapeJS><bean:message key="errors.minLength" arg0="${passwordLabel}" arg1="${passwordLength.min}"/></cyclos:escapeJS>"
	var passwordTooLargeError = "<cyclos:escapeJS><bean:message key="errors.maxLength" arg0="${passwordLabel}" arg1="${passwordLength.max}"/></cyclos:escapeJS>"
	var passwordTooSimpleError = "<cyclos:escapeJS><bean:message key="changePassword.error.obvious"/></cyclos:escapeJS>"
	var passwordMustIncludeLettersNumbersError = "<cyclos:escapeJS><bean:message key="changePassword.error.mustIncludeLettersNumbers"/></cyclos:escapeJS>"
</script>
<ssl:form action="${formAction}" method="post">
<html:hidden property="operator(user).password"/>
<html:hidden property="operator(group)" value="${group.id}"/>
<html:hidden property="confirmPassword"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="createOperator.title"/></td>
        <cyclos:help page="operators#create_operator"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">

<table class="defaultTable">
	<tr>
		<td class="label" width="25%"><bean:message key="operator.group"/></td>
		<td><input type="text" class="large InputBoxDisabled" readonly="readonly" value="${group.name}"/></td>
    </tr>
	<tr>
		<td class="label"><bean:message key="operator.username"/></td>
		<td><html:text property="operator(user).username" size="20" maxlength="${accessSettings.usernameLength.max}" styleClass="large InputBoxEnabled required"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="operator.name"/></td>
		<td><html:text property="operator(name)" size="40" styleClass="large InputBoxEnabled required"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="operator.email"/></td>
		<td><html:text property="operator(email)" size="20" styleClass="large InputBoxEnabled"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="createOperator.password"/></td>
		<td><input type="password" id="_password" size="20" class="small InputBoxEnabled required"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="createOperator.passwordConfirmation"/></td>
		<td><input type="password" id="_confirmPassword" size="20" class="small InputBoxEnabled required"/></td>
	</tr>
    <c:forEach var="field" items="${customFields}">
		<tr>
			<td valign="top" class="label">${field.name}</td>
			<td>
				<cyclos:customField field="${field}" valueName="operator(customValues).value" fieldName="operator(customValues).field"/>
			</td>
		</tr>
    </c:forEach>
	<tr>
		<td colspan="2" align="right">
			<span class="label"><bean:message key="createOperator.action.saveAndNew"/></span>
			<input type="submit" id="saveAndNewButton" class="button" value="<bean:message key="global.submit"/>">
		</td>
	</tr>
	<tr>
		<td colspan="2" align="right">
			<span class="label"><bean:message key="createOperator.action.saveAndOpenProfile"/></span>
			<input type="submit" id="saveAndOpenProfileButton" class="button" value="<bean:message key="global.submit"/>">
		</td>
	</tr>
</table>
    	</td>
    </tr>
</table>

<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">

</ssl:form>