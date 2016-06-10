<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>

<cyclos:script src="/pages/general/manual.js" />

<style>
	span.manual { display: inline !important; }
	span.help { display: none; }
	.manual { display: inline !important; }
	.help { display: none; }
</style>

<jsp:include page="/pages/general/manualDefinitions.jsp" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="help.title.${page}"/></td>
        <td class="tdHelpIcon" height="16">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTable">
        	<a class="default backLink" style="float:left"><bean:message key="global.back" /></a>
			<a class="default" style="float:right" id="printSectionLink"><bean:message key="manual.action.printSection" /></a>
			<br clear="both">
			<div class="manualPage">
				<cyclos:includeCustomizedFile type="help" name="${page}.jsp" />
				<br>
				<a class="default backLink" style="float:left" class="backLink"><bean:message key="global.back" /></a>
				<a href="#top" style="float:right" class="default"><bean:message key="manual.action.top" /></a>
			</div>
		</td>
	</tr>
</table>
