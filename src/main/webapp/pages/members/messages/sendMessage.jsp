<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/messages/sendMessage.js" />
<script language="JavaScript">
	var isAdmin = ${isAdmin};
	var isReply = ${not empty inReplyTo};
	var loggedElement = '${loggedUser.element.id}';
	var noMessageCategories = "<cyclos:escapeJS><bean:message key="messageCategory.noMessageCategories"/></cyclos:escapeJS>";
</script>
<ssl:form method="post" action="${formAction}">
<html:hidden property="toMemberId"/>
<html:hidden property="inReplyTo"/>
<html:hidden property="message(inReplyTo)" value="${sendMessageForm.inReplyTo}"/>
<html:hidden property="message(html)"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="message.title.send" /></td>
        <c:choose>
        	<c:when test="${not empty toBrokeredMembers}">
        		<cyclos:help page="messages#messages_send_brokered_members"/>
        	</c:when>
        	<c:otherwise>
        		<cyclos:help page="messages#messages_send"/>
        	</c:otherwise>
        </c:choose>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<c:choose>
            		<c:when test="${not empty toMember}">
			            <%-- When coming from a member profile, and will send to that member --%>
						<tr>
							<td width="25%" valign="top" class="label"><bean:message key='member.username'/></td>
							<td>
								<html:hidden property="sendTo" value="MEMBER"/>
								<html:hidden property="message(toMember)" value="${sendMessageForm.toMemberId}"/>
								<input class="InputBoxDisabled large" readonly="readonly" value="${toMember.username}">
							</td>
						</tr>
						<tr>
							<td class="label"><bean:message key='member.memberName'/></td>
							<td>
								<input class="InputBoxDisabled large" readonly="readonly" value="${toMember.name}">
							</td>
						</tr>
						<c:if test="${isAdmin}">
							<tr id="adminRow" class="adminRow">
			                    <td valign="top" class="label"><bean:message key="message.category"/></td>
			                    <td>
			                    	<c:choose>
			                    		<%-- There is just one category --%>
				                    	<c:when test="${fn:length(categories) == 1}">
				                    		<input type="hidden" name="message(category)" value="${categories[0].id}"/>
				                    		<input type="text" class="InputBoxDisabled large" readonly="readonly" value="${categories[0].name}"/> 
				                    	</c:when>
				                    	
				                    	<%-- There are more than one category --%>
				                    	<c:otherwise>
				                    		<c:set var="selected" value=""/>
					                    	<html:select property="message(category)" styleId="categorySelect">
					                    		<html:option value=""><bean:message key="message.category.choose"/></html:option>
					                    		<c:forEach items="${categories}" var="cat">
					                    			<html:option value="${cat.id}">${cat.name}</html:option>
					                    		</c:forEach>
					                    	</html:select>
					                    </c:otherwise>
				                    </c:choose>
			                    </td>
			                </tr>
		                </c:if>
		            </c:when>
            		<c:when test="${not empty inReplyTo and inReplyTo.fromMember == null}">
			            <%-- When replying an administration message --%>
						<tr>
							<td width="25%" valign="top" class="label"><bean:message key='message.to'/></td>
							<td>
								<html:hidden property="sendTo" value="ADMIN"/>
								<html:hidden property="message(toMember)" value=""/>
								<input class="InputBoxDisabled large" readonly="readonly" value="${localSettings.applicationUsername}">
							</td>
						</tr>
		                <tr>
		                	<td width="25%" valign="top" class="label"><bean:message key="message.category"/></td>
		                	<td>
		                		<html:hidden property="message(category)"/>
		                		<input value="${categoryName}" readonly="readonly" class="InputBoxDisabled large"/>
		                	</td>
		                </tr>
		            </c:when>
            		<c:when test="${not empty inReplyTo and inReplyTo.fromMember != null}">
			            <%-- When replying an member message --%>
						<tr>
							<td width="25%" valign="top" class="label"><bean:message key='member.username'/></td>
							<td>
								<html:hidden property="sendTo" value="MEMBER"/>
								<html:hidden property="message(toMember)" value="${inReplyTo.fromMember.id}"/>
								<input class="InputBoxDisabled large" readonly="readonly" value="${inReplyTo.fromMember.username}">
							</td>
						</tr>
						<tr>
							<td class="label"><bean:message key='member.memberName'/></td>
							<td>
								<input class="InputBoxDisabled large" readonly="readonly" value="${inReplyTo.fromMember.name}">
							</td>
						</tr>
						<c:if test="${isAdmin}">
							<tr id="adminRow" class="adminRow">
			                    <td valign="top" class="label"><bean:message key="message.category"/></td>
			                    <td>
			                    	<c:set var="selected" value=""/>
			                    	<html:select property="message(category)" styleId="categorySelect">
			                    		<html:option value=""><bean:message key="message.category.choose"/></html:option>
			                    		<c:forEach items="${categories}" var="cat">
			                    			<html:option value="${cat.id}">${cat.name}</html:option>
			                    		</c:forEach>
			                    	</html:select>
			                    </td>
			                </tr>
		                </c:if>
		            </c:when>
		            <c:otherwise>
	            		<%-- The user will select the destination here --%>
		                <tr style="${fn:length(sendTo) == 1 and empty toBrokeredMembers ? 'display:none' : ''}">
		                    <td width="25%" valign="top" class="label"><bean:message key="message.sendTo"/></td>
		                    <td>
		                    	<c:choose><c:when test="${not empty toBrokeredMembers}">
									<html:hidden property='sendTo' value='${toBrokeredMembers}'/>
									<input type="text" class="InputBoxDisabled medium" value="<bean:message key="message.sendTo.${toBrokeredMembers}"/>" disabled="disabled"/>
		                    	</c:when><c:otherwise>
			                    	<html:select property="sendTo" styleId="sendToSelect">
			                    		<c:forEach items="${sendTo}" var="st">
			                    			<html:option value="${st}"><bean:message key="message.sendTo.${st}"/></html:option>
			                    		</c:forEach>
			                    	</html:select>
		                    	</c:otherwise></c:choose>
		                    
		                    </td>
		                </tr>
		                <%-- Both members and admins may send to a member --%>
						<tr class="memberRow" style="display:none">
							<td width="25%" valign="top" class="label"><bean:message key='member.username'/></td>
							<td>
								<html:hidden styleId="memberId" property="message(toMember)"/>
								<input id="memberUsername" class="large">
								<div id="membersByUsername" class="autoComplete"></div>
							</td>
						</tr>
						<tr class="memberRow" style="display:none">
							<td class="label"><bean:message key='member.memberName'/></td>
							<td>
								<input id="memberName" class="large">
								<div id="membersByName" class="autoComplete"></div>
							</td>
						</tr>
						<c:if test="${isAdmin}">
		                	<%-- An admin may send to a group --%>
			                <tr class="groupRow" style="display:none">
			                    <td valign="top" class="label"><bean:message key="message.sendTo.GROUP"/></td>
			                    <td>
			                    	<cyclos:multiDropDown varName="toGroupsSelect" name="message(toGroups)" size="5" onchange="updateMessageCategories()">
			                    		<c:forEach items="${groups}" var="group">
			                    			<cyclos:option value="${group.id}" text="${group.name}"/>
			                    		</c:forEach>
			                    	</cyclos:multiDropDown>
			                    </td>
			                </tr>
						</c:if>
	                	<%-- A member may send to administration. If so, must select a category --%>
		                <tr id="adminRow" class="adminRow" style="display:none">
		                    <td valign="top" class="label"><bean:message key="message.category"/></td>
		                    <td id="messageCategoriesCell">
		                    	<html:select property="message(category)" styleId="categorySelect">
		                    		<html:option value=""><bean:message key="message.category.choose"/></html:option>
		                    		<c:forEach items="${categories}" var="cat">
		                    			<html:option value="${cat.id}">${cat.name}</html:option>
		                    		</c:forEach>
		                    	</html:select>
		                    </td>
		                </tr>
		            </c:otherwise>
				</c:choose>
                <tr>
                    <td valign="top" class="label"><bean:message key="message.subject"/></td>
                    <td><html:text property="message(subject)" styleId="subjectText" styleClass="full"/></td>
                </tr>
                <tr>
                    <td valign="top" class="label"><bean:message key="message.body"/></td>
                    <td>
                    	<c:choose><c:when test="${cyclos:name(messageFormat) == 'PLAIN'}">
                    		<html:textarea property="message(body)" styleId="bodyText" styleClass="full InputBox" rows="9" value="${body}" />
                    	</c:when><c:otherwise>
                    		<cyclos:richTextArea name="message(body)" styleId="bodyText" value="${body}" />
                    	</c:otherwise></c:choose>
                    </td>
                </tr>
                <tr>
					<td colspan="2" align="right"><input type="submit" class="button" value="<bean:message key="global.submit"/>"></td>
				</tr>
            </table>
          </td>            
    </tr>
</table>
</ssl:form>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>		
