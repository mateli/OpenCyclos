<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<form id="cyclosLogin">
<table class="defaultTable">
	<c:if test="${isOperator}">
		<tr>
			<td nowrap="nowrap" class="label" width="40%"><bean:message key="member.username"/></td>
			<td><input id="cyclosMember" name="member" class="medium"/></td>
		</tr>
	</c:if>
	<tr>
		<td nowrap="nowrap" class="label" width="40%"><bean:message key="${isOperator ? 'operator.username' : 'member.username'}"/></td>
		<td><input id="cyclosUsername" name="principal" class="medium"/></td>
	</tr>
	<tr>
		<td nowrap="nowrap" class="label"><bean:message key="login.password"/></td>
		<td><input type="password" id="cyclosPassword" class="medium"/></td>
	</tr>
	<tr>
		<td colspan="2" align="right">
			<br class="small">
			<input type="submit" class="button" value="<bean:message key="global.submit"/>">
		</td>
	</tr>
</table>
</form>