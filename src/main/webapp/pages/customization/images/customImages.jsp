<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:choose>
	<c:when test="${nature == 'CUSTOM'}">
		<c:set var="titleKey" value="customImage.title.custom"/>
		<c:set var="newTitleKey" value="customImage.title.new.custom"/>
		<c:set var="helpPage" value="content_management#custom_images"/>
	</c:when>
	<c:when test="${nature == 'STYLE'}">
		<c:set var="titleKey" value="customImage.title.style"/>
		<c:set var="newTitleKey" value="customImage.title.new.style"/>
		<c:set var="helpPage" value="content_management#style_images"/>
	</c:when>
</c:choose>
<cyclos:script src="/pages/customization/images/customImages.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="customImage.removeConfirmation"/></cyclos:escapeJS>";
	var nature = "${nature}";
</script>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
        <cyclos:help page="${helpPage}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<td class="tdHeaderContents"><bean:message key="customImage.name"/></td>
                	<td class="tdHeaderContents"><bean:message key="customImage.size"/></td>
                	<td class="tdHeaderContents"><bean:message key="customImage.thumbnail"/></td>
                	<c:if test="${cyclos:granted(AdminSystemPermission.CUSTOM_IMAGES_MANAGE)}">
                		<td class="tdHeaderContents" width="5%">&nbsp;</td>
                	</c:if>
                </tr>
                <c:forEach var="image" items="${images}">
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                	<td align="center">${image.name}</td>
	                	<td align="center"><cyclos:format bytes="${image.imageSize}"/></td>
	                    <td align="center"><a class="showImage" imageId="${image.id}"><cyclos:customImage type="${fn:toLowerCase(nature)}" name="${image.name}" thumbnail="true" /></a></td>
						<c:if test="${cyclos:granted(AdminSystemPermission.CUSTOM_IMAGES_MANAGE)}">
		                    <td align="center" nowrap="nowrap"><img src="<c:url value="/pages/images/delete.gif"/>" class="remove" imageId="${image.id}"/></td>
		                </c:if>
	                </tr>
                </c:forEach>
			</table>
		</td>
	</tr>
</table>


<c:if test="${cyclos:granted(AdminSystemPermission.CUSTOM_IMAGES_MANAGE)}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="${newTitleKey}"/></td>
	        <cyclos:help page="content_management#images_upload"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="center" valign="middle" class="tdContentTable">
	        	
	        	<ssl:form action="${formAction}" method="post" enctype="multipart/form-data">
	        		<html:hidden property="nature" />
		            <span class="label"><bean:message key="customImage.upload"/></span>
					<html:file property="upload"/>
					&nbsp;&nbsp;&nbsp;
					<input type="submit" class="button" value="<bean:message key="global.submit"/>">
				</ssl:form>
				&nbsp;
			</td>
		</tr>
	</table>
</c:if>
