<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:set var="mapName" value="${empty simulation ? 'transactionFee' : 'simulation'}" />

<tr>
	<td class="label" width="35%"><bean:message key='transactionFee.aRateRelation'/></td>
	<td>
		<c:forEach var="relation" items="${aRateRelations}">
			<label>
				<html:radio property="${mapName}(aRateRelation)" styleClass="aRateRelation radio" value="${relation}" />
				<bean:message key='transactionFee.aRateRelation.${relation}'/>
			</label>
			&nbsp;&nbsp;&nbsp;
		</c:forEach>
	</td>
</tr>

<tr>
	<td class="label"><bean:message key='transactionFee.h'/></td>
 	<td><html:text styleId="h" property="${mapName}(h)" readonly="true" styleClass="small floatHighPrecision InputBoxDisabled"/> %</td>
</tr>
<tr id="fInfiniteRow" style="display:none">
	<td class="label"><bean:message key='transactionFee.fInfinite'/></td>
	<td><html:text styleId="fInfinite" property="${mapName}(fInfinite)" readonly="true" styleClass="small floatHighPrecisionAllowNegative InputBoxDisabled"/> %</td>
</tr>
<tr id="aFIsZeroRow" style="display:none">
	<td class="label"><bean:message key='transactionFee.aFIsZero'/></td>
	<td>
		<html:text styleId="aFIsZero" property="${mapName}(aFIsZero)" readonly="true" styleClass="small floatHighPrecision InputBoxDisabled"/>
		<bean:message key="global.timePeriod.DAYS" />
	</td>
</tr>
<tr id="gFIsZeroRow" class="display:none">
	<td class="label"><bean:message key='transactionFee.gFIsZero'/></td>
	<td><html:text styleId="gFIsZero" property="${mapName}(gFIsZero)" readonly="true" styleClass="small floatHighPrecision InputBoxDisabled"/> %</td>
</tr>		            	
<tr id="f1Row" style="display:none">
	<td class="label"><bean:message key='transactionFee.f1'/></td>
	<td><html:text styleId="f1" property="${mapName}(f1)" readonly="true" styleClass="small floatHighPrecision InputBoxDisabled"/> %</td>
</tr>
<tr>
	<td class="label"><bean:message key='transactionFee.fMinimal'/></td>
	<td><html:text styleId="fMinimal" property="${mapName}(fMinimal)" readonly="true" styleClass="small floatHighPrecision InputBoxDisabled"/> %</td>
</tr>
