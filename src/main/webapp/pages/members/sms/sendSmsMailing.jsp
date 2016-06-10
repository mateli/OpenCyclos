<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/sms/sendSmsMailing.js" />
<ssl:form method="post" action="${formAction}">
<c:if test="${!canSendFree || !canSendPaid}">
	<html:hidden property="smsMailing(free)" value="${canSendFree}"/>
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="smsMailing.title.send" /></td>
        <cyclos:help page="messages#send_sms_mailing"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td valign="top" class="label" width="25%"><bean:message key="smsMailing.sendType"/></td>
                    <td>
                       	<label>
                       		<html:radio property="singleMember" styleId="toGroup" value="false"/>
	                       	<c:choose>
	                       		<c:when test="${isAdmin}">
	                       			<bean:message key="smsMailing.sendType.group"/>
	                       		</c:when>
	                       		<c:when test="${isBroker}">
	                       			<bean:message key="smsMailing.broker.all"/>
	                       		</c:when>
	                       	</c:choose> 
                       	</label>&nbsp;&nbsp;&nbsp;
                    	<label><html:radio property="singleMember" styleId="toMember" value="true" /> <bean:message key="smsMailing.sendType.member"/></label>
                    </td>
                </tr>
	            <tr class="toHideMember" style="display:none;">
  		            <td nowrap="nowrap" width="25%" class="label"><bean:message key="member.username" /></td>
	            	<td>
						<html:hidden styleId="memberId" property="smsMailing(member)"/>
						<input id="memberUsername" class="medium"/>
						<div id="membersByUsername" class="autoComplete"></div>
	            	</td>
	            </tr>
	            <tr class="toHideMember" style="display:none;">
  		            <td nowrap="nowrap" width="25%" class="label"><bean:message key="member.memberName" /></td>
	            	<td>
						<input id="memberName" class="full"/>
						<div id="membersByName" class="autoComplete"></div>
	            	</td>
	            </tr>			        					
            	<c:if test="${isAdmin}">
	                <tr class="toHideGroups">
	                    <td valign="top" class="label" width="25%"><bean:message key="smsMailing.groups"/></td>
	                    <td>
	           				<cyclos:multiDropDown varName="groupsMdd" name="smsMailing(groups)" emptyLabelKey="member.search.allGroups" onchange="updateVariables()">
	           					<c:forEach var="group" items="${groups}">
	           						<cyclos:option value="${group.id}" text="${group.name}" />
	           					</c:forEach>
	           				</cyclos:multiDropDown>
	                    </td>
	                </tr>
            	</c:if>
            	<c:if test="${canSendFree && canSendPaid}">
	                <tr id="trType">
	                    <td valign="top" class="label" width="25%"><bean:message key="smsMailing.type"/></td>
	                    <td>
	                    	<label><html:radio property="smsMailing(free)" value="true" /> <bean:message key="smsMailing.type.FREE"/></label>
	                    	&nbsp;&nbsp;&nbsp;
	                    	<label><html:radio property="smsMailing(free)" value="false" /> <bean:message key="smsMailing.type.PAID"/></label>
	                    </td>
	                </tr>
	                <tr id="trFree" style="display:none">
	                    <td valign="top" class="label" width="25%"><bean:message key="smsMailing.type"/></td>
	                    <td><input class="InputBoxDisabled medium" readonly="readonly" value="<bean:message key="smsMailing.type.FREE"/>"></td>
	                </tr>
            	</c:if>
            	<tr>
            	    <td valign="top" class="label" width="25%"><bean:message key="smsMailing.variables"/></td>
            		<td>
            		<select id="variables"></select>
					<input id="addButton" type="button" class="button" value="<bean:message key="global.add"/>">
					</td>
            	</tr>
                <tr>
                    <td valign="top" class="label" width="25%"><bean:message key="smsMailing.text"/></td>
                    <td>                    
                    <html:textarea property="smsMailing(text)" rows="5" styleId="text" styleClass="full"/>
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
