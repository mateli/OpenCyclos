<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/customization/translationMessages/editTranslationMessage.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="messageId" />
<html:hidden property="message(id)" />
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="${isInsert ? 'translationMessage.title.insert' : 'translationMessage.title.modify'}"/>
        </td>
        <c:choose>
        	<c:when test="${isInsert}">
		        <cyclos:help page="translation#insert_key"/>
        	</c:when>
        	<c:otherwise>
        		<cyclos:help page="translation#edit_key"/>
        	</c:otherwise>
        </c:choose>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="25%"><bean:message key="translationMessage.key"/></td>
                    <td>
                    	<c:choose><c:when test="${isInsert}">
	                    	<html:text property="message(key)" disabled="true" styleClass="InputBoxDisabled full"/>
	                    </c:when><c:otherwise>
		                    <html:hidden property="message(key)"/>
		                    <input id="messageKey" type="text" class="InputBoxDisabled full" disabled="true" readonly="readonly" value="${message.key}">
	                    </c:otherwise></c:choose>
                    </td>
                </tr>
                <tr>
                    <td class="label" valign="top"><bean:message key="translationMessage.message"/></td>
                    <td><html:textarea rows="6" property="message(value)" styleClass="InputBoxDisabled full" disabled="true"/></td>
                </tr>
                <c:if test="${cyclos:granted(AdminSystemPermission.TRANSLATION_MANAGE)}">
	                <tr>
						<td align="right" colspan="2">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
							<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
						</td>
	                </tr>
                </c:if>
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
