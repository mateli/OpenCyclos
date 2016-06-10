<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<script>
	var currencies = [];
	<c:forEach var="currency" items="${currencies}">
		tts.push({
			id:'${currency.id}',
			name:'<cyclos:escapeJS>${currency.name}</cyclos:escapeJS>'
	});
	</c:forEach>
</script>
<cyclos:script src="/pages/reports/simulations/dRateConfigSimulation.js" />

<ssl:form method="POST" action="${formAction}">
	<html:hidden property="reloadData" value="false"/>
	<c:if test="${not empty singleCurrency}">
		<html:hidden property="simulation(currency)"/>
	</c:if>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key="reports.simulations.dRateConfigSimulation.title" /></td>
			<cyclos:help page="reports#dRate_config_simulation"/>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableForms">
            	<fieldset><legend><bean:message key="global.params"/></legend>
		            <table class="defaultTable">
						<c:if test="${empty singleCurrency && !empty currencies}">
							<tr>
								<td class="label" width="25%"><bean:message key="reports.simulations.dRateConfigSimulation.currency"/></td>
								<td>
									<html:select styleId="currencySelect" property="simulation(currency)" styleClass="InputBoxEnabled">
										<c:forEach var="currency" items="${currencies}">
											<html:option value="${currency.id}">${currency.name}</html:option>
										</c:forEach>													
									</html:select>
								</td>
							</tr>
						</c:if>

		            	<tr>
		            		<td class="label" valign="top"><bean:message key="currency.dRate.interest"/></td>
		            		<td>
		            			<html:text property="simulation(interest)" styleClass="floatHighPrecision medium InputBoxEnabled" />
		            			<bean:message key="global.percentPerDay" />
		            		</td>
		            	</tr>
		            	<tr>
		            		<td class="label" valign="top"><bean:message key="currency.dRate.baseMalus"/></td>
		            		<td>
		            			<html:text property="simulation(baseMalus)" styleClass="float medium InputBoxEnabled" /> 
		            			%
		            		</td>
		            	</tr>
		            	<tr>
		            		<td class="label" valign="top"><bean:message key="currency.dRate.minimalD"/></td>
		            		<td>
		            			<html:text property="simulation(minimalD)" styleClass="float medium InputBoxEnabled" />
		            			<bean:message key="global.timePeriod.DAYS" />
		            		</td>
		            	</tr>
					</table>
				</fieldset>
            	<fieldset><legend><bean:message key="global.range"/></legend>
		            <table class="defaultTable">
		            	<tr>
		            		<td class="label" valign="top"><bean:message key="reports.simulations.dRateConfigSimulation.startD"/></td>
		            		<td>
		            			<html:text property="simulation(rangeStart)" styleClass="number medium InputBoxEnabled" />
		            		</td>
		            	</tr>
		            	<tr>
		            		<td class="label" valign="top"><bean:message key="reports.simulations.dRateConfigSimulation.endD"/></td>
		            		<td>
		            			<html:text property="simulation(rangeEnd)" styleClass="number medium InputBoxEnabled" /> 
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
