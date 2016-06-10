<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:if test="${cyclos:granted(AdminSystemPermission.THEMES_SELECT) || cyclos:granted(AdminSystemPermission.THEMES_REMOVE)}">
	<jsp:include flush="true" page="selectTheme.jsp"/><br/>
</c:if>
<c:if test="${cyclos:granted(AdminSystemPermission.THEMES_IMPORT)}">
	<jsp:include flush="true" page="importTheme.jsp"/><br/>
</c:if>
<c:if test="${cyclos:granted(AdminSystemPermission.THEMES_EXPORT)}">
	<jsp:include flush="true" page="exportTheme.jsp"/><br/>
</c:if>
