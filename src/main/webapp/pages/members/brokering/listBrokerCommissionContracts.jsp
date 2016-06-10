<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/brokering/listBrokerCommissionContracts.js" />

<script>
	var removeConfirmation = "<cyclos:escapeJS><bean:message key='brokerCommissionContract.removeConfirmation'/></cyclos:escapeJS>";
</script>

<input type="hidden" name="memberId" value="${member.id}"/>
<input type="hidden" name="brokerId" value="${broker.id}"/>

<c:choose>
	<c:when test="${empty commissionChargeStatusList}">
		<div class="footerNote" helpPage="brokering#commission_charge_status"><bean:message key="brokerCommission.chargeStatus.noComissions"/></div>
	</c:when>
	<c:otherwise>
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key='brokerCommission.chargeStatus'/></td>
		        <cyclos:help page="brokering#commission_charge_status"/>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <c:forEach var="commissionChargeStatus" items="${commissionChargeStatusList}" varStatus="status">
				        <fieldset>
					        <legend>${commissionChargeStatus.brokerCommission.name}</legend>
				            <c:choose>
				            	<c:when test="${cyclos:name(commissionChargeStatus.chargeStatus) == 'DEFAULT_COMMISSION'}">
					            	<table class="defaultTable">
						            	<tr>
						            		<td class="label" width="30%"><bean:message key='brokerCommission.type'/></td>
											<td><bean:message key='brokerCommission.type.defaultCommission'/></td>
						            	</tr>
						            	<tr>
						            		<td class="label"><bean:message key='transactionFee.amount'/></td>
											<td><cyclos:format amount="${commissionChargeStatus.brokeringCommissionStatus.amount}"/></td>
						            	</tr>
						            	<tr>
						            		<td class="label"><bean:message key='defaultBrokerCommission.validity'/></td>
											<td>
												<c:set var="when" value="${cyclos:name(commissionChargeStatus.brokeringCommissionStatus.when)}"/>
												<c:choose><c:when test="${when == 'COUNT'}">
													<c:set var="transactions" value="${commissionChargeStatus.brokeringCommissionStatus.maxCount - commissionChargeStatus.brokeringCommissionStatus.total.count}"/>
													<bean:message key='defaultBrokerCommission.validity.transactions' arg0="${transactions}"/>
												</c:when><c:when test="${when == 'DAYS'}">
													<cyclos:format rawDate="${commissionChargeStatus.brokeringCommissionStatus.expiryDate}"/>
												</c:when><c:otherwise>
													<bean:message key='defaultBrokerCommission.validity.always'/>
												</c:otherwise></c:choose>
											</td>
						            	</tr>
						            </table>
				            	</c:when>
				            	<c:when test="${cyclos:name(commissionChargeStatus.chargeStatus) == 'CONTRACT'}">
				            		<table class="defaultTable">
						            	<tr>
						            		<td class="label" width="30%"><bean:message key='brokerCommission.type'/></td>
											<td><bean:message key='brokerCommission.type.commissionContract'/></td>
						            	</tr>
						            	<tr>
						            		<td class="label"><bean:message key='transactionFee.amount'/></td>
											<td><cyclos:format amount="${commissionChargeStatus.brokerCommissionContract.amount}"/></td>
						            	</tr>
						            	<tr>
						            		<td class="label"><bean:message key='brokerCommissionContract.startDate'/></td>
											<td><cyclos:format rawDate="${commissionChargeStatus.brokerCommissionContract.period.begin}"/></td>
						            	</tr>
						            	<tr>
						            		<td class="label"><bean:message key='brokerCommissionContract.endDate'/></td>
											<td><cyclos:format rawDate="${commissionChargeStatus.brokerCommissionContract.period.end}"/></td>
						            	</tr>
						            	<tr>
						            		<td class="label"><bean:message key='brokerCommissionContract.status'/></td>
											<td><bean:message key='brokerCommissionContract.status.${commissionChargeStatus.brokerCommissionContract.status}'/></td>
						            	</tr>
						            </table>
				            	</c:when>
				            	<c:otherwise>
				            		<table class="defaultTable">
						            	<tr>
											<td colspan="2">
												<bean:message key='brokerCommission.notCharging'/>
											</td>	            	
						            	</tr>
						            </table>
				            	</c:otherwise>	
			            	</c:choose>
						</fieldset>
					</c:forEach>
		        </td>
		    </tr>
		</table>
	</c:otherwise>
</c:choose>



<c:choose><c:when test="${myContracts}">
	<c:set var="title" value="brokerCommissionContract.title.list" />
	<c:set var="arg0" value=""/>
</c:when><c:otherwise>
	<c:set var="title" value="brokerCommissionContract.title.listByBroker" />
	<c:set var="arg0" value="${member.name}"/>
</c:otherwise></c:choose>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key='${title}' arg0="${arg0}"/></td>
        <cyclos:help page="brokering#commission_contracts_list"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
			<c:choose><c:when test="${empty brokerCommissionContracts}">
				<br><br>
				<div align="center"><bean:message key="brokerCommissionContract.search.noResults"/></div>
				<br>&nbsp;
			</c:when><c:otherwise>	            
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents" width="50%"><bean:message key='brokerCommissionContract.brokerCommission'/></th>
	                    <th class="tdHeaderContents" width="20%"><bean:message key='brokerCommissionContract.amount'/></th>
	                    <th class="tdHeaderContents" width="20%"><bean:message key='brokerCommissionContract.status'/></th>
	                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
	                </tr>
	                <c:set var="canManage" value="${cyclos:granted(BrokerPermission.MEMBERS_MANAGE_CONTRACTS)}"/>             
					<c:forEach var="brokerCommissionContract" items="${brokerCommissionContracts}">
						<c:set var="status" value='${brokerCommissionContract.status}'/>
						<c:set var="editable" value='${cyclos:name(status) == "PENDING" or cyclos:name(status) == "ACTIVE"}'/>
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="left">${brokerCommissionContract.brokerCommission.name}</td>
		                    <td align="center"><cyclos:format amount="${brokerCommissionContract.amount}"/></td>
		                    <td align="center"><bean:message key='brokerCommissionContract.status.${brokerCommissionContract.status}'/></td>
		                    <td align="center">
		                    	<c:choose><c:when test="${canManage and editable}">
			                    	<img brokerCommissionContractId="${brokerCommissionContract.id}" src="<c:url value="/pages/images/edit.gif" />" class="edit details" />
		                    		<img brokerCommissionContractId="${brokerCommissionContract.id}" src="<c:url value="/pages/images/delete.gif"/>" class="remove"/>
			                    </c:when><c:otherwise>
			                    	<img brokerCommissionContractId="${brokerCommissionContract.id}" src="<c:url value="/pages/images/view.gif" />" class="view details" />
			                    </c:otherwise></c:choose>		                    	
							</td>
						</tr>
					</c:forEach>                
	            </table>
	        </c:otherwise></c:choose>
        </td>
    </tr>
</table>

<c:if test="${not myContracts}">
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
			<td align="right">
				<c:if test="${not empty brokerCommissions}">
					<span class="label"><bean:message key='brokerCommissionContract.action.new'/></span>
					<select id="newContract">
						<option value=""><bean:message key="brokerCommissionContract.action.selectBrokerCommission" /></option>
						<c:forEach var="brokerCommission" items="${brokerCommissions}">
							<option value="${brokerCommission.id}">${brokerCommission.name}</option>
						</c:forEach>
					</select>
				</c:if>
			</td>
		</tr>
	</table>
</c:if>