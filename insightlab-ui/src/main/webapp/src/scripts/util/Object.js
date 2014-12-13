
/**
 * @fileoverview Miscellaneous Object utility methods.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 * @link https://developers.google.com/closure/compiler/docs/js-for-compiler
 */



/**
 * Miscellaneous Object utility methods.
 * @constructor
 */
util.Object = function() {return null;};


/**
 * Extends source with target options.
 * @param {!Object} target Options map.
 * @param {!Object} source Options map.
 * @return {!Object} A map of key/value pairs of options.
 * @static
 */
util.Object.extend = function(target, source) {
  for (/** @type {string} */ var key in source) {
    if (source[key] instanceof Array) {
      target[key] = [].concat(source[key]);
    } else if ('object' == typeof source[key]) {
      target[key] = util.Object.extend(
          /** @type {!Object} */ (target[key] || {}),
          /** @type {!Object} */ (source[key]));
    } else {
      target[key] = source[key];
    }
  }
  return /** @type {!Object} */ (target);
};


/**
 * Returns list of object keys;
 * @param {!Object} obj Target Object.
 * @return {!Array} List of target object keys.
 */
util.Object.keys = function(obj) {
  /** @type {!Array} */ var keys = [];
  /** @type {number} */ var i = 0;
  for (/** @type {string|number} */ var key in obj) {
    keys[i++] = key;
  }
  return keys;
};
