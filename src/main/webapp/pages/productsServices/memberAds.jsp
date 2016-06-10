<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<cyclos:script src="/pages/productsServices/memberAds.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="ad.removeConfirmation"/></cyclos:escapeJS>";
	var memberId = "${memberAdsForm.memberId == 0 ? loggedUser.id : memberAdsForm.memberId}";
</script>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<c:choose><c:when test="${myAds}">
	        	<bean:message key="ad.title.my"/>
	        </c:when><c:otherwise>
	        	<bean:message key="ad.title.of" arg0="${member.name}"/>
	        </c:otherwise></c:choose>
        </td>
        <c:choose>
        	<c:when test="${isAdmin}">
        		<c:set var="helpPage" value="advertisements#admin_ads_of_member"/>
        	</c:when><c:otherwise>
        		<c:choose>
	        		<c:when test="${myAds}">
		        		<c:set var="helpPage" value="advertisements#member_my_ads"/>
	        		</c:when><c:otherwise>
		        		<c:set var="helpPage" value="advertisements#ads_of_member"/>
	        		</c:otherwise>
	        	</c:choose>
        	</c:otherwise>
        </c:choose>
        <cyclos:help page="${helpPage}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
        	<jsp:include flush="true" page="/pages/productsServices/includes/adsResults.jsp"/>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
	<c:if test="${!myAds}">
		<td align="left">
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
	</c:if>
	
	<c:if test="${editable && !maxAds}">
		<td align="right">
			<span class="label"><bean:message key="ad.new"/></span>
			<input type="button" class="button" id="newButton" value="<bean:message key="global.submit"/>">
		</td>
	</c:if>
	</tr>
</table>
<c:if test="${editable && maxAds}">
	
	<div class="footerNote"><bean:message key="ad.maxAdsMessage"/></div>
</c:if>