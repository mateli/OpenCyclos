<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/searchMembers.js" />

<ssl:form method="post" action="${formAction}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="member.title.search"/></td>
        <cyclos:help page="${isAdmin ? 'user_management#search_member_by_admin' : 'user_management#search_member_by_member'}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
          		<tr>
            		<td class="label" width="25%"><bean:message key="element.search.keywords"/></td>
            		<td colspan="2"><html:text property="query(keywords)" styleClass="InputBoxEnabled large"/></td>
          		</tr>
          		<c:if test="${not empty groupFilters}">
          			<tr>
            			<td class="label"><bean:message key='${isAdmin ? "permission.systemGroupFilters" : "groupFilter.groups"}'/></td>
            			<td colspan="2">
            				<cyclos:multiDropDown name="query(groupFilters)" emptyLabelKey="member.search.allGroupFilters" onchange="updateGroups()">
            					<c:forEach var="groupFilter" items="${groupFilters}">
            						<cyclos:option value="${groupFilter.id}" text="${groupFilter.name}" />
            					</c:forEach>
              				</cyclos:multiDropDown>
              			</td>
          			</tr>
          		</c:if>
          		
				<c:if test="${isAdmin}">
          			<tr>
            			<td class="label"><bean:message key="member.group"/></td>
            			<td colspan="2">
            				<cyclos:multiDropDown name="query(groups)" varName="groupsSelect" emptyLabelKey="member.search.allGroups">
            					<c:forEach var="group" items="${groups}">
            						<cyclos:option value="${group.id}" text="${group.name}" />
            					</c:forEach>
              				</cyclos:multiDropDown>
              				<input type="hidden" name="query(groups)" value=""/>
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
						<td class="label"><bean:message key="member.broker"/></td>
						<td>
							<input id="brokerName" class="large" value="${query.broker.name}">
							<div id="brokersByName" class="autoComplete"></div>
						</td>
					</tr>
          			<tr>
            			<td class="label">
            				<bean:message key="member.search.date"/>
							<bean:message key="global.range.from"/>
            			</td>
            			<td colspan="2">
            				<html:text property="query(activationPeriod).begin" styleClass="InputBoxEnabled date small"/>
            				&nbsp;
            				<span class="label"><bean:message key="global.range.to"/></span>
            				<html:text property="query(activationPeriod).end" styleClass="InputBoxEnabled date small"/>
            			</td>
          			</tr>
          		</c:if>
			    <c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
		            <tr>
		                <td class="label">${field.name}</td>
		   				<td colspan="2">
		   					<cyclos:customField field="${field}" value="${value}" search="true" valueName="query(customValues).value" fieldName="query(customValues).field"/>
		   				</td>
					</tr>
			    </c:forEach>
          		<tr>
          			<c:set var="colSpan" value="${empty possibleNewGroups ? 3 : 1}" />
         			<c:if test="${not empty possibleNewGroups}">
						<td class="label"><bean:message key="member.action.create"/></td>
						<td>
							<select id="newMemberGroup">
								<option value=""><bean:message key="member.action.create.selectGroup" /></option>
								<c:forEach var="group" items="${possibleNewGroups}">
									<option value="${group.id}">${group.name}</option>
								</c:forEach>
							</select>
						</td>
					</c:if>
					<td align="right" colspan="${colSpan}">
						<input type="submit" class="button" value="<bean:message key="global.search"/>">
					</td>
				</tr>
        	</table>
        </td>
    </tr>
</table>
</ssl:form>

<c:if test="${queryExecuted}">
	<c:set var="helpPage" value="${isAdmin ? 'user_management#admin_search_member_result' : 'user_management#search_member_result'}"/>
	<c:choose><c:when test="${empty elements}">
		<div class="footerNote" helpPage="${helpPage}"><bean:message key="member.search.noResults"/></div>
	</c:when><c:otherwise>
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
		        	<c:if test="${isAdmin}">
		        		<img class="exportCSV" src="<c:url value="/pages/images/save.gif"/>" border="0">
		        		<img class="print" src="<c:url value="/pages/images/print.gif"/>" border="0">
		        	</c:if>
			        <cyclos:help page="${helpPage}" td="false"/>
		        </td>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable" cellspacing="0" cellpadding="0">
		                <tr>
							<td class="tdHeaderContents" width="${localSettings.maxThumbnailWidth}">&nbsp;</td>
		                    <td class="tdHeaderContents" width="30%"><bean:message key="member.username"/></td>
							<td class="tdHeaderContents" align="center"><bean:message key="member.name"/></td>
		                </tr>
						<c:forEach var="member" items="${elements}">
			                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
								<td valign="middle" align="center" width="60" style="margin:0px;padding:0px"><cyclos:images images="${member.images}" imageOnly="true" /></td>
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
<c:if test="${param.fromQuickAccess}">
	<table class="defaultTableContentHidden"><tr><td>
	<input type="button" id="backButton" class="button" value="<bean:message key="global.back"/>">
	</td></tr></table>
</c:if>