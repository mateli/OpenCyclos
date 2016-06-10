<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/accounts/accountFees/editAccountFee.js" />
<script>
	var selectedGeneratedType = "${accountFee.transferType}";
	var selectedRecurrenceField = "${accountFee.recurrence.field}";
	var selectedDay = "${accountFee.day}";
	var selectedHour = "${accountFee.hour}";
	var weekDays = [];
	<c:forEach var="weekDay" items="${weekDays}">
		weekDays.push({value:${weekDay.value}, name:"<bean:message key="global.weekDay.${weekDay}"/>"});
	</c:forEach>
</script>
<ssl:form method="post" action="${formAction}">
<html:hidden property="accountTypeId"/>
<html:hidden property="accountFeeId"/>
<html:hidden property="accountFee(id)"/>
<html:hidden property="accountFee(accountType)" value="${accountType.id}"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${isInsert ? 'accountFee.title.insert' : 'accountFee.title.modify'}"/></td>
        <cyclos:help page="account_management#account_fee_details"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
					<td class="label" width="30%"><bean:message key='accountFee.name'/></td>
					<td><html:text property="accountFee(name)" maxlength="50" readonly="true" styleClass="full InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label" valign="top"><bean:message key='accountFee.description'/></td>
					<td><html:textarea styleId="descriptionText" property="accountFee(description)" rows="6" readonly="true" styleClass="full InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key='accountFee.enabled'/></td>
					<td><html:checkbox property="accountFee(enabled)" styleId="enabledCheck" disabled="true" value="true" styleClass="checkbox InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key='accountFee.chargeMode'/></td>
					<td>
						<c:choose><c:when test="${isInsert}">
							<html:select styleId="chargeModeSelect" property="accountFee(chargeMode)" disabled="true" styleClass="InputBoxDisabled">
								<c:forEach var="chargeMode" items="${chargeModes}">
									<html:option value="${chargeMode}"><bean:message key='accountFee.chargeMode.${chargeMode}'/></html:option>
								</c:forEach>
							</html:select>
						</c:when><c:otherwise>
	                    	<html:hidden property="accountFee(chargeMode)" />
	                    	<input type="text" id="chargeModeText" class="large InputBoxDisabled" readonly="readonly" value="<bean:message key='accountFee.chargeMode.${accountFee.chargeMode}'/>">
						</c:otherwise></c:choose>
					</td>
				</tr>
				<tr id="trDirection" style="display:none">
					<td class="label"><bean:message key='accountFee.paymentDirection'/></td>
					<td>
						<html:select styleId="paymentDirectionSelect" property="accountFee(paymentDirection)" disabled="true" styleClass="InputBoxDisabled">
							<c:forEach var="paymentDirection" items="${paymentDirections}">
								<html:option value="${paymentDirection}"><bean:message key='accountFee.paymentDirection.${paymentDirection}'/></html:option>
							</c:forEach>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key='accountFee.transferType'/></td>
					<td>
						<html:select styleId="generatedSelect" property="accountFee(transferType)" disabled="true" styleClass="InputBoxDisabled">
							<c:forEach var="tt" items="${transferTypes}">
								<html:option value="${tt.id}">${tt.name}</html:option>
							</c:forEach>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key='accountFee.amount'/></td>
					<td><html:text property="accountFee(amount)" readonly="true" styleClass="small float InputBoxDisabled"/></td>
				</tr>
				<tr id="trFreeBase">
					<td class="label"><bean:message key='accountFee.freeBase'/></td>
					<td><html:text property="accountFee(freeBase)" readonly="true" styleClass="small float InputBoxDisabled"/></td>
				</tr>
				<tr id="trInvoiceMode" style="display:none">
					<td class="label"><bean:message key='accountFee.invoiceMode'/></td>
					<td>
						<html:select styleId="invoiceModeSelect" property="accountFee(invoiceMode)" disabled="true" styleClass="InputBoxDisabled">
							<c:forEach var="invoiceMode" items="${invoiceModes}">
								<html:option value="${invoiceMode}"><bean:message key='accountFee.invoiceMode.${invoiceMode}'/></html:option>
							</c:forEach>
						</html:select>
					</td>
				</tr>
				<tr id="trRunMode">
					<td class="label"><bean:message key='accountFee.runMode'/></td>
					<td>
						<c:choose><c:when test="${isInsert}">
							<html:select styleId="runModeSelect" property="accountFee(runMode)" disabled="true" styleClass="InputBoxDisabled">
								<c:forEach var="runMode" items="${runModes}">
									<html:option value="${runMode}"><bean:message key='accountFee.runMode.${runMode}'/></html:option>
								</c:forEach>
							</html:select>
						</c:when><c:otherwise>
	                    	<html:hidden property="accountFee(runMode)" />
	                    	<input type="text" id="runModeText" class="large InputBoxDisabled" readonly="readonly" value="<bean:message key='accountFee.runMode.${accountFee.runMode}'/>">
						</c:otherwise></c:choose>
					</td>
				</tr>
				<tr id="trRecurrence" style="display:none">
					<td class="label"><bean:message key='accountFee.recurrence'/></td>
					<td>
						<c:choose><c:when test="${isInsert}">
	                    	<html:text styleId="recurrenceNumberText" property="accountFee(recurrence).number" maxlength="2" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<html:select styleId="recurrenceFieldSelect" property="accountFee(recurrence).field" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="field" items="${recurrenceFields}">
		                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </c:when><c:otherwise>
	                    	<html:hidden property="accountFee(recurrence).number" />
	                    	<html:hidden property="accountFee(recurrence).field" />
	                    	<c:set var="recurrence">${accountFee.recurrence.number} <bean:message key="global.timePeriod.${accountFee.recurrence.field}"/></c:set>
	                    	<input type="text" id="recurrenceText" class="large InputBoxDisabled" readonly="readonly" value="${recurrence}">
	                    </c:otherwise></c:choose>
					</td>
				</tr>
				<tr id="trDay" style="display:none">
					<td class="label"><bean:message key='accountFee.day'/></td>
					<td><html:select styleId="daySelect" property="accountFee(day)" disabled="true" styleClass="InputBoxDisabled"/></td>
				</tr>
				<tr id="trHour" style="display:none">
					<td class="label"><bean:message key='accountFee.hour'/></td>
					<td>
						<html:select styleId="hourSelect" property="accountFee(hour)" disabled="true" styleClass="InputBoxDisabled">
							<c:forEach var="hour" begin="0" end="23">
								<html:option value="${hour}">${hour}</html:option>
							</c:forEach>
						</html:select>
					</td>
				</tr>
				<c:if test="${not alreadyRan}">
					<tr id="trEnabledSince" style="display:none">
						<td class="label"><bean:message key='accountFee.firstPeriodAfter'/></td>
						<td><html:text property="accountFee(enabledSince)" readonly="true" styleClass="small date InputBoxDisabled"/></td></td>
					</tr>
				</c:if>
				<tr>
					<td class="label" valign="top"><bean:message key='accountFee.groups'/></td>
					<td>
						<cyclos:multiDropDown name="accountFee(groups)" disabled="true" size="5">
							<c:forEach var="group" items="${groups}">
								<cyclos:option value="${group.id}" text="${group.name}" selected="${cyclos:contains(accountFee.groups, group)}"/>
							</c:forEach>
						</cyclos:multiDropDown>
					</td>
				</tr>
				<tr>
					<td align="right" colspan="2">
						<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
						<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
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