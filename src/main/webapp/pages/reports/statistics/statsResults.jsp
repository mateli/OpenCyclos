<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://cewolf.sourceforge.net/tags/cewolf" prefix="cewolf" %>
<cyclos:script src="/pages/scripts/overlib.js" />
<jsp:useBean id="chartPostProcessorImpl" class="nl.strohalm.cyclos.controls.reports.statistics.graphs.ChartPostProcessorImpl" scope="request"/>

<c:forEach var="dataTable" items="${dataList}" varStatus="listLoop">  
	<c:set var="dataTable" scope="request" value="${dataTable}"/>
	<c:if test="${dataTable.showTable}">
		<!--  render tables -->
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    		<tr>
        		<td class="tdHeaderTable" width="60%" nowrap="nowrap">
        			<bean:message key="${dataTable.baseKey}.title"/>
		        </td>
    		    <cyclos:help page="${dataTable.helpFile}${'#'}${dataTable.helpAnchor}"/>
		    </tr>
    		<tr>
        		<td colspan="2" align="left" class="tdContentTableLists">
            		<table class="defaultTable">
          				<c:if test="${dataTable.subTitle!=''}">
          					<tr>
								<td class="tdHeaderContents" colspan="${dataTable.tableColumnCount}">
									${dataTable.subTitle}
								</td>
    	      				</tr>
        	  			</c:if>
						<tr>
							<td class="tdHeaderContents"></td>
							<c:forEach var="colHeaders" items="${dataTable.columnHeaders}" varStatus="headerCounter">
								<td width="${(70.0-(40.0/dataTable.tableColumnCount))/(dataTable.tableColumnCount-1.0)}%" class="tdHeaderContents">
									${colHeaders}${dataTable.columnSubHeaders[headerCounter.index]}
								</td>
							</c:forEach>
						</tr>
						<!--  render table rows -->
						<c:forEach var="rowData" items="${dataTable.tableCells}" varStatus="rowLoop">
	                		<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
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
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    	<tr>
    	    	<td class="tdHeaderTable" width="60%" nowrap="nowrap">
        			<bean:message key="${dataTable.baseKey}.title"/> - <bean:message key="reports.stats.general.graph"/>
	        	</td>
	        	<c:choose> 
  					<c:when test="${dataTable.showTable}" > 
	    	    		<cyclos:help page="statistics#graphs"/>
  					</c:when> 
		  			<c:otherwise> 
		    		    <cyclos:help page="${dataTable.helpFile}${'#'}${dataTable.helpAnchor}"/>
					</c:otherwise> 
				</c:choose>
		    </tr>
		    <!--  multi-graph? -->
			<c:choose>
				<c:when test="${dataTable.multiGraph}">
					<!--  multi graph -->
					<tr>
						<td class="tdContentTableForms" colspan="2" align="center">
							<h3>${dataTable.title}</h3>
						</td>
					</tr>
					<c:forEach var="combinedProducer" items="${dataTable.multiGraphProducers}" varStatus="combinationCounter">
						<!--  define the subgraph -->
						<cewolf:chart 
								id="chart${listLoop.index}.${combinationCounter.index}" 
					    		type="${dataTable.graphTypeValue}"
					    		showlegend="false"
								xaxislabel="${combinedProducer.x_axis}"
					    		yaxislabel="${combinedProducer.y_axis}">
							<cewolf:data>
								<cewolf:producer id="combinedProducer" />
					   		</cewolf:data>
     						<cewolf:colorpaint color="#fffff7"/>					   		
		 					<cewolf:chartpostprocessor id="chartPostProcessorImpl" /> 
						</cewolf:chart>
						<!-- render the subgraph of the multi graph -->
				    	<tr> 
					        <td class="tdContentTableForms" colspan="2" align="center">
								<cewolf:img chartid="chart${listLoop.index}.${combinationCounter.index}" renderer="/cewolf" width="570" height="210" >
									<cewolf:map tooltipgeneratorid="combinedProducer"/> 
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
						<cewolf:colorpaint color="#fffff7"/>
 						<cewolf:chartpostprocessor id="chartPostProcessorImpl" > 
		 					<cewolf:param name="subtitle" value="${dataTable.subTitle}"/>
							<cewolf:param name="domainMarkers" value="${dataTable.domainMarkers}" />
	 					</cewolf:chartpostprocessor>	
					</cewolf:chart>
					<!-- render the not-multi graph -->
			    	<tr> 
				        <td class="tdContentTableForms" colspan="2">
							<cewolf:img chartid="chart${listLoop.index}" renderer="/cewolf" width="570" height="${dataTable.height}" >
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
		<c:if test="${!listLoop.last}">
			
		</c:if>
		
	</c:if>
	<!--  in case nor graphs, nor tables are shown -->
	<c:if test="${!dataTable.showGraph && !dataTable.showTable}">
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    		<tr>
        		<td class="tdHeaderTable">
        			<bean:message key="${dataTable.baseKey}.title"/>
		        </td>
    		    <cyclos:help page="statistics#nodata"/>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableForms">
		        	<table class="defaultTable">
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
		<c:if test="${!listLoop.last}">
			
		</c:if>
		
	</c:if>
</c:forEach>
