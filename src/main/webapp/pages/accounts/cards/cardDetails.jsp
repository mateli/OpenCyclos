<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/cards/cardDetails.js" />
<script>
var confirmActivateCard = "<cyclos:escapeJS><bean:message key="card.activateCard.confirmation"/></cyclos:escapeJS>"
var confirmBlockCard = "<cyclos:escapeJS><bean:message key="card.blockCard.confirmation"/></cyclos:escapeJS>"
var confirmUnblockCard = "<cyclos:escapeJS><bean:message key="card.unblockCard.confirmation"/></cyclos:escapeJS>"
var confirmCancelCard = "<cyclos:escapeJS><bean:message key="card.cancelCard.confirmation"/></cyclos:escapeJS>"
var confirmChangeCardCode = "<cyclos:escapeJS><bean:message key="card.changeCardCode.confirmation"/></cyclos:escapeJS>"
var confirmUnblockSecurityCode = "<cyclos:escapeJS><bean:message key="card.changeSecurityCode.confirmation"/></cyclos:escapeJS>"
var activateWarning = "<cyclos:escapeJS><bean:message key="card.activateWarning.confirmation"/></cyclos:escapeJS>"

var actionActivate  = "<cyclos:escapeJS><bean:message key="card.action.activate"/></cyclos:escapeJS>"
var actionBlock  = "<cyclos:escapeJS><bean:message key="card.action.block"/></cyclos:escapeJS>"
var actionUnblock  = "<cyclos:escapeJS><bean:message key="card.action.unblock"/></cyclos:escapeJS>"
var actionUnblockSecCode  = "<cyclos:escapeJS><bean:message key="card.action.unblockSecurityCode"/></cyclos:escapeJS>"
var actionCancel  = "<cyclos:escapeJS><bean:message key="card.action.cancel"/></cyclos:escapeJS>"
var actionChangeCode  = "<cyclos:escapeJS><bean:message key="card.action.changeCardCode"/></cyclos:escapeJS>"


var usesTransactionPassword = ${usesTransactionPassword}
var transactionPasswordPending =  ${transactionPasswordPending}
var transactionPasswordBlocked = ${transactionPasswordBlocked}
var ignoreCase = ${empty usesTransactionPassword ? false : usesTransactionPassword};
var cardId = ${cardId}
var memberId = ${memberId}
var listOnly = ${listOnly}
var isManualCardCode = ${isManualCardCode}
var hasActiveCard = ${hasActiveCard}
</script>
<ssl:form styleId="cardForm" method="POST" action="${formAction}">

<c:choose>
	<c:when test="${empty card}">
		<div class="footerNote"><bean:message key="card.search.noResults"/></div>
	</c:when>
	<c:otherwise>
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="card.manage"/></td>
		        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
		        	<cyclos:help page="access_devices#card_details" td="false" />
		        </td>		        
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		        	<cyclos:layout className="defaultTable" columns="4">
			        	<tr>
							<td colspan="4">
									<fieldset>
										<legend><bean:message key="card.card"/></legend>
										<cyclos:layout className="defaultTable" columns="4">	
										<tr>
											<td class="label tdHeaderContents" width="30%"><bean:message key="card.member"/></td>
							                <td><cyclos:profile elementId="${card.owner.id}"/></td>
										</tr>
										<tr>		                	
							                <td class="label tdHeaderContents" width="30%"><bean:message key="card.number"/></td>
							                <td><cyclos:format number="${card.cardNumber}" cardNumberPattern="${card.cardType.cardFormatNumber}"/></td>
							            </tr>
							            <c:if test="${not empty card.activationDate}">
								            <tr>
								                <td class="label tdHeaderContents" width="30%"><bean:message key="card.activationDate"/></td>
								                <td><cyclos:format date="${card.activationDate}"/></td>
								            </tr>
							            </c:if>
							            <tr>
							                <td class="label tdHeaderContents" width="30%"><bean:message key='card.creationDate'/></td>
							                <td><cyclos:format date="${card.creationDate}"/></td>
							            </tr>
							            <tr>
							                <td class="label tdHeaderContents" width="30%"><bean:message key='card.expirationDate'/></td>
							                <td><cyclos:format rawDate="${card.expirationDate}"/></td>
							            </tr>
							            <tr>
							                <td class="label tdHeaderContents" width="30%"><bean:message key='card.status'/></td>
							                <c:choose>
							                	<c:when test="${isCardBlocked}"><td><bean:message key="card.status.securityCodeBlocked"/></td></c:when>
							                	<c:otherwise><td><bean:message key="card.status.${card.status}"/></td></c:otherwise>
							                </c:choose>
							            </tr>
							            <c:if test="${showCardSecurityCode && not empty card.cardSecurityCode}">
								            <tr>
								                <td class="label tdHeaderContents" width="30%"><bean:message key='card.securityCode'/></td>
								                <td>${card.cardSecurityCode}</td>
								            </tr>
								        </c:if>
							         </cyclos:layout>
							     </fieldset>
							</td>
			            </tr>
			            <c:if test="${canBlock || canUnblock || canActivate || canCancel || canChangeCode || showUnblockSecurityCodeButton}">
				            <tr>
								<td colspan="4">
									<fieldset>
										<legend><bean:message key="card.actions"/></legend>
										<cyclos:layout className="defaultTable" columns="4">
											<c:if test="${canBlock}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="card.action.block"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="blockCard" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>							                    			                    
									        </c:if>
									        <c:if test="${canUnblock}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="card.action.unblock"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="unblockCard" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>			                    
									        </c:if>
									        <c:if test="${canActivate}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="card.action.activate"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="activateCard" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>			                    
									        </c:if>
									        <c:if test="${canCancel}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="card.action.cancel"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="cancelCard" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>			                    
									        </c:if>
									        <c:if test="${canChangeCode}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="card.action.changeCardCode"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="changeCardCode" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>			                    
									        </c:if>
									        <c:if test="${showUnblockSecurityCodeButton}">
							                    <cyclos:cell width="35%" className="label"><bean:message key="card.action.unblockSecurityCode"/></cyclos:cell>
							                    <cyclos:cell><input type="button" id="unblockSecurityCode" class="button" value="<bean:message key="global.submit"/>"></cyclos:cell>			                    
									        </c:if>						        
									   	</cyclos:layout>
									</fieldset>
								</td>
							</tr>
						</c:if>
						<tr>
							<td colspan="4">
								<fieldset id="tableDiv" style="display:none">
									<legend id="legendId"></legend>
									<table class="defaultTable">										
						        		<tr id="cardCodeDiv" style="display:none">
								        	<td class="label" ><bean:message key="card.changeCardCode.newCode1"/></td>
									        <td>
								            	<input type="${showCardSecurityCode ? 'text' : 'password'}" name="securityCode" class="medium number" style="width: 150px;" >
								            </td>
								        </tr>
								        <c:if test="${not showCardSecurityCode}">
									        <tr style=""><td></td></tr>
									        <tr id="cardCodeDiv2" style="display:none">
									            <td class="label" ><bean:message key="card.changeCardCode.newCode2"/></td>
				                     			<td>
				                     				<input type="password" name="securityCodeConfirmation" class="medium number" style="width: 150px;" >
				                     			</td>
				                     		</tr>
								        </c:if>
			                     		 <tr id="passwordDiv" style="display:none">
						        			<td>
								        		<c:choose>
								        			<c:when test="${transactionPasswordBlocked}">
								        				<div align="center">
									        					<cyclos:escapeHTML brOnly="true"><bean:message key="editCard.error.transactionPasswordBlocked" /></cyclos:escapeHTML>
									        			</div>
										        	</c:when>
										        	<c:when test="${transactionPasswordPending}">
										        		<div align="center">
											        		<c:url value="${pathPrefix}/home" var="homeUrl" />
											        		<bean:message key="editCard.error.transactionPasswordPending" arg0="${homeUrl}" />
											        	</div>
										        	</c:when>
										        	<c:otherwise>
												    		<c:choose>
												        		<c:when test="${usesTransactionPassword}">
														            <tr>
														                 <td class="label"><bean:message key="login.transactionPassword"/></td>
														                  <td>
														                   	<c:set var="hideSubmit" value="${true}" scope="request"/>
														                   	<c:set var="transactionPasswordField" value="password" scope="request" />
																			<jsp:include page="/do/transactionPassword"/>
														                 </td>												                 
														            </tr>
														       	</c:when>						       	
														    </c:choose>
														
													</c:otherwise>
												</c:choose>
											</td>
								        </tr>								       
			                     	    <tr id="submitDiv" style="display:none">
							            	<td colspan="4" align="right">
												<input type="submit" id="submitButton" class="button" value="<bean:message key="global.submit"/>">
											</td>
									    </tr>									    
								    </table>
						    	</fieldset>
							</td>
						</tr>								
			        </cyclos:layout>
			    </td>			         		       
		    </tr>		   
		</table>				 				
	</c:otherwise>
</c:choose>
</ssl:form>
<c:if test="${isAdmin && not empty card.logs}">
	
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<td class="tdHeaderTable"><bean:message key="cardLog.title"/></td>
		<cyclos:help page="access_devices#card_logs" />
		<tr>
		    <td colspan="6" align="left" class="tdContentTableForms">
	    	  	<table class="defaultTable">
					<tr>
						<td class="tdHeaderContents" width="30%"><bean:message key="cardLog.status"/></td>
						<th class="tdHeaderContents" width="30%"><bean:message key="cardLog.by"/></th>
						<td class="tdHeaderContents" width="40%"><bean:message key="cardLog.date"/></td>
					</tr>
					<c:forEach var="log" items="${card.logs}" varStatus="idx">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="left"><bean:message key="card.status.${log.status}"/></td>
							<c:choose>	    	        			
		            			<c:when test="${logsBy[idx.index].byType == 'System'}">
		            				<td align="left" ><cyclos:escapeHTML value="${localSettings.applicationUsername}"/></td>
		            			</c:when>
		            			<c:when test="${logsBy[idx.index].byType == 'SystemTask'}">
		            				<td align="left" ><bean:message key="global.system"/></td>
		            			</c:when>		            			
								<c:otherwise>
									<td align="left" title="<cyclos:escapeHTML value="${logsBy[idx.index].by.name}"/>"><cyclos:profile elementId="${logsBy[idx.index].by.id}"/>								
								</c:otherwise>
		            		</c:choose>
							<td align="center"><cyclos:format dateTime="${log.date}"/></td>
						</tr>						
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</c:if>

<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">