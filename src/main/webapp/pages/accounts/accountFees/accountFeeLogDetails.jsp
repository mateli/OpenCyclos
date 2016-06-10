<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/accounts/accountFees/accountFeeLogDetails.js" />
<script>
	<c:if test="${isRunning}">
		setTimeout("document.forms[0].submit()", 5000);
	</c:if>
</script>

<ssl:form method="post" action="${formAction}">
<html:hidden property="accountFeeLogId"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="accountFee.title.logDetails"/></td>
        <cyclos:help page="account_management#account_fee_log_details"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
					<td class="headerLabel" width="20%"><bean:message key='accountFee.name'/></td>
					<td class="headerField" colspan="4">${fee.name}</td>
				</tr>
				<tr>
					<td class="headerLabel" valign="top"><bean:message key='accountFeeLog.date'/></td>
					<td class="headerField" colspan="4"><cyclos:format dateTime="${log.date}"/></td>
				</tr>
				<tr>
					<td class="headerLabel" valign="top"><bean:message key='accountFeeLog.finishDate'/></td>
					<td class="headerField" colspan="4">
						<c:choose>
							<c:when test="${log.rechargingFailed}"> 
								<bean:message key='accountFeeLog.rechargingFailed'/>
							</c:when>
							<c:when test="${empty log.finishDate}">
								<bean:message key='accountFeeLog.stillRunning'/>
							</c:when>
							<c:otherwise>
								<cyclos:format dateTime="${log.finishDate}"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<c:if test="${not fee.manual}">
					<tr>
						<td class="headerLabel" valign="top"><bean:message key='accountFeeLog.period'/></td>
						<td class="headerField" colspan="4">
							<c:choose><c:when test="${fee.recurrence.number == 1 and cyclos:name(fee.recurrence.field) == 'DAYS'}">
								<cyclos:format rawDate="${log.period.begin}" />
							</c:when><c:otherwise>
								<c:set var="toLabel"><bean:message key="global.range.to" /></c:set>
		                    	<cyclos:format rawDate="${log.period.begin}" />
		                    	${fn:toLowerCase(toLabel)} 
		                    	<cyclos:format rawDate="${log.period.end}" />
							</c:otherwise></c:choose>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="headerLabel" valign="top"><bean:message key='accountFeeLog.amount'/></td>
					<td class="headerField" colspan="4"><cyclos:format amount="${log.amountValue}" unitsPattern="${currencyPattern}"/></td>
				</tr>
				<tr>
					<td class="headerLabel" valign="top"><bean:message key='accountFee.freeBase'/></td>
					<td class="headerField" colspan="4"><cyclos:format number="${empty log.freeBase ? 0.0 : log.freeBase}" unitsPattern="${currencyPattern}"/></td>
				</tr>
				<tr>
					<td class="headerLabel" valign="top"><bean:message key='accountFeeLog.processedMembers'/></td>
					<td class="headerField" colspan="4"><cyclos:format number="${log.totalMembers}"/></td>
				</tr>
				<tr>
					<td class="headerLabel" valign="top"><bean:message key='accountFeeLog.failedMembers'/></td>
					<td class="headerField" colspan="4"><cyclos:format number="${log.failedMembers}"/></td>
				</tr>
				<tr>
					<td class="headerLabel" valign="top"><bean:message key='accountFeeLog.skippedMembers'/></td>
					<td class="headerField" colspan="4"><cyclos:format number="${details.skippedMembers}"/></td>
				</tr>
				<c:if test="${fee.memberToSystem and not invoiceAlways and not invoiceNever}">
					<tr>
						<td class="headerLabel" valign="top"><bean:message key='accountFeeLog.totalCollectedAmount'/></td>
						<td class="headerField" colspan="4"><cyclos:format number="${details.transfers.amount + details.acceptedInvoices.amount}" unitsPattern="${currencyPattern}"/></td>
					</tr>
				</c:if>
				<c:if test="${not invoiceAlways}">
					<tr>
						<td class="headerLabel" valign="top"><bean:message key='accountFeeLog.payments'/></td>
						<td width="5%" class="label" valign="top"><bean:message key='accountFeeLog.count'/></td>
						<td width="5%"><cyclos:format number="${details.transfers.count}"/></td>
						<td width="10%" class="label" valign="top"><bean:message key='accountFeeLog.totalAmount'/></td>
						<td width="15%"><cyclos:format number="${details.transfers.amount}" unitsPattern="${currencyPattern}"/></td>
					</tr>
				</c:if>
				<c:if test="${not invoiceNever}">
					<tr>
						<td class="headerLabel" valign="top"><bean:message key='accountFeeLog.totalInvoices'/></td>
						<td width="5%" class="label" valign="top"><bean:message key='accountFeeLog.count'/></td>
						<td width="5%"><cyclos:format number="${details.invoices.count}"/></td>
						<td width="10%" class="label" valign="top"><bean:message key='accountFeeLog.totalAmount'/></td>
						<td width="15%"><cyclos:format number="${details.invoices.amount}" unitsPattern="${currencyPattern}"/></td>
					</tr>
					<tr>
						<td class="headerLabel" valign="top"><bean:message key='accountFeeLog.openInvoices'/></td>
						<td class="label" valign="top"><bean:message key='accountFeeLog.count'/></td>
						<td><cyclos:format number="${details.openInvoices.count}"/></td>
						<td class="label" valign="top"><bean:message key='accountFeeLog.totalAmount'/></td>
						<td><cyclos:format number="${details.openInvoices.amount}" unitsPattern="${currencyPattern}"/></td>
					</tr>
					<tr>
						<td class="headerLabel" valign="top"><bean:message key='accountFeeLog.acceptedInvoices'/></td>
						<td class="label" valign="top"><bean:message key='accountFeeLog.count'/></td>
						<td><cyclos:format number="${details.acceptedInvoices.count}"/></td>
						<td class="label" valign="top"><bean:message key='accountFeeLog.totalAmount'/></td>
						<td><cyclos:format number="${details.acceptedInvoices.amount}" unitsPattern="${currencyPattern}"/></td>
					</tr>
				</c:if>
				<c:if test="${not isRunning and log.failedMembers gt 0}">
					<tr>
						<td colspan="5" align="right">
							<input id="rechargeFailedButton" type="button" class="button" value="<bean:message key='accountFeeLog.rechargeFailed'/>"/>
						</td>
					</tr>
				</c:if>
			</table>
        </td>
    </tr>
</table>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="accountFee.title.memberSearch"/></td>
        <cyclos:help page="account_management#account_fee_log_member_search"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
          		<tr>
            		<td class="label" width="25%"><bean:message key="accountFeeLog.status"/></td>
            		<td colspan="2">
            			<html:select property="query(status)" styleClass="InputBoxEnabled">
            				<c:forEach var="status" items="${statuses}">
            					<html:option value="${status}"><bean:message key="accountFeeLog.status.${status}"/></html:option>
            				</c:forEach>
            			</html:select>
            		</td>
          		</tr>
         		<tr>
           			<td class="label"><bean:message key="member.group"/></td>
           			<td colspan="2">
           				<cyclos:multiDropDown name="query(groups)" varName="groupsSelect" emptyLabelKey="member.search.allGroups">
           				<c:forEach var="group" items="${groups}">
           					<cyclos:option value="${group.id}" text="${group.name}" />
           				</c:forEach>
             			</cyclos:multiDropDown>
             			<input type="hidden" name="query(groups)" value=""/>
             		</td>
         		</tr>
				<tr>
					<td width="24%" class="label"><bean:message key="member.username"/></td>
					<td>
						<html:hidden property="query(member)" styleId="memberId"/>
						<input id="memberUsername" class="large" value="${query.member.username}">
						<div id="membersByUsername" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="member.member"/></td>
					<td>
						<input id="memberName" class="large" value="${query.member.name}">
						<div id="membersByName" class="autoComplete"></div>
					</td>
				</tr>
          		<tr>
					<td align="right" colspan="2">
						<input type="submit" class="button" value="<bean:message key="global.search"/>">
					</td>
				</tr>
        	</table>
        </td>
    </tr>
</table>

<c:choose><c:when test="${empty members}">
	<div class="footerNote"><bean:message key="accountFee.noMatchingMembers"/></div>
	<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
		</tr>
	</table>
</c:when><c:otherwise>
	<c:set var="memberProperty" value="${localSettings.memberResultDisplay.property}"/>

	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
	        <cyclos:help page="account_management#account_fee_log_member_list"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable" cellspacing="0" cellpadding="0">
	                <tr>
						<td class="tdHeaderContents"><bean:message key="member.member"/></td>
	                    <td class="tdHeaderContents" width="15%"><bean:message key="accountFeeLog.amount"/></td>
						<td class="tdHeaderContents" width="20%"><bean:message key="accountFeeLog.status"/></td>
	                </tr>
					<c:forEach var="entry" items="${members}">
						<c:set var="member" value="${entry.member}" />
		                <c:set var="status" value="${entry.status}" />
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td><cyclos:profile elementId="${member.id}"/></td>
		                    <td align="right"><cyclos:format number="${entry.amount}" unitsPattern="${currencyPattern}" /></td>
		                    <td align="center">
		                    	<c:choose>
		                    		<c:when test="${not empty entry.transfer}">
		                    			<a class="transferLink" transferId="${entry.transfer.id}">
		                    				<bean:message key="accountFeeLog.status.${status}"/>
		                    			</a>
		                    		</c:when>
		                    		<c:when test="${not empty entry.invoice}">
		                    			<a class="invoiceLink" invoiceId="${entry.invoice.id}">
		                    				<bean:message key="accountFeeLog.status.${status}"/>
		                    			</a>
		                    		</c:when>
		                    		<c:otherwise>
		                    			<bean:message key="accountFeeLog.status.${status}"/>
		                    		</c:otherwise>
		                    	</c:choose>
		                    </td>
		                </tr>
		            </c:forEach>
	            </table>
	        </td>
	    </tr>
	</table>
	
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
			<td align="right"><cyclos:pagination items="${members}"/></td>
		</tr>
	</table>		
</c:otherwise></c:choose>

</ssl:form>