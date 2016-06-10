/*
 * Parsers version 2.2.4
 *
 * This is a set of parsers used both to parse and format different types of data
 *
 * Dependencies: 
 *  - JavaScriptUtil.js
 *
 * Author: Luis Fernando Planella Gonzalez (lfpg.dev at gmail dot com)
 * Home Page: http://javascriptools.sourceforge.net
 *
 * JavaScripTools is distributed under the GNU Lesser General Public License (LGPL).
 * For more information, see http://www.gnu.org/licenses/lgpl-2.1.txt
 */

/*****************************************************************************/

///////////////////////////////////////////////////////////////////////////////
// DEFAULT PROPERTY VALUES CONSTANTS
///////////////////////////////////////////////////////////////////////////////

//////////////   NumberParser Constants
//Number of decimal digits (-1 = unlimited)
var JST_DEFAULT_DECIMAL_DIGITS = -1;
//Decimal separator
var JST_DEFAULT_DECIMAL_SEPARATOR = ",";
//Group separator
var JST_DEFAULT_GROUP_SEPARATOR = ".";
//Use grouping?
var JST_DEFAULT_USE_GROUPING = false;
//Currency symbol
var JST_DEFAULT_CURRENCY_SYMBOL = "R$";
//Use the currency symbol?
var JST_DEFAULT_USE_CURRENCY = false;
//Use parenthesis for negative values?
var JST_DEFAULT_NEGATIVE_PARENTHESIS = false;
//Group size
var JST_DEFAULT_GROUP_SIZE = 3;
//Use space after currency symbol
var JST_DEFAULT_SPACE_AFTER_CURRENCY = true;
//When negative place currency inside minus or parenthesis?
var JST_DEFAULT_CURRENCY_INSIDE = false;

//////////////   DateParser Constants
//Default date mask
var JST_DEFAULT_DATE_MASK = "dd/MM/yyyy";
//Default enforce length
var JST_DEFAULT_ENFORCE_LENGTH = true;

//////////////   BooleanParser Constants
//Default true value
var JST_DEFAULT_TRUE_VALUE = "true";
//Default false value
var JST_DEFAULT_FALSE_VALUE = "false";
//Default useBooleanValue value
var JST_DEFAULT_USE_BOOLEAN_VALUE = true;

///////////////////////////////////////////////////////////////////////////////
/*
 * Parser
 *
 * A superclass for all parser types
 */
function Parser() {
    /*
     * Parses the String
     * Parameters:
     *     text: The text to be parsed
     * Returns: The parsed value
     */
    this.parse = function(text) {
        return text;
    }

    /*
     * Formats the value
     * Parameters:
     *     value: The value to be formatted
     * Returns: The formatted value
     */
    this.format = function(value) {
        return value;
    }
    
    /*
     * Checks if the given text is a valid value for this parser.
     * Returns true if the text is empty
     * Parameters:
     *     value: The text to be tested
     * Returns: Is the text valid?
     */
    this.isValid = function(text) {
        return isEmpty(text) || (this.parse(text) != null);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * NumberParser - used for numbers
 * Parameters: 
 *    decimalDigits: The number of decimal digits. -1 Means no limit. Defaults to the JST_DEFAULT_DECIMAL_DIGITS constant value
 *    decimalSeparator: The decimal separator. Defaults to the JST_DEFAULT_DECIMAL_SEPARATOR constant value
 *    groupSeparator: The separator between thousands group. Defaults to the JST_DEFAULT_GROUP_SEPARATOR constant value
 *    useGrouping: Will grouping separator be used?. Defaults to the JST_DEFAULT_USE_GROUPING constant value
 *    currencySymbol: The currency symbol. Defaults to the JST_DEFAULT_CURRENCY_SYMBOL constant value
 *    useCurrency: Will the currencySymbol be used? Defaults to the JST_DEFAULT_USE_CURRENCY constant value
 *    negativeParenthesis: Use parenthesis (true) or "-" (false) for negative values? Defaults to the JST_DEFAULT_NEGATIVE_PARENTHESIS constant value
 *    groupSize: How many digits will be grouped together? Defaults to the JST_DEFAULT_GROUP_SIZE constant value
 *    spaceAfterCurrency: Will a space be placed after the currency symbol? Defaults to the JST_DEFAULT_SPACE_AFTER_CURRENCY constant value
 *    currencyInside: When negative place currency inside minus or parenthesis?
 * Additional methods (others than those defined on the Parser class):
 *    round(): rounds a number to the parser specific precision
 */
function NumberParser(decimalDigits, decimalSeparator, groupSeparator, useGrouping, currencySymbol, useCurrency, negativeParenthesis, groupSize, spaceAfterCurrency, currencyInside) {
    this.base = Parser;
    this.base();
    
    this.decimalDigits = (decimalDigits == null) ? JST_DEFAULT_DECIMAL_DIGITS : decimalDigits;
    this.decimalSeparator = (decimalSeparator == null) ? JST_DEFAULT_DECIMAL_SEPARATOR : decimalSeparator;
    this.groupSeparator = (groupSeparator == null) ? JST_DEFAULT_GROUP_SEPARATOR : groupSeparator;
    this.useGrouping = (useGrouping == null) ? JST_DEFAULT_USE_GROUPING : booleanValue(useGrouping);
    this.currencySymbol = (currencySymbol == null) ? JST_DEFAULT_CURRENCY_SYMBOL : currencySymbol;
    this.useCurrency = (useCurrency == null) ? JST_DEFAULT_USE_CURRENCY : booleanValue(useCurrency);
    this.negativeParenthesis = (negativeParenthesis == null) ? JST_DEFAULT_NEGATIVE_PARENTHESIS : booleanValue(negativeParenthesis);
    this.groupSize = (groupSize == null) ? JST_DEFAULT_GROUP_SIZE : groupSize;
    this.spaceAfterCurrency = (spaceAfterCurrency == null) ? JST_DEFAULT_SPACE_AFTER_CURRENCY : booleanValue(spaceAfterCurrency);
    this.currencyInside = (currencyInside == null) ? JST_DEFAULT_CURRENCY_INSIDE : booleanValue(currencyInside);
    
    this.parse = function(string) {
        string = trim(string);
        if (isEmpty(string)) {
            return null;
        }
        string = replaceAll(string, this.groupSeparator, "");
        string = replaceAll(string, this.decimalSeparator, ".");
        string = replaceAll(string, this.currencySymbol, "");
        var isNegative = (string.indexOf("(") >= 0) || (string.indexOf("-") >= 0);
        string = replaceAll(string, "(", "");
        string = replaceAll(string, ")", "");
        string = replaceAll(string, "-", "");
        string = trim(string);
        //Check the valid characters
        if (!onlySpecified(string, JST_CHARS_NUMBERS + ".")) {
            return null;
        }
        var ret = parseFloat(string);
        ret = isNegative ? (ret * -1) : ret;
        return this.round(ret);
    }
    
    this.format = function(number) {
        //Check if the number is a String
        if (isNaN(number)) {
            number = this.parse(number);
        }
        if (isNaN(number)) return null;
        
        var isNegative = number < 0;
        number = Math.abs(number);
        var ret = "";
        var parts = String(this.round(number)).split(".");
        var intPart = parts[0];
        var decPart = parts.length > 1 ? parts[1] : "";
        
        //Checks if there's thousand separator
        if ((this.useGrouping) && (!isEmpty(this.groupSeparator))) {
            var group, temp = "";
            for (var i = intPart.length; i > 0; i -= this.groupSize) {
                group = intPart.substring(intPart.length - this.groupSize);
                intPart = intPart.substring(0, intPart.length - this.groupSize);
                temp = group + this.groupSeparator + temp;
            }
            intPart = temp.substring(0, temp.length-1);
        }
        
        //Starts building the return value
        ret = intPart;
        
        //Checks if there's decimal digits
        if (this.decimalDigits != 0) {
            if (this.decimalDigits > 0) {
                while (decPart.length < this.decimalDigits) {
                    decPart += "0";
                }
            }
            if (!isEmpty(decPart)) {
                ret += this.decimalSeparator + decPart;
            }
        }
        
        //Checks the negative number
        if (isNegative && !this.currencyInside) {
            if (this.negativeParenthesis) {
                ret = "(" + ret + ")";
            }  else {
                ret = "-" + ret;
            }
        }
        
        //Checks the currency symbol
        if (this.useCurrency) {
            ret = this.currencySymbol + (this.spaceAfterCurrency ? " " : "") + ret;
        }

        if (isNegative && this.currencyInside) {
            if (this.negativeParenthesis) {
                ret = "(" + ret + ")";
            }  else {
                ret = "-" + ret;
            }
        }

        return ret;
    }
    
    /*
     * Rounds the number to the precision
     */
    this.round = function(number) {
    
        //Checks the trivial cases
        if (this.decimalDigits < 0) {
            return number;
        } else if (this.decimalDigits == 0) {
            return Math.round(number);
        }
        
        var mult = Math.pow(10, this.decimalDigits);
    
        return Math.round(number * mult) / mult;
    }
}

/*****************************************************************************/

///////////////////////////////////////////////////////////////////////////////
/*
 * DateParser - Used for dates
 * Parameters:
 *     mask: The date mask. Accepts the following characters:
 *        d: Day         M: month        y: year
 *        h: 12 hour     H: 24 hour      m: minute
 *        s: second      S: millisecond
 *        a: am / pm     A: AM / PM
 *        /. -: Separators
 *        The default is the value of the JST_DEFAULT_DATE_MASK constant
 *     enforceLength: If set to true, each field on the parsed String must have 
 *        the same length as that field on the mask, ie: yyyy-MM-dd with 99-10-8
 *        would result on a parse error. Default: The value of the JST_DEFAULT_ENFORCE_LENGTH constant
 *     completeFieldsWith: A date used to complete missing fields
 * Additional methods (others than those defined on the Parser class):
 *     (none. all other methods are considered "private" and not supposed 
 *      for external use)
 */
function DateParser(mask, enforceLength, completeFieldsWith) {
    
    this.base = Parser;
    this.base();

    this.mask = (mask == null) ? JST_DEFAULT_DATE_MASK : String(mask);
    this.enforceLength = (enforceLength == null) ? JST_DEFAULT_ENFORCE_LENGTH : booleanValue(enforceLength);
    this.completeFieldsWith = completeFieldsWith || null;
    this.numberParser = new NumberParser(0);
    this.compiledMask = new Array();
    
    //Types of fields
    var LITERAL     =  0;
    var MILLISECOND =  1;
    var SECOND      =  2;
    var MINUTE      =  3;
    var HOUR_12     =  4;
    var HOUR_24     =  5;
    var DAY         =  6;
    var MONTH       =  7;
    var YEAR        =  8;
    var AM_PM_UPPER =  9;
    var AM_PM_LOWER = 10;
    
    this.parse = function(string) {
        if (isEmpty(string)) {
            return null;
        }
        string = trim(String(string)).toUpperCase();
        
        //Checks PM entries
        var pm = string.indexOf("PM") != -1;
        string = replaceAll(replaceAll(string, "AM", ""), "PM", "");
        
        //Get each field value
        var parts = [0, 0, 0, 0, 0, 0, 0];
        var partValues = ["", "", "", "", "", "", ""];
        var entries = [null, null, null, null, null, null, null];
        for (var i = 0; i < this.compiledMask.length; i++) {
            var entry = this.compiledMask[i];
            
            var pos = this.getTypeIndex(entry.type);
            
            //Check if is a literal or not
            if (pos == -1) {
                //Check if it's AM/PM or a literal
                if (entry.type == LITERAL) {
                    //Is a literal: skip it
                    string = string.substr(entry.length);
                } else {
                    //It's a AM/PM. All have been already cleared...
                }
            } else {
                var partValue = 0;
                if (i == (this.compiledMask.length - 1)) {
                    partValue = string;
                    string = "";
                } else {
                    var nextEntry = this.compiledMask[i + 1];
                    
                    //Check if the next part is a literal
                    if (nextEntry.type == LITERAL) {
                        var nextPos = string.indexOf(nextEntry.literal);

                        //Check if next literal is missing
                        if (nextPos == -1) {
                            //Probably the last part on the String
                            partValue = string
                            string = "";
                        } else {
                            //Get the value of the part from the string and cuts it
                            partValue = left(string, nextPos);
                            string = string.substr(nextPos);
                        }
                    } else {
                        //Get the value of the part from the string and cuts it
                        partValue = string.substring(0, entry.length);
                        string = string.substr(entry.length);
                    }
                }
                //Validate the part value and store it
                if (!onlyNumbers(partValue)) {
                    return null;
                }
                partValues[pos] = partValue;
                entries[pos] = entry;
                parts[pos] = isEmpty(partValue) ? this.minValue(parts, entry.type) : this.numberParser.parse(partValue);
            }
        }

        //If there's something else on the String, it's an error!
        if (!isEmpty(string)) {
            return null;
        }

        //If was PM, add 12 hours
        if (pm && (parts[JST_FIELD_HOUR] < 12)) {
            parts[JST_FIELD_HOUR] += 12;
        }
        //The month is from 0 to 11
        if (parts[JST_FIELD_MONTH] > 0) {
            parts[JST_FIELD_MONTH]--;
        }
        //Check for 2-digit year
        if (parts[JST_FIELD_YEAR] < 100) {
            if (parts[JST_FIELD_YEAR] < 50) {
                parts[JST_FIELD_YEAR] += 2000;
            } else {
                parts[JST_FIELD_YEAR] += 1900;
            }
        }
                
        //Validate the parts
        for (var i = 0; i < parts.length; i++) {
            var entry = entries[i]
            var part = parts[i];
            var partValue = partValues[i];
            if (part < 0) {
                return null;
            } else if (entry != null) {
                if (this.enforceLength && ((entry.length >= 0) && (partValue.length < entry.length))) {
                    return null;
                }
                part = parseInt(partValue, 10);
                if (isNaN(part) && this.completeFieldsWith != null) {
                    part = parts[i] = getDateField(this.completeFieldsWith, i); 
                }
                if ((part < this.minValue(parts, entry.type)) || (part > this.maxValue(parts, entry.type))) {
                    return null;
                }
            } else if (i == JST_FIELD_DAY && part == 0) {
                part = parts[i] = 1;
            }
        }        

        //Build the return
        return new Date(parts[JST_FIELD_YEAR], parts[JST_FIELD_MONTH], parts[JST_FIELD_DAY], parts[JST_FIELD_HOUR], 
            parts[JST_FIELD_MINUTE], parts[JST_FIELD_SECOND], parts[JST_FIELD_MILLISECOND]);
    }
    
    this.format = function(date) {
        if (!(date instanceof Date)) {
            date = this.parse(date);
        }
        if (date == null) {
            return "";
        }
        var ret = "";
        var parts = [date.getMilliseconds(), date.getSeconds(), date.getMinutes(), date.getHours(), date.getDate(), date.getMonth(), date.getFullYear()];

        //Iterate through the compiled mask
        for (var i = 0; i < this.compiledMask.length; i++) {
            var entry = this.compiledMask[i];
            switch (entry.type) {
                case LITERAL: 
                    ret += entry.literal;
                    break;
                case AM_PM_LOWER:
                    ret += (parts[JST_FIELD_HOUR] < 12) ? "am" : "pm";
                    break;
                case AM_PM_UPPER:
                    ret += (parts[JST_FIELD_HOUR] < 12) ? "AM" : "PM";
                    break;
                case MILLISECOND:
                case SECOND:
                case MINUTE:
                case HOUR_24:
                case DAY:
                    ret += lpad(parts[this.getTypeIndex(entry.type)], entry.length, "0");
                    break;
                case HOUR_12:
                    ret += lpad(parts[JST_FIELD_HOUR] % 12, entry.length, "0");
                    break;
                case MONTH:
                    ret += lpad(parts[JST_FIELD_MONTH] + 1, entry.length, "0");
                    break;
                case YEAR:
                    ret += lpad(right(parts[JST_FIELD_YEAR], entry.length), entry.length, "0");
                    break;
            }
        }
        
        //Return the value
        return ret;
    }
    
    /*
     * Returns the maximum value of the field
     */
    this.maxValue = function(parts, type) {
        switch (type) {
            case MILLISECOND: return 999;
            case SECOND: return 59;
            case MINUTE: return 59;
            case HOUR_12: case HOUR_24: return 23; //Internal value: both 23
            case DAY: return getMaxDay(parts[JST_FIELD_MONTH], parts[JST_FIELD_YEAR]);
            case MONTH: return 12;
            case YEAR: return 9999;
            default: return 0;
        }
    }

    /*
     * Returns the minimum value of the field
     */
    this.minValue = function(parts, type) {
        switch (type) {
            case DAY: case MONTH: case YEAR: return 1;
            default: return 0;
        }
    }
        
    /*
     * Returns the field's type
     */
    this.getFieldType = function(field) {
        switch (field.charAt(0)) {
            case "S": return MILLISECOND;
            case "s": return SECOND;
            case "m": return MINUTE;
            case "h": return HOUR_12;
            case "H": return HOUR_24;
            case "d": return DAY;
            case "M": return MONTH;
            case "y": return YEAR;
            case "a": return AM_PM_LOWER;
            case "A": return AM_PM_UPPER;
            default: return LITERAL;
        }
    }
    
    /*
     * Returns the type's index in the field array
     */
    this.getTypeIndex = function(type) {
        switch (type) {
            case MILLISECOND: return JST_FIELD_MILLISECOND;
            case SECOND: return JST_FIELD_SECOND;
            case MINUTE: return JST_FIELD_MINUTE;
            case HOUR_12: case HOUR_24: return JST_FIELD_HOUR;
            case DAY: return JST_FIELD_DAY;
            case MONTH: return JST_FIELD_MONTH;
            case YEAR: return JST_FIELD_YEAR;
            default: return -1;
        }
    }
    
    /*
     * Class containing information about a field
     */
    var Entry = function(type, length, literal) {
        this.type = type;
        this.length = length || -1;
        this.literal = literal;
    }
    
    /*
     * Compiles the mask
     */
    this.compile = function() {
        var current = "";
        var old = "";
        var part = "";
        this.compiledMask = new Array();
        for (var i = 0; i < this.mask.length; i++) {
            current = this.mask.charAt(i);
            
            //Checks if still in the same field
            if ((part == "") || (current == part.charAt(0))) {
                part += current;
            } else {
                //Field changed: use the field
                var type = this.getFieldType(part);
                
                //Store the mask entry
                this.compiledMask[this.compiledMask.length] = new Entry(type, part.length, part);

                //Handles the field changing
                part = "";
                i--;
            }
        }
        //Checks if there's another unparsed part
        if (part != "") {
            var type = this.getFieldType(part);
            
            //Store the mask entry
            this.compiledMask[this.compiledMask.length] = new Entry(type, part.length, part);
        }
    }
    
    /*
     * Changes the format mask
     */
    this.setMask = function(mask) {
        this.mask = mask;
        this.compile();
    }
    
    //Initially set the mask
    this.setMask(this.mask);
}

///////////////////////////////////////////////////////////////////////////////
/*
 * BooleanParser - used for boolean values
 * Parameters:
 *     trueValue: The value returned when parsing true. Default: the JST_DEFAULT_TRUE_VALUE value
 *     falseValue: The value returned when parsing true. Default: the JST_DEFAULT_FALSE_VALUE value
 */
function BooleanParser(trueValue, falseValue, useBooleanValue) {
    this.base = Parser;
    this.base();

    this.trueValue = trueValue || JST_DEFAULT_TRUE_VALUE;
    this.falseValue = falseValue || JST_DEFAULT_FALSE_VALUE;
    this.useBooleanValue = useBooleanValue || JST_DEFAULT_USE_BOOLEAN_VALUE;

    this.parse = function(string) {
        if (this.useBooleanValue && booleanValue(string)) {
            return true;
        }
        return string == JST_DEFAULT_TRUE_VALUE;
    }
    
    this.format = function(bool) {
        return booleanValue(bool) ? this.trueValue : this.falseValue;
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * StringParser - Parser for String values
 */
function StringParser() {
    this.base = Parser;
    this.base();

    this.parse = function(string) {
        return String(string);
    }
    
    this.format = function(string) {
        return String(string);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * MapParser - used with a Map instance
 * Parameters:
 *     map: The Map with the values
 *     directParse: If set to true, the parse will not use the map, but will
 *                  return the text itself on the parse() method
 */
function MapParser(map, directParse) {
    this.base = Parser;
    this.base();

    this.map = isInstance(map, Map) ? map : new Map();
    this.directParse = booleanValue(directParse);
    
    this.parse = function(value) {
        if (directParse) {
            return value;
        }
        
        var pairs = this.map.getPairs();
        for (var k = 0; k < pairs.length; k++) {
            if (value == pairs[k].value) {
                return pairs[k].key;
            }
        }
        return null;
    }
    
    this.format = function(value) {
        return this.map.get(value);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * EscapeParser - used to escape / unescape characters
 * Parameters:
 *     extraChars: A string containing the characters forced to \uXXXX. 
 *                 Default: ""
 *     onlyExtra: A boolean indicating if only then extraCharacters will 
 *                be processed. Default: false
 */
function EscapeParser(extraChars, onlyExtra) {
    this.base = Parser;
    this.base();
    
    this.extraChars = extraChars || "";
    this.onlyExtra = booleanValue(onlyExtra);
    
    this.parse = function(value) {
        if (value == null) {
            return null;
        }
        return unescapeCharacters(String(value), extraChars, onlyExtra);
    }
    
    this.format = function(value) {
        if (value == null) {
            return null;
        }
        return escapeCharacters(String(value), onlyExtra);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * CustomParser - parses / formats using custom functions
 * Parameters:
 *     formatFunction: The function that will format the data
 *     parseFunction: The function that will parse the data
 */
function CustomParser(formatFunction, parseFunction) {
    this.base = Parser;
    this.base();
    
    this.formatFunction = formatFunction || function(value) { return value; };
    this.parseFunction = parseFunction || function(value) { return value; };
    
    this.parse = function(value) {
        return parseFunction.apply(this, arguments);
    }
    
    this.format = function(value) {
        return formatFunction.apply(this, arguments);
    }
}

///////////////////////////////////////////////////////////////////////////////
/*
 * WrapperParser - wraps another parser, adding custom functions to it's results
 * Parameters:
 *     wrappedParser: The wrapped parser
 *     formatFunction: The function that will format the data that has been already parsed by the wrapped parser
 *     parseFunction: The function that will parse the data before the wrapped parser
 */
function WrapperParser(wrappedParser, formatFunction, parseFunction) {
    this.base = Parser;
    this.base();
    
    this.wrappedParser = wrappedParser || new CustomParser();
    this.formatFunction = formatFunction || function(value) { return value; };
    this.parseFunction = parseFunction || function(value) { return value; };
    
    this.format = function(value) {
        var formatted = this.wrappedParser.format.apply(this.wrappedParser, arguments);
        var args = [];
        args[0] = formatted;
        args[1] = arguments[0];
        for (var i = 1, len = arguments.length; i < len; i++) {
            args[i + 1] = arguments[i];
        }
        return formatFunction.apply(this, args);
    }
    
    this.parse = function(value) {
        var parsed = parseFunction.apply(this, arguments);
        arguments[0] = parsed;
        return this.wrappedParser.parse.apply(this.wrappedParser, arguments);
    }
}
