<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:if test="${isInsert}">
	<script>
		var allowARate = ${true && allowARate};
		var allowDRate = ${true && allowDRate};
		var chargeTypes = [];
		<c:forEach var="type" items="${chargeTypes}">
			<c:set var="label"><cyclos:escapeJS><bean:message key="transactionFee.chargeType.${type}"/></cyclos:escapeJS></c:set>
			chargeTypes.push({name: '${type}', label: '${label}'});
		</c:forEach>
	</script>
</c:if>
<cyclos:script src="/pages/accounts/transactionFees/editSimpleTransactionFee.js" />

<ssl:form method="POST" action="${formAction}">
<html:hidden property="nature" value="SIMPLE"/>
<html:hidden property="accountTypeId"/>
<html:hidden property="transferTypeId"/>
<html:hidden property="transactionFeeId"/>
<html:hidden property="transactionFee(id)"/>
<c:if test="${not empty singleSubject}">
	<html:hidden property="transactionFee(payer)" value="${singleSubject}" />
	<html:hidden property="transactionFee(receiver)" value="${singleSubject}" />
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="transactionFee.title.simple.${isInsert ? 'insert' : 'modify'}"/></td>
        <cyclos:help page="account_management#transaction_fee_details"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
            		<td class="label" width="35%"><bean:message key='transfer.type'/></td>
            		<td><input id="transferTypeName" readonly="readonly" class="full InputBoxDisabled" value="${transferType.name}"/></td>
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
					<c:choose><c:when test="${isInsert}">
		            	<tr class="fromFixedMember" style="display:none">
							<td class="label"><bean:message key='transactionFee.fromFixedMember.username'/></td>
							<td>
								<html:hidden styleId="fromFixedMemberId" property="transactionFee(fromFixedMember)"/>
								<input id="fromFixedMemberUsername" class="full" value="${transactionFee.fromFixedMember.username}">
								<div id="fromFixedMembersByUsername" class="autoComplete"></div>
							</td>
						</tr>
						<tr class="fromFixedMember" style="display:none">
							<td class="label"><bean:message key="transactionFee.fromFixedMember.name"/></td>
							<td>
								<input id="fromFixedMemberName" class="full" value="${transactionFee.fromFixedMember.name}">
								<div id="fromFixedMembersByName" class="autoComplete"></div>
							</td>
						</tr>
					</c:when><c:otherwise>
						<tr class="fromFixedMember" style="display:none">
							<td class="label"><bean:message key='transactionFee.fromFixedMember.username'/></td>
							<td>
								<html:hidden property="transactionFee(fromFixedMember)" />
								<input readonly="readonly" id="fromFixedMemberText" class="full InputBoxDisabled" value="${transactionFee.fromFixedMember.username}" >
							</td>
						</tr>
						<tr class="fromFixedMember" style="display:none">
							<td class="label"><bean:message key='transactionFee.fromFixedMember.name'/></td>
							<td>
								<input readonly="readonly" id="fromFixedMemberText" class="full InputBoxDisabled" value="${transactionFee.fromFixedMember.name}" >
							</td>
						</tr>
					</c:otherwise></c:choose>
	            	<tr>
						<td class="label"><bean:message key='transactionFee.receiver'/></td>
						<td>
							<c:choose><c:when test="${isInsert}">
								<html:select styleId="receiverSelect" property="transactionFee(receiver)" disabled="true" styleClass="InputBoxDisabled">
									<c:forEach var="subject" items="${possibleSubjects}">
										<html:option value="${subject}"><bean:message key="transactionFee.subject.${subject}" /></html:option>
									</c:forEach>
								</html:select>
							</c:when><c:otherwise>
								<html:hidden property="transactionFee(receiver)" />
								<input readonly="readonly" id="receiverText" class="full InputBoxDisabled" value="<bean:message key="transactionFee.subject.${transactionFee.receiver}" />" >
							</c:otherwise></c:choose>
						</td>
	            	</tr>
					<c:choose><c:when test="${isInsert}">
		            	<tr class="toFixedMember" style="display:none">
							<td class="label"><bean:message key='transactionFee.toFixedMember.username'/></td>
							<td>
								<html:hidden styleId="toFixedMemberId" property="transactionFee(toFixedMember)"/>
								<input id="toFixedMemberUsername" class="full" value="${transactionFee.toFixedMember.username}">
								<div id="toFixedMembersByUsername" class="autoComplete"></div>
							</td>
						</tr>
						<tr class="toFixedMember" style="display:none">
							<td class="label"><bean:message key="transactionFee.toFixedMember.name"/></td>
							<td>
								<input id="toFixedMemberName" class="full" value="${transactionFee.toFixedMember.name}">
								<div id="toFixedMembersByName" class="autoComplete"></div>
							</td>
						</tr>
					</c:when><c:otherwise>
						<tr class="toFixedMember" style="display:none">
							<td class="label"><bean:message key='transactionFee.toFixedMember.username'/></td>
							<td>
								<html:hidden property="transactionFee(toFixedMember)" />
								<input readonly="readonly" id="toFixedMemberText" class="full InputBoxDisabled" value="${transactionFee.toFixedMember.username}" >
							</td>
						</tr>
						<tr class="toFixedMember" style="display:none">
							<td class="label"><bean:message key='transactionFee.toFixedMember.name'/></td>
							<td>
								<input readonly="readonly" id="toFixedMemberText" class="full InputBoxDisabled" value="${transactionFee.toFixedMember.name}" >
							</td>
						</tr>
					</c:otherwise></c:choose>
            	</c:if>
            	<c:if test="${!transferType.fromSystem || !transferType.toSystem}">
	            	<tr>
						<td class="label"><bean:message key='transactionFee.allowAnyAccount'/></td>
						<td><html:checkbox styleId="allowAnyAccount" property="allowAnyAccount" disabled="true" value="true"  /></td>
	            	</tr>
	            </c:if>
            	<tr>
					<td class="label"><bean:message key='transactionFee.generatedTransferType'/></td>
					<td>
						<html:select styleId="generatedSelect" property="transactionFee(generatedTransferType)" disabled="true" styleClass="InputBoxDisabled">
							<c:forEach var="tt" items="${generatedTransferTypes}">
								<html:option value="${tt.id}">${tt.name}</html:option>
							</c:forEach>
						</html:select>
					</td>
            	</tr>
            	<tr>
					<td class="label"><bean:message key='transactionFee.chargeType'/></td>
					<td>
						<html:select styleId="chargeTypeSelect" property="transactionFee(chargeType)" disabled="true" styleClass="InputBoxDisabled">
							<c:if test="${!isInsert}">
								<c:forEach var="type" items="${chargeTypes}">
									<html:option value="${type}"><bean:message key="transactionFee.chargeType.${type}"/></html:option>
								</c:forEach>
							</c:if>
						</html:select>
					</td>
				</tr>
            	<tr id="valueTR" style="display:none">
					<td class="label"><bean:message key='transactionFee.value'/></td>
					<td>
						<html:text property="transactionFee(value)" readonly="true" styleClass="small floatHighPrecision InputBoxDisabled"/>
						<span id="percentSign" style="display:none">%</span>
					</td>
           		</tr>
            	<tr id="deductionTR" style="display:none">
					<td class="label"><bean:message key='transactionFee.deductAmount'/></td>
					<td>
						<html:select property="transactionFee(deductAmount)" disabled="true" styleClass="InputBoxDisabled">
							<html:option value="false"><bean:message key='transactionFee.deductAmount.false'/></html:option>
							<html:option value="true"><bean:message key='transactionFee.deductAmount.true'/></html:option>
						</html:select>
					</td>
            	</tr>
            </table>
            
            <c:if test="${allowARate}">
            	<div id="aRateParameters" style="display:none">
		            <fieldset><legend><bean:message key="transactionFee.aRateParameters"/></legend>
		            <table class="defaultTable">
						<jsp:include flush="true" page="/pages/accounts/transactionFees/aRateFieldSet.jsp"/>
		            </table>
		            </fieldset>
            	</div>
            </c:if>

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
