<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/customization/files/listCustomizedFiles.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="customizedFile.removeConfirmation"/></cyclos:escapeJS>";
	var type = "${type}";
</script>

<c:choose>
	<c:when test="${type == 'STATIC_FILE'}">
		<c:set var="titleKey" value="customizedFile.title.search.static"/>
		<c:set var="helpPage" value="content_management#customized_files"/>
	</c:when>
	<c:when test="${type == 'HELP'}">
		<c:set var="titleKey" value="customizedFile.title.search.help"/>
		<c:set var="helpPage" value="content_management#customized_files"/>
	</c:when>
	<c:when test="${type == 'STYLE'}">
		<c:set var="titleKey" value="customizedFile.title.search.css"/>
		<c:set var="helpPage" value="content_management#customized_files"/>
	</c:when>
	<c:when test="${type == 'APPLICATION_PAGE'}">
		<c:set var="titleKey" value="customizedFile.title.applicationPage"/>
		<c:set var="helpPage" value="content_management#customized_files"/>
	</c:when>
	
</c:choose>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
        <cyclos:help page="${helpPage}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<td class="tdHeaderContents"><bean:message key="customizedFile.name"/></td>
                    <td class="tdHeaderContents" width="15%">&nbsp;</td>
                </tr>
                <c:forEach var="file" items="${files}">
	                <c:set var="fileNameClass" value=""/>
                	<c:if test="${file.conflict}">
                		<c:set var="fileNameClass" value="class='conflict'"/>
                	</c:if>
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                	<td><span ${fileNameClass}>${file.name}</span></td>
	                    <td align="center" nowrap="nowrap">
	                    	<c:if test="${type != 'STYLE' && type != 'APPLICATION_PAGE'}">
	                    		<img class="preview" src="<c:url value="/pages/images/preview.gif"/>" fileName="<cyclos:escapeHTML>${file.name}</cyclos:escapeHTML>" border="0">
	                    	</c:if>
	                    	<c:if test="${cyclos:granted(AdminSystemPermission.CUSTOMIZED_FILES_MANAGE)}">
		                    	<img class="edit" src="<c:url value="/pages/images/edit.gif"/>" fileId="${file.id}" border="0">
		                    	<img class="remove" src="<c:url value="/pages/images/delete.gif"/>" fileId="${file.id}" border="0">
		                    </c:if>
	                    </td>
	                </tr>
                </c:forEach>
			</table>
		</td>
	</tr>
</table>

<c:if test="${cyclos:granted(AdminSystemPermission.CUSTOMIZED_FILES_MANAGE)}">
	<table class="defaultTableContentHidden">
		<tr>
			<td align="right">
				<span class="label"><bean:message key="customizedFile.action.customizeNew"/></span>
				<input type="button" class="button" id="newButton" value="<bean:message key="global.submit"/>">
			</td>
		</tr>
	</table>
</c:if>
		
