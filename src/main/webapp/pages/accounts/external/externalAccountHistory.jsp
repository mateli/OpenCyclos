<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/external/externalAccountHistory.js" />
<script>
	var removeConfirmation = "<cyclos:escapeJS><bean:message key="externalAccountHistory.action.confirmDelete"/></cyclos:escapeJS>";
	var nothingSelectedMessage = "<cyclos:escapeJS><bean:message key="global.error.nothingSelected"/></cyclos:escapeJS>";
</script>

<ssl:form method="POST" action="${formAction}">
<html:hidden styleId="accountId" property="externalAccountId" value="${externalAccount.id}"/>
<html:hidden styleId="importId" property="transferImportId" value="${empty transferImport ? 0 : transferImport.id}"/>
<html:hidden property="query(account)"/>
<html:hidden property="query(transferImport)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable">
			<c:choose>
				<c:when test="${empty transferImport}"><bean:message key="externalAccountHistory.title" arg0="${externalAccount.name}"/></c:when>				
				<c:otherwise>
					<c:set var="importDate"><cyclos:format dateTime="${transferImport.date}"/></c:set>
					<bean:message key="externalAccountHistory.transferImport.title" arg0="${externalAccount.name}" arg1="${importDate}"/>
				</c:otherwise>
			</c:choose>						
		</td>
		<cyclos:help page="bookkeeping#external_account_history"/>
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<table class="defaultTable">
				<tr>
					<td class="label"><bean:message key="externalTransfer.type"/></td>
					<td>
						<html:select property="query(type)">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach var="type" items="${types}">
								<html:option value="${type.id}">${type.name}</html:option>
							</c:forEach>
						</html:select>
					</td>
					<td class="label"><bean:message key="externalTransfer.status"/></td>
					<td>
						<html:select property="query(status)">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach var="status" items="${statusList}">
								<html:option value="${status}"><bean:message key="externalTransfer.status.${status}"/></html:option>
							</c:forEach>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="member.username"/></td>
					<td>
						<html:hidden styleId="memberId" property="query(member)"/>
						<input id="memberUsername" class="full" value="${query.member.username}">
						<div id="membersByUsername" class="autoComplete"></div>
					</td>
					<td class="label"><bean:message key="member.memberName"/></td>
					<td>
						<input id="memberName" class="full" value="${query.member.name}">
						<div id="membersByName" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label" width="20%"><bean:message key="externalAccountHistory.amountRange.begin"/></td>
					<td nowrap="nowrap"><html:text property="query(initialAmount)" styleClass="number small"/></td>
					<td class="label" width="20%"><bean:message key="externalAccountHistory.amountRange.end"/></td>
					<td nowrap="nowrap"><html:text property="query(finalAmount)" styleClass="number small"/></td>
				</tr>
				<tr>
					<td class="label" width="20%"><bean:message key="externalAccountHistory.period.begin"/></td>
					<td nowrap="nowrap"><html:text property="query(period).begin" styleClass="date small"/></td>
					<td class="label" width="20%"><bean:message key="externalAccountHistory.period.end"/></td>
					<td nowrap="nowrap"><html:text property="query(period).end" styleClass="date small"/></td>
				</tr>
				<tr>
					<td align="left" colspan="2">
						<input id="backButton" type="button" class="button" value="<bean:message key="global.back"/>">
					</td>
					<td colspan="2" align="right"><input type="submit" class="button" value="<bean:message key="global.search"/>"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</ssl:form>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left"><input id="toImportListButton" class="button" type="button" value="<bean:message key="externalAccountHistory.button.toImportList"/>"></td>
		<td align="right">
			<c:if test="${isProcess}">
				<input id="processPaymentsButton" class="button" type="button" value="<bean:message key="externalAccountHistory.button.processPayments"/>">
			</c:if>
			<c:if test="${empty transferImport}">
				<input id="newPaymentButton" class="button" type="button" value="<bean:message key="externalAccountHistory.button.newPayment"/>">
			</c:if>
		</td>		
	</tr>
</table>
<br/>
<c:choose><c:when test="${empty externalTransfers}">
	<div class="footerNote" helpPage="bookkeeping#external_transfers_history_result"><bean:message key="externalAccountHistory.search.noResults"/></div>
</c:when><c:otherwise>
	<ssl:form action="${actionPrefix}/changeExternalTransfer" styleId="actionsForm">
	<html:hidden property="externalAccountId" value="${externalAccount.id}"/>
	<html:hidden property="transferImportId" value="${empty transferImport ? 0 : transferImport.id}"/>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
			<cyclos:help page="bookkeeping#external_transfers_history_result"/>
		</tr>
		<tr>
			<td colspan="2" align="left">
				<table class="defaultTable">
					<tr>
						<th class="tdHeaderContents" width="16">&nbsp;</th>
						<th class="tdHeaderContents" width="16">&nbsp;</th>
						<th class="tdHeaderContents"><bean:message key="externalTransfer.date"/></th>
						<th class="tdHeaderContents"><bean:message key="externalTransfer.member"/></th>
						<th class="tdHeaderContents"><bean:message key="externalTransfer.type"/></th>
						<th class="tdHeaderContents"><bean:message key="externalTransfer.amount"/></th>
						<th class="tdHeaderContents" width="16">&nbsp;</th>
						<th class="tdHeaderContents" width="16">&nbsp;</th>
					</tr>
					<c:forEach var="externalTransfer" items="${externalTransfers}">
						<c:set var="summaryStatus" value="${externalTransfer.summaryStatus}" />
						<c:set var="tooltip"><cyclos:escapeJS><bean:message key="externalTransfer.status.${summaryStatus}"/></cyclos:escapeJS></c:set>
						<c:set var="editableTransfer" value="${cyclos:name(externalTransfer.status) == 'PENDING'}"/>
						<c:choose> 
							<c:when test="${cyclos:name(summaryStatus) == 'COMPLETE_PENDING'}">
								<c:set var="statusImage" value="pending.gif"/>
							</c:when>
							<c:when test="${cyclos:name(summaryStatus) == 'INCOMPLETE_PENDING'}">
								<c:set var="statusImage" value="incomplete.gif"/>
							</c:when>
							<c:when test="${cyclos:name(summaryStatus) == 'CHECKED'}">
								<c:set var="statusImage" value="checked.gif"/>
							</c:when>
							<c:when test="${cyclos:name(summaryStatus) == 'PROCESSED'}">
								<c:set var="statusImage" value="conciliated.gif"/>
							</c:when>
						</c:choose>
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<c:if test="${not empty possibleActions}">
								<td align="center" valign="middle">
									<c:if test="${cyclos:name(externalTransfer.status) != 'PROCESSED'}">
										<input type="checkbox" name="externalTransferId" class="checkbox" value="${externalTransfer.id}">
									</c:if>
								</td>
							</c:if>
							<td align="center"><img title="${tooltip}" src="<c:url value="/pages/images/${statusImage}"/>"></td>
							<td align="center"><cyclos:format rawDate="${externalTransfer.date}"/></td>
							<td align="center"><cyclos:profile elementId="${externalTransfer.member.id}" pattern="username" /></td>
							<td align="left">${externalTransfer.type.name}</td>
							<td align="center"><cyclos:format number="${externalTransfer.amount}" forceSignal="true"/></td>
							<td align="center">
								<c:choose><c:when test="${editableTransfer && isManage}">
									<img externalTransferId="${externalTransfer.id}" class="edit" src="<c:url value="/pages/images/edit.gif"/>">
								</c:when><c:otherwise>
									<img externalTransferId="${externalTransfer.id}" class="view" src="<c:url value="/pages/images/view.gif"/>">
								</c:otherwise></c:choose>
							</td>
							<td align="center">
								<c:if test="${editableTransfer}">
									<img externalTransferId="${externalTransfer.id}" class="delete" src="<c:url value="/pages/images/delete.gif"/>">
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
	
	<c:if test="${not empty possibleActions}">
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td nowrap="nowrap">
					<input id="selectAllButton" type="button" class="button" value="<bean:message key="global.selectAll"/>">
					<input id="selectNoneButton" type="button" class="button" value="<bean:message key="global.selectNone"/>">
					<br><br class="small">
					<select name="action" id="applyActionSelect">
						<option value=""><bean:message key="externalTransfer.action.choose"/></option>
						<c:forEach var="action" items="${possibleActions}">
							<option value="${action}"><bean:message key="externalTransfer.action.${action}"/></option>
						</c:forEach>
					</select>
				</td>
				<td align="right" valign="top"><cyclos:pagination items="${externalTransfers}"/></td>
			</tr>
		</table>
	</c:if> 
	
	</ssl:form>
	<br/>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key="externalAccountHistory.title.summary"/></td>
			<cyclos:help page="bookkeeping#external_transfers_history_summary"/>
		</tr>
		<tr>
			<td colspan="2" align="left">
				<table class="defaultTable">
					<tr>
						<th class="tdHeaderContents" width="60%"><bean:message key="externalTransfer.status"/></th>
						<th class="tdHeaderContents" width="20%"><bean:message key="global.count"/></th>
						<th class="tdHeaderContents" width="20%"><bean:message key="global.totalAmount"/></th>
					</tr>
					<c:forEach var="summaryStatus" items="${summary}">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="left"><bean:message key="externalTransfer.status.${summaryStatus.key}"/></td>
							<td align="center">${summaryStatus.value.count}</td>
							<td align="center"><cyclos:format number="${summaryStatus.value.amount}"/></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</c:otherwise></c:choose>