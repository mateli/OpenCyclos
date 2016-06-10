<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/brokering/manageBrokerCommissions.js" />

<script>
	var stopConfirmation = "<cyclos:escapeJS><bean:message key='brokerCommission.stopConfirmation'/></cyclos:escapeJS>";
	var suspendConfirmation = "<cyclos:escapeJS><bean:message key='brokerCommission.suspendConfirmation'/></cyclos:escapeJS>";
	var unsuspendConfirmation = "<cyclos:escapeJS><bean:message key='brokerCommission.unsuspendConfirmation'/></cyclos:escapeJS>";
</script>

<ssl:form method="POST" action="${formAction}">

<input type="hidden" name="brokerId" value="${broker.id}"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="defaultBrokerCommission.title" arg0="${broker.username}"/></td>
        <cyclos:help page="brokering#commission_settings"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<tr><td>
	            	<c:forEach var="defaultBrokerCommission" items="${defaultBrokerCommissions}" varStatus="status">
		            	<fieldset>
		            		<legend>${defaultBrokerCommission.brokerCommission.name}</legend>
			            	<table class="defaultTable">
				            	<tr>
									<td colspan="3">
										<input type="hidden" name="defaultBrokerCommission.id" value="${defaultBrokerCommission.id}"/>
					            		<input type="hidden" name="defaultBrokerCommission.brokerCommission" value="${defaultBrokerCommission.brokerCommission.id}" />
									</td>	            	
				            	</tr>
				            	<tr>
									<td class="label" width="35%"><bean:message key='defaultBrokerCommission.defaultCommissionAmount'/></td>
									<td width="35%">
										<cyclos:format number="${defaultBrokerCommission.amount.value}" precision="4"/>
										<bean:message key="global.amount.type.${defaultBrokerCommission.amount.type}"/>
									</td>
									<td width="30%"></td>
				           		</tr>
				            	<tr>
									<td class="label"><bean:message key='transactionFee.when'/></td>
									<td>
										<c:choose>
											<c:when test='${(cyclos:name(defaultBrokerCommission.when) == "DAYS") or (cyclos:name(defaultBrokerCommission.when) == "COUNT")}'>
												${defaultBrokerCommission.count}&nbsp;<bean:message key='transactionFee.when.${defaultBrokerCommission.when}'/>
											</c:when>
											<c:otherwise>
												<bean:message key='transactionFee.when.${defaultBrokerCommission.when}'/>
											</c:otherwise>
										</c:choose>
									</td>
									<td align="right"></td>
				            	</tr>
				            	<tr>
				            		<td class="label"><bean:message key='defaultBrokerCommission.status'/></td>
				            		<td><bean:message key='defaultBrokerCommission.status.${defaultBrokerCommission.status}'/></td>
				            		<td align="right">
				            			<c:choose><c:when test="${not defaultBrokerCommission.suspended}">
											<input type="button" class="button suspend" value="<bean:message key="brokerCommission.action.suspend"/>" brokerCommissionId="${defaultBrokerCommission.brokerCommission.id}"/>
										</c:when><c:otherwise>
											<input type="button" class="button unsuspend" value="<bean:message key="brokerCommission.action.unsuspend"/>" brokerCommissionId="${defaultBrokerCommission.brokerCommission.id}"/>
										</c:otherwise></c:choose>
										<%-- Stop function was disabled
										<input type="button" class="button stop" value="<bean:message key="brokerCommission.action.stop"/>" brokerCommissionId="${defaultBrokerCommission.brokerCommission.id}"/>
										--%>
									</td>
				            	</tr>
				            	<c:if test="${not defaultBrokerCommission.setByBroker}">
					            	<tr>
					            		<td colspan="3" align="center">
					            			<span class="label"><bean:message key="defaultBrokerCommission.noCustomizedByBroker"/></span>
					            		</td>
					            	</tr>
				            	</c:if>
			            	</table>
		            	</fieldset>
		            </c:forEach>
		        </td></tr>	
            </table>
        </td>
    </tr>
</table>
<br/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="brokerCommissionContract.title.list"/></td>
		<cyclos:help page="brokering#commission_contracts_search_filters"/>
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<table class="defaultTable">
				<tr>
					<td class="label"><bean:message key="member.username"/></td>
					<td>
						<html:hidden styleId="memberId" property="query(member)"/>
						<input id="memberUsername" class="full" value="${query.member.username}">
						<div id="membersByUsername" class="autoComplete"></div>
					</td>
					<td class="label"><bean:message key="member.memberName"/></td>
					<td>
						<input id="memberName" class="full" value="${query.member.name}">
						<div id="membersByName" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="brokerCommissionContract.brokerCommission"/></td>
					<td colspan="3">
						<html:select styleClass="large" property="query(brokerCommission)">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach var="brokerCommission" items="${brokerCommissions}">
								<html:option value="${brokerCommission.id}">${brokerCommission.name}</html:option>
							</c:forEach>
						</html:select>
					</td>	
				</tr>
				<tr>
					<td class="label"><bean:message key="brokerCommissionContract.status"/></td>
					<td colspan="3">
						<html:select styleClass="large" property="query(status)">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach var="status" items="${statusList}">
								<html:option value="${status}"><bean:message key="brokerCommissionContract.status.${status}"/></html:option>
							</c:forEach>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="brokerCommissionContract.startDate.from"/></td>
					<td nowrap="nowrap"><html:text property="query(startPeriod).begin" styleClass="small date"/></td>
					<td class="label"><bean:message key="brokerCommissionContract.startDate.to"/></td>
					<td nowrap="nowrap"><html:text property="query(startPeriod).end" styleClass="small date"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="brokerCommissionContract.endDate.from"/></td>
					<td nowrap="nowrap"><html:text property="query(endPeriod).begin" styleClass="small date"/></td>
					<td class="label"><bean:message key="brokerCommissionContract.endDate.to"/></td>
					<td nowrap="nowrap"><html:text property="query(endPeriod).end" styleClass="small date"/></td>
				</tr>
				<tr>
					<td colspan="4" align="right">
						<input type="submit" class="button" value="<bean:message key="global.search"/>">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</ssl:form>

<c:choose>
	<c:when test="${empty brokerCommissionContracts}">
		<div class="footerNote" helpPage="brokering#commission_contracts_search_results"><bean:message key="brokerCommissionContract.search.noResults"/></div>
	</c:when><c:otherwise>
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		        <cyclos:help page="brokering#commission_contracts_search_results"/>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable">
		                <tr>
		                	<th class="tdHeaderContents" width="35%"><bean:message key='brokerCommissionContract.member'/></th>
		                    <th class="tdHeaderContents" width="15%"><bean:message key="brokerCommissionContract.startDate"/></th>
		                    <th class="tdHeaderContents" width="15%"><bean:message key='brokerCommissionContract.endDate'/></th>
		                    <th class="tdHeaderContents" width="15%"><bean:message key='brokerCommissionContract.amount'/></th>
		                    <th class="tdHeaderContents" width="15%"><bean:message key='brokerCommissionContract.status'/></th>
		                    <th class="tdHeaderContents" width="5%">&nbsp;</th>
		                </tr>
		                <c:forEach var="brokerCommissionContract" items="${brokerCommissionContracts}">
		                	<c:set var="editable" value="${cyclos:name(brokerCommissionContract.status) == 'PENDING'}"/>
		                	<c:set var="deletable" value="${cyclos:name(brokerCommissionContract.status) == 'ACTIVE' or cyclos:name(brokerCommissionContract.status) == 'PENDING'}"/>
		                	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			                    <td align="center">${brokerCommissionContract.brokering.brokered.name}</td>
			                    <td align="center"><cyclos:format rawDate="${brokerCommissionContract.period.begin}"/></td>
			                    <td align="center"><cyclos:format rawDate="${brokerCommissionContract.period.end}"/></td>
			                    <td align="center"><cyclos:format amount="${brokerCommissionContract.amount}"/></td>
			                    <td align="center"><bean:message key='brokerCommissionContract.status.${brokerCommissionContract.status}'/></td>
			                    <td align="center"><img brokerCommissionContractId="${brokerCommissionContract.id}" src="<c:url value="/pages/images/view.gif"/>" class="details view"/></td>
		                	</tr>
		                </c:forEach>
		            </table>
		        </td>
		    </tr>
		</table>
	</c:otherwise>
</c:choose>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>