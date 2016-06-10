<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/imports/importedMembersSummary.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="importId" />
<c:set var="emptyAccountType"><bean:message key="memberImport.accountType.empty"/></c:set>
<c:set var="emptyCreditType"><bean:message key="memberImport.initialCreditTransferType.empty"/></c:set>
<c:set var="emptyDebitType"><bean:message key="memberImport.initialDebitTransferType.empty"/></c:set>
<c:set var="canImport" value="${(summary.total - summary.errors) > 0}" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="memberImport.title.summary"/></td>
        <cyclos:help page="user_management#imported_members_summary"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="headerLabel" width="50%"><bean:message key="memberImport.group"/></td>
                    <td class="headerField">${memberImport.group.name}</td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="memberImport.accountType"/></td>
                    <td class="headerField">${empty memberImport.accountType ? emptyAccountType : memberImport.accountType.name}</td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="memberImport.initialCreditTransferType"/></td>
                    <td class="headerField">${empty memberImport.initialCreditTransferType ? emptyCreditType : memberImport.initialCreditTransferType.name}</td>
                </tr>
                <c:if test="${not empty memberImport.initialCreditTransferType}">
	                <tr>
	                    <td class="headerLabel"><bean:message key="memberImport.initialCredits"/></td>
	                    <td class="headerField">${summary.credits.count} / <cyclos:format number="${summary.credits.amount}" unitsPattern="${memberImport.accountType.currency.pattern}" /></td>
	                </tr>
                </c:if>
                <tr>
                    <td class="headerLabel"><bean:message key="memberImport.initialDebitTransferType"/></td>
                    <td class="headerField">${empty memberImport.initialDebitTransferType ? emptyDebitType : memberImport.initialDebitTransferType.name}</td>
                </tr>
                <c:if test="${not empty memberImport.initialDebitTransferType}">
	                <tr>
	                    <td class="headerLabel"><bean:message key="memberImport.initialDebits"/></td>
	                    <td class="headerField">${summary.debits.count} / <cyclos:format number="${summary.debits.amount}" unitsPattern="${memberImport.accountType.currency.pattern}" /></td>
	                </tr>
                </c:if>
                <tr>
                    <td class="headerLabel"><bean:message key="memberImport.totalMembers"/></td>
                    <td class="headerField"><a id="totalLink" class="default">${summary.total}</a></td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="memberImport.successfulMembers"/></td>
                    <td class="headerField"><a id="successLink" class="default">${summary.total - summary.errors}</a></td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="memberImport.membersWithErrors"/></td>
                    <td class="headerField"><a id="errorLink" class="default">${summary.errors}</a></td>
                </tr>
                <c:if test="${canImport}">
	                <tr>
	                    <td class="headerLabel"><bean:message key="memberImport.sendActivationMail"/></td>
	                    <td class="headerField"><html:checkbox property="sendActivationMail" value="true" /></td>
	                </tr>
                </c:if>
                <tr>
					<td><input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>" ></td>
					<c:if test="${canImport}">
						<td align="right"><input type="submit" id="confirmButton" class="button" value="<bean:message key="memberImport.confirm"/>" ></td>
					</c:if>
	            </tr>
             </table>
        </td>
    </tr>
</table>
</ssl:form>