<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/imports/importedMembersDetails.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="importId"/>
<html:hidden property="status"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="memberImport.title.details.${lowercaseStatus}" /></td>
        <cyclos:help page="user_management#imported_member_details"/> 
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">            	
                <tr>
                    <td width="20%" class="label"><bean:message key="memberImport.lineNumber"/></td>
                    <td><html:text property="query(lineNumber)" styleClass="number small" /></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="member.member"/></td>
                    <td><html:text property="query(nameOrUsername)" styleClass="large" /></td>
                </tr>
                <tr>
					<td colspan="2" align="right">
						<input type="submit" class="button" value="<bean:message key="global.search"/>">
					</td>
				</tr>
            </table>
          </td>            
    </tr>
</table>
</ssl:form>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
        <cyclos:help page="user_management#imported_member_details_result"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
			<table class="defaultTable">
				<tr>
					<td align="center" width="10%" class="tdHeaderContents"><bean:message key="memberImport.lineNumber"/></td>
					<td class="tdHeaderContents"><bean:message key="member.member"/></td>
					<c:if test="${cyclos:name(query.status) != 'SUCCESS'}">
						<td class="tdHeaderContents"><bean:message key="memberImport.status"/></td>
					</c:if>
					<c:if test="${hasCreditLimit}">
						<td class="tdHeaderContents"><bean:message key="account.creditLimit"/></td>
						<td class="tdHeaderContents"><bean:message key="account.upperCreditLimit"/></td>
					</c:if>
					<c:if test="${hasBalance}">
						<td class="tdHeaderContents"><bean:message key="account.balance"/></td>
					</c:if>
				</tr>
				<c:set var="memberProperty" value="${localSettings.memberResultDisplay.property}"/>
				<c:forEach var="importedMember" items="${importedMembers}">
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td align="center">${importedMember.lineNumber}</td>
						<td align="center">${importedMember[memberProperty]}</td>
						<c:if test="${cyclos:name(query.status) != 'SUCCESS'}">
							<td><bean:message key="memberImport.status.${importedMember.status}" arg0="${importedMember.errorArgument1}" arg1="${importedMember.errorArgument2}"/></td>
						</c:if>
						<c:if test="${hasCreditLimit}">
							<td align="center"><cyclos:format unitsPattern="${unitsPattern}" number="${importedMember.creditLimit}" /></td>
							<td align="center"><cyclos:format unitsPattern="${unitsPattern}" number="${importedMember.upperCreditLimit}" /></td>
						</c:if>
						<c:if test="${hasBalance}">
							<td align="center"><cyclos:format unitsPattern="${unitsPattern}" number="${importedMember.initialBalance}"/></td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right"><cyclos:pagination items="${importedMembers}"/></td>
	</tr>
</table>		
