<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/payments/requests/requestPayment.js" />
<script>
	var noTransferTypeMessage = "<cyclos:escapeJS><bean:message key='payment.error.noTransferType'/></cyclos:escapeJS>";
	var toMember = ${toMember.id};
</script>

<ssl:form method="post" action="${formAction}">
<c:if test="${not empty singleChannel}">
	<html:hidden property="ticket(toChannel)" value="${singleChannel.id}" />
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="paymentRequest.title.new${empty singleChannel ? '' : '.singleChannel'}" arg0="${singleChannel.displayName}" /></td>
        <cyclos:help page="payments#request"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
					<td class="label" width="25%"><bean:message key='member.username'/></td>
					<td>
						<html:hidden styleId="memberId" property="ticket(from)"/>
						<input id="memberUsername" class="large">
						<div id="membersByUsername" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key='member.memberName'/></td>
					<td>
						<input id="memberName" class="large">
						<div id="membersByName" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key='transfer.amount'/></td>
					<td>
						<html:text styleId="amount" property="ticket(amount)" styleClass="float medium"/>
						<c:choose><c:when test="${not empty singleCurrency}">
							<html:hidden property="ticket(currency)" value="${singleCurrency.id}" />
							${singleCurrency.symbol}
						</c:when><c:otherwise>
							<html:select property="ticket(currency)" styleId="currencySelect">
    	        				<c:forEach var="currency" items="${currencies}">
        	    					<html:option value="${currency.id}">${currency.symbol}</html:option>
            					</c:forEach>
            				</html:select>
						</c:otherwise></c:choose>
    	        	</td>
				</tr>
				<c:if test="${empty singleChannel}">
					<tr>
						<td class="label"><bean:message key='transfer.channel'/></td>
						<td>
							<html:select property="ticket(toChannel)" styleId="channelSelect">
	   	        				<c:forEach var="channel" items="${channels}">
	       	    					<html:option value="${channel.id}">${channel.displayName}</html:option>
	           					</c:forEach>
	           				</html:select>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="label" width="25%" valign="top"><bean:message key='transfer.description'/></td>
					<td><html:textarea styleId="description" property="ticket(description)" styleClass="full" rows="3"/></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="submit" id="submitButton" class="button" value="<bean:message key='global.submit'/>"></td>
				</tr>
            </table>
        </td>
    </tr>
</table>
</ssl:form>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="paymentRequest.title.search" /></td>
        <cyclos:help page="payments#search_requests"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
					<td class="label" width="25%"><bean:message key='ticket.status'/></td>
					<td>
						<select name="status" id="statusSelect">
   	        				<c:forEach var="status" items="${status}">
       	    					<option value="${status}"><bean:message key="ticket.status.${status}" /></option>
           					</c:forEach>
           				</select>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key='member.username'/></td>
					<td>
						<input type="hidden" id="queryMemberId">
						<input id="queryMemberUsername" class="large">
						<div id="queryMembersByUsername" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key='member.memberName'/></td>
					<td>
						<input id="queryMemberName" class="large">
						<div id="queryMembersByName" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="button" id="searchButton" class="button" value="<bean:message key='global.search'/>"></td>
				</tr>
            </table>
        </td>
    </tr>
</table>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="global.searchResults" /></td>
        <cyclos:help page="payments#search_requests"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
        	<div id="resultContainer">&nbsp;</div>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden">
    <tr>
        <td align="right" id="paginationDisplay"></td>
    </tr>
</table>


