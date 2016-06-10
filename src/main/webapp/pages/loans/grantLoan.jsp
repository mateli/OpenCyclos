<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/loans/grantLoan.js" />
<script>
	var paymentLabel = "<cyclos:escapeJS><bean:message key='loanPayment.payment'/></cyclos:escapeJS>";
	var expirationDateLabel = "<cyclos:escapeJS><bean:message key='loanPayment.expirationDate'/></cyclos:escapeJS>";
	var amountLabel = "<cyclos:escapeJS><bean:message key='loanPayment.amount'/></cyclos:escapeJS>";
	
	var transferTypes = [];
	<c:forEach var="tt" items="${transferTypes}">
		transferTypes.push({
			id:'${tt.id}',
			name:'<cyclos:escapeJS>${tt.name}</cyclos:escapeJS>',
			loanType:'${tt.loan.type}',
			expirationDailyInterest:'<cyclos:format amount="${tt.loan.expirationDailyInterestAmount}"/>',
			expirationFee:'<cyclos:format amount="${tt.loan.expirationFee}"/>',
			grantFee:'<cyclos:format amount="${tt.loan.grantFee}"/>',
			monthlyInterest:'<cyclos:format amount="${tt.loan.monthlyInterestAmount}"/>',
			repaymentDays:'${tt.loan.repaymentDays}'
		});
	</c:forEach>
</script>

<ssl:form method="post" action="${formAction}">
<html:hidden property="memberId"/>
<html:hidden property="loanGroupId"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key='loan.title.grant' arg0="${empty member ? loanGroup.name : member.name}"/></td>
        <cyclos:help page="loans#loan"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<c:choose><c:when test="${not empty member}">
					<tr>
						<td class="label" width="25%"><bean:message key='loan.member'/></td>
						<td>
							<input type="hidden" name="loan(member)" value="${member.id}"/>
							<input type="text" class="large InputBoxDisabled" readonly value="${member.name}"/>
						</td>
					</tr>
					<c:if test="${not empty loanGroups}">
						<tr>
							<td class="label" width="25%"><bean:message key='loan.group'/></td>
							<td>
								<select name="loan(loanGroup)">
									<option value=""><bean:message key="loan.group.personal"/></option>
									<c:forEach var="current" items="${loanGroups}">
										<option value="${current.id}">${current.name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</c:if>
				</c:when><c:otherwise>
					<tr>
						<td class="label" width="25%"><bean:message key='loan.group'/></td>
						<td>
							<input type="hidden" name="loan(loanGroup)" value="${loanGroup.id}"/>
							<input type="text" class="large InputBoxDisabled" readonly value="${loanGroup.name}"/>
						</td>
					</tr>
					<tr>
						<td class="label"><bean:message key='loan.group.responsible'/></td>
						<td>
							<select id="responsibleSelect" name="loan(member)">
								<c:forEach var="current" items="${loanGroup.members}">
									<option value="${current.id}">${current.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</c:otherwise></c:choose>
				<tr>
					<td class="label"><bean:message key='loan.type'/></td>
					<td><html:select styleId="transferType" property="loan(transferType)" /></td>
				</tr>
				<tr class="trWithInterest" id="trMonthlyInterest" style="display:none">
					<td class="label"><bean:message key='loan.monthlyInterest'/></td>
					<td><input id="monthlyInterest" class="small InputBoxDisabled" readonly/></td>
				</tr>
				<tr class="trWithInterest" id="trLoanGrantFee" style="display:none">
					<td class="label"><bean:message key="loan.grantFee"/></td>
					<td><input id="grantFee" class="small InputBoxDisabled" readonly/></td>
				</tr>
				<tr class="trWithInterest" id="trLoanExpirationFee" style="display:none">
					<td class="label"><bean:message key="loan.expirationFee"/></td>
					<td><input id="expirationFee" class="small InputBoxDisabled" readonly/></td>
				</tr>
				<tr class="trWithInterest" id="trLoanExpirationDailyInterest" style="display:none">
					<td class="label"><bean:message key="loan.expirationDailyInterest"/></td>
					<td><input id="expirationDailyInterest" class="small InputBoxDisabled" readonly/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key='loan.amount'/></td>
					<td><html:text styleId="amount" property="loan(amount)" styleClass="small float required"/></td>
				</tr>
				<tr id="customValuesRow" style="display:none">
					<td colspan="2" id="customValuesCell" style="padding:0px;">
					</td>
				</tr>
				<c:if test="${cyclos:granted(AdminMemberPermission.LOANS_GRANT_WITH_DATE)}">
					<tr>
						<td class="label"><bean:message key='loan.grant.setDate'/></td>
						<td><input type="checkbox" class="checkbox" id="setDateCheck"></td>
					</tr>
					<tr id="trDate" style="display:none">
						<td class="label"><bean:message key='loan.grant.manualDate'/></td>
						<td><html:text styleId="dateText" property="loan(date)" styleClass="small date"/></td>
					</tr>
				</c:if>
				<tr class="trSinglePayment" style="display:none">
					<td class="label"><bean:message key='loan.repaymentDate'/></td>
					<td><html:text property="loan(repaymentDate)" styleClass="small date required"/></td>
				</tr>
				<tr class="trMultiPayment" style="display:none">
					<td class="label"><bean:message key='loan.firstExpirationDate'/></td>
					<td><input type="text" id="firstExpirationDate" class="small date required"></td>
				</tr>
				<tr class="trMultiPayment" style="display:none">
					<td class="label"><bean:message key='loan.paymentCount'/></td>
					<td>
						<input type="text" name="paymentCount" class="small number required">
						&nbsp;
						<input id="calculatePaymentsButton" class="button" type="button" value="<bean:message key='loan.calculate'/>">
					</td>
				</tr>			
				<tr class="trMultiPayment" style="display:none">
					<td>&nbsp;</td>
					<td><div id="paymentList"></div></td>
				</tr>
				<tr class="trWithInterest" style="display:none">
					<td class="label"><bean:message key='loan.firstExpirationDate'/></td>
					<td><html:text property="loan(firstRepaymentDate)" styleClass="small date required"/></td>
				</tr>
				<tr class="trWithInterest" style="display:none">
					<td class="label"><bean:message key='loan.paymentCount'/></td>
					<td>
						<html:text property="loan(paymentCount)" styleClass="small number required"/>
						&nbsp;
						<input id="showProjectionButton" class="button" type="button" value="<bean:message key='loan.showProjection'/>">
					</td>
				</tr>			
				<tr class="trWithInterest" style="display:none">
					<td>&nbsp;</td>
					<td><div id="paymentProjection"></div></td>
				</tr>
				<tr>
					<td class="label" valign="top"><bean:message key='loan.description'/></td>
					<td><html:textarea styleId="description" property="loan(description)" styleClass="full" rows="7"/></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="submit" class="button" value="<bean:message key='global.submit'/>"></td>
				</tr>
			</table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left"><input id="backButton" type="button" class="button" value="<bean:message key='global.back'/>"></td>
	</tr>
</table>
</ssl:form>
