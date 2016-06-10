<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<cyclos:script src="/pages/groups/permissions/editGroupPermissions.js" />
<script language="JavaScript">
	var groupNature = '${group.nature}';
</script>

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
	        <td colspan="2" align="left" class="tdContentTableForms">
	        	<c:if test="${cyclos:name(moduleType) == 'ADMIN_MEMBER'}">
	        		<c:set var="dto" value="${multiValuesPermissions[AdminMemberPermission.MEMBERS_VIEW]}"/>
					<table class="nested" width="100%">
						<tr>
							<td class="label" width="5%" nowrap="nowrap"><bean:message key="permission.admin.managesGroups"/></td>
							<td>
								<cyclos:multiDropDown name="permission(${dto.property})" size="5" disabled="true" onchange="${empty dto.onChangeListener ? '' : dto.onChangeListener}">
									<c:forEach var="current" items="${dto.possibleValues}">
										<cyclos:option value="${current.id}" text="${current.name}" selected="${cyclos:contains(dto.currentValues, current)}" />
									</c:forEach>
								</cyclos:multiDropDown>
							</td>
						</tr>
					</table>
	        	</c:if>	        	
		        <c:forEach var="module" items="${modules}">
					<fieldset>
						<legend><bean:message key="permission.${module.value}" /></legend>
				
						<table class="nested" width="100%">
							<c:forEach var="permission" items="${module.permissions}">
								<c:set var="selected" value="${cyclos:contains(group.permissions, permission)}"/>
								<c:set var="dto" value="${multiValuesPermissions[permission]}"/>
								<c:choose>
									<c:when test="${cyclos:equals(permission, AdminMemberPermission.MEMBERS_VIEW)}">
										<%-- Ignore it because this permission was added at the beginning of the module--%>
									</c:when>								
									<c:when test="${not empty dto}"> <%-- Is a multiple (MDD / selection list) permission --%>
										<c:choose>
										<c:when test="${empty dto.cssClassName}"><tr></c:when>
										<c:otherwise><tr class=${dto.cssClassName}></c:otherwise>
										</c:choose>				
											<td width="20px"></td>
											<td>
												<table class="nested" width="100%">
													<tr>
														<td width="30%" nowrap="nowrap"><bean:message key="permission.${permission.value}"/></td>
														<td>
															<cyclos:multiDropDown name="permission(${dto.property})" size="5" disabled="true" onchange="${empty dto.onChangeListener ? '' : dto.onChangeListener}" varName="${dto.property}">
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
				</c:forEach> <%-- end for each module --%>					
			</td>
		</tr>
	</table>	
</c:forEach> <%-- end for each module type--%>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left">
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
			<c:if test="${(cyclos:name(group.nature)=='ADMIN' && cyclos:granted(AdminSystemPermission.ADMIN_GROUPS_VIEW)) ||
                         ((cyclos:name(group.nature)=='BROKER' || cyclos:name(group.nature)=='MEMBER') && cyclos:granted(AdminMemberPermission.GROUPS_VIEW) && cyclos:contains(managesGroups, group))}">
				&nbsp;<input type="button" class="button keepEnabled" id="groupSettingsButton" value="<bean:message key="group.settings"/>">
			</c:if>
		</td>
		<c:if test="${editable}"> 
			<td align="right" colspan="2">
				<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
				<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
			</td>
		</c:if>
	</tr>
</table>
</ssl:form>
