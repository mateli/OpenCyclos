<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/messages/searchMessages.js" />
<script>
	var removeConfirmation = "<cyclos:escapeJS><bean:message key="message.action.confirmDelete"/></cyclos:escapeJS>";
	var nothingSelectedMessage = "<cyclos:escapeJS><bean:message key="global.error.nothingSelected"/></cyclos:escapeJS>";
	var moveToTrashTooltip = "<cyclos:escapeJS><bean:message key="message.action.MOVE_TO_TRASH"/></cyclos:escapeJS>";
	var removeTooltip = "<cyclos:escapeJS><bean:message key="message.action.DELETE"/></cyclos:escapeJS>";
</script>
<ssl:form method="post" action="${formAction}">
<html:hidden property="advanced"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="message.title.search" /></td>
        <cyclos:help page="messages#messages_search"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<tr>
	                <td width="25%" class="label"><bean:message key="message.messageBox"/></td>
	                <td>
	                 	<html:select property="query(messageBox)" styleId="messageBoxSelect">
	                 		<c:forEach items="${messageBoxes}" var="messageBox">
	                 			<html:option value="${messageBox}"><bean:message key="message.messageBox.${messageBox}"/></html:option>
	                 		</c:forEach>
	                 	</html:select>
	                </td>
	            </tr>
	            <c:if test="${isAdmin}">
		            <tr>
				        <td valign="top" width="20%" class="label"><bean:message key="message.category"/></td>
				        <td>
		                   	<html:select property="query(category)" styleId="categoriesSelect" >
								<html:option value=""><bean:message key="messageCategory.all"/></html:option>
		                   		<c:forEach items="${categories}" var="cat">
		                   			<html:option value="${cat.id}">${cat.name}</html:option>
		                   		</c:forEach>
		                   	</html:select>
	                    </td>
	            	</tr>
            	</c:if>
            	<c:if test="${searchMessagesForm.advanced}">
		            <c:if test="${!isAdmin}">
		            	<tr>
		                    <td valign="top" class="label"><bean:message key="message.rootType"/></td>
		                    <td>
		                    	<html:select property="query(rootType)">
		                    		<html:option value=""><bean:message key="message.rootType.all"/></html:option>
		                    		<c:forEach items="${rootTypes}" var="type">
		                    			<html:option value="${type}"><bean:message key="message.rootType.${type}"/></html:option>
		                    		</c:forEach>
		                    	</html:select>
							</td>
						</tr>
					</c:if>
	                <tr>
	                    <td valign="top" class="label"><bean:message key="message.search.keywords"/></td>
	                    <td><html:text styleClass="large" property="query(keywords)"/></td>
	                </tr>
					<tr>
						<td class="label"><bean:message key='member.username'/></td>
						<td>
							<html:hidden styleId="memberId" property="query(relatedMember)"/>
							<input id="memberUsername" class="large" value="${relatedMember.username}">
							<div id="membersByUsername" class="autoComplete"></div>
						</td>
					</tr>
					<tr>
						<td class="label"><bean:message key='member.memberName'/></td>
						<td>
							<input id="memberName" class="large" value="${relatedMember.name}">
							<div id="membersByName" class="autoComplete"></div>
						</td>
					</tr>
            	</c:if>
                <tr>
					<td><input id="modeButton" type="button" class="button" value="<bean:message key="global.search.${searchMessagesForm.advanced ? 'NORMAL' : 'ADVANCED'}"/>"></td>
					<c:if test="${searchMessagesForm.advanced}">
						<td align="right"><input type="submit" class="button" value="<bean:message key="global.submit"/>"></td>
					</c:if>
				</tr>
            </table>
          </td>            
    </tr>
</table>
</ssl:form>
<c:if test="${canSend}">
	
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<c:if test="${param.fromQuickAccess}">
				<td align="left">
					<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
				</td>
			</c:if>
			<td align="right">
				<span class="label"><bean:message key="message.action.new"/></span>
				<input type="button" class="button" id="newButton" value="<bean:message key="global.submit"/>">
			</td>
		</tr>
	</table>
</c:if>		

<c:choose><c:when test="${empty messages}">
	<div class="footerNote" helpPage="messages#messages_search_result"><bean:message key="message.search.noResults"/></div>
</c:when><c:otherwise>
	<ssl:form action="${actionPrefix}/changeMessageStatus">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable">
	        	<bean:message key="global.searchResults"/>
	        </td>
	        <cyclos:help page="messages#messages_search_result"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	        	<c:if test="${cyclos:name(messageBox) == 'TRASH' && localSettings.deleteMessagesOnTrashAfter != null && localSettings.deleteMessagesOnTrashAfter.number > 0}">
	        		<c:set var="fieldMessage"><bean:message key="global.timePeriod.${localSettings.deleteMessagesOnTrashAfter.field}" /></c:set>
	        		<div class="label" style="text-align:center">
		        		<bean:message key="message.trashPurgeNotification" arg0="${localSettings.deleteMessagesOnTrashAfter.number}" arg1="${fn:toLowerCase(fieldMessage)}" />
	        		</div>
	        	</c:if>
				<table class="defaultTable">
					<tr>
						<c:if test="${canManage}">
							<td width="2%" class="tdHeaderContents">&nbsp;</td>
						</c:if>
						<td align="center" width="10%" class="tdHeaderContents"><bean:message key="message.date"/></td>
						<td class="tdHeaderContents"><bean:message key="message.subject"/></td>
						<td width="25%" class="tdHeaderContents">
							<c:choose>
								<c:when test="${cyclos:name(messageBox) == 'INBOX'}">
									<bean:message key="message.from"/>
								</c:when>
								<c:when test="${cyclos:name(messageBox) == 'SENT'}">
									<bean:message key="message.to"/>
								</c:when>
								<c:otherwise>
									<bean:message key="message.fromTo"/>
								</c:otherwise>
							</c:choose>
						</td>
						<c:if test="${canManage}">
							<td width="5%" class="tdHeaderContents">&nbsp;</td>
						</c:if>
					</tr>
					<c:set var="memberProperty" value="${localSettings.memberResultDisplay.property}"/>
					<c:forEach var="msg" items="${messages}">
						<c:set var="rootType" value="${msg.type.rootType}" />
						<c:choose>
							<c:when test="${msg.removed}">
								<c:set var="msgClass" value="removedMessage"/>
								<c:if test="${!msg.read}">
									<c:set var="msgClass" value="${msgClass} unreadMessage"/>
								</c:if>
								<c:set var="image" value="message_removed.gif"/>
							</c:when>
							<c:when test="${!msg.read}">
								<c:set var="msgClass" value="unreadMessage"/>
								<c:set var="image" value="message_unread.gif"/>
							</c:when>
							<c:when test="${msg.replied}">
								<c:set var="msgClass" value="readMessage repliedMessage"/>
								<c:set var="image" value="message_replied.gif"/>
							</c:when>
							<c:otherwise>
								<c:set var="msgClass" value="readMessage"/>
								<c:set var="image" value="message_read.gif"/>
							</c:otherwise>
						</c:choose>
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle> ${msgClass}">
							<c:if test="${canManage}">
								<td valign="middle" align="center"><input type="checkbox" name="messageId" class="checkbox" value="${msg.id}"></td>
							</c:if>
							<td align="center" nowrap="nowrap"><cyclos:format date="${msg.date}"/></td>
							<td valign="middle" style="padding-left:0px; padding-right:0px;">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td width="16">
											<a class="messageDetails" messageId="${msg.id}">
												<img src="<html:rewrite page="/pages/images/${image}" />" border="0" />
											</a>
										</td>
										<td valign="middle" style="padding:0px" class="${msgClass}">
											<a class="linkList messageDetails" messageId="${msg.id}">
												<cyclos:truncate value="${msg.subject}" length="100"/>
											</a>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<c:choose><c:when test="${cyclos:name(msg.direction) == 'INCOMING'}">
									<%-- An incoming message may be from the administration, from system or from another member --%>
									<c:choose>
										<c:when test="${msg.fromAdministration}">
											${localSettings.applicationUsername}
										</c:when>
										<c:when test="${msg.fromAMember}">
											<c:set var="member" value="${msg.fromMember}"/>
											<cyclos:profile elementId="${member.id}"/>
										</c:when>
										<c:when test="${msg.fromSystem}">
											<bean:message key="global.system"/>
										</c:when>
									</c:choose>
								</c:when><c:otherwise>
									<%--An outgoing message may be to the administration, to a specific member, to a group or to the broker's registered members --%>
									<c:choose>
										<c:when test="${msg.toAdministration}">
											${localSettings.applicationUsername}
										</c:when>
										<c:when test="${msg.toAGroup}">
											<c:forEach var="group" items="${msg.toGroups}" varStatus="status">
												${group.name}<c:if test="${!status.last}">,</c:if>
											</c:forEach>
										</c:when>
										<c:when test="${msg.toBrokeredMembers}">
											<bean:message key="message.brokeredMembers"/>
										</c:when>
										<c:when test="${msg.toAMember}">
											<c:set var="member" value="${msg.toMember}"/>
											<cyclos:profile elementId="${member.id}"/>
										</c:when>
									</c:choose>
								</c:otherwise></c:choose>
							</td>
							<c:if test="${canManage}">
								<td align="right"><img class="${cyclos:name(messageBox) == 'TRASH' ? 'removePermanently' : 'moveToTrash'}" messageId="${msg.id}" src="<html:rewrite page="/pages/images/delete.gif" />" border="0"></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
	
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td width="50%" nowrap="nowrap">
				<c:if test="${canManage}">
					<input id="selectAllButton" type="button" class="button" value="<bean:message key="global.selectAll"/>">
					<input id="selectNoneButton" type="button" class="button" value="<bean:message key="global.selectNone"/>">
					<br><br class="small">
					<select name="action" id="applyActionSelect">
						<option value=""><bean:message key="message.action.choose"/></option>
						<c:forEach var="action" items="${messageBox.possibleActions}">
							<option value="${action}"><bean:message key="message.action.${action}"/></option>
						</c:forEach>
					</select>
				</c:if>
			</td>
			<td align="right" valign="top"><cyclos:pagination items="${messages}"/></td>
		</tr>
	</table>
	</ssl:form>
</c:otherwise></c:choose>