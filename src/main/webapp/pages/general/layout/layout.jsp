<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:if test="${empty loggedUser}">
	<jsp:forward page="/do/login" />
	<% if (true) return; %>
</c:if>

<html:html>

<c:set var="mainLayout" value="${true}" scope="request" />
<jsp:include page="/pages/general/layout/head.jsp" />

<body class="main">
<cyclos:script src="/pages/general/layout/layout.js" />
<a name="top"></a>

<div class="layoutMain">
<jsp:include page="/pages/general/layout/layoutTop.jsp" />

<!-- Begin: Contents -->
<tiles:insert attribute="body" />
<!-- End:   Contents -->

<jsp:include page="/pages/general/layout/layoutBottom.jsp" />
</div>

</body>

<script>
init();
</script>

</html:html>