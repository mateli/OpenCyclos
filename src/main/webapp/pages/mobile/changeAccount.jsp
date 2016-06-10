<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<div class="title"><cyclos:customImage type="system" name="mobileLogo"/>&nbsp;<bean:message key="accountType.change"/></div>
<c:forEach var="accountType" items="${accountTypes}" >
<a href="<c:url value="/do/mobile/confirmAccount?id=${accountType.id}"/>">${accountType.name}</a><br/>
</c:forEach>
