<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>

<html>
<jsp:include page="/pages/general/layout/head.jsp" />
<jsp:include page="/pages/access/includes/loginDefinitions.jsp" />
<script>
	var statusMessages = {};
	<c:forEach var="entry" items="${statusMessages}">
		statusMessages.${entry.key} = "<cyclos:escapeJS>${entry.value}</cyclos:escapeJS>";
	</c:forEach>
</script>
<cyclos:script src="/pages/access/externalLogin.js" />
<body>
<form id="loginForm" method="post">
	
	<table class="nested" id="containerTable">
		<c:if test="${param.operator}">
			<tr>
				<td nowrap="nowrap" class="label" width="40%"><bean:message key="login.memberUsername"/></td>
				<td><input id="cyclosMember" class="medium"/></td>
			</tr>
		</c:if>
		<tr>
			<td nowrap="nowrap" class="label" width="40%"><bean:message key="login.username"/></td>
			<td><input id="cyclosUsername" class="medium"/></td>
		</tr>
		<tr>
			<td nowrap="nowrap" class="label"><bean:message key="login.password"/></td>
			<td><input type="password" id="cyclosPassword" class="medium"/></td>
		</tr>
		<c:choose><c:when test="${accessSettings.virtualKeyboard}">
			<tr>
				<td colspan="2" align="center">
					<script>renderVirtualKeyboard();</script>
				</td>
			</tr>
			<c:if test="${accessSettings.allowOperatorLogin}">
				<tr>
					<td colspan="2" align="center">
						
						<c:choose><c:when test="${param.operator}">
							<a class="default" href="<c:url value="/do/externalLogin"/>"><bean:message key="login.action.loginAsMember"/></a>
						</c:when><c:otherwise>
							<a class="default" href="<c:url value="/do/externalLogin"><c:param name="operator" value="true"/></c:url>"><bean:message key="login.action.loginAsOperator"/></a>
						</c:otherwise></c:choose>				
					</td>
				</tr>
			</c:if>
		</c:when><c:otherwise>
			<tr>
				<td colspan="2" align="right">
					
					<table class="nested" width="100%">
						<tr>
							<td align="center">
								<c:if test="${accessSettings.allowOperatorLogin}">
									<c:choose><c:when test="${param.operator}">
										<a class="default" href="<c:url value="/do/externalLogin"/>"><bean:message key="login.action.loginAsMember"/></a>
									</c:when><c:otherwise>
										<a class="default" href="<c:url value="/do/externalLogin"><c:param name="operator" value="true"/></c:url>"><bean:message key="login.action.loginAsOperator"/></a>
									</c:otherwise></c:choose>				
								</c:if>
							</td>
							<td align="right">
								<input type="submit" class="button" value="<bean:message key="global.submit"/>">
							</td>	
						</tr>
					</table>
				</td>
			</tr>
		</c:otherwise></c:choose>
	</table>
	
</form>
</body>
</html>