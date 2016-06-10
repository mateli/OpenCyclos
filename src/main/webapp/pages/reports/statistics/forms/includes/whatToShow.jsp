<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>

<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td class="label"><bean:message key="reports.stats.general.whatToShow"/></td>
		<td>
			<html:select styleId="whatToShow" property="query(whatToShow)">
				<c:forEach var="wts" items="${whatToShow}">
					<html:option value="${wts}">
						<bean:message key="reports.stats.activity.whatToShow.${wts}"/>
					</html:option>
				</c:forEach>
			</html:select>
		</td>
	</tr>
</table>
