<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<c:set var="help" value="${param.page}"/>

<style>
	span.help { display: inline !important; }
	span.manual { display: none; }
	.help { display: block !important; }
	.manual { display: none; }
</style>

<jsp:include page="/pages/general/manualDefinitions.jsp" />
<div class="manualPage">
<h1 class="helpTitle"><bean:message key="help.title.${help}"/></h1>
<cyclos:includeCustomizedFile type="help" name="${help}.jsp" />
</div>