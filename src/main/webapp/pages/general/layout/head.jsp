<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="nl.strohalm.cyclos.services.settings.SettingsService"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="nl.strohalm.cyclos.entities.settings.AccessSettings"%>
<%@ page import="nl.strohalm.cyclos.entities.settings.LocalSettings" %>
<%@ page import="nl.strohalm.cyclos.utils.MessageHelper" %>
<%@ page import="nl.strohalm.cyclos.utils.SpringHelper" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://devel.cyclos.org/tlibs/cyclos-core" prefix="cyclos" %>

<head>
<%-- When there is a containerUrl set, ensure the page is running in a frame, or redirect to the container --%>
<c:if test="${not empty sessionScope.containerUrl}">
	<script>
		if (self === top) {
			top.location = "<cyclos:escapeJS>${containerUrl}</cyclos:escapeJS>";
		}
	</script>
	<c:remove var="containerUrl" scope="session"/>
</c:if>


<title>${localSettings.applicationName}</title>
<cyclos:customizedFilePath type="style" name="style.css" var="styleUrl" groupId="${empty loggedUserId ? cookie.groupId.value : ''}" groupFilterId="${empty loggedUserId ? cookie.groupFilterId.value : ''}" />
<link rel="stylesheet" href="<c:url value="${styleUrl}" />">

<link rel="shortcut icon" href="<c:url value="/systemImage?image=icon"/>">

<script language="javascript">
	//Common variables
	var context = "${pageContext.request.contextPath}";
	var pathPrefix = context + "${pathPrefix}";
	var isAdmin = ${empty isAdmin ? "false" : isAdmin};
	var isBroker = ${empty isBroker ? "false" : isBroker};
	var isMember = ${empty isMember ? "false" : isMember};
	var isOperator = ${empty isOperator ? "false" : isOperator};
	var ckLanguage = "${localSettings.language.alternate}";
	var accountNumberLength = ${cyclos:name(accessSettings.usernameGeneration) != 'NONE' ? accessSettings.generatedUsernameLength : 0};

	//Resource bundle messages
	var defaultMessageText = "<cyclos:escapeJS><bean:message key="global.loading"/></cyclos:escapeJS>";
	var maxUploadSize = "${localSettings.maxUploadSize} ${localSettings.maxUploadUnits.display}";
	var uploadLimitText = "<cyclos:escapeJS><bean:message key="global.uploadLimit"/></cyclos:escapeJS>";
	var helpTooltip = "<cyclos:escapeJS><bean:message key="global.tooltip.help"/></cyclos:escapeJS>";
	var removeTooltip = "<cyclos:escapeJS><bean:message key="global.tooltip.remove"/></cyclos:escapeJS>";
	var editTooltip = "<cyclos:escapeJS><bean:message key="global.tooltip.edit"/></cyclos:escapeJS>";
	var viewTooltip = "<cyclos:escapeJS><bean:message key="global.tooltip.view"/></cyclos:escapeJS>";
	var previewTooltip = "<cyclos:escapeJS><bean:message key="global.tooltip.preview"/></cyclos:escapeJS>";
	var permissionsTooltip = "<cyclos:escapeJS><bean:message key="global.tooltip.permissions"/></cyclos:escapeJS>";
	var exportCSVTooltip = "<cyclos:escapeJS><bean:message key="global.tooltip.exportCSV"/></cyclos:escapeJS>";
	var printTooltip = "<cyclos:escapeJS><bean:message key="global.tooltip.print"/></cyclos:escapeJS>";
	var modifyLabel = "<cyclos:escapeJS><bean:message key="global.change"/></cyclos:escapeJS>";
	var cancelLabel = "<cyclos:escapeJS><bean:message key="global.cancel"/></cyclos:escapeJS>";
	var resultsTitle = "<cyclos:escapeJS><bean:message key="global.title.results"/></cyclos:escapeJS>";
	var imageRemoveMessage = "<cyclos:escapeJS><bean:message key="image.removeConfirmation"/></cyclos:escapeJS>";
	var imageDetailsSuccess = "<cyclos:escapeJS><bean:message key="image.details.success"/></cyclos:escapeJS>";
	var imageDetailsError = "<cyclos:escapeJS><bean:message key="image.details.error"/></cyclos:escapeJS>";
	var imageRemovedMessage = "<cyclos:escapeJS><bean:message key="image.removed"/></cyclos:escapeJS>";
	var errorRemovingImageMessage = "<cyclos:escapeJS><bean:message key="image.error.removing"/></cyclos:escapeJS>";
	var noPictureCaption = "<cyclos:escapeJS><bean:message key="image.noPicture.caption"/></cyclos:escapeJS>";
	var helpStatusPrefix = "<cyclos:escapeJS><bean:message key="global.tooltip.help"/></cyclos:escapeJS>";
	<c:if test="${not empty cookie.receiptPrinterId}">
		var receiptPrinterAppletError = "<cyclos:escapeJS><bean:message key="receiptPrinter.error.applet" /></cyclos:escapeJS>";
		var receiptPrinterNotFoundError = "<cyclos:escapeJS><bean:message key="receiptPrinter.error.printerNotFoundError" arg0="#printer#" /></cyclos:escapeJS>";
		var receiptPrinterNoConfigurationError = "<cyclos:escapeJS><bean:message key="receiptPrinter.error.noConfiguration" /></cyclos:escapeJS>";
	</c:if>

	// When not using gecko-based browsers, we use Ext's DOM query
	var ua = navigator.userAgent.toLowerCase();
	var isSafari = (/webkit|khtml/).test(ua);
	var isGecko = !isSafari && ua.indexOf("gecko") > -1;
</script>

<!-- Include the Prototype library (http://www.prototypejs.org) -->
<cyclos:script src="/pages/scripts/prototype.js" />
<!-- Include the Scriptaculous library (http://script.aculo.us) -->
<cyclos:script src="/pages/scripts/effects.js" />
<cyclos:script src="/pages/scripts/dragdrop.js" />
<cyclos:script src="/pages/scripts/controls.js" />
<!-- Include the Behaviour library (http://www.bennolan.com/behaviour) -->
<cyclos:script src="/pages/scripts/behaviour.js" />
<!-- Include the JavaScripTools library (http://javascriptools.sourceforge.net) -->
<cyclos:script src="/pages/scripts/JavaScriptUtil.js" />
<cyclos:script src="/pages/scripts/Parsers.js" />
<cyclos:script src="/pages/scripts/InputMask.js" />
<!-- Include the custom Cyclos script files -->
<cyclos:script src="/pages/scripts/multiDropDown.js" />
<cyclos:script src="/pages/scripts/library.js" />
<!-- Include the javascript calendar (http://www.dynarch.com/projects/calendar) -->
<cyclos:script src="/pages/scripts/calendar.js" />
<cyclos:script src="/pages/scripts/calendar-setup.js" />

<%--
<cyclos:script src="/pages/scripts/all_compressed.js" />
--%>

<!-- Include the CKEditor script files (http://ckeditor.com/) -->
<cyclos:script src="/pages/scripts/ckeditor/ckeditor.js" />

<!-- Include the calendar language and theme -->
<cyclos:script src="/pages/scripts/calendar/lang/calendar-${localSettings.language.alternate}.js" />
<link rel="stylesheet" href="<c:url value="/pages/scripts/calendar/calendar.css" />" />

<script language="javascript">
	function showMessage() {
		<c:if test="${not empty messageKey}">
			<cyclos:escapeJS var="message"><bean:message key="${messageKey}" arg0="${messageArguments[0]}" arg1="${messageArguments[1]}" arg2="${messageArguments[2]}" arg3="${messageArguments[3]}" arg4="${messageArguments[4]}"/></cyclos:escapeJS>
			if(booleanValue(readCookie("showMessage"))) {
				alert('${message}');
			}
			deleteCookie("showMessage");
			<c:remove var="messageKey"/>
			<c:remove var="messageArguments"/> 
		</c:if>
	}
	
	//Set up the date mask default validation message
	JST_DEFAULT_DATE_MASK_VALIDATION_MESSAGE = "<cyclos:escapeJS><bean:message key="errors.dateFormat" arg0="${fn:toLowerCase(localSettings.datePattern.value)}"/></cyclos:escapeJS>";
	JST_DEFAULT_LEFT_TO_RIGHT = ${cyclos:name(localSettings.decimalInputMethod) == 'LTR'};
	
	//Create the parsers and formatting related objects
	var dateParser = new DateParser("${localSettings.datePattern.value}");
	var dateTimeParser = new DateParser("${localSettings.datePattern.value} ${localSettings.timePattern.value}".replace("a", "A"), false);
	var calendarDateFormat = "${localSettings.datePattern.calendarFormat}";
	var calendarDateTimeFormat = "${localSettings.datePattern.calendarFormat} ${localSettings.timePattern.calendarFormat}";
	var datePattern = "${datePattern}";
	var dateTimePattern = datePattern + " ${timePattern}";
	var prec = ${localSettings.precision.value};
	var highPrec = ${localSettings.highPrecision.value};
	var dsep = "${localSettings.decimalSymbols.decimalSeparator}";
	var ksep = "${localSettings.decimalSymbols.groupingSeparator}";
	var numberParser = new NumberParser(prec, dsep, ksep, true);
	var highPrecisionParser = new NumberParser(highPrec, dsep, ksep, true);
	var richEditorsToInitialize = []; 
	
	//Default Ajax responders
	function alertAjaxError(req, e) {
		alert("<cyclos:escapeJS><bean:message key="error.ajax"/></cyclos:escapeJS>");
	}
	
	Ajax.Responders.register({
		onException: alertAjaxError,
		onCreate: function() {
			if (!is.ie) {
				showMessageDiv();
			}
		},
		onComplete: function(req) {
			if (req.transport.status == 401) {
				//alert("<cyclos:escapeJS><bean:message key="error.session.timeout"/></cyclos:escapeJS>\n\n");
				window.location.reload();				
			} else if (Ajax.activeRequestCount == 0 && !is.ie){
				hideMessageDiv();
			}
		}
	});
	
</script>

<cyclos:script src="/pages/general/layout/head.js" />
</head>
