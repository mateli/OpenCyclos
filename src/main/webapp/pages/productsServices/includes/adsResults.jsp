<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<table class="defaultTable">
	<tr>
		<td width="${localSettings.maxThumbnailWidth}" class="tdHeaderContents">&nbsp;</td>
		<td class="tdHeaderContents">
			<bean:message key="ad.description"/>
			<c:if test="${lastAdsForTradeType}">
				&nbsp;&nbsp;
				<bean:message key="ad.description.onlyNew"/>
			</c:if>
		</td>
		<td width="10%" class="tdHeaderContents"><bean:message key="ad.price"/></td>
		<c:if test="${not empty member}">
			<td width="10%" class="tdHeaderContents"><bean:message key="ad.tradeType"/></td>
		</c:if>
		<c:if test="${editable}">
			<c:if test="${not empty member}">
				<td width="10%" class="tdHeaderContents"><bean:message key="ad.status"/></td>
			</c:if>
			<c:if test="${myAds || !readOnly}">
				<td width="10%" class="tdHeaderContents">&nbsp;</td>
			</c:if>
		</c:if>
	</tr>
	<c:set var="ownerProperty" value="${localSettings.memberResultDisplay.property}"/>
	<c:forEach var="ad" items="${ads}">
		<c:set var="memberLink"><cyclos:profile elementId="${ad.owner.id}"/></c:set>
		<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			<td valign="middle" align="center" style="margin:0px;padding:0px;"><cyclos:images images="${ad.images}" imageOnly="true" /></td>
			<td valign="top">
				<div class="productTitle"><a class="linkList viewAd" adId="${ad.id}">${ad.title}</a></div>
				<div class="productOwner"><bean:message key="ad.result.by" arg0="${memberLink}"/></div>
				<div class="productDescription"><cyclos:escapeHTML brOnly="${ad.html}"><cyclos:truncate length="120" lines="2" value="${ad.description}" html="${ad.html}"/></cyclos:escapeHTML></div>
			</td>
			<td align="left" nowrap="nowrap"><cyclos:format number="${ad.price}" unitsPattern="${ad.currency.pattern}" /></td>
			<c:if test="${not empty member}">
				<td align="center"><bean:message key="ad.tradeType.${ad.tradeType}"/></td>
			</c:if>
			<c:if test="${editable}">
				<c:if test="${not empty member}">
					<td align="center"><bean:message key="ad.status.${ad.status}"/></td>
				</c:if>
				<c:if test="${myAds || !readOnly}">
					<td align="center" nowrap="nowrap">
						<img class="edit editAd" adId="${ad.id}" src="<html:rewrite page="/pages/images/edit.gif" />" border="0">
						<img class="remove removeAd" adId="${ad.id}" src="<html:rewrite page="/pages/images/delete.gif" />" border="0">
					</td>
				</c:if>
			</c:if>
		</tr>
	</c:forEach>
</table>
