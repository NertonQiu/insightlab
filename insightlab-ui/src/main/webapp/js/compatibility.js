/**
 * Created by viktor on 02.01.14.
 */
// From https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/keys
if (!Object.keys) {
  Object.keys = (function () {
    'use strict';
    var hasOwnProperty = Object.prototype.hasOwnProperty,
      hasDoNotEnumBug = !({ toString: null }).propertyIsEnumerable('toString'),
      doNotEnums = [
        'toString',
        'toLocaleString',
        'valueOf',
        'hasOwnProperty',
        'isPrototypeOf',
        'propertyIsEnumerable',
        'constructor'
      ],
      doNotEnumsLength = doNotEnums.length;

    return function (obj) {
      if (typeof obj !== 'object' && (typeof obj !== 'function' || obj === null)) {
        throw new TypeError('Object.keys called on non-object');
      }

      var result = [], prop, i;

      for (prop in obj) {
        if (hasOwnProperty.call(obj, prop)) {
          result.push(prop);
        }
      }

      if (hasDoNotEnumBug) {
        for (i = 0; i < doNotEnumsLength; i++) {
          if (hasOwnProperty.call(obj, doNotEnums[i])) {
            result.push(doNotEnums[i]);
          }
        }
      }
      return result;
    };
  }());
}

if (!Array.prototype.filter) {
  Array.prototype.filter = function (fn, context) {
    var i,
      value,
      result = [],
      length;

    if (!this || typeof fn !== 'function' || (fn instanceof RegExp)) {
      throw new TypeError();
    }

    length = this.length;

    for (i = 0; i < length; i++) {
      if (this.hasOwnProperty(i)) {
        value = this[i];
        if (fn.call(context, value, i, this)) {
          result.push(value);
        }
      }
    }
    return result;
  };
}

if (!Array.prototype.indexOf) {
  Array.prototype.indexOf = function (searchElement, fromIndex) {
    var i,
      pivot = (fromIndex) ? fromIndex : 0,
      length;

    if (!this) {
      throw new TypeError();
    }

    length = this.length;

    if (length === 0 || pivot >= length) {
      return -1;
    }

    if (pivot < 0) {
      pivot = length - Math.abs(pivot);
    }

    for (i = pivot; i < length; i++) {
      if (this[i] === searchElement) {
        return i;
      }
    }
    return -1;
  };
}