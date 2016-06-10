<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %>

<table class="tablePrint">
	<tr>
    	<td class="printTitle"><bean:message key="reports.members.smsLog"/></td>
   	</tr>
    <tr>
       <td align="left">
			<table class="tablePrint">
				<!-- Report header -->
				<c:if test="${not empty query.period && (not empty query.period.begin || not empty query.period.end)}">
					<tr>
			 			<td class="printLabel">
			 				<bean:message key="smsLog.date"/>&nbsp;
			 			</td>
						<td class="printData">
							<c:if test="${not empty query.period.begin}">
								<span class="printLabel"><bean:message key="global.range.from"/>:&nbsp;</span>
								<cyclos:format date="${query.period.begin}"/>&nbsp;
							</c:if>
						</td>
						<td class="printData">
							<c:if test="${not empty query.period.end}">
								<span class="printLabel"><bean:message key="global.range.to"/>:&nbsp;</span>
								<cyclos:format date="${query.period.end}"/>&nbsp;
							</c:if>
						</td>
			   		</tr>
			   	</c:if>
			   	<c:if test="${not empty query.memberGroups}">
					<tr>
						<td class="printLabel"><bean:message key="member.group"/>:&nbsp;</td>
						<td class="printData" colspan="2">
							<c:forEach var="group" items="${query.memberGroups}" varStatus="status">
								${group.name}<c:if test="${!status.last}">,</c:if>
							</c:forEach>
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty query.status}">
					<tr>
						<td class="printLabel"><bean:message key="smsLog.status"/>:&nbsp;</td>
						<td class="printData" colspan="2"><bean:message key="smsLog.status.${query.status}"/></td>
					</tr>
				</c:if>
				<c:if test="${not empty query.type}">
					<tr>
						<td class="printLabel"><bean:message key="smsLog.type"/>:&nbsp;</td>
						<td class="printData" colspan="2"><bean:message key="smsLog.type.${query.type}"/></td>
					</tr>
					<c:choose>
						<c:when test="${cyclos:name(query.type) == 'MAILING' && not empty query.mailingTypes}">
							<tr>
								<td class="printLabel"><bean:message key="smsLog.mailingType"/>:&nbsp;</td>
								<td class="printData" colspan="2">
									<c:forEach var="mailingType" items="${query.mailingTypes}" varStatus="status">
										<bean:message key="smsMailing.mailingType.${mailingType}"/><c:if test="${!status.last}">,</c:if>
									</c:forEach>
								</td>
							</tr>
						</c:when>
						<c:when test="${cyclos:name(query.type) == 'NOTIFICATION' && not empty query.messageTypes}">
							<tr>
								<td class="printLabel"><bean:message key="smsLog.messageType"/>:&nbsp;</td>
								<td class="printData" colspan="2">
									<c:forEach var="messageType" items="${query.messageTypes}" varStatus="status">
										<bean:message key="message.type.${messageType}"/><c:if test="${!status.last}">,</c:if>
									</c:forEach>
								</td>
							</tr>
						</c:when>
						<c:when test="${cyclos:name(query.type) == 'SMS_OPERATION' && not empty query.smsTypes}">
							<tr>
								<td class="printLabel"><bean:message key="smsLog.smsType"/>:&nbsp;</td>
								<td class="printData" colspan="2">
									<c:forEach var="smsType" items="${query.smsTypes}" varStatus="status">
										<bean:message key="sms.type.${smsType.code}"/><c:if test="${!status.last}">,</c:if>
									</c:forEach>
								</td>
							</tr>
						</c:when>
					</c:choose>
				</c:if>
			</table>
		
        	<table class="tablePrint" style="margin:auto;width:auto;">
        		<tr>
        			<td></td>
					<c:forEach var="status" items="${statusList}">
						<td class="tdPrintHeader" width="70"><bean:message key="smsLog.status.${status}"/></td>
					</c:forEach>
					<td class="tdPrintHeader" width="70"><bean:message key="global.total"/></td>
				</tr>
				<c:forEach var="type" items="${typesList}">
	         				<tr>
	         					<td class="tdPrintHeader" nowrap="nowrap"><bean:message key="smsLog.type.${type}"/></td>
						<c:forEach var="status" items="${statusList}">
							<td class="tdPrintData" style="text-align:right"><cyclos:format number="${totals[type][status]}"/></td>
						</c:forEach>
						<td class="tdPrintData" style="text-align:right"><b><cyclos:format number="${totalsByType[type]}"/></b></td>
	         				</tr>
				</c:forEach>
				<tr>
	        					<td class="tdPrintHeader"><b><bean:message key="global.total"/></b></td>
					<c:forEach var="status" items="${statusList}">
						<td class="tdPrintData" style="text-align:right"><b><cyclos:format number="${totalsByStatus[status]}"/></b></td>
					</c:forEach>
					<td class="tdPrintData" style="text-align:right"><b><cyclos:format number="${total}"/></b></td>
				</tr>
        	</table>
        	<br>
			
			<table class="tablePrint">
                <tr>
                    <td class="tdPrintHeader"><bean:message key="smsLog.date"/></td>
                    <td class="tdPrintHeader"><bean:message key="member.member"/></td>
					<td class="tdPrintHeader" align="center"><bean:message key="message.type"/></td>
					<td class="tdPrintHeader" align="center"><bean:message key="smsLog.free"/></td>
					<td class="tdPrintHeader" align="center"><bean:message key="smsLog.status"/></td>
                </tr>				

                <c:set var="ownerProperty" value="${localSettings.memberResultDisplay.property}"/>
				<c:forEach var="log" items="${smsLogs}" varStatus="counter">
					<c:if test="${ localSettings.maxIteratorResults == 0 || counter.index < localSettings.maxIteratorResults }">
		                <tr>
		                    <td class="tdPrintData" align="center"><cyclos:format dateTime="${log.date}"/></td>
		                    <td class="tdPrintData" align="center">${log.targetMember[ownerProperty]}</td>
		                    <td class="tdPrintData" align="center">			
		                    	<bean:message key="smsLog.type.${log.type}"/>			                                        	
		                    	<c:choose>
		                    		<c:when test="${not empty log.smsMailing}">(<bean:message key="smsMailing.mailingType.${log.smsMailing.type}"/>)</c:when>
		                    		<c:when test="${not empty log.messageType}">(<bean:message key="message.type.${log.messageType}"/>)</c:when>
		                    		<c:when test="${not empty log.smsType}">
		                    			<c:set var="smsTypeValue">
			                    			<bean:message key="sms.type.${log.smsType.code}.description" arg0="${log.arg0}" arg1="${log.arg1}" arg2="${log.arg2}" arg3="${log.arg3}" arg4="${log.arg4}"/>
			                    		</c:set>
		                    			(<cyclos:escapeHTML value="${smsTypeValue}"/>)
		                    		</c:when>
		                    	</c:choose>
		                    </td>
		                    <td class="tdPrintData" align="center">
		                    	<bean:message key="global.${log.free ? 'yes' : 'no'}"/>
		                    </td>
		                    <td class="tdPrintData" align="center">
		                    	<c:choose>	                    	
		                    		<c:when test="${not empty log.errorType}"><bean:message key="sms.error.type.${log.errorType}"/></c:when>
		                    		<c:otherwise>
				                    	<bean:message key="smsLog.status.DELIVERED"/>
		                    		</c:otherwise>
		                    	</c:choose>
		                    </td>
		                </tr>
		                <cyclos:clearCache/>
	                </c:if>
					<c:if test="${ localSettings.maxIteratorResults > 0 && counter.index == localSettings.maxIteratorResults }">
					   	<tr>
					 		<td class="tdPrintHeader" colspan="5"><bean:message key="reports.print.limitation" arg0="${localSettings.maxIteratorResults}"/></td>
					  	</tr>
				 	</c:if>
	            </c:forEach>

			</table>
		</td>
	</tr>
</table>
