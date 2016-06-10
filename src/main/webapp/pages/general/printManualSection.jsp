<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>

<style>
	span.manual { display: inline !important; }
	span.help { display: none; }
	.manual { display: inline !important; }
	.help { display: none; }
</style>

<jsp:include page="/pages/general/manualDefinitions.jsp" />
<h1><bean:message key="help.title.${page}"/></h1>
<cyclos:includeCustomizedFile type="help" name="${page}.jsp" />

<script type="text/javascript">
$$('a').each(function(a) {
	 a.replace(a.innerHTML)
});
</script>
