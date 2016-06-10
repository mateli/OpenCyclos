<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/messages/viewMessage.js" />
<script>
	var removeConfirmation = "<cyclos:escapeJS><bean:message key="message.action.confirmDelete"/></cyclos:escapeJS>";
	var messageId = "${message.id}";
</script>
<c:set var="rootType" value="${message.type.rootType}" />
<c:set var="fromMember" value="${message.fromMember}" />
<c:set var="toMember" value="${message.toMember}" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="message.title.view" /></td>
        <cyclos:help page="messages#messages_view"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td width="25%" valign="top" class="headerLabel"><bean:message key="message.messageBox"/></td>
                    <td class="headerField"><bean:message key="message.messageBox.${message.messageBox}" /></td>
                </tr>
                <tr>
                    <td valign="top" class="headerLabel"><bean:message key="message.date"/></td>
                    <td class="headerField"><cyclos:format dateTime="${message.date}" /></td>
                </tr>
                <c:if test="${not empty message.category}">
	                <tr>
	                    <td valign="top" class="headerLabel"><bean:message key="message.category"/></td>
	                    <td class="headerField">${message.category.name}</td>
	                </tr>
	            </c:if>
                <c:choose><c:when test="${(isAdmin && empty toMember) || (isMember && toMember == loggedUser.element)}">
	                <tr>
	                    <td valign="top" class="headerLabel"><bean:message key="message.from"/></td>
	                    <td class="headerField">
							<c:choose>
								<c:when test="${not empty message.fromMember}">
									<cyclos:profile elementId="${fromMember.id}"/>
								</c:when>
								<c:when test="${cyclos:name(rootType) == 'SYSTEM'}">
									<bean:message key="global.system"/>
								</c:when>
								<c:otherwise>
									${localSettings.applicationUsername}
								</c:otherwise>
							</c:choose>
						</td>
	                </tr>
               	</c:when><c:otherwise>
	                <tr>
	                    <td valign="top" class="headerLabel"><bean:message key="message.to"/></td>
	                    <td class="headerField">
							<c:choose>
								<c:when test="${message.toBrokeredMembers}">
									<bean:message key="message.brokeredMembers"/>
								</c:when>
								<c:when test="${message.toAdministration}">
									${localSettings.applicationUsername}
								</c:when>
								<c:when test="${message.toAGroup}">
									<c:forEach var="group" items="${msg.toGroups}" varStatus="status">
										${group.name}<c:if test="${!status.last}">,</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<cyclos:profile elementId="${toMember.id}"/>
								</c:otherwise>
							</c:choose>
						</td>
	                </tr>
               	</c:otherwise></c:choose>
               	<tr>
                    <td valign="top" class="headerLabel"><bean:message key="message.subject"/></td>
                    <td class="headerField"><cyclos:escapeHTML>${message.subject}</cyclos:escapeHTML></td>
                </tr>
                <tr>
                    <td valign="top" class="headerLabel"><bean:message key="message.body"/></td>
                    <td class="headerField"><div>
                    	<c:choose><c:when test="${message.html}">
                   			${message.body}
                    	</c:when><c:otherwise>
		                    <cyclos:escapeHTML>${message.body}</cyclos:escapeHTML>
                    	</c:otherwise></c:choose>
                    </div></td>
                </tr>                
                <c:if test="${cyclos:name(message.messageBox) == 'INBOX' && cyclos:name(rootType) != 'SYSTEM' && canReplyMessage}">
	                <tr>
	                    <td colspan="2" align="right">
	                    	<input type="button" class="button" id="replyButton" value="<bean:message key="message.action.reply"/>">
	                    </td>
	                </tr>
                </c:if>
            </table>
          </td>            
    </tr>
</table>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top">
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
		<c:if test="${canManageMessage}">
			<td align="right">
				<table class="nested">
				<c:choose><c:when test="${cyclos:name(message.messageBox) == 'TRASH'}">
					<tr>
						<td class="label"><bean:message key="message.action.RESTORE"/></td>
						<td><input type="button" class="button applyMessageAction" messageAction="RESTORE" id="restoreButton" value="<bean:message key="global.submit"/>"></td>
					</tr>
					<tr>
						<td class="label"><bean:message key="message.action.DELETE"/></td>
						<td><input type="button" class="button applyMessageAction" messageAction="DELETE" confirmation="removeConfirmation" value="<bean:message key="global.submit"/>"></td>
					</tr>
				</c:when><c:otherwise>
					<tr>
						<td class="label"><bean:message key="message.action.MOVE_TO_TRASH"/></td>
						<td><input type="button" class="button applyMessageAction" messageAction="MOVE_TO_TRASH" value="<bean:message key="global.submit"/>"></td>
					</tr>
				</c:otherwise></c:choose>
				</table>
			</td>
		</c:if>
	</tr>
</table>		
