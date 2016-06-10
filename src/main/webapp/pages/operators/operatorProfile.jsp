<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/operators/operatorProfile.js" />
<script>
	var myProfile = ${empty myProfile ? false : myProfile};
	var editable = [];
</script>

<ssl:form action="${formAction}" method="post">
<html:hidden property="operator(id)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
	        <c:choose><c:when test="${myProfile}">
	        	<bean:message key="profile.operator.title.my"/>
	        </c:when><c:otherwise>
		        <bean:message key="profile.operator.title.of" arg0="${operator.name}"/>
	        </c:otherwise></c:choose>
        </td>
        <cyclos:help page="operators#operator_profile"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">

<table class="defaultTable">
	<c:if test="${!myProfile}">
		<tr>
			<td width="25%" class="label"><bean:message key="operator.group"/></td>
			<td colspan="2" nowrap="nowrap"><html:text property="operator(group).name" readonly="true" styleClass="large InputBoxDisabled"/></td>
        </tr>
        <tr>
			<td class="label"><bean:message key="operator.lastLogin"/></td>
			<td colspan="2" nowrap="nowrap">
    	        <c:set var="operatorLoggedOn" value="${false}"/>
				<c:choose>
					<c:when test="${isLoggedIn}">
	    	       		<c:set var="lastLogin"><bean:message key="profile.userOnline"/></c:set>
	    	       		<c:set var="operatorLoggedOn" value="${true}"/>
			       	</c:when>
			       	<c:when test="${empty operator.user.lastLogin}">
	    	       		<c:set var="lastLogin"><bean:message key="profile.neverLoggedOn"/></c:set>
			       	</c:when>
			       	<c:otherwise>
	    	       		<c:set var="lastLogin"><cyclos:format dateTime="${operator.user.lastLogin}"/></c:set>
		        	</c:otherwise>
		        </c:choose>
				<html:text property="operator(user).lastLogin" readonly="true" styleClass="medium InputBoxDisabled${operatorLoggedOn ? ' fieldDecoration' : ''}" value="${lastLogin}"/>
			</td>			
		</tr>
	</c:if>
	<tr>
		<td class="label" width="25%"><bean:message key="operator.username"/></td>
		<td colspan="2" nowrap="nowrap"><html:text property="operator(user).username" maxlength="20" readonly="true" styleClass="medium InputBoxDisabled required"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="operator.name"/></td>
		<td colspan="2" nowrap="nowrap"><html:text property="operator(name)" readonly="true" styleClass="large InputBoxDisabled required"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="operator.email"/></td>
		<td colspan="2" nowrap="nowrap"><html:text property="operator(email)" readonly="true" styleClass="large InputBoxDisabled"/></td>
	</tr>
    <c:forEach var="entry" items="${customFields}" varStatus="loop">
        <c:set var="field" value="${entry.field}"/>
        <c:set var="value" value="${entry.value}"/>
        <tr>
			<td valign="top" class="label">${field.name}</td>
			<td colspan="2" nowrap="nowrap">
				<span class="customFieldContainer" editable="${editableFields[field]}">
					<cyclos:customField field="${field}" value="${value}" editable="${editableFields[field]}" valueName="operator(customValues).value" fieldName="operator(customValues).field" enabled="false"/>
				</span>
			</td>
		</tr>
    </c:forEach>
    
	<tr>
		<td colspan="3" align="right">
			
			<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">
			<input type="submit" id="saveButton" class="ButtonDisabled" disabled value="<bean:message key="global.submit"/>">
		</td>
	</tr>
</table>

    	</td>
    </tr>
</table>
</ssl:form>


<c:if test="${!myProfile}">
	<jsp:include page="/pages/operators/includes/profileOfOperatorByMember.jsp"/>
	
	<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
</c:if>
