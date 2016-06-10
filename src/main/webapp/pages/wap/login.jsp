<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>

<card id="login" title="<bean:message key="mobile.login.title"/>">
<p>${selectedPrincipalLabel}<br/><input name="principal"/></p>
<p><bean:message key="mobile.credentials.${credentials}"/><br/><input name="password"/></p>
<p><anchor title="<bean:message key="global.submit"/>">
<go href="<c:url value="/do/wap/login"/>" method="post">
  <postfield name="principalType" value="${selectedPrincipalType}"/>
  <postfield name="principal" value="$(principal)"/>
  <postfield name="password" value="$(password)"/>
</go>
</anchor></p>
<c:if test="${not empty accessLinks}">
	<p>
	<br />
	<c:forEach var="link" items="${accessLinks}">
		<c:url value="/do/wap/login" var="url">
			<c:if test="${not empty link.paramName}">
				<c:param name="${link.paramName}" value="${link.paramValue}" />
			</c:if>
		</c:url>
		<a href="${url}">${link.label}</a><br />
	</c:forEach>
	</p>
</c:if>
</card>