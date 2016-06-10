<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<cyclos:script src="/pages/webshop/payment.js" />
<jsp:include flush="true" page="/pages/access/includes/virtualKeyboardDefinitions.jsp" />
<ssl:form method="post" action="${formAction}">
<table class="defaultTable standAloneFixedWidth fullBordered" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdContentTableForms">       
        	<div align="center">
        		<bean:message key="webshop.payment.headerText">
        			<jsp:attribute name="arg0">
	        			<b><cyclos:format number="${payment.amount}" unitsPattern="${payment.currency.pattern}" /></b>
        			</jsp:attribute>
        			<jsp:attribute name="arg1">
        				<b>${payment.to.name}</b>
        			</jsp:attribute>
        			<jsp:attribute name="arg2">
        				<b>${payment.to.username}</b>
        			</jsp:attribute>
        		</bean:message>
        		<br><br>
        		<c:if test="${not empty payment.description}">
        			<b><bean:message key="webshop.payment.descriptionText"/></b><br>
        			${payment.description}<br><br>
        		</c:if>
        		<bean:message key="webshop.payment.credentialsText" arg0="${credentialsString}" />
        	</div>
        	<br>
        	
        	<div align="center">
	            <table>
					<tr>
						<td nowrap="nowrap" class="label">
							<c:choose><c:when test="${fn:length(principalTypes) == 1}">
								${selectedPrincipalLabel}
							</c:when><c:otherwise>
								<html:select styleClass="InputBoxEnabled" styleId="principalType" property="principalType">
									<c:forEach var="type" items="${principalTypes}">
										<html:option value="${type.value}">${type.key}</html:option>
									</c:forEach>
								</html:select>
							</c:otherwise></c:choose>
						</td>
						<td>
							<c:choose><c:when test="${empty selectedPrincipalType.customField}">
								<input id="principal" name="principal" class="medium"/>
							</c:when><c:otherwise>
								<cyclos:customField field="${selectedPrincipalType.customField}" valueName="principal" styleId="principal" search="true" size="medium"/>
							</c:otherwise></c:choose>
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="label"><bean:message key='channel.credentials.${credentials}'/></td>
						<td>
							<input type="password" name="credentials" size="20" ${useVirtualKeyboard ? 'readOnly class="InputBoxDisabled medium"' : 'class="medium"'}>
						</td>
					</tr>
					<c:if test="${useVirtualKeyboard}">
						<tr>
							<td colspan="2">
								<div class="virtualKeyboard" field="credentials" style="width:${virtualKeyboardChars ? '100%' : '260px'}"></div>
								<script>
									<c:if test="${not empty virtualKeyboardChars}">
										var chars = "${virtualKeyboardChars}";
										var buttonsPerRow = chars.length / 2;
									</c:if>
								</script>
							</td>
						</tr>
					</c:if>
				</table>
			</div>
			<br>
			<div>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td align="left">
							<input type="button" id="cancelButton" class="button" value="<bean:message key='global.cancel'/>">
						</td>
						<td align="right">
							<input type="submit" class="button" value="<bean:message key='global.submit'/>">
						</td>
					</tr>
				</table>
            </div>
        </td>
    </tr>
</table>
</ssl:form>
