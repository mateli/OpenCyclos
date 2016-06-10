<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/loans/confirmLoan.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="memberId" />
<html:hidden property="loanGroupId" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="loan.title.confirm"/></td>
        <cyclos:help page="loans#loan_confirm"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTable">
            <table class="defaultTable">
            	<tr>
					<td colspan="2" class="label" style="text-align:center;padding-bottom:5px">
						<cyclos:escapeHTML><bean:message key="loan.confirmation.header${wouldRequireAuthorization ? '.withAuthorization' : ''}"/></cyclos:escapeHTML>
					</td>
				</tr>
				<tr>
					<td class="headerLabel" width="30%"><bean:message key='loan.type'/></td>
					<td class="headerField">${loan.transferType.name}</td>
				</tr>
				<c:choose><c:when test="${empty loan.loanGroup}">
					<tr>
						<td class="headerLabel"><bean:message key="member.member"/></td>
						<td class="headerField"><cyclos:profile elementId="${loan.member.id}"/></td>
					</tr>
				</c:when><c:otherwise>
					<tr>
						<td class="headerLabel"><bean:message key='loan.group'/></td>
						<td class="headerField">${loan.loanGroup.name}</td>
					</tr>
					<tr>
						<td class="headerLabel" valign="top"><bean:message key='loan.group.members'/></td>
						<td class="headerField">
							<c:forEach var="member" varStatus="status" items="${membersInGroup}">
								<c:if test="${member == loan.member}">
									<bean:message key="loan.group.responsible"/>:
								</c:if>
								<cyclos:profile elementId="${member.id}"/>
								<c:if test="${!status.last}">
									<br>
								</c:if> 
							</c:forEach>
						</td>
					</tr>
				</c:otherwise></c:choose>	
				<c:if test="${not empty loan.date}">
					<tr>
						<td class="headerLabel"><bean:message key='loan.grantDate'/></td>
						<td class="headerField"><cyclos:format rawDate="${loan.date}"/></td>
					</tr>
				</c:if>
				<tr>
					<td class="headerLabel"><bean:message key='loan.amount'/></td>
					<td class="headerField"><cyclos:format number="${finalAmount}" unitsPattern="${unitsPattern}" /></td>
				</tr>

			    <c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
			        <c:if test="${not empty value.value}">
			            <tr>
			                <td class="headerLabel">${field.name}</td>
			   				<td class="headerField" nowrap="nowrap"><cyclos:customField field="${field}" value="${value}" textOnly="true"/></td>
						</tr>
					</c:if>
			    </c:forEach>

			    <c:if test="${cyclos:name(loan.loanType) == 'SINGLE_PAYMENT'}">
					<tr>
						<td class="headerLabel" valign="top"><bean:message key='loan.repaymentDate'/></td>
						<td><cyclos:format date="${loan.repaymentDate}" /></td>
					</tr>
			    </c:if>
				<c:if test="${not empty payments}">
	               	<tr>
						<td class="headerLabel" valign="top"><bean:message key="loan.payments"/></td>
						<td class="headerField">
							<c:forEach var="payment" items="${payments}">
								<div>
									<span class="inlineLabel"><bean:message key='loanPayment.expirationDate'/>:</span>
									<span style="font-style:italic"><cyclos:format rawDate="${payment.expirationDate}"/></span>.&nbsp;
									<span class="inlineLabel"><bean:message key='loanPayment.amount'/>:</span>
									<cyclos:format number="${payment.amount}" unitsPattern="${unitsPattern}"/>
								</div>
							</c:forEach>
						</td>
					</tr>            	
				</c:if>
				<tr>
					<td class="headerLabel" valign="top"><bean:message key='loan.description'/></td>
					<td class="headerField"><cyclos:escapeHTML>${loan.description}</cyclos:escapeHTML></td>
				</tr>
				<c:if test="${not empty fees}">
	               	<tr>
						<td class="headerLabel"><bean:message key="payment.confirmation.appliedFees"/></td>
						<td class="headerField">
							<c:forEach var="fee" items="${fees}">
								<div>
									<span style="font-style:italic">${fee.key.name}</span>.&nbsp;
									<span class="inlineLabel"><bean:message key='transfer.amount'/>:</span>
									<cyclos:format number="${fee.value}" unitsPattern="${unitsPattern}"/>
								</div>
							</c:forEach>
						</td>
					</tr>            	
				</c:if>
				
				<c:set var="confirmationMessage" value="${loan.transferType.confirmationMessage}"/>
				<c:if test="${not empty confirmationMessage}">
					<tr>
						<td colspan="2" class="label" style="text-align:center;padding-bottom:5px">
							<hr>
							
							<cyclos:escapeHTML>${confirmationMessage}</cyclos:escapeHTML>
						</td>
					</tr>
				</c:if>
				
				<c:choose><c:when test="${requestTransactionPassword}">
					<tr>
						<td colspan="2" class="label" style="text-align:center;padding-bottom:10px">
							<hr>
							
							<cyclos:escapeHTML><bean:message key='loan.confirmation.transactionPassword'/></cyclos:escapeHTML>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center"><jsp:include page="/do/transactionPassword" /></td>
					</tr>
				</c:when><c:otherwise>
					<tr>
						<td colspan="2" align="right"><input type="submit" class="button" value="<bean:message key='global.submit'/>"></td>
					</tr>
				</c:otherwise></c:choose>
            </table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td>
			<input type="button" class="button" id="backButton" value="<bean:message key='global.back'/>">
		</td>
	</tr>
</table>
</ssl:form>