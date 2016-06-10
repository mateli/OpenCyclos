<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %>

<table class="tablePrint">
	<tr>
    	<td class="printTitle"><bean:message key="reports.members.presentation.list.result"/></td>
   	</tr>

    <tr>
       <td align="left">
			<table class="tablePrint">
			
				<!-- Report header -->
				<tr> 
					<td class="tdPrintHeader" colspan="${dto.memberColSpan}" align="center"><bean:message key="member.member"/></td>
					
					<c:set var="brokerColSpan" value="${dto.brokerColSpan}" />
					<c:if test="${brokerColSpan > 0}">
						<td class="tdPrintHeader" colspan="${brokerColSpan}" align="center"><bean:message key="member.broker"/></td>
					</c:if>
					
					<c:forEach var="accountType" items="${accountTypes}">
						<td class="tdPrintHeader" colspan="${dto.accountsColSpan}" align="center"><bean:message key="reports.members.account.header" arg0="${accountType.name}"/></td>
					</c:forEach>
					
					<c:if test="${dto.ads}">
						<td class="tdPrintHeader" colspan="${dto.adsColSpan}" align="center"><bean:message key="reports.members.ads"/></td>
					</c:if>
					
					<c:if test="${dto.givenReferences}">
						<td class="tdPrintHeader" colspan="5" align="center"><bean:message key="reports.members.references.given"/></td>
					</c:if>
					
					<c:if test="${dto.receivedReferences}">
						<td class="tdPrintHeader" colspan="5" align="center"><bean:message key="reports.members.references.received"/></td>
					</c:if>
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
					
					<c:forEach var="entry" items="${accountTypes}">
						<c:if test="${dto.accountsCredits}">
							<td class="tdPrintHeader"><bean:message key="account.creditLimit"/></td>
						</c:if>
						<c:if test="${dto.accountsUpperCredits}">
							<td class="tdPrintHeader"><bean:message key="account.upperCreditLimit"/></td>
						</c:if>
						<c:if test="${dto.accountsBalances}">
							<td class="tdPrintHeader"><bean:message key="account.balance"/></td>
						</c:if>
					</c:forEach>
					
					<c:if test="${dto.activeAds}">
						<td class="tdPrintHeader"><bean:message key="reports.members.ads.active"/></td>
					</c:if>
					<c:if test="${dto.expiredAds}">
						<td class="tdPrintHeader"><bean:message key="reports.members.ads.expired"/></td>
					</c:if>
					<c:if test="${dto.permanentAds}">
						<td class="tdPrintHeader"><bean:message key="reports.members.ads.permanent"/></td>
					</c:if>
					<c:if test="${dto.scheduledAds}">
						<td class="tdPrintHeader"><bean:message key="reports.members.ads.scheduled"/></td>
					</c:if>
					
					<c:if test="${dto.givenReferences}">
						<td class="tdPrintHeader"><bean:message key="reference.level.VERY_BAD"/></td>
						<td class="tdPrintHeader"><bean:message key="reference.level.BAD"/></td>
						<td class="tdPrintHeader"><bean:message key="reference.level.NEUTRAL"/></td>
						<td class="tdPrintHeader"><bean:message key="reference.level.GOOD"/></td>
						<td class="tdPrintHeader"><bean:message key="reference.level.VERY_GOOD"/></td>
					</c:if>
					
					<c:if test="${dto.receivedReferences}">
						<td class="tdPrintHeader"><bean:message key="reference.level.VERY_BAD"/></td>
						<td class="tdPrintHeader"><bean:message key="reference.level.BAD"/></td>
						<td class="tdPrintHeader"><bean:message key="reference.level.NEUTRAL"/></td>
						<td class="tdPrintHeader"><bean:message key="reference.level.GOOD"/></td>
						<td class="tdPrintHeader"><bean:message key="reference.level.VERY_GOOD"/></td>
					</c:if>
					
				</tr>
				
				<!-- Report data -->				
				<c:forEach var="membersReportVO" items="${voIterator}"> 
					<tr>
						<td class="tdPrintData">${membersReportVO.member.username}</td>
						<c:if test="${dto.memberName}">
							<td class="tdPrintData">${membersReportVO.member.name}</td>
						</c:if>
						<c:if test="${dto.brokerUsername}">
							<td class="tdPrintData">${membersReportVO.member.broker.username}</td>
						</c:if>
						<c:if test="${dto.brokerName}">
							<td class="tdPrintData">${membersReportVO.member.broker.name}</td>
						</c:if>
						
						<c:forEach var="accountType" items="${accountTypes}">
							<c:if test="${dto.accountsCredits}">
								<td class="tdPrintData"><cyclos:format number="${membersReportVO.accountsCredits[accountType]}"/></td>
							</c:if>
							<c:if test="${dto.accountsUpperCredits}">
								<td class="tdPrintData"><cyclos:format number="${membersReportVO.accountsUpperCredits[accountType]}"/></td>
							</c:if>
							<c:if test="${dto.accountsBalances}">
								<td class="tdPrintData"><cyclos:format number="${membersReportVO.accountsBalances[accountType]}"/></td>
							</c:if>
						</c:forEach>
						
						<c:if test="${dto.activeAds}">
							<td class="tdPrintData">${membersReportVO.ads[adStatus['ACTIVE']]}</td>
						</c:if>
						<c:if test="${dto.expiredAds}">
							<td class="tdPrintData">${membersReportVO.ads[adStatus['EXPIRED']]}</td>
						</c:if>
						<c:if test="${dto.permanentAds}">
							<td class="tdPrintData">${membersReportVO.ads[adStatus['PERMANENT']]}</td>
						</c:if>
						<c:if test="${dto.scheduledAds}">
							<td class="tdPrintData">${membersReportVO.ads[adStatus['SCHEDULED']]}</td>
						</c:if>
						
						<c:if test="${dto.givenReferences}">
							<c:forEach var="entry" items="${referenceLevels}">
								<td class="tdPrintData">${membersReportVO.givenReferences[entry.value]}</td>
							</c:forEach>
						</c:if>
						
						<c:if test="${dto.receivedReferences}">
							<c:forEach var="entry" items="${referenceLevels}">
								<td class="tdPrintData">${membersReportVO.receivedReferences[entry.value]}</td>
							</c:forEach>
						</c:if>
					</tr>
					
					<cyclos:clearCache/>
				</c:forEach>
				
			</table>
		</td>
	</tr>
</table>
