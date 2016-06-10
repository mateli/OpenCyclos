<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<script>
	var savedMessage = "<cyclos:escapeJS><bean:message key="receiptPrinterSettings.modified" /></cyclos:escapeJS>";
</script>
<cyclos:script src="/pages/members/printSettings/listReceiptPrinterSettings.js" />

<script>
	var removeConfirmation = "<cyclos:escapeJS><bean:message key="receiptPrinterSettings.remove.confirm"/></cyclos:escapeJS>";
</script>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="receiptPrinterSettings.title.list"/></td>
        <cyclos:help page="preferences#receipt_printer_search"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                    <th class="tdHeaderContents"><bean:message key="receiptPrinterSettings.name"/></th>
                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
                </tr>
				<c:forEach var="printer" items="${receiptPrinterSettings}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="left">${printer.name}</td>
	                    <td align="center">
	                    	<img class="edit" printerId="${printer.id}" src="<c:url value="/pages/images/edit.gif"/>" />
							<img class="remove" printerId="${printer.id}" src="<c:url value="/pages/images/delete.gif"/>" />
						</td>
	                </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left" width="65%">
			<c:choose><c:when test="${fn:contains(fn:toLowerCase(header['User-Agent']), 'msie')}">
				<bean:message key="receiptPrinter.error.ie" />
			</c:when><c:otherwise>
				<span class="label"><bean:message key="receiptPrinterSettings.localPrinter"/></span>
				<select id="localPrintSettings">
					<option value=""><bean:message key="receiptPrinterSettings.localPrinter.defaultBrowserPrinting"/></option>
					<c:forEach var="printer" items="${receiptPrinterSettings}">
						<option value="${printer.id}">${printer.name}</option>
					</c:forEach>
				</select>
				<input type="button" class="button" id="saveReceiptPrintSettingsButton" value="<bean:message key="global.submit"/>"> 
			</c:otherwise></c:choose>
		</td>
		<td align="right" valign="top" nowrap="nowrap">
			<span class="label"><bean:message key="receiptPrinterSettings.new"/></span>
			<input type="button" class="button" id="newButton" value="<bean:message key="global.submit"/>">
		</td>
	</tr> 
</table>
