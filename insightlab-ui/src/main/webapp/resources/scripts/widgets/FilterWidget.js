/**
 * Filter widget.
 *
 * @module filter-widget
 **/
ACX.add('filter-widget', function (A) {
  /**
   * @constructor
   * @extends BaseWidget
   * @param {Node|Element|string|jQuery} container Widget container or its ID.
   */
  function FilterWidget(container) {
    BaseWidget.apply(this, arguments);

    /**
     * Initialize filter
     * @param {Object || null || undefined} config Configurations of widget.
     * @this {FilterWidget}
     */
    this.initWidget = function (config) {
      config_ = config;
      self_.WIDGET_NAME = config_['name'];
    };

    /**
     * This method is called whenever an event occurs of the type for which the
     * EventListener interface was registered.
     * @param {Array.<Object>} result The array of filter components.
     * @this {FilterWidget}
     */
    this.handleResult = function (result) {
      data_ = {};
      components_ = [];
      /** @type {number}*/var length = result.length;
      if (result) {
        for (/** @type {number} */var i = 0; i < length; i++) {
          result[i]['parent-component'] = config_['name'];
          /** @type {DropDownComponent|PopupFilter}*/
          var component = self_.createComponent(result[i]);
          if (component) {
            components_.push(component);
          }
        }
        self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
        if (components_.length === 0) {
          self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.loaded', self_));
        }
      } else {
        $container.text('No elements');
      }
    };

    /**
     * Selects saved filters values
     * @param {Object} data Contains values for filters.
     */
    this.setFiltersValue = function (data) {
      if (!data || data.responseID === '0') return;
      /** @type {number}*/var len = components_.length;
      /**
       * First dropdown that has new value
       * @type {?DropDownComponent}
       */var firstDropdown = null;
      for (/** @type {number}*/var i = 0; i < len; i++) {
        /** @type {DropDownComponent|PopupFilter}*/
        var dropdown = components_[i];
        /** @type {string|number}*/var val = dropdown.getData().val;
        if (val !== data[dropdown.WIDGET_NAME] || firstDropdown) {
          dropdown.setDefaultValue(data[dropdown.WIDGET_NAME]);
          firstDropdown = firstDropdown || dropdown;
        }
      }
      firstDropdown && firstDropdown.selectOption(data[firstDropdown.WIDGET_NAME]);
    };

    /**
     * Create components for filter widget
     * @protected
     * @this {FilterWidget}
     * @param {Object} config Configuration of component.
     * @return {BaseWidget}
     */
    this.createComponent = function (config) {
      /** @type {DropDownComponent|PopupFilter}*/var component;
      var div = $('<div></div>').appendTo($container)[0];
      if ('dropdown' === config.control) {
        component = new A.DropDownComponent(div);
      } else if ('pop-up' === config.control) {
        component = new A.PopupFilter($(div));
      }
      component.initWidget(config);
      return component;
    };


    /**
     * Gets settings data.
     * @return {Object} Returns settings data as dictionary representation.
     * @this {FilterWidget}
     */
    this.getData = function () {
      for (/** @type {number} */ var i = 0; i < components_.length; i++) {
        if (components_[i]) {
          data_[components_[i].WIDGET_NAME] = components_[i].getData();
        }
      }
      return data_;
    };

    /**
     * Gets settings data.
     * @return {Array.<DropDownComponent>} filter components.
     * @this {FilterWidget}
     */
    this.getFilterWidgets = function () {
      return components_;
    };

    /**
     * The reference to container.
     * @type {jQuery} Jquery object
     * @private
     */
    var $container = $(this.getContainer());

    /**
     * Storage for settings data.
     * @type {Object}
     * @private
     */
    var data_ = {};

    /**
     * The configuration of component.
     * @type {Object | null | undefined}
     * @private
     */
    var config_;

    /**
     * The reference to current class instance. Used in private methods.
     * @type {FilterWidget}
     * @private
     */
    var self_ = this;

    /**
     * Array of dropdowns
     * @type {Array.<DropDownComponent|PopupFilter>}
     * @private
     */
    var components_ = [];
  }

  A.FilterWidget = FilterWidget;
}, '0.0.1', {requires: ['popup-filter', 'dropdown-component']});

/**
 * Popup filter.
 *
 * @module popup-filter
 **/
ACX.add('popup-filter', function (A) {
  /**
   * @constructor
   * @extends BaseWidget
   * @param {jQuery} container
   */
  function PopupFilter(container) {
    BaseWidget.apply(this, arguments);

    /**
     * Initialize component
     * @param {Object} config Configuration parameter.
     */
    this.initWidget = function (config) {
      config_ = mergeConfig(config);
      self_.WIDGET_NAME = config_.name;
      self_.JSON_URI = config_['json-url'];
      data_.val = config_.insightId;
      container.addClass(config_.cssClass);
      container.append('<p>' + config_.title + '</p>');
      button_ = new A.ButtonComponent(container);
      button_.initWidget(config_.button);
      button_.addEventListener(button_.WIDGET_NAME + '.clicked', function () {
        analyticDatasetPopup(config_['pop-up']);
      });
      loadData();
    };

    /**
     * @return {Object} Filter data.
     */
    this.getData = function () {
      return data_;
    };

    /**
     * Selects option for popup filter.
     * @param {string} value
     */
    this.selectOption = function (value) {
      if (value && value !== data_.val) {
        config_.insightId = value;
        loadData();
      }
    };

    /**
     * Sets default value for filter
     * @param {number|string} val
     */
    this.setDefaultValue = function (val) {
      config_.insightId = val;
    };

    /**
     * Loads and sets default value for component.
     */
    function loadData() {
      self_.loadData(function (data) {
        var error = self_.getErrorMessage(data);
        if (error) {
          container.html(error);
        } else {
          data_.val = data.id;
          data_.text = data.description;
          button_.getElement().text(data_.text);
          self_.dispatchEvent(new WidgetEvent((config_['parent-component'] || '') + self_.WIDGET_NAME + '.changed', self_));
        }
      }, '?insightid=' + config_.insightId);
    }

    /**
     * Show Analytic Dataset pop-up
     * @param {Object} config Configuration for build model.
     */
    var analyticDatasetPopup = function (config) {
      loader_.loadData(config['check-authenticated-url'], function (response) {
        if (response && response['username']) {
          config_['pop-up']['table']['username'] = response['username'];
          $('#analyticDataset-popup').appendTo('body');
          ACXM.Modal.toggle('analyticDataset-popup', {
            modalwidth: 730,
            modalheight: 390
          });
          table_ = table_ || initTable(config_['pop-up']);
          table_.setSelectedRows([data_.val || config_.insightId]);
        }
      });
    };

    /**
     * Initialize table and select button in popup.
     * @param {Object} config
     * @return {DataTableComponent}
     */
    function initTable(config) {
      /**@type {Element|Node}*/
      var tableDiv = document.getElementById('ad-table');
      /**@type {DataTableComponent}*/
      var table = new A.DataTableComponent(tableDiv);
      table.WIDGET_NAME = config['table'].name;
      table.JSON_URI = config['table']['tableoptions']['sAjaxSource'];
      /**@type {ButtonComponent}*/
      var selectBtn = new A.ButtonComponent($('#table-select-btn').empty());
      selectBtn.initWidget(config['select-btn']);
      table.initWidget(config['table']);
      table.removeEventListener(table.WIDGET_NAME + '.checked');
      table.addEventListener(table.WIDGET_NAME + '.checked', function (evt) {
        /**@type {Array.<Object>}*/
        var selected = evt['widget'].getData()['sObjects'];
        data_['selected'] = selected.length ? selected[0] : null;
        selectBtn.getElement().prop('disabled', !data_['selected']);
      });
      table.holderTable();
      $('.x-btn.acxiom-modal-close').on('click', function () {
        self_.dispatchEvent(new WidgetEvent(config['name'] + '.destroyed', self_));
      });
      selectBtn.removeEventListener(selectBtn.WIDGET_NAME + '.clicked');
      selectBtn.addEventListener(selectBtn.WIDGET_NAME + '.clicked', function () {
        if (data_['selected'] && data_.val !== data_['selected'].id) {
          data_.val = data_['selected'].id;
          data_.text = data_['selected'].description;
          button_.getElement().text(data_.text);
          self_.dispatchEvent(new WidgetEvent((config_['parent-component'] || '') + self_.WIDGET_NAME + '.changed', self_));
        }
        $('#analyticDataset-popup div.x-btn.acxiom-modal-close').click();
      });
      self_.removeEventListener(table.WIDGET_NAME + '.updated-data');
      self_.addEventListener(table.WIDGET_NAME + '.updated-data', function () {
        data_['selected'] = null;
        table_.setSelectedRows([data_.val || config_.insightId]);
      });
      return table;
    }

    /**
     * Merge config with default configurations.
     * @param {?Object} config
     * @return {Object} Merged configurations.
     */
    function mergeConfig(config) {
      if (config) {
        for (/**@type {string}*/var prop in config) {
          defaultConfig[prop] = config[prop];
        }
      }
      return defaultConfig;
    }

    /**@type {Object}*/ var defaultConfig = {
      'name': 'insightID',
      'title': 'Analytic Dataset',
      'defaultValue': 'Select an Insight',
      'json-url': '/Insights/getInsight',
      'pop-up': {
        'select-btn': {
          name: 'select-btn',
          'title': 'Select',
          disabled: true
        },
        'table': {
          'name': 'insight-selection-table',
          'id_column_name': 'id',
          'isObjectSelect': true,
          'choosen': true,
          'default-filters': {'status': {
            'operator': '=',
            'operands': {
              'value': 'complete'
            }
          }},
          'server-side-processing': true,
          'column-filters': [
            {
              'column-index': 3,
              'type': 'date-range',
              'name': 'insight-date-range',
              'range-selector': {
                'name': 'insight-date-range-selector',
                'control': 'dropdown',
                'json-data': [
                  { 'val': '', 'text': '所有', 'default': true, 'disabled': true },
                  { 'val': '30', 'text': '过去30'},
                  { 'val': '60', 'text': '过去60'},
                  { 'val': '90', 'text': '过去90'},
                  { 'val': '365', 'text': '过去Year'}
                ],
                'css-class': '',
                'defaultValue': 'Select Date Range'
              }
            },
            { 'column-index': 4, 'type': 'number-range', 'name': 'insight-number-range' },
            { 'column-index': 2, 'type': 'user-select', 'name': 'insight-user-select', 'component-settings': { 'name': 'insight-user-dropdown-filter',
              'json-data': [],
              'defaultValue': 'Created by'
            }
            }
          ],
          'use-column-filters': true,
          'tableoptions': {
            'sAjaxSource': '/Insights/InsightList',
            'bPaginate': true,
            'bServerSide': true
          }
        },
        'check-authenticated-url': '/srv/authtoken/user/details/username'
      },
      button: {
        name: 'insight-select',
        title: 'Select an Insight',
        cssClass: 'popup-btn'
      }
    };
    /**@type {DataTableComponent}*/ var table_;
    /**@type {DataLoader}*/ var loader_ = new DataLoader;
    /**@type {Object}*/ var data_ = { };
    /**@type {ButtonComponent}*/ var button_;
    /**@type {Object}*/ var config_;
    /**@type {PopupFilter}*/ var self_ = this;
  }

  A.PopupFilter = PopupFilter;
}, '0.0.1', {requires: ['datatable-component','button-component']});
