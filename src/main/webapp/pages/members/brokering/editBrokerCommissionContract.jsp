<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/brokering/editBrokerCommissionContract.js" />

<script>
	var byBroker = ${byBroker};
	var isInsert = ${isInsert};
	var cancelConfirmation = "<cyclos:escapeJS><bean:message key='brokerCommissionContract.cancelConfirmation'/></cyclos:escapeJS>";
	var acceptConfirmation = "<cyclos:escapeJS><bean:message key='brokerCommissionContract.acceptConfirmation'/></cyclos:escapeJS>";
	var denyConfirmation = "<cyclos:escapeJS><bean:message key='brokerCommissionContract.denyConfirmation'/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}">

<input type="hidden" name="memberId" value="${brokerCommissionContract.brokering.brokered.id}"/>
<html:hidden property="brokerCommissionContractId"/>
<html:hidden property="brokerCommissionContract(id)"/>
<html:hidden property="brokerCommissionContract(brokering)"/>
<html:hidden property="brokerCommissionContract(brokerCommission)"/>
<html:hidden property="brokerCommissionContract(status)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        <c:choose>
	        <c:when test="${isInsert}">
		        <bean:message key="brokerCommissionContract.title.insert"/></td>
	    	    <cyclos:help page="brokering#commission_contract_insert"/>
	        </c:when>
	        <c:otherwise>
	        	<bean:message key="brokerCommissionContract.title.view"/></td>		        
	    	    <cyclos:help page="brokering#commission_contract_edit"/>
	        </c:otherwise>
        </c:choose>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<c:if test="${not byBroker}">
					<tr>
						<td class="label" width="30%"><bean:message key='brokerCommissionContract.broker'/></td>
						<td><input id="broker" class="InputBoxDisabled large" value="${brokerCommissionContract.brokering.broker.name}" readonly="readonly"/></td>
					</tr>
				</c:if>
				<c:if test="${byAdmin or byBroker}">
					<tr>
						<td class="label"><bean:message key='brokerCommissionContract.member'/></td>
						<td><input id="member" class="InputBoxDisabled large" value="${brokerCommissionContract.brokering.brokered.name}" readonly="readonly"/></td>
					</tr>
				</c:if>
				<tr>
					<td class="label"><bean:message key='brokerCommissionContract.brokerCommission'/></td>
					<td><input id="brokerCommission" class="InputBoxDisabled large" value="${brokerCommissionContract.brokerCommission.name}" readonly="readonly"/></td>
				</tr>
				<tr>
                    <td class="label"><bean:message key="brokerCommissionContract.startDate"/></td>
                    <td>
						<html:text property="brokerCommissionContract(period).begin" styleClass="small InputBoxDisabled date" readonly="true"/>
                    </td>
                </tr>
				<tr>
                    <td class="label"><bean:message key="brokerCommissionContract.endDate"/></td>
                    <td>
						<html:text property="brokerCommissionContract(period).end" styleClass="small InputBoxDisabled date" readonly="true"/>
                    </td>
                </tr>
                <tr>
					<td class="label"><bean:message key='brokerCommissionContract.amount'/></td>
					<td>
						<html:text property="brokerCommissionContract(amount).value" readonly="true" styleClass="small floatHighPrecision InputBoxDisabled"/>
						<html:select property="brokerCommissionContract(amount).type" disabled="true" styleClass="InputBoxDisabled">
							<c:forEach var="type" items="${amountTypes}">
								<html:option value="${type}"><bean:message key="global.amount.type.${type}"/></html:option>
							</c:forEach>
						</html:select>
					</td>
           		</tr>
           		<tr>
					<td class="label"><bean:message key='brokerCommissionContract.status'/></td>
					<td><input id="status" class="InputBoxDisabled large" value="<bean:message key='brokerCommissionContract.status.${brokerCommissionContract.status}'/>" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="left">
						<c:if test="${cancelable and not isInsert}">
							<input type="button" id="cancelButton" class="button" value="<bean:message key="brokerCommissionContract.action.cancel"/>">
						</c:if>
					</td>
					<td align="right">
						<c:if test="${editable}">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">&nbsp;
						</c:if>
						<c:if test="${canAcceptOrDeny}">
							<input type="button" id="acceptButton" class="button" value="<bean:message key="brokerCommissionContract.action.accept"/>">&nbsp;
							<input type="button" id="denyButton" class="button" value="<bean:message key="brokerCommissionContract.action.deny"/>">&nbsp;
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