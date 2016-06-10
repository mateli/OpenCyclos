<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/groups/groupFilters/customizedFiles/editGroupFilterCustomizedFile.js" />
<ssl:form method="post" action="${formAction}" >
<html:hidden property="file(id)" />
<html:hidden property="file(groupFilter)" />
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="groupFilter.customizedFiles.title.${isInsert ? 'new' : 'modify'}" arg0="${group.name}"/></td>
        <cyclos:help page="groups#customize_group_file"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
			<table class="defaultTable">
				<c:choose><c:when test="${isInsert}">
					<tr>
						<td class="label" width="20%"><bean:message key="customizedFile.select.type"/></td>
						<td>
							<html:select styleId="typeSelect" property="file(type)">
								<c:forEach var="current" items="${types}">
									<html:option value="${current}"><bean:message key="customizedFile.type.${current}"/></html:option>
								</c:forEach>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="label" width="20%"><bean:message key="customizedFile.select.name"/></td>
						<td>
							<html:select styleId="fileSelect" property="file(name)"/>
						</td>
					</tr>
				</c:when><c:otherwise>
					<tr>
						<td class="label" width="20%"><bean:message key="customizedFile.type"/></td>
						<td>
							<html:hidden property="file(type)"/>
							<input id="fileType" value="<bean:message key="customizedFile.type.${file.type}"/>" class="full InputBoxDisabled" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="label" width="20%"><bean:message key="customizedFile.name"/></td>
						<td>
							<html:hidden property="file(name)"/>
							<input id="fileName" value="${file.name}" class="full InputBoxDisabled" readonly="readonly"/>
						</td>
					</tr>
				</c:otherwise></c:choose>
				<tr>
					<td class="label" valign="top"><bean:message key="customizedFile.contents"/></td>
					<td><html:textarea styleId="contents" property="file(contents)" value="${file.contents}" rows="20" styleClass="full InputBoxDisabled" readonly="true"/></td>
				</tr>

				<c:if test="${editable}">
					<tr>
						<td colspan="2" align="right">
				       		<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">
				       		&nbsp;
							<input type="submit" id="saveButton" value="<bean:message key="global.submit"/>" class="ButtonDisabled" disabled>
						</td>
					</tr>
				</c:if>

			</table>
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left"><input id="backButton" type="button" class="button" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
</ssl:form>