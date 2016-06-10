<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/accounts/external/fileMappings/setFieldMappingsOrder.js" />

<ssl:form method="post" action="${formAction}">
<html:hidden property="fileMappingId"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="fieldMapping.title.order"/></td>
        <cyclos:help page="bookkeeping#set_field_mappings_order"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTable">
            <table class="defaultTable">
            	<tr>
            		<td align="center">
			        	<br>
			            <ul id="fieldMappings" class="draggableList" style="width:300px">
			                <c:forEach var="fieldMapping" items="${fieldMappings}">
			                	<li>
					            	<input type="hidden" name="fieldsIds" value="${fieldMapping.id}"/>
					                <cyclos:escapeHTML>${fieldMapping.name}</cyclos:escapeHTML>
					            </li>
			                </c:forEach>
						</ul>
						<br>&nbsp;
						<bean:message key="fieldMapping.title.order.description"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<input type="submit" class="button" id="submitButton" value="<bean:message key="global.submit"/>">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td>
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
	</tr>
</table>
</ssl:form>