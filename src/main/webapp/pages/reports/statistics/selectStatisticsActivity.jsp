<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<cyclos:script src="/pages/reports/statistics/selectStatisticsGeneral.js" />

<c:choose>
	<c:when test="${queryExecuted}">
		<c:choose>
			<c:when test="${not empty dataList}">
				<jsp:include flush="true" page="/pages/reports/statistics/statsResults.jsp"/>     
			</c:when>
			<c:otherwise>
				<bean:message key="global.error.nothingSelected"/><p>
			</c:otherwise>
		</c:choose>
		<table class="defaultTableContentHidden">
			<tr>
				<td align="left">
					<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
				</td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		<jsp:include flush="true" page="/pages/reports/statistics/forms/statisticsActivityForm.jsp"/>
	</c:otherwise>
</c:choose>


