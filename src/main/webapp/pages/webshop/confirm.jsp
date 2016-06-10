<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<cyclos:script src="/pages/webshop/confirm.js" />

<ssl:form method="post" action="${formAction}">
<table class="defaultTable standAloneFixedWidth fullBordered" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdContentTable">
            <table class="defaultTable">
				<tr>
					<td width="25%" class="headerLabel"><bean:message key="transfer.from"/></td>
					<td class="headerField">${payment.from.name} (${payment.from.username})</td>
				</tr>
				<tr>
					<td class="headerLabel"><bean:message key="transfer.to"/></td>
					<td class="headerField">${payment.to.name} (${payment.to.username})</td>
				</tr>
				<tr>
					<td class="headerLabel"><bean:message key="transfer.amount"/></td>
					<td class="headerField"><cyclos:format number="${finalAmount}" unitsPattern="${payment.ticket.currency.pattern}"/></td>
				</tr>
				
				<c:if test="${not empty fees}">
	               	<tr>
						<td class="headerLabel"><bean:message key="payment.confirmation.appliedFees"/></td>
						<td class="headerField">
							<c:forEach var="fee" items="${fees}">
								<div>
									<span style="font-style:italic">${fee.key.name}</span>.&nbsp;
									<span class="label"><bean:message key='transfer.amount'/>:</span>
									<cyclos:format number="${fee.value}" unitsPattern="${fee.key.generatedTransferType.from.currency.pattern}"/>
								</div>
							</c:forEach>
						</td>
					</tr>            	
				</c:if>
				
				<c:set var="confirmationMessage" value="${payment.transferType.confirmationMessage}"/>
				<c:if test="${not empty confirmationMessage}">
					<tr>
						<td colspan="2" class="label" style="text-align:center;padding-bottom:5px">
							<hr>
							
							<cyclos:escapeHTML>${confirmationMessage}</cyclos:escapeHTML>
						</td>
					</tr>
				</c:if>
				
				<c:choose><c:when test="${requestTransactionPassword}">
					<tr>
						<td colspan="2" class="label" style="text-align:center;padding-bottom:10px">
							<hr>
							
							<cyclos:escapeHTML><bean:message key='webshop.confirm.transactionPassword'/></cyclos:escapeHTML>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<cyclos:script src="/pages/access/transactionPassword/transactionPassword.js" />
							<cyclos:script src="/pages/scripts/virtualKeyboard.js" />
							<script>
								var chars = "${accessSettings.transactionPasswordChars}";
								var buttonsPerRow = chars.length / 2;
							</script>
							
							<table class="nested">
								<tr>
									<td align="center">
										<input type="password" name="transactionPassword" ${accessSettings.virtualKeyboardTransactionPassword ? 'readonly="readonly" class="InputBoxDisabled medium"' : 'class="medium"'} maxlength="20" style="width:150px"/>
									</td>
								</tr>
								<c:if test="${accessSettings.virtualKeyboardTransactionPassword}">
									<tr>
										<td align="center">
											<div class="virtualKeyboard" field="transactionPassword"></div>
											<script>
												var contrastLabel = "<cyclos:escapeJS><bean:message key="virtualKeyboard.contrast"/></cyclos:escapeJS>";
												var clearLabel = "<cyclos:escapeJS><bean:message key="global.clear"/></cyclos:escapeJS>";
												var submitLabel = null;
											</script>
										</td>
									</tr>
								</c:if>
							</table>
						</td>
					</tr>
				</c:when><c:otherwise>
					<tr>
						<td colspan="2" class="label" style="text-align:center;padding-bottom:10px">
							<cyclos:escapeHTML><bean:message key='webshop.confirm.text'/></cyclos:escapeHTML>
						</td>
					</tr>
				</c:otherwise></c:choose>
				<tr>
					<td>
						<input type="button" id="cancelButton" class="button" value="<bean:message key='global.cancel'/>">
					</td>
					<td align="right">
						<input type="submit" class="button" value="<bean:message key='global.submit'/>">
					</td>
				</tr>
            </table>
        </td>
    </tr>
</table>
</ssl:form>
