<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/accounts/external/fileMappings/editFieldMapping.js" />

<c:choose><c:when test="${isInsert}">
	<c:set var="titleKey" value="fieldMapping.title.new"/>
</c:when><c:otherwise>
	<c:set var="titleKey" value="fieldMapping.title.modify"/>
</c:otherwise></c:choose>

<ssl:form method="post" action="${formAction}">
<html:hidden property="externalAccountId" value="${fieldMapping.fileMapping.account.id}"/>
<html:hidden property="fieldMapping(id)"/>
<html:hidden property="fieldMapping(fileMapping)"/>
<html:hidden property="fieldMapping(order)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
        <cyclos:help page="bookkeeping#edit_field_mapping"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<c:if test="${fieldMapping.order != null && fieldMapping.order > 0}">
	            	<tr>
	            		<td class="label" width="25%"><bean:message key="fieldMapping.order"/></td>
	            		<td>${fieldMapping.order}</td>
	            	</tr>
            	</c:if>
            	<tr>
            		<td class="label" width="25%"><bean:message key="fieldMapping.name"/></td>
            		<td>
            			<html:text property="fieldMapping(name)" readonly="true" styleClass="medium InputBoxDisabled"/>
            		</td>
            	</tr>
            	<tr>
            		<td class="label" width="25%"><bean:message key="fieldMapping.field"/></td>
            		<td>
            			<html:select styleId="fieldSelect" property="fieldMapping(field)" disabled="true" styleClass="InputBoxDisabled">
            				<c:forEach var="field" items="${fields}">
            					<html:option value="${field}"><bean:message key="fieldMapping.field.${field}"/></html:option>
            				</c:forEach>
	            		</html:select>
            		</td>
            	</tr>
            	<tr id="trMemberField" style="display:none">
            		<td class="label" width="25%"><bean:message key="fieldMapping.memberField"/></td>
            		<td>
            			<html:select styleId="memberFieldSelect" property="fieldMapping(memberField)" disabled="true" styleClass="InputBoxDisabled">
            				<c:forEach var="memberField" items="${memberFields}">
            					<html:option value="${memberField.id}">${memberField.name}</html:option>
            				</c:forEach>
	            		</html:select>
            		</td>
            	</tr>
            	<c:if test="${editable}">
	            	<tr>
						<td align="right" colspan="2">
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