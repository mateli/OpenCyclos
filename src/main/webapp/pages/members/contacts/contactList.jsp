<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<cyclos:script src="/pages/members/contacts/contactList.js" />
<script>
	var deleteConfirmationMessage = "<cyclos:escapeJS><bean:message key="contact.removeConfirmation"/></cyclos:escapeJS>";
</script>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="contact.title.list"/></td>
        <cyclos:help page="user_management#contacts"/>
    </tr>
	<tr> 
		<td class="tdContentTableLists" colspan="2">
			<div align="justify">
				<table class="defaultTable">
					<tr>
						<td class="tdHeaderContents" width="27%"><bean:message key="member.username"/></td>
						<td class="tdHeaderContents" width="33%"><bean:message key="member.memberName"/></td>
						<td class="tdHeaderContents" width="30%"><bean:message key="member.email"/></td>
						<td class="tdHeaderContents" width="10%">&nbsp;</td>
					</tr>
					<c:forEach var="contact" items="${contacts}">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	   						<td><cyclos:profile elementId="${contact.contact.id}" pattern="username"/></td>
	   						<td><cyclos:profile elementId="${contact.contact.id}" pattern="name"/></td>
	   						<td>
	   							<c:choose><c:when test="${not contact.contact.hideEmail and not empty contact.contact.email}">
		   							<a href="mailto:${contact.contact.email}">${contact.contact.email}</a>
	   							</c:when><c:otherwise>
	   								&nbsp;
	   							</c:otherwise></c:choose>
	   						</td>
	   						<td align="center" valign="bottom">
		   						<img contactId="${contact.id}" class="edit" src="<c:url value="/pages/images/${empty contact.notes ? 'edit_gray.gif' : 'edit.gif'}"/>" border="0">
	   							<img contactId="${contact.id}" class="remove" src="<c:url value="/pages/images/delete.gif" />" border="0">
	   						</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</td>
   </tr>
</table>

<c:if test="${param.fromQuickAccess}">
	<table class="defaultTableContentHidden"><tr><td>
	<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
	</td></tr></table>
</c:if>

<ssl:form method="post" action="${actionPrefix}/addContact">
<html:hidden property="direct" value="true"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="contact.title.add"/></td>
        <cyclos:help page="user_management#add_contact"/>
    </tr>
    <tr> 
        <td class="tdContentTableForms" colspan="2">
   	        <table class="defaultTable">
				<tr>
					<td class="label" width="25%"><bean:message key='member.username'/></td>
					<td>
						<html:hidden styleId="memberId" property="memberId" />
						<input id="memberUsername" class="large">
						<div id="membersByUsername" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label" width="25%"><bean:message key='member.memberName'/></td>
					<td>
						<input id="memberName" class="large">
						<div id="membersByName" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input type="submit" class="button" value="<bean:message key="global.submit"/>">
					</td>
				</tr>
			</table>
        </td>
   </tr>
</table>
</ssl:form>