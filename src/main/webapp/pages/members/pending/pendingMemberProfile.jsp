<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/pending/pendingMemberProfile.js" />

<ssl:form action="${formAction}" method="post" enctype="multipart/form-data">
<input type="hidden" name="pendingMemberId" value="${pendingMember.id}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="pendingMember.title.profile" /></td>
        <cyclos:help page="user_management#pending_member"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">

			<table class="defaultTable">
				<c:set var="group" value="${pendingMember.memberGroup}" />
				<tr>
					<td width="25%" class="label"><bean:message key="member.group"/></td>
					<td nowrap="nowrap" colspan="2"><input id="groupText" readonly="readonly" class="large InputBoxDisabled" value="${isAdmin || empty group.initialGroupShow ? group.name : group.initialGroupShow}"></td>
				</tr>
			    <tr>
			        <td class="label"><bean:message key="member.creationDate"/></td>
			        <td colspan="2" nowrap="nowrap"><input id="creationDateText" value="<cyclos:format date="${pendingMember.creationDate}"/>" readonly="readonly" class="small InputBoxDisabled"/></td>
			    </tr>
				<c:if test="${isAdmin}">
					<tr>
						<td class="label"><bean:message key="member.brokerUsername"/></td>
						<td colspan="2">
							<input type="hidden" name="pendingMember(broker)" id="brokerId" value="${pendingMember.broker.id}">
							<input id="brokerUsername" class="large InputBoxDisabled" readonly="readonly" value="${pendingMember.broker.username}">
							<div id="brokersByUsername" class="autoComplete"></div>
						</td>
					</tr>
					<tr>
						<td class="label"><bean:message key="member.broker"/></td>
						<td>
							<input id="brokerName" class="large InputBoxDisabled" readonly="readonly" value="${pendingMember.broker.name}">
							<div id="brokersByName" class="autoComplete"></div>
						</td>
					</tr>
			    </c:if>
				<c:if test="${cyclos:name(accessSettings.usernameGeneration) == 'NONE'}">
					<tr>
						<td class="label"><bean:message key="login.username"/></td>
						<td nowrap="nowrap" colspan="2"><input name="pendingMember(username)" readonly="readonly" class="medium InputBoxDisabled required" value="<cyclos:escapeHTML>${pendingMember.username}</cyclos:escapeHTML>"></td>
					</tr>
				</c:if>
				<tr>
					<td class="label"><bean:message key="member.name"/></td>
					<td nowrap="nowrap"><input name="pendingMember(name)" readonly="readonly" class="large InputBoxDisabled required" value="<cyclos:escapeHTML>${pendingMember.name}</cyclos:escapeHTML>"></td>
			        <td valign="bottom" class="label" style="text-align:left" width="15%" nowrap="nowrap"><cyclos:escapeHTML><bean:message key="profile.member.hide"/></cyclos:escapeHTML></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="member.email"/></td>
					<td nowrap="nowrap"><input name="pendingMember(email)" readonly="readonly" class="large InputBoxDisabled required" value="<cyclos:escapeHTML>${pendingMember.email}</cyclos:escapeHTML>"></td>
					<td nowrap="nowrap" align="left"><input type="checkbox" name="pendingMember(hideEmail)" class="checkbox" disabled="disabled" ${pendingMember.hideEmail ? 'checked' : ''}></td>
				</tr>
			    <c:forEach var="entry" items="${customFields}" varStatus="loop">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
			        <c:set var="hidden" value="${entry.value.hidden}"/>
			        <c:choose>
			            <c:when test="${entry.value.hidden}"> 
			                <c:set var="checked" value="checked='checked'"/>
			            </c:when><c:otherwise>
			                <c:set var="checked" value="" />
			            </c:otherwise>
			        </c:choose>
			        <tr>
						<td valign="top" class="label">${field.name}</td>
						<td width="15%" nowrap="nowrap">
							<input type="hidden" id="hidden_${field.id}" name="pendingMember(customValues).hidden" value="${hidden}">
							<cyclos:customField field="${field}" value="${value}" valueName="pendingMember(customValues).value" fieldName="pendingMember(customValues).field" enabled="false"/>
						</td>
						<td nowrap="nowrap" align="left">
							<c:if test="${field.memberCanHide}">
								<input type="checkbox" id="chk_hidden_${field.id}" class="checkbox" disabled="true" ${checked}>
							</c:if>
						</td>
					</tr>
			    </c:forEach>
			    <c:if test="${editable}">
					<tr>
						<td colspan="3" align="right">
							
							<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled value="<bean:message key="global.submit"/>">
						</td>
					</tr>
				</c:if>
			</table>
    	</td>
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td>
			<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
		</td>
		<c:if test="${editable}">
			<td align="right">
				<c:set var="lastTime"><cyclos:format date="${pendingMember.lastEmailDate}"/></c:set>
				<span class="label"><bean:message key="pendingMember.resendEmail" arg0="${lastTime}" /></span>
				<input type="button" class="button" id="resendEmailButton" value="<bean:message key="global.submit"/>"/>
			</td>
		</c:if>
	</tr>
</table>
</ssl:form>