<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<jsp:include flush="true" page="/pages/access/includes/virtualKeyboardDefinitions.jsp" />
<cyclos:script src="/pages/access/login.js" />
<script>
	var loginAction = "/do/login";
	var useVirtualKeyboard = ${empty accessSettings ? false : accessSettings.virtualKeyboard};
	var numericPassword = ${empty accessSettings ? false : accessSettings.numericPassword};
	var returnToUrl = "${param.returnTo}";
	var groupFilterId = ${empty param.groupFilterId ? 'null' : param.groupFilterId};
</script>