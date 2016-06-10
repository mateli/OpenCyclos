<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<c:set var="parameters" value="${loan.parameters}"/> 

<div class="printTitle"><bean:message key="loan.title.printDetails"/></div>

<cyclos:layout columns="4" className="printContent printBorder" width="100%">
	<c:choose><c:when test="${loan.toGroup}">
		<cyclos:cell className="printLabel" width="20%"><bean:message key='loan.group'/></cyclos:cell>
		<cyclos:cell className="printData" width="30%">${loan.loanGroup.name}</cyclos:cell>
	</c:when><c:otherwise>
		<cyclos:cell className="printLabel" width="20%"><bean:message key='member.username'/></cyclos:cell>
		<cyclos:cell className="printData" width="30%">${loan.member.username}</cyclos:cell>
	</c:otherwise></c:choose>
	<cyclos:cell className="printLabel" width="20%"><bean:message key='loan.status' /></cyclos:cell>
	<cyclos:cell className="printData" width="30%"><bean:message key='loan.status.${loan.status}'/></cyclos:cell>

	<c:choose><c:when test="${loan.toGroup}">
		<cyclos:cell className="printLabel"><bean:message key='loan.group.responsible'/></cyclos:cell>
		<cyclos:cell className="printData">${loan.member.username}</cyclos:cell>
		<cyclos:cell className="printLabel"><bean:message key='member.name' /></cyclos:cell>
		<cyclos:cell className="printData">${loan.member.name}</cyclos:cell>
	</c:when><c:otherwise>
		<cyclos:cell className="printLabel"><bean:message key='member.memberName' /></cyclos:cell>
		<cyclos:cell className="printData">${loan.member.name}</cyclos:cell>
	</c:otherwise></c:choose>
	<cyclos:cell className="printLabel"><bean:message key='transfer.type'/></cyclos:cell>
	<cyclos:cell className="printData">${loan.transfer.type.name}</cyclos:cell>

	<cyclos:cell className="printLabel"><bean:message key='loan.grantDate'/></cyclos:cell>
	<cyclos:cell className="printData"><cyclos:format date="${loan.grantDate}"/></cyclos:cell>
	<cyclos:cell className="printLabel"><bean:message key='loan.expirationDate' /></cyclos:cell>
	<cyclos:cell className="printData"><cyclos:format rawDate="${loan.expirationDate}"/></cyclos:cell>

	<cyclos:cell className="printLabel"><bean:message key='loan.amount' /></cyclos:cell>
	<cyclos:cell className="printData"><cyclos:format number="${loan.amount}"/></cyclos:cell>
	<c:choose><c:when test="${loan.status.closed}">
		<cyclos:cell className="printLabel"><bean:message key="loan.repaymentDate"/></cyclos:cell>
		<cyclos:cell className="printData"><cyclos:format dateTime="${loan.repaymentDate}"/></cyclos:cell>
	</c:when><c:otherwise>
		<cyclos:cell className="printLabel"><bean:message key="loan.remainingAmount"/></cyclos:cell>
		<cyclos:cell className="printData"><cyclos:format number="${loan.remainingAmount}"/></cyclos:cell>
	</c:otherwise></c:choose>

	<c:if test="${cyclos:name(parameters.type) == 'WITH_INTEREST'}">
		<c:if test="${loan.amount != loan.totalAmount}">
			<cyclos:cell className="printLabel"><bean:message key='loan.totalAmount' /></cyclos:cell>
			<cyclos:cell className="printData"><cyclos:format number="${loan.totalAmount}"/></cyclos:cell>
		</c:if>
    	<c:if test="${not empty parameters.monthlyInterest}">
            <cyclos:cell className="printLabel"><bean:message key='loan.monthlyInterest'/></cyclos:cell>
   			<cyclos:cell className="printData"><cyclos:format amount="${parameters.monthlyInterestAmount}"/></cyclos:cell>
    	</c:if>
    	<c:if test="${not empty parameters.grantFee}">
            <cyclos:cell className="printLabel"><bean:message key='loan.grantFee'/></cyclos:cell>
   			<cyclos:cell className="printData"><cyclos:format amount="${parameters.grantFee}"/></cyclos:cell>
    	</c:if>
    	<c:if test="${not empty parameters.grantFee}">
            <cyclos:cell className="printLabel"><bean:message key='loan.grantFee'/></cyclos:cell>
   			<cyclos:cell className="printData"><cyclos:format amount="${parameters.grantFee}"/></cyclos:cell>
    	</c:if>
    	<c:if test="${not empty parameters.expirationFee}">
            <cyclos:cell className="printLabel"><bean:message key='loan.expirationFee'/></cyclos:cell>
   			<cyclos:cell className="printData"><cyclos:format amount="${parameters.expirationFee}"/></cyclos:cell>
    	</c:if>
    	<c:if test="${not empty parameters.expirationDailyInterest}">
            <cyclos:cell className="printLabel"><bean:message key='loan.expirationDailyInterest'/></cyclos:cell>
   			<cyclos:cell className="printData"><cyclos:format amount="${parameters.expirationDailyInterestAmount}"/></cyclos:cell>
    	</c:if>
    </c:if>
	
    <c:forEach var="entry" items="${customFields}">
        <c:set var="field" value="${entry.field}"/>
        <c:set var="value" value="${entry.value.value}"/>
        	<cyclos:cell className="printLabel">${field.name}</cyclos:cell>
  			<cyclos:cell className="printData"><cyclos:customField textOnly="true" field="${field}" value="${value}" /></cyclos:cell>
    </c:forEach>
    
    <cyclos:rowBreak/>

	<cyclos:cell className="printLabel" valign="top"><bean:message key='loan.description'/></cyclos:cell>
	<cyclos:cell colspan="3" className="printData"><cyclos:escapeHTML>${loan.transfer.description}</cyclos:escapeHTML></cyclos:cell>
</cyclos:layout>


<c:if test="${cyclos:name(parameters.type) != 'SINGLE_PAYMENT'}">
	<div class="printTitle"><bean:message key='loanPayment.title' /></div>
	<table class="printContent printBorder" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<th class="printLabel"><bean:message key='loanPayment.number' /></th>
			<th class="printLabel"><bean:message	key='loanPayment.expirationDate' /></th>
			<th class="printLabel"><bean:message key='loanPayment.status' /></th>
			<th class="printLabel"><bean:message key='loanPayment.amount' /></th>
			<th class="printLabel"><bean:message key='loanPayment.repaidAmount' /></th>
			<th class="printLabel"><bean:message key='loanPayment.repaymentDate' /></th>
		</tr>
		<c:forEach var="payment" items="${loan.payments}">
			<tr>
				<td class="printData" align="center">${payment.number}</td>
				<td class="printData" align="center"><cyclos:format rawDate="${payment.expirationDate}"/></td>
				<td class="printData" align="center"><bean:message key='loanPayment.status.${payment.status}' /></td>
				<td class="printData" align="right">&nbsp;<cyclos:format number="${payment.amount}"/>&nbsp;</td>
				<td class="printData" align="right">&nbsp;<cyclos:format number="${payment.repaidAmount}"/>&nbsp;</td>
				<td class="printData" align="center">&nbsp;<cyclos:format date="${payment.repaymentDate}"/>&nbsp;</td>
			</tr>
		</c:forEach>
	</table>
	
</c:if>
<c:if test="${not empty loan.loanGroup}">
	<div class="printTitle"><bean:message key='loan.title.loanGroup.members' /></div>
	<table class="printContent printBorder" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<th width="40%" class="printLabel"><bean:message key='member.username' /></th>
			<th width="60%" class="printLabel"><bean:message key='member.name' /></th>
		</tr>
		<c:forEach var="member" items="${loan.toMembers}">
			<tr>
				<td class="printData" align="center">${member.username}</td>
				<td class="printData" align="center">${member.name}</td>
			</tr>
		</c:forEach>
	</table>
	
</c:if> 
