// Patched for Cyclos: see respondToReadyState on line 1601
/*  Prototype JavaScript framework, version 1.7
 *  (c) 2005-2010 Sam Stephenson
 *
 *  Prototype is freely distributable under the terms of an MIT-style license.
 *  For details, see the Prototype web site: http://www.prototypejs.org/
 *
 *--------------------------------------------------------------------------*/

var Prototype = {

  Version: '1.7',

  Browser: (function(){
    var ua = navigator.userAgent;
    var isOpera = Object.prototype.toString.call(window.opera) == '[object Opera]';
    return {
      IE:             !!window.attachEvent && !isOpera,
      Opera:          isOpera,
      WebKit:         ua.indexOf('AppleWebKit/') > -1,
      Gecko:          ua.indexOf('Gecko') > -1 && ua.indexOf('KHTML') === -1,
      MobileSafari:   /Apple.*Mobile/.test(ua)
    }
  })(),

  BrowserFeatures: {
    XPath: !!document.evaluate,

    SelectorsAPI: !!document.querySelector,

    ElementExtensions: (function() {
      var constructor = window.Element || window.HTMLElement;
      return !!(constructor && constructor.prototype);
    })(),
    SpecificElementExtensions: (function() {
      if (typeof window.HTMLDivElement !== 'undefined')
        return true;

      var div = document.createElement('div'),
          form = document.createElement('form'),
          isSupported = false;

      if (div['__proto__'] && (div['__proto__'] !== form['__proto__'])) {
        isSupported = true;
      }

      div = form = null;

      return isSupported;
    })()
  },

  ScriptFragment: '<script[^>]*>([\\S\\s]*?)<\/script>',
  JSONFilter: /^\/\*-secure-([\s\S]*)\*\/\s*$/,

  emptyFunction: function() { },

  K: function(x) { return x }
};

if (Prototype.Browser.MobileSafari)
  Prototype.BrowserFeatures.SpecificElementExtensions = false;
/* Based on Alex Arnell's inheritance implementation. */

var Class = (function() {

  var IS_DONTENUM_BUGGY = (function(){
    for (var p in { toString: 1 }) {
      if (p === 'toString') return false;
    }
    return true;
  })();

  function subclass() {};
  function create() {
    var parent = null, properties = $A(arguments);
    if (Object.isFunction(properties[0]))
      parent = properties.shift();

    function klass() {
      this.initialize.apply(this, arguments);
    }

    Object.extend(klass, Class.Methods);
    klass.superclass = parent;
    klass.subclasses = [];

    if (parent) {
      subclass.prototype = parent.prototype;
      klass.prototype = new subclass;
      parent.subclasses.push(klass);
    }

    for (var i = 0, length = properties.length; i < length; i++)
      klass.addMethods(properties[i]);

    if (!klass.prototype.initialize)
      klass.prototype.initialize = Prototype.emptyFunction;

    klass.prototype.constructor = klass;
    return klass;
  }

  function addMethods(source) {
    var ancestor   = this.superclass && this.superclass.prototype,
        properties = Object.keys(source);

    if (IS_DONTENUM_BUGGY) {
      if (source.toString != Object.prototype.toString)
        properties.push("toString");
      if (source.valueOf != Object.prototype.valueOf)
        properties.push("valueOf");
    }

    for (var i = 0, length = properties.length; i < length; i++) {
      var property = properties[i], value = source[property];
      if (ancestor && Object.isFunction(value) &&
          value.argumentNames()[0] == "$super") {
        var method = value;
        value = (function(m) {
          return function() { return ancestor[m].apply(this, arguments); };
        })(property).wrap(method);

        value.valueOf = method.valueOf.bind(method);
        value.toString = method.toString.bind(method);
      }
      this.prototype[property] = value;
    }

    return this;
  }

  return {
    create: create,
    Methods: {
      addMethods: addMethods
    }
  };
})();
(function() {

  var _toString = Object.prototype.toString,
      NULL_TYPE = 'Null',
      UNDEFINED_TYPE = 'Undefined',
      BOOLEAN_TYPE = 'Boolean',
      NUMBER_TYPE = 'Number',
      STRING_TYPE = 'String',
      OBJECT_TYPE = 'Object',
      FUNCTION_CLASS = '[object Function]',
      BOOLEAN_CLASS = '[object Boolean]',
      NUMBER_CLASS = '[object Number]',
      STRING_CLASS = '[object String]',
      ARRAY_CLASS = '[object Array]',
      DATE_CLASS = '[object Date]',
      NATIVE_JSON_STRINGIFY_SUPPORT = window.JSON &&
        typeof JSON.stringify === 'function' &&
        JSON.stringify(0) === '0' &&
        typeof JSON.stringify(Prototype.K) === 'undefined';

  function Type(o) {
    switch(o) {
      case null: return NULL_TYPE;
      case (void 0): return UNDEFINED_TYPE;
    }
    var type = typeof o;
    switch(type) {
      case 'boolean': return BOOLEAN_TYPE;
      case 'number':  return NUMBER_TYPE;
      case 'string':  return STRING_TYPE;
    }
    return OBJECT_TYPE;
  }

  function extend(destination, source) {
    for (var property in source)
      destination[property] = source[property];
    return destination;
  }

  function inspect(object) {
    try {
      if (isUndefined(object)) return 'undefined';
      if (object === null) return 'null';
      return object.inspect ? object.inspect() : String(object);
    } catch (e) {
      if (e instanceof RangeError) return '...';
      throw e;
    }
  }

  function toJSON(value) {
    return Str('', { '': value }, []);
  }

  function Str(key, holder, stack) {
    var value = holder[key],
        type = typeof value;

    if (Type(value) === OBJECT_TYPE && typeof value.toJSON === 'function') {
      value = value.toJSON(key);
    }

    var _class = _toString.call(value);

    switch (_class) {
      case NUMBER_CLASS:
      case BOOLEAN_CLASS:
      case STRING_CLASS:
        value = value.valueOf();
    }

    switch (value) {
      case null: return 'null';
      case true: return 'true';
      case false: return 'false';
    }

    type = typeof value;
    switch (type) {
      case 'string':
        return value.inspect(true);
      case 'number':
        return isFinite(value) ? String(value) : 'null';
      case 'object':

        for (var i = 0, length = stack.length; i < length; i++) {
          if (stack[i] === value) { throw new TypeError(); }
        }
        stack.push(value);

        var partial = [];
        if (_class === ARRAY_CLASS) {
          for (var i = 0, length = value.length; i < length; i++) {
            var str = Str(i, value, stack);
            partial.push(typeof str === 'undefined' ? 'null' : str);
          }
          partial = '[' + partial.join(',') + ']';
        } else {
          var keys = Object.keys(value);
          for (var i = 0, length = keys.length; i < length; i++) {
            var key = keys[i], str = Str(key, value, stack);
            if (typeof str !== "undefined") {
               partial.push(key.inspect(true)+ ':' + str);
             }
          }
          partial = '{' + partial.join(',') + '}';
        }
        stack.pop();
        return partial;
    }
  }

  function stringify(object) {
    return JSON.stringify(object);
  }

  function toQueryString(object) {
    return $H(object).toQueryString();
  }

  function toHTML(object) {
    return object && object.toHTML ? object.toHTML() : String.interpret(object);
  }

  function keys(object) {
    if (Type(object) !== OBJECT_TYPE) { throw new TypeError(); }
    var results = [];
    for (var property in object) {
      if (object.hasOwnProperty(property)) {
        results.push(property);
      }
    }
    return results;
  }

  function values(object) {
    var results = [];
    for (var property in object)
      results.push(object[property]);
    return results;
  }

  function clone(object) {
    return extend({ }, object);
  }

  function isElement(object) {
    return !!(object && object.nodeType == 1);
  }

  function isArray(object) {
    return _toString.call(object) === ARRAY_CLASS;
  }

  var hasNativeIsArray = (typeof Array.isArray == 'function')
    && Array.isArray([]) && !Array.isArray({});

  if (hasNativeIsArray) {
    isArray = Array.isArray;
  }

  function isHash(object) {
    return object instanceof Hash;
  }

  function isFunction(object) {
    return _toString.call(object) === FUNCTION_CLASS;
  }

  function isString(object) {
    return _toString.call(object) === STRING_CLASS;
  }

  function isNumber(object) {
    return _toString.call(object) === NUMBER_CLASS;
  }

  function isDate(object) {
    return _toString.call(object) === DATE_CLASS;
  }

  function isUndefined(object) {
    return typeof object === "undefined";
  }

  extend(Object, {
    extend:        extend,
    inspect:       inspect,
    toJSON:        NATIVE_JSON_STRINGIFY_SUPPORT ? stringify : toJSON,
    toQueryString: toQueryString,
    toHTML:        toHTML,
    keys:          Object.keys || keys,
    values:        values,
    clone:         clone,
    isElement:     isElement,
    isArray:       isArray,
    isHash:        isHash,
    isFunction:    isFunction,
    isString:      isString,
    isNumber:      isNumber,
    isDate:        isDate,
    isUndefined:   isUndefined
  });
})();
Object.extend(Function.prototype, (function() {
  var slice = Array.prototype.slice;

  function update(array, args) {
    var arrayLength = array.length, length = args.length;
    while (length--) array[arrayLength + length] = args[length];
    return array;
  }

  function merge(array, args) {
    array = slice.call(array, 0);
    return update(array, args);
  }

  function argumentNames() {
    var names = this.toString().match(/^[\s\(]*function[^(]*\(([^)]*)\)/)[1]
      .replace(/\/\/.*?[\r\n]|\/\*(?:.|[\r\n])*?\*\//g, '')
      .replace(/\s+/g, '').split(',');
    return names.length == 1 && !names[0] ? [] : names;
  }

  function bind(context) {
    if (arguments.length < 2 && Object.isUndefined(arguments[0])) return this;
    var __method = this, args = slice.call(arguments, 1);
    return function() {
      var a = merge(args, arguments);
      return __method.apply(context, a);
    }
  }

  function bindAsEventListener(context) {
    var __method = this, args = slice.call(arguments, 1);
    return function(event) {
      var a = update([event || window.event], args);
      return __method.apply(context, a);
    }
  }

  function curry() {
    if (!arguments.length) return this;
    var __method = this, args = slice.call(arguments, 0);
    return function() {
      var a = merge(args, arguments);
      return __method.apply(this, a);
    }
  }

  function delay(timeout) {
    var __method = this, args = slice.call(arguments, 1);
    timeout = timeout * 1000;
    return window.setTimeout(function() {
      return __method.apply(__method, args);
    }, timeout);
  }

  function defer() {
    var args = update([0.01], arguments);
    return this.delay.apply(this, args);
  }

  function wrap(wrapper) {
    var __method = this;
    return function() {
      var a = update([__method.bind(this)], arguments);
      return wrapper.apply(this, a);
    }
  }

  function methodize() {
    if (this._methodized) return this._methodized;
    var __method = this;
    return this._methodized = function() {
      var a = update([this], arguments);
      return __method.apply(null, a);
    };
  }

  return {
    argumentNames:       argumentNames,
    bind:                bind,
    bindAsEventListener: bindAsEventListener,
    curry:               curry,
    delay:               delay,
    defer:               defer,
    wrap:                wrap,
    methodize:           methodize
  }
})());



(function(proto) {


  function toISOString() {
    return this.getUTCFullYear() + '-' +
      (this.getUTCMonth() + 1).toPaddedString(2) + '-' +
      this.getUTCDate().toPaddedString(2) + 'T' +
      this.getUTCHours().toPaddedString(2) + ':' +
      this.getUTCMinutes().toPaddedString(2) + ':' +
      this.getUTCSeconds().toPaddedString(2) + 'Z';
  }


  function toJSON() {
    return this.toISOString();
  }

  if (!proto.toISOString) proto.toISOString = toISOString;
  if (!proto.toJSON) proto.toJSON = toJSON;

})(Date.prototype);


RegExp.prototype.match = RegExp.prototype.test;

RegExp.escape = function(str) {
  return String(str).replace(/([.*+?^=!:${}()|[\]\/\\])/g, '\\$1');
};
var PeriodicalExecuter = Class.create({
  initialize: function(callback, frequency) {
    this.callback = callback;
    this.frequency = frequency;
    this.currentlyExecuting = false;

    this.registerCallback();
  },

  registerCallback: function() {
    this.timer = setInterval(this.onTimerEvent.bind(this), this.frequency * 1000);
  },

  execute: function() {
    this.callback(this);
  },

  stop: function() {
    if (!this.timer) return;
    clearInterval(this.timer);
    this.timer = null;
  },

  onTimerEvent: function() {
    if (!this.currentlyExecuting) {
      try {
        this.currentlyExecuting = true;
        this.execute();
        this.currentlyExecuting = false;
      } catch(e) {
        this.currentlyExecuting = false;
        throw e;
      }
    }
  }
});
Object.extend(String, {
  interpret: function(value) {
    return value == null ? '' : String(value);
  },
  specialChar: {
    '\b': '\\b',
    '\t': '\\t',
    '\n': '\\n',
    '\f': '\\f',
    '\r': '\\r',
    '\\': '\\\\'
  }
});

Object.extend(String.prototype, (function() {
  var NATIVE_JSON_PARSE_SUPPORT = window.JSON &&
    typeof JSON.parse === 'function' &&
    JSON.parse('{"test": true}').test;

  function prepareReplacement(replacement) {
    if (Object.isFunction(replacement)) return replacement;
    var template = new Template(replacement);
    return function(match) { return template.evaluate(match) };
  }

  function gsub(pattern, replacement) {
    var result = '', source = this, match;
    replacement = prepareReplacement(replacement);

    if (Object.isString(pattern))
      pattern = RegExp.escape(pattern);

    if (!(pattern.length || pattern.source)) {
      replacement = replacement('');
      return replacement + source.split('').join(replacement) + replacement;
    }

    while (source.length > 0) {
      if (match = source.match(pattern)) {
        result += source.slice(0, match.index);
        result += String.interpret(replacement(match));
        source  = source.slice(match.index + match[0].length);
      } else {
        result += source, source = '';
      }
    }
    return result;
  }

  function sub(pattern, replacement, count) {
    replacement = prepareReplacement(replacement);
    count = Object.isUndefined(count) ? 1 : count;

    return this.gsub(pattern, function(match) {
      if (--count < 0) return match[0];
      return replacement(match);
    });
  }

  function scan(pattern, iterator) {
    this.gsub(pattern, iterator);
    return String(this);
  }

  function truncate(length, truncation) {
    length = length || 30;
    truncation = Object.isUndefined(truncation) ? '...' : truncation;
    return this.length > length ?
      this.slice(0, length - truncation.length) + truncation : String(this);
  }

  function strip() {
    return this.replace(/^\s+/, '').replace(/\s+$/, '');
  }

  function stripTags() {
    return this.replace(/<\w+(\s+("[^"]*"|'[^']*'|[^>])+)?>|<\/\w+>/gi, '');
  }

  function stripScripts() {
    return this.replace(new RegExp(Prototype.ScriptFragment, 'img'), '');
  }

  function extractScripts() {
    var matchAll = new RegExp(Prototype.ScriptFragment, 'img'),
        matchOne = new RegExp(Prototype.ScriptFragment, 'im');
    return (this.match(matchAll) || []).map(function(scriptTag) {
      return (scriptTag.match(matchOne) || ['', ''])[1];
    });
  }

  function evalScripts() {
    return this.extractScripts().map(function(script) { return eval(script) });
  }

  function escapeHTML() {
    return this.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
  }

  function unescapeHTML() {
    return this.stripTags().replace(/&lt;/g,'<').replace(/&gt;/g,'>').replace(/&amp;/g,'&');
  }


  function toQueryParams(separator) {
    var match = this.strip().match(/([^?#]*)(#.*)?$/);
    if (!match) return { };

    return match[1].split(separator || '&').inject({ }, function(hash, pair) {
      if ((pair = pair.split('='))[0]) {
        var key = decodeURIComponent(pair.shift()),
            value = pair.length > 1 ? pair.join('=') : pair[0];

        if (value != undefined) value = decodeURIComponent(value);

        if (key in hash) {
          if (!Object.isArray(hash[key])) hash[key] = [hash[key]];
          hash[key].push(value);
        }
        else hash[key] = value;
      }
      return hash;
    });
  }

  function toArray() {
    return this.split('');
  }

  function succ() {
    return this.slice(0, this.length - 1) +
      String.fromCharCode(this.charCodeAt(this.length - 1) + 1);
  }

  function times(count) {
    return count < 1 ? '' : new Array(count + 1).join(this);
  }

  function camelize() {
    return this.replace(/-+(.)?/g, function(match, chr) {
      return chr ? chr.toUpperCase() : '';
    });
  }

  function capitalize() {
    return this.charAt(0).toUpperCase() + this.substring(1).toLowerCase();
  }

  function underscore() {
    return this.replace(/::/g, '/')
               .replace(/([A-Z]+)([A-Z][a-z])/g, '$1_$2')
               .replace(/([a-z\d])([A-Z])/g, '$1_$2')
               .replace(/-/g, '_')
               .toLowerCase();
  }

  function dasherize() {
    return this.replace(/_/g, '-');
  }

  function inspect(useDoubleQuotes) {
    var escapedString = this.replace(/[\x00-\x1f\\]/g, function(character) {
      if (character in String.specialChar) {
        return String.specialChar[character];
      }
      return '\\u00' + character.charCodeAt().toPaddedString(2, 16);
    });
    if (useDoubleQuotes) return '"' + escapedString.replace(/"/g, '\\"') + '"';
    return "'" + escapedString.replace(/'/g, '\\\'') + "'";
  }

  function unfilterJSON(filter) {
    return this.replace(filter || Prototype.JSONFilter, '$1');
  }

  function isJSON() {
    var str = this;
    if (str.blank()) return false;
    str = str.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@');
    str = str.replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']');
    str = str.replace(/(?:^|:|,)(?:\s*\[)+/g, '');
    return (/^[\],:{}\s]*$/).test(str);
  }

  function evalJSON(sanitize) {
    var json = this.unfilterJSON(),
        cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
    if (cx.test(json)) {
      json = json.replace(cx, function (a) {
        return '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
      });
    }
    try {
      if (!sanitize || json.isJSON()) return eval('(' + json + ')');
    } catch (e) { }
    throw new SyntaxError('Badly formed JSON string: ' + this.inspect());
  }

  function parseJSON() {
    var json = this.unfilterJSON();
    return JSON.parse(json);
  }

  function include(pattern) {
    return this.indexOf(pattern) > -1;
  }

  function startsWith(pattern) {
    return this.lastIndexOf(pattern, 0) === 0;
  }

  function endsWith(pattern) {
    var d = this.length - pattern.length;
    return d >= 0 && this.indexOf(pattern, d) === d;
  }

  function empty() {
    return this == '';
  }

  function blank() {
    return /^\s*$/.test(this);
  }

  function interpolate(object, pattern) {
    return new Template(this, pattern).evaluate(object);
  }

  return {
    gsub:           gsub,
    sub:            sub,
    scan:           scan,
    truncate:       truncate,
    strip:          String.prototype.trim || strip,
    stripTags:      stripTags,
    stripScripts:   stripScripts,
    extractScripts: extractScripts,
    evalScripts:    evalScripts,
    escapeHTML:     escapeHTML,
    unescapeHTML:   unescapeHTML,
    toQueryParams:  toQueryParams,
    parseQuery:     toQueryParams,
    toArray:        toArray,
    succ:           succ,
    times:          times,
    camelize:       camelize,
    capitalize:     capitalize,
    underscore:     underscore,
    dasherize:      dasherize,
    inspect:        inspect,
    unfilterJSON:   unfilterJSON,
    isJSON:         isJSON,
    evalJSON:       NATIVE_JSON_PARSE_SUPPORT ? parseJSON : evalJSON,
    include:        include,
    startsWith:     startsWith,
    endsWith:       endsWith,
    empty:          empty,
    blank:          blank,
    interpolate:    interpolate
  };
})());

var Template = Class.create({
  initialize: function(template, pattern) {
    this.template = template.toString();
    this.pattern = pattern || Template.Pattern;
  },

  evaluate: function(object) {
    if (object && Object.isFunction(object.toTemplateReplacements))
      object = object.toTemplateReplacements();

    return this.template.gsub(this.pattern, function(match) {
      if (object == null) return (match[1] + '');

      var before = match[1] || '';
      if (before == '\\') return match[2];

      var ctx = object, expr = match[3],
          pattern = /^([^.[]+|\[((?:.*?[^\\])?)\])(\.|\[|$)/;

      match = pattern.exec(expr);
      if (match == null) return before;

      while (match != null) {
        var comp = match[1].startsWith('[') ? match[2].replace(/\\\\]/g, ']') : match[1];
        ctx = ctx[comp];
        if (null == ctx || '' == match[3]) break;
        expr = expr.substring('[' == match[3] ? match[1].length : match[0].length);
        match = pattern.exec(expr);
      }

      return before + String.interpret(ctx);
    });
  }
});
Template.Pattern = /(^|.|\r|\n)(#\{(.*?)\})/;

var $break = { };

var Enumerable = (function() {
  function each(iterator, context) {
    var index = 0;
    try {
      this._each(function(value) {
        iterator.call(context, value, index++);
      });
    } catch (e) {
      if (e != $break) throw e;
    }
    return this;
  }

  function eachSlice(number, iterator, context) {
    var index = -number, slices = [], array = this.toArray();
    if (number < 1) return array;
    while ((index += number) < array.length)
      slices.push(array.slice(index, index+number));
    return slices.collect(iterator, context);
  }

  function all(iterator, context) {
    iterator = iterator || Prototype.K;
    var result = true;
    this.each(function(value, index) {
      result = result && !!iterator.call(context, value, index);
      if (!result) throw $break;
    });
    return result;
  }

  function any(iterator, context) {
    iterator = iterator || Prototype.K;
    var result = false;
    this.each(function(value, index) {
      if (result = !!iterator.call(context, value, index))
        throw $break;
    });
    return result;
  }

  function collect(iterator, context) {
    iterator = iterator || Prototype.K;
    var results = [];
    this.each(function(value, index) {
      results.push(iterator.call(context, value, index));
    });
    return results;
  }

  function detect(iterator, context) {
    var result;
    this.each(function(value, index) {
      if (iterator.call(context, value, index)) {
        result = value;
        throw $break;
      }
    });
    return result;
  }

  function findAll(iterator, context) {
    var results = [];
    this.each(function(value, index) {
      if (iterator.call(context, value, index))
        results.push(value);
    });
    return results;
  }

  function grep(filter, iterator, context) {
    iterator = iterator || Prototype.K;
    var results = [];

    if (Object.isString(filter))
      filter = new RegExp(RegExp.escape(filter));

    this.each(function(value, index) {
      if (filter.match(value))
        results.push(iterator.call(context, value, index));
    });
    return results;
  }

  function include(object) {
    if (Object.isFunction(this.indexOf))
      if (this.indexOf(object) != -1) return true;

    var found = false;
    this.each(function(value) {
      if (value == object) {
        found = true;
        throw $break;
      }
    });
    return found;
  }

  function inGroupsOf(number, fillWith) {
    fillWith = Object.isUndefined(fillWith) ? null : fillWith;
    return this.eachSlice(number, function(slice) {
      while(slice.length < number) slice.push(fillWith);
      return slice;
    });
  }

  function inject(memo, iterator, context) {
    this.each(function(value, index) {
      memo = iterator.call(context, memo, value, index);
    });
    return memo;
  }

  function invoke(method) {
    var args = $A(arguments).slice(1);
    return this.map(function(value) {
      return value[method].apply(value, args);
    });
  }

  function max(iterator, context) {
    iterator = iterator || Prototype.K;
    var result;
    this.each(function(value, index) {
      value = iterator.call(context, value, index);
      if (result == null || value >= result)
        result = value;
    });
    return result;
  }

  function min(iterator, context) {
    iterator = iterator || Prototype.K;
    var result;
    this.each(function(value, index) {
      value = iterator.call(context, value, index);
      if (result == null || value < result)
        result = value;
    });
    return result;
  }

  function partition(iterator, context) {
    iterator = iterator || Prototype.K;
    var trues = [], falses = [];
    this.each(function(value, index) {
      (iterator.call(context, value, index) ?
        trues : falses).push(value);
    });
    return [trues, falses];
  }

  function pluck(property) {
    var results = [];
    this.each(function(value) {
      results.push(value[property]);
    });
    return results;
  }

  function reject(iterator, context) {
    var results = [];
    this.each(function(value, index) {
      if (!iterator.call(context, value, index))
        results.push(value);
    });
    return results;
  }

  function sortBy(iterator, context) {
    return this.map(function(value, index) {
      return {
        value: value,
        criteria: iterator.call(context, value, index)
      };
    }).sort(function(left, right) {
      var a = left.criteria, b = right.criteria;
      return a < b ? -1 : a > b ? 1 : 0;
    }).pluck('value');
  }

  function toArray() {
    return this.map();
  }

  function zip() {
    var iterator = Prototype.K, args = $A(arguments);
    if (Object.isFunction(args.last()))
      iterator = args.pop();

    var collections = [this].concat(args).map($A);
    return this.map(function(value, index) {
      return iterator(collections.pluck(index));
    });
  }

  function size() {
    return this.toArray().length;
  }

  function inspect() {
    return '#<Enumerable:' + this.toArray().inspect() + '>';
  }









  return {
    each:       each,
    eachSlice:  eachSlice,
    all:        all,
    every:      all,
    any:        any,
    some:       any,
    collect:    collect,
    map:        collect,
    detect:     detect,
    findAll:    findAll,
    select:     findAll,
    filter:     findAll,
    grep:       grep,
    include:    include,
    member:     include,
    inGroupsOf: inGroupsOf,
    inject:     inject,
    invoke:     invoke,
    max:        max,
    min:        min,
    partition:  partition,
    pluck:      pluck,
    reject:     reject,
    sortBy:     sortBy,
    toArray:    toArray,
    entries:    toArray,
    zip:        zip,
    size:       size,
    inspect:    inspect,
    find:       detect
  };
})();

function $A(iterable) {
  if (!iterable) return [];
  if ('toArray' in Object(iterable)) return iterable.toArray();
  var length = iterable.length || 0, results = new Array(length);
  while (length--) results[length] = iterable[length];
  return results;
}


function $w(string) {
  if (!Object.isString(string)) return [];
  string = string.strip();
  return string ? string.split(/\s+/) : [];
}

Array.from = $A;


(function() {
  var arrayProto = Array.prototype,
      slice = arrayProto.slice,
      _each = arrayProto.forEach; // use native browser JS 1.6 implementation if available

  function each(iterator, context) {
    for (var i = 0, length = this.length >>> 0; i < length; i++) {
      if (i in this) iterator.call(context, this[i], i, this);
    }
  }
  if (!_each) _each = each;

  function clear() {
    this.length = 0;
    return this;
  }

  function first() {
    return this[0];
  }

  function last() {
    return this[this.length - 1];
  }

  function compact() {
    return this.select(function(value) {
      return value != null;
    });
  }

  function flatten() {
    return this.inject([], function(array, value) {
      if (Object.isArray(value))
        return array.concat(value.flatten());
      array.push(value);
      return array;
    });
  }

  function without() {
    var values = slice.call(arguments, 0);
    return this.select(function(value) {
      return !values.include(value);
    });
  }

  function reverse(inline) {
    return (inline === false ? this.toArray() : this)._reverse();
  }

  function uniq(sorted) {
    return this.inject([], function(array, value, index) {
      if (0 == index || (sorted ? array.last() != value : !array.include(value)))
        array.push(value);
      return array;
    });
  }

  function intersect(array) {
    return this.uniq().findAll(function(item) {
      return array.detect(function(value) { return item === value });
    });
  }


  function clone() {
    return slice.call(this, 0);
  }

  function size() {
    return this.length;
  }

  function inspect() {
    return '[' + this.map(Object.inspect).join(', ') + ']';
  }

  function indexOf(item, i) {
    i || (i = 0);
    var length = this.length;
    if (i < 0) i = length + i;
    for (; i < length; i++)
      if (this[i] === item) return i;
    return -1;
  }

  function lastIndexOf(item, i) {
    i = isNaN(i) ? this.length : (i < 0 ? this.length + i : i) + 1;
    var n = this.slice(0, i).reverse().indexOf(item);
    return (n < 0) ? n : i - n - 1;
  }

  function concat() {
    var array = slice.call(this, 0), item;
    for (var i = 0, length = arguments.length; i < length; i++) {
      item = arguments[i];
      if (Object.isArray(item) && !('callee' in item)) {
        for (var j = 0, arrayLength = item.length; j < arrayLength; j++)
          array.push(item[j]);
      } else {
        array.push(item);
      }
    }
    return array;
  }

  Object.extend(arrayProto, Enumerable);

  if (!arrayProto._reverse)
    arrayProto._reverse = arrayProto.reverse;

  Object.extend(arrayProto, {
    _each:     _each,
    clear:     clear,
    first:     first,
    last:      last,
    compact:   compact,
    flatten:   flatten,
    without:   without,
    reverse:   reverse,
    uniq:      uniq,
    intersect: intersect,
    clone:     clone,
    toArray:   clone,
    size:      size,
    inspect:   inspect
  });

  var CONCAT_ARGUMENTS_BUGGY = (function() {
    return [].concat(arguments)[0][0] !== 1;
  })(1,2)

  if (CONCAT_ARGUMENTS_BUGGY) arrayProto.concat = concat;

  if (!arrayProto.indexOf) arrayProto.indexOf = indexOf;
  if (!arrayProto.lastIndexOf) arrayProto.lastIndexOf = lastIndexOf;
})();
function $H(object) {
  return new Hash(object);
};

var Hash = Class.create(Enumerable, (function() {
  function initialize(object) {
    this._object = Object.isHash(object) ? object.toObject() : Object.clone(object);
  }


  function _each(iterator) {
    for (var key in this._object) {
      var value = this._object[key], pair = [key, value];
      pair.key = key;
      pair.value = value;
      iterator(pair);
    }
  }

  function set(key, value) {
    return this._object[key] = value;
  }

  function get(key) {
    if (this._object[key] !== Object.prototype[key])
      return this._object[key];
  }

  function unset(key) {
    var value = this._object[key];
    delete this._object[key];
    return value;
  }

  function toObject() {
    return Object.clone(this._object);
  }



  function keys() {
    return this.pluck('key');
  }

  function values() {
    return this.pluck('value');
  }

  function index(value) {
    var match = this.detect(function(pair) {
      return pair.value === value;
    });
    return match && match.key;
  }

  function merge(object) {
    return this.clone().update(object);
  }

  function update(object) {
    return new Hash(object).inject(this, function(result, pair) {
      result.set(pair.key, pair.value);
      return result;
    });
  }

  function toQueryPair(key, value) {
    if (Object.isUndefined(value)) return key;
    return key + '=' + encodeURIComponent(String.interpret(value));
  }

  function toQueryString() {
    return this.inject([], function(results, pair) {
      var key = encodeURIComponent(pair.key), values = pair.value;

      if (values && typeof values == 'object') {
        if (Object.isArray(values)) {
          var queryValues = [];
          for (var i = 0, len = values.length, value; i < len; i++) {
            value = values[i];
            queryValues.push(toQueryPair(key, value));
          }
          return results.concat(queryValues);
        }
      } else results.push(toQueryPair(key, values));
      return results;
    }).join('&');
  }

  function inspect() {
    return '#<Hash:{' + this.map(function(pair) {
      return pair.map(Object.inspect).join(': ');
    }).join(', ') + '}>';
  }

  function clone() {
    return new Hash(this);
  }

  return {
    initialize:             initialize,
    _each:                  _each,
    set:                    set,
    get:                    get,
    unset:                  unset,
    toObject:               toObject,
    toTemplateReplacements: toObject,
    keys:                   keys,
    values:                 values,
    index:                  index,
    merge:                  merge,
    update:                 update,
    toQueryString:          toQueryString,
    inspect:                inspect,
    toJSON:                 toObject,
    clone:                  clone
  };
})());

Hash.from = $H;
Object.extend(Number.prototype, (function() {
  function toColorPart() {
    return this.toPaddedString(2, 16);
  }

  function succ() {
    return this + 1;
  }

  function times(iterator, context) {
    $R(0, this, true).each(iterator, context);
    return this;
  }

  function toPaddedString(length, radix) {
    var string = this.toString(radix || 10);
    return '0'.times(length - string.length) + string;
  }

  function abs() {
    return Math.abs(this);
  }

  function round() {
    return Math.round(this);
  }

  function ceil() {
    return Math.ceil(this);
  }

  function floor() {
    return Math.floor(this);
  }

  return {
    toColorPart:    toColorPart,
    succ:           succ,
    times:          times,
    toPaddedString: toPaddedString,
    abs:            abs,
    round:          round,
    ceil:           ceil,
    floor:          floor
  };
})());

function $R(start, end, exclusive) {
  return new ObjectRange(start, end, exclusive);
}

var ObjectRange = Class.create(Enumerable, (function() {
  function initialize(start, end, exclusive) {
    this.start = start;
    this.end = end;
    this.exclusive = exclusive;
  }

  function _each(iterator) {
    var value = this.start;
    while (this.include(value)) {
      iterator(value);
      value = value.succ();
    }
  }

  function include(value) {
    if (value < this.start)
      return false;
    if (this.exclusive)
      return value < this.end;
    return value <= this.end;
  }

  return {
    initialize: initialize,
    _each:      _each,
    include:    include
  };
})());



var Abstract = { };


var Try = {
  these: function() {
    var returnValue;

    for (var i = 0, length = arguments.length; i < length; i++) {
      var lambda = arguments[i];
      try {
        returnValue = lambda();
        break;
      } catch (e) { }
    }

    return returnValue;
  }
};

var Ajax = {
  getTransport: function() {
    return Try.these(
      function() {return new XMLHttpRequest()},
      function() {return new ActiveXObject('Msxml2.XMLHTTP')},
      function() {return new ActiveXObject('Microsoft.XMLHTTP')}
    ) || false;
  },

  activeRequestCount: 0
};

Ajax.Responders = {
  responders: [],

  _each: function(iterator) {
    this.responders._each(iterator);
  },

  register: function(responder) {
    if (!this.include(responder))
      this.responders.push(responder);
  },

  unregister: function(responder) {
    this.responders = this.responders.without(responder);
  },

  dispatch: function(callback, request, transport, json) {
    this.each(function(responder) {
      if (Object.isFunction(responder[callback])) {
        try {
          responder[callback].apply(responder, [request, transport, json]);
        } catch (e) { }
      }
    });
  }
};

Object.extend(Ajax.Responders, Enumerable);

Ajax.Responders.register({
  onCreate:   function() { Ajax.activeRequestCount++ },
  onComplete: function() { Ajax.activeRequestCount-- }
});
Ajax.Base = Class.create({
  initialize: function(options) {
    this.options = {
      method:       'post',
      asynchronous: true,
      contentType:  'application/x-www-form-urlencoded',
      encoding:     'UTF-8',
      parameters:   '',
      evalJSON:     true,
      evalJS:       true
    };
    Object.extend(this.options, options || { });

    this.options.method = this.options.method.toLowerCase();

    if (Object.isHash(this.options.parameters))
      this.options.parameters = this.options.parameters.toObject();
  }
});
Ajax.Request = Class.create(Ajax.Base, {
  _complete: false,

  initialize: function($super, url, options) {
    $super(options);
    this.transport = Ajax.getTransport();
    this.request(url);
  },

  request: function(url) {
    this.url = url;
    this.method = this.options.method;
    var params = Object.isString(this.options.parameters) ?
          this.options.parameters :
          Object.toQueryString(this.options.parameters);

    if (!['get', 'post'].include(this.method)) {
      params += (params ? '&' : '') + "_method=" + this.method;
      this.method = 'post';
    }

    if (params && this.method === 'get') {
      this.url += (this.url.include('?') ? '&' : '?') + params;
    }

    this.parameters = params.toQueryParams();

    try {
      var response = new Ajax.Response(this);
      if (this.options.onCreate) this.options.onCreate(response);
      Ajax.Responders.dispatch('onCreate', this, response);

      this.transport.open(this.method.toUpperCase(), this.url,
        this.options.asynchronous);

      if (this.options.asynchronous) this.respondToReadyState.bind(this).defer(1);

      this.transport.onreadystatechange = this.onStateChange.bind(this);
      this.setRequestHeaders();

      this.body = this.method == 'post' ? (this.options.postBody || params) : null;
      this.transport.send(this.body);

      /* Force Firefox to handle ready state 4 for synchronous requests */
      if (!this.options.asynchronous && this.transport.overrideMimeType)
        this.onStateChange();

    }
    catch (e) {
      this.dispatchException(e);
    }
  },

  onStateChange: function() {
    var readyState = this.transport.readyState;
    if (readyState > 1 && !((readyState == 4) && this._complete))
      this.respondToReadyState(this.transport.readyState);
  },

  setRequestHeaders: function() {
    var headers = {
      'X-Requested-With': 'XMLHttpRequest',
      'X-Prototype-Version': Prototype.Version,
      'Accept': 'text/javascript, text/html, application/xml, text/xml, */*'
    };

    if (this.method == 'post') {
      headers['Content-type'] = this.options.contentType +
        (this.options.encoding ? '; charset=' + this.options.encoding : '');

      /* Force "Connection: close" for older Mozilla browsers to work
       * around a bug where XMLHttpRequest sends an incorrect
       * Content-length header. See Mozilla Bugzilla #246651.
       */
      if (this.transport.overrideMimeType &&
          (navigator.userAgent.match(/Gecko\/(\d{4})/) || [0,2005])[1] < 2005)
            headers['Connection'] = 'close';
    }

    if (typeof this.options.requestHeaders == 'object') {
      var extras = this.options.requestHeaders;

      if (Object.isFunction(extras.push))
        for (var i = 0, length = extras.length; i < length; i += 2)
          headers[extras[i]] = extras[i+1];
      else
        $H(extras).each(function(pair) { headers[pair.key] = pair.value });
    }

    for (var name in headers)
      this.transport.setRequestHeader(name, headers[name]);
  },

  success: function() {
    var status = this.getStatus();
    return !status || (status >= 200 && status < 300) || status == 304;
  },

  getStatus: function() {
    try {
      if (this.transport.status === 1223) return 204;
      return this.transport.status || 0;
    } catch (e) { return 0 }
  },

  respondToReadyState: function(readyState) {
    var state = Ajax.Request.Events[readyState], response = new Ajax.Response(this);

    if (state == 'Complete') {
      try {
        this._complete = true;
        (this.options['on' + response.status]
         || this.options['on' + (this.success() ? 'Success' : 'Failure')]
         || Prototype.emptyFunction)(response, response.responseJSON); //Originally, was response.headerJSON
      } catch (e) {
        this.dispatchException(e);
      }

      var contentType = response.getHeader('Content-type');
      if (this.options.evalJS == 'force'
          || (this.options.evalJS && this.isSameOrigin() && contentType
          && contentType.match(/^\s*(text|application)\/(x-)?(java|ecma)script(;.*)?\s*$/i)))
        this.evalResponse();
    }

    try {
      (this.options['on' + state] || Prototype.emptyFunction)(response, response.headerJSON);
      Ajax.Responders.dispatch('on' + state, this, response, response.headerJSON);
    } catch (e) {
      this.dispatchException(e);
    }

    if (state == 'Complete') {
      this.transport.onreadystatechange = Prototype.emptyFunction;
    }
  },

  isSameOrigin: function() {
    var m = this.url.match(/^\s*https?:\/\/[^\/]*/);
    return !m || (m[0] == '#{protocol}//#{domain}#{port}'.interpolate({
      protocol: location.protocol,
      domain: document.domain,
      port: location.port ? ':' + location.port : ''
    }));
  },

  getHeader: function(name) {
    try {
      return this.transport.getResponseHeader(name) || null;
    } catch (e) { return null; }
  },

  evalResponse: function() {
    try {
      return eval((this.transport.responseText || '').unfilterJSON());
    } catch (e) {
      this.dispatchException(e);
    }
  },

  dispatchException: function(exception) {
    (this.options.onException || Prototype.emptyFunction)(this, exception);
    Ajax.Responders.dispatch('onException', this, exception);
  }
});

Ajax.Request.Events =
  ['Uninitialized', 'Loading', 'Loaded', 'Interactive', 'Complete'];








Ajax.Response = Class.create({
  initialize: function(request){
    this.request = request;
    var transport  = this.transport  = request.transport,
        readyState = this.readyState = transport.readyState;

    if ((readyState > 2 && !Prototype.Browser.IE) || readyState == 4) {
      this.status       = this.getStatus();
      this.statusText   = this.getStatusText();
      this.responseText = String.interpret(transport.responseText);
      this.headerJSON   = this._getHeaderJSON();
    }

    if (readyState == 4) {
      var xml = transport.responseXML;
      this.responseXML  = Object.isUndefined(xml) ? null : xml;
      this.responseJSON = this._getResponseJSON();
    }
  },

  status:      0,

  statusText: '',

  getStatus: Ajax.Request.prototype.getStatus,

  getStatusText: function() {
    try {
      return this.transport.statusText || '';
    } catch (e) { return '' }
  },

  getHeader: Ajax.Request.prototype.getHeader,

  getAllHeaders: function() {
    try {
      return this.getAllResponseHeaders();
    } catch (e) { return null }
  },

  getResponseHeader: function(name) {
    return this.transport.getResponseHeader(name);
  },

  getAllResponseHeaders: function() {
    return this.transport.getAllResponseHeaders();
  },

  _getHeaderJSON: function() {
    var json = this.getHeader('X-JSON');
    if (!json) return null;
    json = decodeURIComponent(escape(json));
    try {
      return json.evalJSON(this.request.options.sanitizeJSON ||
        !this.request.isSameOrigin());
    } catch (e) {
      this.request.dispatchException(e);
    }
  },

  _getResponseJSON: function() {
    var options = this.request.options;
    if (!options.evalJSON || (options.evalJSON != 'force' &&
      !(this.getHeader('Content-type') || '').include('application/json')) ||
        this.responseText.blank())
          return null;
    try {
      return this.responseText.evalJSON(options.sanitizeJSON ||
        !this.request.isSameOrigin());
    } catch (e) {
      this.request.dispatchException(e);
    }
  }
});

Ajax.Updater = Class.create(Ajax.Request, {
  initialize: function($super, container, url, options) {
    this.container = {
      success: (container.success || container),
      failure: (container.failure || (container.success ? null : container))
    };

    options = Object.clone(options);
    var onComplete = options.onComplete;
    options.onComplete = (function(response, json) {
      this.updateContent(response.responseText);
      if (Object.isFunction(onComplete)) onComplete(response, json);
    }).bind(this);

    $super(url, options);
  },

  updateContent: function(responseText) {
    var receiver = this.container[this.success() ? 'success' : 'failure'],
        options = this.options;

    if (!options.evalScripts) responseText = responseText.stripScripts();

    if (receiver = $(receiver)) {
      if (options.insertion) {
        if (Object.isString(options.insertion)) {
          var insertion = { }; insertion[options.insertion] = responseText;
          receiver.insert(insertion);
        }
        else options.insertion(receiver, responseText);
      }
      else receiver.update(responseText);
    }
  }
});

Ajax.PeriodicalUpdater = Class.create(Ajax.Base, {
  initialize: function($super, container, url, options) {
    $super(options);
    this.onComplete = this.options.onComplete;

    this.frequency = (this.options.frequency || 2);
    this.decay = (this.options.decay || 1);

    this.updater = { };
    this.container = container;
    this.url = url;

    this.start();
  },

  start: function() {
    this.options.onComplete = this.updateComplete.bind(this);
    this.onTimerEvent();
  },

  stop: function() {
    this.updater.options.onComplete = undefined;
    clearTimeout(this.timer);
    (this.onComplete || Prototype.emptyFunction).apply(this, arguments);
  },

  updateComplete: function(response) {
    if (this.options.decay) {
      this.decay = (response.responseText == this.lastText ?
        this.decay * this.options.decay : 1);

      this.lastText = response.responseText;
    }
    this.timer = this.onTimerEvent.bind(this).delay(this.decay * this.frequency);
  },

  onTimerEvent: function() {
    this.updater = new Ajax.Updater(this.container, this.url, this.options);
  }
});


function $(element) {
  if (arguments.length > 1) {
    for (var i = 0, elements = [], length = arguments.length; i < length; i++)
      elements.push($(arguments[i]));
    return elements;
  }
  if (Object.isString(element))
    element = document.getElementById(element);
  return Element.extend(element);
}

if (Prototype.BrowserFeatures.XPath) {
  document._getElementsByXPath = function(expression, parentElement) {
    var results = [];
    var query = document.evaluate(expression, $(parentElement) || document,
      null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
    for (var i = 0, length = query.snapshotLength; i < length; i++)
      results.push(Element.extend(query.snapshotItem(i)));
    return results;
  };
}

/*--------------------------------------------------------------------------*/

if (!Node) var Node = { };

if (!Node.ELEMENT_NODE) {
  Object.extend(Node, {
    ELEMENT_NODE: 1,
    ATTRIBUTE_NODE: 2,
    TEXT_NODE: 3,
    CDATA_SECTION_NODE: 4,
    ENTITY_REFERENCE_NODE: 5,
    ENTITY_NODE: 6,
    PROCESSING_INSTRUCTION_NODE: 7,
    COMMENT_NODE: 8,
    DOCUMENT_NODE: 9,
    DOCUMENT_TYPE_NODE: 10,
    DOCUMENT_FRAGMENT_NODE: 11,
    NOTATION_NODE: 12
  });
}



(function(global) {
  function shouldUseCache(tagName, attributes) {
    if (tagName === 'select') return false;
    if ('type' in attributes) return false;
    return true;
  }

  var HAS_EXTENDED_CREATE_ELEMENT_SYNTAX = (function(){
    try {
      var el = document.createElement('<input name="x">');
      return el.tagName.toLowerCase() === 'input' && el.name === 'x';
    }
    catch(err) {
      return false;
    }
  })();

  var element = global.Element;

  global.Element = function(tagName, attributes) {
    attributes = attributes || { };
    tagName = tagName.toLowerCase();
    var cache = Element.cache;

    if (HAS_EXTENDED_CREATE_ELEMENT_SYNTAX && attributes.name) {
      tagName = '<' + tagName + ' name="' + attributes.name + '">';
      delete attributes.name;
      return Element.writeAttribute(document.createElement(tagName), attributes);
    }

    if (!cache[tagName]) cache[tagName] = Element.extend(document.createElement(tagName));

    var node = shouldUseCache(tagName, attributes) ?
     cache[tagName].cloneNode(false) : document.createElement(tagName);

    return Element.writeAttribute(node, attributes);
  };

  Object.extend(global.Element, element || { });
  if (element) global.Element.prototype = element.prototype;

})(this);

Element.idCounter = 1;
Element.cache = { };

Element._purgeElement = function(element) {
  var uid = element._prototypeUID;
  if (uid) {
    Element.stopObserving(element);
    element._prototypeUID = void 0;
    delete Element.Storage[uid];
  }
}

Element.Methods = {
  visible: function(element) {
    return $(element).style.display != 'none';
  },

  toggle: function(element) {
    element = $(element);
    Element[Element.visible(element) ? 'hide' : 'show'](element);
    return element;
  },

  hide: function(element) {
    element = $(element);
    element.style.display = 'none';
    return element;
  },

  show: function(element) {
    element = $(element);
    element.style.display = '';
    return element;
  },

  remove: function(element) {
    element = $(element);
    element.parentNode.removeChild(element);
    return element;
  },

  update: (function(){

    var SELECT_ELEMENT_INNERHTML_BUGGY = (function(){
      var el = document.createElement("select"),
          isBuggy = true;
      el.innerHTML = "<option value=\"test\">test</option>";
      if (el.options && el.options[0]) {
        isBuggy = el.options[0].nodeName.toUpperCase() !== "OPTION";
      }
      el = null;
      return isBuggy;
    })();

    var TABLE_ELEMENT_INNERHTML_BUGGY = (function(){
      try {
        var el = document.createElement("table");
        if (el && el.tBodies) {
          el.innerHTML = "<tbody><tr><td>test</td></tr></tbody>";
          var isBuggy = typeof el.tBodies[0] == "undefined";
          el = null;
          return isBuggy;
        }
      } catch (e) {
        return true;
      }
    })();

    var LINK_ELEMENT_INNERHTML_BUGGY = (function() {
      try {
        var el = document.createElement('div');
        el.innerHTML = "<link>";
        var isBuggy = (el.childNodes.length === 0);
        el = null;
        return isBuggy;
      } catch(e) {
        return true;
      }
    })();

    var ANY_INNERHTML_BUGGY = SELECT_ELEMENT_INNERHTML_BUGGY ||
     TABLE_ELEMENT_INNERHTML_BUGGY || LINK_ELEMENT_INNERHTML_BUGGY;

    var SCRIPT_ELEMENT_REJECTS_TEXTNODE_APPENDING = (function () {
      var s = document.createElement("script"),
          isBuggy = false;
      try {
        s.appendChild(document.createTextNode(""));
        isBuggy = !s.firstChild ||
          s.firstChild && s.firstChild.nodeType !== 3;
      } catch (e) {
        isBuggy = true;
      }
      s = null;
      return isBuggy;
    })();


    function update(element, content) {
      element = $(element);
      var purgeElement = Element._purgeElement;

      var descendants = element.getElementsByTagName('*'),
       i = descendants.length;
      while (i--) purgeElement(descendants[i]);

      if (content && content.toElement)
        content = content.toElement();

      if (Object.isElement(content))
        return element.update().insert(content);

      content = Object.toHTML(content);

      var tagName = element.tagName.toUpperCase();

      if (tagName === 'SCRIPT' && SCRIPT_ELEMENT_REJECTS_TEXTNODE_APPENDING) {
        element.text = content;
        return element;
      }

      if (ANY_INNERHTML_BUGGY) {
        if (tagName in Element._insertionTranslations.tags) {
          while (element.firstChild) {
            element.removeChild(element.firstChild);
          }
          Element._getContentFromAnonymousElement(tagName, content.stripScripts())
            .each(function(node) {
              element.appendChild(node)
            });
        } else if (LINK_ELEMENT_INNERHTML_BUGGY && Object.isString(content) && content.indexOf('<link') > -1) {
          while (element.firstChild) {
            element.removeChild(element.firstChild);
          }
          var nodes = Element._getContentFromAnonymousElement(tagName, content.stripScripts(), true);
          nodes.each(function(node) { element.appendChild(node) });
        }
        else {
          element.innerHTML = content.stripScripts();
        }
      }
      else {
        element.innerHTML = content.stripScripts();
      }

      content.evalScripts.bind(content).defer();
      return element;
    }

    return update;
  })(),

  replace: function(element, content) {
    element = $(element);
    if (content && content.toElement) content = content.toElement();
    else if (!Object.isElement(content)) {
      content = Object.toHTML(content);
      var range = element.ownerDocument.createRange();
      range.selectNode(element);
      content.evalScripts.bind(content).defer();
      content = range.createContextualFragment(content.stripScripts());
    }
    element.parentNode.replaceChild(content, element);
    return element;
  },

  insert: function(element, insertions) {
    element = $(element);

    if (Object.isString(insertions) || Object.isNumber(insertions) ||
        Object.isElement(insertions) || (insertions && (insertions.toElement || insertions.toHTML)))
          insertions = {bottom:insertions};

    var content, insert, tagName, childNodes;

    for (var position in insertions) {
      content  = insertions[position];
      position = position.toLowerCase();
      insert = Element._insertionTranslations[position];

      if (content && content.toElement) content = content.toElement();
      if (Object.isElement(content)) {
        insert(element, content);
        continue;
      }

      content = Object.toHTML(content);

      tagName = ((position == 'before' || position == 'after')
        ? element.parentNode : element).tagName.toUpperCase();

      childNodes = Element._getContentFromAnonymousElement(tagName, content.stripScripts());

      if (position == 'top' || position == 'after') childNodes.reverse();
      childNodes.each(insert.curry(element));

      content.evalScripts.bind(content).defer();
    }

    return element;
  },

  wrap: function(element, wrapper, attributes) {
    element = $(element);
    if (Object.isElement(wrapper))
      $(wrapper).writeAttribute(attributes || { });
    else if (Object.isString(wrapper)) wrapper = new Element(wrapper, attributes);
    else wrapper = new Element('div', wrapper);
    if (element.parentNode)
      element.parentNode.replaceChild(wrapper, element);
    wrapper.appendChild(element);
    return wrapper;
  },

  inspect: function(element) {
    element = $(element);
    var result = '<' + element.tagName.toLowerCase();
    $H({'id': 'id', 'className': 'class'}).each(function(pair) {
      var property = pair.first(),
          attribute = pair.last(),
          value = (element[property] || '').toString();
      if (value) result += ' ' + attribute + '=' + value.inspect(true);
    });
    return result + '>';
  },

  recursivelyCollect: function(element, property, maximumLength) {
    element = $(element);
    maximumLength = maximumLength || -1;
    var elements = [];

    while (element = element[property]) {
      if (element.nodeType == 1)
        elements.push(Element.extend(element));
      if (elements.length == maximumLength)
        break;
    }

    return elements;
  },

  ancestors: function(element) {
    return Element.recursivelyCollect(element, 'parentNode');
  },

  descendants: function(element) {
    return Element.select(element, "*");
  },

  firstDescendant: function(element) {
    element = $(element).firstChild;
    while (element && element.nodeType != 1) element = element.nextSibling;
    return $(element);
  },

  immediateDescendants: function(element) {
    var results = [], child = $(element).firstChild;
    while (child) {
      if (child.nodeType === 1) {
        results.push(Element.extend(child));
      }
      child = child.nextSibling;
    }
    return results;
  },

  previousSiblings: function(element, maximumLength) {
    return Element.recursivelyCollect(element, 'previousSibling');
  },

  nextSiblings: function(element) {
    return Element.recursivelyCollect(element, 'nextSibling');
  },

  siblings: function(element) {
    element = $(element);
    return Element.previousSiblings(element).reverse()
      .concat(Element.nextSiblings(element));
  },

  match: function(element, selector) {
    element = $(element);
    if (Object.isString(selector))
      return Prototype.Selector.match(element, selector);
    return selector.match(element);
  },

  up: function(element, expression, index) {
    element = $(element);
    if (arguments.length == 1) return $(element.parentNode);
    var ancestors = Element.ancestors(element);
    return Object.isNumber(expression) ? ancestors[expression] :
      Prototype.Selector.find(ancestors, expression, index);
  },

  down: function(element, expression, index) {
    element = $(element);
    if (arguments.length == 1) return Element.firstDescendant(element);
    return Object.isNumber(expression) ? Element.descendants(element)[expression] :
      Element.select(element, expression)[index || 0];
  },

  previous: function(element, expression, index) {
    element = $(element);
    if (Object.isNumber(expression)) index = expression, expression = false;
    if (!Object.isNumber(index)) index = 0;

    if (expression) {
      return Prototype.Selector.find(element.previousSiblings(), expression, index);
    } else {
      return element.recursivelyCollect("previousSibling", index + 1)[index];
    }
  },

  next: function(element, expression, index) {
    element = $(element);
    if (Object.isNumber(expression)) index = expression, expression = false;
    if (!Object.isNumber(index)) index = 0;

    if (expression) {
      return Prototype.Selector.find(element.nextSiblings(), expression, index);
    } else {
      var maximumLength = Object.isNumber(index) ? index + 1 : 1;
      return element.recursivelyCollect("nextSibling", index + 1)[index];
    }
  },


  select: function(element) {
    element = $(element);
    var expressions = Array.prototype.slice.call(arguments, 1).join(', ');
    return Prototype.Selector.select(expressions, element);
  },

  adjacent: function(element) {
    element = $(element);
    var expressions = Array.prototype.slice.call(arguments, 1).join(', ');
    return Prototype.Selector.select(expressions, element.parentNode).without(element);
  },

  identify: function(element) {
    element = $(element);
    var id = Element.readAttribute(element, 'id');
    if (id) return id;
    do { id = 'anonymous_element_' + Element.idCounter++ } while ($(id));
    Element.writeAttribute(element, 'id', id);
    return id;
  },

  readAttribute: function(element, name) {
    element = $(element);
    if (Prototype.Browser.IE) {
      var t = Element._attributeTranslations.read;
      if (t.values[name]) return t.values[name](element, name);
      if (t.names[name]) name = t.names[name];
      if (name.include(':')) {
        return (!element.attributes || !element.attributes[name]) ? null :
         element.attributes[name].value;
      }
    }
    return element.getAttribute(name);
  },

  writeAttribute: function(element, name, value) {
    element = $(element);
    var attributes = { }, t = Element._attributeTranslations.write;

    if (typeof name == 'object') attributes = name;
    else attributes[name] = Object.isUndefined(value) ? true : value;

    for (var attr in attributes) {
      name = t.names[attr] || attr;
      value = attributes[attr];
      if (t.values[attr]) name = t.values[attr](element, value);
      if (value === false || value === null)
        element.removeAttribute(name);
      else if (value === true)
        element.setAttribute(name, name);
      else element.setAttribute(name, value);
    }
    return element;
  },

  getHeight: function(element) {
    return Element.getDimensions(element).height;
  },

  getWidth: function(element) {
    return Element.getDimensions(element).width;
  },

  classNames: function(element) {
    return new Element.ClassNames(element);
  },

  hasClassName: function(element, className) {
    if (!(element = $(element))) return;
    var elementClassName = element.className;
    return (elementClassName.length > 0 && (elementClassName == className ||
      new RegExp("(^|\\s)" + className + "(\\s|$)").test(elementClassName)));
  },

  addClassName: function(element, className) {
    if (!(element = $(element))) return;
    if (!Element.hasClassName(element, className))
      element.className += (element.className ? ' ' : '') + className;
    return element;
  },

  removeClassName: function(element, className) {
    if (!(element = $(element))) return;
    element.className = element.className.replace(
      new RegExp("(^|\\s+)" + className + "(\\s+|$)"), ' ').strip();
    return element;
  },

  toggleClassName: function(element, className) {
    if (!(element = $(element))) return;
    return Element[Element.hasClassName(element, className) ?
      'removeClassName' : 'addClassName'](element, className);
  },

  cleanWhitespace: function(element) {
    element = $(element);
    var node = element.firstChild;
    while (node) {
      var nextNode = node.nextSibling;
      if (node.nodeType == 3 && !/\S/.test(node.nodeValue))
        element.removeChild(node);
      node = nextNode;
    }
    return element;
  },

  empty: function(element) {
    return $(element).innerHTML.blank();
  },

  descendantOf: function(element, ancestor) {
    element = $(element), ancestor = $(ancestor);

    if (element.compareDocumentPosition)
      return (element.compareDocumentPosition(ancestor) & 8) === 8;

    if (ancestor.contains)
      return ancestor.contains(element) && ancestor !== element;

    while (element = element.parentNode)
      if (element == ancestor) return true;

    return false;
  },

  scrollTo: function(element) {
    element = $(element);
    var pos = Element.cumulativeOffset(element);
    window.scrollTo(pos[0], pos[1]);
    return element;
  },

  getStyle: function(element, style) {
    element = $(element);
    style = style == 'float' ? 'cssFloat' : style.camelize();
    var value = element.style[style];
    if (!value || value == 'auto') {
      var css = document.defaultView.getComputedStyle(element, null);
      value = css ? css[style] : null;
    }
    if (style == 'opacity') return value ? parseFloat(value) : 1.0;
    return value == 'auto' ? null : value;
  },

  getOpacity: function(element) {
    return $(element).getStyle('opacity');
  },

  setStyle: function(element, styles) {
    element = $(element);
    var elementStyle = element.style, match;
    if (Object.isString(styles)) {
      element.style.cssText += ';' + styles;
      return styles.include('opacity') ?
        element.setOpacity(styles.match(/opacity:\s*(\d?\.?\d*)/)[1]) : element;
    }
    for (var property in styles)
      if (property == 'opacity') element.setOpacity(styles[property]);
      else
        elementStyle[(property == 'float' || property == 'cssFloat') ?
          (Object.isUndefined(elementStyle.styleFloat) ? 'cssFloat' : 'styleFloat') :
            property] = styles[property];

    return element;
  },

  setOpacity: function(element, value) {
    element = $(element);
    element.style.opacity = (value == 1 || value === '') ? '' :
      (value < 0.00001) ? 0 : value;
    return element;
  },

  makePositioned: function(element) {
    element = $(element);
    var pos = Element.getStyle(element, 'position');
    if (pos == 'static' || !pos) {
      element._madePositioned = true;
      element.style.position = 'relative';
      if (Prototype.Browser.Opera) {
        element.style.top = 0;
        element.style.left = 0;
      }
    }
    return element;
  },

  undoPositioned: function(element) {
    element = $(element);
    if (element._madePositioned) {
      element._madePositioned = undefined;
      element.style.position =
        element.style.top =
        element.style.left =
        element.style.bottom =
        element.style.right = '';
    }
    return element;
  },

  makeClipping: function(element) {
    element = $(element);
    if (element._overflow) return element;
    element._overflow = Element.getStyle(element, 'overflow') || 'auto';
    if (element._overflow !== 'hidden')
      element.style.overflow = 'hidden';
    return element;
  },

  undoClipping: function(element) {
    element = $(element);
    if (!element._overflow) return element;
    element.style.overflow = element._overflow == 'auto' ? '' : element._overflow;
    element._overflow = null;
    return element;
  },

  clonePosition: function(element, source) {
    var options = Object.extend({
      setLeft:    true,
      setTop:     true,
      setWidth:   true,
      setHeight:  true,
      offsetTop:  0,
      offsetLeft: 0
    }, arguments[2] || { });

    source = $(source);
    var p = Element.viewportOffset(source), delta = [0, 0], parent = null;

    element = $(element);

    if (Element.getStyle(element, 'position') == 'absolute') {
      parent = Element.getOffsetParent(element);
      delta = Element.viewportOffset(parent);
    }

    if (parent == document.body) {
      var parentOffset = Element.viewportOffset(parent);
      delta[0] -= parentOffset[0];
      delta[1] -= parentOffset[1];
    }

    if (options.setLeft)   element.style.left  = (p[0] - delta[0] + options.offsetLeft) + 'px';
    if (options.setTop)    element.style.top   = (p[1] - delta[1] + options.offsetTop) + 'px';
    if (options.setWidth)  element.style.width = source.offsetWidth + 'px';
    if (options.setHeight) element.style.height = source.offsetHeight + 'px';
    return element;
  }
};

Object.extend(Element.Methods, {
  getElementsBySelector: Element.Methods.select,

  childElements: Element.Methods.immediateDescendants
});

Element._attributeTranslations = {
  write: {
    names: {
      className: 'class',
      htmlFor:   'for'
    },
    values: { }
  }
};

if (Prototype.Browser.Opera) {
  Element.Methods.getStyle = Element.Methods.getStyle.wrap(
    function(proceed, element, style) {
      switch (style) {
        case 'height': case 'width':
          if (!Element.visible(element)) return null;

          var dim = parseInt(proceed(element, style), 10);

          if (dim !== element['offset' + style.capitalize()])
            return dim + 'px';

          var properties;
          if (style === 'height') {
            properties = ['border-top-width', 'padding-top',
             'padding-bottom', 'border-bottom-width'];
          }
          else {
            properties = ['border-left-width', 'padding-left',
             'padding-right', 'border-right-width'];
          }
          return properties.inject(dim, function(memo, property) {
            var val = proceed(element, property);
            return val === null ? memo : memo - parseInt(val, 10);
          }) + 'px';
        default: return proceed(element, style);
      }
    }
  );

  Element.Methods.readAttribute = Element.Methods.readAttribute.wrap(
    function(proceed, element, attribute) {
      if (attribute === 'title') return element.title;
      return proceed(element, attribute);
    }
  );
}

else if (Prototype.Browser.IE) {
  Element.Methods.getStyle = function(element, style) {
    element = $(element);
    style = (style == 'float' || style == 'cssFloat') ? 'styleFloat' : style.camelize();
    var value = element.style[style];
    if (!value && element.currentStyle) value = element.currentStyle[style];

    if (style == 'opacity') {
      if (value = (element.getStyle('filter') || '').match(/alpha\(opacity=(.*)\)/))
        if (value[1]) return parseFloat(value[1]) / 100;
      return 1.0;
    }

    if (value == 'auto') {
      if ((style == 'width' || style == 'height') && (element.getStyle('display') != 'none'))
        return element['offset' + style.capitalize()] + 'px';
      return null;
    }
    return value;
  };

  Element.Methods.setOpacity = function(element, value) {
    function stripAlpha(filter){
      return filter.replace(/alpha\([^\)]*\)/gi,'');
    }
    element = $(element);
    var currentStyle = element.currentStyle;
    if ((currentStyle && !currentStyle.hasLayout) ||
      (!currentStyle && element.style.zoom == 'normal'))
        element.style.zoom = 1;

    var filter = element.getStyle('filter'), style = element.style;
    if (value == 1 || value === '') {
      (filter = stripAlpha(filter)) ?
        style.filter = filter : style.removeAttribute('filter');
      return element;
    } else if (value < 0.00001) value = 0;
    style.filter = stripAlpha(filter) +
      'alpha(opacity=' + (value * 100) + ')';
    return element;
  };

  Element._attributeTranslations = (function(){

    var classProp = 'className',
        forProp = 'for',
        el = document.createElement('div');

    el.setAttribute(classProp, 'x');

    if (el.className !== 'x') {
      el.setAttribute('class', 'x');
      if (el.className === 'x') {
        classProp = 'class';
      }
    }
    el = null;

    el = document.createElement('label');
    el.setAttribute(forProp, 'x');
    if (el.htmlFor !== 'x') {
      el.setAttribute('htmlFor', 'x');
      if (el.htmlFor === 'x') {
        forProp = 'htmlFor';
      }
    }
    el = null;

    return {
      read: {
        names: {
          'class':      classProp,
          'className':  classProp,
          'for':        forProp,
          'htmlFor':    forProp
        },
        values: {
          _getAttr: function(element, attribute) {
            return element.getAttribute(attribute);
          },
          _getAttr2: function(element, attribute) {
            return element.getAttribute(attribute, 2);
          },
          _getAttrNode: function(element, attribute) {
            var node = element.getAttributeNode(attribute);
            return node ? node.value : "";
          },
          _getEv: (function(){

            var el = document.createElement('div'), f;
            el.onclick = Prototype.emptyFunction;
            var value = el.getAttribute('onclick');

            if (String(value).indexOf('{') > -1) {
              f = function(element, attribute) {
                attribute = element.getAttribute(attribute);
                if (!attribute) return null;
                attribute = attribute.toString();
                attribute = attribute.split('{')[1];
                attribute = attribute.split('}')[0];
                return attribute.strip();
              };
            }
            else if (value === '') {
              f = function(element, attribute) {
                attribute = element.getAttribute(attribute);
                if (!attribute) return null;
                return attribute.strip();
              };
            }
            el = null;
            return f;
          })(),
          _flag: function(element, attribute) {
            return $(element).hasAttribute(attribute) ? attribute : null;
          },
          style: function(element) {
            return element.style.cssText.toLowerCase();
          },
          title: function(element) {
            return element.title;
          }
        }
      }
    }
  })();

  Element._attributeTranslations.write = {
    names: Object.extend({
      cellpadding: 'cellPadding',
      cellspacing: 'cellSpacing'
    }, Element._attributeTranslations.read.names),
    values: {
      checked: function(element, value) {
        element.checked = !!value;
      },

      style: function(element, value) {
        element.style.cssText = value ? value : '';
      }
    }
  };

  Element._attributeTranslations.has = {};

  $w('colSpan rowSpan vAlign dateTime accessKey tabIndex ' +
      'encType maxLength readOnly longDesc frameBorder').each(function(attr) {
    Element._attributeTranslations.write.names[attr.toLowerCase()] = attr;
    Element._attributeTranslations.has[attr.toLowerCase()] = attr;
  });

  (function(v) {
    Object.extend(v, {
      href:        v._getAttr2,
      src:         v._getAttr2,
      type:        v._getAttr,
      action:      v._getAttrNode,
      disabled:    v._flag,
      checked:     v._flag,
      readonly:    v._flag,
      multiple:    v._flag,
      onload:      v._getEv,
      onunload:    v._getEv,
      onclick:     v._getEv,
      ondblclick:  v._getEv,
      onmousedown: v._getEv,
      onmouseup:   v._getEv,
      onmouseover: v._getEv,
      onmousemove: v._getEv,
      onmouseout:  v._getEv,
      onfocus:     v._getEv,
      onblur:      v._getEv,
      onkeypress:  v._getEv,
      onkeydown:   v._getEv,
      onkeyup:     v._getEv,
      onsubmit:    v._getEv,
      onreset:     v._getEv,
      onselect:    v._getEv,
      onchange:    v._getEv
    });
  })(Element._attributeTranslations.read.values);

  if (Prototype.BrowserFeatures.ElementExtensions) {
    (function() {
      function _descendants(element) {
        var nodes = element.getElementsByTagName('*'), results = [];
        for (var i = 0, node; node = nodes[i]; i++)
          if (node.tagName !== "!") // Filter out comment nodes.
            results.push(node);
        return results;
      }

      Element.Methods.down = function(element, expression, index) {
        element = $(element);
        if (arguments.length == 1) return element.firstDescendant();
        return Object.isNumber(expression) ? _descendants(element)[expression] :
          Element.select(element, expression)[index || 0];
      }
    })();
  }

}

else if (Prototype.Browser.Gecko && /rv:1\.8\.0/.test(navigator.userAgent)) {
  Element.Methods.setOpacity = function(element, value) {
    element = $(element);
    element.style.opacity = (value == 1) ? 0.999999 :
      (value === '') ? '' : (value < 0.00001) ? 0 : value;
    return element;
  };
}

else if (Prototype.Browser.WebKit) {
  Element.Methods.setOpacity = function(element, value) {
    element = $(element);
    element.style.opacity = (value == 1 || value === '') ? '' :
      (value < 0.00001) ? 0 : value;

    if (value == 1)
      if (element.tagName.toUpperCase() == 'IMG' && element.width) {
        element.width++; element.width--;
      } else try {
        var n = document.createTextNode(' ');
        element.appendChild(n);
        element.removeChild(n);
      } catch (e) { }

    return element;
  };
}

if ('outerHTML' in document.documentElement) {
  Element.Methods.replace = function(element, content) {
    element = $(element);

    if (content && content.toElement) content = content.toElement();
    if (Object.isElement(content)) {
      element.parentNode.replaceChild(content, element);
      return element;
    }

    content = Object.toHTML(content);
    var parent = element.parentNode, tagName = parent.tagName.toUpperCase();

    if (Element._insertionTranslations.tags[tagName]) {
      var nextSibling = element.next(),
          fragments = Element._getContentFromAnonymousElement(tagName, content.stripScripts());
      parent.removeChild(element);
      if (nextSibling)
        fragments.each(function(node) { parent.insertBefore(node, nextSibling) });
      else
        fragments.each(function(node) { parent.appendChild(node) });
    }
    else element.outerHTML = content.stripScripts();

    content.evalScripts.bind(content).defer();
    return element;
  };
}

Element._returnOffset = function(l, t) {
  var result = [l, t];
  result.left = l;
  result.top = t;
  return result;
};

Element._getContentFromAnonymousElement = function(tagName, html, force) {
  var div = new Element('div'),
      t = Element._insertionTranslations.tags[tagName];

  var workaround = false;
  if (t) workaround = true;
  else if (force) {
    workaround = true;
    t = ['', '', 0];
  }

  if (workaround) {
    div.innerHTML = '&nbsp;' + t[0] + html + t[1];
    div.removeChild(div.firstChild);
    for (var i = t[2]; i--; ) {
      div = div.firstChild;
    }
  }
  else {
    div.innerHTML = html;
  }
  return $A(div.childNodes);
};

Element._insertionTranslations = {
  before: function(element, node) {
    element.parentNode.insertBefore(node, element);
  },
  top: function(element, node) {
    element.insertBefore(node, element.firstChild);
  },
  bottom: function(element, node) {
    element.appendChild(node);
  },
  after: function(element, node) {
    element.parentNode.insertBefore(node, element.nextSibling);
  },
  tags: {
    TABLE:  ['<table>',                '</table>',                   1],
    TBODY:  ['<table><tbody>',         '</tbody></table>',           2],
    TR:     ['<table><tbody><tr>',     '</tr></tbody></table>',      3],
    TD:     ['<table><tbody><tr><td>', '</td></tr></tbody></table>', 4],
    SELECT: ['<select>',               '</select>',                  1]
  }
};

(function() {
  var tags = Element._insertionTranslations.tags;
  Object.extend(tags, {
    THEAD: tags.TBODY,
    TFOOT: tags.TBODY,
    TH:    tags.TD
  });
})();

Element.Methods.Simulated = {
  hasAttribute: function(element, attribute) {
    attribute = Element._attributeTranslations.has[attribute] || attribute;
    var node = $(element).getAttributeNode(attribute);
    return !!(node && node.specified);
  }
};

Element.Methods.ByTag = { };

Object.extend(Element, Element.Methods);

(function(div) {

  if (!Prototype.BrowserFeatures.ElementExtensions && div['__proto__']) {
    window.HTMLElement = { };
    window.HTMLElement.prototype = div['__proto__'];
    Prototype.BrowserFeatures.ElementExtensions = true;
  }

  div = null;

})(document.createElement('div'));

Element.extend = (function() {

  function checkDeficiency(tagName) {
    if (typeof window.Element != 'undefined') {
      var proto = window.Element.prototype;
      if (proto) {
        var id = '_' + (Math.random()+'').slice(2),
            el = document.createElement(tagName);
        proto[id] = 'x';
        var isBuggy = (el[id] !== 'x');
        delete proto[id];
        el = null;
        return isBuggy;
      }
    }
    return false;
  }

  function extendElementWith(element, methods) {
    for (var property in methods) {
      var value = methods[property];
      if (Object.isFunction(value) && !(property in element))
        element[property] = value.methodize();
    }
  }

  var HTMLOBJECTELEMENT_PROTOTYPE_BUGGY = checkDeficiency('object');

  if (Prototype.BrowserFeatures.SpecificElementExtensions) {
    if (HTMLOBJECTELEMENT_PROTOTYPE_BUGGY) {
      return function(element) {
        if (element && typeof element._extendedByPrototype == 'undefined') {
          var t = element.tagName;
          if (t && (/^(?:object|applet|embed)$/i.test(t))) {
            extendElementWith(element, Element.Methods);
            extendElementWith(element, Element.Methods.Simulated);
            extendElementWith(element, Element.Methods.ByTag[t.toUpperCase()]);
          }
        }
        return element;
      }
    }
    return Prototype.K;
  }

  var Methods = { }, ByTag = Element.Methods.ByTag;

  var extend = Object.extend(function(element) {
    if (!element || typeof element._extendedByPrototype != 'undefined' ||
        element.nodeType != 1 || element == window) return element;

    var methods = Object.clone(Methods),
        tagName = element.tagName.toUpperCase();

    if (ByTag[tagName]) Object.extend(methods, ByTag[tagName]);

    extendElementWith(element, methods);

    element._extendedByPrototype = Prototype.emptyFunction;
    return element;

  }, {
    refresh: function() {
      if (!Prototype.BrowserFeatures.ElementExtensions) {
        Object.extend(Methods, Element.Methods);
        Object.extend(Methods, Element.Methods.Simulated);
      }
    }
  });

  extend.refresh();
  return extend;
})();

if (document.documentElement.hasAttribute) {
  Element.hasAttribute = function(element, attribute) {
    return element.hasAttribute(attribute);
  };
}
else {
  Element.hasAttribute = Element.Methods.Simulated.hasAttribute;
}

Element.addMethods = function(methods) {
  var F = Prototype.BrowserFeatures, T = Element.Methods.ByTag;

  if (!methods) {
    Object.extend(Form, Form.Methods);
    Object.extend(Form.Element, Form.Element.Methods);
    Object.extend(Element.Methods.ByTag, {
      "FORM":     Object.clone(Form.Methods),
      "INPUT":    Object.clone(Form.Element.Methods),
      "SELECT":   Object.clone(Form.Element.Methods),
      "TEXTAREA": Object.clone(Form.Element.Methods),
      "BUTTON":   Object.clone(Form.Element.Methods)
    });
  }

  if (arguments.length == 2) {
    var tagName = methods;
    methods = arguments[1];
  }

  if (!tagName) Object.extend(Element.Methods, methods || { });
  else {
    if (Object.isArray(tagName)) tagName.each(extend);
    else extend(tagName);
  }

  function extend(tagName) {
    tagName = tagName.toUpperCase();
    if (!Element.Methods.ByTag[tagName])
      Element.Methods.ByTag[tagName] = { };
    Object.extend(Element.Methods.ByTag[tagName], methods);
  }

  function copy(methods, destination, onlyIfAbsent) {
    onlyIfAbsent = onlyIfAbsent || false;
    for (var property in methods) {
      var value = methods[property];
      if (!Object.isFunction(value)) continue;
      if (!onlyIfAbsent || !(property in destination))
        destination[property] = value.methodize();
    }
  }

  function findDOMClass(tagName) {
    var klass;
    var trans = {
      "OPTGROUP": "OptGroup", "TEXTAREA": "TextArea", "P": "Paragraph",
      "FIELDSET": "FieldSet", "UL": "UList", "OL": "OList", "DL": "DList",
      "DIR": "Directory", "H1": "Heading", "H2": "Heading", "H3": "Heading",
      "H4": "Heading", "H5": "Heading", "H6": "Heading", "Q": "Quote",
      "INS": "Mod", "DEL": "Mod", "A": "Anchor", "IMG": "Image", "CAPTION":
      "TableCaption", "COL": "TableCol", "COLGROUP": "TableCol", "THEAD":
      "TableSection", "TFOOT": "TableSection", "TBODY": "TableSection", "TR":
      "TableRow", "TH": "TableCell", "TD": "TableCell", "FRAMESET":
      "FrameSet", "IFRAME": "IFrame"
    };
    if (trans[tagName]) klass = 'HTML' + trans[tagName] + 'Element';
    if (window[klass]) return window[klass];
    klass = 'HTML' + tagName + 'Element';
    if (window[klass]) return window[klass];
    klass = 'HTML' + tagName.capitalize() + 'Element';
    if (window[klass]) return window[klass];

    var element = document.createElement(tagName),
        proto = element['__proto__'] || element.constructor.prototype;

    element = null;
    return proto;
  }

  var elementPrototype = window.HTMLElement ? HTMLElement.prototype :
   Element.prototype;

  if (F.ElementExtensions) {
    copy(Element.Methods, elementPrototype);
    copy(Element.Methods.Simulated, elementPrototype, true);
  }

  if (F.SpecificElementExtensions) {
    for (var tag in Element.Methods.ByTag) {
      var klass = findDOMClass(tag);
      if (Object.isUndefined(klass)) continue;
      copy(T[tag], klass.prototype);
    }
  }

  Object.extend(Element, Element.Methods);
  delete Element.ByTag;

  if (Element.extend.refresh) Element.extend.refresh();
  Element.cache = { };
};


document.viewport = {

  getDimensions: function() {
    return { width: this.getWidth(), height: this.getHeight() };
  },

  getScrollOffsets: function() {
    return Element._returnOffset(
      window.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft,
      window.pageYOffset || document.documentElement.scrollTop  || document.body.scrollTop);
  }
};

(function(viewport) {
  var B = Prototype.Browser, doc = document, element, property = {};

  function getRootElement() {
    if (B.WebKit && !doc.evaluate)
      return document;

    if (B.Opera && window.parseFloat(window.opera.version()) < 9.5)
      return document.body;

    return document.documentElement;
  }

  function define(D) {
    if (!element) element = getRootElement();

    property[D] = 'client' + D;

    viewport['get' + D] = function() { return element[property[D]] };
    return viewport['get' + D]();
  }

  viewport.getWidth  = define.curry('Width');

  viewport.getHeight = define.curry('Height');
})(document.viewport);


Element.Storage = {
  UID: 1
};

Element.addMethods({
  getStorage: function(element) {
    if (!(element = $(element))) return;

    var uid;
    if (element === window) {
      uid = 0;
    } else {
      if (typeof element._prototypeUID === "undefined")
        element._prototypeUID = Element.Storage.UID++;
      uid = element._prototypeUID;
    }

    if (!Element.Storage[uid])
      Element.Storage[uid] = $H();

    return Element.Storage[uid];
  },

  store: function(element, key, value) {
    if (!(element = $(element))) return;

    if (arguments.length === 2) {
      Element.getStorage(element).update(key);
    } else {
      Element.getStorage(element).set(key, value);
    }

    return element;
  },

  retrieve: function(element, key, defaultValue) {
    if (!(element = $(element))) return;
    var hash = Element.getStorage(element), value = hash.get(key);

    if (Object.isUndefined(value)) {
      hash.set(key, defaultValue);
      value = defaultValue;
    }

    return value;
  },

  clone: function(element, deep) {
    if (!(element = $(element))) return;
    var clone = element.cloneNode(deep);
    clone._prototypeUID = void 0;
    if (deep) {
      var descendants = Element.select(clone, '*'),
          i = descendants.length;
      while (i--) {
        descendants[i]._prototypeUID = void 0;
      }
    }
    return Element.extend(clone);
  },

  purge: function(element) {
    if (!(element = $(element))) return;
    var purgeElement = Element._purgeElement;

    purgeElement(element);

    var descendants = element.getElementsByTagName('*'),
     i = descendants.length;

    while (i--) purgeElement(descendants[i]);

    return null;
  }
});

(function() {

  function toDecimal(pctString) {
    var match = pctString.match(/^(\d+)%?$/i);
    if (!match) return null;
    return (Number(match[1]) / 100);
  }

  function getPixelValue(value, property, context) {
    var element = null;
    if (Object.isElement(value)) {
      element = value;
      value = element.getStyle(property);
    }

    if (value === null) {
      return null;
    }

    if ((/^(?:-)?\d+(\.\d+)?(px)?$/i).test(value)) {
      return window.parseFloat(value);
    }

    var isPercentage = value.include('%'), isViewport = (context === document.viewport);

    if (/\d/.test(value) && element && element.runtimeStyle && !(isPercentage && isViewport)) {
      var style = element.style.left, rStyle = element.runtimeStyle.left;
      element.runtimeStyle.left = element.currentStyle.left;
      element.style.left = value || 0;
      value = element.style.pixelLeft;
      element.style.left = style;
      element.runtimeStyle.left = rStyle;

      return value;
    }

    if (element && isPercentage) {
      context = context || element.parentNode;
      var decimal = toDecimal(value);
      var whole = null;
      var position = element.getStyle('position');

      var isHorizontal = property.include('left') || property.include('right') ||
       property.include('width');

      var isVertical =  property.include('top') || property.include('bottom') ||
        property.include('height');

      if (context === document.viewport) {
        if (isHorizontal) {
          whole = document.viewport.getWidth();
        } else if (isVertical) {
          whole = document.viewport.getHeight();
        }
      } else {
        if (isHorizontal) {
          whole = $(context).measure('width');
        } else if (isVertical) {
          whole = $(context).measure('height');
        }
      }

      return (whole === null) ? 0 : whole * decimal;
    }

    return 0;
  }

  function toCSSPixels(number) {
    if (Object.isString(number) && number.endsWith('px')) {
      return number;
    }
    return number + 'px';
  }

  function isDisplayed(element) {
    var originalElement = element;
    while (element && element.parentNode) {
      var display = element.getStyle('display');
      if (display === 'none') {
        return false;
      }
      element = $(element.parentNode);
    }
    return true;
  }

  var hasLayout = Prototype.K;
  if ('currentStyle' in document.documentElement) {
    hasLayout = function(element) {
      if (!element.currentStyle.hasLayout) {
        element.style.zoom = 1;
      }
      return element;
    };
  }

  function cssNameFor(key) {
    if (key.include('border')) key = key + '-width';
    return key.camelize();
  }

  Element.Layout = Class.create(Hash, {
    initialize: function($super, element, preCompute) {
      $super();
      this.element = $(element);

      Element.Layout.PROPERTIES.each( function(property) {
        this._set(property, null);
      }, this);

      if (preCompute) {
        this._preComputing = true;
        this._begin();
        Element.Layout.PROPERTIES.each( this._compute, this );
        this._end();
        this._preComputing = false;
      }
    },

    _set: function(property, value) {
      return Hash.prototype.set.call(this, property, value);
    },

    set: function(property, value) {
      throw "Properties of Element.Layout are read-only.";
    },

    get: function($super, property) {
      var value = $super(property);
      return value === null ? this._compute(property) : value;
    },

    _begin: function() {
      if (this._prepared) return;

      var element = this.element;
      if (isDisplayed(element)) {
        this._prepared = true;
        return;
      }

      var originalStyles = {
        position:   element.style.position   || '',
        width:      element.style.width      || '',
        visibility: element.style.visibility || '',
        display:    element.style.display    || ''
      };

      element.store('prototype_original_styles', originalStyles);

      var position = element.getStyle('position'),
       width = element.getStyle('width');

      if (width === "0px" || width === null) {
        element.style.display = 'block';
        width = element.getStyle('width');
      }

      var context = (position === 'fixed') ? document.viewport :
       element.parentNode;

      element.setStyle({
        position:   'absolute',
        visibility: 'hidden',
        display:    'block'
      });

      var positionedWidth = element.getStyle('width');

      var newWidth;
      if (width && (positionedWidth === width)) {
        newWidth = getPixelValue(element, 'width', context);
      } else if (position === 'absolute' || position === 'fixed') {
        newWidth = getPixelValue(element, 'width', context);
      } else {
        var parent = element.parentNode, pLayout = $(parent).getLayout();

        newWidth = pLayout.get('width') -
         this.get('margin-left') -
         this.get('border-left') -
         this.get('padding-left') -
         this.get('padding-right') -
         this.get('border-right') -
         this.get('margin-right');
      }

      element.setStyle({ width: newWidth + 'px' });

      this._prepared = true;
    },

    _end: function() {
      var element = this.element;
      var originalStyles = element.retrieve('prototype_original_styles');
      element.store('prototype_original_styles', null);
      element.setStyle(originalStyles);
      this._prepared = false;
    },

    _compute: function(property) {
      var COMPUTATIONS = Element.Layout.COMPUTATIONS;
      if (!(property in COMPUTATIONS)) {
        throw "Property not found.";
      }

      return this._set(property, COMPUTATIONS[property].call(this, this.element));
    },

    toObject: function() {
      var args = $A(arguments);
      var keys = (args.length === 0) ? Element.Layout.PROPERTIES :
       args.join(' ').split(' ');
      var obj = {};
      keys.each( function(key) {
        if (!Element.Layout.PROPERTIES.include(key)) return;
        var value = this.get(key);
        if (value != null) obj[key] = value;
      }, this);
      return obj;
    },

    toHash: function() {
      var obj = this.toObject.apply(this, arguments);
      return new Hash(obj);
    },

    toCSS: function() {
      var args = $A(arguments);
      var keys = (args.length === 0) ? Element.Layout.PROPERTIES :
       args.join(' ').split(' ');
      var css = {};

      keys.each( function(key) {
        if (!Element.Layout.PROPERTIES.include(key)) return;
        if (Element.Layout.COMPOSITE_PROPERTIES.include(key)) return;

        var value = this.get(key);
        if (value != null) css[cssNameFor(key)] = value + 'px';
      }, this);
      return css;
    },

    inspect: function() {
      return "#<Element.Layout>";
    }
  });

  Object.extend(Element.Layout, {
    PROPERTIES: $w('height width top left right bottom border-left border-right border-top border-bottom padding-left padding-right padding-top padding-bottom margin-top margin-bottom margin-left margin-right padding-box-width padding-box-height border-box-width border-box-height margin-box-width margin-box-height'),

    COMPOSITE_PROPERTIES: $w('padding-box-width padding-box-height margin-box-width margin-box-height border-box-width border-box-height'),

    COMPUTATIONS: {
      'height': function(element) {
        if (!this._preComputing) this._begin();

        var bHeight = this.get('border-box-height');
        if (bHeight <= 0) {
          if (!this._preComputing) this._end();
          return 0;
        }

        var bTop = this.get('border-top'),
         bBottom = this.get('border-bottom');

        var pTop = this.get('padding-top'),
         pBottom = this.get('padding-bottom');

        if (!this._preComputing) this._end();

        return bHeight - bTop - bBottom - pTop - pBottom;
      },

      'width': function(element) {
        if (!this._preComputing) this._begin();

        var bWidth = this.get('border-box-width');
        if (bWidth <= 0) {
          if (!this._preComputing) this._end();
          return 0;
        }

        var bLeft = this.get('border-left'),
         bRight = this.get('border-right');

        var pLeft = this.get('padding-left'),
         pRight = this.get('padding-right');

        if (!this._preComputing) this._end();

        return bWidth - bLeft - bRight - pLeft - pRight;
      },

      'padding-box-height': function(element) {
        var height = this.get('height'),
         pTop = this.get('padding-top'),
         pBottom = this.get('padding-bottom');

        return height + pTop + pBottom;
      },

      'padding-box-width': function(element) {
        var width = this.get('width'),
         pLeft = this.get('padding-left'),
         pRight = this.get('padding-right');

        return width + pLeft + pRight;
      },

      'border-box-height': function(element) {
        if (!this._preComputing) this._begin();
        var height = element.offsetHeight;
        if (!this._preComputing) this._end();
        return height;
      },

      'border-box-width': function(element) {
        if (!this._preComputing) this._begin();
        var width = element.offsetWidth;
        if (!this._preComputing) this._end();
        return width;
      },

      'margin-box-height': function(element) {
        var bHeight = this.get('border-box-height'),
         mTop = this.get('margin-top'),
         mBottom = this.get('margin-bottom');

        if (bHeight <= 0) return 0;

        return bHeight + mTop + mBottom;
      },

      'margin-box-width': function(element) {
        var bWidth = this.get('border-box-width'),
         mLeft = this.get('margin-left'),
         mRight = this.get('margin-right');

        if (bWidth <= 0) return 0;

        return bWidth + mLeft + mRight;
      },

      'top': function(element) {
        var offset = element.positionedOffset();
        return offset.top;
      },

      'bottom': function(element) {
        var offset = element.positionedOffset(),
         parent = element.getOffsetParent(),
         pHeight = parent.measure('height');

        var mHeight = this.get('border-box-height');

        return pHeight - mHeight - offset.top;
      },

      'left': function(element) {
        var offset = element.positionedOffset();
        return offset.left;
      },

      'right': function(element) {
        var offset = element.positionedOffset(),
         parent = element.getOffsetParent(),
         pWidth = parent.measure('width');

        var mWidth = this.get('border-box-width');

        return pWidth - mWidth - offset.left;
      },

      'padding-top': function(element) {
        return getPixelValue(element, 'paddingTop');
      },

      'padding-bottom': function(element) {
        return getPixelValue(element, 'paddingBottom');
      },

      'padding-left': function(element) {
        return getPixelValue(element, 'paddingLeft');
      },

      'padding-right': function(element) {
        return getPixelValue(element, 'paddingRight');
      },

      'border-top': function(element) {
        return getPixelValue(element, 'borderTopWidth');
      },

      'border-bottom': function(element) {
        return getPixelValue(element, 'borderBottomWidth');
      },

      'border-left': function(element) {
        return getPixelValue(element, 'borderLeftWidth');
      },

      'border-right': function(element) {
        return getPixelValue(element, 'borderRightWidth');
      },

      'margin-top': function(element) {
        return getPixelValue(element, 'marginTop');
      },

      'margin-bottom': function(element) {
        return getPixelValue(element, 'marginBottom');
      },

      'margin-left': function(element) {
        return getPixelValue(element, 'marginLeft');
      },

      'margin-right': function(element) {
        return getPixelValue(element, 'marginRight');
      }
    }
  });

  if ('getBoundingClientRect' in document.documentElement) {
    Object.extend(Element.Layout.COMPUTATIONS, {
      'right': function(element) {
        var parent = hasLayout(element.getOffsetParent());
        var rect = element.getBoundingClientRect(),
         pRect = parent.getBoundingClientRect();

        return (pRect.right - rect.right).round();
      },

      'bottom': function(element) {
        var parent = hasLayout(element.getOffsetParent());
        var rect = element.getBoundingClientRect(),
         pRect = parent.getBoundingClientRect();

        return (pRect.bottom - rect.bottom).round();
      }
    });
  }

  Element.Offset = Class.create({
    initialize: function(left, top) {
      this.left = left.round();
      this.top  = top.round();

      this[0] = this.left;
      this[1] = this.top;
    },

    relativeTo: function(offset) {
      return new Element.Offset(
        this.left - offset.left,
        this.top  - offset.top
      );
    },

    inspect: function() {
      return "#<Element.Offset left: #{left} top: #{top}>".interpolate(this);
    },

    toString: function() {
      return "[#{left}, #{top}]".interpolate(this);
    },

    toArray: function() {
      return [this.left, this.top];
    }
  });

  function getLayout(element, preCompute) {
    return new Element.Layout(element, preCompute);
  }

  function measure(element, property) {
    return $(element).getLayout().get(property);
  }

  function getDimensions(element) {
    element = $(element);
    var display = Element.getStyle(element, 'display');

    if (display && display !== 'none') {
      return { width: element.offsetWidth, height: element.offsetHeight };
    }

    var style = element.style;
    var originalStyles = {
      visibility: style.visibility,
      position:   style.position,
      display:    style.display
    };

    var newStyles = {
      visibility: 'hidden',
      display:    'block'
    };

    if (originalStyles.position !== 'fixed')
      newStyles.position = 'absolute';

    Element.setStyle(element, newStyles);

    var dimensions = {
      width:  element.offsetWidth,
      height: element.offsetHeight
    };

    Element.setStyle(element, originalStyles);

    return dimensions;
  }

  function getOffsetParent(element) {
    element = $(element);

    if (isDocument(element) || isDetached(element) || isBody(element) || isHtml(element))
      return $(document.body);

    var isInline = (Element.getStyle(element, 'display') === 'inline');
    if (!isInline && element.offsetParent) return $(element.offsetParent);

    while ((element = element.parentNode) && element !== document.body) {
      if (Element.getStyle(element, 'position') !== 'static') {
        return isHtml(element) ? $(document.body) : $(element);
      }
    }

    return $(document.body);
  }


  function cumulativeOffset(element) {
    element = $(element);
    var valueT = 0, valueL = 0;
    if (element.parentNode) {
      do {
        valueT += element.offsetTop  || 0;
        valueL += element.offsetLeft || 0;
        element = element.offsetParent;
      } while (element);
    }
    return new Element.Offset(valueL, valueT);
  }

  function positionedOffset(element) {
    element = $(element);

    var layout = element.getLayout();

    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      element = element.offsetParent;
      if (element) {
        if (isBody(element)) break;
        var p = Element.getStyle(element, 'position');
        if (p !== 'static') break;
      }
    } while (element);

    valueL -= layout.get('margin-top');
    valueT -= layout.get('margin-left');

    return new Element.Offset(valueL, valueT);
  }

  function cumulativeScrollOffset(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.scrollTop  || 0;
      valueL += element.scrollLeft || 0;
      element = element.parentNode;
    } while (element);
    return new Element.Offset(valueL, valueT);
  }

  function viewportOffset(forElement) {
    element = $(element);
    var valueT = 0, valueL = 0, docBody = document.body;

    var element = forElement;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      if (element.offsetParent == docBody &&
        Element.getStyle(element, 'position') == 'absolute') break;
    } while (element = element.offsetParent);

    element = forElement;
    do {
      if (element != docBody) {
        valueT -= element.scrollTop  || 0;
        valueL -= element.scrollLeft || 0;
      }
    } while (element = element.parentNode);
    return new Element.Offset(valueL, valueT);
  }

  function absolutize(element) {
    element = $(element);

    if (Element.getStyle(element, 'position') === 'absolute') {
      return element;
    }

    var offsetParent = getOffsetParent(element);
    var eOffset = element.viewportOffset(),
     pOffset = offsetParent.viewportOffset();

    var offset = eOffset.relativeTo(pOffset);
    var layout = element.getLayout();

    element.store('prototype_absolutize_original_styles', {
      left:   element.getStyle('left'),
      top:    element.getStyle('top'),
      width:  element.getStyle('width'),
      height: element.getStyle('height')
    });

    element.setStyle({
      position: 'absolute',
      top:    offset.top + 'px',
      left:   offset.left + 'px',
      width:  layout.get('width') + 'px',
      height: layout.get('height') + 'px'
    });

    return element;
  }

  function relativize(element) {
    element = $(element);
    if (Element.getStyle(element, 'position') === 'relative') {
      return element;
    }

    var originalStyles =
     element.retrieve('prototype_absolutize_original_styles');

    if (originalStyles) element.setStyle(originalStyles);
    return element;
  }

  if (Prototype.Browser.IE) {
    getOffsetParent = getOffsetParent.wrap(
      function(proceed, element) {
        element = $(element);

        if (isDocument(element) || isDetached(element) || isBody(element) || isHtml(element))
          return $(document.body);

        var position = element.getStyle('position');
        if (position !== 'static') return proceed(element);

        element.setStyle({ position: 'relative' });
        var value = proceed(element);
        element.setStyle({ position: position });
        return value;
      }
    );

    positionedOffset = positionedOffset.wrap(function(proceed, element) {
      element = $(element);
      if (!element.parentNode) return new Element.Offset(0, 0);
      var position = element.getStyle('position');
      if (position !== 'static') return proceed(element);

      var offsetParent = element.getOffsetParent();
      if (offsetParent && offsetParent.getStyle('position') === 'fixed')
        hasLayout(offsetParent);

      element.setStyle({ position: 'relative' });
      var value = proceed(element);
      element.setStyle({ position: position });
      return value;
    });
  } else if (Prototype.Browser.Webkit) {
    cumulativeOffset = function(element) {
      element = $(element);
      var valueT = 0, valueL = 0;
      do {
        valueT += element.offsetTop  || 0;
        valueL += element.offsetLeft || 0;
        if (element.offsetParent == document.body)
          if (Element.getStyle(element, 'position') == 'absolute') break;

        element = element.offsetParent;
      } while (element);

      return new Element.Offset(valueL, valueT);
    };
  }


  Element.addMethods({
    getLayout:              getLayout,
    measure:                measure,
    getDimensions:          getDimensions,
    getOffsetParent:        getOffsetParent,
    cumulativeOffset:       cumulativeOffset,
    positionedOffset:       positionedOffset,
    cumulativeScrollOffset: cumulativeScrollOffset,
    viewportOffset:         viewportOffset,
    absolutize:             absolutize,
    relativize:             relativize
  });

  function isBody(element) {
    return element.nodeName.toUpperCase() === 'BODY';
  }

  function isHtml(element) {
    return element.nodeName.toUpperCase() === 'HTML';
  }

  function isDocument(element) {
    return element.nodeType === Node.DOCUMENT_NODE;
  }

  function isDetached(element) {
    return element !== document.body &&
     !Element.descendantOf(element, document.body);
  }

  if ('getBoundingClientRect' in document.documentElement) {
    Element.addMethods({
      viewportOffset: function(element) {
        element = $(element);
        if (isDetached(element)) return new Element.Offset(0, 0);

        var rect = element.getBoundingClientRect(),
         docEl = document.documentElement;
        return new Element.Offset(rect.left - docEl.clientLeft,
         rect.top - docEl.clientTop);
      }
    });
  }
})();
window.$$ = function() {
  var expression = $A(arguments).join(', ');
  return Prototype.Selector.select(expression, document);
};

Prototype.Selector = (function() {

  function select() {
    throw new Error('Method "Prototype.Selector.select" must be defined.');
  }

  function match() {
    throw new Error('Method "Prototype.Selector.match" must be defined.');
  }

  function find(elements, expression, index) {
    index = index || 0;
    var match = Prototype.Selector.match, length = elements.length, matchIndex = 0, i;

    for (i = 0; i < length; i++) {
      if (match(elements[i], expression) && index == matchIndex++) {
        return Element.extend(elements[i]);
      }
    }
  }

  function extendElements(elements) {
    for (var i = 0, length = elements.length; i < length; i++) {
      Element.extend(elements[i]);
    }
    return elements;
  }


  var K = Prototype.K;

  return {
    select: select,
    match: match,
    find: find,
    extendElements: (Element.extend === K) ? K : extendElements,
    extendElement: Element.extend
  };
})();
/*
 * Sizzle CSS Selector Engine - v1.0
 *  Copyright 2009, The Dojo Foundation
 *  Released under the MIT, BSD, and GPL Licenses.
 *  More information: http://sizzlejs.com/
 */
(function(){

var chunker = /((?:\((?:\([^()]+\)|[^()]+)+\)|\[(?:\[[^[\]]*\]|['"][^'"]*['"]|[^[\]'"]+)+\]|\\.|[^ >+~,(\[\\]+)+|[>+~])(\s*,\s*)?((?:.|\r|\n)*)/g,
    done = 0,
    toString = Object.prototype.toString,
    hasDuplicate = false,
    baseHasDuplicate = true;

[0, 0].sort(function(){
    baseHasDuplicate = false;
    return 0;
});

var Sizzle = function(selector, context, results, seed) {
    results = results || [];
    var origContext = context = context || document;

    if ( context.nodeType !== 1 && context.nodeType !== 9 ) {
        return [];
    }

    if ( !selector || typeof selector !== "string" ) {
        return results;
    }

    var parts = [], m, set, checkSet, check, mode, extra, prune = true, contextXML = isXML(context),
        soFar = selector;

    while ( (chunker.exec(""), m = chunker.exec(soFar)) !== null ) {
        soFar = m[3];

        parts.push( m[1] );

        if ( m[2] ) {
            extra = m[3];
            break;
        }
    }

    if ( parts.length > 1 && origPOS.exec( selector ) ) {
        if ( parts.length === 2 && Expr.relative[ parts[0] ] ) {
            set = posProcess( parts[0] + parts[1], context );
        } else {
            set = Expr.relative[ parts[0] ] ?
                [ context ] :
                Sizzle( parts.shift(), context );

            while ( parts.length ) {
                selector = parts.shift();

                if ( Expr.relative[ selector ] )
                    selector += parts.shift();

                set = posProcess( selector, set );
            }
        }
    } else {
        if ( !seed && parts.length > 1 && context.nodeType === 9 && !contextXML &&
                Expr.match.ID.test(parts[0]) && !Expr.match.ID.test(parts[parts.length - 1]) ) {
            var ret = Sizzle.find( parts.shift(), context, contextXML );
            context = ret.expr ? Sizzle.filter( ret.expr, ret.set )[0] : ret.set[0];
        }

        if ( context ) {
            var ret = seed ?
                { expr: parts.pop(), set: makeArray(seed) } :
                Sizzle.find( parts.pop(), parts.length === 1 && (parts[0] === "~" || parts[0] === "+") && context.parentNode ? context.parentNode : context, contextXML );
            set = ret.expr ? Sizzle.filter( ret.expr, ret.set ) : ret.set;

            if ( parts.length > 0 ) {
                checkSet = makeArray(set);
            } else {
                prune = false;
            }

            while ( parts.length ) {
                var cur = parts.pop(), pop = cur;

                if ( !Expr.relative[ cur ] ) {
                    cur = "";
                } else {
                    pop = parts.pop();
                }

                if ( pop == null ) {
                    pop = context;
                }

                Expr.relative[ cur ]( checkSet, pop, contextXML );
            }
        } else {
            checkSet = parts = [];
        }
    }

    if ( !checkSet ) {
        checkSet = set;
    }

    if ( !checkSet ) {
        throw "Syntax error, unrecognized expression: " + (cur || selector);
    }

    if ( toString.call(checkSet) === "[object Array]" ) {
        if ( !prune ) {
            results.push.apply( results, checkSet );
        } else if ( context && context.nodeType === 1 ) {
            for ( var i = 0; checkSet[i] != null; i++ ) {
                if ( checkSet[i] && (checkSet[i] === true || checkSet[i].nodeType === 1 && contains(context, checkSet[i])) ) {
                    results.push( set[i] );
                }
            }
        } else {
            for ( var i = 0; checkSet[i] != null; i++ ) {
                if ( checkSet[i] && checkSet[i].nodeType === 1 ) {
                    results.push( set[i] );
                }
            }
        }
    } else {
        makeArray( checkSet, results );
    }

    if ( extra ) {
        Sizzle( extra, origContext, results, seed );
        Sizzle.uniqueSort( results );
    }

    return results;
};

Sizzle.uniqueSort = function(results){
    if ( sortOrder ) {
        hasDuplicate = baseHasDuplicate;
        results.sort(sortOrder);

        if ( hasDuplicate ) {
            for ( var i = 1; i < results.length; i++ ) {
                if ( results[i] === results[i-1] ) {
                    results.splice(i--, 1);
                }
            }
        }
    }

    return results;
};

Sizzle.matches = function(expr, set){
    return Sizzle(expr, null, null, set);
};

Sizzle.find = function(expr, context, isXML){
    var set, match;

    if ( !expr ) {
        return [];
    }

    for ( var i = 0, l = Expr.order.length; i < l; i++ ) {
        var type = Expr.order[i], match;

        if ( (match = Expr.leftMatch[ type ].exec( expr )) ) {
            var left = match[1];
            match.splice(1,1);

            if ( left.substr( left.length - 1 ) !== "\\" ) {
                match[1] = (match[1] || "").replace(/\\/g, "");
                set = Expr.find[ type ]( match, context, isXML );
                if ( set != null ) {
                    expr = expr.replace( Expr.match[ type ], "" );
                    break;
                }
            }
        }
    }

    if ( !set ) {
        set = context.getElementsByTagName("*");
    }

    return {set: set, expr: expr};
};

Sizzle.filter = function(expr, set, inplace, not){
    var old = expr, result = [], curLoop = set, match, anyFound,
        isXMLFilter = set && set[0] && isXML(set[0]);

    while ( expr && set.length ) {
        for ( var type in Expr.filter ) {
            if ( (match = Expr.match[ type ].exec( expr )) != null ) {
                var filter = Expr.filter[ type ], found, item;
                anyFound = false;

                if ( curLoop == result ) {
                    result = [];
                }

                if ( Expr.preFilter[ type ] ) {
                    match = Expr.preFilter[ type ]( match, curLoop, inplace, result, not, isXMLFilter );

                    if ( !match ) {
                        anyFound = found = true;
                    } else if ( match === true ) {
                        continue;
                    }
                }

                if ( match ) {
                    for ( var i = 0; (item = curLoop[i]) != null; i++ ) {
                        if ( item ) {
                            found = filter( item, match, i, curLoop );
                            var pass = not ^ !!found;

                            if ( inplace && found != null ) {
                                if ( pass ) {
                                    anyFound = true;
                                } else {
                                    curLoop[i] = false;
                                }
                            } else if ( pass ) {
                                result.push( item );
                                anyFound = true;
                            }
                        }
                    }
                }

                if ( found !== undefined ) {
                    if ( !inplace ) {
                        curLoop = result;
                    }

                    expr = expr.replace( Expr.match[ type ], "" );

                    if ( !anyFound ) {
                        return [];
                    }

                    break;
                }
            }
        }

        if ( expr == old ) {
            if ( anyFound == null ) {
                throw "Syntax error, unrecognized expression: " + expr;
            } else {
                break;
            }
        }

        old = expr;
    }

    return curLoop;
};

var Expr = Sizzle.selectors = {
    order: [ "ID", "NAME", "TAG" ],
    match: {
        ID: /#((?:[\w\u00c0-\uFFFF-]|\\.)+)/,
        CLASS: /\.((?:[\w\u00c0-\uFFFF-]|\\.)+)/,
        NAME: /\[name=['"]*((?:[\w\u00c0-\uFFFF-]|\\.)+)['"]*\]/,
        ATTR: /\[\s*((?:[\w\u00c0-\uFFFF-]|\\.)+)\s*(?:(\S?=)\s*(['"]*)(.*?)\3|)\s*\]/,
        TAG: /^((?:[\w\u00c0-\uFFFF\*-]|\\.)+)/,
        CHILD: /:(only|nth|last|first)-child(?:\((even|odd|[\dn+-]*)\))?/,
        POS: /:(nth|eq|gt|lt|first|last|even|odd)(?:\((\d*)\))?(?=[^-]|$)/,
        PSEUDO: /:((?:[\w\u00c0-\uFFFF-]|\\.)+)(?:\((['"]*)((?:\([^\)]+\)|[^\2\(\)]*)+)\2\))?/
    },
    leftMatch: {},
    attrMap: {
        "class": "className",
        "for": "htmlFor"
    },
    attrHandle: {
        href: function(elem){
            return elem.getAttribute("href");
        }
    },
    relative: {
        "+": function(checkSet, part, isXML){
            var isPartStr = typeof part === "string",
                isTag = isPartStr && !/\W/.test(part),
                isPartStrNotTag = isPartStr && !isTag;

            if ( isTag && !isXML ) {
                part = part.toUpperCase();
            }

            for ( var i = 0, l = checkSet.length, elem; i < l; i++ ) {
                if ( (elem = checkSet[i]) ) {
                    while ( (elem = elem.previousSibling) && elem.nodeType !== 1 ) {}

                    checkSet[i] = isPartStrNotTag || elem && elem.nodeName === part ?
                        elem || false :
                        elem === part;
                }
            }

            if ( isPartStrNotTag ) {
                Sizzle.filter( part, checkSet, true );
            }
        },
        ">": function(checkSet, part, isXML){
            var isPartStr = typeof part === "string";

            if ( isPartStr && !/\W/.test(part) ) {
                part = isXML ? part : part.toUpperCase();

                for ( var i = 0, l = checkSet.length; i < l; i++ ) {
                    var elem = checkSet[i];
                    if ( elem ) {
                        var parent = elem.parentNode;
                        checkSet[i] = parent.nodeName === part ? parent : false;
                    }
                }
            } else {
                for ( var i = 0, l = checkSet.length; i < l; i++ ) {
                    var elem = checkSet[i];
                    if ( elem ) {
                        checkSet[i] = isPartStr ?
                            elem.parentNode :
                            elem.parentNode === part;
                    }
                }

                if ( isPartStr ) {
                    Sizzle.filter( part, checkSet, true );
                }
            }
        },
        "": function(checkSet, part, isXML){
            var doneName = done++, checkFn = dirCheck;

            if ( !/\W/.test(part) ) {
                var nodeCheck = part = isXML ? part : part.toUpperCase();
                checkFn = dirNodeCheck;
            }

            checkFn("parentNode", part, doneName, checkSet, nodeCheck, isXML);
        },
        "~": function(checkSet, part, isXML){
            var doneName = done++, checkFn = dirCheck;

            if ( typeof part === "string" && !/\W/.test(part) ) {
                var nodeCheck = part = isXML ? part : part.toUpperCase();
                checkFn = dirNodeCheck;
            }

            checkFn("previousSibling", part, doneName, checkSet, nodeCheck, isXML);
        }
    },
    find: {
        ID: function(match, context, isXML){
            if ( typeof context.getElementById !== "undefined" && !isXML ) {
                var m = context.getElementById(match[1]);
                return m ? [m] : [];
            }
        },
        NAME: function(match, context, isXML){
            if ( typeof context.getElementsByName !== "undefined" ) {
                var ret = [], results = context.getElementsByName(match[1]);

                for ( var i = 0, l = results.length; i < l; i++ ) {
                    if ( results[i].getAttribute("name") === match[1] ) {
                        ret.push( results[i] );
                    }
                }

                return ret.length === 0 ? null : ret;
            }
        },
        TAG: function(match, context){
            return context.getElementsByTagName(match[1]);
        }
    },
    preFilter: {
        CLASS: function(match, curLoop, inplace, result, not, isXML){
            match = " " + match[1].replace(/\\/g, "") + " ";

            if ( isXML ) {
                return match;
            }

            for ( var i = 0, elem; (elem = curLoop[i]) != null; i++ ) {
                if ( elem ) {
                    if ( not ^ (elem.className && (" " + elem.className + " ").indexOf(match) >= 0) ) {
                        if ( !inplace )
                            result.push( elem );
                    } else if ( inplace ) {
                        curLoop[i] = false;
                    }
                }
            }

            return false;
        },
        ID: function(match){
            return match[1].replace(/\\/g, "");
        },
        TAG: function(match, curLoop){
            for ( var i = 0; curLoop[i] === false; i++ ){}
            return curLoop[i] && isXML(curLoop[i]) ? match[1] : match[1].toUpperCase();
        },
        CHILD: function(match){
            if ( match[1] == "nth" ) {
                var test = /(-?)(\d*)n((?:\+|-)?\d*)/.exec(
                    match[2] == "even" && "2n" || match[2] == "odd" && "2n+1" ||
                    !/\D/.test( match[2] ) && "0n+" + match[2] || match[2]);

                match[2] = (test[1] + (test[2] || 1)) - 0;
                match[3] = test[3] - 0;
            }

            match[0] = done++;

            return match;
        },
        ATTR: function(match, curLoop, inplace, result, not, isXML){
            var name = match[1].replace(/\\/g, "");

            if ( !isXML && Expr.attrMap[name] ) {
                match[1] = Expr.attrMap[name];
            }

            if ( match[2] === "~=" ) {
                match[4] = " " + match[4] + " ";
            }

            return match;
        },
        PSEUDO: function(match, curLoop, inplace, result, not){
            if ( match[1] === "not" ) {
                if ( ( chunker.exec(match[3]) || "" ).length > 1 || /^\w/.test(match[3]) ) {
                    match[3] = Sizzle(match[3], null, null, curLoop);
                } else {
                    var ret = Sizzle.filter(match[3], curLoop, inplace, true ^ not);
                    if ( !inplace ) {
                        result.push.apply( result, ret );
                    }
                    return false;
                }
            } else if ( Expr.match.POS.test( match[0] ) || Expr.match.CHILD.test( match[0] ) ) {
                return true;
            }

            return match;
        },
        POS: function(match){
            match.unshift( true );
            return match;
        }
    },
    filters: {
        enabled: function(elem){
            return elem.disabled === false && elem.type !== "hidden";
        },
        disabled: function(elem){
            return elem.disabled === true;
        },
        checked: function(elem){
            return elem.checked === true;
        },
        selected: function(elem){
            elem.parentNode.selectedIndex;
            return elem.selected === true;
        },
        parent: function(elem){
            return !!elem.firstChild;
        },
        empty: function(elem){
            return !elem.firstChild;
        },
        has: function(elem, i, match){
            return !!Sizzle( match[3], elem ).length;
        },
        header: function(elem){
            return /h\d/i.test( elem.nodeName );
        },
        text: function(elem){
            return "text" === elem.type;
        },
        radio: function(elem){
            return "radio" === elem.type;
        },
        checkbox: function(elem){
            return "checkbox" === elem.type;
        },
        file: function(elem){
            return "file" === elem.type;
        },
        password: function(elem){
            return "password" === elem.type;
        },
        submit: function(elem){
            return "submit" === elem.type;
        },
        image: function(elem){
            return "image" === elem.type;
        },
        reset: function(elem){
            return "reset" === elem.type;
        },
        button: function(elem){
            return "button" === elem.type || elem.nodeName.toUpperCase() === "BUTTON";
        },
        input: function(elem){
            return /input|select|textarea|button/i.test(elem.nodeName);
        }
    },
    setFilters: {
        first: function(elem, i){
            return i === 0;
        },
        last: function(elem, i, match, array){
            return i === array.length - 1;
        },
        even: function(elem, i){
            return i % 2 === 0;
        },
        odd: function(elem, i){
            return i % 2 === 1;
        },
        lt: function(elem, i, match){
            return i < match[3] - 0;
        },
        gt: function(elem, i, match){
            return i > match[3] - 0;
        },
        nth: function(elem, i, match){
            return match[3] - 0 == i;
        },
        eq: function(elem, i, match){
            return match[3] - 0 == i;
        }
    },
    filter: {
        PSEUDO: function(elem, match, i, array){
            var name = match[1], filter = Expr.filters[ name ];

            if ( filter ) {
                return filter( elem, i, match, array );
            } else if ( name === "contains" ) {
                return (elem.textContent || elem.innerText || "").indexOf(match[3]) >= 0;
            } else if ( name === "not" ) {
                var not = match[3];

                for ( var i = 0, l = not.length; i < l; i++ ) {
                    if ( not[i] === elem ) {
                        return false;
                    }
                }

                return true;
            }
        },
        CHILD: function(elem, match){
            var type = match[1], node = elem;
            switch (type) {
                case 'only':
                case 'first':
                    while ( (node = node.previousSibling) )  {
                        if ( node.nodeType === 1 ) return false;
                    }
                    if ( type == 'first') return true;
                    node = elem;
                case 'last':
                    while ( (node = node.nextSibling) )  {
                        if ( node.nodeType === 1 ) return false;
                    }
                    return true;
                case 'nth':
                    var first = match[2], last = match[3];

                    if ( first == 1 && last == 0 ) {
                        return true;
                    }

                    var doneName = match[0],
                        parent = elem.parentNode;

                    if ( parent && (parent.sizcache !== doneName || !elem.nodeIndex) ) {
                        var count = 0;
                        for ( node = parent.firstChild; node; node = node.nextSibling ) {
                            if ( node.nodeType === 1 ) {
                                node.nodeIndex = ++count;
                            }
                        }
                        parent.sizcache = doneName;
                    }

                    var diff = elem.nodeIndex - last;
                    if ( first == 0 ) {
                        return diff == 0;
                    } else {
                        return ( diff % first == 0 && diff / first >= 0 );
                    }
            }
        },
        ID: function(elem, match){
            return elem.nodeType === 1 && elem.getAttribute("id") === match;
        },
        TAG: function(elem, match){
            return (match === "*" && elem.nodeType === 1) || elem.nodeName === match;
        },
        CLASS: function(elem, match){
            return (" " + (elem.className || elem.getAttribute("class")) + " ")
                .indexOf( match ) > -1;
        },
        ATTR: function(elem, match){
            var name = match[1],
                result = Expr.attrHandle[ name ] ?
                    Expr.attrHandle[ name ]( elem ) :
                    elem[ name ] != null ?
                        elem[ name ] :
                        elem.getAttribute( name ),
                value = result + "",
                type = match[2],
                check = match[4];

            return result == null ?
                type === "!=" :
                type === "=" ?
                value === check :
                type === "*=" ?
                value.indexOf(check) >= 0 :
                type === "~=" ?
                (" " + value + " ").indexOf(check) >= 0 :
                !check ?
                value && result !== false :
                type === "!=" ?
                value != check :
                type === "^=" ?
                value.indexOf(check) === 0 :
                type === "$=" ?
                value.substr(value.length - check.length) === check :
                type === "|=" ?
                value === check || value.substr(0, check.length + 1) === check + "-" :
                false;
        },
        POS: function(elem, match, i, array){
            var name = match[2], filter = Expr.setFilters[ name ];

            if ( filter ) {
                return filter( elem, i, match, array );
            }
        }
    }
};

var origPOS = Expr.match.POS;

for ( var type in Expr.match ) {
    Expr.match[ type ] = new RegExp( Expr.match[ type ].source + /(?![^\[]*\])(?![^\(]*\))/.source );
    Expr.leftMatch[ type ] = new RegExp( /(^(?:.|\r|\n)*?)/.source + Expr.match[ type ].source );
}

var makeArray = function(array, results) {
    array = Array.prototype.slice.call( array, 0 );

    if ( results ) {
        results.push.apply( results, array );
        return results;
    }

    return array;
};

try {
    Array.prototype.slice.call( document.documentElement.childNodes, 0 );

} catch(e){
    makeArray = function(array, results) {
        var ret = results || [];

        if ( toString.call(array) === "[object Array]" ) {
            Array.prototype.push.apply( ret, array );
        } else {
            if ( typeof array.length === "number" ) {
                for ( var i = 0, l = array.length; i < l; i++ ) {
                    ret.push( array[i] );
                }
            } else {
                for ( var i = 0; array[i]; i++ ) {
                    ret.push( array[i] );
                }
            }
        }

        return ret;
    };
}

var sortOrder;

if ( document.documentElement.compareDocumentPosition ) {
    sortOrder = function( a, b ) {
        if ( !a.compareDocumentPosition || !b.compareDocumentPosition ) {
            if ( a == b ) {
                hasDuplicate = true;
            }
            return 0;
        }

        var ret = a.compareDocumentPosition(b) & 4 ? -1 : a === b ? 0 : 1;
        if ( ret === 0 ) {
            hasDuplicate = true;
        }
        return ret;
    };
} else if ( "sourceIndex" in document.documentElement ) {
    sortOrder = function( a, b ) {
        if ( !a.sourceIndex || !b.sourceIndex ) {
            if ( a == b ) {
                hasDuplicate = true;
            }
            return 0;
        }

        var ret = a.sourceIndex - b.sourceIndex;
        if ( ret === 0 ) {
            hasDuplicate = true;
        }
        return ret;
    };
} else if ( document.createRange ) {
    sortOrder = function( a, b ) {
        if ( !a.ownerDocument || !b.ownerDocument ) {
            if ( a == b ) {
                hasDuplicate = true;
            }
            return 0;
        }

        var aRange = a.ownerDocument.createRange(), bRange = b.ownerDocument.createRange();
        aRange.setStart(a, 0);
        aRange.setEnd(a, 0);
        bRange.setStart(b, 0);
        bRange.setEnd(b, 0);
        var ret = aRange.compareBoundaryPoints(Range.START_TO_END, bRange);
        if ( ret === 0 ) {
            hasDuplicate = true;
        }
        return ret;
    };
}

(function(){
    var form = document.createElement("div"),
        id = "script" + (new Date).getTime();
    form.innerHTML = "<a name='" + id + "'/>";

    var root = document.documentElement;
    root.insertBefore( form, root.firstChild );

    if ( !!document.getElementById( id ) ) {
        Expr.find.ID = function(match, context, isXML){
            if ( typeof context.getElementById !== "undefined" && !isXML ) {
                var m = context.getElementById(match[1]);
                return m ? m.id === match[1] || typeof m.getAttributeNode !== "undefined" && m.getAttributeNode("id").nodeValue === match[1] ? [m] : undefined : [];
            }
        };

        Expr.filter.ID = function(elem, match){
            var node = typeof elem.getAttributeNode !== "undefined" && elem.getAttributeNode("id");
            return elem.nodeType === 1 && node && node.nodeValue === match;
        };
    }

    root.removeChild( form );
    root = form = null; // release memory in IE
})();

(function(){

    var div = document.createElement("div");
    div.appendChild( document.createComment("") );

    if ( div.getElementsByTagName("*").length > 0 ) {
        Expr.find.TAG = function(match, context){
            var results = context.getElementsByTagName(match[1]);

            if ( match[1] === "*" ) {
                var tmp = [];

                for ( var i = 0; results[i]; i++ ) {
                    if ( results[i].nodeType === 1 ) {
                        tmp.push( results[i] );
                    }
                }

                results = tmp;
            }

            return results;
        };
    }

    div.innerHTML = "<a href='#'></a>";
    if ( div.firstChild && typeof div.firstChild.getAttribute !== "undefined" &&
            div.firstChild.getAttribute("href") !== "#" ) {
        Expr.attrHandle.href = function(elem){
            return elem.getAttribute("href", 2);
        };
    }

    div = null; // release memory in IE
})();

if ( document.querySelectorAll ) (function(){
    var oldSizzle = Sizzle, div = document.createElement("div");
    div.innerHTML = "<p class='TEST'></p>";

    if ( div.querySelectorAll && div.querySelectorAll(".TEST").length === 0 ) {
        return;
    }

    Sizzle = function(query, context, extra, seed){
        context = context || document;

        if ( !seed && context.nodeType === 9 && !isXML(context) ) {
            try {
                return makeArray( context.querySelectorAll(query), extra );
            } catch(e){}
        }

        return oldSizzle(query, context, extra, seed);
    };

    for ( var prop in oldSizzle ) {
        Sizzle[ prop ] = oldSizzle[ prop ];
    }

    div = null; // release memory in IE
})();

if ( document.getElementsByClassName && document.documentElement.getElementsByClassName ) (function(){
    var div = document.createElement("div");
    div.innerHTML = "<div class='test e'></div><div class='test'></div>";

    if ( div.getElementsByClassName("e").length === 0 )
        return;

    div.lastChild.className = "e";

    if ( div.getElementsByClassName("e").length === 1 )
        return;

    Expr.order.splice(1, 0, "CLASS");
    Expr.find.CLASS = function(match, context, isXML) {
        if ( typeof context.getElementsByClassName !== "undefined" && !isXML ) {
            return context.getElementsByClassName(match[1]);
        }
    };

    div = null; // release memory in IE
})();

function dirNodeCheck( dir, cur, doneName, checkSet, nodeCheck, isXML ) {
    var sibDir = dir == "previousSibling" && !isXML;
    for ( var i = 0, l = checkSet.length; i < l; i++ ) {
        var elem = checkSet[i];
        if ( elem ) {
            if ( sibDir && elem.nodeType === 1 ){
                elem.sizcache = doneName;
                elem.sizset = i;
            }
            elem = elem[dir];
            var match = false;

            while ( elem ) {
                if ( elem.sizcache === doneName ) {
                    match = checkSet[elem.sizset];
                    break;
                }

                if ( elem.nodeType === 1 && !isXML ){
                    elem.sizcache = doneName;
                    elem.sizset = i;
                }

                if ( elem.nodeName === cur ) {
                    match = elem;
                    break;
                }

                elem = elem[dir];
            }

            checkSet[i] = match;
        }
    }
}

function dirCheck( dir, cur, doneName, checkSet, nodeCheck, isXML ) {
    var sibDir = dir == "previousSibling" && !isXML;
    for ( var i = 0, l = checkSet.length; i < l; i++ ) {
        var elem = checkSet[i];
        if ( elem ) {
            if ( sibDir && elem.nodeType === 1 ) {
                elem.sizcache = doneName;
                elem.sizset = i;
            }
            elem = elem[dir];
            var match = false;

            while ( elem ) {
                if ( elem.sizcache === doneName ) {
                    match = checkSet[elem.sizset];
                    break;
                }

                if ( elem.nodeType === 1 ) {
                    if ( !isXML ) {
                        elem.sizcache = doneName;
                        elem.sizset = i;
                    }
                    if ( typeof cur !== "string" ) {
                        if ( elem === cur ) {
                            match = true;
                            break;
                        }

                    } else if ( Sizzle.filter( cur, [elem] ).length > 0 ) {
                        match = elem;
                        break;
                    }
                }

                elem = elem[dir];
            }

            checkSet[i] = match;
        }
    }
}

var contains = document.compareDocumentPosition ?  function(a, b){
    return a.compareDocumentPosition(b) & 16;
} : function(a, b){
    return a !== b && (a.contains ? a.contains(b) : true);
};

var isXML = function(elem){
    return elem.nodeType === 9 && elem.documentElement.nodeName !== "HTML" ||
        !!elem.ownerDocument && elem.ownerDocument.documentElement.nodeName !== "HTML";
};

var posProcess = function(selector, context){
    var tmpSet = [], later = "", match,
        root = context.nodeType ? [context] : context;

    while ( (match = Expr.match.PSEUDO.exec( selector )) ) {
        later += match[0];
        selector = selector.replace( Expr.match.PSEUDO, "" );
    }

    selector = Expr.relative[selector] ? selector + "*" : selector;

    for ( var i = 0, l = root.length; i < l; i++ ) {
        Sizzle( selector, root[i], tmpSet );
    }

    return Sizzle.filter( later, tmpSet );
};


window.Sizzle = Sizzle;

})();

Prototype._original_property = window.Sizzle;

;(function(engine) {
  var extendElements = Prototype.Selector.extendElements;

  function select(selector, scope) {
    return extendElements(engine(selector, scope || document));
  }

  function match(element, selector) {
    return engine.matches(selector, [element]).length == 1;
  }

  Prototype.Selector.engine = engine;
  Prototype.Selector.select = select;
  Prototype.Selector.match = match;
})(Sizzle);

window.Sizzle = Prototype._original_property;
delete Prototype._original_property;

var Form = {
  reset: function(form) {
    form = $(form);
    form.reset();
    return form;
  },

  serializeElements: function(elements, options) {
    if (typeof options != 'object') options = { hash: !!options };
    else if (Object.isUndefined(options.hash)) options.hash = true;
    var key, value, submitted = false, submit = options.submit, accumulator, initial;

    if (options.hash) {
      initial = {};
      accumulator = function(result, key, value) {
        if (key in result) {
          if (!Object.isArray(result[key])) result[key] = [result[key]];
          result[key].push(value);
        } else result[key] = value;
        return result;
      };
    } else {
      initial = '';
      accumulator = function(result, key, value) {
        return result + (result ? '&' : '') + encodeURIComponent(key) + '=' + encodeURIComponent(value);
      }
    }

    return elements.inject(initial, function(result, element) {
      if (!element.disabled && element.name) {
        key = element.name; value = $(element).getValue();
        if (value != null && element.type != 'file' && (element.type != 'submit' || (!submitted &&
            submit !== false && (!submit || key == submit) && (submitted = true)))) {
          result = accumulator(result, key, value);
        }
      }
      return result;
    });
  }
};

Form.Methods = {
  serialize: function(form, options) {
    return Form.serializeElements(Form.getElements(form), options);
  },

  getElements: function(form) {
    var elements = $(form).getElementsByTagName('*'),
        element,
        arr = [ ],
        serializers = Form.Element.Serializers;
    for (var i = 0; element = elements[i]; i++) {
      arr.push(element);
    }
    return arr.inject([], function(elements, child) {
      if (serializers[child.tagName.toLowerCase()])
        elements.push(Element.extend(child));
      return elements;
    })
  },

  getInputs: function(form, typeName, name) {
    form = $(form);
    var inputs = form.getElementsByTagName('input');

    if (!typeName && !name) return $A(inputs).map(Element.extend);

    for (var i = 0, matchingInputs = [], length = inputs.length; i < length; i++) {
      var input = inputs[i];
      if ((typeName && input.type != typeName) || (name && input.name != name))
        continue;
      matchingInputs.push(Element.extend(input));
    }

    return matchingInputs;
  },

  disable: function(form) {
    form = $(form);
    Form.getElements(form).invoke('disable');
    return form;
  },

  enable: function(form) {
    form = $(form);
    Form.getElements(form).invoke('enable');
    return form;
  },

  findFirstElement: function(form) {
    var elements = $(form).getElements().findAll(function(element) {
      return 'hidden' != element.type && !element.disabled;
    });
    var firstByIndex = elements.findAll(function(element) {
      return element.hasAttribute('tabIndex') && element.tabIndex >= 0;
    }).sortBy(function(element) { return element.tabIndex }).first();

    return firstByIndex ? firstByIndex : elements.find(function(element) {
      return /^(?:input|select|textarea)$/i.test(element.tagName);
    });
  },

  focusFirstElement: function(form) {
    form = $(form);
    var element = form.findFirstElement();
    if (element) element.activate();
    return form;
  },

  request: function(form, options) {
    form = $(form), options = Object.clone(options || { });

    var params = options.parameters, action = form.readAttribute('action') || '';
    if (action.blank()) action = window.location.href;
    options.parameters = form.serialize(true);

    if (params) {
      if (Object.isString(params)) params = params.toQueryParams();
      Object.extend(options.parameters, params);
    }

    if (form.hasAttribute('method') && !options.method)
      options.method = form.method;

    return new Ajax.Request(action, options);
  }
};

/*--------------------------------------------------------------------------*/


Form.Element = {
  focus: function(element) {
    $(element).focus();
    return element;
  },

  select: function(element) {
    $(element).select();
    return element;
  }
};

Form.Element.Methods = {

  serialize: function(element) {
    element = $(element);
    if (!element.disabled && element.name) {
      var value = element.getValue();
      if (value != undefined) {
        var pair = { };
        pair[element.name] = value;
        return Object.toQueryString(pair);
      }
    }
    return '';
  },

  getValue: function(element) {
    element = $(element);
    var method = element.tagName.toLowerCase();
    return Form.Element.Serializers[method](element);
  },

  setValue: function(element, value) {
    element = $(element);
    var method = element.tagName.toLowerCase();
    Form.Element.Serializers[method](element, value);
    return element;
  },

  clear: function(element) {
    $(element).value = '';
    return element;
  },

  present: function(element) {
    return $(element).value != '';
  },

  activate: function(element) {
    element = $(element);
    try {
      element.focus();
      if (element.select && (element.tagName.toLowerCase() != 'input' ||
          !(/^(?:button|reset|submit)$/i.test(element.type))))
        element.select();
    } catch (e) { }
    return element;
  },

  disable: function(element) {
    element = $(element);
    element.disabled = true;
    return element;
  },

  enable: function(element) {
    element = $(element);
    element.disabled = false;
    return element;
  }
};

/*--------------------------------------------------------------------------*/

var Field = Form.Element;

var $F = Form.Element.Methods.getValue;

/*--------------------------------------------------------------------------*/

Form.Element.Serializers = (function() {
  function input(element, value) {
    switch (element.type.toLowerCase()) {
      case 'checkbox':
      case 'radio':
        return inputSelector(element, value);
      default:
        return valueSelector(element, value);
    }
  }

  function inputSelector(element, value) {
    if (Object.isUndefined(value))
      return element.checked ? element.value : null;
    else element.checked = !!value;
  }

  function valueSelector(element, value) {
    if (Object.isUndefined(value)) return element.value;
    else element.value = value;
  }

  function select(element, value) {
    if (Object.isUndefined(value))
      return (element.type === 'select-one' ? selectOne : selectMany)(element);

    var opt, currentValue, single = !Object.isArray(value);
    for (var i = 0, length = element.length; i < length; i++) {
      opt = element.options[i];
      currentValue = this.optionValue(opt);
      if (single) {
        if (currentValue == value) {
          opt.selected = true;
          return;
        }
      }
      else opt.selected = value.include(currentValue);
    }
  }

  function selectOne(element) {
    var index = element.selectedIndex;
    return index >= 0 ? optionValue(element.options[index]) : null;
  }

  function selectMany(element) {
    var values, length = element.length;
    if (!length) return null;

    for (var i = 0, values = []; i < length; i++) {
      var opt = element.options[i];
      if (opt.selected) values.push(optionValue(opt));
    }
    return values;
  }

  function optionValue(opt) {
    return Element.hasAttribute(opt, 'value') ? opt.value : opt.text;
  }

  return {
    input:         input,
    inputSelector: inputSelector,
    textarea:      valueSelector,
    select:        select,
    selectOne:     selectOne,
    selectMany:    selectMany,
    optionValue:   optionValue,
    button:        valueSelector
  };
})();

/*--------------------------------------------------------------------------*/


Abstract.TimedObserver = Class.create(PeriodicalExecuter, {
  initialize: function($super, element, frequency, callback) {
    $super(callback, frequency);
    this.element   = $(element);
    this.lastValue = this.getValue();
  },

  execute: function() {
    var value = this.getValue();
    if (Object.isString(this.lastValue) && Object.isString(value) ?
        this.lastValue != value : String(this.lastValue) != String(value)) {
      this.callback(this.element, value);
      this.lastValue = value;
    }
  }
});

Form.Element.Observer = Class.create(Abstract.TimedObserver, {
  getValue: function() {
    return Form.Element.getValue(this.element);
  }
});

Form.Observer = Class.create(Abstract.TimedObserver, {
  getValue: function() {
    return Form.serialize(this.element);
  }
});

/*--------------------------------------------------------------------------*/

Abstract.EventObserver = Class.create({
  initialize: function(element, callback) {
    this.element  = $(element);
    this.callback = callback;

    this.lastValue = this.getValue();
    if (this.element.tagName.toLowerCase() == 'form')
      this.registerFormCallbacks();
    else
      this.registerCallback(this.element);
  },

  onElementEvent: function() {
    var value = this.getValue();
    if (this.lastValue != value) {
      this.callback(this.element, value);
      this.lastValue = value;
    }
  },

  registerFormCallbacks: function() {
    Form.getElements(this.element).each(this.registerCallback, this);
  },

  registerCallback: function(element) {
    if (element.type) {
      switch (element.type.toLowerCase()) {
        case 'checkbox':
        case 'radio':
          Event.observe(element, 'click', this.onElementEvent.bind(this));
          break;
        default:
          Event.observe(element, 'change', this.onElementEvent.bind(this));
          break;
      }
    }
  }
});

Form.Element.EventObserver = Class.create(Abstract.EventObserver, {
  getValue: function() {
    return Form.Element.getValue(this.element);
  }
});

Form.EventObserver = Class.create(Abstract.EventObserver, {
  getValue: function() {
    return Form.serialize(this.element);
  }
});
(function() {

  var Event = {
    KEY_BACKSPACE: 8,
    KEY_TAB:       9,
    KEY_RETURN:   13,
    KEY_ESC:      27,
    KEY_LEFT:     37,
    KEY_UP:       38,
    KEY_RIGHT:    39,
    KEY_DOWN:     40,
    KEY_DELETE:   46,
    KEY_HOME:     36,
    KEY_END:      35,
    KEY_PAGEUP:   33,
    KEY_PAGEDOWN: 34,
    KEY_INSERT:   45,

    cache: {}
  };

  var docEl = document.documentElement;
  var MOUSEENTER_MOUSELEAVE_EVENTS_SUPPORTED = 'onmouseenter' in docEl
    && 'onmouseleave' in docEl;



  var isIELegacyEvent = function(event) { return false; };

  if (window.attachEvent) {
    if (window.addEventListener) {
      isIELegacyEvent = function(event) {
        return !(event instanceof window.Event);
      };
    } else {
      isIELegacyEvent = function(event) { return true; };
    }
  }

  var _isButton;

  function _isButtonForDOMEvents(event, code) {
    return event.which ? (event.which === code + 1) : (event.button === code);
  }

  var legacyButtonMap = { 0: 1, 1: 4, 2: 2 };
  function _isButtonForLegacyEvents(event, code) {
    return event.button === legacyButtonMap[code];
  }

  function _isButtonForWebKit(event, code) {
    switch (code) {
      case 0: return event.which == 1 && !event.metaKey;
      case 1: return event.which == 2 || (event.which == 1 && event.metaKey);
      case 2: return event.which == 3;
      default: return false;
    }
  }

  if (window.attachEvent) {
    if (!window.addEventListener) {
      _isButton = _isButtonForLegacyEvents;
    } else {
      _isButton = function(event, code) {
        return isIELegacyEvent(event) ? _isButtonForLegacyEvents(event, code) :
         _isButtonForDOMEvents(event, code);
      }
    }
  } else if (Prototype.Browser.WebKit) {
    _isButton = _isButtonForWebKit;
  } else {
    _isButton = _isButtonForDOMEvents;
  }

  function isLeftClick(event)   { return _isButton(event, 0) }

  function isMiddleClick(event) { return _isButton(event, 1) }

  function isRightClick(event)  { return _isButton(event, 2) }

  function element(event) {
    event = Event.extend(event);

    var node = event.target, type = event.type,
     currentTarget = event.currentTarget;

    if (currentTarget && currentTarget.tagName) {
      if (type === 'load' || type === 'error' ||
        (type === 'click' && currentTarget.tagName.toLowerCase() === 'input'
          && currentTarget.type === 'radio'))
            node = currentTarget;
    }

    if (node.nodeType == Node.TEXT_NODE)
      node = node.parentNode;

    return Element.extend(node);
  }

  function findElement(event, expression) {
    var element = Event.element(event);

    if (!expression) return element;
    while (element) {
      if (Object.isElement(element) && Prototype.Selector.match(element, expression)) {
        return Element.extend(element);
      }
      element = element.parentNode;
    }
  }

  function pointer(event) {
    return { x: pointerX(event), y: pointerY(event) };
  }

  function pointerX(event) {
    var docElement = document.documentElement,
     body = document.body || { scrollLeft: 0 };

    return event.pageX || (event.clientX +
      (docElement.scrollLeft || body.scrollLeft) -
      (docElement.clientLeft || 0));
  }

  function pointerY(event) {
    var docElement = document.documentElement,
     body = document.body || { scrollTop: 0 };

    return  event.pageY || (event.clientY +
       (docElement.scrollTop || body.scrollTop) -
       (docElement.clientTop || 0));
  }


  function stop(event) {
    Event.extend(event);
    event.preventDefault();
    event.stopPropagation();

    event.stopped = true;
  }


  Event.Methods = {
    isLeftClick:   isLeftClick,
    isMiddleClick: isMiddleClick,
    isRightClick:  isRightClick,

    element:     element,
    findElement: findElement,

    pointer:  pointer,
    pointerX: pointerX,
    pointerY: pointerY,

    stop: stop
  };

  var methods = Object.keys(Event.Methods).inject({ }, function(m, name) {
    m[name] = Event.Methods[name].methodize();
    return m;
  });

  if (window.attachEvent) {
    function _relatedTarget(event) {
      var element;
      switch (event.type) {
        case 'mouseover':
        case 'mouseenter':
          element = event.fromElement;
          break;
        case 'mouseout':
        case 'mouseleave':
          element = event.toElement;
          break;
        default:
          return null;
      }
      return Element.extend(element);
    }

    var additionalMethods = {
      stopPropagation: function() { this.cancelBubble = true },
      preventDefault:  function() { this.returnValue = false },
      inspect: function() { return '[object Event]' }
    };

    Event.extend = function(event, element) {
      if (!event) return false;

      if (!isIELegacyEvent(event)) return event;

      if (event._extendedByPrototype) return event;
      event._extendedByPrototype = Prototype.emptyFunction;

      var pointer = Event.pointer(event);

      Object.extend(event, {
        target: event.srcElement || element,
        relatedTarget: _relatedTarget(event),
        pageX:  pointer.x,
        pageY:  pointer.y
      });

      Object.extend(event, methods);
      Object.extend(event, additionalMethods);

      return event;
    };
  } else {
    Event.extend = Prototype.K;
  }

  if (window.addEventListener) {
    Event.prototype = window.Event.prototype || document.createEvent('HTMLEvents').__proto__;
    Object.extend(Event.prototype, methods);
  }

  function _createResponder(element, eventName, handler) {
    var registry = Element.retrieve(element, 'prototype_event_registry');

    if (Object.isUndefined(registry)) {
      CACHE.push(element);
      registry = Element.retrieve(element, 'prototype_event_registry', $H());
    }

    var respondersForEvent = registry.get(eventName);
    if (Object.isUndefined(respondersForEvent)) {
      respondersForEvent = [];
      registry.set(eventName, respondersForEvent);
    }

    if (respondersForEvent.pluck('handler').include(handler)) return false;

    var responder;
    if (eventName.include(":")) {
      responder = function(event) {
        if (Object.isUndefined(event.eventName))
          return false;

        if (event.eventName !== eventName)
          return false;

        Event.extend(event, element);
        handler.call(element, event);
      };
    } else {
      if (!MOUSEENTER_MOUSELEAVE_EVENTS_SUPPORTED &&
       (eventName === "mouseenter" || eventName === "mouseleave")) {
        if (eventName === "mouseenter" || eventName === "mouseleave") {
          responder = function(event) {
            Event.extend(event, element);

            var parent = event.relatedTarget;
            while (parent && parent !== element) {
              try { parent = parent.parentNode; }
              catch(e) { parent = element; }
            }

            if (parent === element) return;

            handler.call(element, event);
          };
        }
      } else {
        responder = function(event) {
          Event.extend(event, element);
          handler.call(element, event);
        };
      }
    }

    responder.handler = handler;
    respondersForEvent.push(responder);
    return responder;
  }

  function _destroyCache() {
    for (var i = 0, length = CACHE.length; i < length; i++) {
      Event.stopObserving(CACHE[i]);
      CACHE[i] = null;
    }
  }

  var CACHE = [];

  if (Prototype.Browser.IE)
    window.attachEvent('onunload', _destroyCache);

  if (Prototype.Browser.WebKit)
    window.addEventListener('unload', Prototype.emptyFunction, false);


  var _getDOMEventName = Prototype.K,
      translations = { mouseenter: "mouseover", mouseleave: "mouseout" };

  if (!MOUSEENTER_MOUSELEAVE_EVENTS_SUPPORTED) {
    _getDOMEventName = function(eventName) {
      return (translations[eventName] || eventName);
    };
  }

  function observe(element, eventName, handler) {
    element = $(element);

    var responder = _createResponder(element, eventName, handler);

    if (!responder) return element;

    if (eventName.include(':')) {
      if (element.addEventListener)
        element.addEventListener("dataavailable", responder, false);
      else {
        element.attachEvent("ondataavailable", responder);
        element.attachEvent("onlosecapture", responder);
      }
    } else {
      var actualEventName = _getDOMEventName(eventName);

      if (element.addEventListener)
        element.addEventListener(actualEventName, responder, false);
      else
        element.attachEvent("on" + actualEventName, responder);
    }

    return element;
  }

  function stopObserving(element, eventName, handler) {
    element = $(element);

    var registry = Element.retrieve(element, 'prototype_event_registry');
    if (!registry) return element;

    if (!eventName) {
      registry.each( function(pair) {
        var eventName = pair.key;
        stopObserving(element, eventName);
      });
      return element;
    }

    var responders = registry.get(eventName);
    if (!responders) return element;

    if (!handler) {
      responders.each(function(r) {
        stopObserving(element, eventName, r.handler);
      });
      return element;
    }

    var i = responders.length, responder;
    while (i--) {
      if (responders[i].handler === handler) {
        responder = responders[i];
        break;
      }
    }
    if (!responder) return element;

    if (eventName.include(':')) {
      if (element.removeEventListener)
        element.removeEventListener("dataavailable", responder, false);
      else {
        element.detachEvent("ondataavailable", responder);
        element.detachEvent("onlosecapture", responder);
      }
    } else {
      var actualEventName = _getDOMEventName(eventName);
      if (element.removeEventListener)
        element.removeEventListener(actualEventName, responder, false);
      else
        element.detachEvent('on' + actualEventName, responder);
    }

    registry.set(eventName, responders.without(responder));

    return element;
  }

  function fire(element, eventName, memo, bubble) {
    element = $(element);

    if (Object.isUndefined(bubble))
      bubble = true;

    if (element == document && document.createEvent && !element.dispatchEvent)
      element = document.documentElement;

    var event;
    if (document.createEvent) {
      event = document.createEvent('HTMLEvents');
      event.initEvent('dataavailable', bubble, true);
    } else {
      event = document.createEventObject();
      event.eventType = bubble ? 'ondataavailable' : 'onlosecapture';
    }

    event.eventName = eventName;
    event.memo = memo || { };

    if (document.createEvent)
      element.dispatchEvent(event);
    else
      element.fireEvent(event.eventType, event);

    return Event.extend(event);
  }

  Event.Handler = Class.create({
    initialize: function(element, eventName, selector, callback) {
      this.element   = $(element);
      this.eventName = eventName;
      this.selector  = selector;
      this.callback  = callback;
      this.handler   = this.handleEvent.bind(this);
    },

    start: function() {
      Event.observe(this.element, this.eventName, this.handler);
      return this;
    },

    stop: function() {
      Event.stopObserving(this.element, this.eventName, this.handler);
      return this;
    },

    handleEvent: function(event) {
      var element = Event.findElement(event, this.selector);
      if (element) this.callback.call(this.element, event, element);
    }
  });

  function on(element, eventName, selector, callback) {
    element = $(element);
    if (Object.isFunction(selector) && Object.isUndefined(callback)) {
      callback = selector, selector = null;
    }

    return new Event.Handler(element, eventName, selector, callback).start();
  }

  Object.extend(Event, Event.Methods);

  Object.extend(Event, {
    fire:          fire,
    observe:       observe,
    stopObserving: stopObserving,
    on:            on
  });

  Element.addMethods({
    fire:          fire,

    observe:       observe,

    stopObserving: stopObserving,

    on:            on
  });

  Object.extend(document, {
    fire:          fire.methodize(),

    observe:       observe.methodize(),

    stopObserving: stopObserving.methodize(),

    on:            on.methodize(),

    loaded:        false
  });

  if (window.Event) Object.extend(window.Event, Event);
  else window.Event = Event;
})();

(function() {
  /* Support for the DOMContentLoaded event is based on work by Dan Webb,
     Matthias Miller, Dean Edwards, John Resig, and Diego Perini. */

  var timer;

  function fireContentLoadedEvent() {
    if (document.loaded) return;
    if (timer) window.clearTimeout(timer);
    document.loaded = true;
    document.fire('dom:loaded');
  }

  function checkReadyState() {
    if (document.readyState === 'complete') {
      document.stopObserving('readystatechange', checkReadyState);
      fireContentLoadedEvent();
    }
  }

  function pollDoScroll() {
    try { document.documentElement.doScroll('left'); }
    catch(e) {
      timer = pollDoScroll.defer();
      return;
    }
    fireContentLoadedEvent();
  }

  if (document.addEventListener) {
    document.addEventListener('DOMContentLoaded', fireContentLoadedEvent, false);
  } else {
    document.observe('readystatechange', checkReadyState);
    if (window == top)
      timer = pollDoScroll.defer();
  }

  Event.observe(window, 'load', fireContentLoadedEvent);
})();


Element.addMethods();
/*------------------------------- DEPRECATED -------------------------------*/

Hash.toQueryString = Object.toQueryString;

var Toggle = { display: Element.toggle };

Element.Methods.childOf = Element.Methods.descendantOf;

var Insertion = {
  Before: function(element, content) {
    return Element.insert(element, {before:content});
  },

  Top: function(element, content) {
    return Element.insert(element, {top:content});
  },

  Bottom: function(element, content) {
    return Element.insert(element, {bottom:content});
  },

  After: function(element, content) {
    return Element.insert(element, {after:content});
  }
};

var $continue = new Error('"throw $continue" is deprecated, use "return" instead');

var Position = {
  includeScrollOffsets: false,

  prepare: function() {
    this.deltaX =  window.pageXOffset
                || document.documentElement.scrollLeft
                || document.body.scrollLeft
                || 0;
    this.deltaY =  window.pageYOffset
                || document.documentElement.scrollTop
                || document.body.scrollTop
                || 0;
  },

  within: function(element, x, y) {
    if (this.includeScrollOffsets)
      return this.withinIncludingScrolloffsets(element, x, y);
    this.xcomp = x;
    this.ycomp = y;
    this.offset = Element.cumulativeOffset(element);

    return (y >= this.offset[1] &&
            y <  this.offset[1] + element.offsetHeight &&
            x >= this.offset[0] &&
            x <  this.offset[0] + element.offsetWidth);
  },

  withinIncludingScrolloffsets: function(element, x, y) {
    var offsetcache = Element.cumulativeScrollOffset(element);

    this.xcomp = x + offsetcache[0] - this.deltaX;
    this.ycomp = y + offsetcache[1] - this.deltaY;
    this.offset = Element.cumulativeOffset(element);

    return (this.ycomp >= this.offset[1] &&
            this.ycomp <  this.offset[1] + element.offsetHeight &&
            this.xcomp >= this.offset[0] &&
            this.xcomp <  this.offset[0] + element.offsetWidth);
  },

  overlap: function(mode, element) {
    if (!mode) return 0;
    if (mode == 'vertical')
      return ((this.offset[1] + element.offsetHeight) - this.ycomp) /
        element.offsetHeight;
    if (mode == 'horizontal')
      return ((this.offset[0] + element.offsetWidth) - this.xcomp) /
        element.offsetWidth;
  },


  cumulativeOffset: Element.Methods.cumulativeOffset,

  positionedOffset: Element.Methods.positionedOffset,

  absolutize: function(element) {
    Position.prepare();
    return Element.absolutize(element);
  },

  relativize: function(element) {
    Position.prepare();
    return Element.relativize(element);
  },

  realOffset: Element.Methods.cumulativeScrollOffset,

  offsetParent: Element.Methods.getOffsetParent,

  page: Element.Methods.viewportOffset,

  clone: function(source, target, options) {
    options = options || { };
    return Element.clonePosition(target, source, options);
  }
};

/*--------------------------------------------------------------------------*/

if (!document.getElementsByClassName) document.getElementsByClassName = function(instanceMethods){
  function iter(name) {
    return name.blank() ? null : "[contains(concat(' ', @class, ' '), ' " + name + " ')]";
  }

  instanceMethods.getElementsByClassName = Prototype.BrowserFeatures.XPath ?
  function(element, className) {
    className = className.toString().strip();
    var cond = /\s/.test(className) ? $w(className).map(iter).join('') : iter(className);
    return cond ? document._getElementsByXPath('.//*' + cond, element) : [];
  } : function(element, className) {
    className = className.toString().strip();
    var elements = [], classNames = (/\s/.test(className) ? $w(className) : null);
    if (!classNames && !className) return elements;

    var nodes = $(element).getElementsByTagName('*');
    className = ' ' + className + ' ';

    for (var i = 0, child, cn; child = nodes[i]; i++) {
      if (child.className && (cn = ' ' + child.className + ' ') && (cn.include(className) ||
          (classNames && classNames.all(function(name) {
            return !name.toString().blank() && cn.include(' ' + name + ' ');
          }))))
        elements.push(Element.extend(child));
    }
    return elements;
  };

  return function(className, parentElement) {
    return $(parentElement || document.body).getElementsByClassName(className);
  };
}(Element.Methods);

/*--------------------------------------------------------------------------*/

Element.ClassNames = Class.create();
Element.ClassNames.prototype = {
  initialize: function(element) {
    this.element = $(element);
  },

  _each: function(iterator) {
    this.element.className.split(/\s+/).select(function(name) {
      return name.length > 0;
    })._each(iterator);
  },

  set: function(className) {
    this.element.className = className;
  },

  add: function(classNameToAdd) {
    if (this.include(classNameToAdd)) return;
    this.set($A(this).concat(classNameToAdd).join(' '));
  },

  remove: function(classNameToRemove) {
    if (!this.include(classNameToRemove)) return;
    this.set($A(this).without(classNameToRemove).join(' '));
  },

  toString: function() {
    return $A(this).join(' ');
  }
};

Object.extend(Element.ClassNames.prototype, Enumerable);

/*--------------------------------------------------------------------------*/

(function() {
  window.Selector = Class.create({
    initialize: function(expression) {
      this.expression = expression.strip();
    },

    findElements: function(rootElement) {
      return Prototype.Selector.select(this.expression, rootElement);
    },

    match: function(element) {
      return Prototype.Selector.match(element, this.expression);
    },

    toString: function() {
      return this.expression;
    },

    inspect: function() {
      return "#<Selector: " + this.expression + ">";
    }
  });

  Object.extend(Selector, {
    matchElements: function(elements, expression) {
      var match = Prototype.Selector.match,
          results = [];

      for (var i = 0, length = elements.length; i < length; i++) {
        var element = elements[i];
        if (match(element, expression)) {
          results.push(Element.extend(element));
        }
      }
      return results;
    },

    findElement: function(elements, expression, index) {
      index = index || 0;
      var matchIndex = 0, element;
      for (var i = 0, length = elements.length; i < length; i++) {
        element = elements[i];
        if (Prototype.Selector.match(element, expression) && index === matchIndex++) {
          return Element.extend(element);
        }
      }
    },

    findChildElements: function(element, expressions) {
      var selector = expressions.toArray().join(', ');
      return Prototype.Selector.select(selector, element || document);
    }
  });
})();
// script.aculo.us effects.js v1.9.0, Thu Dec 23 16:54:48 -0500 2010

// Copyright (c) 2005-2010 Thomas Fuchs (http://script.aculo.us, http://mir.aculo.us)
// Contributors:
//  Justin Palmer (http://encytemedia.com/)
//  Mark Pilgrim (http://diveintomark.org/)
//  Martin Bialasinki
//
// script.aculo.us is freely distributable under the terms of an MIT-style license.
// For details, see the script.aculo.us web site: http://script.aculo.us/

// converts rgb() and #xxx to #xxxxxx format,
// returns self (or first argument) if not convertable
String.prototype.parseColor = function() {
  var color = '#';
  if (this.slice(0,4) == 'rgb(') {
    var cols = this.slice(4,this.length-1).split(',');
    var i=0; do { color += parseInt(cols[i]).toColorPart() } while (++i<3);
  } else {
    if (this.slice(0,1) == '#') {
      if (this.length==4) for(var i=1;i<4;i++) color += (this.charAt(i) + this.charAt(i)).toLowerCase();
      if (this.length==7) color = this.toLowerCase();
    }
  }
  return (color.length==7 ? color : (arguments[0] || this));
};

/*--------------------------------------------------------------------------*/

Element.collectTextNodes = function(element) {
  return $A($(element).childNodes).collect( function(node) {
    return (node.nodeType==3 ? node.nodeValue :
      (node.hasChildNodes() ? Element.collectTextNodes(node) : ''));
  }).flatten().join('');
};

Element.collectTextNodesIgnoreClass = function(element, className) {
  return $A($(element).childNodes).collect( function(node) {
    return (node.nodeType==3 ? node.nodeValue :
      ((node.hasChildNodes() && !Element.hasClassName(node,className)) ?
        Element.collectTextNodesIgnoreClass(node, className) : ''));
  }).flatten().join('');
};

Element.setContentZoom = function(element, percent) {
  element = $(element);
  element.setStyle({fontSize: (percent/100) + 'em'});
  if (Prototype.Browser.WebKit) window.scrollBy(0,0);
  return element;
};

Element.getInlineOpacity = function(element){
  return $(element).style.opacity || '';
};

Element.forceRerendering = function(element) {
  try {
    element = $(element);
    var n = document.createTextNode(' ');
    element.appendChild(n);
    element.removeChild(n);
  } catch(e) { }
};

/*--------------------------------------------------------------------------*/

var Effect = {
  _elementDoesNotExistError: {
    name: 'ElementDoesNotExistError',
    message: 'The specified DOM element does not exist, but is required for this effect to operate'
  },
  Transitions: {
    linear: Prototype.K,
    sinoidal: function(pos) {
      return (-Math.cos(pos*Math.PI)/2) + .5;
    },
    reverse: function(pos) {
      return 1-pos;
    },
    flicker: function(pos) {
      var pos = ((-Math.cos(pos*Math.PI)/4) + .75) + Math.random()/4;
      return pos > 1 ? 1 : pos;
    },
    wobble: function(pos) {
      return (-Math.cos(pos*Math.PI*(9*pos))/2) + .5;
    },
    pulse: function(pos, pulses) {
      return (-Math.cos((pos*((pulses||5)-.5)*2)*Math.PI)/2) + .5;
    },
    spring: function(pos) {
      return 1 - (Math.cos(pos * 4.5 * Math.PI) * Math.exp(-pos * 6));
    },
    none: function(pos) {
      return 0;
    },
    full: function(pos) {
      return 1;
    }
  },
  DefaultOptions: {
    duration:   1.0,   // seconds
    fps:        100,   // 100= assume 66fps max.
    sync:       false, // true for combining
    from:       0.0,
    to:         1.0,
    delay:      0.0,
    queue:      'parallel'
  },
  tagifyText: function(element) {
    var tagifyStyle = 'position:relative';
    if (Prototype.Browser.IE) tagifyStyle += ';zoom:1';

    element = $(element);
    $A(element.childNodes).each( function(child) {
      if (child.nodeType==3) {
        child.nodeValue.toArray().each( function(character) {
          element.insertBefore(
            new Element('span', {style: tagifyStyle}).update(
              character == ' ' ? String.fromCharCode(160) : character),
              child);
        });
        Element.remove(child);
      }
    });
  },
  multiple: function(element, effect) {
    var elements;
    if (((typeof element == 'object') ||
        Object.isFunction(element)) &&
       (element.length))
      elements = element;
    else
      elements = $(element).childNodes;

    var options = Object.extend({
      speed: 0.1,
      delay: 0.0
    }, arguments[2] || { });
    var masterDelay = options.delay;

    $A(elements).each( function(element, index) {
      new effect(element, Object.extend(options, { delay: index * options.speed + masterDelay }));
    });
  },
  PAIRS: {
    'slide':  ['SlideDown','SlideUp'],
    'blind':  ['BlindDown','BlindUp'],
    'appear': ['Appear','Fade']
  },
  toggle: function(element, effect, options) {
    element = $(element);
    effect  = (effect || 'appear').toLowerCase();
    
    return Effect[ Effect.PAIRS[ effect ][ element.visible() ? 1 : 0 ] ](element, Object.extend({
      queue: { position:'end', scope:(element.id || 'global'), limit: 1 }
    }, options || {}));
  }
};

Effect.DefaultOptions.transition = Effect.Transitions.sinoidal;

/* ------------- core effects ------------- */

Effect.ScopedQueue = Class.create(Enumerable, {
  initialize: function() {
    this.effects  = [];
    this.interval = null;
  },
  _each: function(iterator) {
    this.effects._each(iterator);
  },
  add: function(effect) {
    var timestamp = new Date().getTime();

    var position = Object.isString(effect.options.queue) ?
      effect.options.queue : effect.options.queue.position;

    switch(position) {
      case 'front':
        // move unstarted effects after this effect
        this.effects.findAll(function(e){ return e.state=='idle' }).each( function(e) {
            e.startOn  += effect.finishOn;
            e.finishOn += effect.finishOn;
          });
        break;
      case 'with-last':
        timestamp = this.effects.pluck('startOn').max() || timestamp;
        break;
      case 'end':
        // start effect after last queued effect has finished
        timestamp = this.effects.pluck('finishOn').max() || timestamp;
        break;
    }

    effect.startOn  += timestamp;
    effect.finishOn += timestamp;

    if (!effect.options.queue.limit || (this.effects.length < effect.options.queue.limit))
      this.effects.push(effect);

    if (!this.interval)
      this.interval = setInterval(this.loop.bind(this), 15);
  },
  remove: function(effect) {
    this.effects = this.effects.reject(function(e) { return e==effect });
    if (this.effects.length == 0) {
      clearInterval(this.interval);
      this.interval = null;
    }
  },
  loop: function() {
    var timePos = new Date().getTime();
    for(var i=0, len=this.effects.length;i<len;i++)
      this.effects[i] && this.effects[i].loop(timePos);
  }
});

Effect.Queues = {
  instances: $H(),
  get: function(queueName) {
    if (!Object.isString(queueName)) return queueName;

    return this.instances.get(queueName) ||
      this.instances.set(queueName, new Effect.ScopedQueue());
  }
};
Effect.Queue = Effect.Queues.get('global');

Effect.Base = Class.create({
  position: null,
  start: function(options) {
    if (options && options.transition === false) options.transition = Effect.Transitions.linear;
    this.options      = Object.extend(Object.extend({ },Effect.DefaultOptions), options || { });
    this.currentFrame = 0;
    this.state        = 'idle';
    this.startOn      = this.options.delay*1000;
    this.finishOn     = this.startOn+(this.options.duration*1000);
    this.fromToDelta  = this.options.to-this.options.from;
    this.totalTime    = this.finishOn-this.startOn;
    this.totalFrames  = this.options.fps*this.options.duration;

    this.render = (function() {
      function dispatch(effect, eventName) {
        if (effect.options[eventName + 'Internal'])
          effect.options[eventName + 'Internal'](effect);
        if (effect.options[eventName])
          effect.options[eventName](effect);
      }

      return function(pos) {
        if (this.state === "idle") {
          this.state = "running";
          dispatch(this, 'beforeSetup');
          if (this.setup) this.setup();
          dispatch(this, 'afterSetup');
        }
        if (this.state === "running") {
          pos = (this.options.transition(pos) * this.fromToDelta) + this.options.from;
          this.position = pos;
          dispatch(this, 'beforeUpdate');
          if (this.update) this.update(pos);
          dispatch(this, 'afterUpdate');
        }
      };
    })();

    this.event('beforeStart');
    if (!this.options.sync)
      Effect.Queues.get(Object.isString(this.options.queue) ?
        'global' : this.options.queue.scope).add(this);
  },
  loop: function(timePos) {
    if (timePos >= this.startOn) {
      if (timePos >= this.finishOn) {
        this.render(1.0);
        this.cancel();
        this.event('beforeFinish');
        if (this.finish) this.finish();
        this.event('afterFinish');
        return;
      }
      var pos   = (timePos - this.startOn) / this.totalTime,
          frame = (pos * this.totalFrames).round();
      if (frame > this.currentFrame) {
        this.render(pos);
        this.currentFrame = frame;
      }
    }
  },
  cancel: function() {
    if (!this.options.sync)
      Effect.Queues.get(Object.isString(this.options.queue) ?
        'global' : this.options.queue.scope).remove(this);
    this.state = 'finished';
  },
  event: function(eventName) {
    if (this.options[eventName + 'Internal']) this.options[eventName + 'Internal'](this);
    if (this.options[eventName]) this.options[eventName](this);
  },
  inspect: function() {
    var data = $H();
    for(property in this)
      if (!Object.isFunction(this[property])) data.set(property, this[property]);
    return '#<Effect:' + data.inspect() + ',options:' + $H(this.options).inspect() + '>';
  }
});

Effect.Parallel = Class.create(Effect.Base, {
  initialize: function(effects) {
    this.effects = effects || [];
    this.start(arguments[1]);
  },
  update: function(position) {
    this.effects.invoke('render', position);
  },
  finish: function(position) {
    this.effects.each( function(effect) {
      effect.render(1.0);
      effect.cancel();
      effect.event('beforeFinish');
      if (effect.finish) effect.finish(position);
      effect.event('afterFinish');
    });
  }
});

Effect.Tween = Class.create(Effect.Base, {
  initialize: function(object, from, to) {
    object = Object.isString(object) ? $(object) : object;
    var args = $A(arguments), method = args.last(),
      options = args.length == 5 ? args[3] : null;
    this.method = Object.isFunction(method) ? method.bind(object) :
      Object.isFunction(object[method]) ? object[method].bind(object) :
      function(value) { object[method] = value };
    this.start(Object.extend({ from: from, to: to }, options || { }));
  },
  update: function(position) {
    this.method(position);
  }
});

Effect.Event = Class.create(Effect.Base, {
  initialize: function() {
    this.start(Object.extend({ duration: 0 }, arguments[0] || { }));
  },
  update: Prototype.emptyFunction
});

Effect.Opacity = Class.create(Effect.Base, {
  initialize: function(element) {
    this.element = $(element);
    if (!this.element) throw(Effect._elementDoesNotExistError);
    // make this work on IE on elements without 'layout'
    if (Prototype.Browser.IE && (!this.element.currentStyle.hasLayout))
      this.element.setStyle({zoom: 1});
    var options = Object.extend({
      from: this.element.getOpacity() || 0.0,
      to:   1.0
    }, arguments[1] || { });
    this.start(options);
  },
  update: function(position) {
    this.element.setOpacity(position);
  }
});

Effect.Move = Class.create(Effect.Base, {
  initialize: function(element) {
    this.element = $(element);
    if (!this.element) throw(Effect._elementDoesNotExistError);
    var options = Object.extend({
      x:    0,
      y:    0,
      mode: 'relative'
    }, arguments[1] || { });
    this.start(options);
  },
  setup: function() {
    this.element.makePositioned();
    this.originalLeft = parseFloat(this.element.getStyle('left') || '0');
    this.originalTop  = parseFloat(this.element.getStyle('top')  || '0');
    if (this.options.mode == 'absolute') {
      this.options.x = this.options.x - this.originalLeft;
      this.options.y = this.options.y - this.originalTop;
    }
  },
  update: function(position) {
    this.element.setStyle({
      left: (this.options.x  * position + this.originalLeft).round() + 'px',
      top:  (this.options.y  * position + this.originalTop).round()  + 'px'
    });
  }
});

// for backwards compatibility
Effect.MoveBy = function(element, toTop, toLeft) {
  return new Effect.Move(element,
    Object.extend({ x: toLeft, y: toTop }, arguments[3] || { }));
};

Effect.Scale = Class.create(Effect.Base, {
  initialize: function(element, percent) {
    this.element = $(element);
    if (!this.element) throw(Effect._elementDoesNotExistError);
    var options = Object.extend({
      scaleX: true,
      scaleY: true,
      scaleContent: true,
      scaleFromCenter: false,
      scaleMode: 'box',        // 'box' or 'contents' or { } with provided values
      scaleFrom: 100.0,
      scaleTo:   percent
    }, arguments[2] || { });
    this.start(options);
  },
  setup: function() {
    this.restoreAfterFinish = this.options.restoreAfterFinish || false;
    this.elementPositioning = this.element.getStyle('position');

    this.originalStyle = { };
    ['top','left','width','height','fontSize'].each( function(k) {
      this.originalStyle[k] = this.element.style[k];
    }.bind(this));

    this.originalTop  = this.element.offsetTop;
    this.originalLeft = this.element.offsetLeft;

    var fontSize = this.element.getStyle('font-size') || '100%';
    ['em','px','%','pt'].each( function(fontSizeType) {
      if (fontSize.indexOf(fontSizeType)>0) {
        this.fontSize     = parseFloat(fontSize);
        this.fontSizeType = fontSizeType;
      }
    }.bind(this));

    this.factor = (this.options.scaleTo - this.options.scaleFrom)/100;

    this.dims = null;
    if (this.options.scaleMode=='box')
      this.dims = [this.element.offsetHeight, this.element.offsetWidth];
    if (/^content/.test(this.options.scaleMode))
      this.dims = [this.element.scrollHeight, this.element.scrollWidth];
    if (!this.dims)
      this.dims = [this.options.scaleMode.originalHeight,
                   this.options.scaleMode.originalWidth];
  },
  update: function(position) {
    var currentScale = (this.options.scaleFrom/100.0) + (this.factor * position);
    if (this.options.scaleContent && this.fontSize)
      this.element.setStyle({fontSize: this.fontSize * currentScale + this.fontSizeType });
    this.setDimensions(this.dims[0] * currentScale, this.dims[1] * currentScale);
  },
  finish: function(position) {
    if (this.restoreAfterFinish) this.element.setStyle(this.originalStyle);
  },
  setDimensions: function(height, width) {
    var d = { };
    if (this.options.scaleX) d.width = width.round() + 'px';
    if (this.options.scaleY) d.height = height.round() + 'px';
    if (this.options.scaleFromCenter) {
      var topd  = (height - this.dims[0])/2;
      var leftd = (width  - this.dims[1])/2;
      if (this.elementPositioning == 'absolute') {
        if (this.options.scaleY) d.top = this.originalTop-topd + 'px';
        if (this.options.scaleX) d.left = this.originalLeft-leftd + 'px';
      } else {
        if (this.options.scaleY) d.top = -topd + 'px';
        if (this.options.scaleX) d.left = -leftd + 'px';
      }
    }
    this.element.setStyle(d);
  }
});

Effect.Highlight = Class.create(Effect.Base, {
  initialize: function(element) {
    this.element = $(element);
    if (!this.element) throw(Effect._elementDoesNotExistError);
    var options = Object.extend({ startcolor: '#ffff99' }, arguments[1] || { });
    this.start(options);
  },
  setup: function() {
    // Prevent executing on elements not in the layout flow
    if (this.element.getStyle('display')=='none') { this.cancel(); return; }
    // Disable background image during the effect
    this.oldStyle = { };
    if (!this.options.keepBackgroundImage) {
      this.oldStyle.backgroundImage = this.element.getStyle('background-image');
      this.element.setStyle({backgroundImage: 'none'});
    }
    if (!this.options.endcolor)
      this.options.endcolor = this.element.getStyle('background-color').parseColor('#ffffff');
    if (!this.options.restorecolor)
      this.options.restorecolor = this.element.getStyle('background-color');
    // init color calculations
    this._base  = $R(0,2).map(function(i){ return parseInt(this.options.startcolor.slice(i*2+1,i*2+3),16) }.bind(this));
    this._delta = $R(0,2).map(function(i){ return parseInt(this.options.endcolor.slice(i*2+1,i*2+3),16)-this._base[i] }.bind(this));
  },
  update: function(position) {
    this.element.setStyle({backgroundColor: $R(0,2).inject('#',function(m,v,i){
      return m+((this._base[i]+(this._delta[i]*position)).round().toColorPart()); }.bind(this)) });
  },
  finish: function() {
    this.element.setStyle(Object.extend(this.oldStyle, {
      backgroundColor: this.options.restorecolor
    }));
  }
});

Effect.ScrollTo = function(element) {
  var options = arguments[1] || { },
  scrollOffsets = document.viewport.getScrollOffsets(),
  elementOffsets = $(element).cumulativeOffset();

  if (options.offset) elementOffsets[1] += options.offset;

  return new Effect.Tween(null,
    scrollOffsets.top,
    elementOffsets[1],
    options,
    function(p){ scrollTo(scrollOffsets.left, p.round()); }
  );
};

/* ------------- combination effects ------------- */

Effect.Fade = function(element) {
  element = $(element);
  var oldOpacity = element.getInlineOpacity();
  var options = Object.extend({
    from: element.getOpacity() || 1.0,
    to:   0.0,
    afterFinishInternal: function(effect) {
      if (effect.options.to!=0) return;
      effect.element.hide().setStyle({opacity: oldOpacity});
    }
  }, arguments[1] || { });
  return new Effect.Opacity(element,options);
};

Effect.Appear = function(element) {
  element = $(element);
  var options = Object.extend({
  from: (element.getStyle('display') == 'none' ? 0.0 : element.getOpacity() || 0.0),
  to:   1.0,
  // force Safari to render floated elements properly
  afterFinishInternal: function(effect) {
    effect.element.forceRerendering();
  },
  beforeSetup: function(effect) {
    effect.element.setOpacity(effect.options.from).show();
  }}, arguments[1] || { });
  return new Effect.Opacity(element,options);
};

Effect.Puff = function(element) {
  element = $(element);
  var oldStyle = {
    opacity: element.getInlineOpacity(),
    position: element.getStyle('position'),
    top:  element.style.top,
    left: element.style.left,
    width: element.style.width,
    height: element.style.height
  };
  return new Effect.Parallel(
   [ new Effect.Scale(element, 200,
      { sync: true, scaleFromCenter: true, scaleContent: true, restoreAfterFinish: true }),
     new Effect.Opacity(element, { sync: true, to: 0.0 } ) ],
     Object.extend({ duration: 1.0,
      beforeSetupInternal: function(effect) {
        Position.absolutize(effect.effects[0].element);
      },
      afterFinishInternal: function(effect) {
         effect.effects[0].element.hide().setStyle(oldStyle); }
     }, arguments[1] || { })
   );
};

Effect.BlindUp = function(element) {
  element = $(element);
  element.makeClipping();
  return new Effect.Scale(element, 0,
    Object.extend({ scaleContent: false,
      scaleX: false,
      restoreAfterFinish: true,
      afterFinishInternal: function(effect) {
        effect.element.hide().undoClipping();
      }
    }, arguments[1] || { })
  );
};

Effect.BlindDown = function(element) {
  element = $(element);
  var elementDimensions = element.getDimensions();
  return new Effect.Scale(element, 100, Object.extend({
    scaleContent: false,
    scaleX: false,
    scaleFrom: 0,
    scaleMode: {originalHeight: elementDimensions.height, originalWidth: elementDimensions.width},
    restoreAfterFinish: true,
    afterSetup: function(effect) {
      effect.element.makeClipping().setStyle({height: '0px'}).show();
    },
    afterFinishInternal: function(effect) {
      effect.element.undoClipping();
    }
  }, arguments[1] || { }));
};

Effect.SwitchOff = function(element) {
  element = $(element);
  var oldOpacity = element.getInlineOpacity();
  return new Effect.Appear(element, Object.extend({
    duration: 0.4,
    from: 0,
    transition: Effect.Transitions.flicker,
    afterFinishInternal: function(effect) {
      new Effect.Scale(effect.element, 1, {
        duration: 0.3, scaleFromCenter: true,
        scaleX: false, scaleContent: false, restoreAfterFinish: true,
        beforeSetup: function(effect) {
          effect.element.makePositioned().makeClipping();
        },
        afterFinishInternal: function(effect) {
          effect.element.hide().undoClipping().undoPositioned().setStyle({opacity: oldOpacity});
        }
      });
    }
  }, arguments[1] || { }));
};

Effect.DropOut = function(element) {
  element = $(element);
  var oldStyle = {
    top: element.getStyle('top'),
    left: element.getStyle('left'),
    opacity: element.getInlineOpacity() };
  return new Effect.Parallel(
    [ new Effect.Move(element, {x: 0, y: 100, sync: true }),
      new Effect.Opacity(element, { sync: true, to: 0.0 }) ],
    Object.extend(
      { duration: 0.5,
        beforeSetup: function(effect) {
          effect.effects[0].element.makePositioned();
        },
        afterFinishInternal: function(effect) {
          effect.effects[0].element.hide().undoPositioned().setStyle(oldStyle);
        }
      }, arguments[1] || { }));
};

Effect.Shake = function(element) {
  element = $(element);
  var options = Object.extend({
    distance: 20,
    duration: 0.5
  }, arguments[1] || {});
  var distance = parseFloat(options.distance);
  var split = parseFloat(options.duration) / 10.0;
  var oldStyle = {
    top: element.getStyle('top'),
    left: element.getStyle('left') };
    return new Effect.Move(element,
      { x:  distance, y: 0, duration: split, afterFinishInternal: function(effect) {
    new Effect.Move(effect.element,
      { x: -distance*2, y: 0, duration: split*2,  afterFinishInternal: function(effect) {
    new Effect.Move(effect.element,
      { x:  distance*2, y: 0, duration: split*2,  afterFinishInternal: function(effect) {
    new Effect.Move(effect.element,
      { x: -distance*2, y: 0, duration: split*2,  afterFinishInternal: function(effect) {
    new Effect.Move(effect.element,
      { x:  distance*2, y: 0, duration: split*2,  afterFinishInternal: function(effect) {
    new Effect.Move(effect.element,
      { x: -distance, y: 0, duration: split, afterFinishInternal: function(effect) {
        effect.element.undoPositioned().setStyle(oldStyle);
  }}); }}); }}); }}); }}); }});
};

Effect.SlideDown = function(element) {
  element = $(element).cleanWhitespace();
  // SlideDown need to have the content of the element wrapped in a container element with fixed height!
  var oldInnerBottom = element.down().getStyle('bottom');
  var elementDimensions = element.getDimensions();
  return new Effect.Scale(element, 100, Object.extend({
    scaleContent: false,
    scaleX: false,
    scaleFrom: window.opera ? 0 : 1,
    scaleMode: {originalHeight: elementDimensions.height, originalWidth: elementDimensions.width},
    restoreAfterFinish: true,
    afterSetup: function(effect) {
      effect.element.makePositioned();
      effect.element.down().makePositioned();
      if (window.opera) effect.element.setStyle({top: ''});
      effect.element.makeClipping().setStyle({height: '0px'}).show();
    },
    afterUpdateInternal: function(effect) {
      effect.element.down().setStyle({bottom:
        (effect.dims[0] - effect.element.clientHeight) + 'px' });
    },
    afterFinishInternal: function(effect) {
      effect.element.undoClipping().undoPositioned();
      effect.element.down().undoPositioned().setStyle({bottom: oldInnerBottom}); }
    }, arguments[1] || { })
  );
};

Effect.SlideUp = function(element) {
  element = $(element).cleanWhitespace();
  var oldInnerBottom = element.down().getStyle('bottom');
  var elementDimensions = element.getDimensions();
  return new Effect.Scale(element, window.opera ? 0 : 1,
   Object.extend({ scaleContent: false,
    scaleX: false,
    scaleMode: 'box',
    scaleFrom: 100,
    scaleMode: {originalHeight: elementDimensions.height, originalWidth: elementDimensions.width},
    restoreAfterFinish: true,
    afterSetup: function(effect) {
      effect.element.makePositioned();
      effect.element.down().makePositioned();
      if (window.opera) effect.element.setStyle({top: ''});
      effect.element.makeClipping().show();
    },
    afterUpdateInternal: function(effect) {
      effect.element.down().setStyle({bottom:
        (effect.dims[0] - effect.element.clientHeight) + 'px' });
    },
    afterFinishInternal: function(effect) {
      effect.element.hide().undoClipping().undoPositioned();
      effect.element.down().undoPositioned().setStyle({bottom: oldInnerBottom});
    }
   }, arguments[1] || { })
  );
};

// Bug in opera makes the TD containing this element expand for a instance after finish
Effect.Squish = function(element) {
  return new Effect.Scale(element, window.opera ? 1 : 0, {
    restoreAfterFinish: true,
    beforeSetup: function(effect) {
      effect.element.makeClipping();
    },
    afterFinishInternal: function(effect) {
      effect.element.hide().undoClipping();
    }
  });
};

Effect.Grow = function(element) {
  element = $(element);
  var options = Object.extend({
    direction: 'center',
    moveTransition: Effect.Transitions.sinoidal,
    scaleTransition: Effect.Transitions.sinoidal,
    opacityTransition: Effect.Transitions.full
  }, arguments[1] || { });
  var oldStyle = {
    top: element.style.top,
    left: element.style.left,
    height: element.style.height,
    width: element.style.width,
    opacity: element.getInlineOpacity() };

  var dims = element.getDimensions();
  var initialMoveX, initialMoveY;
  var moveX, moveY;

  switch (options.direction) {
    case 'top-left':
      initialMoveX = initialMoveY = moveX = moveY = 0;
      break;
    case 'top-right':
      initialMoveX = dims.width;
      initialMoveY = moveY = 0;
      moveX = -dims.width;
      break;
    case 'bottom-left':
      initialMoveX = moveX = 0;
      initialMoveY = dims.height;
      moveY = -dims.height;
      break;
    case 'bottom-right':
      initialMoveX = dims.width;
      initialMoveY = dims.height;
      moveX = -dims.width;
      moveY = -dims.height;
      break;
    case 'center':
      initialMoveX = dims.width / 2;
      initialMoveY = dims.height / 2;
      moveX = -dims.width / 2;
      moveY = -dims.height / 2;
      break;
  }

  return new Effect.Move(element, {
    x: initialMoveX,
    y: initialMoveY,
    duration: 0.01,
    beforeSetup: function(effect) {
      effect.element.hide().makeClipping().makePositioned();
    },
    afterFinishInternal: function(effect) {
      new Effect.Parallel(
        [ new Effect.Opacity(effect.element, { sync: true, to: 1.0, from: 0.0, transition: options.opacityTransition }),
          new Effect.Move(effect.element, { x: moveX, y: moveY, sync: true, transition: options.moveTransition }),
          new Effect.Scale(effect.element, 100, {
            scaleMode: { originalHeight: dims.height, originalWidth: dims.width },
            sync: true, scaleFrom: window.opera ? 1 : 0, transition: options.scaleTransition, restoreAfterFinish: true})
        ], Object.extend({
             beforeSetup: function(effect) {
               effect.effects[0].element.setStyle({height: '0px'}).show();
             },
             afterFinishInternal: function(effect) {
               effect.effects[0].element.undoClipping().undoPositioned().setStyle(oldStyle);
             }
           }, options)
      );
    }
  });
};

Effect.Shrink = function(element) {
  element = $(element);
  var options = Object.extend({
    direction: 'center',
    moveTransition: Effect.Transitions.sinoidal,
    scaleTransition: Effect.Transitions.sinoidal,
    opacityTransition: Effect.Transitions.none
  }, arguments[1] || { });
  var oldStyle = {
    top: element.style.top,
    left: element.style.left,
    height: element.style.height,
    width: element.style.width,
    opacity: element.getInlineOpacity() };

  var dims = element.getDimensions();
  var moveX, moveY;

  switch (options.direction) {
    case 'top-left':
      moveX = moveY = 0;
      break;
    case 'top-right':
      moveX = dims.width;
      moveY = 0;
      break;
    case 'bottom-left':
      moveX = 0;
      moveY = dims.height;
      break;
    case 'bottom-right':
      moveX = dims.width;
      moveY = dims.height;
      break;
    case 'center':
      moveX = dims.width / 2;
      moveY = dims.height / 2;
      break;
  }

  return new Effect.Parallel(
    [ new Effect.Opacity(element, { sync: true, to: 0.0, from: 1.0, transition: options.opacityTransition }),
      new Effect.Scale(element, window.opera ? 1 : 0, { sync: true, transition: options.scaleTransition, restoreAfterFinish: true}),
      new Effect.Move(element, { x: moveX, y: moveY, sync: true, transition: options.moveTransition })
    ], Object.extend({
         beforeStartInternal: function(effect) {
           effect.effects[0].element.makePositioned().makeClipping();
         },
         afterFinishInternal: function(effect) {
           effect.effects[0].element.hide().undoClipping().undoPositioned().setStyle(oldStyle); }
       }, options)
  );
};

Effect.Pulsate = function(element) {
  element = $(element);
  var options    = arguments[1] || { },
    oldOpacity = element.getInlineOpacity(),
    transition = options.transition || Effect.Transitions.linear,
    reverser   = function(pos){
      return 1 - transition((-Math.cos((pos*(options.pulses||5)*2)*Math.PI)/2) + .5);
    };

  return new Effect.Opacity(element,
    Object.extend(Object.extend({  duration: 2.0, from: 0,
      afterFinishInternal: function(effect) { effect.element.setStyle({opacity: oldOpacity}); }
    }, options), {transition: reverser}));
};

Effect.Fold = function(element) {
  element = $(element);
  var oldStyle = {
    top: element.style.top,
    left: element.style.left,
    width: element.style.width,
    height: element.style.height };
  element.makeClipping();
  return new Effect.Scale(element, 5, Object.extend({
    scaleContent: false,
    scaleX: false,
    afterFinishInternal: function(effect) {
    new Effect.Scale(element, 1, {
      scaleContent: false,
      scaleY: false,
      afterFinishInternal: function(effect) {
        effect.element.hide().undoClipping().setStyle(oldStyle);
      } });
  }}, arguments[1] || { }));
};

Effect.Morph = Class.create(Effect.Base, {
  initialize: function(element) {
    this.element = $(element);
    if (!this.element) throw(Effect._elementDoesNotExistError);
    var options = Object.extend({
      style: { }
    }, arguments[1] || { });

    if (!Object.isString(options.style)) this.style = $H(options.style);
    else {
      if (options.style.include(':'))
        this.style = options.style.parseStyle();
      else {
        this.element.addClassName(options.style);
        this.style = $H(this.element.getStyles());
        this.element.removeClassName(options.style);
        var css = this.element.getStyles();
        this.style = this.style.reject(function(style) {
          return style.value == css[style.key];
        });
        options.afterFinishInternal = function(effect) {
          effect.element.addClassName(effect.options.style);
          effect.transforms.each(function(transform) {
            effect.element.style[transform.style] = '';
          });
        };
      }
    }
    this.start(options);
  },

  setup: function(){
    function parseColor(color){
      if (!color || ['rgba(0, 0, 0, 0)','transparent'].include(color)) color = '#ffffff';
      color = color.parseColor();
      return $R(0,2).map(function(i){
        return parseInt( color.slice(i*2+1,i*2+3), 16 );
      });
    }
    this.transforms = this.style.map(function(pair){
      var property = pair[0], value = pair[1], unit = null;

      if (value.parseColor('#zzzzzz') != '#zzzzzz') {
        value = value.parseColor();
        unit  = 'color';
      } else if (property == 'opacity') {
        value = parseFloat(value);
        if (Prototype.Browser.IE && (!this.element.currentStyle.hasLayout))
          this.element.setStyle({zoom: 1});
      } else if (Element.CSS_LENGTH.test(value)) {
          var components = value.match(/^([\+\-]?[0-9\.]+)(.*)$/);
          value = parseFloat(components[1]);
          unit = (components.length == 3) ? components[2] : null;
      }

      var originalValue = this.element.getStyle(property);
      return {
        style: property.camelize(),
        originalValue: unit=='color' ? parseColor(originalValue) : parseFloat(originalValue || 0),
        targetValue: unit=='color' ? parseColor(value) : value,
        unit: unit
      };
    }.bind(this)).reject(function(transform){
      return (
        (transform.originalValue == transform.targetValue) ||
        (
          transform.unit != 'color' &&
          (isNaN(transform.originalValue) || isNaN(transform.targetValue))
        )
      );
    });
  },
  update: function(position) {
    var style = { }, transform, i = this.transforms.length;
    while(i--)
      style[(transform = this.transforms[i]).style] =
        transform.unit=='color' ? '#'+
          (Math.round(transform.originalValue[0]+
            (transform.targetValue[0]-transform.originalValue[0])*position)).toColorPart() +
          (Math.round(transform.originalValue[1]+
            (transform.targetValue[1]-transform.originalValue[1])*position)).toColorPart() +
          (Math.round(transform.originalValue[2]+
            (transform.targetValue[2]-transform.originalValue[2])*position)).toColorPart() :
        (transform.originalValue +
          (transform.targetValue - transform.originalValue) * position).toFixed(3) +
            (transform.unit === null ? '' : transform.unit);
    this.element.setStyle(style, true);
  }
});

Effect.Transform = Class.create({
  initialize: function(tracks){
    this.tracks  = [];
    this.options = arguments[1] || { };
    this.addTracks(tracks);
  },
  addTracks: function(tracks){
    tracks.each(function(track){
      track = $H(track);
      var data = track.values().first();
      this.tracks.push($H({
        ids:     track.keys().first(),
        effect:  Effect.Morph,
        options: { style: data }
      }));
    }.bind(this));
    return this;
  },
  play: function(){
    return new Effect.Parallel(
      this.tracks.map(function(track){
        var ids = track.get('ids'), effect = track.get('effect'), options = track.get('options');
        var elements = [$(ids) || $$(ids)].flatten();
        return elements.map(function(e){ return new effect(e, Object.extend({ sync:true }, options)) });
      }).flatten(),
      this.options
    );
  }
});

Element.CSS_PROPERTIES = $w(
  'backgroundColor backgroundPosition borderBottomColor borderBottomStyle ' +
  'borderBottomWidth borderLeftColor borderLeftStyle borderLeftWidth ' +
  'borderRightColor borderRightStyle borderRightWidth borderSpacing ' +
  'borderTopColor borderTopStyle borderTopWidth bottom clip color ' +
  'fontSize fontWeight height left letterSpacing lineHeight ' +
  'marginBottom marginLeft marginRight marginTop markerOffset maxHeight '+
  'maxWidth minHeight minWidth opacity outlineColor outlineOffset ' +
  'outlineWidth paddingBottom paddingLeft paddingRight paddingTop ' +
  'right textIndent top width wordSpacing zIndex');

Element.CSS_LENGTH = /^(([\+\-]?[0-9\.]+)(em|ex|px|in|cm|mm|pt|pc|\%))|0$/;

String.__parseStyleElement = document.createElement('div');
String.prototype.parseStyle = function(){
  var style, styleRules = $H();
  if (Prototype.Browser.WebKit)
    style = new Element('div',{style:this}).style;
  else {
    String.__parseStyleElement.innerHTML = '<div style="' + this + '"></div>';
    style = String.__parseStyleElement.childNodes[0].style;
  }

  Element.CSS_PROPERTIES.each(function(property){
    if (style[property]) styleRules.set(property, style[property]);
  });

  if (Prototype.Browser.IE && this.include('opacity'))
    styleRules.set('opacity', this.match(/opacity:\s*((?:0|1)?(?:\.\d*)?)/)[1]);

  return styleRules;
};

if (document.defaultView && document.defaultView.getComputedStyle) {
  Element.getStyles = function(element) {
    var css = document.defaultView.getComputedStyle($(element), null);
    return Element.CSS_PROPERTIES.inject({ }, function(styles, property) {
      styles[property] = css[property];
      return styles;
    });
  };
} else {
  Element.getStyles = function(element) {
    element = $(element);
    var css = element.currentStyle, styles;
    styles = Element.CSS_PROPERTIES.inject({ }, function(results, property) {
      results[property] = css[property];
      return results;
    });
    if (!styles.opacity) styles.opacity = element.getOpacity();
    return styles;
  };
}

Effect.Methods = {
  morph: function(element, style) {
    element = $(element);
    new Effect.Morph(element, Object.extend({ style: style }, arguments[2] || { }));
    return element;
  },
  visualEffect: function(element, effect, options) {
    element = $(element);
    var s = effect.dasherize().camelize(), klass = s.charAt(0).toUpperCase() + s.substring(1);
    new Effect[klass](element, options);
    return element;
  },
  highlight: function(element, options) {
    element = $(element);
    new Effect.Highlight(element, options);
    return element;
  }
};

$w('fade appear grow shrink fold blindUp blindDown slideUp slideDown '+
  'pulsate shake puff squish switchOff dropOut').each(
  function(effect) {
    Effect.Methods[effect] = function(element, options){
      element = $(element);
      Effect[effect.charAt(0).toUpperCase() + effect.substring(1)](element, options);
      return element;
    };
  }
);

$w('getInlineOpacity forceRerendering setContentZoom collectTextNodes collectTextNodesIgnoreClass getStyles').each(
  function(f) { Effect.Methods[f] = Element[f]; }
);

Element.addMethods(Effect.Methods);// script.aculo.us dragdrop.js v1.9.0, Thu Dec 23 16:54:48 -0500 2010

// Copyright (c) 2005-2010 Thomas Fuchs (http://script.aculo.us, http://mir.aculo.us)
//
// script.aculo.us is freely distributable under the terms of an MIT-style license.
// For details, see the script.aculo.us web site: http://script.aculo.us/

if(Object.isUndefined(Effect))
  throw("dragdrop.js requires including script.aculo.us' effects.js library");

var Droppables = {
  drops: [],

  remove: function(element) {
    this.drops = this.drops.reject(function(d) { return d.element==$(element) });
  },

  add: function(element) {
    element = $(element);
    var options = Object.extend({
      greedy:     true,
      hoverclass: null,
      tree:       false
    }, arguments[1] || { });

    // cache containers
    if(options.containment) {
      options._containers = [];
      var containment = options.containment;
      if(Object.isArray(containment)) {
        containment.each( function(c) { options._containers.push($(c)) });
      } else {
        options._containers.push($(containment));
      }
    }

    if(options.accept) options.accept = [options.accept].flatten();

    Element.makePositioned(element); // fix IE
    options.element = element;

    this.drops.push(options);
  },

  findDeepestChild: function(drops) {
    deepest = drops[0];

    for (i = 1; i < drops.length; ++i)
      if (Element.isParent(drops[i].element, deepest.element))
        deepest = drops[i];

    return deepest;
  },

  isContained: function(element, drop) {
    var containmentNode;
    if(drop.tree) {
      containmentNode = element.treeNode;
    } else {
      containmentNode = element.parentNode;
    }
    return drop._containers.detect(function(c) { return containmentNode == c });
  },

  isAffected: function(point, element, drop) {
    return (
      (drop.element!=element) &&
      ((!drop._containers) ||
        this.isContained(element, drop)) &&
      ((!drop.accept) ||
        (Element.classNames(element).detect(
          function(v) { return drop.accept.include(v) } ) )) &&
      Position.within(drop.element, point[0], point[1]) );
  },

  deactivate: function(drop) {
    if(drop.hoverclass)
      Element.removeClassName(drop.element, drop.hoverclass);
    this.last_active = null;
  },

  activate: function(drop) {
    if(drop.hoverclass)
      Element.addClassName(drop.element, drop.hoverclass);
    this.last_active = drop;
  },

  show: function(point, element) {
    if(!this.drops.length) return;
    var drop, affected = [];

    this.drops.each( function(drop) {
      if(Droppables.isAffected(point, element, drop))
        affected.push(drop);
    });

    if(affected.length>0)
      drop = Droppables.findDeepestChild(affected);

    if(this.last_active && this.last_active != drop) this.deactivate(this.last_active);
    if (drop) {
      Position.within(drop.element, point[0], point[1]);
      if(drop.onHover)
        drop.onHover(element, drop.element, Position.overlap(drop.overlap, drop.element));

      if (drop != this.last_active) Droppables.activate(drop);
    }
  },

  fire: function(event, element) {
    if(!this.last_active) return;
    Position.prepare();

    if (this.isAffected([Event.pointerX(event), Event.pointerY(event)], element, this.last_active))
      if (this.last_active.onDrop) {
        this.last_active.onDrop(element, this.last_active.element, event);
        return true;
      }
  },

  reset: function() {
    if(this.last_active)
      this.deactivate(this.last_active);
  }
};

var Draggables = {
  drags: [],
  observers: [],

  register: function(draggable) {
    if(this.drags.length == 0) {
      this.eventMouseUp   = this.endDrag.bindAsEventListener(this);
      this.eventMouseMove = this.updateDrag.bindAsEventListener(this);
      this.eventKeypress  = this.keyPress.bindAsEventListener(this);

      Event.observe(document, "mouseup", this.eventMouseUp);
      Event.observe(document, "mousemove", this.eventMouseMove);
      Event.observe(document, "keypress", this.eventKeypress);
    }
    this.drags.push(draggable);
  },

  unregister: function(draggable) {
    this.drags = this.drags.reject(function(d) { return d==draggable });
    if(this.drags.length == 0) {
      Event.stopObserving(document, "mouseup", this.eventMouseUp);
      Event.stopObserving(document, "mousemove", this.eventMouseMove);
      Event.stopObserving(document, "keypress", this.eventKeypress);
    }
  },

  activate: function(draggable) {
    if(draggable.options.delay) {
      this._timeout = setTimeout(function() {
        Draggables._timeout = null;
        window.focus();
        Draggables.activeDraggable = draggable;
      }.bind(this), draggable.options.delay);
    } else {
      window.focus(); // allows keypress events if window isn't currently focused, fails for Safari
      this.activeDraggable = draggable;
    }
  },

  deactivate: function() {
    this.activeDraggable = null;
  },

  updateDrag: function(event) {
    if(!this.activeDraggable) return;
    var pointer = [Event.pointerX(event), Event.pointerY(event)];
    // Mozilla-based browsers fire successive mousemove events with
    // the same coordinates, prevent needless redrawing (moz bug?)
    if(this._lastPointer && (this._lastPointer.inspect() == pointer.inspect())) return;
    this._lastPointer = pointer;

    this.activeDraggable.updateDrag(event, pointer);
  },

  endDrag: function(event) {
    if(this._timeout) {
      clearTimeout(this._timeout);
      this._timeout = null;
    }
    if(!this.activeDraggable) return;
    this._lastPointer = null;
    this.activeDraggable.endDrag(event);
    this.activeDraggable = null;
  },

  keyPress: function(event) {
    if(this.activeDraggable)
      this.activeDraggable.keyPress(event);
  },

  addObserver: function(observer) {
    this.observers.push(observer);
    this._cacheObserverCallbacks();
  },

  removeObserver: function(element) {  // element instead of observer fixes mem leaks
    this.observers = this.observers.reject( function(o) { return o.element==element });
    this._cacheObserverCallbacks();
  },

  notify: function(eventName, draggable, event) {  // 'onStart', 'onEnd', 'onDrag'
    if(this[eventName+'Count'] > 0)
      this.observers.each( function(o) {
        if(o[eventName]) o[eventName](eventName, draggable, event);
      });
    if(draggable.options[eventName]) draggable.options[eventName](draggable, event);
  },

  _cacheObserverCallbacks: function() {
    ['onStart','onEnd','onDrag'].each( function(eventName) {
      Draggables[eventName+'Count'] = Draggables.observers.select(
        function(o) { return o[eventName]; }
      ).length;
    });
  }
};

/*--------------------------------------------------------------------------*/

var Draggable = Class.create({
  initialize: function(element) {
    var defaults = {
      handle: false,
      reverteffect: function(element, top_offset, left_offset) {
        var dur = Math.sqrt(Math.abs(top_offset^2)+Math.abs(left_offset^2))*0.02;
        new Effect.Move(element, { x: -left_offset, y: -top_offset, duration: dur,
          queue: {scope:'_draggable', position:'end'}
        });
      },
      endeffect: function(element) {
        var toOpacity = Object.isNumber(element._opacity) ? element._opacity : 1.0;
        new Effect.Opacity(element, {duration:0.2, from:0.7, to:toOpacity,
          queue: {scope:'_draggable', position:'end'},
          afterFinish: function(){
            Draggable._dragging[element] = false
          }
        });
      },
      zindex: 1000,
      revert: false,
      quiet: false,
      scroll: false,
      scrollSensitivity: 20,
      scrollSpeed: 15,
      snap: false,  // false, or xy or [x,y] or function(x,y){ return [x,y] }
      delay: 0
    };

    if(!arguments[1] || Object.isUndefined(arguments[1].endeffect))
      Object.extend(defaults, {
        starteffect: function(element) {
          element._opacity = Element.getOpacity(element);
          Draggable._dragging[element] = true;
          new Effect.Opacity(element, {duration:0.2, from:element._opacity, to:0.7});
        }
      });

    var options = Object.extend(defaults, arguments[1] || { });

    this.element = $(element);

    if(options.handle && Object.isString(options.handle))
      this.handle = this.element.down('.'+options.handle, 0);

    if(!this.handle) this.handle = $(options.handle);
    if(!this.handle) this.handle = this.element;

    if(options.scroll && !options.scroll.scrollTo && !options.scroll.outerHTML) {
      options.scroll = $(options.scroll);
      this._isScrollChild = Element.childOf(this.element, options.scroll);
    }

    Element.makePositioned(this.element); // fix IE

    this.options  = options;
    this.dragging = false;

    this.eventMouseDown = this.initDrag.bindAsEventListener(this);
    Event.observe(this.handle, "mousedown", this.eventMouseDown);

    Draggables.register(this);
  },

  destroy: function() {
    Event.stopObserving(this.handle, "mousedown", this.eventMouseDown);
    Draggables.unregister(this);
  },

  currentDelta: function() {
    return([
      parseInt(Element.getStyle(this.element,'left') || '0'),
      parseInt(Element.getStyle(this.element,'top') || '0')]);
  },

  initDrag: function(event) {
    if(!Object.isUndefined(Draggable._dragging[this.element]) &&
      Draggable._dragging[this.element]) return;
    if(Event.isLeftClick(event)) {
      // abort on form elements, fixes a Firefox issue
      var src = Event.element(event);
      if((tag_name = src.tagName.toUpperCase()) && (
        tag_name=='INPUT' ||
        tag_name=='SELECT' ||
        tag_name=='OPTION' ||
        tag_name=='BUTTON' ||
        tag_name=='TEXTAREA')) return;

      var pointer = [Event.pointerX(event), Event.pointerY(event)];
      var pos     = this.element.cumulativeOffset();
      this.offset = [0,1].map( function(i) { return (pointer[i] - pos[i]) });

      Draggables.activate(this);
      Event.stop(event);
    }
  },

  startDrag: function(event) {
    this.dragging = true;
    if(!this.delta)
      this.delta = this.currentDelta();

    if(this.options.zindex) {
      this.originalZ = parseInt(Element.getStyle(this.element,'z-index') || 0);
      this.element.style.zIndex = this.options.zindex;
    }

    if(this.options.ghosting) {
      this._clone = this.element.cloneNode(true);
      this._originallyAbsolute = (this.element.getStyle('position') == 'absolute');
      if (!this._originallyAbsolute)
        Position.absolutize(this.element);
      this.element.parentNode.insertBefore(this._clone, this.element);
    }

    if(this.options.scroll) {
      if (this.options.scroll == window) {
        var where = this._getWindowScroll(this.options.scroll);
        this.originalScrollLeft = where.left;
        this.originalScrollTop = where.top;
      } else {
        this.originalScrollLeft = this.options.scroll.scrollLeft;
        this.originalScrollTop = this.options.scroll.scrollTop;
      }
    }

    Draggables.notify('onStart', this, event);

    if(this.options.starteffect) this.options.starteffect(this.element);
  },

  updateDrag: function(event, pointer) {
    if(!this.dragging) this.startDrag(event);

    if(!this.options.quiet){
      Position.prepare();
      Droppables.show(pointer, this.element);
    }

    Draggables.notify('onDrag', this, event);

    this.draw(pointer);
    if(this.options.change) this.options.change(this);

    if(this.options.scroll) {
      this.stopScrolling();

      var p;
      if (this.options.scroll == window) {
        with(this._getWindowScroll(this.options.scroll)) { p = [ left, top, left+width, top+height ]; }
      } else {
        p = Position.page(this.options.scroll).toArray();
        p[0] += this.options.scroll.scrollLeft + Position.deltaX;
        p[1] += this.options.scroll.scrollTop + Position.deltaY;
        p.push(p[0]+this.options.scroll.offsetWidth);
        p.push(p[1]+this.options.scroll.offsetHeight);
      }
      var speed = [0,0];
      if(pointer[0] < (p[0]+this.options.scrollSensitivity)) speed[0] = pointer[0]-(p[0]+this.options.scrollSensitivity);
      if(pointer[1] < (p[1]+this.options.scrollSensitivity)) speed[1] = pointer[1]-(p[1]+this.options.scrollSensitivity);
      if(pointer[0] > (p[2]-this.options.scrollSensitivity)) speed[0] = pointer[0]-(p[2]-this.options.scrollSensitivity);
      if(pointer[1] > (p[3]-this.options.scrollSensitivity)) speed[1] = pointer[1]-(p[3]-this.options.scrollSensitivity);
      this.startScrolling(speed);
    }

    // fix AppleWebKit rendering
    if(Prototype.Browser.WebKit) window.scrollBy(0,0);

    Event.stop(event);
  },

  finishDrag: function(event, success) {
    this.dragging = false;

    if(this.options.quiet){
      Position.prepare();
      var pointer = [Event.pointerX(event), Event.pointerY(event)];
      Droppables.show(pointer, this.element);
    }

    if(this.options.ghosting) {
      if (!this._originallyAbsolute)
        Position.relativize(this.element);
      delete this._originallyAbsolute;
      Element.remove(this._clone);
      this._clone = null;
    }

    var dropped = false;
    if(success) {
      dropped = Droppables.fire(event, this.element);
      if (!dropped) dropped = false;
    }
    if(dropped && this.options.onDropped) this.options.onDropped(this.element);
    Draggables.notify('onEnd', this, event);

    var revert = this.options.revert;
    if(revert && Object.isFunction(revert)) revert = revert(this.element);

    var d = this.currentDelta();
    if(revert && this.options.reverteffect) {
      if (dropped == 0 || revert != 'failure')
        this.options.reverteffect(this.element,
          d[1]-this.delta[1], d[0]-this.delta[0]);
    } else {
      this.delta = d;
    }

    if(this.options.zindex)
      this.element.style.zIndex = this.originalZ;

    if(this.options.endeffect)
      this.options.endeffect(this.element);

    Draggables.deactivate(this);
    Droppables.reset();
  },

  keyPress: function(event) {
    if(event.keyCode!=Event.KEY_ESC) return;
    this.finishDrag(event, false);
    Event.stop(event);
  },

  endDrag: function(event) {
    if(!this.dragging) return;
    this.stopScrolling();
    this.finishDrag(event, true);
    Event.stop(event);
  },

  draw: function(point) {
    var pos = this.element.cumulativeOffset();
    if(this.options.ghosting) {
      var r   = Position.realOffset(this.element);
      pos[0] += r[0] - Position.deltaX; pos[1] += r[1] - Position.deltaY;
    }

    var d = this.currentDelta();
    pos[0] -= d[0]; pos[1] -= d[1];

    if(this.options.scroll && (this.options.scroll != window && this._isScrollChild)) {
      pos[0] -= this.options.scroll.scrollLeft-this.originalScrollLeft;
      pos[1] -= this.options.scroll.scrollTop-this.originalScrollTop;
    }

    var p = [0,1].map(function(i){
      return (point[i]-pos[i]-this.offset[i])
    }.bind(this));

    if(this.options.snap) {
      if(Object.isFunction(this.options.snap)) {
        p = this.options.snap(p[0],p[1],this);
      } else {
      if(Object.isArray(this.options.snap)) {
        p = p.map( function(v, i) {
          return (v/this.options.snap[i]).round()*this.options.snap[i] }.bind(this));
      } else {
        p = p.map( function(v) {
          return (v/this.options.snap).round()*this.options.snap }.bind(this));
      }
    }}

    var style = this.element.style;
    if((!this.options.constraint) || (this.options.constraint=='horizontal'))
      style.left = p[0] + "px";
    if((!this.options.constraint) || (this.options.constraint=='vertical'))
      style.top  = p[1] + "px";

    if(style.visibility=="hidden") style.visibility = ""; // fix gecko rendering
  },

  stopScrolling: function() {
    if(this.scrollInterval) {
      clearInterval(this.scrollInterval);
      this.scrollInterval = null;
      Draggables._lastScrollPointer = null;
    }
  },

  startScrolling: function(speed) {
    if(!(speed[0] || speed[1])) return;
    this.scrollSpeed = [speed[0]*this.options.scrollSpeed,speed[1]*this.options.scrollSpeed];
    this.lastScrolled = new Date();
    this.scrollInterval = setInterval(this.scroll.bind(this), 10);
  },

  scroll: function() {
    var current = new Date();
    var delta = current - this.lastScrolled;
    this.lastScrolled = current;
    if(this.options.scroll == window) {
      with (this._getWindowScroll(this.options.scroll)) {
        if (this.scrollSpeed[0] || this.scrollSpeed[1]) {
          var d = delta / 1000;
          this.options.scroll.scrollTo( left + d*this.scrollSpeed[0], top + d*this.scrollSpeed[1] );
        }
      }
    } else {
      this.options.scroll.scrollLeft += this.scrollSpeed[0] * delta / 1000;
      this.options.scroll.scrollTop  += this.scrollSpeed[1] * delta / 1000;
    }

    Position.prepare();
    Droppables.show(Draggables._lastPointer, this.element);
    Draggables.notify('onDrag', this);
    if (this._isScrollChild) {
      Draggables._lastScrollPointer = Draggables._lastScrollPointer || $A(Draggables._lastPointer);
      Draggables._lastScrollPointer[0] += this.scrollSpeed[0] * delta / 1000;
      Draggables._lastScrollPointer[1] += this.scrollSpeed[1] * delta / 1000;
      if (Draggables._lastScrollPointer[0] < 0)
        Draggables._lastScrollPointer[0] = 0;
      if (Draggables._lastScrollPointer[1] < 0)
        Draggables._lastScrollPointer[1] = 0;
      this.draw(Draggables._lastScrollPointer);
    }

    if(this.options.change) this.options.change(this);
  },

  _getWindowScroll: function(w) {
    var T, L, W, H;
    with (w.document) {
      if (w.document.documentElement && documentElement.scrollTop) {
        T = documentElement.scrollTop;
        L = documentElement.scrollLeft;
      } else if (w.document.body) {
        T = body.scrollTop;
        L = body.scrollLeft;
      }
      if (w.innerWidth) {
        W = w.innerWidth;
        H = w.innerHeight;
      } else if (w.document.documentElement && documentElement.clientWidth) {
        W = documentElement.clientWidth;
        H = documentElement.clientHeight;
      } else {
        W = body.offsetWidth;
        H = body.offsetHeight;
      }
    }
    return { top: T, left: L, width: W, height: H };
  }
});

Draggable._dragging = { };

/*--------------------------------------------------------------------------*/

var SortableObserver = Class.create({
  initialize: function(element, observer) {
    this.element   = $(element);
    this.observer  = observer;
    this.lastValue = Sortable.serialize(this.element);
  },

  onStart: function() {
    this.lastValue = Sortable.serialize(this.element);
  },

  onEnd: function() {
    Sortable.unmark();
    if(this.lastValue != Sortable.serialize(this.element))
      this.observer(this.element)
  }
});

var Sortable = {
  SERIALIZE_RULE: /^[^_\-](?:[A-Za-z0-9\-\_]*)[_](.*)$/,

  sortables: { },

  _findRootElement: function(element) {
    while (element.tagName.toUpperCase() != "BODY") {
      if(element.id && Sortable.sortables[element.id]) return element;
      element = element.parentNode;
    }
  },

  options: function(element) {
    element = Sortable._findRootElement($(element));
    if(!element) return;
    return Sortable.sortables[element.id];
  },

  destroy: function(element){
    element = $(element);
    var s = Sortable.sortables[element.id];

    if(s) {
      Draggables.removeObserver(s.element);
      s.droppables.each(function(d){ Droppables.remove(d) });
      s.draggables.invoke('destroy');

      delete Sortable.sortables[s.element.id];
    }
  },

  create: function(element) {
    element = $(element);
    var options = Object.extend({
      element:     element,
      tag:         'li',       // assumes li children, override with tag: 'tagname'
      dropOnEmpty: false,
      tree:        false,
      treeTag:     'ul',
      overlap:     'vertical', // one of 'vertical', 'horizontal'
      constraint:  'vertical', // one of 'vertical', 'horizontal', false
      containment: element,    // also takes array of elements (or id's); or false
      handle:      false,      // or a CSS class
      only:        false,
      delay:       0,
      hoverclass:  null,
      ghosting:    false,
      quiet:       false,
      scroll:      false,
      scrollSensitivity: 20,
      scrollSpeed: 15,
      format:      this.SERIALIZE_RULE,

      // these take arrays of elements or ids and can be
      // used for better initialization performance
      elements:    false,
      handles:     false,

      onChange:    Prototype.emptyFunction,
      onUpdate:    Prototype.emptyFunction
    }, arguments[1] || { });

    // clear any old sortable with same element
    this.destroy(element);

    // build options for the draggables
    var options_for_draggable = {
      revert:      true,
      quiet:       options.quiet,
      scroll:      options.scroll,
      scrollSpeed: options.scrollSpeed,
      scrollSensitivity: options.scrollSensitivity,
      delay:       options.delay,
      ghosting:    options.ghosting,
      constraint:  options.constraint,
      handle:      options.handle };

    if(options.starteffect)
      options_for_draggable.starteffect = options.starteffect;

    if(options.reverteffect)
      options_for_draggable.reverteffect = options.reverteffect;
    else
      if(options.ghosting) options_for_draggable.reverteffect = function(element) {
        element.style.top  = 0;
        element.style.left = 0;
      };

    if(options.endeffect)
      options_for_draggable.endeffect = options.endeffect;

    if(options.zindex)
      options_for_draggable.zindex = options.zindex;

    // build options for the droppables
    var options_for_droppable = {
      overlap:     options.overlap,
      containment: options.containment,
      tree:        options.tree,
      hoverclass:  options.hoverclass,
      onHover:     Sortable.onHover
    };

    var options_for_tree = {
      onHover:      Sortable.onEmptyHover,
      overlap:      options.overlap,
      containment:  options.containment,
      hoverclass:   options.hoverclass
    };

    // fix for gecko engine
    Element.cleanWhitespace(element);

    options.draggables = [];
    options.droppables = [];

    // drop on empty handling
    if(options.dropOnEmpty || options.tree) {
      Droppables.add(element, options_for_tree);
      options.droppables.push(element);
    }

    (options.elements || this.findElements(element, options) || []).each( function(e,i) {
      var handle = options.handles ? $(options.handles[i]) :
        (options.handle ? $(e).select('.' + options.handle)[0] : e);
      options.draggables.push(
        new Draggable(e, Object.extend(options_for_draggable, { handle: handle })));
      Droppables.add(e, options_for_droppable);
      if(options.tree) e.treeNode = element;
      options.droppables.push(e);
    });

    if(options.tree) {
      (Sortable.findTreeElements(element, options) || []).each( function(e) {
        Droppables.add(e, options_for_tree);
        e.treeNode = element;
        options.droppables.push(e);
      });
    }

    // keep reference
    this.sortables[element.identify()] = options;

    // for onupdate
    Draggables.addObserver(new SortableObserver(element, options.onUpdate));

  },

  // return all suitable-for-sortable elements in a guaranteed order
  findElements: function(element, options) {
    return Element.findChildren(
      element, options.only, options.tree ? true : false, options.tag);
  },

  findTreeElements: function(element, options) {
    return Element.findChildren(
      element, options.only, options.tree ? true : false, options.treeTag);
  },

  onHover: function(element, dropon, overlap) {
    if(Element.isParent(dropon, element)) return;

    if(overlap > .33 && overlap < .66 && Sortable.options(dropon).tree) {
      return;
    } else if(overlap>0.5) {
      Sortable.mark(dropon, 'before');
      if(dropon.previousSibling != element) {
        var oldParentNode = element.parentNode;
        element.style.visibility = "hidden"; // fix gecko rendering
        dropon.parentNode.insertBefore(element, dropon);
        if(dropon.parentNode!=oldParentNode)
          Sortable.options(oldParentNode).onChange(element);
        Sortable.options(dropon.parentNode).onChange(element);
      }
    } else {
      Sortable.mark(dropon, 'after');
      var nextElement = dropon.nextSibling || null;
      if(nextElement != element) {
        var oldParentNode = element.parentNode;
        element.style.visibility = "hidden"; // fix gecko rendering
        dropon.parentNode.insertBefore(element, nextElement);
        if(dropon.parentNode!=oldParentNode)
          Sortable.options(oldParentNode).onChange(element);
        Sortable.options(dropon.parentNode).onChange(element);
      }
    }
  },

  onEmptyHover: function(element, dropon, overlap) {
    var oldParentNode = element.parentNode;
    var droponOptions = Sortable.options(dropon);

    if(!Element.isParent(dropon, element)) {
      var index;

      var children = Sortable.findElements(dropon, {tag: droponOptions.tag, only: droponOptions.only});
      var child = null;

      if(children) {
        var offset = Element.offsetSize(dropon, droponOptions.overlap) * (1.0 - overlap);

        for (index = 0; index < children.length; index += 1) {
          if (offset - Element.offsetSize (children[index], droponOptions.overlap) >= 0) {
            offset -= Element.offsetSize (children[index], droponOptions.overlap);
          } else if (offset - (Element.offsetSize (children[index], droponOptions.overlap) / 2) >= 0) {
            child = index + 1 < children.length ? children[index + 1] : null;
            break;
          } else {
            child = children[index];
            break;
          }
        }
      }

      dropon.insertBefore(element, child);

      Sortable.options(oldParentNode).onChange(element);
      droponOptions.onChange(element);
    }
  },

  unmark: function() {
    if(Sortable._marker) Sortable._marker.hide();
  },

  mark: function(dropon, position) {
    // mark on ghosting only
    var sortable = Sortable.options(dropon.parentNode);
    if(sortable && !sortable.ghosting) return;

    if(!Sortable._marker) {
      Sortable._marker =
        ($('dropmarker') || Element.extend(document.createElement('DIV'))).
          hide().addClassName('dropmarker').setStyle({position:'absolute'});
      document.getElementsByTagName("body").item(0).appendChild(Sortable._marker);
    }
    var offsets = dropon.cumulativeOffset();
    Sortable._marker.setStyle({left: offsets[0]+'px', top: offsets[1] + 'px'});

    if(position=='after')
      if(sortable.overlap == 'horizontal')
        Sortable._marker.setStyle({left: (offsets[0]+dropon.clientWidth) + 'px'});
      else
        Sortable._marker.setStyle({top: (offsets[1]+dropon.clientHeight) + 'px'});

    Sortable._marker.show();
  },

  _tree: function(element, options, parent) {
    var children = Sortable.findElements(element, options) || [];

    for (var i = 0; i < children.length; ++i) {
      var match = children[i].id.match(options.format);

      if (!match) continue;

      var child = {
        id: encodeURIComponent(match ? match[1] : null),
        element: element,
        parent: parent,
        children: [],
        position: parent.children.length,
        container: $(children[i]).down(options.treeTag)
      };

      /* Get the element containing the children and recurse over it */
      if (child.container)
        this._tree(child.container, options, child);

      parent.children.push (child);
    }

    return parent;
  },

  tree: function(element) {
    element = $(element);
    var sortableOptions = this.options(element);
    var options = Object.extend({
      tag: sortableOptions.tag,
      treeTag: sortableOptions.treeTag,
      only: sortableOptions.only,
      name: element.id,
      format: sortableOptions.format
    }, arguments[1] || { });

    var root = {
      id: null,
      parent: null,
      children: [],
      container: element,
      position: 0
    };

    return Sortable._tree(element, options, root);
  },

  /* Construct a [i] index for a particular node */
  _constructIndex: function(node) {
    var index = '';
    do {
      if (node.id) index = '[' + node.position + ']' + index;
    } while ((node = node.parent) != null);
    return index;
  },

  sequence: function(element) {
    element = $(element);
    var options = Object.extend(this.options(element), arguments[1] || { });

    return $(this.findElements(element, options) || []).map( function(item) {
      return item.id.match(options.format) ? item.id.match(options.format)[1] : '';
    });
  },

  setSequence: function(element, new_sequence) {
    element = $(element);
    var options = Object.extend(this.options(element), arguments[2] || { });

    var nodeMap = { };
    this.findElements(element, options).each( function(n) {
        if (n.id.match(options.format))
            nodeMap[n.id.match(options.format)[1]] = [n, n.parentNode];
        n.parentNode.removeChild(n);
    });

    new_sequence.each(function(ident) {
      var n = nodeMap[ident];
      if (n) {
        n[1].appendChild(n[0]);
        delete nodeMap[ident];
      }
    });
  },

  serialize: function(element) {
    element = $(element);
    var options = Object.extend(Sortable.options(element), arguments[1] || { });
    var name = encodeURIComponent(
      (arguments[1] && arguments[1].name) ? arguments[1].name : element.id);

    if (options.tree) {
      return Sortable.tree(element, arguments[1]).children.map( function (item) {
        return [name + Sortable._constructIndex(item) + "[id]=" +
                encodeURIComponent(item.id)].concat(item.children.map(arguments.callee));
      }).flatten().join('&');
    } else {
      return Sortable.sequence(element, arguments[1]).map( function(item) {
        return name + "[]=" + encodeURIComponent(item);
      }).join('&');
    }
  }
};

// Returns true if child is contained within element
Element.isParent = function(child, element) {
  if (!child.parentNode || child == element) return false;
  if (child.parentNode == element) return true;
  return Element.isParent(child.parentNode, element);
};

Element.findChildren = function(element, only, recursive, tagName) {
  if(!element.hasChildNodes()) return null;
  tagName = tagName.toUpperCase();
  if(only) only = [only].flatten();
  var elements = [];
  $A(element.childNodes).each( function(e) {
    if(e.tagName && e.tagName.toUpperCase()==tagName &&
      (!only || (Element.classNames(e).detect(function(v) { return only.include(v) }))))
        elements.push(e);
    if(recursive) {
      var grandchildren = Element.findChildren(e, only, recursive, tagName);
      if(grandchildren) elements.push(grandchildren);
    }
  });

  return (elements.length>0 ? elements.flatten() : []);
};

Element.offsetSize = function (element, type) {
  return element['offset' + ((type=='vertical' || type=='height') ? 'Height' : 'Width')];
};// script.aculo.us controls.js v1.9.0, Thu Dec 23 16:54:48 -0500 2010

// Copyright (c) 2005-2010 Thomas Fuchs (http://script.aculo.us, http://mir.aculo.us)
//           (c) 2005-2010 Ivan Krstic (http://blogs.law.harvard.edu/ivan)
//           (c) 2005-2010 Jon Tirsen (http://www.tirsen.com)
// Contributors:
//  Richard Livsey
//  Rahul Bhargava
//  Rob Wills
//
// script.aculo.us is freely distributable under the terms of an MIT-style license.
// For details, see the script.aculo.us web site: http://script.aculo.us/

// Autocompleter.Base handles all the autocompletion functionality
// that's independent of the data source for autocompletion. This
// includes drawing the autocompletion menu, observing keyboard
// and mouse events, and similar.
//
// Specific autocompleters need to provide, at the very least,
// a getUpdatedChoices function that will be invoked every time
// the text inside the monitored textbox changes. This method
// should get the text for which to provide autocompletion by
// invoking this.getToken(), NOT by directly accessing
// this.element.value. This is to allow incremental tokenized
// autocompletion. Specific auto-completion logic (AJAX, etc)
// belongs in getUpdatedChoices.
//
// Tokenized incremental autocompletion is enabled automatically
// when an autocompleter is instantiated with the 'tokens' option
// in the options parameter, e.g.:
// new Ajax.Autocompleter('id','upd', '/url/', { tokens: ',' });
// will incrementally autocomplete with a comma as the token.
// Additionally, ',' in the above example can be replaced with
// a token array, e.g. { tokens: [',', '\n'] } which
// enables autocompletion on multiple tokens. This is most
// useful when one of the tokens is \n (a newline), as it
// allows smart autocompletion after linebreaks.

if(typeof Effect == 'undefined')
  throw("controls.js requires including script.aculo.us' effects.js library");

var Autocompleter = { };
Autocompleter.Base = Class.create({
  baseInitialize: function(element, update, options) {
    element          = $(element);
    this.element     = element;
    this.update      = $(update);
    this.hasFocus    = false;
    this.changed     = false;
    this.active      = false;
    this.index       = 0;
    this.entryCount  = 0;
    this.oldElementValue = this.element.value;

    if(this.setOptions)
      this.setOptions(options);
    else
      this.options = options || { };

    this.options.paramName    = this.options.paramName || this.element.name;
    this.options.tokens       = this.options.tokens || [];
    this.options.frequency    = this.options.frequency || 0.4;
    this.options.minChars     = this.options.minChars || 1;
    this.options.onShow       = this.options.onShow ||
      function(element, update){
        if(!update.style.position || update.style.position=='absolute') {
          update.style.position = 'absolute';
          Position.clone(element, update, {
            setHeight: false,
            offsetTop: element.offsetHeight + document.viewport.getScrollOffsets().top,
            offsetLeft: document.viewport.getScrollOffsets().left
          });
        }
        Effect.Appear(update,{duration:0.15});
      };
    this.options.onHide = this.options.onHide ||
      function(element, update){ new Effect.Fade(update,{duration:0.15}) };

    if(typeof(this.options.tokens) == 'string')
      this.options.tokens = new Array(this.options.tokens);
    // Force carriage returns as token delimiters anyway
    if (!this.options.tokens.include('\n'))
      this.options.tokens.push('\n');

    this.observer = null;

    this.element.setAttribute('autocomplete','off');

    Element.hide(this.update);

    Event.observe(this.element, 'blur', this.onBlur.bindAsEventListener(this));
    Event.observe(this.element, 'keydown', this.onKeyPress.bindAsEventListener(this));
  },
  
  show: function() {
    if(Element.getStyle(this.update, 'display')=='none') this.options.onShow(this.element, this.update);
    if(!this.iefix &&
      (Prototype.Browser.IE) &&
      (Element.getStyle(this.update, 'position')=='absolute')) {
      new Insertion.After(this.update,
       '<iframe id="' + this.update.id + '_iefix" '+
       'style="display:none;position:absolute;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);" ' +
       'src="javascript:false;" frameborder="0" scrolling="no"></iframe>');
      this.iefix = $(this.update.id+'_iefix');
    }
    if(this.iefix) setTimeout(this.fixIEOverlapping.bind(this), 50);
  },

  fixIEOverlapping: function() {
    Position.clone(this.update, this.iefix, {setTop:(!this.update.style.height)});
    this.iefix.style.zIndex = 1;
    this.update.style.zIndex = 2;
    Element.show(this.iefix);
  },

  hide: function() {
    this.stopIndicator();
    if(Element.getStyle(this.update, 'display')!='none') this.options.onHide(this.element, this.update);
    if(this.iefix) Element.hide(this.iefix);
  },

  startIndicator: function() {
    if(this.options.indicator) Element.show(this.options.indicator);
  },

  stopIndicator: function() {
    if(this.options.indicator) Element.hide(this.options.indicator);
  },

  onKeyPress: function(event) {
    if(this.active)
      switch(event.keyCode) {
       case Event.KEY_TAB:
       case Event.KEY_RETURN:
         this.selectEntry();
         Event.stop(event);
       case Event.KEY_ESC:
         this.hide();
         this.active = false;
         Event.stop(event);
         return;
       case Event.KEY_LEFT:
       case Event.KEY_RIGHT:
         return;
       case Event.KEY_UP:
         this.markPrevious();
         this.render();
         Event.stop(event);
         return;
       case Event.KEY_DOWN:
         this.markNext();
         this.render();
         Event.stop(event);
         return;
      }
     else
       if(event.keyCode==Event.KEY_TAB || event.keyCode==Event.KEY_RETURN ||
         (Prototype.Browser.WebKit > 0 && event.keyCode == 0)) return;

    this.changed = true;
    this.hasFocus = true;

    if(this.observer) clearTimeout(this.observer);
      this.observer =
        setTimeout(this.onObserverEvent.bind(this), this.options.frequency*1000);
  },

  activate: function() {
    this.changed = false;
    this.hasFocus = true;
    this.getUpdatedChoices();
  },

  onHover: function(event) {
    var element = Event.findElement(event, 'LI');
    if(this.index != element.autocompleteIndex)
    {
        this.index = element.autocompleteIndex;
        this.render();
    }
    Event.stop(event);
  },

  onClick: function(event) {
    var element = Event.findElement(event, 'LI');
    this.index = element.autocompleteIndex;
    this.selectEntry();
    this.hide();
  },

  onBlur: function(event) {
    // needed to make click events working
    setTimeout(this.hide.bind(this), 250);
    this.hasFocus = false;
    this.active = false;
  },

  render: function() {
    if(this.entryCount > 0) {
      for (var i = 0; i < this.entryCount; i++)
        this.index==i ?
          Element.addClassName(this.getEntry(i),"selected") :
          Element.removeClassName(this.getEntry(i),"selected");
      if(this.hasFocus) {
        this.show();
        this.active = true;
      }
    } else {
      this.active = false;
      this.hide();
    }
  },

  markPrevious: function() {
    if(this.index > 0) this.index--;
      else this.index = this.entryCount-1;
    this.getEntry(this.index).scrollIntoView(true);
  },

  markNext: function() {
    if(this.index < this.entryCount-1) this.index++;
      else this.index = 0;
    this.getEntry(this.index).scrollIntoView(false);
  },

  getEntry: function(index) {
    return this.update.firstChild.childNodes[index];
  },

  getCurrentEntry: function() {
    return this.getEntry(this.index);
  },

  selectEntry: function() {
    this.active = false;
    this.updateElement(this.getCurrentEntry());
  },

  updateElement: function(selectedElement) {
    if (this.options.updateElement) {
      this.options.updateElement(selectedElement);
      return;
    }
    var value = '';
    if (this.options.select) {
      var nodes = $(selectedElement).select('.' + this.options.select) || [];
      if(nodes.length>0) value = Element.collectTextNodes(nodes[0], this.options.select);
    } else
      value = Element.collectTextNodesIgnoreClass(selectedElement, 'informal');

    var bounds = this.getTokenBounds();
    if (bounds[0] != -1) {
      var newValue = this.element.value.substr(0, bounds[0]);
      var whitespace = this.element.value.substr(bounds[0]).match(/^\s+/);
      if (whitespace)
        newValue += whitespace[0];
      this.element.value = newValue + value + this.element.value.substr(bounds[1]);
    } else {
      this.element.value = value;
    }
    this.oldElementValue = this.element.value;
    this.element.focus();

    if (this.options.afterUpdateElement)
      this.options.afterUpdateElement(this.element, selectedElement);
  },

  updateChoices: function(choices) {
    if(!this.changed && this.hasFocus) {
      this.update.innerHTML = choices;
      Element.cleanWhitespace(this.update);
      Element.cleanWhitespace(this.update.down());

      if(this.update.firstChild && this.update.down().childNodes) {
        this.entryCount =
          this.update.down().childNodes.length;
        for (var i = 0; i < this.entryCount; i++) {
          var entry = this.getEntry(i);
          entry.autocompleteIndex = i;
          this.addObservers(entry);
        }
      } else {
        this.entryCount = 0;
      }

      this.stopIndicator();
      this.index = 0;

      if(this.entryCount==1 && this.options.autoSelect) {
        this.selectEntry();
        this.hide();
      } else {
        this.render();
      }
    }
  },

  addObservers: function(element) {
    Event.observe(element, "mouseover", this.onHover.bindAsEventListener(this));
    Event.observe(element, "click", this.onClick.bindAsEventListener(this));
  },

  onObserverEvent: function() {
    this.changed = false;
    this.tokenBounds = null;
    if(this.getToken().length>=this.options.minChars) {
      this.getUpdatedChoices();
    } else {
      this.active = false;
      this.hide();
    }
    this.oldElementValue = this.element.value;
  },

  getToken: function() {
    var bounds = this.getTokenBounds();
    return this.element.value.substring(bounds[0], bounds[1]).strip();
  },

  getTokenBounds: function() {
    if (null != this.tokenBounds) return this.tokenBounds;
    var value = this.element.value;
    if (value.strip().empty()) return [-1, 0];
    var diff = arguments.callee.getFirstDifferencePos(value, this.oldElementValue);
    var offset = (diff == this.oldElementValue.length ? 1 : 0);
    var prevTokenPos = -1, nextTokenPos = value.length;
    var tp;
    for (var index = 0, l = this.options.tokens.length; index < l; ++index) {
      tp = value.lastIndexOf(this.options.tokens[index], diff + offset - 1);
      if (tp > prevTokenPos) prevTokenPos = tp;
      tp = value.indexOf(this.options.tokens[index], diff + offset);
      if (-1 != tp && tp < nextTokenPos) nextTokenPos = tp;
    }
    return (this.tokenBounds = [prevTokenPos + 1, nextTokenPos]);
  }
});

Autocompleter.Base.prototype.getTokenBounds.getFirstDifferencePos = function(newS, oldS) {
  var boundary = Math.min(newS.length, oldS.length);
  for (var index = 0; index < boundary; ++index)
    if (newS[index] != oldS[index])
      return index;
  return boundary;
};

Ajax.Autocompleter = Class.create(Autocompleter.Base, {
  initialize: function(element, update, url, options) {
    this.baseInitialize(element, update, options);
    this.options.asynchronous  = true;
    this.options.onComplete    = this.onComplete.bind(this);
    this.options.defaultParams = this.options.parameters || null;
    this.url                   = url;
  },

  getUpdatedChoices: function() {
    this.startIndicator();

    var entry = encodeURIComponent(this.options.paramName) + '=' +
      encodeURIComponent(this.getToken());

    this.options.parameters = this.options.callback ?
      this.options.callback(this.element, entry) : entry;

    if(this.options.defaultParams)
      this.options.parameters += '&' + this.options.defaultParams;

    new Ajax.Request(this.url, this.options);
  },

  onComplete: function(request) {
    this.updateChoices(request.responseText);
  }
});

// The local array autocompleter. Used when you'd prefer to
// inject an array of autocompletion options into the page, rather
// than sending out Ajax queries, which can be quite slow sometimes.
//
// The constructor takes four parameters. The first two are, as usual,
// the id of the monitored textbox, and id of the autocompletion menu.
// The third is the array you want to autocomplete from, and the fourth
// is the options block.
//
// Extra local autocompletion options:
// - choices - How many autocompletion choices to offer
//
// - partialSearch - If false, the autocompleter will match entered
//                    text only at the beginning of strings in the
//                    autocomplete array. Defaults to true, which will
//                    match text at the beginning of any *word* in the
//                    strings in the autocomplete array. If you want to
//                    search anywhere in the string, additionally set
//                    the option fullSearch to true (default: off).
//
// - fullSsearch - Search anywhere in autocomplete array strings.
//
// - partialChars - How many characters to enter before triggering
//                   a partial match (unlike minChars, which defines
//                   how many characters are required to do any match
//                   at all). Defaults to 2.
//
// - ignoreCase - Whether to ignore case when autocompleting.
//                 Defaults to true.
//
// It's possible to pass in a custom function as the 'selector'
// option, if you prefer to write your own autocompletion logic.
// In that case, the other options above will not apply unless
// you support them.

Autocompleter.Local = Class.create(Autocompleter.Base, {
  initialize: function(element, update, array, options) {
    this.baseInitialize(element, update, options);
    this.options.array = array;
  },

  getUpdatedChoices: function() {
    this.updateChoices(this.options.selector(this));
  },

  setOptions: function(options) {
    this.options = Object.extend({
      choices: 10,
      partialSearch: true,
      partialChars: 2,
      ignoreCase: true,
      fullSearch: false,
      selector: function(instance) {
        var ret       = []; // Beginning matches
        var partial   = []; // Inside matches
        var entry     = instance.getToken();
        var count     = 0;

        for (var i = 0; i < instance.options.array.length &&
          ret.length < instance.options.choices ; i++) {

          var elem = instance.options.array[i];
          var foundPos = instance.options.ignoreCase ?
            elem.toLowerCase().indexOf(entry.toLowerCase()) :
            elem.indexOf(entry);

          while (foundPos != -1) {
            if (foundPos == 0 && elem.length != entry.length) {
              ret.push("<li><strong>" + elem.substr(0, entry.length) + "</strong>" +
                elem.substr(entry.length) + "</li>");
              break;
            } else if (entry.length >= instance.options.partialChars &&
              instance.options.partialSearch && foundPos != -1) {
              if (instance.options.fullSearch || /\s/.test(elem.substr(foundPos-1,1))) {
                partial.push("<li>" + elem.substr(0, foundPos) + "<strong>" +
                  elem.substr(foundPos, entry.length) + "</strong>" + elem.substr(
                  foundPos + entry.length) + "</li>");
                break;
              }
            }

            foundPos = instance.options.ignoreCase ?
              elem.toLowerCase().indexOf(entry.toLowerCase(), foundPos + 1) :
              elem.indexOf(entry, foundPos + 1);

          }
        }
        if (partial.length)
          ret = ret.concat(partial.slice(0, instance.options.choices - ret.length));
        return "<ul>" + ret.join('') + "</ul>";
      }
    }, options || { });
  }
});

// AJAX in-place editor and collection editor
// Full rewrite by Christophe Porteneuve <tdd@tddsworld.com> (April 2007).

// Use this if you notice weird scrolling problems on some browsers,
// the DOM might be a bit confused when this gets called so do this
// waits 1 ms (with setTimeout) until it does the activation
Field.scrollFreeActivate = function(field) {
  setTimeout(function() {
    Field.activate(field);
  }, 1);
};

Ajax.InPlaceEditor = Class.create({
  initialize: function(element, url, options) {
    this.url = url;
    this.element = element = $(element);
    this.prepareOptions();
    this._controls = { };
    arguments.callee.dealWithDeprecatedOptions(options); // DEPRECATION LAYER!!!
    Object.extend(this.options, options || { });
    if (!this.options.formId && this.element.id) {
      this.options.formId = this.element.id + '-inplaceeditor';
      if ($(this.options.formId))
        this.options.formId = '';
    }
    if (this.options.externalControl)
      this.options.externalControl = $(this.options.externalControl);
    if (!this.options.externalControl)
      this.options.externalControlOnly = false;
    this._originalBackground = this.element.getStyle('background-color') || 'transparent';
    this.element.title = this.options.clickToEditText;
    this._boundCancelHandler = this.handleFormCancellation.bind(this);
    this._boundComplete = (this.options.onComplete || Prototype.emptyFunction).bind(this);
    this._boundFailureHandler = this.handleAJAXFailure.bind(this);
    this._boundSubmitHandler = this.handleFormSubmission.bind(this);
    this._boundWrapperHandler = this.wrapUp.bind(this);
    this.registerListeners();
  },
  checkForEscapeOrReturn: function(e) {
    if (!this._editing || e.ctrlKey || e.altKey || e.shiftKey) return;
    if (Event.KEY_ESC == e.keyCode)
      this.handleFormCancellation(e);
    else if (Event.KEY_RETURN == e.keyCode)
      this.handleFormSubmission(e);
  },
  createControl: function(mode, handler, extraClasses) {
    var control = this.options[mode + 'Control'];
    var text = this.options[mode + 'Text'];
    if ('button' == control) {
      var btn = document.createElement('input');
      btn.type = 'submit';
      btn.value = text;
      btn.className = 'editor_' + mode + '_button';
      if ('cancel' == mode)
        btn.onclick = this._boundCancelHandler;
      this._form.appendChild(btn);
      this._controls[mode] = btn;
    } else if ('link' == control) {
      var link = document.createElement('a');
      link.href = '#';
      link.appendChild(document.createTextNode(text));
      link.onclick = 'cancel' == mode ? this._boundCancelHandler : this._boundSubmitHandler;
      link.className = 'editor_' + mode + '_link';
      if (extraClasses)
        link.className += ' ' + extraClasses;
      this._form.appendChild(link);
      this._controls[mode] = link;
    }
  },
  createEditField: function() {
    var text = (this.options.loadTextURL ? this.options.loadingText : this.getText());
    var fld;
    if (1 >= this.options.rows && !/\r|\n/.test(this.getText())) {
      fld = document.createElement('input');
      fld.type = 'text';
      var size = this.options.size || this.options.cols || 0;
      if (0 < size) fld.size = size;
    } else {
      fld = document.createElement('textarea');
      fld.rows = (1 >= this.options.rows ? this.options.autoRows : this.options.rows);
      fld.cols = this.options.cols || 40;
    }
    fld.name = this.options.paramName;
    fld.value = text; // No HTML breaks conversion anymore
    fld.className = 'editor_field';
    if (this.options.submitOnBlur)
      fld.onblur = this._boundSubmitHandler;
    this._controls.editor = fld;
    if (this.options.loadTextURL)
      this.loadExternalText();
    this._form.appendChild(this._controls.editor);
  },
  createForm: function() {
    var ipe = this;
    function addText(mode, condition) {
      var text = ipe.options['text' + mode + 'Controls'];
      if (!text || condition === false) return;
      ipe._form.appendChild(document.createTextNode(text));
    };
    this._form = $(document.createElement('form'));
    this._form.id = this.options.formId;
    this._form.addClassName(this.options.formClassName);
    this._form.onsubmit = this._boundSubmitHandler;
    this.createEditField();
    if ('textarea' == this._controls.editor.tagName.toLowerCase())
      this._form.appendChild(document.createElement('br'));
    if (this.options.onFormCustomization)
      this.options.onFormCustomization(this, this._form);
    addText('Before', this.options.okControl || this.options.cancelControl);
    this.createControl('ok', this._boundSubmitHandler);
    addText('Between', this.options.okControl && this.options.cancelControl);
    this.createControl('cancel', this._boundCancelHandler, 'editor_cancel');
    addText('After', this.options.okControl || this.options.cancelControl);
  },
  destroy: function() {
    if (this._oldInnerHTML)
      this.element.innerHTML = this._oldInnerHTML;
    this.leaveEditMode();
    this.unregisterListeners();
  },
  enterEditMode: function(e) {
    if (this._saving || this._editing) return;
    this._editing = true;
    this.triggerCallback('onEnterEditMode');
    if (this.options.externalControl)
      this.options.externalControl.hide();
    this.element.hide();
    this.createForm();
    this.element.parentNode.insertBefore(this._form, this.element);
    if (!this.options.loadTextURL)
      this.postProcessEditField();
    if (e) Event.stop(e);
  },
  enterHover: function(e) {
    if (this.options.hoverClassName)
      this.element.addClassName(this.options.hoverClassName);
    if (this._saving) return;
    this.triggerCallback('onEnterHover');
  },
  getText: function() {
    return this.element.innerHTML.unescapeHTML();
  },
  handleAJAXFailure: function(transport) {
    this.triggerCallback('onFailure', transport);
    if (this._oldInnerHTML) {
      this.element.innerHTML = this._oldInnerHTML;
      this._oldInnerHTML = null;
    }
  },
  handleFormCancellation: function(e) {
    this.wrapUp();
    if (e) Event.stop(e);
  },
  handleFormSubmission: function(e) {
    var form = this._form;
    var value = $F(this._controls.editor);
    this.prepareSubmission();
    var params = this.options.callback(form, value) || '';
    if (Object.isString(params))
      params = params.toQueryParams();
    params.editorId = this.element.id;
    if (this.options.htmlResponse) {
      var options = Object.extend({ evalScripts: true }, this.options.ajaxOptions);
      Object.extend(options, {
        parameters: params,
        onComplete: this._boundWrapperHandler,
        onFailure: this._boundFailureHandler
      });
      new Ajax.Updater({ success: this.element }, this.url, options);
    } else {
      var options = Object.extend({ method: 'get' }, this.options.ajaxOptions);
      Object.extend(options, {
        parameters: params,
        onComplete: this._boundWrapperHandler,
        onFailure: this._boundFailureHandler
      });
      new Ajax.Request(this.url, options);
    }
    if (e) Event.stop(e);
  },
  leaveEditMode: function() {
    this.element.removeClassName(this.options.savingClassName);
    this.removeForm();
    this.leaveHover();
    this.element.style.backgroundColor = this._originalBackground;
    this.element.show();
    if (this.options.externalControl)
      this.options.externalControl.show();
    this._saving = false;
    this._editing = false;
    this._oldInnerHTML = null;
    this.triggerCallback('onLeaveEditMode');
  },
  leaveHover: function(e) {
    if (this.options.hoverClassName)
      this.element.removeClassName(this.options.hoverClassName);
    if (this._saving) return;
    this.triggerCallback('onLeaveHover');
  },
  loadExternalText: function() {
    this._form.addClassName(this.options.loadingClassName);
    this._controls.editor.disabled = true;
    var options = Object.extend({ method: 'get' }, this.options.ajaxOptions);
    Object.extend(options, {
      parameters: 'editorId=' + encodeURIComponent(this.element.id),
      onComplete: Prototype.emptyFunction,
      onSuccess: function(transport) {
        this._form.removeClassName(this.options.loadingClassName);
        var text = transport.responseText;
        if (this.options.stripLoadedTextTags)
          text = text.stripTags();
        this._controls.editor.value = text;
        this._controls.editor.disabled = false;
        this.postProcessEditField();
      }.bind(this),
      onFailure: this._boundFailureHandler
    });
    new Ajax.Request(this.options.loadTextURL, options);
  },
  postProcessEditField: function() {
    var fpc = this.options.fieldPostCreation;
    if (fpc)
      $(this._controls.editor)['focus' == fpc ? 'focus' : 'activate']();
  },
  prepareOptions: function() {
    this.options = Object.clone(Ajax.InPlaceEditor.DefaultOptions);
    Object.extend(this.options, Ajax.InPlaceEditor.DefaultCallbacks);
    [this._extraDefaultOptions].flatten().compact().each(function(defs) {
      Object.extend(this.options, defs);
    }.bind(this));
  },
  prepareSubmission: function() {
    this._saving = true;
    this.removeForm();
    this.leaveHover();
    this.showSaving();
  },
  registerListeners: function() {
    this._listeners = { };
    var listener;
    $H(Ajax.InPlaceEditor.Listeners).each(function(pair) {
      listener = this[pair.value].bind(this);
      this._listeners[pair.key] = listener;
      if (!this.options.externalControlOnly)
        this.element.observe(pair.key, listener);
      if (this.options.externalControl)
        this.options.externalControl.observe(pair.key, listener);
    }.bind(this));
  },
  removeForm: function() {
    if (!this._form) return;
    this._form.remove();
    this._form = null;
    this._controls = { };
  },
  showSaving: function() {
    this._oldInnerHTML = this.element.innerHTML;
    this.element.innerHTML = this.options.savingText;
    this.element.addClassName(this.options.savingClassName);
    this.element.style.backgroundColor = this._originalBackground;
    this.element.show();
  },
  triggerCallback: function(cbName, arg) {
    if ('function' == typeof this.options[cbName]) {
      this.options[cbName](this, arg);
    }
  },
  unregisterListeners: function() {
    $H(this._listeners).each(function(pair) {
      if (!this.options.externalControlOnly)
        this.element.stopObserving(pair.key, pair.value);
      if (this.options.externalControl)
        this.options.externalControl.stopObserving(pair.key, pair.value);
    }.bind(this));
  },
  wrapUp: function(transport) {
    this.leaveEditMode();
    // Can't use triggerCallback due to backward compatibility: requires
    // binding + direct element
    this._boundComplete(transport, this.element);
  }
});

Object.extend(Ajax.InPlaceEditor.prototype, {
  dispose: Ajax.InPlaceEditor.prototype.destroy
});

Ajax.InPlaceCollectionEditor = Class.create(Ajax.InPlaceEditor, {
  initialize: function($super, element, url, options) {
    this._extraDefaultOptions = Ajax.InPlaceCollectionEditor.DefaultOptions;
    $super(element, url, options);
  },

  createEditField: function() {
    var list = document.createElement('select');
    list.name = this.options.paramName;
    list.size = 1;
    this._controls.editor = list;
    this._collection = this.options.collection || [];
    if (this.options.loadCollectionURL)
      this.loadCollection();
    else
      this.checkForExternalText();
    this._form.appendChild(this._controls.editor);
  },

  loadCollection: function() {
    this._form.addClassName(this.options.loadingClassName);
    this.showLoadingText(this.options.loadingCollectionText);
    var options = Object.extend({ method: 'get' }, this.options.ajaxOptions);
    Object.extend(options, {
      parameters: 'editorId=' + encodeURIComponent(this.element.id),
      onComplete: Prototype.emptyFunction,
      onSuccess: function(transport) {
        var js = transport.responseText.strip();
        if (!/^\[.*\]$/.test(js)) // TODO: improve sanity check
          throw('Server returned an invalid collection representation.');
        this._collection = eval(js);
        this.checkForExternalText();
      }.bind(this),
      onFailure: this.onFailure
    });
    new Ajax.Request(this.options.loadCollectionURL, options);
  },

  showLoadingText: function(text) {
    this._controls.editor.disabled = true;
    var tempOption = this._controls.editor.firstChild;
    if (!tempOption) {
      tempOption = document.createElement('option');
      tempOption.value = '';
      this._controls.editor.appendChild(tempOption);
      tempOption.selected = true;
    }
    tempOption.update((text || '').stripScripts().stripTags());
  },

  checkForExternalText: function() {
    this._text = this.getText();
    if (this.options.loadTextURL)
      this.loadExternalText();
    else
      this.buildOptionList();
  },

  loadExternalText: function() {
    this.showLoadingText(this.options.loadingText);
    var options = Object.extend({ method: 'get' }, this.options.ajaxOptions);
    Object.extend(options, {
      parameters: 'editorId=' + encodeURIComponent(this.element.id),
      onComplete: Prototype.emptyFunction,
      onSuccess: function(transport) {
        this._text = transport.responseText.strip();
        this.buildOptionList();
      }.bind(this),
      onFailure: this.onFailure
    });
    new Ajax.Request(this.options.loadTextURL, options);
  },

  buildOptionList: function() {
    this._form.removeClassName(this.options.loadingClassName);
    this._collection = this._collection.map(function(entry) {
      return 2 === entry.length ? entry : [entry, entry].flatten();
    });
    var marker = ('value' in this.options) ? this.options.value : this._text;
    var textFound = this._collection.any(function(entry) {
      return entry[0] == marker;
    }.bind(this));
    this._controls.editor.update('');
    var option;
    this._collection.each(function(entry, index) {
      option = document.createElement('option');
      option.value = entry[0];
      option.selected = textFound ? entry[0] == marker : 0 == index;
      option.appendChild(document.createTextNode(entry[1]));
      this._controls.editor.appendChild(option);
    }.bind(this));
    this._controls.editor.disabled = false;
    Field.scrollFreeActivate(this._controls.editor);
  }
});

//**** DEPRECATION LAYER FOR InPlace[Collection]Editor! ****
//**** This only  exists for a while,  in order to  let ****
//**** users adapt to  the new API.  Read up on the new ****
//**** API and convert your code to it ASAP!            ****

Ajax.InPlaceEditor.prototype.initialize.dealWithDeprecatedOptions = function(options) {
  if (!options) return;
  function fallback(name, expr) {
    if (name in options || expr === undefined) return;
    options[name] = expr;
  };
  fallback('cancelControl', (options.cancelLink ? 'link' : (options.cancelButton ? 'button' :
    options.cancelLink == options.cancelButton == false ? false : undefined)));
  fallback('okControl', (options.okLink ? 'link' : (options.okButton ? 'button' :
    options.okLink == options.okButton == false ? false : undefined)));
  fallback('highlightColor', options.highlightcolor);
  fallback('highlightEndColor', options.highlightendcolor);
};

Object.extend(Ajax.InPlaceEditor, {
  DefaultOptions: {
    ajaxOptions: { },
    autoRows: 3,                                // Use when multi-line w/ rows == 1
    cancelControl: 'link',                      // 'link'|'button'|false
    cancelText: 'cancel',
    clickToEditText: 'Click to edit',
    externalControl: null,                      // id|elt
    externalControlOnly: false,
    fieldPostCreation: 'activate',              // 'activate'|'focus'|false
    formClassName: 'inplaceeditor-form',
    formId: null,                               // id|elt
    highlightColor: '#ffff99',
    highlightEndColor: '#ffffff',
    hoverClassName: '',
    htmlResponse: true,
    loadingClassName: 'inplaceeditor-loading',
    loadingText: 'Loading...',
    okControl: 'button',                        // 'link'|'button'|false
    okText: 'ok',
    paramName: 'value',
    rows: 1,                                    // If 1 and multi-line, uses autoRows
    savingClassName: 'inplaceeditor-saving',
    savingText: 'Saving...',
    size: 0,
    stripLoadedTextTags: false,
    submitOnBlur: false,
    textAfterControls: '',
    textBeforeControls: '',
    textBetweenControls: ''
  },
  DefaultCallbacks: {
    callback: function(form) {
      return Form.serialize(form);
    },
    onComplete: function(transport, element) {
      // For backward compatibility, this one is bound to the IPE, and passes
      // the element directly.  It was too often customized, so we don't break it.
      new Effect.Highlight(element, {
        startcolor: this.options.highlightColor, keepBackgroundImage: true });
    },
    onEnterEditMode: null,
    onEnterHover: function(ipe) {
      ipe.element.style.backgroundColor = ipe.options.highlightColor;
      if (ipe._effect)
        ipe._effect.cancel();
    },
    onFailure: function(transport, ipe) {
      alert('Error communication with the server: ' + transport.responseText.stripTags());
    },
    onFormCustomization: null, // Takes the IPE and its generated form, after editor, before controls.
    onLeaveEditMode: null,
    onLeaveHover: function(ipe) {
      ipe._effect = new Effect.Highlight(ipe.element, {
        startcolor: ipe.options.highlightColor, endcolor: ipe.options.highlightEndColor,
        restorecolor: ipe._originalBackground, keepBackgroundImage: true
      });
    }
  },
  Listeners: {
    click: 'enterEditMode',
    keydown: 'checkForEscapeOrReturn',
    mouseover: 'enterHover',
    mouseout: 'leaveHover'
  }
});

Ajax.InPlaceCollectionEditor.DefaultOptions = {
  loadingCollectionText: 'Loading options...'
};

// Delayed observer, like Form.Element.Observer,
// but waits for delay after last key input
// Ideal for live-search fields

Form.Element.DelayedObserver = Class.create({
  initialize: function(element, delay, callback) {
    this.delay     = delay || 0.5;
    this.element   = $(element);
    this.callback  = callback;
    this.timer     = null;
    this.lastValue = $F(this.element);
    Event.observe(this.element,'keyup',this.delayedListener.bindAsEventListener(this));
  },
  delayedListener: function(event) {
    if(this.lastValue == $F(this.element)) return;
    if(this.timer) clearTimeout(this.timer);
    this.timer = setTimeout(this.onTimerEvent.bind(this), this.delay * 1000);
    this.lastValue = $F(this.element);
  },
  onTimerEvent: function() {
    this.timer = null;
    this.callback(this.element, $F(this.element));
  }
});/*
   Behaviour v1.1 by Ben Nolan, June 2005. Based largely on the work
   of Simon Willison (see comments by Simon below).

   Description:
   	
   	Uses css selectors to apply javascript behaviours to enable
   	unobtrusive javascript in html documents.
   	
   Usage:   
   
	var myrules = {
		'b.someclass' : function(element){
			element.onclick = function(){
				alert(this.innerHTML);
			}
		},
		'#someid u' : function(element){
			element.onmouseover = function(){
				this.innerHTML = "BLAH!";
			}
		}
	};
	
	Behaviour.register(myrules);
	
	// Call Behaviour.apply() to re-apply the rules (if you
	// update the dom, etc).

   License:
   
   	This file is entirely BSD licensed.
   	
   More information:
   	
   	http://ripcord.co.nz/behaviour/
   
*/   
//Flag to determine if the behaviour was already applied on the page
var behaviourApplied = false;

var Behaviour = {
	list : new Array,
	
	register : function(sheet){
		Behaviour.list.push(sheet);
	},
	
	start : function(){
		Behaviour.addLoadEvent(function(){
			Behaviour.apply();
		});
	},
	
	apply : function(){
		for (var h=0;sheet=Behaviour.list[h];h++){
			for (var selector in sheet){
				list = document.getElementsBySelector(selector);
				
				if (!list){
					continue;
				}

				for (i=0;element=list[i];i++){
					sheet[selector](element);
				}
			}
		}
		behaviourApplied = true;
	},
	
	addLoadEvent : function(func){
		Event.observe(self, "load", function() {
			if (!behaviourApplied) {
				func();
			}
		});
	}
}

Behaviour.start();

if (!document.getElementsBySelector) {
    document.getElementsBySelector = $$;
}/*
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
/**
 * MultiDropDown is a multiple choice dropdown combo, generated using javascript
 */
var openedItemsContainer = null;
var MultiDropDown = Class.create();
Object.extend(MultiDropDown.prototype, {
	initialize: function(container, name, values, options) {
		this.name = name || "";

		//Add this to the collection
		document.multiDropDowns.push(this);
		if (!isEmpty(this.name)) {
			document.multiDropDowns[this.name] = this;
		}

		this.container = $(container);
		this.values = values || [];
		this.options = options || {};
		this.options.open = typeof (this.options.open) == "boolean" ? this.options.open : false;
		this.options.disabled = typeof (this.options.disabled) == "boolean" ? this.options.disabled : false;
		this.options.size = this.options.size || 5;
		this.options.minWidth = this.options.minWidth || 50;
		this.options.maxWidth = this.options.maxWidth || 400;
		this.options.height = this.options.height || 21;
		
		//Update the text on document load
		addOnReadyListener(this.render.bind(this));
	},
	
	render: function() {
		var multiDropDown = this;
		this.visibleRows = Math.min(this.options.size, this.values.length);
		
		//Determine the line height
		this.container.setStyle({'opacity':0});
		this.container.innerHTML = "<div class='multiDropDownOption'><input class='checkbox' type='checkbox'>A</div>";
		var height = Element.getDimensions(this.container.firstChild).height;
		if (height > 0) {
			this.lineHeight = height + 1;
		}
		this.container.innerHTML = "";
		this.container.setStyle({'opacity':''});

		//Create the hidden which will be useful in both single field mode and multi mode (to submit an empty value as well, which will clear the value on session forms 
		var hidden = document.createElement('input');
		hidden.setAttribute("type", "hidden");
		hidden.setAttribute("name", this.name);
		this.valueField = this.container.appendChild(hidden);

		//Create the elements on the div
		this.createHeader();
		this.createItemsContainer();		
		this.createItems();
		
		var _this = this;
		var name = this.name;
		var header = this.header;
		var div = this.itemsContainer;
		var visibleRows = this.visibleRows;
		var options = this.options;

		var toggleFunction = function(event) {
			var isHidden = !div.visible();
			if (isHidden) {
				div.values = getValue(name);
				Element.show(div);
				if (is.ie || is.opera) {
					Position.prepare();
					var offsetTop = Element.getDimensions(header).height + document.viewport.getScrollOffsets().top;
					var offsetLeft = document.viewport.getScrollOffsets().left;
					Position.clone(header, div, {setHeight: false, offsetTop: offsetTop, offsetLeft: offsetLeft});
				}
			} else {
				Element.hide(div);
				if (options.onchange) {
					var newValues = getValue(name);
					if (newValues && newValues.join) newValues = newValues.join(',');
					var oldValues = div.values;
					if (oldValues && oldValues.join) oldValues = oldValues.join(',');
					if (newValues != div.values) {
						if (typeof options.onchange == 'string') {
							eval(options.onchange);
						} else {
							options.onchange.apply(div);
						}
					}
				}
			}
			
			if (isHidden) {
				if (openedItemsContainer != null) {
					openedItemsContainer.hide();
				}
					
				openedItemsContainer = div;
				multiDropDown.updateValues();
			} else {
				openedItemsContainer = null;
			}
			if (event) {
				Event.stop(event);
			}
		};
		
		if (!this.options.open) {
			//Toggle the visibility of the items container on click events
			Event.observe(this.header, 'click', toggleFunction.bindAsEventListener(this.header));
			
			//Hide the div when the user clicks outside
			var toWatch = is.ie && document.body != null ? document.body : self;
			Event.observe(toWatch, "click", function(event) {
				if (div.visible()) {
					toggleFunction(event);
				}
			}.bindAsEventListener(this));
		}
		
		this.updateValues()
	},
	
	createHeader: function() {
		//If the dropdown is open, there is no header
		if (this.options.open) {
			this.header = null;
		} else {
			var style = $H();
			style.set('padding-left', '4px');
			style.set('clear', 'right');
			style.set('cursor', 'default');

			var className = "multiDropDownText " + (this.options.disabled ? "multiDropDownDisabled" : "multiDropDown");
			this.header = this.create("div", style, className, this.container);
			this.header.id = this.name;
		}
	},
	
	createItemsContainer: function() {
		var style = $H();
		if (!this.options.open) {
			style.set('position', 'absolute');
		}
		style.set('display', 'none');
		var height = this.lineHeight * Math.max(this.visibleRows, 1);
		if (is.ie && is.version >= 7) {
			height += 5;
		}
		style.set('height', height + 'px');
		style.set('overflow', 'auto');
		//style.set('overflow-y', 'scroll');
		style.set('margin-top', '-1px');

		var className = "multiDropDownText " + (this.options.disabled ? "multiDropDownDisabled" : "multiDropDown");
		this.itemsContainer = this.create("div", style, className, this.header == null ? this.container : this.header);
	}, 
	
	create: function(tagName, style, className, afterNode) {
		var element = document.createElement(tagName);
		element.className = className;
		
		style.map(function(pair) {
			try {
				element.style[pair.key.camelize()] = pair.value;
			} catch (e) {}
		});
		return this.container.appendChild(element);
	}, 
	
	createItems: function() {
		var container = this;
		var itemsContainer = this.itemsContainer;
		var namePart = this.options.singleField ? "" : " name='" + this.name + "'";
		var disabled = this.options.disabled;
		var open = this.options.open;
		var heightPart = this.lineHeight && this.lineHeight > 0 ? this.lineHeight + "px" : "17px";
		
		//Create each checkbox
		this.values.each(function(current, index) {
			var item = itemsContainer.appendChild(document.createElement('div'));
			item.style.cursor = 'default';
			var sb = new StringBuffer();
			sb.append("<div class='multiDropDownOption' style='white-space:nowrap;height:" + heightPart + "'>");
			sb.append("<input type='checkbox' class='checkbox' style='vertical-align:middle;' ").append(disabled ? "disabled='disabled'" : "").append(" value='").append(current.value).append("' ").append(namePart).append(current.selected ? " checked='checked'" : "").append(">");
			sb.append(" <span class='multiDropDownText' style='white-space:nowrap;padding-right:3px;vertical-align:middle;'>").append(current.text).append("</span>");
			sb.append("</div>");
			item.innerHTML += sb.toString();
			changeClassOnHover(item, "", "multiDropDownHover");
			var checkbox = item.getElementsByTagName("input")[0];
			
			Event.observe(checkbox, 'click', function(event) {
				if (!checkbox.disabled) {
					if (!open && !container.options.disabled) {
						container.updateValues();
					}
					if (event.stopPropagation) {
						event.stopPropagation();
					} else {
						event.cancelBubble = true;
					}
				}
			});
			
			Event.observe(item, 'click', function(event) {
				if (!checkbox.disabled) {
					if (Event.element(event).tagName.toLowerCase() != 'input') {
						var check = this.getElementsByTagName('input')[0];
						check.checked = !check.checked;
					}
				}
				if (!open && !container.options.disabled) {
					container.updateValues();
				}
				if (event.stopPropagation) {
					event.stopPropagation();
				} else {
					event.cancelBubble = true;
				}
			}.bindAsEventListener(item));
		});
	},
	
	updateValues: function() {
		if (this.options.open) {
			Element.show(this.itemsContainer);
		}
		var className = "multiDropDownText " + (this.options.disabled ? "multiDropDownDisabled" : "multiDropDown");
		if (this.header != null) {
			this.header.className = className;
		}
		this.itemsContainer.className = className;
		
		var selected = [];
		var selectedValues = [];
		var spans = $A(this.itemsContainer.getElementsByTagName("span"))
		var checkWidth = 0;
		if (is.ie) {
			checkWidth = 20;
		}
		var maxTextWidth = 0;

		//Retrieve the selected text and values
		$A(this.itemsContainer.getElementsByTagName("input")).each(function(checkbox, index) {
			var currentSpan = spans[index];
			if (checkWidth == 0) {
				checkWidth = Element.getDimensions(currentSpan.previousSibling.previousSibling).width;
			}
			var width = Element.getDimensions(currentSpan).width;
			maxTextWidth = Math.max(maxTextWidth, width);
			if (checkbox.checked) {
				selected.push(currentSpan.innerHTML);
				if (checkbox.value != "") {
					selectedValues.push(checkbox.value);
				}
			}
		});

		//Update the header with the selected text
		var headerTable = null;
		var headerSpan = null;
		if (this.header != null) {
			if (typeof(mddNoItemsMessage) == "undefined") {
				mddNoItemsMessage = "";
			}
			var emptyLabel = this.options.emptyLabel || mddNoItemsMessage;
						
			if (typeof(mddSingleItemsMessage) == "undefined") {
				mddSingleItemsMessage = "";
			}
			if (typeof(mddMultiItemsMessage) == "undefined") {
				mddMultiItemsMessage = "";
			}
			var text = selected.length == 0 ? emptyLabel : selected.length == 1 ? mddSingleItemsMessage : replaceAll(mddMultiItemsMessage, "#items#", selected.length);
			if (text.length == 0) {
				text = "&nbsp;";
			}
			this.header.style.width = '';
			this.header.innerHTML = "<table style='background-color:transparent;border-spacing:0px;padding:0px;border-collapse:collapse;' cellpadding='0' cellspacing='0' height='100%'><tr><td nowrap class='multiDropDownText multiDropDownLabel'><span>" + text + "</span></td><td style='padding:0px !important;' width='15' valign='top' align='right'><img style='margin:0px;' src='" + context + "/pages/images/dropdown.gif'></td></tr></table>";
			headerTable = this.header.firstChild;
			headerSpan = headerTable.getElementsByTagName("span")[0];
		}
		
		//If in singleField mode, set the hidden value
		if (this.options.singleField) {
			this.valueField.value = selectedValues.join(',');
		}
		
		//Ensure the dimensions
		var headerAdjust = 0;
		if (is.ie) {
			headerAdjust = -2;
		}
		if (this.header != null) {
			var headerWidth = Element.getDimensions(headerTable).width;
			if (headerWidth > this.options.maxWidth) {
				headerWidth = this.options.maxWidth;
			} else if (headerWidth < this.options.minWidth) {
				headerWidth = this.options.minWidth;
			}
			this.header.style.width = headerWidth + "px";
			headerTable.style.width = (headerWidth + headerAdjust) + "px";
		}
		var scrollWidth = 30;
		var headerDiff = 7;
		var itemsWidth = checkWidth + maxTextWidth + scrollWidth;
		if (itemsWidth > this.options.maxWidth) {
			itemsWidth = this.options.maxWidth;
		} else if (this.header != null && itemsWidth < headerWidth) {
			itemsWidth = headerWidth + headerDiff;
		} else if (this.header != null && itemsWidth > headerWidth) {
			headerWidth = itemsWidth;
			itemsWidth += headerDiff;
			this.header.style.width = headerWidth + "px";
			headerTable.style.width = (headerWidth + headerAdjust) + "px";
		}
		this.itemsContainer.style.width = (itemsWidth - 3) + "px";
		this.itemsContainer.style.zIndex = 99999;
	},
	
	disable: function() {
		this.options.disabled = true;
		this.updateValues();
	},
	
	enable: function() {
		this.options.disabled = false;
		this.updateValues();
	},
	
	getValue: function() {
        var selectedValues = [];
        $A(this.itemsContainer.getElementsByTagName("input")).each(function(checkbox) {
            if (checkbox.checked) {
            	selectedValues.push(checkbox.value);
            }
        });
        return selectedValues;
	}
});//Initialize the document's multi drop down collection
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
}/*  Copyright Mihai Bazon, 2002-2005  |  www.bazon.net/mishoo
 * -----------------------------------------------------------
 *
 * The DHTML Calendar, version 1.0 "It is happening again"
 *
 * Details and latest version at:
 * www.dynarch.com/projects/calendar
 *
 * This script is developed by Dynarch.com.  Visit us at www.dynarch.com.
 *
 * This script is distributed under the GNU Lesser General Public License.
 * Read the entire license text here: http://www.gnu.org/licenses/lgpl.html
 */

// $Id: calendar.js,v 1.3 2008-07-28 13:04:14 luis Exp $

/** The Calendar object constructor. */
Calendar = function (firstDayOfWeek, dateStr, onSelected, onClose) {
	// member variables
	this.activeDiv = null;
	this.currentDateEl = null;
	this.getDateStatus = null;
	this.getDateToolTip = null;
	this.getDateText = null;
	this.timeout = null;
	this.onSelected = onSelected || null;
	this.onClose = onClose || null;
	this.dragging = false;
	this.hidden = false;
	this.minYear = 1970;
	this.maxYear = 2050;
	this.dateFormat = Calendar._TT["DEF_DATE_FORMAT"];
	this.ttDateFormat = Calendar._TT["TT_DATE_FORMAT"];
	this.isPopup = true;
	this.weekNumbers = true;
	this.firstDayOfWeek = typeof firstDayOfWeek == "number" ? firstDayOfWeek : Calendar._FD; // 0 for Sunday, 1 for Monday, etc.
	this.showsOtherMonths = false;
	this.dateStr = dateStr;
	this.ar_days = null;
	this.showsTime = false;
	this.time24 = true;
	this.yearStep = 2;
	this.hiliteToday = true;
	this.multiple = null;
	// HTML elements
	this.table = null;
	this.element = null;
	this.tbody = null;
	this.firstdayname = null;
	// Combo boxes
	this.monthsCombo = null;
	this.yearsCombo = null;
	this.hilitedMonth = null;
	this.activeMonth = null;
	this.hilitedYear = null;
	this.activeYear = null;
	// Information
	this.dateClicked = false;

	// one-time initializations
	if (typeof Calendar._SDN == "undefined") {
		// table of short day names
		if (typeof Calendar._SDN_len == "undefined")
			Calendar._SDN_len = 3;
		var ar = new Array();
		for (var i = 8; i > 0;) {
			ar[--i] = Calendar._DN[i].substr(0, Calendar._SDN_len);
		}
		Calendar._SDN = ar;
		// table of short month names
		if (typeof Calendar._SMN_len == "undefined")
			Calendar._SMN_len = 3;
		ar = new Array();
		for (var i = 12; i > 0;) {
			ar[--i] = Calendar._MN[i].substr(0, Calendar._SMN_len);
		}
		Calendar._SMN = ar;
	}
};

// ** constants

/// "static", needed for event handlers.
Calendar._C = null;

/// detect a special case of "web browser"
Calendar.is_ie = ( /msie/i.test(navigator.userAgent) &&
		   !/opera/i.test(navigator.userAgent) );

Calendar.is_ie5 = ( Calendar.is_ie && /msie 5\.0/i.test(navigator.userAgent) );

/// detect Opera browser
Calendar.is_opera = /opera/i.test(navigator.userAgent);

/// detect KHTML-based browsers
Calendar.is_khtml = /Konqueror|Safari|KHTML/i.test(navigator.userAgent);

// BEGIN: UTILITY FUNCTIONS; beware that these might be moved into a separate
//        library, at some point.

Calendar.getAbsolutePos = function(el) {
	var SL = 0, ST = 0;
	var is_div = /^div$/i.test(el.tagName);
	if (is_div && el.scrollLeft)
		SL = el.scrollLeft;
	if (is_div && el.scrollTop)
		ST = el.scrollTop;
	var r = { x: el.offsetLeft - SL, y: el.offsetTop - ST };
	if (el.offsetParent) {
		var tmp = this.getAbsolutePos(el.offsetParent);
		r.x += tmp.x;
		r.y += tmp.y;
	}
	return r;
};

Calendar.isRelated = function (el, evt) {
	var related = evt.relatedTarget;
	if (!related) {
		var type = evt.type;
		if (type == "mouseover") {
			related = evt.fromElement;
		} else if (type == "mouseout") {
			related = evt.toElement;
		}
	}
	while (related) {
		if (related == el) {
			return true;
		}
		related = related.parentNode;
	}
	return false;
};

Calendar.removeClass = function(el, className) {
	if (!(el && el.className)) {
		return;
	}
	var cls = el.className.split(" ");
	var ar = new Array();
	for (var i = cls.length; i > 0;) {
		if (cls[--i] != className) {
			ar[ar.length] = cls[i];
		}
	}
	el.className = ar.join(" ");
};

Calendar.addClass = function(el, className) {
	Calendar.removeClass(el, className);
	el.className += " " + className;
};

// FIXME: the following 2 functions totally suck, are useless and should be replaced immediately.
Calendar.getElement = function(ev) {
	var f = Calendar.is_ie ? window.event.srcElement : ev.currentTarget;
	while (f.nodeType != 1 || /^div$/i.test(f.tagName))
		f = f.parentNode;
	return f;
};

Calendar.getTargetElement = function(ev) {
	var f = Calendar.is_ie ? window.event.srcElement : ev.target;
	while (f.nodeType != 1)
		f = f.parentNode;
	return f;
};

Calendar.stopEvent = function(ev) {
	ev || (ev = window.event);
	if (Calendar.is_ie) {
		ev.cancelBubble = true;
		ev.returnValue = false;
	} else {
		ev.preventDefault();
		ev.stopPropagation();
	}
	return false;
};

Calendar.addEvent = function(el, evname, func) {
	if (el.attachEvent) { // IE
		el.attachEvent("on" + evname, func);
	} else if (el.addEventListener) { // Gecko / W3C
		el.addEventListener(evname, func, true);
	} else {
		el["on" + evname] = func;
	}
};

Calendar.removeEvent = function(el, evname, func) {
	if (el.detachEvent) { // IE
		el.detachEvent("on" + evname, func);
	} else if (el.removeEventListener) { // Gecko / W3C
		el.removeEventListener(evname, func, true);
	} else {
		el["on" + evname] = null;
	}
};

Calendar.createElement = function(type, parent) {
	var el = null;
	if (document.createElementNS) {
		// use the XHTML namespace; IE won't normally get here unless
		// _they_ "fix" the DOM2 implementation.
		el = document.createElementNS("http://www.w3.org/1999/xhtml", type);
	} else {
		el = document.createElement(type);
	}
	if (typeof parent != "undefined") {
		parent.appendChild(el);
	}
	return el;
};

// END: UTILITY FUNCTIONS

// BEGIN: CALENDAR STATIC FUNCTIONS

/** Internal -- adds a set of events to make some element behave like a button. */
Calendar._add_evs = function(el) {
	with (Calendar) {
		addEvent(el, "mouseover", dayMouseOver);
		addEvent(el, "mousedown", dayMouseDown);
		addEvent(el, "mouseout", dayMouseOut);
		if (is_ie) {
			addEvent(el, "dblclick", dayMouseDblClick);
			el.setAttribute("unselectable", true);
		}
	}
};

Calendar.findMonth = function(el) {
	if (typeof el.month != "undefined") {
		return el;
	} else if (typeof el.parentNode.month != "undefined") {
		return el.parentNode;
	}
	return null;
};

Calendar.findYear = function(el) {
	if (typeof el.year != "undefined") {
		return el;
	} else if (typeof el.parentNode.year != "undefined") {
		return el.parentNode;
	}
	return null;
};

Calendar.showMonthsCombo = function () {
	var cal = Calendar._C;
	if (!cal) {
		return false;
	}
	var cal = cal;
	var cd = cal.activeDiv;
	var mc = cal.monthsCombo;
	if (cal.hilitedMonth) {
		Calendar.removeClass(cal.hilitedMonth, "hilite");
	}
	if (cal.activeMonth) {
		Calendar.removeClass(cal.activeMonth, "active");
	}
	var mon = cal.monthsCombo.getElementsByTagName("div")[cal.date.getMonth()];
	Calendar.addClass(mon, "active");
	cal.activeMonth = mon;
	var s = mc.style;
	s.display = "block";
	if (cd.navtype < 0)
		s.left = cd.offsetLeft + "px";
	else {
		var mcw = mc.offsetWidth;
		if (typeof mcw == "undefined")
			// Konqueror brain-dead techniques
			mcw = 50;
		s.left = (cd.offsetLeft + cd.offsetWidth - mcw) + "px";
	}
	s.top = (cd.offsetTop + cd.offsetHeight) + "px";
};

Calendar.showYearsCombo = function (fwd) {
	var cal = Calendar._C;
	if (!cal) {
		return false;
	}
	var cal = cal;
	var cd = cal.activeDiv;
	var yc = cal.yearsCombo;
	if (cal.hilitedYear) {
		Calendar.removeClass(cal.hilitedYear, "hilite");
	}
	if (cal.activeYear) {
		Calendar.removeClass(cal.activeYear, "active");
	}
	cal.activeYear = null;
	var Y = cal.date.getFullYear() + (fwd ? 1 : -1);
	var yr = yc.firstChild;
	var show = false;
	for (var i = 12; i > 0; --i) {
		if (Y >= cal.minYear && Y <= cal.maxYear) {
			yr.innerHTML = Y;
			yr.year = Y;
			yr.style.display = "block";
			show = true;
		} else {
			yr.style.display = "none";
		}
		yr = yr.nextSibling;
		Y += fwd ? cal.yearStep : -cal.yearStep;
	}
	if (show) {
		var s = yc.style;
		s.display = "block";
		if (cd.navtype < 0)
			s.left = cd.offsetLeft + "px";
		else {
			var ycw = yc.offsetWidth;
			if (typeof ycw == "undefined")
				// Konqueror brain-dead techniques
				ycw = 50;
			s.left = (cd.offsetLeft + cd.offsetWidth - ycw) + "px";
		}
		s.top = (cd.offsetTop + cd.offsetHeight) + "px";
	}
};

// event handlers

Calendar.tableMouseUp = function(ev) {
	var cal = Calendar._C;
	if (!cal) {
		return false;
	}
	if (cal.timeout) {
		clearTimeout(cal.timeout);
	}
	var el = cal.activeDiv;
	if (!el) {
		return false;
	}
	var target = Calendar.getTargetElement(ev);
	ev || (ev = window.event);
	Calendar.removeClass(el, "active");
	if (target == el || target.parentNode == el) {
		Calendar.cellClick(el, ev);
	}
	var mon = Calendar.findMonth(target);
	var date = null;
	if (mon) {
		date = new Date(cal.date);
		if (mon.month != date.getMonth()) {
			date.setMonth(mon.month);
			cal.setDate(date);
			cal.dateClicked = false;
			cal.callHandler();
		}
	} else {
		var year = Calendar.findYear(target);
		if (year) {
			date = new Date(cal.date);
			if (year.year != date.getFullYear()) {
				date.setFullYear(year.year);
				cal.setDate(date);
				cal.dateClicked = false;
				cal.callHandler();
			}
		}
	}
	with (Calendar) {
		removeEvent(document, "mouseup", tableMouseUp);
		removeEvent(document, "mouseover", tableMouseOver);
		removeEvent(document, "mousemove", tableMouseOver);
		cal._hideCombos();
		_C = null;
		return stopEvent(ev);
	}
};

Calendar.tableMouseOver = function (ev) {
	var cal = Calendar._C;
	if (!cal) {
		return;
	}
	var el = cal.activeDiv;
	var target = Calendar.getTargetElement(ev);
	if (target == el || target.parentNode == el) {
		Calendar.addClass(el, "hilite active");
		Calendar.addClass(el.parentNode, "rowhilite");
	} else {
		if (typeof el.navtype == "undefined" || (el.navtype != 50 && (el.navtype == 0 || Math.abs(el.navtype) > 2)))
			Calendar.removeClass(el, "active");
		Calendar.removeClass(el, "hilite");
		Calendar.removeClass(el.parentNode, "rowhilite");
	}
	ev || (ev = window.event);
	if (el.navtype == 50 && target != el) {
		var pos = Calendar.getAbsolutePos(el);
		var w = el.offsetWidth;
		var x = ev.clientX;
		var dx;
		var decrease = true;
		if (x > pos.x + w) {
			dx = x - pos.x - w;
			decrease = false;
		} else
			dx = pos.x - x;

		if (dx < 0) dx = 0;
		var range = el._range;
		var current = el._current;
		var count = Math.floor(dx / 10) % range.length;
		for (var i = range.length; --i >= 0;)
			if (range[i] == current)
				break;
		while (count-- > 0)
			if (decrease) {
				if (--i < 0)
					i = range.length - 1;
			} else if ( ++i >= range.length )
				i = 0;
		var newval = range[i];
		el.innerHTML = newval;

		cal.onUpdateTime();
	}
	var mon = Calendar.findMonth(target);
	if (mon) {
		if (mon.month != cal.date.getMonth()) {
			if (cal.hilitedMonth) {
				Calendar.removeClass(cal.hilitedMonth, "hilite");
			}
			Calendar.addClass(mon, "hilite");
			cal.hilitedMonth = mon;
		} else if (cal.hilitedMonth) {
			Calendar.removeClass(cal.hilitedMonth, "hilite");
		}
	} else {
		if (cal.hilitedMonth) {
			Calendar.removeClass(cal.hilitedMonth, "hilite");
		}
		var year = Calendar.findYear(target);
		if (year) {
			if (year.year != cal.date.getFullYear()) {
				if (cal.hilitedYear) {
					Calendar.removeClass(cal.hilitedYear, "hilite");
				}
				Calendar.addClass(year, "hilite");
				cal.hilitedYear = year;
			} else if (cal.hilitedYear) {
				Calendar.removeClass(cal.hilitedYear, "hilite");
			}
		} else if (cal.hilitedYear) {
			Calendar.removeClass(cal.hilitedYear, "hilite");
		}
	}
	return Calendar.stopEvent(ev);
};

Calendar.tableMouseDown = function (ev) {
	if (Calendar.getTargetElement(ev) == Calendar.getElement(ev)) {
		return Calendar.stopEvent(ev);
	}
};

Calendar.calDragIt = function (ev) {
	var cal = Calendar._C;
	if (!(cal && cal.dragging)) {
		return false;
	}
	var posX;
	var posY;
	if (Calendar.is_ie) {
		posY = window.event.clientY + document.body.scrollTop;
		posX = window.event.clientX + document.body.scrollLeft;
	} else {
		posX = ev.pageX;
		posY = ev.pageY;
	}
	cal.hideShowCovered();
	var st = cal.element.style;
	st.left = (posX - cal.xOffs) + "px";
	st.top = (posY - cal.yOffs) + "px";
	return Calendar.stopEvent(ev);
};

Calendar.calDragEnd = function (ev) {
	var cal = Calendar._C;
	if (!cal) {
		return false;
	}
	cal.dragging = false;
	with (Calendar) {
		removeEvent(document, "mousemove", calDragIt);
		removeEvent(document, "mouseup", calDragEnd);
		tableMouseUp(ev);
	}
	cal.hideShowCovered();
};

Calendar.dayMouseDown = function(ev) {
	var el = Calendar.getElement(ev);
	if (el.disabled) {
		return false;
	}
	var cal = el.calendar;
	cal.activeDiv = el;
	Calendar._C = cal;
	if (el.navtype != 300) with (Calendar) {
		if (el.navtype == 50) {
			el._current = el.innerHTML;
			addEvent(document, "mousemove", tableMouseOver);
		} else
			addEvent(document, Calendar.is_ie5 ? "mousemove" : "mouseover", tableMouseOver);
		addClass(el, "hilite active");
		addEvent(document, "mouseup", tableMouseUp);
	} else if (cal.isPopup) {
		cal._dragStart(ev);
	}
	if (el.navtype == -1 || el.navtype == 1) {
		if (cal.timeout) clearTimeout(cal.timeout);
		cal.timeout = setTimeout("Calendar.showMonthsCombo()", 250);
	} else if (el.navtype == -2 || el.navtype == 2) {
		if (cal.timeout) clearTimeout(cal.timeout);
		cal.timeout = setTimeout((el.navtype > 0) ? "Calendar.showYearsCombo(true)" : "Calendar.showYearsCombo(false)", 250);
	} else {
		cal.timeout = null;
	}
	return Calendar.stopEvent(ev);
};

Calendar.dayMouseDblClick = function(ev) {
	Calendar.cellClick(Calendar.getElement(ev), ev || window.event);
	if (Calendar.is_ie) {
		document.selection.empty();
	}
};

Calendar.dayMouseOver = function(ev) {
	var el = Calendar.getElement(ev);
	if (Calendar.isRelated(el, ev) || Calendar._C || el.disabled) {
		return false;
	}
	if (el.ttip) {
		if (el.ttip.substr(0, 1) == "_") {
			el.ttip = el.caldate.print(el.calendar.ttDateFormat) + el.ttip.substr(1);
		}
		el.calendar.tooltips.innerHTML = el.ttip;
	}
	if (el.navtype != 300) {
		Calendar.addClass(el, "hilite");
		if (el.caldate) {
			Calendar.addClass(el.parentNode, "rowhilite");
		}
	}
	return Calendar.stopEvent(ev);
};

Calendar.dayMouseOut = function(ev) {
	with (Calendar) {
		var el = getElement(ev);
		if (isRelated(el, ev) || _C || el.disabled)
			return false;
		removeClass(el, "hilite");
		if (el.caldate)
			removeClass(el.parentNode, "rowhilite");
		if (el.calendar)
			el.calendar.tooltips.innerHTML = _TT["SEL_DATE"];
		return stopEvent(ev);
	}
};

/**
 *  A generic "click" handler :) handles all types of buttons defined in this
 *  calendar.
 */
Calendar.cellClick = function(el, ev) {
	var cal = el.calendar;
	var closing = false;
	var newdate = false;
	var date = null;
	if (typeof el.navtype == "undefined") {
		if (cal.currentDateEl) {
			Calendar.removeClass(cal.currentDateEl, "selected");
			Calendar.addClass(el, "selected");
			closing = (cal.currentDateEl == el);
			if (!closing) {
				cal.currentDateEl = el;
			}
		}
		cal.date.setDateOnly(el.caldate);
		date = cal.date;
		var other_month = !(cal.dateClicked = !el.otherMonth);
		if (!other_month && !cal.currentDateEl)
			cal._toggleMultipleDate(new Date(date));
		else
			newdate = !el.disabled;
		// a date was clicked
		if (other_month)
			cal._init(cal.firstDayOfWeek, date);
	} else {
		if (el.navtype == 200) {
			Calendar.removeClass(el, "hilite");
			cal.callCloseHandler();
			return;
		}
		date = new Date(cal.date);
		if (el.navtype == 0)
			date.setDateOnly(new Date()); // TODAY
		// unless "today" was clicked, we assume no date was clicked so
		// the selected handler will know not to close the calenar when
		// in single-click mode.
		// cal.dateClicked = (el.navtype == 0);
		cal.dateClicked = false;
		var year = date.getFullYear();
		var mon = date.getMonth();
		function setMonth(m) {
			var day = date.getDate();
			var max = date.getMonthDays(m);
			if (day > max) {
				date.setDate(max);
			}
			date.setMonth(m);
		};
		switch (el.navtype) {
		    case 400:
			Calendar.removeClass(el, "hilite");
			var text = Calendar._TT["ABOUT"];
			if (typeof text != "undefined") {
				text += cal.showsTime ? Calendar._TT["ABOUT_TIME"] : "";
			} else {
				// FIXME: this should be removed as soon as lang files get updated!
				text = "Help and about box text is not translated into this language.\n" +
					"If you know this language and you feel generous please update\n" +
					"the corresponding file in \"lang\" subdir to match calendar-en.js\n" +
					"and send it back to <mihai_bazon@yahoo.com> to get it into the distribution  ;-)\n\n" +
					"Thank you!\n" +
					"http://dynarch.com/mishoo/calendar.epl\n";
			}
			alert(text);
			return;
		    case -2:
			if (year > cal.minYear) {
				date.setFullYear(year - 1);
			}
			break;
		    case -1:
			if (mon > 0) {
				setMonth(mon - 1);
			} else if (year-- > cal.minYear) {
				date.setFullYear(year);
				setMonth(11);
			}
			break;
		    case 1:
			if (mon < 11) {
				setMonth(mon + 1);
			} else if (year < cal.maxYear) {
				date.setFullYear(year + 1);
				setMonth(0);
			}
			break;
		    case 2:
			if (year < cal.maxYear) {
				date.setFullYear(year + 1);
			}
			break;
		    case 100:
			cal.setFirstDayOfWeek(el.fdow);
			return;
		    case 50:
			var range = el._range;
			var current = el.innerHTML;
			for (var i = range.length; --i >= 0;)
				if (range[i] == current)
					break;
			if (ev && ev.shiftKey) {
				if (--i < 0)
					i = range.length - 1;
			} else if ( ++i >= range.length )
				i = 0;
			var newval = range[i];
			el.innerHTML = newval;
			cal.onUpdateTime();
			return;
		    case 0:
			// TODAY will bring us here
			if ((typeof cal.getDateStatus == "function") &&
			    cal.getDateStatus(date, date.getFullYear(), date.getMonth(), date.getDate())) {
				return false;
			}
			break;
		}
		if (!date.equalsTo(cal.date)) {
			cal.setDate(date);
			newdate = true;
		} else if (el.navtype == 0)
			newdate = closing = true;
	}
	if (newdate) {
		ev && cal.callHandler();
	}
	if (closing) {
		Calendar.removeClass(el, "hilite");
		ev && cal.callCloseHandler();
	}
};

// END: CALENDAR STATIC FUNCTIONS

// BEGIN: CALENDAR OBJECT FUNCTIONS

/**
 *  This function creates the calendar inside the given parent.  If _par is
 *  null than it creates a popup calendar inside the BODY element.  If _par is
 *  an element, be it BODY, then it creates a non-popup calendar (still
 *  hidden).  Some properties need to be set before calling this function.
 */
Calendar.prototype.create = function (_par) {
	var parent = null;
	if (! _par) {
		// default parent is the document body, in which case we create
		// a popup calendar.
		parent = document.getElementsByTagName("body")[0];
		this.isPopup = true;
	} else {
		parent = _par;
		this.isPopup = false;
	}
	this.date = this.dateStr ? new Date(this.dateStr) : new Date();

	var table = Calendar.createElement("table");
	this.table = table;
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.calendar = this;
	Calendar.addEvent(table, "mousedown", Calendar.tableMouseDown);

	var div = Calendar.createElement("div");
	this.element = div;
	div.className = "calendar";
	if (this.isPopup) {
		div.style.position = "absolute";
		div.style.display = "none";
	}
	div.appendChild(table);

	var thead = Calendar.createElement("thead", table);
	var cell = null;
	var row = null;

	var cal = this;
	var hh = function (text, cs, navtype) {
		cell = Calendar.createElement("td", row);
		cell.colSpan = cs;
		cell.className = "button";
		if (navtype != 0 && Math.abs(navtype) <= 2)
			cell.className += " nav";
		Calendar._add_evs(cell);
		cell.calendar = cal;
		cell.navtype = navtype;
		cell.innerHTML = "<div unselectable='on'>" + text + "</div>";
		return cell;
	};

	row = Calendar.createElement("tr", thead);
	var title_length = 6;
	(this.isPopup) && --title_length;
	(this.weekNumbers) && ++title_length;

	hh("?", 1, 400).ttip = Calendar._TT["INFO"];
	this.title = hh("", title_length, 300);
	this.title.className = "title";
	if (this.isPopup) {
		this.title.ttip = Calendar._TT["DRAG_TO_MOVE"];
		this.title.style.cursor = "move";
		hh("&#x00d7;", 1, 200).ttip = Calendar._TT["CLOSE"];
	}

	row = Calendar.createElement("tr", thead);
	row.className = "headrow";

	this._nav_py = hh("&#x00ab;", 1, -2);
	this._nav_py.ttip = Calendar._TT["PREV_YEAR"];

	this._nav_pm = hh("&#x2039;", 1, -1);
	this._nav_pm.ttip = Calendar._TT["PREV_MONTH"];

	this._nav_now = hh(Calendar._TT["TODAY"], this.weekNumbers ? 4 : 3, 0);
	this._nav_now.ttip = Calendar._TT["GO_TODAY"];

	this._nav_nm = hh("&#x203a;", 1, 1);
	this._nav_nm.ttip = Calendar._TT["NEXT_MONTH"];

	this._nav_ny = hh("&#x00bb;", 1, 2);
	this._nav_ny.ttip = Calendar._TT["NEXT_YEAR"];

	// day names
	row = Calendar.createElement("tr", thead);
	row.className = "daynames";
	if (this.weekNumbers) {
		cell = Calendar.createElement("td", row);
		cell.className = "name wn";
		cell.innerHTML = Calendar._TT["WK"];
	}
	for (var i = 7; i > 0; --i) {
		cell = Calendar.createElement("td", row);
		if (!i) {
			cell.navtype = 100;
			cell.calendar = this;
			Calendar._add_evs(cell);
		}
	}
	this.firstdayname = (this.weekNumbers) ? row.firstChild.nextSibling : row.firstChild;
	this._displayWeekdays();

	var tbody = Calendar.createElement("tbody", table);
	this.tbody = tbody;

	for (i = 6; i > 0; --i) {
		row = Calendar.createElement("tr", tbody);
		if (this.weekNumbers) {
			cell = Calendar.createElement("td", row);
		}
		for (var j = 7; j > 0; --j) {
			cell = Calendar.createElement("td", row);
			cell.calendar = this;
			Calendar._add_evs(cell);
		}
	}

	if (this.showsTime) {
		row = Calendar.createElement("tr", tbody);
		row.className = "time";

		cell = Calendar.createElement("td", row);
		cell.className = "time";
		cell.colSpan = 2;
		cell.innerHTML = Calendar._TT["TIME"] || "&nbsp;";

		cell = Calendar.createElement("td", row);
		cell.className = "time";
		cell.colSpan = this.weekNumbers ? 4 : 3;

		(function(){
			function makeTimePart(className, init, range_start, range_end) {
				var part = Calendar.createElement("span", cell);
				part.className = className;
				part.innerHTML = init;
				part.calendar = cal;
				part.ttip = Calendar._TT["TIME_PART"];
				part.navtype = 50;
				part._range = [];
				if (typeof range_start != "number")
					part._range = range_start;
				else {
					for (var i = range_start; i <= range_end; ++i) {
						var txt;
						if (i < 10 && range_end >= 10) txt = '0' + i;
						else txt = '' + i;
						part._range[part._range.length] = txt;
					}
				}
				Calendar._add_evs(part);
				return part;
			};
			var hrs = cal.date.getHours();
			var mins = cal.date.getMinutes();
			var t12 = !cal.time24;
			var pm = (hrs > 12);
			if (t12 && pm) hrs -= 12;
			var H = makeTimePart("hour", hrs, t12 ? 1 : 0, t12 ? 12 : 23);
			var span = Calendar.createElement("span", cell);
			span.innerHTML = ":";
			span.className = "colon";
			var M = makeTimePart("minute", mins, 0, 59);
			var AP = null;
			cell = Calendar.createElement("td", row);
			cell.className = "time";
			cell.colSpan = 2;
			if (t12)
				AP = makeTimePart("ampm", pm ? "pm" : "am", ["am", "pm"]);
			else
				cell.innerHTML = "&nbsp;";

			cal.onSetTime = function() {
				var pm, hrs = this.date.getHours(),
					mins = this.date.getMinutes();
				if (t12) {
					pm = (hrs >= 12);
					if (pm) hrs -= 12;
					if (hrs == 0) hrs = 12;
					AP.innerHTML = pm ? "pm" : "am";
				}
				H.innerHTML = (hrs < 10) ? ("0" + hrs) : hrs;
				M.innerHTML = (mins < 10) ? ("0" + mins) : mins;
			};

			cal.onUpdateTime = function() {
				var date = this.date;
				var h = parseInt(H.innerHTML, 10);
				if (t12) {
					if (/pm/i.test(AP.innerHTML) && h < 12)
						h += 12;
					else if (/am/i.test(AP.innerHTML) && h == 12)
						h = 0;
				}
				var d = date.getDate();
				var m = date.getMonth();
				var y = date.getFullYear();
				date.setHours(h);
				date.setMinutes(parseInt(M.innerHTML, 10));
				date.setFullYear(y);
				date.setMonth(m);
				date.setDate(d);
				this.dateClicked = false;
				this.callHandler();
			};
		})();
	} else {
		this.onSetTime = this.onUpdateTime = function() {};
	}

	var tfoot = Calendar.createElement("tfoot", table);

	row = Calendar.createElement("tr", tfoot);
	row.className = "footrow";

	cell = hh(Calendar._TT["SEL_DATE"], this.weekNumbers ? 8 : 7, 300);
	cell.className = "ttip";
	if (this.isPopup) {
		cell.ttip = Calendar._TT["DRAG_TO_MOVE"];
		cell.style.cursor = "move";
	}
	this.tooltips = cell;

	div = Calendar.createElement("div", this.element);
	this.monthsCombo = div;
	div.className = "combo";
	for (i = 0; i < Calendar._MN.length; ++i) {
		var mn = Calendar.createElement("div");
		mn.className = Calendar.is_ie ? "label-IEfix" : "label";
		mn.month = i;
		mn.innerHTML = Calendar._SMN[i];
		div.appendChild(mn);
	}

	div = Calendar.createElement("div", this.element);
	this.yearsCombo = div;
	div.className = "combo";
	for (i = 12; i > 0; --i) {
		var yr = Calendar.createElement("div");
		yr.className = Calendar.is_ie ? "label-IEfix" : "label";
		div.appendChild(yr);
	}

	this._init(this.firstDayOfWeek, this.date);
	parent.appendChild(this.element);
};

/** keyboard navigation, only for popup calendars */
Calendar._keyEvent = function(ev) {
	var cal = window._dynarch_popupCalendar;
	if (!cal || cal.multiple)
		return false;
	(Calendar.is_ie) && (ev = window.event);
	var act = (Calendar.is_ie || ev.type == "keypress"),
		K = ev.keyCode;
	if (ev.ctrlKey) {
		switch (K) {
		    case 37: // KEY left
			act && Calendar.cellClick(cal._nav_pm);
			break;
		    case 38: // KEY up
			act && Calendar.cellClick(cal._nav_py);
			break;
		    case 39: // KEY right
			act && Calendar.cellClick(cal._nav_nm);
			break;
		    case 40: // KEY down
			act && Calendar.cellClick(cal._nav_ny);
			break;
		    default:
			return false;
		}
	} else switch (K) {
	    case 32: // KEY space (now)
		Calendar.cellClick(cal._nav_now);
		break;
	    case 27: // KEY esc
		act && cal.callCloseHandler();
		break;
	    case 37: // KEY left
	    case 38: // KEY up
	    case 39: // KEY right
	    case 40: // KEY down
		if (act) {
			var prev, x, y, ne, el, step;
			prev = K == 37 || K == 38;
			step = (K == 37 || K == 39) ? 1 : 7;
			function setVars() {
				el = cal.currentDateEl;
				var p = el.pos;
				x = p & 15;
				y = p >> 4;
				ne = cal.ar_days[y][x];
			};setVars();
			function prevMonth() {
				var date = new Date(cal.date);
				date.setDate(date.getDate() - step);
				cal.setDate(date);
			};
			function nextMonth() {
				var date = new Date(cal.date);
				date.setDate(date.getDate() + step);
				cal.setDate(date);
			};
			while (1) {
				switch (K) {
				    case 37: // KEY left
					if (--x >= 0)
						ne = cal.ar_days[y][x];
					else {
						x = 6;
						K = 38;
						continue;
					}
					break;
				    case 38: // KEY up
					if (--y >= 0)
						ne = cal.ar_days[y][x];
					else {
						prevMonth();
						setVars();
					}
					break;
				    case 39: // KEY right
					if (++x < 7)
						ne = cal.ar_days[y][x];
					else {
						x = 0;
						K = 40;
						continue;
					}
					break;
				    case 40: // KEY down
					if (++y < cal.ar_days.length)
						ne = cal.ar_days[y][x];
					else {
						nextMonth();
						setVars();
					}
					break;
				}
				break;
			}
			if (ne) {
				if (!ne.disabled)
					Calendar.cellClick(ne);
				else if (prev)
					prevMonth();
				else
					nextMonth();
			}
		}
		break;
	    case 13: // KEY enter
		if (act)
			Calendar.cellClick(cal.currentDateEl, ev);
		break;
	    default:
		return false;
	}
	return Calendar.stopEvent(ev);
};

/**
 *  (RE)Initializes the calendar to the given date and firstDayOfWeek
 */
Calendar.prototype._init = function (firstDayOfWeek, date) {
	var today = new Date(),
		TY = today.getFullYear(),
		TM = today.getMonth(),
		TD = today.getDate();
	this.table.style.visibility = "hidden";
	var year = date.getFullYear();
	if (year < this.minYear) {
		year = this.minYear;
		date.setFullYear(year);
	} else if (year > this.maxYear) {
		year = this.maxYear;
		date.setFullYear(year);
	}
	this.firstDayOfWeek = firstDayOfWeek;
	this.date = new Date(date);
	var month = date.getMonth();
	var mday = date.getDate();
	var no_days = date.getMonthDays();

	// calendar voodoo for computing the first day that would actually be
	// displayed in the calendar, even if it's from the previous month.
	// WARNING: this is magic. ;-)
	date.setDate(1);
	var day1 = (date.getDay() - this.firstDayOfWeek) % 7;
	if (day1 < 0)
		day1 += 7;
	date.setDate(-day1);
	date.setDate(date.getDate() + 1);

	var row = this.tbody.firstChild;
	var MN = Calendar._SMN[month];
	var ar_days = this.ar_days = new Array();
	var weekend = Calendar._TT["WEEKEND"];
	var dates = this.multiple ? (this.datesCells = {}) : null;
	for (var i = 0; i < 6; ++i, row = row.nextSibling) {
		var cell = row.firstChild;
		if (this.weekNumbers) {
			cell.className = "day wn";
			cell.innerHTML = date.getWeekNumber();
			cell = cell.nextSibling;
		}
		row.className = "daysrow";
		var hasdays = false, iday, dpos = ar_days[i] = [];
		for (var j = 0; j < 7; ++j, cell = cell.nextSibling, date.setDate(iday + 1)) {
			iday = date.getDate();
			var wday = date.getDay();
			cell.className = "day";
			cell.pos = i << 4 | j;
			dpos[j] = cell;
			var current_month = (date.getMonth() == month);
			if (!current_month) {
				if (this.showsOtherMonths) {
					cell.className += " othermonth";
					cell.otherMonth = true;
				} else {
					cell.className = "emptycell";
					cell.innerHTML = "&nbsp;";
					cell.disabled = true;
					continue;
				}
			} else {
				cell.otherMonth = false;
				hasdays = true;
			}
			cell.disabled = false;
			cell.innerHTML = this.getDateText ? this.getDateText(date, iday) : iday;
			if (dates)
				dates[date.print("%Y%m%d")] = cell;
			if (this.getDateStatus) {
				var status = this.getDateStatus(date, year, month, iday);
				if (this.getDateToolTip) {
					var toolTip = this.getDateToolTip(date, year, month, iday);
					if (toolTip)
						cell.title = toolTip;
				}
				if (status === true) {
					cell.className += " disabled";
					cell.disabled = true;
				} else {
					if (/disabled/i.test(status))
						cell.disabled = true;
					cell.className += " " + status;
				}
			}
			if (!cell.disabled) {
				cell.caldate = new Date(date);
				cell.ttip = "_";
				if (!this.multiple && current_month
				    && iday == mday && this.hiliteToday) {
					cell.className += " selected";
					this.currentDateEl = cell;
				}
				if (date.getFullYear() == TY &&
				    date.getMonth() == TM &&
				    iday == TD) {
					cell.className += " today";
					cell.ttip += Calendar._TT["PART_TODAY"];
				}
				if (weekend.indexOf(wday.toString()) != -1)
					cell.className += cell.otherMonth ? " oweekend" : " weekend";
			}
		}
		if (!(hasdays || this.showsOtherMonths))
			row.className = "emptyrow";
	}
	this.title.innerHTML = Calendar._MN[month] + ", " + year;
	this.onSetTime();
	this.table.style.visibility = "visible";
	this._initMultipleDates();
	// PROFILE
	// this.tooltips.innerHTML = "Generated in " + ((new Date()) - today) + " ms";
};

Calendar.prototype._initMultipleDates = function() {
	if (this.multiple) {
		for (var i in this.multiple) {
			var cell = this.datesCells[i];
			var d = this.multiple[i];
			if (!d)
				continue;
			if (cell)
				cell.className += " selected";
		}
	}
};

Calendar.prototype._toggleMultipleDate = function(date) {
	if (this.multiple) {
		var ds = date.print("%Y%m%d");
		var cell = this.datesCells[ds];
		if (cell) {
			var d = this.multiple[ds];
			if (!d) {
				Calendar.addClass(cell, "selected");
				this.multiple[ds] = date;
			} else {
				Calendar.removeClass(cell, "selected");
				delete this.multiple[ds];
			}
		}
	}
};

Calendar.prototype.setDateToolTipHandler = function (unaryFunction) {
	this.getDateToolTip = unaryFunction;
};

/**
 *  Calls _init function above for going to a certain date (but only if the
 *  date is different than the currently selected one).
 */
Calendar.prototype.setDate = function (date) {
	if (!date.equalsTo(this.date)) {
		this._init(this.firstDayOfWeek, date);
	}
};

/**
 *  Refreshes the calendar.  Useful if the "disabledHandler" function is
 *  dynamic, meaning that the list of disabled date can change at runtime.
 *  Just * call this function if you think that the list of disabled dates
 *  should * change.
 */
Calendar.prototype.refresh = function () {
	this._init(this.firstDayOfWeek, this.date);
};

/** Modifies the "firstDayOfWeek" parameter (pass 0 for Synday, 1 for Monday, etc.). */
Calendar.prototype.setFirstDayOfWeek = function (firstDayOfWeek) {
	this._init(firstDayOfWeek, this.date);
	this._displayWeekdays();
};

/**
 *  Allows customization of what dates are enabled.  The "unaryFunction"
 *  parameter must be a function object that receives the date (as a JS Date
 *  object) and returns a boolean value.  If the returned value is true then
 *  the passed date will be marked as disabled.
 */
Calendar.prototype.setDateStatusHandler = Calendar.prototype.setDisabledHandler = function (unaryFunction) {
	this.getDateStatus = unaryFunction;
};

/** Customization of allowed year range for the calendar. */
Calendar.prototype.setRange = function (a, z) {
	this.minYear = a;
	this.maxYear = z;
};

/** Calls the first user handler (selectedHandler). */
Calendar.prototype.callHandler = function () {
	if (this.onSelected) {
		this.onSelected(this, this.date.print(this.dateFormat));
	}
};

/** Calls the second user handler (closeHandler). */
Calendar.prototype.callCloseHandler = function () {
	if (this.onClose) {
		this.onClose(this);
	}
	this.hideShowCovered();
};

/** Removes the calendar object from the DOM tree and destroys it. */
Calendar.prototype.destroy = function () {
	var el = this.element.parentNode;
	el.removeChild(this.element);
	Calendar._C = null;
	window._dynarch_popupCalendar = null;
};

/**
 *  Moves the calendar element to a different section in the DOM tree (changes
 *  its parent).
 */
Calendar.prototype.reparent = function (new_parent) {
	var el = this.element;
	el.parentNode.removeChild(el);
	new_parent.appendChild(el);
};

// This gets called when the user presses a mouse button anywhere in the
// document, if the calendar is shown.  If the click was outside the open
// calendar this function closes it.
Calendar._checkCalendar = function(ev) {
	var calendar = window._dynarch_popupCalendar;
	if (!calendar) {
		return false;
	}
	var el = Calendar.is_ie ? Calendar.getElement(ev) : Calendar.getTargetElement(ev);
	for (; el != null && el != calendar.element; el = el.parentNode);
	if (el == null) {
		// calls closeHandler which should hide the calendar.
		window._dynarch_popupCalendar.callCloseHandler();
		return Calendar.stopEvent(ev);
	}
};

/** Shows the calendar. */
Calendar.prototype.show = function () {
	var rows = this.table.getElementsByTagName("tr");
	for (var i = rows.length; i > 0;) {
		var row = rows[--i];
		Calendar.removeClass(row, "rowhilite");
		var cells = row.getElementsByTagName("td");
		for (var j = cells.length; j > 0;) {
			var cell = cells[--j];
			Calendar.removeClass(cell, "hilite");
			Calendar.removeClass(cell, "active");
		}
	}
	this.element.style.display = "block";
	this.hidden = false;
	if (this.isPopup) {
		window._dynarch_popupCalendar = this;
		Calendar.addEvent(document, "keydown", Calendar._keyEvent);
		Calendar.addEvent(document, "keypress", Calendar._keyEvent);
		Calendar.addEvent(document, "mousedown", Calendar._checkCalendar);
	}
	this.hideShowCovered();
};

/**
 *  Hides the calendar.  Also removes any "hilite" from the class of any TD
 *  element.
 */
Calendar.prototype.hide = function () {
	if (this.isPopup) {
		Calendar.removeEvent(document, "keydown", Calendar._keyEvent);
		Calendar.removeEvent(document, "keypress", Calendar._keyEvent);
		Calendar.removeEvent(document, "mousedown", Calendar._checkCalendar);
	}
	this.element.style.display = "none";
	this.hidden = true;
	this.hideShowCovered();
};

/**
 *  Shows the calendar at a given absolute position (beware that, depending on
 *  the calendar element style -- position property -- this might be relative
 *  to the parent's containing rectangle).
 */
Calendar.prototype.showAt = function (x, y) {
	var s = this.element.style;
	s.left = x + "px";
	s.top = y + "px";
	this.show();
};

/** Shows the calendar near a given element. */
Calendar.prototype.showAtElement = function (el, opts) {
	var self = this;
	var p = Calendar.getAbsolutePos(el);
	if (!opts || typeof opts != "string") {
		this.showAt(p.x, p.y + el.offsetHeight);
		return true;
	}
	function fixPosition(box) {
		if (box.x < 0)
			box.x = 0;
		if (box.y < 0)
			box.y = 0;
		var cp = document.createElement("div");
		var s = cp.style;
		s.position = "absolute";
		s.right = s.bottom = s.width = s.height = "0px";
		document.body.appendChild(cp);
		var br = Calendar.getAbsolutePos(cp);
		document.body.removeChild(cp);
		if (Calendar.is_ie) {
			br.y += document.body.scrollTop;
			br.x += document.body.scrollLeft;
		} else {
			br.y += window.scrollY;
			br.x += window.scrollX;
		}
		var tmp = box.x + box.width - br.x;
		if (tmp > 0) box.x -= tmp;
		tmp = box.y + box.height - br.y;
		if (tmp > 0) box.y -= tmp;
	};
	this.element.style.display = "block";
	Calendar.continuation_for_the_fucking_khtml_browser = function() {
		var w = self.element.offsetWidth;
		var h = self.element.offsetHeight;
		self.element.style.display = "none";
		var valign = opts.substr(0, 1);
		var halign = "l";
		if (opts.length > 1) {
			halign = opts.substr(1, 1);
		}
		// vertical alignment
		switch (valign) {
		    case "T": p.y -= h; break;
		    case "B": p.y += el.offsetHeight; break;
		    case "C": p.y += (el.offsetHeight - h) / 2; break;
		    case "t": p.y += el.offsetHeight - h; break;
		    case "b": break; // already there
		}
		// horizontal alignment
		switch (halign) {
		    case "L": p.x -= w; break;
		    case "R": p.x += el.offsetWidth; break;
		    case "C": p.x += (el.offsetWidth - w) / 2; break;
		    case "l": p.x += el.offsetWidth - w; break;
		    case "r": break; // already there
		}
		p.width = w;
		p.height = h + 40;
		self.monthsCombo.style.display = "none";
		fixPosition(p);
		self.showAt(p.x, p.y);
	};
	if (Calendar.is_khtml)
		setTimeout("Calendar.continuation_for_the_fucking_khtml_browser()", 10);
	else
		Calendar.continuation_for_the_fucking_khtml_browser();
};

/** Customizes the date format. */
Calendar.prototype.setDateFormat = function (str) {
	this.dateFormat = str;
};

/** Customizes the tooltip date format. */
Calendar.prototype.setTtDateFormat = function (str) {
	this.ttDateFormat = str;
};

/**
 *  Tries to identify the date represented in a string.  If successful it also
 *  calls this.setDate which moves the calendar to the given date.
 */
Calendar.prototype.parseDate = function(str, fmt) {
	if (!fmt)
		fmt = this.dateFormat;
	this.setDate(Date.parseDate(str, fmt));
};

Calendar.prototype.hideShowCovered = function () {
	if (!Calendar.is_ie && !Calendar.is_opera)
		return;
	function getVisib(obj){
		var value = obj.style.visibility;
		if (!value) {
			if (document.defaultView && typeof (document.defaultView.getComputedStyle) == "function") { // Gecko, W3C
				if (!Calendar.is_khtml)
					value = document.defaultView.
						getComputedStyle(obj, "").getPropertyValue("visibility");
				else
					value = '';
			} else if (obj.currentStyle) { // IE
				value = obj.currentStyle.visibility;
			} else
				value = '';
		}
		return value;
	};

	var tags = new Array("applet", "iframe", "select");
	var el = this.element;

	var p = Calendar.getAbsolutePos(el);
	var EX1 = p.x;
	var EX2 = el.offsetWidth + EX1;
	var EY1 = p.y;
	var EY2 = el.offsetHeight + EY1;

	for (var k = tags.length; k > 0; ) {
		var ar = document.getElementsByTagName(tags[--k]);
		var cc = null;

		for (var i = ar.length; i > 0;) {
			cc = ar[--i];

			p = Calendar.getAbsolutePos(cc);
			var CX1 = p.x;
			var CX2 = cc.offsetWidth + CX1;
			var CY1 = p.y;
			var CY2 = cc.offsetHeight + CY1;

			if (this.hidden || (CX1 > EX2) || (CX2 < EX1) || (CY1 > EY2) || (CY2 < EY1)) {
				if (!cc.__msh_save_visibility) {
					cc.__msh_save_visibility = getVisib(cc);
				}
				cc.style.visibility = cc.__msh_save_visibility;
			} else {
				if (!cc.__msh_save_visibility) {
					cc.__msh_save_visibility = getVisib(cc);
				}
				cc.style.visibility = "hidden";
			}
		}
	}
};

/** Internal function; it displays the bar with the names of the weekday. */
Calendar.prototype._displayWeekdays = function () {
	var fdow = this.firstDayOfWeek;
	var cell = this.firstdayname;
	var weekend = Calendar._TT["WEEKEND"];
	for (var i = 0; i < 7; ++i) {
		cell.className = "day name";
		var realday = (i + fdow) % 7;
		if (i) {
			cell.ttip = Calendar._TT["DAY_FIRST"].replace("%s", Calendar._DN[realday]);
			cell.navtype = 100;
			cell.calendar = this;
			cell.fdow = realday;
			Calendar._add_evs(cell);
		}
		if (weekend.indexOf(realday.toString()) != -1) {
			Calendar.addClass(cell, "weekend");
		}
		cell.innerHTML = Calendar._SDN[(i + fdow) % 7];
		cell = cell.nextSibling;
	}
};

/** Internal function.  Hides all combo boxes that might be displayed. */
Calendar.prototype._hideCombos = function () {
	this.monthsCombo.style.display = "none";
	this.yearsCombo.style.display = "none";
};

/** Internal function.  Starts dragging the element. */
Calendar.prototype._dragStart = function (ev) {
	if (this.dragging) {
		return;
	}
	this.dragging = true;
	var posX;
	var posY;
	if (Calendar.is_ie) {
		posY = window.event.clientY + document.body.scrollTop;
		posX = window.event.clientX + document.body.scrollLeft;
	} else {
		posY = ev.clientY + window.scrollY;
		posX = ev.clientX + window.scrollX;
	}
	var st = this.element.style;
	this.xOffs = posX - parseInt(st.left);
	this.yOffs = posY - parseInt(st.top);
	with (Calendar) {
		addEvent(document, "mousemove", calDragIt);
		addEvent(document, "mouseup", calDragEnd);
	}
};

// BEGIN: DATE OBJECT PATCHES

/** Adds the number of days array to the Date object. */
Date._MD = new Array(31,28,31,30,31,30,31,31,30,31,30,31);

/** Constants used for time computations */
Date.SECOND = 1000 /* milliseconds */;
Date.MINUTE = 60 * Date.SECOND;
Date.HOUR   = 60 * Date.MINUTE;
Date.DAY    = 24 * Date.HOUR;
Date.WEEK   =  7 * Date.DAY;

Date.parseDate = function(str, fmt) {
	var today = new Date();
	var y = 0;
	var m = -1;
	var d = 0;
	var a = str.split(/\W+/);
	var b = fmt.match(/%./g);
	var i = 0, j = 0;
	var hr = 0;
	var min = 0;
	for (i = 0; i < a.length; ++i) {
		if (!a[i])
			continue;
		switch (b[i]) {
		    case "%d":
		    case "%e":
			d = parseInt(a[i], 10);
			break;

		    case "%m":
			m = parseInt(a[i], 10) - 1;
			break;

		    case "%Y":
		    case "%y":
			y = parseInt(a[i], 10);
			(y < 100) && (y += (y > 29) ? 1900 : 2000);
			break;

		    case "%b":
		    case "%B":
			for (j = 0; j < 12; ++j) {
				if (Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { m = j; break; }
			}
			break;

		    case "%H":
		    case "%I":
		    case "%k":
		    case "%l":
			hr = parseInt(a[i], 10);
			break;

		    case "%P":
		    case "%p":
			if (/pm/i.test(a[i]) && hr < 12)
				hr += 12;
			else if (/am/i.test(a[i]) && hr >= 12)
				hr -= 12;
			break;

		    case "%M":
			min = parseInt(a[i], 10);
			break;
		}
	}
	if (isNaN(y)) y = today.getFullYear();
	if (isNaN(m)) m = today.getMonth();
	if (isNaN(d)) d = today.getDate();
	if (isNaN(hr)) hr = today.getHours();
	if (isNaN(min)) min = today.getMinutes();
	if (y != 0 && m != -1 && d != 0)
		return new Date(y, m, d, hr, min, 0);
	y = 0; m = -1; d = 0;
	for (i = 0; i < a.length; ++i) {
		if (a[i].search(/[a-zA-Z]+/) != -1) {
			var t = -1;
			for (j = 0; j < 12; ++j) {
				if (Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { t = j; break; }
			}
			if (t != -1) {
				if (m != -1) {
					d = m+1;
				}
				m = t;
			}
		} else if (parseInt(a[i], 10) <= 12 && m == -1) {
			m = a[i]-1;
		} else if (parseInt(a[i], 10) > 31 && y == 0) {
			y = parseInt(a[i], 10);
			(y < 100) && (y += (y > 29) ? 1900 : 2000);
		} else if (d == 0) {
			d = a[i];
		}
	}
	if (y == 0)
		y = today.getFullYear();
	if (m != -1 && d != 0)
		return new Date(y, m, d, hr, min, 0);
	return today;
};

/** Returns the number of days in the current month */
Date.prototype.getMonthDays = function(month) {
	var year = this.getFullYear();
	if (typeof month == "undefined") {
		month = this.getMonth();
	}
	if (((0 == (year%4)) && ( (0 != (year%100)) || (0 == (year%400)))) && month == 1) {
		return 29;
	} else {
		return Date._MD[month];
	}
};

/** Returns the number of day in the year. */
Date.prototype.getDayOfYear = function() {
	var now = new Date(this.getFullYear(), this.getMonth(), this.getDate(), 0, 0, 0);
	var then = new Date(this.getFullYear(), 0, 0, 0, 0, 0);
	var time = now - then;
	return Math.floor(time / Date.DAY);
};

/** Returns the number of the week in year, as defined in ISO 8601. */
Date.prototype.getWeekNumber = function() {
	var d = new Date(this.getFullYear(), this.getMonth(), this.getDate(), 0, 0, 0);
	var DoW = d.getDay();
	d.setDate(d.getDate() - (DoW + 6) % 7 + 3); // Nearest Thu
	var ms = d.valueOf(); // GMT
	d.setMonth(0);
	d.setDate(4); // Thu in Week 1
	return Math.round((ms - d.valueOf()) / (7 * 864e5)) + 1;
};

/** Checks date and time equality */
Date.prototype.equalsTo = function(date) {
	return ((this.getFullYear() == date.getFullYear()) &&
		(this.getMonth() == date.getMonth()) &&
		(this.getDate() == date.getDate()) &&
		(this.getHours() == date.getHours()) &&
		(this.getMinutes() == date.getMinutes()));
};

/** Set only the year, month, date parts (keep existing time) */
Date.prototype.setDateOnly = function(date) {
	var tmp = new Date(date);
	this.setDate(1);
	this.setFullYear(tmp.getFullYear());
	this.setMonth(tmp.getMonth());
	this.setDate(tmp.getDate());
};

/** Prints the date in a string according to the given format. */
Date.prototype.print = function (str) {
	var m = this.getMonth();
	var d = this.getDate();
	var y = this.getFullYear();
	var wn = this.getWeekNumber();
	var w = this.getDay();
	var s = {};
	var hr = this.getHours();
	var pm = (hr >= 12);
	var ir = (pm) ? (hr - 12) : hr;
	var dy = this.getDayOfYear();
	if (ir == 0)
		ir = 12;
	var min = this.getMinutes();
	var sec = this.getSeconds();
	s["%a"] = Calendar._SDN[w]; // abbreviated weekday name [FIXME: I18N]
	s["%A"] = Calendar._DN[w]; // full weekday name
	s["%b"] = Calendar._SMN[m]; // abbreviated month name [FIXME: I18N]
	s["%B"] = Calendar._MN[m]; // full month name
	// FIXME: %c : preferred date and time representation for the current locale
	s["%C"] = 1 + Math.floor(y / 100); // the century number
	s["%d"] = (d < 10) ? ("0" + d) : d; // the day of the month (range 01 to 31)
	s["%e"] = d; // the day of the month (range 1 to 31)
	// FIXME: %D : american date style: %m/%d/%y
	// FIXME: %E, %F, %G, %g, %h (man strftime)
	s["%H"] = (hr < 10) ? ("0" + hr) : hr; // hour, range 00 to 23 (24h format)
	s["%I"] = (ir < 10) ? ("0" + ir) : ir; // hour, range 01 to 12 (12h format)
	s["%j"] = (dy < 100) ? ((dy < 10) ? ("00" + dy) : ("0" + dy)) : dy; // day of the year (range 001 to 366)
	s["%k"] = hr;		// hour, range 0 to 23 (24h format)
	s["%l"] = ir;		// hour, range 1 to 12 (12h format)
	s["%m"] = (m < 9) ? ("0" + (1+m)) : (1+m); // month, range 01 to 12
	s["%M"] = (min < 10) ? ("0" + min) : min; // minute, range 00 to 59
	s["%n"] = "\n";		// a newline character
	s["%p"] = pm ? "PM" : "AM";
	s["%P"] = pm ? "pm" : "am";
	// FIXME: %r : the time in am/pm notation %I:%M:%S %p
	// FIXME: %R : the time in 24-hour notation %H:%M
	s["%s"] = Math.floor(this.getTime() / 1000);
	s["%S"] = (sec < 10) ? ("0" + sec) : sec; // seconds, range 00 to 59
	s["%t"] = "\t";		// a tab character
	// FIXME: %T : the time in 24-hour notation (%H:%M:%S)
	s["%U"] = s["%W"] = s["%V"] = (wn < 10) ? ("0" + wn) : wn;
	s["%u"] = w + 1;	// the day of the week (range 1 to 7, 1 = MON)
	s["%w"] = w;		// the day of the week (range 0 to 6, 0 = SUN)
	// FIXME: %x : preferred date representation for the current locale without the time
	// FIXME: %X : preferred time representation for the current locale without the date
	s["%y"] = ('' + y).substr(2, 2); // year without the century (range 00 to 99)
	s["%Y"] = y;		// year with the century
	s["%%"] = "%";		// a literal '%' character

	var re = /%./g;
	if (!Calendar.is_ie5 && !Calendar.is_khtml)
		return str.replace(re, function (par) { return s[par] || par; });

	var a = str.match(re);
	for (var i = 0; i < a.length; i++) {
		var tmp = s[a[i]];
		if (tmp) {
			re = new RegExp(a[i], 'g');
			str = str.replace(re, tmp);
		}
	}

	return str;
};

Date.prototype.__msh_oldSetFullYear = Date.prototype.setFullYear;
Date.prototype.setFullYear = function(y) {
	var d = new Date(this);
	d.__msh_oldSetFullYear(y);
	if (d.getMonth() != this.getMonth())
		this.setDate(28);
	this.__msh_oldSetFullYear(y);
};

// END: DATE OBJECT PATCHES


// global object that remembers the calendar
window._dynarch_popupCalendar = null;
/*  Copyright Mihai Bazon, 2002, 2003  |  http://dynarch.com/mishoo/
 * ---------------------------------------------------------------------------
 *
 * The DHTML Calendar
 *
 * Details and latest version at:
 * http://dynarch.com/mishoo/calendar.epl
 *
 * This script is distributed under the GNU Lesser General Public License.
 * Read the entire license text here: http://www.gnu.org/licenses/lgpl.html
 *
 * This file defines helper functions for setting up the calendar.  They are
 * intended to help non-programmers get a working calendar on their site
 * quickly.  This script should not be seen as part of the calendar.  It just
 * shows you what one can do with the calendar, while in the same time
 * providing a quick and simple method for setting it up.  If you need
 * exhaustive customization of the calendar creation process feel free to
 * modify this code to suit your needs (this is recommended and much better
 * than modifying calendar.js itself).
 */

// $Id: calendar-setup.js,v 1.4 2008-07-28 13:04:14 luis Exp $

/**
 *  This function "patches" an input field (or other element) to use a calendar
 *  widget for date selection.
 *
 *  The "params" is a single object that can have the following properties:
 *
 *    prop. name   | description
 *  -------------------------------------------------------------------------------------------------
 *   inputField    | the ID of an input field to store the date
 *   displayArea   | the ID of a DIV or other element to show the date
 *   button        | ID of a button or other element that will trigger the calendar
 *   eventName     | event that will trigger the calendar, without the "on" prefix (default: "click")
 *   ifFormat      | date format that will be stored in the input field
 *   daFormat      | the date format that will be used to display the date in displayArea
 *   singleClick   | (true/false) wether the calendar is in single click mode or not (default: true)
 *   firstDay      | numeric: 0 to 6.  "0" means display Sunday first, "1" means display Monday first, etc.
 *   align         | alignment (default: "Br"); if you don't know what's this see the calendar documentation
 *   range         | array with 2 elements.  Default: [1900, 2999] -- the range of years available
 *   weekNumbers   | (true/false) if it's true (default) the calendar will display week numbers
 *   flat          | null or element ID; if not null the calendar will be a flat calendar having the parent with the given ID
 *   flatCallback  | function that receives a JS Date object and returns an URL to point the browser to (for flat calendar)
 *   disableFunc   | function that receives a JS Date object and should return true if that date has to be disabled in the calendar
 *   onSelect      | function that gets called when a date is selected.  You don't _have_ to supply this (the default is generally okay)
 *   onClose       | function that gets called when the calendar is closed.  [default]
 *   onUpdate      | function that gets called after the date is updated in the input field.  Receives a reference to the calendar.
 *   date          | the date that the calendar will be initially displayed to
 *   showsTime     | default: false; if true the calendar will include a time selector
 *   timeFormat    | the time format; can be "12" or "24", default is "12"
 *   electric      | if true (default) then given fields/date areas are updated for each move; otherwise they're updated only on close
 *   step          | configures the step of the years in drop-down boxes; default: 2
 *   position      | configures the calendar absolute position; default: null
 *   cache         | if "true" (but default: "false") it will reuse the same calendar object, where possible
 *   showOthers    | if "true" (but default: "false") it will show days from other months too
 *
 *  None of them is required, they all have default values.  However, if you
 *  pass none of "inputField", "displayArea" or "button" you'll get a warning
 *  saying "nothing to setup".
 */
Calendar.setup = function (params) {
	function param_default(pname, def) { if (typeof params[pname] == "undefined") { params[pname] = def; } };

	param_default("inputField",     null);
	param_default("displayArea",    null);
	param_default("button",         null);
	param_default("eventName",      "click");
	param_default("ifFormat",       "%Y/%m/%d");
	param_default("daFormat",       "%Y/%m/%d");
	param_default("singleClick",    true);
	param_default("disableFunc",    null);
	param_default("dateStatusFunc", params["disableFunc"]);	// takes precedence if both are defined
	param_default("dateText",       null);
	param_default("firstDay",       null);
	param_default("align",          "Br");
	param_default("range",          [1900, 2999]);
	param_default("weekNumbers",    true);
	param_default("flat",           null);
	param_default("flatCallback",   null);
	param_default("onSelect",       null);
	param_default("onClose",        null);
	param_default("onUpdate",       null);
	param_default("date",           null);
	param_default("showsTime",      false);
	param_default("timeFormat",     "24");
	param_default("electric",       true);
	param_default("step",           2);
	param_default("position",       null);
	param_default("cache",          false);
	param_default("showOthers",     false);
	param_default("multiple",       null);

	var tmp = ["inputField", "displayArea", "button"];
	for (var i in tmp) {
		if (typeof params[tmp[i]] == "string") {
			params[tmp[i]] = document.getElementById(params[tmp[i]]);
		}
	}
	if (!(params.flat || params.multiple || params.inputField || params.displayArea || params.button)) {
		alert("Calendar.setup:\n  Nothing to setup (no fields found).  Please check your code");
		return false;
	}

	function onSelect(cal) {
		var p = cal.params;
		var update = (cal.dateClicked || p.electric);
		if (update && p.inputField) {
			p.inputField.value = cal.date.print(p.ifFormat);
			if (typeof p.inputField.onchange == "function")
				p.inputField.onchange();
		}
		if (update && p.displayArea)
			p.displayArea.innerHTML = cal.date.print(p.daFormat);
		if (update && typeof p.onUpdate == "function")
			p.onUpdate(cal);
		if (update && p.flat) {
			if (typeof p.flatCallback == "function")
				p.flatCallback(cal);
		}
		if (update && p.singleClick && cal.dateClicked)
			cal.callCloseHandler();
	};

	if (params.flat != null) {
		if (typeof params.flat == "string")
			params.flat = document.getElementById(params.flat);
		if (!params.flat) {
			alert("Calendar.setup:\n  Flat specified but can't find parent.");
			return false;
		}
		var cal = new Calendar(params.firstDay, params.date, params.onSelect || onSelect);
		cal.showsOtherMonths = params.showOthers;
		cal.showsTime = params.showsTime;
		cal.time24 = (params.timeFormat == "24");
		cal.params = params;
		cal.weekNumbers = params.weekNumbers;
		cal.setRange(params.range[0], params.range[1]);
		cal.setDateStatusHandler(params.dateStatusFunc);
		cal.getDateText = params.dateText;
		if (params.ifFormat) {
			cal.setDateFormat(params.ifFormat);
		}
		if (params.inputField && typeof params.inputField.value == "string") {
			cal.parseDate(params.inputField.value);
		}
		cal.create(params.flat);
		cal.show();
		return false;
	}

	var triggerEl = params.button || params.displayArea || params.inputField;
	triggerEl["on" + params.eventName] = function() {
		var dateEl = params.inputField || params.displayArea;
		var dateFmt = params.inputField ? params.ifFormat : params.daFormat;
		var mustCreate = false;
		var cal = window.calendar;
		if (dateEl) {
			if (dateEl.disabled || dateEl.readOnly) {
				return false;
			}
			params.date = Date.parseDate(dateEl.value || dateEl.innerHTML, dateFmt);
		}
		if (!(cal && params.cache)) {
			window.calendar = cal = new Calendar(params.firstDay,
							     params.date,
							     params.onSelect || onSelect,
							     params.onClose || function(cal) { cal.hide(); });
			cal.showsTime = params.showsTime;
			cal.time24 = (params.timeFormat == "24");
			cal.weekNumbers = params.weekNumbers;
			mustCreate = true;
		} else {
			if (params.date)
				cal.setDate(params.date);
			cal.hide();
		}
		if (params.multiple) {
			cal.multiple = {};
			for (var i = params.multiple.length; --i >= 0;) {
				var d = params.multiple[i];
				var ds = d.print("%Y%m%d");
				cal.multiple[ds] = d;
			}
		}
		cal.showsOtherMonths = params.showOthers;
		cal.yearStep = params.step;
		cal.setRange(params.range[0], params.range[1]);
		cal.params = params;
		cal.setDateStatusHandler(params.dateStatusFunc);
		cal.getDateText = params.dateText;
		cal.setDateFormat(dateFmt);
		if (mustCreate)
			cal.create();
		cal.refresh();
		if (!params.position)
			cal.showAtElement(params.button || params.displayArea || params.inputField, params.align);
		else
			cal.showAt(params.position[0], params.position[1]);
		return false;
	};

	return cal;
};
