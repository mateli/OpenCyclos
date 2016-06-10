/*
 * JavaScriptUtil version 2.2.4
 *
 * The JavaScriptUtil is a set of misc functions used by the other scripts
 *
 * Author: Luis Fernando Planella Gonzalez (lfpg.dev at gmail dot com)
 * Home Page: http://javascriptools.sourceforge.net
 *
 * JavaScripTools is distributed under the GNU Lesser General Public License (LGPL).
 * For more information, see http://www.gnu.org/licenses/lgpl-2.1.txt
 */

///////////////////////////////////////////////////////////////////////////////
// Constants
var JST_CHARS_NUMBERS = "0123456789";
var JST_CHARS_LOWER = "";
var JST_CHARS_UPPER = "";
//Scan the upper and lower characters
for (var i = 50; i < 500; i++) {
    var c = String.fromCharCode(i);
    var lower = c.toLowerCase();
    var upper = c.toUpperCase();
    if (lower != upper) {
        JST_CHARS_LOWER += lower;
        JST_CHARS_UPPER += upper;
    }
}
var JST_CHARS_LETTERS = JST_CHARS_LOWER + JST_CHARS_UPPER;
var JST_CHARS_ALPHA = JST_CHARS_LETTERS + JST_CHARS_NUMBERS;
var JST_CHARS_BASIC_LOWER = "abcdefghijklmnopqrstuvwxyz";
var JST_CHARS_BASIC_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
var JST_CHARS_BASIC_LETTERS = JST_CHARS_BASIC_LOWER + JST_CHARS_BASIC_UPPER;
var JST_CHARS_BASIC_ALPHA = JST_CHARS_BASIC_LETTERS + JST_CHARS_NUMBERS;
var JST_CHARS_WHITESPACE = " \t\n\r";

//Number of milliseconds in a second
var MILLIS_IN_SECOND = 1000;

//Number of milliseconds in a minute
var MILLIS_IN_MINUTE = 60 * MILLIS_IN_SECOND;

//Number of milliseconds in a hour
var MILLIS_IN_HOUR = 60 * MILLIS_IN_MINUTE;

//Number of milliseconds in a day
var MILLIS_IN_DAY = 24 * MILLIS_IN_HOUR;

//Date field: milliseconds
var JST_FIELD_MILLISECOND = 0;

//Date field: seconds
var JST_FIELD_SECOND = 1;

//Date field: minutes
var JST_FIELD_MINUTE = 2;

//Date field: hours
var JST_FIELD_HOUR = 3;

//Date field: days
var JST_FIELD_DAY = 4;

//Date field: months
var JST_FIELD_MONTH = 5;

//Date field: years
var JST_FIELD_YEAR = 6;

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the reference to the named object
 * Parameters:
 *     name: The object's name. When a reference, return it.
 *     source: The object where to search the name
 * Returns: The reference, or null if not found
 */
function getObject(objectName, source) {
    if (isEmpty(objectName)) {
        return null;
    }
    if (!isInstance(objectName, String)) {
        return objectName;
    }
    if(isEmpty(source)) {
        source = self;
    }
    //Check if the source is a reference or a name
    if(isInstance(source, String)) {
        //It's a name. Try to find it on a frame
        sourceName = source;
        source = self.frames[sourceName];
        if (source == null) source = parent.frames[sourceName];
        if (source == null) source = top.frames[sourceName];
        if (source == null) source = getObject(sourceName);
        if (source == null) return null;
    }
    //Get the document
    var document = (source.document) ? source.document : source;
    //Check the browser's type
    if (document.getElementById) {
        //W3C
        var collection = document.getElementsByName(objectName);
        if (collection.length == 1) return collection[0];
        if (collection.length > 1) {
            //When the collection itself is an array, return it
            if (typeof(collection) == "array") {
                return collection;
            }
            //Copy the collection nodes to an array
            var ret = new Array(collection.length);
            for (var i = 0; i < collection.length; i++) {
                ret[i] = collection[i];
            }
            return ret;
        }
        return document.getElementById(objectName);
    } else {
        //Old Internet Explorer
        if (document[objectName]) return document[objectName];
        if (document.all[objectName]) return document.all[objectName];
        if (source[objectName]) return source[objectName];
    }
    return null;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns if the object is an instance of the specified class
 * Parameters:
 *     object: The object
 *     clazz: The class
 * Returns: Is the object an instance of the class?
 */
function isInstance(object, clazz) {
    if ((object == null) || (clazz == null)) {
        return false;
    }
    if (object instanceof clazz) {
        return true;
    }
    if ((clazz == String) && (typeof(object) == "string")) {
        return true;
    }
    if ((clazz == Number) && (typeof(object) == "number")) {
        return true;
    }
    if ((clazz == Array) && (typeof(object) == "array")) {
        return true;
    }
    if ((clazz == Function) && (typeof(object) == "function")) {
        return true;
    }
    var base = object.base;
    while (base != null) {
        if (base == clazz) {
            return true;
        }
        base = base.base;
    }
    return false;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns true if the object value represents a true value
 * Parameters:
 *     object: The input object. It will be treated as a string.
 *        if the string starts with any of the characters on trueChars, it 
 *        will be considered true. False otherwise.
 *     trueChars: The initial characters for the object be a true value, 
 *        ingoring case. Default: 1, Y, N or S.
 * Returns: The boolean value
 */
function booleanValue(object, trueChars) {
    if (object == true || object == false) {
        return object;
    } else {
        object = String(object);
        if (object.length == 0) {
            return false;
        } else {
            var first = object.charAt(0).toUpperCase();
            trueChars = isEmpty(trueChars) ? "T1YS" : trueChars.toUpperCase();
            return trueChars.indexOf(first) != -1
        }
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns if the object is undefined
 * Parameters:
 *     object: The object to test
 * Returns: Is the object undefined?
 */
function isUndefined(object) {
    return typeof(object) == "undefined";
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Invokes the function with the given arguments
 * Parameters:
 *     functionName: The function name
 *     args: The arguments. If is not an array, uses a single 
 *                argument. If null, uses no arguments.
 * Returns: The invocation return value
 */
function invoke(functionName, args) {
    var arguments;
    if (args == null || isUndefined(args)) {
        arguments = "()";
    } else if (!isInstance(args, Array)) {
        arguments = "(args)";
    } else {
        arguments = "(";
        for (var i = 0; i < args.length; i++) {
            if (i > 0) {
                arguments += ",";
            }
            arguments += "args[" + i + "]";
        }
        arguments += ")";
    }
    return eval(functionName + arguments);
}


///////////////////////////////////////////////////////////////////////////////
/*
 * Makes an object invoke a function as if it were a method of his
 * Parameters:
 *     object: The target object
 *     method: The function reference
 *     args: The arguments. If is not an array, uses a single 
 *                argument. If null, uses no arguments.
 * Returns: The invocation return value
 */
function invokeAsMethod(object, method, args) {
    return method.apply(object, args);
}

///////////////////////////////////////////////////////////////////////////////
/**
 * Ensures the given argument is an array.
 * When null or undefined, returns an empty array.
 * When an array return it.
 * Otherwise, return an array with the argument in
 * Parameters:
 *     object: The object
 * Returns: The array
 */
function ensureArray(object) {
    if (typeof(object) == 'undefined' || object == null) {
        return [];
    }
    if (object instanceof Array) {
        return object;
    }
    return [object];
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the index of the object in array, -1 if it's not there...
 * Parameters:
 *     object: The object to search
 *     array: The array where to search
 *     startingAt: The index where to start the search (optional)
 * Returns: The index
 */
function indexOf(object, array, startingAt) {
    if ((object == null) || !(array instanceof Array)) {
        return -1;
    }
    if (startingAt == null) {
        startingAt = 0;
    }
    for (var i = startingAt; i < array.length; i++) {
        if (array[i] == object) {
            return i;
        }
    }
    return -1;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns if the object is in the array
 * Parameters:
 *     object: The object to search
 *     array: The array where to search
 * Returns: Is the object in the array?
 */
function inArray(object, array) {
    return indexOf(object, array) >= 0;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Removes all occurrences of the given object or objects from an array.
 * Parameters:
 *     array: The array
 *     object1 .. objectN: The objects to remove
 * Returns: The new array
 */
function removeFromArray(array) {
    if (!isInstance(array, Array)) {
        return null;
    }
    var ret = new Array();
    var toRemove = removeFromArray.arguments.slice(1);
    for (var i = 0; i < array.length; i++) {
        var current = array[i];
        if (!inArray(current, toRemove)) {
            ret[ret.length] = current;
        }
    }
    return ret;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the concatenation of two arrays
 * Parameters:
 *     array1 ... arrayN: The arrays to concatenate
 * Returns: The concatenation of the two arrays
 */
function arrayConcat() {
    var ret = [];
    for (var i = 0; i < arrayConcat.arguments.length; i++) {
        var current = arrayConcat.arguments[i];
        if (!isEmpty(current)) {
            if (!isInstance(current, Array)) {
                current = [current]
            }
            for (j = 0; j < current.length; j++) {
                ret[ret.length] = current[j];
            }
        }
    }
    return ret;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the concatenation of two arrays
 * Parameters:
 *     array1: The array first array
 *     array1: The array second array
 * Returns: The concatenation of the two arrays
 */
function arrayEquals(array1, array2) {
    if (!isInstance(array1, Array) || !isInstance(array2, Array)) {
        return false;
    }
    if (array1.length != array2.length) {
        return false;
    }
    for (var i = 0; i < array1.length; i++) {
        if (array1[i] != array2[i]) {
            return false;
        }
    }
    return true;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Checks or unchecks all the checkboxes
 * Parameters:
 *     object: The reference for the checkbox or checkbox array.
 *     flag: If true, checks, otherwise, unchecks the checkboxes
 */
function checkAll(object, flag) {
    //Check if is the object name    
    if (typeof(object) == "string") {
        object = getObject(object);
    }
    if (object != null) {
        if (!isInstance(object, Array)) {
            object = [object];
        }
        for (i = 0; i < object.length; i++) {
            object[i].checked = flag;
        }
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Adds an event listener to the object.
 * Parameters:
 *     object: The object that generates events
 *     eventName: A name like keypress, blur, etc
 *     handler: A function that will handle the event
 */
function observeEvent(object, eventName, handler) {
    object = getObject(object);
    if (object != null) {
        if (object.addEventListener) {
            object.addEventListener(eventName, function(e) {return invokeAsMethod(object, handler, [e]);}, false);
        } else if (object.attachEvent) {
            object.attachEvent("on" + eventName, function() {return invokeAsMethod(object, handler, [window.event]);});
        } else {
            object["on" + eventName] = handler;
        }
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Return the keycode of the given event
 * Parameters:
 *     event: The interface event
 */
function typedCode(event) {
    var code = 0;
    if (event == null && window.event) {
        event = window.event;
    }
    if (event != null) {
        if (event.keyCode) {
            code = event.keyCode;
        } else if (event.which) {
            code = event.which;
        }
    }
    return code;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Stops the given event of propagating
 * Parameters:
 *     event: The interface event
 */
function stopPropagation(event) {
    if (event == null && window.event) {
        event = window.event;
    }
    if (event != null) {
        if (event.stopPropagation != null) {
            event.stopPropagation();
        } else if (event.cancelBubble !== null) {
            event.cancelBubble = true;
        }
    }
    return false;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Prevents the default action for the given event
 * Parameters:
 *     event: The interface event
 */
function preventDefault(event) {
    if (event == null && window.event) {
        event = window.event;
    }
    if (event != null) {
        if (event.preventDefault != null) {
            event.preventDefault();
        } else if (event.returnValue !== null) {
            event.returnValue = false;
        }
    }
    return false;
}

/*
 * Prepares the input object to use caret or selection manipulation
 * functions on Internet Explorer. Should be called only once on each input
 * Parameters:
 *     object: The reference for the input
 */
function prepareForCaret(object) {
    object = getObject(object);
    if (object == null || !object.type) {
        return null;
    }
    if (object.createTextRange) {
        var handler = function() {
            object.caret = document.selection.createRange().duplicate();
        }
        object.attachEvent("onclick", handler);
        object.attachEvent("ondblclick", handler);
        object.attachEvent("onselect", handler);
        object.attachEvent("onkeyup", handler);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Return if an object supports caret operations
 * Parameters:
 *     object: The reference for the input
 * Returns: Are caret operations supported?
 */
function isCaretSupported(object) {
    object = getObject(object);
    if (object == null || !object.type) {
        return false;
    }
    //Opera 8- claims to support setSelectionRange, but it does not works for caret operations, only selection
    if (navigator.userAgent.toLowerCase().indexOf("opera") >= 0) {
        return false;
    }
    return object.setSelectionRange != null || object.createTextRange != null;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Return if an object supports input selection operations
 * Parameters:
 *     object: The reference for the input
 * Returns: Are input selection operations supported?
 */
function isInputSelectionSupported(object) {
    object = getObject(object);
    if (object == null || !object.type) {
        return false;
    }
    return object.setSelectionRange != null || object.createTextRange != null;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns an input's selected text. 
 * For IE, requires prepareForCaret() to be first called on the object
 * Parameters:
 *     object: The reference for the input
 * Returns: The selected text
 */
function getInputSelection(object) {
    object = getObject(object);
    if (object == null || !object.type) {
        return null;
    }
    try {
        if (object.createTextRange && object.caret) {
            return object.caret.text;
        } else if (object.setSelectionRange) {
            var selStart = object.selectionStart;
            var selEnd = object.selectionEnd;
            return object.value.substring(selStart, selEnd);
        }
    } catch (e) {
        // Ignore
    }
    return "";
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Sets the selection range on an input field.
 * For IE, requires prepareForCaret() to be first called on the object
 * Parameters:
 *     object: The reference for the input
 *     start: The selection start
 *     end: The selection end
 * Returns: An array with 2 integers: start and end
 */
function getInputSelectionRange(object) {
    object = getObject(object);
    if (object == null || !object.type) {
        return null;
    }
    try {
        if (object.selectionEnd) {
            return [object.selectionStart, object.selectionEnd];
        } else if (object.createTextRange && object.caret) {
            var end = getCaret(object);
            return [end - object.caret.text.length, end];
        }
    } catch (e) {
        // Ignore
    }
    return null;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Sets the selection range on an input field
 * Parameters:
 *     object: The reference for the input
 *     start: The selection start
 *     end: The selection end
 */
function setInputSelectionRange(object, start, end) {
    object = getObject(object);
    if (object == null || !object.type) {
        return;
    }
    try {
        if (start < 0) {
            start = 0;
        }
        if (end > object.value.length) {
            end = object.value.length;
        }
        if (object.setSelectionRange) {
            object.focus();
            object.setSelectionRange(start, end);
        } else if (object.createTextRange) {
            object.focus();
            var range;
            if (object.caret) {
                range = object.caret;
                range.moveStart("textedit", -1);
                range.moveEnd("textedit", -1);
            } else {
                range = object.createTextRange();
            }
            range.moveEnd('character', end);
            range.moveStart('character', start);
            range.select();
        }
    } catch (e) {
        // Ignore
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the caret position. When a range is selected, returns the range end.
 * For IE, requires prepareForCaret() to be first called on the object
 * Parameters:
 *     object: The reference for the input
 * Returns: The position
 */
function getCaret(object) {
    object = getObject(object);
    if (object == null || !object.type) {
        return null;
    }
    try {
        if (object.createTextRange && object.caret) {
            var range = object.caret.duplicate();
            range.moveStart('textedit', -1);
            return range.text.length;
        } else if (object.selectionStart || object.selectionStart == 0) {
            return object.selectionStart;
        }
    } catch (e) {
    }
    return null;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Sets the caret to the specified position
 * Parameters:
 *     object: The reference for the input
 *     pos: The position
 */
function setCaret(object, pos) {
    setInputSelectionRange(object, pos, pos);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Sets the caret to the text end
 * Parameters:
 *     object: The reference for the input
 */
function setCaretToEnd(object) {
    object = getObject(object);
    if (object == null || !object.type) {
        return;
    }
    try {
        if (object.createTextRange) {
          var range = object.createTextRange();
          range.collapse(false);
          range.select();
        } else if (object.setSelectionRange) {
          var length = object.value.length;
          object.setSelectionRange(length, length);
          object.focus();
        }
    } catch (e) {
        // Ignore
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Sets the caret to the text start
 * Parameters:
 *     object: The reference for the input
 */
function setCaretToStart(object) {
    object = getObject(object);
    if (object == null || !object.type) {
        return;
    }
    try {
        if (object.createTextRange) {
          var range = object.createTextRange();
          range.collapse(true);
          range.select();
        } else if (object.setSelectionRange) {
          object.focus();
          object.setSelectionRange(0, 0);
        }
    } catch (e) {
        // Ignore
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Selects a given string on the input text
 * Parameters:
 *     object: The reference for the input
 *     string: The string to select
 */
function selectString(object, string) {
    if (isInstance(object, String)) {
        object = getObject(object);
    }
    if (object == null || !object.type) {
        return;
    }
    var match = new RegExp(string, "i").exec(object.value);
    if (match) {
        setInputSelectionRange(object, match.index, match.index + match[0].length);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Replaces the selected text for a new one. On IE, only works if the object has focus.
 * For IE, requires prepareForCaret() to be first called on the object
 * Parameters:
 *     object: The reference for the input
 *     string: The new text
 */
function replaceSelection(object, string) {
    object = getObject(object);
    if (object == null || !object.type) {
        return;
    }
    if (object.setSelectionRange) {
        var selectionStart = object.selectionStart;
        var selectionEnd = object.selectionEnd;
        object.value = object.value.substring(0, selectionStart) + string + object.value.substring(selectionEnd);
        if (selectionStart != selectionEnd) {
            setInputSelectionRange(object, selectionStart, selectionStart + string.length);
        } else {
            setCaret(object, selectionStart + string.length);
        }
    } else if (object.createTextRange && object.caret) {
        object.caret.text = string;
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Clears the array of options to a given select field
 * Parameters:
 *     select: The reference for the select, or the select name
 * Returns: The array of removed options
 */
function clearOptions(select) {
    select = getObject(select);
    var ret = new Array();
    if (select != null) {
        for (var i = 0; i < select.options.length; i++) {
            var option = select.options[i];
            ret[ret.length] = new Option(option.text, option.value);
        }
        select.options.length = 0;
    }
    return ret;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Adds an options to a given select field
 * Parameters:
 *     select: The reference for the select, or the select name
 *     option: The Option instance
 *     sort: Will sort the options? Default: false
 *     textProperty: The object property, when not an Option, to read the text. Defaults to "text"
 *     valueProperty: The object property, when not an Option, to read the value. Defaults to "value"
 *     selectedProperty: The object property, when not an Option, to read if the option is selected. Defaults to "selected"
 */
function addOption(select, option, sort, textProperty, valueProperty, selectedProperty) {
    select = getObject(select);
    if (select == null || option == null) {
        return;
    }
    
    textProperty = textProperty || "text";
    valueProperty = valueProperty || "value";
    selectedProperty = selectedProperty || "selected"
    if (isInstance(option, Map)) {
        option = option.toObject();
    }
    if (isUndefined(option[valueProperty])) {
        valueProperty = textProperty;
    }
    var selected = false;
    if (!isUndefined(option[selectedProperty])) {
        selected = option[selectedProperty];
    }
    option = new Option(option[textProperty], option[valueProperty], selected, selected);

    select.options[select.options.length] = option;

    if (booleanValue(sort)) {
        sortOptions(select);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Adds the array of options to a given select field
 * Parameters:
 *     select: The reference for the select, or the select name
 *     options: The array of Option instances
 *     sort: Will sort the options? Default: false
 *     textProperty: The object property, when not an Option, to read the text. Defaults to "text"
 *     valueProperty: The object property, when not an Option, to read the value. Defaults to "value"
 *     selectedProperty: The object property, when not an Option, to read if the option is selected. Defaults to "selected"
 */
function addOptions(select, options, sort, textProperty, valueProperty, selectedProperty) {
    select = getObject(select);
    if (select == null) {
        return;
    }
    for (var i = 0; i < options.length; i++) {
        addOption(select, options[i], false, textProperty, valueProperty, selectedProperty);
    }
    if (!select.multiple && select.selectedIndex < 0 && select.options.length > 0) {
        select.selectedIndex = 0;
    }
    if (booleanValue(sort)) {
        sortOptions(select);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Compares two options
 * Parameters:
 *     opt1: The first option
 *     opt2: The second option
 */
function compareOptions(opt1, opt2) {
    if (opt1 == null && opt2 == null) {
        return 0;
    }
    if (opt1 == null) {
        return -1;
    }
    if (opt2 == null) {
        return 1;
    }
    if (opt1.text == opt2.text) {
        return 0;
    } else if (opt1.text > opt2.text) {
        return 1;
    } else {
        return -1;
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Sets the array of options to a given select field
 * Parameters:
 *     select: The reference for the select, or the select name
 *     options: The array of Option instances
 *     addEmpty: A flag indicating an empty option should be added. Defaults to false
 *     textProperty: The object property, when not an Option, to read the text. Defaults to "text"
 *     valueProperty: The object property, when not an Option, to read the value. Defaults to "value"
 *     selectedProperty: The object property, when not an Option, to read if the option is selected. Defaults to "selected"
 * Returns: The original Options array
 */
function setOptions(select, options, addEmpty, sort, textProperty, valueProperty, selectedProperty) {
    select = getObject(select);
    var ret = clearOptions(select);
    var addEmptyIsString = isInstance(addEmpty, String);
    if (addEmptyIsString || booleanValue(addEmpty)) {
        select.options[0] = new Option(addEmptyIsString ? addEmpty : "");
    }
    addOptions(select, options, sort, textProperty, valueProperty, selectedProperty);
    return ret;
}


///////////////////////////////////////////////////////////////////////////////
/*
 * Sorts the array of options to a given select field
 * Parameters:
 *     select: The reference for the select, or the select name
 *     sortFunction The sortFunction to use. Defaults to the default sort function
 */
function sortOptions(select, sortFunction) {
    select = getObject(select);
    if (select == null) {
        return;
    }
    var options = clearOptions(select);
    if (isInstance(sortFunction, Function)) {
        options.sort(sortFunction);
    } else {
        options.sort(compareOptions);
    }
    setOptions(select, options);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Transfers the options from a select to another
 * Parameters:
 *     source: The reference for the source select, or the select name
 *     dest: The reference for the destination select, or the select name
 *     all: Will transfer all options (true) or the selected ones (false)? Default: false
 *     sort: Will sort the options? Default: false
 */
function transferOptions(source, dest, all, sort) {
    source = getObject(source);
    dest = getObject(dest);
    if (source == null || dest == null) {
        return;
    }
    if (booleanValue(all)) {
        addOptions(dest, clearOptions(source), sort);
    } else {
        var sourceOptions = new Array();
        var destOptions = new Array();
        for (var i = 0; i < source.options.length; i++) {
            var option = source.options[i];
            var options = (option.selected) ? destOptions : sourceOptions;
            options[options.length] = new Option(option.text, option.value);
        }
        setOptions(source, sourceOptions, false, sort);
        addOptions(dest, destOptions, sort);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Gets the value of an element
 * Parameters:
 *     object: The reference for the element
 * Returns: The value or an Array containing the values, if there's more than one
 */
function getValue(object) {
    object = getObject(object);
    if (object == null) {
        return null;
    }

    //Check if object is an array
    if (object.length && !object.type) {
        var ret = new Array();
        for (var i = 0; i < object.length; i++) {
            var temp = getValue(object[i]);
            if (temp != null) {
                ret[ret.length] = temp;
            }
        }
        return ret.length == 0 ? null : ret.length == 1 ? ret[0] : ret;
    }

    //Check the object type
    if (object.type) {
        //Select element
        if (object.type.indexOf("select") >= 0) {
            var ret = new Array();
            if (!object.multiple && object.selectedIndex < 0 && object.options.length > 0) {
                //On konqueror, if no options is marked as selected, not even the first one will return as selected
                ret[ret.length] = object.options[0].value;
            } else {
                for (i = 0; i < object.options.length; i++) {
                    var option = object.options[i];
                    if (option.selected) {
                        ret[ret.length] = option.value;
                        if (!object.multiple) {
                            break;
                        }
                    }
                }
            }
            return ret.length == 0 ? null : ret.length == 1 ? ret[0] : ret;
        }
    
        //Radios and checkboxes
        if (object.type == "radio" || object.type == "checkbox") {
            return booleanValue(object.checked) ? object.value : null;
        } else {
            //Other input elements
            return object.value;
        }
    } else if (typeof(object.innerHTML) != "undefined"){
        //Not an input
        return object.innerHTML;
    } else {
        return null;
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Sets the value of an element
 * Parameters:
 *     object: The reference for the element
 *     value: The value to be set
 */
function setValue(object, value) {

    //Validates the object
    if (object == null) {
        return;
    }
    
    //Check if is the object name    
    if (typeof(object) == "string") {
        object = getObject(object);
    }

    //Ensures the array is made of strings
    var values = ensureArray(value);
    for (var i = 0; i < values.length; i++) {
        values[i] = values[i] == null ? "" : "" + values[i];
    }
    
    //Check if object is an array
    if (object.length && !object.type) {
        while (values.length < object.length) {
            values[values.length] = "";
        }
        for (var i = 0; i < object.length; i++) {
            var obj = object[i];
            setValue(obj, inArray(obj.type, ["checkbox", "radio"]) ? values : values[i]);
        }
        return;
    }
    //Check the object type
    if (object.type) {
        //Check the input type
        if (object.type.indexOf("select") >= 0) {
            //Select element
            for (var i = 0; i < object.options.length; i++) {
                var option = object.options[i];
                option.selected = inArray(option.value, values);
            }
            return;
        } else if (object.type == "radio" || object.type == "checkbox") {
            //Radios and checkboxes 
            object.checked = inArray(object.value, values);
            return;
        } else {
            //Other input elements: get the first value
            object.value = values.length == 0 ? "" : values[0];
            return;
        }
    } else if (typeof(object.innerHTML) != "undefined"){
        //The object is not an input
        object.innerHTML = values.length == 0 ? "" : values[0];
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns an argument depending on the value of the first argument.
 * Example: decode(param, 1, 'A', 2, 'B', 'C'). When param == 1, returns 'A'.
 * When param == 2, return 'B'. Otherwise, return 'C'
 * Parameters:
 *     object: The object
 *     (additional parametes): The tested values and the return value
 * Returns: The correct argument
 */
function decode(object) {
    var args = decode.arguments;
    for (var i = 1; i < args.length; i += 2) {
        if (i < args.length - 1) {
            if (args[i] == object) {
                return args[i + 1];
            }
        } else {
            return args[i];
        }
    }
    return null;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns an argument depending on the boolean value of the prior argument.
 * Example: select(a > b, 'A', b > a, 'B', 'Equals'). When a > b, returns 'A'.
 * When b > a, return 'B'. Otherwise, return 'Equals'
 * Parameters:
 *     (additional parametes): The tested values and the return value
 * Returns: The correct argument
 */
function select() {
    var args = select.arguments;
    for (var i = 0; i < args.length; i += 2) {
        if (i < args.length - 1) {
            if (booleanValue(args[i])) {
                return args[i + 1];
            }
        } else {
            return args[i];
        }
    }
    return null;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns if an object is an empty instance ("", null, undefined or NaN)
 * Parameters:
 *     object: The object
 * Returns: Is the object an empty instance?
 */
function isEmpty(object) {
    return object == null || String(object) == "" || typeof(object) == "undefined" || (typeof(object) == "number" && isNaN(object));
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the object if not empty (according to the isEmpty function), or the
 *     second parameter otherwise
 * Parameters:
 *     object: The object
 *     emptyValue: The object returned if the first is empty
 * Returns: The first object if is not empty, otherwise the second one
 */
function ifEmpty(object, emptyValue) {
    return isEmpty(object) ? emptyValue : object;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the object if not null, or the second parameter otherwise
 * Parameters:
 *     object: The object
 *     nullValue: The object returned if the first is null
 * Returns: The first object if is not empty, otherwise the second one
 */
function ifNull(object, nullValue) {
    return object == null ? nullValue : object;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Replaces all the occurences in the string
 * Parameters:
 *     string: The string
 *     find: Text to be replaced
 *     replace: Text to replace the previous
 * Returns: The new string
 */
function replaceAll(string, find, replace) {
    return String(string).split(find).join(replace);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Repeats the String a number of times
 * Parameters:
 *     string: The string
 *     times: How many times?
 * Returns: The new string
 */
function repeat(string, times) {
    var ret = "";
    for (var i = 0; i < Number(times); i++) {
        ret += string;
    }
    return ret;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Removes all specified characters on the left side
 * Parameters:
 *     string: The string
 *     chars: The string containing all characters to be removed. Default: JST_CHARS_WHITESPACE
 * Returns: The new string
 */
function ltrim(string, chars) {
    string = string ? String(string) : "";
    chars = chars || JST_CHARS_WHITESPACE;
    var pos = 0;
    while (chars.indexOf(string.charAt(pos)) >= 0 && (pos <= string.length)) {
        pos++;
    }
    return string.substr(pos);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Removes all specified characters on the right side
 * Parameters:
 *     string: The string
 *     chars: The string containing all characters to be removed. Default: JST_CHARS_WHITESPACE
 * Returns: The new string
 */
function rtrim(string, chars) {
    string = string ? String(string) : "";
    chars = chars || JST_CHARS_WHITESPACE;
    var pos = string.length - 1;
    while (chars.indexOf(string.charAt(pos)) >= 0 && (pos >= 0)) {
        pos--;
    }
    return string.substring(0, pos + 1);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Removes all whitespaces on both left and right sides
 * Parameters:
 *     string: The string
 *     chars: The string containing all characters to be removed. Default: JST_CHARS_WHITESPACE
 * Returns: The new string
 */
function trim(string, chars) {
    chars = chars || JST_CHARS_WHITESPACE;
    return ltrim(rtrim(string, chars), chars);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Make the string have the specified length, completing with the specified 
 * character on the left. If the String is greater than the specified size, 
 * it is truncated to it, using the leftmost characters
 * Parameters:
 *     string: The string
 *     size: The string size
 *     chr: The character that will fill the string
 * Returns: The new string
 */
function lpad(string, size, chr) {
    string = String(string);
    if (size < 0) {
        return "";
    }
    if (isEmpty(chr)) {
        chr = " ";
    } else {
        chr = String(chr).charAt(0);
    }
    while (string.length < size) {
        string = chr + string;
    }
    return left(string, size);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Make the string have the specified length, completing with the specified 
 * character on the right. If the String is greater than the specified size, 
 * it is truncated to it, using the leftmost characters
 * Parameters:
 *     string: The string
 *     size: The string size
 *     chr: The character that will fill the string
 * Returns: The new string
 */
function rpad(string, size, chr) {
    string = String(string);
    if (size <= 0) {
        return "";
    }
    chr = String(chr);
    if (isEmpty(chr)) {
        chr = " ";
    } else {
        chr = chr.charAt(0);
    }
    while (string.length < size) {
        string += chr;
    }
    return left(string, size);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Removes the specified number of characters 
 * from a string after an initial position
 * Parameters:
 *     string: The string
 *     pos: The initial position
 *     size: The crop size (optional, default=1)
 * Returns: The new string
 */
function crop(string, pos, size) {
    string = String(string);
    if (size == null) {
        size = 1;
    }
    if (size <= 0) {
        return "";
    }
    return left(string, pos) + mid(string, pos + size);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Removes the specified number of characters from the left of a string 
 * Parameters:
 *     string: The string
 *     size: The crop size (optional, default=1)
 * Returns: The new string
 */
function lcrop(string, size) {
    if (size == null) {
        size = 1;
    }
    return crop(string, 0, size);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Removes the specified number of characters from the right of a string 
 * Parameters:
 *     string: The string
 *     size: The crop size (optional, default=1)
 * Returns: The new string
 */
function rcrop(string, size) {
    string = String(string);
    if (size == null) {
        size = 1;
    }
    return crop(string, string.length - size, size);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Capitalizes the text, uppercasing the first letter of every word
 * Parameters:
 *     text: The text
 *     separators: An String containing all separator characters. Default: JST_CHARS_WHITESPACE + '.?!'
 * Returns: The new text
 */
function capitalize(text, separators) {
    text = String(text);
    separators = separators || JST_CHARS_WHITESPACE + '.?!';
    var out = "";
    var last = '';    
    for (var i = 0; i < text.length; i++) {
        var current = text.charAt(i);
        if (separators.indexOf(last) >= 0) {
            out += current.toUpperCase();
        } else {
            out += current.toLowerCase();
        }
        last = current;
    }
    return out;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Checks if the string contains only the specified characters
 * Parameters:
 *     string: The string
 *     possible: The string containing the possible characters
 * Returns: Do the String contains only the specified characters?
 */
function onlySpecified(string, possible) {
    string = String(string);
    possible = String(possible);
    for (var i = 0; i < string.length; i++) {
        if (possible.indexOf(string.charAt(i)) == -1) {
            return false;
        }
    }
    return true;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Checks if the string contains only numbers
 * Parameters:
 *     string: The string
 * Returns: Do the String contains only numbers?
 */
function onlyNumbers(string) {
    return onlySpecified(string, JST_CHARS_NUMBERS);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Checks if the string contains only letters
 * Parameters:
 *     string: The string
 * Returns: Do the String contains only lettersts?
 */
function onlyLetters(string) {
    return onlySpecified(string, JST_CHARS_LETTERS);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Checks if the string contains only alphanumeric characters (letters or digits)
 * Parameters:
 *     string: The string
 * Returns: Do the String contains only alphanumeric characters?
 */
function onlyAlpha(string) {
    return onlySpecified(string, JST_CHARS_ALPHA);
}
///////////////////////////////////////////////////////////////////////////////
/*
 * Checks if the string contains only letters without accentiation
 * Parameters:
 *     string: The string
 * Returns: Do the String contains only lettersts?
 */
function onlyBasicLetters(string) {
    return onlySpecified(string, JST_CHARS_BASIC_LETTERS);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Checks if the string contains only alphanumeric characters  without accentiation (letters or digits)
 * Parameters:
 *     string: The string
 * Returns: Do the String contains only alphanumeric characters?
 */
function onlyBasicAlpha(string) {
    return onlySpecified(string, JST_CHARS_BASIC_ALPHA);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the left most n characters
 * Parameters:
 *     string: The string
 *     n: The number of characters
 * Returns: The substring
 */
function left(string, n) {
    string = String(string);
    return string.substring(0, n);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the right most n characters
 * Parameters:
 *     string: The string
 *     n: The number of characters
 * Returns: The substring
 */
function right(string, n) {
    string = String(string);
    return string.substr(string.length - n);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns n characters after the initial position
 * Parameters:
 *     string: The string
 *     pos: The initial position
 *     n: The number of characters (optional)
 * Returns: The substring
 */
function mid(string, pos, n) {
    string = String(string);
    if (n == null) {
        n = string.length;
    }
    return string.substring(pos, pos + n);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Inserts a value inside a string
 * Parameters:
 *     string: The string
 *     pos: The insert position
 *     value: The value to be inserted
 * Returns: The updated
 */
function insertString(string, pos, value) {
    string = String(string);
    var prefix = left(string, pos);
    var suffix = mid(string, pos)
    return prefix + value + suffix;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the function name for a given function reference
 * Parameters:
 *     funct: The function
 *     unnamed: The String to return on unnamed functions. Default: [unnamed]
 * Returns: The function name. If the reference is not a function, returns null
 */
function functionName(funct, unnamed) {
    if (typeof(funct) == "function") {
        var src = funct.toString();
        var start = src.indexOf("function");
        var end = src.indexOf("(");
        if ((start >= 0) && (end >= 0)) {
            start += 8; //The "function" length
            var name = trim(src.substring(start, end));
            return isEmpty(name) ? (unnamed || "[unnamed]") : name;
        }
    } if (typeof(funct) == "object") {
        return functionName(funct.constructor);
    }
    return null;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns all properties in the object, sorted or not, with the separator between them.
 * Parameters:
 *     object: The object
 *     separator: The separator between properties
 *     sort: Will the properties be sorted?
 *     includeObject: Will the object.toString() be included?
 *     objectSeparator: The text separating the object.toString() from the properties. Default to a line
 * Returns: The string
 */
function debug(object, separator, sort, includeObject, objectSeparator) {
    if (object == null) {
        return "null";
    }
    sort = booleanValue(sort == null ? true : sort);
    includeObject = booleanValue(includeObject == null ? true : sort);
    separator = separator || "\n";
    objectSeparator = objectSeparator || "--------------------";

    //Get the properties
    var properties = new Array();
    for (var property in object) {
        var part = property + " = ";
        try {
            part += object[property];
        } catch (e) {
            part += "<Error retrieving value>";
        }
        properties[properties.length] = part;
    }
    //Sort if necessary
    if (sort) {
        properties.sort();
    }
    //Build the output
    var out = "";
    if (includeObject) {
        try {
            out = object.toString() + separator;
        } catch (e) {
            out = "<Error calling the toString() method>"
        }
        if (!isEmpty(objectSeparator)) {
            out += objectSeparator + separator;
        }
    }
    out += properties.join(separator);
    return out;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Escapes the string's special characters to their escaped form
 * ('\\' to '\\\\', '\n' to '\\n', ...) and the extraChars are escaped via unicode
 * (\\uXXXX, where XXXX is the hexadecimal charcode)
 * Parameters:
 *     string: The string to be escaped
 *     extraChars: The String containing extra characters to be escaped
 *     onlyExtra: If true, do not process the standard characters ('\\', '\n', ...)
 * Returns: The encoded String
 */
function escapeCharacters(string, extraChars, onlyExtra) {
    var ret = String(string);
    extraChars = String(extraChars || "");
    onlyExtra = booleanValue(onlyExtra);
    //Checks if must process only the extra characters
    if (!onlyExtra) {
        ret = replaceAll(ret, "\n", "\\n");
        ret = replaceAll(ret, "\r", "\\r");
        ret = replaceAll(ret, "\t", "\\t");
        ret = replaceAll(ret, "\"", "\\\"");
        ret = replaceAll(ret, "\'", "\\\'");
        ret = replaceAll(ret, "\\", "\\\\");
    }
    //Process the extra characters
    for (var i = 0; i < extraChars.length; i++) {
        var chr = extraChars.charAt(i);
        ret = replaceAll(ret, chr, "\\\\u" + lpad(new Number(chr.charCodeAt(0)).toString(16), 4, '0'));
    }
    return ret;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Unescapes the string, changing the special characters to their unescaped form
 * ('\\\\' to '\\', '\\n' to '\n', '\\uXXXX' to the hexadecimal ASC(XXXX), ...)
 * Parameters:
 *     string: The string to be unescaped
 *     onlyExtra: If true, do not process the standard characters ('\\', '\n', ...)
 * Returns: The unescaped String
 */
function unescapeCharacters(string, onlyExtra) {
    var ret = String(string);
    var pos = -1;
    var u = "\\\\u";
    onlyExtra = booleanValue(onlyExtra);
    //Process the extra characters
    do {
        pos = ret.indexOf(u);
        if (pos >= 0) {
            var charCode = parseInt(ret.substring(pos + u.length, pos + u.length + 4), 16);
            ret = replaceAll(ret, u + charCode, String.fromCharCode(charCode));
        }
    } while (pos >= 0);
    
    //Checks if must process only the extra characters
    if (!onlyExtra) {
        ret = replaceAll(ret, "\\n", "\n");
        ret = replaceAll(ret, "\\r", "\r");
        ret = replaceAll(ret, "\\t", "\t");
        ret = replaceAll(ret, "\\\"", "\"");
        ret = replaceAll(ret, "\\\'", "\'");
        ret = replaceAll(ret, "\\\\", "\\");
    }
    return ret;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Writes the specified cookie
 * Parameters:
 *     name: The cookie name
 *     value: The value
 *     document: The document containing the cookie. Default to self.document
 *     expires: The expiration date or the false flag, indicating it never expires. Defaults: until browser is closed.
 *     path: The cookie's path. Default: not specified
 *     domain: The cookie's domain. Default: not specified
 */
function writeCookie(name, value, document, expires, path, domain, secure) {
    document = document || self.document;
    var str = name + "=" + (isEmpty(value) ? "" : encodeURIComponent(value));
    if (path != null) str += "; path=" + path;
    if (domain != null) str += "; domain=" + domain;
    if (secure != null && booleanValue(secure)) str += "; secure";
    if (expires === false) expires = new Date(2500, 12, 31);
    if (expires instanceof Date) str += "; expires=" + expires.toGMTString();
    document.cookie = str;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Reads the specified cookie
 * Parameters:
 *     name: The cookie name
 *     document: The document containing the cookie. Default to self.document
 * Returns: The value
 */
function readCookie(name, document) {
    document = document || self.document;
    var prefix = name + "=";
    var cookie = document.cookie;
    var begin = cookie.indexOf("; " + prefix);
    if (begin == -1) {
    begin = cookie.indexOf(prefix);
    if (begin != 0) return null;
    } else
    begin += 2;
    var end = cookie.indexOf(";", begin);
    if (end == -1)
    end = cookie.length;
    return decodeURIComponent(cookie.substring(begin + prefix.length, end));
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Removes the specified cookie
 * Parameters:
 *     name: The cookie name
 *     document: The document containing the cookie. Default to self.document
 *     path: The cookie's path. Default: not specified
 *     domain: The cookie's domain. Default: not specified
 */
function deleteCookie(name, document, path, domain) {
    writeCookie(name, null, document, path, domain);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the value of a given date field 
 * Parameters:
 *     date: The date
 *     field: the field. May be one of the constants JST_FIELD_*. Defaults to JST_FIELD_DAY
 */
function getDateField(date, field) {
    if (!isInstance(date, Date)) {
        return null;
    }
    switch (field) {
        case JST_FIELD_MILLISECOND:
            return date.getMilliseconds();
        case JST_FIELD_SECOND:
            return date.getSeconds();
        case JST_FIELD_MINUTE:
            return date.getMinutes();
        case JST_FIELD_HOUR:
            return date.getHours();
        case JST_FIELD_DAY:
            return date.getDate();
        case JST_FIELD_MONTH:
            return date.getMonth();
        case JST_FIELD_YEAR:
            return date.getFullYear();
    }
    return null;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Sets the value of a given date field 
 * Parameters:
 *     date: The date
 *     field: the field. May be one of the constants JST_FIELD_*. Defaults to JST_FIELD_DAY
 *     value: The field value
 */
function setDateField(date, field, value) {
    if (!isInstance(date, Date)) {
        return;
    }
    switch (field) {
        case JST_FIELD_MILLISECOND:
            date.setMilliseconds(value);
            break;
        case JST_FIELD_SECOND:
            date.setSeconds(value);
            break;
        case JST_FIELD_MINUTE:
            date.setMinutes(value);
            break;
        case JST_FIELD_HOUR:
            date.setHours(value);
            break;
        case JST_FIELD_DAY:
            date.setDate(value);
            break;
        case JST_FIELD_MONTH:
            date.setMonth(value);
            break;
        case JST_FIELD_YEAR:
            date.setFullYear(value);
            break;
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Adds a field to a date
 * Parameters:
 *     date: the date
 *     amount: the amount to add. Defaults to 1
 *     field: The field. May be one of the constants JST_FIELD_*. Defaults to JST_FIELD_DAY
 * Returns: The new date
 */
function dateAdd(date, amount, field) {
    if (!isInstance(date, Date)) {
        return null;
    }
    if (amount == 0) {
        return new Date(date.getTime());
    }
    if (!isInstance(amount, Number)) {
        amount = 1;
    }
    if (field == null) field = JST_FIELD_DAY;
    if (field < 0 || field > JST_FIELD_YEAR) {
        return null;
    }
    var time = date.getTime();
    if (field <= JST_FIELD_DAY) {
        var mult = 1;
        switch (field) {
            case JST_FIELD_SECOND:
                mult = MILLIS_IN_SECOND;
                break;
            case JST_FIELD_MINUTE:
                mult = MILLIS_IN_MINUTE;
                break;
            case JST_FIELD_HOUR:
                mult = MILLIS_IN_HOUR;
                break;
            case JST_FIELD_DAY:
                mult = MILLIS_IN_DAY;
                break;
        }
        var time = date.getTime();
        time += mult * amount;
        return new Date(time);
    }
    var ret = new Date(time);
    var day = ret.getDate();
    var month = ret.getMonth();
    var year = ret.getFullYear();
    if (field == JST_FIELD_YEAR) {
        year += amount;
    } else if (field == JST_FIELD_MONTH) {
        month += amount;
    }
    while (month > 11) {
        month -= 12;
        year++;
    }
    day = Math.min(day, getMaxDay(month, year));
    ret.setDate(day);
    ret.setMonth(month);
    ret.setFullYear(year);
    return ret;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the difference, as in date2 - date1
 * Parameters:
 *     date1: the first date
 *     date2: the second date
 *     field: The field. May be one of the constants JST_FIELD_*. Default to JST_FIELD_DAY
 * Returns: An integer number
 */
function dateDiff(date1, date2, field) {
    if (!isInstance(date1, Date) || !isInstance(date2, Date)) {
        return null;
    }
    if (field == null) field = JST_FIELD_DAY;
    if (field < 0 || field > JST_FIELD_YEAR) {
        return null;
    }
    if (field <= JST_FIELD_DAY) {
        var div = 1;
        switch (field) {
            case JST_FIELD_SECOND:
                div = MILLIS_IN_SECOND;
                break;
            case JST_FIELD_MINUTE:
                div = MILLIS_IN_MINUTE;
                break;
            case JST_FIELD_HOUR:
                div = MILLIS_IN_HOUR;
                break;
            case JST_FIELD_DAY:
                div = MILLIS_IN_DAY;
                break;
        }
        return Math.round((date2.getTime() - date1.getTime()) / div);
    }
    var years = date2.getFullYear() - date1.getFullYear();
    if (field == JST_FIELD_YEAR) {
        return years;
    } else if (field == JST_FIELD_MONTH) {
        var months1 = date1.getMonth();
        var months2 = date2.getMonth();
        
        if (years < 0) {
            months1 += Math.abs(years) * 12;
        } else if (years > 0) {
            months2 += years * 12;
        }
        
        return (months2 - months1);
    }
    return null;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Truncates the date, setting all fields lower than the specified one to its minimum value
 * Parameters:
 *     date: The date
 *     field: The field. May be one of the constants JST_FIELD_*. Default to JST_FIELD_DAY
 * Returns: The new Date
 */
function truncDate(date, field) {
    if (!isInstance(date, Date)) {
        return null;
    }
    if (field == null) field = JST_FIELD_DAY;
    if (field < 0 || field > JST_FIELD_YEAR) {
        return null;
    }
    var ret = new Date(date.getTime());
    if (field > JST_FIELD_MILLISECOND) {
        ret.setMilliseconds(0);
    }
    if (field > JST_FIELD_SECOND) {
        ret.setSeconds(0);
    }
    if (field > JST_FIELD_MINUTE) {
        ret.setMinutes(0);
    }
    if (field > JST_FIELD_HOUR) {
        ret.setHours(0);
    }
    if (field > JST_FIELD_DAY) {
        ret.setDate(1);
    }
    if (field > JST_FIELD_MONTH) {
        ret.setMonth(0);
    }
    return ret;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the maximum day of a given month and year
 * Parameters:
 *     month: the month
 *     year: the year
 * Returns: The maximum day
 */
function getMaxDay(month, year) {
    month = new Number(month) + 1;
    year = new Number(year);
    switch (month) {
        case 1: case 3: case 5: case 7:
        case 8: case 10: case 12:
            return 31;
        case 4: case 6: case 9: case 11:
            return 30;
        case 2:
            if ((year % 4) == 0) {
                return 29;
            } else {
                return 28;
            }
        default:
            return 0;
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * Returns the full year, given a 2 digit year. 50 or less returns 2050
 * Parameters:
 *     year: the year
 * Returns: The 4 digit year
 */
function getFullYear(year) {
    year = Number(year);
    if (year < 1000) {
        if (year < 50 || year > 100) {
            year += 2000;
        } else {
            year += 1900;
        }
    }
    return year;
}

/**
 * Sets the opacity of a given object. The value ranges from 0 to 100.
 * This function is currently supported by Internet Explorer (only works 
 * when the object's position is absolute - why?!? >:-( ) and Gecko based 
 * browsers only.
 * Parameters:
 *     object: The object name or reference
 *     value: The opacity value from 0 to 100. Default: 100
 */
function setOpacity(object, value) {
    object = getObject(object);
    if (object == null) {
        return;
    }
    value = Math.round(Number(value));
    if (isNaN(value) || value > 100) {
        value = 100;
    }
    if (value < 0) {
        value = 0;
    }
    var style = object.style;
    if (style == null) {
        return;
    }
    //Opacity on Mozilla / Gecko based browsers
    style.MozOpacity = value / 100;
    //Opacity on Internet Explorer
    style.filter = "alpha(opacity=" + value + ")";
}

/**
 * Returns the opacity of a given object. The value ranges from 0 to 100.
 * This function is currently supported by Internet Explorer and Gecko based browsers only.
 * Parameters:
 *     object: The object name or reference
 */
function getOpacity(object) {
    object = getObject(object);
    if (object == null) {
        return;
    }
    var style = object.style;
    if (style == null) {
        return;
    }
    //Check the known options
    if (style.MozOpacity) {
        //Opacity on Mozilla / Gecko based browsers
        return Math.round(style.MozOpacity * 100);
    } else if (style.filter) {
        //Opacity on Internet Explorer
        var regExp = new RegExp("alpha\\(opacity=(\d*)\\)");
        var array = regExp.exec(style.filter);
        if (array != null && array.length > 1) {
            return parseInt(array[1], 10);
        }
    }
    //Not found. Return 100%
    return 100;
}

///////////////////////////////////////////////////////////////////////////////
/*
 * A class that represents a key/value pair
 * Parameters:
 *     key: The key
 *     value: The value
 */
function Pair(key, value) {
    this.key = key == null ? "" : key;
    this.value = value;
    
    /* Returns a String representation of this pair */
    this.toString = function() {
        return this.key + "=" + this.value;
    };
}

///////////////////////////////////////////////////////////////////////////////
/*
 * DEPRECATED - Pair is a much meaningful name, use it instead.
 *              Value will be removed in future versions.
 */
function Value(key, value) {
    this.base = Pair;
    this.base(key, value);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * A class that represents a Map. It is a set of pairs. Each key exists 
 * only once. If the key is added again, it's value will be updated instead
 * of adding a new pair.
 * Parameters:
 *     pairs: The initial pairs array (optional)
 */
function Map(pairs) {
    this.pairs = pairs || new Array();
    this.afterSet = null;
    this.afterRemove = null;
    
    /*
     * Adds the pair to the map
     */
    this.putValue = function(pair) {
        this.putPair(pair);
    }

    /*
     * Adds the pair to the map
     */
    this.putPair = function(pair) {
        if (isInstance(pair, Pair)) {
            for (var i = 0; i < this.pairs.length; i++) {
                if (this.pairs[i].key == pair.key) {
                    this.pairs[i].value = pair.value;
                }
            }
            this.pairs[this.pairs.length] = pair;
            if (this.afterSet != null) {
                this.afterSet(pair, this);
            }
        }
    }

    /*
     * Adds the key / value to the map
     */
    this.put = function(key, value) {
        this.putValue(new Pair(key, value));
    }

    /*
     * Adds all the pairs to the map
     */
    this.putAll = function(map) {
        if (!(map instanceof Map)) {
            return;
        }
        var entries = map.getEntries();
        for (var i = 0; i < entries.length; i++) {
            this.putPair(entries[i]);
        }
    }
    
    /*
     * Returns the entry count
     */
    this.size = function() {
        return this.pairs.length;
    }
    
    /*
     * Returns the mapped entry
     */
    this.get = function(key) {
        for (var i = 0; i < this.pairs.length; i++) {
            var pair = this.pairs[i];
            if (pair.key == key) {
                return pair.value;
            }
        }
        return null;
    }
    
    /*
     * Returns the keys
     */
    this.getKeys = function() {
        var ret = new Array();
        for (var i = 0; i < this.pairs.length; i++) {
            ret[ret.length] = this.pairs[i].key;
        }
        return ret;
    }
    
    /*
     * Returns the values
     */
    this.getValues = function() {
        var ret = new Array();
        for (var i = 0; i < this.pairs.length; i++) {
            ret[ret.length] = this.pairs[i].value;
        }
        return ret;
    }

    /*
     * Returns the pairs
     */
    this.getEntries = function() {
        return this.getPairs();
    }
    
    /*
     * Returns the pairs
     */
    this.getPairs = function() {
        var ret = new Array();
        for (var i = 0; i < this.pairs.length; i++) {
            ret[ret.length] = this.pairs[i];
        }
        return ret;
    }
    
    /*
     * Remove the specified key, returning the pair
     */
    this.remove = function (key) {
        for (var i = 0; i < this.pairs.length; i++) {
            var pair = this.pairs[i];
            if (pair.key == key) {
                this.pairs.splice(i, 1);
                if (this.afterRemove != null) {
                    this.afterRemove(pair, this);
                }
                return pair;
            }
        }
        return null;
    }
    
    /*
     * Removes all values
     */
    this.clear = function (key) {
        var ret = this.pairs;
        for (var i = 0; i < ret.length; i++) {
            this.remove(ret[i].key);
        }
        return ret;
    }
    
    /* Returns a String representation of this map */
    this.toString = function() {
        return functionName(this.constructor) + ": {" + this.pairs + "}";
    }
    
    /* Returns an object containing a property value for each pair in this map */
    this.toObject = function() {
        ret = new Object();
        for (var i = 0; i < this.pairs.length; i++) {
            var pair = this.pairs[i];
            ret[pair.key] = pair.value;
        }
        return ret;
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * A Map that gets its pairs using a single string. The String has a pair
 * separator and a name/value separator. Ex: name1=value1&name2=value2&...
 * Parameters:
 *     string: The string in form: name1=value1&name2=value2&...
 *     nameSeparator: The String between the name/value pairs. Default: &
 *     valueSeparator: The String between name and value. Default: =
 */
function StringMap(string, nameSeparator, valueSeparator, isEncoded) {
    this.nameSeparator = nameSeparator || "&";
    this.valueSeparator = valueSeparator || "=";
    this.isEncoded = isEncoded == null ? true : booleanValue(isEncoded);
    
    var pairs = new Array();
    string = trim(string);
    if (!isEmpty(string)) {
        var namesValues = string.split(nameSeparator);
        for (i = 0; i < namesValues.length; i++) {
            var nameValue = namesValues[i].split(valueSeparator);
            var name = trim(nameValue[0]);
            var value = "";
            if (nameValue.length > 0) {
                value = trim(nameValue[1]);
                if (this.isEncoded) {
                    value = decodeURIComponent(value);
                }
            }
            var pos = -1;
            for (j = 0; j < pairs.length; j++) {
                if (pairs[j].key == name) {
                    pos = j;
                    break;
                }
            }
            //Check if the value already existed: build an array
            if (pos >= 0) {
                var array = pairs[pos].value;
                if (!isInstance(array, Array)) {
                    array = [array];
                }
                array[array.length] = value;
                pairs[pos].value = array;
            } else {
                pairs[pairs.length] = new Pair(name, value);
            }
        }
    }
    this.base = Map;
    this.base(pairs);
    
    /*
     * Rebuild the String
     */
    this.getString = function() {
        var ret = new Array();
        for (var i = 0; i < this.pairs.length; i++) {
            var pair = this.pairs[i];
            ret[ret.length] = pair.key + this.valueSeparator + this.value;
        }
        return ret.join(this.nameSeparator);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * A StringMap used to get values from the location query string
 * Parameters:
 *     location: the location object. Default to self.location
 */
function QueryStringMap(location) {
    this.location = location || self.location;
    
    var string = String(this.location.search);
    if (!isEmpty(string)) {
        //Remove the ? at the start
        string = string.substr(1);
    }

    this.base = StringMap;
    this.base(string, "&", "=", true);
    
    //Ensures the string will not be modified
    this.putPair = function() {
        alert("Cannot put a value on a query string");
    }
    this.remove = function() {
        alert("Cannot remove a value from a query string");
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * A StringMap used to get values from the document cookie
 * Parameters:
 *     document: the document. Default to self.document
 */
function CookieMap(document) {
    this.document = document || self.document;

    this.base = StringMap;
    this.base(document.cookie, ";", "=", true);

    //Set the callback to update the cookie    
    this.afterSet = function (pair) {
        writeCookie(pair.key, pair.value, this.document);
    }
    this.afterRemove = function (pair) {
        deleteCookie(pair.key, this.document);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * A Map used to get/set an object's properties
 * Parameters:
 *     object: The object
 */
function ObjectMap(object) {
    this.object = object;

    var pairs = new Array();
    for (var property in this.object) {
        pairs[pairs.length] = new Pair(property, this.object[property]);
    }
    this.base = Map;
    this.base(pairs);

    //Set the callback to update the object    
    this.afterSet = function (pair) {
        this.object[pair.key] = pair.value;
    }
    this.afterRemove = function (pair) {
        try {
            delete object[pair.key];
        } catch (exception) {
            object[pair.key] = null;
        }
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * A class used to build a string with more performance than string concatenation
 */
function StringBuffer(initialCapacity) {
    this.initialCapacity = initialCapacity || 10;
    this.buffer = new Array(this.initialCapacity);
 
    //Appends the value to the buffer. The buffer itself is returned, so nested calls may be done
    this.append = function(value) {
        this.buffer[this.buffer.length] = value;
        return this;
    }
    
    //Clears the buffer
    this.clear = function() {
        delete this.buffer;
        this.buffer = new Array(this.initialCapacity);
    }
    
    //Returns the buffered string
    this.toString = function() {
        return this.buffer.join("");
    }
    
    //Returns the buffered string length
    this.length = function() {
        return this.toString().length;
    }
}
