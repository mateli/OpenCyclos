<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<c:choose>
	<c:when test="${isInsert}">
		<c:set var="titleKey" value="guaranteeType.title.new"/>
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="guaranteeType.title.modify"/>		
	</c:otherwise>
</c:choose>

<c:set var="helpPage" value="guarantees#edit_guarantee_type"/>

<cyclos:script src="/pages/accounts/guarantees/types/editGuaranteeType.js" />

<script>
	var allAuthorizers = ${allAuthorizersStr};
	var paymentObligationAuthorizers = ${paymentObligationAuthorizersStr};
	var paymentObligationAuthorizersI18N = ${paymentObligationAuthorizersI18N};
	var allAuthorizersI18N = ${allAuthorizersI18N};
	var originalAuthorizer = '${isInsert ? null : guaranteeType.authorizedBy}';
	var transferTypeSelectOption = '<bean:message key="guaranteeType.transferType.select"/>';
</script>

<ssl:form action="${formAction}" method="post">
	<html:hidden property="guaranteeTypeId"/>
	<html:hidden property="guaranteeType(id)"/>

	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
			<cyclos:help page="${helpPage}" />
		</tr>
		<tr>
	        <td colspan="4" align="left" class="tdContentTableForms">
	        	<table class="defaultTable"> 
	        		<tr>
		            	<td class="label"><bean:message key="guaranteeType.name" /></td>
		            	<td colspan="3">
		            		<div style="white-space: nowrap"><html:text property="guaranteeType(name)" maxlength="100" readonly="true" styleClass="full InputBoxDisabled required"/></div>
		            	</td>
		            </tr>
		            <tr>
	            		<td class="label"><bean:message key="guaranteeType.currency"/></td>
	            		<td>
	            			<c:choose><c:when test="${isInsert}">
	            			<div style="white-space: nowrap">
		            			<html:select property="guaranteeType(currency)" disabled="true" styleClass="InputBoxDisabled required" styleId="currenciesSelect">
		            				<c:forEach var="currency" items="${currencies}">
		            					<html:option value="${currency.id}">${currency.name}</html:option>
		            				</c:forEach>
		            			</html:select>
							</div>		            			
		            		</c:when><c:otherwise>
			            		<input type="hidden" name="guaranteeType(currency)" value="${guaranteeType.currency.id}"/>
			            		<input id="currencyText" class="InputBoxDisabled medium" readonly value="${guaranteeType.currency.name}"/>
				            </c:otherwise></c:choose>	
	            		</td>
		            </tr>
		            <tr>
		            	<td class="label"><bean:message key="guaranteeType.model" /></td>
		            	<td>
		            		<c:choose><c:when test="${isInsert}">
		            		<div style="white-space: nowrap">
		            			<html:select property="guaranteeType(model)" disabled="true" styleClass="InputBoxDisabled required" styleId="guaranteeTypesModels">
		            				<c:forEach var="model" items="${model}">
		            					<html:option value="${model}"><bean:message key="guaranteeType.model.${model}"/></html:option>
		            				</c:forEach>
		            			</html:select>
		            		</div>
		            		</c:when><c:otherwise>
			            		<input type="hidden" name="guaranteeType(model)" value="${guaranteeType.model}" id="guaranteeTypesModels"/>
			            		<input id="modelText" class="InputBoxDisabled medium" readonly value="<bean:message key="guaranteeType.model.${guaranteeType.model}"/>"/>
				            </c:otherwise></c:choose>
		            	</td>
		            	</tr>
		            <tr>
		            	<td class="label"><bean:message key="guaranteeType.enabledGuaranteeType" /></td>
		            	<td>
		            		<html:checkbox property="guaranteeType(enabled)" value="true" disabled="true" />
		            	</td>
		            </tr>
		            <tr>
		            	<td class="label"><bean:message key="guaranteeType.authorizedBy" /></td>
		            	<td>
		            	<div style="white-space: nowrap">
	            			<html:select property="guaranteeType(authorizedBy)" disabled="true" styleClass="InputBoxDisabled required" styleId="authorizedBy">
	            				<c:forEach var="authorizedBy" items="${allAuthorizers}">
	            					<html:option value="${authorizedBy}"><bean:message key="guaranteeType.authorizedBy.${authorizedBy}"/></html:option>
	            				</c:forEach>
	            			</html:select>
	            		</div>
		            	</td>
		            	<td>&nbsp;</td>
		            	<td>&nbsp;</td>
		            </tr>
					<tr class="toHidePendingExpiration" style="display:none;">
		            	<td class="label"><bean:message key="guaranteeType.pendingGuaranteeExpiration" /></td>
		            	<bean:define id="pendingGuaranteeExpirationTooltip"><bean:message key="guaranteeType.pendingGuaranteeExpiration.tooltip" /></bean:define>
		            	<td colspan="3" title="${pendingGuaranteeExpirationTooltip}" nowrap="nowrap">
	                    	<html:text property="guaranteeType(pendingGuaranteeExpiration).number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<html:select property="guaranteeType(pendingGuaranteeExpiration).field" styleClass="small InputBoxDisabled" disabled="true">
		                   		<c:forEach var="field" items="${pendingGuaranteeExpiration}">
		                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    	(<span class="fieldDecoration"><bean:message key="guaranteeType.answerTodayMessage"/></span>)
	                    </td>	             
	                </tr>
	                <tr class="toHide" style="display:none">    
		            	<td class="label"><bean:message key="guaranteeType.paymentObligationPeriod" /></td>
		            	<bean:define id="paymentObligationPeriodTooltip"><bean:message key="guaranteeType.paymentObligationPeriod.tooltip" /></bean:define>
		            	<td title="${paymentObligationPeriodTooltip}" nowrap="nowrap">
	                    	<html:text property="guaranteeType(paymentObligationPeriod).number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<html:select property="guaranteeType(paymentObligationPeriod).field" styleClass="small InputBoxDisabled" disabled="true">
		                   		<c:forEach var="field" items="${paymentObligationPeriod}">
		                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    	(<span class="fieldDecoration"><bean:message key="guaranteeType.expireTodayMessage"/></span>)
	                    </td>
		            </tr>
				
					<tr>
						<td class="label"><bean:message key="guaranteeType.creditFee" /></td>
						<td colspan="1" align="left">
							<fieldset>
		                  		<table class="defaultTable" align="center">
			                  		<tr>
				                  		<td class="label"><bean:message key="guaranteeType.fee.value" /></td>
						            	<td sty_le="background-color: green;">
						            		<html:text property="guaranteeType(creditFee).fee" styleClass="tiny float InputBoxDisabled" styleId="guaranteeType(creditFee).fee" readonly="true"/>
					                    	<html:select property="guaranteeType(creditFee).type" styleClass="small InputBoxDisabled" disabled="true">
						                   		<c:forEach var="feeType" items="${feeTypes}">
						                    		<html:option value="${feeType}"><bean:message key="guaranteeType.feeType.${feeType}"/></html:option>
						                   		</c:forEach>
					                    	</html:select>
					                    </td>
				                    </tr>
				                    <tr class="toHidePayer" style="display:none">
					                    <td class="label"><bean:message key="guaranteeType.fee.paidBy" /></td>
					                    <td st_yle="background-color: red;">
					               			<html:select property="guaranteeType(creditFeePayer)" disabled="true" styleClass="medium InputBoxDisabled" styleId="creditFeePayers">
					            				<c:forEach var="creditFeePayer" items="${feePayers}">
					            					<html:option value="${creditFeePayer}"><bean:message key="guaranteeType.feePayers.${creditFeePayer}"/></html:option>
					            				</c:forEach>
					            			</html:select>	                  	
					            		</td>
				            		</tr>
				            		<tr>
					            		<td class="label">
					                    	<bean:message key="guaranteeType.creditFee.readonly" />
					                    </td>
					                    <td>
					                    	<html:checkbox styleId="isCreditFeeReadonly" styleClass="checkbox" property="guaranteeType(creditFee).readonly" value="true" disabled="true" />
					                    </td>
				                    </tr>
			                   	</table>
			            	</fieldset>
		            	</td>
		            </tr>
					<tr>
						<td class="label" nowrap="nowrap"><bean:message key="guaranteeType.issueFee" /></td>
						<td colspan="1" align="left">
							<fieldset>
		                  		<table class="defaultTable" align="center">
			                  		<tr>
				                  		<td class="label"><bean:message key="guaranteeType.fee.value" /></td>
						            	<td>
						            		<html:text property="guaranteeType(issueFee).fee" styleClass="tiny float InputBoxDisabled" styleId="guaranteeType(issueFee).fee" readonly="true"/>
					                    	<html:select property="guaranteeType(issueFee).type" styleClass="small InputBoxDisabled" disabled="true">
						                   		<c:forEach var="feeType" items="${feeTypes}">
						                    		<html:option value="${feeType}"><bean:message key="guaranteeType.feeType.${feeType}"/></html:option>
						                   		</c:forEach>
					                    	</html:select>
					                    </td>
				                    </tr>
				                    <tr class="toHidePayer" style="display:none">
					                    <td class="label"><bean:message key="guaranteeType.fee.paidBy" /></td>
					                    <td>
					               			<html:select property="guaranteeType(issueFeePayer)" disabled="true" styleClass="medium InputBoxDisabled" styleId="issueFeePayers">
					            				<c:forEach var="issueFeePayer" items="${feePayers}">
					            					<html:option value="${issueFeePayer}"><bean:message key="guaranteeType.feePayers.${issueFeePayer}"/></html:option>
					            				</c:forEach>
					            			</html:select>	                  	
					            		</td>
				            		</tr>
				            		<tr>
					            		<td class="label">
					                    	<bean:message key="guaranteeType.issueFee.readonly" />
					                    </td>
					                    <td>
					                    	<html:checkbox styleId="isIssueFeeReadonly" styleClass="checkbox" property="guaranteeType(issueFee).readonly" value="true" disabled="true" />
					                    </td>
				                    </tr>
			                   	</table>
			            	</fieldset>
		            	</td>
		            </tr>
		            
				
		            <tr>
		           		<td class="label"><bean:message key="guaranteeType.description" /></td>
		           		<td colspan="4"><html:textarea property="guaranteeType(description)" readonly="true" styleClass="full InputBoxDisabled" rows="5"/></td>
					</tr>
					<tr>
		            	<td class="label"><bean:message key="guaranteeType.transferTypes"/></td>
		            	<td colspan="3">
			            	<fieldset style="margin: 0px;">
								<table class="defaultTable" align="center">
									<tr class="toHideCreditFee" style="display:none;">
										<td class="label" nowrap="nowrap">
											<bean:message key="guaranteeType.creditFeeTransferType" />
										</td>
										<td>
											<div style="white-space: nowrap">
										    	<html:select property="guaranteeType(creditFeeTransferType)" styleClass="large InputBoxDisabled required" disabled="true">
										    		<html:option value=""><bean:message key="guaranteeType.transferType.select"/></html:option>
										    		<c:forEach var="creditFeeTransferTypes" items="${creditFeeTransferType}">
														<html:option value="${creditFeeTransferTypes.id}">${creditFeeTransferTypes.name}</html:option>
											        </c:forEach>
										    	</html:select>
										    </div>
							    		</td>
								   	</tr>
									<tr class="toHideIssueFee" style="display:none;">
									  	<td class="label" nowrap="nowrap">
									  		<bean:message key="guaranteeType.issueFeeTransferType" />
									  	</td>
									  	<td>
											<div style="white-space: nowrap">
												<html:select property="guaranteeType(issueFeeTransferType)" styleClass="large InputBoxDisabled required" disabled="true">
													<html:option value=""><bean:message key="guaranteeType.transferType.select"/></html:option>
											 		<c:forEach var="issueFeeTransferType" items="${issueFeeTransferType}">
												  		<html:option value="${issueFeeTransferType.id}">${issueFeeTransferType.name}</html:option>
											 		</c:forEach>
												</html:select>
											</div>
									  	</td>
								    </tr>
								    <tr>
								    	<td class="label">
											<bean:message key="guaranteeType.loanTransferType" />
										</td>
								    	<td>
									    	<div style="white-space: nowrap">
											   	<html:select property="guaranteeType(loanTransferType)" styleClass="large InputBoxDisabled required" disabled="true">
											   		<html:option value=""><bean:message key="guaranteeType.transferType.select"/></html:option>
										    		<c:forEach var="loanTransferTypes" items="${loanTransferType}">
											     		<html:option value="${loanTransferTypes.id}">${loanTransferTypes.name}</html:option>
										     		</c:forEach>
										    	</html:select>
										    </div>
								    	</td>
							  	  	</tr>
									<tr class="toHideForward" style="display:none">
									  	<td class="label" nowrap="nowrap">
									  		<bean:message key="guaranteeType.forwardTransferType" />
									  	</td>
									  	<td>
										  	<div style="white-space: nowrap">
												<html:select property="guaranteeType(forwardTransferType)" styleClass="large InputBoxDisabled required" disabled="true">
													<html:option value=""><bean:message key="guaranteeType.transferType.select"/></html:option>
											 		<c:forEach var="fwdTransferType" items="${forwardTransferType}">
												  		<html:option value="${fwdTransferType.id}">${fwdTransferType.name}</html:option>
											 		</c:forEach>
												</html:select>
											</div>
									  	</td>
								    </tr>
								</table>
							</fieldset>
		            	</td>
		            </tr>
					
					<c:if test="${editable}">
				        <tr>
							<td align="right" colspan="4">
								<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button"/>
								<input type="submit" id="saveButton" class="ButtonDisabled" disabled value="<bean:message key="global.submit"/>"/>
							</td>
						</tr>
					</c:if>		
	        	</table>
	        </td>
		</tr>		
	</table>
<br/>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
</ssl:form>