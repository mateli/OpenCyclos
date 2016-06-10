<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<card id="viewPayments" title="<bean:message key="mobile.viewPayments.title"/>">
<p><b><bean:message key="mobile.viewPayments.date"/></b><br/><cyclos:format dateTime="${payment.date}"/></p>
<p><b><bean:message key="mobile.viewPayments.amount"/></b><br/>${amount}</p>
<p><b><bean:message key="mobile.viewPayments.related"/></b><br/>${isDebit ? payment.to.ownerName : payment.from.ownerName}</p>
<c:if test="${not empty payment.transactionNumber}">
	<p><b><bean:message key="mobile.viewPayments.transactionNumber"/></b><br/>${payment.transactionNumber}</p>
</c:if>
<p><b><bean:message key="mobile.viewPayments.description"/></b><br/><cyclos:escapeHTML>${payment.description}</cyclos:escapeHTML></p>
<p>
<a href="<c:url value="/do/wap/home"/>"><bean:message key="mobile.home"/></a>&nbsp;
<c:if test="${not empty previous}">
    <a href="<c:url value="/do/wap/viewPayments?current=${previous}"/>"><bean:message key="mobile.viewPayments.previous"/></a>&nbsp;
</c:if>
<c:if test="${not empty next}">
    <a href="<c:url value="/do/wap/viewPayments?current=${next}"/>"><bean:message key="mobile.viewPayments.next"/></a>
</c:if>
</p>
</card>
