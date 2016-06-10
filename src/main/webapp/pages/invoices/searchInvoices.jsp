<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/invoices/searchInvoices.js" />
<script>
	var memberId = "${member.id}";
	var lastFilter = "${searchInvoicesForm.query.direction}";
</script>

<c:choose>
	<c:when test="${myInvoices && isAdmin}">
		<c:set var="titleKey" value="invoice.title.system"/>
		<c:set var="helpPage" value="invoices#manage_invoices_by_admin"/>
	</c:when>
	<c:when test="${myInvoices && isMember}">
		<c:set var="titleKey" value="invoice.title.my"/>
		<c:set var="helpPage" value="invoices#manage_invoices_by_member"/>
	</c:when>
	<c:otherwise>
		<c:set var="titleKey" value="invoice.title.of"/>
		<c:set var="helpPage" value="invoices#manage_member_invoices_by_${isAdmin ? 'admin' : 'broker'}"/>
	</c:otherwise>
</c:choose>

<ssl:form method="POST" action="${formAction}">
<html:hidden property="advanced"/>
<html:hidden property="query(memberId)"/>
<c:set var="isIncoming" value="${searchInvoicesForm.query.direction == 'INCOMING'}"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="${titleKey}" arg0="${member.name}"/></td>
		<cyclos:help page="${helpPage}"/>
	</tr>
	<tr>
		<td colspan="2" align="left" class="tdContentTableForms">
			<cyclos:layout columns="4" className="defaultTable">
				<c:choose><c:when test="${searchInvoicesForm.advanced}">
				
					<cyclos:cell width="20%" className="label"><bean:message key="invoice.status"/></cyclos:cell>
					<cyclos:cell>
						<html:select styleClass="full" property="query(status)">
							<html:option value=""><bean:message key="global.search.all"/></html:option>
							<c:forEach var="stat" items="${status}">
								<html:option value="${stat}"><bean:message key="invoice.status.${stat}"/></html:option>
							</c:forEach>
						</html:select>
					</cyclos:cell>
				
					<cyclos:cell class="label"><bean:message key="invoice.search.direction"/></cyclos:cell>
					<cyclos:cell>
						<c:forEach var="direction" items="${directions}">
							<span><label>
								<html:radio style="vertical-align:middle" styleClass="radio direction" property="query(direction)" value="${direction}" />
								<span style="vertical-align:middle"><bean:message key="invoice.search.direction.${direction}"/></span>
							</label></span>
							&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</cyclos:cell>
					
					<cyclos:cell width="20%" className="label"><bean:message key="invoice.search.period.begin"/></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><html:text property="query(period).begin" styleClass="small date"/></cyclos:cell>
					<cyclos:cell width="20%" className="label"><bean:message key="invoice.search.period.end"/></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><html:text property="query(period).end" styleClass="small date"/></cyclos:cell>
					
					<cyclos:cell className="label"><bean:message key="member.username"/></cyclos:cell>
					<cyclos:cell>
						<html:hidden styleId="relatedMemberId" property="query(relatedMemberId)"/>
						<input id="memberUsername" class="full" value="${query.relatedMember.username}">
						<div id="membersByUsername" class="autoComplete"></div>
					</cyclos:cell>
					<cyclos:cell className="label"><bean:message key="member.memberName"/></cyclos:cell>
					<cyclos:cell>
						<input id="memberName" class="full" value="${query.relatedMember.name}">
						<div id="membersByName" class="autoComplete"></div>
					</cyclos:cell>
					
					<c:if test="${isAdmin}">
						<cyclos:cell className="label"><bean:message key="invoice.transferType"/></cyclos:cell>
						<cyclos:cell>
							<html:select styleClass="full" property="query(transferType)">
								<html:option value=""><bean:message key="global.search.all"/></html:option>
								<c:forEach var="transferType" items="${transferTypes}">
									<html:option value="${transferType.id}">${transferType.name}</html:option>
								</c:forEach>
							</html:select>
						</cyclos:cell>
					</c:if>
					<cyclos:cell className="label"><bean:message key="invoice.description"/></cyclos:cell>
					<cyclos:cell><html:text style="width:100%" property="query(description)"/></cyclos:cell>
					<c:if test="${not empty localSettings.transactionNumber}">
						<cyclos:cell className="label"><bean:message key="transfer.transactionNumber"/></cyclos:cell>
						<cyclos:cell><html:text style="width:100%" property="query(transactionNumber)"/></cyclos:cell>
					</c:if>
					<cyclos:rowBreak/>
					
					<c:if test="${not empty operators}">
						<tr>
							<td class="label" width="20%"><bean:message key="member.operator"/></td>
							<td colspan="3">
								<html:select property="query(by)">
									<html:option value=""><bean:message key="global.search.all"/></html:option>
									<c:forEach var="operator" items="${operators}">
										<html:option value="${operator.id}">${operator.name} (${operator.username})</html:option>
									</c:forEach>
								</html:select>
							</td>
						</tr>
					</c:if>
					
					<cyclos:rowBreak/>
					
					<cyclos:cell colspan="2">
   						<c:if test="${isMember}">
   							<input id="modeButton" type="button" class="button" value="<bean:message key="global.search.NORMAL"/>">
   						</c:if>
	   				</cyclos:cell>
	   				<cyclos:cell align="right" colspan="2">
	   					<input type="submit" class="button" value="<bean:message key="global.search"/>">
	   				</cyclos:cell>
	   				
				</c:when><c:otherwise>
				
					<cyclos:cell>
	   					<input id="modeButton" type="button" class="button" value="<bean:message key="global.search.ADVANCED"/>">
	   				</cyclos:cell>
	   				<cyclos:cell colspan="3" align="right" valign="middle">
						<span class="label" style="vertical-align:middle"><bean:message key="invoice.search.direction"/></span>
						<c:forEach var="direction" items="${directions}">
							<label>
								<html:radio style="vertical-align:middle" styleClass="radio direction" property="query(direction)" value="${direction}" />
								<span style="vertical-align:middle"><bean:message key="invoice.search.direction.${direction}"/></span>
							</label>
							&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</cyclos:cell>
					
				</c:otherwise></c:choose>
			</cyclos:layout>
		</td>
	</tr>
</table>
</ssl:form>


<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="global.searchResults"/></td>
        <cyclos:help page="invoices#invoices_result_by_${isAdmin ? 'admin' : 'member'}"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableLists">
            <table class="defaultTable">
                <tr>
                	<th class="tdHeaderContents" width="15%"><bean:message key='invoice.date'/></th>
                    <th class="tdHeaderContents" width="25%"><bean:message key="invoice.${isIncoming ? 'from' : 'to'}"/></th>
                    <th class="tdHeaderContents" width="40%"><bean:message key='invoice.description'/></th>
                    <th class="tdHeaderContents" width="15%"><bean:message key='invoice.amount'/></th>
                    <th class="tdHeaderContents" width="5%">&nbsp;</th>
                </tr>
                <c:set var="memberProperty" value="${localSettings.memberResultDisplay.property}"/>
                <c:forEach var="entry" items="${invoices}">
                	<c:set var="invoice" value="${entry.invoice}" />
                	<tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
	                    <td align="center"><cyclos:format date="${invoice.date}"/></td>
	                    <td align="center">
	                    	<c:choose><c:when test="${not empty entry.relatedMember}">
	                    		<cyclos:profile elementId="${entry.relatedMember.id}"/>
	                    	</c:when><c:otherwise>
	                    		${entry.relatedName}
	                    	</c:otherwise></c:choose>
	                    </td>
	                    <td align="left"><cyclos:truncate value="${invoice.description}"/></td>
	                    <td align="right"><cyclos:format number="${invoice.amount}"/></td>
	                    <td align="center"><img invoiceId="${invoice.id}" src="<c:url value="/pages/images/view.gif"/>" class="details view"/></td>
                	</tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
	<tr>
		<c:if test="${!myInvoices}">
			<td><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
		</c:if>
		<td align="right"><cyclos:pagination items="${invoices}"/></td>
	</tr>
</table>