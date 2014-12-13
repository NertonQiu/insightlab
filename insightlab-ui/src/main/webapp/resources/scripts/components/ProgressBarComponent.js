;/**
 * Progressbar component.
 *
 * @module progressbar-component
 **/
ACX.add('progressbar-component', function(A) {
  /**
   * @constructor
   * @extends {BaseComponent}
   * @param {Node|Element|string} container Widget container or its ID.
   */
  function ProgressBarComponent(container) {
    BaseComponent.apply(this, arguments);

    /**
     * Initialize {ProgressBarComponent}
     * @param {Object} config
     * @protected
     * @this {ProgressBarComponent}
     */
    this.initWidget = function(config) {
      config_ = config;
      self_.JSON_URI = config_['json-url'];
      self_.WIDGET_NAME = config_['name'];
      $container.html('');
      if (self_.JSON_URI) {
        self_.handleEvent();
      } else {
        self_.draw();
      }
    };

    /**
     * Draws progressbar component
     * @this {ProgressBarComponent}
     */
    this.draw = function() {
      /** @type {jQuery}*/
      var $progressBarContainer = $('<div class="progress-inner"></div>');

      if (config_['error']) {
        var $error = $('<img/>').attr({'src': config_['error'], 'class': 'error'}).hide();
        $($error).appendTo($progressBarContainer);
      }
      if (config_['title']) {
        $('<span/>', { text: config_['title'] }).appendTo($progressBarContainer);
      }
      /** @type {jQuery}*/
      var $progressBarDiv = $('<div/>', { 'class': 'progressbar' });

      if (config_['tooltip']) {
        $progressBarDiv.attr('add-title', config_['titleTooltip']);
      }
      $progressBarDiv.appendTo($progressBarContainer);
      $progressBar = $progressBarDiv.progressbar({
        value: parseFloat((data_.percentage * 100).toFixed(2)) > 100 ? 100 : parseFloat((data_.percentage * 100).toFixed(2))
      });
      $container.append($progressBarContainer);
      self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.drawed', self_));
    };

    /**
     * This method is called whenever an event occurs of the type for which the
     * EventListener interface was registered.
     * @protected
     */
    this.handleEvent = function() {
      self_.loadData(function(result) {
        var error = self_.getErrorMessage(result);
        if (error) {
          log(error);
        } else if (config_['self-fill']) {
          data_['percentage'] = result.hasOwnProperty('percentage') ? parseFloat(result['percentage']) / 100 : 100;
          self_.draw();
        } else {
          if (result[config_['response-mapping']['object']]) result = result[config_['response-mapping']['object']];
          data_['percentage'] = (result[config_['response-mapping']['value']] / result[config_['response-mapping']['max-value']]);
          self_.draw();
          recolorBar();
          if (config_['isTitle']) {
            var title = config_['response-mapping']['value-title'] + result[config_['response-mapping']['value']].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') +
              '\n' + config_['response-mapping']['max-value-title'] +
              result[config_['response-mapping']['max-value']].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            $progressBar.attr('title', title);
          }
          self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.updated', self_));
        }
        self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.loaded', self_));
      }, config_['params'] ? ('?' + $.param(config_['params'])) : null);
    };

    /**
     * @param {Number} value
     * @protected
     * @this {ProgressBarComponent}
     */
    this.setData = function(value) {
      data_['percentage'] = value;
      $progressBar.progressbar({
        value: parseFloat((data_['percentage'] * 100).toFixed(2)) > 100 ? 100 : parseFloat((data_['percentage'] * 100).toFixed(2))
      });
    };

    /**
     * @param {String} value
     * @param {String} error
     * @protected
     * @this {ProgressBarComponent}
     */
    this.updateStatus = function(value, error) {
      var status;
      if ($('.progress-inner span').length) {
        status = $('.progress-inner span');
      } else {
        status = $('<span/>').appendTo('div.progress-inner');
      }
      if (error) {
        $('img.error').show();
        var progressContainer = $progressBar.find('div');
        progressContainer.attr('style', 'background: #d36b6b; border-color: #d15656');
        progressContainer.closest('.progress-inner').addClass('error');
        $('.progressbar').attr('add-title', error);
        tooltip('.progressbar');
      }
      $(status).text(value);
    };

    /**
     * Gets data of progress bar
     * @protected
     * @return {Object} data_.
     */
    this.getData = function() {
      return data_;
    };

    var recolorBar = function() {
      var progressContainer = $progressBar.find('div');
      if (data_.percentage <= 0.7) {
        progressContainer.addClass('progress-green');
      } else if (data_.percentage <= 0.9) {
        progressContainer.addClass('progress-yellow');
      } else {
        progressContainer.addClass('progress-red');
      }
    };

    /**
     * The reference to container.
     * @type {jQuery} Jquery object
     * @private
     */
    var $container = $(this.getContainer());

    /**
     * @type {Object}
     */
    var $progressBar;

    /**
     * The configuration of component.
     * @type {Object}
     * @private
     */
    var config_ = null;

    /**
     * Storage for settings data.
     * @type {Object}
     * @private
     */
    var data_ = { 'percentage': 0 };

    /**
     * The reference to current class instance. Used in private methods.
     * @type {ProgressBarComponent}
     * @private
     */
    var self_ = this;
  }
  A.ProgressBarComponent = ProgressBarComponent;
});
