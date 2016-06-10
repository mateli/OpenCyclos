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
		<c:if test="${showAmount}">
			<td class="tdHeaderContents"><bean:message key="transfer.amount"/></td>
		</c:if>
		<td class="tdHeaderContents" width="20%"><bean:message key="member.member"/></td>
		<td class="tdHeaderContents" nowrap="nowrap"><bean:message key="reference.level"/></td>
		<td class="tdHeaderContents"><bean:message key="reference.comments"/></td>
		<td width="5%" class="tdHeaderContents">&nbsp;</td>
    </tr>

	<c:forEach var="current" items="${references}">
		<c:set var="relatedMember" value="${isGiven ? current.to : current.from}" />
		<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			<td valign="top" align="center"><cyclos:format date="${current.date}"/></td>
			<c:if test="${showAmount}">
				<td valign="top" align="center" nowrap="nowrap" class="${isGiven ? 'ClassColorDebit' : 'ClassColorCredit'}"><cyclos:format number="${isGiven ? -current.payment.amount : current.payment.amount}" unitsPattern="${current.payment.type.from.currency.pattern}" /></td>
			</c:if>
			<td valign="top"><cyclos:profile elementId="${relatedMember.id}"/></td>
			<td valign="top" align="center" nowrap="nowrap"><bean:message key="reference.level.${current.level}"/></td>
			<td valign="top">
				<cyclos:truncate value="${current.comments}"/>
				<c:if test="${not empty current.replyComments}">
					<br />
					&rArr; <cyclos:truncate value="${current.replyComments}"/>					
				</c:if>
			</td>
			<td valign="top" align="center" nowrap="nowrap">
				<img referenceId="${current.id}" class="view details" src="<html:rewrite page="/pages/images/view.gif" />" border="0"/>
			</td>
		</tr>
	</c:forEach>	                
</table>
