<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/accounts/transactionFees/editBrokerCommission.js" />

<script>
    var editableGeneratedTT = ${editableGeneratedTT};
	var selectedGeneratedType = "${transactionFee.generatedTransferType.id}";
	var isFromSystem = ${empty transactionFee ? false : transactionFee.fromSystem};
</script>

<ssl:form method="POST" action="${formAction}">
<html:hidden property="nature" value="BROKER"/>
<html:hidden property="accountTypeId"/>
<html:hidden property="transferTypeId"/>
<html:hidden property="transactionFeeId"/>
<html:hidden property="transactionFee(id)"/>
<c:if test="${not empty singleSubject}">
	<html:hidden property="transactionFee(payer)" value="${singleSubject}" />
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="transactionFee.title.broker.${isInsert ? 'insert' : 'modify'}"/></td>
        <cyclos:help page="brokering#broker_commission_details"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
            		<td class="label" width="35%"><bean:message key='transfer.type'/></td>
            		<td><input id="transferTypeName" readonly="true" class="full InputBoxDisabled" value="${transferType.name}"/></td>
            	</tr>
				<tr>
            		<td class="label"><bean:message key='transactionFee.name'/></td>
            		<td><html:text property="transactionFee(name)" maxlength="100" readonly="true" styleClass="full InputBoxDisabled"/></td>
            	</tr>
            	<tr>
					<td class="label" valign="top"><bean:message key='transactionFee.description'/></td>
					<td><html:textarea property="transactionFee(description)" rows="6" readonly="true" styleClass="full InputBoxDisabled"/></td>
            	</tr>
            	<tr>
					<td class="label"><bean:message key='transactionFee.enabled'/></td>
					<td><html:checkbox property="transactionFee(enabled)" value="true" disabled="true" styleClass="checkbox InputBoxDisabled"/></td>
            	</tr>
            	<c:if test="${empty singleSubject}">
	            	<tr>
						<td class="label"><bean:message key='transactionFee.payer'/></td>
						<td>
							<c:choose><c:when test="${isInsert}">
								<html:select styleId="payerSelect" property="transactionFee(payer)" disabled="true" styleClass="InputBoxDisabled">
									<c:forEach var="subject" items="${possibleSubjects}">
										<html:option value="${subject}"><bean:message key="transactionFee.subject.${subject}" /></html:option>
									</c:forEach>
								</html:select>
							</c:when><c:otherwise>
								<html:hidden property="transactionFee(payer)" />
								<input readonly="readonly" id="payerText" class="full InputBoxDisabled" value="<bean:message key="transactionFee.subject.${transactionFee.payer}" />" >
							</c:otherwise></c:choose>
						</td>
	            	</tr>
            	</c:if>
            	<tr id="whichBrokerTR" style="display:none">
					<td class="label"><bean:message key='transactionFee.whichBroker'/></td>
					<td>
						<c:choose><c:when test="${isInsert}">
							<html:select styleId="whichBrokerSelect" property="transactionFee(whichBroker)" disabled="true" styleClass="InputBoxDisabled">
								<c:forEach var="whichBroker" items="${whichBrokers}">
									<html:option value="${whichBroker}"><bean:message key="transactionFee.whichBroker.${whichBroker}" /></html:option>
								</c:forEach>
							</html:select>
						</c:when><c:otherwise>
							<html:hidden property="transactionFee(whichBroker)" />
							<input readonly="readonly" id="whichBrokerText" class="full InputBoxDisabled" value="<bean:message key="transactionFee.whichBroker.${transactionFee.whichBroker}" />" >
						</c:otherwise></c:choose>
					</td>
            	</tr>
            	<c:if test="${editableGeneratedTT && cyclos:name(transactionFee.payer) != 'SYSTEM'}">
	            	<tr>
						<td class="label"><bean:message key='transactionFee.allowAnyAccount'/></td>
						<td><html:checkbox styleId="allowAnyAccount" property="allowAnyAccount" disabled="true" value="true"  /></td>
	            	</tr>
	            </c:if>
            	<tr>
					<td class="label"><bean:message key='transactionFee.generatedTransferType'/></td>
					<td>
						<c:choose><c:when test="${editableGeneratedTT}">
							<html:select styleId="generatedSelect" property="transactionFee(generatedTransferType)" disabled="true" styleClass="InputBoxDisabled">
								<c:forEach var="tt" items="${generatedTransferTypes}">
									<html:option value="${tt.id}">${tt.name}</html:option>
								</c:forEach>
							</html:select>
						</c:when><c:otherwise>
							<html:hidden property="transactionFee(generatedTransferType)"/>
							<input id="generatedTransferTypeName" value="${transactionFee.generatedTransferType.name}" class="large InputBoxDisabled"/>
						</c:otherwise></c:choose>
					</td>
            	</tr>
            	<tr>
					<td class="label"><bean:message key='transactionFee.chargeType'/></td>
					<td>
						<html:select property="transactionFee(chargeType)" disabled="true" styleClass="InputBoxDisabled">
							<c:forEach var="type" items="${chargeTypes}">
								<html:option value="${type}"><bean:message key="transactionFee.chargeType.${type}"/></html:option>
							</c:forEach>
						</html:select>
					</td>
				</tr>
            	<tr>
					<td class="label"><bean:message key='transactionFee.amount'/></td>
					<td><html:text property="transactionFee(value)" readonly="true" styleClass="small floatHighPrecision InputBoxDisabled"/></td>
           		</tr>
           		<tr class="trMaxAmount" style="display:none">
					<td class="label"><bean:message key='transactionFee.maxAmount'/></td>
					<td>
						<html:text property="transactionFee(maxFixedValue)" readonly="true" styleClass="small floatHighPrecision InputBoxDisabled"/>
						<bean:message key="global.amount.type.FIXED"/>
					</td>
           		</tr>
           		<tr class="trMaxAmount" style="display:none">
					<td class="label"><bean:message key='transactionFee.maxAmount'/></td>
					<td>
						<html:text property="transactionFee(maxPercentageValue)" readonly="true" styleClass="small floatHighPrecision InputBoxDisabled"/>
						<bean:message key="global.amount.type.PERCENTAGE"/>
					</td>
           		</tr>
            </table>

            <fieldset><legend><bean:message key="transactionFee.conditions"/></legend>
            <table class="defaultTable">
            	<tr>
					<td class="label" width="35%"><bean:message key='transactionFee.initialAmount'/></td>
            		<td><html:text property="transactionFee(initialAmount)" readonly="true" styleClass="small float InputBoxDisabled"/></td>
            	</tr>
            	<tr>
					<td class="label"><bean:message key='transactionFee.finalAmount'/></td>
            		<td><html:text property="transactionFee(finalAmount)" readonly="true" styleClass="small float InputBoxDisabled"/></td>
            	</tr>
            	<c:if test="${not transferType.fromSystem}">
	            	<tr>
						<td class="label"><bean:message key='transactionFee.fromAllGroups'/></td>
						<td><html:checkbox styleId="fromAllCheck" disabled="true" property="transactionFee(fromAllGroups)" value="true" /></td>
	            	</tr>
	            	<tr id="trFromGroups" style="display:none">
						<td class="label" valign="top"><bean:message key='transactionFee.fromGroups'/></td>
						<td>
							<cyclos:multiDropDown varName="fromGroupsSelect" name="transactionFee(fromGroups)" disabled="true" size="5">
								<c:forEach var="group" items="${possibleFromGroups}">
									<cyclos:option value="${group.id}" text="${group.name}" selected="${cyclos:contains(transactionFee.fromGroups, group)}"/>
								</c:forEach>
							</cyclos:multiDropDown>
						</td>
					</tr>
				</c:if>
				<c:if test="${not transferType.toSystem}">
	            	<tr>
						<td class="label"><bean:message key='transactionFee.toAllGroups'/></td>
						<td><html:checkbox styleId="toAllCheck" disabled="true" property="transactionFee(toAllGroups)" value="true" /></td>
	            	</tr>
	            	<tr id="trToGroups" style="display:none">
						<td class="label" valign="top"><bean:message key='transactionFee.toGroups'/></td>
						<td>
							<cyclos:multiDropDown varName="toGroupsSelect" name="transactionFee(toGroups)" disabled="true" size="5">
								<c:forEach var="group" items="${possibleToGroups}">
									<cyclos:option value="${group.id}" text="${group.name}" selected="${cyclos:contains(transactionFee.toGroups, group)}"/>
								</c:forEach>
							</cyclos:multiDropDown>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="label"><bean:message key='transactionFee.allBrokerGroups'/></td>
					<td><html:checkbox styleId="brokerCheck" disabled="true" property="transactionFee(allBrokerGroups)" value="true" /></td>
            	</tr>
            	<tr id="trBrokerGroups" style="display:none">
					<td class="label" valign="top"><bean:message key='transactionFee.brokerGroups'/></td>
					<td>
						<cyclos:multiDropDown varName="brokerGroupsSelect" name="transactionFee(brokerGroups)" disabled="true" size="5">
							<c:forEach var="brokerGroup" items="${brokerGroups}">
								<c:set var="selected" value="${isBrokerCommission and cyclos:contains(transactionFee.brokerGroups, brokerGroup)}"/>
								<cyclos:option value="${brokerGroup.id}" text="${brokerGroup.name}" selected="${selected}"/>
							</c:forEach>
						</cyclos:multiDropDown>
					</td>
				</tr>
            	<tr>
					<td class="label"><bean:message key='transactionFee.when'/></td>
					<td>
						<html:text styleId="countText" property="transactionFee(count)" readonly="true" styleClass="tiny number InputBoxDisabled" style="display:none"/>
						<html:select styleId="whenSelect" property="transactionFee(when)" disabled="true" styleClass="InputBoxDisabled">
							<c:forEach var="when" items="${whens}">
								<html:option value="${when}"><bean:message key='transactionFee.when.${when}'/></html:option>
							</c:forEach>
						</html:select>
					</td>
            	</tr>
            </table>
            </fieldset>
            
            <table class="defaultTable">
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
