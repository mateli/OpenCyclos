<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/customization/documents/editMemberDocument.js" />

<ssl:form method="post" action="${formAction}" enctype="multipart/form-data">
<html:hidden property="documentId" />
<html:hidden property="document(id)" />
<html:hidden property="document(member)" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="${empty document.id ? 'document.title.insert' : 'document.title.modify'}"/>
        </td>
        <cyclos:help page="documents#edit_member_document"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="25%"><bean:message key="document.name"/></td>
                    <td><html:text property="document(name)" disabled="true" styleClass="full InputBoxDisabled"/></td>
                </tr>
                <tr>
                    <td class="label" valign="top"><bean:message key="document.description"/></td>
                    <td><html:textarea rows="4" styleId="descriptionText" disabled="true" property="document(description)" styleClass="full InputBoxDisabled"/></td>
                </tr>
                <tr>
                	<td class="label" valign="top"><bean:message key="document.visibility"/></td>
                	<td>
                		<html:select property="document(visibility)" styleClass="InputBoxDisabled" disabled="true">
                			<c:forEach var="visibility" items="${visibilities}">
                				<html:option value="${visibility}"><bean:message key="document.visibility.${visibility}"/></html:option>
   	    		           	</c:forEach>
               			</html:select>
                	</td>
                </tr>
                <c:if test="${document.persistent}">
	                <tr>
	                	<td class="label" valign="top"><bean:message key="document.currentFile"/></td>
	                	<td>
	                		<a id="currentFileLink" class="default" documentId="${document.id}">
	                	    	<bean:message key="document.currentFileLink">
	                				<jsp:attribute name="arg0">${document.binaryFile.name}</jsp:attribute>
	                				<jsp:attribute name="arg1"><cyclos:format bytes="${document.binaryFile.size}"/></jsp:attribute>
	                		   </bean:message>
	                		</a>
	                	</td>
	                </tr>
	            </c:if>    
                <tr>
                    <td class="label" valign="top"><bean:message key="document.newFile"/></td>
                    <td><html:file property="upload" styleClass="upload" /></td>
                </tr>
                <c:if test="${byBroker or adminCanManage}">
	                <tr>
						<td align="right" colspan="2">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">
							&nbsp;
	                		<input type="submit" id="saveButton" class="ButtonDisabled" disabled value="<bean:message key="global.submit"/>" >
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