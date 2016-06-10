<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<cyclos:script src="/pages/access/changePassword.js" />

<c:set var="numericPassword" value="${ofAdmin ? false : accessSettings.numericPassword}"/>

<ssl:form styleId="changePasswordForm" method="post" action="${formAction}">
<html:hidden property="userId"/>
<html:hidden property="embed"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<c:choose><c:when test="${myPassword}">
        		<bean:message key="changePassword.title.my"/>
        	</c:when><c:otherwise>
        		<bean:message key="changePassword.title.of" arg0="${user.element.name}"/>
        	</c:otherwise></c:choose>
        </td>
        <cyclos:help page="passwords#change"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
					<td colspan="2" align="center">
						<br/>
						<c:choose><c:when test="${passwordLength.min == passwordLength.max}">
							<bean:message key="changePassword.passwordLength" arg0="${passwordLength.min}"/>
						</c:when><c:otherwise>
							<bean:message key="changePassword.passwordLengthRange" arg0="${passwordLength.min}" arg1="${passwordLength.max}"/>
						</c:otherwise></c:choose>
						<br/><br/>
					</td>
				</tr>
				<c:if test="${shouldRequestOldPassword}">
	                <tr>
	                     <td class="label"><bean:message key="changePassword.oldPassword"/></td>
	                     <td><input type="password" name="oldPassword" class="medium ${numericPassword ? 'number' : ''}"></td>
	                </tr>
                </c:if>
                <tr>
                     <td class="label"><bean:message key="changePassword.newPassword"/></td>
                     <td><input type="password" name="newPassword" class="medium ${numericPassword ? 'number' : ''}"></td>
                </tr>
                <tr>
                     <td class="label"><bean:message key="changePassword.newPasswordConfirmation"/></td>
                     <td><input type="password" name="newPasswordConfirmation" class="medium ${numericPassword ? 'number' : ''}"></td>
                </tr>
                <c:if test="${!myPassword}">
	                <tr>
	                     <td class="label"><bean:message key="changePassword.forceChange"/></td>
	                     <td><input type="checkbox" class="checkbox" name="forceChange" value="true"></td>
	                </tr>
                </c:if>
                <tr>
                     <td colspan="2" align="right"><input type="submit" class="button" value="<bean:message key="global.submit"/>"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</ssl:form>
<c:if test="${expired}">
	
	<div class="footerNote">
		<cyclos:escapeHTML>
			<bean:message key="changePassword.expired" />
		</cyclos:escapeHTML>
	</div>
</c:if>
