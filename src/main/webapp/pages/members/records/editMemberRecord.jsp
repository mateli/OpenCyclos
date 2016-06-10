<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/records/editMemberRecord.js" />
<script>
	var isFlat = ${cyclos:name(type.layout) == 'FLAT'};
	var returnToSearch = ${searchMemberRecordsForm.global == true};
	var baseMemberRecordId = "${baseMemberRecord.id}";
</script>
<ssl:form action="${formAction}" method="post">
<html:hidden property="elementId" value="${element.id}" />
<html:hidden property="typeId" value="${type.id}" />
<html:hidden property="memberRecord(id)" value="${memberRecord.id}" />
<html:hidden property="global" value="${global}" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="memberRecord.title.${memberRecord.persistent ? 'edit' : 'insert'}" arg0="${type.name}" arg1="${element.name}" /></td>
		<cyclos:help page="member_records#member_records"/>
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<table class="defaultTable">
				<c:if test="${isAdmin and memberRecord.persistent}">
				   	<tr>
				   		<td colspan="2">
				   			<fieldset>
				   				<table class="defaultTable">
				   					<tr>
				   						<td width="25%" class="label"><bean:message key="memberRecord.by"/></td>
				   						<td>${memberRecord.by.name}</td>
				   						<td class="label"><bean:message key="memberRecord.date"/></td>
				   						<td><cyclos:format dateTime="${memberRecord.date}"/></td>
				   					</tr>
				   					<c:if test="${not empty memberRecord.modifiedBy}">
					   					<tr>
					   						<td class="label"><bean:message key="memberRecord.modifiedBy"/></td>
					   						<td>${memberRecord.modifiedBy.name}</td>
					   						<td class="label"><bean:message key="memberRecord.lastModified"/></td>
					   						<td><cyclos:format dateTime="${memberRecord.lastModified}"/></td>
										</tr>
									</c:if>
				   				</table>
				   			</fieldset>
				   		</td>
				   	</tr>
			   	</c:if>

				<c:forEach var="entry" items="${customFieldEntries}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
		            <tr>
		                <td class="label" width="25%">${field.name}</td>
		   				<td>
		   					<cyclos:customField field="${field}" value="${value}" editable="${!cyclos:contains(readOnlyFields, field)}" valueName="memberRecord(customValues).value" fieldName="memberRecord(customValues).field" enabled="false"/>
		   				</td>
					</tr>
			    </c:forEach>
			   	
			   	<c:if test="${ (isInsert && canCreate) || (!isInsert && type.editable && canModify) }">
					<tr>
						<td align="right" colspan="2">
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
</ssl:form>

<table class="defaultTableContentHidden">
	<tr>
		<td><input type="button" id="backButton" memberId="${member.id}" id="backButton" class="button" value="<bean:message key="global.back"/>"></td>
		<c:if test="${canCreate and memberRecord.persistent and not global}">
			<td align="right" id="tdCopyButton">
				<span class="label"><bean:message key="memberRecord.action.newBasedOnThis" arg0="${type.name}" /></span>
				<input type="button" id="copyButton" value="<bean:message key="global.submit"/>" class="button">
			</td>
		</c:if>
	</tr>
</table>
