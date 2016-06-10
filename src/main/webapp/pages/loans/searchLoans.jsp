<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/loans/searchLoans.js" />
<c:choose>
	<c:when test="${not empty loanGroup}">
		<c:set var="titleKey" value="loan.title.search.group"/>
		<c:set var="helpPage" value="loan_groups#search_loans"/>
	</c:when>
	<c:when test="${isAdmin}">
		<c:set var="titleKey" value="${empty member ? 'loan.title.search' : 'loan.title.search.of'}"/>
		<c:set var="helpPage" value="${empty member ? 'loans#search_loans_by_admin' : 'loans#search_loans_member_by_admin'}"/>
	</c:when>
	<c:when test="${myLoans}">
		<c:set var="titleKey" value="loan.title.search.${isOperator ? 'of' : 'my'}"/>
		<c:set var="helpPage" value="loans#search_loans_by_member"/>
	</c:when>
	<c:when test="${byBroker}">
		<c:set var="titleKey" value="loan.title.search.of"/>
		<c:set var="helpPage" value="loans#search_loans_member_by_broker"/>
	</c:when>
</c:choose>

<ssl:form method="post" action="${formAction}">
<html:hidden property="loanGroupId"/>
<html:hidden property="memberId"/>
<c:if test="${not empty singleTransferType}">
	<html:hidden property="query(transferType)" value="${singleTransferType.id}" />
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key='${titleKey}' arg0="${empty member ? loanGroup.name : member.name}"/></td>
        <cyclos:help page="${helpPage}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
        	<cyclos:layout columns="4">
            	<c:choose><c:when test="${not fullQuery}">
            	
					<cyclos:cell colspan="4" align="right">
						<span class="label" style="vertical-align:middle"><bean:message key='loan.status'/></span>
						<c:forEach var="stat" items="${status}">
							<label>
								<html:radio property="query(status)" styleClass="statusRadio radio" style="vertical-align:middle" value="${stat}"></html:radio>
								<span style="vertical-align:middle"><bean:message key="loan.status.${stat}"/></span>
							</label>
							&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</cyclos:cell>
					
				</c:when><c:otherwise>

					<cyclos:cell className="label" width="20%"><bean:message key='loan.search.status'/></cyclos:cell>
					<cyclos:cell width="30%">
						<html:select property="query(queryStatus)" styleClass="full">
							<html:option value=""><bean:message key="global.search.all.male"/></html:option>
							<c:forEach var="stat" items="${queryStatus}">
								<html:option value="${stat}"><bean:message key="loan.queryStatus.${stat}"/></html:option>
							</c:forEach>
						</html:select>
					</cyclos:cell>

					<c:if test="${empty singleTransferType}">
						<cyclos:cell className="label" width="20%"><bean:message key='loan.type'/></cyclos:cell>
						<cyclos:cell width="30%">
							<html:select styleId="transferTypeSelect" property="query(transferType)" styleClass="full InputBoxEnabled">
								<html:option value=""><bean:message key='global.search.all'/></html:option>
								<c:forEach var="tt" items="${transferTypes}">
									<html:option value="${tt.id}">${tt.name}</html:option>
								</c:forEach>
							</html:select>
						</cyclos:cell>
					</c:if>
					
				    <c:forEach var="entry" items="${loanFieldValues}">
				        <c:set var="field" value="${entry.field}"/>
				        <c:set var="value" value="${entry.value.value}"/>
			            <cyclos:cell className="label">${field.name}</cyclos:cell>
			   			<cyclos:cell nowrap="nowrap">
			   				<cyclos:customField field="${field}" value="${value}" search="true" valueName="query(loanValues).value" fieldName="query(loanValues).field"/>
						</cyclos:cell>
				    </c:forEach>
				    <c:forEach var="entry" items="${memberFieldValues}">
				        <c:set var="field" value="${entry.field}"/>
				        <c:set var="value" value="${entry.value.value}"/>
			            <cyclos:cell className="label">${field.name}</cyclos:cell>
			            <cyclos:cell nowrap="nowrap">
							<cyclos:customField field="${field}" value="${value}" search="true" valueName="query(memberValues).value" fieldName="query(memberValues).field"/>
						</cyclos:cell>
				    </c:forEach>

					<cyclos:rowBreak/>
					
					<cyclos:cell className="label"><bean:message key="member.username"/></cyclos:cell>
					<cyclos:cell>
						<html:hidden styleId="memberId" property="query(member)"/>
						<input id="memberUsername" class="full" value="${query.member.username}">
						<div id="membersByUsername" class="autoComplete"></div>
					</cyclos:cell>
					<cyclos:cell className="label"><bean:message key="member.memberName"/></cyclos:cell>
					<cyclos:cell>
						<input id="memberName" class="full" value="${query.member.name}">
						<div id="membersByName" class="autoComplete"></div>
					</cyclos:cell>
					
					<cyclos:cell className="label"><bean:message key="member.brokerUsername"/></cyclos:cell>
					<cyclos:cell>
						<html:hidden styleId="brokerId" property="query(broker)"/>
						<input id="brokerUsername" class="full" value="${query.broker.username}">
						<div id="brokersByUsername" class="autoComplete"></div>
					</cyclos:cell>
					<cyclos:cell className="label"><bean:message key="member.brokerName"/></cyclos:cell>
					<cyclos:cell>
						<input id="brokerName" class="full" value="${query.broker.name}">
						<div id="brokersByName" class="autoComplete"></div>
					</cyclos:cell>

					<c:if test="${not empty loanGroups}">
						<cyclos:cell className="label"><bean:message key='loan.group'/></cyclos:cell>
						<cyclos:cell>
							<html:select property="query(loanGroup)" styleClass="full InputBoxEnabled">
								<html:option value=""><bean:message key='global.search.all'/></html:option>
								<c:forEach var="loanGroup" items="${loanGroups}">
									<html:option value="${loanGroup.id}">${loanGroup.name}</html:option>
								</c:forEach>
	         				</html:select>
		          		</cyclos:cell>
	          		</c:if>
					<c:if test="${not empty localSettings.transactionNumber}">
						<cyclos:cell className="label"><bean:message key="transfer.transactionNumber"/></cyclos:cell>
						<cyclos:cell><html:text style="width:100%" property="query(transactionNumber)"/></cyclos:cell>
					</c:if>

				    <cyclos:rowBreak/>

					<cyclos:cell className="nestedLabel"><span class="label"><bean:message key='loan.grantDate'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(grantPeriod).begin"/></cyclos:cell>
					<cyclos:cell className="label"><bean:message key='global.range.to'/></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(grantPeriod).end"/></cyclos:cell>
					
					<cyclos:cell className="nestedLabel"><span class="label"><bean:message key='loan.expirationDate'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(expirationPeriod).begin"/></cyclos:cell>
					<cyclos:cell className="label"><bean:message key='global.range.to'/></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(expirationPeriod).end"/></cyclos:cell>
					
					<cyclos:cell className="nestedLabel"><span class="label"><bean:message key='loan.paymentDate'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(paymentPeriod).begin"/></cyclos:cell>
					<cyclos:cell className="label"><bean:message key='global.range.to'/></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(paymentPeriod).end"/></cyclos:cell>
					
					<cyclos:cell colspan="4" align="right">
						<input class="button" type="submit" value="<bean:message key='global.search'/>">
					</cyclos:cell>
				</c:otherwise></c:choose>
            </cyclos:layout>
        </td>
    </tr>
</table>
</ssl:form>

<c:if test="${not empty loans}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key='global.searchResults'/></td>
        <td class="tdHelpIcon" align="right" width="15%" valign="middle">
			<img class="exportCSV" src="<c:url value="/pages/images/save.gif"/>" border="0">
       		<img class="print" src="<c:url value="/pages/images/print.gif" />" border="0" width="16" height="16">
       		<cyclos:help page="loans#search_loans_result" td="false"/>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<c:if test="${fullQuery && empty searchLoansForm.query.member}">
                    	<th width="15%" class="tdHeaderContents"><bean:message key='member.member'/></th>
                    </c:if>
                    <th class="tdHeaderContents"><bean:message key='loan.description'/></th>
                    <th width="10%" class="tdHeaderContents"><bean:message key='loan.amount'/></th>
                    <th width="10%" class="tdHeaderContents"><bean:message key='loan.remainingAmount'/></th>
                    <c:if test="${isMultiPayment}">
	                    <th width="10%" class="tdHeaderContents"><bean:message key='loan.payments'/></th>
                    </c:if>
					<c:forEach var="field" items="${customFieldsForList}">
						<th class="tdHeaderContents">${field.name}</th>
					</c:forEach>
                    <th class="tdHeaderContents" width="5%">&nbsp;</th>
                </tr>
				<c:forEach var="loan" items="${loans}">
					<c:set var="unitsPattern" value="${loan.transfer.type.from.currency.pattern}" />
					<c:set var="closedPaymentsCount" value="${loan.closedPaymentsCount}" />
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                	<c:if test="${fullQuery && empty searchLoansForm.query.member}">
	                		<td align="center"><cyclos:profile elementId="${loan.member.id}"/></td>
	                	</c:if>
	                    <td align="left"><cyclos:truncate value="${loan.transfer.description}"/></td>
	                    <td align="right" nowrap="nowrap"><cyclos:format number="${loan.totalAmount}" unitsPattern="${unitsPattern}"/></td>
	                    <td align="right" nowrap="nowrap"><cyclos:format number="${loan.remainingAmount}" unitsPattern="${unitsPattern}"/></td>
	                    <c:if test="${isMultiPayment}">
		                    <td align="center" nowrap="nowrap">
	                    		${closedPaymentsCount} /
		                    	${loan.paymentCount}
		                    </td>
		                </c:if>
						<c:forEach var="field" items="${customFieldsForList}">
							<td><cyclos:customField field="${field}" collection="${loan.transfer.customValues}" textOnly="true" /></td>
						</c:forEach>
	                    <td align="center">
	                    	<img loanId="${loan.id}" src="<c:url value="/pages/images/view.gif"/>" class="details view">
	                    </td>
	                 </tr>
				</c:forEach>
            </table>
        </td>
    </tr>
</table>

</c:if>
<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<c:if test="${not fullQuery and not myLoans}">
			    <input type="button" class="button" id="backButton" value="<bean:message key='global.back'/>">
			</c:if>
		</td>
		<td align="right"><cyclos:pagination items="${loans}"/></td>
	</tr>
</table>
