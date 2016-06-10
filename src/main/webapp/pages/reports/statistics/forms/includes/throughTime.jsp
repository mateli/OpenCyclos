<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<br id="tableThroughTimeBr" class="small" style="{display:none;}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0" id="tableThroughTime" style="{display:none;}">
   	<tr>
       	<td class="tdHeaderTable">
       		<bean:message key="reports.stats.throughTheTime"/>
        </td>
   	    <cyclos:help page="statistics#periods"/>
    </tr>
   	<tr>
       	<td colspan="2" align="left" class="tdContentTableLists">
   			<table class="defaultTable">
				<tr>
           			<td width="35%">
           				<fieldset>
           					<legend>
           						<bean:message key="reports.stats.show"/>
           					</legend>
           					<table class="defaultTable">
								<tr>
									<td width="100%">
										<c:forEach var="range" items="${throughTimeRange}">
											<label><html:radio property="query(throughTimeRange)" value="${range}" styleClass="radio throughTimeRange" /> <bean:message key="reports.stats.keydevelopments.throughTime.${range}"/></label>
		   								</c:forEach>
			            			</td>
			            		</tr>
			            	</table>
			            </fieldset>
			        </td>

			        <%-- through months  --%>
			        <td id="tdThroughMonths">
			        	<fieldset>
			        		<legend>
           						<bean:message key="reports.stats.keydevelopments.throughTime.selectMonths"/>
           					</legend>
			        		<table class="defaultTable">
			        			<tr>
			        				<td><bean:message key="global.range.from"/>&nbsp;</td>
			        			    <td>
			        			    	<html:select property="query(initialMonth)" styleClass="InputBoxEnabled">
											<c:forEach var="month" items="${months}">
												<html:option value="${month.value}"><bean:message key="global.month.${month}"/></html:option>
											</c:forEach>
										</html:select>
									</td>
									<td> / </td>
			        			    <td><html:text property="query(initialMonthYear)" styleClass="InputBoxEnabled number small"/></td>
			        			</tr>
			        			<tr>
			        				<td><bean:message key="global.range.to"/>&nbsp;</td>
			        			    <td>
			        			    	<html:select property="query(finalMonth)" styleClass="InputBoxEnabled">
											<c:forEach var="month" items="${months}">
												<html:option value="${month.value}"><bean:message key="global.month.${month}"/></html:option>
											</c:forEach>
										</html:select>
			        			    </td>
			        			    <td> / </td>
			        			    <td><html:text property="query(finalMonthYear)" styleClass="InputBoxEnabled number small"/></td>
			        			</tr>
			        		</table>
			        	</fieldset>
			        </td>

			        <%-- through quarters --%>
			        <td id="tdThroughQuarters" style="{display:none;}">
			        	<fieldset>
				        	<legend>
           						<bean:message key="reports.stats.keydevelopments.throughTime.selectQuarters"/>
           					</legend>
			        		<table class="defaultTable">
			        			<tr>
			        				<td><bean:message key="global.range.from"/>&nbsp;</td>
			        			    <td>
			        			    	<html:select property="query(initialQuarter)" styleClass="InputBoxEnabled">
											<c:forEach var="quarter" items="${quarters}">
												<html:option value="${quarter}"><bean:message key="global.quarter.${quarter}"/></html:option>
											</c:forEach>
										</html:select>
									</td>
									<td> / </td>
			        			    <td><html:text property="query(initialQuarterYear)" styleClass="InputBoxEnabled number small"/></td>
			        			</tr>
			        			<tr>
			        				<td><bean:message key="global.range.to"/>&nbsp;</td>
			        			    <td>
			        			    	<html:select property="query(finalQuarter)" styleClass="InputBoxEnabled">
											<c:forEach var="quarter" items="${quarters}">
												<html:option value="${quarter}"><bean:message key="global.quarter.${quarter}"/></html:option>
											</c:forEach>
										</html:select>
			        			    </td>
			        			    <td> / </td>
			        			    <td><html:text property="query(finalQuarterYear)" styleClass="InputBoxEnabled number small"/></td>
			        			</tr>
			        		</table>
			        	</fieldset>
			        </td>

			        <%-- through years --%>
			        <td id="tdThroughYears" style="{display:none;}">
			        	<fieldset>
			        		<legend>
           						<bean:message key="reports.stats.keydevelopments.throughTime.selectYears"/>
           					</legend>
			        		<table class="defaultTable" > 
			        			<tr>
			        				<td><bean:message key="global.range.from"/>&nbsp;</td>
			        			    <td><html:text property="query(initialYear)" styleClass="InputBoxEnabled number small"/></td>
			        			</tr>
			        			<tr>
			        				<td><bean:message key="global.range.to"/>&nbsp;</td>
			        			    <td><html:text property="query(finalYear)" styleClass="InputBoxEnabled number small"/></td>
			        			</tr>
			        		</table>
			        	</fieldset>
			        </td>
			        
			     </tr>
    		</table>
        </td>
    </tr>
</table>