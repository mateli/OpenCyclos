<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<cyclos:script src="/pages/reports/statistics/forms/statisticsForms.js" />
<cyclos:script src="/pages/reports/statistics/forms/statisticsTaxesForm.js" />
		
<ssl:form method="post" action="${formAction}"> 
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable">
				<bean:message key="reports.stats.taxes.title"/>
			</td>
			<cyclos:help page="statistics#taxes_fees"/>
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
									
	                				<tr id="volumeTR" style="display:none">
				            			<td class="label" width="33%">
				            				<bean:message key="reports.stats.taxes.volume"/>&nbsp;&nbsp;
				            			</td>
				            			<td></td>

    	    			    			<td align="center">
											<html:checkbox styleId="volumeCheckBox" 
														   property="query(volume)" 
														   value="true" 
														   styleClass="checkbox itemCheckBox"/>
    	    			    			</td>
    	    			    			<td align="center">
											<html:checkbox styleId="volumeGraphCheckBox" 
														   property="query(volumeGraph)" 
														   value="true" 
														   styleClass="checkbox graphCheckBox"/>
    	    			    			</td>
    					        		<td></td>
					            	</tr>
	                				<tr id="numberOfMembersTR" style="display:none">
				            			<td class="label" width="33%">
				            				<bean:message key="reports.stats.taxes.numberOfMembers"/>&nbsp;&nbsp;
				            			</td>
				            			<td></td>

    	    			    			<td align="center">
											<html:checkbox styleId="numberOfMembersCheckBox" 
														   property="query(numberOfMembers)" 
														   value="true" 
														   styleClass="checkbox itemCheckBox"/>
    	    			    			</td>
    	    			    			<td align="center">
											<html:checkbox styleId="numberOfMembersGraphCheckBox" 
														   property="query(numberOfMembersGraph)" 
														   value="true" 
														   styleClass="checkbox graphCheckBox"/>
    	    			    			</td>
    					        		<td></td>
					            	</tr>
	                				<tr>
				            			<td class="label" width="33%">
				            				<bean:message key="reports.stats.taxes.medianPerMember"/>&nbsp;&nbsp;
				            			</td>
				            			<td></td>

    	    			    			<td align="center">
											<html:checkbox styleId="medianPerMemberCheckBox" 
														   property="query(medianPerMember)" 
														   value="true" 
														   styleClass="checkbox itemCheckBox"/>
    	    			    			</td>
    	    			    			<td align="center">
											<html:checkbox styleId="medianPerMemberGraphCheckBox" 
														   property="query(medianPerMemberGraph)" 
														   value="true" 
														   styleClass="checkbox graphCheckBox"/>
    	    			    			</td>
    					        		<td></td>
					            	</tr>
	                				<tr id="maxMemberTR" style="display:none">
				            			<td class="label" width="33%">
				            				<bean:message key="reports.stats.taxes.maxMember"/>&nbsp;&nbsp;
				            			</td>
				            			<td></td>

    	    			    			<td align="center">
											<html:checkbox styleId="maxMemberCheckBox" 
														   property="query(maxMember)" 
														   value="true" 
														   styleClass="checkbox itemCheckBox"/>
    	    			    			</td>
    	    			    			<td align="center">
											<html:checkbox styleId="maxMemberGraphCheckBox" 
														   property="query(maxMemberGraph)" 
														   value="true" 
														   styleClass="checkbox graphCheckBox"/>
    	    			    			</td>
    					        		<td></td>
					            	</tr>
	                				<tr>
				            			<td class="label" width="33%">
				            				<bean:message key="reports.stats.taxes.relativeToGrossProduct"/>&nbsp;&nbsp;
				            			</td>
				            			<td></td>

    	    			    			<td align="center">
											<html:checkbox styleId="relativeToGrossProductCheckBox" 
														   property="query(relativeToGrossProduct)" 
														   value="true" 
														   styleClass="checkbox itemCheckBox"/>
    	    			    			</td>
    	    			    			<td align="center">
											<html:checkbox styleId="relativeToGrossProductGraphCheckBox" 
														   property="query(relativeToGrossProductGraph)" 
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
   		<!-- The block with the extra checkboxes for paid / not paid -->
		<tr>
			<td colspan="2">
	            <table class="defaultTable">
	            	<tr>
	            		<td>&nbsp;</td>
	            	</tr>
	            </table>
	        </td>
	    </tr>
		<tr>
			<td colspan="2" class="tdContentTableLists">
				<table class="defaultTable">
					<tr>
						<td colspan="2" align="left" class="tdContentTableForms">
						<fieldset>
							<legend><bean:message key="reports.stats.taxes.paid.legend"/></legend>
				            <table class="defaultTable">
				            	<tr>
				            		<td colspan="2" align="center">
										<c:forEach var="paidOrNotValue" items="${paidOrNot}">
											<label>
												<html:radio property="query(paidOrNot)" value="${paidOrNotValue}" styleClass="radio paid" />
												<bean:message key="reports.stats.taxes.paid.${paidOrNotValue.value}"/>
												&nbsp;&nbsp;&nbsp;&nbsp;
											</label>
				   						</c:forEach>
				   					</td>
				   				</tr>
				   				<tr>
				   					<td colspan="2">
				   						&nbsp;
				   					</td>
				   				</tr>
				   				<tr>
				   					<td class="label">
				   						<bean:message key="reports.stats.taxes.paid.notPaidLimit"/>&nbsp;&nbsp;&nbsp;
				   					</td>
				   					<td>
				   						<html:text property="query(notPaidLimit)" styleClass="tiny number InputBoxEnabled"/>
				   						<bean:message key="global.timePeriod.DAYS"/>
				   					</td>
				   				</tr>
							</table>
						</fieldset>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
<!--  The block for Common Fields -->
	<jsp:include flush="true" page="/pages/reports/statistics/forms/includes/commonFields.jsp"/>

	<br class="small" >	
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