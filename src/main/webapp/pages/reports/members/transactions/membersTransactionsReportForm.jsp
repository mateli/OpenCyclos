<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<cyclos:script src="/pages/reports/members/transactions/membersTransactionsReportForm.js" />
<script>
	var selectPaymentFilter = "<cyclos:escapeJS><bean:message key="reports.members_reports.select_payment_filter"/></cyclos:escapeJS>";
</script>
<ssl:form method="post" action="${formAction}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
    	    <td class="tdHeaderTable"><bean:message key="reports.members.presentation"/></td>
        	<cyclos:help page="reports#member_reports"/> 
    	</tr>
	    <tr>
    	    <td colspan="2" align="left" class="tdContentTableForms">
				
				<table class="defaultTable">
					
					<tr>
						<td class="label" width="35%"><bean:message key="member.memberName"/>&nbsp;</td>
		            	<td align="left"><html:checkbox property="membersTransactionsReport(memberName)" styleClass="checkbox" value="true"/>&nbsp;</td>
		            </tr>
					<tr>
						<td class="label"><bean:message key="member.brokerUsername"/>&nbsp;</td>
		            	<td align="left"><html:checkbox property="membersTransactionsReport(brokerUsername)" styleClass="checkbox" value="true"/>&nbsp;</td>
		            </tr>
					<tr>
						<td class="label"><bean:message key="member.brokerName"/>&nbsp;</td>
		            	<td align="left"><html:checkbox property="membersTransactionsReport(brokerName)" styleClass="checkbox" value="true"/>&nbsp;</td>
		            </tr>
		         	
		         	<tr>
						<td class="label"><bean:message key="reports.members_reports.member_groups"/>&nbsp;</td>
		       			<td>
		       				<cyclos:multiDropDown varName="groupSelect" emptyLabelKey="member.search.selectGroups" name="membersTransactionsReport(memberGroups)" onchange="memberGroupsChanged()">
								<c:forEach var="memberGroup" items="${memberGroups}">
					         		<cyclos:option value="${memberGroup.id}" text="${memberGroup.name}" />
					         	</c:forEach>
					        </cyclos:multiDropDown>
		         		</td>
		         	</tr>
		         	
		         	<tr>
						<td class="label"><bean:message key="reports.members_reports.account_type"/>&nbsp;</td>
		       			<td>
		       				<cyclos:multiDropDown varName="accountTypesSelect" name="membersTransactionsReport(accountTypes)" emptyLabelKey="reports.members_reports.account_type.selectAccountTypes" onchange="accountTypesChanged()">
								<c:forEach var="accountType" items="${accountTypes}">
					         		<cyclos:option value="${accountType.id}" text="${accountType.name}" />
					         	</c:forEach>
					        </cyclos:multiDropDown>
		         		</td>
		         	</tr>
		         	
       				<tr>
       					<td class="label"><bean:message key="paymentFilter.title.list"/></td>
						<td align="left">
							<cyclos:multiDropDown varName="transactionsPFsSelect" name="membersTransactionsReport(transactionsPaymentFilters)" emptyLabelKey="paymentFilter.selectPaymentFilters" />
						</td>
		         	</tr>
		         	
		         	<tr>
						<td class="label"><bean:message key='global.range.from'/>&nbsp;</td>
		       			<td>
		       				<html:text styleClass="date small" property="membersTransactionsReport(period).begin"/>
		         		</td>
		         	</tr>
		         	
		         	<tr>
						<td class="label"><bean:message key='global.range.to'/>&nbsp;</td>
		       			<td>
		       				<html:text styleClass="date small" property="membersTransactionsReport(period).end"/>
		         		</td>
		         	</tr>
		         	
		         	<tr>
	         			<td class="label"><bean:message key="reports.members_reports.outgoing_transactions"/></td>
            			<td align="left"><html:checkbox property="membersTransactionsReport(outgoingTransactions)" styleClass="checkbox" value="true"/></td>
            		</tr>
            		
       				<tr>
       					<td class="label"><bean:message key="reports.members_reports.incoming_transactions"/></td>
       					<td align="left"><html:checkbox property="membersTransactionsReport(incomingTransactions)" styleClass="checkbox" value="true"/></td>
      				</tr>
      				<tr>
       					<td class="label"><bean:message key="reports.members_reports.include_no_traders"/></td>
       					<td align="left"><html:checkbox property="membersTransactionsReport(includeNoTraders)" styleClass="checkbox" value="true"/></td>
         			</tr>
         			<tr>
         				<td class="label"><bean:message key="reports.members_reports.details_level"/></td>
          				<td align="left" nowrap="nowrap">
							<div>
								<c:forEach var="detailsLevel" items="${detailsLevels}">
									<label><html:radio property="membersTransactionsReport(detailsLevel)" value="${detailsLevel}" styleClass="radio detailsLevel" /><bean:message key="reports.members_reports.details_level.${detailsLevel}"/></label>
				   				</c:forEach>
							</div>
						</td>
					</tr>
					
		         </table>
				
               	<table class="defaultTable">
                	<tr>
                    	<td align="right">
                    		<input id="printReportButton" type="button" class="button" value="<bean:message key="reports.members.print_btn"/>">&nbsp;&nbsp;
							<input id="downloadReportButton" type="button" class="button" value="<bean:message key="reports.members.download_btn"/>">&nbsp;&nbsp;
                    	</td>
                    </tr>
				</table>
				
			</td>
		</tr>
	</table>
</ssl:form>