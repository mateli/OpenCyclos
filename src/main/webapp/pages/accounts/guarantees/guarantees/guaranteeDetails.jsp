<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<cyclos:script src="/pages/accounts/guarantees/guarantees/guaranteeDetails.js" />

<c:set var="titleKey" value="guarantee.title.guaranteeDetails"/>
<c:set var="titleKey2" value="guarantee.paymentObligations"/>
<c:set var="titleKey3" value="guarantee.logs"/>
<c:set var="helpPage" value="guarantees#guarantee_details"/>

<script>
	var certificationId = ${certificationId};
	var transferId = ${empty param.transferId ? -1 : param.transferId};
	var removeConfirmation = "<cyclos:escapeJS><bean:message key='guarantee.removeConfirmation'/></cyclos:escapeJS>";	
</script>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
	<cyclos:help page="${helpPage}" />
	<tr>
        <td colspan="4" align="left" class="tdContentTableForms">
        	<table class="defaultTable">
	            <tr>
        			<td class="headerLabel" width="25%"><bean:message key="guarantee.status" /></td>
	            	<td class="headerField" nowrap="nowrap" colspan="3"><bean:message key="guarantee.status.${guarantee.status}"/></td>
	            </tr>
	            <tr>
	            	<td class="headerLabel"><bean:message key="guarantee.guaranteeType" /></td>
	            	<td class="headerField" colspan="3">${guarantee.guaranteeType.name}</td>	            	
	            </tr>
				<c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
			        <c:if test="${not empty value.value}">
			            <tr>
			                <td class="headerLabel">${field.name}</td>
			   				<td class="headerField" colspan="3"><cyclos:customField field="${field}" value="${value}" textOnly="true"/></td>
						</tr>
					</c:if>
			    </c:forEach>
				<tr>
	            	<td class="headerLabel"><bean:message key="guarantee.amount" /></td>
	            	<td class="headerField" colspan="3"><cyclos:format number="${guarantee.amount}" unitsPattern="${guarantee.guaranteeType.currency.pattern}" /></td>
	            </tr>
        		<tr>
        			<td class="headerLabel"><bean:message key="guarantee.issuer" /></td>
	            	<td class="headerField" colspan="3"><cyclos:profile elementId="${guarantee.issuer.id}"/></td>
				</tr>
				<tr>
        			<td class="headerLabel"><bean:message key="guarantee.buyer" /></td>
	            	<td class="headerField" colspan="3"><cyclos:profile elementId="${guarantee.buyer.id}"/></td>
	            </tr>
				<c:if test="${not isWithBuyerOnly}">
	        		<tr>
						<td class="headerLabel"><bean:message key="guarantee.seller" /></td>
		            	<td class="headerField" colspan="3"><cyclos:profile elementId="${guarantee.seller.id}"/></td>
		            </tr>
				</c:if>
	            <tr>
	            	<td class="headerLabel"><bean:message key="guarantee.registrationDate" /></td>
	            	<td class="headerField" colspan="3"><cyclos:format dateTime='${guarantee.registrationDate}'/></td>
	            </tr>
	            <tr>
	            	<c:choose><c:when test="${not empty guarantee.validity.begin}">
	            		<td class="headerLabel"> 
	            			<span class="label"><bean:message key='guarantee.validity'/></span>
	            			<span class="lastLabel"><bean:message key='global.range.from'/></span>
	            		</td>
	            		<td class="headerField"><cyclos:format rawDate='${guarantee.validity.begin}'/></td>
		            	<td class="headerLabel"><bean:message key='global.range.to'/></td>
		            	<td class="headerField"><cyclos:format rawDate='${guarantee.validity.end}'/></td>
	            	</c:when><c:otherwise>
	            		<td class="headerLabel"> 
	            			<span class="label"><bean:message key='guarantee.validity'/></span>
	            			<span class="lastLabel"><bean:message key='global.range.to'/></span>
	            		</td>
	            		<td class="headerField" colspan="3"><cyclos:format rawDate='${guarantee.validity.end}'/></td>
	            	</c:otherwise></c:choose>
	            </tr>
				<c:set var="colSpan" value="${showCurrentFeeValues ? 1 : 3}"/>
	            <tr>
					<td class="headerLabel"><bean:message key="guarantee.creditFee" /></td>
					<td class="headerField float" colSpan="${colSpan}">
		            	<bean:define id="creditFeePattern" value="${guarantee.creditFeeSpec.type == fixedFeeType ? guarantee.guaranteeType.currency.pattern : null}"/>
						<cyclos:format number="${guarantee.creditFeeSpec.fee}" unitsPattern="${creditFeePattern}" />
						<c:if test="${guarantee.creditFeeSpec.type != fixedFeeType}">
							<bean:message key="guaranteeType.feeType.${guarantee.creditFeeSpec.type}"/>
						</c:if>					
					</td>
					<c:if test="${showCurrentFeeValues}">
						<td class="headerLabel"><bean:message key="guarantee.currentCreditFeeValue"/></td>
						<td class="headerField" align="left"><cyclos:format number="${guarantee.creditFee}" unitsPattern="${guarantee.guaranteeType.currency.pattern}"/></td>
					</c:if>
       			</tr>
				<tr>
					<td class="headerLabel"><bean:message key="guarantee.issueFee" /></td>
					<td class="headerField float" colspan="${colSpan}">
		            	<bean:define id="issueFeePattern" value="${guarantee.issueFeeSpec.type == fixedFeeType ? guarantee.guaranteeType.currency.pattern : null}"/>
						<cyclos:format number="${guarantee.issueFeeSpec.fee}" unitsPattern="${issueFeePattern}" />
						<c:if test="${guarantee.issueFeeSpec.type != fixedFeeType}">
							<bean:message key="guaranteeType.feeType.${guarantee.issueFeeSpec.type}"/>
						</c:if>
					</td>	            
					<c:if test="${showCurrentFeeValues}">
						<td class="headerLabel"><bean:message key="guarantee.currentIssueFeeValue"/></td>
						<td class="headerField" align="left"><cyclos:format number="${guarantee.issueFee}" unitsPattern="${guarantee.guaranteeType.currency.pattern}"/></td>
					</c:if>
				</tr>
				<c:if test="${showLoan}">
					<tr>
						<td class="headerLabel"><bean:message key="guarantee.generatedLoan"/></td>
					    <td class="headerField"><a id="loanLink" class="default" guaranteeId="${guarantee.id}" loanId="${guarantee.loan.id}"><bean:message key='guarantee.loan'/></a></td>				
					</tr>
				</c:if>
				<tr>
					<td colspan="4" align="right">
						<c:if test="${canAccept}">
							<input type="button" id="acceptButton" class="button" guaranteeId="${guarantee.id}" value="<bean:message key="guarantee.action.accept"/>">
						</c:if>
						<c:if test="${canDeny}">
							<input type="button" id="denyButton" class="button" guaranteeId="${guarantee.id}" value="<bean:message key="guarantee.action.deny"/>">
						</c:if>
						<c:if test="${canCancel}">
							<input type="button" id="cancelButton" class="button" guaranteeId="${guarantee.id}" value="<bean:message key="guarantee.action.cancel"/>">
						</c:if>
						<c:if test="${canDelete}">
							<input type="button" id="deleteButton" class="button" guaranteeId="${guarantee.id}" value="<bean:message key="guarantee.action.delete"/>">
						</c:if>														
					</td>
				</tr>
        	</table>
        </td>
	</tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left"><input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>

<c:if test="${showPaymentObligations and not empty guarantee.paymentObligations}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<td class="tdHeaderTable"><bean:message key="${titleKey2}"/></td>
		<cyclos:help page="guarantees#guarantee_payment_obligations" />
		<tr>
		    <td colspan="6" align="left" class="tdContentTableForms">
	    	  	<table class="defaultTable">
					<tr>
						<td class="tdHeaderContents"><bean:message key="paymentObligation.status"/></td>
						<td class="tdHeaderContents"><bean:message key="paymentObligation.amount"/></td>
						<td class="tdHeaderContents"><bean:message key="paymentObligation.expirationDate"/></td>
						<td class="tdHeaderContents"><bean:message key="paymentObligation.maxPublishDate"/></td>
						<td class="tdHeaderContents"><bean:message key="paymentObligation.registrationDate"/></td>
						<td class="tdHeaderContents">&nbsp;</td>
					</tr>
					<c:forEach var="paymentObligation" items="${guarantee.paymentObligations}">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td><bean:message key="paymentObligation.status.${paymentObligation.status}"/></td>
							<td><cyclos:format number="${paymentObligation.amount}" unitsPattern="${paymentObligation.currency.pattern}" /></td>
							<td align="center"><cyclos:format rawDate="${paymentObligation.expirationDate}"/></td>
							<td align="center"><cyclos:format rawDate="${paymentObligation.maxPublishDate}"/></td>
							<td align="center"><cyclos:format date="${paymentObligation.registrationDate}"/></td>	
		                    <td align="center"><img class="paymentObligationDetails" src="<c:url value="/pages/images/view.gif" />" paymentObligationId="${paymentObligation.id}" />	</td>
						</tr>						
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty guarantee.logs}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<td class="tdHeaderTable"><bean:message key="${titleKey3}"/></td>
		<cyclos:help page="guarantees#guarantee_logs" />
		<tr>
		    <td colspan="6" align="left" class="tdContentTableForms">
	    	  	<table class="defaultTable">
					<tr>
						<td class="tdHeaderContents"><bean:message key="guaranteeLog.status"/></td>
						<th class="tdHeaderContents"><bean:message key="guaranteeLog.by"/></th>
						<td class="tdHeaderContents"><bean:message key="guaranteeLog.date"/></td>
					</tr>
					<c:forEach var="log" items="${guarantee.logs}" varStatus="idx">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="left"><bean:message key="guarantee.status.${log.status}"/></td>
							<c:choose>								
								<c:when test="${logsBy[idx.index].byType == 'System'}">
									<td align="left" ><cyclos:escapeHTML value="${localSettings.applicationUsername}"/></td>
								</c:when>
								<c:when test="${logsBy[idx.index].byType == 'SystemTask'}">
								    <td align="left" ><bean:message key="global.system"/></td>
								</c:when>
								<c:otherwise>
									<td align="left" title="<cyclos:escapeHTML value="${logsBy[idx.index].by.name}"/>"><cyclos:profile elementId="${logsBy[idx.index].by.id}"/></td>
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