<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/guarantees/guarantees/registerGuarantee.js" />
<script type="text/javascript">
	var issuerGroupsId = ${issuerGroupsId};
	var buyerGroupsId;
	<c:if test="${buyerGroupsId != null}">
		buyerGroupsId = ${buyerGroupsId};
	</c:if>
	var sellerGroupsId = ${sellerGroupsId};
	var isWithBuyerAndSeller = ${isWithBuyerAndSeller};
</script>
<c:set var="helpPage" value="guarantees#guarantee_register"/>
<c:set var="titleKey" value="guarantee.registerGuarantee" />
<c:set var="creditFeeReadonly" value="${guaranteeType.creditFee.readonly}" />
<c:set var="issueFeeReadonly" value="${guaranteeType.issueFee.readonly}" />

<ssl:form action="${formAction}">
<html:hidden property="guarantee(guaranteeType)" styleId="guaranteeTypeId" value="${guaranteeType.id}"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
		<cyclos:help page="${helpPage}" />
	</tr>
	<tr>
	    <td colspan="4" align="left" class="tdContentTableForms">
	    	<cyclos:layout columns="4">
	    		<cyclos:cell className="label" nowrap="nowrap"><bean:message key="guarantee.guaranteeType"/></cyclos:cell>
	    		<cyclos:cell nowrap="nowrap" colspan="3"><cyclos:escapeHTML value="${guaranteeType.name}"/></cyclos:cell>

				<cyclos:cell className="label" nowrap="nowrap"><bean:message key="guaranteeType.model"/></cyclos:cell>
				<cyclos:cell nowrap="nowrap" colspan="3"><cyclos:escapeHTML><bean:message key="guaranteeType.model.${guaranteeType.model}"/></cyclos:escapeHTML></cyclos:cell>

				<cyclos:cell colspan="4" height="10">&nbsp;</cyclos:cell>
				
				<cyclos:cell className="label"><bean:message key="guarantee.issuerUsername"/></cyclos:cell>
				<cyclos:cell>
					<html:hidden styleId="issuerId" property="guarantee(issuer)"/>
					<div style="white-space: nowrap;"><input id="issuerUsername" class="full required" value="${guarantee.certificacion.issuer.username}"></div>
					<div id="issuersByUsername" class="autoComplete"></div>							
				</cyclos:cell>
				<cyclos:cell className="label"><bean:message key="guarantee.issuerName"/></cyclos:cell>
				<cyclos:cell>
					<div style="white-space: nowrap;"><input id="issuerName" class="full required" value="${guarantee.certificacion.issuer.name}"></div>
					<div id="issuersByName" class="autoComplete"></div>
				</cyclos:cell>

				<cyclos:cell className="label"><bean:message key="guarantee.buyerUsername"/></cyclos:cell>
				<cyclos:cell>
					<html:hidden styleId="buyerId" property="guarantee(buyer)"/>
					<div style="white-space: nowrap;"><input id="buyerUsername" class="full required" value="${guarantee.buyer.username}"></div>
					<div id="buyersByUsername" class="autoComplete"></div>
				</cyclos:cell>
				<cyclos:cell className="label"><bean:message key="guarantee.buyerName"/></cyclos:cell>
				<cyclos:cell>
					<div style="white-space: nowrap;"><input id="buyerName" class="full required" value="${guarantee.buyer.name}"></div>
					<div id="buyersByName" class="autoComplete"></div>
				</cyclos:cell>
				
   	  			<c:if test="${isWithBuyerAndSeller}">
   	  				<cyclos:cell className="label"><bean:message key="guarantee.sellerUsername"/></cyclos:cell>
   	  				<cyclos:cell>
						<html:hidden styleId="sellerId" property="guarantee(seller)"/>
						<div style="white-space: nowrap;"><input id="sellerUsername" class="full required" value="${guarantee.seller.username}"></div>
						<div id="sellersByUsername" class="autoComplete"></div>
					</cyclos:cell>
					<cyclos:cell className="label"><bean:message key="guarantee.sellerName"/></cyclos:cell>
					<cyclos:cell>
						<div style="white-space: nowrap;"><input id="sellerName" class="full required" value="${guarantee.seller.name}"></div>
						<div id="sellersByName" class="autoComplete"></div>
					</cyclos:cell>
				</c:if>
								
				<cyclos:cell className="label"><bean:message key="guarantee.amount"/></cyclos:cell>
				<cyclos:cell><div style="white-space: nowrap;"><html:text property="guarantee(amount)" styleClass="float full required" styleId="amount"/></div></cyclos:cell>
				
				<cyclos:rowBreak/>
				
				<cyclos:cell className="label"><span class="label"><bean:message key='guarantee.validity'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><input id="validityBegin" class="date small required" type="text" name="guarantee(validity).begin" onCalendarUpdate="calculateFee()"/></cyclos:cell>
				<cyclos:cell className="label"><bean:message key='global.range.to'/></cyclos:cell>
				<cyclos:cell nowrap="nowrap"><input id="validityEnd" class="date small required" type="text" name="guarantee(validity).end" onCalendarUpdate="calculateFee()"/></cyclos:cell>

				<cyclos:cell className="label"><bean:message key="guarantee.creditFee"/></cyclos:cell>
				<cyclos:cell>
					<bean:define id="formatedCreditFee"><cyclos:format number="${guaranteeType.creditFee.fee}"/></bean:define>
					<html:text property="guarantee(creditFeeSpec).fee" styleId="creditFeeSpecFee" styleClass="${creditFeeReadonly ? 'tiny float InputBoxDisabled' : 'tiny float'}" readonly="${creditFeeReadonly}" value="${formatedCreditFee}"/>
         			<html:select property="guarantee(creditFeeSpec).type" styleId="creditFeeSpecType" styleClass="${creditFeeReadonly ? 'small InputBoxDisabled' : 'small'}" disabled="${creditFeeReadonly}" value="${guaranteeType.creditFee.type}">
                   		<c:forEach var="feeType" items="${feeTypes}">
                    		<html:option value="${feeType}"><bean:message key="guaranteeType.feeType.${feeType}"/></html:option>
                   		</c:forEach>
                   	</html:select>
                </cyclos:cell>
                <cyclos:cell className="label"><bean:message key="guarantee.issueFee"/></cyclos:cell>
                <cyclos:cell>
					<bean:define id="formatedIssueFee"><cyclos:format number="${guaranteeType.issueFee.fee}"/></bean:define>
					<html:text property="guarantee(issueFeeSpec).fee" styleId="issueFeeSpecFee" styleClass="${issueFeeReadonly ? 'tiny float InputBoxDisabled' : 'tiny float'}" readonly="${issueFeeReadonly}" value="${formatedIssueFee}"/>
         					<html:select property="guarantee(issueFeeSpec).type" styleId="issueFeeSpecType" styleClass="${issueFeeReadonly ? 'small InputBoxDisabled' : 'small'}" disabled="${issueFeeReadonly}" value="${guaranteeType.issueFee.type}">
                   		<c:forEach var="feeType" items="${feeTypes}">
                    		<html:option value="${feeType}"><bean:message key="guaranteeType.feeType.${feeType}"/></html:option>
                   		</c:forEach>
                   	</html:select>
                </cyclos:cell>
                
				<tr class="toHide" style="display:none;">
					<td>&nbsp;</td>
					<td id="creditFeeValueTd"align="left"></td>
					<td>&nbsp;</td>
					<td id="issueFeeValueTd" align="left"></td>
				</tr>

				<c:forEach var="field" items="${customFields}">
					<cyclos:cell className="label">${field.name}</cyclos:cell>
					<cyclos:cell><cyclos:customField field="${field}" editable="true" fieldName="guarantee(customValues).field" valueName="guarantee(customValues).value" /></cyclos:cell>
				</c:forEach>
				
	           	<tr>
					<td colspan="4" align="right">
						<input type="submit" class="button" value="<bean:message key="global.submit"/>">
					</td>
					<td>&nbsp;</td>
				</tr>				
	    	</cyclos:layout>
		</td>
	</tr>
</table>
</ssl:form>

<table class="defaultTableContentHidden" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>"></td>
	</tr>
</table>
