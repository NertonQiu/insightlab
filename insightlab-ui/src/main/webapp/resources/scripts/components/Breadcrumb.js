/**
 * Breadcrumb component.
 *
 * @module breadcrumb-component
 **/
ACX.add('breadcrumb-component', function (A) {
  /**
   * @constructor
   * @param {Node|Element|string|jQuery} container Widget container or its ID.
   * @extends BaseWidget
   */
  function Breadcrumb(container) {
    BaseWidget.apply(this, arguments);

    /**
     * Initialize {Breadcrumb}
     * @param {Object} config Configuration object.
     * @protected
     * @this {Breadcrumb}
     */
    this.initWidget = function (config) {
      elements_ = [];
      $container.text('');
      if (window.location.hash)
        data_['checkID'] = window.location.hash.replace('#', '');
      config_ = config;
      self_.WIDGET_NAME = 'BreadCrumb';
      self_.draw();
    };

    /**
     * Draw breadcrumb
     * @protected
     * @this {Breadcrumb}
     */
    this.draw = function () {
      $container.addClass('acxiom-breadcrumb-container');
      for (/** @type {number} */var i = 0; i < config_.length; i++) {
        drawElement(i);
      }
      self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.drawed', self_));

    };


    var elements_ = [];

    function drawElement(index, redraw) {
      var config = config_[index];
      /**@type {jQuery}*/
      var currentElement = elements_[index];
      if (index && !redraw) {
        /**@type {jQuery}*/
        var dividerSpan = ($('<span/>')).appendTo($container);
        if (config.flag) {
          dividerSpan.text('|');
        } else {
          dividerSpan.text('>');
        }
      }
      if (config.isactive) {
        if (config.clickable) {
          var ref = config.link || '#';
          ref += config.hash ? '#' + config.hash : '';
          currentElement = ($('<a/>', { id: 'detailLink', href: ref })).appendTo($container);
          data_['detailConfig'] = config;
          currentElement.off();
          currentElement.on('click', function () {
            self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.clicked', self_));
          });
        }
      }
      else if (config.link) {
        var ref = config.link;
        ref += config.hash ? '#' + config.hash : '';
        if (!currentElement) {
          currentElement = $('<a/>').appendTo($container);
          elements_.push(currentElement);
        }
        currentElement.attr('href', ref);

      }
      else {
        if (!currentElement) {
          currentElement = $('<span/>').appendTo($container);
          elements_.push(currentElement);
        }
      }
      currentElement.text(config.name);
      currentElement.attr('add-title', config.title);
      if (currentElement.text().length > 30) {
        tooltip(currentElement);
      }


    }

    /**
     * draw & update breadcrumb
     * @param {Object} elConfig
     * @param {number} number
     * @this {Breadcrumb}
     */
    this.setElement = function (elConfig, number) {
      if (config_[number]) {
        mergeConfig(config_[number], elConfig);
        drawElement(number, true);
      } else {
        config_.push(elConfig);
        drawElement(number);
      }
    };

    function mergeConfig(config, newConfig) {
      for (var prop in newConfig) {
        config[prop] = newConfig[prop];
      }
      return config;
    }

    /**
     * Gets component data
     * @this {Breadcrumb}
     * @protected
     * @return {Object}
     */
    this.getData = function () {
      return data_;
    };

    /**
     * The reference to container. Used in private methods.
     * @type {jQuery}
     * @private
     */
    var $container = $(this.getContainer());

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
    var data_ = {};

    /**
     * The reference to current class instance. Used in private methods.
     * @type {Breadcrumb}
     * @private
     */
    var self_ = this;
  }

  A.Breadcrumb = Breadcrumb;
});
