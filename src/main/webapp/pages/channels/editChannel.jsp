<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:set var="selectedPrincipalTypes" value="${channel.principalTypes}"/>
<script>
	var isBuiltin = ${isBuiltin};
	var principalTypes = [];
	<c:forEach var="entry" items="${possiblePrincipalTypes}">
		{
			var obj = {value: '${entry.key}', text: '${entry.value}', selected: ${cyclos:contains(selectedPrincipalTypes, entry.key)} };
			principalTypes.push(obj);
			principalTypes[obj.value] = obj;
		}
	</c:forEach>
	var selectedDefaultPrincipalType = "${channel.defaultPrincipalType}";
</script>
<cyclos:script src="/pages/channels/editChannel.js" />
<ssl:form method="post" action="${formAction}">
<html:hidden property="channelId"/>
<html:hidden property="channel(id)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0" style="float: none">
    <tr>
        <td class="tdHeaderTable"><bean:message key="channel.title.${channel['transient'] ? 'insert' : 'modify'}"/></td>
        <cyclos:help page="settings#channels_detail"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                   	<td class="label" width="30%"><bean:message key="channel.displayName"/></td>
                   	<td align="left"><html:text property="channel(displayName)" styleClass="full required InputBoxDisabled" readonly="true"/></td>
            	</tr>
                <tr>
                   	<td class="label"><bean:message key="channel.internalName"/></td>
                   	<td align="left"><html:text property="channel(internalName)" styleClass="full required InputBoxDisabled" readonly="true"/></td>
            	</tr>
                <tr>
                   	<td class="label"><bean:message key="channel.principals"/></td>
                   	<td align="left"><cyclos:multiDropDown name="principalTypes" disabled="true" varName="principalTypesSelect" onchange="updatePrincipalTypes()"/></td>
            	</tr>
                <tr>
                   	<td class="label"><bean:message key="channel.defaultPrincipal"/></td>
                   	<td align="left"><html:select property="defaultPrincipalType" styleId="defaultPrincipalTypeSelect" disabled="true" styleClass="InputBoxDisabled"/></td>
            	</tr>
                <tr>
                   	<td class="label"><bean:message key="channel.credentials"/></td>
                   	<td align="left">
		            	<c:choose><c:when test="${empty singleCredential}">
	                   		<html:select property="channel(credentials)" disabled="true" styleClass="InputBoxDisabled">
	                   			<c:forEach var="credentials" items="${possibleCredentials}">
	                   				<html:option value="${credentials}"><bean:message key="channel.credentials.${credentials}" /></html:option>
	                   			</c:forEach>
	                   		</html:select>
        		    	</c:when><c:otherwise>
        		    		<c:set var="credentialLabel"><bean:message key="channel.credentials.${singleCredential}" /></c:set>
        		    		<input id="credentialLabel" class="full required InputBoxDisabled" disabled="disabled" value="${credentialLabel}"></td>
        		    	</c:otherwise></c:choose>
                   	</td>
            	</tr>
       	    	<c:if test="${allowsPaymentRequest}">
	                <tr>
	                   	<td class="label"><bean:message key="channel.supportsPaymentRequest"/></td>
	                   	<td align="left"><input type="checkbox" id="supportsPaymentRequest" class="checkbox" disabled="disabled" ${empty channel.paymentRequestWebServiceUrl ? '' : 'checked="checked"'}/></td>
	            	</tr>
	                <tr id="trWS">
	                   	<td class="label"><bean:message key="channel.webServiceUrl"/></td>
	                   	<td align="left"><html:text property="channel(paymentRequestWebServiceUrl)" styleClass="full InputBoxDisabled" readonly="true"/></td>
	            	</tr>
	            </c:if>
	            <c:if test="${canManage}">
	                <tr>
	                  	<td colspan="2" align="right" style="padding-top: 20px">
							<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">
							&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="true" value="<bean:message key="global.submit"/>">
	                   	</td>
	            	</tr>
            	</c:if>
            </table>
        </td>
    </tr>
</table>
<br/>
<input type="button" value="<bean:message key="global.back"/>" class="button" id="backButton">
</ssl:form>
