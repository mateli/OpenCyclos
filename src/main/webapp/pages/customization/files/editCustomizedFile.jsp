<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<cyclos:script src="/pages/customization/files/editCustomizedFile.js" />

<script>
	var type = "${file.type}";
	var isTransient = ${file['transient']};
	var chooseLabel = "<cyclos:escapeJS><bean:message key='global.choose'/></cyclos:escapeJS>";
</script>

<c:choose>
	<c:when test="${type == 'STATIC_FILE'}">
		<c:set var="titleKey" value="customizedFile.title.customize.static"/>
		<c:set var="helpPage" value="content_management#edit_customized_file"/>
	</c:when>
	<c:when test="${type == 'HELP'}">
		<c:set var="titleKey" value="customizedFile.title.customize.help"/>
		<c:set var="helpPage" value="content_management#edit_customized_file"/>
	</c:when>
	<c:when test="${type == 'STYLE'}">
		<c:set var="titleKey" value="customizedFile.title.customize.css"/>
		<c:set var="helpPage" value="content_management#edit_customized_file"/>
	</c:when>
	<c:when test="${type == 'APPLICATION_PAGE'}">
		<c:set var="titleKey" value="customizedFile.title.customize.page"/>
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${file['transient']}">
		<c:set var="helpPage" value="content_management#edit_new_customized_file"/>
	</c:when>
	<c:otherwise>
		<c:set var="helpPage" value="content_management#edit_customized_file"/>
	</c:otherwise>
</c:choose>

<ssl:form method="post" action="${formAction}" >
<html:hidden property="file(id)" />
<html:hidden property="file(type)"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
        <cyclos:help page="${helpPage}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
			<table class="defaultTable">
				<c:if test="${not empty file.newContents}">
					<tr>
						<td align="center" colspan="2"><br class='small'/><cyclos:escapeHTML brOnly="true"><bean:message key="customizedFile.newContentsNotification"/></cyclos:escapeHTML><br class='small'/><br class='small'/></td>
					</tr>
				</c:if>
				<c:choose>
					<c:when test="${type == 'APPLICATION_PAGE'}">
						
						<c:choose><c:when test="${file['transient']}">
							<tr>
								<td class="label" width="20%"><bean:message key="customizedFile.path"/></td>
								<td><input type="text" name="filePath" value="${file.relativePath != null ? file.relativePath : "/" }" class="full InputBoxDisabled"/></td>
							</tr>
							<tr>
								<td class="label" width="20%"><bean:message key="customizedFile.select.name"/></td>
								<td>	
									<html:hidden property="file(name)"/>
									<select id="contentSelect" name="contentSelect">
										<option value=""><bean:message key="global.choose"/></option>
									</select>
									<span id="upLevelLink">&nbsp;<a class="default" href="javascript:upLevel()"><bean:message key="global.up"/></a></span>
								</td>
							</tr>
						</c:when><c:otherwise>
							<tr>
								<td class="label" width="20%"><bean:message key="customizedFile.path"/></td>
								<td>	
									<html:text property="file(name)" styleClass="full InputBoxDisabled"/>
								</td>
							</tr>
						</c:otherwise></c:choose>
					</c:when>
					<c:otherwise>
						<c:choose><c:when test="${file['transient']}">
							<tr>
								<td class="label" width="20%"><bean:message key="customizedFile.select.name"/></td>
								<td>
									<html:select styleId="fileSelect" property="file(name)">
										<c:forEach var="current" items="${filesNotYetCustomized}">
											<html:option value="${current}">${current}</html:option>
										</c:forEach>
									</html:select>
								</td>
							</tr>
						</c:when><c:otherwise>
							<tr>
								<td class="label" width="20%"><bean:message key="customizedFile.name"/></td>
								<td>
									<html:hidden property="file(name)"/>
									<input id="fileName" value="${file.name}" class="full InputBoxDisabled" readonly/>
								</td>
							</tr>
						</c:otherwise></c:choose>
					</c:otherwise>
				</c:choose>
				<tr>
					<td class="label" valign="top"><bean:message key="customizedFile.contents"/></td>
					<td><html:textarea styleId="contents" property="file(contents)" value="${file.contents}" rows="20" styleClass="full InputBoxDisabled" readonly="true"/></td>
				</tr>
				<c:if test="${not empty file.newContents}">
					<tr>
						<td class="label" valign="top"><bean:message key="customizedFile.newContents"/></td>
						<td><html:textarea styleId="contents" property="file(newContents)" rows="20" styleClass="full InputBoxDisabled" readonly="true"/></td>
					</tr>
				</c:if>
				<c:if test="${file.persistent and not emptyfile.originalContents}">
				<tr id="showOriginalContents">
					<td class="label" valign="top"><bean:message key="customizedFile.originalContents"/></td>
					<td>
						&nbsp;<a class="default" href="javascript:showOriginalContents(true)"><bean:message key="global.show"/></a>
					</td>
				</tr>
				
				<tr id="originalContents" style="display:none">
					<td class="label" valign="top"><bean:message key="customizedFile.originalContents"/></td>
					<td>
						&nbsp;<a class="default" href="javascript:showOriginalContents(false)"><bean:message key="global.hide"/></a>
						<html:textarea styleId="contents" property="file(originalContents)" rows="20" styleClass="full InputBoxDisabled" readonly="true"/>
					</td>
				</tr>
				</c:if>
				<tr>
					<td colspan="2" align="right">
						<c:if test="${file.conflict}">
							<input type="checkbox" name="resolveConflicts" id="resolveCheck" class="checkbox" disabled="disabled"/> <bean:message key="customizedFile.resolveConflict"/> &nbsp;
						</c:if>
			       		<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">
			       		&nbsp;
						<input type="submit" id="saveButton" value="<bean:message key="global.submit"/>" class="ButtonDisabled" disabled>
								
					</td>
				</tr>
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