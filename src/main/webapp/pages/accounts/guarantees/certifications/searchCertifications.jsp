<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<script language="javascript">
	var removeConfirmation = "<cyclos:escapeJS><bean:message key='certification.removeConfirmation'/></cyclos:escapeJS>";
	var buyerGroupIds = ${buyerGroupIds};
	var issuerGroupIds = ${issuerGroupIds}
</script>

<cyclos:script src="/pages/accounts/guarantees/certifications/searchCertifications.js" />

<c:set var="titleKey" value="certification.title.search"/>
<c:set var="helpPage" value="guarantees#certifications_search"/>
 
<ssl:form method="post" action="${formAction}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
		<cyclos:help page="${helpPage}" />
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<cyclos:layout columns="4">
				
				<cyclos:cell className="label"><bean:message key='certification.status'/></cyclos:cell>
				<cyclos:cell colspan="3">
					<cyclos:multiDropDown name="query(statusList)" emptyLabelKey="global.search.all">
						<c:forEach var="stat" items="${status}">
							<c:set var="label"><bean:message key="certification.status.${stat}"/></c:set>
							<cyclos:option value="${stat}" text="${label}" selected="${cyclos:contains(query.statusList, stat)}" />
						</c:forEach>
					</cyclos:multiDropDown>						
					<html:hidden property="query(statusList)"/>					
				</cyclos:cell>
				
				<c:if test="${showIssuer}">							
					<cyclos:cell className="label"><bean:message key="certification.issuerUsername"/></cyclos:cell>
					<cyclos:cell>
						<html:hidden styleId="issuerId" property="query(issuer)"/>
						<input id="issuerUsername" class="full" value="${query.issuer.username}">
						<div id="issuersByUsername" class="autoComplete"></div>
					</cyclos:cell>
					<cyclos:cell className="label"><bean:message key="certification.issuerName"/></cyclos:cell>
					<cyclos:cell>
						<input id="issuerName" class="full" value="${query.issuer.name}">
						<div id="issuersByName" class="autoComplete"></div>
					</cyclos:cell>
				</c:if>

				<c:if test="${showBuyer}">							
					<cyclos:cell className="label"><bean:message key="certification.buyerUsername"/></cyclos:cell>
					<cyclos:cell>
						<html:hidden styleId="buyerId" property="query(buyer)"/>
						<input id="buyerUsername" class="full" value="${query.buyer.username}">
						<div id="buyersByUsername" class="autoComplete"></div>
					</cyclos:cell>
					<cyclos:cell className="label"><bean:message key="certification.buyerName"/></cyclos:cell>
					<cyclos:cell>
						<input id="buyerName" class="full" value="${query.buyer.name}">
						<div id="buyersByName" class="autoComplete"></div>
					</cyclos:cell>
				</c:if>
				
				<cyclos:cell className="nestedLabel"><span class="label"><bean:message key='certification.starts'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(starts).begin"/></cyclos:cell>
				<cyclos:cell className="label"><bean:message key='global.range.to'/></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(starts).end"/></cyclos:cell>

				<cyclos:cell className="nestedLabel"><span class="label"><bean:message key='certification.expires'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(expires).begin"/></cyclos:cell>
				<cyclos:cell className="label"><bean:message key='global.range.to'/></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(expires).end"/></cyclos:cell>

				<cyclos:cell colspan="4">&nbsp;</cyclos:cell>

				<cyclos:cell colspan="2" align="left">
					<c:if test="${isIssuer}">
						<input class="button" type="button" id="newButton" value="<bean:message key='certification.action.create'/>">
					</c:if>					
				</cyclos:cell>
				<cyclos:cell colspan="2" align="right">
					<html:submit styleId="searchButton" styleClass="button"><bean:message key='global.search'/></html:submit>
				</cyclos:cell>
				
			</cyclos:layout>
		</td>
	</tr>
</table>


</ssl:form>
<c:if test="${not empty listCertificationDTOs}"> 
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key='global.searchResults'/></td>
	        <td class="tdHelpIcon" align="right" width="15%" valign="middle">
	       		<cyclos:help page="guarantees#certifications_search_results" td="false"/>
	        </td>
	    </tr>
		<tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	    	  	<table class="defaultTable">
					<tr>
						<th class="tdHeaderContents"><bean:message key="certification.status"/></th>
						<c:if test="${hasViewPermission || isIssuer}">
							<th class="tdHeaderContents"><bean:message key="certification.buyerUsername"/></th>
						</c:if>
						<c:if test="${not isIssuer}">
							<th class="tdHeaderContents"><bean:message key="certification.issuerUsername"/></th>
						</c:if>
						<th class="tdHeaderContents"><bean:message key='global.range.from'/></th>
						<th class="tdHeaderContents"><bean:message key='global.range.to'/></th>
						<th class="tdHeaderContents"><bean:message key="certification.amount"/></th>
						<th class="tdHeaderContents"><bean:message key="certification.usedAmount"/></th>
						<th class="tdHeaderContents">&nbsp;</th>
					</tr>
					<c:forEach var="certificationDTO" items="${listCertificationDTOs}">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td align="left"><cyclos:escapeHTML><bean:message key="certification.status.${certificationDTO.certification.status}"/></cyclos:escapeHTML></td>					
							<c:if test="${hasViewPermission || isIssuer}">
								<td align="left" title="<cyclos:escapeHTML value='${certificationDTO.certification.buyer.name}'/>"><cyclos:profile elementId="${certificationDTO.certification.buyer.id}"/></td>
							</c:if>
							<c:if test="${not isIssuer}">
								<td align="left" title="<cyclos:escapeHTML value='${certificationDTO.certification.issuer.name}'/>"><cyclos:profile elementId="${certificationDTO.certification.issuer.id}"/></td>
							</c:if>
							<td align="center" nowrap="nowrap"><cyclos:format rawDate="${certificationDTO.certification.validity.begin}"/></td>
							<td align="center" nowrap="nowrap"><cyclos:format rawDate="${certificationDTO.certification.validity.end}"/></td>
							<td align="right" nowrap="nowrap"><cyclos:format number="${certificationDTO.certification.amount}" unitsPattern="${certificationDTO.certification.guaranteeType.currency.pattern}"/></td>
							<td align="right" nowrap="nowrap"><cyclos:format number="${certificationDTO.usedAmount}" unitsPattern="${certificationDTO.certification.guaranteeType.currency.pattern}"/></td>
	                    <td align="center" class="small">
	                    	<c:choose>
	                    		<c:when test="${isEditable}">
	                    			<img certificationId="${certificationDTO.certification.id}" src="<c:url value="/pages/images/edit.gif" />" class="edit details" />
	                    		</c:when>
	                    		<c:otherwise>
									<img certificationId="${certificationDTO.certification.id}" src="<c:url value="/pages/images/view.gif" />" class="view details" />
	                    		</c:otherwise>
	                    	</c:choose>
						</td>							
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
	
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right"><cyclos:pagination items="${listCertificationDTOs}"/></td>
		</tr>
	</table>				
</c:if>