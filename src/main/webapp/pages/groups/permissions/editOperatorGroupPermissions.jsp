<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Collection" %>
<%@page import="nl.strohalm.cyclos.access.Module" %>
<%@page import="nl.strohalm.cyclos.access.Permission" %>

<cyclos:script src="/pages/groups/permissions/editOperatorGroupPermissions.js" />

<ssl:form action="${formAction}" method="post">
<html:hidden property="groupId" />
<html:hidden property="permission(group)" value="${group.id}" />

<c:forEach var="entry" items="${modulesByType}">
	<c:set var="moduleType" value="${entry.key}" />
	<c:set var="modules" value="${entry.value}" />

	<%-- Begin a new window for this new type --%>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="permission.module.type.${moduleType}" arg0="${group.name}"/></td>
	        <cyclos:help page="groups#manage_group_permissions_${fn:toLowerCase(cyclos:name(moduleType))}" />
	    </tr>
	    <tr>
    	   	<%
	       	Map<Module, List<Permission>> notAllowedPermissionsMap = (Map<Module, List<Permission>>) request.getAttribute("notAllowedPermissionsMap");
			%>
	        <td colspan="2" align="left" class="tdContentTableForms">
		        <c:forEach var="module" items="${moduleType.modules}">
		        	<%
		        	Module module = (Module) pageContext.getAttribute("module");
		        	Collection<Permission> permissions = module.getPermissions();
		        	System.out.println(module + " - " + notAllowedPermissionsMap.get(module));
		        	Collection<Permission> notAllowed = notAllowedPermissionsMap.get(module);
		        	pageContext.setAttribute("isEmptyModule", notAllowed != null && notAllowed.containsAll(permissions));
		        	%>
		        	<c:if test="${not isEmptyModule}">
						<fieldset>
							<legend><bean:message key="permission.${module.value}" /></legend>
					
							<table class="nested" width="100%">
								<c:forEach var="permission" items="${module.permissions}">
									<c:set var="selected" value="${cyclos:contains(group.permissions, permission)}"/>
									<c:set var="dto" value="${multiValuesPermissions[permission]}"/>
									<c:choose>
										<c:when test="${cyclos:contains(notAllowedPermissionsMap[module], permission)}">
											<%-- Just ignore it --%>
										</c:when>
										<c:when test="${not empty dto}"> <%-- Is a multiple (MDD / selection list) permission --%>				
											<tr ${empty dto.cssClassName ? '' : 'class="dto.cssClassName"'}>
												<td width="20px"></td>
												<td>
													<table class="nested" width="100%">
														<tr>
															<td width="30%" nowrap="nowrap"><bean:message key="permission.${permission.value}"/></td>
															<td>
																<cyclos:multiDropDown name="permission(${dto.property})" size="5" disabled="true" onchange="${empty dto.onChangeListener ? '' : dto.onChangeListener}">
																	<c:forEach var="value" items="${dto.possibleValues}">
																		<cyclos:option value="${value.id}" text="${value.name}" selected="${cyclos:contains(dto.currentValues, value)}" />
																	</c:forEach>
																</cyclos:multiDropDown>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</c:when>
										<c:otherwise> <%-- Is a single (boolean) permission --%>
											<tr>
												<td width="20px"><input class="checkbox" id="chk_${permission.value}" name="permission(operations)" type="checkbox" ${selected ? 'checked="checked"' : ''} disabled="disabled" value="${permission.qualifiedName}"></td>
												<td><label for="chk_${permission.value}"><bean:message key="permission.${permission.value}"/></label></td>
											</tr>
										</c:otherwise>
									</c:choose>				
								</c:forEach> <%-- end for each permission --%>
							</table>
						</fieldset>
					</c:if>
				</c:forEach> <%-- end for each module --%>					
			</td>
		</tr>
	</table>	
</c:forEach> <%-- end for each module type--%>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left">
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
			<c:if test="${cyclos:granted(MemberPermission.OPERATORS_MANAGE)}">
				&nbsp;<input type="button" class="button" id="groupSettingsButton" value="<bean:message key="group.settings"/>">
			</c:if>
		</td>
		<td align="right" colspan="2">
			<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
			<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
		</td>
	</tr>
</table>

</ssl:form>
