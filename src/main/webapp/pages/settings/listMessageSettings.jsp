<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/settings/listMessageSettings.js" />
<c:set var="imageClass" value="details ${editable ? 'edit' : 'view'}" />
<c:url var="imageUrl" value="/pages/images/${editable ? 'edit' : 'view'}.gif" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="settings.message.general.title"/>
        </td>
        <cyclos:help page="translation#general_notifications"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                    <th class="tdHeaderContents" width="43%"><bean:message key="settings.message.setting"/></th>
                    <th class="tdHeaderContents" width="43%"><bean:message key="settings.message.value"/></th>
                   	<th class="tdHeaderContents" width="4%">&nbsp;</th>
                </tr>
				<c:forEach var="setting" items="${general}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="left"><bean:message key="settings.message.${setting}"/></td>  
	                    <td align="left"><cyclos:truncate value="${messageSettings[setting]}" html="${setting == 'messageMailSuffixHtml'}" /></td>
                    	<td align="center"><img setting="${setting}" src="${imageUrl}" class="${imageClass}"/></td>
	                </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>


<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="settings.message.member.title"/>
        </td>
        <cyclos:help page="translation#member_notifications"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                    <th class="tdHeaderContents" width="43%"><bean:message key="settings.message.setting"/></th>
                    <th class="tdHeaderContents" width="43%"><bean:message key="settings.message.subject"/></th>
                   	<th class="tdHeaderContents" width="4%">&nbsp;</th>
                </tr>
				<c:forEach var="setting" items="${memberNotifications}">
					<c:set var="subjectProperty" value="${setting}Subject" />
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="left"><bean:message key="settings.message.${setting}"/></td>  
	                    <td align="left"><cyclos:truncate value="${messageSettings[subjectProperty]}" /></td>
                    	<td align="center"><img setting="${setting}" src="${imageUrl}" class="${imageClass}"/></td>
	                </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>


<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="settings.message.admin.title"/>
        </td>
        <cyclos:help page="translation#admin_notifications"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                    <th class="tdHeaderContents" width="43%"><bean:message key="settings.message.setting"/></th>
                    <th class="tdHeaderContents" width="43%"><bean:message key="settings.message.subject"/></th>
                   	<th class="tdHeaderContents" width="4%">&nbsp;</th>
                </tr>
				<c:forEach var="setting" items="${adminNotifications}">
					<c:set var="subjectProperty" value="${setting}Subject" />
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="left"><bean:message key="settings.message.${setting}"/></td>  
	                    <td align="left"><cyclos:truncate value="${messageSettings[subjectProperty]}" /></td>
                    	<td align="center"><img setting="${setting}" src="${imageUrl}" class="${imageClass}"/></td>
	                </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
