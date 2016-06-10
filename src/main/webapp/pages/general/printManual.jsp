<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>

<style>
	span.manual { display: inline !important; }
	span.help { display: none; }
	.manual { display: block !important; }
	.help { display: none; }
</style>

<jsp:include page="/pages/general/manualDefinitions.jsp" />
<h1><bean:message key="manual.title.${isAdmin ? 'admin' : 'member'}" /></h1>
<c:forEach var="help" items="${helps}">
	<a name="${help}_jsp"></a>
	<h1><bean:message key="help.title.${help}"/></h1>
	<cyclos:includeCustomizedFile type="help" name="${help}.jsp" />
	
</c:forEach>

<script type="text/javascript">
$$('a').each(function(a) {
    var pos = a.href.indexOf('#');
    if (pos > 0) {
        a.href = a.href.substring(pos);
    } else {
        pos = a.href.indexOf('page='); 
        if (pos > 0) {
    		a.href = "#" + a.href.substring(pos + 5) + "_jsp";
        }
    }
});
</script>