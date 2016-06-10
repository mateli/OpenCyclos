<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<c:choose>
	<c:when test="${toMe}">
		<c:set var="helpPage" value="invoices#incoming_invoice_detail_by_${isAdmin ? 'admin' : 'member'}"/>
	</c:when>
	<c:when test="${fromMe}">
		<c:set var="helpPage" value="invoices#outgoing_invoice_detail_by_${isAdmin ? 'admin' : 'member'}"/>
	</c:when>
	<c:otherwise>
		<c:set var="helpPage" value="invoices#invoice_details"/>
	</c:otherwise>
</c:choose>
<c:set var="toMember" value="${invoice.toMember}"/>
<c:set var="fromMember" value="${invoice.fromMember}"/>
<c:set var="amount"><cyclos:format number="${invoice.amount}" unitsPattern="${unitsPattern}" /></c:set>

<cyclos:script src="/pages/invoices/invoiceDetails.js" />
<script>
	var memberId = "${searchInvoicesForm.memberId}";
	var invoiceId = "${invoice.id}";
	<c:if test="${canDeny}">
		var denyMessage = "<cyclos:escapeJS><bean:message key="invoice.denyConfirmationMessage" arg0="${fromMember.name}" arg1="${amount}"/></cyclos:escapeJS>";
	</c:if>
	<c:if test="${canCancel}">
		var cancelMessage = "<cyclos:escapeJS><bean:message key="invoice.cancelConfirmationMessage" arg0="${toMember.name}" arg1="${amount}"/></cyclos:escapeJS>";
	</c:if>
	var accountFeeLogId = "${invoiceDetailsForm.accountFeeLogId}";
</script>

<c:if test="${canAccept && fn:length(transferTypes) == 1}">
	<c:set var="transferType" value="${empty invoice.transferType ? transferTypes[0] : invoice.transferType}"/>
	<input type="hidden" id="transferType" value="${transferType.id}"/>
</c:if>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="invoice.title.details"/></td>
        <td class="tdHelpIcon" align="right" width="13%" valign="middle" nowrap="nowrap">
        	<img class="print" invoiceId="${invoice.id}" src="<c:url value="/pages/images/print.gif" />">
        	<cyclos:help page="${helpPage}" td="false"/>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
				<tr>
					<td width="25%" class="headerLabel"><bean:message key="invoice.from"/></td>
					<td class="headerField">
						<c:choose><c:when test="${empty fromMember}">
							${localSettings.applicationUsername}
						</c:when><c:otherwise>
							<cyclos:profile elementId="${fromMember.id}"/>
						</c:otherwise></c:choose>
					</td>
				</tr>
				<tr>
					<td class="headerLabel"><bean:message key="invoice.to"/></td>
					<td class="headerField">
						<c:choose><c:when test="${empty toMember}">
							${localSettings.applicationUsername}
						</c:when><c:otherwise>
							<cyclos:profile elementId="${toMember.id}"/>
						</c:otherwise></c:choose>
					</td>
				</tr>
				<c:if test="${showSentBy}">
					<tr>
						<td class="headerLabel"><bean:message key="invoice.sentBy"/></td>
						<td class="headerField">
	            			<c:choose>
	            				<c:when test="${not empty sentByMember}">
	    	        				<cyclos:profile elementId="${sentByMember.id}"/>
		            			</c:when>
	            				<c:when test="${not empty sentByOperator}">
	    	        				<cyclos:profile elementId="${sentByOperator.id}"/>
		            			</c:when>
		            			<c:when test="${not empty sentByAdmin}">
		            				<cyclos:profile elementId="${sentByAdmin.id}"/>
		            			</c:when>
		            			<c:when test="${sentBySystem}">
		            				${localSettings.applicationUsername}
		            			</c:when>
		            		</c:choose>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="headerLabel"><bean:message key='invoice.date'/></td>
					<td class="headerField"><cyclos:format dateTime="${invoice.date}"/></td>
				</tr>
				<tr>
					<td class="headerLabel"><bean:message key='invoice.status'/></td>
					<td class="headerField">
						<bean:message key='invoice.status.${invoice.status}'/>
						<c:if test="${cyclos:name(invoice.status) == 'ACCEPTED'}">
							<c:if test="${not empty transferId}">
								<a id="transferLink" class="default" style="margin-left:20px;" transferId="${transferId}"><bean:message key='invoice.action.goToPayment'/></a>
							</c:if>
							<c:if test="${not empty paymentId}">
								<a id="paymentLink" class="default" style="margin-left:20px;" paymentId="${paymentId}"><bean:message key='invoice.action.goToPayment'/></a>
							</c:if>
						</c:if>
					</td>
				</tr>
				<c:if test="${showPerformedBy}">
					<tr>
						<td class="headerLabel"><bean:message key="invoice.performedBy"/></td>
						<td class="headerField">
	            			<c:choose>
	            				<c:when test="${not empty performedByMember}">
	    	        				<cyclos:profile elementId="${performedByMember.id}"/>
		            			</c:when>
	            				<c:when test="${not empty performedByOperator}">
	    	        				<cyclos:profile elementId="${performedByOperator.id}"/>
		            			</c:when>
		            			<c:when test="${not empty performedByAdmin}">
		            				<cyclos:profile elementId="${performedByAdmin.id}"/>
		            			</c:when>
		            			<c:when test="${performedBySystem}">
		            				${localSettings.applicationUsername}
		            			</c:when>
		            		</c:choose>
						</td>
					</tr>
				</c:if>
				<c:if test="${showDestinationAccountType}">
					<tr>
						<td class="headerLabel"><bean:message key="invoice.destination"/></td>
						<td class="headerField">${invoice.destinationAccountType.name}</td>
					</tr>
				</c:if>
				<c:choose><c:when test="${not empty invoice.transferType}">
					<tr>
						<td class="headerLabel"><bean:message key="invoice.transferType"/></td>
						<td class="headerField">${invoice.transferType.name}</td>
					</tr>
				</c:when><c:otherwise>
					<c:if test="${canAccept && fn:length(transferTypes) != 1}">
						<tr>
							<td class="headerLabel"><bean:message key="invoice.transferType"/></td>
							<td class="headerField">
								<c:choose><c:when test="${empty transferTypes}">
									<div class="fieldDecoration">
										<cyclos:escapeHTML><bean:message key="invoice.error.noTransferType"/></cyclos:escapeHTML>
									</div>
								</c:when><c:otherwise>
									<select id="transferType">
										<c:forEach var="transferType" items="${transferTypes}">
											<option value="${transferType.id}">${transferType.name}</option>
										</c:forEach>
									</select>
								</c:otherwise></c:choose>
							</td>
						</tr>
					</c:if>
				</c:otherwise></c:choose>
				<c:choose>
					<c:when test="${empty invoice.payments}">
						<tr>
							<td class="headerLabel"><bean:message key='invoice.amount'/></td>
							<td class="headerField">${amount}</td>
						</tr>
					</c:when>
					<c:when test="${fn:length(invoice.payments) == 1}">
						<c:set var="firstPayment" value="${invoice.payments[0]}" />
						<tr>
							<td class="headerLabel"><bean:message key='invoice.amount'/></td>
							<td class="headerField"><cyclos:format number="${firstPayment.amount}" unitsPattern="${unitsPattern}" /></td>
						</tr>
						<tr>
							<td class="headerLabel"><bean:message key='invoice.scheduledFor'/></td>
							<td class="headerField"><cyclos:format rawDate="${firstPayment.date}" /></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td class="headerLabel"><bean:message key='invoice.totalAmount'/></td>
							<td class="headerField"><cyclos:format number="${invoice.amount}" unitsPattern="${unitsPattern}" /></td>
						</tr>
						<tr>
							<td class="headerLabel" valign="top"><bean:message key='invoice.payments'/></td>
							<td class="headerField">
								<table class="nested">
									<tr>
										<th class="tdHeaderContents" width="20%"><bean:message key='transfer.number'/></th>
										<th class="tdHeaderContents" width="40%"><bean:message key='transfer.date'/></th>
										<th class="tdHeaderContents" width="40%"><bean:message key='transfer.amount'/></th>
									</tr>
									<c:forEach var="current" items="${invoice.payments}" varStatus="status">
										<tr>
											<th class="tdHeaderContents">${status.count}</th>
											<td align="center" nowrap="nowrap"><cyclos:format rawDate="${current.date}" /></td>
											<td align="center" nowrap="nowrap"><cyclos:format number="${current.amount}" unitsPattern="${unitsPattern}" /></td>
										</tr>									
									</c:forEach>
								</table>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<c:forEach var="entry" items="${customFields}">
			        <c:set var="field" value="${entry.field}"/>
			        <c:set var="value" value="${entry.value}"/>
			        <c:if test="${not empty value.value}">
			            <tr>
			                <td class="headerLabel">${field.name}</td>
			   				<td class="headerField"><cyclos:customField field="${field}" value="${value}" textOnly="true"/></td>
						</tr>
					</c:if>
			    </c:forEach>
				<tr>
					<td class="headerLabel" valign="top"><bean:message key="invoice.description"/></td>
					<td class="headerField"><cyclos:escapeHTML>${invoice.description}</cyclos:escapeHTML></td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<table cellpadding="0" cellspacing="0">
							<tr>
								<c:if test="${canAccept}">
									<td><ssl:form method="get" styleId="acceptForm" action="${actionPrefix}/acceptInvoice">
										<input type="hidden" name="invoiceId" value="${invoice.id}"/>
										<input type="hidden" name="memberId" value="${member.id}"/>
										<c:if test="${not empty invoiceDetailsForm.accountFeeLogId}">
											<input type="hidden" name="accountFeeLogId" value="${invoiceDetailsForm.accountFeeLogId}"/>
										</c:if>
										<input type="hidden" name="transferTypeId"/>
										&nbsp;&nbsp;<input type="submit" class="button" value="<bean:message key='invoice.action.accept'/>">
									</ssl:form></td>
								</c:if>
								<c:if test="${canDeny}">
									<td><ssl:form method="get" styleId="denyForm" action="${actionPrefix}/denyInvoice">
										<input type="hidden" name="invoiceId" value="${invoice.id}"/>
										<c:if test="${not empty invoiceDetailsForm.accountFeeLogId}">
											<input type="hidden" name="accountFeeLogId" value="${invoiceDetailsForm.accountFeeLogId}"/>
										</c:if>
										&nbsp;&nbsp;<input type="submit" class="button" value="<bean:message key='invoice.action.deny'/>">
									</ssl:form></td>
								</c:if>
								<c:if test="${canCancel}">
									<td><ssl:form method="get" styleId="cancelForm" action="${actionPrefix}/cancelInvoice">
										<input type="hidden" name="invoiceId" value="${invoice.id}"/>
										&nbsp;&nbsp;<input type="submit" class="button" value="<bean:message key='invoice.action.cancel'/>">
									</ssl:form></td>
								</c:if>
							</tr>
						</table>
					</td>
				</tr>
            </table>
        </td>
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left"><input class="button" type="button" id="backButton" value="<bean:message key='global.back'/>"></td>
	</tr>
</table>