<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:url var="updateProfileUrl" value="${pathPrefix}/${isOperator ? 'operatorProfile' : 'profile'}">
	<c:param name="fromMenu" value="true" />
	<c:param name="fromQuickAccess" value="true" />
</c:url>
<c:url var="searchMembersUrl" value="${pathPrefix}/searchMembers">
	<c:param name="fromMenu" value="true" />
	<c:param name="fromQuickAccess" value="true" />
</c:url>
<c:url var="accountInformationUrl" value="${pathPrefix}/accountOverview">
	<c:param name="fromMenu" value="true" />
	<c:param name="fromQuickAccess" value="true" />
</c:url>
<c:url var="makePaymentUrl" value="${pathPrefix}/payment">
	<c:param name="fromMenu" value="true" />
	<c:param name="fromQuickAccess" value="true" />
	<c:param name="selectMember" value="true" />
</c:url>
<c:url var="placeAdUrl" value="${pathPrefix}/editAd">
	<c:param name="fromMenu" value="true" />
	<c:param name="fromQuickAccess" value="true" />
</c:url>
<c:url var="searchAdsUrl" value="${pathPrefix}/searchAds">
	<c:param name="fromMenu" value="true" />
	<c:param name="fromQuickAccess" value="true" />
</c:url>
<c:url var="messagesUrl" value="${pathPrefix}/searchMessages">
	<c:param name="fromMenu" value="true" />
	<c:param name="fromQuickAccess" value="true" />
</c:url>
<c:url var="contactsUrl" value="${pathPrefix}/contacts">
	<c:param name="fromMenu" value="true" />
	<c:param name="fromQuickAccess" value="true" />
</c:url>
 
<c:set var="width">${100 / quickAccess.functionCount}%</c:set>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
     <tr>
        <td class="tdHeaderTable"><bean:message key="quickAccess.title"/></td>
        <cyclos:help page="home#quick_access"/>
    </tr>
    <tr> 
        <td class="tdContentTableForms" colspan="2">
			<table class="defaultTable">
				<tr>
					<c:if test="${quickAccess.updateProfile}">
						<td width="${width}" align="center" valign="top">
							<a href="${updateProfileUrl}" class="quickAccessLink">
								<cyclos:customImage name="quickAccess_updateProfile" type="system" class="quickAccessIcon" />
								<bean:message key="quickAccess.updateProfile" />
							</a>
						</td>
					</c:if>
					<c:if test="${quickAccess.accountInformation}">
						<td width="${width}" align="center" valign="top">
							<a href="${accountInformationUrl}" class="quickAccessLink">
								<cyclos:customImage name="quickAccess_accountInfo" type="system" class="quickAccessIcon" />
								<bean:message key="quickAccess.accountInfo" />
							</a>
						</td>
					</c:if>
					<c:if test="${quickAccess.memberPayment}">
						<td width="${width}" align="center" valign="top">
							<a href="${makePaymentUrl}" class="quickAccessLink">
								<cyclos:customImage name="quickAccess_makePayment" type="system" class="quickAccessIcon" />
								<bean:message key="quickAccess.makePayment" />
							</a>
						</td>
					</c:if>
					<c:if test="${quickAccess.publishAd}">
						<td width="${width}" align="center" valign="top">
							<a href="${placeAdUrl}"class="quickAccessLink" >
								<cyclos:customImage name="quickAccess_placeAd" type="system" class="quickAccessIcon" />
								<bean:message key="quickAccess.placeAd" />
							</a>
						</td>
					</c:if>
					<c:if test="${quickAccess.searchAds}">
						<td width="${width}" align="center" valign="top">
							<a href="${searchAdsUrl}" class="quickAccessLink" >
								<cyclos:customImage name="quickAccess_searchAds" type="system" class="quickAccessIcon" />
								<bean:message key="quickAccess.searchAds" />
							</a>
						</td>
					</c:if>
					<c:if test="${quickAccess.searchMembers}">
						<td width="${width}" align="center" valign="top">
							<a href="${searchMembersUrl}" class="quickAccessLink">
								<cyclos:customImage name="quickAccess_searchMembers" type="system" class="quickAccessIcon" />
								<bean:message key="quickAccess.searchMembers" />
							</a>
						</td>
					</c:if>
					<c:if test="${quickAccess.viewMessages}">
						<td width="${width}" align="center" valign="top">
							<a href="${messagesUrl}" class="quickAccessLink" >
								<cyclos:customImage name="quickAccess_messages" type="system" class="quickAccessIcon" />
								<bean:message key="quickAccess.messages" />
							</a>
						</td>
					</c:if>
					<c:if test="${quickAccess.viewContacts}">
						<td width="${width}" align="center" valign="top">
							<a href="${contactsUrl}" class="quickAccessLink">
								<cyclos:customImage name="quickAccess_contacts" type="system" class="quickAccessIcon" />
								<bean:message key="quickAccess.contacts" />
							</a>
						</td>
					</c:if>
	            </tr>
            </table>
        </td>
   </tr>
</table>