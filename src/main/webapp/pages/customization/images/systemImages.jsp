<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/customization/images/systemImages.js" />
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="customImage.title.system"/></td>
        <cyclos:help page="content_management#system_images"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<td class="tdHeaderContents"><bean:message key="customImage.name"/></td>
                	<td class="tdHeaderContents"><bean:message key="customImage.size"/></td>
                	<td class="tdHeaderContents"><bean:message key="customImage.thumbnail"/></td>
                </tr>
                <c:forEach var="vo" items="${images}">
					<c:set var="image" value="${vo.image}"/>
	                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                	<td align="center">${vo.label}</td>
	                	<td align="center"><cyclos:format bytes="${image.imageSize}"/></td>
	                    <td align="center"><a class="showImage" imageId="${image.id}"><cyclos:customImage name="${image.name}" type="system" thumbnail="true" /></a></td>
	                </tr>
                </c:forEach>
			</table>
		</td>
	</tr>
</table>

<c:if test="${cyclos:granted(AdminSystemPermission.CUSTOM_IMAGES_MANAGE)}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="customImage.title.system.update"/></td>
	        <cyclos:help page="content_management#images_upload"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="center" valign="middle" class="tdContentTableForms">
	        	<ssl:form action="${formAction}" method="post" enctype="multipart/form-data">
	            <table class="defaultTable">
	                <tr>
	                   	<td class="label" width="25%"><bean:message key="customImage.upload"/></td>
	                   	<td>
							<html:select property="name">
								<c:forEach var="vo" items="${images}">
									<c:set var="image" value="${vo.image}"/>
									<html:option value="${image.name}">${vo.label}</html:option>
								</c:forEach>
							</html:select>
						</td>
						<td></td>
	            	</tr>
	            	<tr>
	                   	<td class="label"><bean:message key="customImage.choose"/></td>
	                   	<td>
		                   	<html:file property="upload"/>
	                   	</td>
						<td align="right">
		                   	<input type="submit" class="button" value="<bean:message key="global.submit"/>">
						</td>
	            	</tr>
	            </table>
				</ssl:form>
			</td>
		</tr>
	</table>
</c:if>
