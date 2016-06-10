<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/reports/members/sms/smsLogsReport.js" />

<ssl:form method="post" action="${formAction}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="reports.members.smsLog"/></td>
        <cyclos:help page="reports#sms_log_report"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
       			<tr>
         			<td class="label">
         				<span class="label"><bean:message key="smsLog.date"/></span>
         				<span class="label"><bean:message key="global.range.from"/></span>
         			</td>
         			<td colspan="3">
         				<html:text property="query(period).begin" styleClass="InputBoxEnabled date small"/>&nbsp;
         				<span class="label"><bean:message key="global.range.to"/></span>
         				<html:text property="query(period).end" styleClass="InputBoxEnabled date small"/>
         			</td>
       			</tr>
       			<tr>
           			<td class="label"><bean:message key="member.group"/></td>
           			<td colspan="3">
           				<cyclos:multiDropDown name="query(memberGroups)" varName="groupsSelect" emptyLabelKey="member.search.allGroups">
          					<c:forEach var="memberGroup" items="${memberGroups}">
          						<cyclos:option value="${memberGroup.id}" text="${memberGroup.name}" />
          					</c:forEach>
           				</cyclos:multiDropDown>
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
					<td width="50%">
						<input id="memberName" class="full" value="${query.member.name}">
						<div id="membersByName" class="autoComplete"></div>
					</td>
       			</tr>
				<tr>
					<td class="label"><bean:message key="smsLog.status"/></td>
					<td colspan="3">
						<html:select property="query(status)" styleClass="InputBoxEnabled">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach var="status" items="${statusList}">
								<html:option value="${status}"><bean:message key="smsLog.status.${status}"/></html:option>
							</c:forEach>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="smsLog.type"/></td>
					<td>
						<html:select property="query(type)" styleId="typeSelect" styleClass="InputBoxEnabled">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach var="type" items="${typesList}">
								<html:option value="${type}"><bean:message key="smsLog.type.${type}"/></html:option>
							</c:forEach>
						</html:select>
					</td>
					<td class="label">
						<div class="smsLogType smsLogType-MAILING" style="display:none">
							<bean:message key="smsLog.mailingType"/>
						</div>
						<div class="smsLogType smsLogType-NOTIFICATION" style="display:none">
							<bean:message key="smsLog.messageType"/>
						</div>
						<div class="smsLogType smsLogType-SMS_OPERATION" style="display:none">
							<bean:message key="smsLog.smsType"/>
						</div>
					</td>
					<td>
						<div class="smsLogType smsLogType-MAILING" style="display:none">
		           			<cyclos:multiDropDown varName="mailingTypes" name="query(mailingTypes)" emptyLabelKey="global.search.all">
								<c:forEach var="mailingType" items="${mailingTypes}">
									<c:set var="label"><bean:message key="smsMailing.mailingType.${mailingType}"/></c:set>
									<cyclos:option value="${mailingType}" text="${label}" selected="${cyclos:contains(query.mailingTypes, mailingType)}" />
								</c:forEach>					
							</cyclos:multiDropDown>	
							<input type="hidden" name="query(mailingTypes)" value="" />
						</div>
						<div class="smsLogType smsLogType-NOTIFICATION" style="display:none">
		           			<cyclos:multiDropDown varName="messageTypes" name="query(messageTypes)" emptyLabelKey="global.search.all">
								<c:forEach var="messageType" items="${messagesTypes}">
									<c:set var="label"><bean:message key="message.type.${messageType}"/></c:set>
									<cyclos:option value="${messageType}" text="${label}" selected="${cyclos:contains(query.messageTypes, messageType)}" />
								</c:forEach>					
							</cyclos:multiDropDown>
							<input type="hidden" name="query(messageTypes)" value="" />	
						</div>
						<div class="smsLogType smsLogType-SMS_OPERATION" style="display:none">
							<cyclos:multiDropDown varName="smsTypes" name="query(smsTypes)" emptyLabelKey="global.search.all">
								<c:forEach var="smsType" items="${smsTypes}">
									<c:set var="label"><bean:message key="sms.type.${smsType.code}"/></c:set>
									<cyclos:option value="${smsType.id}" text="${label}" selected="${cyclos:contains(query.smsTypes, smsType)}" />
								</c:forEach>					
							</cyclos:multiDropDown>
							<input type="hidden" name="query(smsTypes)" value="" />
						</div>
					</td>						
				</tr>
          		<tr>
					<td colspan="4" align="right">
						<input type="submit" class="button" value="<bean:message key="global.search"/>">
					</td>
				</tr>
        	</table>
        </td>
    </tr>
</table>
</ssl:form>

<c:if test="${queryExecuted}">
	<br/>
	<c:choose><c:when test="${empty smsLogs}">
		<div class="footerNote" helpPage="reports#sms_log_report_search_results"><bean:message key="smsLog.search.noResults"/></div>
	</c:when><c:otherwise>
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
	        		<img class="exportCSV" src="<c:url value="/pages/images/save.gif"/>" border="0">
	        		<img class="print" src="<c:url value="/pages/images/print.gif"/>" border="0">
			        <cyclos:help page="reports#sms_log_report_search_results" td="false"/>
		        </td>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable" cellspacing="0" cellpadding="0">
		            	<tr>
		            		<td colspan="5" align="center">
		            			<table>
		            				<tr>
		            					<td></td>
										<c:forEach var="status" items="${statusList}">
											<td class="headerLabel" width="70"><bean:message key="smsLog.status.${status}"/></td>
										</c:forEach>
										<td class="headerLabel" width="70"><bean:message key="global.total"/></td>
									</tr>
									<c:forEach var="type" items="${typesList}">
			            				<tr>
			            					<td class="headerLabel" nowrap="nowrap"><bean:message key="smsLog.type.${type}"/></td>
											<c:forEach var="status" items="${statusList}">
												<td align="right" class="headerField"><cyclos:format number="${totals[type][status]}"/></td>
											</c:forEach>
											<td align="right" class="headerField"><b><cyclos:format number="${totalsByType[type]}"/></b></td>
			            				</tr>
									</c:forEach>
									<tr>
		            					<td class="headerLabel"><b><bean:message key="global.total"/></b></td>
										<c:forEach var="status" items="${statusList}">
											<td align="right" class="headerField"><b><cyclos:format number="${totalsByStatus[status]}"/></b></td>
										</c:forEach>
										<td align="right" class="headerField"><b><cyclos:format number="${total}"/></b></td>
									</tr>
		            			</table>
		            			
		            		</td>
		            	</tr>
		                <tr>
		                    <td class="tdHeaderContents"><bean:message key="smsLog.date"/></td>
		                    <td class="tdHeaderContents"><bean:message key="member.member"/></td>
							<td class="tdHeaderContents" align="center"><bean:message key="message.type"/></td>
							<td class="tdHeaderContents" align="center"><bean:message key="smsLog.free"/></td>
							<td class="tdHeaderContents" align="center"><bean:message key="smsLog.status"/></td>
		                </tr>
		                <c:set var="ownerProperty" value="${localSettings.memberResultDisplay.property}"/>
						<c:forEach var="log" items="${smsLogs}">
			                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			                    <td align="center"><cyclos:format dateTime="${log.date}"/></td>
			                    <td align="center"><cyclos:profile elementId="${log.targetMember.id}"/></td>
			                    <td align="center">			
			                    	<bean:message key="smsLog.type.${log.type}"/><br/>
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
			                    <td align="center">
			                    	<bean:message key="global.${log.free ? 'yes' : 'no'}"/>
			                    </td>
			                    <td align="center">
			                    	<c:choose>	                    	
			                    		<c:when test="${not empty log.errorType}"><bean:message key="sms.error.type.${log.errorType}"/></c:when>
			                    		<c:otherwise>
					                    	<bean:message key="smsLog.status.DELIVERED"/>
			                    		</c:otherwise>
			                    	</c:choose>
			                    </td>
			                </tr>
			            </c:forEach>
		            </table>
		        </td>
		    </tr>
		</table>
		
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right"><cyclos:pagination items="${smsLogs}"/></td>
			</tr>
		</table>		
	</c:otherwise></c:choose>
</c:if>
