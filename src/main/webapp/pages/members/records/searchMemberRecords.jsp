<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/records/searchMemberRecords.js" />
<script>
	var removeConfirmation = "<cyclos:escapeJS><bean:message key='memberRecord.removeConfirmation' arg0="${type.name}"/></cyclos:escapeJS>";
	var elementNature = "${element.nature}"
</script>

<c:set var="global" value="${searchMemberRecordsForm.global}" />
<ssl:form method="post" action="${formAction}">
<c:if test="${!global}">
	<html:hidden property="elementId" value="${element.id}" />
</c:if>
<html:hidden property="typeId"/>
<html:hidden property="global"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0" >
	<tr>
		<td class="tdHeaderTable">
			<bean:message key="memberRecord.title.search.${global ? 'global' : 'member'}" arg0="${type.label}" arg1="${element.name}"/>
		</td>
		<c:choose>
        	<c:when test='${global}'>
            	<cyclos:help page="member_records#search_member_records"/>
        	</c:when>
        	<c:otherwise>
            	<cyclos:help page="member_records#search_records_of_member"/>
        	</c:otherwise>
    	</c:choose>
	</tr>
	<tr>
		<td align="left" class="tdContentTableForms" colspan="2">
		  	<table class="defaultTable" border="0">
		  		<tr>
					<td nowrap="nowrap" width="20%" class="label"><bean:message key="memberRecord.search.keywords"/></td>
					<td nowrap="nowrap" align="left" colspan="3"><html:text property="query(keywords)" styleId='keywords' styleClass="full"/></td> 
				</tr>
				<c:if test="${global}">
					<tr>
						<td class="label" width="20%"><bean:message key="member.username"/></td>
						<td width="30%">
							<html:hidden styleId="queryElementId" property="queryElementId"/>
							<input id="memberUsername" class="full" value="${query.element.username}">
							<div id="membersByUsername" class="autoComplete"></div>
						</td>
						<td class="label" width="20%"><bean:message key="member.memberName"/></td>
						<td width="30%">
							<input id="memberName" class="full" value="${query.element.name}">
							<div id="membersByName" class="autoComplete"></div>
						</td>
					</tr>
					<c:if test="${isAdmin}">
						<tr>
							<td class="label"><bean:message key="member.brokerUsername"/></td>
							<td>
								<html:hidden styleId="queryBrokerId" property="query(broker)"/>
								<input id="brokerUsername" class="full" value="${query.broker.username}">
								<div id="brokersByUsername" class="autoComplete"></div>
							</td>
							<td class="label"><bean:message key="member.brokerName"/></td>
							<td>
								<input id="brokerName" class="full" value="${query.broker.name}">
								<div id="brokersByName" class="autoComplete"></div>
							</td>
						</tr>
					</c:if>
				</c:if>
			    <c:forEach var="entry" items="${customValues}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value.value}"/>
		            <tr>
		                <td class="label">${field.name}</td>
		                <td colspan="3"><cyclos:customField field="${field}" value="${value}" search="true" valueName="query(customValues).value" fieldName="query(customValues).field"/></td>
					</tr>
			    </c:forEach>
			    <tr>
					<td class="label" nowrap="nowrap" width="20%"><bean:message key='memberRecord.search.fromDate'/>&nbsp;</td>
	       			<td nowrap="nowrap" colspan="3"> 
		       			<html:text styleClass="date small" property="query(period).begin"/>&nbsp;&nbsp;
	       				<span class="inlineLabel"><bean:message key='global.range.to'/>&nbsp;</span>
	       				<html:text styleClass="date small" property="query(period).end"/>
	         		</td>
	         	</tr>
	         	<tr>
	         		<td colspan="4"> </td>
	         	</tr>
	         	<tr>
            		<td colspan="4">
            			<input type="submit" id="searchButton" class="button" value="<bean:message key="global.search"/>" style="float:right">
            			<c:if test="${canCreate && !global}">
	            			<span style="margin-left:50px" class="label"><bean:message key="memberRecord.action.new" arg0="${type.name}"/></span>
            				<input type="button" class="button" id="newButton" value="<bean:message key="global.submit" />">
            			</c:if>
            		</td>
          		</tr>
			</table>
		</td>
	</tr>
</table>
</ssl:form>

			
<c:if test="${queryExecuted}">
<%-- The query was executed. Show the member records or a no results message --%>
<c:choose><c:when test="${empty memberRecords}">
	<div class="footerNote"><bean:message key="memberRecord.search.noResults" arg0="${type.label}"/></div>
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<c:if test="${!global}">
		<tr>
			<td><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
		</tr>
	</c:if>
	</table>	
</c:when><c:otherwise>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable">${type.label}</td>
	        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
	       		<img class="exportCSV" src="<c:url value="/pages/images/save.gif"/>" border="0">
		        <cyclos:help page="member_records#member_records_search_results" td="false"/>
	        </td>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	            	<tr>
	            		<td class="tdHeaderContents" width="15%"><bean:message key="memberRecord.date"/></td>
	            		<td class="tdHeaderContents"><bean:message key="memberRecord.user"/></td>
	            		<c:forEach var="field" items="${fieldsOnList}">
	            			<td class="tdHeaderContents">${field.name}</td>
	            		</c:forEach>
	            		<td class="tdHeaderContents" width="10%">&nbsp;</td>
	            	</tr>
	            	<c:forEach var="memberRecord" items="${memberRecords}">
		            	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		            		<td align="left"><cyclos:format date="${memberRecord.date}"/></td>
		            		<td align="left"><cyclos:profile elementId="${memberRecord.element.id}"/></td>
		            		<c:forEach var="field" items="${fieldsOnList}">
		            			<td align="left">
		            				<cyclos:truncate html="${cyclos:name(field.control) == 'RICH_EDITOR'}">
		            					<jsp:attribute name="value">
		            						<cyclos:customField textOnly="true" encodeHtml="false" field="${field}" collection="${memberRecord.customValues}" />
		            					</jsp:attribute>
		            				</cyclos:truncate>
		            			</td>
		            		</c:forEach>
		            		<td align="center">
								<c:choose><c:when test="${canModify && type.editable}">
				                    <img class="edit memberRecordDetails" src="<c:url value="/pages/images/edit.gif"/>" memberRecordId="${memberRecord.id}" memberRecordTypeId="${memberRecord.type.id}" border="0">
				                </c:when><c:otherwise>
					                <img class="view memberRecordDetails" src="<c:url value="/pages/images/view.gif"/>" memberRecordId="${memberRecord.id}" memberRecordTypeId="${memberRecord.type.id}" border="0">
								</c:otherwise></c:choose>
								<c:if test="${canDelete}">
									<img class="remove" src="<c:url value="/pages/images/delete.gif"/>" memberRecordId="${memberRecord.id}" memberRecordTypeId="${memberRecord.type.id}" border="0">
								</c:if>
		            		</td>
		            	</tr>
	            	</c:forEach>
	            </table>
	        </td>
		</tr>
	</table>
	
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<c:if test="${!global}">
				<td><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
			</c:if>
			<td align="right"><cyclos:pagination items="${memberRecords}"/></td>
		</tr>
	</table>	
</c:otherwise></c:choose>
</c:if>