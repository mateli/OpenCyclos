<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<bean:message key="about.version" arg0="${cyclosVersion}"/><br>
<c:set var="url">http://www.cyclos.org/</c:set>
<bean:message key="about.message" arg0="${url}"/><p>
<img border="0" src="<html:rewrite page="/pages/images/gaucho.gif"/>" width="65" height="74">