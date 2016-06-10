<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/groups/groupFilters/editGroupFilter.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="groupFilterId"/>
<html:hidden property="groupFilter(id)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="groupFilter.title.${isInsert ? 'insert' : 'modify'}"/></td>
        <cyclos:help page="groups#group_filter"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
					<td class="label" width="25%"><bean:message key='groupFilter.name'/></td>
					<td><html:text property="groupFilter(name)" readonly="true" styleClass="full InputBoxDisabled"/></td>
				</tr>
            	<tr>
            		<td class="label"><bean:message key="groupFilter.rootUrl" /></td>
            		<td><html:text property="groupFilter(rootUrl)" styleClass="full InputBoxDisabled" readonly="true"/></td>
            	</tr>
				<tr>
					<td class="label" width="25%"><bean:message key='groupFilter.loginPageName'/></td>
					<td><html:text property="groupFilter(loginPageName)" readonly="true" styleClass="full InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label" width="25%"><bean:message key='groupFilter.containerUrl'/></td>
					<td><html:text property="groupFilter(containerUrl)" readonly="true" styleClass="full InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label" valign="top"><bean:message key='groupFilter.description'/></td>
					<td><html:textarea styleId="descriptionText" property="groupFilter(description)" rows="6" readonly="true" styleClass="full InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key='groupFilter.showInProfile'/></td>
					<td><html:checkbox property="groupFilter(showInProfile)" disabled="true" styleClass="checkbox InputBoxDisabled" value="true"/></td>
				</tr>
				<tr>
					<td class="label" valign="top"><bean:message key='groupFilter.groups'/></td>
					<td>
						<cyclos:multiDropDown name="groupFilter(groups)" disabled="true" size="5">
							<c:forEach var="g" items="${groups}">
								<cyclos:option value="${g.id}" text="${g.name}" selected="${cyclos:contains(groupFilter.groups, g)}"/>
							</c:forEach>
						</cyclos:multiDropDown>
					</td>
				</tr>
				<tr>
					<td class="label" valign="top"><bean:message key='groupFilter.viewableBy'/></td>
					<td>
						<cyclos:multiDropDown name="groupFilter(viewableBy)" disabled="true" size="5">
							<c:forEach var="vb" items="${viewableBy}">
								<cyclos:option value="${vb.id}" text="${vb.name}" selected="${cyclos:contains(groupFilter.viewableBy, vb)}"/>
							</c:forEach>
						</cyclos:multiDropDown>
					</td>
				</tr>
				<c:if test="${cyclos:granted(AdminSystemPermission.GROUP_FILTERS_MANAGE)}">
					<tr>
						<td align="right" colspan="2">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
				</c:if>
			</table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
</ssl:form>

<c:if test="${!isInsert}">
	
	<script>
		var stopCustomizingConfirmation = "<cyclos:escapeJS><bean:message key="groupFilter.customizedFiles.removeConfirmation"/></cyclos:escapeJS>";
	</script>
	<c:choose>
		<c:when test="${empty customizedFiles}">
			<div class="footerNote" helpPage="groups#manage_group_filter_customized_files"><bean:message key="groupFilter.customizedFiles.noResults"/></div>
		</c:when>
		<c:otherwise>
			<table class="defaultTableContent" cellspacing="0" cellpadding="0">
			    <tr>
			        <td class="tdHeaderTable"><bean:message key="groupFilter.customizedFiles.title"/></td>
			        <cyclos:help page="groups#manage_group_filter_customized_files"/>
			    </tr>
			    <tr>
			        <td colspan="2" align="left" class="tdContentTableLists">
			            <table class="defaultTable">
			            	<tr>
			            		<td class="tdHeaderContents"><bean:message key="customizedFile.type" /></td>
			            		<td class="tdHeaderContents"><bean:message key="customizedFile.name" /></td>
			            		<td class="tdHeaderContents" width="10%">&nbsp;</td>
			            	</tr>
			            	<c:forEach var="file" items="${customizedFiles}">
				            	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
				            		<td><cyclos:escapeHTML><bean:message key="customizedFile.type.${file.type}" /></cyclos:escapeHTML></td>
				            		<td><cyclos:escapeHTML>${file.name}</cyclos:escapeHTML></td>
				            		<td align="center" nowrap="nowrap">
				                    	<c:if test="${cyclos:name(file.type) != 'STYLE'}">
				                    		<img class="preview previewCustomizedFile" src="<c:url value="/pages/images/preview.gif"/>" fileName="<cyclos:escapeHTML>${file.name}</cyclos:escapeHTML>" fileType="<cyclos:escapeHTML>${file.type}</cyclos:escapeHTML>" border="0">
				                    	</c:if>
					            		<c:choose><c:when test="${canManageCustomizedFiles}">
											<img fileId="${file.id}" class="edit customizedFileDetails" src="<c:url value="/pages/images/edit.gif" />" />
					            			<img fileId="${file.id}" class="remove removeCustomizedFile" src="<c:url value="/pages/images/delete.gif" />" />
				                      	</c:when><c:otherwise>
				                      		<img fileId="${file.id}" class="view customizedFileDetails" src="<c:url value="/pages/images/view.gif" />" />
				                      	</c:otherwise></c:choose>
				            		</td>
				            	</tr>
			            	</c:forEach>
			            </table>
			        </td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
	
	<c:if test="${canManageCustomizedFiles}">
		<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
			<tr>
				<td align="right">
					<span class="label"><bean:message key="groupFilter.customizedFiles.action.new"/></span>
					<input type="button" id="newCustomizedFileButton" class="button" value="<bean:message key="global.submit"/>">
				</td>
			</tr>
		</table>
	</c:if>
</c:if>
