<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/customization/translationMessages/manageTranslationMessages.js" />
<script>
	var confirmImportMessage = "<cyclos:escapeJS><bean:message key="translationMessage.import.confirmation"/></cyclos:escapeJS>";
	var noFileMessage = "<cyclos:escapeJS><bean:message key="translationMessage.import.error.noFile"/></cyclos:escapeJS>";
	var confirmImportSettingsMessage = "<cyclos:escapeJS><bean:message key="settings.import.confirmation"/></cyclos:escapeJS>";
	var noSettingsFileMessage = "<cyclos:escapeJS><bean:message key="settings.error.noFile"/></cyclos:escapeJS>";
</script>
<ssl:form styleId="importForm" method="post" action="/admin/importTranslationMessages" enctype="multipart/form-data">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="translationMessage.title.importExport"/>
        </td>
        <cyclos:help page="translation#import_file"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
        	<table width="100%">
        		<tr>
        			<td>
			        	<fieldset>
			        		<legend><bean:message key="translationMessage.title.import"/></legend>
				            <table class="defaultTable">
				                <tr>
				                    <td class="label" width="25%"><bean:message key="translationMessage.import.type"/></td>
				                    <td>
				                    	<html:select property="importType">
				                    		<c:forEach var="importType" items="${importTypes}">
				                    			<html:option value="${importType}"><bean:message key="translationMessage.import.type.${importType}"/></html:option>
				                    		</c:forEach>
				                    	</html:select>
				                    </td>
				                </tr>
				                <tr>
				                    <td class="label"><bean:message key="translationMessage.import.file"/></td>
				                    <td><html:file property="fileUpload"/></td>
				                </tr>
								<tr>
									<td colspan="2" align="right">
										<input type="submit" class="button" value="<bean:message key="global.submit"/>">
									</td>
								</tr>
				            </table>
				        </fieldset>
			    	</td>
			    </tr>
			    <tr>
			    	<td align="right" class="tdContentTableForms">
			    		
			        	<span class="label"><bean:message key="translationMessage.action.export"/></span>
			        	<input type="button" class="button" id="exportButton" value="<bean:message key="global.submit"/>">
			            &nbsp;
			    	</td>
			    </tr>
		    </table>
		</td>            
    </tr>
</table>
</ssl:form>



<ssl:form styleId="settingsForm" method="post" action="/admin/importSettings" enctype="multipart/form-data">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="settings.title.translations.file"/>
        </td>
        <cyclos:help page="translation#imexport_notifications_mails"/>
    </tr>
    <tr>
    	<td colspan="2" class="tdContentTableForms">
	        <table class="defaultTable">
	           	<tr>
	           		<td class="label" width="25%"><bean:message key="settings.types"/></td>
	           		<td>
	                	<c:forEach var="settingType" items="${settingTypes}">
	                		<input type="checkbox" name="type" value="${settingType}"><bean:message key="settings.type.${settingType}"/><br/>
	                	</c:forEach>
	                </td>
	            </tr>
	               <tr>
	               	<td class="label"><bean:message key="settings.action"/></td>
	               	<td>
	               		<select id="actionSelect">
	               			<c:forEach var="action" items="${actions}">
	               				<option value="${action}"><bean:message key="settings.action.${action}"/></option> 
	               			</c:forEach>
	               		</select>
	               	</td>
	               </tr>
	               <tr id="trFile">
	                   <td class="label"><bean:message key="settings.file"/></td>
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
