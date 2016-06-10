<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/customization/documents/viewDocumentForm.js" />

<ssl:form action="${formAction}" method="post">
<html:hidden property="documentId" value="${document.id}"/>
<html:hidden property="memberId" value="${member.id}"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable" style="height:16px;"><bean:message key="document.title.form" arg0="${document.name}"/></td>
        <td class="tdHelpIcon">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
        	
        	<jsp:include flush="true" page="${formPage}" />
        	&nbsp;
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" width="50%">
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
		<td align="right" width="50%">
			<input type="submit" class="button" value="<bean:message key="global.submit"/>">
		</td>
	</tr>
</table>
</ssl:form>