<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<script>
	var comparedPeriodsTitle = "<cyclos:escapeJS><bean:message key="reports.stats.comparedPeriods" /></cyclos:escapeJS>";
	var singlePeriodTitle = "<cyclos:escapeJS><bean:message key="reports.stats.Period" /></cyclos:escapeJS>";
</script>
<br id="tableComparedPeriodsBr" class="small" style="{display:none;}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0" id="tableComparedPeriods" style="{display:none;}">
   	<tr>
       	<td class="tdHeaderTable" id="comparedPeriodsTitle">
       		<bean:message key="reports.stats.comparedPeriods"/>
        </td>
   	    <cyclos:help page="statistics#periods"/>
    </tr>
   	<tr>
       	<td colspan="2" align="left" class="tdContentTableLists">
           	<table class="defaultTable">
           		<tr>
           			<td width="50%">
           				<fieldset>
           					<legend>
           						<bean:message key="reports.stats.period.periodMain"/>
           					</legend>
           					<table class="defaultTable">
           						<tr>
									<td width="22%" align="right">
            							<bean:message key="global.range.from"/> &nbsp;
            						</td>
            						<td>
										<html:text property="query(periodMain).begin" styleClass="InputBoxEnabled date small"/>
									</td>
								</tr>
								<tr>
									<td align="right">
		    							<bean:message key="global.range.to"/> &nbsp;
		    						</td>
		    						<td>
										<html:text property="query(periodMain).end" styleClass="InputBoxEnabled date small"/>
									</td>
								</tr>
								<tr>
									<td align="right">
										<bean:message key="reports.stats.period.name"/> &nbsp;
									</td>
									<td>
										<html:text property="query(periodMain).name" styleClass="InputBoxEnabled"/>
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
					<td id="tdPeriodComparedTo" width="50%">
						<fieldset>
							<legend>
								<bean:message key="reports.stats.period.periodCompared"/>
							</legend>
							<table class="defaultTable">
								<tr>
									<td width="22%" align="right">
            							<bean:message key="global.range.from"/> &nbsp;
            						</td>
            						<td>
										<html:text property="query(periodComparedTo).begin" styleClass="InputBoxEnabled date small"/>
									</td>
								</tr>
								<tr>
									<td align="right">
		    							<bean:message key="global.range.to"/> &nbsp;
		    						</td>
		    						<td>
       									<html:text property="query(periodComparedTo).end" styleClass="InputBoxEnabled date small"/>
									</td>
								</tr>
								<tr>
									<td align="right">
										<bean:message key="reports.stats.period.name"/> &nbsp;
									</td>
									<td>
										<html:text property="query(periodComparedTo).name" styleClass="InputBoxEnabled small"/>
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
</table>