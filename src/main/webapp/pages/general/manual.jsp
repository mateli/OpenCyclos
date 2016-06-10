<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>

<cyclos:script src="/pages/general/manual.js" />

<style>
	span.manual { display: inline !important; }
	span.help { display: none; }
	.manual { display: block !important; }
	.help { display: none; }
</style>

<jsp:include page="/pages/general/manualDefinitions.jsp" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="manual.title.${isAdmin ? 'admin' : 'member'}" /></td>
        <td class="tdHelpIcon" height="16">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTable">
			<a style="float:right" class="default" id="printManualLink"><bean:message key="manual.action.printManual" /></a>
			<ul>
			<c:forEach var="page" items="${helps}">
				<li><a class="pageLink manualLink" page="${page}" class="default"><bean:message key="help.title.${page}"/></a></li>
			</c:forEach>
			</ul>
		</td>
	</tr>
</table>
