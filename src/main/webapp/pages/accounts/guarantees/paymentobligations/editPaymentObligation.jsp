<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<cyclos:script src="/pages/accounts/guarantees/paymentobligations/editPaymentObligation.js" />

<c:set var="paymentObligationsHistory" value="paymentObligation.history"/>
<c:set var="helpPage" value="guarantees#edit_payment_obligation"/>

<script>
        var sellerGroupIds = ${sellerGroupsId};
		var removeConfirmation = "<cyclos:escapeJS><bean:message key='paymentObligation.removeConfirmation'/></cyclos:escapeJS>";
		var guaranteeId = ${guaranteeId};
		var isEditable = ${isEditable};
</script>

<ssl:form method="post" action="${formAction}">

<html:hidden property="paymentObligationId"/>
<html:hidden property="paymentObligation(id)"/>
<html:hidden property="paymentObligation(status)"/>
<html:hidden property="paymentObligation(registrationDate)"/>
<html:hidden property="issuerId" value="${issuerId}"/>

<c:choose>
	<c:when test="${isInsert}">
		<c:set var="titleKey" value="paymentObligation.title.new"/>
	</c:when>
	<c:when test="${isEditable}">
		<c:set var="titleKey" value="paymentObligation.title.modify"/>
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="paymentObligation.title.view"/>	
	</c:otherwise>
</c:choose>

<c:set var="requiredClassName" value="${isEditable ? 'required' : ''}" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
		<cyclos:help page="${helpPage}" />
	</tr>
	<tr>
        <td colspan="2" align="left" class="tdContentTableForms">
        	<table class="defaultTable">
        		
        		<c:if test="${not isEditable}"> 
	        		<tr>
	        			<td class="label"><bean:message key="paymentObligation.status"/></td>
	        			<td>
	        				<bean:define id="poStatus"><bean:message key="paymentObligation.status.${paymentObligation.status}"/></bean:define>
			           		<input value="${poStatus}" class="full InputBoxDisabled"/>
	        			</td>
	        			<td></td>
	        			<td></td>
	        		</tr>
				</c:if>        		

            	<c:if test="${not isSeller || isSeller && isBuyer}">
	            	<tr>
		            	<td width="25%" class="label"><bean:message key="paymentObligation.sellerUsername" /></td>
		            	<td>
							<html:hidden styleId="sellerId" property="paymentObligation(seller)"/>
							<div style="white-space: nowrap;"><input id="sellerUsername" class="full InputBoxDisabled ${requiredClassName}" value="${paymentObligation.seller.username}" readonly></div>
							<div id="sellersByUsername" class="autoComplete"></div>
		            	</td>
		            	<td width="25%" class="label"><bean:message key="paymentObligation.sellerName" /></td>
		            	<td>
							<div style="white-space: nowrap;"><input id="sellerName" class="full InputBoxDisabled ${requiredClassName}" value="<cyclos:escapeHTML value="${paymentObligation.seller.name}"/>" readonly></div>
							<div id="sellersByName" class="autoComplete"></div>
		            	</td>
		            </tr>
            	</c:if>
            	<c:if test="${not isBuyer || isSeller && isBuyer && not isInsert}">
	           		<tr>
		            	<td width="25%" class="label"><bean:message key="paymentObligation.buyerUsername" /></td>
		            	<td>
							<input id="buyerUsername" class="full InputBoxDisabled" value="${paymentObligation.buyer.username}" readonly>
		            	</td>
		            	<td width="25%" class="label"><bean:message key="paymentObligation.buyerName" /></td>
		            	<td>
							<input id="buyerName" class="full InputBoxDisabled" value="<cyclos:escapeHTML value="${paymentObligation.buyer.name}"/>" readonly>
		            	</td>
	            	</tr>
            	</c:if>

   	            <tr>
					<td width="25%" class="label"><bean:message key="paymentObligation.maxPublishDate" /></td>
	            	<td nowrap="nowrap">
	            		<div style="white-space: nowrap;"><html:text property="paymentObligation(maxPublishDate)" styleId="maxPublishDate" styleClass="date InputBoxDisabled ${requiredClassName}" readonly="true"/></div>
	            	</td>
	            	<td width="25%" class="label"><bean:message key="paymentObligation.expirationDate" /></td>
	            	<td nowrap="nowrap">
	            		<div style="white-space: nowrap;"><html:text property="paymentObligation(expirationDate)" styleId="expirationDate" styleClass="date InputBoxDisabled ${requiredClassName}" readonly="true"/></div>
	            	</td>	            	
	            </tr>
				<tr>
		        	<td width="25%" class="label"><bean:message key="paymentObligation.amount" /></td>
	            	<td colspan="3">
	            		<div style="white-space: nowrap;">
	            			<html:text property="paymentObligation(amount)" styleId="amount" readonly="true" styleClass="float InputBoxDisabled"/>
		            		<c:choose>
		            			<c:when test="${not empty singleCurrency}">
									<html:hidden property="paymentObligation(currency)" value="${singleCurrency.id}" />
									${singleCurrency.symbol}<c:if test="${isEditable}">&nbsp;<span class='fieldDecoration' style='vertical-align:top'>*</span></c:if>
								</c:when>
								<c:when test="${not isEditable}">
									<html:hidden property="paymentObligation(currency)" value="${paymentObligation.currency.id}" />
									${paymentObligation.currency.symbol}							
								</c:when>
								<c:otherwise>
									<html:select property="paymentObligation(currency)" disabled="true" styleClass="InputBoxDisabled ${requiredClassName}" styleId="currencySelect">
			   	        				<c:forEach var="currency" items="${currencies}">
			       	    					<html:option value="${currency.id}">${currency.symbol}</html:option>
			           					</c:forEach>
			           				</html:select>
								</c:otherwise>
							</c:choose>
						</div>
	            	</td>
	            </tr>
   	            <tr>
	           		<td class="label"><bean:message key="paymentObligation.description" /></td>
	           		<td colspan="3"><div style="white-space: nowrap;"><html:textarea property="paymentObligation(description)" readonly="true" styleClass="full InputBoxDisabled ${requiredClassName}" disabled="true" rows="5"/></div></td>	            	
	            </tr>
				<tr>
					<c:if test="${canAccept}">
						<td align="right" class="label" nowrap="nowrap">
							<bean:message key="paymentObligation.selectIssuer"/>
						</td>
						<td>
							<html:select property="issuerId" styleId="issuers">
								<c:forEach var="issuer_item" items="${issuers}">
									<html:option value="${issuer_item.id}"><cyclos:escapeHTML value="${issuer_item.name}"/></html:option>
								</c:forEach>
							</html:select>
						</td>
						<td align="right" colspan="2">
							<input type="button" class="button" id="acceptPaymentObligation" paymentObligationId="${paymentObligation.id}" value="<bean:message key="paymentObligation.accept"/>"/>						
					</c:if>
					<c:if test="${not canAccept}">
						<td align="right" colspan="4}">
					</c:if>							
						<c:if test="${canPublish}">
							<input type="button" class="button" id="publishButton" paymentObligationId="${paymentObligation.id}" value="<bean:message key="paymentObligation.publish"/>"/>
						</c:if>
						<c:if test="${canConceal}">
							<input type="button" class="button" id="concealButton" paymentObligationId="${paymentObligation.id}" value="<bean:message key="paymentObligation.conceal"/>"/>
						</c:if>								
						<c:if test="${canReject}">
							<input type="button" class="button" id="rejectButton" paymentObligationId="${paymentObligation.id}" value="<bean:message key="paymentObligation.reject"/>"/>
						</c:if>
						<c:if test="${canDelete}">
							<input type="button" class="button" id="deleteButton" paymentObligationId="${paymentObligation.id}" value="<bean:message key="paymentObligation.delete"/>"/>
						</c:if>
			    		<c:if test="${isEditable}">
							<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button"/>
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled value="<bean:message key="global.submit"/>"/>
						</c:if>
					</td>
				</tr>	            
        	</table>
        </td>
	</tr>
</table>
</ssl:form>


<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left">
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>

<c:if test="${not empty paymentObligation.logs}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key='paymentObligation.logs'/></td>
	        <td class="tdHelpIcon" align="right" width="15%" valign="middle">
	       		<cyclos:help page="guarantees#payment_obligation_logs" td="false"/>
	        </td>
	    </tr>
		<tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	    	  	<table class="defaultTable">
					<tr>
						<th class="tdHeaderContents"><bean:message key="paymentObligationLog.status"/></th>
						<th class="tdHeaderContents"><bean:message key="paymentObligationLog.by"/></th>
						<th class="tdHeaderContents"><bean:message key="paymentObligationLog.date"/></th>
					</tr>
					<c:forEach var="log" items="${paymentObligation.logs}" varStatus="idx">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="left"><cyclos:escapeHTML><bean:message key="paymentObligation.status.${log.status}"/></cyclos:escapeHTML></td>
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
