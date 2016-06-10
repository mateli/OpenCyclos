<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<script>
	var disableSmsConfirmation = "<cyclos:escapeJS><bean:message key="notificationPreferences.disableSms.confirmation"/></cyclos:escapeJS>";
</script>
<cyclos:script src="/pages/members/preferences/notificationPreferences.js" />

<c:set var="columnWidth" value='${smsEnabled ? "15%" : "20%"}'/>

<ssl:form method="post" action="${formAction}">
<c:if test="${loggedElement.id != member.id}">
	<html:hidden styleId="memberId" property="memberId"/>
</c:if>
<c:choose>
	<c:when test="${loggedElement.id == member.id}">
		<c:set var="title"><bean:message key="notificationPreferences.my.title.name"/></c:set>	
	</c:when>
	<c:otherwise>
		<c:set var="title"><bean:message key="notificationPreferences.title.name" arg0="${member.name}"/></c:set>
	</c:otherwise>
</c:choose>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">${title}</td>
        <cyclos:help page="preferences#notification_preferences"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists" style="padding-bottom:5px">
            <table class="defaultTable">
            	<tr> 
	            	<td class="tdHeaderContents" width="55%">&nbsp;</td>
                    <td class="tdHeaderContents" width="${columnWidth}"><bean:message key="notificationPreferences.email"/></td>
					<td class="tdHeaderContents" width="${columnWidth}"><bean:message key="notificationPreferences.message"/></td>
					<c:if test="${smsEnabled and hasSmsMessages}">
						<td class="tdHeaderContents" width="${columnWidth}"><bean:message key="notificationPreferences.sms"/></td>
					</c:if>
				</tr>
				<c:forEach var="type" items="${types}">
					<c:set var="typeSmsEnabled" value="${cyclos:contains(smsEnabledTypes, type)}"/>
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                <td><bean:message key="message.type.${type}"/></td>
	                    <c:choose><c:when test="${hasEmail}">
	                    	<td width="${columnWidth}" align="center"><html:checkbox property="notificationPreference(${type}_email)" disabled="true" styleClass="checkbox InputBoxDisabled email"/></td>
	                    </c:when><c:otherwise>
	                    	<td width="${columnWidth}" align="center"><input type="checkbox" name="notEnabled" disabled="disabled" class="checkbox InputBoxDisabled"/></td>
	                    </c:otherwise></c:choose>
	                    <td width="${columnWidth}" align="center"><html:checkbox property="notificationPreference(${type}_message)" disabled="true" styleClass="checkbox InputBoxDisabled message"/></td>
	                    <c:if test="${smsEnabled and hasSmsMessages}">
	                    	<c:choose><c:when test="${typeSmsEnabled}">
	                    		<td width="${columnWidth}" align="center"><html:checkbox property="notificationPreference(${type}_sms)" disabled="true" styleClass="checkbox InputBoxDisabled sms smsCheck"/></td>
	                    	</c:when><c:otherwise>
	                    		<td width="${columnWidth}" align="center"></td>
	                    	</c:otherwise></c:choose>
	                    </c:if>
	                </tr>
                </c:forEach>
            	<tr>
	            	<td>&nbsp;</td>
                    <td align="center">
                    	<a class="default checkAll" messageType="email"><bean:message key="notificationPreferences.selectAll"/></a>
                    	<a class="default uncheckAll" disabled="true" messageType="email"><bean:message key="notificationPreferences.selectNone"/></a>
                    </td>
                    <td align="center">
                    	<a class="default checkAll" disabled="true" messageType="message"><bean:message key="notificationPreferences.selectAll"/></a>
                    	<a class="default uncheckAll" disabled="true" messageType="message"><bean:message key="notificationPreferences.selectNone"/></a>
                    </td>
					<c:if test="${smsEnabled and hasSmsMessages}">
                    <td align="center">
                    	<a class="default checkAll" disabled="true" messageType="sms"><bean:message key="notificationPreferences.selectAll"/></a>
                    	<a class="default uncheckAll" disabled="true" messageType="sms"><bean:message key="notificationPreferences.selectNone"/></a>
                    </td>
					</c:if>
				</tr>
                <c:if test="${not hasEmail}">
                    <tr>
   	                	<td colspan="3" align="center" class="fieldDecoration"><bean:message key="notificationPreferences.hasntEmail"/></td>
                    </tr>
                </c:if>
                <c:if test="${smsEnabled}">
	                <tr>
	                	<td colspan="4" align="center">
	                		<fieldset>
	                			<legend><bean:message key="notificationPreferences.smsHeading"/></legend>
		                		<table cellpadding="0" cellspacing="0" width="100%">
		                			<c:if test="${canChangeChannelAccess}">
			                			<tr>
			                				<td><bean:message key="notificationPreferences.enableSmsOperations"/></td>
			                				<td width="39%"><html:checkbox property="enableSmsOperations" styleClass="smsCheck" value="true" disabled="true" /></td>
			                			</tr>
			                		</c:if>
		               				<c:if test="${additionalChargedSms != 1 or maxFreeSms != 0}">
			                			<tr>
		                					<td><bean:message key="${additionalChargedSms == 1 ? 'notificationPreferences.allowChargingSms' : 'notificationPreferences.allowChargingSmsPackage'}"/></td>
		                					<td width="39%"><html:checkbox property="allowChargingSms" styleClass="smsCheck" value="true" disabled="true" /></td>
			                			</tr>
		               				</c:if>
		                			<tr>
		                				<td><bean:message key="notificationPreferences.acceptFreeMailing"/></td>
		                				<td width="39%"><html:checkbox property="acceptFreeMailing" styleClass="smsCheck" value="true" disabled="true" /></td>
		                			</tr>
		                			<tr>
		                				<td><bean:message key="notificationPreferences.acceptPaidMailing"/></td>
		                				<td><html:checkbox property="acceptPaidMailing" styleClass="smsCheck" value="true" disabled="true" /></td>
		                			</tr>
		                			<tr>
		                				<td colspan="2">&nbsp;</td>
		                			</tr>
		                			<c:if test="${maxFreeSms > 0 && showFreeSms}">
			                			<tr>
			                				<td colspan="2"><bean:message key="notificationPreferences.freeSmsUsed" arg0="${smsStatus.freeSmsSent}" arg1="${maxFreeSms}"/></td>
			                			</tr>
			                		</c:if>
		                			<c:if test="${smsStatus.paidSmsLeft > 0}">
			                			<tr>
			                				<td colspan="2">
			                					<bean:message key="notificationPreferences.paidSmsLeft" arg0="${smsStatus.paidSmsLeft}">
			                						<jsp:attribute name="arg1"><cyclos:format rawDate="${smsStatus.paidSmsExpiration}"/></jsp:attribute>
			                					</bean:message>
			                				</td>
			                			</tr>
			                		</c:if>
		                			<c:choose>
		                				<c:when test="${additionalChargedSms == 1}">
				                			<tr>
				                				<td colspan="2">
				                					<bean:message key="notificationPreferences.costPerMessage">
			                							<jsp:attribute name="arg0"><cyclos:format number="${additionalChargeAmount}" unitsPattern="${additionalChargeCurrency.pattern}"/></jsp:attribute>
			                						</bean:message>
				                				</td>
				                			</tr>
				                		</c:when>
		                				<c:when test="${additionalChargedSms > 1}">
				                			<c:if test="${smsStatus.paidSmsLeft == 0}">
					                			<tr>
					                				<td colspan="2">
					                					<bean:message key="notificationPreferences.noPaidSmsLeft" />
					                				</td>
					                			</tr>
					                		</c:if>
				                			<tr>
				                				<td colspan="2">
				                					<bean:message key="notificationPreferences.costPerAdditionalMessages" arg0="${additionalChargedSms}">
			                							<jsp:attribute name="arg1"><cyclos:format number="${additionalChargeAmount}" unitsPattern="${additionalChargeCurrency.pattern}"/></jsp:attribute>
														<jsp:attribute name="arg2">${additionalChargePeriod.number} <bean:message key="global.timePeriod.${additionalChargePeriod.field}"/></jsp:attribute>			                							
			                						</bean:message>
				                				</td>
				                			</tr>
				                		</c:when>
			                		</c:choose>
		                		</table>
	                		</fieldset>
	                	</td>
	                </tr>
                </c:if>
                <tr>
                	<td>
                		<c:if test="${smsEnabled}">
                			<input type="button" id="disableSmsButton" class="button" style="display:none" value="<bean:message key='notificationPreferences.disableSms'/>">
                		</c:if>
                	</td>
					<td align="right" colspan="${smsEnabled ? 3 : 2}">
						<input type="button" id="modifyButton" class="button" value="<bean:message key='global.change'/>">
						&nbsp;
            	   		<input type="submit" id="submitButton" class="ButtonDisabled" disabled="true" value="<bean:message key='global.submit'/>" >
					</td>
	            </tr>

            </table>
        </td>
    </tr>
</table>

	<c:if test="${loggedElement.id != member.id}">
		
		<table class="defaultTableContentHidden">
	            <tr>
	            	<td>
	            		<input type="button" class="button" id="backButton" value="<bean:message key='global.back'/>">
	            	</td>
	            </tr>
		</table>
	</c:if>
</ssl:form>