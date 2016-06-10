<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<script>
	var chargeTypes = [];
	<c:forEach var="type" items="${chargeTypes}">
		<c:set var="label"><cyclos:escapeJS><bean:message key="transactionFee.chargeType.${type}"/></cyclos:escapeJS></c:set>
		chargeTypes.push({name: '${type}', label: '${label}'});
	</c:forEach>

	var tts = [];
	<c:forEach var="tt" items="${tts}">
		tts.push({
			id:'${tt.id}',
			name:'<cyclos:escapeJS>${tt.name}</cyclos:escapeJS>'
	});
	</c:forEach>

	var fees = [];
	<c:forEach var="fee" items="${fees}">
		tts.push({
			id:'${fee.id}',
			name:'<cyclos:escapeJS>${fee.name}</cyclos:escapeJS>'
	});
	</c:forEach>
</script>
<cyclos:script src="/pages/reports/simulations/aRateConfigSimulation.js" />

<ssl:form method="POST" action="${formAction}">
	<html:hidden property="reloadData" value="false"/>
	<c:if test="${not empty singleTT}">
		<html:hidden property="simulation(transferType)"/>
	</c:if>
	<c:if test="${not empty singleFee}">
		<html:hidden property="simulation(transactionFee)"/>
	</c:if>

	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key="reports.simulations.aRateConfigSimulation.title" /></td>
			<cyclos:help page="reports#aRate_config_simulation"/>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableForms">
	            <table class="defaultTable">
					<c:if test="${empty singleTT && !empty tts}">
						<tr>
							<td class="label" width="40%"><bean:message key="reports.simulations.aRateConfigSimulation.transferType"/></td>
							<td>
								<html:select styleId="ttSelect" property="simulation(transferType)" styleClass="InputBoxEnabled">
									<c:forEach var="tt" items="${tts}">
										<html:option value="${tt.id}">${tt.name}</html:option>
									</c:forEach>													
								</html:select>
							</td>
						</tr>
					</c:if>

					<c:if test="${empty singleFee && !empty fees}">
						<tr>
							<td class="label" width="40%"><bean:message key="reports.simulations.aRateConfigSimulation.transactionFee"/></td>
							<td>
								<html:select styleId="feeSelect" property="simulation(transactionFee)" styleClass="InputBoxEnabled">
									<c:forEach var="fee" items="${fees}">
										<html:option value="${fee.id}">${fee.name}</html:option>
									</c:forEach>													
								</html:select>
							</td>
						</tr>
					</c:if>
				</table>

            	<fieldset><legend><bean:message key="global.params"/></legend>
		            <table class="defaultTable">
		            	<tr>
							<td class="label"><bean:message key='transactionFee.chargeType'/></td>
							<td>
		            			<c:forEach var="type" items="${chargeTypes}">
			            			<label>
				            			<html:radio property="simulation(chargeType)" styleClass="chargeTypeRadio radio" value="${type}" />
				            			<bean:message key="transactionFee.chargeType.${type}" />
			            			</label>
			            			&nbsp;&nbsp;&nbsp;
		            			</c:forEach>
							</td>
						</tr>

						<jsp:include flush="true" page="/pages/accounts/transactionFees/aRateFieldSet.jsp"/>
						
					</table>
				</fieldset>
            	<fieldset><legend><bean:message key="global.range"/></legend>
		            <table class="defaultTable">
		            	<tr>
		            		<td class="label" width="35%"><bean:message key="reports.simulations.aRateConfigSimulation.startA"/></td>
		            		<td>
			            		<html:text styleId="startA" property="simulation(rangeStart)" styleClass="number tiny InputBoxEnabled" />
								<span id="percentRangeStart" style="display:none">
									&nbsp;
									<bean:message key="reports.simulations.aRateConfigSimulation.range.percentUnits"/>
								</span>
							</td>
		            	</tr>
		            	<tr>
		            		<td class="label" width="35%"><bean:message key="reports.simulations.aRateConfigSimulation.endA"/></td>
							<td>
		            			<html:text styleId="endA" property="simulation(rangeEnd)" styleClass="number tiny InputBoxEnabled" />
								<span id="percentRangeEnd" style="display:none">
									&nbsp;
									<bean:message key="reports.simulations.aRateConfigSimulation.range.percentUnits"/>
								</span>
							</td>
		            	</tr>
					</table>
				</fieldset>
	            <table class="defaultTable">
					<tr>
						<td align="right" colspan="2">
							<input id="submitButton" class="button" type="submit" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
				</table>
					
			</td>
		</tr>
	</table>
</ssl:form>
<p>&nbsp;
<c:if test="${not empty dataList}">
	<jsp:include flush="true" page="/pages/reports/statistics/statsResults.jsp"/>
</c:if>


<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left">
			<input id="backButton" class="button" type="button" value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>
