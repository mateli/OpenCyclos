<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<ssl:form method="post" action="/mobile/login">
<html:hidden property="principalType" value="${selectedPrincipalType}"/>
<div class="title"><cyclos:customImage type="system" name="mobileLogo"/>&nbsp;<bean:message key="mobile.login.title"/></div>
<div class="label">${selectedPrincipalLabel}</div>
<html:text property="principal"/><br/>
<div class="label"><bean:message key="mobile.credentials.${credentials}"/></div>
<html:password property="password"/><br/><br/>
<input type="submit" value="<bean:message key="global.submit"/>"/>
</ssl:form>
<c:if test="${not empty accessLinks}">
	<br />
	<c:forEach var="link" items="${accessLinks}">
		<c:url value="/do/mobile/login" var="url">
			<c:if test="${not empty link.paramName}">
				<c:param name="${link.paramName}" value="${link.paramValue}" />
			</c:if>
		</c:url>
		<a href="${url}">${link.label}</a>
	</c:forEach>
</c:if>
