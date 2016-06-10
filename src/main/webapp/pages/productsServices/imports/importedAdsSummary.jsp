<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/productsServices/imports/importedAdsSummary.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="importId" />

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="adImport.title.summary"/></td>
        <cyclos:help page="advertisements#imported_ads_summary"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<c:if test="${empty singleCurrency}">
	                <tr>
	                    <td class="headerLabel" width="50%"><bean:message key="adImport.currency"/></td>
	                    <td class="headerField">${singleCurrency.name}</td>
	                </tr>
            	</c:if>
                <tr>
                    <td class="headerLabel" width="50%"><bean:message key="adImport.totalAds"/></td>
                    <td class="headerField"><a id="totalLink" class="default">${summary.total}</a></td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="adImport.successfulAds"/></td>
                    <td class="headerField"><a id="successLink" class="default">${summary.total - summary.errors}</a></td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="adImport.adsWithErrors"/></td>
                    <td class="headerField"><a id="errorLink" class="default">${summary.errors}</a></td>
                </tr>
                <tr>
                    <td class="headerLabel"><bean:message key="adImport.newCategories"/></td>
                    <td class="headerField"><a id="newCategoriesLink" class="default">${summary.newCategories}</a></td>
                </tr>
                <tr>
					<td><input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>" ></td>
					<c:if test="${(summary.total - summary.errors) > 0}">
						<td align="right"><input type="submit" id="confirmButton" class="button" value="<bean:message key="adImport.confirm"/>" ></td>
					</c:if>
	            </tr>
             </table>
        </td>
    </tr>
</table>
</ssl:form>