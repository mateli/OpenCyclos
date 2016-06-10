<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<c:set var="parameters" value="${loan.parameters}"/> 
<script>
	<c:if test="${canPerformExpiredAction}">
		var expiredActionConfirmation = "<cyclos:escapeJS><bean:message key="loan.setExpiredStatus.confirmation"/></cyclos:escapeJS>";
	</c:if>
	var backToSearchLoanPayments = booleanValue("${param.backToSearchLoanPayments}")
	var repaymentTransferTypeId = "${repaymentTransferTypeId}";
</script>

<cyclos:script src="/pages/loans/loanDetails.js" />
<ssl:form styleId="editForm" action="${formAction}" method="post">
	<html:hidden property="loanId"/>
	<html:hidden property="loanPaymentId"/>
	<html:hidden property="memberId"/>
	<html:hidden property="loanGroupId"/>
	<html:hidden property="guaranteeId"/>
</ssl:form>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="loan.title.details"/></td>
        <td class="tdHelpIcon" align="right" width="8%" valign="middle" nowrap="nowrap">
       		<img src="<c:url value="/pages/images/print.gif" />" class="print">
        	<cyclos:help page="loans#loan_detail" td="false"/>
        </td>
    </tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
		<cyclos:layout columns="4">
			<c:choose><c:when test="${loan.toGroup}">
				<cyclos:cell className="headerLabel" width="20%"><bean:message key='loan.group'/></cyclos:cell>
				<cyclos:cell className="headerField" width="30%">${loan.loanGroup.name}</cyclos:cell>
			</c:when><c:otherwise>
				<cyclos:cell className="headerLabel" width="20%"><bean:message key='member.username'/></cyclos:cell>
				<cyclos:cell className="headerField" width="30%">${loan.member.username}</cyclos:cell>
			</c:otherwise></c:choose>
			<cyclos:cell className="headerLabel" width="20%"><bean:message key='loan.status' /></cyclos:cell>
			<cyclos:cell className="headerField" width="30%">
				<c:set var="status" value="${loan.status}" />
				<c:choose>
					<c:when test="${status.relatedToAuthorization}">
						<bean:message key='loan.status.${status}'/>
					</c:when>
					<c:otherwise>
						<c:choose><c:when test="${fn:length(loan.payments) == 1}">
							<bean:message key='loan.status.${loan.payments[0].status}'/>
						</c:when><c:otherwise>
							<bean:message key='loan.status.${status}'/>
						</c:otherwise></c:choose>
					</c:otherwise>
				</c:choose>
			</cyclos:cell>

			<c:choose><c:when test="${loan.toGroup}">
				<cyclos:cell className="headerLabel"><bean:message key='loan.group.responsible'/></cyclos:cell>
				<cyclos:cell className="headerField">${loan.member.username}</cyclos:cell>
				<cyclos:cell className="headerLabel"><bean:message key='member.name' /></cyclos:cell>
				<cyclos:cell className="headerField">${loan.member.name}</cyclos:cell>
			</c:when><c:otherwise>
				<cyclos:cell className="headerLabel"><bean:message key='member.memberName' /></cyclos:cell>
				<cyclos:cell className="headerField">${loan.member.name}</cyclos:cell>
			</c:otherwise></c:choose>
			<cyclos:cell className="headerLabel"><bean:message key='loan.grantDate'/></cyclos:cell>
			<cyclos:cell className="headerField"><cyclos:format date="${loan.grantDate}"/></cyclos:cell>
			
			<cyclos:cell className="headerLabel"><bean:message key='transfer.type'/></cyclos:cell>
			<cyclos:cell className="headerField">${loan.transfer.type.name}</cyclos:cell>
			<cyclos:cell className="headerLabel"><bean:message key='loan.expirationDate' /></cyclos:cell>
			<cyclos:cell className="headerField"><cyclos:format rawDate="${loan.expirationDate}"/></cyclos:cell>

			<cyclos:cell className="headerLabel"><bean:message key='loan.amount' /></cyclos:cell>
			<cyclos:cell className="headerField"><cyclos:format number="${loan.amount}" unitsPattern="${currencyPattern}" /></cyclos:cell>
			<c:choose>
				<c:when test="${cyclos:name(loan.status) == 'CLOSED'}">
					<cyclos:cell className="headerLabel"><bean:message key="loan.repaymentDate"/></cyclos:cell>
					<cyclos:cell className="headerField"><cyclos:format dateTime="${loan.repaymentDate}"/></cyclos:cell>
				</c:when>
				<c:when test="${cyclos:name(loan.status) == 'OPEN'}">
					<cyclos:cell className="headerLabel"><bean:message key="loan.remainingAmount"/></cyclos:cell>
					<cyclos:cell className="headerField"><cyclos:format number="${loan.remainingAmount}" unitsPattern="${currencyPattern}"/></cyclos:cell>
				</c:when>
			</c:choose>

			<c:if test="${cyclos:name(parameters.type) == 'WITH_INTEREST'}">
				<c:if test="${loan.amount != loan.totalAmount}">
					<cyclos:cell className="headerLabel"><bean:message key='loan.totalAmount' /></cyclos:cell>
					<cyclos:cell className="headerField"><cyclos:format number="${loan.totalAmount}" unitsPattern="${currencyPattern}"/></cyclos:cell>
				</c:if>
		    	<c:if test="${not empty parameters.monthlyInterest}">
		            <cyclos:cell className="headerLabel"><bean:message key='loan.monthlyInterest'/></cyclos:cell>
		   			<cyclos:cell className="headerField"><cyclos:format amount="${parameters.monthlyInterestAmount}" unitsPattern="${monthlyInterestPattern}"/></cyclos:cell>
		    	</c:if>
		    	<c:if test="${not empty parameters.grantFee}">
		            <cyclos:cell className="headerLabel"><bean:message key='loan.grantFee'/></cyclos:cell>
		   			<cyclos:cell className="headerField"><cyclos:format amount="${parameters.grantFee}" unitsPattern="${grantFeePattern}"/></cyclos:cell>
		    	</c:if>
		    	<c:if test="${not empty parameters.expirationFee}">
		            <cyclos:cell className="headerLabel"><bean:message key='loan.expirationFee'/></cyclos:cell>
		   			<cyclos:cell className="headerField"><cyclos:format amount="${parameters.expirationFee}" unitsPattern="${expirationFeePattern}"/></cyclos:cell>
		    	</c:if>
		    	<c:if test="${not empty parameters.expirationDailyInterest}">
		            <cyclos:cell className="headerLabel"><bean:message key='loan.expirationDailyInterest'/></cyclos:cell>
		   			<cyclos:cell className="headerField"><cyclos:format amount="${parameters.expirationDailyInterestAmount}" unitsPattern="expirationDailyInterestPattern"/></cyclos:cell>
		    	</c:if>
		    </c:if>
			
		    <c:forEach var="entry" items="${customFields}">
		        <c:set var="field" value="${entry.field}"/>
		        <c:set var="value" value="${entry.value}"/>
		        <c:if test="${not empty value.value}">
		            <cyclos:cell className="headerLabel">${field.name}</cyclos:cell>
		   			<cyclos:cell className="headerField"><cyclos:customField field="${field}" value="${value}" textOnly="true"/></cyclos:cell>
	   			</c:if>
		    </c:forEach>
		    
		    <cyclos:rowBreak/>

			<cyclos:cell className="headerLabel" valign="top"><bean:message key='loan.description'/></cyclos:cell>
			<cyclos:cell className="headerField" colspan="3"><cyclos:escapeHTML>${loan.transfer.description}</cyclos:escapeHTML></cyclos:cell>

			<cyclos:rowBreak/>
		    <c:if test="${showRelatedTransfer}">
				<cyclos:cell colspan="4" align="right" valign="top">
					<a id="openTransferLink" class="default" transferId="${loan.transfer.id}"><bean:message key='loan.openTransfer'/></a>
				</cyclos:cell>
			</c:if>
			<c:if test="${canPerformExpiredAction}">
				<cyclos:cell colspan="4" align="right">
					<ssl:form action="${actionPrefix}/manageLoanExpiredStatus" method="post" styleId="expiredStatusForm">
						<html:hidden property="loanId" value="${loanDetailsForm.loanId}" />
						<html:hidden property="memberId" value="${loanDetailsForm.memberId}" />
						<html:hidden property="loanGroupId" value="${loanDetailsForm.loanGroupId}" />
						<html:hidden property="transactionPassword"/>						
						<html:hidden property="status" />
					</ssl:form>
					<c:if test="${canMarkAsInProcess}">
						<c:set var="statusText"><bean:message key="loan.status.IN_PROCESS"/></c:set>
						<input type="button" class="button setExpiredStatus" status="IN_PROCESS" value="<bean:message key='loan.setExpiredStatus' arg0="${statusText}" />" />
					</c:if>
					<c:if test="${canMarkAsRecovered}">
						&nbsp;&nbsp;&nbsp;
						<c:set var="statusText"><bean:message key="loan.status.RECOVERED"/></c:set>
						<input type="button" class="button setExpiredStatus" status="RECOVERED" value="<bean:message key='loan.setExpiredStatus' arg0="${statusText}" />" />
					</c:if>
					<c:if test="${canMarkAsUnrecoverable}">
						&nbsp;&nbsp;&nbsp;
						<c:set var="statusText"><bean:message key="loan.status.UNRECOVERABLE"/></c:set>
						<input type="button" class="button setExpiredStatus" status="UNRECOVERABLE" value="<bean:message key='loan.setExpiredStatus' arg0="${statusText}" />" />
					</c:if>
				</cyclos:cell>
			</c:if>
		</cyclos:layout>
		</td>
	</tr>
</table>

<c:if test="${cyclos:name(parameters.type) != 'SINGLE_PAYMENT'}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key='loanPayment.title' /></td>
			<cyclos:help page="loans#loan_parcels_detail"/>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableLists">
			<table class="defaultTable">
				<tr>
					<th class="tdHeaderContents"><bean:message key='loanPayment.number' /></th>
					<th class="tdHeaderContents"><bean:message	key='loanPayment.expirationDate' /></th>
					<th class="tdHeaderContents"><bean:message key='loanPayment.status' /></th>
					<th class="tdHeaderContents"><bean:message key='loanPayment.amount' /></th>
					<th class="tdHeaderContents"><bean:message key='loanPayment.repaidAmount' /></th>
					<th class="tdHeaderContents"><bean:message key='loanPayment.repaymentDate' /></th>
				</tr>
				<c:forEach var="payment" items="${loan.payments}">
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td align="center">${payment.number}</td>
						<td align="center"><cyclos:format rawDate="${payment.expirationDate}"/></td>
						<td align="center"><bean:message key='loanPayment.status.${payment.status}' /></td>
						<td align="right"><cyclos:format number="${payment.amount}" unitsPattern="${currencyPattern}"/></td>
						<td align="right"><cyclos:format number="${payment.repaidAmount}" unitsPattern="${currencyPattern}"/></td>
						<td align="center"><cyclos:format date="${payment.repaymentDate}"/></td>
					</tr>
				</c:forEach>
			</table>
			</td>
		</tr>
	</table>
	
</c:if>
<c:if test="${not empty loan.loanGroup}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key='loan.title.loanGroup.members' /></td>
			<cyclos:help page="loans#loan_to_members"/>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableLists">
			<table class="defaultTable">
				<tr>
					<th width="40%" class="tdHeaderContents"><bean:message key='member.username' /></th>
					<th width="60%" class="tdHeaderContents"><bean:message	key='member.name' /></th>
				</tr>
				<c:forEach var="member" items="${membersInLoan}">
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td align="center"><cyclos:profile elementId="${member.id}" styleClass="${member == loan.member ? 'fieldDecoration' : ''}" pattern="username"/></td>
						<td align="center"><cyclos:profile elementId="${member.id}" styleClass="${member == loan.member ? 'fieldDecoration' : ''}" pattern="name"/></td>
					</tr>
				</c:forEach>
			</table>
			</td>
		</tr>
	</table>
	
</c:if>
<c:if test="${not empty repaymentAmounts and (canRepay or canDiscard)}">	
	<c:set var="loanPayment" value="${repaymentAmounts.loanPayment}"/>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key="loan.title.repayment" /></td>
			<cyclos:help page="loans#loan_repayment${isMember ? '_by_member' : '_by_admin'}"/>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableForms">
				<ssl:form style="display:inline;" styleId="repayForm" method="post" action="${actionPrefix}/repayLoan">
				<input type="hidden" name="loanId" value="${loan.id}">
				<input type="hidden" name="memberId" value="${loanDetailsForm.memberId}"/>
				<input type="hidden" name="loanGroupId" value="${loanDetailsForm.loanGroupId}"/>
				<input type="hidden" name="transactionPassword" />
				<c:if test="${!allowPartialRepayments}">
					<input type="hidden" name="amount" value="<cyclos:format number="${repaymentAmounts.remainingAmountAtDate}"/>" />
				</c:if>

				<table class="defaultTable">
					<c:if test="${canRepay or canDiscard}">				
						<c:if test="${cyclos:name(parameters.type) != 'SINGLE_PAYMENT'}">
							<tr>
								<td class="label" width="35%"><bean:message key='loan.repayment.paymentNumber' /></td>
								<td>
									<html:select property="loanPaymentId" styleId="paymentSelect" styleClass="medium">
										<c:forEach var="pmt" items="${loan.payments}">
											<c:if test="${not pmt.status.closed}">
												<option value="${pmt.id}" ${pmt == loanPayment ? 'selected="selected"' : ''}">${pmt.number}</option>
											</c:if>
										</c:forEach>
									</html:select>
								</td>
							</tr>
							<tr>
								<td class="label"><bean:message key='loan.repayment.amount' /></td>
								<td><input class="InputBoxDisabled medium" readonly="readonly" value="<cyclos:format number="${loanPayment.amount}" unitsPattern="${currencyPattern}"/>"></td>
							</tr>
							<c:set var="interest" value="${repaymentAmounts.interest}"/>
							<c:if test="${interest > 0}">
								<tr>
									<td class="label"><bean:message key='loan.repayment.interest' /></td>
									<td><input class="InputBoxDisabled medium" readonly="readonly" value="<cyclos:format number="${interest}"/>"></td>
								</tr>
								<tr>
									<td class="label"><bean:message key='loan.repayment.totalAmount' /></td>
									<td><input class="InputBoxDisabled medium" readonly="readonly" value="<cyclos:format number="${repaymentAmounts.totalAmount}" unitsPattern="${currencyPattern}"/>"></td>
								</tr>
							</c:if>
							<tr>
								<td class="label" width="25%"><bean:message key='loan.repayment.repaidAmount' /></td>
								<td><input class="InputBoxDisabled medium" readonly="readonly" value="<cyclos:format number="${loanPayment.repaidAmount == null ? 0.0 : loanPayment.repaidAmount}"/>"></td>
							</tr>
							<tr>
								<td class="label" style="font-weight:bold"><bean:message key='loan.repayment.remainingAmount' /></td>
								<td><input class="InputBoxDisabled medium" readonly="readonly" value="<cyclos:format number="${repaymentAmounts.remainingAmountAtDate}" unitsPattern="${currencyPattern}"/>"></td>
							</tr>
						</c:if>
						<c:if test="${allowPartialRepayments}">
							<tr>
								<td class="label" width="35%"><bean:message key='loan.repayment.amountToRepaid'/></td>
								<td><input type="text" id="amountText" class="float medium" name="amount" value="<cyclos:format number="${repaymentAmounts.remainingAmountAtDate}"/>"></td>
							</tr>
						</c:if>
						<tr id="customValuesRow" style="display:none">
							<td colspan="2" id="customValuesCell" style="padding:0px;">
							</td>
						</tr>
						<c:if test="${cyclos:granted(AdminMemberPermission.LOANS_REPAY_WITH_DATE)}">
							<tr>
								<td class="label"><bean:message key='loan.repayment.setDate'/></td>
								<td><input type="checkbox" class="checkbox" id="setDateCheck"></td>
							</tr>
							<tr id="trDate" style="display:none">
								<td class="label"><bean:message key='loan.repayment.manualDate'/></td>
								<td><html:text styleId="dateText" property="date" styleClass="small date"/></td>
							</tr>
						</c:if>
					</c:if> <%-- end of canRepay or canDiscard --%>
					<c:if test="${requestTransactionPassword}">
						<tr>
							<td colspan="2" class="label" style="text-align:center;padding-bottom:10px">
								<hr>
								
								<cyclos:escapeHTML><bean:message key="loan.repayment.transactionPassword.${canRepay && canDiscard ? 'repayOrDiscard' : canRepay ? 'repayOnly' : canDiscard ? 'discardOnly' : 'canPerformExpiredAction'}"/></cyclos:escapeHTML>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<c:set var="hideSubmit" value="${true}" scope="request"/>
								<c:set var="transactionPasswordField" value="_transactionPassword" scope="request" />
								<jsp:include page="/do/transactionPassword" />
							</td>
						</tr>
					</c:if>
					
					<tr>
						<td colspan="2" align="right">
							<c:if test="${canRepay}">
								<input class="button" type="submit" value="<bean:message key='loan.repayment.repay'/>">
							</c:if>
							<c:if test="${canDiscard}">
								<script>
									var discardConfirmationMessage = "<cyclos:escapeJS><bean:message key="${cyclos:name(parameters.type) == 'SINGLE_PAYMENT' ? 'loan.repayment.discard.loan.confirmationMessage' : 'loan.repayment.discard.payment.confirmationMessage'}"/></cyclos:escapeJS>";
								</script>
								&nbsp;
								<input class="button" type="button" id="discardButton" value="<bean:message key="${cyclos:name(parameters.type) == 'SINGLE_PAYMENT' ? 'loan.repayment.discard.loan' : 'loan.repayment.discard.payment'}"/>">
							</c:if>									
						</td>
					</tr>
				</table>
			</ssl:form>
			<c:if test="${canDiscard}">
				<ssl:form styleId="discardForm" method="post" action="${actionPrefix}/discardLoan">
					<html:hidden property="loanId" value="${loan.id}"/>
					<html:hidden property="loanPaymentId" value="${loanPayment.id}"/>
					<html:hidden property="transactionPassword"/>
					<html:hidden property="memberId"/>
					<html:hidden property="loanGroupId"/>
					<c:if test="${cyclos:granted(AdminMemberPermission.LOANS_REPAY_WITH_DATE)}">
						<html:hidden property="date"/>
					</c:if>
				</ssl:form>
			</c:if>
			</td>
		</tr>
	</table>

</c:if>

<table class="defaultTableContentHidden"><tr><td>
<input class="button" id="backButton" type="button" value="<bean:message key='global.back'/>"> 
</td></tr></table>