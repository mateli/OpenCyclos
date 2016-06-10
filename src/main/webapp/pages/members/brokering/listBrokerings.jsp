<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/brokering/listBrokerings.js" />
<script>
	var confirmationWithoutBroker = "<cyclos:escapeJS><bean:message key="brokering.change.confirmation" arg0="#newBroker#" arg1="#member#" arg2="#oldBroker#" /></cyclos:escapeJS>"
	var confirmationWithBroker = "<cyclos:escapeJS><bean:message key="brokering.change.confirmation.withBroker" arg0="#newBroker#" arg1="#member#" arg2="#oldBroker#" /></cyclos:escapeJS>"
</script>
<ssl:form styleId="searchBrokeringsForm" method="post" action="${formAction}">
<html:hidden property="memberId" value="${broker.id}"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<c:choose><c:when test="${myBrokerings}">
	        	<bean:message key="brokering.title.list.my"/>
	        </c:when><c:otherwise>
	        	<bean:message key="brokering.title.list.of" arg0="${broker.name}"/>
	        </c:otherwise></c:choose>
        </td>
        <cyclos:help page="brokering#broker_search_member"/>
    </tr>
    <tr>
        <td colspan="2" align="right" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
					<td class="label" nowrap="nowrap"><bean:message key="member.username"/></td>
					<td style="text-align: left">
						<html:text property="query(username)" styleClass="medium"/>
					</td>
					<td class="label"><bean:message key="member.memberName"/></td>
					<td style="text-align: left">
						<html:text property="query(name)" styleClass="medium"/>
					</td>
				</tr>
				<tr>
           			<td class="label"><bean:message key="member.group"/></td>
           			<td colspan="3">
          				<cyclos:multiDropDown name="query(groups)" varName="groupsSelect" emptyLabelKey="member.search.allGroups">
          					<c:forEach var="group" items="${groups}">
          						<c:set var="groupLabel" value="${group.initialGroupShow != null ? group.initialGroupShow : group.name }"/>
          						<cyclos:option value="${group.id}" text="${groupLabel}" />
          					</c:forEach>
            			</cyclos:multiDropDown>
            		</td>
        		</tr>	
          		<tr>
            		<td class="label"><bean:message key="brokering.list.status"/>&nbsp;</td>
            		<td colspan="3" align="left">
	            		<html:select styleId="statusSelect" property="query(status)" styleClass="InputBoxEnabled">
	            			<c:forEach var="stat" items="${status}">
	            				<html:option value="${stat}"><bean:message key="brokering.status.${stat}"/></html:option>
	            			</c:forEach>
	            		</html:select>
            		</td>
          		</tr>
          		<tr>
					<td colspan="4" align="right">
						<input type="submit" class="button" value="<bean:message key="global.search"/>">
					</td>
				</tr>
          		
        	</table>
        </td>
    </tr>
</table>
</ssl:form>

<c:if test="${isAdmin}">
	<table class="defaultTableContentHidden">
		<tr>
			<td><input type="button" memberId="${broker.id}" id="backButton" class="button" value="<bean:message key="global.back"/>"></td>
		</tr>
	</table>
	
</c:if>

<c:set var="helpPage" value="${isAdmin ? 'brokering#admin_brokering_list' : isBroker ? 'brokering#broker_search_member_result' : 'user_management#search_member_result'}"/>
<c:choose><c:when test="${empty brokerings}">
	<div class="footerNote" helpPage="${helpPage}"><bean:message key="brokering.list.noResults"/></div>
</c:when><c:otherwise>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
	        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
        		<img class="exportCSV" src="<c:url value="/pages/images/save.gif"/>" border="0">
        		<img class="print" src="<c:url value="/pages/images/print.gif"/>" border="0">
		        <cyclos:help page="${helpPage}" td="false"/>
	        </td>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable" cellspacing="0" cellpadding="0">
	                <tr>
	                    <td class="tdHeaderContents" width="30%"><bean:message key="member.username"/></td>
						<td class="tdHeaderContents" width="65%" align="center"><bean:message key="member.memberName"/></td>
						<c:if test="${canChangeBroker}">
							<td class="tdHeaderContents" width="8%">&nbsp;</td>
						</c:if>
	                </tr>
					<c:forEach var="brokering" items="${brokerings}">
						<c:set var="member" value="${brokering.brokered}"/>
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="left"><cyclos:profile elementId="${member.id}" pattern="username"/></td>
		                    <td align="left"><cyclos:profile elementId="${member.id}" pattern="name"/></td>
			                <c:if test="${canChangeBroker}">
		                    	<td align="center" nowrap="nowrap">
			                    	<script>
			                    		brokered["${member.id}"] = {username:'<cyclos:escapeJS>${member.username}</cyclos:escapeJS>', name:'<cyclos:escapeJS>${member.name}</cyclos:escapeJS>'};
			                    	</script>
			                    	<img class="remove" memberId="${member.id}" src="<c:url value="/pages/images/delete.gif"/>">
			                    </td>
		                    </c:if>
		                </tr>
		            </c:forEach>
	            </table>
	        </td>
	    </tr>
	</table>
</c:otherwise></c:choose>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<c:if test="${!empty brokerings}">
			<td align="right"><cyclos:pagination items="${brokerings}"/></td>
		</c:if>
	</tr>
</table>

<c:if test="${canChangeBroker}">
	<div id="removeMemberDiv" style="display:none">
		<ssl:form action="/admin/removeBrokeredMember" styleId="removeMemberForm" method="post">
			<html:hidden property="memberId"/>
			<html:hidden property="brokerId" value="${broker.id}"/>
			<table class="defaultTableContent" cellspacing="0" cellpadding="0">
			    <tr>
			        <td class="tdHeaderTable"><bean:message key="brokering.title.removeMember"/></td>
			        <cyclos:help page="brokering#remove_member_to_broker"/>
			    </tr>
			    <tr>
			        <td colspan="2" align="left" class="tdContentTableForms">
			            <table class="defaultTable">
			                <tr>
			                    <td class="label" width="30%"><bean:message key="member.username"/></td>
							   	<td><input class="InputBoxDisabled large" name="username" readonly="readonly"/></td>
						   	</tr>
			                <tr>
			                    <td class="label"><bean:message key="member.memberName"/></td>
							   	<td><input class="InputBoxDisabled large" name="name" readonly="readonly"/></td>
						   	</tr>
			                <tr>
			                    <td class="label"><bean:message key="remark.comments"/></td>
							   	<td><html:textarea styleId="comments" styleClass="full" rows="5" property="comments"/></td>
						   	</tr>
							<tr>
								<td colspan="2" align="right">
									<input type="button" id="cancelRemoveMemberButton" class="button" value="<bean:message key="global.cancel"/>"/>
									&nbsp;
									<input type="submit" class="button" value="<bean:message key="global.submit"/>"/>
								</td>
							</tr>
			            </table>
			        </td>
			    </tr>
			</table>
		</ssl:form>
		
	</div>
	<ssl:form action="/admin/addMemberToBroker" styleId="addMemberForm" method="post">
		<html:hidden property="newBrokerId" value="${broker.id}" />
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="brokering.title.addMember"/></td>
		        <cyclos:help page="brokering#add_member_to_broker"/>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableForms">
		            <table class="defaultTable">
		                <tr>
		                    <td class="label" width="30%"><bean:message key="member.username"/></td>
							<td>
								<html:hidden property="memberId" styleId="changeMemberId"/>
								<input id="memberUsername" class="large">
								<div id="membersByUsername" class="autoComplete"></div>
							</td>
						</tr>
						<tr>
		                    <td class="label"><bean:message key="member.memberName"/></td>
							<td>
								<input id="memberName" class="large">
								<div id="membersByName" class="autoComplete"></div>
							</td>
		                </tr>
						<tr>
							<td class="label" width="25%"><bean:message key="changeBroker.suspendCommission"/></td>
							<td><html:checkbox property="suspendCommission" value="true"/></td>						
						</tr>
					   	<tr>
							<td class="label" valign="top"><bean:message key="remark.comments"/></td>
						   	<td><html:textarea styleId="comments" styleClass="full" rows="5" property="comments"/></td>
					   	</tr>
						<tr>
							<td colspan="2" align="right"><input type="submit" class="button" value="<bean:message key="global.submit"/>"/></td>
						</tr>
		            </table>
		        </td>
		    </tr>
		</table>
	</ssl:form>
</c:if>