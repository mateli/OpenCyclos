<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:set var="standaloneLayout" value="${true}" scope="request" />
<!-- Cyclos error page -->
<cyclos:noCache/>
<html:html>
	<jsp:include page="/pages/general/layout/head.jsp" />
	
	<c:if test="${empty loggedUser and not isPosWeb}">
		<cyclos:customizedFilePath type="style" name="login.css" var="loginUrl" groupId="${cookie.groupId.value}" groupFilterId="${cookie.groupFilterId.value}" />	
		<link rel="stylesheet" href="<c:url value="${loginUrl}" />">
	</c:if>
	
	<c:if test="${isPosWeb}">
		<cyclos:customizedFilePath type="style" name="posweb.css" var="poswebStyleUrl" />
		<link rel="stylesheet" href="<c:url value="${poswebStyleUrl}" />">
	</c:if>
	
	<body class="${isPosWeb || isWebShop ? '' : 'main'}">

	<c:choose>
		<c:when test="${isPosWeb}">
			
			<table class="poswebRoot" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<div align="center">
							<cyclos:includeCustomizedFile name="posweb_header.jsp" type="static" />
			
		</c:when>
		<c:when test="${isWebShop}">

			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="center">
						<cyclos:includeCustomizedFile type="static" name="webshop_header.jsp" />
					</td>
				</tr>
				<tr>
					<td align="center">
					
		</c:when>
		<c:otherwise>
			<jsp:include page="/pages/general/layout/layoutTop.jsp" />
			<br><br><br>
		</c:otherwise>
	</c:choose>

	<table class="${isPosWeb ? 'defaultTableContentHidden poswebLoginTable' : isWebShop ? 'defaultTableContentHidden' : 'defaultTableCenter'}">
		<tr>
			<td align="center" valign="top">

				<c:choose><c:when test="${isWebShop}">
					<table class="defaultTable standAloneFixedWidth bordered" cellspacing="0" cellpadding="0">
				</c:when><c:otherwise>
					<table class="defaultTableContent" style="width:400px;float:none;" cellspacing="0" cellpadding="0">
	    				<tr>
	  						<td class="tdHeaderTable">${localSettings.applicationName}</td>
	  						<td class="tdHelpIcon">&nbsp;</td>
	    				</tr>
				</c:otherwise></c:choose>
    				<tr>
        				<td class="tdContentTable" colspan="2">
							<c:if test="${empty errorKey}">
								<c:set var="errorKey" value="error.general" />
							</c:if>
							<table class="defaultTable">
                				<tr>
                   					<td align="center">
                   						<c:choose>
                   							<c:when test="${isPosWeb}"><br /></c:when>
                   							<c:when test="${isWebShop}"><br /><br /><br /></c:when>
                   						</c:choose>
                   						<cyclos:escapeHTML>
                   						<c:choose><c:when test="${not empty errorMessage}">
                   							${errorMessage}
                   						</c:when><c:otherwise>
	                   						<bean:message key="${errorKey}" arg0="${errorArguments[0]}" arg1="${errorArguments[1]}" arg2="${errorArguments[2]}" arg3="${errorArguments[3]}" arg4="${errorArguments[4]}"/>
                   						</c:otherwise></c:choose>
                   						</cyclos:escapeHTML>
                   						<c:if test="${isWebShop}"><br /><br /><br /></c:if>
                   						<br />&nbsp;
                   					</td>
                				</tr>
                                <tr>
                                    <td align="right">
		                				<c:choose>
	        		                        <c:when test="${not empty errorReturnTo}">
        		                        		<input id="btn" type="button" class="button" onClick="self.location.replace('<c:url value="${errorReturnTo}"/>')" value="<bean:message key="${empty errorButtonKey ? 'global.back' : errorButtonKey}"/>">
        		                        	</c:when>
        		                        	<c:when test="${not empty forceBack}">
	                                        	<input id="btn" type="button" class="button" onClick="history.back()" value="<bean:message key="${empty errorButtonKey ? 'global.back' : errorButtonKey}"/>">
	        		                        </c:when>
		                					<c:when test="${empty loggedUser}">
		                						<c:set var="baseLoginUrl" value="/do/login" />
		                						<c:if test="${isPosWeb}">
		                							<c:set var="baseLoginUrl" value="<%= nl.strohalm.cyclos.controls.posweb.PosWebHelper.loginUrl(request) %>" />
		                						</c:if>
		                						<c:url var="loginUrl" value="${baseLoginUrl}">
		                							<c:if test="${not empty loginParamName and not empty loginParamValue}">
		                								<c:param name="${loginParamName}">${loginParamValue}</c:param>
		                								<c:param name="login">true</c:param>
		                							</c:if>
		                						</c:url>
	                                        	<input id="btn" type="button" class="button" onClick="self.location.replace('${loginUrl}')" value="<bean:message key="${empty errorButtonKey ? 'global.ok' : errorButtonKey}"/>">
	        		                        </c:when>
	        		                        <c:otherwise>
        		                        		<input id="btn" type="button" class="button" onClick="history.back()" value="<bean:message key="${empty errorButtonKey ? 'global.back' : errorButtonKey}"/>">
        		                        	</c:otherwise>
        		                        </c:choose>
                                    </td>
                                </tr>
							</table>
							<script>
								Event.observe(self, "load", function() {
									setFocus('btn');									
								});
							</script>
	    				</td>
    				</tr>
				</table>
			</td>
		</tr>
	</table> 
	
	&nbsp;

	<c:choose>
		<c:when test="${isPosWeb}">
						<cyclos:includeCustomizedFile name="posweb_footer.jsp" type="static" />
						</div>
					</td>
				</tr>
			</table>
		</c:when>
		<c:when test="${isWebShop}">
				<tr>
					<td align="center">
						<cyclos:includeCustomizedFile type="static" name="webshop_footer.jsp" />
					</td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<jsp:include page="/pages/general/layout/layoutBottom.jsp" />
		</c:otherwise>
	</c:choose>

	</body>
</html:html> 

<!-- Before remove the errorKey attribute we must set in the request to generate the detailed trace-->
<c:set var="errorKey" value="${errorKey}" scope="request"/> 

<c:remove var="errorReturnTo" scope="session" />
<c:remove var="forceBack" scope="session" />  
<c:remove var="errorKey" scope="session"/> 
<c:remove var="errorArguments" scope="session"/>
<c:remove var="errorButtonKey" scope="session"/>  
<c:remove var="currentRequestUrl" scope="session"/> 
<c:remove var="isPosWeb" scope="request"/>
