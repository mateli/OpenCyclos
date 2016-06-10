<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<form id="cyclosLogin">
<input type="hidden" name="operatorLogin" value="${empty param.operator ? false : param.operator}" />
<input type="hidden" name="principalType" value="${selectedPrincipalType}" />
<table class="nested">
	<c:if test="${param.operator}">
		<tr>
			<td nowrap="nowrap" class="label" width="40%"><bean:message key="member.username"/></td>
			<td><input id="cyclosMember" name="member" class="medium"/></td>
		</tr>
	</c:if>
	<tr>
		<td nowrap="nowrap" class="label" width="40%">${selectedPrincipalLabel}</td>
		<td><c:choose><c:when test="${empty selectedPrincipalType.customField}">
				<input id="cyclosUsername" name="principal" class="small"/>
			</c:when><c:otherwise>
				<cyclos:customField field="${selectedPrincipalType.customField}" valueName="principal" styleId="cyclosUsername" search="true" size="medium"/>
			</c:otherwise></c:choose></td>
	</tr>
	<tr>
		<td nowrap="nowrap" class="label"><bean:message key="login.password"/></td>
		<td><input type="password" id="cyclosPassword" class="small"/></td>
	</tr>
	<c:choose><c:when test="${accessSettings.virtualKeyboard}">
		<tr>
			<td colspan="2" align="center">
				<script>renderVirtualKeyboard();</script>
			</td>
		</tr>
	</c:when><c:otherwise>
		<tr>
			<td colspan="2" align="right">
				<br class="small">
				<input type="submit" class="button" value="<bean:message key="global.submit"/>">
			</td>
		</tr>
	</c:otherwise></c:choose>
	<c:if test="${not empty accessLinks}">
		<tr>
			<td colspan="2" align="${accessSettings.virtualKeyboard ? 'center': 'right'}">
				<br class="small">
				<c:choose><c:when test="${not empty singleAccessLink}">
					<c:url value="/do/login" var="url">
						<c:param name="login" value="true" />
						<c:if test="${not empty singleAccessLink.paramName}">
							<c:param name="${singleAccessLink.paramName}" value="${singleAccessLink.paramValue}" />
						</c:if>
						<c:if test="${not empty param.groupId}">
							<c:param name="groupId" value="${param.groupId}" />
						</c:if>
						<c:if test="${not empty param.groupFilterId}">
							<c:param name="groupFilterId" value="${param.groupFilterId}" />
						</c:if>
					</c:url>
					<a class="default" href="${url}">${singleAccessLink.label}</a>
				</c:when><c:otherwise>
					<a class="default" id="accessOptionsLink"><bean:message key="login.accessOptions"/></a>
					<div id="accessOptions" style="display:none">
						<br class="small">
						<c:forEach var="link" items="${accessLinks}">
							<c:url value="/do/login" var="url">
								<c:param name="login" value="true" />
								<c:if test="${not empty link.paramName}">
									<c:param name="${link.paramName}" value="${link.paramValue}" />
								</c:if>
								<c:if test="${not empty param.groupId}">
									<c:param name="groupId" value="${param.groupId}" />
								</c:if>
								<c:if test="${not empty param.groupFilterId}">
									<c:param name="groupFilterId" value="${param.groupFilterId}" />
								</c:if>
							</c:url>
							<a class="default" href="${url}">${link.label}</a><br>
						</c:forEach>
					</div>
				</c:otherwise></c:choose>
			</td>
		</tr>
	</c:if>
</table>
</form>
