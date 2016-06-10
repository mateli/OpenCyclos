<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="sslext" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:if test="${empty tpAlign}">
	<c:set var="tpAlign" value="center" />
</c:if>

<c:if test="${empty transactionPasswordField}">
	<c:set var="transactionPasswordField" value="transactionPassword" />
</c:if>

<cyclos:script src="/pages/access/transactionPassword/transactionPassword.js" />
<cyclos:script src="/pages/scripts/virtualKeyboard.js" />
<script>
	var chars = "${accessSettings.transactionPasswordChars}";
	<c:if test="${not empty buttonsPerRow}">
		var buttonsPerRow = ${buttonsPerRow};
	</c:if>
</script>
<c:if test="${not hideSubmit and empty submitLabel}">
	<c:set var="submitLabel"><bean:message key="global.submit"/></c:set>
</c:if>

<table class="nested">
	<tr>
		<td align="${tpAlign}" style="padding-left:0px;${accessSettings.virtualKeyboardTransactionPassword ? '' : 'padding-bottom:0px'}">
			<input type="password" name="${transactionPasswordField}" ${accessSettings.virtualKeyboardTransactionPassword ? 'readonly="readonly" class="InputBoxDisabled medium"' : 'class="medium"'} maxlength="20" style="width:150px"/>
			<c:if test="${!accessSettings.virtualKeyboardTransactionPassword && not empty submitLabel}">
				<input type="submit" class="button" value="${submitLabel}">
			</c:if>
		</td>
	</tr>
	<c:if test="${accessSettings.virtualKeyboardTransactionPassword}">
		<tr>
			<td align="${tpAlign}" style="padding-left:0px;padding-top:5px">
				<div class="virtualKeyboard" field="${transactionPasswordField}"></div>
				<script>
					var contrastLabel = "<cyclos:escapeJS><bean:message key="virtualKeyboard.contrast"/></cyclos:escapeJS>";
					var clearLabel = "<cyclos:escapeJS><bean:message key="global.clear"/></cyclos:escapeJS>";
					var submitLabel = null;
					<c:if test="${not empty submitLabel}">
						submitLabel = "<cyclos:escapeJS>${submitLabel}</cyclos:escapeJS>"
					</c:if>
				</script>
			</td>
		</tr>
	</c:if>
</table>
