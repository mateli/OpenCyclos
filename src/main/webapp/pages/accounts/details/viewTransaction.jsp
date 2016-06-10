<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<c:set var="isScheduledPayment" value="${not empty transfer.rootTransfer.scheduledPayment}" />

<cyclos:script src="/pages/accounts/details/viewTransaction.js" />
<script>
	<c:if test="${canAuthorize}">
		var authorizeConfirmationMessage = "<cyclos:escapeJS><bean:message key="payment.authorizeConfirmationMessage"/></cyclos:escapeJS>";
		var denyConfirmationMessage = "<cyclos:escapeJS><bean:message key="payment.confirmation.deny"/></cyclos:escapeJS>";
		<c:set var="commentsLabel"><bean:message key="transferAuthorization.comments"/></c:set>
		var commentsRequiredMessage = "<cyclos:escapeJS><bean:message key="errors.required" arg0="${commentsLabel}"/></cyclos:escapeJS>";
	</c:if>
	<c:if test="${canCancel}">
		var cancelConfirmationMessage = "<cyclos:escapeJS><bean:message key="payment.confirmation.cancel"/></cyclos:escapeJS>";
	</c:if>
	<c:if test="${canChargeback}">
		var chargebackConfirmation = "<cyclos:escapeJS><bean:message key="payment.confirmation.chargeback"/></cyclos:escapeJS>"
	</c:if>
	var memberId = "${memberId}";
	var typeId = "${typeId}";
</script>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
       		<bean:message key="transfer.title.details"/>
        </td>
        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
        	<img class="print" transferId="${transfer.id}" src="<c:url value="/pages/images/print.gif" />">
        	<cyclos:help page="payments#transaction_detail" td="false"/>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<c:choose><c:when test="${not empty transfer.processDate and transfer.processedAtDifferentDate}">
	            	<tr>
						<td class="label tdHeaderContents" width="30%"><bean:message key="${isScheduledPayment ? 'transfer.scheduledFor' : 'transfer.submitDate'}"/></td>
	            		<%--if the transfer is a scheduled one then the date is stored without the hours/mins that is why we must use the raw date --%>
            			<td><cyclos:format dateTime="${isScheduledPayment ? null : transfer.date}" rawDate="${isScheduledPayment ? transfer.date : null}"/></td>          			
	            	</tr>
	            	<tr>
						<td class="label tdHeaderContents"><bean:message key="transfer.processDate"/></td>
	            		<td><cyclos:format dateTime="${transfer.processDate}"/></td>
	            	</tr>
            	</c:when><c:otherwise>
	            	<tr>
						<td class="label tdHeaderContents" width="30%"><bean:message key="${isScheduledPayment ? 'transfer.scheduledFor' : 'transfer.submitDate'}"/></td>
						<%--if the transfer is a scheduled one then the date is stored without the hours/mins that is why we must use the raw date --%>
            			<td><cyclos:format dateTime="${isScheduledPayment ? null : transfer.date}" rawDate="${isScheduledPayment ? transfer.date : null}"/></td>
	            	</tr>
            	</c:otherwise></c:choose>
				<c:if test="${not empty transfer.transactionNumber}">
					<tr>
						<td class="label tdHeaderContents"><bean:message key="transfer.transactionNumber"/></td>
						<td>${transfer.transactionNumber}</td>
					</tr>
				</c:if>
            	<tr>
					<td class="label tdHeaderContents"><bean:message key="transfer.from"/></td>
            		<td>
            			<c:set var="from" value="${transfer.actualFrom}" />
            			<c:choose><c:when test="${transfer.actuallyFromSystem}">
	            			${from.ownerName}
            			</c:when><c:otherwise>
            				<cyclos:profile elementId="${from.owner.id}"/>
            			</c:otherwise></c:choose>
            		</td>
            	</tr>
            	<tr>
					<td class="label tdHeaderContents"><bean:message key="transfer.to"/></td>
            		<td>
            			<c:set var="to" value="${transfer.actualTo}" />
            			<c:choose><c:when test="${transfer.actuallyToSystem}">
	            			${to.ownerName}
            			</c:when><c:otherwise>
            				<cyclos:profile elementId="${to.owner.id}"/>
            			</c:otherwise></c:choose>
            		</td>
            	</tr>
            	<c:if test="${showBy}">
	            	<tr>
						<td class="label tdHeaderContents"><bean:message key="transfer.by"/></td>
	            		<td>
	            			<c:choose>
	            				<c:when test="${not empty byMember}">
	    	        				<cyclos:profile elementId="${byMember.id}"/>
		            			</c:when>
	            				<c:when test="${not empty byOperator}">
	    	        				<cyclos:profile elementId="${byOperator.id}"/>
		            			</c:when>
		            			<c:when test="${not empty byAdmin}">
		            				<cyclos:profile elementId="${byAdmin.id}"/>
		            			</c:when>
		            			<c:when test="${bySystem}">
		            				${localSettings.applicationUsername}
		            			</c:when>
		            		</c:choose>
	            		</td>
	            	</tr>
	            </c:if>
            	<tr>
            		<td class="label tdHeaderContents"><bean:message key='transfer.type'/></td>
            		<td>${transfer.type.name}</td>
            	</tr>
           		<c:if test="${isAdmin && transfer.type.conciliable}">
	            	<tr>
	            		<td class="label tdHeaderContents"><bean:message key='transfer.Conciliated'/></td>
	            		<td>
	    	        		<bean:message key="global.${empty transfer.externalTransfer ? 'no' : 'yes'}" />
    	        		</td>
	            	</tr>
           		</c:if>
            	<tr>
					<td class="label tdHeaderContents"><bean:message key="transfer.amount"/></td>
            		<td><cyclos:format number="${transfer.actualAmount}" unitsPattern="${unitsPattern}"/></td>
            	</tr>
				<%-- Rates are not shown in transaction details. But as there is some chance they might be in future, I left the code inside this comment for the time being.
				When removing this block, also remove corresponding code in action. Note that i-rate is not yet added to this
            	<c:if test="${not empty aRate}">
	            	<tr>
						<td class="label tdHeaderContents"><bean:message key="transfer.aRate"/></td>
        	    		<td>
							<cyclos:format number="${aRate}" precision="3" />&nbsp;
							<bean:message key="global.timePeriod.DAYS"/>
        	    		</td>
            		</tr>
				</c:if>
            	<c:set var="dRate" value="${transfer.dRate}" />
            	<c:if test="${not empty dRate}">
	            	<tr>
						<td class="label tdHeaderContents"><bean:message key="transfer.dRate"/></td>
        	    		<td>
							<cyclos:format number="${transfer.dRate}" precision="3" />&nbsp;
							<bean:message key="global.timePeriod.DAYS"/>
        	    		</td>
            		</tr>
				</c:if>
				--%>
            	<c:if test="${isScheduledPayment}">
					<td class="label tdHeaderContents"><bean:message key="transfer.scheduling"/></td>
            		<td>
            			<c:set var="formattedScheduledPaymentAmount"><cyclos:format number="${scheduledPayment.amount}" unitsPattern="${unitsPattern}" /></c:set>
            			<bean:message key="transfer.schedulingDetails" arg0="${scheduledPaymentNumber}" arg1="${scheduledPaymentCount}" arg2="${formattedScheduledPaymentAmount}" />
            		</td>
            	</c:if>
				<c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
			        <c:if test="${not empty value.value}">
			            <tr>
			                <td class="label tdHeaderContents">${field.name}</td>
			   				<td colspan="2">
			   					<cyclos:customField field="${field}" value="${value}" textOnly="true"/>
			   				</td>
						</tr>
					</c:if>
			    </c:forEach>
            	<c:set var="chargedBackBy" value="${transfer.chargedBackBy}" />
            	<c:if test="${not empty chargedBackBy}">
	            	<tr>
						<td class="label tdHeaderContents"><bean:message key="transfer.chargeback"/></td>
	            		<td>
	            			<cyclos:format dateTime="${chargedBackBy.date}"/>
	            			<a class="default showTransferDetails" transferId="${chargedBackBy.id}">
	            				<bean:message key="transfer.chargeback.details" />
	            			</a>
	            		</td>
	            	</tr>
            	</c:if>
            	<c:set var="chargebackOf" value="${transfer.chargebackOf}" />
            	<c:if test="${not empty chargebackOf}">
	            	<tr>
						<td class="label tdHeaderContents"><bean:message key="transfer.chargebackOf"/></td>
	            		<td>
	            			<cyclos:format dateTime="${chargebackOf.date}"/>
	            			<a class="default showTransferDetails" transferId="${chargebackOf.id}">
	            				<bean:message key="transfer.chargeback.details" />
	            			</a>
	            		</td>
	            	</tr>
            	</c:if>
            	<c:if test="${isScheduledPayment or transfer.type.requiresAuthorization}">
	            	<tr>
						<td class="label tdHeaderContents"><bean:message key="payment.status"/></td>
	            		<td><bean:message key="payment.status.${transfer.status}"/></td>
	            	</tr>
	            	<c:if test="${not empty comments}">
	            		<tr>
							<td class="label tdHeaderContents"><bean:message key="transferAuthorization.comments"/></td>
		            		<td><cyclos:escapeHTML>${comments}</cyclos:escapeHTML></td>
		            	</tr>
	            	</c:if> 
	            </c:if>
	            <c:if test="${not empty transfer.description}">
	            	<tr>
						<td class="label tdHeaderContents" valign="top"><bean:message key='transfer.description'/></td>
	            		<td><cyclos:escapeHTML>${transfer.description}</cyclos:escapeHTML></td>
	            	</tr>
            	</c:if>
	            <c:if test="${not empty guarantee}">
	            	<tr>
	            		<td class="label tdHeaderContents" valign="top"><bean:message key='transfer.guarantee'/></td>
		                <td><a class="default showGuaranteeDetails" guaranteeId="${guarantee.id}" transferId="${transfer.id}">
	            				<bean:message key="transfer.guarantee.details" /></a>
	            		</td>
	            	</tr>
            	</c:if>
            	<c:if test="${!showActions && (canAuthorize || canCancel || canPayNow || canChargeback)}">
	            	<tr id="trActions">
	            		<td colspan="2" align="right">
	            			<a class="default" id="toggleActionsLink">
	            				<bean:message key="payment.actions" />
	            			</a>
	            		</td>
	            	</tr>
            	</c:if>
            	<c:if test="${canAuthorize || canCancel}">
	            	<tr class="trAction" style="display:${showActions ? '' : 'none'}">
	            		<td colspan="2" class="label" style="text-align:center;padding-bottom:10px">
							<hr>
	            			<div style="padding-bottom: 10px"><bean:message key='transferAuthorization.comments'/></div>
	            			<textarea id="comments" rows="5" class="full" style="width:70%"></textarea>
			            	<c:if test="${showCommentsCheckBox}">
			            		<br>
			            		<label>
					            	<bean:message key='transferAuthorization.showToMember'/>
									<input type="checkbox" id="showToMember" class="checkbox" value="true"/>
								</label>
			                </c:if>
	            		</td>
	            	</tr>
	            </c:if>
            	<c:if test="${requestTransactionPassword}">
	            	<tr class="trAction" style="display:${showActions ? '' : 'none'}">
						<td colspan="2" class="label" style="text-align:center;padding-bottom:10px">
							<hr>
							<cyclos:escapeHTML><bean:message key="payment.${canChargeback ? 'chargeback' : canCancel ? 'cancelOrDeny' : suppressDeny ? 'authorizeOnly' : 'authorize'}.transactionPassword"/></cyclos:escapeHTML>
						</td>
					</tr>
	            	<tr class="trAction" style="display:${showActions ? '' : 'none'}">
						<td colspan="2" align="center">
							<c:set var="hideSubmit" value="${true}" scope="request" />
							<c:set var="transactionPasswordField" value="_transactionPassword" scope="request" />
							<jsp:include page="/do/transactionPassword?noCheck=true" />
						</td>
					</tr>
				</c:if>
            	<tr class="trAction" style="display:${showActions ? '' : 'none'}">
					<td colspan="2" align="right">
						<c:if test="${alreadyAuthorized}">
							<div class="label" style="text-align:center"><bean:message key="transferAuthorization.error.alreadyAuthorized"/></div>
						</c:if>								
						<table cellpadding="0" cellspacing="0">
							<tr>
								<c:if test="${canAuthorize}">
									<td><ssl:form method="post" styleId="authorizeForm" action="${actionPrefix}/authorizePayment">
										<input type="hidden" name="transferId" value="${transfer.id}"/>
										<input type="hidden" name="memberId" value="${memberId}"/>
										<input type="hidden" name="typeId" value="${typeId}"/>
										<input type="hidden" name="comments"/>
										<input type="hidden" name="showToMember"/>
										<html:hidden property="transactionPassword"/>
										&nbsp;&nbsp;<input type="submit" class="button" value="<bean:message key='payment.action.authorize'/>">
									</ssl:form></td>
									<c:if test="${!suppressDeny}">
										<td><ssl:form method="post" styleId="denyForm" action="${actionPrefix}/denyPayment">
											<input type="hidden" name="transferId" value="${transfer.id}"/>
											<input type="hidden" name="memberId" value="${memberId}"/>
											<input type="hidden" name="typeId" value="${typeId}"/>
											<input type="hidden" name="comments"/>
											<input type="hidden" name="showToMember"/>
											<html:hidden property="transactionPassword"/>
											&nbsp;&nbsp;<input type="submit" class="button" value="<bean:message key='payment.action.deny'/>">
										</ssl:form></td>
									</c:if>
								</c:if>
								<c:if test="${canCancel}">
									<td><ssl:form method="post" styleId="cancelForm" action="${actionPrefix}/cancelPayment">
										<input type="hidden" name="transferId" value="${transfer.id}"/>
										<input type="hidden" name="memberId" value="${memberId}"/>
										<input type="hidden" name="typeId" value="${typeId}"/>
										<input type="hidden" name="comments"/>
										<input type="hidden" name="showToMember"/>
										<html:hidden property="transactionPassword"/>
										&nbsp;&nbsp;<input type="submit" class="button" value="<bean:message key='payment.action.cancel'/>">
									</ssl:form></td>
								</c:if>
								<c:if test="${canPayNow}">
									<td><input type="button" class="button" id="payNowButton" transferId="${transfer.id}" value="<bean:message key='payment.action.payNow'/>"></td>
								</c:if>
								<c:if test="${canChargeback}">
									<td><ssl:form method="post" styleId="chargebackForm" action="${actionPrefix}/chargebackPayment">
										<input type="hidden" name="transferId" value="${transfer.id}"/>
										<html:hidden property="transactionPassword"/>
										&nbsp;&nbsp;<input type="submit" class="button" value="<bean:message key='payment.action.chargeback'/>">
									</ssl:form></td>									
								</c:if>
							</tr>
						</table>
					</td>
				</tr>
            </table>
        </td>
    </tr>
</table>


<c:if test="${not empty authorizations}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="transfer.title.authorizations"/></td>
	        <cyclos:help page="payments#transaction_authorizations_detail"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents" width="15%"><bean:message key='transferAuthorization.date'/></th>
	                	<th class="tdHeaderContents" width="20%"><bean:message key='transferAuthorization.action'/></th>
	                	<th class="tdHeaderContents" width="20%"><bean:message key='transferAuthorization.by'/></th>
	                	<th class="tdHeaderContents" width="45%"><bean:message key='transferAuthorization.comments'/></th>
	                </tr>
					<c:forEach var="auth" items="${authorizations}">												
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="center"><cyclos:format date="${auth.date}"/></td>
							<td align="center"><bean:message key="transferAuthorization.action.${auth.action}"/></td>
							<td align="center"><cyclos:profile elementId="${auth.by.id}"/></td>
							<td><cyclos:escapeHTML>${auth.comments}</cyclos:escapeHTML></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
	
</c:if>

<c:if test="${not empty parent}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable">
	       		<bean:message key="transfer.title.parent"/>
	        </td>
	        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
	        	<img class="view showTransferDetails" transferId="${parent.id}" src="<c:url value="/pages/images/view.gif"/>"/>
	        </td>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableForms">
	            <table class="defaultTable">
	            	<c:choose><c:when test="${not empty transfer.processDate and transfer.processedAtDifferentDate}">
		            	<tr>
							<%--if the transfer is a scheduled one then the date is stored without the hours/mins that is why we must use the raw date --%>
							<td class="label tdHeaderContents" width="30%"><bean:message key="${isScheduledPayment ? 'transfer.scheduledFor' : 'transfer.submitDate'}"/></td>							            			
            				<td><cyclos:format dateTime="${isScheduledPayment ? null : parent.date}" rawDate="${isScheduledPayment ? parent.date : null}"/></td>            					            	
		            	</tr>
		            	<tr>
							<td class="label tdHeaderContents"><bean:message key="transfer.processDate"/></td>
		            		<td><cyclos:format dateTime="${parent.processDate}"/></td>
		            	</tr>
	            	</c:when><c:otherwise>
		            	<tr>
							<%--if the transfer is a scheduled one then the date is stored without the hours/mins that is why we must use the raw date --%>
							<td class="label tdHeaderContents" width="30%"><bean:message key="${isScheduledPayment ? 'transfer.scheduledFor' : 'transfer.submitDate'}"/></td>							            			
            				<td><cyclos:format dateTime="${isScheduledPayment ? null : parent.date}" rawDate="${isScheduledPayment ? parent.date : null}"/></td>            					            	
		            	</tr>
	            	</c:otherwise></c:choose>
					<c:if test="${not empty parent.transactionNumber}">
						<tr>
							<td class="label tdHeaderContents"><bean:message key="transfer.transactionNumber"/></td>
							<td>${parent.transactionNumber}</td>
						</tr>
					</c:if>
	            	<tr>
						<td class="label tdHeaderContents"><bean:message key="transfer.from"/></td>
	            		<td>
	            			<c:set var="from" value="${parent.actualFrom}" />
	            			<c:choose><c:when test="${parent.actuallyFromSystem}">
		            			${from.ownerName}
	            			</c:when><c:otherwise>
	            				<cyclos:profile elementId="${from.owner.id}"/>
	            			</c:otherwise></c:choose>
	            		</td>
	            	</tr>
	            	<tr>
						<td class="label tdHeaderContents"><bean:message key="transfer.to"/></td>
	            		<td>
	            			<c:set var="to" value="${parent.actualTo}" />
	            			<c:choose><c:when test="${parent.actuallyToSystem}">
		            			${to.ownerName}
	            			</c:when><c:otherwise>
	            				<cyclos:profile elementId="${to.owner.id}"/>
	            			</c:otherwise></c:choose>
	            		</td>
	            	</tr>
	            	<tr>
	            		<td class="label tdHeaderContents"><bean:message key='transfer.type'/></td>
	            		<td>${parent.type.name}</td>
	            	</tr>
	            	<tr>
						<td class="label tdHeaderContents"><bean:message key="transfer.amount"/></td>
	            		<td><cyclos:format number="${parent.actualAmount}" unitsPattern="${parent.type.from.currency.pattern}"/></td>
	              	</tr>
					<c:forEach var="entry" items="${parentCustomFields}">
				        <c:set var="field" value="${entry.field}"/>
				        <c:set var="value" value="${entry.value}"/>
				        <c:if test="${not empty value.value}">
				            <tr>
				                <td class="label tdHeaderContents">${field.name}</td>
				   				<td colspan="2">
				   					<cyclos:customField field="${field}" value="${value}" textOnly="true"/>
				   				</td>
							</tr>
						</c:if>
				    </c:forEach>
	            	<tr>
						<td class="label tdHeaderContents" valign="top"><bean:message key='transfer.description'/></td>
	            		<td><cyclos:escapeHTML>${parent.description}</cyclos:escapeHTML></td>
	            	</tr>
	            </table>
	        </td>
	    </tr>
	</table>
	
</c:if>

<c:if test="${not empty children}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="transfer.title.children"/></td>
	        <td class="tdHelpIcon" height="16">&nbsp;</td>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents" width="15%"><bean:message key="${isScheduledPayment ? 'transfer.scheduledFor' : 'transfer.date'}"/></th>
	                	<th class="tdHeaderContents" width="15%"><bean:message key='transfer.from'/></th>
	                	<th class="tdHeaderContents" width="15%"><bean:message key='transfer.to'/></th>
	                    <th class="tdHeaderContents" width="15%"><bean:message key='transfer.amount'/></th>
	                    <th class="tdHeaderContents" width="35%"><bean:message key='transfer.description'/></th>
	                    <th class="tdHeaderContents" width="5%"></th>
	                </tr>
					<c:forEach var="child" items="${children}">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="center" class="${className}"><cyclos:format date="${isScheduledPayment ? null : child.date}" rawDate="${isScheduledPayment ? child.date : null}"/></td>		            			
							<td align="left"   class="${className}">${child.actualFrom.ownerName}</td>
							<td align="left"   class="${className}">${child.actualTo.ownerName}</td>
							<td align="center" class="${className}"><cyclos:format number="${child.actualAmount}" unitsPattern="${child.type.from.currency.pattern}"/></td>
							<td align="left"   class="${className}"><cyclos:truncate value="${child.description}"/></td>
							<td align="center" class="${className}"><img class="view showChild" childId="${child.id}" src="<c:url value="/pages/images/view.gif"/>"/></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
	
</c:if>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left">
			<input class="button" type="button" id="backButton" value="<bean:message key='global.back'/>">
		</td>
	</tr>
</table>