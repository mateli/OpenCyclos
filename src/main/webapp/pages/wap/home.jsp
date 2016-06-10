<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:set var="balance"><cyclos:format number="${status.balance}" unitsPattern="${unitsPattern}" /></c:set>
<c:set var="availableBalance"><cyclos:format number="${status.availableBalance}" unitsPattern="${unitsPattern}" /></c:set>
<c:set var="reservedAmount"><cyclos:format number="${status.reservedAmount <= 0 ? null : status.reservedAmount}" unitsPattern="${unitsPattern}" /></c:set>
<c:set var="creditLimit"><cyclos:format number="${status.creditLimit <= 0 ? null : status.creditLimit}" unitsPattern="${unitsPattern}" /></c:set>

<card id="home" title="<bean:message key="mobile.home.title"/>">
<c:if test="${multipleAccounts}">
	<p><bean:message key="account.type"/>: <br/>${accountType.name}</p>
</c:if>

<p><bean:message key="mobile.home.balance" arg0="${balance}"/></p>
<c:if test="${balance != availableBalance}">
	<p><bean:message key="mobile.home.availableBalance" arg0="${availableBalance}"/></p>
</c:if>
<c:if test="${not empty reservedAmount}">
	<p><bean:message key="mobile.home.reservedAmount" arg0="${reservedAmount}"/></p>
</c:if>
<c:if test="${not empty creditLimit}">
	<p><bean:message key="mobile.home.creditLimit" arg0="${creditLimit}"/></p>
</c:if>

<c:if test="${cyclos:granted(MemberPermission.PAYMENTS_PAYMENT_TO_MEMBER) && cyclos:name(account.status)=='ACTIVE'}">
	<p><a href="<c:url value="/do/wap/doPayment?fromMenu=true"/>"><bean:message key="mobile.home.doPayment"/></a></p>
</c:if>
<p><a href="<c:url value="/do/wap/viewPayments?fromMenu=true"/>"><bean:message key="mobile.home.viewPayments"/></a></p>
<c:if test="${multipleAccounts}">
	<p><a href="<c:url value="/do/wap/changeAccount"/>"><bean:message key="accountType.change"/></a></p>
</c:if>
<p><a href="<c:url value="/do/wap/logout"/>"><bean:message key="mobile.home.logout"/></a></p>
</card>
