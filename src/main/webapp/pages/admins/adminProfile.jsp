<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/admins/adminProfile.js" />
<script>
	var myProfile = ${empty myProfile ? false : myProfile};
</script>

<ssl:form action="${formAction}" method="post">
<html:hidden property="admin(id)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
	        <c:choose><c:when test="${myProfile}">
	        	<bean:message key="profile.admin.title.my"/>
	        </c:when><c:otherwise>
		        <bean:message key="profile.admin.title.of" arg0="${admin.name}"/>
	        </c:otherwise></c:choose>
        </td>
        <cyclos:help page="profiles#admin_profile"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">

<table class="defaultTable">
	<c:if test="${isAdmin}">
		<tr>
			<td width="25%" class="label"><bean:message key="admin.group"/></td>
			<td colspan="2" nowrap="nowrap"><html:text property="admin(group).name" readonly="true" styleClass="large InputBoxDisabled"/></td>
        </tr>
        <c:if test="${!myProfile}">
	        <tr>
				<td class="label"><bean:message key="admin.lastLogin"/></td>
				<td colspan="2" nowrap="nowrap">
	    	        <c:set var="adminLoggedOn" value="${false}"/>
					<c:choose>
						<c:when test="${isLoggedIn}">
		    	       		<c:set var="lastLogin"><bean:message key="profile.userOnline"/></c:set>
		    	       		<c:set var="adminLoggedOn" value="${true}"/>
				       	</c:when>
				       	<c:when test="${empty admin.user.lastLogin}">
		    	       		<c:set var="lastLogin"><bean:message key="profile.neverLoggedOn"/></c:set>
				       	</c:when>
				       	<c:otherwise>
		    	       		<c:set var="lastLogin"><cyclos:format dateTime="${admin.user.lastLogin}"/></c:set>
			        	</c:otherwise>
			        </c:choose>
					<html:text property="admin(user).lastLogin" readonly="true" styleClass="medium InputBoxDisabled${adminLoggedOn ? ' fieldDecoration' : ''}" value="${lastLogin}"/>
				</td>			
			</tr>
		</c:if>
	</c:if>
	<tr>
		<td class="label" width="25%"><bean:message key="admin.username"/></td>
		<td colspan="2" nowrap="nowrap"><html:text property="admin(user).username" maxlength="20" readonly="true" styleClass="medium InputBoxDisabled required"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="admin.name"/></td>
		<td colspan="2" nowrap="nowrap"><html:text property="admin(name)" readonly="true" styleClass="large InputBoxDisabled required"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="admin.email"/></td>
		<td colspan="2" nowrap="nowrap"><html:text property="admin(email)" readonly="true" styleClass="large InputBoxDisabled required"/></td>
	</tr>
    <c:forEach var="entry" items="${customFields}">
        <c:set var="field" value="${entry.field}"/>
        <c:set var="value" value="${entry.value}"/>
        <c:if test="${editable}">
            <tr>
                <td class="label">${field.name}</td>
   				<td nowrap="nowrap">
   					<cyclos:customField field="${field}" value="${value}" editable="${editable}" valueName="admin(customValues).value" fieldName="admin(customValues).field" enabled="false"/>
   				</td>
			</tr>
		</c:if>
    </c:forEach>
	<c:if test="${editable}">
		<tr>
			<td colspan="3" align="right">
				
				<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">
				<input type="submit" id="saveButton" class="ButtonDisabled" disabled value="<bean:message key="global.submit"/>">
			</td>
		</tr>
	</c:if>
</table>

    	</td>
    </tr>
</table>
</ssl:form>


<c:if test="${!myProfile}">
	<jsp:include page="/pages/admins/includes/profileOfAdminByAdmin.jsp"/>
	
	<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
</c:if>
