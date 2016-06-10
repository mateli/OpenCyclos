<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<%-- The admin can manage password if any of the below permissions are granted --%>
<c:set var="canManagePasswords" value="${cyclos:granted(AdminAdminPermission.ACCESS_CHANGE_PASSWORD)}"/>
<c:set var="managePasswordsKey" value="profile.action.manageLoginPassword"/>
<c:if test="${admin.group.basicSettings.transactionPassword.used && cyclos:granted(AdminAdminPermission.ACCESS_TRANSACTION_PASSWORD)}">
	<c:set var="canManagePasswords" value="${true}"/>
	<c:set var="managePasswordsKey" value="profile.action.managePasswords"/>
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="profile.action.title" arg0="${admin.name}"/></td>
        <cyclos:help page="profiles#actions_for_admin" />
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
			<cyclos:layout className="defaultTable" columns="4">
				<c:if test="${!removed}"> 
                	<cyclos:cell width="35%" className="label"><bean:message key="profile.action.mail"/></cyclos:cell>
                	<cyclos:cell align="left"><input type="button" class="linkButton" linkURL="mailto:${admin.email}" value="<bean:message key="global.submit"/>"></cyclos:cell>
                </c:if>
                <c:forEach var="entry" items="${countByRecordType}">
					<c:set var="recordType" value="${entry.key}" />
					<c:set var="recordCount" value="${entry.value}" />
                    <cyclos:cell width="35%" className="label">${recordType.label} (${recordCount})</cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="${cyclos:name(recordType.layout) == 'FLAT' ? 'flatMemberRecords' : 'searchMemberRecords'}?global=false&elementId=${admin.id}&typeId=${recordType.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
	            </c:forEach>
               	<c:if test="${cyclos:granted(AdminAdminPermission.ADMINS_CHANGE_GROUP)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.changeGroup"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="changeAdminGroup?adminId=${admin.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
                </c:if>
		   	    <c:if test="${disabledLogin && cyclos:granted(AdminAdminPermission.ACCESS_ENABLE_LOGIN)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.allowLogin"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="allowUserLogin?userId=${admin.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
		        </c:if>
	    	    <c:if test="${isLoggedIn && cyclos:granted(AdminAdminPermission.ACCESS_DISCONNECT)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.disconnect"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="disconnectUser?userId=${admin.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
		        </c:if>
	    	    <c:if test="${!removed && canManagePasswords}">
                    <cyclos:cell width="35%" className="label"><bean:message key="${managePasswordsKey}"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="managePasswords?userId=${admin.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
            </cyclos:layout>
       	</td>
   	</tr>
</table>
