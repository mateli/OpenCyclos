<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/alerts/searchErrorLog.js" />


<script languaje="javascript">
	function checkDates() { 
		if((new Date($('start').value)).valueOf() <= (new Date($('end').value)).valueOf()) {
			return true;
		} else {
			alert('<bean:message key="global.timePeriod.invalid"/>');
			return false;
		}
	}
</script>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="errorLog.title.search"/></td>
        <cyclos:help page="alerts_logs#error_history"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
        
        
           	<ssl:form method="post" action="${formAction}" onsubmit="javascript: return checkDates();">
            <table class="defaultTable">
          		<tr>
            		<td class="label" width="30%"><bean:message key="errorLog.search.date.begin"/></td>
            		<td nowrap="nowrap"><html:text styleId="start" property="query(period).begin" styleClass="date small" maxlength="10"/></td>
            	</tr>
            	<tr>
            		<td class="label"><bean:message key="errorLog.search.date.end"/></td>
            		<td nowrap="nowrap"><html:text styleId="end" property="query(period).end" styleClass="date small" maxlength="10"/></td>
          		</tr>
          		<tr>
            		<td align="right" colspan="4">
						
						<input type="submit" class="button" value="<bean:message key="global.search"/>">
					</td>
          		</tr>
        	</table>        	
       		</ssl:form>
        </td>
    </tr>
</table>

<c:choose><c:when test="${empty errorLog}">
	<div class="footerNote" helpPage="alerts_logs#error_history_result"><bean:message key="errorLog.search.noResults"/></div>
</c:when><c:otherwise>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
	        <cyclos:help page="alerts_logs#error_history_result"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
					    <th class="tdHeaderContents" width="25%"><bean:message key="errorLog.date"/></th>
	                    <th class="tdHeaderContents"><bean:message key="errorLog.path"/></th>
	                    <th class="tdHeaderContents" width="5%">&nbsp;</th>
	                </tr>
					<c:forEach var="entry" items="${errorLog}">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="center" nowrap="nowrap"><cyclos:format dateTime="${entry.date}"/></td>
		                    <td>${entry.path}</td>  
	                    	<td align="center" nowrap="nowrap">
								<img entryId="${entry.id}" src="<c:url value="/pages/images/view.gif"/>" class="view"/>
							</td>
		                </tr>
	                </c:forEach>
	            </table>
	        </td>
	    </tr>
	</table>
	
	<table class="defaultTableContentHidden">
		<tr>
	        <td align="right"><cyclos:pagination items="${errorLog}"/></td>
		</tr>
	</table>
</c:otherwise></c:choose>
