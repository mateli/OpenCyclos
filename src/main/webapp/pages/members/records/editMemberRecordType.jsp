<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/records/editMemberRecordType.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="customField.removeConfirmation"/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}">
<html:hidden property="memberRecordTypeId"/>
<html:hidden property="memberRecordType(id)"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="memberRecordType.title.${isInsert ? 'insert' : 'modify'}"/></td>
        <cyclos:help page="member_records#edit_member_record_type"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
            		<td class="label" width="25%"><bean:message key="memberRecordType.name"/></td>
            		<td><html:text property="memberRecordType(name)" maxlength="100" readonly="true" styleClass="full InputBoxDisabled"/></td>
            	</tr>
            	<tr>
            		<td class="label" width="25%"><bean:message key="memberRecordType.label"/></td>
            		<td><html:text property="memberRecordType(label)" maxlength="100" readonly="true" styleClass="full InputBoxDisabled"/></td>
            	</tr>
				<tr>
            		<td class="label"><bean:message key="memberRecordType.description"/></td>
					<td><html:textarea styleId="descriptionText" property="memberRecordType(description)" rows="6" readonly="true" styleClass="full InputBoxDisabled"/></td>            		
            	</tr>
            	<tr>
            		<td class="label" valign="top"><bean:message key="memberRecordType.groups"/></td>
            		<td>
						<cyclos:multiDropDown name="memberRecordType(groups)" disabled="true" size="5">
							<c:forEach var="group" items="${groups}">
								<cyclos:option value="${group.id}" text="${group.name}" selected="${cyclos:contains(memberRecordType.groups, group)}"/>
							</c:forEach>
						</cyclos:multiDropDown>
            		</td>
            	</tr>
            	<tr>
                    <td class="label"><bean:message key="memberRecordType.layout"/></td>
                    <td>
                    	<html:select property="memberRecordType(layout)" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="layout" items="${layouts}">
	                    		<html:option value="${layout}"><bean:message key="memberRecordType.layout.${layout}"/></html:option>
	                   		</c:forEach>
                   		</html:select>
                    </td>
                </tr>
                <tr>
                    <td class="label"><bean:message key='memberRecordType.showMenuItem'/></td>
					<td><html:checkbox property="memberRecordType(showMenuItem)" styleClass="checkbox" disabled="true" value="true"/></td>
                </tr>
               	<tr>
                    <td class="label"><bean:message key='memberRecordType.editable'/></td>
					<td><html:checkbox property="memberRecordType(editable)" styleClass="checkbox" disabled="true" value="true"/></td>
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
</ssl:form>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>

<c:if test="${!isInsert}">
	
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key='memberRecordType.fields.title.list'/></td>
	        <cyclos:help page="member_records#member_record_type_fields_list"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                	<td class="tdHeaderContents"><bean:message key="customField.name"/></td>
	                    <td class="tdHeaderContents" width="10%">&nbsp;</td>
	                </tr>
	                <c:forEach var="field" items="${memberRecordType.fields}">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                	<td><cyclos:escapeHTML>${field.name}</cyclos:escapeHTML></td>
		                    <td align="center" nowrap="nowrap">
		                    	<c:choose><c:when test="${editable}">
				                    <img class="edit fieldDetails" src="<c:url value="/pages/images/edit.gif"/>" fieldId="${field.id}" border="0">
			                    	<img class="remove" src="<c:url value="/pages/images/delete.gif"/>" fieldId="${field.id}" border="0">
				                </c:when><c:otherwise>
					                <img class="view fieldDetails" src="<c:url value="/pages/images/view.gif"/>" fieldId="${field.id}" border="0">
								</c:otherwise></c:choose>
		                    </td>
		                </tr>
	                </c:forEach>                
	            </table>
	        </td>
	    </tr>
	</table>
	<c:if test="${editable}">
	
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right" width="50%">
					<c:if test="${fn:length(memberRecordType.fields) > 1}">
						<span class="label"><bean:message key="customField.action.changeOrder"/></span>
						<input type="button" class="button" id="changeOrderButton" value="<bean:message key="global.submit"/>">
					</c:if>
				</td>
				<td align="right" width="50%">
					<span class="label"><bean:message key='memberRecordType.fields.action.new'/></span>
					<input class="button" type="button" id="newButton" value="<bean:message key='global.submit'/>">
				</td>
			</tr>
		</table>
	</c:if>
</c:if>