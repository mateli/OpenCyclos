<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-tiles" prefix="tiles" %>

<html:html>

<jsp:include page="/pages/general/layout/head.jsp" />

<body onLoad="showMessage();">

<table class="defaultTableCenter">
	<tr>
		<td align="center" valign="top">
			
			<tiles:insert attribute="body" />
			&nbsp;
		</td>
	</tr>
</table>

</body>

<script>
if (self !== top) {
	$$(".defaultTableCenter").each(function(e) {
		e.removeClassName("defaultTableCenter");
		e.style.marginLeft = "auto";
		e.style.marginRight = "auto";
	});
}
init();
</script>

</html:html> 