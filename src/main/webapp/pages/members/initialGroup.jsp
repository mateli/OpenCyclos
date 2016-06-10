<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<script>
	var isPublic = ${isPublic ? true : false};
</script>
<cyclos:script src="/pages/members/initialGroup.js" />

<table class="${isPublic ? 'standAloneFixedWidth' : ''} defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="createMember.initialGroup.title"/></td>
        <td class="tdHelpIcon" height="16">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2" align="center" class="tdContentTableLists">
				<br>
				<div class="loadingMessage" style="padding:5px;text-align:justify;width:90%;">
					<cyclos:escapeHTML brOnly="true">
						<bean:message key="createMember.initialGroup.preface.${isPublic ? 'public' : 'byBroker'}" />
					</cyclos:escapeHTML>
				</div>
				<br>
                <c:forEach var="group" items="${groups}">
	                <a class="group default" groupId="${group.id}">
	                	${empty group.initialGroupShow ? group.name : group.initialGroupShow}
	                </a><br>
	                <br class="small">
                </c:forEach>
        </td>
    </tr>
</table>
<table class="${isPublic ? 'standAloneFixedWidth' : ''} defaultTableContentHidden" cellspacing="0" cellpadding="0" >
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
