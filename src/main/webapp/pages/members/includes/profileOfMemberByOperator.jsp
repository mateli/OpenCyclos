<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="profile.action.title" arg0="${member.name}"/></td>
        <cyclos:help page="profiles#actions_for_member_by_operator" />
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
			<cyclos:layout className="defaultTable" columns="4">
				<c:if test="${cyclos:granted(OperatorPermission.PAYMENTS_PAYMENT_TO_MEMBER)}">
					<cyclos:cell width="35%" className="label"><bean:message key="profile.action.payment"/></cyclos:cell>
					<cyclos:cell align="left"><input type="button" class="linkButton" linkURL="payment?to=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
               	<c:if test="${cyclos:granted(OperatorPermission.REFERENCES_VIEW)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.references"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="references?nature=GENERAL&memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
                </c:if>
               	<c:if test="${hasTransactionFeedbacks}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.transactionFeedbacks"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="references?nature=TRANSACTION&memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
                </c:if>
               	<c:if test="${cyclos:granted(OperatorPermission.INVOICES_SEND_TO_MEMBER)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.sendInvoice"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="sendInvoice?to=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
                </c:if>
                <c:if test="${cyclos:granted(MemberPermission.ADS_VIEW)}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.viewAds"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="memberAds?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
                </c:if>
                <c:if test="${not empty member.email and not member.hideEmail}">
	                <cyclos:cell width="35%" className="label"><bean:message key="profile.action.mail"/></cyclos:cell>
	                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="mailto:${member.email}" value="<bean:message key="global.submit"/>"></cyclos:cell>
				</c:if>
                <c:if test="${operatorCanViewReports}">
                    <cyclos:cell width="35%" className="label"><bean:message key="profile.action.activities"/></cyclos:cell>
                    <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="activities?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
                </c:if>
                <c:if test="${cyclos:granted(OperatorPermission.CONTACTS_MANAGE)}">
	                <cyclos:cell width="35%" className="label"><bean:message key="profile.action.addContact"/></cyclos:cell>
	                <cyclos:cell align="left"><input type="button" class="linkButton" linkURL="addContact?memberId=${member.id}" value="<bean:message key="global.submit"/>"></cyclos:cell>
	            </c:if>
            </cyclos:layout>
       	</td>
   	</tr>
</table>
