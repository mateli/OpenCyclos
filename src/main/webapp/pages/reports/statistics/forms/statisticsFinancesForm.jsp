<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<cyclos:script src="/pages/reports/statistics/forms/statisticsForms.js" />
<cyclos:script src="/pages/reports/statistics/forms/statisticsFinancesForm.js" />
		
<ssl:form method="post" action="${formAction}"> 
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable">
				<bean:message key="reports.stats.finances.title"/>
			</td>
			<cyclos:help page="statistics#finances"/>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableLists">
				<table class="defaultTable">
					<tr>
						<td colspan="2" align="left" class="tdContentTableForms">
							<jsp:include flush="true" page="/pages/reports/statistics/forms/includes/whatToShow.jsp"/>
							<fieldset>
				            	<table class="defaultTable">
									<jsp:include flush="true" page="/pages/reports/statistics/forms/includes/tableHeader.jsp"/>
	        			        	<tr id="singlePeriodOverviewTR" style="display:none">
	            						<td class="label" width="33%">
	            							<bean:message key="reports.stats.finances.overview"/>&nbsp;&nbsp;
	            						</td>
				            			<td></td>
    	    			    			<td align="center">
											<html:checkbox styleId="overviewCheckBox"
														   property="query(overview)" 
														   value="true" 
														   styleClass="checkbox itemCheckBox"/>
    	    			    			</td>
    	    			    			<td align="center">
											<html:checkbox styleId="overviewGraphCheckBox" 
											               property="query(overviewGraph)" 
											               value="true" 
											               styleClass="checkbox graphCheckBox"
											               disabled="true"/>
    	    			    			</td>
				            			<td></td>
				            		</tr>
	                				<tr>
				            			<td class="label" width="33%">
				            				<bean:message key="reports.stats.finances.income"/>&nbsp;&nbsp;
				            			</td>
				            			<td></td>

    	    			    			<td align="center">
											<html:checkbox styleId="incomeCheckBox" 
														   property="query(income)" 
														   value="true" 
														   styleClass="checkbox itemCheckBox"/>
    	    			    			</td>
    	    			    			<td align="center">
											<html:checkbox styleId="incomeGraphCheckBox" 
														   property="query(incomeGraph)" 
														   value="true" 
														   styleClass="checkbox graphCheckBox"/>
    	    			    			</td>
    					        		<td></td>
					            	</tr>
	        			        	<tr>
	    	        					<td class="label" width="33%">
	    	        						<bean:message key="reports.stats.finances.expenditure"/>&nbsp;&nbsp;
	    	        					</td>
				            			<td></td>
    	    			    			<td align="center">
											<html:checkbox styleId="expenditureCheckBox" 
														   property="query(expenditure)" 
														   value="true" 
														   styleClass="checkbox itemCheckBox"/>
    	    			    			</td>
    	    			    			<td align="center">
											<html:checkbox styleId="expenditureGraphCheckBox" 
														   property="query(expenditureGraph)" 
														   value="true" 
														   styleClass="checkbox graphCheckBox"/>
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
	<jsp:include flush="true" page="/pages/reports/statistics/forms/includes/commonFields.jsp"/>

		
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