<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/messageCategory/listMessageCategories.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="messageCategory.removeConfirmation"/></cyclos:escapeJS>";
</script>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="messageCategory.title.list"/></td>
        <!-- XXX change the help -->
        <cyclos:help page="messages#message_categories"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<td class="tdHeaderContents" width="90%"><bean:message key="messageCategory.name"/></td>
                    <td class="tdHeaderContents" width="10%">&nbsp;</td>
                </tr>
                <c:forEach var="messageCategory" items="${messageCategories}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                	<td><cyclos:escapeHTML>${messageCategory.name}</cyclos:escapeHTML></td>
	                    <td align="center" nowrap="nowrap">
	                    	<c:if test="${cyclos:granted(AdminSystemPermission.MESSAGE_CATEGORIES_MANAGE)}">
								<img class="edit messageCategoryDetails" src="<c:url value="/pages/images/edit.gif"/>" messageCategoryId="${messageCategory.id}" border="0">
    	                		<img class="remove" src="<c:url value="/pages/images/delete.gif"/>" messageCategoryId="${messageCategory.id}" border="0">
			                </c:if>
	                    </td>
	                </tr>
                </c:forEach>
			</table>
		</td>
	</tr>
</table>


<c:if test="${cyclos:granted(AdminSystemPermission.MESSAGE_CATEGORIES_MANAGE)}">
	<table class="defaultTableContentHidden">
		<tr>
			<td align="right">
				<span class="label"><bean:message key="messageCategory.action.new"/></span>
				<input type="button" class="button" id="newButton" value="<bean:message key="global.submit"/>">
			</td>
		</tr>
	</table>
</c:if>
