// PLEASE DO NOT EDIT CODEBASE SOURCE FILES.
// @link http://svn.sethq.com/acxiom/codebase/src/scripts/util/StringUtils.js

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
function StringUtils() {return null;}


/**
 * Wrap given string with quotes.
 * Use given quote symbol if provided.
 * Returns empty quoted string if no input string provided.
 * Sample usage:<br/>
 * <code>
 *  <p>StringUtils.quote('abc'); // -> '"abc"';</p>
 *  <p>StringUtils.quote('abc', '&lt;'); // -> '&lt;abc&lt;';</p>
 *  <p>StringUtils.quote(); // -> '""';</p>
 * </code>
 * @param {string=} opt_str String.
 * @param {string=} opt_quote Optional quotes mark. Default is '"'.
 * @return {string} Quoted string.
 * @static
 */
StringUtils.quote = function(opt_str, opt_quote) {
  opt_quote || (opt_quote = '"');
  return opt_quote + (opt_str || '') + opt_quote;
};


/**
 * Trims leading and trailing whitespace from the given String.
 * @param {string} str The String to check.
 * @return {string} Returns the trimmed String.
 * @static
 */
StringUtils.trimWhitespace = function(str) {
  return 'trim' in String.prototype ?
      str.trim() : StringUtils.trimTrailingWhitespace(
      StringUtils.trimLeadingWhitespace(str));
};


/**
 * Trims leading whitespace from the given String.
 * @param {string} str The String to check.
 * @return {string} Returns the trimmed String.
 * @static
 */
StringUtils.trimLeadingWhitespace = function(str) {
  return 'trimLeft' in String.prototype ?
      str.trimLeft() : str.replace(/^\s+/g, '');
};


/**
 * Trims trailing whitespace from the given String.
 * @param {string} str The String to check.
 * @return {string} Returns the trimmed String.
 * @static
 */
StringUtils.trimTrailingWhitespace = function(str) {
  return 'trimRight' in String.prototype ?
      str.trimRight() : str.replace(/\s+$/g, '');
};


/**
 * Converts HTML to plain text.
 * @param {string} str Input string.
 * @return {string} Converted string.
 * @static
 */
StringUtils.toPlainText = function(str) {
  return new Option(str).innerHTML;
};


/**
 * Converts <code>obj</code> to query string.
 * @param {Object} obj The key-value pairs object.
 * @return {string} Returns query string or empty string if no parameters given.
 * @static
 */
StringUtils.toQueryString = function(obj) {
  /** @type {string} */ var result = '?';
  for (/** @type {string} */ var key in obj) {
    result += encodeURIComponent(key) + '=' +
        encodeURIComponent(obj[key]) + '&';
  }
  return result.slice(0, -1);
};



/**
 * Methods to manipulate with UTF-8 strings.
 * @constructor
 * @static
 */
StringUtils.UTF8 = function() {return null;};


/**
 * @param {string} str String to encode.
 * @return {string} Returns encoded string.
 */
StringUtils.UTF8.encode = function(str) {
  return unescape(encodeURIComponent(str));
};


/**
 * @param {string} str String to decode.
 * @return {string} Returns decoded string.
 */
StringUtils.UTF8.decode = function(str) {
  return decodeURIComponent(escape(str));
};



/**
 * Base64 utils.
 * @constructor
 */
StringUtils.Base64 = function() { return null; };


/**
 * @const
 * @type {string}
 */
StringUtils.Base64.CHARACTER_TABLE =
    'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';


/**
 * Encodes string to base64.
 * @param {string} str String to encode.
 * @return {string} Returns encoded string.
 * @static
 */
StringUtils.Base64.encode = function(str) {
  if (window.btoa) {
    return window.btoa(str);
  }

  /** @type {!Array} */
  var table = StringUtils.Base64.CHARACTER_TABLE.split('');
  /** @type {!Array} */ var buffer = str.split('');
  /** @type {number} */ var block = 0;
  /** @type {number} */ var index = 0;
  /** @type {string} */ var result = '';

  for (; buffer[index | 0] || (table = ['='], index % 1);
      result += table[63 & block >> 8 - index % 1 * 8])
    block = block << 8 | str.charCodeAt(index -= -.75);

  return result;
};


/**
 * Decodes base64-encoded string.
 * @param {string} str Encoded string.
 * @return {string} Returns decoded string.
 * @static
 */
StringUtils.Base64.decode = function(str) {
  if (window.atob) {
    return window.atob(str);
  }

  /** @type {!Array} */ var buffer = str.split('');
  /** @type {string} */ var result = '';
  /** @type {number} */ var bit = 0;
  /** @type {number} */ var counter = 0;
  /** @type {number} */ var charBuffer = 0;
  /** @type {string} */ var character = '';

  for (/** @type {number} */ var i = 0; character = buffer[i++];) {
    charBuffer = StringUtils.Base64.CHARACTER_TABLE.indexOf(character);
    if (~charBuffer) {
      bit = counter % 4 ? bit * 64 + charBuffer : charBuffer;
      if (counter++ % 4) {
        result += String.fromCharCode(255 & bit >> (-2 * counter & 6));
      }
    }
  }
  return result;
};

