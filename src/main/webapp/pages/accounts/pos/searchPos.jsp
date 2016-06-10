<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/pos/searchPos.js" />

<script>
var removeConfirmation = "<cyclos:escapeJS><bean:message key="pos.remove.confirmation"/></cyclos:escapeJS>"
</script>

<ssl:form method="POST" action="${formAction}">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key="pos.title.search" /></td>
			<cyclos:help page="access_devices#search_pos"/>
		</tr>
		<tr>
			<td colspan="2" align="left" class="tdContentTableForms">
				<cyclos:layout columns="4" className="defaultTable">
					
					<!-- Status -->
					<cyclos:cell width="20%" className="label"><bean:message key="pos.status"/></cyclos:cell>
					<cyclos:cell width="30%">
						<cyclos:multiDropDown name="query(statuses)" emptyLabelKey="global.search.all.male">
	            			<c:forEach var="stat" items="${statuses}">
	            			<c:set var="label"><bean:message key="pos.status.${stat}"/></c:set>
	            				<cyclos:option value="${stat}" text="${label}"/>
	            			</c:forEach>
	              		</cyclos:multiDropDown>              		
					</cyclos:cell>
					
					<!-- PosId -->
					<cyclos:cell width="20%" className="label"><bean:message key="pos.posId"/></cyclos:cell>
					<cyclos:cell width="30%"><html:text style="width:100%" property="query(posId)"/></cyclos:cell>		
					
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
						
					<cyclos:rowBreak/>
					
					<c:set var="searchColspan" value="4"/>
					<c:if test="${cyclos:granted(AdminMemberPermission.POS_MANAGE) || cyclos:granted(BrokerPermission.POS_MANAGE)}">
						<c:set var="searchColspan" value="2"/>
						<cyclos:cell className="label"><bean:message key="pos.new"/></cyclos:cell>
						<cyclos:cell>
							<input type="button" class="linkButton" linkURL="editPos" value="<bean:message key="global.submit"/>">
						</cyclos:cell>				
					</c:if>
					
					<cyclos:cell align="right" colspan="${searchColspan}">
	   					<input type="submit" class="button" value="<bean:message key="global.search"/>">
	   				</cyclos:cell>	   							
				</cyclos:layout>
			</td>
		</tr>
	</table>
	

<c:choose>
	<c:when test="${empty pos}">
		<div class="footerNote" helpPage="access_devices#search_pos_results"><bean:message key="pos.search.noResults"/></div>
	</c:when>
	<c:otherwise>
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		    	<td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		    	<td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
			    	<cyclos:help page="access_devices#search_pos_results" td="false" />
		        </td>	        
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable">
		                <tr>
		                	<th class="tdHeaderContents" width="35%"><bean:message key='pos.posId'/></th>
		                    <th class="tdHeaderContents" width="35%"><bean:message key="member.username"/></th>
		                    <th class="tdHeaderContents" width="15%"><bean:message key='pos.status'/></th>
		                    <th class="tdHeaderContents" width="15%">&nbsp;</th>
		                </tr>
		                <c:forEach var="ps" items="${pos}">
		                	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                		<td align="left" ><cyclos:truncate value="${ps.posId}"/></td>
			                    <td align="left" ><cyclos:truncate value="${ps.memberPos.member.username}"/></td>
			                    <c:choose>
			                    	<c:when test="${ps.status != 'ASSIGNED'}">
			                    		<td align="right"><bean:message key="pos.status.${ps.status}"/></td>
			                    	</c:when>
			                    	<c:otherwise>
			                    		<td align="right"><bean:message key="pos.status.${ps.memberPos.status}"/></td>
			                    	</c:otherwise>
			                    </c:choose>
			                    <td align="center">
			                    	<img posMainId="${ps.id}" class="edit posDetails" src="<c:url value="/pages/images/edit.gif" />" />
			                    	<c:if test="${cyclos:granted(AdminMemberPermission.POS_MANAGE) || cyclos:granted(BrokerPermission.POS_MANAGE)}">
			                    		<img posMainId="${ps.id}" class="remove" src="<c:url value="/pages/images/delete.gif" />" />
			                    	</c:if>
			                    </td>                   
		                	</tr>
		                </c:forEach>
		            </table>
		        </td>
		    </tr>
		</table>
		
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right"><cyclos:pagination items="${pos}"/></td>
			</tr>
		</table>		
	</c:otherwise>
</c:choose>
</ssl:form>
