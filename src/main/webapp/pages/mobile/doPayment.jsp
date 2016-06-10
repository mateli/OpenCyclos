<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<ssl:form method="post" action="/mobile/doPayment">
<div class="title"><cyclos:customImage type="system" name="mobileLogo"/>&nbsp;<bean:message key="mobile.payment.title.do"/></div>
<div class="label"><bean:message key="mobile.payment.username"/></div>
<html:text property="username"/><br/>
<div class="label"><bean:message key="mobile.payment.amount"/></div>
<html:text property="amount"/><br/>
<div class="label"><bean:message key="mobile.payment.description"/></div>
<html:textarea property="description"/><br/>
<input type="submit" value="<bean:message key="global.submit"/>"/>
&nbsp;&nbsp;
<a href="<c:url value="/do/mobile/home"/>"><bean:message key="mobile.home"/></a>
</ssl:form>