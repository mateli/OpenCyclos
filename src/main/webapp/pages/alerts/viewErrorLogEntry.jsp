<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/alerts/viewErrorLogEntry.js" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="errorLog.title.details"/></td>
        <cyclos:help page="alerts_logs#error_log_details" width=""/>
    </tr>
    <tr> 
        <td class="tdContentTableForms" colspan="2">
   	        <table class="defaultTable">
                <tr>
                   	<td width="25%" class="headerLabel"><bean:message key="errorLog.date"/></td>
               	    <td class="headerField"><cyclos:format dateTime="${errorLogEntry.date}"/></td>
                </tr>
                <tr>
                   	<td class="headerLabel"><bean:message key="errorLog.loggedUser"/></td>
               	    <td class="headerField">
            			<c:choose>
            				<c:when test="${not empty loggedMember}">
    	        				<cyclos:profile elementId="${loggedMember.id}"/>
	            			</c:when>
	            			<c:when test="${not empty loggedAdmin}">
	            				<cyclos:profile elementId="${loggedAdmin.id}"/>
	            			</c:when>
	            			<c:otherwise>
	            				<bean:message key="errorLog.loggedUser.none" />
	            			</c:otherwise>
	            		</c:choose>
					</td>
                </tr>
                <tr>
                   	<td class="headerLabel"><bean:message key="errorLog.path"/></td>
               	    <td class="headerField">${errorLogEntry.path}</td>
                </tr>
                <tr>
                   	<td class="headerLabel" valign="top"><bean:message key="errorLog.parameters"/></td>
               	    <td class="headerField">
               	    	<div style="width:470px;overflow:auto;white-space:nowrap;">
		               	    <c:forEach var="e" items="${errorLogEntry.parameters}" varStatus="status">
		               	    	<div>
			               	    	<span class="label"><cyclos:escapeHTML>${e.key}</cyclos:escapeHTML></span>=
			               	    	<cyclos:escapeHTML>${e.value}</cyclos:escapeHTML>
			               	    </div>
		               	    </c:forEach>
		               	</div>
               	    </td>
                </tr>
                <tr>
                   	<td class="headerLabel" valign="top"><bean:message key="errorLog.stackTrace"/></td>
               	    <td class="headerField">
               	    	<div style="width:470px;overflow:auto;white-space:nowrap;">
               	    		<cyclos:escapeHTML>${errorLogEntry.stackTrace}</cyclos:escapeHTML>
               	    	</div>
               	    </td>
                </tr>
			</table>
        </td>
   </tr>
</table>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
