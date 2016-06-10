<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/general/memberStatus.js" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="home.status.title"/></td>
        <cyclos:help page="home#home_status"/>
    </tr>
    <tr> 
        <td class="tdContentTableLists" colspan="2" align="center">
       		<br class="small">
        	<c:if test="${status.unreadMessages > 0}">
	        	<div>
		        	<a id="messagesLink" class="default"><bean:message key="home.status.unreadMessages" arg0="${status.unreadMessages}" /></a>
        		</div>
        		<br class="small">
        	</c:if>
        	<c:if test="${status.openInvoices > 0}">
	        	<div>
		        	<a id="invoicesLink" class="default"><bean:message key="home.status.openInvoices" arg0="${status.openInvoices}" /></a>
        		</div>
        		<br class="small">
        	</c:if>
        	<c:if test="${status.openLoans > 0}">
	        	<div>
		        	<a id="loansLink" class="default"><bean:message key="home.status.openLoans" arg0="${status.openLoans}" /></a>
        		</div>
        		<br class="small">
        	</c:if>
        	<c:if test="${status.paymentsToAuthorize > 0}">
	        	<div>
		        	<a id="authorizeLink" class="default"><bean:message key="home.status.paymentsToAuthorize" arg0="${status.paymentsToAuthorize}" /></a>
        		</div>
        		<br class="small">
        	</c:if>
        	<c:if test="${status.newPayments > 0}">
	        	<div>
		        	<a id="paymentsLink" class="default"><bean:message key="home.status.newPayments" arg0="${status.newPayments}" /></a>
        		</div>
        		<br class="small">
        	</c:if>
        	<c:if test="${status.paymentsAwaitingFeedback > 0}">
	        	<div>
		        	<a id="paymentsAwaitingFeedbackLink" class="default"><bean:message key="home.status.paymentsAwaitingFeedback" arg0="${status.paymentsAwaitingFeedback}" /></a>
        		</div>
        		<br class="small">
        	</c:if>
        	<c:if test="${status.newReferences > 0}">
	        	<div>
		        	<a id="referencesLink" class="default"><bean:message key="home.status.newReferences" arg0="${status.newReferences}" /></a>
        		</div>
        		<br class="small">
        	</c:if>
        	<c:if test="${status.hasPendingCommissionContracts}">
	        	<div>
		        	<a id="pendingContractsLink" class="default"><bean:message key="home.status.hasPendingCommissionContracts" /></a>
        		</div>
        		<br class="small">
        	</c:if>
        </td>
   </tr>
</table>
