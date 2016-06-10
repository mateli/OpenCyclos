<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<html:html>

<jsp:include page="/pages/general/layout/head.jsp" />
<cyclos:script src="/pages/general/layout/poswebLayout.js" />

<cyclos:customizedFilePath type="style" name="posweb.css" var="poswebStyleUrl" />
<link rel="stylesheet" href="<c:url value="${poswebStyleUrl}" />">

<body>

<table class="poswebRoot" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div align="center">
			<cyclos:includeCustomizedFile name="posweb_header.jsp" type="static" />
			
			<table class="defaultTableContentHidden poswebTopTable">
				<tr>
					<td>
						<jsp:include page="/pages/posweb/includes/printSettingsLink.jsp" />
					</td>
					<td align="right">
						<c:set var="noLoggedUserDataBar" value="${true}" scope="request" />
						<jsp:include page="/pages/general/loggedUserData.jsp" />
					</td>
				</tr>
			</table>
			
			<table class="defaultTableContentHidden poswebTopTable">
				<tr>
					<td align="center">
						<tiles:insert attribute="body" />
						<c:if test="${not hidePoswebActions}">
							<br clear="both">
							<br class="small">
							<input type="button" id="logoutButton" class="poswebAction button" style="float:left" value="<bean:message key="posweb.action.logout"/>">
							<c:if test="${showReceive}">
								<input type="button" id="receivePaymentButton" class="poswebAction button" style="float:right" value="<bean:message key="posweb.action.receivePayment"/>">
							</c:if>
							<c:if test="${showMake}">
								<input type="button" id="makePaymentButton" class="poswebAction button" style="float:right" value="<bean:message key="posweb.action.makePayment"/>">
							</c:if>
							<c:if test="${showTransactions}">
								<input type="button" id="searchTransactionsButton" class="poswebAction button" style="float:right" value="<bean:message key="posweb.action.searchTransactions"/>">
							</c:if>
						</c:if>
					</td>
				</tr>
			</table>
			
			<cyclos:includeCustomizedFile name="posweb_footer.jsp" type="static" />
			</div>
		</td>
	</tr>
</table>


</body>

<script>
init();
</script>

</html:html> 