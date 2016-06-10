<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/customization/fields/setCustomFieldOrder.js" />

<c:set var="arg0" value=""/>
<c:choose>
	<c:when test="${nature == 'MEMBER'}">
		<c:set var="titleKey" value="customField.title.order.member"/>
	</c:when>
	<c:when test="${nature == 'ADMIN'}">
		<c:set var="titleKey" value="customField.title.order.admin"/>
	</c:when>
	<c:when test="${nature == 'OPERATOR'}">
		<c:set var="titleKey" value="customField.title.order.operator"/>
	</c:when>
	<c:when test="${nature == 'AD'}">
		<c:set var="titleKey" value="customField.title.order.ad"/>
	</c:when>
	<c:when test="${nature == 'PAYMENT'}">
		<c:set var="titleKey" value="customField.title.order.payment"/>
		<c:set var="arg0"     value="${transferType.name}" />
	</c:when>
	<c:when test="${nature == 'LOAN_GROUP'}">
		<c:set var="titleKey" value="customField.title.order.loanGroup"/>
	</c:when>
	<c:when test="${nature == 'MEMBER_RECORD'}">
		<c:set var="titleKey" value="customField.title.order.memberRecord"/>
		<c:set var="arg0"     value="${memberRecordType.name}" /> 
	</c:when>
</c:choose>

<ssl:form method="post" action="${formAction}">
<html:hidden property="nature"/>
<html:hidden property="memberRecordTypeId"/>
<html:hidden property="transferTypeId"/>
<input type="hidden" name="accountTypeId" value="${transferType.from.id}"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}" arg0="${arg0}"/></td>
        <cyclos:help page="custom_fields#order_custom_fields"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTable">
            <table class="defaultTable">
            	<tr>
            		<td align="center">
			        	<br>
			            <ul id="customFields" class="draggableList" style="width:300px">
			                <c:forEach var="customField" items="${customFields}">
			                	<li>
					            	<input type="hidden" name="fieldIds" value="${customField.id}"/>
					                <cyclos:escapeHTML>${customField.name}</cyclos:escapeHTML>
					            </li>
			                </c:forEach>
						</ul>
						<br>&nbsp;
						<bean:message key="customField.title.order.description"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<input type="submit" class="button" id="submitButton" value="<bean:message key="global.submit"/>">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td>
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>
</ssl:form>