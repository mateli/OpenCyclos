<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<div class="title"><cyclos:customImage type="system" name="mobileLogo"/>&nbsp;<bean:message key="mobile.home.title"/></div>	
<c:if test="${multipleAccounts}">
	<bean:message key="account.type"/>: <br/>${accountType.name}<br/>
</c:if>

<c:set var="balance"><cyclos:format number="${status.balance}" unitsPattern="${unitsPattern}" /></c:set>
<c:set var="availableBalance"><cyclos:format number="${status.availableBalance}" unitsPattern="${unitsPattern}" /></c:set>
<c:set var="reservedAmount"><cyclos:format number="${status.reservedAmount <= 0 ? null : status.reservedAmount}" unitsPattern="${unitsPattern}" /></c:set>
<c:set var="creditLimit"><cyclos:format number="${status.creditLimit <= 0 ? null : status.creditLimit}" unitsPattern="${unitsPattern}" /></c:set>

<bean:message key="mobile.home.balance" arg0="${balance}"/><br/>
<c:if test="${balance != availableBalance}">
	<bean:message key="mobile.home.availableBalance" arg0="${availableBalance}"/><br/>
</c:if>
<c:if test="${not empty reservedAmount}">
	<bean:message key="mobile.home.reservedAmount" arg0="${reservedAmount}"/><br/>
</c:if>
<c:if test="${not empty creditLimit}">
	<bean:message key="mobile.home.creditLimit" arg0="${creditLimit}"/><br/>
</c:if>
<c:if test="${cyclos:granted(MemberPermission.PAYMENTS_PAYMENT_TO_MEMBER) && cyclos:name(account.status)=='ACTIVE'}">
	<a href="<c:url value="/do/mobile/doPayment?fromMenu=true"/>"><bean:message key="mobile.home.doPayment"/></a><br/>
</c:if>
<a href="<c:url value="/do/mobile/viewPayments?fromMenu=true"/>"><bean:message key="mobile.home.viewPayments"/></a><br/>
<c:if test="${multipleAccounts}">
	<a href="<c:url value="/do/mobile/changeAccount"/>"><bean:message key="accountType.change"/></a><br/>
</c:if>
<a href="<c:url value="/do/mobile/logout"/>"><bean:message key="mobile.home.logout"/></a>
