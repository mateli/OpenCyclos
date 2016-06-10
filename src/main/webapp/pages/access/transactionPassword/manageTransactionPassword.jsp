<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/access/transactionPassword/manageTransactionPassword.js" />
<script>
	var resetConfirmationMessage = "<cyclos:escapeJS><bean:message key="transactionPassword.reset.confirmMessage"/></cyclos:escapeJS>";
	var blockConfirmationMessage = "<cyclos:escapeJS><bean:message key="transactionPassword.block.confirmMessage"/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}">
<html:hidden property="block" />
<html:hidden property="userId" />
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="transactionPassword.title.manage" arg0="${user.username}"/></td>
        <cyclos:help page="passwords#manage_transaction_password"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
        	<c:set var="status" value="${user.transactionPasswordStatus}"/>
            <table class="defaultTable">
                <tr>
					<td class="label"><bean:message key="transactionPassword.status.user"/></td>
					<td width="40%"><bean:message key="transactionPassword.status.user.${status}"/></td>
			    </tr>
                <tr><td>&nbsp;</td></tr>
                <c:if test="${canReset}">
	                <tr>
						<td class="label"><bean:message key="transactionPassword.action.reset"/></td>
						<td><input type="submit" id="resetButton" class="button" value="<bean:message key="global.submit"/>">
				    </tr>
				</c:if>
                <c:if test="${canBlock}">
				    <tr>
						<td class="label"><bean:message key="transactionPassword.action.block"/></td>
						<td><input type="submit" id="blockButton" class="button" value="<bean:message key="global.submit"/>">
					</tr>
				</c:if>
            </table>
		</td>
    </tr>
</table>
</ssl:form>