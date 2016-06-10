<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
	<c:when test="${not empty returnTo}">
		<c:set var="path" value="${returnTo}"/>
		<c:remove var="returnTo"/>
	</c:when>
	<c:when test="${isMember}">
		<c:set var="path" value="/member/home"/>
	</c:when>
	<c:when test="${isAdmin}">
		<c:set var="path" value="/admin/home"/>
	</c:when>
	<c:otherwise>
		<c:set var="path">/login?login=true&groupId=${param.groupId}&groupFilterId=${param.groupFilterId}</c:set>
	</c:otherwise>
</c:choose>
<c:redirect url="/do${path}" />
