<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<c:if test="${!noLoggedUserDataBar}">
	<div id="loginDataBar" class="loginDataBar">
</c:if>
	<c:if test="${not empty loggedUser}">
		<span class="loginData">
			<c:choose>
				<c:when test="${isOperator}">
					<c:set var="loggedName" value="${loggedUser.operator.member.username} / ${loggedUser.username } - ${loggedUser.element.name}" />				
				</c:when>
				<c:otherwise>
					<c:set var="loggedName" value="${loggedUser.username } - ${loggedUser.element.name}" />				
				</c:otherwise>
			</c:choose>
			<bean:message key="session.loggedAs" arg0="${loggedName}"/>
		</span>
		<span class="loginData">
			<c:choose>
				<c:when test="${not empty loggedUser.lastLogin}">
					<c:set var="lastLogin"><cyclos:format dateTime="${loggedUser.lastLogin}"/></c:set>
					<bean:message key="session.lastLogin" arg0="${lastLogin}"/>
				</c:when>
				<c:otherwise>
					<bean:message key="session.firstLogin"/>
				</c:otherwise>
			</c:choose>
		</span>
	</c:if>
<c:if test="${!noLoggedUserDataBar}">
	</div>
</c:if>
