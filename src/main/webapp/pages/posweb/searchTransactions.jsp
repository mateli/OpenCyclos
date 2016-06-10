<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/posweb/includes/printTransactionReceipt.js" />
<cyclos:script src="/pages/posweb/searchTransactions.js" />
<ssl:form method="post" action="${formAction}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="posweb.searchTransactions.title"/></td>
        <td class="tdHelpIcon">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2" align="center" class="tdContentTableForms">
            <table class="defaultTable" id="formTable">
				<tr>
					<td width="25%" class="label"><bean:message key='transfer.date'/></td>
					<td><html:text property="date" styleClass="date" /></td>
				<tr>
				<tr>
					<td colspan="2">
						<a class="default" id="printLink" style="float:left"><bean:message key='posweb.searchTransactions.print'/></a>
						<input type="submit" id="submitButton" class="button" value="<bean:message key='global.submit'/>">
					</td>
				</tr>
            </table>
        </td>
    </tr>
</table>
</ssl:form>
<br>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="posweb.searchTransactions.title.transactions"/></td>
        <td class="tdHelpIcon">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
			<c:choose><c:when test="${empty transfers}">
				<br>
				<div class="footerNote"><bean:message key="posweb.searchTransactions.noTransactions"/></div>
				<br class="small">&nbsp;
			</c:when><c:otherwise>
	            <table class="defaultTable" cellspacing="0" cellpadding="0">
	                <tr>
						<th class="tdHeaderContents"><bean:message key="transfer.hour"/></th>
						<th class="tdHeaderContents" width="25%"><bean:message key="transfer.fromOrTo"/></th>
						<c:if test="${localSettings.transactionNumber.valid}">
							<th class="tdHeaderContents"><bean:message key="transfer.transactionNumber"/></th>
						</c:if>	
						<th class="tdHeaderContents" align="right"><bean:message key="transfer.amount"/></th>
						<th class="tdHeaderContents" width="16">&nbsp;</th>
	                </tr>
					<c:forEach var="entry" items="${transfers}">
						<c:set var="transfer" value="${entry.payment}" />
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="center"><cyclos:format time="${transfer.actualDate}" /></td>
							<td align="center">${entry.relatedAccount.ownerName}</td>
							<c:if test="${localSettings.transactionNumber.valid}">
		                    	<td align="center">${transfer.transactionNumber}</td>
		                    </c:if>
		                    <td align="right"><cyclos:format number="${entry.amount}" unitsPattern="${transfer.from.type.currency.pattern}" /></td>
		                    <td align="right"><img class="print" src="<c:url value="/pages/images/print.gif"/>" border="0" paymentId="${transfer.id}" isScheduled="false"></td>
		                </tr>
		            </c:forEach>
	            </table>
			</c:otherwise></c:choose>
        </td>
    </tr>
</table>

<c:if test="${not empty scheduledPayments}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="posweb.searchTransactions.title.scheduledPayments"/></td>
	        <td class="tdHelpIcon">&nbsp;</td>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable" cellspacing="0" cellpadding="0">
	                <tr>
						<th class="tdHeaderContents"><bean:message key="transfer.hour"/></th>
						<th class="tdHeaderContents" width="25%"><bean:message key="transfer.fromOrTo"/></th>
						<th class="tdHeaderContents"><bean:message key="scheduledPayment.parcels"/></th>
						<th class="tdHeaderContents"><bean:message key="transfer.firstPaymentDate"/></th>
						<th class="tdHeaderContents" align="right"><bean:message key="scheduledPayment.amount"/></th>
						<th class="tdHeaderContents" width="16">&nbsp;</th>
	                </tr>
					<c:forEach var="entry" items="${scheduledPayments}">
						<c:set var="sched" value="${entry.payment}" />
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="center"><cyclos:format time="${sched.date}" /></td>
							<td align="center">${entry.relatedAccount.ownerName}</td>
		                    <td align="center">${fn:length(sched.transfers)}</td>
		                    <td align="center"><cyclos:format rawDate="${sched.actualDate}" /></td>
		                    <td align="right"><cyclos:format number="${entry.amount}" unitsPattern="${sched.from.type.currency.pattern}" /></td>
		                    <td align="right"><img class="print" src="<c:url value="/pages/images/print.gif"/>" border="0" paymentId="${sched.id}" isScheduled="true"></td>
		                </tr>
		            </c:forEach>
	            </table>
	        </td>
	    </tr>
	</table>
</c:if>