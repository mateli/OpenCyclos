<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/printSettings/editReceiptPrinterSettings.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="id" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="receiptPrinterSettings.title.${receiptPrinterSettings.persistent ? 'modify' : 'new'}"/>
        </td>
        <cyclos:help page="preferences#receipt_printer_details"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="40%"><bean:message key="receiptPrinterSettings.name"/></td>
                    <td><html:text property="receiptPrinterSettings(name)" maxlength="100" styleClass="required large InputBoxDisabled" readonly="true"/></td>
				</tr>
                <tr>
                    <td class="label"><bean:message key="receiptPrinterSettings.printerName"/></td>
                    <td><html:text property="receiptPrinterSettings(printerName)" maxlength="100" styleClass="required large InputBoxDisabled" readonly="true"/></td>
				</tr>
                <tr>
                    <td class="label"><bean:message key="receiptPrinterSettings.beginOfDocCommand"/></td>
                    <td><html:text property="receiptPrinterSettings(beginOfDocCommand)" maxlength="100" styleClass="large InputBoxDisabled" readonly="true"/></td>
				</tr>
                <tr>
                    <td class="label"><bean:message key="receiptPrinterSettings.endOfDocCommand"/></td>
                    <td><html:text property="receiptPrinterSettings(endOfDocCommand)" maxlength="100" styleClass="large InputBoxDisabled" readonly="true"/></td>
				</tr>
                <tr>
                    <td class="label"><bean:message key="receiptPrinterSettings.paymentAdditionalMessage"/></td>
                    <td><html:textarea property="receiptPrinterSettings(paymentAdditionalMessage)" rows="4" styleClass="large InputBoxDisabled" readonly="true"/></td>
				</tr>
				<c:if test="${editable}">
					<tr>
						<td colspan="2" id="helpMessageContainer">
							<cyclos:escapeHTML brOnly="true"><bean:message key="receiptPrinterSettings.helpMessage" arg0="http://project.cyclos.org/wiki/index.php?title=Receipt_printers"/></cyclos:escapeHTML>
						</td>
					</tr>
	               	<tr>
						<td align="right" colspan="2">
							<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">
							&nbsp;
							<input type="submit" id="saveButton" value="<bean:message key="global.submit"/>" class="ButtonDisabled" disabled="true">
						</td>
					</tr>
				</c:if>
            </table>
		</td>            
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left">
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>
</ssl:form>