<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:set var="managePasswordsKey" value="profile.action.manageLoginPassword"/>
<c:if test="${operator.group.basicSettings.transactionPassword.used}">
	<c:set var="managePasswordsKey" value="profile.action.managePasswords"/>
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="profile.action.title" arg0="${operator.name}"/></td>
        <cyclos:help page="operators#actions_for_operator_by_member" />
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
			<cyclos:layout className="defaultTable" columns="4">
               	<%-- Send e-mail --%>
                <cyclos:cell width="35%" className="label"><bean:message key="profile.action.mail"/></cyclos:cell>
                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="mailto:${operator.email}" value="<bean:message key="global.submit"/>"></cyclos:cell>
	    	    <%-- Disconnect --%>
	    	    <c:if test="${isLoggedIn}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.disconnect"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="disconnectUser?userId=${operator.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
		        </c:if>
				<c:if test="${not removed}">
					<%-- Change operator group --%>
	                <cyclos:cell width="35%" className="label"><bean:message key="profile.action.changeGroup"/></cyclos:cell>
	                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="changeOperatorGroup?operatorId=${operator.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
		    	    <%-- Manage passwords --%>
	                <cyclos:cell width="35%" className="label"><bean:message key="${managePasswordsKey}"/></cyclos:cell>
	                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="managePasswords?userId=${operator.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
					<%-- Enable login --%>
			   	    <c:if test="${disabledLogin}">
	                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.allowLogin"/></cyclos:cell>
	                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="allowUserLogin?userId=${operator.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
			        </c:if>
			    </c:if>
            </cyclos:layout>
       	</td>
   	</tr>
</table>