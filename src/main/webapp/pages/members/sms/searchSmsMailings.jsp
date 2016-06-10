<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/sms/searchSmsMailings.js" />

<ssl:form method="post" action="${formAction}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="smsMailing.title.search"/></td>
        <cyclos:help page="messages#sms_mailings"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
       			<tr>
         			<td class="label" width="30%"><bean:message key="smsMailing.sendType"/></td>
         			<td>
         				<label>
	         				<html:radio property="query(recipient)" styleClass="radio recipient" value=""/>
	         				<bean:message key="global.search.all"/>
         				</label>
         				&nbsp;
         				<label>
	         				<html:radio property="query(recipient)" styleClass="radio recipient" value="GROUPS"/>
	         				<c:choose>
	         					<c:when test="${isAdmin}">
	         						<bean:message key="smsMailing.sendType.group"/> &nbsp;
	         					</c:when>
	         					<c:when test="${isBroker}">
	         						<bean:message key="smsMailing.broker.all"/> &nbsp;
	         					</c:when>
	         				</c:choose>
	         				
         				</label>
         				&nbsp;
         				<label>
	         				<html:radio property="query(recipient)" styleClass="radio recipient" value="MEMBER"/>
	         				<bean:message key="smsMailing.sendType.member"/>
         				</label>
         			</td>
				</tr>
				<c:if test="${isAdmin}">
					<tr class="trGroup" style="display:none">
	         			<td class="label"><bean:message key="member.group"/></td>
	         			<td>
	         				<html:select property="query(group)">
	         					<html:option value=""><bean:message key="global.search.all"/></html:option>
	         					<c:forEach var="group" items="${groups}">
	         						<html:option value="${group.id}">${group.name}</html:option>
	         					</c:forEach>
							</html:select>
	         			</td>
					</tr>
				</c:if>
	            <tr class="trMember" style="display:none;">
  		            <td nowrap="nowrap" class="label"><bean:message key="member.username" /></td>
	            	<td>
						<html:hidden styleId="memberId" property="query(member)"/>
						<input id="memberUsername" class="medium" value="${query.member.username}"/>
						<div id="membersByUsername" class="autoComplete"></div>
	            	</td>
	            </tr>
	            <tr class="trMember" style="display:none;">
  		            <td nowrap="nowrap" class="label"><bean:message key="member.memberName" /></td>
	            	<td>
						<input id="memberName" class="full" value="${query.member.name}" />
						<div id="membersByName" class="autoComplete"></div>
	            	</td>
	            </tr>			        					
				<tr>
         			<td class="label">
         				<span class="label"><bean:message key="smsMailing.date"/></span>
         				<span class="label"><bean:message key="global.range.from"/></span>
         			</td>
         			<td colspan="3">
         				<html:text property="query(period).begin" styleClass="InputBoxEnabled date small"/>&nbsp;
         				<span class="label"><bean:message key="global.range.to"/></span>
         				<html:text property="query(period).end" styleClass="InputBoxEnabled date small"/>
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

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<c:if test="${param.fromQuickAccess}">
			<td align="left">
				<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
			</td>
		</c:if>
		<c:if test="${canSend}">
			<td align="right">
				<span class="label"><bean:message key="smsMailing.new"/></span>
				<input type="button" id="newButton" class="button" value="<bean:message key="global.submit"/>">
			</td>
		</c:if>
	</tr>
</table>


<c:if test="${queryExecuted}">
	<br/>
	<c:choose><c:when test="${empty smsMailings}">
		<div class="footerNote" helpPage="messages#sms_mailings_results"><bean:message key="smsMailing.search.noResults"/></div>
	</c:when><c:otherwise>
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
			        <cyclos:help page="messages#sms_mailings_results" td="false"/>
		        </td>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable" cellspacing="0" cellpadding="0">
		                <tr>
		                    <td class="tdHeaderContents"><bean:message key="smsMailing.date"/></td>
		                    <c:if test="${isAdmin}">
		                    	<td class="tdHeaderContents"><bean:message key="smsMailing.by"/></td>
		                    </c:if>
							<c:if test="${viewFree && viewPaid}">
								<td class="tdHeaderContents" align="center"><bean:message key="smsMailing.type"/></td>
							</c:if>
							<td class="tdHeaderContents" align="center"><bean:message key="smsMailing.text"/></td>
	                    	<td nowrap="nowrap" class="tdHeaderContents"><bean:message key="smsMailing.recipients"/></td>
	                    	<c:if test="${cyclos:name(query.recipient) != 'MEMBER'}">
								<td class="tdHeaderContents" align="center"><bean:message key="smsMailing.sentSms"/></td>
	                    	</c:if>
		                </tr>
						<c:forEach var="mailing" items="${smsMailings}">
			                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			                    <td align="center"><cyclos:format dateTime="${mailing.date}"/></td>
			                    <c:if test="${isAdmin}">
			                    	<td align="center">${mailing.by.name}</td>
			                    </c:if>
			                    <c:if test="${viewFree && viewPaid}">
			                    	<td align="center"><bean:message key="smsMailing.type.${mailing.free ? 'FREE' : 'PAID'}"/></td>
			                    </c:if>
			                    <td>${mailing.text}</td>
		                    	<td align="center">
		                    		<c:choose>
		                    			<c:when test="${not empty mailing.member}">
		                    				<cyclos:profile elementId="${mailing.member.id}"/>
		                    			</c:when>
		                    			<c:when test="${isBroker}">
		                    				<bean:message key="smsMailing.broker.all"/>
		                    			</c:when>
		                    			<c:when test="${mailing.by.group.nature == 'BROKER'}">
		                    				<bean:message key="smsMailing.byBroker.all"/>
		                    			</c:when>
										<c:when test="${isAdmin}">
											<c:if test="${fn:length(mailing.groups) > 0}">
					                    			<c:forEach var="group" varStatus="status" items="${mailing.groups}">
					                    				${group.name}<c:if test="${!status.last}">,</c:if>
					                    			</c:forEach>
											</c:if>									
										</c:when>
									</c:choose>									
		                    	</td>
		                    	<c:if test="${cyclos:name(query.recipient) != 'MEMBER'}">
				                    <td align="center">
				                    	<c:choose>
				                    		<c:when test="${mailing.finished}">${mailing.sentSms}</c:when>
				                    		<c:otherwise><bean:message key="smsMailing.stillSending"/></c:otherwise>
				                    	</c:choose>
				                    </td>
				                </c:if>
			                </tr>
			            </c:forEach>
		            </table>
		        </td>
		    </tr>
		</table>
		
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right"><cyclos:pagination items="${smsMailings}"/></td>
			</tr>
		</table>		
	</c:otherwise></c:choose>
</c:if>

