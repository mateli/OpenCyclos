<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/loanGroups/viewLoanGroup.js" />
<script>
	var memberId = "${param.memberId}";
</script>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="loanGroup.title.view"/></td>
        <cyclos:help page="loan_groups#loan_group_detail${isAdmin ? '_by_admin' : '_by_member'}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                   	<td class="headerLabel" width="25%"><bean:message key="loanGroup.name"/></td>
                   	<td class="headerField" align="left">${loanGroup.name}</td>
            	</tr>
                <tr>
                   	<td class="headerLabel" valign="top"><bean:message key="loanGroup.description"/></td>
                   	<td class="headerField" align="left"><cyclos:escapeHTML>${loanGroup.description}</cyclos:escapeHTML></td>
            	</tr>
			    <c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value.value}"/>
		            <tr>
		                <td class="headerLabel">${field.name}</td>
		   				<td class="headerField" nowrap="nowrap">
		   					<cyclos:customField field="${field}" value="${value}" textOnly="true"/>
		   				</td>
					</tr>
			    </c:forEach>
            </table>
        </td>
    </tr>
</table>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="loanGroup.title.members"/></td>
        <cyclos:help page="loan_groups#loan_group_members${isAdmin ? '_by_admin' : '_by_member'}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <th class="tdHeaderContents" width="30%"><bean:message key='member.name'/></th>
                    <th class="tdHeaderContents"><bean:message key='member.username'/></th>
                    <c:if test="${editable}">
	                    <th class="tdHeaderContents" width="5%">&nbsp;</th>
	                </c:if>
                </tr>

				<c:forEach items="${loanGroup.members}" var="member">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="left"><cyclos:profile elementId="${member.id}" pattern="name"/></td>
	                    <td align="left"><cyclos:profile elementId="${member.id}" pattern="username"/></td>
	                    <c:if test="${editable}">
		                    <td align="center" nowrap="nowrap">
		                    	<img src="<c:url value="/pages/images/delete.gif"/>" class="remove removeMember" memberId="${member.id}"/>
		                    </td>
		                </c:if>
	                 </tr>
				</c:forEach>
            </table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden"><tr><td>
<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
</td></tr></table>