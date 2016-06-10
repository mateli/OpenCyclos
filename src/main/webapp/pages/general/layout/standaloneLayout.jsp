<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<html:html>

<jsp:include page="/pages/general/layout/head.jsp" />

<body class="main" onLoad="showMessage();">
<c:set var="standaloneLayout" value="${true}" scope="request" />
<jsp:include page="/pages/general/layout/layoutTop.jsp" />

<div class="standaloneContents">
<tiles:insert attribute="body" />
</div>

<jsp:include page="/pages/general/layout/layoutBottom.jsp" />			

</body>

<script>
init();
</script>

</html:html> 