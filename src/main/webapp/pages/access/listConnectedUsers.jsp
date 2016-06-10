<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<script>
	var nothingSelectedMessage = "<cyclos:escapeJS><bean:message key="global.error.nothingSelected"/></cyclos:escapeJS>";
	var disconnectTooltip = "<cyclos:escapeJS><bean:message key="connectedUsers.disconnectToolTip"/></cyclos:escapeJS>";
</script>
<cyclos:script src="/pages/access/listConnectedUsers.js" />

<c:if test="${isAdmin}">
	<ssl:form method="post" action="${formAction}">
	
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="connectedUsers.title"/></td>
	        <cyclos:help page="user_management#connected_users"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="right" class="tdContentTableForms">
	            <table class="defaultTable">
	          		<tr>
	            		<td class="label" width="25%"><bean:message key="connectedUsers.nature"/></td>
	            		<td>
	            			<cyclos:multiDropDown name="query(natures)" emptyLabelKey="global.search.all">
	            				<c:forEach var="nature" items="${groupNatures}">
	            					<c:set var="natureLabel"><bean:message key="group.nature.${nature}"/></c:set>
	            					<cyclos:option value="${nature}" text="${natureLabel}" selected="${cyclos:contains(query.natures, nature)}"/>
	            				</c:forEach>
	            			</cyclos:multiDropDown>
	            		</td>
	            		<td width="10%"><input type="submit" class="button" value="<bean:message key="global.submit"/>"></td>
	          		</tr>
	        	</table>
	        </td>
	    </tr>
	</table>
	</ssl:form>
	
</c:if>
<c:if test="${not empty sessions}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
	        <cyclos:help page="user_management#connected_users_result"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable" cellspacing="0" cellpadding="0">
	                <tr>
	                    <td class="tdHeaderContents" width="20%"><bean:message key="member.username"/></td>
						<td class="tdHeaderContents" width="35%"><bean:message key="member.name"/></td>
						<td class="tdHeaderContents" width="25%"><bean:message key="connectedUsers.loggedAt"/></td>
						<td class="tdHeaderContents" width="15%"><bean:message key="connectedUsers.remoteAddress"/></td>
						<td class="tdHeaderContents" width="5%"></td>
	                </tr>
					<c:forEach var="session" items="${sessions}">
	                	<c:set var="user" value="${session.user}"/>
	                	<c:set var="nature" value="${cyclos:name(user.element.nature)}"/>
						<c:set var="trClass"><t:toggle>ClassColor1|ClassColor2</t:toggle></c:set>
		                <tr class="${trClass}">
		                    <td align="left" valign="top">
		                    	<c:choose><c:when test="${nature == 'OPERATOR' && isAdmin}">
		                    		<c:set var="member" value="${user.operator.member}"/>		                    	
			                    	<cyclos:profile elementId="${member.id}" pattern="username"/>
			                    	/ ${user.username}
		                    	</c:when><c:otherwise>
			                    	<cyclos:profile elementId="${user.id}" pattern="username"/>
		                    	</c:otherwise></c:choose>
		                    </td>
		                    <td align="left" valign="top">
		                    	<c:choose><c:when test="${nature == 'OPERATOR' && isAdmin}">
			                    	${user.element.name}
		                    	</c:when><c:otherwise>
			                    	<cyclos:profile elementId="${user.id}" pattern="name"/>
		                    	</c:otherwise></c:choose>
							</td>
		                    <td align="center"><cyclos:format dateTime="${session.creationDate}" /></td>
		                    <td align="center">${session.remoteAddress}</td>
		                    <td align="center" valign="middle">
		                    	<c:set var="allowDisconnect" value="${true}"/>
		                    	<c:if test="${isAdmin}">
				                    <c:choose>
				                    	<c:when test="${session.identifier == pageContext.session.id}">
				                    		<%-- Cannot disconnect your same session --%>
				                    		<c:set var="allowDisconnect" value="${false}"/>
				                    	</c:when>
				                    	<c:otherwise>
				                    		<c:set var="allowDisconnect" value="${currentIsAdmin ? canDisconnectAdmin : canDisconnectMember}"/>
				                    	</c:otherwise>
				                    </c:choose>
		                    	</c:if>
		                    	<c:if test="${allowDisconnect}">
				                    <img class="disconnect" sessionId="${session.id}" src="<c:url value="/pages/images/delete.gif" />" />
		                    	</c:if>
		                    </td>
		                </tr>
		            </c:forEach>
	            </table>
	        </td>
	    </tr>
	</table>
</c:if>

<c:choose>
<c:when test="${not empty sessions}">
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<cyclos:pagination items="${sessions}"/>
			</td>
		</tr>
	</table>
</c:when>
<c:when test="${isMember}">
	<div class="footerNote" helpPage="user_management#connected_users_result"><bean:message key="connectedUsers.noOperators"/></div>
</c:when>
</c:choose>

<c:if test="${fn:endsWith(navigation.previous, '/home')}">
	<table class="defaultTableContentHidden">
		<tr>
			<td align="left">
				<input class="button" type="button" id="backButton" value="<bean:message key='global.back'/>">
			</td>
		</tr>
	</table>
</c:if>