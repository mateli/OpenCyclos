<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<!-- Cyclos runtime error page -->

<cyclos:noCache/>

<c:url value="/do/error" var="url" />
<%
String url = (String) pageContext.getAttribute("url");
if (response.isCommitted()) {
	out.println(String.format("<script>self.location = \"%s\";</script>", url)); 
} else {
    response.sendRedirect(url);
}
return;
%>