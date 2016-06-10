<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<cyclos:script src="/pages/accounts/currencies/manageCurrency.js" />

<ssl:form action="${formAction}">
	<html:hidden property="currencyId" />
</ssl:form>

<jsp:include flush="true" page="${pathPrefix}/editCurrency">
	<jsp:param name="currencyId" value="${currency.id}"/>
</jsp:include>

<c:if test="${ratesEnabled && pendingRateInit == null}">
	<jsp:include flush="true" page="${pathPrefix}/reinitializeRates">
		<jsp:param name="currencyId" value="${currency.id}"/>
	</jsp:include>
</c:if>

