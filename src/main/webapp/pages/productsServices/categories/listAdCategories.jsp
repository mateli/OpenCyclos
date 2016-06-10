<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/productsServices/categories/listAdCategories.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="adCategory.removeConfirmation"/></cyclos:escapeJS>";
</script>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="adCategory.title.list"/>
        </td>
        <cyclos:help page="advertisements#manage_categories"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                    <td class="tdHeaderContents"><bean:message key="adCategory.name"/></td>
                    <td width="15%" class="tdHeaderContents"><bean:message key="category.status"/></td>
                    <td width="15%" class="tdHeaderContents"><bean:message key="category.children"/></td>
                    <td width="8%" class="tdHeaderContents">&nbsp;</td>
                </tr>
                <c:forEach var="category" items="${categories}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="left">${category.name}</td>
	                    <td align="center"><bean:message key="category.status.${category.active ? 'ACTIVE' : 'INACTIVE'}"/></td>
						<td align="center">${fn:length(category.children)}</td>
						<td align="right" nowrap="nowrap">
	                        <c:choose><c:when test="${editable}">
	                        	<img class="edit" categoryId="${category.id}" src="<c:url value="/pages/images/edit.gif"/>"/>
			                    <img class="remove" categoryId="${category.id}" src="<c:url value="/pages/images/delete.gif"/>"/>
	                        </c:when><c:otherwise>
	                        	<img class="view" categoryId="${category.id}" src="<c:url value="/pages/images/view.gif"/>"/>
	                        </c:otherwise></c:choose>
	                    </td>
	                </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
<c:if test="${editable}">
	
	<table class="defaultTableContentHidden">
		<tr>
			<td align="right" width="50%">
				<span class="label"><bean:message key="adCategory.alterOrder"/></span>
				<input type="button" class="button" id="changeOrderButton" value="<bean:message key="global.submit"/>">
			</td>
			<td align="right">
				<span class="label"><bean:message key="adCategory.new"/></span>
				<input type="button" class="button" id="newButton" value="<bean:message key="global.submit"/>">
			</td>
		</tr>
	</table>
</c:if>