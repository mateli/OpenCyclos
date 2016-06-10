<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/customization/documents/listDocuments.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="document.removeConfirmation"/></cyclos:escapeJS>";
</script>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="document.title.list"/></td>
        <cyclos:help page="documents#document_list"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<td class="tdHeaderContents" width="60%"><bean:message key="document.name"/></td>
                	<td class="tdHeaderContents" width="30%"><bean:message key="document.nature"/></td>
                    <td class="tdHeaderContents" width="10%">&nbsp;</td>
                </tr>
                <c:forEach var="document" items="${documents}">
                	<c:choose><c:when test="${cyclos:name(document.nature) == 'DYNAMIC'}">
                		<c:set var="detailsClass" value="dynamicDocumentDetails" />
                		<c:set var="previewClass" value="dynamicPreview" />
                		<c:set var="hasPermission" value="${cyclos:granted(AdminMemberPermission.DOCUMENTS_MANAGE_DYNAMIC)}" />
                	</c:when><c:when test="${cyclos:name(document.nature) == 'STATIC'}">
                		<c:set var="detailsClass" value="staticDocumentDetails" />
                		<c:set var="previewClass" value="staticPreview" />
                		<c:set var="hasPermission" value="${cyclos:granted(AdminMemberPermission.DOCUMENTS_MANAGE_STATIC)}" />
                	</c:when><c:otherwise>
                		<c:set var="detailsClass" value="staticDocumentDetails" />
                		<c:set var="previewClass" value="staticPreview" />
                		<c:set var="hasPermission" value="${cyclos:granted(AdminMemberPermission.DOCUMENTS_MANAGE_MEMBER)}" />
                	</c:otherwise></c:choose>
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                	<td><cyclos:escapeHTML>${document.name}</cyclos:escapeHTML></td>
	                	<td align="center"><bean:message key="document.nature.${document.nature}"/></td>
	                    <td align="center" nowrap="nowrap">
	                    	<img class="${previewClass}" src="<c:url value="/pages/images/preview.gif"/>" documentId="${document.id}" border="0">
	                    	<c:choose><c:when test="${hasPermission}">
								<img class="edit ${detailsClass}" src="<c:url value="/pages/images/edit.gif"/>" documentId="${document.id}" border="0">
	                    		<img class="remove" src="<c:url value="/pages/images/delete.gif"/>" documentId="${document.id}" border="0">
			                </c:when><c:otherwise>
			                	<img class="view ${detailsClass}" src="<c:url value="/pages/images/view.gif"/>" documentId="${document.id}" border="0">
							</c:otherwise></c:choose>
	                    </td>
	                </tr>
                </c:forEach>
			</table>
		</td>
	</tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="right">
			<c:if test="${cyclos:granted(AdminMemberPermission.DOCUMENTS_MANAGE_DYNAMIC)}">
				<span class="label"><bean:message key="document.action.new.dynamic"/></span>
				<input type="button" class="button" id="newDynamicButton" value="<bean:message key="global.submit"/>"> &nbsp;
			</c:if>
			<c:if test="${cyclos:granted(AdminMemberPermission.DOCUMENTS_MANAGE_STATIC)}">
				<span class="label"><bean:message key="document.action.new.static"/></span>
				<input type="button" class="button" id="newStaticButton" value="<bean:message key="global.submit"/>">
			</c:if>				
		</td>
	</tr>
</table>
