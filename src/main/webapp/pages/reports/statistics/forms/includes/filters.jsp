<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<br id="filterTableBr" class="small" style="{display:none;}" >
<table class="defaultTableContent" cellspacing="0" cellpadding="0" style="display:none" id="filterTable">
	<tr>
		<td class="tdHeaderTable">
			<bean:message key="reports.stats.filters"/>
		</td>
		<cyclos:help page="statistics#filters"/>
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<table class="defaultTable">

				<tr id="trSystemAccountFilter" style="display:none">
					<td class="label">
						<bean:message key="reports.stats.systemAccountFilter"/>&nbsp;
					</td>
					<td>
						<html:select styleId="systemAccountSelect" property="query(systemAccountFilter)">
							<c:forEach var="sysAccs" items="${systemAccounts}">
								<html:option value="${sysAccs.id}">${sysAccs.name}</html:option>
							</c:forEach>
						</html:select>
					</td>
				</tr>
				
				<div id=trGroupFilter style="display:none">
					<c:if test="${not empty groupFilters}">
						<tr>
							<td class="label">
								<bean:message key="reports.stats.groupOfGroups"/>&nbsp;
							</td>
							<td>
								<cyclos:multiDropDown 
										varName="groupOfGroupsMultiDropDown"
										name="query(groupFilters)" 
										emptyLabelKey="global.nothingSelected"
										onchange="groupOfGroupsChanged()">
									<c:forEach var="filter" items="${groupFilters}">
										<cyclos:option value="${filter.id}" text="${filter.name}" />
									</c:forEach>
								</cyclos:multiDropDown>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="label">
							<bean:message key="reports.stats.groupfilter"/>&nbsp;
						</td>
						<td >
							<cyclos:multiDropDown 
									varName="groupsMultiDropDown" 
									name="query(groups)" 
									emptyLabelKey="member.search.allGroups"
									onchange="memberGroupsChanged()">
								<c:forEach var="group" items="${groups}">
									<cyclos:option value="${group.id}" text="${group.name}" />
								</c:forEach>
							</cyclos:multiDropDown>
						</td>
					</tr>
				</div>

				<c:if test="${not empty accountFeeList}">
					<tr id="trAccountFeeFilters" style="display:none">
						<td class="label">
							<bean:message key="reports.stats.accountFeeFilters"/>&nbsp;
						</td>
						<td>
							<cyclos:multiDropDown
									varName="accountFeesMultiDropDown" 
									name="query(accountFees)" 
									emptyLabelKey="reports.stats.general.selectAny" 
									minWidth="120"
									size="10" >
								<c:forEach var="filter" items="${accountFeeList}">
									<cyclos:option value="${filter.id}" text="${filter.name}" />
								</c:forEach>
							</cyclos:multiDropDown>
						</td>
					</tr>
				</c:if>

				<c:if test="${not empty transactionFeeList}">
					<tr id="trTransactionFeeFilters" style="display:none">
						<td class="label">
							<bean:message key="reports.stats.transactionFeeFilters"/>&nbsp;
						</td>
						<td>
							<cyclos:multiDropDown
									varName="transactionFeesMultiDropDown" 
									name="query(transactionFees)" 
									emptyLabelKey="reports.stats.general.selectAny" 
									minWidth="120"
									size="10" >
								<c:forEach var="filter" items="${transactionFeeList}">
									<cyclos:option value="${filter.id}" text="${filter.name}" />
								</c:forEach>
							</cyclos:multiDropDown>
						</td>
					</tr>
				</c:if>

				<tr id="trPaymentFilter" style="display:none">
					<td class="label">
						<bean:message key="reports.stats.paymentfilter"/>&nbsp;
					</td>
					<td>
						<html:select styleId="filterSelect" property="query(paymentFilter)">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach var="filter" items="${paymentFilterList}">
								<html:option value="${filter.id}">${filter.name}</html:option>
							</c:forEach>
						</html:select>
					</td>
				</tr>

				<tr id="trMultiPaymentFilter" style="display:none">
					<td class="label">
						<bean:message key="reports.stats.paymentfilters"/>&nbsp;
					</td>
					<td>
						<cyclos:multiDropDown
								varName="paymentFiltersMultiDropDown" 
								name="query(paymentFilters)" 
								emptyLabelKey="reports.stats.general.selectMulti" 
								minWidth="120"
								size="10" >
							<c:forEach var="filter" items="${paymentFilterList}">
								<cyclos:option value="${filter.id}" text="${filter.name}" />
							</c:forEach>
						</cyclos:multiDropDown>
					</td>
				</tr>
				
			</table>
		</td>
	</tr>
</table>