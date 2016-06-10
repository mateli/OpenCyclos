<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<body class="login">

<jsp:include page="/pages/general/layout/layoutTop.jsp" />

<table class="loginTable">

	<c:if test="${!systemOnline}">
		<tr>
			<td colspan="3">
				<center>
					<div class="alertNotification">
						<cyclos:escapeHTML><bean:message key="login.systemOffline" /></cyclos:escapeHTML>
					</div>
					&nbsp;
				</center>
			</td>
		</tr>
	</c:if>

	<c:if test="${loggedOut}">
		<tr>
			<td colspan="3">
				<center>
					<div class="fieldDecoration loginRegistration" style="width: inherit">
						<cyclos:escapeHTML><bean:message key="${loggedOut ? 'error.session.timeout' : 'login.redirectFromMessage' }" /></cyclos:escapeHTML>
					</div>
					&nbsp;
				</center>
			</td>
		</tr>				
	</c:if>
	

	<tr>
		<td class="loginFormContainer">
			<div class="loginTitle">
				<cyclos:escapeHTML><bean:message key="login.form.title" arg0="${localSettings.applicationName}" /></cyclos:escapeHTML>
			</div>
			<div class="loginText">
				<cyclos:escapeHTML><bean:message key="${param.operator ? 'login.operator.form.text' : 'login.form.text'}" arg0="${localSettings.applicationName}" /></cyclos:escapeHTML>
			</div>

			<table class="nested loginFormTable">
				<tr>
					<c:if test="${!accessSettings.virtualKeyboard}">
						<td class="loginFormDecoration">&nbsp;</td>
					</c:if>
					<td class="loginForm">
						<jsp:include flush="true" page="/pages/access/includes/loginForm.jsp" />
					</td>
 				</tr>
 			</table>
		</td>
		
		<td class="loginSeparator">&nbsp;</td>
		
		<td class="loginRegistration" id="loginRegistration">
			<div class="loginRegistrationDiv" id="loginRegistrationDiv">
				<div class="loginTitle">
					<cyclos:escapeHTML><bean:message key="login.registration.title" arg0="${localSettings.applicationName}" /></cyclos:escapeHTML>
				</div>
				<div class="loginText">
					<cyclos:escapeHTML><bean:message key="login.registration.text" arg0="${localSettings.applicationName}" /></cyclos:escapeHTML>
				</div>
				<input type="button" onclick="publicRegisterUser()" class="button" value="<bean:message key="global.submit"/>">
			</div>
		</td>
	</tr>
	
	<tr>
		<td colspan="3" class="bottomText">
			<bean:message key="login.bottomText"/>
		</td>
	</tr>
</table>
<jsp:include page="/pages/general/layout/layoutBottom.jsp" />
</body>