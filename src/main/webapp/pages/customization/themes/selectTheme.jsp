<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<script>
	var chooseThemeMessage = "<cyclos:escapeJS><bean:message key="theme.select.message"/></cyclos:escapeJS>";
	var nothingSelectedMessage = "<cyclos:escapeJS><bean:message key="global.error.nothingSelected"/></cyclos:escapeJS>";
	var selectConfirmationMessage = "<cyclos:escapeJS><bean:message key="theme.select.confirmationMessage"/></cyclos:escapeJS>";
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="theme.remove.confirmation"/></cyclos:escapeJS>";
	var themes = [];
	<c:forEach var="theme" items="${themes}">
		<c:set var="stylesStr">
			<c:forEach var="style" items="${theme.styles}" varStatus="status">
				<bean:message key="theme.style.${style}" />${status.last ? '' : ', '}
			</c:forEach>
		</c:set>
		themes.push({
			title: '<cyclos:escapeJS>${theme.title}</cyclos:escapeJS>',
			author:'<cyclos:escapeJS>${theme.author}</cyclos:escapeJS>',
			filename: '<cyclos:escapeJS>${theme.filename}</cyclos:escapeJS>',
			version: '<cyclos:escapeJS>${theme.version}</cyclos:escapeJS>',
			description: '<cyclos:escapeJS>${theme.description}</cyclos:escapeJS>',
			styles: '<cyclos:escapeJS>${stylesStr}</cyclos:escapeJS>'
			});
	</c:forEach>
</script>
<cyclos:script src="/pages/customization/themes/selectTheme.js" />
<ssl:form method="post" action="/admin/selectTheme" styleId="selectForm">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="theme.title.select"/></td>
        <cyclos:help page="content_management#select_theme"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="25%"><bean:message key="theme.theme"/></td>
                    <td><html:select styleId="themeSelect" property="filename"/></td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="theme.title"/></td>
                    <td class="headerField" id="tdTitle"></td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="theme.styles"/></td>
                    <td class="headerField" id="tdStyles"></td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="theme.author"/></td>
                    <td class="headerField" id="tdAuthor"></td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="theme.version"/></td>
                    <td class="headerField" id="tdVersion"></td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="theme.description"/></td>
                    <td class="headerField" id="tdDescription"></td>
                </tr>
				<tr>
					<td colspan="2" align="right">
						<table class="nested">
							<c:if test="${cyclos:granted(AdminSystemPermission.THEMES_SELECT)}">
								<tr>
									<td class="label"><bean:message key="theme.action.select"/></td>
									<td><input id="selectButton" type="submit" class="button" value="<bean:message key="global.submit"/>"></td>
								</tr>
							</c:if>
							<c:if test="${cyclos:granted(AdminSystemPermission.THEMES_REMOVE)}">
								<tr>
									<td class="label"><bean:message key="theme.action.remove"/></td>
									<td><input id="removeButton" type="button" class="button" value="<bean:message key="global.submit"/>"></td>
								</tr>
							</c:if>
						</table>
					</td>
				</tr>
            </table>
		</td>            
    </tr>
</table>
</ssl:form>
<ssl:form method="post" action="${actionPrefix}/removeTheme" styleId="removeForm">
	<html:hidden property="filename"/>
</ssl:form>
