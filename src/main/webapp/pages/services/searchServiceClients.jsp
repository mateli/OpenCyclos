<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/services/searchServiceClients.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="serviceClient.removeConfirmation"/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}">

<c:choose><c:when test="${empty serviceClients}">
	<div class="footerNote" title="<bean:message key="serviceClient.title.list"/>" helpPage="settings#web_services_clients"><bean:message key="serviceClient.noResults"/></div>
	
	<table class="defaultTableContentHidden">
		<tr>
			<td>
	        	<span class="label">
	        		<bean:message key="serviceClient.action.new" />
	        	</span>
	        	<input type="button" class="button" value="<bean:message key="global.submit" />" id="newButton">
	        </td>
		</tr>
	</table>
</c:when><c:otherwise>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="serviceClient.title.list"/></td>
	        <cyclos:help page="settings#web_services_clients"/>
	    </tr>
		<tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents" width="30%"><bean:message key='serviceClient.name'/></th>
	                    <th class="tdHeaderContents"><bean:message key='serviceClient.address'/></th>
	                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
	                </tr>
					<c:forEach items="${serviceClients}" var="client">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="left">${client.name}</td>
		                    <td align="left">${client.hostname}</td>
		                    <td align="center" nowrap="nowrap">
		                    	<c:choose><c:when test="${cyclos:granted(AdminSystemPermission.SERVICE_CLIENTS_MANAGE)}">
		            				<img src="<c:url value="/pages/images/edit.gif"/>" class="edit serviceClientDetails" clientId="${client.id}"/>
		                    		<img src="<c:url value="/pages/images/delete.gif"/>" class="remove" clientId="${client.id}"/>
		            			</c:when><c:otherwise>
									<img src="<c:url value="/pages/images/view.gif"/>" class="view serviceClientDetails" clientId="${client.id}"/>
	        	    			</c:otherwise></c:choose>
		                    </td>
		                 </tr>
					</c:forEach>
				</table>
			</td>
	    </tr>
	</table>
	
	<table class="defaultTableContentHidden">
		<tr>
			<c:if test="${cyclos:granted(AdminSystemPermission.SERVICE_CLIENTS_MANAGE)}">
				<td>
		        	<span class="label">
		        		<bean:message key="serviceClient.action.new" />
		        	</span>
		        	<input type="button" class="button" value="<bean:message key="global.submit" />" id="newButton">
				</td>
			</c:if>
	        <td align="right"><cyclos:pagination items="${serviceClients}"/></td>
		</tr>
	</table>
</c:otherwise></c:choose>
</ssl:form>
