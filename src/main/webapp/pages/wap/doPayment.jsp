<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<card id="doPayment" title="<bean:message key="mobile.payment.title.do"/>">
<p><b><bean:message key="mobile.payment.username"/></b><br/><input name="username" value="${mobileDoPaymentForm.username}"/></p>
<p><b><bean:message key="mobile.payment.amount"/></b><br/><input name="amount" value="${mobileDoPaymentForm.amount}"/></p>
<p><b><bean:message key="mobile.payment.description"/></b><br/><input name="description" value="${mobileDoPaymentForm.description}"/></p>
<p>
<anchor title="<bean:message key="global.submit"/>">
  <go href="<c:url value="/do/wap/doPayment"/>" method="post">
  	<postfield name="username" value="$(username)"/>
  	<postfield name="amount" value="$(amount)"/>
  	<postfield name="description" value="$(description)"/>
  </go>
</anchor>
<a href="<c:url value="/do/wap/home"/>"><bean:message key="mobile.home"/></a>
</p>
</card>