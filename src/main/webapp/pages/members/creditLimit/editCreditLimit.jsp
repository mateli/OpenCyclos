<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/creditLimit/editCreditLimit.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="memberId" />
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="creditLimit.title" arg0="${member.name}"/></td>
        <cyclos:help page="account_management#credit_limit"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
					<th width="28%" rowspan="2"></th>
					<th width="36%" colspan="2" class="tdHeaderContents"><bean:message key="creditLimit.normal"/></th>
					<th width="36%" colspan="2" class="tdHeaderContents"><bean:message key="creditLimit.upper"/></th>
				</tr>
				<tr>
					<th width="16%" class="tdHeaderContents"><bean:message key="creditLimit.current"/></th>
					<th width="16%" class="tdHeaderContents"><bean:message key="creditLimit.new"/></th>
					<th width="16%" class="tdHeaderContents"><bean:message key="creditLimit.current"/></th>
					<th width="16%" class="tdHeaderContents"><bean:message key="creditLimit.new"/></th>
				</tr>
                <c:forEach var="entry" items="${limits}">
					<tr>
						<td class="label">
							<input type="hidden" name="accountTypeIds" value="${entry.accountType.id}">
							${entry.accountType.name}
						</td>
						<td align="center"><cyclos:format number="${entry.creditLimit}"/></td>
						<td align="center"><input name="newCreditLimits" class="full floatNegative" style="text-align:right" value="<cyclos:format number="${entry.creditLimit}"/>"></td>
						<td align="center"><cyclos:format number="${entry.upperCreditLimit}" default="-"/></td>
						<td align="center"><input name="newUpperCreditLimits" class="full float" style="text-align:right" value="<cyclos:format number="${entry.upperCreditLimit}"/>"></td>
					</tr>
				</c:forEach>
				<tr>
					<td align="right" colspan="5"><input type="submit" class="button" value="<bean:message key="global.submit"/>"></td>
				</tr>
            </table>
		</td>
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left"><input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
</ssl:form>