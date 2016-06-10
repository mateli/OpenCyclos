<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<c:if test="${accessSettings.virtualKeyboard || accessSettings.virtualKeyboardTransactionPassword}">
	<cyclos:script src="/pages/scripts/virtualKeyboard.js" />
	<script>
		var fullLabel = "<cyclos:escapeJS><bean:message key="virtualKeyboard.full"/></cyclos:escapeJS>";
		var capsLockLabel = "<cyclos:escapeJS><bean:message key="virtualKeyboard.capsLock"/></cyclos:escapeJS>";
		var contrastLabel = "<cyclos:escapeJS><bean:message key="virtualKeyboard.contrast"/></cyclos:escapeJS>";
		var clearLabel = "<cyclos:escapeJS><bean:message key="global.clear"/></cyclos:escapeJS>";
		var submitLabel = "<cyclos:escapeJS><bean:message key="global.submit"/></cyclos:escapeJS>";
	</script>
</c:if>
