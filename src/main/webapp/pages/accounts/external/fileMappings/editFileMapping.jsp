<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/accounts/external/fileMappings/editFileMapping.js" />
<script>
	var resetFileMappingConfirmation = "<cyclos:escapeJS><bean:message key='fileMapping.resetConfirmation'/></cyclos:escapeJS>";
	var removeFieldMappingConfirmation = "<cyclos:escapeJS><bean:message key='fieldMapping.removeConfirmation'/></cyclos:escapeJS>";
</script>

<ssl:form method="post" action="${formAction}">
<html:hidden property="externalAccountId"/>
<html:hidden property="fileMapping(id)"/>
<html:hidden property="fileMapping(account)"/>

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable"><bean:message key="fileMapping.title"/></td>
        <cyclos:help page="bookkeeping#edit_file_mapping"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <div id="fileMappingDescription" style="display:none" align="center">
            	
            	<a id="showFileMappingLink" class="default"><bean:message key="fileMapping.noFileMapping.message"/></a>
            	&nbsp;
            </div>
            <table class="defaultTable" id="fileMappingTable" style="display:none">
            	<tr>
	            	<td class="label" width="25%"><bean:message key="fileMapping.nature"/></td>
	            	<c:choose><c:when test="${isInsert}">
            			<td colspan="3">
	            			<html:select styleId="natureSelect" property="fileMapping(nature)" disabled="true" styleClass="InputBoxDisabled">
	            				<c:forEach var="nature" items="${natures}">
	            					<html:option value="${nature}"><bean:message key="fileMapping.nature.${nature}"/></html:option>
	            				</c:forEach>
		            		</html:select>
		            	</td>
	            	</c:when><c:otherwise>
	            		<td colspan="3">
	            			<html:hidden property="fileMapping(nature)" value="${fileMapping.nature}"/>
	            			<bean:message key="fileMapping.nature.${fileMapping.nature}"/>
	            		</td>
	            	</c:otherwise></c:choose>
            	</tr>
            	<tr class="trCSV">
            		<td class="label" width="25%"><bean:message key="fileMapping.columnSeparator"/></td>
            		<td>
            			<html:text property="fileMapping(columnSeparator)" maxlength="1" readonly="true" styleClass="small InputBoxDisabled"/>
            		</td>
            		<td class="label" width="25%"><bean:message key="fileMapping.stringQuote"/></td>
            		<td>
            			<html:text property="fileMapping(stringQuote)" maxlength="1" readonly="true" styleClass="small InputBoxDisabled"/>
            		</td>
            	</tr>
            	<tr class="trWithFields">
            		<td class="label" width="25%"><bean:message key="fileMapping.headerLines"/></td>
            		<td>
            			<html:text property="fileMapping(headerLines)" maxlength="2" readonly="true" styleClass="small number InputBoxDisabled"/>
            		</td>
            		<td class="label" width="25%"><bean:message key="fileMapping.dateFormat"/></td>
            		<td>
            			<html:text property="fileMapping(dateFormat)" readonly="true" styleClass="small InputBoxDisabled"/>
            		</td>
            	</tr>
            	<tr class="trWithFields"> 
            		<td class="label" width="25%"><bean:message key="fileMapping.numberFormat"/></td>
            		<td>
		           		<html:select styleId="numberFormatSelect" property="fileMapping(numberFormat)" disabled="true" styleClass="InputBoxDisabled">
	          				<c:forEach var="numberFormat" items="${numberFormats}">
	          					<html:option value="${numberFormat}"><bean:message key="fileMapping.numberFormat.${numberFormat}"/></html:option>
	          				</c:forEach>
		           		</html:select>
		           	</td>
		           	<td class="label tdFixedPosition" width="25%"><bean:message key="fileMapping.decimalPlaces"/></td>
            		<td class="tdFixedPosition">
            			<html:text property="fileMapping(decimalPlaces)" maxlength="1" readonly="true" styleClass="small number InputBoxDisabled"/>
            		</td>
		           	<td class="label tdWithSeparator" width="25%"><bean:message key="fileMapping.decimalSeparator"/></td>
            		<td class="tdWithSeparator">
            			<html:text property="fileMapping(decimalSeparator)" maxlength="1" readonly="true" styleClass="small InputBoxDisabled"/>
            		</td>
				</tr>
            	<tr class="trWithFields">
            		<td class="label" width="25%"><bean:message key="fileMapping.negativeAmountValue"/></td>
            		<td colspan="3">
            			<html:text property="fileMapping(negativeAmountValue)" maxlength="50" readonly="true" styleClass="small InputBoxDisabled"/>
            		</td>
            	</tr>
            	<tr class="trCustom">
            		<td class="label" width="25%"><bean:message key="fileMapping.className"/></td>
            		<td colpan="3">
            			<html:text property="fileMapping(className)" readonly="true" styleClass="full InputBoxDisabled"/>
            		</td>
            	</tr>
            	<c:if test="${editable}">
	            	<tr>
	            		<td align="left">
	            			<c:if test="${!isInsert}">
	            				<input type="button" id="resetButton" class="button" value="<bean:message key="global.reset"/>">
	            			</c:if>
	            		</td>
	            		<td colspan="2"/>
						<td align="right">
							<input type="button" id="modifyFileMappingButton" class="button" value="<bean:message key="global.change"/>">&nbsp;
							<input type="submit" id="saveFileMappingButton" class="ButtonDisabled" disabled="disabled" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
				</c:if>
				
            </table>
        </td>
    </tr>
</table>
</ssl:form>

<c:if test="${cyclos:value(fileMapping.nature) == 'CSV'}">
	
	<table class="defaultTableContent" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="tdHeaderTable"><bean:message key='fileMapping.fields.title'/></td>
	        <cyclos:help page="bookkeeping#file_mapping_fields_list"/>
	    </tr>
	    <tr>
	        <td colspan="2" align="left" class="tdContentTableLists">
	            <table class="defaultTable">
	                <tr>
	                	<th class="tdHeaderContents" width="10%"><bean:message key='fieldMapping.order'/></th>
	                    <th class="tdHeaderContents" width="40%"><bean:message key='fieldMapping.name'/></th>
	                    <th class="tdHeaderContents" width="40%"><bean:message key='fieldMapping.field'/></th>
	                    <th class="tdHeaderContents" width="10%">&nbsp;</th>
	                </tr>                
					<c:forEach var="fieldMapping" items="${fieldMappings}">
		                <tr class="<t:toggle>ClassColor1|ClassColor2</t:toggle>">
		                    <td align="center">${fieldMapping.order}</td>
		                    <td align="left">${fieldMapping.name}</td>
		                    <td align="left"><bean:message key='fieldMapping.field.${fieldMapping.field}'/></td>
		                    <td align="center">
		                    	<c:choose><c:when test="${editable}">
			                    	<img fieldMappingId="${fieldMapping.id}" src="<c:url value="/pages/images/edit.gif" />" class="edit details fieldMappingDetails" />
			                    	<img fieldMappingId="${fieldMapping.id}" src="<c:url value="/pages/images/delete.gif" />" class="remove fieldMappingRemove" />
			                    </c:when><c:otherwise>
			                    	<img fieldMappingId="${fieldMapping.id}" src="<c:url value="/pages/images/view.gif" />" class="view details fieldMappingDetails" />
			                    </c:otherwise></c:choose>		                    	
							</td>
						</tr>
					</c:forEach>                
	            </table>
	        </td>
	    </tr>
	</table>
	
	<c:if test="${editable}">
		<table class="defaultTableContentHidden" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right" width="50%">
					<c:if test="${fn:length(fieldMappings) > 1}">
						<span class="label"><bean:message key="fieldMapping.action.changeOrder"/></span>
						<input type="button" class="button" id="changeOrderButton" value="<bean:message key="global.submit"/>">
					</c:if>
				</td>
				<td align="right">
					<span class="label"><bean:message key='fieldMapping.action.new'/></span>
					<input class="button" type="button" id="newFieldMappingButton" value="<bean:message key='global.submit'/>">
				</td>
			</tr>
		</table>
	</c:if>
</c:if>