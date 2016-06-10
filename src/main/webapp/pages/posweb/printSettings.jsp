<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<script>
	var savedMessage = "<cyclos:escapeJS><bean:message key="receiptPrinterSettings.modified" /></cyclos:escapeJS>";
</script>
<cyclos:script src="/pages/posweb/printSettings.js" />
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="posweb.printSettings.title"/></td>
        <td class="tdHelpIcon">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2" align="center" class="tdContentTableForms">
			<c:choose><c:when test="${fn:contains(fn:toLowerCase(header['User-Agent']), 'msie')}">
				<br><br>
				<bean:message key="receiptPrinter.error.ie" />
				<br><br>&nbsp;
			</c:when><c:otherwise>
	            <table class="defaultTable" id="formTable">
					<tr>
						<td width="50%" class="label"><bean:message key='receiptPrinterSettings.localPrinter'/></td>
						<td>
							<select id="localPrintSettings">
								<option value=""><bean:message key="receiptPrinterSettings.localPrinter.defaultBrowserPrinting"/></option>
								<c:forEach var="printer" items="${receiptPrinterSettings}">
									<option value="${printer.id}">${printer.name}</option>
								</c:forEach>
							</select>
						</td>
					<tr>
					<tr>
						<td colspan="2">
							<input type="submit" id="submitButton" class="button" value="<bean:message key='global.submit'/>">
						</td>
					</tr>
	            </table>
			</c:otherwise></c:choose>
        </td>
    </tr>
</table>
