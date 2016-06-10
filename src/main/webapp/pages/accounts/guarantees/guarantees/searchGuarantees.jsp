<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>

<cyclos:script src="/pages/accounts/guarantees/guarantees/searchGuarantees.js" />

<script type="text/javascript">
	var issuerGroupsId = ${issuerGroupsId};
	var buyerGroupsId = ${buyerGroupsId};
	var sellerGroupsId = ${sellerGroupsId};
	var guaranteeTypeIdsWithBuyerOnly = ${guaranteeTypeIdsWithBuyerOnly};
</script>

<c:set var="titleKey" value="guarantee.title.searchGuarantees"/>
<c:set var="titleKey2" value="guarantee.title.guaranteeList"/>
<c:set var="helpPage" value="guarantees#guarantees_search"/>

<ssl:form action="${formAction}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
			<cyclos:help page="${helpPage}" />
		</tr>
		<tr>
	        <td colspan="2" align="left" class="tdContentTableForms">
	        	<table class="defaultTable">
	        		<tr>
		            	<td class="label"><bean:message key="guarantee.status" /></td>
		            	<td colspan="${isBuyer || isSeller ? 1 : 3}">
							<cyclos:multiDropDown name="query(statusList)" emptyLabelKey="global.search.all">
								<c:forEach var="stat" items="${guaranteeStatuses}">
									<c:set var="label"><bean:message key="guarantee.status.${stat}"/></c:set>
									<cyclos:option value="${stat}" text="${label}" selected="${cyclos:contains(query.statusList, stat)}" />
								</c:forEach>
							</cyclos:multiDropDown>
							<html:hidden property="query(statusList)" value=""/>						
		            	</td>
		            	<c:if test="${isBuyer || isSeller}">
							<td class="label" colspan="3">
		            			<html:checkbox styleId="withBuyerOnly" property="query(withBuyerOnly)"/>
								<bean:message key="guarantee.myWithBuyerOnly" />		            			
		            		</td>
		            	</c:if>
		            </tr>		            		            
		            <c:if test="${showGuaranteeType}">
			            <tr>
			            	<td class="label">
								<bean:message key="guarantee.guaranteeType"/>
							</td>
							<td colspan="3"> 
								<html:select styleId="guaranteeType" property="query(guaranteeType)">
									<html:option value=""><bean:message key="global.search.all"/></html:option>																
					    			<c:forEach var="guaranteeTypeItem" items="${guaranteeTypes}">
										<html:option value="${guaranteeTypeItem.id}">${guaranteeTypeItem.name}</html:option>
					    			</c:forEach>				    			
								</html:select>					
							</td>	
			            </tr>
			        </c:if>
					<c:forEach var="entry" items="${customFields}">
				        <c:set var="field" value="${entry.field}"/>
				        <c:set var="value" value="${entry.value}"/>
			            <tr>
			            	<td class="label">${field.name}</td>
							<td colspan="3"> 
								<cyclos:customField field="${field}" value="${value}" editable="true" valueName="query(customValues).value" fieldName="query(customValues).field" search="true" />
							</td>	
			            </tr>
					</c:forEach>
		            <c:if test="${showIssuer}">
			            <tr>
	   		            	<td width="25%" class="label"><bean:message key="guarantee.issuerUsername" /></td>
			            	<td>
								<html:hidden styleId="issuerId" property="query(issuer)"/>
								<input id="issuerUsername" class="full" value="${query.issuer.username}">
								<div id="issuersByUsername" class="autoComplete"></div>
			            	</td>
	   		            	<td width="25%" class="label"><bean:message key="guarantee.issuerName" /></td>	   		            
			            	<td>
								<input id="issuerName" class="full" value="${query.issuer.name}">
								<div id="issuersByName" class="autoComplete"></div>
			            	</td>
			            </tr>
					</c:if>
	    	        <c:if test="${showBuyer}">
			            <tr class="toHideBuyer">
	   		            	<td width="25%" class="label"><bean:message key="guarantee.buyerUsername" /></td>
			            	<td>
								<html:hidden styleId="buyerId" property="query(buyer)"/>
								<input id="buyerUsername" class="full" value="${query.buyer.username}">
								<div id="buyersByUsername" class="autoComplete"></div>
			            	</td>
	   		            	<td width="25%" class="label"><bean:message key="guarantee.buyerName" /></td>
			            	<td>
								<input id="buyerName" class="full" value="${query.buyer.name}">
								<div id="buyersByName" class="autoComplete"></div>
			            	</td>
			            </tr>
		            </c:if>
		            <c:if test="${showSeller}">
			            <tr class="toHideSeller">
	   		            	<td width="25%" class="label"><bean:message key="guarantee.sellerUsername" /></td>
			            	<td>
								<html:hidden styleId="sellerId" property="query(seller)"/>
								<input id="sellerUsername" class="full" value="${query.seller.username}">
								<div id="sellersByUsername" class="autoComplete"></div>
			            	</td>
	   		            	<td width="25%" class="label"><bean:message key="guarantee.sellerName" /></td>
			            	<td>
								<input id="sellerName" class="full" value="${query.seller.name}">
								<div id="sellersByName" class="autoComplete"></div>
			            	</td>
			            </tr>	            
			        </c:if>
		            <tr class="toHideMember">
   		            	<td width="25%" class="label"><bean:message key="member.username" /></td>
		            	<td>
							<html:hidden styleId="memberId" property="query(member)"/>
							<input id="memberUsername" class="full" value="${query.member.username}">
							<div id="membersByUsername" class="autoComplete"></div>
		            	</td>
   		            	<td width="25%" class="label"><bean:message key="member.memberName" /></td>
		            	<td>
							<input id="memberName" class="full" value="${query.member.name}">
							<div id="membersByName" class="autoComplete"></div>
		            	</td>
		            </tr>			        
		            <tr>
		               	<td width="25%" class="label"><span class="label"><bean:message key='guarantee.starts'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></td>
		            	<td nowrap="nowrap"><html:text styleClass="date small" property="query(starts).begin"/></td>
	   	            	<td width="25%" class="label"><bean:message key="global.range.to" /></td>
		            	<td nowrap="nowrap"><html:text styleClass="date small" property="query(starts).end"/></td>
	       			</tr>
		            <tr>
	   	            	<td width="25%" class="label"><span class="label"><bean:message key='guarantee.expires'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></td>
		            	<td nowrap="nowrap"><html:text styleClass="date small" property="query(expires).begin"/></td>
	   	            	<td width="25%" class="label"><bean:message key="global.range.to" /></td>
		            	<td nowrap="nowrap"><html:text styleClass="date small" property="query(expires).end"/></td>
	       			</tr>
	      			<tr>
	      				<td width="25%" class="label"><span class="label"><bean:message key='guarantee.amount'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></td>
	      				<td>
	      					<html:text styleClass="float medium" property="query(amountLowerLimit)"/>
	      				</td>
						<td class="label"><bean:message key="global.range.to"/></td>
	      				<td>
	      					<html:text styleClass="float medium" property="query(amountUpperLimit)"/>
	      				</td>
	      			</tr>
	      			<tr class="small">
	      				<td colspan="4">&nbsp;</td>
	      			</tr>
					<tr>
					<c:if test="${not empty guaranteeTypesToRegister}">
						<td colspan="3" align="left">
							<select id="newGuarantee" class="large">
								<option value=""><bean:message key="guaranteeType.action.select"/></option>
				    			<c:forEach var="_guaranteeTypes" items="${guaranteeTypesToRegister}">
					     			<option value="${_guaranteeTypes.id}">${_guaranteeTypes.name}</option>
				    			</c:forEach>
							</select>
						</td>
					</c:if>
						<td align="right" colspan="${not empty guaranteeTypesToRegister ? '1' : '4'}">
							<input type="submit" id="searchButton" class="button" value="<bean:message key="global.search"/>"/>
						</td>
					</tr>
	        	</table>
	        </td>
		</tr>
	</table>
</ssl:form>	
<c:if test="${not empty guarantees}"> 
	
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<td class="tdHeaderTable"><bean:message key="${titleKey2}"/></td>
		<cyclos:help page="guarantees#guarantees_search_results" />
		<tr>
		    <td colspan="6" align="left" class="tdContentTableForms">
	    	  	<table class="defaultTable">
					<tr>
						<td class="tdHeaderContents"><bean:message key="guarantee.status"/></td>
						<c:if test="${showIssuer}"><td class="tdHeaderContents"><bean:message key="guarantee.issuer"/></td></c:if>
						<c:if test="${showBuyer && not query.withBuyerOnly}"><td class="tdHeaderContents"><bean:message key="guarantee.buyer"/></td></c:if>
						<c:if test="${showSeller && not query.withBuyerOnly}"><td class="tdHeaderContents"><bean:message key="guarantee.seller"/></td></c:if>
						<td class="tdHeaderContents"><bean:message key="global.range.from"/></td>
						<td class="tdHeaderContents"><bean:message key='global.range.to'/></td>
						<td class="tdHeaderContents"><bean:message key="guarantee.amount"/></td>
						<%-- 
						<td class="tdHeaderContents"><bean:message key="guarantee.issueFee"/></td>
						<td class="tdHeaderContents"><bean:message key="guarantee.creditFee"/></td>
						--%>
						<td class="tdHeaderContents">&nbsp;</td>
					</tr>
					<c:forEach var="guarantee" items="${guarantees}">
						<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
							<td><bean:message key="guarantee.status.${guarantee.status}"/></td>
							<c:if test="${showIssuer}"><td title='<cyclos:escapeHTML value="${guarantee.issuer.name}"/>'><cyclos:profile elementId="${guarantee.issuer.id}"/></td></c:if>
							<c:if test="${showBuyer && not query.withBuyerOnly}"><td title='<cyclos:escapeHTML value="${guarantee.buyer.name}"/>'><cyclos:profile elementId="${guarantee.buyer.id}"/></td></c:if>
							<c:if test="${showSeller && not query.withBuyerOnly}"><td title='<cyclos:escapeHTML value="${guarantee.seller.name}"/>'><cyclos:profile elementId="${guarantee.seller.id}"/></td></c:if>
							<td align="center" nowrap="nowrap"><cyclos:format rawDate="${guarantee.validity.begin}"/></td>
							<td align="center" nowrap="nowrap"><cyclos:format rawDate="${guarantee.validity.end}"/></td>							
							<td align="right" nowrap="nowrap"><cyclos:format number="${guarantee.amount}" unitsPattern="${guarantee.guaranteeType.currency.pattern}" /></td>
							<%--
							<td align="right" nowrap="nowrap"><cyclos:format number="${guarantee.issueFee}" unitsPattern="${guarantee.guaranteeType.currency.pattern}" /></td>
							<td align="right" nowrap="nowrap"><cyclos:format number="${guarantee.creditFee}" unitsPattern="${guarantee.guaranteeType.currency.pattern}" /></td>
							--%>
		                    <td align="center"><img class="guaranteeDetails" src="<c:url value="/pages/images/view.gif" />" guaranteeId="${guarantee.id}" />	</td>
					</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
	
	<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right"><cyclos:pagination items="${guarantees}"/></td>
		</tr>
	</table>
</c:if>
