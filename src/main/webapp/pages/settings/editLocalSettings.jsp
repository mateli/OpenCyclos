<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://sslext.sf.net/tags/sslext" prefix="ssl" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>
<%@ taglib uri="http://www.servletsuite.com/servlets/toggletag" prefix="t" %> 

<cyclos:script src="/pages/settings/editLocalSettings.js" />

<script language="JavaScript">
	var oldLanguage='${localSettings.language}';
	var confirmationMessage='<cyclos:escapeJS><bean:message key="settings.local.changeConfirmationMessage"/></cyclos:escapeJS>';
</script>

<ssl:form method="post" action="${formAction}">

<table class="defaultTableContent" cellspacing="0" cellpadding="0">
    <tr>
        <td class="tdHeaderTable">
        	<bean:message key="settings.local.title"/>
        </td>
        <cyclos:help page="settings#local"/>
    </tr>
    <tr>
        <td colspan="2" align="left" class="tdContentTableForms">
            <fieldset>
            	<legend><bean:message key="settings.local.identification"/></legend>
	            <table class="defaultTable">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.local.applicationName"/></td>
	                    <td><html:text property="setting(applicationName)" styleClass="full InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.applicationUsername"/></td>
	                    <td><html:text property="setting(applicationUsername)" styleClass="full InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
                   		<td class="label"><bean:message key="settings.local.cyclosId"/></td>
                   		<td><html:text property="setting(cyclosId)" styleClass="full InputBoxDisabled" readonly="true"/></td>
                   	</tr>
	                <tr>
                   		<td class="label"><bean:message key="settings.local.rootUrl"/></td>
                   		<td><html:text property="setting(rootUrl)" styleClass="full InputBoxDisabled" readonly="true"/></td>
                   	</tr>
	                <tr>
                   		<td class="label"><bean:message key="settings.local.containerUrl"/></td>
                   		<td><html:text property="setting(containerUrl)" styleClass="full InputBoxDisabled" readonly="true"/></td>
                   	</tr>
	            </table>
	        </fieldset>
            <fieldset>
            	<legend><bean:message key="settings.local.internationalization"/></legend>
		        <table class="defaultTable">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.local.language"/></td>
	                    <td>
	                    	<html:select property="setting(language)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="lang" items="${languages}">
		                    		<html:option value="${lang}"><bean:message key="settings.local.language.${lang}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.numberLocale"/></td>
	                    <td>
	                    	<html:select property="setting(numberLocale)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="numberLocale" items="${numberLocales}">
		                    		<html:option value="${numberLocale}"><bean:message key="settings.local.numberLocale.${numberLocale}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.precision"/></td>
	                    <td>
	                    	<html:select property="setting(precision)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="precision" items="${precisions}">
		                    		<html:option value="${precision}">${precision.value}</html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.highPrecision"/></td>
	                    <td>
	                    	<html:select property="setting(highPrecision)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="precision" items="${precisions}">
		                    		<html:option value="${precision}">${precision.value}</html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.decimalInputMethod"/></td>
	                    <td>
	                    	<html:select property="setting(decimalInputMethod)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="decimalInputMethod" items="${decimalInputMethods}">
		                    		<html:option value="${decimalInputMethod}"><bean:message key="settings.local.decimalInputMethod.${decimalInputMethod}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.timeZone"/></td>
	                    <td>
	                    	<html:select property="setting(timeZone)" styleClass="InputBoxDisabled" disabled="true">
	                    		<html:option value=""><bean:message key="settings.local.timeZone.none" /></html:option>
		                   		<c:forEach var="timeZone" items="${timeZones}">
		                    		<html:option value="${timeZone}">${timeZone}</html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.datePattern"/></td>
	                    <td>
	                    	<html:select property="setting(datePattern)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="entry" items="${datePatterns}">
		                    		<html:option value="${entry.key}">${entry.value}</html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.timePattern"/></td>
	                    <td>
	                    	<html:select property="setting(timePattern)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="timePattern" items="${timePatterns}">
		                    		<html:option value="${timePattern}">${timePattern.description}</html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
            	</table>
            </fieldset>
            <fieldset>
            	<legend><bean:message key="settings.local.limits"/></legend>
		        <table class="defaultTable">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.local.maxIteratorResults"/></td>
	                    <td><html:text property="setting(maxIteratorResults)" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    (<span class="fieldDecoration"><bean:message key="settings.unlimited"/></span>)
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.local.maxPageResults"/></td>
	                    <td><html:text property="setting(maxPageResults)" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.maxAjaxResults"/></td>
	                    <td><html:text property="setting(maxAjaxResults)" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.maxUploadSize"/></td>
	                    <td nowrap="nowrap">
	                    	<html:text property="setting(maxUploadSize)" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<html:select property="setting(maxUploadUnits)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="unit" items="${uploadUnits}">
		                    		<html:option value="${unit}">${unit.display}</html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.maxImageSize"/></td>
	                    <td>
	                    	<html:text property="setting(maxImageWidth)" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	x
	                    	<html:text property="setting(maxImageHeight)" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<bean:message key="global.pixels" />
						</td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.maxThumbnailSize"/></td>
	                    <td>
	                    	<html:text property="setting(maxThumbnailWidth)" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	x
	                    	<html:text property="setting(maxThumbnailHeight)" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<bean:message key="global.pixels" />
						</td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.brokeringExpiration"/></td>
	                    <td nowrap="nowrap">
	                    	<html:text property="setting(brokeringExpirationPeriod).number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<html:select property="setting(brokeringExpirationPeriod).field" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="field" items="${brokeringExpirationUnits}">
		                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    	(<span class="fieldDecoration"><bean:message key="settings.neverExpiresMessage"/></span>)
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.deleteMessagesOnTrashAfter"/></td>
	                    <td nowrap="nowrap">
	                    	<html:text property="setting(deleteMessagesOnTrashAfter).number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<html:select property="setting(deleteMessagesOnTrashAfter).field" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="field" items="${deleteMessagesExpirationUnits}">
		                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    	(<span class="fieldDecoration"><bean:message key="settings.neverDeleteMessage"/></span>)
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.deletePendingRegistrationsAfter"/></td>
	                    <td nowrap="nowrap">
	                    	<html:text property="setting(deletePendingRegistrationsAfter).number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<html:select property="setting(deletePendingRegistrationsAfter).field" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="field" items="${deleteMessagesExpirationUnits}">
		                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    	(<span class="fieldDecoration"><bean:message key="settings.unlimited"/></span>)
	                    </td>
	                </tr>
	            </table>
            </fieldset>
            <fieldset>
            	<legend><bean:message key="settings.local.dataDisplay"/></legend>
		        <table class="defaultTable">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.local.emailRequired"/></td>
	                    <td><html:checkbox property="setting(emailRequired)" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
	                </tr>
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.local.emailUnique"/></td>
	                    <td><html:checkbox property="setting(emailUnique)" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.memberResultDisplay"/></td>
	                    <td>
	                    	<html:select property="setting(memberResultDisplay)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="memberResultDisplay" items="${memberResultDisplays}">
		                    		<html:option value="${memberResultDisplay}"><bean:message key="settings.local.memberResultDisplay.${memberResultDisplay}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.memberSortOrder"/></td>
	                    <td>
	                    	<html:select property="setting(memberSortOrder)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="memberSortOrder" items="${memberSortOrders}">
		                    		<html:option value="${memberSortOrder}"><bean:message key="settings.local.memberSortOrder.${memberSortOrder}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.adDescriptionFormat"/></td>
	                    <td>
	                    	<html:select property="setting(adDescriptionFormat)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="format" items="${textFormats}">
		                    		<html:option value="${format}"><bean:message key="global.textFormat.${format}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.messageFormat"/></td>
	                    <td>
	                    	<html:select property="setting(messageFormat)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="format" items="${textFormats}">
		                    		<html:option value="${format}"><bean:message key="global.textFormat.${format}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.local.showCountersInAdCategories"/></td>
	                    <td><html:checkbox property="setting(showCountersInAdCategories)" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
	                </tr>
	            </table>
            </fieldset>
            <fieldset>
            	<legend><bean:message key="settings.local.csv"/></legend>
		        <table class="defaultTable">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.local.csv.useHeader"/></td>
	                    <td><html:checkbox property="setting(csvUseHeader)" value="true" styleClass="checkbox InputBoxDisabled" disabled="true"/></td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.csv.stringQuote"/></td>
	                    <td>
	                    	<html:select property="setting(csvStringQuote)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="csvStringQuote" items="${csvStringQuotes}">
		                    		<html:option value="${csvStringQuote}"><cyclos:escapeHTML><bean:message key="settings.local.csv.stringQuote.${csvStringQuote}"/></cyclos:escapeHTML></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.csv.valueSeparator"/></td>
	                    <td>
	                    	<html:select property="setting(csvValueSeparator)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="csvValueSeparator" items="${csvValueSeparators}">
		                    		<html:option value="${csvValueSeparator}"><cyclos:escapeHTML><bean:message key="settings.local.csv.valueSeparator.${csvValueSeparator}"/></cyclos:escapeHTML></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.csv.recordSeparator"/></td>
	                    <td>
	                    	<html:select property="setting(csvRecordSeparator)" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="csvRecordSeparator" items="${csvRecordSeparators}">
		                    		<html:option value="${csvRecordSeparator}"><cyclos:escapeHTML><bean:message key="settings.local.csv.recordSeparator.${csvRecordSeparator}"/></cyclos:escapeHTML></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
	                </tr>
	            </table>
            </fieldset>
            <fieldset>
            	<legend><bean:message key="settings.local.sms"/></legend>
		        <table class="defaultTable">
		        	<tr>
                   		<td class="label" width="30%"><bean:message key="settings.local.sms.enable"/></td>
                   		<td><html:checkbox property="setting(smsEnabled)" styleId="smsCheck" styleClass="checkbox InputBoxDisabled" disabled="true" value="true" /></td>
                   	</tr>		        
                  	<tr class="trSms" style="display:none">
                   		<td class="label""><bean:message key="settings.local.sms.channel"/></td>
                   		<td>
	                    	<html:select property="setting(smsChannelName)" styleClass="InputBoxDisabled" disabled="true">
		                    	<html:option value=""><bean:message key="global.no.defined"/></html:option>
		                   		<c:forEach var="channel" items="${smsChannels}">
		                    		<html:option value="${channel.internalName}">${channel.displayName}</html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
                   	</tr>                   	
                  	<tr class="trSms" style="display:none">
                   		<td class="label""><bean:message key="settings.local.sms.sendSmsWebServiceUrl"/></td>
                   		<td><html:text property="setting(sendSmsWebServiceUrl)" styleClass="full InputBoxDisabled" readonly="true"/></td>
                   	</tr>                   	
                  	<tr class="trSms" style="display:none">
                   		<td class="label""><bean:message key="settings.local.sms.customField"/></td>
                   		<td>
                   			<html:select property="setting(smsCustomFieldId)" styleClass="InputBoxDisabled" disabled="true">
		                    	<html:option value=""><bean:message key="global.no.defined"/></html:option>
		                   		<c:forEach var="field" items="${smsCustomFields}">
		                    		<html:option value="${field.id}">${field.name}</html:option>
		                   		</c:forEach>
                   			</html:select>
						</td>
                   	</tr>                   	
	            </table>
            </fieldset>
            <fieldset>
            	<legend><bean:message key="settings.local.transactionNumber"/></legend>
		        <table class="defaultTable">
					<tr>
                   		<td class="label" width="30%"><bean:message key="settings.local.transactionNumber.enable"/></td>
                   		<td><html:checkbox property="setting(transactionNumber).enabled" styleId="transactionNumberCheck" styleClass="checkbox InputBoxDisabled" disabled="true" value="true" /></td>
                   	</tr>
                  	<tr class="trTransactionNumber" style="display:none">
                   		<td class="label"><bean:message key="settings.local.transactionNumber.prefix"/></td>
                   		<td><html:text property="setting(transactionNumber).prefix" styleClass="medium InputBoxDisabled" readonly="true"/></td>
                   	</tr>
                  	<tr class="trTransactionNumber" style="display:none">
                   		<td class="label"><bean:message key="settings.local.transactionNumber.padLength"/></td>
                   		<td><html:text maxlength="2" property="setting(transactionNumber).padLength" styleClass="number tiny InputBoxDisabled" readonly="true"/></td>
                   	</tr>
                  	<tr class="trTransactionNumber" style="display:none">
                   		<td class="label"><bean:message key="settings.local.transactionNumber.suffix"/></td>
                   		<td><html:text property="setting(transactionNumber).suffix" styleClass="medium InputBoxDisabled" readonly="true"/></td>
                   	</tr>
	            </table>
            </fieldset>
            <fieldset>
            	<legend><bean:message key="settings.local.chargebacks"/></legend>
		        <table class="defaultTable">
					<tr>
                   		<td class="label" width="30%"><bean:message key="settings.local.maxChargebackTime"/></td>
                   		<td>
	                    	<html:text property="setting(maxChargebackTime).number" styleClass="tiny number InputBoxDisabled" readonly="true"/>
	                    	<html:select property="setting(maxChargebackTime).field" styleClass="InputBoxDisabled" disabled="true">
		                   		<c:forEach var="field" items="${maxChargebackTimeUnits}">
		                    		<html:option value="${field}"><bean:message key="global.timePeriod.${field}"/></html:option>
		                   		</c:forEach>
	                    	</html:select>
	                    </td>
                   	</tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.chargebackDescription"/></td>
	                    <td><html:textarea property="setting(chargebackDescription)" styleClass="full InputBoxDisabled" readonly="true" rows="3"/></td>
	                </tr>
	            </table>
            </fieldset>
            <fieldset>
            	<legend><bean:message key="settings.local.scheduledTasks"/></legend>
		        <table class="defaultTable">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.local.schedulingHour"/></td>
	                    <td><html:text property="setting(schedulingHour)" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
	                </tr>
	                <tr>
	                    <td class="label"><bean:message key="settings.local.schedulingMinute"/></td>
	                    <td><html:text property="setting(schedulingMinute)" styleClass="tiny number InputBoxDisabled" readonly="true"/></td>
	                </tr>
	            </table>
            </fieldset>
            <fieldset>
            	<legend><bean:message key="settings.local.extra"/></legend>
		        <table class="defaultTable">
	                <tr>
	                    <td class="label" width="30%"><bean:message key="settings.local.transferListenerClass"/></td>
	                    <td><html:text property="setting(transferListenerClass)" styleClass="full InputBoxDisabled" readonly="true"/></td>
	                </tr>
	            </table>
            </fieldset>
			<c:if test="${cyclos:granted(AdminSystemPermission.SETTINGS_MANAGE_LOCAL)}">
				<table class="defaultTable">
					<tr>
						<td colspan="2" align="right">
							<input type="button" id="modifyButton" class="button" value="<bean:message key="global.change"/>">
							&nbsp;
							<input type="submit" id="saveButton" disabled="disabled" class="ButtonDisabled" value="<bean:message key="global.submit"/>">
						</td>
					</tr>
	            </table>
			</c:if>   
		</td>            
    </tr>
</table>
</ssl:form>
