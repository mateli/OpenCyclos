<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/memberBulkActions.js" />
<script>
	var confirmChangeGroup = "<cyclos:escapeJS><bean:message key="member.bulkActions.changeGroup.confirmation"/></cyclos:escapeJS>"
	var confirmChangeBroker = "<cyclos:escapeJS><bean:message key="member.bulkActions.changeBroker.confirmation"/></cyclos:escapeJS>"
	var confirmGenerateCard = "<cyclos:escapeJS><bean:message key="member.bulkActions.generateCard.confirmation"/></cyclos:escapeJS>"
	var confirmChangeChannels = "<cyclos:escapeJS><bean:message key="member.bulkActions.changeChannels.confirmation"/></cyclos:escapeJS>"
</script>

<ssl:form method="post" action="${formAction}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="member.title.bulkActions.filter"/></td>
        <cyclos:help page="user_management#bulk_actions_filter"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
          		<c:if test="${not empty groupFilters}">
          			<tr>
            			<td class="label"><bean:message key="member.groupFilter"/></td>
            			<td colspan="2">
            				<cyclos:multiDropDown name="query(groupFilters)" emptyLabelKey="member.search.allGroupFilters" onchange="updateGroups()">
            					<c:forEach var="groupFilter" items="${groupFilters}">
            						<cyclos:option value="${groupFilter.id}" text="${groupFilter.name}" />
            					</c:forEach>
              				</cyclos:multiDropDown>
              			</td>
          			</tr>
          		</c:if>
         		<tr>
           			<td class="label"><bean:message key="member.group"/></td>
           			<td colspan="2">
           				<cyclos:multiDropDown name="query(groups)" varName="groupsSelect" emptyLabelKey="member.search.allGroups">
           					<c:forEach var="group" items="${groups}">
           						<cyclos:option value="${group.id}" text="${group.name}" />
           					</c:forEach>
             				</cyclos:multiDropDown>
             			</td>
         			</tr>
				<tr>
					<td width="24%" class="label"><bean:message key="member.brokerUsername"/></td>
					<td>
						<html:hidden property="query(broker)" styleId="brokerId"/>
						<input id="brokerUsername" class="large" value="${query.broker.username}">
						<div id="brokersByUsername" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="member.brokerName"/></td>
					<td>
						<input id="brokerName" class="large" value="${query.broker.name}">
						<div id="brokersByName" class="autoComplete"></div>
					</td>
				</tr>
			    <c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value.value}"/>
		            <tr>
		                <td class="label">${field.name}</td>
		   				<td colspan="2">
		   					<cyclos:customField field="${field}" value="${value}" search="true" valueName="query(customValues).value" fieldName="query(customValues).field"/>
		   				</td>
					</tr>
			    </c:forEach>
          		<tr>
					<td colspan="3" align="right">
						<input type="button" class="button" id="previewButton" value="<bean:message key="global.preview"/>">
					</td>
				</tr>
        	</table>
        </td>
    </tr>
</table>

<c:if test="${queryExecuted}">
	<c:choose><c:when test="${empty elements}">
		<div class="footerNote"><bean:message key="member.search.noResults"/></div>
	</c:when><c:otherwise>

		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		        <td class="tdHelpIcon">&nbsp;</td>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable" cellspacing="0" cellpadding="0">
		                <tr>
		                    <td class="tdHeaderContents" width="30%"><bean:message key="member.username"/></td>
							<td class="tdHeaderContents" align="center"><bean:message key="member.name"/></td>
		                </tr>
						<c:forEach var="member" items="${elements}">
			                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
			                    <td><cyclos:profile elementId="${member.id}" pattern="username"/></td>
			                    <td><cyclos:profile elementId="${member.id}" pattern="name"/></td>
			                </tr>
			            </c:forEach>
		            </table>
		        </td>
		    </tr>
		</table>
		
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right"><cyclos:pagination items="${elements}"/></td>
			</tr>
		</table>		
	</c:otherwise></c:choose>
	
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="member.title.bulkActions.action"/></td>
        <cyclos:help page="user_management#bulk_action"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
        	<div align="center">
        		<span class="label"><bean:message key="member.bulkActions.choose"/></span>
        		<select id="actionSelect">
        			<option></option>
        			<c:if test="${cyclos:granted(AdminMemberPermission.BULK_ACTIONS_CHANGE_GROUP)}">
	        			<option value="changeGroup"><bean:message key="member.bulkActions.changeGroup"/></option>
					</c:if>
        			<c:if test="${cyclos:granted(AdminMemberPermission.BULK_ACTIONS_CHANGE_BROKER)}">
	        			<option value="changeBroker"><bean:message key="member.bulkActions.changeBroker"/></option>
					</c:if>
					<c:if test="${cyclos:granted(AdminMemberPermission.BULK_ACTIONS_GENERATE_CARD)}">
	        			<option value="generateCard"><bean:message key="member.bulkActions.generateCard"/></option>
					</c:if>
					<c:if test="${cyclos:granted(AdminMemberPermission.BULK_ACTIONS_CHANGE_CHANNELS)}">
		        		<option value="changeChannels"><bean:message key="member.bulkActions.changeChannels"/></option>
					</c:if>					
        		</select>
        	</div>
        	<div id="changeGroupDiv" style="display:none">
        		<hr>
	            <table class="defaultTable">
					<tr>
	                	<td class="label"><bean:message key="changeGroup.new"/></td>
	                	<td>
	                		<html:select property="changeGroup.newGroup" styleId="newGroupSelect">
	               				<html:option value=""></html:option>
	                			<c:forEach var="current" items="${possibleNewGroups}">
		                			<html:option value="${current.id}"><cyclos:escapeHTML>${current.name}</cyclos:escapeHTML></html:option>
	                			</c:forEach>
	                		</html:select>
	                	</td>
	                </tr>
	                <tr>
	                	<td valign="top" class="label"><bean:message key="remark.comments"/></td>
	                	<td><html:textarea styleId="changeGroupComments" styleClass="full" rows="5" property="changeGroup.comments"/></td>
	                </tr>
	          		<tr>
						<td colspan="2" align="right">
							<input type="button" class="button" id="changeGroupButton" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
	        	</table>
	        </div>
        	<div id="changeBrokerDiv" style="display:none">
        		<hr>
	            <table class="defaultTable">
					<tr>
						<td width="24%" class="label"><bean:message key="changeBroker.new"/></td>
						<td>
							<html:hidden property="changeBroker.newBroker" styleId="newBrokerId"/>
							<input id="newBrokerUsername" class="large" size="20">
							<div id="newBrokersByUsername" class="autoComplete"></div>
						</td>
					</tr>
					<tr>
						<td class="label"><bean:message key="member.brokerName"/></td>
						<td>
							<input id="newBrokerName" class="large" size="40">
							<div id="newBrokersByName" class="autoComplete"></div>
						</td>
					</tr>
					<tr>
						<td class="label" width="25%"><bean:message key="changeBroker.suspendCommission"/></td>
						<td><html:checkbox property="changeBroker.suspendCommission" value="true"/></td>						
					</tr>
				   	<tr>
						<td class="label" valign="top"><bean:message key="remark.comments"/></td>
					   	<td><html:textarea styleId="changeBrokerComments" styleClass="full" rows="5" property="changeBroker.comments"/></td>
				   	</tr>
	          		<tr>
						<td colspan="2" align="right">
							<input type="button" class="button" id="changeBrokerButton" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
	        	</table>
        	</div>
        	<div id="generateCardDiv" style="display:none">
        		<hr>
	            <table class="defaultTable">
	            	<tr>
						<td class="label" width="50%"><bean:message key="generateCard.generateForMemberWithPendingCard"/></td>
						<td><html:checkbox property="generateCard.generateForPending" value="true"/></td>						
					</tr>
					<tr>
						<td class="label" width="50%"><bean:message key="generateCard.generateForMemberWithActiveCard"/></td>
						<td><html:checkbox property="generateCard.generateForActive" value="true"/></td>						
					</tr>
				   	<tr>
						<td colspan="2" align="right">
							<input type="button" class="button" id="generateCardButton" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
				</table>
        	</div>
        	<div id="changeChannelsDiv" style="display:none">
        		<hr>
	            <table class="defaultTable">
					<tr>	                	
	                	<td class="label"><bean:message key="changeChannels.enableChannels"/></td>
	                	<td>	                	
	                    	<cyclos:multiDropDown varName="enableChannels" name="changeChannels.enableIds">
		                    	<c:forEach var="channel" items="${channels}">
	    	                		<cyclos:option value="${channel.id}" text="${channel.displayName}"/>
	        	            	</c:forEach>
	            	        </cyclos:multiDropDown>	                		                    
	                	</td>
	                </tr>	                
					<tr>
	                	<td class="label"><bean:message key="changeChannels.disableChannels"/></td>
	                	<td>
	                    	<cyclos:multiDropDown varName="disableChannels" name="changeChannels.disableIds">
		                    	<c:forEach var="channel" items="${channels}">
		                    		<cyclos:option value="${channel.id}" text="${channel.displayName}"/>
		                    	</c:forEach>
		                    </cyclos:multiDropDown>
	                	</td>
					</tr>
	          		<tr>
						<td colspan="2" align="right">
							<input type="button" class="button" id="changeChannelsButton" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
	        	</table>
	        </div>        	
        </td>
    </tr>
</table>

</ssl:form>
