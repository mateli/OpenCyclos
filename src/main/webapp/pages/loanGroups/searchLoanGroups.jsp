<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/loanGroups/searchLoanGroups.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="loanGroup.removeConfirmation"/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="loanGroup.title.search"/></td>
        <cyclos:help page="loan_groups#search_loans_group"/>
    </tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<table class="defaultTable">
				<tr>
					<td class="label" width="25%"><bean:message key="loanGroup.name"/></td>
					<td><html:text property="query(name)" maxlength="50" size="40"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="loanGroup.description"/></td>
					<td><html:text property="query(description)" maxlength="50" size="40"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="member.username"/></td>
					<td>
						<html:hidden styleId="memberId" property="query(member)"/>
						<input id="memberUsername" size="40" value="${query.member.username}">
						<div id="membersByUsername" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="member.memberName"/></td>
					<td>
						<input id="memberName" size="40" value="${query.member.name}">
						<div id="membersByName" class="autoComplete"></div>
					</td>
				</tr>
          		<tr>
          			<c:set var="submitColspan" value="2" />
           			<c:if test="${cyclos:granted(AdminSystemPermission.LOAN_GROUPS_MANAGE)}">
           				<c:set var="submitColspan" value="4" />
	            		<td class="label">
	            			<bean:message key="loanGroup.action.create" />
		            	</td>
	    	        	<td>
	        				<input type="button" class="button linkButton" linkURL="editLoanGroup" value="<bean:message key="global.submit" />">
						</td>
					</c:if>
					<td colspan="${submitColspan}" align="right">
						<input type="submit" class="button" value="<bean:message key="global.search"/>">
					</td>
          		</tr>
			</table>
		</td>
	</tr>
</table>

<c:if test="${not empty loanGroups}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
	        <cyclos:help page="loan_groups#search_loans_group_result"/>
	    </tr>
		<tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents" width="30%"><bean:message key='loanGroup.name'/></th>
	                    <th class="tdHeaderContents"><bean:message key='loanGroup.description'/></th>
	                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
	                </tr>
					<c:forEach items="${loanGroups}" var="group">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="left">${group.name}</td>
		                    <td align="left"><cyclos:truncate value="${group.description}"/></td>
		                    <td align="center" nowrap="nowrap">
		                    	<c:choose><c:when test="${cyclos:granted(AdminSystemPermission.LOAN_GROUPS_MANAGE)}">
		            				<img src="<c:url value="/pages/images/edit.gif"/>" class="edit loanGroupDetails" loanGroupId="${group.id}"/>
		                    		<img src="<c:url value="/pages/images/delete.gif"/>" class="remove" loanGroupId="${group.id}"/>
		            			</c:when><c:otherwise>
									<img src="<c:url value="/pages/images/view.gif"/>" class="view loanGroupDetails" loanGroupId="${group.id}"/>
	        	    			</c:otherwise></c:choose>
		                    </td>
		                 </tr>
					</c:forEach>
				</table>
			</td>
	    </tr>
	</table>

	<table class="defaultTableContentHidden">
		<tr>
	        <td align="right"><cyclos:pagination items="${loanGroups}"/></td>
		</tr>
	</table>
</c:if>
</ssl:form>
