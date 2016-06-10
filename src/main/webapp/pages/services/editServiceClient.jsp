<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/services/editServiceClient.js" />
<script>
var channelsById = {};
var emptyPassword = ${empty serviceClient.password};
</script>
<ssl:form method="post" action="${formAction}">
<html:hidden property="clientId"/>
<html:hidden property="serviceClient(id)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="serviceClient.title.${serviceClient['transient'] ? 'insert' : 'modify'}"/></td>
        <cyclos:help page="settings#web_services_clients_detail"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                   	<td class="label" width="25%"><bean:message key="serviceClient.name"/></td>
                   	<td align="left" colspan="3"><html:text property="serviceClient(name)" styleClass="full required InputBoxDisabled" readonly="true"/></td>
            	</tr>
            	<%-- Only the hostname is typed. The IP address is resolved --%>
                <tr>
                   	<td class="label"><bean:message key="serviceClient.address"/></td>
                   	<td align="left" colspan="3"><html:text property="serviceClient(hostname)" styleClass="full required InputBoxDisabled" readonly="true" /></td>
            	</tr>
                <tr>
                   	<td class="label"><bean:message key="serviceClient.channel"/></td>
                   	<td align="left" colspan="3">
                   		<html:select styleId="channelSelect" property="serviceClient(channel)" styleClass="InputBoxDisabled" disabled="true">
                   			<html:option value=""></html:option>
                   			<c:forEach var="channel" items="${channels}">
                   				<html:option value="${channel.id}">${channel.displayName}</html:option>
                   				<script>
                   					channelsById["${channel.id}"] = "${channel.internalName}";
                   				</script>
                   			</c:forEach>
                   		</html:select>
                   	</td>
            	</tr>
				<tr>
					<td class="label"><bean:message key='serviceClient.member'/></td>
					<td width="20%">
						<div style="position:relative; width:96%">
							<html:hidden styleId="memberId" property="serviceClient(member)" />
							<input id="memberUsername" class="full InputBoxDisabled" disabled="disabled" value="${serviceClient.member.username}">
							<div id="membersByUsername" class="autoComplete"></div>
						</div>
					</td>
					<td class="label" width="10%" nowrap="nowrap"><bean:message key='member.name'/></td>
					<td>
						<div style="position:relative; width:96%">
							<input id="memberName" class="full InputBoxDisabled" disabled="disabled" value="${serviceClient.member.name}">
							<div id="membersByName" class="autoComplete"></div>
						</div>
					</td>
				</tr>
                <tr>
                   	<td class="label"><bean:message key="serviceClient.username"/></td>
                   	<td align="left" colspan="3"><html:text property="serviceClient(username)" styleClass="medium InputBoxDisabled" readonly="true"/></td>
            	</tr>
                <tr>
                   	<td class="label"><bean:message key="serviceClient.password"/></td>
                   	<td align="left" colspan="3"><html:password property="serviceClient(password)" styleClass="medium InputBoxDisabled" readonly="true"/></td>
            	</tr>
				<tr id="tr_credentials_required" class="operationTR" style="display:none">
	 				<td class="label" nowrap="nowrap" ><label for="chk_credentialsRequired"><bean:message key="serviceClient.credentialsRequired"/></label></td>
	 				<td><input type="checkbox" disabled="disabled" class="checkbox" id="chk_credentialsRequired" name="serviceClient(credentialsRequired)" value="true" <c:if test="${serviceClient.credentialsRequired}">checked="checked"</c:if> /></td>
				</tr>
                <tr>
                   	<td class="label" valign="top"><bean:message key="serviceClient.permissions"/></td>
                   	<td align="left" colspan="3">
                   		<table class="nested">
                   		<c:forEach var="operation" items="${operations}">
                   			<c:choose>
                   			
                   				<c:when test="${cyclos:name(operation) == 'DO_PAYMENT'}">
                   					<tr id="tr_${operation}" style="display:none" class="operationTR">
										<td></td>
										<td>
											<table class="nested" width="100%">
												<tr>
													<td width="30%" nowrap="nowrap"><bean:message key="serviceOperation.${operation}"/></td>
													<td>
														<cyclos:multiDropDown varName="doPaymentTypes" name="serviceClient(doPaymentTypes)" size="5" disabled="true">
															<c:forEach var="tt" items="${doPaymentTypes}">
																<cyclos:option value="${tt.id}" text="${tt.name}" selected="${cyclos:contains(serviceClient.doPaymentTypes, tt)}" />
															</c:forEach>
														</cyclos:multiDropDown>
													</td>
												</tr>
											</table>
										</td>
									</tr>
                   				</c:when>
                   				
                   				<c:when test="${cyclos:name(operation) == 'RECEIVE_PAYMENT'}">
                   					<tr id="tr_${operation}" style="display:none" class="operationTR">
										<td></td>
										<td>
											<table class="nested" width="100%">
												<tr>
													<td width="30%" nowrap="nowrap"><bean:message key="serviceOperation.${operation}"/></td>
													<td>
														<cyclos:multiDropDown varName="receivePaymentTypes" name="serviceClient(receivePaymentTypes)" size="5" disabled="true">
															<c:forEach var="tt" items="${receivePaymentTypes}">
																<cyclos:option value="${tt.id}" text="${tt.name}" selected="${cyclos:contains(serviceClient.receivePaymentTypes, tt)}" />
															</c:forEach>
														</cyclos:multiDropDown>
													</td>
												</tr>
											</table>
										</td>
									</tr>
                   				</c:when>
                   				
                   				<c:when test="${cyclos:name(operation) == 'CHARGEBACK'}">
                   					<tr id="tr_${operation}" style="display:none" class="operationTR">
										<td></td>
										<td>
											<table class="nested" width="100%">
												<tr>
													<td width="30%" nowrap="nowrap"><bean:message key="serviceOperation.${operation}"/></td>
													<td>
														<cyclos:multiDropDown varName="chargebackPaymentTypes" name="serviceClient(chargebackPaymentTypes)" size="5" disabled="true">
															<c:forEach var="tt" items="${chargebackPaymentTypes}">
																<cyclos:option value="${tt.id}" text="${tt.name}" selected="${cyclos:contains(serviceClient.chargebackPaymentTypes, tt)}" />
															</c:forEach>
														</cyclos:multiDropDown>
													</td>
												</tr>
											</table>
										</td>
									</tr>
                   				</c:when>
                   				
                   				<c:when test="${cyclos:name(operation) == 'MANAGE_MEMBERS'}">
                   					<tr id="tr_${operation}" style="display:none" class="operationTR">
										<td></td>
										<td>
											<table class="nested" width="100%">
												<tr>
													<td width="30%" nowrap="nowrap"><bean:message key="serviceOperation.${operation}"/></td>
													<td>
														<cyclos:multiDropDown varName="manageGroups" name="serviceClient(manageGroups)" onchange="updateManageGroups()" size="5" disabled="true">
															<c:forEach var="group" items="${memberGroups}">
																<cyclos:option value="${group.id}" text="${group.name}" selected="${cyclos:contains(serviceClient.manageGroups, group)}" />
															</c:forEach>
														</cyclos:multiDropDown>
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr id="tr_ignoreRegistrationValidations" style="display:none">
										<td>
											<html:checkbox property="serviceClient(ignoreRegistrationValidations)" styleId="chk_ignoreRegistrationValidations" styleClass="checkbox" disabled="true" value="true" />
										</td>
										<td>
											<label for="chk_ignoreRegistrationValidations"><bean:message key="serviceClient.ignoreRegistrationValidations"/></label>
										</td>
									</tr>

                   				</c:when>
                   				
                   				<c:otherwise>
									<%-- The SMS operation only appears if SMS notifications are enabled. Others always appear --%>
                   					<c:if test="${cyclos:name(operation) != 'SMS' || localSettings.smsEnabled}">
	                   					<tr id="tr_${operation}" class="operationTR" style="display:none">
			                   				<td><input type="checkbox" disabled="disabled" class="checkbox" id="chk_${operation}" name="serviceClient(permissions)" value="${operation}" <c:if test="${cyclos:contains(serviceClient.permissions, operation)}">checked="checked"</c:if> /></td>
			                   				<td><label for="chk_${operation}"><bean:message key="serviceOperation.${operation}"/></label></td>
	                   					</tr>
                   					</c:if>
                   				</c:otherwise>
                   			</c:choose>
                   		</c:forEach>
                   		</table>
                   	</td>
            	</tr>
                <tr>
                  	<td colspan="4" align="right">
						<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">
						&nbsp;
						<input type="submit" id="saveButton" class="ButtonDisabled" disabled="true" value="<bean:message key="global.submit"/>">
                   	</td>
            	</tr>
            </table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td><input type="button" value="<bean:message key="global.back"/>" class="button" id="backButton"></td>
	</tr>
</table>

</ssl:form>
