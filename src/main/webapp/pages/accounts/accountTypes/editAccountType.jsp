<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<c:choose>
	<c:when test="${isInsert}">
		<c:set var="titleKey" value="accountType.title.new"/>
		</c:when>
	<c:when test="${cyclos:name(accountType.nature) == 'SYSTEM'}">
		<c:set var="titleKey" value="accountType.title.modify.system"/>
		</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="accountType.title.modify.member"/>
		</c:otherwise>
</c:choose>

<cyclos:script src="/pages/accounts/accountTypes/editAccountType.js" />
<script>
	var isSystem = ${isSystem};
</script>
<ssl:form method="post" action="${formAction}">
<html:hidden property="accountTypeId"/>
<html:hidden property="accountType(id)"/>
<c:if test="${!isInsert}">
	<html:hidden property="accountType(nature)" value="${accountType.nature}"/>
</c:if>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
        <cyclos:help page="account_management#account_details"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<c:if test="${isInsert}">
					<tr>
	            		<td class="label" width="25%"><bean:message key="accountType.nature"/></td>
	            		<td>
	            			<html:select styleId="natureSelect" property="accountType(nature)" disabled="true" styleClass="InputBoxDisabled">
	            				<c:forEach var="nature" items="${natures}">
	            					<html:option value="${nature}"><bean:message key="accountType.nature.${nature}"/></html:option>
	            				</c:forEach>
		            		</html:select>
		            	</td>
	            	</tr>
            	</c:if>
				<tr>
            		<td class="label" width="25%"><bean:message key="accountType.name"/></td>
            		<td><html:text property="accountType(name)" maxlength="50" readonly="true" styleClass="full InputBoxDisabled"/></td>
            	</tr>
            	<tr>
            		<td class="label" valign="top"><bean:message key="accountType.description"/></td>
            		<td><html:textarea styleId="descriptionText" property="accountType(description)" rows="6" readonly="true" styleClass="full InputBoxDisabled"/></td>
            	</tr>
				<tr>
            		<td class="label"><bean:message key="accountType.currency"/></td>
            		<td>
            			<c:choose><c:when test="${isInsert}">
	            			<html:select property="accountType(currency)" disabled="true" styleClass="InputBoxDisabled">
	            				<c:forEach var="currency" items="${currencies}">
	            					<html:option value="${currency.id}">${currency.name}</html:option>
	            				</c:forEach>
	            			</html:select>
	            		</c:when><c:otherwise>
		            		<input type="hidden" name="accountType(currency)" value="${accountType.currency.id}"/>
		            		<input id="currencyText" class="InputBoxDisabled medium" readonly value="${accountType.currency.name}"/>
			            </c:otherwise></c:choose>	
            		</td>
            	</tr>
            	<c:if test="${isInsert || isSystem}">
					<tr id="trLimitType" style="display:none">
	            		<td class="label"><bean:message key="accountType.limitType"/></td>
	            		<td>
	            			<c:choose><c:when test="${isInsert}">
		            			<html:select styleId="limitTypeSelect" property="accountType(limitType)" disabled="true" styleClass="InputBoxDisabled">
		            				<c:forEach var="limitType" items="${limitTypes}">
		            					<html:option value="${limitType}"><bean:message key="accountType.limitType.${limitType}"/></html:option>
		            				</c:forEach>
			            		</html:select>
			            	</c:when><c:otherwise>
			            		<input type="hidden" name="accountType(limitType)" value="${accountType.limitType}"/>
			            		<input id="limitTypeText" class="InputBoxDisabled medium" readonly value="<bean:message key="accountType.limitType.${accountType.limitType}"/>"/>
			            	</c:otherwise></c:choose>
		            	</td>
	            	</tr>
	            	<c:if test="${isInsert || cyclos:name(accountType.limitType) == 'LIMITED'}">
						<tr id="trCreditLimit" style="display:none">
		            		<td class="label"><bean:message key="account.creditLimit"/></td>
		            		<td><html:text property="accountType(creditLimit)" maxlength="20" readonly="true" styleClass="medium floatNegative InputBoxDisabled"/></td>
		            	</tr>
						<tr id="trUpperCreditLimit" style="display:none">
		            		<td class="label"><bean:message key="account.upperCreditLimit"/></td>
		            		<td><html:text property="accountType(upperCreditLimit)" maxlength="20" readonly="true" styleClass="medium float InputBoxDisabled"/></td>
		            	</tr>
	            	</c:if>
            	</c:if>
            	<c:if test="${editable}">
	            	<tr>
						<td align="right" colspan="2">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">
							&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
				</c:if>
            </table>
        </td>
    </tr>
</table>
</ssl:form>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>

<c:if test="${!isInsert}">
	<script>
		var removeTransferTypeConfirmation = "<cyclos:escapeJS><bean:message key="transferType.removeConfirmation"/></cyclos:escapeJS>";
		var removeAccountFeeConfirmation = "<cyclos:escapeJS><bean:message key="accountFee.removeConfirmation"/></cyclos:escapeJS>";
		var removePaymentFilterConfirmation = "<cyclos:escapeJS><bean:message key="paymentFilter.removeConfirmation"/></cyclos:escapeJS>";
	</script>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="transferType.title.list"/></td>
	        <cyclos:help page="account_management#transaction_type_search"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents"><bean:message key="transferType.name"/></th>
	                    <th class="tdHeaderContents"><bean:message key="transferType.from"/></th>
	                    <th class="tdHeaderContents"><bean:message key="transferType.to"/></th>
	                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
	                </tr>
	                <c:forEach var="transferType" items="${transferTypes}">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="left">${transferType.name}</td>
		                    <td align="left">${transferType.from.name}</td>
		                    <td align="left">${transferType.to.name}</td>
		                    <td align="center" nowrap="nowrap">
								<c:choose><c:when test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
				                    <img class="edit transferTypeDetails" transferTypeId="${transferType.id}" src="<c:url value="/pages/images/edit.gif" />" />
				                    <img class="remove removeTransferType" transferTypeId="${transferType.id}" src="<c:url value="/pages/images/delete.gif" />" />
				                </c:when><c:otherwise>
				                	<img class="view transferTypeDetails" transferTypeId="${transferType.id}" src="<c:url value="/pages/images/view.gif" />" />
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
				<td align="right">
					<span class="label"><bean:message key="transferType.action.new"/></span>
					<input class="button" id="newTransferTypeButton" type="button" value="<bean:message key="global.submit"/>">
				</td>
			</tr>
		</table>
	</c:if>
	
	<c:if test="${!isSystem}">
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="accountFee.title.list"/></td>
		        <cyclos:help page="account_management#account_fee_list"/>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable">
		                <tr>
		                    <th class="tdHeaderContents"><bean:message key="accountFee.name"/></th>
		                    <th class="tdHeaderContents" width="20%"><bean:message key="accountFee.amount"/></th>
		                    <th class="tdHeaderContents" width="10%"><bean:message key="accountFee.recurrence"/></th>
		                    <th class="tdHeaderContents" width="10%"><bean:message key="accountFee.day"/></th>
		                    <th class="tdHeaderContents" width="10%"><bean:message key="accountFee.hour"/></th>
		                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
		                </tr>
		                <c:forEach var="accountFee" items="${accountFees}">
			                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			                    <td align="left">${accountFee.name}</td>
			                    <td align="left"><cyclos:format amount="${accountFee.amountValue}" unitsPattern="${accountFee.accountType.currency.pattern}"/></td>
			                    <c:choose>
				                    <c:when test="${!accountFee.enabled}">
			                    		<td align="center"><bean:message key="accountFee.disabled"/></td>
			                    		<td align="center">-</td>
			                    		<td align="center">-</td>
				                    </c:when>
				                    <c:when test="${accountFee.manual}">
			                    		<td align="center"><bean:message key="accountFee.manual"/></td>
			                    		<td align="center">-</td>
			                    		<td align="center">-</td>
			                    	</c:when>
			                    	<c:otherwise>
			                    		<td align="center">${accountFee.recurrence.number} <bean:message key="global.timePeriod.${accountFee.recurrence.field}"/></td>
			                    		<td align="center">
			                    			<c:choose>
			                    				<c:when test="${cyclos:name(accountFee.recurrence.field) == 'WEEKS'}">
			                    					<bean:message key="global.weekDay.short.${accountFee.weekDay}"/>
			                    				</c:when>
			                    				<c:when test="${cyclos:name(accountFee.recurrence.field) == 'MONTHS'}">
			                    					${accountFee.day}
			                    				</c:when>
			                    				<c:otherwise>
			                    					-
			                    				</c:otherwise>
			                    			</c:choose>
			                    		</td>
			                    		<td align="center">${accountFee.hour}</td>
			                    	</c:otherwise>
		                    	</c:choose>
			                    <td align="center" nowrap="nowrap">
				                    <c:choose><c:when test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
				                    	<img class="edit accountFeeDetails" accountFeeId="${accountFee.id}" src="<c:url value="/pages/images/edit.gif" />" />
					                    <img class="remove removeAccountFee" accountFeeId="${accountFee.id}" src="<c:url value="/pages/images/delete.gif" />" />
					                </c:when><c:otherwise>
					                	<img class="view accountFeeDetails" accountFeeId="${accountFee.id}" src="<c:url value="/pages/images/view.gif" />" />
									</c:otherwise></c:choose>
			                    </td>
			                </tr>
						</c:forEach>
		            </table>
		        </td>
		    </tr>
		</table>
		
		<c:if test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
			<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
				<tr>
					<td align="right">
						<span class="label"><bean:message key="accountFee.action.new"/></span>
						<input class="button" id="newAccountFeeButton" type="button" value="<bean:message key="global.submit"/>">
					</td>
				</tr>
			</table>
		</c:if>
		
	</c:if>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="paymentFilter.title.list"/></td>
	        <cyclos:help page="account_management#payment_filter_search"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents"><bean:message key="paymentFilter.name"/></th>
	                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
	                </tr>
	                <c:forEach var="paymentFilter" items="${paymentFilters}">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="left">${paymentFilter.name}</td>
		                    <td align="center" nowrap="nowrap">
		                    	<c:choose><c:when test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
				                    <img class="edit paymentFilterDetails" paymentFilterId="${paymentFilter.id}" src="<c:url value="/pages/images/edit.gif" />" />
				                    <img class="remove removePaymentFilter" paymentFilterId="${paymentFilter.id}" src="<c:url value="/pages/images/delete.gif" />" />
				                </c:when><c:otherwise>
				                	<img class="view paymentFilterDetails" paymentFilterId="${paymentFilter.id}" src="<c:url value="/pages/images/view.gif" />" />
								</c:otherwise></c:choose>
		                    </td>
						</tr>
	                </c:forEach>
	            </table>
	        </td>
	    </tr>
	</table>
	
	<c:if test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
		<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
			<tr>
				<td align="right">
					<span class="label"><bean:message key="paymentFilter.action.new"/></span>
					<input class="button" id="newPaymentFilterButton" type="button" value="<bean:message key="global.submit"/>">
				</td>
			</tr>
		</table>
	</c:if>
</c:if>
