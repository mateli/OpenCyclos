<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://cewolf.sourceforge.net/tags/cewolf" prefix="cewolf" %>
<cyclos:script src="/pages/scripts/overlib.js" />
<jsp:useBean id="chartPostProcessorImpl" class="nl.strohalm.cyclos.controls.reports.statistics.graphs.ChartPostProcessorImpl" scope="request"/>
<c:set var="type" value="${cyclos:name(statisticsType)}"/>

<c:choose>
	<c:when test="${type == 'KEY_DEVELOPMENTS'}">
		<c:set var="titleKey" value="reports.stats.keydevelopments.title"/>
	</c:when>
	<c:when test="${type == 'MEMBER_ACTIVITIES'}">
		<c:set var="titleKey" value="reports.stats.activity.title"/>
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="reports.stats.finances.title"/>		   
	</c:otherwise>
</c:choose>

<div class="printTitle"><bean:message key="${titleKey}"/></div>

<c:forEach var="dataTable" items="${dataList}" varStatus="listLoop">  
	<c:set var="dataTable" scope="request" value="${dataTable}"/>
	<c:if test="${dataTable.showTable}">
		<!--  render tables -->
		<table width="100%" cellspacing="0" >
    		<tr>
        		<td class="printLabel" style="text-align:left">
        			<bean:message key="${dataTable.baseKey}.title"/>
		        </td>
		    </tr>
    		<tr>
        		<td colspan="2" align="left" >
					<table width="100%" class="printBorder">
            			<c:if test="${dataTable.subTitle!=''}">
          					<tr>
								<td class="printLabel" colspan="${dataTable.tableColumnCount}">
									${dataTable.subTitle}
								</td>
    	      				</tr>
        	  			</c:if>
						<tr>
							<td class="printLabel"></td>
							<c:forEach var="colHeaders" items="${dataTable.columnHeaders}" varStatus="headerCounter">
								<td width="${(70.0-(40.0/dataTable.tableColumnCount))/(dataTable.tableColumnCount-1.0)}%" class="printLabel" style="text-align:center">
									${colHeaders}${dataTable.columnSubHeaders[headerCounter.index]}
								</td>
							</c:forEach>
						</tr>
						<!--  render table rows -->
						<c:forEach var="rowData" items="${dataTable.tableCells}" varStatus="rowLoop">
	                		<tr >
								<td>${dataTable.rowHeaders[rowLoop.index]}	</td>
								<c:forEach var="cellData" items="${rowData}" >
									<td align="center">
										<cyclos:format number="${cellData}" />
									</td>
								</c:forEach>
		            	    </tr>
	    	            </c:forEach>
					</table>
	    	    </td>
	    	</tr>
	    	<!-- The block with filter info -->
   			<c:if test="${!dataTable.showGraph}">
	   			<tr><td colspan="2">&nbsp;</td></tr>
			    <jsp:include flush="true" page="/pages/reports/statistics/usedFilters.jsp"/>
			</c:if>
		</table>
		
	</c:if>
	<!--  Graph section -->
	<c:if test="${dataTable.showGraph}">
		<!-- Box header  -->
		<table cellspacing="0" cellpadding="0">
	    	<tr>
    	    	<td class="printLabel" style="text-align:left">
        			<bean:message key="${dataTable.baseKey}.title"/> - <bean:message key="reports.stats.general.graph"/>
	        	</td>
		    </tr>
		    <!--  multi-graph? -->
			<c:choose>
				<c:when test="${dataTable.multiGraph}">
					<!--  multi graph -->
					<tr>
						<td colspan="2" align="left">
							<h3>${dataTable.title}</h3>
						</td>
					</tr>
					<c:forEach var="combinedProducer" items="${dataTable.multiGraphProducers}" varStatus="combinationCounter">
						<!--  define the subgraph -->
						<cewolf:chart 
								id="chart${listLoop.index}" 
					    		type="${dataTable.graphTypeValue}"
					    		showlegend="false"
								xaxislabel="${combinedProducer.x_axis}"
					    		yaxislabel="${combinedProducer.y_axis}">
							<cewolf:data>
								<cewolf:producer id="combinedProducer" />
					   		</cewolf:data>
		 					<cewolf:chartpostprocessor id="chartPostProcessorImpl" /> 
						</cewolf:chart>
						<!-- render the subgraph of the multi graph -->
				    	<tr> 
					        <td >
								<cewolf:img chartid="chart${listLoop.index}" renderer="/cewolf" width="570" height="210" >
									<cewolf:map tooltipgeneratorid="dataTable"/> 
								</cewolf:img>
	    				    </td>
					    </tr>
					</c:forEach>	
				</c:when>
				<c:otherwise>
					<!--  not-multi graph, definition -->
					<cewolf:chart
    						id="chart${listLoop.index}"
	    					title="${dataTable.title}"
			    			type="${dataTable.graphTypeValue}"
			    			showlegend="${dataTable.showLegend}"
				    		yaxislabel="${dataTable.y_axis}"
				    		xaxislabel="${dataTable.x_axis}">
						<cewolf:data>
							<cewolf:producer id="dataTable"/>
						</cewolf:data>
 						<cewolf:chartpostprocessor id="chartPostProcessorImpl" > 
		 					<cewolf:param name="subtitle" value="${dataTable.subTitle}"/>
	 					</cewolf:chartpostprocessor>	
					</cewolf:chart>
					<!-- render the not-multi graph -->
			    	<tr> 
				        <td class="printData">
							<cewolf:img chartid="chart${listLoop.index}" renderer="/cewolf" width="570" height="300" >
								<cewolf:map tooltipgeneratorid="dataTable"/> 
							</cewolf:img>
	    			    </td>
				    </tr>
				</c:otherwise>
			</c:choose>
		<!--  Rest of table  -->	
		<!-- The block with filter info -->
		    <jsp:include flush="true" page="/pages/reports/statistics/usedFilters.jsp"/>
		</table>
		
	</c:if>
	<!--  in case nor graphs, nor tables are shown -->
	<c:if test="${!dataTable.showGraph && !dataTable.showTable}">
		<table cellspacing="0" cellpadding="0">
    		<tr>
        		<td class="printLabel">
        			<bean:message key="${dataTable.baseKey}.title"/>
		        </td>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="printData">
		        	<table class="printBorder" >
		        		<tr>
		        			<td align="center">
		        				<font color="red"> 
		    	    			<bean:message key="reports.stats.general.nodata"/>
		    	    			</font>
	        				</td>
	        			</tr>
	        		</table>
	        	</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${!listLoop.last}">
		
	</c:if>
</c:forEach>
