<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable">
			<bean:message key="reports.simulations.choose"/>
		</td>
		<cyclos:help page="reports#choose_simulation"/>
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<cyclos:layout className="defaultTable" columns="2">
				<c:if test="${cyclos:granted(AdminSystemPermission.REPORTS_A_RATE_CONFIG_SIMULATION)}">
					<cyclos:cell width="40%" className="label">
						<bean:message key="reports.simulations.choose.aRateConfig" />&nbsp;&nbsp;
					</cyclos:cell>
					<cyclos:cell align="left">
						<input type="button" class="linkButton" linkURL="aRateConfigSimulation" value="<bean:message key="global.submit" />"/>
					</cyclos:cell>
				</c:if>						
				<c:if test="${cyclos:granted(AdminSystemPermission.REPORTS_D_RATE_CONFIG_SIMULATION)}">
					<cyclos:cell width="40%" className="label">
						<bean:message key="reports.simulations.choose.dRateConfig" />&nbsp;&nbsp;
					</cyclos:cell>
					<cyclos:cell align="left">
						<input type="button" class="linkButton" linkURL="dRateConfigSimulation" value="<bean:message key="global.submit" />"/>
					</cyclos:cell>
				</c:if>						
			</cyclos:layout>
		</td>
	</tr>
</table>
