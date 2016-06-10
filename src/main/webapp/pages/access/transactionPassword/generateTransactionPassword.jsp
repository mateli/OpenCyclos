<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/access/transactionPassword/generateTransactionPassword.js" />
<script>
    var generatedMessage = "<cyclos:escapeJS><cyclos:escapeHTML><bean:message key="transactionPassword.generated"/></cyclos:escapeHTML></cyclos:escapeJS>";
	var okLabel = "<cyclos:escapeJS><bean:message key="global.ok"/></cyclos:escapeJS>";
</script>

<div id="transactionPasswordDIV">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="transactionPassword.title"/></td>
        <cyclos:help page="passwords#transaction_password_generation"/>
    </tr>
    <tr> 
        <td class="tdContentTableForms" colspan="2" id="transactionPasswordTextTD" style="text-align: justify">
            <cyclos:escapeHTML><bean:message key="transactionPassword.description"/></cyclos:escapeHTML>
        </td>
    </tr>
    <tr> 
        <td class="tdContentTableForms" colspan="2" align="right" id="transactionPasswordButtonTD">
            <input type="button" class="button" onclick="generateTransactionPassword()" value="<bean:message key="transactionPassword.generate"/>">
        </td>
    </tr>
</table>
</div>