<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/productsServices/categories/manageAdCategories.js" />
<script>
	var confirmImportMessage = "<cyclos:escapeJS><bean:message key="adCategory.import.confirmation"/></cyclos:escapeJS>";
	var noFileMessage = "<cyclos:escapeJS><bean:message key="adCategory.import.error.noFile"/></cyclos:escapeJS>";
</script>
<ssl:form styleId="importForm" method="post" action="/admin/importAdCategories" enctype="multipart/form-data">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="adCategory.title.import"/>
        </td>
        <cyclos:help page="advertisements#import_ad_categories"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="25%"><bean:message key="adCategory.import.file"/></td>
                    <td><html:file property="upload"/></td>
                    <td align="right"><input type="submit" class="button" value="<bean:message key="global.submit"/>"></td>
				</tr>
            </table>
		</td>            
    </tr>
</table>
</ssl:form>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="adCategory.title.export"/>
        </td>
        <cyclos:help page="advertisements#export_ad_categories"/>
    </tr>
    <tr>
        <td colspan="2" align="right" class="tdContentTableForms">
        	
        	<span class="label"><bean:message key="adCategory.action.export"/></span>
        	<input type="button" class="button" id="exportButton" value="<bean:message key="global.submit"/>">
            &nbsp;
		</td>            
    </tr>
</table>
