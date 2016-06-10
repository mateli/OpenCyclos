<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:set var="pinLabel"><bean:message key="login.pin"/></c:set>
<c:set var="pinLength" value="${member.group.memberSettings.pinLength}"/>
<script>
	var memberId = "${member.id}";
</script>
<cyclos:script src="/pages/access/channels/manageExternalAccess.js" />
<c:if test="${canChangePin}">
	<ssl:form styleId="changePinForm" method="post" action="${actionPrefix}/changePin">
	<html:hidden property="memberId" value="${member.id}"/>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable">
	        	<c:choose><c:when test="${myAccess}">
	        		<bean:message key="changePin.title.my"/>
	        	</c:when><c:otherwise>
	        		<bean:message key="changePin.title.of" arg0="${member.name}"/>
	        	</c:otherwise></c:choose>
	        </td>
	        <cyclos:help page="passwords#change_pin"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableForms">
	        	<c:choose>
	        		<c:when test="${transactionPasswordBlocked}">
	        			<div align="center">
	        				<cyclos:escapeHTML brOnly="true"><bean:message key="changePin.error.transactionPasswordBlocked" /></cyclos:escapeHTML>
	        			</div>
	        		</c:when>
	        		<c:when test="${transactionPasswordPending}">
	        			<div align="center">
		        			<c:url value="${pathPrefix}/home" var="homeUrl" />
		        			<bean:message key="changePin.error.transactionPasswordPending" arg0="${homeUrl}" />
		        		</div>
	        		</c:when>
	        		<c:otherwise>
			            <table class="defaultTable">
							<c:if test="${myAccess}">
								<c:choose><c:when test="${usesTransactionPassword}">
					                <tr>
					                     <td class="label"><bean:message key="login.transactionPassword"/></td>
					                     <td>
					                     	<c:set var="tpAlign" value="left" scope="request" />
											<c:set var="hideSubmit" value="${true}" scope="request" />
											<c:set var="transactionPasswordField" value="credentials" scope="request" /> 
											<jsp:include page="/do/transactionPassword"/>
					                     </td>
					                </tr>
						       	</c:when><c:otherwise>
					                <tr>
					                     <td class="label"><bean:message key="changePassword.currentPassword"/></td>
					                     <td><input type="password" name="credentials" class="medium"></td>
					                </tr>
						       	</c:otherwise></c:choose>
			                </c:if>
							<tr>
								<td colspan="2" align="center">
									<br/>
									<c:choose><c:when test="${pinLength.min == pinLength.max}">
										<bean:message key="changePin.pinLength" arg0="${pinLength.min}"/>
									</c:when><c:otherwise>
										<bean:message key="changePin.pinLengthRange" arg0="${pinLength.min}" arg1="${pinLength.max}"/>
									</c:otherwise></c:choose>
									<br/><br/>
								</td>
							</tr>
			                <tr>
			                    <td class="label"><bean:message key="changePin.newPin"/></td>
			                    <td><input type="password" name="newPin" class="medium digits"></td>
			                </tr>
			                <tr>
			                    <td class="label"><bean:message key="changePin.newPinConfirmation"/></td>
			                    <td><input type="password" name="newPinConfirmation" class="medium digits"></td>
			                </tr>
			                <tr>
			                    <td colspan="2" align="right">
			                    	<input type="submit" class="button" value="<bean:message key="global.submit"/>">
			                    </td>
			                </tr>
			            </table>
	        		</c:otherwise>
	        	</c:choose>
	        </td>
	    </tr>
	</table>
	</ssl:form>
</c:if>
<c:if test="${canUnblockPin}">
	<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
		<tr>
			<td align="right">
				<span class="label"><bean:message key="pin.unblock.message" /></span> 
				<input type="button" class="button" id="unblockPinButton" value="<bean:message key="pin.unblock.button"/>">
			</td>
		</tr>
	</table>
</c:if>
<c:if test="${canChangeChannelAccess and not empty channels}">
	<ssl:form styleId="selectChannelsForm" method="post" action="${actionPrefix}/selectChannels">
	<html:hidden property="member.id" value="${member.id}"/>
	<html:hidden property="memberId" value="${member.id}"/>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable">
	        	<c:choose><c:when test="${myAccess}">
	        		<bean:message key="selectChannels.title.my"/>
	        	</c:when><c:otherwise>
	        		<c:choose><c:when test="${canChangeChannelAccess}">
	        			<bean:message key="selectChannels.title.of" arg0="${member.name}"/>
	        		</c:when><c:otherwise>
	        			<bean:message key="viewChannels.title.of" arg0="${member.name}"/>
	        		</c:otherwise></c:choose>
	        	</c:otherwise></c:choose>
	        </td>
	        <cyclos:help page="passwords#select_channels"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableForms">
	            <table class="defaultTable">
	                <tr>
	                    <td class="label"><bean:message key="selectChannels.channels"/></td>
	                    <td>
							<c:forEach var="channel" items="${channels}">
								<label>
									<c:set var="checked" value="${cyclos:contains(member.channels, channel) ?  \"checked='checked'\" : \"\" }"/>
			                   		<input type="checkbox" name="channels" class="checkbox" disabled="true" value="${channel.id}" ${checked} /> ${channel.displayName}<br/>
								</label>
		                   	</c:forEach>
						</td>
	                </tr>
	                <c:if test="${canManagePreferences}">
		                <tr>
		                	<td class="label">
		                	</td>
		                	<td style="padding-top: 10px">
		                		<bean:message key="channel.enableSMS"/>&nbsp;<a class="default"><bean:message key="channel.enableSMSLinkTo"/></a>
		                	</td>	
		                </tr>
					</c:if>		                
			        <c:if test="${canChangeChannelAccess}">
			            <table class="defaultTable">
			                <tr>
								<td colspan="2" align="right">
									<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">
									&nbsp;
									<input type="submit" id="saveButton" disabled="disabled" class="ButtonDisabled" value="<bean:message key="global.submit"/>">
								</td>
			                </tr>
						</table>
					</c:if>
	            </table>
	        </td>
	    </tr>
	</table>
	</ssl:form>
</c:if>
<c:if test="${not myAccess}">
	<table class="defaultTableContentHidden"><tr><td>
	<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
	</td></tr></table>
</c:if>