<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/imports/importMembers.js" />
<script>
	var emptyAccountsMessage = "<cyclos:escapeJS><bean:message key="memberImport.accountType.empty"/></cyclos:escapeJS>";
	var emptyInitialCreditMessage = "<cyclos:escapeJS><bean:message key="memberImport.initialCreditTransferType.empty"/></cyclos:escapeJS>";
	var emptyInitialDebitMessage = "<cyclos:escapeJS><bean:message key="memberImport.initialDebitTransferType.empty"/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}" enctype="multipart/form-data">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="memberImport.title.import"/></td>
        <cyclos:help page="user_management#import_members"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="25%"><bean:message key="memberImport.group"/></td>
                    <td>
                    	<html:select styleId="groupSelect" property="import(group)">
                    		<html:option value=""><bean:message key="memberImport.group.select" /></html:option>
                    		<c:forEach var="group" items="${groups}">
                    			<html:option value="${group.id}">${group.name}</html:option>
                    		</c:forEach>
                    	</html:select>
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="memberImport.accountType"/></td>
                    <td><html:select styleId="accountTypeSelect" property="import(accountType)" /></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="memberImport.initialCreditTransferType"/></td>
                    <td><html:select styleId="initialCreditTransferTypeSelect" property="import(initialCreditTransferType)" /></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="memberImport.initialDebitTransferType"/></td>
                    <td><html:select styleId="initialDebitTransferTypeSelect" property="import(initialDebitTransferType)" /></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="memberImport.file"/></td>
                    <td><html:file property="upload" /></td>
                </tr>
                <tr>
					<td align="right" colspan="2">
	               		<input type="submit" id="submitButton" class="button" value="<bean:message key="global.submit"/>" >
					</td>
	            </tr>
             </table>
        </td>
    </tr>
</table>
</ssl:form>