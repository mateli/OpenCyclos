<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %>

<table class="tablePrint" cellspacing="0" cellpadding="0">
	<tr>
    	<td class="printTitle"><bean:message key="reports.members.presentation.result"/></td>
   	</tr>
    <tr>
        <td align="left">
			<table class="tablePrint">

				<!-- Report header -->			
				<tr> 
					<td class="tdPrintHeader" rowspan="2" colspan="${dto.memberColSpan}"><bean:message key="member.member"/></td>
					
					<c:set var="brokerColSpan" value="${dto.brokerColSpan}" />
					<c:if test="${brokerColSpan > 0}">
						<td class="tdPrintHeader" rowspan="2" colspan="${brokerColSpan}"><bean:message key="member.broker"/></td>
					</c:if>
					
					<c:forEach var="tPaymentFilter" items="${dto.transactionsPaymentFilters}">
						<td class="tdPrintHeader" colspan="${dto.transactionsColSpan[tPaymentFilter]}">
							${tPaymentFilter.name}
						</td>
					</c:forEach>
				</tr>
				
				<tr>
					<!-- 
					<c:if test="${brokerColSpan > 0}">
						<td class="tdPrintHeader" colspan="${brokerColSpan}">&nbsp;</td>
					</c:if>
					 -->
					<c:forEach var="tPaymentFilter" items="${dto.transactionsPaymentFilters}">
						<c:if test="${dto.incomingTransactions}">
							<td class="tdPrintHeader" colspan="2">
								<bean:message key="reports.members_reports.credits"/>
							</td>
						</c:if>
						<c:if test="${dto.outgoingTransactions}">
							<td class="tdPrintHeader" colspan="2">
								<bean:message key="reports.members_reports.debits"/>
							</td>
						</c:if>
					</c:forEach>
				</tr>
				
				<tr>
					<td class="tdPrintHeader"><bean:message key="member.username"/></td>
					<c:if test="${dto.memberName}">
						<td class="tdPrintHeader"><bean:message key="member.name"/></td>
					</c:if>

					<c:if test="${dto.brokerUsername}">
						<td class="tdPrintHeader"><bean:message key="member.brokerUsername"/></td>
					</c:if>
					<c:if test="${dto.brokerName}">
						<td class="tdPrintHeader"><bean:message key="member.brokerName"/></td>
					</c:if>
					
					<c:forEach var="tPaymentFilter" items="${dto.transactionsPaymentFilters}">
						<c:if test="${dto.incomingTransactions}">
							<td class="tdPrintHeader"><bean:message key="reports.members_reports.number"/></td>
							<td class="tdPrintHeader"><bean:message key="reports.members_reports.total_amount"/></td>
						</c:if>
						<c:if test="${dto.outgoingTransactions}">
							<td class="tdPrintHeader"><bean:message key="reports.members_reports.number"/></td>
							<td class="tdPrintHeader"><bean:message key="reports.members_reports.total_amount"/></td>
						</c:if>
					</c:forEach>
				</tr>
				
				<!-- Report data -->				
				<c:forEach var="vo" items="${iterator}"> 

					<c:if test="${dto.includeNoTraders || vo.hasData}">
						<tr>
							<td class="tdPrintData">${vo.member.username}</td>
							<c:if test="${dto.memberName}">
								<td class="tdPrintData">${vo.member.name}</td>
							</c:if>
	
							<c:if test="${dto.brokerUsername}">
								<td class="tdPrintData">${vo.member.broker.username}</td>
							</c:if>
							<c:if test="${dto.brokerName}">
								<td class="tdPrintData">${vo.member.broker.name}</td>
							</c:if>
	
							<c:forEach var="tPaymentFilter" items="${dto.transactionsPaymentFilters}">
								<c:if test="${dto.incomingTransactions}">
									<td class="tdPrintData">${vo.credits[tPaymentFilter].count}</td>
									<td class="tdPrintData"><cyclos:format number="${vo.credits[tPaymentFilter].amount}"/></td>
								</c:if>
								<c:if test="${dto.outgoingTransactions}">
									<td class="tdPrintData">${vo.debits[tPaymentFilter].count}</td>
									<td class="tdPrintData"><cyclos:format number="${vo.debits[tPaymentFilter].amount}"/></td>
								</c:if>
							</c:forEach>
						</tr>
					</c:if>
					
					<cyclos:clearCache/>
				</c:forEach>
				
			</table>
		</td>
	</tr>
</table>