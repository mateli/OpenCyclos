<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="about.title"/></td>
        <td class="tdHelpIcon" height="16">&nbsp;</td>
    </tr>
    <tr> 
        <td class="tdContentTable" colspan="2" style="text-align:justify">
			<cyclos:includeCustomizedFile type="static" name="member_about.jsp" />
        </td>
   </tr>
</table>