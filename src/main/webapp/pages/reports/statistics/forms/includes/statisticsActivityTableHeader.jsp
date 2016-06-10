<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>

<tr></tr>

<tr>
	<td class="tdHeaderContents" width="33%">
		<bean:message key="reports.stats.general.whatToShow"/>
	</td>
	<td class="tdHeaderContents" width="2%"></td>
	<td class="tdHeaderContents singlePeriod" colspan="3" width="50%"></td>
	<td style="display:none" class="tdHeaderContents compare2periods" colspan="3" width="50%">
		<bean:message key="reports.stats.general.comparePeriods"/>
	</td>
	<td style="display:none" class="tdHeaderContents throughTime" colspan="3" width="50%">
		<bean:message key="reports.stats.throughTheTime"/>
	</td>
	<td style="display:none" class="tdHeaderContents distribution" colspan="3" width="50%"></td>
</tr>

<tr>
	<td class="tdHeaderContents" width="33%">
		(<bean:message key="reports.stats.general.perMember"/>)
	</td>
	<td class="tdHeaderContents" width="2%"></td>
	
	<td align="center" class="tdHeaderContents singlePeriod" width="24%"></td>
		
	<td class="tdHeaderContents compare2periods" width="24%">
		<bean:message key="reports.stats.general.table"/>
	</td>
	<td class="tdHeaderContents compare2periods" width="24%">
		<bean:message key="reports.stats.general.graph"/>
	</td>
	
	<td class="tdHeaderContents throughTime" width="24%">
		<bean:message key="reports.stats.general.table"/>
	</td>
	<td class="tdHeaderContents throughTime" width="24%">
		<bean:message key="reports.stats.general.graph"/>
	</td>
		
	<td style="display:none" class="tdHeaderContents distribution" width="24%">
		<bean:message key="reports.stats.general.histogram"/>
	</td>
	<td style="display:none" class="tdHeaderContents distribution" width="24%">
		<bean:message key="reports.stats.general.topten"/>
	</td>
	
	<td class="tdHeaderContents" width="2%"></td>
</tr>