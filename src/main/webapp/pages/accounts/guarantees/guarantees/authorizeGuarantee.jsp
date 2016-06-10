<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/guarantees/guarantees/authorizeGuarantee.js" />
<c:set var="helpPage" value="guarantees#guarantee_authorization"/>
<c:set var="titleKey" value="guarantee.title.authorizeGuarantee" />

<c:set var="creditFeeSpec" value="${empty guarantee.creditFeeSpec ? guarantee.guaranteeType.creditFee : guarantee.creditFeeSpec}"/>
<c:set var="issueFeeSpec" value="${empty guarantee.issueFeeSpec ? guarantee.guaranteeType.issueFee : guarantee.issueFeeSpec}"/>
<c:set var="creditFeeReadonly" value="${isIssuer || guarantee.guaranteeType.creditFee.readonly}" />
<c:set var="issueFeeReadonly" value="${guarantee.guaranteeType.issueFee.readonly}" />

<ssl:form action="${formAction}">
	<html:hidden property="guaranteeId"/>
	<input type="hidden" id="guaranteeTypeId" value="${guarantee.guaranteeType.id}"/>
	<html:hidden property="guarantee(amount)" value="${guarantee.amount}"/>
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
		<tr>
			<td class="tdHeaderTable"><bean:message key="${titleKey}"/></td>
			<cyclos:help page="${helpPage}" />
		</tr>
		<tr>
		    <td colspan="4" align="left" class="tdContentTableForms">
	    	  	<cyclos:layout columns="4" className="defaultTable">
	    	  		<c:set var="formattedAmount"><cyclos:format number="${guarantee.amount}" /></c:set>
	    	  		<cyclos:cell className="label"><bean:message key="guarantee.amount" /></cyclos:cell>
	    	  		<cyclos:cell elementId="amount" amount="${formattedAmount}"><cyclos:format number="${guarantee.amount}" unitsPattern="${guarantee.guaranteeType.currency.pattern}" /></cyclos:cell>

					<c:forEach var="entry" items="${customFields}">
						<c:set var="field" value="${entry.field}" />
						<c:set var="value" value="${entry.value}" />
						<cyclos:cell className="label">${field.name}</cyclos:cell>
						<cyclos:cell><cyclos:customField field="${field}" value="${value}" editable="true" fieldName="guarantee(customValues).field" valueName="guarantee(customValues).value" /></cyclos:cell>
					</c:forEach>
					
					<cyclos:rowBreak/>

					<cyclos:cell className="label"><span class="label"><bean:message key='guarantee.validity'/></span><span class="lastLabel"><bean:message key='global.range.from'/></span></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><div style="white-space: nowrap;"><input id="validityBegin" class="date small required" type="text" name="guarantee(validity).begin" value="<cyclos:format rawDate="${guarantee.validity.begin}"/>" onCalendarUpdate="calculateFee()"/></div></cyclos:cell>
					<cyclos:cell className="label"><bean:message key='global.range.to'/></cyclos:cell>
					<cyclos:cell nowrap="nowrap"><div style="white-space: nowrap;"><input id="validityEnd" class="date small required" type="text" name="guarantee(validity).end" value="<cyclos:format rawDate="${guarantee.validity.end}"/>" onCalendarUpdate="calculateFee()"/></div></cyclos:cell>
					
					<cyclos:cell className="label"><bean:message key="guarantee.creditFee"/></cyclos:cell>
					<cyclos:cell nowrap="nowrap">
					    <bean:define id="creditFee"><cyclos:format number="${creditFeeSpec.fee}"/></bean:define>
						<html:text property="guarantee(creditFeeSpec).fee" styleId="creditFeeSpecFee" styleClass="${creditFeeReadonly ? 'tiny float InputBoxDisabled' : 'tiny float'}" readonly="${creditFeeReadonly}"  value="${creditFee}"/>
          					<html:select property="guarantee(creditFeeSpec).type" styleId="creditFeeSpecType" styleClass="${creditFeeReadonly ? 'medium InputBoxDisabled' : 'medium'}" disabled="${creditFeeReadonly}" value="${creditFeeSpec.type}">
	                   		<c:forEach var="feeType" items="${feeTypes}">
	                    		<html:option value="${feeType}"><bean:message key="guaranteeType.feeType.${feeType}"/></html:option>
	                   		</c:forEach>
                    	</html:select>
                    </cyclos:cell>
                    <cyclos:cell className="label"><bean:message key="guarantee.amountToCharge"/></cyclos:cell>
					<cyclos:cell elementId="creditFeeValueTd" align="left"></cyclos:cell>
					
					<cyclos:cell className="label"><bean:message key="guaranteeType.issueFee"/></cyclos:cell>
					<cyclos:cell nowrap="nowrap">
					    <bean:define id="issueFee"><cyclos:format number="${issueFeeSpec.fee}"/></bean:define>
						<html:text property="guarantee(issueFeeSpec).fee" styleId="issueFeeSpecFee" styleClass="${issueFeeReadonly ? 'tiny float InputBoxDisabled' : 'tiny float'}" readonly="${issueFeeReadonly}"  value="${issueFee}"/>
          					<html:select property="guarantee(issueFeeSpec).type" styleId="issueFeeSpecType" styleClass="${issueFeeReadonly ? 'medium InputBoxDisabled' : 'medium'}" disabled="${issueFeeReadonly}" value="${issueFeeSpec.type}">
	                   		<c:forEach var="feeType" items="${feeTypes}">
	                    		<html:option value="${feeType}"><bean:message key="guaranteeType.feeType.${feeType}"/></html:option>
	                   		</c:forEach>
                    	</html:select>		            	
	                </cyclos:cell>
	                
	                
	                <cyclos:cell className="label"><bean:message key="guarantee.amountToCharge"/></cyclos:cell>
	                <cyclos:cell elementId="issueFeeValueTd" align="left"></cyclos:cell>

					<cyclos:cell colspan="4" /><cyclos:cell colspan="4" />
					
					<c:choose>
						<c:when test="${canAcceptLoan}">
							<cyclos:cell colspan="3">
			                	<html:checkbox property="automaticLoanAuthorization" value="true"></html:checkbox>
			                	<bean:message key="guarantee.automaticLoanAuthorization"/>
			                </cyclos:cell>
						</c:when>
						<c:otherwise>
							<cyclos:cell colspan="3"/>
						</c:otherwise>
					</c:choose>
					<cyclos:cell align="right">
						<input type="submit" class="button" value="<bean:message key="guarantee.action.accept"/>">
					</cyclos:cell>
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