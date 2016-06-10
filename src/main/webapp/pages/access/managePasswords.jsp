<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/access/managePasswords.js" />
<script>
	var ofAdmin = ${ofAdmin};
	var ofOperator = ${ofOperator};
</script>

<ssl:form action="${formAction}">
	<html:hidden property="userId" />
</ssl:form>

<c:if test="${canChangePassword}">
	<jsp:include flush="true" page="${pathPrefix}/changePassword">
		<jsp:param name="userId" value="${user.id}"/>
		<jsp:param name="embed" value="true"/>
	</jsp:include>
	<%-- Render a small row break if the user can reset the password --%>
	<c:if test="${canResetPassword}">
		
	</c:if>
</c:if>

<c:if test="${canResetPassword}">
	<script>
		var resetConfirmation = "<cyclos:escapeJS><bean:message key="changePassword.resetAndSend.confirmation" /></cyclos:escapeJS>";
	</script>
	<ssl:form styleId="resetAndSendPasswordForm" action="${actionPrefix}/resetAndSendPassword" method="post">
		<html:hidden property="userId" value="${user.id}" />
		<table class="defaultTableContentHidden">
			<tr>
				<td>
					<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
				</td>
				<td align="right">
					<span class="label"><bean:message key="changePassword.resetAndSend" /></span>
					<input type="submit" class="button" id="resetAndSendButton" value="<bean:message key="global.submit"/>"/>
				</td>
			</tr>
	</table>
	</ssl:form>
</c:if>

<c:if test="${canManageTransactionPassword}">
	<%-- Render a row break if there's some other content on the page --%>
	<c:if test="${canChangePassword || canResetPassword}">
		
	</c:if>
	<jsp:include flush="true" page="${pathPrefix}/manageTransactionPassword">
		<jsp:param name="userId" value="${user.id}"/>
		<jsp:param name="embed" value="true"/>
	</jsp:include>
</c:if>

<%-- Render a back button only if it was not previously rendered --%>
<c:if test="${!canResetPassword}">
	<table class="defaultTableContentHidden"><tr><td>
	<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
	</td></tr></table>
</c:if>