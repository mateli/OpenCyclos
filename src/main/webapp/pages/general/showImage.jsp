<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:set var="showThumbnails" value="${param.showThumbnails}"/>

<cyclos:script src="/pages/general/showImage.js" />
<script>
	var showThumbnails = ${showThumbnails};
	var maxWindowWidth = ${localSettings.maxImageWidth + 80};
</script>

<c:if test="${showThumbnails}">
	<div id="thumbnailContainer" class="popupThumbnailContainer"></div>
</c:if>

<div class="popupImageContainer">
	<img id="popupImage" src="<html:rewrite page="/image"/>?id=${param.id}" border="0">
</div>
<br>
<div class="footerNote" id="caption"></div>


<div align="center">
	<br>
	<input type="button" id="closeButton" class="button" style="vertical-align:top; margin-bottom:5px;" value="<bean:message key="global.close"/>">
</div>
