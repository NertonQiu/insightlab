/**
 * @fileoverview Utils.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 * @link https://developers.google.com/closure/compiler/docs/js-for-compiler
 */



/**
 * Various utils.
 * @constructor
 */
var Utils = function() { return null; };


/**
 * Detect is given object is a string.
 * @param {*} string Object to be tested.
 * @return {boolean} Indicates if given object is a string.
 * @static
 */
Utils.isString = function(string) {
  return typeof string == 'string';
};


/**
 * Detect is given object is a function.
 * @param {*} fn Object to be tested.
 * @return {boolean} Indicates if given object is a function.
 * @static
 */
Utils.isFunction = function(fn) {
  return fn instanceof Function;
};


/**
 * Detect is given object is undefined.
 * @param {*} obj Object to be tested.
 * @return {boolean} Indicates if given object is undefined.
 * @static
 */
Utils.isUndefined = function(obj) {
  return obj === void 0;
};


/**
 * Detect is given object is an object.
 * @param {*} obj Object to be tested.
 * @return {boolean} Indicates if given object is an object.
 * @static
 */
Utils.isObject = function(obj) {
  return obj === Object(obj);
};


/**
 * Detect is given object is an array.
 * @param {*} obj Object to be tested.
 * @return {boolean} indicates if given object is an array.
 * @static
 */
Utils.isArray = function(obj) {
  return obj instanceof Array;
};


/**
 * Converts Node collection or arguments object to native Array.
 * @param {Object} args Array-like object.
 * @param {number=} opt_from Index to slice values from.
 * @return {Array} Returns native array.
 * @static
 */
Utils.toArray = function(args, opt_from) {
  var a = args || [], l = a.length || 0, res = [], i = opt_from || 0;
  for (; i < l; i++) {
    res.push(a[i]);
  }
  return res;
};


/**
 * Detects if item is in array.
 * @param {*} item Item to search for.
 * @param {Array} arr Array to search in.
 * @return {boolean} Returns true if found, otherwise false.
 * @static
 */
Utils.inArray = function(item, arr) {
  var r;
  if (arr.indexOf) {
    r = (arr.indexOf(item) + 1);
  }else {
    var i = arr.length;
    while (i--) {
      if (arr[i] === item) {
        return true;
      }
    }
  }
  return !!r;
};


/**
 * Gets values from all form fields.
 * @param {HTMLFormElement} form Any form.
 * @return {!Object} Returns associative array, where key is field name,
 *       and value is field value.
 * @static
 */
Utils.getFormData = function(form) {
  /** @dict */ var data = {};
  /** @type {HTMLCollection} */ var elements = form.elements;
  /** @type {number} */ var length = elements.length;
  for (/** @type {number} */ var i = 0; i < length; i++) {
    /** @type {!HTMLElement} */ var field = elements[i];
    if (elements[i].name) {
      data[field.name] = StringUtils.toPlainText(
          field[(field.type == 'checkbox' ? 'checked' : 'value')]);
    }
  }
  return data;
};


/**
 * Clear form fields.
 * @param {HTMLFormElement|Element} form Html form.
 * @static
 */
Utils.clearFormFields = function(form) {
  var i = 0, el = form.elements, l = el.length, e;
  for (; i < l; i++) {
    e = el[i];
    if (Utils.inArray(e.type, ['button', 'submit', 'reset'])) {
      continue;
    }
    e.value && (e.value = '');
    e.checked && (e.checked = false);
    e.selectedIndex && (e.selectedIndex = 0);
  }
};


/**
 * Merge two objects.
 * extender values always override obj values.
 * Object passed as first argument will be updated.
 * If first argument is null then new object will be created.
 * @param {?Object} obj Object to extend. If null value passed - new object
 * created.
 * @param {Object} extender Object to copy values from.
 * @return {Object} Merged obj.
 * @static
 */
Utils.merge = function(obj, extender) {
  var key;
  var result = obj || {};
  if (!Utils.isUndefined(extender)) {
    for (key in extender) {
      if (Object.prototype.hasOwnProperty.call(extender, key)) {
        result[key] = extender[key];
      }
    }
  }
  return result;
};


/**
 * Redirect browser to page.
 * @param {string} page Constant name from Const.Url.
 * @param {Object=} opt_params Optional parameters which will be passed to page.
 * @static
 */
Utils.redirect = function(page, opt_params) {
  window.location.replace(Const.getUrl(page) +
      StringUtils.toQueryString(opt_params));
};

