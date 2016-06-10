<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/settings/manageSettings.js" />
<script>
	var confirmImportMessage = "<cyclos:escapeJS><bean:message key="settings.import.confirmation"/></cyclos:escapeJS>";
	var noFileMessage = "<cyclos:escapeJS><bean:message key="settings.error.noFile"/></cyclos:escapeJS>";
</script>
<ssl:form styleId="importForm" method="post" action="/admin/importSettings" enctype="multipart/form-data">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="settings.title.file"/>
        </td>
        <cyclos:help page="settings#import_export"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable" width="100%">
            	<tr>
            		<td class="label" width="25%"><bean:message key="settings.types"/></td>
            		<td>
	                	<c:forEach var="settingType" items="${settingTypes}">
	                		<input type="checkbox" name="type" value="${settingType}"><bean:message key="settings.type.${settingType}"/><br/>
	                	</c:forEach>
	                </td>
	            </tr>
                <tr>
                	<td class="label" width="25%"><bean:message key="settings.action"/></td>
                	<td>
                		<select id="actionSelect">
                			<c:forEach var="action" items="${actions}">
                				<option value="${action}"><bean:message key="settings.action.${action}"/></option> 
                			</c:forEach>
                		</select>
                	</td>
                </tr>
                <tr id="trFile">
                    <td class="label" width="25%"><bean:message key="settings.file"/></td>
                    <td><html:file property="upload"/></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="submit" class="button" value="<bean:message key="global.submit"/>"></td>
				</tr>
            </table>
		</td>            
    </tr>
</table>
</ssl:form>

