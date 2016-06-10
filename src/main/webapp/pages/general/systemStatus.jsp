<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<script>
	var uptimeMessage = "<cyclos:escapeJS><bean:message key="home.admin.status.systemUptime.message" arg0="#days#" arg1="#hours#"/></cyclos:escapeJS>";
</script>
<cyclos:script src="/pages/general/systemStatus.js" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="home.admin.status.title"/></td>
        <cyclos:help page="home#home_status"/>
    </tr>
    <tr> 
        <td class="tdContentTableForms" colspan="2">
   	        <table class="defaultTable">
                <tr>
                   	<td width="35%" class="headerLabel" nowrap="nowrap"><bean:message key="home.admin.status.cyclosVersion"/></td>
               	    <td class="headerField" colspan="2">${applicationStatus.cyclosVersion}</td>
                </tr>
                <tr>
                   	<td class="headerLabel" nowrap="nowrap"><bean:message key="home.admin.status.systemUptime"/></td>
               	    <td class="headerField" colspan="2" id="uptime"><bean:message key="home.admin.status.systemUptime.message" arg0="${applicationStatus.uptimeDays}" arg1="${applicationStatus.uptimeHours}"/></td>
                </tr>
                <c:if test="${cyclos:granted(AdminMemberPermission.MESSAGES_VIEW)}">
	                <tr>
	                   	<td class="headerLabel" nowrap="nowrap"><bean:message key="home.admin.status.unreadMessages"/></td>
	               	    <td class="headerField" colspan="2"><a id="unreadMessages">${applicationStatus.unreadMessages}</a></td>
	                </tr>
                </c:if>
                <c:if test="${cyclos:granted(AdminMemberPermission.INVOICES_ACCEPT) || cyclos:granted(AdminMemberPermission.INVOICES_CANCEL) || cyclos:granted(AdminMemberPermission.INVOICES_DENY)}">
	                <tr>
	                   	<td class="headerLabel" nowrap="nowrap"><bean:message key="home.admin.status.openInvoices"/></td>
	               	    <td class="headerField" colspan="2"><a id="openInvoices">${applicationStatus.openInvoices}</a></td>
	                </tr>
                </c:if>
                <c:if test="${cyclos:granted(AdminSystemPermission.STATUS_VIEW_CONNECTED_ADMINS)}">
	                <tr>
	                   	<td class="headerLabel" nowrap="nowrap"><bean:message key="home.admin.status.connectedAdmins"/></td>
	               	    <td class="headerField" colspan="2"><a id="connectedAdmins">${applicationStatus.connectedAdmins}</a></td>
	                </tr>
	            </c:if>
                <c:if test="${cyclos:granted(AdminSystemPermission.STATUS_VIEW_CONNECTED_MEMBERS)}">
	                <tr>
	                   	<td class="headerLabel" nowrap="nowrap"><bean:message key="home.admin.status.connectedMembers"/></td>
	               	    <td class="headerField" colspan="2"><a id="connectedMembers">${applicationStatus.connectedMembers}</a></td>
	                </tr>
                </c:if>
                <c:if test="${cyclos:granted(AdminSystemPermission.STATUS_VIEW_CONNECTED_BROKERS)}">
	                <tr>
	                   	<td class="headerLabel" nowrap="nowrap"><bean:message key="home.admin.status.connectedBrokers"/></td>
	               	    <td class="headerField" colspan="2"><a id="connectedBrokers">${applicationStatus.connectedBrokers}</a></td>
	                </tr>
                </c:if>
                <c:if test="${cyclos:granted(AdminSystemPermission.STATUS_VIEW_CONNECTED_OPERATORS)}">
	                <tr>
	                   	<td class="headerLabel" nowrap="nowrap"><bean:message key="home.admin.status.connectedOperators"/></td>
	               	    <td class="headerField" colspan="2"><a id="connectedOperators">${applicationStatus.connectedOperators}</a></td>
	                </tr>
                </c:if>
                <c:if test="${cyclos:granted(AdminSystemPermission.ALERTS_VIEW_SYSTEM_ALERTS)}">
	                <tr>
	                   	<td class="headerLabel" nowrap="nowrap"><bean:message key="home.admin.status.systemAlerts"/></td>
	               	    <td class="headerField" colspan="2"><a id="systemAlerts">${applicationStatus.systemAlerts}</a></td>
	                </tr>
	            </c:if>
	            <c:if test="${cyclos:granted(AdminSystemPermission.ALERTS_VIEW_MEMBER_ALERTS)}">
	                <tr>
	                   	<td class="headerLabel" nowrap="nowrap"><bean:message key="home.admin.status.memberAlerts"/></td>
	               	    <td class="headerField" colspan="2"><a id="memberAlerts">${applicationStatus.memberAlerts}</a></td>
	                </tr>
                </c:if>
	            <c:if test="${cyclos:granted(AdminSystemPermission.ERROR_LOG_VIEW)}">
	                <tr>
	                   	<td class="headerLabel" nowrap="nowrap"><bean:message key="home.admin.status.errors"/></td>
	               	    <td class="headerField"><a id="errors">${applicationStatus.errors}</a></td>
	               	    <td style="text-align:right" nowrap="nowrap"><a id="refreshStatus" class="default"><bean:message key="home.admin.status.refresh"/></a></td>
	                </tr>
                </c:if>
			</table>
        </td>
   </tr>
</table>
