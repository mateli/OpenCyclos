<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<tr>
	<td width="5%" class="tdContentTableForms">&nbsp;</td>
	<td width="40%" class="tdContentTableForms" id="selectAllNoneTD">
		<input id="selectAllButton" type="button" class="button" value="<bean:message key="global.selectAll"/>">
		&nbsp; 
		<input id="selectNoneButton" type="button" class="button" value="<bean:message key="global.selectNone"/>">
	</td>
	<td width="50%" align="right" class="tdContentTableForms" id="selectAllNoneGraphsTD">
		<input id="selectAllGraphsButton" type="button" class="ButtonDisabled" value="<bean:message key="reports.stats.general.graph.selectAll"/>">
		&nbsp; 
		<input id="selectNoGraphsButton" type="button" class="ButtonDisabled" value="<bean:message key="reports.stats.general.graph.selectNone"/>">
	</td>
	<td width="5%" class="tdContentTableForms">&nbsp;</td>
</tr>