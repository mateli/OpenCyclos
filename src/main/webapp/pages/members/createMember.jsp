<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<c:set var="numericPassword" value="${ofAdmin ? false : accessSettings.numericPassword}"/>

<cyclos:script src="/pages/members/createMember.js" />
<script>
	var isPublic = ${empty isPublic ? false : isPublic};
	var agreementPrintTitle = "<cyclos:escapeJS>${localSettings.applicationName} - <bean:message key="group.registrationAgreement" /></cyclos:escapeJS>";
	var registrationAgreementNotCheckedMessage = "<cyclos:escapeJS><bean:message key="createMember.error.registrationAgreementCheck" /></cyclos:escapeJS>";
</script>
<c:choose>
	<c:when test="${isPublic}">
		<%-- Public member registration --%>
		<c:set var="titleKey" value="createMember.title.public"/>
		<c:set var="helpPage" value="registration#public_create_member"/>
	</c:when>
	<c:when test="${byBroker}">
		<%-- Member registration by broker --%>
		<c:set var="titleKey" value="createMember.title.byBroker"/>
		<c:set var="helpPage" value="user_management#create_user_for_broker"/>
	</c:when>
	<c:otherwise>
		<%-- Member registration by administrator --%>
		<c:set var="titleKey" value="createMember.title.byAdmin"/>
		<c:set var="helpPage" value="user_management#create_user"/>
	</c:otherwise>
</c:choose>

<ssl:form action="${formAction}" method="post">
<html:hidden property="member(group)" value="${createMemberForm.groupId}"/>
<html:hidden property="groupId"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
        <cyclos:help page="${helpPage}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">

<table class="defaultTable">
	<c:if test="${isAdmin}">
		<tr>
			<td width="30%" class="label"><bean:message key="member.group"/></td>
			<td colspan="2"><input type="text" class="InputBoxDisabled large" readonly="true" value="${group.name}"/></td>
        </tr>
	</c:if>
	<c:if test="${not accessSettings.usernameGenerated}">
		<tr>
			<td width="30%" class="label"><bean:message key="login.username"/></td>
			<td valign="top" colspan="2"><html:text property="member(user).username" maxlength="${accessSettings.usernameLength.max}" styleClass="large InputBoxEnabled required"/></td>
		</tr>
	</c:if>
	<tr>
		<td width="30%" class="label"><bean:message key="member.name"/></td>
		<td><html:text property="member(name)" styleClass="large InputBoxEnabled required"/></td>
        <td valign="bottom" class="label" style="text-align:left" width="15%" nowrap="nowrap"><cyclos:escapeHTML><bean:message key="profile.member.hide"/></cyclos:escapeHTML></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="member.email"/></td>
		<td><html:text property="member(email)" styleClass="large InputBoxEnabled ${localSettings.emailRequired ? 'required' : ''}"/></td>
		<td nowrap="nowrap" valign="top" align="left"><html:checkbox property="member(hideEmail)" styleClass="checkbox" /></td>
	</tr>
    <c:forEach var="field" items="${customFields}">
		<tr>
			<td valign="top" class="label">${field.name}</td>
			<td>
				<c:if test="${field.memberCanHide}">
					<input type="hidden" id="hidden_${field.id}" name="member(customValues).hidden" value="false">
				</c:if>
				<cyclos:customField field="${field}" valueName="member(customValues).value" fieldName="member(customValues).field"/>
			</td>
			<td nowrap="nowrap" valign="top" align="left">
				<c:if test="${field.memberCanHide}">
					<input type="checkbox" id="chk_hidden_${field.id}" class="checkbox">
				</c:if>
			</td>
		</tr>
    </c:forEach>

	<c:if test="${allowSetBroker}">
		<tr>
			<td class="label"><bean:message key="createMember.assignBroker"/></td>
			<td><input type="checkbox" class="checkbox" id="assignBrokerCheck"></td>
		</tr>
		<tr class="trBroker" style="display:none">
			<td class="label"><bean:message key="member.brokerUsername"/></td>
			<td>
				<html:hidden property="member(broker)" styleId="newBrokerId"/>
				<input id="brokerUsername" class="large" size="20">
				<div id="brokersByUsername" class="autoComplete"></div>
			</td>
		</tr>
		<tr class="trBroker" style="display:none">
			<td class="label"><bean:message key="member.brokerName"/></td>
			<td>
				<input id="brokerName" class="large" size="40">
				<div id="brokersByName" class="autoComplete"></div>
			</td>
		</tr>
	</c:if>

	<c:if test="${allowAutomaticPassword}">
		<tr>
			<td class="label"><bean:message key="createMember.assignPassword"/></td>
			<td><input type="checkbox" class="checkbox" id="assignPasswordCheck" name="manualPassword" value="true"></td>
		</tr>
	</c:if>
	
	<c:if test="${allowSetPassword}">
		<c:if test="${not allowAutomaticPassword}">
			<input type="hidden" name="manualPassword" value="true"></input>
		</c:if>
		
		<tr class="trPassword" style="display: ${allowAutomaticPassword ? 'none' : ''}">
			<td class="label"><bean:message key="createMember.password"/></td>
			<td><input type="password" name="member(user).password" size="20" class="small InputBoxEnabled ${numericPassword ? 'number' : ''} ${allowAutomaticPassword ? '' :'required'}"/></td>
		</tr>
		<tr class="trPassword" style="display: ${allowAutomaticPassword ? 'none' : ''}">
			<td class="label"><bean:message key="createMember.passwordConfirmation"/></td>
			<td><input type="password" name="confirmPassword" size="20" class="small InputBoxEnabled ${numericPassword ? 'number' : ''} ${allowAutomaticPassword ? '' :'required'}"/></td>
		</tr>
	</c:if>
	<c:if test="${allowSetForceChangePassword}">
		<tr class="trPassword" style="display: ${allowAutomaticPassword ? 'none' : ''}">
			<td class="label"><bean:message key="createMember.forceChangePassword"/></td>
			<td><input type="checkbox" name="forceChangePassword" class="checkbox" value="true"></td>
		</tr>
	</c:if>
	
	<c:choose><c:when test="${isPublic}">
		<tr>
			<td colspan="3" align="center">
				<br class="small"/>
				<span class="label"><bean:message key="createMember.captcha"/></span><br>
				<br class="small"/>
				<img id="captchaImage" style="display:none"/><br>
				<a id="newCaptcha" class="default"><bean:message key="createMember.newCaptcha"/></a><br>
				<br class="small"/>
				<html:text property="captcha" styleClass="small InputBoxEnabled required"/>
			</td>
		</tr>
		<c:choose><c:when test="${empty group.registrationAgreement}">
			<tr>
				<td colspan="3" align="right">
					<input type="submit" id="saveButton" class="button" value="<bean:message key="global.submit"/>">
				</td>
			</tr>
		</c:when><c:otherwise>
			<tr>
				<td colspan="3" class="label" style="text-align:center">
					<br>
					<bean:message key="group.registrationAgreement" />
					<span style="font-weight: normal">
					(<a class="default" id="printAgreement"><bean:message key="global.print"/></a>)
					</span>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<div id="registrationAgreement" class="fakeField" style="text-align:justify;height:150px;width:80%;overflow:scroll">${group.registrationAgreement.contents}</div>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<br class="small">
					<label>
					<input type="checkbox" class="checkbox" id="registrationAgreementCheck">
					<bean:message key="createMember.registrationAgreementButton" />
					</label>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="right">
					<br class="small">
					<input type="submit" id="saveButton" class="button" value="<bean:message key="global.submit" />">
				</td>
			</tr>
		</c:otherwise></c:choose>
	</c:when><c:otherwise>
		<tr>
			<td colspan="3" align="right">
				<span class="label"><bean:message key="createMember.action.saveAndNew"/></span>
				<input type="submit" id="saveAndNewButton" class="button" value="<bean:message key="global.submit"/>">
			</td>
		</tr>
		<tr>
			<td colspan="3" align="right">
				<span class="label"><bean:message key="createMember.action.saveAndOpenProfile"/></span>
				<input type="submit" id="saveAndOpenProfileButton" class="button" value="<bean:message key="global.submit"/>">
			</td>
		</tr>
	</c:otherwise></c:choose>
</table>        

    	</td>
    </tr>
</table>

<br class="small">
<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td><input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>

</ssl:form>