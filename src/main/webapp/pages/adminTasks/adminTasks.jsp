<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<script>
	var rebuildConfirmation = "<cyclos:escapeJS><bean:message key='adminTasks.indexes.confirmRebuild'/></cyclos:escapeJS>";
	var setOfflineConfirmation = "<cyclos:escapeJS><bean:message key='adminTasks.onlineState.confirmOffline'/></cyclos:escapeJS>";
</script>
<cyclos:script src="/pages/adminTasks/adminTasks.js" />
<c:if test="${canViewIndexes}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="adminTasks.indexes.title"/></td>
	        <cyclos:help page="settings#search_indexes"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents"><bean:message key='adminTasks.indexes.type'/></th>
	                    <th width="15%" class="tdHeaderContents"><bean:message key='adminTasks.indexes.status'/></th>
	                    <th width="10%" class="tdHeaderContents">&nbsp;</th>
	                </tr>
					<c:forEach items="${indexesStatus}" var="entry">
						<c:set var="type" value="${entry.key}" />
						<c:set var="status" value="${entry.value}" />
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="left"><bean:message key="adminTasks.indexes.type.${type}" /></td>
		                    <td align="center"><bean:message key="adminTasks.indexes.status.${status}" /></td>
		                    <td align="center">
		                    	<a class="default rebuild" entityType="${type}">
									<bean:message key="adminTasks.indexes.rebuild" />
		                    	</a>
		                    </td>
		                 </tr>
					</c:forEach>
				</table>
	        </td>
	    </tr>
	</table>
	
	<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
		<tr>
			<td align="right" nowrap="nowrap">
				<input type="button" value="<bean:message key="adminTasks.indexes.rebuildAll" />" class="button rebuild">
			</td>
		</tr>
	</table>
	
</c:if>

<c:if test="${canManageOnlineState}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="adminTasks.onlineState.title"/></td>
	        <cyclos:help page="settings#online_state"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableForms">
	        	<br>
	        	<div align="center"><bean:message key="adminTasks.onlineState.${systemOnline ? 'online' : 'offline'}"/></div>
	            &nbsp;
	        </td>
	    </tr>
	</table>
	
	<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
		<tr>
			<td align="right" nowrap="nowrap">
				<input type="button" value="<bean:message key="adminTasks.onlineState.${systemOnline ? 'setOffline' : 'setOnline'}"/>" class="button" id="setStateButton" online="${!systemOnline}">
			</td>
		</tr>
	</table>
	
</c:if>
