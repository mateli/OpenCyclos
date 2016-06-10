<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/groups/groupFilters/listGroupFilters.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="groupFilter.removeConfirmation"/></cyclos:escapeJS>";
</script>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="groupFilter.title.list"/>
        </td>
        <cyclos:help page="groups#manage_group_filters"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                    <td width="90%" class="tdHeaderContents"><bean:message key="groupFilter.name"/></td>
                    <td width="10%" class="tdHeaderContents">&nbsp;</td>
                </tr>
                <c:forEach var="groupFilter" items="${groupFilters}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="left">${groupFilter.name}</td>
	                    <td align="center">
		                    <c:choose><c:when test="${cyclos:granted(AdminSystemPermission.GROUP_FILTERS_MANAGE)}">
		                    	<img class="edit" groupFilterId="${groupFilter.id}" src="<c:url value="/pages/images/edit.gif"/>"/>
		                    	<img class="remove" groupFilterId="${groupFilter.id}" src="<c:url value="/pages/images/delete.gif"/>"/>
		                    </c:when><c:otherwise>
		                    	<img class="view" groupFilterId="${groupFilter.id}" src="<c:url value="/pages/images/view.gif"/>"/>
		                    </c:otherwise></c:choose>
	                    </td>
	                </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
<c:if test="${cyclos:granted(AdminSystemPermission.GROUP_FILTERS_MANAGE)}">
	
	<table class="defaultTableContentHidden">
		<tr>
			<td align="right">
				<span class="label"><bean:message key="groupFilter.new"/></span>
				<input type="button" class="button" id="newButton" value="<bean:message key="global.submit"/>">
			</td>
		</tr>
	</table>
</c:if>
