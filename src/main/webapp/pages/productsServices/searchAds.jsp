<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/productsServices/searchAds.js" />
<script>
	var removeConfirmationMessage = "<cyclos:escapeJS><bean:message key="ad.removeConfirmation"/></cyclos:escapeJS>";
	var queryExec = ${queryExecuted};
</script>

<ssl:form method="post" action="${formAction}">
<html:hidden property="advanced"/>
<html:hidden property="forceShowFields" value=""/>
<html:hidden property="categoryOnly" value=""/>
<html:hidden property="lastAdsForTradeType" value="${lastAdsForTradeType}"/>
<html:hidden property="query(category)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0" >
	<tr>
		<td class="tdHeaderTable">
			<bean:message key="ad.title.search"/>
		</td>
		<cyclos:help page="advertisements#advertisement_search"/>
	</tr>
	<tr>
		<td align="left" class="tdContentTableForms" colspan="2">
			<table class="defaultTable">
		  		<tr>
					<td width="25%"></td>
					<td colspan="3">
						<table class="nested" width="100%">
							<tr>
								<td valign="top" class="label" style="text-align:left">
									<c:forEach var="tradeType" items="${tradeTypes}">
										<label><html:radio property="query(tradeType)" value="${tradeType}" styleClass="radio tradeType" /> <bean:message key="ad.search.tradeType.${tradeType}"/></label>
					   				</c:forEach>
					   			</td>
				   		   		<td valign="top" class="label" nowrap="nowrap">
				   		   			<c:if test="${queryExecuted}">
				   		   				<b><a id="viewCategories" class="default"><bean:message key="ad.view.categories"/></a></b>&nbsp;&nbsp;&nbsp;&nbsp;
				   		   			</c:if>
				   		   			<c:if test="${!lastAdsForTradeType}">
				   		   				<b><a id="viewLastAds" class="default"><bean:message key="ad.view.lastAds"/></a></b>
				   		   			</c:if>
				   		   		</td>
				   		   	</tr>
				   		</table>
				   	</td>
		  		</tr>
				<tr>
					<td width="25%" class="label" nowrap="nowrap">
						<c:if test="${!searchAdsForm.advanced}">
							<input id="modeButton" type="button" class="button" value="<bean:message key="global.search.ADVANCED"/>">
							&nbsp;&nbsp;&nbsp;
						</c:if>
						<bean:message key="ad.search.keywords"/>
					</td>
					<td align="left" colspan="${searchAdsForm.advanced ? 3 : 1}"><html:text property="query(keywords)" styleClass="full"/></td> 
					<c:if test="${!searchAdsForm.advanced}">
   						<td align="right" nowrap="nowrap" width="25%">
   							<input type="submit" id="searchButton" class="button" value="<bean:message key="global.search"/>">
						</td>		
					</c:if>
				</tr>
			<c:if test="${searchAdsForm.advanced}">
   				<tr>
					<td class="label"><bean:message key="ad.search.withImagesOnly"/></td>
		   			<td>
		   				<html:checkbox property="query(withImagesOnly)" value="true" styleClass="checkbox"/>
						<c:if test="${not empty status}">
							<td class="label" width="25%"><bean:message key="ad.status"/></td>
							<td>
								<html:select property="query(status)" styleClass="InputBoxEnabled">
									<html:option value=""><bean:message key="global.search.all"/></html:option>
									<c:forEach var="stat" items="${status}">
										<html:option value="${stat}"><bean:message key="ad.status.${stat}"/></html:option>
									</c:forEach>
								</html:select>
							</td>
						</c:if>
		   			</td>
				</tr>
				<c:if test="${not empty groupFilters || not empty memberGroups}">
			  		<tr>
						<c:if test="${not empty groupFilters}">
		           			<td class="label"><bean:message key="member.groupFilter"/></td>
		           			<td>
		           				<cyclos:multiDropDown name="query(groupFilters)" emptyLabelKey="member.search.allGroupFilters">
		           					<c:forEach var="groupFilter" items="${groupFilters}">
		           						<cyclos:option value="${groupFilter.id}" text="${groupFilter.name}" />
		           					</c:forEach>
		           				</cyclos:multiDropDown>
		         			</td>
	       				</c:if>
		       			<c:if test="${not empty memberGroups}">
		           			<td class="label"><bean:message key="member.group"/></td>
		           			<td width="35%">
		           				<cyclos:multiDropDown name="query(groups)" emptyLabelKey="member.search.allGroups">
		           					<c:forEach var="memberGroup" items="${memberGroups}">
		           						<cyclos:option value="${memberGroup.id}" text="${memberGroup.name}" />
		           					</c:forEach>
		           				</cyclos:multiDropDown>
		         			</td>
		       			</c:if>
		       		</tr>
		       	</c:if>
				<tr>
   					<td align="left" class="label">
   						<bean:message key="ad.search.price"/>
						&nbsp;
						<bean:message key="global.range.from"/>
					</td>
					<td colspan="3">
						<html:text maxlength="20" property="query(initialPrice)" styleClass="InputBoxEnabled float small"/>
						&nbsp;
						<span class="label"><bean:message key="global.range.to"/></span>
						<html:text maxlength="20" property="query(finalPrice)" styleClass="InputBoxEnabled float small"/>
						&nbsp;
				   		<c:if test="${not empty currencies}">
							<span class="label"><bean:message key="accountType.currency"/></span>
							<html:select property="query(currency)" styleClass="InputBoxEnabled">
				   				<html:option value=""></html:option>
								<c:forEach var="currency" items="${currencies}">
					   				<html:option value="${currency.id}">${currency.symbol}</html:option>
				   				</c:forEach>
	  						</html:select>
					   	</c:if>
					</td>
		   		</tr>
  				<tr>
  				   	<td class="label"><bean:message key="ad.search.since"/></td>
					<td colspan="3">
						<html:select property="query(since).number" styleClass="InputBoxEnabled">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach begin="1" end="12" varStatus="loop">
								<html:option value="${loop.count}">${loop.count}</html:option>
							</c:forEach>
						</html:select>
						<html:select property="query(since).field" styleClass="InputBoxEnabled">
							<c:forEach var="period" items="${sincePeriods}">
				   				<html:option value="${period}"><bean:message key="global.timePeriod.${period}"/></html:option>
			   				</c:forEach>
  						</html:select>			   					
					</td>
		  		</tr>	
			    <c:forEach var="entry" items="${memberFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value.value}"/>
		            <tr>
		                <td class="label">${field.name}</td>
		   				<td colspan="3">
		   					<cyclos:customField field="${field}" value="${value}" search="true" valueName="query(memberValues).value" fieldName="query(memberValues).field"/>
		   				</td>
					</tr>
			    </c:forEach>
			    <c:forEach var="entry" items="${adFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value.value}"/>
		            <tr>
		                <td class="label">${field.name}</td>
		   				<td colspan="3">
		   					<cyclos:customField field="${field}" value="${value}" search="true" valueName="query(adValues).value" fieldName="query(adValues).field"/>
		   				</td>
					</tr>
			    </c:forEach>
   			</c:if>

			<c:if test="${searchAdsForm.advanced}">
   				<tr>
   					<td align="left">
	   					<input id="modeButton" type="button" class="button" value="<bean:message key="global.search.NORMAL"/>">
   					</td>
   					<td align="right" colspan="3">
						<input type="submit" id="searchButton" class="button" value="<bean:message key="global.search"/>">
					</td>
   				</tr>
			</c:if>
			</table>
		</td>
	</tr>
</table>
</ssl:form>

<c:if test="${param.fromQuickAccess}">
	<table class="defaultTableContentHidden"><tr><td>
	<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
	</td></tr></table>
</c:if>

<c:if test="${not empty categoryPath && queryExecuted}">
	
	<table class="defaultTableContentHidden">
		<tr>
			<td>
				<a class="default category" categoryId="0"><bean:message key="adCategory.navigator.root" /></a>	
				&gt;
				<c:forEach var="cat" items="${categoryPath}" varStatus="status">
					<c:choose>
						<c:when test="${category == cat}">
							${cat.name}
						</c:when>
						<c:otherwise>
							<a class="default category" categoryId="${cat.id}">${cat.name}</a>	
						</c:otherwise>
					</c:choose>
		
					<c:if test="${!status.last}">
						&gt;
					</c:if>
				</c:forEach>
			</td>
		</tr>
	</table>
	
</c:if>
		
<%-- The query was executed. Show the ads or a no results message --%>
<c:choose><c:when test="${queryExecuted}">

	<c:choose><c:when test="${empty ads}">
		<div class="footerNote" helpPage="advertisements#search_ads_result"><bean:message key="ad.search.noResults"/></div>
	</c:when><c:otherwise>

		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable">
		        	<bean:message key="global.searchResults"/>
		        </td>
		        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
		        	<c:if test="${isAdmin}">
		        		<img class="exportCSV" src="<c:url value="/pages/images/save.gif"/>" border="0">
		        	</c:if>
	        		<img class="print" src="<c:url value="/pages/images/print.gif"/>" border="0">
			        <cyclos:help page="advertisements#search_ads_result" td="false"/>
		        </td>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
                   	<jsp:include flush="true" page="/pages/productsServices/includes/adsResults.jsp"/>
				</td>
			</tr>
		</table>
		
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right"><cyclos:pagination items="${ads}"/></td>
			</tr>
		</table>		
	</c:otherwise></c:choose>

</c:when><c:otherwise>
	<%-- The query has not been executed: show the categories --%>
	
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="ad.title.categories"/></td>
	        <td class="tdHelpIcon">&nbsp;</td>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists" style="padding:10px">
	        	<table class="defaultTable">
	        		<tr>
	        			<td width="50%" valign="top">
				        	<c:forEach var="category" items="${categories}" varStatus="status">
				        		<%-- Since there's no way to set the scope on the forEach tag, we must copy category from page to request scope --%>
				        		<c:set var="category" scope="request" value="${category}"/>
		        				<ul class="categoryLevel0">
									<jsp:include flush="true" page="/pages/productsServices/includes/categories.jsp"/>
								</ul>
								<c:if test="${status.count == splitCategoriesAt}">
									</td>
									<td width="50%" valign="top">
								</c:if>
							</c:forEach>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

</c:otherwise></c:choose>