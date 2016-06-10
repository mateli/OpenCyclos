<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="conversionSimulation.result"/></td>
		<cyclos:help page="account_management#conversion_simulation_result"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
			<c:if test="${not empty usedARate || not empty usedDRate}">
				<fieldset><legend><bean:message key="conversionSimulation.result.input"/></legend>
					<cyclos:layout className="defaultTable" columns="4">
						<c:if test="${simulateConversionForm.advanced}">
							<c:if test="${not empty usedARate}">
								<cyclos:cell width="20%" className="headerLabel"><bean:message key="conversionSimulation.result.input.arate"/></cyclos:cell>
								<cyclos:cell width="30%">
									<cyclos:format number="${usedARate}" precision="2" />&nbsp;
									<bean:message key="global.timePeriod.DAYS"/>
								</cyclos:cell>
							</c:if>
							<c:if test="${not empty usedDRate}">
								<cyclos:cell width="20%" className="headerLabel"><bean:message key="conversionSimulation.result.input.drate"/></cyclos:cell>
								<cyclos:cell width="30%">
									<cyclos:format number="${usedDRate}" precision="2" />&nbsp;
									<bean:message key="global.timePeriod.DAYS"/>
								</cyclos:cell>
							</c:if>
						</c:if>
						<cyclos:cell width="20%" className="headerLabel"><bean:message key="conversionSimulation.result.input.date"/></cyclos:cell>
						<cyclos:cell width="30%">
							<cyclos:format dateTime="${usedDate}" />
						</cyclos:cell>
					</cyclos:layout>
				</fieldset>
			</c:if>
			<fieldset><legend><bean:message key="conversionSimulation.result.result"/></legend>
	            <table class="defaultTable">
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td width="40%" class="tdHeaderContents"><bean:message key="conversionSimulation.result.name"/></td>
						<td width="25%" class="tdHeaderContents"><bean:message key="conversionSimulation.result.percentage"/></td>
						<td width="35%" class="tdHeaderContents"><bean:message key="conversionSimulation.result.amount"/></td>
					</tr>
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td><bean:message key='conversionSimulation.result.initialAmount'/></td>
						<td align="center">&nbsp;</td>
						<td align="center"><cyclos:format number="${totalAmount}" unitsPattern="${unitsPattern}" /></td>
					</tr>
					<c:choose><c:when test="${not empty fees}">
						<c:forEach var="fee" items="${fees}" varStatus="counter">
			               	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
								<td>${fee.key.name}</td>
								<td align="center">
									<c:if test="${fee.key.chargeType.value == 'P' || fee.key.chargeType.value == 'A' || fee.key.chargeType.value == 'D' || fee.key.chargeType.value == 'M'}">
										<cyclos:format number="${feePercentages[counter.count-1]}" />&nbsp;%
									</c:if>
								</td>
								<td align="center">
									<c:choose><c:when test="${fee.key.deductAmount}">
										-&nbsp;<cyclos:format number="${fee.value}" unitsPattern="${fee.key.generatedTransferType.from.currency.pattern}"/>
									</c:when><c:otherwise>
										<font color="red">
											(<cyclos:format number="${fee.value}" unitsPattern="${fee.key.generatedTransferType.from.currency.pattern}"/>)
										</font>
									</c:otherwise></c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:when><c:otherwise>
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="center" colspan="3">
								<span style="font-style:italic"><bean:message key="conversionSimulation.result.nofees"/></span>
							</td>
						</tr>
					</c:otherwise></c:choose>
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td colspan="3" class="label" >
							<hr>
						</td>
					</tr>
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td><bean:message key='conversionSimulation.result.total'/></td>
						<td align="center"><cyclos:format number="${totalFees}" />%</td>
						<td align="center"><cyclos:format number="${totalFeeAmount}" unitsPattern="${unitsPattern}" /></td>
					</tr>
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td colspan="3" class="label" >
							<hr>
						</td>
					</tr>
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td><bean:message key='conversionSimulation.result.feelessAmount'/></td>
						<td align="center">&nbsp;</td>
						<td align="center"><cyclos:format number="${feelessAmount}" unitsPattern="${unitsPattern}" /></td>
					</tr>
	            </table>
			</fieldset>
        </td>
    </tr>
</table>
