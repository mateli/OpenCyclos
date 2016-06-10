<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %>

<c:set var="isInsert" value="${field['transient']}"/>
<c:set var="hasPossibleValues" value="${!isInsert && cyclos:name(field.type) == 'ENUMERATED'}"/>
<c:set var="arg0" value=""/>
<c:set var="parent" value="${field.parent}" />

<c:choose>
	<c:when test="${nature == 'MEMBER'}">
		<c:set var="titleKey" value="customField.title.${isInsert ? 'insert' : 'modify'}.member"/>
	</c:when>
	<c:when test="${nature == 'ADMIN'}">
		<c:set var="titleKey" value="customField.title.${isInsert ? 'insert' : 'modify'}.admin"/>
	</c:when>
	<c:when test="${nature == 'OPERATOR'}">
		<c:set var="titleKey" value="customField.title.${isInsert ? 'insert' : 'modify'}.operator"/>
	</c:when>
	<c:when test="${nature == 'AD'}">
		<c:set var="titleKey" value="customField.title.${isInsert ? 'insert' : 'modify'}.ad"/>
	</c:when>
	<c:when test="${nature == 'PAYMENT'}">
		<c:set var="titleKey" value="customField.title.${isInsert ? 'insert' : 'modify'}.payment"/>
		<c:set var="arg0" value="${transferType.name}"/>
	</c:when>
	<c:when test="${nature == 'LOAN_GROUP'}">
		<c:set var="titleKey" value="customField.title.${isInsert ? 'insert' : 'modify'}.loanGroup"/>
	</c:when>
	<c:when test="${nature == 'MEMBER_RECORD'}">
		<c:set var="titleKey" value="customField.title.${isInsert ? 'insert' : 'modify'}.memberRecord"/>
		<c:set var="arg0" value="${memberRecordType.name}"/>
	</c:when>
</c:choose>

<cyclos:script src="/pages/customization/fields/editCustomField.js" />
<script>
var unhideConfirmationMessage = "<cyclos:escapeJS><bean:message key="customField.confirmUnhide"/></cyclos:escapeJS>";
var originalCanHideValue = ${nature == 'MEMBER' ? field.memberCanHide : false};

var removePossibleValueConfirmationMessage = "<cyclos:escapeJS><bean:message key="customField.possibleValue.removeConfirmation"/></cyclos:escapeJS>";
var moveValueConfirmation = "<cyclos:escapeJS><bean:message key="customField.moveValue.confirmation" arg0="#oldValue#" arg1="#newValue#"/></cyclos:escapeJS>";
var types = [];
<c:forEach var="type" items="${types}">
	types.push({
		name: "${type}", 
		description: "<cyclos:escapeJS><bean:message key="customField.type.${type}"/></cyclos:escapeJS>",
		defaultSelected: ${field.type == type},
		controls: [
		<c:forEach var="control" items="${type.possibleControls}" varStatus="status">
			${status.count == 1 ? '' : ','}"${control}"
		</c:forEach>
		]
	});
</c:forEach>

var controls = [];
<c:forEach var="control" items="${controls}">
	controls.push({
		name: "${control}", 
		description: "<cyclos:escapeJS><bean:message key="customField.control.${control}"/></cyclos:escapeJS>"
	});
</c:forEach>
var selectedControl = "${field.control}";
var nature = "${nature}";
</script>

<ssl:form method="post" action="${formAction}">
<input type="hidden" name="nature" value="${nature}" />
<html:hidden property="fieldId" />
<html:hidden property="field(id)" />
<html:hidden property="field(transferType)"/>
<html:hidden property="field(memberRecordType)"/>
<html:hidden property="accountTypeId" value="${transferType.from.id}"/>
<html:hidden property="backToTransferTypeId" value="${backToTransferType.id}"/>
<html:hidden property="backToAccountTypeId" value="${backToTransferType.from.id}"/>
<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="${titleKey}" arg0="${arg0}"/>
        </td>
        <cyclos:help page="custom_fields#edit_custom_fields"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <table class="defaultTable">
                <tr>
                    <td class="label" width="30%"><bean:message key="customField.name"/></td>
                    <td><html:text property="field(name)" styleClass="large InputBoxDisabled" readonly="true"/></td>
                </tr>                
                <tr>
                    <td class="label" width="30%"><bean:message key="customField.internalName"/></td>
                    <td><html:text property="field(internalName)" styleClass="medium InputBoxDisabled" readonly="true"/></td>
                </tr>
                <tr>
                    <td class="label"><bean:message key="customField.type"/></td>
                    <td>
                    	<c:choose><c:when test="${isInsert}">
	                    	<html:select styleId="typeSelect" property="field(type)" styleClass="InputBoxDisabled" disabled="true"/>
	                    </c:when><c:otherwise>
	                    	<html:hidden property="field(type)"/>
	                    	<input class="medium InputBoxDisabled" id="typeText" readonly="readonly" value="<bean:message key="customField.type.${field.type}"/>">
	                    </c:otherwise></c:choose>
                    </td>
                </tr>
                <tr style="display:none" id="trParent">
                    <td class="label"><bean:message key="customField.parent"/></td>
                    <td>
                    	<c:choose><c:when test="${isInsert}">
	                    	<html:select styleId="parentSelect" property="field(parent)" styleClass="InputBoxDisabled" disabled="true">
	                    		<html:option value=""></html:option>
	                    		<c:forEach var="current" items="${possibleParentFields}">
	                    			<html:option value="${current.id}">${current.name}</html:option>
	                    		</c:forEach>
	                    	</html:select>
	                    </c:when><c:otherwise>
	                    	<html:hidden property="field(parent)"/>
	                    	<input class="large InputBoxDisabled" id="parentText" readonly="readonly" value="<cyclos:escapeHTML>${parent.name}</cyclos:escapeHTML>">
	                    </c:otherwise></c:choose>
                    </td>
                </tr>
                <tr id="trAllSelected" style="display:none">
                    <td class="label"><bean:message key="customField.allSelectedLabel"/></td>
                    <td><html:text styleClass="large InputBoxDisabled" readonly="true" property="field(allSelectedLabel)"/></td>
                </tr>   
                <tr id="trPattern" style="display:none">
                    <td class="label"><bean:message key="customField.pattern"/></td>
                    <td><html:text styleClass="large InputBoxDisabled" readonly="true" property="field(pattern)"/></td>
                </tr>             
                <tr id="trControl">
                    <td class="label"><bean:message key="customField.control"/></td>
                    <td>
                    	<html:select property="field(control)" styleId="controlSelect" styleClass="InputBoxDisabled" disabled="true"/>
                    </td>
                </tr>
                <tr id="trSize" style="display:none">
                    <td class="label"><bean:message key="customField.size"/></td>
                    <td>
                    	<html:select property="field(size)" styleClass="InputBoxDisabled" disabled="true">
	                   		<c:forEach var="size" items="${sizes}">
	                    		<html:option value="${size}"><bean:message key="customField.size.${size}"/></html:option>
	                   		</c:forEach>
                   		</html:select>
                    </td>
                </tr>
                <c:choose>
                	<c:when test="${nature == 'MEMBER'}">
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.member.visibilityAccess"/></td>
		                    <td>
		                    	<html:select property="field(visibilityAccess)" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="acc" items="${accessForView}">
			                    		<html:option value="${acc}"><bean:message key="customField.member.access.${acc}"/></html:option>
			                   		</c:forEach>
		                   		</html:select>
		                    </td>
		                </tr>
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.member.updateAccess"/></td>
		                    <td>
		                    	<html:select property="field(updateAccess)" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="acc" items="${accessForEdit}">
			                    		<html:option value="${acc}"><bean:message key="customField.member.access.${acc}"/></html:option>
			                   		</c:forEach>
		                   		</html:select>
		                    </td>
		                </tr>
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.member.memberSearchAccess"/></td>
		                    <td>
		                    	<html:select property="field(memberSearchAccess)" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="acc" items="${memberAndAdsAccess}">
			                    		<html:option value="${acc}"><bean:message key="customField.member.access.${acc}"/></html:option>
			                   		</c:forEach>
		                   		</html:select>
		                    </td>
		                </tr>
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.member.adSearchAccess"/></td>
		                    <td>
		                    	<html:select property="field(adSearchAccess)" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="acc" items="${memberAndAdsAccess}">
			                    		<html:option value="${acc}"><bean:message key="customField.member.access.${acc}"/></html:option>
				                   	</c:forEach>
		                   		</html:select>
		                    </td>
		                </tr>
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.member.indexing"/></td>
		                    <td>
		                    	<html:select property="field(indexing)" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="indexing" items="${indexings}">
			                    		<html:option value="${indexing}"><bean:message key="customField.member.indexing.${indexing}"/></html:option>
				                   	</c:forEach>
		                   		</html:select>
		                    </td>
		                </tr>
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.member.loanSearchAccess"/></td>
		                    <td>
		                    	<html:select property="field(loanSearchAccess)" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="acc" items="${access}">
		                    			<html:option value="${acc}"><bean:message key="customField.member.access.${acc}"/></html:option>
			                   		</c:forEach>
		                   		</html:select>
		                    </td>
		                </tr>
		                <tr class="rootField" id="trCanHide" style="display:none">
		                    <td class="label"><bean:message key="customField.member.memberCanHide"/></td>
		                    <td><html:checkbox property="field(memberCanHide)" styleId="memberCanHide" styleClass="checkbox" disabled="true" value="true"/></td>
		                </tr>
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.member.showInPrint"/></td>
		                    <td><html:checkbox property="field(showInPrint)" styleClass="checkbox" disabled="true" value="true"/></td>
		                </tr>
                	</c:when>
                	<c:when test="${nature == 'ADMIN'}">
                		<%-- No special fields on admin --%>
                	</c:when>
                	<c:when test="${nature == 'OPERATOR'}">
                		<tr style="display:none">
                			<td colspan="2"><html:hidden property="field(member)"/></td>
                		</tr>
                		<tr class="rootField">
                			<td class="label"><bean:message key="customField.operator.visibility"/></td>
		                    <td>
		                    	<html:select property="field(visibility)" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="visibility" items="${visibilities}">
			                    		<html:option value="${visibility}"><bean:message key="customField.operator.visibility.${visibility}"/></html:option>
			                   		</c:forEach>
		                   		</html:select>
		                    </td>
                		</tr>
                	</c:when>
                	<c:when test="${nature == 'AD'}">
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.ad.visibility"/></td>
		                    <td>
		                    	<html:select property="field(visibility)" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="visibility" items="${visibilities}">
			                    		<html:option value="${visibility}"><bean:message key="customField.ad.visibility.${visibility}"/></html:option>
			                   		</c:forEach>
		                    	</html:select>
		                    </td>
		                </tr>
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.ad.showInSearch"/></td>
		                    <td><html:checkbox property="field(showInSearch)" styleClass="checkbox" disabled="true" value="true"/></td>
		                </tr>
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.ad.indexed"/></td>
		                    <td><html:checkbox property="field(indexed)" styleClass="checkbox" disabled="true" value="true"/></td>
		                </tr>
                	</c:when>
                	<c:when test="${nature == 'PAYMENT'}">
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.payment.enabled"/></td>
		                    <td><html:checkbox property="field(enabled)" styleClass="checkbox" disabled="true" value="true"/></td>
		                </tr>
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.payment.searchAccess"/></td>
		                    <td>
		                    	<c:choose><c:when test="${transferType.loanType}">
		                    		<html:checkbox property="field(searchAccess)" disabled="true" value="BOTH_ACCOUNTS" />
		                    		<html:hidden property="field(searchAccess)" value="NONE" />
		                    	</c:when><c:otherwise>
									<html:select property="field(searchAccess)" styleClass="InputBoxDisabled" disabled="true">
				                   		<c:forEach var="access" items="${accesses}">
				                    		<html:option value="${access}"><bean:message key="customField.payment.access.${access}"/></html:option>
				                   		</c:forEach>
			                   		</html:select>
		                    	</c:otherwise></c:choose>
							</td>
		                </tr>
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.payment.listAccess"/></td>
		                    <td>
		                    	<c:choose><c:when test="${transferType.loanType}">
		                    		<html:checkbox property="field(listAccess)" disabled="true" value="BOTH_ACCOUNTS" />
		                    		<html:hidden property="field(listAccess)" value="NONE" />
		                    	</c:when><c:otherwise>
									<html:select property="field(listAccess)" styleClass="InputBoxDisabled" disabled="true">
				                   		<c:forEach var="access" items="${accesses}">
				                    		<html:option value="${access}"><bean:message key="customField.payment.access.${access}"/></html:option>
				                   		</c:forEach>
			                   		</html:select>
		                    	</c:otherwise></c:choose>
							</td>
		                </tr>
                	</c:when>
                	<c:when test="${nature == 'LOAN_GROUP'}">
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.loanGroup.showInSearch"/></td>
		                    <td><html:checkbox property="field(showInSearch)" styleClass="checkbox" disabled="true" value="true"/></td>
		                </tr>
                	</c:when>
                	<c:when test="${nature == 'MEMBER_RECORD'}">
                		<tr class="rootField">
		                    <td class="label">
		                    	<bean:message key="customField.memberRecord.showInSearch"/>
		                    </td>
		                    <td><html:checkbox property="field(showInSearch)" styleClass="checkbox" disabled="true" value="true"/></td>
		                </tr>
		                <tr class="rootField">
		                    <td class="label"><bean:message key="customField.memberRecord.showInList"/></td>
		                    <td><html:checkbox property="field(showInList)" styleClass="checkbox" disabled="true" value="true"/></td>
		                </tr>
		                <tr class="rootField">
                			<td class="label"><bean:message key="customField.memberRecord.brokerAccess"/></td>
		                    <td>
		                    	<html:select property="field(brokerAccess)" styleClass="InputBoxDisabled" disabled="true">
			                   		<c:forEach var="access" items="${accesses}">
			                    		<html:option value="${access}"><bean:message key="customField.memberRecord.brokerAccess.${access}"/></html:option>
			                   		</c:forEach>
		                   		</html:select>
		                    </td>
                		</tr>
                	</c:when>
                </c:choose>
				<tr id="trValidation" style="display:none" class="rootField">
					<td class="label"><bean:message key="customField.validation"/></td>
					<td>
						<fieldset>
							<table width="100%" class="nested">
								<tr>
									<td width="35%" class="label"><bean:message key="customField.validation.required"/></td>
									<td width="15%"><html:checkbox property="field(validation).required" styleClass="checkbox" disabled="true" value="true"/></td>
									<td width="35%" class="label"><span class="unique"><bean:message key="customField.validation.unique"/></span></td>
									<td width="15%"><html:checkbox property="field(validation).unique" styleClass="checkbox unique" disabled="true" value="true"/></td>
								</tr>
								<tr id="trRangeConstraint" style="display:none">
									<td class="label"><bean:message key="customField.validation.minLength"/></td>
									<td><html:text property="field(validation).lengthConstraint.min" styleClass="number tiny InputBoxDisabled" readonly="true"/></td>
									<td class="label"><bean:message key="customField.validation.maxLength"/></td>
									<td><html:text property="field(validation).lengthConstraint.max" styleClass="number tiny InputBoxDisabled" readonly="true"/></td>
								</tr>
								<tr id="trValidatorClass" style="display:none">
									<td class="label"><bean:message key="customField.validation.validatorClass"/></td>
									<td colspan="3"><html:text property="field(validation).validatorClass" styleClass="large InputBoxDisabled" readonly="true"/></td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>                
                <tr>
                    <td class="label" valign="top"><bean:message key="customField.description"/></td>
                    <td><html:textarea styleClass="full InputBoxDisabled" readonly="true" rows="4" property="field(description)"/></td>
                </tr>
                <c:if test="${nature == 'MEMBER' || nature == 'ADMIN'}">
	                <tr class="rootField">
	                    <td class="label" valign="top"><bean:message key="customField.groups"/></td>
	                    <td>
							<cyclos:multiDropDown name="field(groups)" disabled="true" size="5">
								<c:forEach var="group" items="${groups}">
									<cyclos:option value="${group.id}" text="${group.name}" selected="${cyclos:contains(field.groups, group)}"/>
								</c:forEach>
							</cyclos:multiDropDown>
						</td>
	                </tr>
	            </c:if>
                <tr>
                	<td align="right" colspan="2">
						<c:if test="${canManage}">                	
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
	                		<input type="submit" id="saveButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
                		</c:if>
                	</td>
                </tr>
            </table>
		</td>            
    </tr>
</table>

<table class="defaultTableContentHidden">
	<tr>
		<td align="left">
			<input type="button" class="button" id="backButton" value="<bean:message key="global.back"/>">
		</td>
		<c:if test="${hasPossibleValues}">
			<c:if test="${not empty parent}">
				<td>
					<span id="parentValues">
						<span class="label" id="parentFieldName">${parent.name}</span>
						<html:select property="parentValueId" styleId="parentValueSelect">
							<c:forEach var="parentPossibleValue" items="${parent.possibleValues}">
								<html:option value="${parentPossibleValue.id}">${parentPossibleValue.value}</html:option>
							</c:forEach>
						</html:select>
					</span>
				</td>
			</c:if>
			<c:if test="${canManage && (parent == null || (parent != null && editCustomFieldForm.parentValueId > 0))}">
				<td align="right">
					<input type="button" id="newPossibleValueButton" class="button" value="<bean:message key="customField.action.newPossibleValue"/>">
				</td>
			</c:if>
		</c:if>
	</tr>
</table>
</ssl:form>

<c:if test="${hasPossibleValues}">
	<div id="possibleValues">
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable">
	        	<bean:message key="customField.possibleValue.title"/>
	        </td>
	        <cyclos:help page="custom_fields#possible_values"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableForms">
	            <table class="defaultTable">
	                <tr>
	                	<td class="tdHeaderContents"><bean:message key="customField.possibleValue.value"/></td>
	                	<td width="10%" nowrap="nowrap" align="center" class="tdHeaderContents"><bean:message key="customField.possibleValue.default"/></td>
	                	<td width="10%" nowrap="nowrap" align="center" class="tdHeaderContents"><bean:message key="customField.possibleValue.enabled"/></td>
	                    <c:if test="${canManage}">
		                    <td class="tdHeaderContents" width="10%">&nbsp;</td>
		                </c:if>
	                </tr>
	                <c:forEach var="possibleValue" items="${possibleValues}">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td class="possibleValue" id="tdPossibleValueValue_${possibleValue.id}"><cyclos:escapeHTML>${possibleValue.value}</cyclos:escapeHTML></td>
		                    <td align="center" id="tdPossibleValueDefault_${possibleValue.id}" isDefault="${possibleValue.defaultValue}">
		                    	<c:choose><c:when test="${possibleValue.defaultValue}">
		                    		<bean:message key="global.yes" />
		                    	</c:when><c:otherwise>
		                    		&nbsp;
		                    	</c:otherwise></c:choose>
		                    </td>
		                    <td align="center" id="tdPossibleValueEnabled_${possibleValue.id}" isEnabled="${possibleValue.enabled}"><bean:message key="${possibleValue.enabled? 'global.yes' : 'global.no'}" /></td>
		                    <c:if test="${canManage}">
		                    	<td align="center">
									<img class="edit editPossibleValue" src="<c:url value="/pages/images/edit.gif"/>" possibleValueId="${possibleValue.id}" border="0">
		                    		<img class="remove removePossibleValue" src="<c:url value="/pages/images/delete.gif"/>" possibleValueId="${possibleValue.id}" border="0">
			                    </td>	
			                </c:if>
		                </tr>
	                </c:forEach>
	            </table>
			</td>            
	    </tr>
	</table>
	
	</div>
	<a name="editPossibleValue"></a>
	<ssl:form method="post" action="${actionPrefix}/editCustomFieldPossibleValue" styleId="editPossibleValueForm" style="display:none">
	
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable">
	        	<span id="possibleValueTitleInsert" style="display:none"><bean:message key="customField.possibleValue.title.insert"/></span>
	        	<span id="possibleValueTitleModify" style="display:none"><bean:message key="customField.possibleValue.title.modify"/></span>
	        </td>
	        <cyclos:help page="custom_fields#edit_possible_value"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableForms">
	        	<input type="hidden" name="nature" value="${nature}">
        		<input type="hidden" name="possibleValue(id)">
        		<input type="hidden" name="possibleValue(parent)" id="parentValueIdHidden">
        		<input type="hidden" name="possibleValue(field)" value="${field.id}">
	            <table class="nested" width="100%">
                	<c:if test="${not empty parent.possibleValues}">
		                <tr>
		                    <td class="label" width="30%">${parent.name}</td>
		                    <td colspan="2"><input class="InputBoxDisabled full" readonly="readonly" id="parentValueDisplay"></td>
	                    </tr>
                	</c:if>
	                <tr id="trSingleValue" style="display:none">
	                    <td class="label" width="30%"><bean:message key="customField.possibleValue.value"/></td>
	                    <td colspan="2"><input type="text" name="possibleValue(value)" class="full" /></td>
	                </tr>
	                <tr id="trMultipleValues" style="display:none">
	                    <td class="label" width="30%"><bean:message key="customField.possibleValue.value"/></td>
	                    <td colspan="2">
	                    	<textarea name="multipleValues" rows="6" class="full"></textarea>
	                    	
	                    	<bean:message key="customField.possibleValue.multipleValues" />
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="customField.possibleValue.default"/></td>
	                    <td colspan="2"><input type="checkbox" class="checkbox" name="possibleValue(defaultValue)"></td>
                    </tr>
	                <tr>
	                    <td class="label"><bean:message key="customField.possibleValue.enabled"/></td>
	                    <td colspan="2"><input type="checkbox" class="checkbox" name="possibleValue(enabled)"></td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
	                <tr>
						<td width="30%" align="left">
							<input type="button" class="button" id="cancelPossibleValueButton" value="<bean:message key="global.cancel"/>">
						</td>
	                	<td width="40%" align="center" nowrap="nowrap">
							<select id="movePossibleValue" style="width:100%">
								<option><bean:message key="customField.moveValue"/></option>
							</select>
						</td>
	                	<td width="30%" align="right">
							<input type="submit" id="submitPossibleValueButton" class="button" value="<bean:message key="global.submit"/>">
						</td>
	                </tr>
	            </table>
			</td>            
	    </tr>
	</table>
    </ssl:form>
	
</c:if>