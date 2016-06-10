<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<script>
	var myProfile = ${empty myProfile ? false : myProfile};
	var maxImages = ${empty maxImages ? false : maxImages};
	var canChangeName = ${empty canChangeName ? false : canChangeName};
	var canChangeEmail = ${empty canChangeEmail ? false : canChangeEmail};
	var canChangeUsername = ${empty canChangeUsername ? false : canChangeUsername};
</script>
<cyclos:script src="/pages/members/editProfile.js" />

<c:choose>
	<c:when test="${myProfile}">
		<c:set var="titleKey" value="profile.member.title.my"/>
	</c:when>
	<c:when test="${byBroker}">
		<c:set var="titleKey" value="profile.member.title.of"/>
	</c:when>
	<c:when test="${isAdmin}">
		<c:set var="titleKey" value="profile.member.title.of"/>
	</c:when>
</c:choose>

<ssl:form action="${formAction}" method="post" enctype="multipart/form-data">
<html:hidden property="member(id)"/>

<c:set var="imageRowSpan" value="${5 + fn:length(customFields)}" />
<c:if test="${isAdmin || byBroker}">
	<c:set var="imageRowSpan" value="${imageRowSpan + 2}" />
</c:if>
<c:if test="${!isAdmin && !byBroker && groupFilters != null}">
	<c:set var="imageRowSpan" value="${imageRowSpan + 1}" />
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}" arg0="${member.name}"/></td>
        <cyclos:help page="profiles#member_profile"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
			<table class="defaultTable">
				<tr height="1">
					<td colspan="3"></td>
					<td rowspan="${imageRowSpan}" valign="top">
			    		<cyclos:images varName="images" images="${images}" editable="true" style="float:right;" />
					</td>
				</tr>
				<c:if test="${isAdmin || byBroker}">
					<tr>
						<td width="25%" class="label"><bean:message key="member.group"/></td>
						<td nowrap="nowrap" colspan="2"><html:text property="member(group).name" styleId="groupText" readonly="true" styleClass="large InputBoxDisabled"/></td>
			        </tr>
			        <tr>
						<td class="label"><bean:message key="member.lastLogin"/></td>
						<td nowrap="nowrap" colspan="2">
			    	        <c:set var="memberLoggedOn" value="${false}"/>
							<c:choose>
								<c:when test="${isLoggedIn}">
				    	       		<c:set var="lastLogin"><bean:message key="profile.userOnline"/></c:set>
				    	       		<c:set var="memberLoggedOn" value="${true}"/>
						       	</c:when>
						       	<c:when test="${empty member.user.lastLogin}">
				    	       		<c:set var="lastLogin"><bean:message key="profile.neverLoggedOn"/></c:set>
						       	</c:when>
						       	<c:otherwise>
				    	       		<c:set var="lastLogin"><cyclos:format dateTime="${member.user.lastLogin}"/></c:set>
					        	</c:otherwise>
					        </c:choose>
							<html:text property="member.user.lastLogin" styleId="loginText" readonly="true" styleClass="medium InputBoxDisabled${memberLoggedOn ? ' fieldDecoration' : ''}" value="${lastLogin}"/>
						</td>			
					</tr>
				</c:if>
				<c:if test="${!isAdmin && !byBroker && groupFilters != null}">
					<tr>
						<td width="25%" class="label"><bean:message key="member.groupFilter"/></td>
						<td nowrap="nowrap" colspan="2"><input name="groupFilters" type="text" readonly="true" class="medium InputBoxDisabled" value="${groupFilters}" /></td>
					</tr>
				</c:if>
				<tr>
					<td width="25%" class="label"><bean:message key="member.username"/></td>
					<td nowrap="nowrap" colspan="2"><html:text property="member(user).username" maxlength="20" readonly="true" styleClass="medium InputBoxDisabled required"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="member.name"/></td>
					<td nowrap="nowrap"><html:text property="member(name)" readonly="true" styleClass="large InputBoxDisabled required"/></td>
			        <td valign="bottom" class="label" style="text-align:center;" nowrap="nowrap"><cyclos:escapeHTML><bean:message key="profile.member.hide"/></cyclos:escapeHTML></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="member.email"/></td>
					<td nowrap="nowrap"><html:text property="member(email)" readonly="true" styleClass="large InputBoxDisabled ${localSettings.emailRequired ? 'required' : ''}"/></td>
					<td nowrap="nowrap" valign="top" align="center"><html:checkbox property="member(hideEmail)" styleClass="checkbox" disabled="true" value="true" /></td>
				</tr>
				<c:if test="${not empty pendingEmailChange}">
					<tr>
						<td class="label"></td>
						<td colspan="2">
							<bean:message key="profile.pendingEmail" arg0="${pendingEmailChange.newEmail}" />
							<br>
							<bean:message key="profile.pendingEmailLastSent">
								<jsp:attribute name="arg0"><cyclos:format dateTime="${pendingEmailChange.lastEmailDate}" /></jsp:attribute>
							</bean:message>
							<c:if test="${canChangeEmail}">
								<br>
								<a id="resendEmailChangeValidation" class="default"><bean:message key="profile.pendingEmail.link" /></a>
							</c:if>
						</td>
					</tr>
				</c:if>
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
						<td class="label">${field.name}</td>
						<td width="15%" nowrap="nowrap" colspan="${cyclos:name(field.control) == 'RICH_EDITOR' ? 3 : 1}">
							<input type="hidden" id="hidden_${field.id}" name="member(customValues).hidden" value="${hidden}">
							<span class="customFieldContainer" editable="${editableFields[field]}">
								<cyclos:customField field="${field}" value="${value}" editable="${editableFields[field]}" valueName="member(customValues).value" fieldName="member(customValues).field" enabled="false"/>
							</span>
						</td>
						<c:if test="${field.memberCanHide && cyclos:name(field.control) != 'RICH_EDITOR'}">
							<td nowrap="nowrap" valign="top" align="center">
								<input type="checkbox" id="chk_hidden_${field.id}" class="checkbox" disabled="true" ${checked}>
							</td>
						</c:if>
					</tr>
			    </c:forEach>
				<c:if test="${not empty member.broker}">
				    <tr>
				        <td class="label"><bean:message key="member.broker"/></td>
				        <td colspan="2" nowrap="nowrap">
				        	<table class="nested" width="100%">
				        		<tr>
				        			<td width="90%" style="padding:0px;">
							            <input type="text" id="brokerText" value="${member.broker.name}" readonly="true" class="full InputBoxDisabled"/>
							        </td>
							        <td nowrap="nowrap">
							        	&nbsp;
							        	<c:set var="label"><bean:message key="profile.member.brokerLink"/></c:set>
							            <cyclos:profile elementId="${member.broker.id}" text="${label}" styleClass="default" />
							        </td>
							    </tr>
				            </table>
				        </td>
				    </tr>
			    </c:if>
				<c:if test="${isAdmin and empty member.activationDate}">
				    <tr>
				        <td class="label"><bean:message key="member.creationDate"/></td>
				        <td colspan="2" nowrap="nowrap"><input id="creationDateText" value="<cyclos:format date="${member.creationDate}"/>" readonly="true" class="small InputBoxDisabled"/></td>
				    </tr>
			    </c:if>
			    <tr>
			    	<td>&nbsp;</td>
			    </tr>
				<tr style="display:none" id="trMaxPictures">
					<td class="label"><bean:message key="profile.member.addPicture"/></td>
					<td colspan="2">
						<div class="fieldDecoration">
							<bean:message key="profile.member.maxPictures"/>
						</div>
					</td>
				</tr>
				<tr style="display:none" id="trPictureCheck">
					<td class="label"><bean:message key="profile.member.addPicture"/></td>
					<td colspan="2" valign="top">	
						<input type="checkbox" class="checkbox" disabled="disabled" id="addPictureCheck">
					</td>
				</tr>
				<tr class="trPicture" colspan="3" style="display:none">
					<td class="label"><bean:message key="image.file"/></td>
					<td colspan="3">						
						<html:file property="picture" styleClass="InputBoxDisabled upload"/>
					</td>
				</tr>
				<tr class="trPicture" colspan="3" style="display:none">
					<td class="label"><bean:message key="image.caption"/></td>
					<td colspan="1">						
						<html:text property="pictureCaption" styleId="captionText" readonly="true" styleClass="large InputBoxDisabled"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right">
						<input type="button" id="modifyButton" value="<bean:message key="global.change"/>" class="button">
						&nbsp;
						<input type="submit" id="saveButton" class="ButtonDisabled" disabled value="<bean:message key="global.submit"/>">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</ssl:form>
<c:if test="${!myProfile || param.fromQuickAccess}">
	<c:choose>
	    <c:when test="${isAdmin}">
			
	        <jsp:include page="/pages/members/includes/profileOfMemberByAdmin.jsp"/>
	    </c:when>
	    <c:when test="${byBroker}">
			
	        <jsp:include page="/pages/members/includes/profileOfMemberByBroker.jsp"/>
	    </c:when>
	    <c:when test="${isMember && profileOfOtherMember && hasAccounts}">
			
	        <jsp:include page="/pages/members/includes/profileOfMemberByMember.jsp"/>
	    </c:when>
	</c:choose>
	
	<table class="defaultTableContentHidden"><tr><td>
	<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
	</td></tr></table>
</c:if>