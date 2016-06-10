<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<table class="defaultTable">
	<tr>
		<td class="tdHeaderContents" width="15%"><bean:message key="reference.date"/></td>
		<td class="tdHeaderContents"><bean:message key="member.username"/></td>
		<td class="tdHeaderContents"><bean:message key="member.name"/></td>
		<td class="tdHeaderContents" nowrap="nowrap"><bean:message key="reference.level"/></td>
		<td width="5%" class="tdHeaderContents">&nbsp;</td>
    </tr>

	<c:forEach var="current" items="${references}">
		<c:set var="relatedMember" value="${isGiven ? current.to : current.from}" />
		<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			<td align="center"><cyclos:format date="${current.date}"/></td>
			<td align="left"><cyclos:profile elementId="${relatedMember.id}" pattern="username"/></td>
			<td align="left"><cyclos:profile elementId="${relatedMember.id}" pattern="name"/></td>
			<td align="center" nowrap="nowrap"><bean:message key="reference.level.${current.level}"/></td>
			<td align="center" nowrap="nowrap">
				<c:choose><c:when test="${canManage}">
					<img referenceId="${current.id}" class="edit details" src="<html:rewrite page="/pages/images/edit.gif" />" border="0"/>
					<img referenceId="${current.id}" class="remove" src="<html:rewrite page="/pages/images/delete.gif" />" border="0"/>
				</c:when><c:otherwise>
					<img referenceId="${current.id}" class="view details" src="<html:rewrite page="/pages/images/view.gif" />" border="0"/>
				</c:otherwise></c:choose>
			</td>
		</tr>
	</c:forEach>	                
</table>
