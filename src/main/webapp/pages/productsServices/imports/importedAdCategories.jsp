<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/productsServices/imports/importedAdCategories.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="importId"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="adImport.title.newCategories" /></td>
        <cyclos:help page="advertisements#import_ad_categories"/> 
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
			<table class="defaultTable">
				<tr>
					<td class="tdHeaderContents"><bean:message key="ad.category"/></td>
				</tr>
				<c:forEach var="category" items="${categories}">
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td>${category.fullName}</td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
</ssl:form>
