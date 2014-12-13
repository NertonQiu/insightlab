/**
 * @fileoverview DomUtils.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 * @link https://developers.google.com/closure/compiler/docs/js-for-compiler
 */



/**
 * Methods to manipulate DOM.
 * @constructor
 */
function DomUtils() { return null; }


/**
 * Indicates whether the browser is any version of IE.
 * @type {boolean}
 */
DomUtils.IE = !!window.VBArray;


/**
 * Alias of W3C document.getElementById.
 * Used to reduce size after compilation.
 * @param {string} id A case-sensitive string representing the unique ID of the
 *     element being sought.
 * @return {Element} Returns reference to an Element object, or null if an
 *     element with the specified ID is not in the document.
 */
DomUtils.getElement = function(id) {
  return document.getElementById(id);
};


/**
 * Alias of W3C element.getElementsByTagName.
 * Used to reduce size after compilation.
 * @param {Element|Node} element Element to search tags.
 * @param {string} tagName Tag name.
 * @return {NodeList} Returns list of found elements in the
 *     order they appear in the tree.
 */
DomUtils.getElementsByTagName = function(element, tagName) {
  return element && element.getElementsByTagName(tagName);
};


/**
 * Removes element from DOM.
 * @param {Element|Node} element Node to remove.
 * @static
 */
DomUtils.destroy = function(element) {
  if (element && element.parentNode) {
    element.parentNode.removeChild(element);
  }
};


/**
 * Removes all children from given DOM element.
 * @param {Element|Node} element Node to be cleared.
 * @static
 */
DomUtils.clear = function(element) {
  while (element.lastChild)
    element.removeChild(element.lastChild);
};


/**
 * Gets first non-text node from given element.
 * @param {Element|Node} element Parent node.
 * @return {Node} Child node.
 * @static
 */
DomUtils.getFirstElementChild = function(element) {
  if (element.firstElementChild) {
    return element.firstElementChild;
  }
  /** @type {Element|Node} */ var firstChild = element.firstChild;
  while (firstChild && firstChild.nodeType != 3) {
    firstChild = firstChild.nextSibling;
  }
  return firstChild;
};


/**
 * Toggle visibility of given element.
 * @param {Element|Node} element Element.
 * @return {boolean} Visibility state.
 * @static
 */
DomUtils.toggle = function(element) {
  /** @type {boolean} */ var visible = false;
  if (window.getComputedStyle) {
    /** @type {CSSStyleDeclaration} */
    var style = window.getComputedStyle(/** @type {Element} */ (element), null);
    visible = style.getPropertyValue('display') != 'none';
  } else {
    visible = element.currentStyle['display'] != 'none';
  }
  if (visible) {
    DomUtils.hide(element);
  } else {
    DomUtils.show(element);
  }
  return !visible;
};


/**
 * Toggle class for given node.
 * @param {Element|Node} element Element.
 * @param {string} className Class name.
 * @static
 */
DomUtils.toggleClass = function(element, className) {
  /** @type {!RegExp} */
  var classNameRe = new RegExp('\\b' + className + '(\\s|$)', 'g');
  if (classNameRe.test(element.className)) {
    element.className = element.className.replace(classNameRe, '');
  } else {
    element.className += ' ' + className;
  }
};


/**
 * Shows DOM element.
 * @param {Element|Node} element Node to show.
 * @param {string=} opt_display value to set as element.style.display.
 *     Default value is 'block'.
 * @static
 */
DomUtils.show = function(element, opt_display) {
  if (element && element.style) {
    element.style['display'] = opt_display || 'block';
  }
};


/**
 * Hides DOM element.
 * @param {Element|Node} element Node to hide.
 * @static
 */
DomUtils.hide = function(element) {
  if (element && element.style) {
    element.style['display'] = 'none';
  }
};


/**
 * Removes class from given element.
 * @param {Element|Node} element Element to update class.
 * @param {string} className Class name to remove.
 * @static
 */
DomUtils.removeClass = function(element, className) {
  /** @type {!RegExp} */
  var classNameRe = new RegExp('(?:^|\\s)' + className + '(?!\\S)');
  element.className = element.className.replace(classNameRe, '');
};


/**
 * Adds class to given element if such class not found.
 * @param {Element|Node} element Element.
 * @param {string} className Class name to add.
 * @static
 */
DomUtils.addClass = function(element, className) {
  if (!DomUtils.hasClass(element, className)) {
    element.className += ' ' + className;
  }
};


/**
 * Detects element have class name.
 * @param {Element|Node} element Element to test.
 * @param {string} className Class name.
 * @return {boolean} Does element className match given className.
 * @static
 */
DomUtils.hasClass = function(element, className) {
  /** @type {!RegExp} */
  var classNameRe = new RegExp('(?:^|\\s)' + className + '(?!\\S)');
  return classNameRe.test(element.className);
};


/**
 * Gets elements by class name. Use native getElementsByClassName if supported.
 * @param {Element|Node} element Element to start searching.
 * @param {string} className Class name to match.
 * @return {Array} Array of found elements.
 * @static
 */
DomUtils.getElementsByClassName = function(element, className) {
  if (element.getElementsByClassName) {
    return Utils.toArray(element.getElementsByClassName(className));
  }

  /** @type {!Array} */ var result = [];
  /** @type {number} */ var length = element.childNodes.length;
  for (/** @type {number} */ var i = 0; i < length; i++) {
    /** @type {Node} */ var child = element.childNodes[i];
    if (DomUtils.hasClass(child, className)) {
      result.push(child);
    }
    result.concat(DomUtils.getElementsByClassName(child, className));
  }
  return result;
};


/**
 * Enable DOM element.
 * Remove class 'state-disabled' and remove '.disabled' property.
 * @param {Element|Node} element Element to enable.
 * @static
 */
DomUtils.enable = function(element) {
  if (element && (element.disabled || element.getAttribute('disabled'))) {
    DomUtils.removeClass(element, 'state-disabled');
    element.removeAttribute('disabled');
  }
};


/**
 * Disable DOM element.
 * Add class 'state-disabled' and set '.disabled' property.
 * @param {Element|Node} element Element to disable.
 * @static
 */
DomUtils.disable = function(element) {
  if (element && (!element.disabled || !element.getAttribute('disabled'))) {
    DomUtils.addClass(element, 'state-disabled');
    element.setAttribute('disabled', true);
  }
};


// Events
/**
 * Cross-browser addEventListener
 * @param {Element|Node} element Element to which attach event.
 * @param {string} type Type of event.
 * @param {function(Event,...)} callback Event listener.
 * @static
 */
DomUtils.addEvent = function(element, type, callback) {
  if (element.addEventListener) {
    element.addEventListener(type, callback, false);
  } else if (element.attachEvent) {
    element.attachEvent('on' + type, callback);
  } else {
    /** @type {Function} */ var handler = element['on' + type];
    element['on' + type] = function(event) {
      event || (event = window.event);
      handler && handler(event);
      callback(event);
    };
  }
};


/**
 * Cross-browser stop event propagation.
 * @param {Event} event event to stop.
 * @static
 */
DomUtils.stopEvent = function(event) {
  event || (event = window.event);
  event.stopPropagation && event.stopPropagation();
  event.preventDefault && event.preventDefault();
  event.returnValue = false;
  event.cancelBubble = true;
};


/**
 * Assign proper value to given element.
 * @param {Element|Node} element Element.
 * @param {?} value Value.
 * @static
 */
DomUtils.assign = function(element, value) {
  // TODO add more checks here & encode HTML entities.
  if (element.tagName != 'BUTTON') {
    element.value = value;
  }
};


/**
 * Gets an array of elements from given tag list.
 * @param {Element|Node} element Element to search in.
 * @param {...string} var_args Tag names.
 * @return {!Array} Array of matched elements.
 * @static
 */
DomUtils.getElementsByTagNames = function(element, var_args) {
  /** @type {Array.<string>} */ var tagNames = Utils.toArray(arguments, 1);
  /** @type {number} */ var length = tagNames.length;
  /** @type {!Array} */ var result = [];
  for (/** @type {number} */ var i = 0; i < length; i++) {
    result = result.concat(
        Utils.toArray(DomUtils.getElementsByTagName(element, tagNames[i])));
  }
  return result;
};
