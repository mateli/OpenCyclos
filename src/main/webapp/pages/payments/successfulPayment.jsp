<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<script	language="JavaScript">
	var transferId = "${param.transferId}";
	var selfPayment = ${selfPayment && true};
	var relatedMember = "${relatedMember}";
	var selectMember = "${param.selectMember}";
	var params = $H();
	params.set('toSystem', "${toSystem && true}");
	params.set('selectMember', "${selectMember && true}");
	params.set('from', "${from}");
	<c:if test="${not selectMember}">
		params.set('to', "${to}");
	</c:if>
</script>

<cyclos:script src="/pages/payments/successfulPayment.js" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="payment.title.sucessfulPayment"/>
        </td>
        <td class="tdHelpIcon">&nbsp;</td>
    </tr>
    
    <tr>
        <td class="tdContentTableForms" colspan="2">
            <table class="defaultTable">
			    <tr>
			    	<td align="center" colspan="2">
			    		<br/>
			    		<cyclos:escapeHTML><bean:message key="payment.${pendingAuthorization ? 'awaitingAuthorization' : 'performed'}"/></cyclos:escapeHTML>
			    		<br/><br/><br/>
			    	</td>
			    </tr>
			    <c:choose>
			    	<c:when test="${not empty transfer}">
					    <tr> 
					    	<td class="label"><bean:message key='global.print'/></td>
					    	<td><input type="button" class="button" id="printButton" value="<bean:message key='global.submit'/>"></td>
					    </tr>
					    <tr>
					    	<td class="label"><bean:message key='payment.newPayment'/></td>
					    	<td><input type="button" class="button" id="newPaymentButton" value="<bean:message key='global.submit'/>"></td>
					    </tr>
					    <c:if test="${not empty relatedMember}">
						    <tr>
								<td class="label"><bean:message key='payment.backToMemberProfile'/></td>
								<td><input type="button" class="button" id="backToMemberProfileButton" value="<bean:message key='global.submit'/>"></td>
							</tr>
						</c:if>
			    	</c:when>
			    </c:choose>
				<tr><td>&nbsp;</td></tr>
			</table>
		</td>
	</tr>
</table>
