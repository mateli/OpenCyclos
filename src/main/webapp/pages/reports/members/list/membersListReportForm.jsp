<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<cyclos:script src="/pages/reports/members/list/membersListReportForm.js" />

<script>
	var historyDateEmpty = "<cyclos:escapeJS><bean:message key="reports.members.date.empty"/></cyclos:escapeJS>";
	JST_DEFAULT_DATE_MASK_VALIDATION_MESSAGE = "<cyclos:escapeJS><bean:message key="errors.dateFormat" arg0="${fn:toLowerCase(localSettings.datePattern.value)} HH:mm:ss"/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
   	    <td class="tdHeaderTable"><bean:message key="reports.members.presentation.list"/></td>
       	<cyclos:help page="reports#member_lists"/> 
   	</tr>
    <tr>
   	    <td colspan="2" align="left" class="tdContentTableForms">
				<table class="defaultTable">
	       			<tr>
	       				<td align="left">
	       					<html:radio styleClass="radio periodType" property="membersListReport(periodType)" value="PERIOD_CURRENT">
	       						<bean:message key="reports.members.date.current"/>
	       					</html:radio>
							<html:radio styleId="periodTypeHistory" styleClass="radio periodType" property="membersListReport(periodType)" value="PERIOD_HISTORY">
								<bean:message key="reports.members.date.history"/>
							</html:radio>
							<html:text styleId="periodDate" property="membersListReport(period)" styleClass="dateTime InputBoxDisabled" disabled="true"></html:text>
							<input type="text" style="display:none"></input>
	       				</td>
	       			</tr>
				</table>
				<fieldset>
					<legend><bean:message key="reports.members.broker"/></legend>
					<table class="defaultTable">
						<tr>
							<td width="24%" class="label"><bean:message key="member.username"/></td>
							<td>
								<html:hidden property="membersListReport(brokerId)" styleId="brokerId"/>
								<input id="brokerUsername" class="large" size="20">
								<div id="brokersByUsername" class="autoComplete"></div>
							</td>
						</tr>
						<tr>
							<td class="label"><bean:message key="member.name"/></td>
							<td>
								<input id="brokerName" class="large" size="40">
								<div id="brokersByName" class="autoComplete"></div>
							</td>
						</tr>
					</table>
				</fieldset>
              	<fieldset>
              		<legend><bean:message key="reports.members.members"/></legend>
             		<table class="defaultTable">
	               	<tr>
	            		<td align="left"><html:checkbox property="membersListReport(memberName)" styleClass="checkbox" styleId="memberName" value="true"/>&nbsp;<bean:message key="member.memberName"/>&nbsp;</td>
	               	</tr>
	               	<tr>
	            		<td align="left"><html:checkbox property="membersListReport(brokerUsername)" styleClass="checkbox" value="true"/>&nbsp;<bean:message key="member.brokerUsername"/>&nbsp;</td>
	               	</tr>
	               	<tr>
	            		<td align="left"><html:checkbox property="membersListReport(brokerName)" styleClass="checkbox" value="true"/>&nbsp;<bean:message key="member.brokerName"/>&nbsp;</td>
	               	</tr>
	               	<tr>
	         			<td align="left">
			         		<table>
			         			<tr>		
				         			<td><bean:message key="reports.members.permission_groups"/>&nbsp;</td>
				         			<td>
				         				<cyclos:multiDropDown name="membersListReport(groups)" emptyLabelKey="member.search.allGroups">
				         					<c:forEach var="group" items="${groups}">
				         						<cyclos:option value="${group.id}" text="${group.name}" />
				         					</c:forEach>
				           				</cyclos:multiDropDown>
				           			</td>
			           			</tr>
			           		</table>
	           			</td>
	       			</tr>
       			</table>
      			</fieldset>
         		
         		<fieldset>
         			<legend><bean:message key="reports.members.ads"/></legend>
          		<table class="defaultTable">
          			<tr>
	            		<td align="left" colspan="2"><html:checkbox property="membersListReport(activeAds)" styleClass="checkbox" value="true"/>&nbsp;<bean:message key="reports.members.ads.active_ads"/>&nbsp;</td>
                	</tr>
                	<tr>
	            		<td align="left" colspan="2"><html:checkbox property="membersListReport(expiredAds)" styleClass="checkbox" value="true"/>&nbsp;<bean:message key="reports.members.ads.expired_ads"/>&nbsp;</td>
                	</tr>
                	<tr>
	            		<td align="left" colspan="2"><html:checkbox property="membersListReport(permanentAds)" styleClass="checkbox" value="true"/>&nbsp;<bean:message key="reports.members.ads.permanent_ads"/>&nbsp;</td>
                	</tr>
                	<tr>
	            		<td align="left" colspan="2"><html:checkbox property="membersListReport(scheduledAds)" styleClass="checkbox" value="true"/>&nbsp;<bean:message key="reports.members.ads.scheduled_ads"/>&nbsp;</td>
                	</tr>
                </table>
            </fieldset>
               	
               <fieldset>
               	<legend><bean:message key="reports.members.references"/></legend>
                <table class="defaultTable">
                	<tr>
	            		<td align="left" colspan="2"><html:checkbox property="membersListReport(givenReferences)" styleClass="checkbox" value="true"/>&nbsp;<bean:message key="reference.title.given.my"/>&nbsp;</td>
                	</tr>
                	<tr>
	            		<td align="left" colspan="2"><html:checkbox property="membersListReport(receivedReferences)" styleClass="checkbox" value="true"/>&nbsp;<bean:message key="reference.title.received.my"/>&nbsp;</td>
                	</tr>
                </table>
            </fieldset>
               	
               <fieldset>
               	<legend><bean:message key="reports.members.accounts"/></legend>
				<table class="defaultTable">
                	<tr>
	            		<td align="left" colspan="2"><html:checkbox styleId="accountsCredits" property="membersListReport(accountsCredits)" styleClass="checkbox" value="true"/>&nbsp;<bean:message key="reports.members.accounts.credits"/>&nbsp;</td>
                	</tr>
                	<tr>
	            		<td align="left" colspan="2"><html:checkbox styleId="accountsUpperCredits" property="membersListReport(accountsUpperCredits)" styleClass="checkbox" value="true"/>&nbsp;<bean:message key="reports.members.accounts.upper_credits"/>&nbsp;</td>
                	</tr>
                	<tr>
	            		<td align="left" colspan="2"><html:checkbox property="membersListReport(accountsBalances)" styleClass="checkbox" value="true"/>&nbsp;<bean:message key="reports.members.accounts.balances"/>&nbsp;</td>
                	</tr>
                </table>
			</fieldset>
               
			<table class="defaultTable">
               	<tr>
                   	<td align="left">
                   		<input id="selectAllButton" type="button" class="button" value="<bean:message key="global.selectAll"/>">&nbsp;&nbsp;
						<input id="selectNoneButton" type="button" class="button" value="<bean:message key="global.selectNone"/>">&nbsp;&nbsp;
                   	</td>
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