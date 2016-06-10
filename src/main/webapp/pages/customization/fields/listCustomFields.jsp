<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/customization/fields/listCustomFields.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="customField.removeConfirmation"/></cyclos:escapeJS>";
	var nature = "${nature}";
</script>

<c:choose>
	<c:when test="${nature == 'MEMBER'}">
		<c:set var="titleKey" value="customField.title.list.member"/>
		<c:set var="helpPage" value="custom_fields#list_custom_fields"/>
	</c:when>
	<c:when test="${nature == 'ADMIN'}">
		<c:set var="titleKey" value="customField.title.list.admin"/>
		<c:set var="helpPage" value="custom_fields#list_custom_fields"/>
	</c:when>
	<c:when test="${nature == 'OPERATOR'}">
		<c:set var="titleKey" value="customField.title.list.operator"/>
		<c:set var="helpPage" value="custom_fields#list_custom_fields"/>
	</c:when>
	<c:when test="${nature == 'AD'}">
		<c:set var="titleKey" value="customField.title.list.ad"/>
		<c:set var="helpPage" value="custom_fields#list_custom_fields"/>
	</c:when>
	<c:when test="${nature == 'LOAN'}">
		<c:set var="titleKey" value="customField.title.list.loan"/>
		<c:set var="helpPage" value="custom_fields#list_custom_fields"/>
	</c:when>
	<c:when test="${nature == 'LOAN_GROUP'}">
		<c:set var="titleKey" value="customField.title.list.loanGroup"/>
		<c:set var="helpPage" value="custom_fields#list_custom_fields"/>
	</c:when>
</c:choose>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
        <cyclos:help page="${helpPage}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<td class="tdHeaderContents"><bean:message key="customField.name"/></td>
                    <td class="tdHeaderContents" width="10%">&nbsp;</td>
                </tr>
                <c:forEach var="customField" items="${customFields}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                	<td><cyclos:escapeHTML>${customField.name}</cyclos:escapeHTML></td>
	                    <td align="center" nowrap="nowrap">
	                    	<c:choose><c:when test="${nature == 'OPERATOR' || cyclos:granted(AdminSystemPermission.CUSTOM_FIELDS_MANAGE)}">
			                    <img class="edit fieldDetails" src="<c:url value="/pages/images/edit.gif"/>" fieldId="${customField.id}" border="0">
		                    	<img class="remove" src="<c:url value="/pages/images/delete.gif"/>" fieldId="${customField.id}" border="0">
			                </c:when><c:otherwise>
				                <img class="view fieldDetails" src="<c:url value="/pages/images/view.gif"/>" fieldId="${customField.id}" border="0">
							</c:otherwise></c:choose>
	                    </td>
	                </tr>
                </c:forEach>
			</table>
		</td>
	</tr>
</table>

<c:if test="${nature == 'OPERATOR' || cyclos:granted(AdminSystemPermission.CUSTOM_FIELDS_MANAGE)}">
	<table class="defaultTableContentHidden">
		<tr>
			<td align="right" width="50%">
				<c:if test="${fn:length(customFields) > 1}">
					<span class="label"><bean:message key="customField.action.changeOrder"/></span>
					<input type="button" class="button" id="changeOrderButton" value="<bean:message key="global.submit"/>">
				</c:if>
			</td>
			<td align="right" width="50%">
				<span class="label"><bean:message key="customField.action.new"/></span>
				<input type="button" class="button" id="newButton" value="<bean:message key="global.submit"/>">
			</td>
		</tr>
	</table>
</c:if>
		
