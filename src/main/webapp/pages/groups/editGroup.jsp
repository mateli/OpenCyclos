<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<cyclos:script src="/pages/groups/editGroup.js" />

<script language="JavaScript">
	var isInsert = ${isInsert};
</script>

<c:choose>
	<c:when test="${empty group or empty group.id}">
		<c:set var="titleKey" value="group.title.new"/>
		<c:set var="helpPage" value="${isMember ? 'operators#insert_operator_group' : 'groups#insert_group'}"/>
	</c:when>
	<c:when test="${cyclos:name(group.nature) == 'ADMIN'}">
		<c:set var="titleKey" value="group.title.modify.admin"/>
		<c:set var="helpPage" value="groups#edit_admin_group"/>
	</c:when>
	<c:when test="${cyclos:name(group.nature) == 'MEMBER'}">
		<c:set var="titleKey" value="group.title.modify.member"/>
		<c:set var="helpPage" value="groups#edit_member_group"/>
	</c:when>
	<c:when test="${cyclos:name(group.nature) == 'BROKER'}">
		<c:set var="titleKey" value="group.title.modify.broker"/>
		<c:set var="helpPage" value="groups#edit_broker_group"/>
	</c:when>
	<c:when test="${cyclos:name(group.nature) == 'OPERATOR'}">
		<c:set var="titleKey" value="group.title.modify.operator"/>
		<c:set var="helpPage" value="operators#edit_operator_group"/>
	</c:when>
</c:choose>

<ssl:form action="${formAction}" method="post">
<html:hidden property="groupId" />
<html:hidden property="group(id)" />
<c:if test="${isMemberGroup && !usesPin}">
	<%-- Use hiddens to preserve the pin length when there're no channels with pin --%>
	<html:hidden property="group(memberSettings).pinLength.min" />
	<html:hidden property="group(memberSettings).pinLength.max" />
</c:if>
				            		
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
        <cyclos:help page="${helpPage}" width="20%" />
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
        	<fieldset>
        		<legend><bean:message key="group.details"/></legend>
	            <table class="defaultTable">
	            	<c:choose><c:when test="${!isOperatorGroup}">
		            	<tr>
		            		<td width="25%" class="label"><bean:message key="group.nature"/></td>
		            		<td>
				            	<c:choose>
				            		<c:when test="${isInsert}">
					            		<html:select property="group(nature)" styleId="naturesSelect" styleClass="InputBoxDisabled" disabled="true">
					            			<c:forEach var="nature" items="${natures}">
					            				<html:option value="${nature}"><bean:message key="group.nature.${nature}"/></html:option>
					            			</c:forEach>
					            		</html:select>
				                	</c:when>
				                	<c:otherwise>
				                		<input type="text" id="natureText" class="medium InputBoxDisabled" readonly="readonly" value="<bean:message key="group.nature.${group.nature}"/>">
					            	</c:otherwise>
					            </c:choose>
			           		</td>
		            	</tr>
		            </c:when><c:otherwise>
		            	<html:hidden property="group(nature)" value="OPERATOR"/>
		            </c:otherwise></c:choose>
	            	<tr>
	            		<td width="25%" class="label"><bean:message key="group.isRemoved" /></td>
	            		<td>
			            	<c:choose><c:when test="${isInsert}">
			            		<html:checkbox property="group(status)" styleId="removedCheck" value="REMOVED"/>
		                	</c:when><c:otherwise>
		                		<html:hidden property="group(status)"/>
		                		<input type="text" id="statusText" class="medium InputBoxDisabled" readonly="readonly" value="<bean:message key="global.${cyclos:name(group.status) == 'REMOVED' ? 'yes' : 'no'}"/>">
			            	</c:otherwise></c:choose>
	            		</td>
	            	</tr>
	            	<tr>
	            		<td class="label"><bean:message key="group.name" /></td>
	            		<td><html:text property="group(name)" styleClass="full InputBoxDisabled" readonly="true"/></td>
	            	</tr>
	            	<c:if test="${cyclos:name(group.nature) != 'OPERATOR'}">
		            	<tr>
		            		<td class="label"><bean:message key="group.rootUrl" /></td>
		            		<td><html:text property="group(rootUrl)" styleClass="full InputBoxDisabled" readonly="true"/></td>
		            	</tr>
	            	</c:if>
	            	<c:if test="${showLoginPageName}">
		            	<tr>
		            		<td class="label"><bean:message key="group.loginPageName" /></td>
		            		<td><html:text property="group(loginPageName)" styleClass="full InputBoxDisabled" readonly="true"/></td>
		            	</tr>
		            	<tr>
		            		<td class="label"><bean:message key="group.containerUrl" /></td>
		            		<td><html:text property="group(containerUrl)" styleClass="full InputBoxDisabled" readonly="true"/></td>
		            	</tr>
		            </c:if>
	            	<tr>
	            		<td valign="top" class="label"><bean:message key="group.description" /></td>
	            		<td><html:textarea property="group(description)" styleClass="full InputBoxDisabled" rows="4" readonly="true"/></td>
	            	</tr>
	            	<c:if test="${isInsert}">
	            		<tr id="trCopySettingsFrom">
		            		<td width="25%" class="label"><bean:message key="group.copySettingsFrom" /></td>
		            		<td>
			            		<html:select property="baseGroupId" styleId="groupsSelect" styleClass="InputBoxDisabled" disabled="true">
			            			<html:option value=""></html:option>
			            			<c:forEach var="baseGroup" items="${baseGroups}">
			            				<html:option value="${baseGroup.id}">${baseGroup.name}</html:option>
			            			</c:forEach>
			            		</html:select>
			           		</td>
		            	</tr>
	            	</c:if>
	   				<c:if test="${!isInsert && group.status.enabled && (isBrokerGroup || isMemberGroup)}">
                    	<c:choose><c:when test="${empty accountTypes}">
                			<tr>
			                    <td class="label"><bean:message key="group.active"/></td>
			                    <td>    	
                    				<html:checkbox property="group(active)" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/>
                    			</td>
			                </tr>
                    	</c:when><c:otherwise>
                    		<html:hidden property="group(active)" value="true"/>
                    	</c:otherwise></c:choose>
	            	</c:if>
	            </table>
	        </fieldset>
            <c:if test="${!isOperatorGroup && !isInsert && group.status.enabled}">
		        <c:if test="${isMemberGroup}">
					<script>
						var originalRegistrationAgreement = "${group.registrationAgreement.id}";
					</script>
		        	<br/>
		       		<fieldset>
						<legend><bean:message key="group.title.settings.registration"/></legend>
						<table class="defaultTable">
			                <tr>
			                    <td class="label" width="35%"><bean:message key="group.initialGroup"/></td>
			                    <td><html:checkbox property="group(initialGroup)" styleId="initialCheck" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
			                </tr>
			            	<tr class="initialGroup" style="display:none">
			            		<td class="label"><bean:message key="group.initialGroupShow" /></td>
			            		<td><html:text property="group(initialGroupShow)" styleClass="full InputBoxDisabled" readonly="true"/></td>
			            	</tr>
			                <tr>
			                    <td class="label"><bean:message key="group.settings.emailValidation"/></td>
			                    <td>
			                    	<cyclos:multiDropDown name="group(memberSettings).emailValidation" disabled="true">
				                    	<c:forEach var="emailValidation" items="${emailValidations}">
				                    		<c:set var="label"><bean:message key="group.settings.emailValidation.${emailValidation}"/></c:set>
				                    		<cyclos:option value="${emailValidation}" text="${label}" selected="${cyclos:contains(group.memberSettings.emailValidation, emailValidation)}"/>
				                    	</c:forEach>
				                    </cyclos:multiDropDown>
			                    </td>
			                </tr>
			                <c:if test="${not empty registrationAgreements}">
				            	<tr>
				                    <td class="label"><bean:message key="group.registrationAgreement"/></td>
				                    <td>
				                    	<html:select property="group(registrationAgreement)" styleId="registrationAgreementSelect" styleClass="InputBoxDisabled"  disabled="true">
				                    		<html:option value=""><bean:message key="group.registrationAgreement.none"/></html:option>
				                    		<c:forEach var="registrationAgreement" items="${registrationAgreements}">
												<html:option value="${registrationAgreement.id}">${registrationAgreement.name}</html:option>
				                    		</c:forEach>
				                    	</html:select>
				                    </td>
				            	</tr>
				            	<tr id="forceAcceptTR" style="display:none">
				                    <td class="label"><bean:message key="group.registrationAgreement.forceAccept"/></td>
				                    <td><html:checkbox property="forceAccept" styleClass="checkbox" value="true" /></td>
				            	</tr>
				            </c:if>
			                <tr>
			                    <td class="label"><bean:message key="group.settings.sendPasswordByEmail"/></td>
			                    <td><html:checkbox property="group(memberSettings).sendPasswordByEmail" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
			                </tr>
			                <tr>
			            		<td class="label"><bean:message key="group.settings.maxImagesPerMember" /></td>
			            		<td><html:text property="group(memberSettings).maxImagesPerMember" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
			            	</tr>
			                <tr>
			                    <td class="label"><bean:message key="group.settings.expireMembersAfter"/></td>
			                    <td>
			            			<html:text property="group(memberSettings).expireMembersAfter.number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
			                    	<html:select property="group(memberSettings).expireMembersAfter.field" styleClass="InputBoxDisabled" disabled="true">
				                   		<c:forEach var="field" items="${expirationTimeFields}">
				                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
				                   		</c:forEach>
			                    	</html:select>
			                    	(<span class="fieldDecoration"><bean:message key="settings.neverExpiresMessage"/></span>)
			                    </td>
			                </tr>
			                <tr>
			                    <td class="label"><bean:message key="group.settings.groupAfterExpiration"/></td>
			                    <td>
			                    	<html:select property="group(memberSettings).groupAfterExpiration" styleClass="InputBoxDisabled" disabled="true">
			                    		<html:option value=""></html:option>
				                   		<c:forEach var="group" items="${possibleExpirationGroups}">
				                    		<html:option value="${group.id}">${group.name}</html:option>
				                   		</c:forEach>
			                    	</html:select>
			                    </td>
			                </tr>
						</table>
					</fieldset>
				</c:if>
				<br/>
	            <fieldset>
					<legend><bean:message key="group.title.settings.access"/></legend>
					<table class="defaultTable">
						<c:if test="${isMemberGroup}">
							<tr>
			                    <td class="label" width="50%"><bean:message key="group.settings.channels"/></td>
			                    <td>
			                    	<cyclos:multiDropDown name="group(channels)" disabled="true">
				                    	<c:forEach var="channel" items="${channels}">
				                    		<cyclos:option value="${channel.id}" text="${channel.displayName}" selected="${cyclos:contains(group.channels, channel)}"/>
				                    	</c:forEach>
				                    </cyclos:multiDropDown>
			                    </td>
			                </tr>
			                <tr>
			                    <td class="label"><bean:message key="group.settings.defaultChannels"/></td>
			                    <td>
			                    	<cyclos:multiDropDown name="group(defaultChannels)" disabled="true">
				                    	<c:forEach var="channel" items="${channels}">
				                    		<cyclos:option value="${channel.id}" text="${channel.displayName}" selected="${cyclos:contains(group.defaultChannels, channel)}"/>
				                    	</c:forEach>
				                    </cyclos:multiDropDown>
			                    </td>
			                </tr>
			                <c:if test="${not empty cardTypes}">
				                <tr>
				            		<td class="label"><bean:message key="group.settings.cardType" /></td>
				            		<td>
				            			<html:select property="group(cardType)" styleClass="InputBoxDisabled" disabled="true">
				            				<html:option value=""></html:option>
					                   		<c:forEach var="cardType" items="${cardTypes}">
					                    		<html:option value="${cardType.id}">${cardType.name}</html:option>
					                   		</c:forEach>
				                    	</html:select>
				            		</td>
				            	</tr>
				            </c:if>
			                <c:if test="${usesPin}">
			            	  	<tr>
				            		<td class="label">
				            			<bean:message key="group.settings.pinLength" />
				            			<bean:message key="global.min" />
				            		</td>
				            		<td>
				            			<html:text property="group(memberSettings).pinLength.min" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/>
				            			&nbsp;
				            			<span class="inlineLabel"><bean:message key="global.max" /></span>
				            			<html:text property="group(memberSettings).pinLength.max" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/>
				            		</td>
				            	</tr>
				            	<tr>
				            		<td class="label"><bean:message key="group.settings.maxPinWrongTries" /></td>
				            		<td><html:text property="group(memberSettings).maxPinWrongTries" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/></td>
				            	</tr>
				            	<tr>
				            		<td class="label"><bean:message key="group.settings.pinBlockTimeAfterMaxTries" /></td>
				            		<td>
				            			<html:text property="group(memberSettings).pinBlockTimeAfterMaxTries.number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
				                    	<html:select property="group(memberSettings).pinBlockTimeAfterMaxTries.field" styleClass="InputBoxDisabled" disabled="true">
					                   		<c:forEach var="field" items="${deactivationTimePeriodFields}">
					                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
					                   		</c:forEach>
				                    	</html:select>
									</td>
				            	</tr>
				            </c:if>
						</c:if>
						<tr>
		            		<td class="label" width="50%">
		            			<bean:message key="group.settings.passwordLength" />
		            			<bean:message key="global.min" />
		            		</td>
		            		<td>
		            			<html:text property="group(basicSettings).passwordLength.min" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/>
		            			&nbsp;
		            			<span class="inlineLabel"><bean:message key="global.max" /></span>
		            			<html:text property="group(basicSettings).passwordLength.max" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/>
		            		</td>
		            	</tr>
		            	<tr>
		            		<td class="label"><bean:message key="group.settings.passwordPolicy" /></td>
		            		<td>
		                    	<html:select property="group(basicSettings).passwordPolicy" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="policy" items="${passwordPolicies}">
			                    		<html:option value="${policy}"><bean:message key="group.settings.passwordPolicy.${policy}"/></html:option>
			                   		</c:forEach>
		                    	</html:select>
		            		</td>
		            	</tr>
		            	<tr>
		            		<td class="label"><bean:message key="group.settings.passwordTries.maximum" /></td>
		            		<td><html:text property="group(basicSettings).maxPasswordWrongTries" styleClass="tiny InputBoxDisabled" maxlength="2" readonly="true"/></td>
		            	</tr>
		            	<tr>
		            		<td class="label"><bean:message key="group.settings.passwordTries.deactivationTime" /></td>
		            		<td>
		            			<html:text property="group(basicSettings).deactivationAfterMaxPasswordTries.number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
		                    	<html:select property="group(basicSettings).deactivationAfterMaxPasswordTries.field" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="field" items="${deactivationTimePeriodFields}">
			                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
			                   		</c:forEach>
		                    	</html:select>
		            		</td>
		            	</tr>
		            	<tr>
		            		<td class="label"><bean:message key="group.settings.passwordExpiresAfter" /></td>
		            		<td>
		            			<html:text property="group(basicSettings).passwordExpiresAfter.number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
		                    	<html:select property="group(basicSettings).passwordExpiresAfter.field" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="field" items="${passwordExpiresAfterFields}">
			                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
			                   		</c:forEach>
		                    	</html:select>
		            		</td>
		            	</tr>
		            	<tr>
		            		<td class="label"><bean:message key="group.settings.transactionPassword" /></td>
		            		<td>
		                    	<html:select property="group(basicSettings).transactionPassword" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="transactionPassword" items="${transactionPasswords}">
			                    		<html:option value="${transactionPassword}"><bean:message key="transactionPassword.${transactionPassword}"/></html:option>
			                   		</c:forEach>
		                    	</html:select>
		            		</td>
		            	</tr>
		            	<tr>
		            		<td class="label"><bean:message key="group.settings.transactionPassword.length" /></td>
		            		<td><html:text property="group(basicSettings).transactionPasswordLength" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
		            	</tr>
		            	<tr>
		            		<td class="label"><bean:message key="group.settings.maxTransactionPasswordWrongTries" /></td>
		            		<td><html:text property="group(basicSettings).maxTransactionPasswordWrongTries" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
		            	</tr>		            	
					</table>
				</fieldset>
				<c:if test="${isMemberGroup}">
					<br/>
					<fieldset>
						<legend><bean:message key="group.title.settings.notifications"/></legend>
						<table class="defaultTable">
							<tr>
			                    <td class="label" width="50%"><bean:message key="group.settings.defaultMailMessages"/></td>
			                    <td>
				                    <cyclos:multiDropDown name="group(defaultMailMessages)" disabled="true">
				                    	<c:forEach var="type" items="${messageTypes}">
				                    		<cyclos:option value="${type}" selected="${cyclos:contains(group.defaultMailMessages, type)}">
				                    			<jsp:attribute name="text"><bean:message key="message.type.${type}" /></jsp:attribute>
				                    		</cyclos:option>
				                    	</c:forEach>
				                    </cyclos:multiDropDown>
			                    </td>
			                </tr>
			                <c:if test="${localSettings.smsEnabled}">
				                <tr>
				                    <td class="label"><bean:message key="group.smsMessages"/></td>
				                    <td>
					                    <cyclos:multiDropDown name="group(smsMessages)" disabled="true">
					                    	<c:forEach var="type" items="${smsMessageTypes}">
					                    		<cyclos:option value="${type}" selected="${cyclos:contains(group.smsMessages, type)}">
					                    			<jsp:attribute name="text"><bean:message key="message.type.${type}" /></jsp:attribute>
					                    		</cyclos:option>
					                    	</c:forEach>
					                    </cyclos:multiDropDown>
				                    </td>
				                </tr>
				                <tr>
				                    <td class="label"><bean:message key="group.defaultSmsMessages"/></td>
				                    <td>
					                    <cyclos:multiDropDown name="group(defaultSmsMessages)" disabled="true">
					                    	<c:forEach var="type" items="${smsMessageTypes}">
					                    		<cyclos:option value="${type}" selected="${cyclos:contains(group.defaultSmsMessages, type)}">
					                    			<jsp:attribute name="text"><bean:message key="message.type.${type}" /></jsp:attribute>
					                    		</cyclos:option>
					                    	</c:forEach>
					                    </cyclos:multiDropDown>
				                    </td>
				                </tr>
				                <tr>
				                    <td class="label"><bean:message key="group.defaultAllowChargingSms"/></td>
				                    <td><html:checkbox property="group(defaultAllowChargingSms)" styleClass="checkbox InputBoxDisabled" value="true" disabled="true" /></td>
				                </tr>
				                <tr>
				                    <td class="label"><bean:message key="group.defaultAcceptFreeMailing"/></td>
				                    <td><html:checkbox property="group(defaultAcceptFreeMailing)" styleClass="checkbox InputBoxDisabled" value="true" disabled="true" /></td>
				                </tr>
				                <tr>
				                    <td class="label"><bean:message key="group.defaultAcceptPaidMailing"/></td>
				                    <td><html:checkbox property="group(defaultAcceptPaidMailing)" styleClass="checkbox InputBoxDisabled" value="true" disabled="true" /></td>
				                </tr>
				                <tr>
				                    <td class="label"><bean:message key="group.settings.smsChargeTransferType"/></td>
				                    <td>
				                    	<html:select property="group(memberSettings).smsChargeTransferType" styleClass="InputBoxDisabled" disabled="true">
				                    		<html:option value=""></html:option>
					                   		<c:forEach var="smsChargeTransferType" items="${smsChargeTransferTypes}">
					                    		<html:option value="${smsChargeTransferType.id}">${smsChargeTransferType.name}</html:option>
					                   		</c:forEach>
				                    	</html:select>
				                    </td>
				                </tr>
				                <tr>
									<td class="label"><bean:message key='group.settings.smsCustomContext'/></td>
									<td>
										<html:checkbox property="useCustomSMSContextClass" styleId="customSmsCtxCheck" styleClass="checkbox"/>									
									</td>
				           		</tr>				           						                
				                <tr class="basicSmsCtx" style="display:none;">
									<td class="label"><bean:message key='group.settings.smsFree'/></td>
									<td>
										<html:text property="group(memberSettings).smsFree" readonly="true" styleClass="tiny number InputBoxDisabled"/>
									</td>
				           		</tr>
				                <tr class="basicSmsCtx" style="display:none;">
									<td class="label"><bean:message key='group.settings.smsShowFreeThreshold'/></td>
									<td>
										<html:text property="group(memberSettings).smsShowFreeThreshold" readonly="true" styleClass="tiny number InputBoxDisabled"/>
									</td>
				           		</tr>
				                <tr class="basicSmsCtx" style="display:none;">
									<td class="label"><bean:message key='group.settings.smsAdditionalCharged'/></td>
									<td>
										<html:text property="group(memberSettings).smsAdditionalCharged" readonly="true" styleClass="tiny number InputBoxDisabled"/>
									</td>
				           		</tr>				           						           		
				                <tr class="basicSmsCtx" style="display:none;">
									<td class="label"><bean:message key='group.settings.smsChargeAmount'/></td>
									<td>
										<html:text property="group(memberSettings).smsChargeAmount" readonly="true" styleClass="small float InputBoxDisabled"/>
									</td>
				           		</tr>				           		
				                <tr class="basicSmsCtx" style="display:none;">				                				                
									<td class="label"><bean:message key='group.settings.smsAdditionalChargedPeriod'/></td>									
				            		<td>
				            			<html:text property="group(memberSettings).smsAdditionalChargedPeriod.number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
				                    	<html:select property="group(memberSettings).smsAdditionalChargedPeriod.field" styleClass="InputBoxDisabled" disabled="true">
					                   		<c:forEach var="field" items="${smsAdditionalChargedPeriodFields}">
					                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
					                   		</c:forEach>
				                    	</html:select>			                    	
				            		</td>
				           		</tr>
				                <tr class="customSmsCtx" style="display:none;">
									<td class="label"><bean:message key='group.settings.smsContextClassName'/></td>
									<td>
										<html:text property="group(memberSettings).smsContextClassName" readonly="true" styleClass="full InputBoxDisabled"/>
									</td>
				           		</tr>				           		
				           	</c:if>
						</table>
					</fieldset>
				</c:if>
				<c:if test="${isBrokerGroup}">
					<br/>
					<fieldset>
						<legend><bean:message key="group.title.settings.brokering"/></legend>
						<table class="defaultTable">
				            <tr>
			                    <td class="label" width="50%"><bean:message key="group.settings.possibleInitialGroups"/></td>
			                    <td>
				                    <cyclos:multiDropDown name="group(possibleInitialGroups)" disabled="true">
				                    	<c:forEach var="memberGroup" items="${memberGroups}">
				                    		<cyclos:option value="${memberGroup.id}" text="${memberGroup.name}" selected="${cyclos:contains(group.possibleInitialGroups, memberGroup)}"/>
				                    	</c:forEach>
				                    </cyclos:multiDropDown>
			                    </td>
			                </tr>
						</table>
					</fieldset>
            	</c:if>
				<c:if test="${isMemberGroup}">
					<br/>
					<fieldset>
						<legend><bean:message key="group.title.settings.advertisements"/></legend>
						<table class="defaultTable">
							<tr>
			            		<td class="label" width="50%"><bean:message key="group.settings.maxAdsPerMember" /></td>
			            		<td><html:text property="group(memberSettings).maxAdsPerMember" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
			            	</tr>
			                <tr>
			                    <td class="label"><bean:message key="group.settings.enablePermanentAds"/></td>
			                    <td><html:checkbox property="group(memberSettings).enablePermanentAds" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
			                </tr>
			            	<tr>
			            		<td class="label"><bean:message key="group.settings.defaultAdPublicationTime" /></td>
			            		<td>
			            			<html:text property="group(memberSettings).defaultAdPublicationTime.number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
			                    	<html:select property="group(memberSettings).defaultAdPublicationTime.field" styleClass="InputBoxDisabled" disabled="true">
				                   		<c:forEach var="field" items="${timePeriodFields}">
				                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
				                   		</c:forEach>
			                    	</html:select>
			            		</td>
			            	</tr>
			            	<tr>
			            		<td class="label"><bean:message key="group.settings.maxAdPublicationTime" /></td>
			            		<td>
			            			<html:text property="group(memberSettings).maxAdPublicationTime.number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
			                    	<html:select property="group(memberSettings).maxAdPublicationTime.field" styleClass="InputBoxDisabled" disabled="true">
				                   		<c:forEach var="field" items="${timePeriodFields}">
				                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
				                   		</c:forEach>
			                    	</html:select>
			            		</td>
			            	</tr>
			            	<tr>
			                    <td class="label"><bean:message key="group.settings.externalAdPublication"/></td>
			                    <td>
			                    	<html:select property="group(memberSettings).externalAdPublication" styleClass="InputBoxDisabled" disabled="true">
				                   		<c:forEach var="externalAdPublication" items="${externalAdPublications}">
				                    		<html:option value="${externalAdPublication}"><bean:message key="group.settings.externalAdPublication.${externalAdPublication}"/></html:option>
				                   		</c:forEach>
			                    	</html:select>
			                    </td>
			                </tr>
			            	<tr>
			            		<td class="label"><bean:message key="group.settings.maxAdImagesPerMember" /></td>
			            		<td><html:text property="group(memberSettings).maxAdImagesPerMember" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
			            	</tr>
			            	<tr>
			            		<td class="label"><bean:message key="group.settings.maxAdDescriptionSize" /></td>
			            		<td><html:text property="group(memberSettings).maxAdDescriptionSize" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
			            	</tr>
						</table>
					</fieldset>
					<br/>
					<fieldset>
						<legend><bean:message key="group.title.settings.payments"/></legend>
						<table class="defaultTable">
							<c:if test="${showScheduling}">
				                <tr>
				                    <td class="label" width="50%"><bean:message key="group.settings.maxSchedulingPayments"/></td>
				                    <td><html:text property="group(memberSettings).maxSchedulingPayments" styleClass="tiny number InputBoxDisabled" disabled="true"/></td>
				                </tr>
				                <tr>
				                    <td class="label" width="50%"><bean:message key="group.settings.maxSchedulingPeriod"/></td>
				                    <td>
				            			<html:text property="group(memberSettings).maxSchedulingPeriod.number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
				                    	<html:select property="group(memberSettings).maxSchedulingPeriod.field" styleClass="InputBoxDisabled" disabled="true">
					                   		<c:forEach var="field" items="${timePeriodFields}">
					                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
					                   		</c:forEach>
				                    	</html:select>
									</td>
				                </tr>
		            		</c:if>
		            		<c:if test="${isMemberGroup}">
				                <tr>
				                    <td class="label" width="50%"><bean:message key="group.settings.showPosWebPaymentDescription"/></td>
				                    <td><html:checkbox property="group(memberSettings).showPosWebPaymentDescription" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
				                </tr>
			                </c:if>
			                <tr>
			                    <td class="label" width="50%"><bean:message key="group.settings.hideCurrencyOnPayments"/></td>
			                    <td><html:checkbox property="group(basicSettings).hideCurrencyOnPayments" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
			                </tr>
						</table>
					</fieldset>
					<br/>
					<fieldset>
						<legend><bean:message key="group.title.settings.loans"/></legend>
						<table class="defaultTable">
								<tr>
				                    <td class="label" width="50%"><bean:message key="group.settings.viewLoansByGroup"/></td>
				                    <td><html:checkbox property="group(memberSettings).viewLoansByGroup" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
				                </tr>
				                <tr>
				                    <td class="label"><bean:message key="group.settings.repayLoanByGroup"/></td>
				                    <td><html:checkbox property="group(memberSettings).repayLoanByGroup" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
				                </tr>
						</table>
					</fieldset>
	            </c:if>
			</c:if>
			<c:if test="${isOperatorGroup && not empty transferTypes}">
				<br/>
				<fieldset>
					<legend><bean:message key="group.settings.maxAmountPerDay"/></legend>
		            <table class="defaultTable">
						<c:forEach var="tt" items="${transferTypes}">
							<tr>
								<td width="50%" nowrap="nowrap" class="label">${tt.name}</td>
								<td>
									<input type="hidden" name="group(transferTypeIds)" value="${tt.id}"/>
									<input type="text" name="group(maxAmountPerDayByTT)" class="InputBoxDisabled small float" readonly="readonly" value="<cyclos:format number="${group.maxAmountPerDayByTransferType[tt]}"/>"/>
								</td>
							</tr>
						</c:forEach>
					</table>
				</fieldset>
			</c:if>
			<c:if test="${isInsert || editable}">
				<table class="defaultTable">
		           	<tr>
						<td align="right" colspan="2">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
						</td>
		           	</tr>
				</table>
			</c:if>
        </td>
	</tr>
</table>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left">
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
			<c:if test="${!isInsert && editable && group.status.enabled}">
				&nbsp;<input type="button" class="button keepEnabled" id="groupPermissionsButton" value="<bean:message key="group.permissions"/>">
			</c:if>
		</td>
	</tr>
</table>
</ssl:form>

<c:if test="${!isInsert && isMemberGroup && group.status.enabled}">
	
	<script>
		var removeAccountConfirmation = "<cyclos:escapeJS><bean:message key="group.account.removeConfirmation"/></cyclos:escapeJS>";
	</script>
	<c:choose>
		<c:when test="${empty accountTypes}">
			<div class="footerNote" helpPage="groups#manage_group_accounts"><bean:message key="group.account.noResults"/></div>
		</c:when>
		<c:otherwise>
			<table class="defaultTableContent" cellspacing="0" cellpadding="0">
			    <tr>
			        <td class="tdHeaderTable"><bean:message key="group.account.title"/></td>
			        <cyclos:help page="groups#manage_group_accounts"/>
			    </tr>
			    <tr>
			        <td colspan="2" align="left" class="tdContentTableLists">
			            <table class="defaultTable">
			            	<tr>
			            		<td class="tdHeaderContents"><bean:message key="account.type" /></td>
			            		<td class="tdHeaderContents" width="10%">&nbsp;</td>
			            	</tr>
			            	<c:forEach var="account" items="${accountTypes}">
				            	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
				            		<td><cyclos:escapeHTML>${account.name}</cyclos:escapeHTML></td>
				            		<td align="center" nowrap="nowrap">
					            		<c:choose><c:when test="${editable && cyclos:granted(AdminMemberPermission.GROUPS_MANAGE_ACCOUNT_SETTINGS)}">
											<img accountTypeId="${account.id}" class="edit accountDetails" src="<c:url value="/pages/images/edit.gif" />" />
					            			<img accountTypeId="${account.id}" class="remove removeAccount" src="<c:url value="/pages/images/delete.gif" />" />
				                      	</c:when><c:otherwise>
				                      		<img accountTypeId="${account.id}" class="view accountDetails" src="<c:url value="/pages/images/view.gif" />" />
				                      	</c:otherwise></c:choose>
				            		</td>
				            	</tr>
			            	</c:forEach>
			            </table>
			        </td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
	
	<c:if test="${editable && cyclos:granted(AdminMemberPermission.GROUPS_MANAGE_ACCOUNT_SETTINGS)}">
		<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
			<tr>
				<td align="right">
					<span class="label"><bean:message key="group.account.action.new"/></span>
					<input type="button" id="newAccountButton" class="button" value="<bean:message key="global.submit"/>">
				</td>
			</tr>
		</table>
	</c:if>
</c:if>

<c:if test="${!isInsert && group.status.enabled}">
	
	<script>
		var stopCustomizingConfirmation = "<cyclos:escapeJS><bean:message key="group.customizedFiles.removeConfirmation"/></cyclos:escapeJS>";
	</script>
	<c:set var="help" value="${isMember ? 'operators#manage_group_customized_files' : 'groups#manage_group_customized_files'}"/>
	<c:choose>
		<c:when test="${empty customizedFiles}">
			<div class="footerNote" helpPage="${help}" title="<bean:message key="group.customizedFiles.title"/>"><bean:message key="group.customizedFiles.noResults"/></div>
		</c:when>
		<c:otherwise>
			<table class="defaultTableContent" cellspacing="0" cellpadding="0">
			    <tr>
			        <td class="tdHeaderTable"><bean:message key="group.customizedFiles.title"/></td>
			        <cyclos:help page="${help}"/>
			    </tr>
			    <tr>
			        <td colspan="2" align="left" class="tdContentTableLists">
			            <table class="defaultTable">
			            	<tr>
			            		<c:if test="${isAdmin}">
			            			<td class="tdHeaderContents"><bean:message key="customizedFile.type" /></td>
			            		</c:if>
			            		<td class="tdHeaderContents"><bean:message key="customizedFile.name" /></td>
			            		<td class="tdHeaderContents" width="10%">&nbsp;</td>
			            	</tr>
			            	<c:forEach var="file" items="${customizedFiles}">
				            	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
				            		<c:if test="${isAdmin}">
				            			<td><cyclos:escapeHTML><bean:message key="customizedFile.type.${file.type}" /></cyclos:escapeHTML></td>
				            		</c:if>
				            		<td><cyclos:escapeHTML>${file.name}</cyclos:escapeHTML></td>
				            		<td align="center" nowrap="nowrap">
				                    	<c:if test="${cyclos:name(file.type) != 'STYLE'}">
				                    		<img class="preview previewCustomizedFile" src="<c:url value="/pages/images/preview.gif"/>" fileName="<cyclos:escapeHTML>${file.name}</cyclos:escapeHTML>" fileType="<cyclos:escapeHTML>${file.type}</cyclos:escapeHTML>" border="0">
				                    	</c:if>
					            		<c:choose><c:when test="${editable && canManageFiles}">
											<img fileId="${file.id}" class="edit customizedFileDetails" src="<c:url value="/pages/images/edit.gif" />" />
					            			<img fileId="${file.id}" class="remove removeCustomizedFile" src="<c:url value="/pages/images/delete.gif" />" />
				                      	</c:when><c:otherwise>
				                      		<img fileId="${file.id}" class="view customizedFileDetails" src="<c:url value="/pages/images/view.gif" />" />
				                      	</c:otherwise></c:choose>
				            		</td>
				            	</tr>
			            	</c:forEach>
			            </table>
			        </td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
	
	<c:if test="${canManageFiles}">
		<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
			<tr>
				<td align="right">
					<span class="label"><bean:message key="group.customizedFiles.action.new"/></span>
					<input type="button" id="newCustomizedFileButton" class="button" value="<bean:message key="global.submit"/>">
				</td>
			</tr>
		</table>
	</c:if>
</c:if>
