<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/messageCategory/editMessageCategory.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="messageCategoryId" />
<html:hidden property="messageCategory(id)" />
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="${empty messageCategory.id ? 'messageCategory.title.insert' : 'messageCategory.title.modify'}"/>
        </td>
        <c:choose>
        	<c:when test="${empty messageCategory.id}">
		        <cyclos:help page="messages#new_message_category"/>
        	</c:when>
        	<c:otherwise>
		        <cyclos:help page="messages#edit_message_category"/>
        	</c:otherwise>
        </c:choose>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="25%"><bean:message key="messageCategory.name"/></td>
                    <td><html:text property="messageCategory(name)" disabled="true" styleClass="full InputBoxDisabled required"/></td>
                </tr>
                <tr>
					<td align="right" colspan="2">
						<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">
						&nbsp;
	               		<input type="submit" id="saveButton" class="ButtonDisabled" disabled 
	               			   value="<bean:message key="global.submit"/>" >
					</td>
	            </tr>
             </table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left">
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>
</ssl:form>