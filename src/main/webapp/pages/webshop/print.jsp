<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<div style="height:100%;vertical-align:top">
	<jsp:include page="/pages/accounts/details/printTransaction.jsp">
		<jsp:param name="showConfirmation" value="true" />
	</jsp:include>
	
	<input type="button" class="printButton" value="<bean:message key="global.print" />" onclick="window.print()">
	&nbsp;&nbsp;&nbsp;
	<input type="button" class="printButton" value="<bean:message key="global.close"/>" onclick="self.location = '${returnUrl}'">
</div>
