<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
    	<td class="tdHeaderTable">
    		<bean:message key="reports.current.presentation.result${not empty historyTime ? '.at' : ''  }"/>
    		<cyclos:format dateTime="${historyTime}"/>
   		</td>
        <cyclos:help page="reports#current_result"/>
   	</tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
			<table class="defaultTable">
				<c:if test="${dto.memberGroupInformation}">
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td align="right"><b><bean:message key="reports.current.member_group_information"/></b></td>
	               		<td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>

	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
    	        		<td  width="60%" align="right">
        	    			<bean:message key="reports.current.n_enabled"/>:
            			</td>
            			<td  width="25%" align="right">
            				<b><cyclos:format number="${report.numberActiveMembers}"/></b>
	            		</td>
    	        		<td></td>
        	       	</tr>
					
					<c:forEach var="entry" items="${report.groupMemberCount}">
                    	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
  							<c:set var="groupName" value="${entry.key.name}"/>
  							<c:set var="count" value="${entry.value}"/>
        	        		<td width="60%" align="right">
    							<bean:message key="reports.current.member_count_on_group" arg0="${groupName}"/>
                			</td>
                			<td width="25%" align="right">
                				<b><cyclos:format number="${count}"/></b>
    	                   	</td>
        		       		<td></td>
                	   	</tr>
            	   	</c:forEach>
        	    </c:if>
        	    
				<c:if test="${dto.ads}">
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle> >
	               		<td align="right"><b><bean:message key="reports.current.ads_information"/></b></td>
	               		<td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>

	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
    	        		<td width="60%" align="right">
							<bean:message key="reports.current.n_adsaccount"/>:
            			</td>
            			<td width="25%" align="right">
            				<b><cyclos:format number="${report.adReportVO.numberActiveMembersWithAds}"/></b>
						</td>
    	        		<td></td>
        	       	</tr>

	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
    	        		<td  width="60%" align="right">
							<bean:message key="reports.current.n_actvads"/>:
            			</td>
            			<td  width="25%" align="right">
            				<b><cyclos:format number="${report.adReportVO.numberActiveAdvertisements}"/></b>
						</td>
    	        		<td></td>
        	       	</tr>

	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	   	         		<td width="60%" align="right">
							<bean:message key="reports.current.n_expads"/>:
        	    		</td>
            			<td width="25%" align="right">
            				<b><cyclos:format number="${report.adReportVO.numberExpiredAdvertisements}"/></b>
						</td>
	            		<td></td>
    	           	</tr>

	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	   	         		<td width="60%" align="right">
							<bean:message key="reports.current.n_schedads"/>:
        	    		</td>
            			<td width="25%" align="right">
            				<b><cyclos:format number="${report.adReportVO.numberScheduledAdvertisements}"/></b>
						</td>
	            		<td></td>
    	           	</tr>
    	           	
    	           	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	   	         		<td width="60%" align="right">
							<bean:message key="reports.current.n_permads"/>:
        	    		</td>
            			<td width="25%" align="right">
            				<b><cyclos:format number="${report.adReportVO.numberPermanentAdvertisements}"/></b>
						</td>
	            		<td></td>
    	           	</tr>
        	    </c:if>

				<c:if test="${dto.systemAccountInformation}">
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td align="right"><b><bean:message key="reports.current.system_account"/></b></td>
	               		<td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>
	               	<c:forEach items="${report.systemAccountTypesBalance}" var="entry">
	               		<c:set var="account" value="${entry.key}" />
	               		<c:set var="balance" value="${entry.value}" />
    	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
        	        		<td width="60%" align="right">
    							<bean:message key="reports.current.balance_account" arg0="${account.name}"/>:
                			</td>
                			<td width="25%" align="right">
                				<b><cyclos:format number="${balance}" unitsPattern="${account.currency.pattern}"/></b>
    						</td>
        	        		<td></td>
            	       	</tr>
        	       	</c:forEach>
        	    </c:if>

				<c:if test="${dto.memberAccountInformation}">
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td align="right"><b><bean:message key="reports.current.member_account"/></b></td>
	               		<td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>
	               	<c:forEach items="${report.memberAccountTypesBalance}" var="entry">
	               		<c:set var="account" value="${entry.key}" />
	               		<c:set var="balance" value="${entry.value}" />
    	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
        	        		<td width="60%" align="right">
    							<bean:message key="reports.current.balance_account" arg0="${account.name}"/>:
                			</td>
                			<td width="25%" align="right">
                				<b><cyclos:format number="${balance}" unitsPattern="${account.currency.pattern}"/></b>
    						</td>
        	        		<td></td>
            	       	</tr>
        	       	</c:forEach>
        	    </c:if>

				<c:if test="${dto.invoices}">
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td align="right"><b><bean:message key="reports.current.invoices"/></b></td>
	               		<td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>
	               	<c:forEach var="currencyEntry" items="${report.invoicesSummaries}">
	               		<c:set var="currency" value="${currencyEntry.key}" />

						<c:if test="${not singleCurrency}">
			               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
			               		<td align="right"><b>${currency.name}</b></td>
			               		<td>&nbsp;</td><td>&nbsp;</td>
			               	</tr>
			            </c:if>
	               		
		               	<c:forEach var="entry" items="${currencyEntry.value}">
		               		<c:set var="summary" value="${entry.value}"/>
			               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
		    	        		<td width="60%" align="right">
									<bean:message key="reports.current.invoices.${entry.key}.count"/>:
		            			</td>
		            			<td width="25%" align="right">
		            				<b><cyclos:format number="${summary.count}"/></b>
								</td>
		    	        		<td></td>
		        	       	</tr>
			               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
		    	        		<td width="60%" align="right">
									<bean:message key="reports.current.invoices.${entry.key}.amount"/>:
		            			</td>
		            			<td width="25%" align="right">
		            				<b><cyclos:format number="${summary.amount}" unitsPattern="${currency.pattern}"/></b>
								</td>
		    	        		<td></td>
		        	       	</tr>
	        	       	</c:forEach>
	        	    </c:forEach>
				</c:if>

				<c:if test="${dto.loans}">
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td align="right"><b><bean:message key="reports.current.loans"/></b></td>
	               		<td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>
	               	<c:forEach var="entry" items="${report.openLoansSummary}">
	               		<c:set var="currency" value="${entry.key}" />
	               		<c:set var="loans" value="${entry.value}" />

						<c:if test="${not singleCurrency}">
			               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
			               		<td align="right"><b>${currency.name}</b></td>
			               		<td>&nbsp;</td><td>&nbsp;</td>
			               	</tr>
			            </c:if>
	
		               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	    	        		<td width="60%" align="right">
								<bean:message key="reports.current.number_open_loans"/>:
	            			</td>
	            			<td width="25%" align="right">
	            				<b><cyclos:format number="${loans.count}"/></b>
							</td>
	    	        		<td></td>
	        	       	</tr>
		               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	    	        		<td width="60%" align="right">
								<bean:message key="reports.current.remaining_open_loans"/>:
	            			</td>
	            			<td width="25%" align="right">
	            				<b><cyclos:format number="${loans.amount}" unitsPattern="${currency.pattern}"/></b>
							</td>
	    	        		<td></td>
	        	       	</tr>
	        	    </c:forEach>
				</c:if>
				
        	    <c:if test="${dto.references}">
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td align="right"><b><bean:message key="reports.current.references"/></b></td>
	               		<td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>

					<c:set var="totalReferences" value="${0}"/>
					<c:forEach var="reference" items="${report.givenReferences}">
		               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	    	        		<td width="70%" align="right">
								<bean:message key="reference.level.${reference.key}"/>:
	            			</td>
	            			<td width="25%" align="right">
	            				<b><cyclos:format number="${reference.value}"/></b>
							</td>
	    	        		<td></td>
	        	       	</tr>
	        	       	<c:set var="totalReferences" value="${totalReferences + reference.value}"/>
        	       	</c:forEach>

	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
    	        		<td  width="70%" align="right">
							<bean:message key="reports.current.references.total"/>:
            			</td>
            			<td  width="25%" align="right">
            				<b><cyclos:format number="${totalReferences}"/></b>
						</td>
    	        		<td></td>
        	       	</tr>
        	    </c:if>
        	    
				<c:if test="${dto.nothing}">
					<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               		<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	               	</tr>
	               	<tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
    	        		<td  colspan="3" align="center" >
							<bean:message key="reports.nocheckboxes"/>
            			</td>
        	       	</tr>
        	    </c:if>
        	    
        	    <tr class=<t:toggle>"ClassColor1"|"ClassColor2"</t:toggle>>
	               	<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	            </tr>
                
			</table>
		</td>
	</tr>
</table>



<table class="defaultTableContentHidden">
	<tr>
		<td align="left">
			<input type="button" name="back" class="button" 
				onclick="self.location='<c:url value="/do/admin/reportsCurrentState"/>'" 
				value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>
