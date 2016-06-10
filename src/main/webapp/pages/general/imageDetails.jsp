<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<br class="small">
<c:set var="hasDrag" value="${fn:length(images) > 1}"/>
<script>
	var hasDrag = ${hasDrag};
</script>
<cyclos:script src="/pages/general/imageDetails.js" />
<ssl:form method="post" action="${formAction}">
<html:hidden property="images(nature)"/>
<html:hidden property="images(owner)"/>

<ul id="images" class="draggableList" style="width:100%">
	<c:forEach var="image" items="${images}">
    	<li>
        <input type="hidden" name="images(details).id" value="${image.id}"/>
        <table class="nested" width="100%">
        	<tr>
            	<td width="${localSettings.maxThumbnailWidth + 8}" align="center">
		        	<img src="<html:rewrite page="/thumbnail?id=${image.id}"/>"/>
				</td>
				<td style="padding-right: 5px">
					<input name="images(details).caption" class="full" value="${image.caption}"/>
				</td>
			</tr>
		</table>
		</li>
	</c:forEach>
</ul>

<br>&nbsp;
<div class="footerNote">
	<bean:message key="${hasDrag ? 'image.details.drag.hint' : 'image.details.hint'}"/>
</div>
<br>
<div align="center">
	<input type="button" id="closeButton" class="button" value="<bean:message key="global.close"/>">
	&nbsp;&nbsp;&nbsp;
	<input type="submit" class="button" id="submitButton" value="<bean:message key="global.submit"/>">
</div>
</ssl:form>