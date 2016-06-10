<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="contactUs.title"/></td>
        <cyclos:help page="user_management#contact_us"/>
    </tr>
    <tr> 
        <td class="tdContentTable" colspan="2" style="text-align:justify">
			<cyclos:includeCustomizedFile type="static" name="contact.jsp" />
        </td>
   </tr>
</table>