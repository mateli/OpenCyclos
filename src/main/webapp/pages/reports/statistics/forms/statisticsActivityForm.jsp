<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<cyclos:script src="/pages/reports/statistics/forms/statisticsForms.js" />
<cyclos:script src="/pages/reports/statistics/forms/statisticsActivityForm.js" />
<script>
	var allPaymentsString = "<cyclos:escapeJS><bean:message key="reports.stats.general.allPaymentTypes"/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}"> 
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable">
				<bean:message key="reports.stats.activity.title"/>
			</td>
			<cyclos:help page="statistics#member_activity"/>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableForms">
	            <table class="defaultTable">
					<tr>
						<td colspan="2" align="left" class="tdContentTableForms"> 
							<jsp:include flush="true" page="/pages/reports/statistics/forms/includes/whatToShow.jsp"/>
							<fieldset>
					            <table class="defaultTable">
									<jsp:include flush="true" page="/pages/reports/statistics/forms/includes/statisticsActivityTableHeader.jsp"/>

					            	<tr>
					            		<td class="label" width="33%">
	   	        							<bean:message key="reports.stats.keydevelopments.grossProduct"/>&nbsp;&nbsp;
				    	        		</td>
				    	        		<td></td>
	   	        						<td align="center">
				    	        			<html:checkbox styleId="grossProductCheckBox" 
	   	        						            	   property="query(grossProduct)" 
	   	        						              	   value="true" 
	    	        			            			   styleClass="checkbox itemCheckBox"/>
	   					        		</td>
	   					        		<td style="display:none" class="compare2periods" align="center">
	   					        			<html:checkbox styleId="grossProductGraphCheckBoxCP"
	   					        						   property="query(grossProductGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
	   					        		</td>
	   					        		<td style="display:none" class="throughTime" align="center">
	   					        			<html:checkbox styleId="grossProductGraphCheckBoxTT"
	   					        						   property="query(grossProductGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
	   					        		</td>
	   					        		<td style="display:none" class="distribution" align="center">
	   					        			<html:checkbox styleId="grossProductToptenCheckBox"
	   					        						   property="query(grossProductTopten)"
	   					        						   value="true"
	   					        						   styleClass="checkbox itemCheckBox distribution"/>
	   					        		</td>
	   					        		<td></td>
				            		</tr>
				            		
					            	<tr>
					            		<td class="label" width="33%">
	   	        							<bean:message key="reports.stats.keydevelopments.numberOfTransactions"/>&nbsp;&nbsp;
				    	        		</td>
				    	        		<td></td>
	   	        						<td align="center">
				    	        			<html:checkbox styleId="numberOfTransactionsCheckBox" 
	   	        						            	   property="query(numberTransactions)" 
	   	        						              	   value="true" 
	    	        			            			   styleClass="checkbox itemCheckBox"/>
	   					        		</td>
	   					        		<td style="display:none" class="compare2periods" align="center">
	   					        			<html:checkbox styleId="numberOfTransactionsGraphCheckBoxCP"
	   					        						   property="query(numberTransactionsGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
	   					        		</td>
	   					        		<td style="display:none" class="throughTime" align="center">
	   					        			<html:checkbox styleId="numberOfTransactionsGraphCheckBoxTT"
	   					        						   property="query(numberTransactionsGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
	   					        		</td>
	   					        		<td style="display:none" class="distribution" align="center">
	   					        			<html:checkbox styleId="numberOfTransactionsToptenCheckBox"
	   					        						   property="query(numberTransactionsTopten)"
	   					        						   value="true"
	   					        						   styleClass="checkbox itemCheckBox distribution"/>
	   					        		</td>
	   					        		<td></td>
				            		</tr>
					            	
					            	<tr id="noTradersTR">
					            		<td class="label" width="33%">
	   	        							<bean:message key="reports.stats.activity.developments.percentageNoTraders"/>&nbsp;&nbsp;
				    	        		</td>
				    	        		<td></td>
	   	        						<td align="center">
				    	        			<html:checkbox styleId="percentageNoTradeCheckBox" 
	   	        						            	   property="query(percentageNoTrade)" 
	   	        						              	   value="true" 
	    	        			            			   styleClass="checkbox itemCheckBox"/>
	   					        		</td>
	   					        		<td style="display:none" class="compare2periods" align="center">
	   					        			<html:checkbox styleId="percentageNoTradeGraphCheckBoxCP"
	   					        						   property="query(percentageNoTradeGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
	   					        		</td>
	   					        		<td style="display:none" class="throughTime" align="center">
	   					        			<html:checkbox styleId="percentageNoTradeGraphCheckBoxTT"
	   					        						   property="query(percentageNoTradeGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
	   					        		</td>
	   					        		<td></td>
				            		</tr>
				            		
					            	<tr>
					            		<td class="label" width="33%">
	   	        							<bean:message key="reports.stats.activity.developments.logins"/>&nbsp;&nbsp;
				    	        		</td>
				    	        		<td></td>
	   	        						<td align="center">
				    	        			<html:checkbox styleId="loginTimesCheckBox" 
	   	        						            	   property="query(loginTimes)" 
	   	        						              	   value="true" 
	    	        			            			   styleClass="checkbox itemCheckBox logins"/>
	   					        		</td>
	   					        		<td style="display:none" class="compare2periods" align="center">
	   					        			<html:checkbox styleId="loginTimesGraphCheckBoxCP"
	   					        						   property="query(loginTimesGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
	   					        		</td>
	   					        		<td style="display:none" class="throughTime" align="center">
	   					        			<html:checkbox styleId="loginTimesGraphCheckBoxTT"
	   					        						   property="query(loginTimesGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
	   					        		</td>
	   					        		<td style="display:none" class="distribution" align="center">
	   					        			<html:checkbox styleId="loginTimesToptenCheckBox"
	   					        						   property="query(loginTimesTopten)"
	   					        						   value="true"
	   					        						   styleClass="checkbox itemCheckBox logins distribution"/>
	   					        		</td>
	   					        		<td></td>
				            		</tr>
           		            	</table>
           		            </fieldset>
					    </td>
					</tr>	
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
	            <table class="defaultTable">
            		<!-- The block with the select All/None buttons -->
					<jsp:include flush="true" page="/pages/reports/statistics/forms/includes/selectAllNoneGraphButtons.jsp"/>
				</table>
			</td>
		</tr>
	</table>
	
<!--  The block for Common Fields -->
	<jsp:include flush="true" page="/pages/reports/statistics/forms/includes/commonFields.jsp"></jsp:include>

	
<!-- submit button -->
	<table class="defaultTableContentHidden">
		<tr>
			<td align="left">
				<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
			</td>
			<td align="right">
            	<input id="submitButton" type="button" class="button" value="<bean:message key="reports.stats.general.submit"/>">
           		<input id="printReportButton" type="button" class="button" value="<bean:message key="reports.stats.general.printable"/>">
           	</td>
		</tr>
	</table>

</ssl:form> 
