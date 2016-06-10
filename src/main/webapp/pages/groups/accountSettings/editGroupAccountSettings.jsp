<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<cyclos:script src="/pages/groups/accountSettings/editGroupAccountSettings.js" />

<c:choose>
	<c:when test="${isInsert}">
		<c:set var="titleKey" value="group.account.title.new"/>
		<c:set var="helpPage" value="groups#insert_group_account"/>
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="group.account.title.modify"/>
		<c:set var="helpPage" value="groups#edit_group_account"/>
	</c:otherwise>
</c:choose>

<ssl:form action="${formAction}" method="post">
<html:hidden property="groupId" />
<html:hidden property="accountTypeId" />
<html:hidden property="setting(id)" />
<html:hidden property="setting(group)" value="${group.id}" />

<c:choose><c:when test="${isInsert && empty possibleAccountTypes}">
  	
	<%-- Trying to associate a new account type, but there are no possible types --%>
	<div class="footerNote" helpPage="${helpPage}"><bean:message key="group.account.allAccountsOnGroup" /></div>
</c:when><c:otherwise>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}" arg0="${group.name}" arg1="${settings.accountType.name}"/></td>
        <cyclos:help page="${helpPage}" />
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
       		
	            <table class="defaultTable">
	            	<tr>
	            		<td width="30%" class="label"><bean:message key="account.type" /></td>
	            		<td>
			            	<c:choose><c:when test="${isInsert}">
			            		<html:select styleId="accountTypeSelect" property="setting(accountType)" styleClass="InputBoxDisabled" disabled="true">
			            			<c:forEach var="type" items="${possibleAccountTypes}">
			            				<html:option value="${type.id}">${type.name}</html:option>
			            			</c:forEach>
			            		</html:select>
		                	</c:when><c:otherwise>
		                		<html:hidden property="setting(accountType)" />
		                		<input type="text" id="accountTypeText" class="large InputBoxDisabled" readonly="readonly" value="${settings.accountType.name}">
			            	</c:otherwise></c:choose>
		           		</td>
	            	</tr>
	            	<tr>
	            		<td class="label"><bean:message key="account.isDefault" /></td>
	            		<td><html:checkbox property="setting(default)" styleClass="checkbox" value="true" disabled="true"/></td>
	            	</tr>
	            	<c:if test="${group.basicSettings.transactionPassword.used}">
		            	<tr>
		            		<td class="label"><bean:message key="account.transactionPasswordRequired" /></td>
		            		<td><html:checkbox property="setting(transactionPasswordRequired)" styleClass="checkbox" value="true" disabled="true"/></td>
		            	</tr>
	            	</c:if>
	            	<tr>
	            		<td class="label"><bean:message key="account.hideWhenNoCreditLimit" /></td>
	            		<td><html:checkbox property="setting(hideWhenNoCreditLimit)" styleClass="checkbox" value="true" disabled="true"/></td>
	            	</tr>
	            	<tr>
	            		<td class="label"><bean:message key="account.defaultCreditLimit" /></td>
	            		<td valign="middle"><html:text property="setting(defaultCreditLimit)" styleClass="small floatNegative InputBoxDisabled" readonly="true"/></td>
	            	</tr>
	            	<tr>
	            		<td class="label"><bean:message key="account.defaultUpperCreditLimit" /></td>
	            		<td valign="middle"><html:text property="setting(defaultUpperCreditLimit)" styleClass="small float InputBoxDisabled" readonly="true"/></td>
	            	</tr>
	            	<!-- <c:if test="${!isInsert}"> -->
		            	<tr>
		            		<td class="label"><bean:message key="group.account.updateAccountLimits" /></td>
		            		<td valign="middle"><html:checkbox property="updateAccountLimits" styleClass="checkbox" value="true" disabled="true"/></td>
		            	</tr>
	            	<!-- </c:if>  -->
	            	<tr>
	            		<td class="label"><bean:message key="account.initialCredit" /></td>
	            		<td><html:text property="setting(initialCredit)" styleClass="small float InputBoxDisabled" readonly="true"/></td>
	            	</tr>
	            	<tr>
	            		<td class="label"><bean:message key="account.initialCreditTransferType" /></td>
	            		<td>
	            			<html:select styleId="initialCreditTTSelect" property="setting(initialCreditTransferType)" styleClass="InputBoxDisabled" disabled="true">
		            			<html:option value=""></html:option>
	            				<c:forEach var="tt" items="${possibleTransferTypes}">
	            					<html:option value="${tt.id}">${tt.name}</html:option>
	            				</c:forEach>
	            			</html:select>
	            		</td>
	            	</tr>
	            	<tr>
	            		<td class="label"><bean:message key="account.lowUnits" /></td>
	            		<td><html:text property="setting(lowUnits)" styleClass="small float InputBoxDisabled" readonly="true"/></td>
	            	</tr>
	            	<tr>
	            		<td class="label" valign="top"><bean:message key="account.lowUnitsMessage" /></td>
	            		<td><html:textarea property="setting(lowUnitsMessage)" styleClass="full InputBoxDisabled" rows="4" readonly="true"/></td>
	            	</tr>
	            	<c:if test="${editable}">
		            	<tr>
							<td align="right" colspan="2">
								<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">
								&nbsp;
								<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
							</td>
		            	</tr>
		            </c:if>
	            </table>
	        </td>
		</tr>
	</table>
</c:otherwise></c:choose>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
	<tr>
		<c:if test="${not empty pendingAccounts and pendingAccounts > 0}">
			<td>
				<br> 
				<div class="fieldDecoration" style="text-align:center">
					<bean:message key="group.account.pendingAccounts" arg0="${pendingAccounts}" />
				</div>
			</td>
		</c:if>
	</tr>
</table>
</ssl:form>