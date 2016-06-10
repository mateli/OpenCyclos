<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/accounts/external/externalTransfers/editExternalTransfer.js" />

<c:choose><c:when test="${isInsert}">
	<c:set var="titleKey" value="externalTransfer.title.new"/>
</c:when><c:otherwise>
	<c:set var="titleKey" value="externalTransfer.title.modify"/>
</c:otherwise></c:choose>

<ssl:form method="post" action="${formAction}">
<html:hidden property="externalAccountId" value="${externalAccount.id}"/>
<html:hidden property="transferImportId" value="${transferImportId}"/>
<html:hidden property="externalTransfer(id)"/>
<html:hidden property="externalTransfer(account)" value="${externalAccount.id}"/>
<html:hidden property="externalTransfer(transferImport)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
        <cyclos:help page="${isInsert ? 'bookkeeping#new_external_transfer' : 'bookkeeping#edit_external_transfer'}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<c:if test="${not empty externalTransfer.lineNumber}">
	            	<tr>
						<td class="label" width="20%"><bean:message key="externalTransfer.lineNumber"/></td>
						<td><input id="lineNumber" class="small InputBoxDisabled" disabled="disabled" value="${externalTransfer.lineNumber}"></td>
					</tr>
				</c:if>
            	<tr>
					<td class="label" width="20%"><bean:message key="externalTransfer.type"/></td>
					<td>
						<c:choose><c:when test="${editable}">
							<html:select property="externalTransfer(type)" styleClass="InputBoxDisabled" disabled="true">
								<html:option value=""></html:option>
								<c:forEach var="type" items="${externalAccount.types}">
									<html:option value="${type.id}">${type.name}</html:option>
								</c:forEach>
							</html:select>
						</c:when><c:otherwise>
							<input type="text" class="medium InputBoxDisabled" disabled="disabled" value="${externalTransfer.type.name}">
						</c:otherwise></c:choose>
					</td>
					<c:if test="${!isInsert}">
						<td class="label" width="20%"><bean:message key="externalTransfer.status"/></td>
						<td><input type="text" id="statusText" class="medium InputBoxDisabled" readonly="readonly" value="<bean:message key="externalTransfer.status.${externalTransfer.status}"/>"></td>
					</c:if>
				</tr>
				<tr>
					<td class="label" width="20%"><bean:message key="externalTransfer.date"/></td>
					<td><html:text property="externalTransfer(date)" disabled="true" styleClass="${editable ? 'date' : ''} small InputBoxDisabled"/></td>
					<td class="label" width="20%"><bean:message key="externalTransfer.amount"/></td>
					<td><html:text property="externalTransfer(amount)" disabled="true" styleClass="floatAllowNegative small InputBoxDisabled"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="member.username"/></td>
					<td>
						<html:hidden styleId="memberId" property="externalTransfer(member)"/>
						<input id="memberUsername" class="full InputBoxDisabled" disabled="disabled" value="${externalTransfer.member.username}">
						<div id="membersByUsername" class="autoComplete"></div>
					</td>
					<td class="label"><bean:message key="member.memberName"/></td>
					<td>
						<input id="memberName" class="full InputBoxDisabled" disabled="disabled" value="${externalTransfer.member.name}">
						<div id="membersByName" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label" width="20%" valign="top">
						<bean:message key="externalTransfer.description"/>
					</td>
					<td colspan="3">
						<html:textarea styleId="descriptionText" value="${externalTransfer.description}" property="externalTransfer(description)" rows="6" readonly="true" styleClass="full InputBoxDisabled"/>
					</td>
				</tr>
				<c:if test="${not empty externalTransfer.comments}">
					<tr>
						<td class="label" width="20%" valign="top">
							<bean:message key="externalTransfer.comments"/>
						</td>
						<td colspan="3">
							<textarea id="comments" class="full InputBoxDisabled" readonly="readonly" rows="7">${externalTransfer.comments}</textarea>
						</td>
					</tr>
				</c:if>
            	<c:if test="${editable}">
	            	<tr>
						<td align="right" colspan="4">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
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