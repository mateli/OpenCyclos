<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://cewolf.sourceforge.net/tags/cewolf" prefix="cewolf" %>
<c:if test="${dataTable.showAppliedFilters}">
	<tr>
		<td colspan="2" align="left" class="tdContentTableLists">
			<fieldset>
	        	<legend>
	           		<bean:message key="reports.stats.general.appliedFilters"/>
	           	</legend>
		   		<table class="defaultTable">
		   			<c:set var="filterCount" value="${dataTable.filterCount}"/>
					<tr>
						<c:forEach var="filterItem" items="${dataTable.filtersUsed}">
							<td width="${100 / filterCount}%">
								<table class="defaultTable">
						   			<!-- header of the table -->
									<tr>
										<td>
				   							<b><bean:message key="${filterItem.headerKey}"/></b>
										</td>
									</tr>
									<!-- results row(s) -->
									<c:forEach var="filterElement" items="${filterItem.values}">  
			                			<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
											<td>
												${filterElement}
											</td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</c:forEach>
					</tr>
				</table>
		    </fieldset>
		</td>
	</tr>
</c:if>