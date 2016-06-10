<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<cyclos:script src="/pages/customization/themes/exportTheme.js" />
<ssl:form method="post" action="/admin/exportTheme" styleId="exportForm">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="theme.title.export"/></td>
        <cyclos:help page="content_management#export_theme"/>
    </tr>
    <tr>
		<td colspan="2" class="tdContentTableForms">
    		<table class="defaultTable">
			    <tr>
			        <td class="label" width="25%"><bean:message key="theme.title"/></td>
			        <td>
						<html:text property="theme(title)" styleClass="large required"/>
			        </td>
				</tr>
				<tr>
			        <td class="label" width="25%"><bean:message key="theme.filename"/></td>
			        <td>
						<html:text property="theme(filename)" styleClass="large required"/>
			        </td>
				</tr>
				<tr>
			        <td class="label" width="25%"><bean:message key="theme.author"/></td>
			        <td>
						<html:text property="theme(author)" styleClass="large"/>
			        </td>
				</tr>
				<tr>
			        <td class="label" width="25%"><bean:message key="theme.version"/></td>
			        <td>
						<html:text property="theme(version)" styleClass="large"/>
			        </td>
				</tr>
				<tr>
			        <td class="label" width="25%"><bean:message key="theme.description"/></td>
			        <td>
						<html:textarea property="theme(description)" rows="3" styleClass="large"/>
			        </td>
				</tr>
				<tr>
			        <td class="label" width="25%"><bean:message key="theme.stylesToExport"/></td>
			        <td>
			        	<c:forEach var="style" items="${styles}">
			        		<label>
			        			<input type="checkbox" name="theme(styles)" value="${style}" checked="checked" />
			        			<bean:message key="theme.style.${style}" />
			        		</label>
			        		
			        	</c:forEach>
			        </td>
				</tr>
			    <tr>
			        <td colspan="2" align="right">
			        	<input type="submit" class="button" id="exportButton" value="<bean:message key="global.submit"/>">
					</td>            
			    </tr>
		    </table>
		</td>
	</tr>
</table>
</ssl:form>