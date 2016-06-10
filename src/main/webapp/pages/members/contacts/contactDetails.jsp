<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/contacts/contactDetails.js" />

<ssl:form method="post" action="${formAction}" >
<html:hidden property="contact(id)" />
<html:hidden property="contact(contact)" />
<html:hidden property="contact(owner)" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="contact.title.edit"/>
        </td>
        <cyclos:help page="user_management#contact_note"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td width="25%" class="label"><bean:message key="member.username"/></td>
                    <td><input type="text" id="usernameText" class="medium InputBoxDisabled" readonly="true" value="${contact.username}"/></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="member.name"/></td>
                    <td><input type="text" id="nameText" class="full InputBoxDisabled" readonly="true" value="${contact.name}"/></td>
                </tr>
                <tr>
                    <td class="label" valign="top"><bean:message key="contact.notes"/></td>
                    <td><html:textarea styleId="notes" property="contact(notes)" rows="8" styleClass="full InputBoxDisabled" readonly="true"/></td>
                </tr>
                <tr>
					<td colspan="2" align="right">
						<input class="button" id="modifyButton" type="button" value="<bean:message key="global.change"/>">&nbsp;
						<input id="saveButton" type="submit" class="ButtonDisabled" disabled="true" value="<bean:message key="global.submit"/>">
					</td>
                </tr>
            </table>
        </td>            
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left">
			<input class="button" type="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>
</ssl:form>