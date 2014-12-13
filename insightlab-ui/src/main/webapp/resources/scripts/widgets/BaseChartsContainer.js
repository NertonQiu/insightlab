/**
 * @fileoverview
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */


/**
 * Base charts container.
 *
 * @module basecharts-container
 **/
ACX.add('basecharts-container', function (A) {
  /**
   * @constructor
   * @extends BaseWidget
   * @param {Node|Element|string} container Widget container or its ID.
   */
  function BaseChartsContainer(container) {
    BaseWidget.apply(this, arguments);

    /**
     * Specifies widget name.
     * @type {string}
     */
    this.WIDGET_NAME = 'charts-container';

    /**
     * This method is called whenever an event occurs of the type for which the
     * EventListener interface was registered.
     * @param {WidgetEvent} evt The WidgetEvent contains contextual information about the event.
     */
    this.handleEvent = function (evt, opt_params) {
      if (arguments.callee.jqxhr_) {
        arguments.callee.jqxhr_.abort();
      }
      data_[evt.type] = evt["widget"].getData();
      if (self_.JSON_URI)
        arguments.callee.jqxhr_ = self_.loadData(self_.handleResult, opt_params);
    };

    /**
     * Gets settings data.
     * Accessible by 'evt.widget.getData()' in 'handleEvent(evt)' method.
     * @return {Object} Returns settings data as dictionary representation.
     */
    this.getData = function () {
      for (var i = 0; i < widgets_.length; i++) {
        if (widgets_[i] && widgets_[i].getData) {
          data_[widgets_[i].WIDGET_NAME] = widgets_[i].getData();
        }
      }
      return data_;
    };

    /**
     * @protected
     */
    this.initWidget = function () {
    };

    /**
     * @protected
     */
    this.createWidget = function (config) {
    };

    /**
     * @protected
     */
    this.handleResult = function (result) {
      if (result) {
        for (var i = 0; i < result.length; i++) {
          widgets_.push(self_.createWidget(result[i]));
        }
      }
    };

    /**
     * The reference to current class instance. Used in private methods.
     * @type {BaseWidget}
     * @private
     */
    var self_ = this;

    /**
     * Storage for settings data.
     * @type {Object}
     * @private
     */
    var data_ = {};

    /**
     * @private
     */
    var widgets_ = [];
  }
});
