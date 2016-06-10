<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<c:if test="${not empty loggedUser}">

<c:choose><c:when test="${empty tickets}">
	<div align="center" style="font-weight:bold; margin: 10px;">
		<bean:message key="paymentRequest.search.empty" />
	</div>
</c:when><c:otherwise>

	<table class="defaultTable" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderContents"><bean:message key="member.member"/></td>
			<td class="tdHeaderContents"><bean:message key="transfer.amount"/></td>
			<td class="tdHeaderContents"><bean:message key="ticket.status"/></td>
			<c:if test="${empty singleChannel}">
				<td class="tdHeaderContents"><bean:message key="transfer.channel"/></td>
			</c:if>
			<td width="20%" class="tdHeaderContents"><bean:message key="ticket.date"/></td>
			<td class="tdHeaderContents" width="16px">&nbsp;</td>
		</tr>
		<c:forEach var="ticket" items="${tickets}">
			<c:set var="currency" value="${ticket.currency}" />
			<c:if test="${empty currency}">
				<c:set var="currency" value="${ticket.transferType.from.currency}" />
			</c:if>
			<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
				<td><cyclos:profile elementId="${ticket.from.id}"/></td>
				<td><cyclos:format number="${ticket.amount}" unitsPattern="${currency.pattern}" /></td>
				<td><bean:message key="ticket.status.${ticket.status}" /></td>
				<c:if test="${empty singleChannel}">
					<td><c:out value="${ticket.toChannel.displayName}"/></td>
				</c:if>
				<td nowrap="nowrap"><cyclos:format dateTime="${ticket.creationDate}"/></td>
				<td>
					<c:if test="${not empty ticket.transfer.processDate}">
						<img class="printTransfer" transferId="${ticket.transfer.id}" src="<c:url value="/pages/images/print.gif" />">
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div id="paginationContainer" style="display:none"><cyclos:pagination items="${tickets}"/></div>
	<script>
		$$('a.resultLink').each(layoutBehaviour.a);
		$$('img.printTransfer').each(applyPrint);
		$('paginationDisplay').innerHTML = $('paginationContainer').innerHTML;
	</script>
</c:otherwise></c:choose>

</c:if>
