<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:if test="${not empty mobileException}">
	<c:set var="titleKey" value="${mobileException.titleKey}"/>
	<c:set var="messageKey" value="${mobileException.messageKey}"/>
	<c:set var="arguments" value="${mobileException.args}"/>
</c:if>
<c:set var="mobileBookmark" scope="page" value="${mobileBookmark}"/>
<c:remove var="mobileBookmark" scope="session"/>

<card id="error" title="<cyclos:escapeHTML><bean:message key="${empty titleKey ? 'mobile.error.title' : titleKey}"/></cyclos:escapeHTML>">
<p>
<cyclos:escapeHTML>
<bean:message key="${empty messageKey ? 'error.general' : messageKey}" arg0="${arguments[0]}" arg1="${arguments[1]}" arg2="${arguments[2]}" arg3="${arguments[3]}" arg4="${arguments[4]}"/>
</cyclos:escapeHTML>
</p>
<p>
<c:choose>
	<c:when test="${empty loggedUser}">
		<a href="<c:url value="/do/wap/login"/>"><bean:message key="global.ok"/></a>
   	</c:when>
   	<c:otherwise>
   		<c:choose>
   			<c:when test="${not empty mobileBookmark}">
   				<a href="<c:url value="/do${mobileBookmark}"/>"><bean:message key="global.back"/></a>
   			</c:when>
   			<c:otherwise>
   				<a href="<c:url value="/do/wap/home"/>"><bean:message key="global.ok"/></a>
   			</c:otherwise>
   		</c:choose>
   	</c:otherwise>
   </c:choose>
</p>
</card>