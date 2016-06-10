<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<html:html>
<jsp:include page="/pages/general/layout/head.jsp" />	
<style media="print">
	body {overflow: inherit;}
</style>
<style media="screen">
	body {overflow: auto;}
</style>

<script>
/*
Clear the behaviour list, because nothing is interactive on a print page,
except the bottom buttons, and their actions are set on the printLayout.js file
*/
Behaviour.list = [];
var isPosWeb = ${empty isPosWeb ? false : isPosWeb};
</script>
<cyclos:script src="/pages/general/layout/printLayout.js" />

<body class="bodyPrint">
	<tiles:insert attribute="body" />
	<center>
		<br>
		<input type="button" class="printButton" id="printButton" value="<bean:message key="global.print"/>${isPosWeb ? " (F4)" : ""}">
		&nbsp;&nbsp;&nbsp;
		<input type="button" class="printButton" id="closeButton" value="<bean:message key="global.close"/>${isPosWeb ? " (Esc)" : ""}">
	</center>
</body>
<script>
init();
<c:if test="${param.print == 'true'}">
	print();
</c:if>
</script>
</html:html>

