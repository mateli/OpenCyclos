<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable">
			<bean:message key="reports.stats.chooseStatisticsCategories"/>
		</td>
		<cyclos:help page="statistics#choose_category"/>
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<cyclos:layout className="defaultTable" columns="2">
				<cyclos:cell width="40%" className="label">
					<bean:message key="reports.stats.choose.keydevelopments" />&nbsp;&nbsp;
				</cyclos:cell>
				<cyclos:cell align="left">
					<input type="button" class="linkButton" linkURL="statisticsKeyDevelopments" value="<bean:message key="global.submit" />"/>
				</cyclos:cell>						
				<cyclos:cell width="40%" className="label">
					<bean:message key="reports.stats.choose.activity"/>&nbsp;&nbsp;
				</cyclos:cell>
				<cyclos:cell align="left">
					<input type="button" class="linkButton" linkURL="statisticsActivity" value="<bean:message key="global.submit" />"/>
				</cyclos:cell>
				<cyclos:cell width="40%" className="label">
					<bean:message key="reports.stats.choose.finances"/>&nbsp;&nbsp;
				</cyclos:cell>
				<cyclos:cell align="left">
					<input type="button" class="linkButton" linkURL="statisticsFinances" value="<bean:message key="global.submit" />"/>
				</cyclos:cell>
				<!--  
				<cyclos:cell width="40%" className="label">
					<bean:message key="reports.stats.choose.taxes"/>&nbsp;&nbsp;
				</cyclos:cell>
				<cyclos:cell align="left">
					<input type="button" class="linkButton" linkURL="statisticsTaxes" value="<bean:message key="global.submit" />"/>
				</cyclos:cell>
				<cyclos:cell width="40%" className="label">
					<bean:message key="reports.stats.choose.balances"/>&nbsp;&nbsp;
				</cyclos:cell>
				<cyclos:cell align="left">
					<input type="button" class="linkButton" linkURL="statisticsBalances" value="<bean:message key="global.submit" />"/>
				</cyclos:cell>
				<cyclos:cell width="40%" className="label">
					<bean:message key="reports.stats.choose.miscellaneous"/>&nbsp;&nbsp;
				</cyclos:cell>
				<cyclos:cell align="left">
					<input type="button" class="linkButton" linkURL="statisticsMiscellaneous" value="<bean:message key="global.submit" />"/>
				</cyclos:cell>
				-->
			</cyclos:layout>
		</td>
	</tr>
</table>
