<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/records/flatMemberRecords.js" />
<script>
	var removeConfirmation = "<cyclos:escapeJS><bean:message key='memberRecord.removeConfirmation' arg0="${type.name}"/></cyclos:escapeJS>";
	var elementNature = "${element.nature}";
	var currentElementId = "${element.id}"; 
</script>
<ssl:form action="${formAction}" method="post">
<html:hidden property="memberRecord(element)"/>
<html:hidden property="memberRecord(type)"/>

<c:if test="${!removed && canCreate}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key="memberRecord.title.flat" arg0="${type.label}" arg1="${element.name}"/></td>
			<cyclos:help page="member_records#remarks"/>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableForms">
				<table class="defaultTable">
				   	<c:forEach var="field" items="${customFields}">
			            <tr>
			                <td class="label">${field.name}</td>
			   				<td nowrap="nowrap">
			   					<cyclos:customField field="${field}" editable="true" fieldName="memberRecord(customValues).field" valueName="memberRecord(customValues).value"/>
			   				</td>
						</tr>
				    </c:forEach>
				   	<tr>
				   		<td colspan="2" align="right"><input type="submit" value="<bean:message key="global.submit"/>" class="button"></td>
				   	</tr>
				</table>
			</td>
		</tr>
	</table>
	
</c:if>
</ssl:form>

<c:choose><c:when test="${empty memberRecords}">
	<div class="footerNote"><bean:message key="memberRecord.search.noResults" arg0="${type.label}"/></div>
</c:when><c:otherwise>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable" style="height: 19px">${type.label}</td>
			<td class="tdHelpIcon">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableLists">
				<c:forEach var="memberRecord" items="${memberRecords}" varStatus="loop">
					<c:if test="${loop.count > 1}">
						<hr/>
					</c:if>
					<a name="memberRecord_${memberRecord.id}"></a>
					<table style="width:100%" cellspacing="0" cellpadding="0">
						<tr>
							<td>
					            <table style="width:100%" cellspacing="0" cellpadding="0">
					                <tr>
					                    <td class="tdHeaderContents" width="25%"><bean:message key="memberRecord.by"/></td>
				   						<td>${memberRecord.by.name} (<cyclos:format dateTime="${memberRecord.date}"/>)</td>
							   		</tr>
							   		<c:if test="${not empty memberRecord.modifiedBy}">
								   		<tr>
								   			<td class="tdHeaderContents"><bean:message key="memberRecord.modifiedBy"/></td>
								   			<td>${memberRecord.modifiedBy.name} (<cyclos:format dateTime="${memberRecord.lastModified}"/>)</td>
										</tr>
									</c:if>
									<c:forEach var="field" items="${customFields}">
								        <c:set var="value"><cyclos:customField field="${field}" collection="${memberRecord.customValues}" textOnly="true" /></c:set>
								        <c:if test="${not empty value}">
								        	<c:if test="${cyclos:name(field.control) != 'RICH_EDITOR'}">
								        		<c:set var="value"><cyclos:escapeHTML brOnly="true">${value}</cyclos:escapeHTML></c:set>
								        	</c:if>
								            <tr>
								                <td valign="top" class="tdHeaderContents">${field.name}</td>
								   				<td>${value}</td>
											</tr>
										</c:if>
								    </c:forEach>
					            </table>
					        </td>
					        <c:if test="${!removed && (canModify && type.editable) || canDelete}">
					        	<td width="16" valign="top">
				                    <c:if test="${canModify && type.editable}">
				                    	<img class="edit" style="margin-bottom:3px;" src="<c:url value="/pages/images/edit.gif"/>" memberRecordId="${memberRecord.id}" memberRecordTypeId="${memberRecord.type.id}" border="0">
				                    </c:if>
				                    <c:if test="${canDelete}">
			                    		<img class="remove" src="<c:url value="/pages/images/delete.gif"/>" memberRecordId="${memberRecord.id}" memberRecordTypeId="${memberRecord.type.id}" border="0">
			                    	</c:if>
					        	</td>
					        </c:if>
					    </tr>
				    </table>
	            </c:forEach>
			</td>
		</tr>
	</table>
</c:otherwise></c:choose>

<table class="defaultTableContentHidden">
	<tr>
		<td><input type="button" id="backButton" id="backButton" class="button" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
