
/**
 * @fileoverview Miscellaneous String utility methods.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 * @link https://developers.google.com/closure/compiler/docs/js-for-compiler
 */



/**
 * Miscellaneous String utility methods.
 * @link http://static.springsource.org/spring/docs/2.5.x/api/org/
 *     springframework/util/StringUtils.html
 * @constructor
 */
util.StringUtils = function() {return null;};


/**
 * Trims leading and trailing whitespace from the given String.
 * @param {string} str The String to check.
 * @return {string} Returns the trimmed String.
 * @static
 */
util.StringUtils.trimWhitespace = function(str) {
  return str.trim && str.trim() || util.StringUtils.trimTrailingWhitespace(
      util.StringUtils.trimLeadingWhitespace(str));
};


/**
 * Trims leading whitespace from the given String.
 * @param {string} str The String to check.
 * @return {string} Returns the trimmed String.
 * @static
 */
util.StringUtils.trimLeadingWhitespace = function(str) {
  return str.trimLeft && str.trimLeft() || str.replace(/^\s+/g, '');
};


/**
 * Trims trailing whitespace from the given String.
 * @param {string} str The String to check.
 * @return {string} Returns the trimmed String.
 * @static
 */
util.StringUtils.trimTrailingWhitespace = function(str) {
  return str.trimRight && str.trimRight() || str.replace(/\s+$/g, '');
};


/**
 * Converts HTML to plain text.
 * @param {string} str The input string.
 * @return {string} Converted string.
 * @static
 */
util.StringUtils.toPlainText = function(str) {
  return str.replace(/<|>/gm, function(m) {
    return '&' + (m == '<' ? 'l' : 'g') + 't;';
  });
};


/**
 * Converts <code>obj</code> to query string.
 * @param {Object} obj The key-value pairs object.
 * @param {string=} opt_prefix Optional query prefix.
 * @return {string} Returns query string or empty string if no parameters
 *     given.
 * @static
 */
util.StringUtils.toQueryString = function(obj, opt_prefix) {
  /** @type {string} */ var result = opt_prefix || '?';
  for (/** @type {string} */ var key in obj) {
    result += util.StringUtils.URI.encode(key) + '=' +
        util.StringUtils.URI.encode(obj[key]) + '&';
  }
  return result.slice(0, -1);
};



/**
 * Methods to manipulate with UTF-8 strings.
 * @constructor
 * @static
 */
util.StringUtils.UTF8 = function() {return null;};


/**
 * @param {string} str String to encode.
 * @return {string} Returns encoded string.
 * @static
 */
util.StringUtils.UTF8.encode = function(str) {
  return unescape(util.StringUtils.URI.encode(str));
};


/**
 * @param {string} str String to decode.
 * @return {string} Returns decoded string.
 * @static
 */
util.StringUtils.UTF8.decode = function(str) {
  return util.StringUtils.URI.decode(escape(str));
};



/**
 * Methods to manipulate with URI strings.
 * @constructor
 * @static
 */
util.StringUtils.URI = function() {return null;};


/**
 * @param {string} str String to encode.
 * @return {string} Returns encoded string.
 * @static
 */
util.StringUtils.URI.encode = function(str) {
  return (encodeURIComponent || escape)(str);
};


/**
 * @param {string} str String to decode.
 * @return {string} Returns decoded string.
 * @static
 */
util.StringUtils.URI.decode = function(str) {
  return (decodeURIComponent || unescape)(str);
};



/**
 * Base64 utils.
 * @constructor
 */
util.StringUtils.Base64 = function() { return null; };


/**
 * @const
 * @type {string}
 */
util.StringUtils.Base64.CHARACTER_TABLE =
    'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';


/**
 * Encodes string to base64.
 * @param {string} str String to encode.
 * @return {string} Returns encoded string.
 * @static
 */
util.StringUtils.Base64.encode = function(str) {
  if (window.btoa) {
    return window.btoa(str);
  }

  /** @type {!Array.<string>} */
  var table = util.StringUtils.Base64.CHARACTER_TABLE.split('');
  /** @type {!Array.<string>} */ var buffer = str.split('');
  /** @type {number} */ var block = 0;
  /** @type {number} */ var index = 0;
  /** @type {string} */ var result = '';

  for (; buffer[index | 0] || (table = ['='], index % 1);
      result += table[63 & block >> 8 - index % 1 * 8])
    block = block << 8 | str.charCodeAt(index -= -3 / 4);

  return result;
};


/**
 * Decodes base64-encoded string.
 * @param {string} str Encoded string.
 * @return {string} Returns decoded string.
 * @static
 */
util.StringUtils.Base64.decode = function(str) {
  if (window.atob) {
    return window.atob(str);
  }

  /** @type {!Array.<string>} */ var buffer = str.split('');
  /** @type {string} */ var result = '';
  /** @type {number} */ var bit = 0;
  /** @type {number} */ var counter = 0;
  /** @type {number} */ var index = 0;
  /** @type {string} */ var character = '';

  for (/** @type {number} */ var i = 0; character = buffer[i++];) {
    index = util.StringUtils.Base64.CHARACTER_TABLE.indexOf(character);
    if (~index) {
      bit = counter % 4 ? bit * 64 + index : index;
      if (counter++ % 4) {
        result += String.fromCharCode(255 & bit >> (-2 * counter & 6));
      }
    }
  }
  return result;
};



/**
 * Simple implementation of JSON methods.
 * @constructor
 */
util.StringUtils.JSON = function() {return null};


/**
 * This method parses a JSON text to produce an object or array.
 * @param {string} value String to parse.
 * @return {?Object} Returns parsed object from string.
 * @static
 */
util.StringUtils.JSON.parse = function(value) {
  return /** @type {Object} */ ((window.JSON ?
      JSON.parse(value) : eval('(' + value + ')')) || null);
};


/**
 * This method produces a JSON text from a JavaScript value.
 * @param {*} obj Any JavaScript value, usually an object or array.
 * @return {string} Returns serialized object as string.
 * @static
 */
util.StringUtils.JSON.stringify = function(obj) {
  if (window.JSON) return JSON.stringify(obj);

  /** @type {string} */ var type = typeof obj;
  if (type != 'object' || obj === null) {
    return type == 'string' ? '"' + obj + '"' : '' + obj;
  } else {
    /** @type {Array.<string>} */ var buffer = [];
    /** @type {boolean} */
    var isArray = /** @type {boolean} */ (obj && obj instanceof Array);
    for (/** @type {string} */ var key in obj) {
      /** @type {string|Object} */ var value = obj[key];
      type = typeof value;
      if (type == 'string') {
        value = '"' + value + '"';
      } else if (type == 'object' && value !== null) {
        value = util.StringUtils.JSON.stringify(/** @type {Object}*/ (value));
      }
      buffer.push((isArray ? '' : '"' + key + '":') + value);
    }

    return (isArray ? '[' : '{') + buffer + (isArray ? ']' : '}');
  }
};


/**
 * Converts string to a byte array.
 * @param {string} str The input string.
 * @return {!Array.<number>} Returns byte array.
 */
util.StringUtils.toByteArray = function(str) {
  /** @type {!Array.<number>} */ var result = [];
  for (/** @type {number} */ var i = 0; i < str.length; i++) {
    /** @type {number} */ var code = str.charCodeAt(i);
    if (code < 128) {
      result.push(code);
    } else if (code < 2048) {
      result.push((code >> 6) | 192);
      result.push((code & 63) | 128);
    } else if (code <= 65535) {
      result.push((code >> 12) | 224); // 192 + 32
      result.push(((code >> 6) & 63) | 128);
      result.push((code & 63) | 128);
    } else {
      result.push((code >> 18) | 240); // 224 + 16
      result.push(((code >> 12) & 63) | 128);
      result.push(((code >> 6) & 63) | 128);
      result.push((code & 63) | 128);
    }
  }
  return result;
};



/**
 * LZW compression utility.
 * @link http://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch
 * @constructor
 */
util.StringUtils.LZW = function() { return null; };


/**
 * Encodes string using LZW algorithm.
 * @param {string} str The input string.
 * @return {string} Returns compressed string using LZW algorithm.
 */
util.StringUtils.LZW.encode = function(str) {
  /** @type {!Object.<string, number>} */ var dict = {};
  /** @type {!Array.<string>} */ var data = str.split('');
  /** @type {!Array} */ var out = [];
  /** @type {number} */ var code = 256;
  /** @type {string} */ var phrase = data.shift();
  while (data.length) {
    /** @type {string} */ var next = data.shift();
    if (dict[phrase + next] != null) {
      phrase += next;
    } else {
      out.push(phrase.length > 1 ? dict[phrase] : phrase.charCodeAt(0));
      dict[phrase + next] = code++;
      phrase = next;
    }
  }
  out.push(phrase.length > 1 ? dict[phrase] : phrase.charCodeAt(0));
  for (/** @type {number} */ var i = 0; i < out.length; i++) {
    out[i] = String.fromCharCode(/** @type {number} */ (out[i]));
  }
  return out.join('');
};


/**
 * Decodes string encoded with LZW algorithm.
 * @param {string} str The input string encoded with LZW algorithm.
 * @return {string} Returns decoded string.
 */
util.StringUtils.LZW.decode = function(str) {
  /** @type {!Object} */ var dict = {};
  /** @type {!Array.<string>} */ var data = str.split('');
  /** @type {!Array.<string>} */ var out = [data.shift()];
  /** @type {number} */ var code = 256;
  /** @type {string} */ var chr = out[0];
  /** @type {string} */ var tmp = chr;
  for (/** @type {number} */ var i = 0; i < data.length; i++) {
    /** @type {number} */ var next = data[i].charCodeAt(0);
    /** @type {string} */ var phrase =
        next < 256 ? data[i] : (dict[next] ? dict[next] : (tmp + chr));
    out.push(phrase);
    chr = phrase.charAt(0);
    dict[code++] = tmp + chr;
    tmp = phrase;
  }
  return out.join('');
};
