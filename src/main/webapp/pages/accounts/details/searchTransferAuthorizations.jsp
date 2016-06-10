<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/details/searchTransferAuthorizations.js" />

<ssl:form method="POST" action="${formAction}">
<html:hidden property="memberId" />
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="transferAuthorization.title.list${empty by ? '' : '.of'}" arg0="${by.name}"/></td>
		<cyclos:help page="payments#transfer_authorizations_by_${isAdmin ? 'admin' : 'member'}"/>
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<cyclos:layout columns="4" className="defaultTable">

				<c:if test="${not empty transferTypes}">
					<cyclos:cell className="label" width="20%"><bean:message key="transfer.type"/></cyclos:cell>
					<cyclos:cell nowrap="nowrap" colspan="3">
						<html:select property="query(transferType)">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach var="tt" items="${transferTypes}">
								<html:option value="${tt.id}">${tt.name}</html:option>
							</c:forEach>
						</html:select>
					</cyclos:cell>
				</c:if>

				<cyclos:cell className="label" width="20%"><bean:message key="transferAuthorization.action"/></cyclos:cell>
				<cyclos:cell nowrap="nowrap">
					<html:select property="query(action)">
						<html:option value=""><bean:message key="global.search.all"/></html:option>
						<c:forEach var="action" items="${actions}">
							<html:option value="${action}"><bean:message key="transferAuthorization.action.${action}"/></html:option>
						</c:forEach>
					</html:select>
				</cyclos:cell>
				<cyclos:rowBreak/>

				<c:if test="${isAdmin && empty by}">
					<cyclos:cell className="label" width="20%"><bean:message key="admin.adminUsername"/></cyclos:cell>
					<cyclos:cell>
						<html:hidden styleId="adminId" property="query(by)"/>
						<input id="adminUsername" class="full" value="${query.by.username}">
						<div id="adminsByUsername" class="autoComplete"></div>
					</cyclos:cell>
					<cyclos:cell className="label" width="20%"><bean:message key="admin.adminName"/></cyclos:cell>
					<cyclos:cell>
						<input id="adminName" class="full" value="${query.by.name}">
						<div id="adminsByName" class="autoComplete"></div>
					</cyclos:cell>
				</c:if>
				
				<c:if test="${empty by}">
					<cyclos:cell className="label" width="20%"><bean:message key="member.username"/></cyclos:cell>
					<cyclos:cell>
						<html:hidden styleId="memberId" property="query(member)"/>
						<input id="memberUsername" class="full" value="${query.member.username}">
						<div id="membersByUsername" class="autoComplete"></div>
					</cyclos:cell>
					<cyclos:cell className="label" width="20%"><bean:message key="member.memberName"/></cyclos:cell>
					<cyclos:cell>
						<input id="memberName" class="full" value="${query.member.name}">
						<div id="membersByName" class="autoComplete"></div>
					</cyclos:cell>
				</c:if>
				
				<cyclos:cell className="label"><bean:message key="accountHistory.period.begin"/></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text property="query(period).begin" styleClass="date small"/></cyclos:cell>
				<cyclos:cell className="label"><bean:message key="accountHistory.period.end"/></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text property="query(period).end" styleClass="date small"/></cyclos:cell>
				
   				<tr>
   					<td align="right" colspan="4"><input type="submit" class="button" value="<bean:message key="global.search"/>"></td>
   				</tr>
			</cyclos:layout>
		</td>
	</tr>
</table>
</ssl:form>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		<cyclos:help page="payments#transfers_authorizations_result"/>
	</tr>
	<tr>
		<td colspan="2" align="left">
			<table class="defaultTable">
				<tr>
					<th class="tdHeaderContents" width="15%"><bean:message key="transferAuthorization.date"/></th>
					<th class="tdHeaderContents" width="20%"><bean:message key="transfer.from"/></th>
					<th class="tdHeaderContents" width="20%"><bean:message key="transfer.to"/></th>
					<th class="tdHeaderContents" width="20%"><bean:message key="transfer.amount"/></th>
					<th class="tdHeaderContents" width="20%"><bean:message key="transferAuthorization.action"/></th>
					<th class="tdHeaderContents" width="5%">&nbsp;</th>
				</tr>
				<c:forEach var="authorization" items="${authorizations}">
					<c:set var="transfer" value="${authorization.transfer}"/>
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td align="center"><cyclos:format date="${authorization.date}"/></td>
						<td align="left">
							<c:choose><c:when test="${transfer.fromSystem}">
								${transfer.from.ownerName}
							</c:when><c:otherwise>
								<cyclos:profile elementId="${transfer.from.member.id}"/>
							</c:otherwise></c:choose>
						</td>
						<td align="left">
							<c:choose><c:when test="${transfer.toSystem}">
								${transfer.to.ownerName}
							</c:when><c:otherwise>
								<cyclos:profile elementId="${transfer.to.member.id}"/>
							</c:otherwise></c:choose>
						</td>
						<td align="left"><cyclos:format number="${transfer.amount}" /></td>
						<td align="center"><bean:message key="transferAuthorization.action.${authorization.action}" /></td>
						<td align="center"><img transferId="${transfer.id}" class="view" src="<c:url value="/pages/images/view.gif"/>"></td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<c:if test="${not empty by}">
			<td><input type="button" class="button" id="backButton" value="<bean:message key="global.back" />"/></td>
		</c:if>
		<td align="right"><cyclos:pagination items="${authorizations}"/></td>
	</tr>
</table>
