<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<html:html>

<c:choose><c:when test="${not empty loggedUser}">
	<head>
		<meta http-equiv="refresh" content="0;URL=${pathPrefix}/home">
	</head>
</c:when><c:otherwise>

	<jsp:include page="/pages/general/layout/head.jsp" />
	<cyclos:customizedFilePath type="style" name="login.css" var="loginUrl" groupId="${empty loggedUserId ? cookie.groupId.value : ''}" groupFilterId="${empty loggedUserId ? cookie.groupFilterId.value : ''}" />	
	<link rel="stylesheet" href="<c:url value="${loginUrl}" />">	

	<jsp:include flush="true" page="/pages/access/includes/loginDefinitions.jsp" />	
	
	<cyclos:includeCustomizedFile type="static" name="login.jsp" groupId="${empty loggedUserId ? cookie.groupId.value : ''}" groupFilterId="${empty loggedUserId ? cookie.groupFilterId.value : ''}" />	
	
	<script>
		if (!is.ie6) {
			var td = $('loginRegistration');
			var div = $('loginRegistrationDiv');
			if (td && div) {
				div.style.height = (td.getHeight() - 10) + "px";
			}
		}
		ensureLoginForm();
	</script>

</c:otherwise></c:choose>

</html:html>
