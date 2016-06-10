<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/pending/searchPendingMembers.js" />
<c:set var="timePeriod">${localSettings.deletePendingRegistrationsAfter.number} <bean:message key="global.timePeriod.${localSettings.deletePendingRegistrationsAfter.field}" /></c:set>
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="pendingMember.removeConfirmation" arg0="${timePeriod}" /></cyclos:escapeJS>";
</script>
<ssl:form method="post" action="${formAction}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="pendingMember.title.search"/></td>
        <cyclos:help page="user_management#search_pending_member"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
          		<tr>
            		<td class="label" width="25%"><bean:message key="member.name"/></td>
            		<td><html:text property="query(name)" styleClass="InputBoxEnabled large"/></td>
          		</tr>
          		<c:if test="${not empty groups}">
	         		<tr>
	           			<td class="label"><bean:message key="member.group"/></td>
	           			<td>
	           				<cyclos:multiDropDown name="query(groups)" varName="groupsSelect" emptyLabelKey="member.search.allGroups">
		           				<c:forEach var="group" items="${groups}">
		           					<cyclos:option value="${group.id}" text="${isAdmin || empty initialGroupShow ? group.name : group.initialGroupShow}" />
		           				</c:forEach>
	             			</cyclos:multiDropDown>
	             			<input type="hidden" name="query(groups)" value=""/>
	             		</td>
	         		</tr>
         		</c:if>
				<c:if test="${isAdmin}">
					<tr>
						<td class="label"><bean:message key="member.brokerUsername"/></td>
						<td>
							<html:hidden property="query(broker)" styleId="brokerId"/>
							<input id="brokerUsername" class="large" value="${query.broker.username}">
							<div id="brokersByUsername" class="autoComplete"></div>
						</td>
					</tr>
					<tr>
						<td class="label"><bean:message key="member.broker"/></td>
						<td>
							<input id="brokerName" class="large" value="${query.broker.name}">
							<div id="brokersByName" class="autoComplete"></div>
						</td>
					</tr>
          		</c:if>
				<tr>
           			<td class="nestedLabel">
           				<span class="label"><bean:message key="pendingMember.search.date"/></span>
           				<span class="lastLabel"><bean:message key="global.range.from"/></span>
           			</td>
           			<td>
           				<html:text property="query(creationPeriod).begin" styleClass="InputBoxEnabled date small"/>
           				&nbsp;
           				<span class="label"><bean:message key="global.range.to"/></span>
           				<html:text property="query(creationPeriod).end" styleClass="InputBoxEnabled date small"/>
           			</td>
				</tr>
			    <c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value.value}"/>
		            <tr>
		                <td class="label">${field.name}</td>
		   				<td>
		   					<cyclos:customField field="${field}" value="${value}" search="true" valueName="query(customValues).value" fieldName="query(customValues).field"/>
		   				</td>
					</tr>
			    </c:forEach>
          		<tr>
					<td align="right" colspan="2">
						<input type="submit" class="button" value="<bean:message key="global.search"/>">
					</td>
				</tr>
        	</table>
        </td>
    </tr>
</table>
</ssl:form>

<c:choose><c:when test="${empty pendingMembers}">
	<div class="footerNote" helpPage="user_management#search_pending_member_result"><bean:message key="member.search.noResults"/></div>
</c:when><c:otherwise>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
	        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
	       		<img class="exportCSV" src="<c:url value="/pages/images/save.gif"/>" border="0">
	       		<img class="print" src="<c:url value="/pages/images/print.gif"/>" border="0">
		        <cyclos:help page="user_management#search_pending_member_result" td="false"/>
	        </td>
	        
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	        	<c:set var="manualUsername" value="${cyclos:name(accessSettings.usernameGeneration) == 'NONE'}" />
	            <table class="defaultTable" cellspacing="0" cellpadding="0">
	                <tr>
	                	<c:if test="${manualUsername}">
		                    <td class="tdHeaderContents"><bean:message key="member.username"/></td>
	                	</c:if>
						<td class="tdHeaderContents" align="center"><bean:message key="member.name"/></td>
						<td class="tdHeaderContents" width="10%" nowrap="nowrap" align="center"><bean:message key="member.creationDate"/></td>
						<td class="tdHeaderContents" width="40">&nbsp;</td>
	                </tr>
					<c:forEach var="pendingMember" items="${pendingMembers}">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                	<c:if test="${manualUsername}">
			                    <td>${pendingMember.username}</td>
			                </c:if>
		                    <td>${pendingMember.name}</td>
		                    <td nowrap="nowrap" align="center"><cyclos:format date="${pendingMember.creationDate}" /></td>
		                    <td nowrap="nowrap" width="40" align="right">
		                    	<img class="edit" pendingMemberId="${pendingMember.id}" src="<c:url value="/pages/images/edit.gif"/>"/>
		                    	<img class="remove" pendingMemberId="${pendingMember.id}" src="<c:url value="/pages/images/delete.gif"/>"/>
		                    </td>
		                </tr>
		            </c:forEach>
	            </table>
	        </td>
	    </tr>
	</table>
	
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right"><cyclos:pagination items="${pendingMembers}"/></td>
		</tr>
	</table>		
</c:otherwise></c:choose>
