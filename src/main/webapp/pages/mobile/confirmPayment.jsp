<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<ssl:form method="post" action="/mobile/confirmPayment">
<div class="title"><cyclos:customImage type="system" name="mobileLogo"/>&nbsp;<bean:message key="mobile.payment.title.confirm"/></div>
<cyclos:escapeHTML>${confirmationMessage}</cyclos:escapeHTML>
<br/><br/>
<c:if test="${requestTransactionPassword}">
	<div class="label"><bean:message key="mobile.payment.transactionPassword"/></div>
	<html:password property="transactionPassword"/><br/>
</c:if>
<input type="submit" value="<bean:message key="global.confirm"/>"/>
<a href="<c:url value="/do/mobile/doPayment"/>"><bean:message key="global.back"/></a>
<a href="<c:url value="/do/mobile/home"/>"><bean:message key="mobile.home"/></a>
</ssl:form>
