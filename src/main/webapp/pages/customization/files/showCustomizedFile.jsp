<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:set var="type" value="${param.type}"/>
<c:set var="fileName" value="${param.fileName}"/>
<c:set var="groupId" value="${param.groupId}"/>
<c:set var="groupFilterId" value="${param.groupFilterId}"/>
<c:choose>
	<c:when test="${type == 'STATIC_FILE'}">
		<c:set var="type" value="static"/>
	</c:when>
	<c:when test="${type == 'HELP'}">
		<c:set var="type" value="help"/>
	</c:when>
</c:choose>
<table class="defaultTable">
	<tr>
		<td align="center" valign="top">

			<table class="defaultTableContent" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tdHeaderTable" width="50%" nowrap="nowrap" height="16px"><bean:message key="customizedFile.title.preview" arg0="${fileName}"/></td>
					<td class="tdHelpIcon">&nbsp;</td>
				</tr>
				<tr>
			 		<td colspan="2">
			 			<cyclos:includeCustomizedFile type="${type}" name="${fileName}" groupId="${groupId}" groupFilterId="${groupFilterId}"/>
					</td>
				</tr>
			</table>
			
			<input type="button" onclick="window.close();" class="button" value="<bean:message key="global.close"/>">
		</td>
	</tr>
</table>
