<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<cyclos:script src="/pages/reports/statistics/forms/statisticsForms.js" />
<cyclos:script src="/pages/reports/statistics/forms/statisticsKeyDevelopmentsForm.js" />
<script>
	var allPaymentsString = "<cyclos:escapeJS><bean:message key="reports.stats.general.allPaymentTypes"/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}"> 
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable">
				<bean:message key="reports.stats.keydevelopments.title"/>
			</td>
			<cyclos:help page="statistics#key_development"/>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableForms">
	            <table class="defaultTable">
					<tr>
						<td colspan="2" align="left" class="tdContentTableForms"> 
							<jsp:include flush="true" page="/pages/reports/statistics/forms/includes/whatToShow.jsp"/>
							<fieldset>
					            <table class="defaultTable">
									<jsp:include flush="true" page="/pages/reports/statistics/forms/includes/tableHeader.jsp"/>
					            	<tr>
					            		<td class="label" width="33%">
	   	        							<bean:message key="reports.stats.keydevelopments.numberOfMembers"/>&nbsp;&nbsp;
				    	        		</td>
				    	        		<td></td>
	   	        						<td align="center">
				    	        			<html:checkbox styleId="numberOfMembersCheckBox" 
	   	        						            	   property="query(numberOfMembers)" 
	   	        						              	   value="true" 
	    	        			            			   styleClass="checkbox itemCheckBox"/>
	   					        		</td>
	   					        		<td style="display:none" class="compare2periods" align="center">
	   					        			<html:checkbox styleId="numberOfMembersGraphCheckBox"
	   					        						   property="query(numberOfMembersGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
	   					        		</td>
	   					        		<td></td>
				            		</tr>
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
	   					        			<html:checkbox styleId="grossProductGraphCheckBox"
	   					        						   property="query(grossProductGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
	   					        		</td>
	   					        		<td style="display:none" class="throughTime" align="center">
											<bean:message key="reports.stats.general.graph.allGraphs"/>
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
	   	        						            	   property="query(numberOfTransactions)" 
	   	        						              	   value="true" 
	    	        			            			   styleClass="checkbox itemCheckBox"/>
	   					        		</td>
	   					        		<td style="display:none" class="compare2periods" align="center">
	   					        			<html:checkbox styleId="numberOfTransactionsGraphCheckBox"
	   					        						   property="query(numberOfTransactionsGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
	   					        		</td>
	   					        		<td style="display:none" class="throughTime" align="center">
	   					        			<html:checkbox styleId="thruTimeGraphCheckBox"
	   					        						   property="query(thruTimeGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
										</td>
	   					        		<td></td>
				            		</tr>
					            	<tr>
					            		<td class="label" width="33%">
	   	        							<bean:message key="reports.stats.keydevelopments.transactionAmount"/>&nbsp;&nbsp;
				    	        		</td>
				    	        		<td></td>
	   	        						<td align="center">
				    	        			<html:checkbox styleId="transactionAmountCheckBox" 
	   	        						            	   property="query(transactionAmount)" 
	   	        						              	   value="true" 
	    	        			            			   styleClass="checkbox itemCheckBox"/>
	   					        		</td>
	   					        		<td style="display:none" class="compare2periods" align="center">
	   					        			<html:checkbox styleId="transactionAmountGraphCheckBox"
	   					        						   property="query(transactionAmountGraph)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
	   					        		</td>
	   					        		<td></td>
				            		</tr>
					            	<tr>
					            		<td class="label" width="33%">
	   	        							<bean:message key="reports.stats.keydevelopments.numberOfAds"/>&nbsp;&nbsp;
				    	        		</td>
				    	        		<td></td>
	   	        						<td align="center">
				    	        			<html:checkbox styleId="numberOfAdsCheckBox" 
	   	        						            	   property="query(numberOfAds)" 
	   	        						              	   value="true" 
	    	        			            			   styleClass="checkbox itemCheckBox"/>
	   					        		</td>
	   					        		<td style="display:none" class="compare2periods" align="center">
	   					        			<html:checkbox styleId="numberOfAdsGraphCheckBox"
	   					        						   property="query(numberOfAdsGrap)"
	   					        						   value="true"
	   					        						   styleClass="checkbox graphCheckBox"
	   					        						   disabled="true"/>
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
