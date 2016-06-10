<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<html:html>

<jsp:include page="/pages/general/layout/head.jsp" />
<jsp:include flush="true" page="/pages/access/includes/loginDefinitions.jsp" />
<cyclos:script src="/pages/posweb/login.js" />
<script>
	var loginAction = "/do/posweb/login";
</script>

<cyclos:customizedFilePath type="style" name="posweb.css" var="poswebStyleUrl" />
<link rel="stylesheet" href="<c:url value="${poswebStyleUrl}" />">

<cyclos:includeCustomizedFile type="static" name="posweb_login.jsp" />

</html:html>
