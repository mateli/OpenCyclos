<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/loans/searchLoanPayments.js" />
<ssl:form method="post" action="${formAction}">
<c:if test="${not empty singleTransferType}">
	<html:hidden property="query(transferType)" value="${singleTransferType.id}" />
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="loanPayment.title.search"/></td>
        <cyclos:help page="loans#search_loan_payments"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
        	<cyclos:layout columns="4">
				<cyclos:cell className="label" width="20%"><bean:message key='loanPayment.status'/></cyclos:cell>
				<cyclos:cell width="30%">
					<html:hidden property="query(statusList)" />
					<cyclos:multiDropDown name="query(statusList)" emptyLabelKey="global.search.all">
						<c:forEach var="stat" items="${status}">
							<c:set var="label"><bean:message key="loanPayment.status.${stat}"/></c:set>
							<cyclos:option value="${stat}" text="${label}" selected="${cyclos:contains(query.statusList, stat)}" />
						</c:forEach>
					</cyclos:multiDropDown>
				</cyclos:cell>
				
				<c:if test="${empty singleTransferType}">
					<cyclos:cell className="label" width="20%"><bean:message key='transfer.type'/></cyclos:cell>
					<cyclos:cell width="30%">
						<html:select property="query(transferType)" styleId="transferTypeSelect" styleClass="full InputBoxEnabled">
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

				<c:if test="${fn:length(loanGroups) > 0}">
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

			    <cyclos:rowBreak/>

				<cyclos:cell className="nestedLabel"><span class="label"><bean:message key='loan.expirationDate'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(expirationPeriod).begin"/></cyclos:cell>
				<cyclos:cell className="label"><bean:message key='global.range.to'/></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(expirationPeriod).end"/></cyclos:cell>

				<cyclos:cell className="nestedLabel"><span class="label"><bean:message key='loan.paymentDate'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(repaymentPeriod).begin"/></cyclos:cell>
				<cyclos:cell className="label"><bean:message key='global.range.to'/></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(repaymentPeriod).end"/></cyclos:cell>
				
				<cyclos:cell colspan="4" align="right">
					<input class="button" type="submit" value="<bean:message key='global.search'/>">
				</cyclos:cell>
            </cyclos:layout>
        </td>
    </tr>
</table>
</ssl:form>

<c:if test="${not empty loanPayments}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key='global.searchResults'/></td>
        <td class="tdHelpIcon" align="right" width="15%" valign="middle">
			<img class="exportCSV" src="<c:url value="/pages/images/save.gif"/>" border="0">
       		<img class="print" src="<c:url value="/pages/images/print.gif" />" border="0" width="16" height="16">
       		<cyclos:help page="loans#search_loan_payments_result" td="false"/>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<c:forEach var="field" items="${loanFieldsForResults}">
	                	<th class="tdHeaderContents">${field.name}</th>
                	</c:forEach>
                   	<th class="tdHeaderContents"><bean:message key='member.member'/></th>
                    <th width="10%" class="tdHeaderContents"><bean:message key='loan.expirationDate'/></th>
                    <th width="15%" class="tdHeaderContents"><bean:message key='loanPayment.amount'/></th>
                    <th width="15%" class="tdHeaderContents"><bean:message key='loanPayment.status'/></th>
                    <th width="15%" class="tdHeaderContents"><bean:message key='loanPayment.search.repaidAmount'/></th>
                    <th width="15%" class="tdHeaderContents"><bean:message key='loanPayment.search.discardedAmount'/></th>
					<c:forEach var="field" items="${customFieldsForList}">
						<th class="tdHeaderContents">${field.name}</th>
					</c:forEach>
					<th width="5%" class="tdHeaderContents">&nbsp;</th>
                </tr>
				<c:forEach var="payment" items="${loanPayments}">
					<c:set var="loan" value="${payment.loan}"/>
					<c:set var="unitsPattern" value="${loan.transfer.type.from.currency.pattern}" />										
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                	<c:forEach var="field" items="${loanFieldsForResults}">
		                	<td><cyclos:customField collection="${loan.customValues}" textOnly="true" field="${field}" /></td>
	                	</c:forEach>
                		<td align="center"><cyclos:profile elementId="${loan.member.id}"/></td>
	                    <td align="center"><cyclos:format rawDate="${payment.expirationDate}"/></td>
	                    <td align="right" nowrap="nowrap"><cyclos:format number="${payment.amount}" unitsPattern="${unitsPattern}"/></td>
	                    <td align="center">
	                    	<bean:message key="loanPayment.status.${payment.status}"/>
	                    	<c:if test="${cyclos:name(payment.status) == 'REPAID' || cyclos:name(payment.status) == 'DISCARDED'}">
	                    		<div style="white-space:nowrap">
		                    		(<cyclos:format date="${payment.repaymentDate}"/>)
	                    		</div>
	                    	</c:if>
	                    </td>
	                    <td align="right" nowrap="nowrap"><cyclos:format number="${payment.repaidAmount}" unitsPattern="${unitsPattern}"/></td>
	                    <td align="right" nowrap="nowrap"><cyclos:format number="${payment.discardedAmount}" unitsPattern="${unitsPattern}"/></td>
						<c:forEach var="field" items="${customFieldsForList}">
							<td><cyclos:customField field="${field}" collection="${payment.loan.transfer.customValues}" textOnly="true" /></td>
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

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right"><cyclos:pagination items="${loanPayments}"/></td>
	</tr>
</table>
</c:if>
