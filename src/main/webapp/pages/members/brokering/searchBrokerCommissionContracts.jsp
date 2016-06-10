<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/members/brokering/searchBrokerCommissionContracts.js" />

<ssl:form method="POST" action="${formAction}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="brokerCommissionContract.title.list"/></td>
		<cyclos:help page="brokering#commission_contracts_search_filters"/>
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<table class="defaultTable">
				<tr>
					<td class="label"><bean:message key="member.username"/></td>
					<td>
						<html:hidden styleId="memberId" property="query(member)"/>
						<input id="memberUsername" class="full" value="${query.member.username}">
						<div id="membersByUsername" class="autoComplete"></div>
					</td>
					<td class="label"><bean:message key="member.memberName"/></td>
					<td>
						<input id="memberName" class="full" value="${query.member.name}">
						<div id="membersByName" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="brokerCommissionContract.brokerCommission"/></td>
					<td colspan="3">
						<html:select styleClass="large" property="query(brokerCommission)">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach var="brokerCommission" items="${brokerCommissions}">
								<html:option value="${brokerCommission.id}">${brokerCommission.name}</html:option>
							</c:forEach>
						</html:select>
					</td>	
				</tr>
				<tr>
					<td class="label"><bean:message key="brokerCommissionContract.status"/></td>
					<td colspan="3">
						<html:select styleClass="large" property="query(status)">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach var="status" items="${statusList}">
								<html:option value="${status}"><bean:message key="brokerCommissionContract.status.${status}"/></html:option>
							</c:forEach>
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="brokerCommissionContract.startDate.from"/></td>
					<td nowrap="nowrap"><html:text property="query(startPeriod).begin" styleClass="small date"/></td>
					<td class="label"><bean:message key="brokerCommissionContract.startDate.to"/></td>
					<td nowrap="nowrap"><html:text property="query(startPeriod).end" styleClass="small date"/></td>
				</tr>
				<tr>
					<td class="label"><bean:message key="brokerCommissionContract.endDate.from"/></td>
					<td nowrap="nowrap"><html:text property="query(endPeriod).begin" styleClass="small date"/></td>
					<td class="label"><bean:message key="brokerCommissionContract.endDate.to"/></td>
					<td nowrap="nowrap"><html:text property="query(endPeriod).end" styleClass="small date"/></td>
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


<c:choose>
	<c:when test="${empty brokerCommissionContracts}">
		<div class="footerNote" helpPage="brokering#commission_contracts_search_results"><bean:message key="brokerCommissionContract.search.noResults"/></div>
	</c:when><c:otherwise>
		<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		    <tr>
		        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
		        <cyclos:help page="brokering#commission_contracts_search_results"/>
		    </tr>
		    <tr>
		        <td colspan="2" align="left" class="tdContentTableLists">
		            <table class="defaultTable">
		                <tr>
		                	<th class="tdHeaderContents" width="30%"><bean:message key='brokerCommissionContract.member'/></th>
		                    <th class="tdHeaderContents" width="15%"><bean:message key="brokerCommissionContract.startDate"/></th>
		                    <th class="tdHeaderContents" width="15%"><bean:message key='brokerCommissionContract.endDate'/></th>
		                    <th class="tdHeaderContents" width="15%"><bean:message key='brokerCommissionContract.amount'/></th>
		                    <th class="tdHeaderContents" width="15%"><bean:message key='brokerCommissionContract.status'/></th>
		                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
		                </tr>
		                <c:forEach var="brokerCommissionContract" items="${brokerCommissionContracts}">
		                	<c:set var="editable" value="${cyclos:name(brokerCommissionContract.status) == 'PENDING'}"/>
		                	<c:set var="deletable" value="${cyclos:name(brokerCommissionContract.status) == 'ACTIVE' or cyclos:name(brokerCommissionContract.status) == 'PENDING'}"/>
		                	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                		<c:set var="member" value="${brokerCommissionContract.brokering.brokered}"/>
			                    <td align="center"><cyclos:profile elementId="${member.id}"/></td>
			                    <td align="center"><cyclos:format rawDate="${brokerCommissionContract.period.begin}"/></td>
			                    <td align="center"><cyclos:format rawDate="${brokerCommissionContract.period.end}"/></td>
			                    <td align="center"><cyclos:format amount="${brokerCommissionContract.amount}"/></td>
			                    <td align="center"><bean:message key='brokerCommissionContract.status.${brokerCommissionContract.status}'/></td>
			                    <td align="right">
			                    	<img brokerCommissionContractId="${brokerCommissionContract.id}" src="<c:url value="/pages/images/${editable ? 'edit' : 'view'}.gif"/>" class="details ${editable ? 'edit' : 'view'}"/>
			                    	<c:if test="${deletable}">
			                    		<img brokerCommissionContractId="${brokerCommissionContract.id}" src="<c:url value="/pages/images/delete.gif"/>" class="remove"/>
			                    	</c:if>
			                    </td>
		                	</tr>
		                </c:forEach>
		            </table>
		        </td>
		    </tr>
		</table>
	</c:otherwise>
</c:choose>