/**
 * @fileoverview
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */


/**
 * Base widgets container.
 *
 * @module basewidgets-container
 **/
ACX.add('basewidgets-container', function (A) {
  /**
   * @constructor
   * @extends BaseWidget
   * @param {Node|Element|string} container Widget container or its ID.
   */
  function BaseWidgetsContainer(container) {
    BaseWidget.apply(this, arguments);

    /**
     * Specifies widget name.
     * @type {string}
     */
    this.WIDGET_NAME = 'components-container';

    /**
     * This method is called whenever an event occurs of the type for which the
     * EventListener interface was registered.
     * @param {WidgetEvent} evt The WidgetEvent contains contextual information about the event.
     */
    this.handleEvent = function (evt) {
      if (arguments.callee.jqxhr_) {
        arguments.callee.jqxhr_.abort();
      }
      var data = {};
      if (evt && evt["widget"]) {
        data_[evt.type] = evt["widget"].getData();
        $.extend(data, evt["widget"].getData());
      }
      if (self_.JSON_URI) {
        if (arguments.callee.jqxhr_) arguments.callee.jqxhr_.abort();
        var params = getParams_(data);
        if (params) {
          arguments.callee.jqxhr_ = self_.loadData(self_.handleResult, params);
        }
      }
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
      if (dataFormatter_) {

        try {
          result = dataFormatter_(result);
        }
        catch (e) {
          result = { error: "Bad response structure" };
        }
      }
      for (var i = 0; i < config_.length; i++) {
        var widget = self_.createWidget(config_[i]);
        widgets_.push(widget);
        widget.handleResult(result);
      }
      data_['rows-data'] = result;
    };

    this.setConfig = function (config) {
      config_ = config;
    };

    /**
     * Sets function for formatting data
     */
    this.setDataFormatter = function (dataFormatter) {
      if (typeof(dataFormatter) == 'function')
        dataFormatter_ = dataFormatter;
    };

    /**
     * Sets function for formatting data
     */
    this.setGetParams = function (getParams) {
      if (typeof(getParams) == 'function')
        getParams_ = getParams;
    };

    /**
     * adds data from event
     */
    this.addData = function (evt) {
      if (evt && evt["widget"]) {
        data_[evt.type] = evt["widget"].getData();
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
    var data_ = { };

    var getParams_ = null;

    var dataFormatter_ = null;

    /**
     * @private
     */
    var widgets_ = [];
    var config_ = null;
  }

  A.BaseWidgetsContainer = BaseWidgetsContainer;
});
