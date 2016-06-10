<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<ssl:form method="post" action="/admin/importTheme" enctype="multipart/form-data">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="theme.title.import"/></td>
        <cyclos:help page="content_management#import_theme"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label"><bean:message key="theme.message.import"/></td>
                    <td><html:file property="upload"/></td>
                </tr>
				<tr>
					<td colspan="2" align="right">
						<input type="submit" class="button" value="<bean:message key="global.submit"/>">
					</td>
				</tr>
            </table>
		</td>            
    </tr>
</table>
</ssl:form>