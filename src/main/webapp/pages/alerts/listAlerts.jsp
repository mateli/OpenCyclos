<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/alerts/listAlerts.js" />
<script>
	var removeOneConfirmation = "<cyclos:escapeJS><bean:message key="alert.removeOne.confirm"/></cyclos:escapeJS>";
	var removeSelectedConfirmation = "<cyclos:escapeJS><bean:message key="alert.removeSelected.confirm"/></cyclos:escapeJS>";
	var noneSelectedMessage = "<cyclos:escapeJS><bean:message key="global.error.nothingSelected"/></cyclos:escapeJS>";
</script>
<c:set var="editable" value="${(isSystem && cyclos:granted(AdminSystemPermission.ALERTS_MANAGE_SYSTEM_ALERTS)) || (!isSystem && cyclos:granted(AdminSystemPermission.ALERTS_MANAGE_MEMBER_ALERTS))}"/>
<ssl:form method="post" action="/admin/removeAlerts">
<input type="hidden" name="alertType" value="${isSystem ? 'SYSTEM' : 'MEMBER'}"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="alert.title.${isSystem ? 'system' : 'member'}"/></td>
        <cyclos:help page="${isSystem ? 'alerts_logs#system_alerts' : 'alerts_logs#member_alerts'}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<c:if test="${editable}">
	                	<th class="tdHeaderContents" width="5%">&nbsp;</th>
	                </c:if>
                    <th class="tdHeaderContents"><bean:message key="alert.title"/></th>
                    <c:if test="${isMember}">
	                    <th class="tdHeaderContents"><bean:message key="member.member"/></th>
                    </c:if>
				    <th class="tdHeaderContents" width="25%"><bean:message key="alert.date"/></th>
				    <c:if test="${editable}">
                    	<th class="tdHeaderContents" width="5%">&nbsp;</th>
                    </c:if>	
                </tr>
				<c:forEach var="alert" items="${alerts}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                	<c:if test="${editable}">
		                	<td align="center"><input type="checkbox" class="checkbox" name="alertIds" value="${alert.id}"></td>
		                </c:if>
	                    <td align="left"><bean:message key="${alert.key}" arg0="${alert.arg0}" arg1="${alert.arg1}" arg2="${alert.arg2}" arg3="${alert.arg3}" arg4="${alert.arg4}"/></td>  
	                    <c:if test="${isMember}">
	                    	<td align="left"><cyclos:profile elementId="${alert.member.id}" /></td>
	                    </c:if>
	                    <td align="center" nowrap="nowrap"><cyclos:format dateTime="${alert.date}"/></td>
	                    <c:if test="${editable}">
	                    	<td align="center">
	                    		<img alertId="${alert.id}" src="<c:url value="/pages/images/delete.gif"/>" class="remove"/>
							</td>
						</c:if>
	                </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>

<c:if test="${editable}">
	<table class="defaultTableContentHidden">
		<tr>
			<td>
				<input id="selectAllButton" type="button" class="button" value="<bean:message key="global.selectAll"/>">
				<input id="selectNoneButton" type="button" class="button" value="<bean:message key="global.selectNone"/>">
			</td>
			<td align="right">
				<input type="submit" class="button" value="<bean:message key="global.removeSelected"/>">
			</td>
		</tr>
	</table>
</c:if>
</ssl:form>
