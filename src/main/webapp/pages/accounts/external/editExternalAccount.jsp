<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/external/editExternalAccount.js" />
<ssl:form method="post" action="${formAction}">
<html:hidden property="externalAccountId"/>
<html:hidden property="externalAccount(id)"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="externalAccount.title.${isInsert ? 'insert' : 'modify'}"/></td>
        <cyclos:help page="bookkeeping#new_edit_external_account"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
            		<td class="label" width="25%"><bean:message key="externalAccount.name"/></td>
            		<td><html:text property="externalAccount(name)" maxlength="100" readonly="true" styleClass="full InputBoxDisabled"/></td>
            	</tr>
				<tr>
            		<td class="label"><bean:message key="externalAccount.description"/></td>
					<td><html:textarea styleId="descriptionText" property="externalAccount(description)" rows="6" readonly="true" styleClass="full InputBoxDisabled"/></td>            		
            	</tr>
				<tr>
            		<td class="label"><bean:message key="externalAccount.systemAccount"/></td>
            		<td>
            			<html:select property="externalAccount(systemAccountType)" disabled="true" styleClass="InputBoxDisabled">
							<c:forEach var="systemAccount" items="${systemAccounts}">
								<html:option value="${systemAccount.id}">${systemAccount.name}</html:option>
							</c:forEach>
						</html:select>
            		</td>
            	</tr>
            	<tr>
            		<td class="label" valign="top"><bean:message key="externalAccount.memberAccount"/></td>
            		<td>
            			<html:select property="externalAccount(memberAccountType)" disabled="true" styleClass="InputBoxDisabled">
							<c:forEach var="memberAccount" items="${memberAccounts}">
								<html:option value="${memberAccount.id}">${memberAccount.name}</html:option>
							</c:forEach>
						</html:select>
            		</td>
            	</tr>
            	<c:if test="${editable}">
	            	<tr>
						<td align="right" colspan="2">
							<input type="button" id="modifyExternalAccountButton" class="button" value="<bean:message key="global.change"/>">
							&nbsp;
							<input type="submit" id="saveExternalAccountButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
				</c:if>
            </table>
        </td>
    </tr>
</table>
</ssl:form>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>

<c:set var="showChildren" value="${!isInsert}" />
<c:if test="${showChildren}">
	<jsp:include flush="true" page="/do/admin/editFileMapping">
		<jsp:param name="externalAccountId" value="${externalAccount.id}"/>
	</jsp:include>	
</c:if>

<c:if test="${showChildren}">
	<jsp:include flush="true" page="/do/admin/listExternalTransferTypes">
		<jsp:param name="externalAccountId" value="${externalAccount.id}"/>
	</jsp:include>	
</c:if>
