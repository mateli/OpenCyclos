<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/guarantees/types/listGuaranteeTypes.js" />

<c:set var="helpPage" value="guarantees#guarantee_types_list"/>
<c:set var="titleKey" value="guaranteeType.title.listGuaranteeTypes" />
<script>
	var removeGuaranteeTypeConfirmation = "<cyclos:escapeJS><bean:message key="guaranteeType.removeConfirmation"/></cyclos:escapeJS>";
</script>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
	<cyclos:help page="${helpPage}" />
	<tr>
	    <td colspan="2" align="left" class="tdContentTableForms">
    	  	<table class="defaultTable">
				<tr>
					<td class="tdHeaderContents" width="70%"><bean:message key="guaranteeType.name"/></td>
					<td class="tdHeaderContents" width="20%"><bean:message key="guaranteeType.status"/></td>
					<td class="tdHeaderContents" width="10%">&nbsp;</td>
				</tr>
				<c:forEach var="guaranteeTypeItem" items="${listGuaranteeTypes}">
					<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
						<td>
							<cyclos:escapeHTML><cyclos:truncate value="${guaranteeTypeItem.name}"/></cyclos:escapeHTML> 
						</td>
						
						<c:set var="guaranteeTypeStatus" value="${guaranteeTypeItem.enabled ? 'ENABLED' : 'DISABLED' }"/>
						<td align="center"><bean:message key="guaranteeType.status.${guaranteeTypeStatus}"/></td>
						<bean:define id="tooltip"><bean:message key="guaranteeType${(guaranteeTypeItem.enabled ? '.enabled.' : '.disabled.')}tooltip" /></bean:define>
						<td align="center" title="${tooltip}">
							<c:choose>
								<c:when test="${editable}">
									<img class="edit details" guaranteeTypeId="${guaranteeTypeItem.id}" src="<c:url value="/pages/images/edit.gif" />" />
									<img class="remove" guaranteeTypeId="${guaranteeTypeItem.id}" src="<c:url value="/pages/images/delete.gif" />" />
								</c:when>
								<c:otherwise>
									<img class="view details" guaranteeTypeId="${guaranteeTypeItem.id}" src="<c:url value="/pages/images/view.gif" />" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>
<br/>
<c:if test="${editable}">
	<table class="defaultTableContentHidden">
		<tr>
			<td align="right">
				<span class="label">
					<bean:message key="guaranteeType.action.new" />
				</span>
				<input type="button" id="newButton" class="button" value="<bean:message key="global.submit" />">
			</td>
		</tr>
	</table>
</c:if>