<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:set var="layoutColspan" scope="request" value="${empty loggedUser ? 1 : 2}" />

<div class="topContainer" id="topContainer" cellpadding="0" cellspacing="0" style="display:none">
<div class="topContainerBorder">
<div class="topTable" id="topTable" cellpadding="0" cellspacing="0">
	
	<div class="headerBar" colspan="${layoutColspan}" style="position:relative">
		<cyclos:includeCustomizedFile type="static" name="top.jsp" groupId="${empty loggedUserId ? cookie.groupId.value : ''}" groupFilterId="${empty loggedUserId ? cookie.groupFilterId.value : ''}" />
		<c:if test="${not empty loggedUser}">
			<jsp:include page="/pages/general/loggedUserData.jsp" />
		</c:if>
	</div>
	
    <c:if test="${mainLayout}">
	<div id="menuHolder" class="menuHolder">
		<ul id="menuContainer" class="menuContainer">
			<script>var allMenus=[];</script>
			<!-- Begin: Menu -->
			<tiles:insert attribute="menu" />
			<!-- End:   Left Menu -->
			<div style="clear:both; margin:0; padding:0; font-size:0;line-height:0;"><!--&nbsp;--></div>
		</ul>
	</div>
	</c:if>
	
    <div valign="top" <c:if test="${!mainLayout}">style="width:100%"</c:if> class="tdContents ${standaloneLayout ? 'tdContentsStandalone' : ''}" id="tdContents">