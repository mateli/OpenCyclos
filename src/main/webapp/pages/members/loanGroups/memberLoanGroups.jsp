<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<c:choose>
	<c:when test="${myLoanGroups}">
		<c:set var="titleKey" value="loanGroup.title.my"/>
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="loanGroup.title.of"/>
	</c:otherwise>
</c:choose>

<cyclos:script src="/pages/members/loanGroups/memberLoanGroups.js" />
<script>
	var memberId = "${member.id}";
</script>
<c:if test="${editable}">
	<script>
		var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="loanGroup.removeFromMemberConfirmation"/></cyclos:escapeJS>";
		var addConfirmationMessage = "<cyclos:escapeJS><bean:message key="loanGroup.addMemberConfirmation"/></cyclos:escapeJS>";
	</script>
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}" arg0="${member.name}"/></td>
        <cyclos:help page="loan_groups#member_loan_groups${isAdmin ? '_by_admin' : '_by_member'}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                    <th class="tdHeaderContents"><bean:message key='loanGroup.loanGroup'/></th>
                    <th class="tdHeaderContents" width="5%">&nbsp;</th>
                </tr>
				<c:forEach var="loanGroup" items="${loanGroups}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="left">${loanGroup.name}</td>
	                    <td align="center" nowrap="nowrap">
   	                		<img class="view" src="<c:url value="/pages/images/view.gif" />" loanGroupId="${loanGroup.id}" />
		                    <c:if test="${editable}">
    	                		<img class="remove" src="<c:url value="/pages/images/delete.gif" />" loanGroupId="${loanGroup.id}" />
		                    </c:if>
	           	        </td>
	                 </tr>
				</c:forEach>
			</table>
		</td>
    </tr>
</table>


<c:if test="${editable and not empty unrelatedLoanGroups}">
	<ssl:form method="post" styleId="addForm" style="display:inline" action="${actionPrefix}/addMemberToLoanGroup">
	<html:hidden property="memberId" value="${member.id}"/>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="loanGroup.title.addMember" arg0="${member.name}"/></td>
	        <cyclos:help page="loan_groups#add_member_loan_groups"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableForms">
	            <table class="defaultTable">
	          		<tr>
	            		<td class="label"><bean:message key="loanGroup.loanGroup"/></td>
	            		<td>
			    			<html:select property="loanGroupId">
			    				<c:forEach var="loanGroup" items="${unrelatedLoanGroups}">
				    				<html:option value="${loanGroup.id}">${loanGroup.name}</html:option>
				    			</c:forEach>
			    			</html:select>
	            		</td>
	            		<td align="right"><input type="submit" class="button" value="<bean:message key="global.submit"/>"></td>
	          		</tr>
	        	</table>
			</td>
	    </tr>
	</table>
	</ssl:form>
	
</c:if>

<c:if test="${!myLoanGroups}">
	<table class="defaultTableContentHidden"><tr><td>
	<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
	</td></tr></table>
</c:if>