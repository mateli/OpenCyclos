<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/external/editExternalTransferType.js" />
<ssl:form method="post" action="${formAction}">
<html:hidden property="externalTransferTypeId"/>
<html:hidden property="account"/>
<html:hidden property="externalTransferType(account)"/>
<html:hidden property="externalTransferType(id)"/>

<script>
var externalAccountId = ${account.id};
var toMemberTTs = [
<c:forEach var="tt" items="${toMemberTransferTypes}" varStatus="status">
	{id:${tt.id}, name:'<cyclos:escapeJS>${tt.name}</cyclos:escapeJS>'}${status.last ? '' : ','}
</c:forEach>
];
var toSystemTTs = [
<c:forEach var="tt" items="${toSystemTransferTypes}" varStatus="status">
	{id:${tt.id}, name:'<cyclos:escapeJS>${tt.name}</cyclos:escapeJS>'}${status.last ? '' : ','}
</c:forEach>
];
var selectedTT = "${externalTransferType.transferType.id}";
</script>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="externalTransferType.title.${isInsert ? 'insert' : 'modify'}"/></td>
        <cyclos:help page="bookkeeping#edit_external_transfer_type"/> 
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="25%"><bean:message key="externalTransferType.name"/></td>
                    <td><html:text property="externalTransferType(name)" maxlength="100" readonly="true" styleClass="full InputBoxDisabled"/></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="externalTransferType.description"/></td>
                    <td><html:textarea styleId="descriptionText" property="externalTransferType(description)" rows="6" readonly="true" styleClass="full InputBoxDisabled"/></td>                    
                </tr>
                <tr>
                    <td class="label"><bean:message key="externalTransferType.code"/></td>
                    <td><html:text property="externalTransferType(code)" maxlength="20" readonly="true" styleClass="large InputBoxDisabled"/></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="externalTransferType.action"/></td>
                    <td>
                        <html:select property="externalTransferType(action)" styleId="actionSelect" styleClass="InputBoxDisabled" disabled="true">
                            <c:forEach var="action" items="${actions}">
                                <html:option value="${action}"><bean:message key="externalTransferType.action.${action}"/></html:option>
                            </c:forEach>
                        </html:select>
                    </td>
                </tr>
                <tr id="trTransferType" style="display:none">
                    <td class="label"><bean:message key="externalTransferType.transferType"/></td>
                    <td><html:select property="externalTransferType(transferType)" styleId="transferTypeSelect" styleClass="InputBoxDisabled" disabled="true" /></td>   
                </tr>
                <c:if test="${editable}">
                    <tr>
                        <td align="right" colspan="2">
                            <input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">
                            &nbsp;
                            <input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</ssl:form>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
    </tr>
</table>