
/**
 * @fileoverview Dropdown component implementation.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * @constructor
 * @extends {BaseComponent}
 * @param {Node|Element|string} container Widget container or its ID.
 */
function DropdownComponent(container) {
  BaseComponent.apply(this, arguments);

  /**
   * @param {string=} opt_label Optional label text.
   * @return {string} Returns component's HTML template.
   */
  this.getHtmlTemplate = function(opt_label) {
    return '<div class="dropdown span3">' +
           '<a class="dropdown-toggle"' +
           'data-toggle="dropdown" href="#">' + (opt_label || '') +
           '<span class="dropdown-icon"></span></a>' +
           '<ul class="dropdown-menu" role="menu" ' +
           'aria-labelledby="dropdownMenu"></ul></div>';
  };

  /**
   * Draws the dropdown.
   * @this {DropdownComponent}
   */
  this.draw = function() {
    /** @type {Node|Element} */
    var node = this.getContainer();
    node.innerHTML = this.getHtmlTemplate();
    /** @type {Node|Element} */ var a = node.getElementsByTagName('A')[0];
    /** @type {Node|Element} */ var ul = node.getElementsByTagName('UL')[0];
    /** @type {Array} */ var list = this['dataList'];

    for (/** @type {number} */ var i = 0; i < list.length; i++) {
      /** @type {string} */
      var id = list[i]['label'].toLowerCase().replace(' ', '-');

      /** @type {Node|Element} */
      var li = ul.appendChild(document.createElement('LI'));
      li.innerHTML = '<a id="' + id + '" value="' + list[i]['value'] +
                     '">' + list[i]['title'] + '</a>';
      if (!i) {
        selected_ = li.firstChild;
        $(a).text(selected_.innerHTML);
      }
    }

    $(ul).on('click', 'li', function(e) {
      selected_ = e.target || e.srcElement;
      $(a).text(selected_.innerHTML);
    });
  };

  /**
   * Gets component data.
   * @return {Object} Returns component data as dictionary representation.
   */
  this.getData = function() {
    var data = {};
    if (selected_) {
      data[selected_.id] = selected_.innerText || selected_.textContent;
    }
    return data;
  };

  /**
   * Reference to selected element.
   * @type {Node|Element}
   * @private
   */
  var selected_ = null;
}

// Export for closure compiler.
window['DropdownComponent'] = DropdownComponent;

