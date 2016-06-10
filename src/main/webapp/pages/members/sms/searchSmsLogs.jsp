<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/sms/searchSmsLogs.js" />

<c:choose><c:when test="${mySmsLogs}">
	<c:set var="titleKey" value="smsLog.title.searchMy"/>
	<c:set var="arg0" value=""/>
</c:when><c:otherwise>
	<c:set var="titleKey" value="smsLog.title.search"/>
	<c:set var="arg0" value="${member.name}"/>
</c:otherwise></c:choose>

<ssl:form method="post" action="${formAction}">
<html:hidden styleId="memberId" property="query(member)"/>
<!-- this hidden is neccesary by the permission's control in the BaseAction -->
<input type="hidden" name="memberId" value="${member.id}"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}" arg0="${arg0}"/></td>
        <cyclos:help page="reports#sms_log"/>
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
					<td class="label"><bean:message key="smsLog.status"/></td>
					<td>
						<html:select styleId="status" property="query(status)" styleClass="InputBoxEnabled">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach var="status" items="${statusList}">
								<html:option value="${status}"><bean:message key="smsLog.status.${status}"/></html:option>
							</c:forEach>
						</html:select>
					</td>
           			<td class="label"><bean:message key="smsLog.type"/></td>
           			<td>
           				<html:select property="query(type)" styleClass="InputBoxEnabled" styleId="typeSelector">
							<html:option value=""><bean:message key="global.search.all.male"/></html:option>
							<c:forEach var="type" items="${typesList}">
								<html:option value="${type}"><bean:message key="smsLog.type.${type}"/></html:option>
							</c:forEach>
						</html:select>
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
		<div class="footerNote" helpPage="reports#sms_log_search_results"><bean:message key="smsLog.search.noResults"/></div>
	</c:when><c:otherwise>
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
			        <cyclos:help page="reports#sms_log_search_results" td="false"/>
		        </td>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable" cellspacing="0" cellpadding="0">
		                <tr>
		                    <td class="tdHeaderContents" width="20%"><bean:message key="smsLog.date"/></td>
							<td class="tdHeaderContents" align="center"><bean:message key="message.type"/></td>
							<td class="tdHeaderContents" align="center"><bean:message key="smsLog.free"/></td>
							<td class="tdHeaderContents" align="center"><bean:message key="smsLog.status"/></td>
		                </tr>
						<c:forEach var="log" items="${smsLogs}">
			                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			                    <td align="center"><cyclos:format dateTime="${log.date}"/></td>
			                    <td align="center">			
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
			                    <td align="center">
			                    	<c:choose>
			                    		<c:when test="${log.chargedMember == member}">
			                    			<c:set var="free" value="${log.free}"/>
			                    		</c:when>
			                    		<c:otherwise>
			                    			<c:set var="free" value="${true}"/>
			                    		</c:otherwise>
			                    	</c:choose>
			                    	<bean:message key="global.${free ? 'yes' : 'no'}"/>
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

<c:if test="${not mySmsLogs}">
	<table class="defaultTableContentHidden">
		<tr>
			<td>
				<input type="button" class="button" id="backButton" value="<bean:message key='global.back'/>">
			</td>
		</tr>
	</table>
</c:if>