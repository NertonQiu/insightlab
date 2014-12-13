/**
 * @fileoverview Table component implementation.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * @constructor
 * @extends {BaseComponent}
 * @param {Node|Element|string} container Widget container or its ID.
 */
function TableComponent(container) {
  BaseComponent.apply(this, arguments);

  /**
   * Draws the table.
   * @this {TableComponent}
   */
  this.draw = function() {
    table_ = table_ || this.getContainer().getElementsByTagName('TABLE')[0];
    if (!table_) {
      this.getContainer().innerHTML = '<table class="table table-hover ' +
                                      'table-expanded table-striped ' +
                                      'table-bordered table-sortered">' +
                                      '<colgroup></colgroup>' +
                                      '<caption></caption>' +
                                      '<thead><tr></tr></thead>' +
                                      '<tbody></tbody></table>';
      table_ = this.getContainer().getElementsByTagName('TABLE')[0];
    }

    var caption = table_.getElementsByTagName('CAPTION')[0];
    if (!caption.innerHTML)
      caption.innerHTML = this['options']['title'] || '&nbsp;';

    clearTableData_();
    drawHeader_(this['dataList'], this['options']);
    drawData_(this['dataList'], this['options']);
    drawFooter_(this['dataList'], this['options']);
  };

  /**
   * @param {Object} column The column object from <code>options.columns</code>.
   * @param {number} index Column index.
   * @return {boolean} Returns <code>true</code> if column should be visible.
   * @protected
   * @this {TableComponent}
   */
  this.isVisible = function(column, index) {
    return true;
  };

  // Export for closure compiler.
  this['isVisible'] = this.isVisible;

  /**
   * @param {Array} row The row object.
   * @param {number} index Column index.
   * @return {string} Returns cell title.
   * @protected
   * @this {TableComponent}
   */
  this.getCellTooltip = function(row, index) {
    return '';
  };

  // Export for closure compiler.
  this['getCellTooltip'] = this.getCellTooltip;

  /**
   * @param {Array|Object} data The table data.
   * @param {Object} options The table options.
   * @private
   */
  function drawData_(data, options) {
    /** @type {Node|Element} */
    var tbody = table_.getElementsByTagName('TBODY')[0];
    /** @type {number} */ var maxRows = +(options['max_rows'] || 15);
    /** @type {number} */ var i = 0;
    /** @type {number} */ var j = 0;

    for (; i < Math.min(data['rows'].length, maxRows);) {
      /** @type {Array} */ var row = data['rows'][i++];
      /** @type {Node|Element} */ var bodyRow = tbody.insertRow(-1);
      for (/** @type {string} */ var col in options['columns']) {
        /** @type {Object} */ var column = options['columns'][col];
        for (j = 0; j < row.length; j++) {
          if (column[data['columns'][j]]) {
            if (self_['isVisible'](column, j)) {
              /** @type {Node|Element} */ var td = bodyRow.insertCell(-1);
              setFormattedValue_(td, column['type'], row[j]);
              td.title = self_['getCellTooltip'](row, j);
            }
          }
        }
      }
    }
  }

  /**
   * @param {Array|Object} data The table data.
   * @param {Object} options The table options.
   * @private
   */
  function drawHeader_(data, options) {
    /** @type {Node|Element} */
    var row = table_.getElementsByTagName('THEAD')[0].firstChild;
    /** @type {Node|Element} */
    var colgroup = table_.getElementsByTagName('COLGROUP')[0];

    /** @type {number} */ var i = 0;
    /** @type {number} */ var j = 0;
    for (; i < options['columns'].length; i++) {
      for (j = 0; j < data['columns'].length; j++) {
        /** @type {Object} */ var column = options['columns'][i];
        /** @type {string} */ var columnName = data['columns'][j];
        if (column[columnName]) {
          /** @type {string} */ var title = column[columnName];
          if (self_['isVisible'](column, j)) {
            /** @type {Node|Element} */
            var th = row.appendChild(document.createElement('TH'));
            /** @type {Node|Element} */
            var div = th.appendChild(document.createElement('DIV'));
            div.innerHTML = th.title = title;

            /** @type {Node|Element} */
            var span = div.getElementsByTagName('SPAN')[0];            
            if (column['sort'] == 'false') {
              if (span) span.parentNode.removeChild(span);
            } else {
              if (!span) span = div.appendChild(document.createElement('SPAN'));
              span.className = 'caret';
            }            
            th.setAttribute('data-column', columnName);
            th.setAttribute('data-sorting', column['sort']);

            if (/_name$/.test(columnName)) {
              /** @type {Node|Element} */
              var col = colgroup.appendChild(document.createElement('COL'));
              col.setAttribute('width', '150');
              col.setAttribute('data-column', columnName);
            }
          }
        }
      }
    }
  }

  /**
   * @param {Array|Object} data The table data.
   * @param {Object} options The table options.
   * @private
   */
  function drawFooter_(data, options) {
    if (data['totals'] && options['totals'] == true) {
      /** @type {Node|Element} */
      var footer = table_.getElementsByTagName('TFOOT')[0];
      if (!footer) footer = table_.appendChild(document.createElement('TFOOT'));

      /** @type {Node|Element} */
      var row = footer.insertRow(-1);
      row.className = 'grand-totals';

      /** @type {number} */ var i = 0;
      /** @type {number} */ var j = 0;
      for (; i < options['columns'].length; i++) {
        for (j = 0; j < data['columns'].length; j++) {
          /** @type {string} */ var column = options['columns'][i];
          if (column[data['columns'][j]]) {
            /** @type {string|number} */ var total = data['totals'][j];
            if (self_['isVisible'](column, j)) {
              total = total || total == 0 ? total : '';
              setFormattedValue_(row.insertCell(-1), column['type'], total);
            }
          }
        }
      }
    }
  }

  /**
   * @param {Node|Element} cell HTML table cell.
   * @param {string} type Formatting type.
   * @param {string|number} value Value.
   * @private
   */
  function setFormattedValue_(cell, type, value) {
    if (typeof value === 'number' && isFinite(value)) {
      if (type == 'percents') {
        value = (value * 100).toFixed(3) + '%';
        cell.style.textAlign = 'right';
      } else if (type == 'currency') {
        value = '$' + (+value).toFixed(2);
        cell.style.textAlign = 'right';
      } else if (type == 'number') {
        (function() {
          /** @type {google.visualization.NumberFormat} */
          var formatter = getNumberFormatter_();
          if (formatter)
            cell.innerHTML = cell.title = formatter.formatValue(value);
          else setTimeout(arguments.callee, 250);
        })();
        cell.style.textAlign = 'right';
      } else if (type == 'double') {
        value = parseFloat(value);
        cell.style.textAlign = 'right';
      }
    }
    cell.innerHTML = cell.title = value;
  }

  /**
   * @private
   */
  function clearTableData_() {
    /** @type {Array} */ var nodes = [
      table_.getElementsByTagName('COLGROUP')[0],
      table_.getElementsByTagName('THEAD')[0].firstChild,
      table_.getElementsByTagName('TBODY')[0],
      table_.getElementsByTagName('TFOOT')[0]
    ];

    for (/** @type {number} */ var i = 0; i < nodes.length;) {
      /** @type {Node|Element} */ var node = nodes[i++];
      if (node) {
        while (node.lastChild) node.removeChild(node.lastChild);
      }
    }
  }

  /**
   * @return {google.visualization.NumberFormat} Returns number formatter.
   * @private
   */
  function getNumberFormatter_() {
    if (!numberFormatter_) {
      if (window['google'] && google['visualization'] &&
          google.visualization['NumberFormat']) {
        numberFormatter_ = new google.visualization.NumberFormat(
            {'pattern': '#,###'});
      }
    }
    return numberFormatter_;
  }

  /**
   * @type {google.visualization.NumberFormat}
   * @private
   */
  var numberFormatter_ = null;

  /**
   * @type {Node|Element}
   * @private
   */
  var table_ = null;

  /**
   * The reference to current class instance. Used in private methods.
   * @type {TableComponent}
   * @private
   */
  var self_ = this;
}

// Export for closure compiler.
window['TableComponent'] = TableComponent;

