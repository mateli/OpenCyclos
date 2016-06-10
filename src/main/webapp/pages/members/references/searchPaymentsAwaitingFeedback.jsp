<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/references/searchPaymentsAwaitingFeedback.js" />
<ssl:form method="post" action="${formAction}">
<c:choose><c:when test="${empty payments}">
	<div class="footerNote" helpPage="transaction_feedback#payments_awaiting_feedback"><bean:message key="reference.paymentsAwaitingFeedback.noResults"/></div>
</c:when><c:otherwise>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="reference.title.paymentsAwaitingFeedback"/></td>
	        <cyclos:help page="transaction_feedback#payments_awaiting_feedback"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                    <td class="tdHeaderContents" width="20%"><bean:message key="transfer.date"/></td>
						<td class="tdHeaderContents"><bean:message key="member.username"/></td>
	                    <td class="tdHeaderContents"><bean:message key="member.name"/></td>
	                    <td class="tdHeaderContents" width="20%"><bean:message key="transfer.amount"/></td>
	                    <td width="5%" class="tdHeaderContents">&nbsp;</td>
	                </tr>
	                
	                <c:forEach var="payment" items="${payments}">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="center" nowrap="nowrap">
		                    	<c:choose><c:when test="${payment.scheduled}">
			                    	<cyclos:format rawDate="${payment.date}"/>
		                    	</c:when><c:otherwise>
			                    	<cyclos:format date="${payment.date}"/>
		                    	</c:otherwise></c:choose>
		                    </td>
		                    <td align="left"><cyclos:profile elementId="${payment.memberId}" pattern="username"/></td>
		                    <td align="left"><cyclos:profile elementId="${payment.memberId}" pattern="name"/></td>
		                    <td align="center" nowrap="nowrap"><cyclos:format number="${payment.amount}" unitsPattern="${payment.currency.pattern}" /></td>
		                    <td align="right">
		                   		<img transferId="${payment.scheduled ? '' : payment.id}" scheduledPaymentId="${payment.scheduled ? payment.id : ''}" class="edit" src="<html:rewrite page="/pages/images/edit.gif" />" border="0"/>
		                    </td>
		                </tr>
	                </c:forEach>	                
	            </table>
	        </td>
	    </tr>
	</table>
</c:otherwise></c:choose>
	
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td><input type="button" class="button" id="backButton" value="<bean:message key='global.back'/>"></td>
			<td align="right"><cyclos:pagination items="${payments}"/></td>
		</tr>
	</table>
</ssl:form>
