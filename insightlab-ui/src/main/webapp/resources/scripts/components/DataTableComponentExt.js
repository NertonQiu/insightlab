/**
 * Datatable component filters-ext.
 *
 * @module datatable-component-filters-ext
 **/
ACX.add('datatable-component-filters-ext', function (A) {
  /**
   * Creates range filter for datatable.
   * @param {Object} config Configuration object.
   */
  A.DataTableComponent.prototype.createRangeFilter = function (config) {
    var $filterLink = config['$th'].find('.filter-ico');
    $filterLink = $('<a class="filter-ico"/>', { text: 'filter' });
    $filterLink.appendTo(config['$th'].find('div'));
    var $container = $filterTemp.clone().appendTo('body');
    var settings = config['settings'];
    if (settings['range-selector']) {
      /**@type{DropDownComponent}*/
      var dateSelector = new A.DropDownComponent($('<div></div>').appendTo($container));
      dateSelector.initWidget(settings['range-selector']);
      dateSelector.addEventListener(dateSelector.WIDGET_NAME + '.changed', function (evt) {
        var range = evt['widget'].getData()['val'];
        var dropdownText = evt['widget'].getData()['text'];
        var $calDiv = $('.gldp-default');
        if ('title' !== range && range) {
          isReseted_ = false;
          var today = new Date();
          upper.setData(today, true);
          var fromday = new Date();
          fromday.setDate(today.getDate() - range);
          lower.setData(fromday, true);
          filterBtn.getElement().prop('disabled', isReseted_);
        }
        else if ('所有' === dropdownText && 'title' !== range) {
          isReseted_ =!((data_['input0'] && data_['input1']) && (data_['input0'].length &&  data_['input1'].length));
          lower.setData('');
          upper.setData('');
          filterBtn.getElement().prop('disabled', isReseted_);
        }
        if ($calDiv) $calDiv.hide();
      });
    }

    /**@type{InputTagComponent}*/
    var lower = new A.InputTagComponent($('<div class="div-input"></div>').appendTo($container));
    /**@type{InputTagComponent}*/
    var upper = new A.InputTagComponent($('<div class="div-input"></div>').appendTo($container));
    records_ = [lower, upper];
    var dateSettings = 'date-field' === config['type'] ? settings['date-settings'] : null;
    var lowerConfig = { name: settings['name'] + 'lower-input',
      validator: { tooltip: null },
      type: config['type'],
      'date-settings': dateSettings,
      label: 'date-field' === config['type'] ? '开始日期' : '&nbsp;&nbsp;低',
      'characteristic': 'date-field' === config['type'] ? 'start-date' : null,
      cssClass: 'date-field' === config['type'] ? 'date-input label-input' : 'label-input'
    };
    lower.initWidget(lowerConfig);
    var upperConfig = { name: settings['name'] + 'upper-input',
      validator: { tooltip: null },
      type: config['type'],
      'date-settings': dateSettings,
      label: 'date-field' === config['type'] ? '结束日期' : '&nbsp;&nbsp;高',
      'characteristic': 'date-field' === config['type'] ? 'end-date' : null,
      cssClass: 'date-field' === config['type'] ? 'date-input label-input' : 'label-input'
    };
    upper.initWidget(upperConfig);

    //** calendar functionality **//
    $('.date-input').parent().addClass('date-div').append('<div class="date-img"></div>');
    $('.date-div').on('click', function (e) {
      e.stopPropagation();
    });
    var $vis = $('.gldp-default');
    $('.date-img').on('click', function () {
      if ($vis.hasClass('vis')) {
        $vis.hide().removeClass('vis');
      } else {
        $(this).parent().find('input').click();
      }
    });
    $('.date-div').find('input').on('focus', function () {
      $vis.hide().removeClass('vis');
      $(this).click();
    });
    $(document).keyup(function (e) {
      if (e.keyCode == 27) {
        $vis.hide().removeClass('vis');
        $container.hide();
      }
    });
    $('.acxiom-page-container, .filter-popup').on('click', function () {
      $vis.hide().removeClass('vis');
    });
    $('.filter-popup').on('click', function (e) {
      e.stopPropagation();
    });
    $filterLink.on('click', function (e) {
      $('.filter-ico').removeClass('filter-active');
      e.stopPropagation();
      var offset = $(this).offset();
      var width = $container.outerWidth(true) - 20;
      $container.css({ top: offset.top + 20, left: offset.left - width });
      if ($container.is(':visible')) {
        $container.hide();
        $filterLink.removeClass('filter-active');
      } else {
        $('.filter-popup').hide();
        $container.show();
        $filterLink.addClass('filter-active');
      }
    });
    var columnIndex = settings['column-index'];
    /**@type{ButtonComponent}*/
    var filterBtn = new A.ButtonComponent($container);
    var btnConfig = { name: settings['name'] + '-filter',
      title: '过滤',
      disabled: true
    };
    filterBtn.initWidget(btnConfig);
    filterBtn.removeEventListener(filterBtn.WIDGET_NAME + '.clicked');
    filterBtn.addEventListener(filterBtn.WIDGET_NAME + '.clicked', function () {
      /**@type {string}*/ var val;
      var lowerData = lower.getData();
      var upperData = upper.getData();
      if (dateSelector) {
        dateSelector.enableOptions();
        var selectData = dateSelector.getData();
        var enableConfig = {
          'value': selectData['val'],
          'text': selectData['text'],
          'disabled': true
        };
        dateSelector.enableOptions([enableConfig]);
        if ('所有' === selectData['text'] && !lowerData.text && !upperData.text) {
          val = '';
          $container.hide();
          filterBtn.getElement().prop('disabled', true);
          clearBtn.getElement().prop('disabled', true);
          $filterLink.removeClass('filtered');
          if (settings['ui-filter']) {
            config['dataTable'].fnSettings()['custom-filtering']['value'] = val;
            config['dataTable'].fnDraw();
            return;
          } else {
            config['dataTable'].fnFilter(val, columnIndex);
            return;
          }
        }
      }
      if (lower.validate() && upper.validate()) {
        var firstValue = parseInt(lowerData['text']),
          secondValue = parseInt(upperData['text']);
        if ('number-field' === lowerData['input-type'] && (secondValue - firstValue < 0 || (!secondValue && !firstValue))) {
          ACXM.Dialog.show({ 'id': 'validationError', 'type': 'error',
            'title': '',
            'message': 'Enter valid record range'
          });
          return false;
        }
        else {
          val = lowerData['text'] + '&&' + upper.getData()['text'];
        }
        if ('date-field' === lowerData['input-type']) {
          var lowerDate = lower.formatToUtc(new Date(lowerData['date-text'] || lowerData['text']), false);
          var upperDate = upper.formatToUtc(new Date(upperData['date-text'] || upperData['text']), false);
          if (validLength(upperDate) < validLength(lowerDate, true)) {
            ACXM.Dialog.show({ 'id': 'validationError', 'type': 'error',
              'title': '',
              'message': 'Enter valid date range'
            });
            return false;
          }
          val = lowerDate + '&&' + upperDate;
        }
        $container.hide();
        if (settings['ui-filter']) {
          config['dataTable'].fnSettings()['custom-filtering']['value'] =
            (lowerData['date-text'] || lowerData['text']) + '&&' + (upperData['date-text'] || upperData['text']);
          config['dataTable'].fnDraw();
        } else {
          config['dataTable'].fnFilter(val, columnIndex);
        }
        filterBtn.getElement().prop('disabled', true);
        clearBtn.getElement().prop('disabled', false);
        if ($filterLink.hasClass('filter-active'))
          $filterLink.removeClass('filter-active').addClass('filtered');
      }
    });

    function validLength(data, lower) {
      var date = data.substring(0, data.indexOf(' '));
      var arr = date.split('-');
      if (lower) {
        var currentDate = +new Date(arr[0], arr[1], arr[2]).setHours(24);
      } else {
        currentDate = +new Date(arr[0], arr[1], arr[2]);
      }
      return currentDate;
    }
    /**@type{ButtonComponent}*/
    var clearBtn = new A.ButtonComponent($container);
    clearBtn.initWidget({
      title: '清除', name: settings['name'] + '-clear',
      cssClass: 'btn-link',
      disabled: true
    });
    clearBtn.removeEventListener(clearBtn.WIDGET_NAME + '.clicked');
    clearBtn.addEventListener(clearBtn.WIDGET_NAME + '.clicked', function () {
      isReseted_ = true;
      $container.hide();
      lower.setData('');
      upper.setData('');
      if (settings['range-selector']) {
        dateSelector.updateDropdownData(settings['range-selector']['json-data']);
      }
      if (settings['ui-filter']) {
        config['dataTable'].fnSettings()['custom-filtering']['value'] = '';
        config['dataTable'].fnDraw();
      } else {
        config['dataTable'].fnFilter('', columnIndex);
      }
      if ($filterLink.hasClass('filter-active') || $filterLink.hasClass('filtered'))
        $filterLink.removeClass('filter-active filtered');
        filterBtn.getElement().prop('disabled', true);
        clearBtn.getElement().prop('disabled', true);
    });
    //** set input values **//
    if (settings['state-date'] || settings['state-record']) setInputValues();

    lower.addEventListener(lower.WIDGET_NAME + '.input-changed', inputChange);
    upper.addEventListener(upper.WIDGET_NAME + '.input-changed', inputChange);

    function inputChange() {
      if (lower.getData()['text'] && upper.getData()['text']) {
        filterBtn.getElement().prop('disabled', false);
      } else {
        filterBtn.getElement().prop('disabled', true);
      }
      if (settings['range-selector']) {
        dateSelector.selectOption('title');
      }
    }

    //** set input values functionality **//
    function setInputValues() {
      var stateFilter = settings['state-date'] || settings['state-record'];
      if (stateFilter) {
        if ('date' === stateFilter['type']) {
          if (stateFilter['operands'].hasOwnProperty('value')) {
            if ('>' === stateFilter['operator'] || '=' === stateFilter['operator']) {
              lower.formatToUtc(stateFilter['operands']['value'], true);
              upper.setData('');
            } else if ('<' === stateFilter['operator']) {
              upper.formatToUtc(stateFilter['operands']['value'], true);
              lower.setData('');
            }
          } else {
            lower.formatToUtc(stateFilter['operands']['lower'], true);
            upper.formatToUtc(stateFilter['operands']['upper'], true);
          }
          dateSelector.selectOption('title');
          dateSelector.enableOptions();
        }
        else if ('number' === stateFilter['type']) {
          if (stateFilter['operands'].hasOwnProperty('value')) {
            if ('>' === stateFilter['operator'] || '=' === stateFilter['operator']) {
              lower.setData(stateFilter['operands']['value']);
              upper.setData('');
            } else if ('<' === stateFilter['operator']) {
              upper.setData(stateFilter['operands']['value']);
              lower.setData('');
            }
          } else {
            lower.setData(stateFilter['operands']['lower']);
            upper.setData(stateFilter['operands']['upper']);
          }
        }
        $filterLink.addClass('filtered');
        clearBtn.getElement().prop('disabled', false);
      }
    }

    $('.acxiom-page-container').off('click').on('click', function () {
      $('.filter-popup').hide();
      $('.filter-ico').removeClass('filter-active');
    });

    $('.filter-popup').on('click', function (e) {
      e.stopPropagation();
    });
    $(window).on('resize', function () {
      var offset = $('.filter-active').offset();
      var width = $container.outerWidth(true) - 20;
      if (offset) $container.css({ top: offset.top + 20, left: offset.left - width });
    });
  };

  /**
   * Creates select filter for datatable.
   * @param {Object} config Configuration object.
   */
  A.DataTableComponent.prototype.createSelectFilter = function (config) {
    var that = this;
    var settings = config['settings'];
    var columnIndex = settings['column-index'];
    that.filters = that.filters || {};
    if (that.filters[settings['name']]) {
      that.filters[settings['name']].update();
    } else {
      that.filters[settings['name']] = create(config);
    }
    function create(config) {
      var $filterLink = config['$th'].find('.filter-ico');
      $filterLink = $('<a class="filter-ico"/>', { text: 'filter' });
      $filterLink.appendTo(config['$th'].find('div'));
      var $container = $filterTemp.clone().appendTo('body');
      var dropdown = new A.DropDownComponent($('<div/>').appendTo($container));
      dropdown.initWidget(formatConfig_());
      initFilters_();
      $filterLink.off('click').on('click', function (e) {
        $('.filter-ico').removeClass('filter-active');
        e.stopPropagation();
        var offset = $(this).offset();
        var width = $container.outerWidth(true) - 20;
        if (offset && offset.left < 220)
          width = $container.outerWidth(true) - 100;
        $container.css({ top: offset.top + 20, left: offset.left - width });
        if ($container.is(':visible')) {
          $container.hide();
          $filterLink.removeClass('filter-active');
        } else {
          $('.filter-popup').hide();
          $container.show();
          $filterLink.addClass('filter-active');
        }
      });
      var filterBtn = new A.ButtonComponent($container);
      var options = { name: settings['name'] + '-filter',
        title: '过滤',
        disabled: true
      };
      filterBtn.initWidget(options);

      filterBtn.removeEventListener(filterBtn.WIDGET_NAME + '.clicked');
      filterBtn.addEventListener(filterBtn.WIDGET_NAME + '.clicked',
        function () {
          $container.hide();
          var val = dropdown.getData()['val'];
          config['dataTable'].fnFilter(val, columnIndex);
          filterBtn.getElement().prop('disabled', true);
          clearBtn.getElement().prop('disabled', false);
          if ($filterLink.hasClass('filter-active'))
            $filterLink.removeClass('filter-active').addClass(
              'filtered');
          $('.filter-popup').hide();
        });
      var clearBtn = new A.ButtonComponent($container);
      clearBtn.initWidget({
        title: '清除', name: settings['name'] + '-clear',
        cssClass: 'btn-link',
        disabled: !config['dataTable'].fnSettings()['aoPreSearchCols'][columnIndex]['sSearch']
      });
      clearBtn.removeEventListener(clearBtn.WIDGET_NAME + '.clicked');
      clearBtn.addEventListener(clearBtn.WIDGET_NAME + '.clicked', function () {
        config['dataTable'].fnFilter('', columnIndex);
        $container.hide();
        if ($filterLink.hasClass('filter-active') || $filterLink.hasClass('filtered'))
          $filterLink.removeClass('filter-active filtered');
        dropdown.initWidget(formatConfig_());
        filterBtn.getElement().prop('disabled', isReseted_);
        clearBtn.getElement().prop('disabled', true);
      });
      dropdown.addEventListener(dropdown.WIDGET_NAME + '.changed',
        function () {
          isReseted_ = false; 
          filterBtn.getElement().prop('disabled', isReseted_);
        });

      $('html').off('click').on('click', function (e) {
        if ($('#validationError').length) return;
          e = e || window.event;
          var target = e.target;
          var className = !target.className.length ?
              target.parentNode.className :
              target.className;
          if (!($(target).parents().hasClass('gldp-default') ||
          $(target).hasClass('gldp-default'))) {
            $('.filter-popup').hide();
            $('.filter-ico').removeClass('filter-active');
            if (('' + className).indexOf('sorting') < 0) {
              isReseted_ = true;
              resetFilter_();
            }
          }
      });
      $('.filter-popup').on('click', function (e) {
        e.stopPropagation();
      });
      $(document).keyup(function (e) {
        if (e.keyCode == 27) {
          $container.hide();
        }
      });
      $(window).on('resize', function () {
        var offset = $('.filter-active').offset();
        var width = $container.outerWidth(true) - 20;
        if (offset && offset.left < 220) width = $container.outerWidth(true) - 100;
        if (offset) $container.css({ top: offset.top + 20, left: offset.left - width });
      });

      function formatConfig_() {
        var dropdownData = $.extend(true, {}, settings['component-settings']),
          currentSearch = config['dataTable'].fnSettings()['aoPreSearchCols'][columnIndex];
        if ('user-select' === settings['type']) {
          if (currentSearch.sSearch) {
            $filterLink.addClass('filtered');
            if (clearBtn) clearBtn.getElement().prop('disabled', false);
          }
          else {
            $filterLink.removeClass('filtered');
            if (clearBtn) clearBtn.getElement().prop('disabled', true);
          }
          dropdownData['json-data'].push({ 'val': '', 'text': '所有', 'disabled': true, 'default': '所有'});
          var users = settings['usernames'] ? settings['usernames'] : [],
            i = 0, length = users.length;
          for (; i < length; i++) {
            if (currentSearch.sSearch === users[i]) {
              delete dropdownData['json-data'][0]['disabled'];
              dropdownData['json-data'].push({'text': users[i], 'val': users[i], 'disabled': true, 'default': users[i]});
            }
            else {
              dropdownData['json-data'].push({'text': users[i], 'val': users[i]});
            }
          }
        }
        else if ('select' === settings['type']) {
          var j = 0, len = dropdownData['json-data'].length;
          for (; j < len; j++) {
            if (currentSearch.sSearch === dropdownData['json-data'][j]['val']) {
              dropdownData['json-data'][j]['disabled'] = true;
              dropdownData['json-data'][j]['default'] = dropdownData['json-data'][j]['text'];
              $filterLink.addClass('filtered');
            }
          }
        }
        return dropdownData;
      }

      return{
        name: config.name,
        update: function updateSelector() {
          dropdown.updateDropdownData(formatConfig_()['json-data']);
          filterBtn.getElement().prop('disabled', true);
        }
      };
    }
  };

  var $filterTemp = $('<div class="filter-popup" style="display:none"></div>');

  /**
   * The reference to update spinner. Used in private methods.
   * @type {jQuery|undefined}
   * @private
   */
  var $tableSpinner;

  /**
   * @param {Object} config
   * */
  A.DataTableComponent.prototype.srvUpdateRow = function (config) {
    /**@type {DataTableComponent}*/var that = this;
    if (!that.dataList || !(that.dataList instanceof Array)) return;
    /**@type {Array.<Object>}*/
    var uncompletedRows = that.getUncompletedRows(config['statusArray']);
    if (uncompletedRows.uncompletedList.length && that.idleTime < 2) {
      /** @type {string} */
      var params = '?' + config['query-param'] + '=' +
        uncompletedRows.ids.join('&' + config['query-param'] + '=');
      //*** workaround for hiding progress-loader on checking statuses for update
      var $progressLoader = $('#progress-loader').width(0);
      $tableSpinner = $tableSpinner || $('<div style="display:none"></div>').addClass('progress-loader').appendTo($(document).find('body'));
      //***
      that.dataLoader.loadData(config['status-url'], function (data) {
        data['results'] = data['results'] || data;
        /**@type {Array.<Object>}*/var list = data['results'];
        /**@type {number}*/ var length = list.length;
        for (var i = 0; i < length; i++) {
          /**@type {Object}*/var value = list[i];
          /**@type {Array.<Object>}*/
          var itemsUpdate = uncompletedRows.uncompletedList.filter(function (currentUpdate, index) {
            var status = typeof currentUpdate['status'] === 'object' ? currentUpdate['status']['status'] : currentUpdate['status'];
            return currentUpdate['id'] === value[config['id-prop']] && value['status'] && status !== value['status'];
          });
          var itemsLength = itemsUpdate.length;
          if (itemsLength === 0) continue;
          var itemUpdate = itemsUpdate[0];
          for (var j = 0; j < config['columns'].length; j++) {
            var column = config['columns'][j];
            if (column['srv-prop'] === 'status' && typeof itemUpdate['status'] === 'object') {
              itemUpdate['status']['status'] = value[column['srv-prop']];
            } else {
              itemUpdate[column['table-prop']] = value[column['srv-prop']];
            }
            that.updateRow({ value: itemUpdate[column['table-prop']], row: itemUpdate['index'], column: column['table-prop'] });
            config['callback'] && config['callback'](value);
          }
          if (value['status'] === config['new-value']) {
            $tableSpinner.show();
          }
        }
        //*** simulate update
        setTimeout(function () {
          $tableSpinner.hide();
          $progressLoader.width('100%');
        }, 500);
        //***
      }, params);
    }
    setTimeout(function () {
      that.srvUpdateRow(config);
    }, 1e4);
  };
  /**@type {number}*/
  A.DataTableComponent.prototype.idleTime = 0;

  $(document).ready(function () {
    //Increment the idle time counter every minute.
    var idleInterval = setInterval(timerIncrement, 60000); // 1 minute
    //Zero the idle timer on mouse movement.
    $(this).mousemove(function (e) {
      A.DataTableComponent.prototype.idleTime = 0;
    });
    $(this).keypress(function (e) {
      A.DataTableComponent.prototype.idleTime = 0;
    });
  });

  function timerIncrement() {
    A.DataTableComponent.prototype.idleTime += 1;
  }

  /**
   * Initializes available filters.
   * @private
   */
  function initFilters_() {
    /** @type {NodeList} */ var filters = $('.filter-ico');
    /** @type {NodeList} */ var popups = $('.filter-popup');
    for (/** @type {number} */var i = 0; i < filters.length;) {
      filters[i++].onclick = function() { onclick_(popups); };
    }
  }

  /**
   * Performs onclick event for filters buttons.
   * @param {NodeList} popups List of popups.
   * @private
   */
  function onclick_(popups) {
    setTimeout(function() {
      for (/** @type {number} */ var i = 0; i < popups.length; i++) 
        if ('none' != popups[i].style.display) popup_ = popups[i];
      select_ = $(popup_).find('SELECT')[0];
      if (checkIsDate_()) {
        inputs_ = popup_.getElementsByTagName('INPUT');
        isDate_ = checkIsDate_();
      } else inputs_ = records_;
      fillData_();
    }, 100);  
  }

  /**
   * Fills data_ object.
   * @private
   */
  function fillData_() {
    data_['selected'] = select_ ? select_.selectedIndex : null;
    for (i = 0; i < inputs_.length;i++)
      data_['input' + i] = isDate_ ? inputs_[i].value :
      inputs_[i].getData()['text'];
  }

  /**
   * Resets filter.
   * @private
   */
  function resetFilter_() {
    if (popup_ && inputs_) {
      if (select_) select_.selectedIndex = data_['selected'];
      for (/** @type {number} */ var i = 0; i < inputs_.length; i++) {
        if (checkIsDate_()) inputs_[i].value = data_['input' + i]
        else inputs_[i].setData(data_['input' + i]);
      }
      popup_.getElementsByTagName('BUTTON')[0].setAttribute('disabled', true);
      popup_.style.display = 'none';
      isDate_ = false;
    }
  }

  /**
   * Checks is popups inputs are date inputs.
   * @private
   */
  function checkIsDate_() {
    return $(popup_.getElementsByTagName('INPUT')[0]).hasClass('date-input');
  }

  /** @type {Node} */
  var records_ = [];

  /** @type {Node} */
  var inputs_ = [];

  /** @type {number} */
  var select_ = null;

  /** @type {Object} */
  var data_ = {}

  /** @type {Element} */
  var popup_ = null;

  /** @type {boolean} */
  var isReseted_ = false;

  /** @type {boolean} */
  var isDate_ = false;

  /**
   * @param {Array.<string>} statusArray
   * @return {Object}
   * */
  A.DataTableComponent.prototype.getUncompletedRows = function (statusArray) {
    /**@type {Array.<string|number>}*/var uncompletedIds = [];
    /**@type {Array.<Object>}*/
    var uncompletedList = this['dataList'].filter(function (current, indexInArray) {
      var status = typeof current['status'] === 'object' ? current['status']['status'] : current['status'];
      if (($.inArray(status, statusArray)) == -1) {
        uncompletedIds.push(current['id']);
        current['index'] = indexInArray;
        return true;
      }
      return false;
    });
    return {
      uncompletedList: uncompletedList,
      ids: uncompletedIds
    };
  };
}, '0.0.1', {
  requires: ['datatable-component',
    'input-component',
    'dropdown-component',
    'button-component'
  ]
});
