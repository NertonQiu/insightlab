
/**
 * @fileoverview Simple date range selector widget.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * @constructor
 * @extends {BaseWidget}
 * @param {Node|Element|string} container Widget container or its ID.
 */
function DateRangeWidget(container) {
  BaseWidget.apply(this, arguments);

  /**
   * @inheritDoc
   */
  this['WIDGET_NAME'] = 'date-range';

  /**
   * @type {string}
   */
  var COOKIE_KEY_INDEX = 'date_range_index';

  /**
   * Gets settings data.
   * Accessible by 'evt.widget.getData()' in 'handleEvent(evt)' method.
   * @return {Object} Returns settings data as dictionary representation.
   */
  this.getData = function() {
    return data_;
  };

  // Export for closure compiler.
  this['getData'] = this.getData;

  /**
   * Initialize widget.
   */
  this.initWidget = function() {
    getContainer_().innerHTML = 'Loading...';
    self_['loadData'](function(result) {
      if (result) self_['setDateRange'](result);
    });
  };

  // Export for closure compiler.
  this['initWidget'] = this.initWidget;

  /**
   * @param {Object} range Date range object.
   */
  this.setDateRange = function(range) {
    var dates = prepareDateRange_(range),
        container = getContainer_();
    container.innerHTML = '';
    container.className += ' dropdown';

    var div = container.appendChild(document.createElement('A'));
    div.className = 'dropdown-toggle';
    div.setAttribute('data-toggle', 'dropdown');
    div.setAttribute('role', 'button');
    var span = div.appendChild(document.createElement('SPAN'));
    span.className = 'dropdown-icon';
    var ul = container.appendChild(document.createElement('UL'));
    ul.className = 'dropdown-menu';

    for (var i = 0; i < dates.length; i++) {
      var li = ul.appendChild(document.createElement('LI'));
      var a = li.appendChild(document.createElement('A'));
      a.innerHTML = dates[i]['title'];

      if ('default' in dates[i] && dates[i]['default']) {
        div.innerHTML = showDate_(dates[i]['from_date']) +
                        '&nbsp;&mdash;&nbsp;' + showDate_(dates[i]['to_date']);
        div.appendChild(span);
        data_['from_date'] = dates[i]['from_date'];
        data_['to_date'] = dates[i]['to_date'];
      }
      li.index = i;
      li.onclick = function(e) {
        cookie_.set(COOKIE_KEY_INDEX, this.index);
        div.innerHTML = showDate_(dates[this.index]['from_date']) +
                        '&nbsp;&mdash;&nbsp;' +
                        showDate_(dates[this.index]['to_date']);
        div.appendChild(span);
        data_['from_date'] = dates[this.index]['from_date'];
        data_['to_date'] = dates[this.index]['to_date'];
        self_.dispatchEvent(
            new WidgetEvent(self_['WIDGET_NAME'] + '.changed', self_));
        return false;
      };
    }
    $(div).dropdown();
  };

  // Export for closure compiler.
  this['setDateRange'] = this.setDateRange;

  /**
   * Returns an array of available date ranges.<br>
   * Available rages are:<br>
   * - Last 1 day<br>
   * - Last 7 days<br>
   * - Last 14 days<br>
   * - Last 30 days<br>
   * - Last 90 days<br>
   * - Last month<br>
   * - Last year<br>
   * - Month to date<br>
   * - Quarter to date<br>
   * - Year to date<br>
   * <code>[{title: string, from_date: string, to_date: string}, ...]</code>
   * @param {Object} range Date range object.
   * @return {Array} Returns prepared date range.
   * @private
   */
  function prepareDateRange_(range) {
    var i, day = 24 * 60 * 60 * 1000,
        today = new Date,
        result = [],
        ranges = [
          {
            'title': '过去  1 天',
            'from_date': formatDate_(new Date((+today) - day)),
            'to_date': formatDate_(today)
          }, {
            'title': '过去 7天',
            'default': true,
            'from_date': formatDate_(new Date((+today) - 7 * day)),
            'to_date': formatDate_(today)
          }, {
            'title': '过去 14天',
            'from_date': formatDate_(new Date((+today) - 14 * day)),
            'to_date': formatDate_(today)
          }, {
            'title': '过去30天',
            'from_date': formatDate_(new Date((+today) - 30 * day)),
            'to_date': formatDate_(today)
          }, {
            'title': '过去90天',
            'from_date': formatDate_(new Date((+today) - 90 * day)),
            'to_date': formatDate_(today)
          }, {
            'title': '上个月 ',
            'from_date': formatDate_(today, 'lastmonth'),
            'to_date': formatDate_(today, 'month')
          }, {
            'title': '去年',
            'from_date': formatDate_(today, 'lastyear'),
            'to_date': formatDate_(today, 'year')
          }, {
            'title': '当月',
            'from_date': formatDate_(today, 'month'),
            'to_date': formatDate_(today)
          }, {
            'title': '季度为止',
            'from_date': formatDate_(today, 'quarter'),
            'to_date': formatDate_(today)
          }, {
            'title': '年初至今',
            'from_date': formatDate_(today, 'year'),
            'to_date': formatDate_(today)
          }
        ];

    /** @type {boolean} */ var hasDefault = false;
    for (i = 0; i < ranges.length; i++) {
      if (range['start_date'] < ranges[i]['to_date'] &&
          range['end_date'] > ranges[i]['from_date']) {
        result.push(ranges[i]);
        if ('default' in ranges[i] && ranges[i]['default']) {
          hasDefault = true;
        }
      }
    }

    if (result.length) {
      /** @type {?string} */ var index = cookie_.get(COOKIE_KEY_INDEX);
      if (index != '') {
        for (i = 0; i < result.length; i++) {
          result[i]['default'] = false;
        }
        result[index]['default'] = true;
      } else if (!hasDefault) {
        result[0]['default'] = true;
      }
    }

    return result;
  }

  /**
   * @return {Node|Element|undefined} Returns reference to widget container.
   * @private
   */
  function getContainer_() {
    /** @type {NodeList|Array} */
    var childNodes = self_.getContainer().childNodes;
    for (/** @type {number} */ var i = 0; i < childNodes.length; i++) {
      if (childNodes[i].className == self_.getContainer().id + '-wrapper') {
        return childNodes[i];
      }
    }
    return self_.getContainer();
  }


  /**
   * Formats date range according to selection rules.
   * @param {Date} date Date to format.
   * @param {string} type Formatting type.
   * @return {string} Returns formated date range according to selection rules.
   * @private
   */
  function formatDate_(date, type) {
    /** @type {number} */ var year = date.getFullYear();
    /** @type {number|string} */ var month = date.getMonth() + 1;
    /** @type {number|string} */ var day = date.getDate();
    if (type == 'month') {
      day = 1;
    } else if (type == 'lastmonth') {
      if (month == 1) {
        year -= 1;
        month = 12;
      } else {
        month -= 1;
      }
      day = 1;
    } else if (type == 'lastyear') {
      day = 1;
      month = 1;
      year -= 1;
    } else if (type == 'quarter') {
      month = (month - 1) / 3 * 3 + 1;
      day = 1;
    } else if (type == 'year') {
      month = 1;
      day = 1;
    }
    if (month < 10) month = '0' + month;
    if (day < 10) day = '0' + day;
    return '' + year + '-' + month + '-' + day;
  }


  /**
   * Prepares date range to be shown on UI.
   * @param {string} date Date to show.
   * @return {string} Returns prepared date range to be shown on UI.
   * @private
   */
  function showDate_(date) {
    /** @type {Array} */
    var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug',
      'Sep', 'Oct', 'Nov', 'Dec'];
    /** @type {Array} */ var values = date.split('-');
    /** @type {string} */ var year = values[0];
    /** @type {string} */ var month = values[1];
    /** @type {string} */ var day = values[2];
    month = months[parseInt(month, 10) - 1];
    return month + ' ' + parseInt(day, 10) + ', ' + year;
  }


  /**
   * The reference to current class instance. Used in private methods.
   * @type {BaseWidget}
   * @private
   */
  var self_ = this;


  /**
   * @type {Object}
   * @private
   */
  var data_ = {};


  /**
   * @type {DataStorage}
   * @private
   */
  var cookie_ = new DataStorage('cookie');
}


// Export for closure compiler.
window['DateRangeWidget'] = DateRangeWidget;

