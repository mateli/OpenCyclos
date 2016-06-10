<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<c:url value="/pages/images" var="images" scope="request" />
<style>
	.admin { display: ${isAdmin ? "block !important" : "none"}; }
	.member { display: ${isMember || isOperator ? "block" : "none"}; }
	.broker { display: ${isBroker ? "block !important" : "none"}; }
	.issuer { display: ${isIssuer ? "block !important" : "none"}; }
	.buyer { display: ${isBuyer ? "block !important" : "none"}; }
	.seller { display: ${isSeller ? "block !important" : "none"}; }
	.notOperator { display: ${isOperator ? "none !important" : ""}; }
	.notBroker { display: ${isBroker ? "none !important" : ""}; }

	span.admin { display: ${isAdmin ? "inline !important" : "none"}; }
	span.member { display: ${isMember || isOperator ? "inline" : "none"}; }
	span.broker { display: ${isBroker ? "inline !important" : "none"}; }
	span.issuer { display: ${isIssuer ? "inline !important" : "none"}; }
	span.buyer { display: ${isBuyer ? "inline !important" : "none"}; }
	span.seller { display: ${isSeller ? "inline !important" : "none"}; }

	li.admin { display: ${isAdmin ? "list-item !important" : "none"}; }
	li.member { display: ${isMember || isOperator ? "list-item" : "none"}; }
	li.broker { display: ${isBroker ? "list-item !important" : "none"}; }
	li.issuer { display: ${isIssuer ? "list-item !important" : "none"}; }
	li.buyer { display: ${isBuyer ? "list-item !important" : "none"}; }
	li.seller { display: ${isSeller ? "list-item !important" : "none"}; }


</style>
