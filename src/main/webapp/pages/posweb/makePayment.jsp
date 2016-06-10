<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<cyclos:script src="/pages/posweb/makePayment.js" />
<script>
	var selectTransferTypeMessage = "<cyclos:escapeJS><bean:message key="payment.selectTransferType" /></cyclos:escapeJS>";
	var noTransferTypeMessage = "<cyclos:escapeJS><bean:message key="payment.error.noTransferType" /></cyclos:escapeJS>";
	var loggedMember = "${loggedMember.id}";
</script>
<ssl:form method="post" action="${formAction}">
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="posweb.title.makePayment"/></td>
        <td class="tdHelpIcon">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2" align="center" class="tdContentTableForms">
        	
            <table class="defaultTable" id="formTable">
				<tr>
					<td width="35%" class="label"><bean:message key='transfer.from'/></td>
					<td><input class="InputBoxDisabled full" readonly="readonly" value="${loggedMember.name}"></td>
				</tr>
				<tr>
					<td class="label"><bean:message key='transfer.amount'/></td>
					<td style="text-align: left">
						<html:text styleId="amount" property="amount" styleClass="float medium"/>
						<c:choose><c:when test="${not empty singleCurrency}">
							<html:hidden property="currency" value="${singleCurrency.id}" />
							${singleCurrency.symbol}
						</c:when><c:otherwise>
							<html:select property="currency" styleId="currencySelect">
    	        				<c:forEach var="currency" items="${currencies}">
        	    					<html:option value="${currency.id}">${currency.symbol}</html:option>
            					</c:forEach>
            				</html:select>
						</c:otherwise></c:choose>
					</td>
				</tr>
				<c:if test="${showDescription}">
					<tr>
						<td class="label"><bean:message key="transfer.description"/></td>
						<td><html:textarea property="description" styleClass="full" rows="2" /></td>
					</tr>
				</c:if>
				<tr>
					<td class="label"><bean:message key="posweb.target.username"/></td>
					<td>
						<html:hidden styleId="memberId" property="to"/>
						<input id="memberUsername" class="full">
						<div id="membersByUsername" class="autoComplete"></div>
					</td>
				</tr>
				<tr>
					<td class="label"><bean:message key="posweb.target.name"/></td>
					<td>
						<input id="memberName" class="full">
						<div id="membersByName" class="autoComplete"></div>
					</td>
				</tr>
				<tr id="transferTypeRow" style="display:none">
					<td class="label"><bean:message key="transfer.type"/></td>
					<td><select id="transferType" name="type" class="full"></select></td>
				</tr>
				<tr id="customValuesRow" style="display:none">
					<td colspan="2" id="customValuesCell" style="padding:0px;">
					</td>
				</tr>
				<tr id="passwordRow" style="display:none">
					<td class="label"><bean:message key='login.transactionPassword'/></td>
					<td><input type="password" name="transactionPassword" class="full"/></td>
				</tr>
				<tr>
					<td>
						
						<input type="button" id="clearButton" class="button" value="<bean:message key='global.clear'/> (Esc)">
					</td>
					<td align="right">
						
						<input type="submit" class="button" value="<bean:message key='global.submit'/>">
					</td>
				</tr>
            </table>
            <jsp:include page="includes/lastPayment.jsp"></jsp:include>
        </td>
    </tr>
</table>
</ssl:form>
