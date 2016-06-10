<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>

<card id="viewPayments" title="<bean:message key="accountType.change"/>">
<c:forEach var="accountType" items="${accountTypes}" >
<p><a href="<c:url value="/do/wap/confirmAccount?id=${accountType.id}"/>">${accountType.name}</a></p>
</c:forEach>
</card>
