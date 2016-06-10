<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/general/operatorHome.js" />

<c:if test="${generateTransactionPassword}">
	<jsp:include flush="true" page="/pages/access/transactionPassword/generateTransactionPassword.jsp"></jsp:include>
</c:if>

<c:if test="${status.hasData}">
	<jsp:include flush="true" page="/pages/general/memberStatus.jsp"></jsp:include>
</c:if>

<c:if test="${not empty quickAccess}">
	<jsp:include flush="true" page="quickAccess.jsp"/>
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="home.operator.news.title"/></td>
        <cyclos:help page="home#home_news"/>
    </tr>
    <tr> 
        <td class="tdContentTableForms" colspan="2">
        	<cyclos:includeCustomizedFile type="static" name="general_news.jsp"/>
        </td>
   </tr>
</table>

<c:if test="${cyclos:granted(BasicPermission.BASIC_INVITE_MEMBER)}">
	<jsp:include flush="true" page="invitePerson.jsp"/>
</c:if>
