<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<table class="nested" width="100%" cellpadding="0" cellspacing="0">
	<c:forEach var="field" items="${customFields}">
		<tr>
			<td width="${columnWidth}" class="label">${field.name}</td>
			<td><cyclos:customField field="${field}" editable="true" valueName="${valueName}" fieldName="${fieldName}" /></td>
		</tr>
	</c:forEach>
</table>