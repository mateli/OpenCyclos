<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/productsServices/imports/importedAdsDetails.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="importId"/>
<html:hidden property="status"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="adImport.title.details.${lowercaseStatus}" /></td>
        <cyclos:help page="advertisements#imported_ad_details"/> 
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">            	
                <tr>
                    <td width="20%" class="label"><bean:message key="adImport.lineNumber"/></td>
                    <td><html:text property="query(lineNumber)" styleClass="number small" /></td>
					<td align="right"><input type="submit" class="button" value="<bean:message key="global.search"/>"></td>
				</tr>
            </table>
          </td>            
    </tr>
</table>
</ssl:form>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
        <cyclos:help page="advertisements#imported_ad_details_result"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
			<table class="defaultTable">
				<tr>
					<td align="center" width="10%" class="tdHeaderContents"><bean:message key="adImport.lineNumber"/></td>
					<td class="tdHeaderContents"><bean:message key="ad.owner"/></td>
					<td class="tdHeaderContents"><bean:message key="ad.category"/></td>
					<td class="tdHeaderContents"><bean:message key="ad.price"/></td>
					<c:if test="${cyclos:name(query.status) != 'SUCCESS'}">
						<td class="tdHeaderContents"><bean:message key="adImport.status"/></td>
					</c:if>
				</tr>
				<c:set var="memberProperty" value="${localSettings.memberResultDisplay.property}"/>
				<c:forEach var="importedAd" items="${importedAds}">
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td align="center">${importedAd.lineNumber}</td>
						<td align="center">${importedAd.owner[memberProperty]}</td>
						<td>${empty importedAd.existingCategory ? importedAd.importedCategory.fullName : importedAd.existingCategory.fullName}</td>
						<td><cyclos:format number="${importedAd.price}" unitsPattern="${adImport.currency.pattern}" /></td>
						<c:if test="${cyclos:name(query.status) != 'SUCCESS'}">
							<td><bean:message key="adImport.status.${importedAd.status}" arg0="${importedAd.errorArgument1}" arg1="${importedAd.errorArgument2}"/></td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right"><cyclos:pagination items="${importedAds}"/></td>
	</tr>
</table>		
