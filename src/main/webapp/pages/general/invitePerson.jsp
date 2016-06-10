<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/general/invitePerson.js" />

<ssl:form styleId="inviteForm" method="post" action="${actionPrefix}/invite" onsubmit="return requestValidation(this);">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
     <tr>
        <td class="tdHeaderTable"><bean:message key="invite.title"/></td>
        <cyclos:help page="home#home_invite"/>
    </tr>
    <tr> 
        <td class="tdContentTableForms" colspan="2">
			<table class="defaultTable">
				<tr>
					<td align="justify"><cyclos:escapeHTML><bean:message key="invite.message"/></cyclos:escapeHTML></td>
				</tr>
				<tr>
					<td align="right">
	            		<html:text property="to" styleClass="large"/>&nbsp;
	            		<html:submit property="Submit" styleClass="button"><bean:message key='global.send'/></html:submit>
	            	</td>
	            </tr>
            </table>
        </td>
   </tr>
</table>
</ssl:form>