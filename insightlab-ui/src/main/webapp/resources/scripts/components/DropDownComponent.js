/**
 * Dropdown component.
 *
 * @module dropdown-component
 **/
ACX.add('dropdown-component', function (A) {
  /**
   * @constructor
   * @param {Node|Element|string | jQuery} container Widget container or its ID.
   * @extends BaseComponent
   */
  function DropDownComponent(container) {
    BaseWidget.apply(this, arguments);

    /**
     * Initialize {DropDownComponent}
     * @param {Object} config
     * @protected
     * @this {DropDownComponent}
     */
    this.initWidget = function (config) {
      config_ = config;
      self_.WIDGET_NAME = config_['name'];
      self_.JSON_URI = config_['json-url'];
      // check if current dropdown has dependency from other widget
      if (config['prevWidget']) {
        if (config['dependentWidgets']) {
          self_.addEventListener((config_['parent-component'] || '') + config['dependentWidgets']['name'] + '.changed', function (evt) {
            dependentParam.val = evt['widget'].getData()['val'];
            dependentParam.param = config['dependentWidgets'].param;
          });
        }
        self_.removeEventListener((config_['parent-component'] || '') + config['prevWidget'] + '.changed');
        self_.addEventListener((config_['parent-component'] || '') + config['prevWidget'] + '.changed', self_.handleEvent);
        buildHeader();
      } else {
        self_.handleEvent();
      }

      // add reset option
      if (config_['reset']) {
        self_.addEventListener((config_['parent-component'] || '') + self_.WIDGET_NAME + '.changed', function () {
          if (data_['val'] != 'reset_dropdown') {
            if (!hasReset) {
              addOptions([
                { 'val': 'reset_dropdown', 'text': 'Reset'}
              ]);
            }
            hasReset = true;
          } else {
            data_ = {};
            self_.handleEvent();
            hasReset = false;
          }
        });
      }
    };

    /**
     * This method is called whenever an event occurs of the type for which the
     * EventListener interface was registered.
     * @param {WidgetEvent} evt The WidgetEvent contains contextual information about the event.
     * @this {DropDownComponent}
     */
    this.handleEvent = function (evt) {
      $container.html('');
      // clears parent value
      // value sets in parentVal in method getParams
      parentVal = null;
      data_ = { };
      //set parameters for load data
      var params = getParams(evt);

      // when widget depends on previous which is not selected, it is disabled
      disabled = !((config_['prevWidget'] && parentVal) || !config_['prevWidget']);

      data_['disabled'] = disabled;

      // build header for dropdown
      buildHeader();

      // option for static filter, config_['json-data'] stores static values for dropdown
      if (config_['json-data']) {
        //load dropdown options
        loadDropdown(config_['json-data']);
        $('#progress-loader').hide();
      } else if (!disabled) {
        self_.loadData(loadDropdown, '?' + $.param(params));
      } else {
        self_.dispatchEvent(new WidgetEvent((config_['parent-component'] || '') + self_.WIDGET_NAME + '.changed', self_));
      }
    };

    /**
     * Gets state of checkbox
     * @protected
     * @return {Object}
     */
    this.getData = function () {
      if (config_['mapped-value']) {
        return {val: valuesMapper[data_['val']],
          text: data_['text']
        };
      }
      return data_;
    };

    this.enableOptions = function (options) {
      if (!options) {
        $dropdownContainer.find('option:disabled').not('option[value="title"]').prop('disabled', false);
      } else {
        for (var index = 0; index < options.length; index++) {
          options[index]['value'] ? $dropdownContainer.find('option[value=' + options[index]['value'] + ']').prop('disabled', options[index]['disabled']) :
            $dropdownContainer.find('option:contains(' + options[index]['text'] + ')').prop('disabled', options[index]['disabled']);
        }
      }
    };

    this.selectOption = function (value) {
      if (value && value !== data_.val) {
        var $select = $dropdownContainer.find('select');
        var $option = $select.find('option[value=' + value + ']');
        data_['text'] = $option.text();
        data_['val'] = value;
        if (config_['abbreviation']) {
          data_['abbreviation'] = $option.attr('data-abbr');
        }
        $select.val(value);
        // fire changed event
        self_.dispatchEvent(new WidgetEvent((config_['parent-component'] || '') + self_.WIDGET_NAME + '.changed', self_));
      }
    };

    this.updateDropdownData = function (data) {
      $dropdownContainer.find('select option').not('option[value="title"]').remove();
      loadDropdown(data);
    };

    /**
     * Sets default value for filter
     * @param {number|string} val
     */
    this.setDefaultValue = function (val) {
      config_['default'] = val;
      data_.val = null;
    };

    /**
     * @param {Object} options for header.
     * @return {string} Returns component's HTML template.
     */
    var getHtmlTemplate = function (options) {
      return (options['title'] ? '<p>' + options['title'] + '</p>' : '') +
        '<div class="acxiom-select span12">' +
        '<select' + (options['disabled'] ? ' disabled="disabled"' : '') + '>' +
        '<option disabled="disabled" value="title" selected="selected">' + (options['text'] || '') + '</option>' +
        '</select>' +
        '</div>';
    };

    /**
     * creates header for dropdown
     * @private
     */
    var buildHeader = function () {
      // sets specific class for dropdown container
      $container.addClass(config_['css-class']);
      // sets title
      var title = '';
      if (config_['title']) {
        // filter can have different titles dependent on previous selected filter value
        if (config_['specialparentVal'] && parentVal == config_['specialparentVal']) {
          title = config_['specialtitle'];
          config_['defaultValue'] = 'Select a ' + config_['specialtitle'];
        } else {
          title = config_['title'];
          config_['defaultValue'] = 'Select a ' + config_['title'];
        }
      }
      // create container for dropdown
      $dropdownContainer = $(getHtmlTemplate({ 'disabled': disabled, 'text': config_['defaultValue'], 'title': title }));
      $dropdownContainer.appendTo($container);
      // add event on option select
      if ($.browser.mozilla) {
        //$dropdownContainer.find('select option:first-child').removeAttr('disabled');
        $dropdownContainer.find('select').on('click', "option:not(':first-child')", function () {
          var $selector = $(this).filter(':selected');
          if ($selector.val() === data_['val']) return;
          // set selected value in widget data storage
          data_['val'] = $selector.val();
          data_['text'] = $selector.text();
          if (config_['abbreviation']) {
            data_['abbreviation'] = $selector.attr('data-abbr');
          }
          // fire changed event
          self_.dispatchEvent(new WidgetEvent((config_['parent-component'] || '') + self_.WIDGET_NAME + '.changed', self_));
        });
      } else {
        $dropdownContainer.find('select').on('change', function () {
          var $selector = $(this).find(':selected');
          // set selected value in widget data storage
          data_['val'] = $selector.val();
          data_['text'] = $selector.text();
          if (config_['abbreviation']) {
            data_['abbreviation'] = $selector.attr('data-abbr'); //this.getAttribute('data-abbr');
          }
          // fire changed event
          self_.dispatchEvent(new WidgetEvent((config_['parent-component'] || '') + self_.WIDGET_NAME + '.changed', self_));
        });
      }
    };

    /*
     * Create options for dropdown from result
     * @param {Array} result The component data. expected: {results:[{text:"", val:""}]}
     * @private
     */
    var loadDropdown = function (result) {
      // verify if result is corect
      var error = self_.getErrorMessage(result);
      if (error && !disabled) {
        $container.html('<div class="alert">' + error + '</div>');
        $('#progress-loader').hide();
      } else {
        if (result.hasOwnProperty('results')) result = result['results'];

        // if filter is enable but has no data options
        if (result.length == 0 && !disabled) {
          $dropdownContainer.find('select option:first').text('Change previous filter');
          $dropdownContainer.find('select').prop('disabled', true);
        }
        var $optionList = $dropdownContainer.find('select');
        for (var j = 0; j < result.length; j++) {
          var val = config_['val-property'] ? result[j][config_['val-property']] : result[j]['val'];
          var $option = $('<option/>', {
            value: val
          });
          var text = config_['text-property'] ? result[j][config_['text-property']] : result[j]['text'];
          $option.text(text);

          if (result[j]['disabled']) {
            $option.prop('disabled', true);
          }
          if (config_['abbreviation']) {
            $option.attr('data-abbr', result[j][config_['abbreviation']]);
          }

          if (('default' in result[j] && result[j]['default']) ||
            //check if dropdown has default prop and if its value is equal current value
            (config_['default'] && config_['default'] == val) ||
            // check if dropdown has one option
            result.length == 1 || (config_['default-option'] && config_['default-option'] == val)) {
            $option.prop('selected', true);
            data_['val'] = val;
            data_['text'] = text;
            config_['default-option'] = null;
            if (config_['abbreviation']) {
              data_['abbreviation'] = result[j][config_['abbreviation']];
            }
            config_['default'] = null;
          }

          $option.appendTo($optionList);
        }

      }
      self_.dispatchEvent(new WidgetEvent((config_['parent-component'] || '') + self_.WIDGET_NAME + '.changed', self_));
      firstLoad = false;
    };

    /**
     * get parameters for loading data in dropdown
     * @param {WidgetEvent} evt The WidgetEvent contains contextual information about the event.
     * @return {Object} params parameters for xmlhttprequest.
     */
    var getParams = function (evt) {
      //config_["params"] contains additional parameters for api call
      //evt contains value of previous widget which has influence on current (previous widget)
      //parentVal property name of previous widget
      var params = config_['params'] ? config_['params'] : {};
      if (evt && 'parentVal' in config_) {
        if ('widget' in evt && evt['widget'].getData()['val']) {
          parentVal = evt['widget'].getData()['val'];
          params[config_['parentVal']] = evt['widget'].getData()['val'];
        }
      }
      if (config_['dependentWidgets']) {
        params[dependentParam.param] = dependentParam.val;
      }
      return params;
    };

    /**
     * Uses for recents requests.
     * @type {boolean} is first data load for dropdown
     * @private
     */
    var firstLoad = true;

    /**
     * The reference to container.
     * @type {jQuery}
     * @private
     */
    var $container = $(this.getContainer());

    /**
     * The reference to current dropdown container.
     * @type {jQuery}
     * @private
     */
    var $dropdownContainer;

    /**
     * value of previous (dependent) widget
     * @type {Object}
     * @private
     */
    var parentVal = null;

    /**
     * Additional dependent component
     * @type {Object}
     * @private
     */
    var dependentParam = {};

    /**
     * Flag, has reset option
     * @type {boolean}
     * @private
     */
    var hasReset = false;

    /**
     * Dynamically adds options to dropdown
     * @param {Array} options
     */
    var addOptions = function (options) {
      var selectContainer = $(self_.getContainer()).find('div.acxiom-select');
      var optionList = $(selectContainer).find('select');
      for (var j in options) {
        var $option = $('<option/>').appendTo(optionList);
        $option.text(options[j]['text']);
        $option.attr('value', options[j]['val']);
      }
    };

    /**
     * Storage for configuration data.
     * @type {Object}
     * @private
     */
    var config_ = null;

    /**
     * flag, is dropdown enable for selecting data
     * type {boolean}
     */
    var disabled = true;

    /**
     * Storage for settings data.
     * @type {Object}
     * @private
     */
    var data_ = {};

    /**
     * The reference to current class instance. Used in private methods.
     * @type {DropDownComponent}
     * @private
     */
    var self_ = this;

    var valuesMapper = {
      'analytic-national': {type: 'analytic-national',
        'targettype': 'Dataset',
        'referencetype': 'National'
      },
      'analytic-survey': { type: 'analytic-survey',
        'targettype': 'Dataset',
        'referencetype': 'SurveyResponse'},
      'survey-national': {type: 'survey-national',
        'targettype': 'SurveyResponse',
        'referencetype': 'SurveyUniverse'}
    };
  }

  A.DropDownComponent = DropDownComponent;
});