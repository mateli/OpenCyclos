<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<div class="title"><cyclos:customImage type="system" name="mobileLogo"/>&nbsp;<bean:message key="mobile.viewPayments.title"/></div>
<span class="label"><bean:message key="mobile.viewPayments.date"/></span> <cyclos:format dateTime="${payment.date}"/><br/>
<span class="label"><bean:message key="mobile.viewPayments.amount"/></span> <span class="${isDebit ? 'debit' : 'credit'}">${amount}</span><br/>
<span class="label"><bean:message key="mobile.viewPayments.related"/></span> ${isDebit ? payment.to.ownerName : payment.from.ownerName}<br/>
<c:if test="${not empty payment.transactionNumber}">
	<span class="label"><bean:message key="mobile.viewPayments.transactionNumber"/></span> ${payment.transactionNumber}<br/>
</c:if>
<span class="label"><bean:message key="mobile.viewPayments.description"/></span> <cyclos:escapeHTML>${payment.description}</cyclos:escapeHTML><br/>
<a href="<c:url value="/do/mobile/home"/>"><bean:message key="mobile.home"/></a>&nbsp;
<c:if test="${not empty previous}">
    <a href="<c:url value="/do/mobile/viewPayments?current=${previous}"/>"><bean:message key="mobile.viewPayments.previous"/></a>&nbsp;
</c:if>
<c:if test="${not empty next}">
    <a href="<c:url value="/do/mobile/viewPayments?current=${next}"/>"><bean:message key="mobile.viewPayments.next"/></a>
</c:if>
