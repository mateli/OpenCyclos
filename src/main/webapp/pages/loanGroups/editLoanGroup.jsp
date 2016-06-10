<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:set var="memberMessage"><bean:message key="member.member" /></c:set>
<cyclos:script src="/pages/loanGroups/editLoanGroup.js" />
<script>
	var removeMemberConfirmationMessage = "<cyclos:escapeJS><bean:message key="loanGroup.removeMemberConfirmation"/></cyclos:escapeJS>";
	var memberRequiredError = "<cyclos:escapeJS><bean:message key="errors.required" arg0="${memberMessage}"/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}">
<html:hidden property="loanGroupId"/>
<html:hidden property="memberId"/>
<html:hidden property="loanGroup(id)"/>

<c:set var="isInsert" value="${empty loanGroup.id}"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="loanGroup.title.${isInsert ? 'insert' : 'modify'}"/></td>
        <c:choose>
        	<c:when test="${isInsert}">
		        <cyclos:help page="loan_groups#create_loan_group"/>
        	</c:when>
        	<c:otherwise>
		        <cyclos:help page="loan_groups#loan_group_detail${isAdmin ? '_by_admin' : '_by_member'}"/>
        	</c:otherwise>
        </c:choose>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                   	<td class="label" width="25%"><bean:message key="loanGroup.name"/></td>
                   	<td align="left"><html:text property="loanGroup(name)" styleClass="full ${isInsert ? 'InputBoxEnabled' : 'InputBoxDisabled'} required" readonly="${!isInsert}"/></td>
            	</tr>
                <tr>
                   	<td class="label" valign="top"><bean:message key="loanGroup.description"/></td>
                   	<td align="left"><html:textarea styleId="descriptionText" property="loanGroup(description)" rows="5" styleClass="full ${isInsert ? 'InputBoxEnabled' : 'InputBoxDisabled'}" readonly="${!isInsert}"/></td>
            	</tr>
			    <c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
		            <tr>
		                <td class="label">${field.name}</td>
		   				<td nowrap="nowrap">
		   					<cyclos:customField field="${field}" value="${value}" editable="true" valueName="loanGroup(customValues).value" fieldName="loanGroup(customValues).field" enabled="${isInsert}"/>
		   				</td>
					</tr>
			    </c:forEach>
                <tr>
                   	<td colspan="2" align="right">
                   		
                   		<c:choose><c:when test="${isInsert}">
							<input type="submit" value="<bean:message key="global.submit"/>" class="button">
						</c:when><c:otherwise>
	                   		<input type="button" id="viewLoansButton" class="button" value="<bean:message key="loanGroup.action.viewLoans"/>">&nbsp;
	                   		<input type="button" id="grantLoanButton" class="button" value="<bean:message key="loanGroup.action.grantLoan"/>">&nbsp;
	                   		<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
							<input type="submit" id="saveButton" value="<bean:message key="global.submit"/>" class="ButtonDisabled" disabled>
                   		</c:otherwise></c:choose>
                   	</td>
            	</tr>
            </table>
        </td>
    </tr>
</table>
</ssl:form>

<c:if test="${!isInsert}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="loanGroup.title.members"/></td>
	        <cyclos:help page="loan_groups#loan_group_members${isAdmin ? '_by_admin' : '_by_member'}"/>
	    </tr>
		<tr>
			<td colspan="2" align="left" class="bordered" style="border-top:none;">
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label tdHeaderContents" style="padding-left:5px;" nowrap="nowrap"><bean:message key="member.username"/>&nbsp;</td>
						<td class="tdHeaderContents" width="20%">
							<input type="hidden" id="foundMemberId"/>
							<input style="width:100%" id="memberUsername">
							<div id="membersByUsername" class="autoComplete"></div>
						</td>
						<td class="label tdHeaderContents" style="padding-left:5px;" nowrap="nowrap"><bean:message key="member.name"/>&nbsp;</td>
						<td class="tdHeaderContents" width="30%">
							<input style="width:100%" id="memberName">
							<div id="membersByName" class="autoComplete"></div>
						</td>
						<td class="tdHeaderContents">
							<input type="button" class="button" id="addMemberButton" value="<bean:message key="global.add"/>">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableForms">
	            <table class="defaultTable">
	                <tr>
	                    <th class="tdHeaderContents" width="30%"><bean:message key='member.username'/></th>
	                    <th class="tdHeaderContents"><bean:message key='member.name'/></th>
		                <th class="tdHeaderContents" width="5%">&nbsp;</th>
	                </tr>
	
					<c:forEach items="${loanGroup.members}" var="member">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="left"><cyclos:profile elementId="${member.id}" pattern="username"/></td>
		                    <td align="left"><cyclos:profile elementId="${member.id}" pattern="name"/></td>
		                    <td align="center" nowrap="nowrap">
		                    	<c:if test="${cyclos:contains(managesGroups, member.group)}">
			                    	<img src="<c:url value="/pages/images/delete.gif"/>" class="remove removeMember" memberId="${member.id}"/>
		                    	</c:if>
		                    </td>
		                 </tr>
					</c:forEach>
	            </table>
	        </td>
	    </tr>
</table>

</c:if>

<table class="defaultTableContentHidden"><tr><td>
<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
</td></tr></table>
