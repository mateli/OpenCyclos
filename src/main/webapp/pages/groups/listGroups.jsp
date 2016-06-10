<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/groups/listGroups.js" />
<script>
	var removeConfirmation = "<cyclos:escapeJS><bean:message key="group.removeConfirmation"/></cyclos:escapeJS>";
</script>

<c:if test="${isAdmin}">
<ssl:form method="post" action="${formAction}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="group.title.search"/></td>
        <cyclos:help page="groups#search_groups"/>
    </tr>
    <tr>
		<td align="left" class="tdContentTableForms" colspan="2">
		  	<table class="defaultTable" border="0">
		  		<tr>
           			<td class="label">
           				<span class="inlineLabel"><bean:message key="group.nature"/></span>
           				<html:select property="query(nature)" styleId="naturesSelect">
           					<html:option value=""><bean:message key="global.search.all"/></html:option>
           					<c:forEach var="nature" items="${natures}">
           						<html:option value="${nature}"><bean:message key="group.nature.${nature}"/></html:option>
           					</c:forEach>
           				</html:select>
         			</td>
		  			<c:if test="${not empty groupFilters}" >
	           			<td class="label">
	           				<span class="inlineLabel"><bean:message key="member.groupFilter"/></span>
		           			<html:select property="query(groupFilter)" styleId="groupFiltersSelect">
		           				<html:option value=""><bean:message key="member.search.allGroupFilters"/></html:option>
		           				<c:forEach var="groupFilter" items="${groupFilters}">
		           					<html:option value="${groupFilter.id}">${groupFilter.name}</html:option>
		           				</c:forEach>
		           			</html:select>
	         			</td>
       				</c:if>
       			</tr>
			</table>
		</td>
	</tr>
</table>
</ssl:form>

</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="group.title.list"/></td>
        <cyclos:help page="${isMember ? 'operators#manage_operator_groups' : 'groups#manage_groups'}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
            	<tr>
            		<td class="tdHeaderContents"><bean:message key="group.name" /></td>
            		<c:if test="${isAdmin}">
	            		<td class="tdHeaderContents" width="20%"><bean:message key="group.nature" /></td>
            		</c:if>
            		<td class="tdHeaderContents" width="10%">&nbsp;</td>
            	</tr>
            	<c:forEach var="group" items="${groups}">
            		<c:if test="${(cyclos:name(group.nature)=='ADMIN' && cyclos:granted(AdminSystemPermission.ADMIN_GROUPS_VIEW)) ||
                                  ((cyclos:name(group.nature)=='BROKER' || cyclos:name(group.nature)=='MEMBER') && cyclos:granted(AdminMemberPermission.GROUPS_VIEW) && cyclos:contains(managesGroups, group)) ||
                                  (cyclos:name(group.nature)=='OPERATOR' && cyclos:granted(MemberPermission.OPERATORS_MANAGE))}">
		            	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		            		<td><cyclos:escapeHTML>${group.name}</cyclos:escapeHTML></td>
		            		<c:if test="${isAdmin}">
		            			<td><cyclos:escapeHTML><bean:message key="group.nature.${group.nature}" /></cyclos:escapeHTML></td>
		            		</c:if>
		            		<td align="center" nowrap="nowrap">
		            			<c:choose><c:when test="${cyclos:name(group.nature)=='OPERATOR' || cyclos:granted(permissionByNature[group.nature])}">
		            				<img groupId="${group.id}" class="edit groupDetails" src="<c:url value="/pages/images/edit.gif" />" />
		            			</c:when><c:otherwise>
		            				<img groupId="${group.id}" class="view groupDetails" src="<c:url value="/pages/images/view.gif" />" />
		            			</c:otherwise></c:choose>
		            			<c:choose><c:when test="${group.status.enabled && (cyclos:name(group.nature)=='OPERATOR' || cyclos:granted(permissionByNature[group.nature]))}">
		                      		<img groupId="${group.id}" class="permissions" src="<c:url value="/pages/images/permissions.gif" />" />
		                      	</c:when><c:otherwise>
		                      		<img src="<c:url value="/pages/images/permissions_gray.gif" />" />
		                      	</c:otherwise></c:choose>
		                      	<c:if test="${cyclos:name(group.nature)=='OPERATOR' || cyclos:granted(permissionByNature[group.nature])}"> 
		            				<img groupId="${group.id}" class="remove" src="<c:url value="/pages/images/delete.gif" />" />
		            			</c:if>
		            		</td>
		            	</tr>
		            </c:if>
            	</c:forEach>
            </table>
        </td>
	</tr>
</table>

<c:if test="${manageAnyGroup}"> 
	<table class="defaultTableContentHidden">
		<tr>
			<td align="right"><span class="label"><bean:message key="group.action.new" /></span><input type="button" id="newButton" class="button" value="<bean:message key="global.submit" />"></td>
		</tr>
	</table>
</c:if>
