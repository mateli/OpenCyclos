//Initialize the document's multi drop down collection
document.multiDropDowns = [];

//Do not use any pad functions for year - Forces the user to type 4 digits on year
JST_DEFAULT_DATE_MASK_YEAR_PAD_FUNCTION = null;

//Add a method to Hash to build a plain string
Hash.toPlainString = function() {
    return this.map(function(pair) {
        var value = ensureArray(pair[1])
        return arrayToParams(value, pair[0]);
    }).join('&');
}

//Add the F1..F12 key codes to the Event object
Object.extend(Event, {
  KEY_F1: 112,
  KEY_F2: 113,
  KEY_F3: 114,
  KEY_F4: 115,
  KEY_F5: 116,
  KEY_F6: 117,
  KEY_F7: 118,
  KEY_F8: 119,
  KEY_F9: 120,
  KEY_F10: 121,
  KEY_F11: 122,
  KEY_F12: 123
});

/** Key binding */
function handleKeyBindings(event) {
		event = event || window.event;
        var keyCode = typedCode(event);
        for (var selector in registeredKeyBindings) {
                if (selector == keyCode) {
                        registeredKeyBindings[selector]();
                        Event.stop(event);
                        break;
                }
        }
}
var registeredKeyBindings = {};
var keyBindingsApplied = false;
function keyBinding(code, handler) {
        registeredKeyBindings[code] = handler;
        if (!keyBindingsApplied) {
            Event.observe(document.body, "keydown", handleKeyBindings);
            keyBindingsApplied = true;
        }
}

/**
 * All returned JSON values are wrapped under the 'result' property  
 */
Ajax.Response.prototype._getResponseJSONOriginal = Ajax.Response.prototype._getResponseJSON;
Ajax.Response.prototype._getResponseJSON = function() {
    var json = this._getResponseJSONOriginal(arguments);
    return json == null ? null : json.result;
}

function Browser() {
	/*---Deze class is een verzameling van gebruikersinstellingen zoals browser, resolutie ed.
	**---By Suppa 10 oct. 2002
	**---CopyRight 2002 by SuppaNet.Com
	*/
	var agent = navigator.userAgent.toLowerCase();
	this.browser = navigator.appName.toLowerCase();
	this.version = parseInt(navigator.appVersion);
	
	this.ns  = (agent.indexOf("mozilla") != -1) && (agent.indexOf("spoofer")==-1) && (agent.indexOf("compatible") == -1);
	this.ie = (agent.indexOf("msie") != -1);
	this.konqueror = (agent.indexOf("konqueror") != -1);
	
	this.layers = (document.layers != null);
	this.all = (document.all != null);
	this.dom = (document.getElementById != null);
	
	this.ie5 = (agent.indexOf("msie 5") != -1);
	this.ie55 = (agent.indexOf("msie 5.5") != -1);
	
	this.ie6 = (agent.indexOf("msie 6") != -1);
	this.ie7 = (agent.indexOf("msie 7") != -1);
	this.ie8 = (agent.indexOf("msie 8") != -1);
	this.ie9 = (agent.indexOf("msie 9") != -1);
	if (this.ie) {
	    this.ieVersion = /msie (\d+)/.exec(agent)[1];
	}
	
	this.ns5 = (agent.indexOf("netscape5") != -1); //does this browser exists?? I have never seen it...
	this.ns6 = (agent.indexOf("netscape6") != -1);
	
	this.mozilla = (agent.indexOf("mozilla") != -1) && (agent.indexOf("msie") == -1) && (agent.indexOf("netscape") == -1) && !this.layers;
	this.opera = (agent.indexOf("opera") != -1);
	this.webkit = (agent.indexOf("webkit") != -1);
	
	this.lowResolution = (screen.width <= 800);
	
	this.windows = ((agent.indexOf("win") != -1) || (agent.indexOf("16bit") != -1));
}

var is = new Browser();
if (is.ie) {
    $(document.documentElement).addClassName('ie');
    $(document.documentElement).addClassName('ie' + is.ieVersion);
}

/* Try to avoid memory leaks on closures on IE */
if (is.ie6 || is.ie7) {
    //This hack of adding a class name to the HTML element is useful when using quirks for those browsers
    $(document.documentElement).addClassName('ieold');
    
	function clearAllEventListeners() {
		var elements = document.getElementsByTagName("*");
		for (var i = 0, len = elements.length; i < len; i++) {
			clearEventHandlers(elements[i]);
		}
	}
	Event.observe(self, "unload", clearAllEventListeners);
}

/* Removes all event handlers */
function clearEventHandlers(element) {
	element.onclick = null;
	element.oldOnclick = null;
	element.onsubmit = null;
	element.onfocus = null;
	element.onmouseover = null;
	element.onmouseout = null;
	element.onload = null;
}

/* Navigates to the profile of an element */
function navigateToProfile(id, nature) {
	var path;
	var idParam;
	switch (nature) {
		case 'ADMIN':
			path = '/adminProfile';
			idParam = 'adminId';
			break;
		case 'OPERATOR':
			path = '/operatorProfile';
			idParam = 'operatorId';
			break;
		default:
			path = '/profile';
			idParam = 'memberId';
			break;
	}
	self.location = pathPrefix + path + "?" + idParam + "=" + id ;
}

/* Functions executed after page content loaded, but before all images and scripts (the normal onload) */
var onReadyListeners = [];
function addOnReadyListener(listener) {
	onReadyListeners.push(listener);
}

/* Initialization function */
var skipDoubleSubmitCheck = false;
function init() {
	initMessageDiv();
	onReadyListeners.each(function(listener) {
		listener();
	});
	Behaviour.apply();
	Event.observe(self, "load", function() {
		if (typeof(showMessage) == "function") {
			showMessage();
		}
		//Initialize the registered rich editors
		for (var i = 0; i < richEditorsToInitialize.length; i++) {
			makeRichEditor(richEditorsToInitialize[i]);
		}
	});
	
	$$("form").each(function(form) {
		form.alreadySubmitted = false;
		if (form.onsubmit) {
			// Set a flag on the form indicating whether it will be submitted and apply the double submit check
			form._original_onsubmit = form.onsubmit;
			form.onsubmit = function(event) {
				var result = form._original_onsubmit(event);
				form.willSubmit = (result != false);

				//Apply the double submit check
				if (!skipDoubleSubmitCheck && result !== false) {
					form.alreadySubmitted = true;
				}

				return result;
			}
		}
	});
}

/* Performs a synchronous request to validate the form */
function requestValidation(form, action, callback, skipDefaultActions) {
	if (form == null) {
		form = document.forms.length > 0 ? document.forms[0] : null;
	}
	if (!form) {
		var msg = "No form found";
		alert(msg);
		throw new Error(msg);
	}
	skipDefaultActions = skipDefaultActions === true;
	
	//Check if already on a submit action - avoid double submit
	if (form.alreadySubmitted) {
		return false;
	}

	//Serialize the form data	
	action = action || form.action;
	var data = ["validation=true"];
	var serialized = serializeForm(form);
	if (serialized.length > 0) {
		data.push(serialized);
	}

	//Do the request
	var xml = null;
	request = new Ajax.Request(action, {
		method: "post",
		asynchronous: false,
		postBody: data.join("&")
	});
	
	//Since the request is synchronous, we must manually hide the div
	hideMessageDiv();
	
	//Process the validation results
	xml = request.transport.responseXML;
	if (xml == null || xml.documentElement == null) {
		if (alertAjaxError) {
			alertAjaxError();
		}
		return false;
	}
	var validation = xml.documentElement;
	var status = validation.getAttribute("value");
	var returnValue;
	
	var callbackParams = null;
	if (callback) {
		callbackParams = {
			'xml': validation,
			'status': status,
			'request': request.transport
		};
	}
	
	if (status == "error") {
		var message;
		try {
			message = validation.getElementsByTagName("message").item(0).firstChild.data;
		} catch (exception) {
			message = null;
		}
		if (callbackParams != null) {
			callbackParams.message = message;
		}
		if (message != null && !skipDefaultActions) {
			alert(message);
		}

		//Set the focus on the first possible property		
		var properties = validation.getElementsByTagName("properties");
		try {
			properties = properties.item(0).firstChild.data.split(',');
		} catch (exception) {
			properties = [];
		}
		if (properties.each && form.elements) {
			properties.each(function(property) {
				var element = null;
				for (var j = 0; j < form.elements.length; j++) {
					var current = form.elements[j];
					if ((current.id && current.id.indexOf(property) >= 0) || (current.name && current.name.indexOf(property) >= 0) || (current.getAttribute("fieldName") == property)) {
						element = current;
						break;
					}
				}
				if (element != null && !element.disabled && element.focus) {
					if (!skipDefaultActions) {
						if (element.type == 'textarea') {
							focusRichEditor(element.name)
						} else {
							setFocus(element);
						}
					}
					if (callbackParams != null) {
						callbackParams.focusElement = element;
					}
					throw $break;
				}
			});
		}
		returnValue = false;
	} else if (status == "success") {
		returnValue = true;
	} else {
		alert("Unknown validation status: " + status);
		returnValue = false;
	}
	//Invoke the callback
	if (callback) {
		callbackParams.returnValue = returnValue;
		var callbackReturnValue = callback(callbackParams);
		if (typeof callbackReturnValue != 'undefined') {
			returnValue = callbackReturnValue;
		}
	}
	return returnValue;
}

function setFocus(element) {
	try {
		$(element).focus();
	} catch (e) {
	}
}

function serializeForm(form) {
	if (form == null) {
		form = document.forms.length > 0 ? document.forms[0] : null;
	}
	if (!form) {
		var msg = "No form found";
		alert(msg);
		throw new Error(msg);
	}
	if (form.toQueryString) {
		return form.toQueryString();
	}
	//Ensure that CKEditor instances are correctly updated before serializing
	if (typeof(CKEDITOR) != 'undefined' && CKEDITOR.instances) {
		for ( instance in CKEDITOR.instances ){
            CKEDITOR.instances[instance].updateElement();
		}
	}
	
	var out = [];
	var elements = form.getElementsByTagName("*");
	for (var i = 0; i < elements.length; i++) {
		var element = elements[i];
		if (typeof(element.type) == "undefined" || element.name == "" || element.disabled) {
			continue;
		}
		switch (element.type) {
			case "radio":
			case "checkbox":
				if (element.checked) {
					out.push(element.name + "=" + encodeURIComponent(element.value));
				}
				break;
			case "select-one":
				//Set the selected element to the first if none selected - Workarround for Konqueror
				if (element.selectedIndex < 0 && element.options.length > 0) {
					element.selectedIndex = 0;
				}
				if (element.selectedIndex >= 0) {
					out.push(element.name + "=" + encodeURIComponent(element.options[element.selectedIndex].value));
				}
				break;
			case "select-many":
				for (var j = 0; j < element.options.length; j++) {
					var option = element.options[j];
					if (option.selected) {
						out.push(element.name + "=" + encodeURIComponent(option.value));
					}
				}
			case "file":
				//File is not serialized - we can't read the file contents from JavaScript!!!
				break;
			default:
				out.push(element.name + "=" + encodeURIComponent(element.value));
		}
	}
	return out.join("&");
}

var calendarCount = 0;

var onCalendarUpdate = function(calendar) {
	var toEval = calendar.params.inputField.getAttribute('onCalendarUpdate');
	if (toEval != null) {
		eval(toEval);
	}
};

function addCalendarButton(input) {
	input = $(input);
	var useTime = Element.hasClassName(input, 'dateTime') || Element.hasClassName(input, 'dateTimeNoLabel');
	var buttonId = "calendarTrigger" + calendarCount++;
	var button = document.createElement("img");
	button.id = buttonId;
	button.border = '0';
	button.src = context + "/pages/images/calendar.gif";
	button.setAttribute("style", "margin-left:2px;")
	button = input.parentNode.insertBefore(button, input.nextSibling);
	
	setPointer(button);

	var initialDate = new Date();
	initialDate.setMilliseconds(0);
	initialDate.setSeconds(0);
	Calendar.setup({
		ifFormat: useTime ? calendarDateTimeFormat : calendarDateFormat,
		inputField: elementId(input),
		button: buttonId,
		date: initialDate,
		weekNumbers: false,
		showOthers: true,
		electric: false,
		showsTime: useTime,
		timeFormat: dateTimeParser.mask.indexOf("h") >= 0 ? "12" : "24",
		onUpdate : onCalendarUpdate
	});

	if (!useTime) {
		input.style.width = "86px";
	}
}

function changeClassOnHover(element, className, hoverClassName, stopEvents) {
	element = $(element);
	Event.observe(element, "mouseover", function(e) { 
		try {
			addRemoveClassName(element, hoverClassName, className);
		} catch (ex) {
		}
		if (stopEvents) {
			Event.stop(e);
		}
	});
	Event.observe(element, "mouseout", function(e) {
		try {
			addRemoveClassName(element, className, hoverClassName);
		} catch (ex) {
		}
		if (stopEvents) {
			Event.stop(e);
		}
	});
}

function addClassOnHover(element, className) {
	Event.observe(element, "mouseover", function() { 
		try {
			Element.addClassName(element, className); 
		} catch (e) {}
	});
	Event.observe(element, "mouseout", function() {
		try {
			Element.removeClassName(element, className); 
		} catch (e) {}
	});
}

var isLeftMenu = true;
var currentlyOpenedMenu = null;
function restoreMenu() {
	allMenus.each(function(menu) {
		var openMenu = readCookie("openmenu");
		var sub = getSubMenuContainer(menu);
		if (sub) {
			if (menu.id == openMenu) {
				currentlyOpenedMenu = menu.id;
				sub.show();
			} else {
				sub.hide();
			}
		}
	});
}

function getSubMenuContainer(menu) {
	var container = menu.subMenuContainer; 
	if (!container) {
		try {
			var id = menu.id;
			id = replaceAll(id, "menu", "subMenuContainer");
			container = $(id);
		} catch (e) {
			container = null;
		}
	}
	return container;
}

function toggleSubMenu(menu, effect) {
	menu = $(menu);
	if (currentlyOpenedMenu == menu.id) {
		closeSubMenu(menu, effect);
	} else {
		$$(".menu").each(function(m) {
			closeSubMenu(m, effect);
		});
		openSubMenu(menu, effect);
	}
}

function openSubMenu(menu, effect) {
	menu = $(menu);
	container = getSubMenuContainer(menu);
	if (container != null && !container.visible()) {
		if (!isLeftMenu) {
			Position.clone(menu, container, {offsetTop: menu.getHeight() + (is.ie ? 1 : 0), offsetLeft: is.ie6 || is.ie7 ? 0 : -1, setWidth:false, setHeight:false});
		}
		if (effect) {
			new Effect.BlindDown(container, {duration:0.1});
		} else {
			container.show();
		}
		var containerWidth = container.getWidth();
		container.immediateDescendants().each(function(el) {
			el.style.width = containerWidth + "px";
		});
	}
	currentlyOpenedMenu = menu.id;
	writeCookie("openmenu", menu.id, document, null, context);
}

function closeSubMenu(menu, effect) {
	menu = $(menu);
	var closingCurrent = menu.id == currentlyOpenedMenu;
	var container = getSubMenuContainer(menu);
	if (container != null && container.visible()) {
		if (effect) {
			new Effect.BlindUp(container, {duration:0.1});
		} else {
			container.hide();
		}
	}
	//Close the currently open item
	if (closingCurrent) {
		currentlyOpenedMenu = null;
		//Clear possibly visible previous cookies
		while (document.cookie.indexOf("openMenu") >= 0) {
			deleteCookie("openmenu");
		}
	}
}

var currentHelpWindow = null;
function showHelp(page, width, height, features, left, top) {
	try {
		currentHelpWindow.close();
	} catch (e) {}
	var defaultLeft = 20;
	var defaultTop = 20;
	var url = context + "/do/help?page=" + page;
	var name = "help" + (new Date().getTime());
	features = (features ? "," + features : "") + getLocation(width, height, left || defaultLeft, top || defaultTop);
	try {
		currentHelpWindow = window.open(url, name, "scrollbars=yes,resizable=yes,width=" + width + ",height=" + height + features);
	} catch (e) {
		currentHelpWindow = null;
	}
}

var currentPrintWindow = null;
function printResults(form, url, width, height) {
	try {
		currentPrintWindow.close();
	} catch (e) {}
	width = width || Math.min(screen.width, 800) - 60;
	height = height || Math.min(screen.width, 600) - 40;
	var name = "print" + (new Date().getTime());
	try {
		currentPrintWindow = window.open(form == null ? url : "", name, "scrollbars=yes,resizable=yes,width=" + width + ",height=" + height + getLocation(width, height, 10, 10));
	} catch (e) {
		currentPrintWindow = null;
	}
	if (currentPrintWindow != null && form != null) {
		submitTo(form, url, name);
	}
	return currentPrintWindow;
}

var currentImageWindow = null;
function showImage(id, showThumbnails) {
	showThumbnails = typeof(showThumbnails) == "boolean" ? showThumbnails : false;
	try {
		currentImageWindow.close();
	} catch (e) {}
	var width = 210;
	var height = 100;
	var name = "image" + (new Date().getTime());
	try {
		currentImageWindow = window.open(pathPrefix + "/showImage?id=" + id + "&showThumbnails=" + showThumbnails, name, "scrollbars=yes,resizable=yes,width=" + width + ",height=" + height + getLocation(width, height, 10, 10));
	} catch (e) {
		currentImageWindow = null;
	}
	return currentImageWindow;
}

function submitTo(form, url, target) {
	var oldTarget = form.target;
	var oldAction = form.action;
	try {
		if (target) {
			form.target = target;
		}
		form.action = url;
		form.submit();
	} finally {
		form.target = oldTarget;
		form.action = oldAction;
		//Remove the double submit check
		form.alreadySubmitted = false;
	}
}
 
function getLocation(winWidth, winHeight, winLeft, winTop){
	var winLocation = ""
	if (winLeft < 0) winLeft = screen.width - winWidth + winLeft;
	if (winTop < 0) winTop = screen.height - winHeight + winTop;
	if (winTop == "cen") winTop = (screen.height - winHeight) / 2 - 20;
	if (winLeft == "cen") winLeft = (screen.width - winWidth) / 2;
	if (winLeft > 0 & winTop > 0) {
		winLocation = ",screenX=" + winLeft + ",left=" + winLeft	
					+ ",screenY=" + winTop  + ",top=" + winTop;
	} else {
		winLocation = "";
	}
	return winLocation;
}

// Set the element cursor style to a pointer - ie6 is not standard compilant
function setPointer(element) {
	$(element).style.cursor = is.ie6 ? "hand" : "pointer";
}

var originalGetValue = getValue;
var getValue = function(object) {
    // Test a MultiDropDown instance
    if (isInstance(object, MultiDropDown)) {
        return object.getValue();
    }

    // Test a MultiDropDown name
    if (isInstance(object, String) && document.multiDropDowns[object] != null) {
        return document.multiDropDowns[object].getValue();
    }
    
    return originalGetValue(object);
}

// to disable a form field
function disableField(field, forceDisabled, initialState) {

	if (!field) {
		return;
	}

	// Test a MultiDropDown instance
	if (field instanceof MultiDropDown) {
		field.disable();
		return;
	}

	// Test a MultiDropDown name
	if (isInstance(field, String) && document.multiDropDowns[field] != null) {
		document.multiDropDowns[field].disable();
		return;
	}

	field = $(field);
	if (!field || field.hasClassName("keepEnabled")) {
		return;
	}
	var addClassName = null;
	var removeClassName = null;	
	switch (field.type) {
		case 'text':
		case 'password':
		case 'file':
		case 'textarea':
		case 'select-multiple':
		case 'select-one':
		case 'radio':
		case 'checkbox':
			addClassName = 'InputBoxDisabled';
			removeClassName = 'InputBoxEnabled';
			if (field.type == 'textarea' && Element.hasClassName(field, 'richEditor')) {
				var fieldId = field.getAttribute("fieldId") || field.name;
				var envelope = $('envelopeOfField_' + fieldId);
				var text = $('textOfField_' + fieldId);
				if (envelope) envelope.hide();
				if (text) text.show();
				Element.removeClassName(field, 'richEditor');
				Element.addClassName(field, 'richEditorDisabled');
			}
			if (initialState && field.type.indexOf('select') >= 0) {
			    if (typeof field.initialOptions != 'undefined') {
			        setOptions(field, field.initialOptions);
			    }
			    if (typeof field.initialOptionId != 'undefined') {
			        setValue(field, field.initialOptionId);
			    }
			}
			break;
		case 'button':
		case 'submit':
			addClassName = 'ButtonDisabled';
			removeClassName = 'button';
			break;
	}
	addRemoveClassName(field, addClassName, removeClassName);
	if (['text', 'password', 'textarea'].include(field.type)) {
		field.readOnly = true;
		if (forceDisabled) {
			field.disabled = true;
		}
	} else {
		field.disabled = true;
	}

	//Handle text format radios (generated by RichTextArea tag)	
	if (field.type == 'radio' && Element.hasClassName(field, 'textFormatRadio') && booleanValue(field.value)) {
		if (field.fieldText) {
			field.fieldText.show();
		}
		if (field.fieldEnvelope) {
			field.fieldEnvelope.hide();
		}
	}
}

// Adds a classname and remove another
function addRemoveClassName(element, addClassName, removeClassName) {
	var className = element.className;
	if (removeClassName) {
		className = trim(replaceAll(className, removeClassName, ""));
	}
	if (addClassName) {
		if (className.indexOf(addClassName) < 0) {
			className = trim(className) + " " + addClassName;
		}
	}
	element.className = className;
}

// to enable a form field
function enableField(field) {

	if (!field) {
		return;
	}
	
	// Test a MultiDropDown instance
	if (field instanceof MultiDropDown) {
		field.enable();
		return;
	}

	// Test a MultiDropDown name
	if (isInstance(field, String) && document.multiDropDowns[field] != null) {
		document.multiDropDowns[field].enable();
		return;
	}

	field = $(field);
	if (!field || field.hasClassName("readonly")) { //Ensure readonly fields are never enabled
		return;
	}
	var removeClassName = null;
	var addClassName = null;
	switch (field.type) {
		case 'text':
		case 'password':
		case 'file':
		case 'textarea':
		case 'select-multiple':
		case 'select-one':
			addClassName = 'InputBoxEnabled';
			removeClassName = 'InputBoxDisabled';
			if (field.type == 'textarea' && Element.hasClassName(field, 'richEditorDisabled')) {
				var fieldId = ifEmpty(field.getAttribute("fieldId"), field.name);
				var makeRich = true;
				try {
					if ($('textFormatRadio_' + fieldId + '_plain').checked) {
						makeRich = false;
					}
				} catch (e) {}
				if (makeRich) {
					$('textOfField_' + fieldId).hide();
					$('textOfField_' + fieldId).preventAutoSize = true;
					$('envelopeOfField_' + fieldId).show();
					makeRichEditor(field);
					Element.removeClassName(field, 'richEditorDisabled');
					Element.addClassName(field, 'richEditor');
				}
			}
			break;
		case 'button':
		case 'submit':
			addClassName = 'button';
			removeClassName = 'ButtonDisabled';
			break;
	}
	addRemoveClassName(field, addClassName, removeClassName);
	field.readOnly = false;
	field.disabled = false;
	
	//Handle text format radios (generated by RichTextArea tag)	
	if (field.type == 'radio' && Element.hasClassName(field, 'textFormatRadio') && field.checked) {
		field.onclick();
	}
}

var modifyButtonName = "modifyButton";
var saveButtonName = "saveButton";
var backButtonName = "backButton"

/* Enable the form fields and remove the change button */
function enableFormForInsert() {
	var modifyButton = $(modifyButtonName);
	if (modifyButton) {
		modifyButton.click();
		Element.remove(modifyButton);
	}
}

/* Transforms the given textarea in a rich text editor */
function makeRichEditor(textarea) {
	if (!textarea || !textarea.type || textarea.type != 'textarea' || textarea.richEditor != null) {
		try {
			return textarea.richEditor;
		} catch (e) {
			return null;
		}
	}
	var editor = CKEDITOR.replace(elementId(textarea), {
		language: ckLanguage
	});		
	editor.basePath = context + "/pages/scripts/";
	editor.config.toolbar = 'Cyclos';
	if (is.ie) {
		// Avoid memory leak in IE
		Event.observe(self, "unload", function() {
			textarea.richEditor = null;
		});
	}
	textarea.richEditor = editor;
	var form = textarea.form;
	Event.observe(form, "submit", function () {
		if (form.willSubmit) {
			textarea.value = replaceAll(textarea.value, "\n", "<br>");
		}
	});
	return editor;
}

/* Function called when the CK Editor is loaded */
var focusEditorOnComplete = [];
function CKeditor_OnComplete(editor) {
	var name = editor.name;
	if (inArray(name, focusEditorOnComplete)) {
		var editor = CKEDITOR.instances[name];
		editor.focus();
	}
}

/* Focus a rich text editor with the given name */
function focusRichEditor(name) {
	var textarea = getObject(name);
	if (textarea) {
		setFocus(textarea);
	}
	focusEditorOnComplete.push(name);
	try {
		textarea.richEditor.focus();
	} catch (e) {}
}

// Event handler for when a user clicks a modify button
var afterCancelEditing = null;
function modifyResetClick() {
	this.form.reset();
	disableFormFields.apply(this.form, this.form.keepFields);
	if (afterCancelEditing) afterCancelEditing();
}

/* Enable fields of the form */
function enableFormFields() {
	var modifyButton = $(modifyButtonName);
	var saveButton = $(saveButtonName);
	
	//The additional arguments are field names we don't wat to touch.
	var keep = $A(arguments);

	if (modifyButton) {
		//Transform the modify in a cancel button
		modifyButton.oldOnclick = modifyButton.onclick;
		modifyButton.onclick = modifyResetClick;
		modifyButton.form.keepFields = keep;
		modifyButton.value = cancelLabel;
		modifyButton = null;
	}
	if (saveButton) {
		enableField(saveButton);
		saveButton = null;
	}

	//Process each field
	processFields(this, keep, enableField);
}

/* Disable fields of the form */
function disableFormFields() {
	var modifyButton = $(modifyButtonName);
	var saveButton = $(saveButtonName);
	var backButton = $(backButtonName);

	if (modifyButton) {
		if (modifyButton.oldOnclick) {
			//Transform the cancel back to modify button
			modifyButton.onclick = modifyButton.oldOnclick;
			modifyButton.value = modifyLabel;
			modifyButton.oldOnclick = null;
		}
	}
	if (saveButton) {
		disableField(saveButton);
	}
	
	//The additional arguments are field names we don't want to touch.
	var keep = $A(arguments);
	if (modifyButton) {
		keep.push(elementId(modifyButton));
	}
	if (backButton) {
		keep.push(elementId(backButton));
	}

	//Process each field
	callback = function(field) {
	    disableField(field, false, true);
	}
	processFields(this, keep, callback);
}

// Apply the given function on each form field
function processFields(form, keep, funct) {
	//Process each field
	var elements = form.elements;
	for (var i = 0, len = elements.length; i < len; i++) {
		var field = elements[i];
		if (!field.type || field.type == "hidden") {
			continue;
		}
		var toKeep = keep.find(function(e) {
			return e === field || e == field.id || e == field.name;
		});
		if (!toKeep) {
			funct(field);
		}
		field = null;
	};
	
	//Process the multi drop downs
	for (var i = 0; i < document.multiDropDowns.length; i++) {
		var mdd = document.multiDropDowns[i];
		if (keep.indexOf(mdd.name) < 0) {
			funct(mdd);
		}
	};
}

/* Observes the keypressing and triggers the callback if the value has changed - and interactive onchange */
function observeChanges(element, callback) {

	if (callback) {
		callback = callback.bindAsEventListener(element);
	}

	var storeValue = function(event) {
		this._lastValue = this.value;
	}.bindAsEventListener(element);
	
	var checkStoredValue = function(event) {
		if (this._lastValue != null && this._lastValue != this.value) {
			this.changed = true;
			if (callback) {
				callback(event);
			}
		}
	}.bindAsEventListener(element);

	element.clearChanges = function() {
		delete this._lastValue;
		this.changed = false;
	};

	Event.observe(element, "focus", function() {
		element.changed = false;
	})	
	Event.observe(element, "keydown", storeValue);
	Event.observe(element, "mousedown", storeValue);
	Event.observe(element, "keyup", checkStoredValue);	
	Event.observe(element, "mouseup", checkStoredValue);
}

/**
 * Returns the url of the given Location object without the query string part. 
 * If the parameter is not specified, the current location is used 
 */
function urlWithoutQueryString(loc) {
	loc = loc || self.location;
	return loc.protocol + "//" + loc.host + loc.pathname
}

var enableMessageDiv = true;
var messageDiv = null;
var messageDivDimensions = {width:300, height:20};
function initMessageDiv() {
	if (!enableMessageDiv || messageDiv != null) {
		return;
	}
	var div = document.createElement("div");
	div.className = "loadingMessage";
	div.style.position = "absolute";
	div.style.display = "none";
	div.style.width = messageDivDimensions.width + "px";
	div.style.height = messageDivDimensions.height + "px";
	div.appendChild(document.createTextNode(defaultMessageText || " "));
	messageDiv = document.body.appendChild(div);
	
	if (!is.ie6) {
		messageDiv.style.position = 'fixed';
		messageDiv.style.bottom = '3px';
		messageDiv.style.right = '3px';
	}
}

/* Shows message under a div on the bottom-right corner */
function showMessageDiv(message) {
	if (!enableMessageDiv || !(message || defaultMessageText)) {
		return;
	}
	if (messageDiv == null) {
		initMessageDiv();
	}
	if (typeof(message) == "string") {
		messageDiv.innerHTML = message;
	}
	
	if (is.ie6) {
		var positionElement = document.body;
		var scrollX = document.body ? document.body.scrollLeft : window.pageXOffset;
		var scrollY = document.body ? document.body.scrollTop : window.pageYOffset;
		var width = positionElement.clientWidth ? positionElement.clientWidth : window.innerWidth;
		var height = positionElement.clientHeight ? positionElement.clientHeight : window.innerHeight;
		var x = (width + scrollX - messageDivDimensions.width - 3);
		var y = (height + scrollY - messageDivDimensions.height - 3);
		messageDiv.style.left = x + "px";
		messageDiv.style.top = y + "px";
	}
	
	
	Element.show(messageDiv);
	messageDiv.innerHTML += "&nbsp;";
}

/* Hides the loading message */
function hideMessageDiv() {
	if (messageDiv == null) {
		return;
	}
	Element.hide(messageDiv);
}

/* Returns the url from the given hash's 'url' key, or 'pathPrefix + defaultUrl' */
function getUrl(params, defaultUrl) {
    var url = null;
    if (params instanceof Hash) {
        url = params.get('url');
    }
    return url || pathPrefix + defaultUrl;
}

/* Find admins, returning them as an array to the callback */
function findAdmins(params, callback) {
	params = params || $H();
	var url = getUrl(params, "/searchAdminsAjax");
	new Ajax.Request(url, {
	    method: 'post',
		parameters: params.toPlainString ? params.toPlainString() : params,
		onSuccess: function(request, array) {
			callback(array);
		}
	});
}

/* Find members, returning them as an array to the callback */
function findMembers(params, callback) {
	params = params || $H();
	var url = getUrl(params, "/searchMembersAjax");
	new Ajax.Request(url, {
	    method: 'post',
		parameters: params.toPlainString ? params.toPlainString() : params,
		onSuccess: function(request, array) {
			callback(array);
		}
	});
}

/* Find transfer types, returning them as an array to the callback */
function findTransferTypes(params, callback) {
	params = params || $H();
	var url = getUrl(params, "/searchTransferTypesAjax");
	new Ajax.Request(url, {
	    method: 'post',
		parameters: params.toPlainString ? params.toPlainString() : params,
		onSuccess: function(request, array) {
			callback(array);
		}
	});
}

/* Find account types, returning them as an array to the callback */
function findAccountTypes(params, callback) {
	params = params || $H();
	var url = getUrl(params, "/searchAccountTypesAjax");
	new Ajax.Request(url, {
	    method: 'post',
		parameters: params.toPlainString ? params.toPlainString() : params,
		onSuccess: function(request, array) {
			callback(array);
		}
	});
}

/* Find groups, returning them as an array to the callback */
function findGroups(params, callback) {
	params = params || $H();
	var url = getUrl(params, "/searchGroupsAjax");
	new Ajax.Request(url, {
	    method: 'post',
		parameters: params.toPlainString ? params.toPlainString() : params,
		onSuccess: function(request, array) {
			callback(array);
		}
	});
}

/* Find message categories, returning them as an array to the callback */
function findMessageCategories(params, callback) {
	params = params || $H();
	var url = getUrl(params, "/searchMessageCategoriesAjax");
	new Ajax.Request(url, {
	    method: 'post',
		parameters: params.toPlainString ? params.toPlainString() : params,
		onSuccess: function(request, array) {
			callback(array);
		}
	});
}


/* Find payment filters, returning them as an array to the callback */
function findPaymentFilters(params, callback) {
	params = params || $H();
	var url = getUrl(params, "/searchPaymentFiltersAjax");
	new Ajax.Request(url, {
	    method: 'post',
		parameters: params.toPlainString ? params.toPlainString() : params,
		onSuccess: function(request, array) {
			callback(array);
		}
	});
}

function findDirectoryContents(params, callback) {
	params = params || $H();
	var url = getUrl(params, "/getDirectoryContentsAjax");
	new Ajax.Request(url, {
	    method: 'post',
		parameters: params.toPlainString ? params.toPlainString() : params,
		onSuccess: function(request, array) {
			callback(array);
		},
		onError: function() {
			alert ("Error getting directory contents");
		},
		onFailure: function() {
			alert ("Error getting directory contents");
		}
	});
}


/* Admin autocompleter class */
Autocompleter.Admin = Class.create();
Object.extend(Object.extend(Autocompleter.Admin.prototype, Autocompleter.Base.prototype), {

	initialize: function(element, update, options) {
		this.baseInitialize(element, update, options);
		this.options.autoSelect = true;
		Event.observe(window, "unload", purge.bind(self, this.options));
		Event.observe(window, "unload", purge.bind(self, this));
	},

	getUpdatedChoices: function() {
		params = $H();
		params.set(this.options.paramName, this.getToken());
		findAdmins(params, this.updateAdmins.bind(this));
	},

	updateAdmins: function(admins) {
		admins = $A(admins);
		this.options.admins = admins;
		var sb = new StringBuffer(5 * admins.length + 2);
		sb.append("<ul>");
		for (var i = 0; i < admins.length; i++) {
			var admin = admins[i];
			sb.append("<li index='").append(i).append("'>").append(admin.name).append(" (").append(admin.username).append(")</li>");
		}
		sb.append("</ul>");
		this.updateChoices(sb.toString());
	}
});

/* Member autocompleter class */
Autocompleter.Member = Class.create();
Object.extend(Object.extend(Autocompleter.Member.prototype, Autocompleter.Base.prototype), {

	initialize: function(element, update, options) {
		this.baseInitialize(element, update, options);
		this.options.autoSelect = true;
		Event.observe(window, "unload", purge.bind(self, this.options));
		Event.observe(window, "unload", purge.bind(self, this));
	},

	getUpdatedChoices: function() {
		if (this.element.skipSearch) {
			this.element.skipSearch = null;
			return;
		}
		params = $H();
		if (this.options.brokers) {
			params.set("brokers", true);
		}
		if (this.options.brokered) {
			params.set("brokered", true);
		}
		if (this.options.maxScheduledPayments) {
			params.set("maxScheduledPayments", true);
		}
		if (this.options.enabled) {
			params.set("enabled", true);
		}
		if (this.options.exclude) {
			params.set("exclude", this.options.exclude);
		}
		if (this.options.groupIds) {
			params.set("groupIds", this.options.groupIds);
		}
		if(this.options.viewableGroup) {
			params.set("viewableGroup", this.options.viewableGroup);
		}
		params.set(this.options.paramName, this.getToken());
		findMembers(params, this.updateMembers.bind(this));
	},

	updateMembers: function(members) {
		members = $A(members);
		this.options.members = members;
		var sb = new StringBuffer(5 * members.length + 2);
		sb.append("<ul>");
		for (var i = 0; i < members.length; i++) {
			var member = members[i];
			sb.append("<li index='").append(i).append("'>").append(member.name).append(" (").append(member.username).append(")</li>");
		}
		sb.append("</ul>");
		this.updateChoices(sb.toString());
	}
});


/* Prepare an input to be a admin auto-completer */
function prepareForAdminAutocomplete(input, div, options, idControl, usernameControl, nameControl, focusControlAfterSelect, afterSelect) {
	
	input = $(input);
	div = $(div);
	idControl = $(idControl);
	usernameControl = $(usernameControl);
	nameControl = $(nameControl);
	focusControlAfterSelect = $(focusControlAfterSelect);

	//We must handle string ids in order to avoid leaks in IE
	var inputId = elementId(input);
	var divId = elementId(div);
	var idControlId = elementId(idControl);
	var usernameControlId = elementId(usernameControl);
	var nameControlId = elementId(nameControl);
	var focusControlAfterSelectId = elementId(focusControlAfterSelect);
	
	div.style.width = Element.getDimensions(input).width + "px";
	
	options = Object.extend(options || {}, {
		updateElement: function(element) {
			var admin = this.admins[element.autocompleteIndex];
			var input = $(inputId);
			var toFocus = $(focusControlAfterSelectId);
			try {
				input.update(admin);
				if (toFocus && toFocus.focus) {
					toFocus.focus();
				}
				if (afterSelect) {
					afterSelect(admin);
				}
			} finally {
				input = null;
				toFocus = null;
			}
		}
	});
	
	//Start the autocomplete
	new Autocompleter.Admin(input, div, options);

	//Set the reference on the id control
	idControl.admin = null;
	if (idControl.value != '') {
		idControl.admin = {id: idControl.value, name: nameControl.value, username: usernameControl.value};
	}
		
	input.update = function(admin) {
		var idControl = $(idControlId);
		try {
			idControl.admin = admin;
			if (!admin) {
				admin = {id:"", username:"", name:""};
			}
			setValue(idControl, admin.id);
			setValue(usernameControlId, admin.username);
			setValue(nameControlId, admin.name);
		} finally {
			idControl = null;
		}
	}

	//Update the inputs before the onblur event clears the inputs!!
	Event.observe(div, "mousedown", function () {
		var input = $(inputId);
		var idControl = $(idControlId);
		try {
			input.preventClear = true;
			input.update(idControl.admin);
		} finally {
			input = null;
			idControl = null;
		}
	});
	
	//If the admin changed the value without confirming an option, clear it	
	Event.observe(input, "blur", function () {
		var input = $(inputId);
		var idControl = $(idControlId);
		try {
			if (input.preventClear) {
				input.preventClear = false;
			} else if (idControl.value == "" || trim(input.value).length == 0) {
				input.update(null);
			} else if (idControl.admin != null) {
				input.update(idControl.admin);
			}
		} finally {
			input = null;
			idControl = null;
		}
	}.bindAsEventListener(input));
	
	//Clear the references to avoid leaks in IE
	input = null;
	div = null;
	idControl = null;
	usernameControl = null;
	nameControl = null;
	focusControlAfterSelect = null;
}

/* Prepare an input to be a member auto-completer */
function prepareForMemberAutocomplete(input, div, options, idControl, usernameControl, nameControl, focusControlAfterSelect, afterSelect) {
	
	//Start the autocompleter
	var autoCompleter = new Autocompleter.Member(input, div, options);
	autoCompleter.hasFocus = true;
	autoCompleter.updateChoices("<ul></ul>");
	autoCompleter.hasFocus = false;

	input = $(input);
	div = $(div);
	idControl = $(idControl);
	usernameControl = $(usernameControl);
	nameControl = $(nameControl);
	focusControlAfterSelect = $(focusControlAfterSelect);

	//Handle custom events
	if (usernameControl.eventsAdded !== true) {
		var handleEnter = function(e) {
			var code = typedCode(e);
			switch (code) {
				case 17:
					return false;
				case Event.KEY_RETURN:
					autoCompleter.selectEntry();
					return false;
			}
		};
		
		//For generated account numbers instead of typed username, apply the mask
		if (accountNumberLength > 0 && !usernameControl.mask) {
			var mask = new NumberMask(new NumberParser(0), usernameControl, accountNumberLength, false);
			mask.keyPressFunction = handleEnter;
		} else {
			usernameControl.onkeypress = handleEnter;
		}
		nameControl.onkeypress = handleEnter;
		
		//Mark as event handlers added
		usernameControl.eventsAdded = true;
	}

	//We must handle string ids in order to avoid leaks in IE
	var inputId = elementId(input);
	var divId = elementId(div);
	var idControlId = elementId(idControl);
	var usernameControlId = elementId(usernameControl);
	var nameControlId = elementId(nameControl);
	var focusControlAfterSelectId = elementId(focusControlAfterSelect);
	
	div.style.width = Element.getDimensions(input).width + "px";
	
	options = Object.extend(options || {}, {
		updateElement: function(element) {
			if (!element) {
				return;
			}
			var member = this.members[element.autocompleteIndex];
			var input = $(inputId);
			var toFocus = $(focusControlAfterSelectId);
			try {
				input.update(member);
				if (toFocus && toFocus.focus) {
					toFocus.focus();
				}
			} finally {
				input = null;
				toFocus = null;
			}
		}
	});
	
	//Set the reference on the id control
	idControl.member = null;
	if (idControl.value != '') {
		idControl.member = {id: idControl.value, name: nameControl.value, username: usernameControl.value};
	}
		
	input.update = function(member) {
		var idControl = $(idControlId);
		try {
			setValue(usernameControlId, member == null ? '' : member.username);
			setValue(nameControlId, member == null ? '' : member.name);
			//Only update if the member has actually changed
			var memberId = member == null ? "" : member.id;
			if (idControl.value != memberId) {
				idControl.member = member;
				if (!member) {
					member = {id:"", username:"", name:""};
				}
				setValue(idControl, member.id);
				if (afterSelect) {
					afterSelect(idControl.member);
				}
			}
		} finally {
			idControl = null;
		}
	}

	//Update the inputs before the onblur event clears the inputs!!
	Event.observe(div, "mousedown", function () {
		var input = $(inputId);
		var idControl = $(idControlId);
		try {
			input.preventClear = true;
			input.update(idControl.member);
		} finally {
			input = null;
			idControl = null;
		}
	});
	
	//If the member changed the value without confirming an option, clear it	
	Event.observe(input, "blur", function () {
		var input = $(inputId);
		var idControl = $(idControlId);
		try {
			if (input.preventClear) {
				input.preventClear = false;
			} else if (idControl.value == "" || trim(input.value).length == 0) {
				if (input.member != null) {
					input.update(null);
				}
			} else if (idControl.member != null) {
				if (input.member == null || input.member.id != idControl.member.id) {
					input.update(idControl.member);
				}
			}
		} finally {
			input = null;
			idControl = null;
		}
	}.bindAsEventListener(input));
	
	//Handle special keys	
	Event.observe(input, "keyup", function(event) {
		var input = $(inputId);
		switch (typedCode(event)) {
			case Event.KEY_LEFT:
			case Event.KEY_RIGHT:
			case Event.KEY_UP:
			case Event.KEY_DOWN:
			case Event.KEY_HOME:
			case Event.KEY_END:
			case Event.KEY_PAGEUP:
			case Event.KEY_DOWN:
				input.skipSearch = true;
				break;
			case Event.KEY_BACKSPACE:
			case Event.KEY_DELETE:
				if (input.value.length == 0) {
					input.update(null);
				}
				break;
		}
	});
	
	//Clear the references to avoid leaks in IE
	input = null;
	div = null;
	idControl = null;
	usernameControl = null;
	nameControl = null;
	focusControlAfterSelect = null;
}

/* Removes all properties from an object. It is not recursive */
function purge(object) {
	if (object == null) {
		return;
	}
	for (var prop in object) {
		try {
			delete object[prop];
		} catch (e) {
			alert(debug(e));
		}
	}
	delete object;
}

/* Returns an element id, assigning one if none is found */
function elementId(element) {
	var id = null;
	if (element) {
		element = $(element);
		if (isEmpty(element.id)) {
			element.id = "_id" + new Date().getTime() + "_" + Math.floor(Math.random() * 1000);
		}
		id = element.id;
	}
	element = null;
	return id;
}

/* Return to the last stored navigation path, with the specified parameters */
function backToLastLocation(params) {
	if (params && params.toQueryString) {
		params = params.toQueryString();
	}
	self.location = context + "/do/back?currentPage=" + location.pathname + "*?" + params;
}

/* Returns the style definition for the given selector */
function getStyle(selector) {
    try {
        for (var i = 0; i < document.styleSheets.length; i++) {
            var ss = document.styleSheets[i];
            var rules = ss.rules ? ss.rules : ss.cssRules;
            for (var j = 0; j < rules.length; j++) {
            	var rule = rules[j];
            	if (!rule.selectorText) continue;
                var selectorText = rule.selectorText.split(" ").join();
                if (selectorText.indexOf(selector) == 0 || selectorText.indexOf("," + selector) > 0) {
                    return rule.style;
                }
            }
        }
    } catch (exception) {
        alert("Exception: " + exception)
    }
    return null;
}

/* Shuffle an array */
function shuffle(a) {
	var ret = [];
	var array = Array.apply(new Array(), a);
	while (array != null && array.length > 0) {
		ret.push(array.splice(Math.floor(Math.random() * array.length), 1)[0]);
	}
	return ret;
};

/** Validate a password */
function validatePassword(field, numeric, range, mustBeNumericError, mustBeAlphaNumericError, passwordTooSmallError, passwordTooLargeError) {
	try {
		field = getObject(field);
	} catch (e) {
		field = null;
	}
	if (field == null || typeof(field.value) == null) {
		alert("Invalid password field");
		return false;
	}
	var value = field.value;
	//Here, we don't validate empty fields
	if (value.length > 0) {
		if (value.length < range.min) {
			// Min length
			alert(passwordTooSmallError);
			field.focus();
			return false;
		} else if (value.length > range.max) {
			// Max length
			alert(passwordTooLargeError);
			field.focus();
			return false;
		} else {
			// Unaccepted characters
			var accepted = numeric ? JST_CHARS_NUMBERS : JST_CHARS_BASIC_ALPHA;
			if (!onlySpecified(value, accepted)) {
				alert(numeric ? mustBeNumericError : mustBeAlphaNumericError);
				field.focus();
				return false;
			}
		}
	}
	//Ok
	return true;
};

/** capitalizes a string. Example: capitalizeString("numberOfMembers") gives "NumberOfMembers". */
function capitalizeString(term){
	return term.charAt(0).toUpperCase() + term.substr(1);
}

/** Ensures an object is returned as an array */
function ensureArray(object) {
	if (!object) {
		return [];
	} else if (object instanceof Array) {
		return object;
	} else {
		return [object];
	}
}

/** Convert an array into a query string form with the given name */
function arrayToParams(array, paramName) {
	return ensureArray(array).map(function(element) {
		return paramName + "=" + element;
	}).join("&");
}

/** Used by the custom field tag, will update child values after given a parent select */
function updateCustomFieldChildValues(nature, parent, child) {
	parent = $(parent);
	child = $(child);
	if (!parent || !child) {
		return;
	}
	var parentValueId = getValue(parent);
	var currentValue = getValue(child);
	clearOptions(child);
	var required = Element.hasClassName(child, "required");
	var emptyLabel = child.getAttribute("fieldEmptyLabel") || "";
	if (!required) {
		addOption(child, new Option(emptyLabel, ""))
	}

	if (!isEmpty(parentValueId)) {
		var childFieldId = child.getAttribute("fieldId");
		var url = context + "/do/searchPossibleValuesAjax";
		var params = {nature: nature, fieldId: childFieldId, parentValueId: parentValueId};
		new Ajax.Request(url, { 
			method: 'post',
			parameters: $H(params).toQueryString(),
			onSuccess: function(request, possibleValues) {
				addOptions(child, possibleValues, false, "value", "id");
				//Try to find the option containing the old value
				var preselectedOption = isEmpty(currentValue) ? null : possibleValues.find(function(pv) {
					return pv.id == currentValue;
				});
				if (!preselectedOption) {
					//No previous option - preselect the default value, if any
					preselectedOption = possibleValues.find(function(pv) {
						return booleanValue(pv.defaultValue);
					});
				}
				if (preselectedOption) {
					setValue(child, preselectedOption.id + "");
				}
			}
		});
	}
}

/*********************************************************************/
/*                            IMAGES                                 */
/*********************************************************************/

//An image descriptor class
function ImageDescriptor(id, caption, url) {
	this.id = id;
	this.caption = caption;
	this.url = url;
}

//A descriptor for a No Picture
var noPictureDescriptor = new ImageDescriptor(null, noPictureCaption, context + "/systemImage?image=noPicture&thumbnail=true");

//The main image container class
var ImageContainer = Class.create();
Object.extend(ImageContainer.prototype, {

	initialize: function(div, nature, ownerId) {
		this.div = div;
		div.container = this;
		this.onRemove = null;
		this.nature = nature;
		this.ownerId = ownerId;
		this.imageDescriptors = [];
		
		//Prevent memory leak in IE
		if (is.ie) {
			Event.observe(self, "unload", function() {
				div.container.release();
				div = null;
			});
		}
	},
	
	nextImage: function() {
		if (this.currentImage < this.imageDescriptors.length - 1) {
			this.currentImage++;
		} else {
			this.currentImage = 0;
		}
		this.updateImage();
	},
	
	previousImage: function() {
		if (this.currentImage > 0) {
			this.currentImage--;
		} else {
			this.currentImage = this.imageDescriptors.length - 1;
		}
		this.updateImage();
	},
	
	currentImageDescriptor: function() {
		if (this.imageDescriptors.length == 0) {
			return noPictureDescriptor;
		}
		return this.imageDescriptors[this.currentImage];
	},
	
	updateImage: function() {
		var currentImageDescriptor = this.currentImageDescriptor();
		var noPicture = !currentImageDescriptor.id;
		var caption = currentImageDescriptor.caption;
		this.thumbnail.src = currentImageDescriptor.url;
		this.thumbnail.alt = caption;
		this.thumbnail.title = caption;
		if (noPicture) {
			this.thumbnail.style.pointer = "default";
			this.thumbnail.onclick = null;
		} else {
			setPointer(this.thumbnail);
			this.thumbnail.onclick = this.showImage.bind(this);
			if (this.index) {
				this.index.innerHTML = (this.currentImage + 1) + " / " + this.imageDescriptors.length;
			}
		}
	},
	
	showImage: function() {
		var currentImageDescriptor = this.currentImageDescriptor();
		if (currentImageDescriptor.id) {
			window.imageContainer = this;
			showImage(currentImageDescriptor.id, this.imageDescriptors.length > 1);
		}
	},
	
	removeImage: function() {
		var container = this;
		if (confirm(imageRemoveMessage)) {
			var currentImageDescriptor = this.currentImageDescriptor();
			new Ajax.Request(pathPrefix + "/removeImage", {
				method: 'get',
				parameters: "id=" + currentImageDescriptor.id,
				onSuccess: function() {
					container.handleRemove();
				},
				onFailure: function() {
					alert(errorRemovingImageMessage);
				}
			});
		}
	},
	
	details: function() {
		var params = $H();
		params.set("images(nature)", this.nature);
		params.set("images(owner)", this.ownerId);
		var url = pathPrefix + "/imageDetails?" + params.toQueryString();
		var width = 500;
		var height = 570;
		var popup = this.currentDetailsWindow = window.open(url, "imageDetails", "scrollbars=yes,resizable=yes,width=" + width + ",height=" + height + getLocation(width, height, 10, 10));		
		window.imageContainer = this;
		Event.observe(self, "unload", function() {
			try { 
				popup.close();
			} catch (e) {
				//Ignore
			}
		});
	},
	
	handleImageDetailsSuccess: function(updatedDetails) {
		this.imageDescriptors = (updatedDetails || []).map(function(detail) {
			return new ImageDescriptor(detail.id, detail.caption, context + "/thumbnail?id=" + detail.id);
		});
		this.currentImage = 0;
		this.updateImage();
		try {
			this.currentDetailsWindow.close();
		} catch (e) {}
		alert(imageDetailsSuccess);
	},
	
	handleImageDetailsError: function() {
		alert(imageDetailsError);
	},	
	
	handleRemove: function() {
		//Rebuild the id array
		this.imageDescriptors = this.imageDescriptors.reject(function(descriptor, index) {
			return index == this.currentImage;
		}.bind(this));
		if (this.currentImage >= this.imageDescriptors.length) {
			this.currentImage--;
		}
		if (this.imageDescriptors.length == 0) {
			//All images were removed! Change the image to no picture
			this.thumbnail.src = noPictureDescriptor.url;
			this.releaseElement('imageRemove');
			this.releaseElement('imageDetails');
		} else {
			if (this.imageDescriptors.length == 1) {
				//There is only 1 image left... remove the controls container
				this.releaseElement('controls');
				this.controls = null;
			}
			this.updateImage();
		}
		if (this.onRemove) {
			this.onRemove();
		}
		alert(imageRemovedMessage);
	},

	appendElement: function(name, element) {
		element = $(element);
		if (element) {
			element.container = this;
			this[name] = element;
		}
	},

	releaseElement: function(name) {
		var element = this[name];
		if (element) {
			element.container = null;
			try {
				Element.remove(element);
			} finally {
				this[name] = null;
			}
		}
	},
	
	release: function() {
		this.releaseElement('imageRemove');
		this.releaseElement('imageDetails');
		this.releaseElement('previous');
		this.releaseElement('next');
		this.releaseElement('controls');
		this.releaseElement('index');
		this.releaseElement('thumbnail');
		this.releaseElement('div');
	}
});

var focusFirstPaymentField = false;
function updatePaymentFieldsCallback(request) {
	var html = request ? request.responseText : "";
	var row = $('customValuesRow');
	var cell = $('customValuesCell');
	if (isEmpty(html)) {
		//No custom fields. Clear
		row.hide();
		cell.innerHTML = "";
	} else {
		row.show();
		cell.innerHTML = html;
		html.evalScripts();
		//Apply the behavior to the form elements
		$A(cell.getElementsByTagName("input")).each(headBehaviour.input);
		$A(cell.getElementsByTagName("select")).each(function(select) {
			headBehaviour.select(select);
			if (select.onchange) select.onchange();
		});
		$A(cell.getElementsByTagName("textarea")).each(headBehaviour.textarea);
		if (focusFirstPaymentField) {
			var inputs = cell.getElementsByTagName("input");
			for (var i = 0; i < inputs.length; i++) {
				var input = inputs[i];
				if (input.type == 'text' || input.type == 'textarea') {
					input.focus();
					break;
				}
			}
		}
	}
}

function isReceiptPrinterSet() {
    return !isEmpty(readCookie("receiptPrinterId"));
}

function printReceipt(url, params) {
    new Ajax.Request(url, {
        method: 'post',
        parameters: isEmpty(params) ? '' : params.toPlainString ? params.toPlainString() : params,
        onSuccess: function(request, result) {
            var printerName = result.printerName; 
            if (isEmpty(printerName)) {
                alert(receiptPrinterNoConfigurationError);
                return;
            }
            initJZebra(printerName, function() {
                doPrintReceipt(result.text);
            });
        }
    });
}

var printerOk = false;

function monitorJZebra(condition, callback, attempt) {
    if (attempt > 20) {
        throw "timeout";
    }
    if (!condition()) {
        monitorJZebra.bind(self, condition, callback, attempt ? attempt + 1 : 1).delay(0.5);
    } else {
        try {
            callback();
        } catch (e) {
            alert(document.jZebra + "\n\n" + debug(e) + "\n\n" + callback);
        }
    }
}

function initJZebra(printerName, callback) {
    var invokeCallback = function() {
        var printerOk = document.jZebra.getPrinterName() != null;
        if (printerOk) {
            callback();
        } else {
            alert(replaceAll(receiptPrinterNotFoundError, "#printer#", printerName));
        }
    };
    if (isEmpty($A(document.getElementsByName("jZebra")))) {
        //Initialize before invoking callback
        try {
            var applet = document.createElement("applet");
            applet.setAttribute("name", "jZebra");
            applet.setAttribute("code", "jzebra.RawPrintApplet.class");
            applet.setAttribute("archive", context + "/jzebra.jar");
            applet.setAttribute("width", 1);
            applet.setAttribute("height", 1);
            document.jZebra = document.body.appendChild(applet);
            
            monitorJZebra(function() {
                return typeof(document.jZebra.findPrinter) == 'function';
            }, function() {
                document.jZebra.findPrinter(printerName);
                monitorJZebra(function() {return document.jZebra.isDoneFinding()}, invokeCallback)
            });
        } catch (e) {
            alert(receiptPrinterAppletError + "\n\n" + debug(e));
        }
    } else {
        //Already initialized. Invoke the callback
        invokeCallback();
    }
}

function doPrintReceipt(text) {
    document.jZebra.append(text);
    monitorJZebra(function() {return document.jZebra.isDoneAppending()}, function() {
        document.jZebra.print();
        monitorJZebra(function() {return document.jZebra.isDonePrinting()}, function() {
            var e = document.jZebra.getException();
            if (e != null) {
                alert(printError + "\n\n" + e.getLocalizedMessage());
            }
        });
    });
}