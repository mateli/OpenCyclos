<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/transferTypes/editTransferType.js" />
<script>
	var transferTypeId = "${transferType.id}";
	var isSystemAccount = ${isSystemAccount};
	var isToSystemAccount = ${transferType.persistent and transferType.toSystem}
	var removeAuthorizationLevelConfirmation = "<cyclos:escapeJS><bean:message key="authorizationLevel.removeConfirmation"/></cyclos:escapeJS>";
	<c:if test="${not isInsert && transferType.requiresAuthorization}">
		var authorizerNames = { 
			<c:forEach var="authorizer" varStatus="status" items="${authorizers}">
				'${authorizer}':'<cyclos:escapeJS><bean:message key="authorizationLevel.authorizer.${authorizer}"/></cyclos:escapeJS>'${status.last ? '' : ', '}
			</c:forEach>
		}
		var authorizationLevels = [];
		<c:forEach var="level" items="${authorizationLevels}">
			authorizationLevels.push({
				id:${level.id},
				amount:'<cyclos:format number="${level.amount}"/>',
				level:${level.level},
				authorizer:'${level.authorizer}',
				adminGroups:[
					<c:forEach var="g" varStatus="status" items="${level.adminGroups}">
						'${g.id}'${status.last ? '' : ', '}
					</c:forEach>
				]
			});
		</c:forEach>
	</c:if>
	var removeTransactionFeeConfirmation = "<cyclos:escapeJS><bean:message key="transactionFee.removeConfirmation"/></cyclos:escapeJS>";
	var removeBrokerCommissionConfirmation = "<cyclos:escapeJS><bean:message key="brokerCommission.removeConfirmation"/></cyclos:escapeJS>";
	var removeFieldConfirmationMessage = "<cyclos:escapeJS><bean:message key="customField.removeConfirmation"/></cyclos:escapeJS>";
	<c:if test="${isInsert}">
		var accountTypes = [];
		<c:forEach var="at" items="${accountTypes}">
			accountTypes.push({id:${at.id}, name:'<cyclos:escapeJS>${at.name}</cyclos:escapeJS>', isSystem:${cyclos:name(at.nature) == 'SYSTEM'}});
		</c:forEach>
	</c:if>
</script>
<ssl:form method="post" action="${formAction}">
<html:hidden property="accountTypeId"/>
<html:hidden property="transferTypeId"/>
<html:hidden property="transferType(id)"/>
<html:hidden property="transferType(from)" value="${accountType.id}"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="transferType.title.${isInsert ? 'insert' : 'modify'}"/></td>
        <cyclos:help page="account_management#transaction_type_details"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
            		<td class="label" width="30%"><bean:message key="transferType.name"/></td>
            		<td><html:text styleId="nameText" property="transferType(name)" maxlength="100" readonly="true" styleClass="full InputBoxDisabled"/></td>
            	</tr>
            	<tr>
            		<td class="label" valign="top"><bean:message key="transferType.description"/></td>
            		<td><html:textarea styleId="descriptionText" property="transferType(description)" rows="6" readonly="true" styleClass="full InputBoxDisabled"/></td>
            	</tr>
            	<tr>
            		<td class="label" valign="top"><bean:message key="transferType.confirmationMessage"/></td>
            		<td><html:textarea styleId="confirmationText" property="transferType(confirmationMessage)" rows="6" readonly="true" styleClass="full InputBoxDisabled"/></td>
            	</tr>
            	<tr>
					<td class="label"><bean:message key="transferType.from"/></td>
					<td><input type="text" id="fromText" readonly="readonly" class="large InputBoxDisabled" value="${transferType.from.name}"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="transferType.to"/></td>
					<td>
						<c:choose><c:when test="${isInsert}">
							<html:select styleId="toSelect" property="transferType(to)" disabled="true" styleClass="InputBoxDisabled"/>
						</c:when><c:otherwise>
							<html:hidden property="transferType(to)" />
							<input type="text" id="toText" readonly="readonly" class="large InputBoxDisabled" value="${transferType.to.name}"/>
						</c:otherwise></c:choose>
					</td>
				</tr>
				<c:if test="${(isInsert || !transferType.toSystem)}">
					<tr class="toMember" style="display:none">
						<td class="label"><bean:message key="transferType.fixedDestinationMember.username"/></td>
						<td>
							<html:hidden styleId="fixedDestinationMemberId" property="transferType(fixedDestinationMember)"/>
							<input id="memberUsername" readonly="readonly" class="InputBoxDisabled full" value="${transferType.fixedDestinationMember.username}">
							<div id="membersByUsername" class="autoComplete"></div>
						</td>
					</tr>
					<tr class="toMember" style="display:none">
						<td class="label"><bean:message key="transferType.fixedDestinationMember.name"/></td>
						<td>
							<input id="memberName" readonly="readonly" class="InputBoxDisabled full" value="${transferType.fixedDestinationMember.name}">
							<div id="membersByName" class="autoComplete"></div>
						</td>
					</tr>
				</c:if>
				<tr id="trEnabled" style="display:none">
					<td class="label"><bean:message key="transferType.enabled"/></td>
					<td><html:checkbox property="transferType(enabled)" disabled="true" styleClass="checkbox InputBoxDisabled" value="true"/></td>
				</tr>
				<tr id="trAvailability" style="display:none">
					<td class="label"><bean:message key="transferType.availability"/></td>
					<td>
						<html:hidden styleId="paymentContext" property="transferType(context).payment" />
						<html:hidden styleId="selfPaymentContext" property="transferType(context).selfPayment" />
						<select id="availabilitySelect" disabled="true" class="InputBoxDisabled">
							<option value="DISABLED" ${!transferType.context.payment && !transferType.context.selfPayment ? 'selected' : ''}><bean:message key="transferType.availability.DISABLED" /></option>
							<option value="PAYMENT" ${transferType.context.payment ? 'selected' : ''}><bean:message key="transferType.availability.PAYMENT" /></option>
							<option value="SELF_PAYMENT" ${transferType.context.selfPayment ? 'selected' : ''}><bean:message key="transferType.availability.SELF_PAYMENT" /></option>
						</select>
					</td>
				</tr>
				<tr id="trChannels" style="display:none">
					<td class="label"><bean:message key="transferType.channels"/></td>
					<td colspan="3">
						<cyclos:multiDropDown name="transferType(channels)" varName="channelsSelect" disabled="true">
	                    	<c:forEach var="channel" items="${channels}">
	                    		<cyclos:option value="${channel.id}" text="${channel.displayName}" selected="${cyclos:contains(transferType.channels, channel)}"/>
	                    	</c:forEach>
	                    </cyclos:multiDropDown>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="transferType.priority"/></td>
					<td><html:checkbox property="transferType(priority)" value="true" disabled="true" styleClass="checkbox InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="transferType.maxAmountPerDay"/></td>
					<td><html:text property="transferType(maxAmountPerDay)" readonly="true" styleClass="small float InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="transferType.minAmount"/></td>
					<td><html:text property="transferType(minAmount)" readonly="true" styleClass="small float InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="transferType.transferListenerClass"/></td>
					<td><html:text property="transferType(transferListenerClass)" readonly="true" styleClass="full InputBoxDisabled" maxlength="200"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="transferType.transactionHierarchyVisibility"/></td>
					<td>
					    <html:select property="transferType(transactionHierarchyVisibility)" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="visibility" items="${transactionHierarchyVisibilities}">
	                    		<html:option value="${visibility}"><bean:message key="transferType.transactionHierarchyVisibility.${visibility}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="transferType.requiresAuthorization"/></td>
					<td><html:checkbox property="transferType(requiresAuthorization)" value="true" disabled="true" styleClass="checkbox InputBoxDisabled"/></td>
				</tr>
				<tr class="notForLoan" id="trAllowsScheduledPayments" style="display:none">
					<td class="label"><bean:message key="transferType.allowsScheduledPayments"/></td>
					<td><html:checkbox styleId="schedulingCheck" property="transferType(allowsScheduledPayments)" value="true" disabled="true" styleClass="checkbox InputBoxDisabled"/></td>
				</tr>
				<tr class="notForLoan trScheduling" style="display:none">
					<td class="label"><bean:message key="transferType.reserveTotalAmountOnScheduling"/></td>
					<td><html:checkbox property="transferType(reserveTotalAmountOnScheduling)" value="true" disabled="true" styleClass="checkbox InputBoxDisabled"/></td>
				</tr>
				<tr class="notForLoan trScheduling" style="display:none">
					<td class="label"><bean:message key="transferType.allowCancelScheduledPayments"/></td>
					<td><html:checkbox property="transferType(allowCancelScheduledPayments)" value="true" disabled="true" styleClass="checkbox InputBoxDisabled"/></td>
				</tr>
				<tr class="notForLoan trScheduling" style="display:none">
					<td class="label"><bean:message key="transferType.allowBlockScheduledPayments"/></td>
					<td><html:checkbox property="transferType(allowBlockScheduledPayments)" value="true" disabled="true" styleClass="checkbox InputBoxDisabled"/></td>
				</tr>
				<tr class="notForLoan trScheduling" style="display:none">
					<td class="label"><bean:message key="transferType.showScheduledPaymentsToDestination"/></td>
					<td><html:checkbox property="transferType(showScheduledPaymentsToDestination)" value="true" disabled="true" styleClass="checkbox InputBoxDisabled"/></td>
				</tr>
				<c:if test="${localSettings.smsEnabled}">
					<tr class="toMember" style="display:none">
						<td class="label"><bean:message key="transferType.allowSmsNotification"/></td>
						<td><html:checkbox property="transferType(allowSmsNotification)" value="true" disabled="true" styleClass="checkbox InputBoxDisabled"/></td>
					</tr>
				</c:if>
				<tr>
					<td class="label"><bean:message key="transferType.conciliabled"/></td>
					<td><html:checkbox property="transferType(conciliable)" value="true" disabled="true" styleClass="checkbox InputBoxDisabled"/></td>
				</tr>
				<c:if test="${!isSystemAccount && (isInsert || !transferType.toSystem)}">
					<tr class="notForLoan" id="trRequiresFeedback" style="${isInsert ? 'display:none' : ''}">
						<td class="label"><bean:message key="transferType.requiresFeedback"/></td>
						<td><html:checkbox styleId="requiresFeedbackCheck" property="transferType(requiresFeedback)" value="true" disabled="true" styleClass="checkbox InputBoxDisabled"/></td>
					</tr>
					<tr class="notForLoan feedbackRequired" style="display:none">
						<td class="label"><bean:message key="transferType.feedbackExpirationTime"/></td>
						<td>
							<html:text property="transferType(feedbackExpirationTime).number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<html:select property="transferType(feedbackExpirationTime).field" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="field" items="${feedbackTimeFields}">
		                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
						</td>
					</tr>
					<tr class="notForLoan feedbackRequired" style="display:none">
						<td class="label"><bean:message key="transferType.feedbackReplyExpirationTime"/></td>
						<td>
							<html:text property="transferType(feedbackReplyExpirationTime).number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<html:select property="transferType(feedbackReplyExpirationTime).field" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="field" items="${feedbackTimeFields}">
		                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
						</td>
					</tr>
					<tr class="notForLoan feedbackRequired" style="display:none">
						<td class="label"><bean:message key="transferType.defaultFeedbackLevel"/></td>
						<td>
							<html:select property="transferType(defaultFeedbackLevel)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="level" items="${localSettings.referenceLevelList}">
		                    		<html:option value="${level}"><bean:message key="reference.level.${level}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
						</td>
					</tr>
					<tr class="notForLoan feedbackRequired" style="display:none">
						<td class="label"><bean:message key="transferType.defaultFeedbackComments"/></td>
						<td><html:textarea property="transferType(defaultFeedbackComments)" styleClass="full InputBoxDisabled" readonly="true" rows="3" /></td>
					</tr>
				</c:if>
				<c:if test="${isSystemAccount && (isInsert || !transferType.toSystem)}">
					<tr id="trIsLoan" style="display:none">
						<td class="label"><bean:message key="transferType.isLoan"/></td>
						<td><input type="checkbox" id="isLoanCheckBox" name="isLoan" disabled="true" class="checkbox InputBoxDisabled" ${transferType.loanType ? 'checked' : ''}></td>
					</tr>
					<tr id="trLoan" style="display:none">
						<td colspan="2">
							<fieldset>
								<legend><bean:message key="transferType.loanParameters"/></legend>
								<table class="defaultTable">
									<tr>
		            					<td class="label" width="40%" nowrap="nowrap"><bean:message key="loan.type"/></td>
					            		<td>
					            			<html:select styleId="loanTypeSelect" property="transferType(loan).type" disabled="true" styleClass="InputBoxDisabled">
					            				<c:forEach var="type" items="${loanTypes}">
					            					<html:option value="${type}"><bean:message key="loan.type.${type}"/></html:option>
					            				</c:forEach>
											</html:select>
										</td>
					            	</tr>
									<tr>
		            					<td class="label" nowrap="nowrap"><bean:message key="loan.repaymentType"/></td>
					            		<td>
					            			<html:select styleId="repaymentTypeSelect" property="transferType(loan).repaymentType" disabled="true" styleClass="InputBoxDisabled">
					            				<c:forEach var="tt" items="${loanRepaymentTypes}">
					            					<html:option value="${tt.id}">${tt.name}</html:option>
					            				</c:forEach>
					            			</html:select>
										</td>
					            	</tr>
					            	
									<tr class="trLoanSinglePayment" nowrap="nowrap">
										<td class="label"><bean:message key="loan.repaymentDays"/></td>
										<td><html:text property="transferType(loan).repaymentDays" readonly="true" disabled="true" styleClass="small number InputBoxDisabled"/></td>
									</tr>
									
									<tr class="trLoanWithInterest">
										<td class="label" nowrap="nowrap"><bean:message key="loan.monthlyInterest"/></td>
										<td><html:text property="transferType(loan).monthlyInterest" readonly="true" styleClass="small float InputBoxDisabled"/>%</td>
									</tr>
									<tr class="trLoanWithInterest">
		            					<td class="label" nowrap="nowrap"><bean:message key="loan.monthlyInterestRepaymentType"/></td>
					            		<td>
					            			<html:select styleId="monthlyInterestRepaymentTypeSelect" property="transferType(loan).monthlyInterestRepaymentType" disabled="true" styleClass="InputBoxDisabled">
				            					<option></option>
					            				<c:forEach var="tt" items="${loanRepaymentTypes}">
					            					<html:option value="${tt.id}">${tt.name}</html:option>
					            				</c:forEach>
					            			</html:select>
										</td>
					            	</tr>
									<tr class="trLoanWithInterest">
										<td class="label" nowrap="nowrap"><bean:message key="loan.grantFee"/></td>
										<td>
											<html:text property="transferType(loan).grantFee.value" readonly="true" styleClass="small float InputBoxDisabled"/>
											<html:select property="transferType(loan).grantFee.type" disabled="true" styleClass="InputBoxDisabled">
												<c:forEach var="type" items="${amountTypes}">
													<html:option value="${type}"><bean:message key="global.amount.type.${type}"/></html:option>
												</c:forEach>
											</html:select>
										</td>
									</tr>
									<tr class="trLoanWithInterest">
		            					<td class="label" nowrap="nowrap"><bean:message key="loan.grantFeeRepaymentType"/></td>
					            		<td>
					            			<html:select styleId="grantFeeRepaymentTypeSelect" property="transferType(loan).grantFeeRepaymentType" disabled="true" styleClass="InputBoxDisabled">
				            					<option></option>
					            				<c:forEach var="tt" items="${loanRepaymentTypes}">
					            					<html:option value="${tt.id}">${tt.name}</html:option>
					            				</c:forEach>
					            			</html:select>
										</td>
					            	</tr>
									<tr class="trLoanWithInterest">
										<td class="label" nowrap="nowrap"><bean:message key="loan.expirationFee"/></td>
										<td>
											<html:text property="transferType(loan).expirationFee.value" readonly="true" styleClass="small float InputBoxDisabled"/>
											<html:select property="transferType(loan).expirationFee.type" disabled="true" styleClass="InputBoxDisabled">
												<c:forEach var="type" items="${amountTypes}">
													<html:option value="${type}"><bean:message key="global.amount.type.${type}"/></html:option>
												</c:forEach>
											</html:select>
										</td>
									</tr>
									<tr class="trLoanWithInterest">
		            					<td class="label" nowrap="nowrap"><bean:message key="loan.expirationFeeRepaymentType"/></td>
					            		<td>
					            			<html:select styleId="expirationFeeRepaymentTypeSelect" property="transferType(loan).expirationFeeRepaymentType" disabled="true" styleClass="InputBoxDisabled">
				            					<option></option>
					            				<c:forEach var="tt" items="${loanRepaymentTypes}">
					            					<html:option value="${tt.id}">${tt.name}</html:option>
					            				</c:forEach>
					            			</html:select>
										</td>
					            	</tr>
									<tr class="trLoanWithInterest">
										<td class="label"><bean:message key="loan.expirationDailyInterest"/></td>
										<td><html:text property="transferType(loan).expirationDailyInterest" readonly="true" styleClass="small float InputBoxDisabled"/>%</td>
									</tr>
									<tr class="trLoanWithInterest">
		            					<td class="label" nowrap="nowrap"><bean:message key="loan.expirationDailyInterestRepaymentType"/></td>
					            		<td>
					            			<html:select styleId="expirationDailyInterestRepaymentTypeSelect" property="transferType(loan).expirationDailyInterestRepaymentType" disabled="true" styleClass="InputBoxDisabled">
				            					<option></option>
					            				<c:forEach var="tt" items="${loanRepaymentTypes}">
					            					<html:option value="${tt.id}">${tt.name}</html:option>
					            				</c:forEach>
					            			</html:select>
										</td>
					            	</tr>
				            	</table>
							</fieldset>
						</td>
					</tr>
				</c:if>
				
				<tr>
					<td align="right" colspan="2">
						<c:if test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">					
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
						</c:if>
					</td>
				</tr>
            </table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
</ssl:form>

<c:if test="${not isInsert}">
	
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key='transferType.fields.title.list'/></td>
	        <cyclos:help page="account_management#payment_fields_list"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                	<td class="tdHeaderContents"><bean:message key="customField.name"/></td>
	                	<td class="tdHeaderContents"><bean:message key="customField.payment.type"/></td>
	                	<td class="tdHeaderContents" width="20%"><bean:message key="customField.payment.enabled"/></td>
	                    <td class="tdHeaderContents" width="10%">&nbsp;</td>
	                </tr>
	                <c:forEach var="field" items="${customFields}">
	                	<c:set var="owned" value="${field.transferType == transferType}"/>
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                	<td><cyclos:escapeHTML>${field.name}</cyclos:escapeHTML></td>
		                	<td>
			                	<c:choose><c:when test="${owned}">
			                		<bean:message key="customField.payment.type.OWNED"/>
			                	</c:when><c:otherwise>
			                		<bean:message key="customField.payment.type.LINKED" arg0="${field.transferType.name}"/>
			                	</c:otherwise></c:choose>
		                	</td>
		                	<td align="center"><bean:message key="global.${field.enabled ? 'yes' : 'no'}" /></td>
		                    <td align="center" nowrap="nowrap">
		                    	<c:choose><c:when test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
		                    		<c:choose><c:when test="${owned}">
					                    <img class="edit fieldDetails" src="<c:url value="/pages/images/edit.gif"/>" fieldId="${field.id}" border="0">
					                </c:when><c:otherwise>
						                <img class="view fieldDetails" src="<c:url value="/pages/images/view.gif"/>" fieldId="${field.id}" border="0">
									</c:otherwise></c:choose>
			                    	<img class="remove removeField" src="<c:url value="/pages/images/delete.gif"/>" fieldId="${field.id}" border="0">
				                </c:when><c:otherwise>
					                <img class="view fieldDetails" src="<c:url value="/pages/images/view.gif"/>" fieldId="${field.id}" border="0">
								</c:otherwise></c:choose>
		                    </td>
		                </tr>
	                </c:forEach>                
	            </table>
	        </td>
	    </tr>
	</table>
	<c:if test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
	
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right" width="50%">
					<span class="label"><bean:message key='transferType.fields.action.new'/></span>
					<input class="button" type="button" id="newFieldButton" value="<bean:message key='global.submit'/>">
				</td>
				<td align="right" width="50%">
					<span class="label"><bean:message key='transferType.fields.action.link'/></span>
					<input class="button" type="button" id="linkFieldButton" value="<bean:message key='global.submit'/>">
				</td>
			</tr>
			<c:if test="${fn:length(transferType.customFields) > 1}">
				<tr>
					<td colspan="2" align="right" style="padding-top:5px">
						<span class="label"><bean:message key="customField.action.changeOrder"/></span>
						<input type="button" class="button" id="changeFieldOrderButton" value="<bean:message key="global.submit"/>">
					</td>
				</tr>
			</c:if>
		</table>
		<div id="linkFieldContainer" style="display:none">
			
			<ssl:form method="post" action="${actionPrefix}/linkPaymentCustomField">
	
				<table class="defaultTableContent" cellspacing="0" cellpadding="0">
				    <tr>
				        <td class="tdHeaderTable"><bean:message key='transferType.fields.action.link'/></td>
				        <cyclos:help page="account_management#payment_fields_link"/>
				    </tr>
				    <tr>
				        <td colspan="2" align="left" class="tdContentTableForms">
				            <table class="defaultTable">
								<tr>
				            		<td class="label" width="25%"><bean:message key="account.type"/></td>
				            		<td>
				            			<select id="linkedFieldAccountType">
				            				<c:forEach var="at" items="${linkedFieldAccountTypes}">
				            					<option value="${at.id}">${at.name}</option>
				            				</c:forEach>
				            			</select>
				            		</td>
				            	</tr>
				            	<tr>
				            		<td class="label"><bean:message key="transfer.type"/></td>
				            		<td><select id="linkedFieldTransferType"></select></td>
				            	</tr>
				            	<tr>
				            		<td class="label" width="25%"><bean:message key="customField.possibleValue.field"/></td>
				            		<td><select id="linkedFieldCustomField"></select></td>
				            	</tr>
				            	<tr>
				            		<td align="left">
				            			<input type="button" id="cancelLinkedCustomFieldButton" class="button" value="<bean:message key="global.cancel"/>">
				            		</td>
				            		<td align="right">
				            			<input type="button" id="saveLinkedCustomFieldButton" class="button" value="<bean:message key="global.submit"/>">
				            		</td>
				            	</tr>
				            </table>
				        </td>
				    </tr>
				</table>
			</ssl:form>
		</div>
	</c:if>
</c:if>

<c:if test="${not isInsert && transferType.requiresAuthorization}">
	
	<div id="authorizationLevelForm" style="display:none">
	<ssl:form method="post" action="/admin/editAuthorizationLevel">
	<html:hidden property="authorizationLevelId"/>
	<html:hidden property="authorizationLevel(id)"/>
	<html:hidden property="authorizationLevel(transferType)" value="${transferType.id}"/>
	<html:hidden property="authorizationLevel(level)"/>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="authorizationLevel.title.edit"/></td>
	        <cyclos:help page="account_management#edit_authorization_level"/>
	    </tr>
	    <tr>
			<td colspan="2" align="left" class="tdContentTableForms">
	            <table class="defaultTable">
	            	<tr id="trLevelReadOnly" style="display:none">
	        			<td class="label" width="40%" nowrap="nowrap"><bean:message key="authorizationLevel.level"/></td>
	            		<td>
	            			<input type="text" name="levelReadOnly" class="medium InputBoxDisabled" disabled="disabled"/>
						</td>
	            	</tr>
					<tr>
						<td class="label"><bean:message key="authorizationLevel.amount"/></td>
						<td><html:text property="authorizationLevel(amount)" styleClass="medium float"/></td>
				    </tr>
				    <tr>
	        			<td class="label" width="40%" nowrap="nowrap"><bean:message key="authorizationLevel.authorizer"/></td>
	            		<td id="tdAuthorizerReadOnly" style="display:none">
	            			<input type="text" name="authorizerReadOnly" class="medium InputBoxDisabled" disabled="disabled"/>
						</td>
						<td id="tdAuthorizerSelect">
	            			<html:select styleId="authorizerSelect" property="authorizationLevel(authorizer)">
	            				<c:forEach var="authorizer" items="${possibleAuthorizers}">
	            					<html:option value="${authorizer}"><bean:message key="authorizationLevel.authorizer.${authorizer}"/></html:option>
	            				</c:forEach>
							</html:select>
						</td>
	            	</tr>
	            	<tr id="trAdminGroups" style="display:none">
						<td class="label">
							<bean:message key="authorizationLevel.adminGroups"/>&nbsp;
						</td>
						<td>
							<cyclos:multiDropDown 
									varName="groupsMultiDropDown" 
									name="authorizationLevel(adminGroups)" 
									emptyLabelKey="authorizationLevel.adminGroups.none">
								<c:forEach var="group" items="${adminGroups}">
									<cyclos:option value="${group.id}" text="${group.name}" />
								</c:forEach>
							</cyclos:multiDropDown>
						</td>
					</tr>
					<tr>
						<td align="right" colspan="2">
							<c:if test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
								
								<input type="button" id="cancelAuthorizationLevelButton" class="button" value="<bean:message key="global.cancel"/>">&nbsp;					
								<input type="submit" id="insertAuthorizationLevelButton" class="button" value="<bean:message key="global.submit"/>">
							</c:if>
						</td>
					</tr>
					
				</table>
			</td>
		</tr>
	</table>
	</ssl:form>
	</div>
	
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="authorizationLevel.title.list"/></td>
	        <cyclos:help page="account_management#authorized_payment_levels"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
				<c:set var="lastLevelAmount" value="${0}" />
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents" width="10%"><bean:message key="authorizationLevel.level"/></th>
	                    <th class="tdHeaderContents"><bean:message key="authorizationLevel.amount"/></th>
	                    <th class="tdHeaderContents"><bean:message key="authorizationLevel.authorizer"/></th>
	                    <th class="tdHeaderContents"><bean:message key="authorizationLevel.adminGroups"/></th>
	                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
	                </tr>
	                <c:forEach var="level" items="${authorizationLevels}">
						<c:set var="lastLevelAmount" value="${level.amount}" />
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		            		<td align="center">${level.level}</td>
		            		<td><cyclos:format number="${level.amount}"/></td>
		            		<td><bean:message key="authorizationLevel.authorizer.${level.authorizer}"/></td>
		            		<td>
			            		<c:forEach var="g" varStatus="status" items="${level.adminGroups}">
								    ${g.name}${status.last ? '' : ', '}
								</c:forEach>
							</td>
		            		<td align="center">
		            			<c:if test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
									<img class="edit authorizationLevelDetails" authorizationLevelId="${level.id}" level="${level.level}" src="<c:url value="/pages/images/edit.gif" />"/>
		            				<img class="remove removeAuthorizationLevel" authorizationLevelId="${level.id}" src="<c:url value="/pages/images/delete.gif" />"/>
				                </c:if>
		                    </td>
		                </tr>
	                </c:forEach>
	            </table>
	        </td>
	    </tr>
	</table>
	
	<c:if test="${insertNewLevel && cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
		<script>var lastLevelAmount = ${lastLevelAmount};</script>
		
		<table class="defaultTableContentHidden">
			<tr>
				<td align="right">
					<span class="label"><bean:message key="authorizationLevel.action.new"/></span>
					<input class="button" type="button" id="newAuthorizationLevelButton" value="<bean:message key="global.submit"/>">
				</td>
			</tr>
		</table>
	</c:if>
</c:if>

<c:if test="${not isInsert}">
	
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="transactionFee.title.simple.list"/></td>
	        <cyclos:help page="account_management#transaction_fee_search"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents"><bean:message key="transactionFee.name"/></th>
	                    <th class="tdHeaderContents" width="15%"><bean:message key="transactionFee.amount"/></th>
	                    <th class="tdHeaderContents" width="12%"><bean:message key="transactionFee.enabled"/></th>
	                    <th class="tdHeaderContents" width="7%">&nbsp;</th>
	                </tr>
	                <c:forEach var="fee" items="${simpleTransactionFees}">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		            		<td align="left">${fee.name}</td>
		            		<td align="center"><cyclos:format amount="${fee.amount}" unitsPattern="${fee.generatedTransferType.currency.pattern}"/></td>
		            		<td align="center"><bean:message key="global.${fee.enabled ? 'yes' : 'no'}" /></td>
		            		<td align="center">
		            			<c:choose><c:when test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
									<img class="edit transactionFeeDetails" transactionFeeId="${fee.id}" src="<c:url value="/pages/images/edit.gif" />"/>
		            				<img class="remove removeTransactionFee" transactionFeeId="${fee.id}" src="<c:url value="/pages/images/delete.gif" />"/>
				                </c:when><c:otherwise>
				                	<img class="view transactionFeeDetails" transactionFeeId="${fee.id}" src="<c:url value="/pages/images/view.gif" />" />
								</c:otherwise></c:choose>
		                    </td>
		                </tr>
	                </c:forEach>
	            </table>
	        </td>
	    </tr>
	</table>
	
	<c:if test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
		<table class="defaultTableContentHidden">
			<tr>
				<td align="right">
					<span class="label"><bean:message key="transactionFee.action.simple.new"/></span>
					<input class="button" type="button" id="newSimpleTransactionFeeButton" value="<bean:message key="global.submit"/>">
				</td>
			</tr>
		</table>
	</c:if>
	
	<c:if test="${transferType.fromMember}">
		
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="transactionFee.title.broker.list"/></td>
		        <cyclos:help page="brokering#broker_commission_list"/>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable">
		                <tr>
		                    <th class="tdHeaderContents"><bean:message key="transactionFee.name"/></th>
		                    <th class="tdHeaderContents" width="20%"><bean:message key="transactionFee.enabled"/></th>
		                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
		                </tr>
		                <c:forEach var="fee" items="${brokerCommissions}">
							<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			            		<td align="left">${fee.name}</td>
			            		<td align="center"><bean:message key="global.${fee.enabled ? 'yes' : 'no'}" /></td>
			            		<td align="center">
			            			<c:choose><c:when test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
										<img class="edit transactionFeeDetails" transactionFeeId="${fee.id}" src="<c:url value="/pages/images/edit.gif" />"/>
			            				<img class="remove removeBrokerCommission" transactionFeeId="${fee.id}" src="<c:url value="/pages/images/delete.gif" />"/>
					                </c:when><c:otherwise>
					                	<img class="view transactionFeeDetails" transactionFeeId="${fee.id}" src="<c:url value="/pages/images/view.gif" />" />
									</c:otherwise></c:choose>
			                    </td>
			                </tr>
		                </c:forEach>
		            </table>
		        </td>
		    </tr>
		</table>
		
		<c:if test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
			<table class="defaultTableContentHidden">
				<tr>
					<td align="right">
						<span class="label"><bean:message key="transactionFee.action.broker.new"/></span>
						<input class="button" type="button" id="newBrokerCommissionButton" value="<bean:message key="global.submit"/>">
					</td>
				</tr>
			</table>
		</c:if>
	</c:if>

</c:if>
