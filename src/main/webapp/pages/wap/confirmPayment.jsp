<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<card id="confirm" title="<bean:message key="mobile.payment.title.confirm"/>">
<p><cyclos:escapeHTML>${confirmationMessage}</cyclos:escapeHTML></p>
<c:if test="${requestTransactionPassword}">
	<p><bean:message key="mobile.payment.transactionPassword"/><br/><input name="transactionPassword"/></p>
</c:if>
<p>
<anchor title="<bean:message key="confirmPayment.confirm"/>">
  <go href="<c:url value="/do/wap/confirmPayment"/>" method="post">
  	<postfield name="transactionPassword" value="$(transactionPassword)"/>
  </go>
</anchor>
<a href="<c:url value="/do/wap/doPayment"/>"><bean:message key="global.back"/></a>
<a href="<c:url value="/do/wap/home"/>"><bean:message key="mobile.home"/></a>
</p>
</card>