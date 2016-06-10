<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/brokering/defaultBrokerCommissions.js" />

<script language="JavaScript">
	var keepDisabled = ['natureName'];
</script>

<ssl:form method="POST" action="${formAction}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="defaultBrokerCommission.title.my"/></td>
        <cyclos:help page="brokering#commission_settings"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<tr><td>
	            	<c:forEach var="defaultBrokerCommission" items="${defaultBrokerCommissions}" varStatus="status">
	            		<c:set var="id" value="${defaultBrokerCommission.id}"/>
	            		<c:if test="${defaultBrokerCommission.suspended}">
	            			<script language="JavaScript">
	            				keepDisabled[keepDisabled.length] = "dbc_value_${id}";
	            				keepDisabled[keepDisabled.length] = "dbc_type_${id}";
	            				keepDisabled[keepDisabled.length] = "countText_${id}";
	            				keepDisabled[keepDisabled.length] = "dbc_when_${id}";
	            			</script>
	            		</c:if>
		            	<fieldset>
		            		<legend>${defaultBrokerCommission.brokerCommission.name}</legend>
			            	<table class="defaultTable">
				            	<tr>
									<td colspan="2">
										<input type="hidden" name="defaultBrokerCommission.id" value="${id}"/>
					            		<input type="hidden" name="defaultBrokerCommission.brokerCommission" value="${defaultBrokerCommission.brokerCommission.id}" />
									</td>	            	
				            	</tr>
				            	<tr>
									<td class="label" width="40%"><bean:message key='defaultBrokerCommission.defaultCommissionAmount'/></td>
									<td>
										<input type="text" name="defaultBrokerCommission.value" id="dbc_value_${id}" class="small floatHighPrecision InputBoxDisabled" readonly="true" value='<cyclos:format number="${defaultBrokerCommission.amount.value}" precision="4"/>'/>
										<select name="defaultBrokerCommission.type" id="dbc_type_${id}" class="InputBoxDisabled" disabled="disabled">
											<c:forEach var="type" items="${amountTypes}">
												<c:set var="selected" value='${cyclos:name(defaultBrokerCommission.amount.type) == type ? "selected" : ""}'/>
												<option value="${type}" ${selected}><bean:message key="global.amount.type.${type}"/></option>
											</c:forEach>
										</select>
									</td>
				           		</tr>
				            	<tr>
									<td class="label"><bean:message key='transactionFee.when'/></td>
									<td>
										<input type="text" id="countText_${id}" name="defaultBrokerCommission.count" readonly="true" value="${defaultBrokerCommission.count}" class="tiny number InputBoxDisabled" style="display:none"/>
										<select name="defaultBrokerCommission.when" id="dbc_when_${id}" class="when InputBoxDisabled" disabled="disabled" commissionId="${id}">
											<c:forEach var="when" items="${whens}">
												<c:set var="selected" value='${cyclos:name(defaultBrokerCommission.when) == when ? "selected" : ""}'/>
												<option value="${when}" ${selected}><bean:message key='transactionFee.when.${when}'/></option>
											</c:forEach>
										</select>
									</td>
				            	</tr>
				            	<tr>
				            		<td class="label"><bean:message key='defaultBrokerCommission.status'/></td>
				            		<td><bean:message key='defaultBrokerCommission.status.${defaultBrokerCommission.status}'/></td>
				            	</tr>
				            	<c:if test="${not defaultBrokerCommission.setByBroker}">
					            	<tr>
					            		<td colspan="2" align="center">
					            			<span class="label"><bean:message key="defaultBrokerCommission.noCustomizedByBroker"/></span>
					            		</td>
					            	</tr>
				            	</c:if>
			            	</table>
		            	</fieldset>
		            </c:forEach>
		        </td></tr>	
            	<tr>
					<td align="right" colspan="2">
						<c:if test="${cyclos:granted(BrokerPermission.MEMBERS_MANAGE_DEFAULTS) && !empty(defaultBrokerCommissions)}">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
						</c:if>
						<c:if test="${empty(defaultBrokerCommissions)}">
							<bean:message key='transfer.commision.broker.notcommision'/>
						</c:if>
					</td>
            	</tr>
            </table>
        </td>
    </tr>
</table>
</ssl:form>
