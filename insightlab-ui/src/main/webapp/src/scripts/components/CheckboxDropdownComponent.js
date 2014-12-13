/**
 * @fileoverview Multiselectable dropdown component implementation.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * @constructor
 * @extends {DropdownComponent}
 * @param {Node|Element|string} container Widget container or its ID.
 */
function CheckboxDropdownComponent(container) {
  DropdownComponent.apply(this, arguments);

  /**
   * Draws the dropdown.
   * @this {CheckboxDropdownComponent}
   */
  this.draw = function() {
    /** @type {Node|Element} */ var node = this.getContainer();
    node.innerHTML = this.getHtmlTemplate('Show/Hide Columns');
    node.firstChild.className += ' pull-right';

    /** @type {Node|Element} */ var ul = node.getElementsByTagName('UL')[0];
    /** @type {Array} */ var list = this.getSortedList();

    for (/** @type {number} */ var i = 0; i < list.length; i++) {
      /** @type {string} */ var item = list[i];
      /** @type {Node|Element} */
      var li = ul.appendChild(document.createElement('LI'));
      li.setAttribute('data-item', item);
      /** @type {string} */ var title = this.getItemTitle(item);

      if (this['isCheckable'](item)) {
        /** @type {string} */ var key = this.getItemKey(item);
        /** @type {number} */ var index = this['getItemIndex'](item);
        /** @type {boolean} */ var checked = this['isChecked'](item);
        li.innerHTML = '<label for="' + key + '" title="' + title + '">' +
                       '<input type="checkbox" value="' + index +
                       '" id="' + key + '" class="checkbox' +
                       (checked ? ' checked' : '') + '"' +
                       (checked ? ' checked' : '') + '><span></span>' +
                       title + '</label>';
        li.onclick = inputClickHandler_;
      } else {
        li.innerHTML = '<label title="' + title + '">' + title + '</label>';
        li.firstChild.className = 'acxm-label';
      }
    }
  };

  /**
   * @return {Array} Returns sorted item list.
   * @protected
   * @this {CheckboxDropdownComponent}
   */
  this.getSortedList = function() {
    /** @type {Object} */ var columns = this['options']['columns'];
    /** @type {Array} */ var list = this['dataList'];
    /** @type {Array} */ var result = [];
    for (/** @type {string} */ var key in columns) {
      /** @type {Object} */ var column = columns[key];
      for (/** @type {number} */ var i = 0; i < list.length;) {
        /** @type {string} */ var item = list[i++];
        if (column[item]) {
          result.push(item);
          break;
        }
      }
    }
    return result;
  };

  /**
   * @param {string} item List item.
   * @return {number} Returns item index.
   * @protected
   * @this {CheckboxDropdownComponent}
   */
  this.getItemIndex = function(item) {
    /** @type {Array} */ var list = this['dataList'];
    for (/** @type {number} */ var i = 0; i < list.length; i++) {
      if (item == list[i]) {
        return i;
      }
    }
    return 0;
  };

  // Export for closure compiler.
  this['getItemIndex'] = this.getItemIndex;

  /**
   * @param {string} item List item.
   * @return {string} Returns item key.
   * @protected
   * @this {CheckboxDropdownComponent}
   */
  this.getItemKey = function(item) {
    return this['options']['name'] + '-' + item.toLowerCase().replace(' ', '-');
  };

  // Export for closure compiler.
  this['getItemKey'] = this.getItemKey;

  /**
   * @param {string} item List item.
   * @return {string} Returns item title.
   * @protected
   * @this {CheckboxDropdownComponent}
   */
  this.getItemTitle = function(item) {
    /** @type {Object} */ var columns = this['options']['columns'];
    for (/** @type {string} */ var key in columns) {
      /** @type {Object} */ var column = columns[key];
      if (column[item]) {
        return column[item];
      }
    }

    return /** @type {string}*/ (item);
  };

  /**
   * @param {string} item List item.
   * @return {boolean} Returns <code>true</code> if items is checkable.
   * @protected
   * @this {CheckboxDropdownComponent}
   */
  this.isCheckable = function(item) {
    return true;
  };

  // Export for closure compiler.
  this['isCheckable'] = this.isCheckable;

  /**
   * @param {string} item List item.
   * @return {boolean} Returns <code>true</code> if item is checked.
   * @protected
   * @this {CheckboxDropdownComponent}
   */
  this.isChecked = function(item) {
    return false;
  };

  // Export for closure compiler.
  this['isChecked'] = this.isChecked;

  /**
   * Gets component data.
   * @return {Object} Returns component data as dictionary representation.
   */
  this.getData = function() {
    return data_;
  };

  /**
   * @param {Event} e Event.
   * @this {Node|Element}
   * @private
   */
  function inputClickHandler_(e) {
    // Note: "this" is LI element.
    /** @type {Node|Element} */
    var input = this.getElementsByTagName('INPUT')[0];

    if (input.checked) {
      input.className = input.className.replace(' checked', '');
      input.className += ' checked';
      data_[input.id] = parseInt(input.value, 10);
    } else {
      delete data_[input.id];
      input.className = input.className.replace(' checked', '');
    }

    self_.dispatchEvent(
        new WidgetEvent(self_['options']['name'] + 'drop' + '.changed', self_));
    e && e.stopPropagation && e.stopPropagation();
  }

  /**
   * Storage for component data.
   * @type {Object}
   * @private
   */
  var data_ = {};

  /**
   * The reference to current class instance. Used in private methods.
   * @type {CheckboxDropdownComponent}
   * @private
   */
  var self_ = this;
}

// Export for closure compiler.
window['CheckboxDropdownComponent'] = CheckboxDropdownComponent;

