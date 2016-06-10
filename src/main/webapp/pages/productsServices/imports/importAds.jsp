<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/productsServices/imports/importAds.js" />

<ssl:form method="post" action="${formAction}" enctype="multipart/form-data">
<c:if test="${not empty singleCurrency}">
	<html:hidden property="import(currency)" value="${singleCurrency.id}" />
</c:if>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="adImport.title.import"/></td>
        <cyclos:help page="advertisements#import_ads"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
            	<c:if test="${empty singleCurrency}">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="adImport.currency"/></td>
	                    <td>
	                    	<html:select styleId="currencySelect" property="import(currency)">
	                    		<c:forEach var="currency" items="${currencies}">
	                    			<html:option value="${currency.id}">${currency.name}</html:option>
	                    		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
                </c:if>
                <tr>
                    <td class="label" width="30%"><bean:message key="adImport.file"/></td>
                    <td><html:file property="upload" /></td>
                </tr>
                <tr>
					<td align="right" colspan="2">
	               		<input type="submit" id="submitButton" class="button" value="<bean:message key="global.submit"/>" >
					</td>
	            </tr>
             </table>
        </td>
    </tr>
</table>
</ssl:form>