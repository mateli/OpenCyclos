<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/accounts/paymentFilters/editPaymentFilter.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="accountTypeId"/>
<html:hidden property="paymentFilterId"/>
<html:hidden property="paymentFilter(id)"/>
<html:hidden property="paymentFilter(accountType)" value="${accountType.id}"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="paymentFilter.title.${isInsert ? 'insert' : 'modify'}" arg0="${accountType.name}"/></td>
        <cyclos:help page="account_management#payment_filter_details"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
					<td class="label" width="25%"><bean:message key='paymentFilter.name'/></td>
					<td><html:text property="paymentFilter(name)" maxlength="50" readonly="true" styleClass="full InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label" valign="top"><bean:message key='paymentFilter.description'/></td>
					<td><html:textarea styleId="descriptionText" property="paymentFilter(description)" rows="6" readonly="true" styleClass="full InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key='paymentFilter.showInAccountHistory'/></td>
					<td><html:checkbox property="paymentFilter(showInAccountHistory)" disabled="true" styleClass="checkbox InputBoxDisabled" value="true"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key='paymentFilter.showInReports'/></td>
					<td><html:checkbox property="paymentFilter(showInReports)" disabled="true" styleClass="checkbox InputBoxDisabled" value="true"/></td>
				</tr>
				<tr>
					<td class="label" valign="top"><bean:message key='paymentFilter.transferTypes'/></td>
					<td>
						<cyclos:multiDropDown name="paymentFilter(transferTypes)" disabled="true" size="5">
							<c:forEach var="tt" items="${transferTypes}">
								<cyclos:option value="${tt.id}" text="${tt.name}" selected="${cyclos:contains(paymentFilter.transferTypes, tt)}"/>
							</c:forEach>
						</cyclos:multiDropDown>
					</td>
				</tr>
				<tr>
					<td class="label" valign="top"><bean:message key='paymentFilter.groups'/></td>
					<td>
						<cyclos:multiDropDown name="paymentFilter(groups)" disabled="true" size="5">
							<c:forEach var="group" items="${groups}">
								<cyclos:option value="${group.id}" text="${group.name}" selected="${cyclos:contains(paymentFilter.groups, group)}"/>
							</c:forEach>
						</cyclos:multiDropDown>
					</td>
				</tr>
				<c:if test="${cyclos:granted(AdminSystemPermission.ACCOUNTS_MANAGE)}">
					<tr>
						<td align="right" colspan="2">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">						&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
				</c:if>
			</table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
</ssl:form>
