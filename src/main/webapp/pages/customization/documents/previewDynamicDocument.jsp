<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
	body {overflow:auto !important}
</style>

<c:if test="${!empty formPage}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td width="50%" nowrap="nowrap" class="tdHeaderTable"><bean:message key="document.formPage"/></td>
	        <td class="tdHelpIcon">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td colspan="2" class="tdContentTableForms"><br/><jsp:include flush="true" page="${formPage}"/><br/>&nbsp;</td>
	    </tr>
	</table>
	
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td width="50%" nowrap="nowrap" class="tdHeaderTable"><bean:message key="document.documentPage"/></td>
        <td class="tdHelpIcon">&nbsp;</td>
    </tr>
    <tr>
    	<td colspan="2"><jsp:include flush="true" page="${documentPage}"/></td>
    </tr>
</table>


<table class="defaultTable">
	<tr>
		<td align="center" valign="top">
			
			<input type="button" onclick="window.close();" class="button" value="<bean:message key="global.close"/>">
		</td>
	</tr>
</table>