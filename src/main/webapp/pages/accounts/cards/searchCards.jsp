<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/cards/searchCards.js" />
<script>	
	var memberId;
	var listOnly = ${listOnly};
	var cardOwner = ${cardOwner};
	var confirmGenerateCard = "<cyclos:escapeJS><bean:message key="member.generateCard.confirmation" arg0="${member.name}"/></cyclos:escapeJS>"
</script>
<ssl:form method="POST" action="${formAction}">
<c:if test="${not listOnly}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key="card.title.search" /></td>
			<cyclos:help page="access_devices#search_cards"/>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableForms">
				<cyclos:layout columns="4" className="defaultTable">
					
					<!-- Expiration From -->
					<cyclos:cell className="label"><bean:message key='card.expirationPeriod.from'/></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(expiration).begin"/></cyclos:cell>
					
					<!-- Expiration To -->
					<cyclos:cell className="label"><bean:message key='card.expirationPeriod.to'/></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><html:text styleClass="date small" property="query(expiration).end"/></cyclos:cell>
					
					<!-- Owner userName -->
					<cyclos:cell className="label"><bean:message key="member.username"/></cyclos:cell>
					<cyclos:cell>
						<html:hidden styleId="memberId" property="query(member)"/>
						<input id="memberUsername" class="full" value="${query.member.username}">
						<div id="membersByUsername" class="autoComplete"></div>
					</cyclos:cell>
					
					<!-- Owner name -->
					<cyclos:cell className="label"><bean:message key="member.memberName"/></cyclos:cell>
					<cyclos:cell>
						<input id="memberName" class="full" value="${query.member.name}">
						<div id="membersByName" class="autoComplete"></div>
					</cyclos:cell>
					
					
					<!-- Groups -->
					<c:if test="${isAdmin}">
						<cyclos:cell class="label"><bean:message key="member.group"/></cyclos:cell>
						<cyclos:cell>            			
		            		<cyclos:multiDropDown name="query(groups)" emptyLabelKey="member.search.allGroups">
		            			<c:forEach var="group" items="${groups}">
		            				<cyclos:option value="${group.id}" text="${group.name}" />
		            			</c:forEach>
		              		</cyclos:multiDropDown>              		
		              	</cyclos:cell>
		            </c:if>
					
					<!-- Card Number -->
					<cyclos:cell className="label"><bean:message key="card.number"/></cyclos:cell>
					<cyclos:cell><html:text style="width:100%" property="query(number)" styleClass="number"/></cyclos:cell>
					
					<!-- Card Type - required,If there is only one card type in the system this filter shouldn't be showed. -->
					<bean:size id="cardTypeSize" collection="${cardTypes}"/>				
					<c:if test="${cardTypeSize > 1}">
						<cyclos:cell className="label"><bean:message key="card.cardType"/></cyclos:cell>
						<cyclos:cell>
							<html:select property="query(cardType)" styleClass="InputBoxEnabled">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
								<c:forEach var="cardType" items="${cardTypes}">
									<html:option value="${cardType.id}">${cardType.name}</html:option>
								</c:forEach>
							</html:select>
						</cyclos:cell>
					</c:if>
						
					<!-- Status -->
					<cyclos:cell width="20%" className="label"><bean:message key="card.status"/></cyclos:cell>
					<cyclos:cell>
						<cyclos:multiDropDown name="query(status)" emptyLabelKey="global.search.all.male">
	            			<c:forEach var="stat" items="${status}">
	            				<c:set var="label"><bean:message key="card.status.${stat}"/></c:set>
	            				<cyclos:option value="${stat}" text="${label}" selected="${cyclos:contains(query.status, stat)}" />
	            			</c:forEach>
	              		</cyclos:multiDropDown>              		
					</cyclos:cell>

					<cyclos:rowBreak/>
					
					<cyclos:cell align="right" colspan="4">
	   					<input type="submit" class="button" value="<bean:message key="global.search"/>">
	   				</cyclos:cell>	   							
				</cyclos:layout>
			</td>
		</tr>
	</table>
	
</c:if>

<c:choose>
	<c:when test="${empty cards}">
		<div class="footerNote"><bean:message key="card.search.noResults"/></div>
		
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<c:if test="${listOnly && !isFromMenu}">
					<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
				</c:if>
			</tr>
		</table>	
	</c:when>
	<c:otherwise>
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		    	<c:choose>
		    		<c:when test="${listOnly}">
		    			<td class="tdHeaderTable"><bean:message key="card.user.of" arg0="${member.name}"/></td>		    					    			
		    		</c:when> 
		    		<c:otherwise>
		    			<td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		    		</c:otherwise>
		    	</c:choose>		        
		        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
			        <c:if test="${isAdmin && !listOnly}">
		        		<img class="exportCSV" src="<c:url value="/pages/images/save.gif"/>" border="0">
		        		<img class="print" src="<c:url value="/pages/images/print.gif"/>" border="0">
		        	</c:if>		        
		        	<cyclos:help page="access_devices#search_card_results" td="false" />
		        </td>	        
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
			        <c:choose>
			        	<c:when test="${listOnly}">
				            <table class="defaultTable">
				                <tr>
				                	<th class="tdHeaderContents" width="45%"><bean:message key="card.number"/></th>
				                    <th class="tdHeaderContents" width="30%"><bean:message key='card.expirationDate'/></th>
				                    <th class="tdHeaderContents" width="20%"><bean:message key='card.status'/></th>
				                    <th class="tdHeaderContents" width="5%">&nbsp;</th>
				                </tr>
				                <c:forEach var="card" items="${cards}">
				                	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
					                    <td align="left"><cyclos:format number="${card.cardNumber}" cardNumberPattern="${card.cardType.cardFormatNumber}"/></td>
					                    <td align="left"><cyclos:format rawDate="${card.expirationDate}"/></td>
					                    <td align="left"><bean:message key="card.status.${card.status}"/></td>
					                    <td align="center"><img cardId="${card.id}" src="<c:url value="/pages/images/view.gif"/>" class="details view"/></td>                   
				                	</tr>
				                </c:forEach>
				            </table>
			            </c:when>
			        	<c:otherwise>
			        		<table class="defaultTable">
				                <tr>
				                	<th class="tdHeaderContents" width="30%"><bean:message key='card.member'/></th>
				                    <th class="tdHeaderContents" width="30%"><bean:message key="card.number"/></th>
				                    <th class="tdHeaderContents" width="20%"><bean:message key='card.expirationDate'/></th>
				                    <th class="tdHeaderContents" width="15%"><bean:message key='card.status'/></th>
				                    <th class="tdHeaderContents" width="5%">&nbsp;</th>
				                </tr>
				                <c:forEach var="card" items="${cards}">
				                	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
					                    <td align="left" ><cyclos:truncate value="${card.owner.name}"/></td>
					                    <td align="left"><cyclos:format number="${card.cardNumber}" cardNumberPattern="${card.cardType.cardFormatNumber}"/></td>
					                    <td align="left"><cyclos:format rawDate="${card.expirationDate}"/></td>
					                    <td align="left"><bean:message key="card.status.${card.status}"/></td>
					                    <td align="center"><img cardId="${card.id}" src="<c:url value="/pages/images/view.gif"/>" class="details view"/></td>                   
				                	</tr>
				                </c:forEach>
				            </table>
			        	</c:otherwise>
			        </c:choose>
		        </td>
		    </tr>
		</table>
		
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<c:if test="${!isFromMenu && listOnly}">
					<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
				</c:if>
				<td align="right"><cyclos:pagination items="${cards}"/></td>
			</tr>
		</table>		
	</c:otherwise>
</c:choose>
</ssl:form>
<c:if test="${listOnly && (cyclos:granted(AdminMemberPermission.CARDS_GENERATE) || cyclos:granted(BrokerPermission.CARDS_GENERATE)) && ((!isFromMenu && isBroker) || !isBroker)}">
	
	<ssl:form method="post" action="${actionPrefix}/createCard">
	<html:hidden property="memberId" value="${member.id}"/>
	<html:hidden property="listOnly" value="${listOnly}"/>
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right" colspan="4">
					<span class="label"><bean:message key="card.new"/></span>
					<input type="submit" class="button" id="generateCardButton" value="<bean:message key="global.submit"/>">
				</td>
			<tr>			
		</table>
	</ssl:form>
</c:if>
