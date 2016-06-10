<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<script>
	var arateDefault = ${empty arateDefault ? 'null' : arateDefault};
	var drateDefault = ${empty drateDefault ? 'null' : drateDefault};
	var tts = [];
	<c:forEach var="tt" items="${tts}">
		tts.push({
			id:'${tt.id}',
			name:'<cyclos:escapeJS>${tt.name}</cyclos:escapeJS>',
			rated:'${tt.havingRatedFees}',
			drated:'${tt.havingDratedFees}',
			arated:'${tt.havingAratedFees}'
	});
	</c:forEach>
</script>

<cyclos:script src="/pages/payments/conversionSimulation/conversionSimulation.js" />

<c:choose><c:when test="${myAccount}">
	<c:set var="titleKey" value="conversionSimulation.title.my" />
</c:when><c:otherwise>
	<c:set var="titleKey" value="conversionSimulation.title.of" />
</c:otherwise></c:choose>

<ssl:form method="POST" action="${formAction}">
<html:hidden property="memberId"/>
<html:hidden property="reloadData" value="false"/>
<html:hidden property="advanced"/>
<c:if test="${not empty singleAccount}">
	<html:hidden property="simulation(account)"/>
</c:if>
<c:if test="${not empty singleTT}">
	<html:hidden property="simulation(transferType)"/>
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="${titleKey}" arg0="${member.name}" /></td>
		<cyclos:help page="account_management#conversion_simulation"/>
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<c:if test="${empty singleAccount}">
					<tr>
						<td class="label" width="25%"><bean:message key="conversionSimulation.account"/></td>
						<td>
							<html:select styleId="accountSelect" property="simulation(account)" styleClass="InputBoxEnabled">
								<c:forEach var="account" items="${accounts}">
									<html:option value="${account.id}">${account.type.name}</html:option>
								</c:forEach>													
							</html:select>
						</td>
					</tr>
				</c:if>

				<c:if test="${empty singleTT}">
					<tr>
						<td class="label" width="25%"><bean:message key="conversionSimulation.transferType"/></td>
						<td>
							<html:select styleId="transferTypeSelect" property="simulation(transferType)" styleClass="InputBoxEnabled">
								<c:forEach var="tt" items="${tts}">
									<html:option value="${tt.id}">${tt.name}</html:option>
								</c:forEach>													
							</html:select>
						</td>
					</tr>
				</c:if>

				<tr>
					<td class="label" width="25%"><bean:message key="conversionSimulation.amount"/></td>
					<td>
						<html:text property="simulation(amount)" styleClass="InputBoxEnabled float medium" />
						${account.type.currency.symbol}
					</td>
				</tr>
			</table>

           	<div id="ratedBlock" style="display:none">
				<c:choose><c:when test="${simulateConversionForm.advanced}">
	            	<fieldset><legend><bean:message key="conversionSimulation.rates"/></legend>
			            <table class="defaultTable">
							<tr>
								<td class="label" width="25%">
									<bean:message key="conversionSimulation.useActualRates"/>
								</td>
								<td>
									<html:checkbox styleId="useActualRatesBox" property="simulation(useActualRates)" value="true" />
								</td>
							</tr>
	
							<tr id="trARate" style="display:none">
								<td class="label" width="25%" >
									<div id="targetedA"><bean:message key="conversionSimulation.aRate.targeted"/></div>
									<div id="presentA"><bean:message key="conversionSimulation.aRate.present"/></div>
								</td>
								<td>
									<html:text styleId="aRateEdit" property="simulation(arate)" styleClass="InputBoxDisabled float medium" />
								</td>
							</tr>
	
							<tr id="trDRate" style="display:none">
								<td class="label" width="25%">
									<div id="targetedD"><bean:message key="conversionSimulation.dRate.targeted"/></div>
									<div id="presentD"><bean:message key="conversionSimulation.dRate.present"/></div>
								</td>
								<td>
									<html:text styleId="dRateEdit" property="simulation(drate)" styleClass="InputBoxDisabled float medium" />
								</td>
							</tr>
						
						</table>
					</fieldset>
				</c:when>
				<c:otherwise>
					<%-- if not advanced, useActualRates is not included, but it IS needed, so we must assure it is true. So....: --%>
					<html:hidden property="simulation(useActualRates)" value="true" />
				</c:otherwise></c:choose>
				<%-- next elements are in the rates div section because it makes no sense to show anything with date/time if no rates enabled, as fee is then independant of date. 
				They are not in the rates fieldset for clarity for the user.
				--%>
	            <table class="defaultTable">
					<tr id="presentRateForDate" style="display:none">
						<td class="label" width="25%">
							<bean:message key="conversionSimulation.date"/>
						</td>
						<td>
							<html:text property="simulation(date)" styleClass="InputBoxEnabled date medium" />
						</td>
					</tr>
	
					<tr>
						<td class="label" width="25%">
							<bean:message key="conversionSimulation.graph"/>
						</td>
						<td>
							<html:checkbox property="simulation(graph)" />
						</td>
					</tr>
				</table>
			</div>

            <table class="defaultTable">
				<tr>
   					<td align="left">
			           	<div id="advancedButtons" style="display:none">
							<c:choose><c:when test="${simulateConversionForm.advanced}">
			   					<input id="modeButton" type="button" class="button" value="<bean:message key="global.search.NORMAL"/>">
							</c:when>
							<c:otherwise>
								<input id="modeButton" type="button" class="button" value="<bean:message key="global.search.ADVANCED"/>">
							</c:otherwise></c:choose>
						</div>
					</td>
					<td align="right" >
						<input id="submitButton" class="button" type="submit" value="<bean:message key="global.submit"/>">
					</td>
				</tr>
			</table>
				
		</td>
	</tr>
</table>
</ssl:form>

<c:if test="${not empty totalAmount}">
	
	<jsp:include flush="true" page="/pages/payments/conversionSimulation/conversionSimulationResult.jsp"/>
</c:if>
<c:if test="${not empty dataList}">
	
	<jsp:include flush="true" page="/pages/reports/statistics/statsResults.jsp"/>
</c:if>


<c:if test="${!myAccount && not empty member}">
	
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left">
				<input id="backButton" class="button" type="button" value="<bean:message key="global.back"/>">
			</td>
		</tr>
	</table>
</c:if>
