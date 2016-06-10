<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<html:html>

<jsp:include page="/pages/general/layout/head.jsp" />

<body onLoad="showMessage();">

<table width="100%" cellspacing="0" cellpadding="0">
<tr>
	<td align="center">
		<cyclos:includeCustomizedFile type="static" name="webshop_header.jsp" />
	</td>
</tr>
<tr>
	<td align="center">
		<tiles:insert attribute="body" />
	</td>
</tr>
<tr>
	<td align="center">
		<cyclos:includeCustomizedFile type="static" name="webshop_footer.jsp" />
	</td>
</tr>
</table>

</body>

</html:html> 