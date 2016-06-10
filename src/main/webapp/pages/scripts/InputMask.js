/*
 * InputMask version 2.2.4
 *
 * This script contains several classes to restrict the user input on HTML inputs.
 *
 * Dependencies: 
 *  - JavaScriptUtil.js
 *  - Parsers.js (for NumberMask and DateMask)
 *
 * Author: Luis Fernando Planella Gonzalez (lfpg.dev at gmail dot com)
 * Home Page: http://javascriptools.sourceforge.net
 *
 * You may freely distribute this file, just don't remove this header
 */

/*
 * Default value constants
 */
//Will InputMask be applied when the user strokes a backspace?
var JST_NUMBER_MASK_APPLY_ON_BACKSPACE = true;
//Will InputMask validate the text on the onblur event?
var JST_MASK_VALIDATE_ON_BLUR = true;
//Allow negative values by default on the NumberMask
var JST_DEFAULT_ALLOW_NEGATIVE = true;
//Will the NumberMask digits be from left to right by default?
var JST_DEFAULT_LEFT_TO_RIGHT = false;
//Validates the typed text on DateMask?
var JST_DEFAULT_DATE_MASK_VALIDATE = true;
//The default message for DateMask validation errors
var JST_DEFAULT_DATE_MASK_VALIDATION_MESSAGE = "";
//The default padFunction for years
var JST_DEFAULT_DATE_MASK_YEAR_PAD_FUNCTION = getFullYear;
//The default padFunction for am/pm field
var JST_DEFAULT_DATE_MASK_AM_PM_PAD_FUNCTION = function(value) {
    if (isEmpty(value)) return "";
    switch (left(value, 1)) {
        case 'a': return 'am';
        case 'A': return 'AM';
        case 'p': return 'pm';
        case 'P': return 'PM';
    }
    return value;
} 
//The default decimal separator for decimal separator for the JST_MASK_DECIMAL 
//Note that this does not affect the NumberMask instances
var JST_FIELD_DECIMAL_SEPARATOR = new Literal(typeof(JST_DEFAULT_DECIMAL_SEPARATOR) == "undefined" ? "," : JST_DEFAULT_DECIMAL_SEPARATOR);
//The SizeLimit default output text
var JST_DEFAULT_LIMIT_OUTPUT_TEXT = "${left}";

///////////////////////////////////////////////////////////////////////////////
//Temporary variables for the masks
numbers = new Input(JST_CHARS_NUMBERS);
optionalNumbers = new Input(JST_CHARS_NUMBERS);
optionalNumbers.optional = true;
oneToTwoNumbers = new Input(JST_CHARS_NUMBERS, 1, 2);
year = new Input(JST_CHARS_NUMBERS, 1, 4, getFullYear);
dateSep = new Literal("/");
dateTimeSep = new Literal(" ");
timeSep = new Literal(":");

/*
 * Some prebuilt masks
 */
var JST_MASK_NUMBERS       = [numbers];
var JST_MASK_DECIMAL       = [numbers, JST_FIELD_DECIMAL_SEPARATOR, optionalNumbers];
var JST_MASK_UPPER         = [new Upper(JST_CHARS_LETTERS)];
var JST_MASK_LOWER         = [new Lower(JST_CHARS_LETTERS)];
var JST_MASK_CAPITALIZE    = [new Capitalize(JST_CHARS_LETTERS)];
var JST_MASK_LETTERS       = [new Input(JST_CHARS_LETTERS)];
var JST_MASK_ALPHA         = [new Input(JST_CHARS_ALPHA)];
var JST_MASK_ALPHA_UPPER   = [new Upper(JST_CHARS_ALPHA)];
var JST_MASK_ALPHA_LOWER   = [new Lower(JST_CHARS_ALPHA)];
var JST_MASK_DATE          = [oneToTwoNumbers, dateSep, oneToTwoNumbers, dateSep, year];
var JST_MASK_DATE_TIME     = [oneToTwoNumbers, dateSep, oneToTwoNumbers, dateSep, year, dateTimeSep, oneToTwoNumbers, timeSep, oneToTwoNumbers];
var JST_MASK_DATE_TIME_SEC = [oneToTwoNumbers, dateSep, oneToTwoNumbers, dateSep, year, dateTimeSep, oneToTwoNumbers, timeSep, oneToTwoNumbers, timeSep, oneToTwoNumbers];

//Clear the temporary variables
delete numbers;
delete optionalNumbers;
delete oneToTwoNumbers;
delete year;
delete dateSep;
delete dateTimeSep;
delete timeSep;

/* We ignore the following characters on mask:
45 - insert, 46 - del (not on konqueror), 35 - end, 36 - home, 33 - pgup, 
34 - pgdown, 37 - left, 39 - right, 38 - up, 40 - down,
127 - del on konqueror, 4098 shift + tab on konqueror */
var JST_IGNORED_KEY_CODES = [45, 35, 36, 33, 34, 37, 39, 38, 40, 127, 4098];
if (navigator.userAgent.toLowerCase().indexOf("khtml") < 0) {
    JST_IGNORED_KEY_CODES[JST_IGNORED_KEY_CODES.length] = 46;
}
//All other with keyCode < 32 are also ignored
for (var i = 0; i < 32; i++) {
    JST_IGNORED_KEY_CODES[JST_IGNORED_KEY_CODES.length] = i;
}
//F1 - F12 are also ignored
for (var i = 112; i <= 123; i++) {
    JST_IGNORED_KEY_CODES[JST_IGNORED_KEY_CODES.length] = i;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * This is the main InputMask class.
 * Parameters: 
 *    fields: The mask fields
 *    control: The reference to the control that is being masked
 *    keyPressFunction: The additional function instance used on the keyPress event
 *    keyDownFunction: The additional function instance used on the keyDown event
 *    keyUpFunction: The additional function instance used on the keyUp event
 *    blurFunction: The additional function instance used on the blur event
 *    updateFunction: A callback called when the mask is applied
 *    changeFunction: The additional function instance used on the change event
 */
function InputMask(fields, control, keyPressFunction, keyDownFunction, keyUpFunction, blurFunction, updateFunction, changeFunction) {
    
    //Check if the fields are a String
    if (isInstance(fields, String)) {
        fields = maskBuilder.parse(fields);
    } else if (isInstance(fields, MaskField)) {
        fields = [fields];
    }
    
    //Check if the fields are a correct array of fields
    if (isInstance(fields, Array)) {
        for (var i = 0; i < fields.length; i++) {
            var field = fields[i];
            if (!isInstance(field, MaskField)) {
                alert("Invalid field: " + field);
                return;
            }
        }
    } else {
        alert("Invalid field array: " + fields);
        return;
    }
    
    this.fields = fields;

    //Validate the control
    control = validateControlToMask(control);
    if (!control) {
        alert("Invalid control to mask");
        return;
    } else {
        this.control = control;
        prepareForCaret(this.control);
        this.control.supportsCaret = isCaretSupported(this.control);
    }
    
    //Set the control's reference to the mask descriptor
    this.control.mask = this;
    this.control.pad = false;
    this.control.ignore = false;

    //Set the function calls
    this.keyDownFunction = keyDownFunction || null;
    this.keyPressFunction = keyPressFunction || null;
    this.keyUpFunction = keyUpFunction || null;
    this.blurFunction = blurFunction || null;
    this.updateFunction = updateFunction || null;
    this.changeFunction = changeFunction || null;

    //The onKeyDown event will detect special keys
    function onKeyDown (event) {
        if (window.event) {
            event = window.event;
        }

        this.keyCode = typedCode(event);
        
        //Check for extra function on keydown
        if (this.mask.keyDownFunction != null) {
            var ret = invokeAsMethod(this, this.mask.keyDownFunction, [event, this.mask]);
            if (ret == false) {
                return preventDefault(event);
            }
        }
    }
    observeEvent(this.control, "keydown", onKeyDown);
    
    //The KeyPress event will filter the typed character
    function onKeyPress (event) {
        if (window.event) {
            event = window.event;
        }

        //Get what's been typed        
        var keyCode = this.keyCode || typedCode(event);
        var ignore = event.altKey || event.ctrlKey || inArray(keyCode, JST_IGNORED_KEY_CODES);

        //When a range is selected, clear it
        if (!ignore) {
            var range = getInputSelectionRange(this);
            if (range != null && range[0] != range[1]) {
                replaceSelection(this, "");
            }
        }
        
        //Prepre the variables
        this.caretPosition = getCaret(this);
        this.setFixedLiteral = null;
        var typedChar = this.typedChar = ignore ? "" : String.fromCharCode(typedCode(event));
        var fieldDescriptors = this.fieldDescriptors = this.mask.getCurrentFields();
        var currentFieldIndex = this.currentFieldIndex = this.mask.getFieldIndexUnderCaret();

        //Check if any field accept the typed key
        var accepted = false;
        if (!ignore) {
            var currentField = this.mask.fields[currentFieldIndex];
            accepted = currentField.isAccepted(typedChar);
            if (accepted) {
                //Apply basic transformations
                if (currentField.upper) {
                    typedChar = this.typedChar = typedChar.toUpperCase();
                } else if (currentField.lower) {
                    typedChar = this.typedChar = typedChar.toLowerCase();
                }
                if (currentFieldIndex == this.mask.fields.length - 2) {
                    var nextFieldIndex = currentFieldIndex + 1;
                    var nextField = this.mask.fields[nextFieldIndex];
                    if (nextField.literal) {
                        //When this field is the last input and the next field is literal, if this field is complete, append the literal also
                        var currentFieldIsComplete = !currentField.acceptsMoreText(fieldDescriptors[currentFieldIndex].value + typedChar);
                        if (currentFieldIsComplete) {
                            this.setFixedLiteral = nextFieldIndex;
                        }
                    }
                }
            } else {
                var previousFieldIndex = currentFieldIndex - 1;
                if (currentFieldIndex > 0 && this.mask.fields[previousFieldIndex].literal && isEmpty(fieldDescriptors[previousFieldIndex].value)) {
                    //When passed by the previous field without it having a value, force it to have a value
                    this.setFixedLiteral = previousFieldIndex;
                    accepted = true;
                } else if (currentFieldIndex < this.mask.fields.length - 1) {
                    //When typed the next literal, pad this field and go to the next one
                    var descriptor = fieldDescriptors[currentFieldIndex];
                    var nextFieldIndex = currentFieldIndex + 1;
                    var nextField = this.mask.fields[nextFieldIndex];
                    if (nextField.literal && nextField.text.indexOf(typedChar) >= 0) {
                        //Mark the field as setting the current literal
                        this.setFixedLiteral = nextFieldIndex;
                        accepted = true;
                    }
                } else if (currentFieldIndex == this.mask.fields.length - 1 && currentField.literal) {
                    // When the mask ends with a literal and it's the current field, force it to have a value
                    this.setFixedLiteral = currentFieldIndex;
                    accepted = true;
                }
            }
        }

        //Check for extra function on keypress
        if (this.mask.keyPressFunction != null) {
            var ret = invokeAsMethod(this, this.mask.keyPressFunction, [event, this.mask]);
            if (ret == false) {
                return preventDefault(event);
            }
        }
        
        //Return on ignored keycodes
        if (ignore) {
            return;
        }
        
        //Apply the mask
        var shouldApplyMask = !ignore && accepted;
        if (shouldApplyMask) {
            applyMask(this.mask, false);
        }
        
        this.keyCode = null;
        return preventDefault(event);
    }
    observeEvent(this.control, "keypress", onKeyPress);

    //The KeyUp event is no longer used, and will be kept for backward compatibility
    function onKeyUp (event) {
        if (window.event) {
            event = window.event;
        }

        //Check for extra function on keyup
        if (this.mask.keyUpFunction != null) {
            var ret = invokeAsMethod(this, this.mask.keyUpFunction, [event, this.mask]);
            if (ret == false) {
                return preventDefault(event);
            }
        }
    }
    observeEvent(this.control, "keyup", onKeyUp);
    
    //Add support for onchange event
    function onFocus (event) {
        this._lastValue = this.value;
    }
    observeEvent(this.control, "focus", onFocus);
    
    //The Blur event will apply the mask again, to ensure the user will not paste an invalid value
    function onBlur (event) {
        if (window.event) {
            event = window.event;
        }
        
        document.fieldOnBlur = this;
        try {        
            var valueChanged = this._lastValue != this.value;
            
            if (valueChanged && JST_MASK_VALIDATE_ON_BLUR) {
                applyMask(this.mask, true);
            }
            
            if (this.mask.changeFunction != null) {
                if (valueChanged && this.mask.changeFunction != null) {
                    var e = {};
                    for (property in event) {
                        e[property] = event[property];
                    }
                    e.type = "change";
                    invokeAsMethod(this, this.mask.changeFunction, [e, this.mask]);
                }
            }
                
            //Check for extra function on blur
            if (this.mask.blurFunction != null) {
                var ret = invokeAsMethod(this, this.mask.blurFunction, [event, this.mask]);
                if (ret == false) {
                    return preventDefault(event);
                }
            }
            return true;
        } finally {
            document.fieldOnBlur = null;
        }
    }
    observeEvent(this.control, "blur", onBlur);
    
    //Method to determine whether the mask fields are complete
    this.isComplete = function () {

        //Ensures the field values will be parsed
        applyMask(this, true);
        
        //Get the fields
        var descriptors = this.getCurrentFields();

        //Check if there is some value
        if (descriptors == null || descriptors.length == 0) {
            return false;
        }

        //Check for completed values
        for (var i = 0; i < this.fields.length; i++) {
            var field = this.fields[i];
            if (field.input && !field.isComplete(descriptors[i].value) && !field.optional) {
                return false;
            }
        }
        return true;
    }
    
    //Method to force a mask update
    this.update = function () {
        applyMask(this, true);
    }
    
    //Returns an array with objects containing values, start position and end positions
    this.getCurrentFields = function(value) {
        value = value || this.control.value;
        var descriptors = [];
        var currentIndex = 0;
        //Get the value for input fields
        var lastInputIndex = -1;
        for (var i = 0; i < this.fields.length; i++) {
            var field = this.fields[i];
            var fieldValue = "";
            var descriptor = {};
            if (field.literal) {
                if (lastInputIndex >= 0) {
                    var lastInputField = this.fields[lastInputIndex];
                    var lastInputDescriptor = descriptors[lastInputIndex];
                    //When no text is accepted by the last input field, 
                    //assume the next input will be used, so, assume the value for this literal as it's text
                    if (field.text.indexOf(mid(value, currentIndex, 1)) < 0 && lastInputField.acceptsMoreText(lastInputDescriptor.value)) {
                        descriptor.begin = -1;
                    } else {
                        descriptor.begin = currentIndex;
                    }
                }
                if (currentIndex >= value.length) {
                    break;
                }
                if (value.substring(currentIndex, currentIndex + field.text.length) == field.text) {
                    currentIndex += field.text.length;
                }
            } else {
                //Check if there's a value
                var upTo = field.upTo(value, currentIndex);
                if (upTo < 0 && currentIndex >= value.length) {
                    break;
                }
                fieldValue = upTo < 0 ? "" : field.transformValue(value.substring(currentIndex, upTo + 1));
                descriptor.begin = currentIndex;
                descriptor.value = fieldValue;
                currentIndex += fieldValue.length;
                lastInputIndex = i;
            }
            descriptors[i] = descriptor;
        }
        
        //Complete the descriptors
        var lastWithValue = descriptors.length - 1;
        for (var i = 0; i < this.fields.length; i++) {
            var field = this.fields[i];
            if (i > lastWithValue) {
                descriptors[i] = {value: "", begin: -1};
            } else {
                // Literals with inputs on both sides that have values also have values
                if (field.literal) {
                    var descriptor = descriptors[i]; 

                    //Literals that have been set begin < 0 will have no value 
                    if (descriptor.begin < 0) {
                        descriptor.value = "";
                        continue;
                    }
                    
                    //Find the previous input value
                    var previousField = null;
                    var previousValueComplete = false;
                    for (var j = i - 1; j >= 0; j--) {
                        var current = this.fields[j]; 
                        if (current.input) {
                            previousField = current;
                            previousValueComplete = current.isComplete(descriptors[j].value);
                            if (previousValueComplete) {
                                break;
                            } else {
                                previousField = null;
                            }
                        }
                    }

                    //Find the next input value
                    var nextField = null;
                    var nextValueExists = null;
                    for (var j = i + 1; j < this.fields.length && j < descriptors.length; j++) {
                        var current = this.fields[j]; 
                        if (current.input) {
                            nextField = current;
                            nextValueExists = !isEmpty(descriptors[j].value);
                            if (nextValueExists) {
                                break;
                            } else {
                                nextField = null;
                            }
                        }
                    }
                    //3 cases for using the value: 
                    // * both previous and next inputs have complete values
                    // * no previous input and the next has complete value
                    // * no next input and the previous has complete value
                    if ((previousValueComplete && nextValueExists) || (previousField == null && nextValueExists) || (nextField == null && previousValueComplete)) {
                        descriptor.value = field.text;
                    } else {
                        descriptor.value = "";
                        descriptor.begin = -1;
                    }
                }
            }
        }
        return descriptors;
    }
    
    //Returns the field index under the caret
    this.getFieldIndexUnderCaret = function() {
        var value = this.control.value;
        var caret = getCaret(this.control);
        //When caret operations are not supported, assume it's at text end
        if (caret == null) caret = value.length;
        var lastPosition = 0;
        var lastInputIndex = null;
        var lastInputAcceptsMoreText = false;
        var lastWasLiteral = false;
        var returnNextInput = isEmpty(value) || caret == 0;
        for (var i = 0; i < this.fields.length; i++) {
            var field = this.fields[i];
            if (field.input) {
                //Check whether should return the next input field
                if (returnNextInput || lastPosition > value.length) {
                    return i;
                }
                //Find the field value
                var upTo = field.upTo(value, lastPosition)
                if (upTo < 0) {
                    return i; //lastInputIndex == null || lastWasLiteral ? i : lastInputIndex;
                }
                //Handle unlimited fields
                if (field.max < 0) {
                    var nextField = null;
                    if (i < this.fields.length - 1) {
                        nextField = this.fields[i + 1];
                    }
                    if (caret - 1 <= upTo && (nextField == null || nextField.literal)) {
                        return i;
                    } 
                }
                var fieldValue = value.substring(lastPosition, upTo + 1);
                var acceptsMoreText = field.acceptsMoreText(fieldValue);
                var positionToCheck = acceptsMoreText ? caret - 1 : caret
                if (caret >= lastPosition && positionToCheck <= upTo) {
                    return i;
                }
                lastInputAcceptsMoreText = acceptsMoreText;
                lastPosition = upTo + 1;
                lastInputIndex = i;
            } else {
                if (caret == lastPosition) {
                    returnNextInput = !lastInputAcceptsMoreText;
                }
                lastPosition += field.text.length;
            }
            lastWasLiteral = field.literal;
        }
        return this.fields.length - 1;
    }
    
    //Method to determine if the mask is only for filtering which chars can be typed
    this.isOnlyFilter = function () {
        if (this.fields == null || this.fields.length == 0) {
            return true;
        }
        if (this.fields.length > 1) {
            return false;
        }
        var field = this.fields[0];
        return field.input && field.min <= 1 && field.max <= 0;
    }
    
    //Returns if this mask changes the text case
    this.transformsCase = function() {
        if (this.fields == null || this.fields.length == 0) {
            return false;
        }
        for (var i = 0; i < this.fields.length; i++) {
            var field = this.fields[i];
            if (field.upper || field.lower || field.capitalize) {
                return true;
            }
        }
        return false;
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * This is the main NumberMask class.
 * Parameters: 
 *    parser: The NumberParser instance used by the mask
 *    control: The reference to the control that is being masked
 *    maxIntegerDigits: The limit for integer digits (excluding separators). 
 *                      Default: -1 (no limit)
 *    allowNegative: Should negative values be allowed? Default: see the 
 *                   value of the JST_DEFAULT_ALLOW_NEGATIVE constant.
 *    keyPressFunction: The additional function instance used on the keyPress event
 *    keyDownFunction: The additional function instance used on the keyDown event
 *    keyUpFunction: The additional function instance used on the keyUp event
 *    blurFunction: The additional function instance used on the blur event
 *    updateFunction: A callback called when the mask is applied
 *    leftToRight: Indicates if the input will be processed from left to right. 
 *                 Default: the JST_DEFAULT_LEFT_TO_RIGHT constant
 *    changeFunction: The additional function instance used on the change event
 */
function NumberMask(parser, control, maxIntegerDigits, allowNegative, keyPressFunction, keyDownFunction, keyUpFunction, blurFunction, updateFunction, leftToRight, changeFunction) {
    //Validate the parser
    if (!isInstance(parser, NumberParser)) {
        alert("Illegal NumberParser instance");
        return;
    }
    this.parser = parser;
    
    //Validate the control
    control = validateControlToMask(control);
    if (!control) {
        alert("Invalid control to mask");
        return;
    } else {
        this.control = control;
        prepareForCaret(this.control);
        this.control.supportsCaret = isCaretSupported(this.control);
    }

    //Get the additional properties
    this.maxIntegerDigits = maxIntegerDigits || -1;
    this.allowNegative = allowNegative || JST_DEFAULT_ALLOW_NEGATIVE;
    this.leftToRight = leftToRight || JST_DEFAULT_LEFT_TO_RIGHT;

    //Set the control's reference to the mask and other aditional flags
    this.control.mask = this;
    this.control.ignore = false;
    this.control.swapSign = false;
    this.control.toDecimal = false;
    this.control.oldValue = this.control.value;
    
    //Set the function calls
    this.keyDownFunction = keyDownFunction || null;
    this.keyPressFunction = keyPressFunction || null;
    this.keyUpFunction = keyUpFunction || null;
    this.blurFunction = blurFunction || null;
    this.updateFunction = updateFunction || null;
    this.changeFunction = changeFunction || null;
    
    //The onKeyDown event will detect special keys
    function onKeyDown (event) {
        if (window.event) {
            event = window.event;
        }
        
        var keyCode = typedCode(event);
        this.ignore = event.altKey || event.ctrlKey || inArray(keyCode, JST_IGNORED_KEY_CODES);

        //Check for extra function on keydown
        if (this.mask.keyDownFunction != null) {
            var ret = invokeAsMethod(this, this.mask.keyDownFunction, [event, this.mask]);
            if (ret == false) {
                return preventDefault(event);
            }
        }
        
        return true;
    }
    observeEvent(this.control, "keydown", onKeyDown);

    //The KeyPress event will filter the keys
    function onKeyPress (event) {
        if (window.event) {
            event = window.event;
        }

        var keyCode = typedCode(event);
        var typedChar = String.fromCharCode(keyCode);

        //Check for extra function on keypress
        if (this.mask.keyPressFunction != null) {
            var ret = invokeAsMethod(this, this.mask.keyPressFunction, [event, this.mask]);
            if (ret == false) {
                return preventDefault(event);
            }
        }

        if (this.ignore) {
            return true;
        }

        //Store the old value
        this.oldValue = this.value;

        //Check for the minus sign
        if (typedChar == '-') {
            if (this.mask.allowNegative) {
                if (this.value == '') {
                    //Typing the negative sign on the empty field. ok.
                    this.ignore = true;
                    return true;
                }
                //The field is not empty. Set the swapSign flag, so applyNumberMask will do the job
                this.swapSign = true;
                applyNumberMask(this.mask, false, false);
            }
            return preventDefault(event);
        }
        //Check for the decimal separator
        if (this.mask.leftToRight && typedChar == this.mask.parser.decimalSeparator && this.mask.parser.decimalDigits != 0) {
            this.toDecimal = true;
            if (this.supportsCaret) {
                return preventDefault(event);
            }
        }
        this.swapSign = false;
        this.toDecimal = false;
        this.accepted = false;
        if (this.mask.leftToRight && typedChar == this.mask.parser.decimalSeparator) {
            if (this.mask.parser.decimalDigits == 0 || this.value.indexOf(this.mask.parser.decimalSeparator) >= 0) {
                this.accepted = true;
                return preventDefault(event);
            } else {
                return true;
            }
        }
        this.accepted = onlyNumbers(typedChar);
        if (!this.accepted) {
            return preventDefault(event);
        }
    }
    observeEvent(this.control, "keypress", onKeyPress);
    
    //The KeyUp event will apply the mask
    function onKeyUp (event) {
        //Check an invalid parser
        if (this.mask.parser.decimalDigits < 0 && !this.mask.leftToRight) {
            alert("A NumberParser with unlimited decimal digits is not supported on NumberMask when the leftToRight property is false");
            this.value = "";
            return false;
        }

        if (window.event) {
            event = window.event;
        }
        //Check if it's not an ignored key
        var keyCode = typedCode(event);
        var isBackSpace = (keyCode == 8) && JST_NUMBER_MASK_APPLY_ON_BACKSPACE;
        if (this.supportsCaret && (this.toDecimal || (!this.ignore && this.accepted) || isBackSpace)) {
            //If the number was already 0 and we stroke a backspace, clear the text value
            if (isBackSpace && this.mask.getAsNumber() == 0) {
                this.value = "";
            }
            applyNumberMask(this.mask, false, isBackSpace);
        }
        //Check for extra function on keyup
        if (this.mask.keyUpFunction != null) {
            var ret = invokeAsMethod(this, this.mask.keyUpFunction, [event, this.mask]);
            if (ret == false) {
                return preventDefault(event);
            }
        }

        return true;
    }
    observeEvent(this.control, "keyup", onKeyUp);
    
    //Add support for onchange event
    function onFocus (event) {
        if (this.mask.changeFunction != null) {
            this._lastValue = this.value;
        }
    }
    observeEvent(this.control, "focus", onFocus);

    //The Blur event will apply the mask again, to ensure the user will not paste an invalid value
    function onBlur (event) {
        if (window.event) {
            event = window.event;
        }
        
        if (JST_MASK_VALIDATE_ON_BLUR) {
            if (this.value == '-') {
                this.value = '';
            } else {
                applyNumberMask(this.mask, true, false);
            }
        }
        
        if (this.mask.changeFunction != null) {
            if (this._lastValue != this.value && this.mask.changeFunction != null) {
                var e = {};
                for (property in event) {
                    e[property] = event[property];
                }
                e.type = "change";
                invokeAsMethod(this, this.mask.changeFunction, [e, this.mask]);
            }
        }
        
        //Check for extra function on keydown
        if (this.mask.blurFunction != null) {
            var ret = invokeAsMethod(this, this.mask.blurFunction, [event, this.mask]);
            if (ret == false) {
                return preventDefault(event);
            }
        }
        return true;
    }
    observeEvent(this.control, "blur", onBlur);
    
    //Method to determine if the mask is all complete
    this.isComplete = function() {
        return this.control.value != "";
    }
    
    //Returns the control value as a number
    this.getAsNumber = function() {
        var number = this.parser.parse(this.control.value);
        if (isNaN(number)) {
            number = null;
        }
        return number;
    }

    //Sets the control value as a number
    this.setAsNumber = function(number) {
        var value = "";
        if (isInstance(number, Number)) {
            value = this.parser.format(number);
        }
        this.control.value = value;
        this.update();
    }
    
    //Method to force a mask update
    this.update = function() {
        applyNumberMask(this, true, false);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * This is the main DateMask class.
 * Parameters: 
 *    parser: The DateParser instance used by the mask
 *    control: The reference to the control that is being masked
 *    validate: Validate the control on the onblur event? Default: The JST_DEFAULT_DATE_MASK_VALIDATE value
 *    validationMessage: Message alerted on validation on fail. The ${value} 
 *       placeholder may be used as a substituition for the field value, and ${mask} 
 *       for the parser mask. Default: the JST_DEFAULT_DATE_MASK_VALIDATION_MESSAGE value
 *    keyPressFunction: The additional function instance used on the keyPress event
 *    keyDownFunction: The additional function instance used on the keyDown event
 *    keyUpFunction: The additional function instance used on the keyUp event
 *    blurFunction: The additional function instance used on the blur event
 *    updateFunction: A callback called when the mask is applied
 *    changeFunction: The additional function instance used on the change event
 */
function DateMask(parser, control, validate, validationMessage, keyPressFunction, keyDownFunction, keyUpFunction, blurFunction, updateFunction, changeFunction) {
    
    //Validate the parser
    if (isInstance(parser, String)) {
        parser = new DateParser(parser);
    }
    if (!isInstance(parser, DateParser)) {
        alert("Illegal DateParser instance");
        return;
    }
    this.parser = parser;
    
    //Set a control to keyPressFunction, to ensure the validation won't be shown twice
    this.extraKeyPressFunction = keyPressFunction || null;
    function maskKeyPressFunction (event, dateMask) {
        dateMask.showValidation = true;
        if (dateMask.extraKeyPressFunction != null) {
            var ret = invokeAsMethod(this, dateMask.extraKeyPressFunction, [event, dateMask]);
            if (ret == false) {
                return false;
            }
        }
        return true;
    }
    
    //Set the validation to blurFunction, plus the informed blur function
    this.extraBlurFunction = blurFunction || null;
    function maskBlurFunction (event, dateMask) {
        var control = dateMask.control;
        if (dateMask.validate && control.value.length > 0) {
            var controlValue = control.value.toUpperCase();
            controlValue = controlValue.replace(/A[^M]/, "AM");
            controlValue = controlValue.replace(/A$/, "AM");
            controlValue = controlValue.replace(/P[^M]/, "PM");
            controlValue = controlValue.replace(/P$/, "PM");
            var date = dateMask.parser.parse(controlValue);
            if (date == null) {
                var msg = dateMask.validationMessage;
                if (dateMask.showValidation && !isEmpty(msg)) {
                    dateMask.showValidation = false;
                    msg = replaceAll(msg, "${value}", control.value);
                    msg = replaceAll(msg, "${mask}", dateMask.parser.mask);
                    alert(msg);
                }
                control.value = "";
                control.focus();
            } else {
                control.value = dateMask.parser.format(date);
            }
        }
        if (dateMask.extraBlurFunction != null) {
            var ret = invokeAsMethod(this, dateMask.extraBlurFunction, [event, dateMask]);
            if (ret == false) {
                return false;
            }
        }
        return true;
    }
    
    //Build the fields array
    var fields = new Array();
    var old = '';
    var mask = this.parser.mask;
    while (mask.length > 0) {
        var field = mask.charAt(0);
        var size = 1;
        var maxSize = -1;
        var padFunction = null;
        while (mask.charAt(size) == field) {
            size++;
        }
        mask = mid(mask, size);
        var accepted = JST_CHARS_NUMBERS;
        switch (field) {
            case 'd': case 'M': case 'h': case 'H': case 'm': case 's': 
                maxSize = 2;
                break;
            case 'y':
                padFunction = JST_DEFAULT_DATE_MASK_YEAR_PAD_FUNCTION;
                if (size == 2) {
                    maxSize = 2;
                } else {
                    maxSize = 4;
                }
                break;
            case 'a': case 'A': case 'p': case 'P':
                maxSize = 2;
                padFunction = JST_DEFAULT_DATE_MASK_AM_PM_PAD_FUNCTION;
                accepted = 'aApP';
                break;
            case 'S':
                maxSize = 3;
                break;
        }
        var input;
        if (maxSize == -1) {
            input = new Literal(field);
        } else {
            input = new Input(accepted, size, maxSize);
            if (padFunction == null) {
                input.padFunction = new Function("text", "return lpad(text, " + maxSize + ", '0')");
            } else {
                input.padFunction = padFunction;
            }
        }
        fields[fields.length] = input;
    }
    
    //Initialize the superclass
    this.base = InputMask;
    this.base(fields, control, maskKeyPressFunction, keyDownFunction, keyUpFunction, maskBlurFunction, updateFunction, changeFunction);
    
    //Store the additional variables
    this.validate = validate == null ? JST_DEFAULT_DATE_MASK_VALIDATE : booleanValue(validate);
    this.showValidation = true;
    this.validationMessage = validationMessage || JST_DEFAULT_DATE_MASK_VALIDATION_MESSAGE;
    this.control.dateMask = this;
    
    //Returns the control value as a date
    this.getAsDate = function() {
        return this.parser.parse(this.control.value);
    }

    //Sets the control value as a date
    this.setAsDate = function(date) {
        var value = "";
        if (isInstance(date, Date)) {
            value = this.parser.format(date);
        }
        this.control.value = value;
        this.update();
    }
}


///////////////////////////////////////////////////////////////////////////////
/*
 * This class limits the size of an input (mainly textAreas, that does not have a 
 * maxLength attribute). Optionally, can use another element to output the number 
 * of characters on the element and/or the number of characters left.
 * Like the masks, this class uses the keyUp and blur events, may use additional
 * callbacks for those events.
 * Parameters:
 *     control: The textArea reference or name
 *     maxLength: The maximum text length
 *     output: The element to output the number of characters left. Default: none
 *     outputText: The text. May use two placeholders: 
 *         ${size} - Outputs the current text size
 *         ${left} - Outputs the number of characters left
 *         ${max} - Outputs the maximum number characters that the element accepts
 *         Examples: "${size} / ${max}", "You typed ${size}, and have ${left} more characters to type"
 *         Default: "${left}"
 *     updateFunction: If set, this function will be called when the text is updated. So, custom actions
 *         may be performed. The arguments passed to the function are: The control, the text size, the maximum size
 *         and the number of characters left.
 *     keyUpFunction: The additional handler to the keyUp event. Default: none
 *     blurFunction: The additional handler to the blur event. Default: none
 *     keyPressFunction: The additional handler to the keyPress event. Default: none
 *     keyDownFunction: The additional handler to the keyDown event. Default: none
 *     changeFunction: The additional function instance used on the change event. Default: none
 */
function SizeLimit(control, maxLength, output, outputText, updateFunction, keyUpFunction, blurFunction, keyDownFunction, keyPressFunction, changeFunction) {
    //Validate the control
    control = validateControlToMask(control);
    if (!control) {
        alert("Invalid control to limit size");
        return;
    } else {
        this.control = control;
        prepareForCaret(control);
    }
    
    if (!isInstance(maxLength, Number)) {
        alert("Invalid maxLength");
        return;
    }

    //Get the additional properties
    this.control = control;
    this.maxLength = maxLength;
    this.output = output || null;
    this.outputText = outputText || JST_DEFAULT_LIMIT_OUTPUT_TEXT;
    this.updateFunction = updateFunction || null;
    this.keyDownFunction = keyDownFunction || null;
    this.keyPressFunction = keyPressFunction || null;
    this.keyUpFunction = keyUpFunction || null;
    this.blurFunction = blurFunction || null;
    this.changeFunction = changeFunction || null;

    //Set the control's reference to the mask descriptor
    this.control.sizeLimit = this;

    //The onKeyDown event will detect special keys
    function onKeyDown (event) {
        if (window.event) {
            event = window.event;
        }

        var keyCode = typedCode(event);
        this.ignore = inArray(keyCode, JST_IGNORED_KEY_CODES);

        //Check for extra function on keydown
        if (this.sizeLimit.keyDownFunction != null) {
            var ret = invokeAsMethod(this, this.sizeLimit.keyDownFunction, [event, this.sizeLimit]);
            if (ret == false) {
                return preventDefault(event);
            }
        }
    }
    observeEvent(this.control, "keydown", onKeyDown);
    
    //The KeyPress event will filter the typed character
    function onKeyPress (event) {
        if (window.event) {
            event = window.event;
        }
        
        var keyCode = typedCode(event);
        var typedChar = String.fromCharCode(keyCode);
        var allowed = this.ignore || this.value.length < this.sizeLimit.maxLength;
        
        //Check for extra function on keypress
        if (this.sizeLimit.keyPressFunction != null) {
            var ret = invokeAsMethod(this, this.sizeLimit.keyPressFunction, [event, this.sizeLimit]);
            if (ret == false) {
                return preventDefault(event);
            }
        }
        if (!allowed) {
            preventDefault(event);
        }
    }
    observeEvent(this.control, "keypress", onKeyPress);
    
    //The KeyUp event handler
    function onKeyUp (event) {
        if (window.event) {
            event = window.event;
        }

        //Check for extra function on keypress
        if (this.sizeLimit.keyUpFunction != null) {
            var ret = invokeAsMethod(this, this.sizeLimit.keyUpFunction, [event, this.sizeLimit]);
            if (ret == false) {
                return false;
            }
        }
        return checkSizeLimit(this, false);
    }
    observeEvent(this.control, "keyup", onKeyUp);

    //Add support for onchange event
    function onFocus (event) {
        if (this.mask && this.mask.changeFunction != null) {
            this._lastValue = this.value;
        }
    }
    observeEvent(this.control, "focus", onFocus);
    
    //The Blur event handler
    function onBlur (event) {
        if (window.event) {
            event = window.event;
        }
        
        var ret = checkSizeLimit(this, true);
        
        if (this.mask && this.mask.changeFunction != null) {
            if (this._lastValue != this.value && this.sizeLimit.changeFunction != null) {
                var e = {};
                for (property in event) {
                    e[property] = event[property];
                }
                e.type = "change";
                invokeAsMethod(this, this.sizeLimit.changeFunction, [e, this.sizeLimit]);
            }
        }

        //Check for extra function on blur
        if (this.sizeLimit.blurFunction != null) {
            var ret = invokeAsMethod(this, this.sizeLimit.blurFunction, [event, this.sizeLimit]);
            if (ret == false) {
                return false;
            }
        }
        return ret;
    }
    observeEvent(this.control, "blur", onBlur);
    
    // Method used to update the limit    
    this.update = function() {
        checkSizeLimit(this.control, true);
    }

    //Initially check the size limit
    this.update();
}

//Function to determine if a given object is a valid control to mask
function validateControlToMask(control) {
    control = getObject(control)
    if (control == null) {
        return false;
    } else if (!(control.type) || (!inArray(control.type, ["text", "textarea", "password"]))) {
        return false;
    } else {
        //Avoid memory leak on MSIE
        if (/MSIE/.test(navigator.userAgent) && !window.opera) {
            observeEvent(self, "unload", function() {
                control.mask = control.dateMask = control.sizeLimit = null;
            });
        }
        return control;
    }
}

//Function to handle the mask format
function applyMask(mask, isBlur) {
    var fields = mask.fields;

    //Return if there are fields to process
    if ((fields == null) || (fields.length == 0)) {
        return;
    }

    var control = mask.control;
    if (isEmpty(control.value) && isBlur) {
        return true;
    }
    
    var value = control.value;
    var typedChar = control.typedChar;
    var typedIndex = control.caretPosition;
    var setFixedLiteral = control.setFixedLiteral;
    var currentFieldIndex = mask.getFieldIndexUnderCaret();
    var fieldDescriptors = mask.getCurrentFields();
    var currentDescriptor = fieldDescriptors[currentFieldIndex];
    
    //Apply the typed char
    if (isBlur || !isEmpty(typedChar)) {
        var out = new StringBuffer(fields.length);
        var caretIncrement = 1;
        for (var i = 0; i < fields.length; i++) {
            var field = fields[i];
            var descriptor = fieldDescriptors[i];
            var padValue = (setFixedLiteral == i + 1);
            if (currentFieldIndex == i) {
                //Append the typed char
                if (!isEmpty(typedIndex) && !isEmpty(typedChar) && field.isAccepted(typedChar)) {
                    var fieldStartsAt = descriptor.begin < 0 ? value.length : descriptor.begin;
                    var insertAt = Math.max(0, typedIndex - fieldStartsAt);
                    if (field.input && field.acceptsMoreText(descriptor.value)) {
                        //When more text is accepted, insert the typed char
                        descriptor.value = insertString(descriptor.value, insertAt, typedChar);
                    } else {
                        //No more text is accepted, overwrite
                        var prefix = left(descriptor.value, insertAt);
                        var suffix = mid(descriptor.value, insertAt + 1);
                        descriptor.value = prefix + typedChar + suffix;
                    }
                }
            } else if (currentFieldIndex == i + 1 && field.literal && typedIndex == descriptor.begin) {
                //Increment the caret when "passing by" a literal 
                caretIncrement += field.text.length;
            }
            //Pad the last field on blur 
            if (isBlur && !isEmpty(descriptor.value) && i == fields.length - 1 && field.input) {
                padValue = true
            }
            //Check if the value should be padded
            if (padValue) {
                var oldValue = descriptor.value;
                descriptor.value = field.pad(descriptor.value);
                var inc = descriptor.value.length - oldValue.length;
                if (inc > 0) {
                    caretIncrement += inc; 
                } 
            }
            out.append(descriptor.value);
        }
        value = out.toString();
    }
    
    //Build the output
    fieldDescriptors = mask.getCurrentFields(value);
    var out = new StringBuffer(fields.length);
    for (var i = 0; i < fields.length; i++) {
        var field = fields[i];
        var descriptor = fieldDescriptors[i];
        //When a literal is fixed or the next field has value, append it forcefully
        if (field.literal && (setFixedLiteral == i || (i < fields.length - 1 && !isEmpty(fieldDescriptors[i + 1].value)))) {
            descriptor.value = field.text;
        }
        out.append(descriptor.value);
    }
    
    //Update the control value
    control.value = out.toString();
    if (control.caretPosition != null && !isBlur) {
        if (control.pad) {
            setCaretToEnd(control);
        } else {
            setCaret(control, typedIndex + control.value.length - value.length + caretIncrement);
        }
    }
    
    //Call the update function if present
    if (mask.updateFunction != null) {
        mask.updateFunction(mask);
    }

    //Clear the control variables
    control.typedChar = null;
    control.fieldDescriptors = null;
    control.currentFieldIndex = null;
    
    return false;
}

//Retrieve the number of characters that are not digits up to the caret
function nonDigitsToCaret(value, caret) {
    if (caret == null) {
        return null;
    }
    var nonDigits = 0;
    for (var i = 0; i < caret && i < value.length; i++) {
        if (!onlyNumbers(value.charAt(i))) {
            nonDigits++;
        }
    }
    return nonDigits;
}

//Function to handle the number mask format
function applyNumberMask(numberMask, isBlur, isBackSpace) {
    var control = numberMask.control;
    var value = control.value;
    if (value == "") {
        return true;
    }
    var parser = numberMask.parser;
    var maxIntegerDigits = numberMask.maxIntegerDigits;
    var swapSign = false;
    var toDecimal = false;
    var leftToRight = numberMask.leftToRight;
    if (control.swapSign == true) {
        swapSign = true;
        control.swapSign = false;
    }
    if (control.toDecimal == true) {
        toDecimal = value.indexOf(parser.decimalSeparator) < 0;
        control.toDecimal = false;
    }
    var intPart = "";
    var decPart = "";
    var isNegative = value.indexOf('-') >= 0 || value.indexOf('(') >= 0;
    if (value == "") {
        value = parser.format(0);
    }
    value = replaceAll(value, parser.groupSeparator, '')
    value = replaceAll(value, parser.currencySymbol, '')
    value = replaceAll(value, '-', '')
    value = replaceAll(value, '(', '')
    value = replaceAll(value, ')', '')
    value = replaceAll(value, ' ', '')
    var pos = value.indexOf(parser.decimalSeparator);
    var hasDecimal = (pos >= 0);
    var caretAdjust = 0;
    
    //Check if the handling will be from left to right or right to left
    if (leftToRight) {
        //The left to right is based on the decimal separator position
        if (hasDecimal) {
            intPart = value.substr(0, pos);
            decPart = value.substr(pos + 1);
        } else {
            intPart = value;
        }
        if (isBlur && parser.decimalDigits > 0) {
            decPart = rpad(decPart, parser.decimalDigits, '0');
        }
    } else {
        //The right to left is based on a fixed decimal size
        var decimalDigits = parser.decimalDigits;
        value = replaceAll(value, parser.decimalSeparator, '');
        intPart = left(value, value.length - decimalDigits);
        decPart = lpad(right(value, decimalDigits), decimalDigits, '0');
    }
    var zero = onlySpecified(intPart + decPart, '0');

    //Validate the input
    if ((!isEmpty(intPart) && !onlyNumbers(intPart)) || (!isEmpty(decPart) && !onlyNumbers(decPart))) {
        control.value = control.oldValue;
        return true;
    }
    if (leftToRight && parser.decimalDigits >= 0 && decPart.length > parser.decimalDigits) {
        decPart = decPart.substring(0, parser.decimalDigits);
    }
    if (maxIntegerDigits >= 0 && intPart.length > maxIntegerDigits) {
        caretAdjust = maxIntegerDigits - intPart.length - 1;
        intPart = left(intPart, maxIntegerDigits);
    }
    //Check the sign
    if (zero) {
        isNegative = false;
    } else if (swapSign) {
        isNegative = !isNegative;
    }
    //Format the integer part with decimal separators
    if (!isEmpty(intPart)) {
        while (intPart.charAt(0) == '0') {
            intPart = intPart.substr(1);
        }
    }
    if (isEmpty(intPart)) {
        intPart = "0";
    }
    if ((parser.useGrouping) && (!isEmpty(parser.groupSeparator))) {
        var group, temp = "";
        for (var i = intPart.length; i > 0; i -= parser.groupSize) {
            group = intPart.substring(intPart.length - parser.groupSize);
            intPart = intPart.substring(0, intPart.length - parser.groupSize);
            temp = group + parser.groupSeparator + temp;
        }
        intPart = temp.substring(0, temp.length-1);
    }
    //Format the output
    var out = new StringBuffer();
    var oneFormatted = parser.format(isNegative ? -1 : 1);
    var appendEnd = true;
    pos = oneFormatted.indexOf('1');
    out.append(oneFormatted.substring(0, pos));
    out.append(intPart);
    
    //Build the output
    if (leftToRight) {
        if (toDecimal || !isEmpty(decPart)) {
            out.append(parser.decimalSeparator).append(decPart);
            appendEnd = !toDecimal;
        }
    } else {
        if (parser.decimalDigits > 0) {
            out.append(parser.decimalSeparator);
        }
        out.append(decPart);
    }
    
    if (appendEnd && oneFormatted.indexOf(")") >= 0) {
        out.append(")");
    }

    //Retrieve the caret    
    var caret = getCaret(control);
    var oldNonDigitsToCaret = nonDigitsToCaret(control.value, caret), hadSymbol;
    var caretToEnd = toDecimal || caret == null || caret == control.value.length;
    if (caret != null && !isBlur) { 
        hadSymbol = control.value.indexOf(parser.currencySymbol) >= 0 || control.value.indexOf(parser.decimalSeparator) >= 0;
    }
    
    //Update the value
    control.value = out.toString();
    
    if (caret != null && !isBlur) {    
        //If a currency symbol was inserted, set caret to end    
        if (!hadSymbol && ((value.indexOf(parser.currencySymbol) >= 0) || (value.indexOf(parser.decimalSeparator) >= 0))) {
            caretToEnd = true;
        }
        //Restore the caret
        if (!caretToEnd) {
            //Retrieve the new caret position
            var newNonDigitsToCaret = nonDigitsToCaret(control.value, caret);
            //There's no caret adjust when backspace was pressed
            if (isBackSpace) {
                caretAdjust = 0;
            }
            setCaret(control, caret + caretAdjust + newNonDigitsToCaret - oldNonDigitsToCaret);
        } else {
            setCaretToEnd(control);
        }
    }
    
    //Call the update function if present
    if (numberMask.updateFunction != null) {
        numberMask.updateFunction(numberMask);
    }

    return false;
}

//Function to check the text limit
function checkSizeLimit(control, isBlur) {
    var sizeLimit = control.sizeLimit;
    var max = sizeLimit.maxLength;
    var diff = max - control.value.length;
    if (control.value.length > max) {
        control.value = left(control.value, max);
        setCaretToEnd(control);
    }
    var size = control.value.length;
    var charsLeft = max - size;
    if (sizeLimit.output != null) {
        var text = sizeLimit.outputText;
        text = replaceAll(text, "${size}", size);
        text = replaceAll(text, "${left}", charsLeft);
        text = replaceAll(text, "${max}", max);
        setValue(sizeLimit.output, text);
    }
    if (isInstance(sizeLimit.updateFunction, Function)) {
        sizeLimit.updateFunction(control, size, max, charsLeft);
    }
    return true;
}

///////////////////////////////////////////////////////////////////////////////
// MaskField Type Classes

/*
 * General input field type
 */
function MaskField() {
    this.literal = false;
    this.input = false;
    
    //Returns the index up to where the texts matches this input
    this.upTo = function(text, fromIndex) {
        return -1;
    }
}

/*
 * Literal field type
 */
function Literal(text) {
    this.base = MaskField;
    this.base();
    this.text = text;
    this.literal = true;
    
    //Return if the character is in the text
    this.isAccepted = function(chr) {
        return onlySpecified(chr, this.text);
    }
    
    //Returns the index up to where the texts matches this input
    this.upTo = function(text, fromIndex) {
        return text.indexOf(this.text, fromIndex);
    }
}

/*
 * User input field type
 */
function Input(accepted, min, max, padFunction, optional) {
    this.base = MaskField;
    this.base();
    this.accepted = accepted;
    if (min != null && max == null) {
        max = min;
    }
    this.min = min || 1;
    this.max = max || -1;
    this.padFunction = padFunction || null;
    this.input = true;
    this.upper = false;
    this.lower = false;
    this.capitalize = false;
    this.optional = booleanValue(optional);

    //Ensures the min/max consistencies
    if (this.min < 1) {
        this.min = 1;
    }
    if (this.max == 0) {
        this.max = -1;
    }
    if ((this.max < this.min) && (this.max >= 0)) {
        this.max = this.min;
    }
    
    //Returns the index up to where the texts matches this input
    this.upTo = function(text, fromIndex) {
        text = text || "";
        fromIndex = fromIndex || 0;
        if (text.length < fromIndex) {
            return -1;
        }
        var toIndex = -1;
        for (var i = fromIndex; i < text.length; i++) {
            if (this.isAccepted(text.substring(fromIndex, i + 1))) {
                toIndex = i;
            } else {
                break;
            }
        }
        return toIndex;
    }

    //Tests whether this field accepts more than the given text     
    this.acceptsMoreText = function(text) {
        if (this.max < 0) return true; 
        return (text || "").length < this.max;
    }
    
    //Tests whether the text is accepted
    this.isAccepted = function(text) {
        return ((this.accepted == null) || onlySpecified(text, this.accepted)) && ((text.length <= this.max) || (this.max < 0));
    }

    //Tests whether the text length is ok
    this.checkLength = function(text) {
        return (text.length >= this.min) && ((this.max < 0) || (text.length <= this.max));
    }
    
    //Tests whether the text is complete
    this.isComplete = function(text) {
        text = String(text);
        if (isEmpty(text)) {
            return this.optional;
        }
        return text.length >= this.min;
    }

    //Apply the case transformations when necessary
    this.transformValue = function(text) {
        text = String(text);
        if (this.upper) {
            return text.toUpperCase();
        } else if (this.lower) {
            return text.toLowerCase();
        } else if (this.capitalize) {
            return capitalize(text);
        } else {
            return text;
        }
    }
    
    //Pads the text
    this.pad = function(text) {
        text = String(text);
        if ((text.length < this.min) && ((this.max >= 0) || (text.length <= this.max)) || this.max < 0) {
            var value;
            if (this.padFunction != null) {
                value = this.padFunction(text, this.min, this.max);
            } else {
                value = text;
            }
            //Enforces padding
            if (value.length < this.min) {
                var padChar = ' ';
                if (this.accepted == null || this.accepted.indexOf(' ') > 0) {
                    padChar = ' ';
                } else if (this.accepted.indexOf('0') > 0) {
                    padChar = '0';
                } else {
                    padChar = this.accepted.charAt(0);
                }
                return left(lpad(value, this.min, padChar), this.min);
            } else {
                //If has no max limit
                return value;
            }
        } else {
            return text;
        }
    }
}

/*
 * Lowercased input field type
 */
function Lower(accepted, min, max, padFunction, optional) {
    this.base = Input;
    this.base(accepted, min, max, padFunction, optional);
    this.lower = true;
}

/*
 * Uppercased input field type
 */
function Upper(accepted, min, max, padFunction, optional) {
    this.base = Input;
    this.base(accepted, min, max, padFunction, optional);
    this.upper = true;
}

/*
 * Capitalized input field type
 */
function Capitalize(accepted, min, max, padFunction, optional) {
    this.base = Input;
    this.base(accepted, min, max, padFunction, optional);
    this.capitalize = true;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * The FieldBuilder class is used to build input fields
 */
function FieldBuilder() {
    
    /**
     * Builds a literal input with the given text
     */
    this.literal = function(text) {
        return new Literal(text);
    }

    /* 
     * Build an input field with the given accepted chars. 
     * All other parameters are optional 
     */
    this.input = function(accepted, min, max, padFunction, optional) {
        return new Input(accepted, min, max, padFunction, optional);
    }

    /* 
     * Build an uppercase input field with the given accepted chars. 
     * All other parameters are optional 
     */
    this.upper = function(accepted, min, max, padFunction, optional) {
        return new Upper(accepted, min, max, padFunction, optional);
    }

    /* 
     * Build an lowercase field with the given accepted chars. 
     * All other parameters are optional 
     */
    this.lower = function(accepted, min, max, padFunction, optional) {
        return new Lower(accepted, min, max, padFunction, optional);
    }

    /* 
     * Build an capitalized input field with the given accepted chars. 
     * All other parameters are optional 
     */
    this.capitalize = function(accepted, min, max, padFunction, optional) {
        return new Capitalize(accepted, min, max, padFunction, optional);
    }
    
    /* 
     * Build an input field accepting any chars.
     * All parameters are optional 
     */
    this.inputAll = function(min, max, padFunction, optional) {
        return this.input(null, min, max, padFunction, optional);
    }

    /* 
     * Build an uppercase input field accepting any chars.
     * All parameters are optional 
     */
    this.upperAll = function(min, max, padFunction, optional) {
        return this.upper(null, min, max, padFunction, optional);
    }

    /* 
     * Build an lowercase field accepting any chars.
     * All parameters are optional 
     */
    this.lowerAll = function(min, max, padFunction, optional) {
        return this.lower(null, min, max, padFunction, optional);
    }

    /* 
     * Build an capitalized input field accepting any chars.
     * All parameters are optional 
     */
    this.capitalizeAll = function(min, max, padFunction, optional) {
        return this.capitalize(null, min, max, padFunction, optional);
    }
    
    /* 
     * Build an input field accepting only numbers.
     * All parameters are optional 
     */
    this.inputNumbers = function(min, max, padFunction, optional) {
        return this.input(JST_CHARS_NUMBERS, min, max, padFunction, optional);
    }
    
    /* 
     * Build an input field accepting only letters.
     * All parameters are optional 
     */
    this.inputLetters = function(min, max, padFunction, optional) {
        return this.input(JST_CHARS_LETTERS, min, max, padFunction, optional);
    }

    /* 
     * Build an uppercase input field accepting only letters.
     * All parameters are optional 
     */
    this.upperLetters = function(min, max, padFunction, optional) {
        return this.upper(JST_CHARS_LETTERS, min, max, padFunction, optional);
    }

    /* 
     * Build an lowercase input field accepting only letters.
     * All parameters are optional 
     */
    this.lowerLetters = function(min, max, padFunction, optional) {
        return this.lower(JST_CHARS_LETTERS, min, max, padFunction, optional);
    }

    /* 
     * Build an capitalized input field accepting only letters.
     * All parameters are optional 
     */
    this.capitalizeLetters = function(min, max, padFunction, optional) {
        return this.capitalize(JST_CHARS_LETTERS, min, max, padFunction, optional);
    }
}
//Create a FieldBuilder instance
var fieldBuilder = new FieldBuilder();

///////////////////////////////////////////////////////////////////////////////
/*
 * The MaskBuilder class is used to build masks in a easier to use way
 */
function MaskBuilder() {

    /* 
     * Parses a String, building a mask from it.
     * The following characters are recognized
     * #, 0 or 9 - A number               
     * a or A - A letter
     * ? or _ - Any character
     * l or L - A lowercase letter
     * u or U - An uppercase letter
     * c or C - A capitalized letter
     * \\ - A backslash
     * \#, \0, ... - Those literal characters
     * any other character - A literal text
     */
    this.parse = function(string) {
        if (string == null || !isInstance(string, String)) {
            return this.any();
        }
        var fields = new Array();
        var start = null;
        var lastType = null;
        //helper function
        var switchField = function(type, text) {
            switch (type) {
                case '_':
                    return fieldBuilder.inputAll(text.length);
                case '#':
                    return fieldBuilder.inputNumbers(text.length);
                case 'a':
                    return fieldBuilder.inputLetters(text.length);
                case 'l': 
                    return fieldBuilder.lowerLetters(text.length);
                case 'u': 
                    return fieldBuilder.upperLetters(text.length);
                case 'c': 
                    return fieldBuilder.capitalizeLetters(text.length);
                default:
                    return fieldBuilder.literal(text);
            }
        }
        //Let's parse the string
        for (var i = 0; i < string.length; i++) {
            var c = string.charAt(i);
            if (start == null) {
                start = i;
            }
            var type;
            var literal = false;
            //Checks for the escaping backslash
            if (c == '\\') {
                if (i == string.length - 1) {
                    break;
                }
                string = left(string, i) + mid(string, i + 1);
                c = string.charAt(i);
                literal = true;
            }
            //determine the field type
            if (literal) {
                type = '?';
            } else {
                switch (c) {
                    case '?': case '_':
                        type = '_';
                        break;
                    case '#': case '0': case '9':
                        type = '#';
                        break;
                    case 'a': case 'A':
                        type = 'a';
                        break;
                    case 'l': case 'L':
                        type = 'l';
                        break;
                    case 'u': case 'U':
                        type = 'u';
                        break;
                    case 'c': case 'C':
                        type = 'c';
                        break;
                    default:
                        type = '?';
                }
            }
            if (lastType != type && lastType != null) {
                var text = string.substring(start, i);
                fields[fields.length] = switchField(lastType, text);
                start = i;
                lastType = type;
            } else {
                lastType = type
            }
        }
        //Use the last field
        if (start < string.length) {
            var text = string.substring(start);
            fields[fields.length] = switchField(lastType, text);
        }
        return fields;
    }
    
    /* 
     * Build a mask that accepts the given characters
     * May also specify the max length
     */
    this.accept = function(accepted, max) {
        return [fieldBuilder.input(accepted, max)];
    }

    /* 
     * Build a mask that accepts any characters
     * May also specify the max length
     */
    this.any = function(max) {
        return [fieldBuilder.any(max)];
    }

    /* 
     * Build a numeric mask
     * May also specify the max length
     */
    this.numbers = function(max) {
        return [fieldBuilder.inputNumbers(max)];
    }
    
    /* 
     * Build a decimal input mask
     */
    this.decimal = function() {
        var decimalField = fieldBuilder.inputNumbers();
        decimalField.optional = true;
        return [fieldBuilder.inputNumbers(), JST_FIELD_DECIMAL_SEPARATOR, decimalField];
    }
    
    /* 
     * Build a mask that only accepts letters
     * May also specify the max length
     */
    this.letters = function(max) {
        return [fieldBuilder.inputLetters(max)];
    }
    
    /* 
     * Build a mask that only accepts uppercase letters
     * May also specify the max length
     */
    this.upperLetters = function(max) {
        return [fieldBuilder.upperLetters(max)];
    }
    
    /* 
     * Build a mask that only accepts lowercase letters
     * May also specify the max length
     */
    this.lowerLetters = function(max) {
        return [fieldBuilder.lowerLetters(max)];
    }
    
    /* 
     * Build a mask that only accepts capitalized letters
     * May also specify the max length
     */
    this.capitalizeLetters = function(max) {
        return [fieldBuilder.capitalizeLetters(max)];
    }
}
//Create a MaskBuilder instance
var maskBuilder = new MaskBuilder();
