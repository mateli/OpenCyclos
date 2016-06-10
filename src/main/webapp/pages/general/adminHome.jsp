<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/general/adminHome.js" />

<c:if test="${generateTransactionPassword}">
	<jsp:include flush="true" page="/pages/access/transactionPassword/generateTransactionPassword.jsp"></jsp:include>
</c:if>

<c:if test="${cyclos:granted(AdminSystemPermission.STATUS_VIEW)}">
	<jsp:include flush="true" page="systemStatus.jsp"/>
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="home.admin.jumpToProfile.title"/></td>
        <cyclos:help page="home#jump_to_profile"/>
    </tr>
    <tr> 
        <td class="tdContentTableForms" colspan="2">
   	        <table class="defaultTable">
				<tr>
					<td class="label" width="25%"><bean:message key='member.username'/></td>
					<td>
						<input type="hidden" id="memberId"/>
						<input id="memberUsername" class="large">
						<div id="membersByUsername" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label" width="25%"><bean:message key='member.name'/></td>
					<td>
						<input id="memberName" class="large">
						<div id="membersByName" class="autoComplete"></div>
					</td>
				</tr>
			</table>
        </td>
   </tr>
</table>

<c:if test="${cyclos:granted(BasicPermission.BASIC_INVITE_MEMBER)}">
	<jsp:include flush="true" page="invitePerson.jsp"/>
</c:if>
