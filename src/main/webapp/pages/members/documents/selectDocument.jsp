<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/members/documents/selectDocument.js" />

<script>
	var memberId = "${member.id}";
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="document.removeConfirmation"/></cyclos:escapeJS>";
</script>

<c:set var="colWidth" value='${myDocuments ? "90%" : "70%"}'/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${myDocuments ? 'document.title.select.my' : 'document.title.select.of'}" arg0="${member.name}"/></td>
        <cyclos:help page="documents#member_document"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<td class="tdHeaderContents" width="${colWidth}"><bean:message key="document.name"/></td>
                	<c:if test="${not myDocuments}">
                		<td class="tdHeaderContents" width="20%"><bean:message key="document.nature"/></td>
                	</c:if>
                    <td class="tdHeaderContents" width="10%">&nbsp;</td>
                </tr>
                <c:forEach var="document" items="${documents}">
                	<c:choose><c:when test="${cyclos:name(document.nature) == 'DYNAMIC'}">
                		<c:set var="previewClass" value="dynamicPreview" />
                		<c:set var="hasFormPage" value="${document.hasFormPage}" />
                	</c:when><c:when test="${cyclos:name(document.nature) == 'MEMBER'}">
                		<c:set var="previewClass" value="memberPreview" />
                		<c:set var="visibility" value="${document.visibility}"/>
                	</c:when><c:otherwise>
                		<c:if test="${!byBroker}">
	                		<c:set var="previewClass" value="staticPreview" />
                		</c:if>
						<c:if test="${byBroker}">
                			<c:set var="previewClass" value="staticBrokerPreview" />
                		</c:if>
                	</c:otherwise></c:choose>
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                	<td><cyclos:escapeHTML>${document.name}</cyclos:escapeHTML></td>
	                	<c:if test="${not myDocuments}">
	                		<td align="center"><bean:message key="document.nature.${document.nature}"/></td>
	                	</c:if>
	                    <td align="left" nowrap="nowrap">
	                    	<img class="${previewClass}" src="<c:url value="/pages/images/preview.gif"/>" documentId="${document.id}" hasFormPage="${hasFormPage}" border="0">
	                    	<c:if test="${!removed and (cyclos:name(document.nature) == 'MEMBER' and (adminCanManage or (byBroker and brokerCanManage and (visibility == 'MEMBER' or visibility == 'BROKER'))))}">
		                    	<img class="edit" src="<c:url value="/pages/images/edit.gif"/>" documentId="${document.id}" border="0">
	                    		<img class="remove" src="<c:url value="/pages/images/delete.gif"/>" documentId="${document.id}" border="0">
	                    	</c:if>
	                    </td>
	                </tr>
                </c:forEach>
			</table>
		</td>
	</tr>
</table>

<c:if test="${!removed && (adminCanManage or (byBroker and brokerCanManage))}">
	
	<table class="defaultTableContentHidden">
		<tr>
			<c:if test="${!myDocuments}">
				<td align="left">
					<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
				</td>
			</c:if>
			<td align="right">
				<span class="label"><bean:message key="document.action.new.member"/></span>
				<input type="button" class="button" id="newMemberButton" value="<bean:message key="global.submit"/>">
			</td>
		</tr>
	</table>
</c:if>