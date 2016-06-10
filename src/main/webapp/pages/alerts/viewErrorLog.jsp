<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/alerts/viewErrorLog.js" />
<script>
	var removeOneConfirmation = "<cyclos:escapeJS><bean:message key="errorLog.removeOne.confirm"/></cyclos:escapeJS>";
	var removeSelectedConfirmation = "<cyclos:escapeJS><bean:message key="errorLog.removeSelected.confirm"/></cyclos:escapeJS>";
	var noneSelectedMessage = "<cyclos:escapeJS><bean:message key="global.error.nothingSelected"/></cyclos:escapeJS>";
</script>
<c:set var="editable" value="${cyclos:granted(AdminSystemPermission.ERROR_LOG_MANAGE)}" />
<c:choose><c:when test="${empty errorLog}">
	<div class="footerNote" helpPage="alerts_logs#error_log"><bean:message key="errorLog.search.noResults"/></div>
</c:when><c:otherwise>
	<ssl:form method="post" action="/admin/removeErrorLogEntries">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
	        <cyclos:help page="alerts_logs#error_log"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                	<c:if test="${editable}">
	                		<th class="tdHeaderContents" width="5%">&nbsp;</th>
	                	</c:if>
					    <th class="tdHeaderContents" width="25%"><bean:message key="errorLog.date"/></th>
	                    <th class="tdHeaderContents"><bean:message key="errorLog.path"/></th>
                		<th class="tdHeaderContents" width="10%">&nbsp;</th>
	                </tr>
					<c:forEach var="entry" items="${errorLog}">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                	<c:if test="${editable}">
			                	<td align="center"><input type="checkbox" class="checkbox" name="entryIds" value="${entry.id}"></td>
			                </c:if>
		                    <td align="center" nowrap="nowrap"><cyclos:format dateTime="${entry.date}"/></td>
		                    <td>${entry.path}</td>
	                    	<td align="center" nowrap="nowrap">
								<img entryId="${entry.id}" src="<c:url value="/pages/images/view.gif"/>" class="view"/>
			                    <c:if test="${editable}">
		                    		<img entryId="${entry.id}" src="<c:url value="/pages/images/delete.gif"/>" class="remove"/>
								</c:if>
							</td>
		                </tr>
	                </c:forEach>
	            </table>
	        </td>
	    </tr>
	</table>
	
	<table class="defaultTableContentHidden">
		<tr>
			<td width="50%">
				<input id="selectAllButton" type="button" class="button" value="<bean:message key="global.selectAll"/>">
				<input id="selectNoneButton" type="button" class="button" value="<bean:message key="global.selectNone"/>">
			</td>
			<td align="right">
				<input type="submit" class="button" value="<bean:message key="global.removeSelected"/>">
			</td>
		</tr>
	</table>
	</ssl:form>
</c:otherwise></c:choose>
