<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<div class="printTitle"><bean:message key="ad.title.print"/></div>
<table width="100%">
	<tr>
		<td class="printLabel" width="20%"><bean:message key="ad.tradeType"/>:&nbsp;</td>
		<td class="printData"><bean:message key="ad.tradeType.${query.tradeType}"/></td>
	</tr>
	<c:if test="${not empty query.keywords}">
		<tr>
			<td class="printLabel"><bean:message key="ad.search.keywords"/>:&nbsp;</td>
			<td class="printData">${query.keywords}</td>
		</tr>
	</c:if>
    <c:forEach var="entry" items="${memberFields}">
        <c:set var="field" value="${entry.field}"/>
		<c:set var="value" value="${entry.value.value}"/>
		<c:if test="${not empty value}">
			<tr>
				<td class="printLabel">${field.name}:&nbsp;</td>
				<td class="printData"><cyclos:customField field="${field}" textOnly="true" value="${value}"/></td>
			</tr>
		</c:if>
    </c:forEach>
    <c:forEach var="entry" items="${basicAdFields}">
        <c:set var="field" value="${entry.field}"/>
        <c:set var="value" value="${entry.value.value}"/>
        <c:if test="${not empty value}">
			<tr>
				<td class="printLabel">${field.name}:&nbsp;</td>
				<td class="printData"><cyclos:customField field="${field}" textOnly="true" value="${value}"/></td>
			</tr>
		</c:if>
    </c:forEach>
    <c:forEach var="entry" items="${advancedAdFields}">
        <c:set var="field" value="${entry.field}"/>
        <c:set var="value" value="${entry.value.value}"/>
        <c:if test="${not empty value}">
			<tr>
				<td class="printLabel">${field.name}:&nbsp;</td>
				<td class="printData"><cyclos:customField field="${field}" textOnly="true" value="${value}"/></td>
			</tr>
		</c:if>
    </c:forEach>
	<c:if test="${not empty query.status}">
		<tr>
			<td class="printLabel"><bean:message key="ad.status"/>:&nbsp;</td>
			<td class="printData"><bean:message key="ad.status.${query.status}"/></td>
		</tr>
	</c:if>
	<c:if test="${not empty query.category}">
		<tr>
			<td class="printLabel"><bean:message key="ad.category"/>:&nbsp;</td>
			<td class="printData">${category.fullName}</td>
		</tr>
	</c:if>
	<c:if test="${not empty sinceDate}">
		<tr>
		   	<td class="printLabel"><bean:message key="ad.print.since"/>:&nbsp;</td>
			<td class="printData"><cyclos:format date="${sinceDate}"/></td>
  		</tr>
  	</c:if>
  	<c:if test="${not empty query.initialPrice || not empty query.finalPrice}">
		<tr>
 			<td class="printLabel"><bean:message key="ad.search.price"/>&nbsp;</td>
			<td class="printData">
				<c:if test="${not empty query.initialPrice}">
					<span class="printLabel"><bean:message key="ad.search.price.begin"/>:&nbsp;</span>
					<cyclos:format number="${query.initialPrice}"/>&nbsp;
				</c:if>
				<c:if test="${not empty query.finalPrice}">
					<span class="printLabel"><bean:message key="ad.search.price.end"/>:&nbsp;</span>
					<cyclos:format number="${query.finalPrice}"/>&nbsp;
				</c:if>
			</td>
   		</tr>
   	</c:if>
</table>
<hr class="print">
<c:forEach var="ad" items="${ads}" varStatus="counter">
	<c:if test="${ localSettings.maxIteratorResults == 0 || counter.index < localSettings.maxIteratorResults }">
	<cyclos:layout columns="4" className="printContent" width="100%">
		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="ad.title"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="40%">${ad.title}</cyclos:cell>
		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="ad.category"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="40%">${ad.category.fullName}</cyclos:cell>

		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="ad.owner"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="40%">${ad.owner.name}</cyclos:cell>
		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="member.username"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="40%">${ad.owner.username}</cyclos:cell>
		
		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="ad.price"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="40%"><cyclos:format number="${ad.price}" unitsPattern="${ad.currency.pattern}"/></cyclos:cell>
		
		<cyclos:cell className="printLabel" nowrap="nowrap" width="10%"><bean:message key="ad.publicationPeriod"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" width="40%">
			<c:choose><c:when test="${ad.permanent}">
				<bean:message key="ad.publicationPeriod.permanent"/>
			</c:when><c:otherwise>
				<cyclos:format rawDate="${ad.publicationPeriod.begin}"/>
				-
				<cyclos:format rawDate="${ad.publicationPeriod.end}"/>
			</c:otherwise></c:choose>
		</cyclos:cell>
		
		<c:forEach var="field" items="${adFields}">
			<cyclos:cell className="printLabel" nowrap="nowrap" width="10%">${field.name}:&nbsp;</cyclos:cell>
			<cyclos:cell className="printData" width="40%"><cyclos:customField field="${field}" textOnly="true" collection="${ad.customValues}"/></cyclos:cell>
		</c:forEach>
		
		<cyclos:rowBreak/>
		
		<cyclos:cell className="printLabel" valign="top" nowrap="nowrap" width="10%"><bean:message key="ad.description"/>:&nbsp;</cyclos:cell>
		<cyclos:cell className="printData" colspan="3">
			<c:choose><c:when test="${ad.html}">
				${ad.description}
			</c:when><c:otherwise>
				<cyclos:escapeHTML>${ad.description}</cyclos:escapeHTML>
			</c:otherwise></c:choose>
		</cyclos:cell>		
	</cyclos:layout>
	<hr class="print">
	
	<cyclos:clearCache />
	</c:if>		
	<c:if test="${ localSettings.maxIteratorResults > 0 && counter.index == localSettings.maxIteratorResults }">
   	<tr>
 		<td class="tdPrintHeader" colspan="4"><bean:message key="reports.print.limitation" arg0="${localSettings.maxIteratorResults}"/></td>
  	</tr>
 	</c:if>
</c:forEach>