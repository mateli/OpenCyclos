<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<script language="javascript">
	var buyerGroupIds = ${buyerGroupIds};
	var sellerGroupIds = ${sellerGroupIds};
	var multipleSelection = ${buyerFiltered && currencyFiltered && isSeller};
</script>

<cyclos:script src="/pages/accounts/guarantees/paymentobligations/searchPaymentObligations.js" />

<c:set var="titleKey" value="paymentObligation.title.searchPaymentObligations"/>
<c:set var="helpPage" value="guarantees#payment_obligations_search"/>
<c:set var="multipleSelection" value="${buyerFiltered && currencyFiltered && isSeller}"/>

<ssl:form method="post" action="${formAction}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
		<cyclos:help page="${helpPage}"/>
	</tr>
	<tr>
        <td colspan="2" align="left" class="tdContentTableForms">
        	<cyclos:layout columns="4">
				<cyclos:cell className="label"><bean:message key='paymentObligation.status'/></cyclos:cell>
				<cyclos:cell>
					<cyclos:multiDropDown name="query(statusList)" emptyLabelKey="global.search.all">
						<c:forEach var="stat" items="${status}">
							<c:set var="label"><bean:message key="paymentObligation.status.${stat}"/></c:set>
							<cyclos:option value="${stat}" text="${label}" selected="${cyclos:contains(query.statusList, stat)}" />
						</c:forEach>
					</cyclos:multiDropDown>
					<html:hidden property="query(statusList)"/>					
				</cyclos:cell>
				<cyclos:cell className="label"><bean:message key='paymentObligation.currency'/></cyclos:cell>
				<cyclos:cell>
					<html:select property="query(currency)">
						<html:option value=""><bean:message key="global.search.all"/></html:option>
		    			<c:forEach var="curency" items="${currencies}">
			     			<html:option value="${curency.id}">${curency.name}</html:option>
		    			</c:forEach>
					</html:select>
				</cyclos:cell>
				<cyclos:rowBreak/>
				
				<c:if test="${showBuyer}">
					<cyclos:cell className="label"><bean:message key="paymentObligation.buyerUsername"/></cyclos:cell>
					<cyclos:cell>
						<html:hidden styleId="buyerId" property="query(buyer)"/>
						<input id="buyerUsername" class="full" value="${query.buyer.username}">
						<div id="buyersByUsername" class="autoComplete"></div>
					</cyclos:cell>
					<cyclos:cell className="label"><bean:message key="paymentObligation.buyerName"/></cyclos:cell>
					<cyclos:cell>
						<input id="buyerName" class="full" value="${query.buyer.name}">
						<div id="buyersByName" class="autoComplete"></div>
					</cyclos:cell>
				</c:if>
				
				<c:if test="${showSeller}">
					<cyclos:cell className="label"><bean:message key="paymentObligation.sellerUsername"/></cyclos:cell>
					<cyclos:cell>
						<html:hidden styleId="sellerId" property="query(seller)"/>
						<input id="sellerUsername" class="full" value="${query.seller.username}">
						<div id="sellersByUsername" class="autoComplete"></div>
					</cyclos:cell>
					<cyclos:cell className="label"><bean:message key="paymentObligation.sellerName"/></cyclos:cell>
					<cyclos:cell>
						<input id="sellerName" class="full" value="${query.seller.name}">
						<div id="sellersByName" class="autoComplete"></div>
					</cyclos:cell>
				</c:if>
								
				<cyclos:cell className="nestedLabel"><span class="label"><bean:message key='paymentObligation.expiration'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(expiration).begin"/></cyclos:cell>
				<cyclos:cell className="label"><bean:message key='global.range.to'/></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(expiration).end"/></cyclos:cell>
				
				<cyclos:cell className="nestedLabel"><span class="label"><bean:message key='paymentObligation.amount'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text styleClass="float medium" property="query(amountLowerLimit)"/></cyclos:cell>
				<cyclos:cell className="label"><bean:message key='global.range.to'/></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><html:text styleClass="float medium" property="query(amountUpperLimit)"/></cyclos:cell>

				<c:if test="${isSeller}">
					<cyclos:cell colspan="4">&nbsp;</cyclos:cell>
					<cyclos:cell colspan="4" align="left"><bean:message key="paymentObligation.pack.selection" /></cyclos:cell>
					<cyclos:cell colspan="4">&nbsp;</cyclos:cell>
				</c:if>
				
				<cyclos:cell colspan="2" align="left">
					<c:if test="${showNewPaymentObligationButton}">
						<input type="button" id="newButton" class="button" value="<bean:message key="paymentObligation.action.create" />">
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

<c:if test="${not empty paymentObligations}">
	<ssl:form action="${isOperator ? '/operator/acceptPaymentObligationPack' : '/member/acceptPaymentObligationPack'}">
		<html:hidden property="selectIssuer" value="true"/>
		<input id="hiddenPaymentObligation" class="checkbox" type="checkbox" name="paymentObligationIds" style="display: none;">
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key='global.searchResults'/></td>
		        <td class="tdHelpIcon" align="right" width="15%" valign="middle">
		       		<cyclos:help page="guarantees#payment_obligations_search_results" td="false"/>
		        </td>
		    </tr>
			<tr>
			    <td colspan="2" align="left" class="tdContentTableForms">
		    	  	<table class="defaultTable">
						<tr>
							<c:if test="${multipleSelection}">
								<td align="left" class="tdHeaderContents" width="5%"></td>
							</c:if>
							<td class="tdHeaderContents" width="20%"><bean:message key="paymentObligation.status" /></td>
							<c:if test="${showBuyer}">
								<td class="tdHeaderContents"><bean:message key="paymentObligation.buyer" /></td>
							</c:if>
							<c:if test="${showSeller}">
								<td class="tdHeaderContents"><bean:message key="paymentObligation.seller" /></td>
							</c:if>														
							<td class="tdHeaderContents" width="15%" nowrap="nowrap"><bean:message key="paymentObligation.amount" /></td>
							<td class="tdHeaderContents" width="15%" nowrap="nowrap"><bean:message key="paymentObligation.maxPublishDate" /></td>							
							<td class="tdHeaderContents" width="15%" nowrap="nowrap"><bean:message key="paymentObligation.expire" /></td>
							<td class="tdHeaderContents" width="5%">&nbsp;</td>
						</tr>
						<c:forEach var="po" items="${paymentObligations}">
							<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
								<c:choose>
									<c:when test="${multipleSelection && po.status == publishedStatus}">
			            				<td><input class="checkbox" type="checkbox" name="paymentObligationIds" value="${po.id}"></td>
			            			</c:when>
									<c:when test="${multipleSelection && po.status != publishedStatus}">
										<td></td>										
									</c:when>			     
								</c:choose>       		
								<td align="left"><cyclos:escapeHTML><bean:message key="paymentObligation.status.${po.status}"/></cyclos:escapeHTML></td>
								<c:if test="${showBuyer}">										            		
									<td align="left" title="<cyclos:escapeHTML value='${po.buyer.name}'/>"><cyclos:profile elementId="${po.buyer.id}"/></td>
								</c:if>
								<c:if test="${showSeller}">										            		
									<td align="left" title="<cyclos:escapeHTML value='${po.seller.name}'/>"><cyclos:profile elementId="${po.seller.id}"/></td>
								</c:if>
								<td align="right"><cyclos:format number="${po.amount}" unitsPattern="${po.currency.pattern}"/></td>
								<td align="center"><cyclos:format rawDate="${po.maxPublishDate}"/></td>
								<td align="center"><cyclos:format rawDate="${po.expirationDate}"/></td>																
								<td align="center">
			                    	<c:choose>
			                    		<c:when test="${isBuyer && po.status == registeredStatus}">									
											<img class="edit details" src="<c:url value="/pages/images/edit.gif" />" paymentObligationIds="${po.id}" />
			                    		</c:when>
	    		                		<c:otherwise>
											<img class="view details" src="<c:url value="/pages/images/view.gif" />" paymentObligationIds="${po.id}" />
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	
	<c:if test="${multipleSelection}">
		<table class="defaultTableContentHidden">
			<tr>
				<td align="left" style="width: 100%;">
					<input id="selectAllButton" type="button" class="button" value="<bean:message key="global.selectAll"/>">
					<input id="selectNoneButton" type="button" class="button" value="<bean:message key="global.selectNone"/>">
				</td>
				<td align="right">
					<input id="next" type="submit" class="button" style="display: none;" value="<bean:message key="paymentObligation.next"/>">
				</td>
			</tr>
		</table>
	</c:if>
	</ssl:form>
	
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right"><cyclos:pagination items="${paymentObligations}"/></td>
		</tr>
	</table>
</c:if>