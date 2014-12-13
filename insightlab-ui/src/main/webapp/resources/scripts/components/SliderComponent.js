;/**
 * Slider component.
 *
 * @module slider-component
 **/
ACX.add('slider-component', function(A) {
  /**
   * @constructor
   * @extends {BaseComponent}
   * @param {Node|Element|string | jQuery} container Widget container or its ID.
   */
  function SliderComponent(container) {
    BaseComponent.apply(this, arguments);

    /**
     * Initialize {SliderComponent}
     * @param {Object} config
     * @protected
     * @this {SliderComponent}
     */
    this.initWidget = function(config) {
      config_ = config;
      data_.selected_val = config.val;
      self_.WIDGET_NAME = config_['name'];
      self_.draw();
    };

    /**
     * Draws slider component
     * @this {SliderComponent}
     */
    this.draw = function() {
      $container.html('');
      if (config_['title']) {
        $('<H3/>', { text: config_['title'] }).appendTo($container);
      }
      if (config_['label']) {
        $('<label class="label-field">' + config_['label'] + '</label>').appendTo($container);
      }
      buildSlider();
    };

    /**
     * Creates slider
     * @private
     */
    var buildSlider = function() {
      if (!config_ || !config_.max) {
        log('No options available'); //Wrong config
      } else {
        var $div = $('<div/>', { id: config_['id'] });
        if (config_['tooltip']) $div.attr('add-title', config_['tooltip']);
        $slider = $div.slider({
          range: 'min',
          min: config_.min || 0,
          max: config_.max || 100,
          step: config_.step || 1,
          value: config_.val,
          change: function(event, ui) {
            data_['selected_val'] = ui.value;
            data_['index_step'] = $.inArray(ui.value, sliderSteps);
            self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
          }
        });
        if (!config_['enable'])
          $div.slider('disable');
        $div.appendTo($container);
        buildStepLabels($div);
      }
    };

    var buildStepLabels = function(sliderContent) {
      var $ulContainer = $('<ul/>').addClass('slider-list unstyled');
      $('<li/>', { text: config_.firstElement || 0 }).appendTo($ulContainer);
      if (config_.otherElements) {
        for (var i = 0; i < config_['otherElements'].length; i++) {
          $('<li/>', { text: config_['otherElements'][i]['element'], style: 'margin-left: ' + config_['otherElements'][i]['margin'] }).appendTo($ulContainer);
        }
      }
      $ulContainer.insertBefore(sliderContent);
      if (config_['specificLabels']) {
        var $labelsContainer = $('<ul/>').addClass('slider-list');
        $labelsContainer.css('margin-top', '-15px');
        var interval = config_.step / (config_.max - config_.min) * 100, width = 0;
        for (var /** @type {number} */j = config_.min; j <= config_.max; j = j + config_.step) {
          var $optionLi = $('<li/>').appendTo($labelsContainer);
          $optionLi.css('padding-left', width + '%');
          $optionLi.text(j);
          sliderSteps.push(j);
          width += interval;
        }
        $labelsContainer.insertAfter(sliderContent);
      } else {
        for (var /** @type {number} */j = 1; j <= config_.max; j++) {
          var $optionLi = $('<li/>').appendTo($ulContainer);
          $optionLi.css('padding-left', (j / config_.max * 100) + '%');
          $optionLi.text(j);
        }
      }
    };

    this.setValue = function(value) {
      if ($slider && value) {
        $slider.slider('value', value);
      }
    };

    var $slider;

    /**
     * Gets state of checkbox
     * @protected
     * @this {SliderComponent}
     * @return {Object}
     */
    this.getData = function() {
      return data_;
    };

    /**
     * The reference to container.
     * @type {jQuery} Jquery object
     * @private
     */
    var $container = $(this.getContainer());

    /**
     * The configuration of component.
     * @type {Object}
     * @private
     */
    var config_;

    /**
     * Storage for settings data.
     * @type {Object}
     * @private
     */
    var data_ = { };
    /**
     * Array of slider steps.
     * @type {Array}
     * @private
     */
    var sliderSteps = [];

    /**
     * The reference to current class instance. Used in private methods.
     * @type {SliderComponent}
     * @private
     */
    var self_ = this;
  }
  A.SliderComponent = SliderComponent;
});
