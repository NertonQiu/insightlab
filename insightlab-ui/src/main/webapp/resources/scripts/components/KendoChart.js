;/**
 * Wrapper for kendo chart.
 *
 * @module kendo-chart-wrapper
 **/
ACX.add('kendo-chart-wrapper', function(A) {
  /**
   * @constructor.
   * @param {Node|Element|string} container Widget container or its ID.
   */
  function KendoChart(container) {

    /**
     * Draws the chart.
     * @param {Array} data A data array to use to draw the chart.
     * @param {Object} config Configuration object for current chart.
     * @this {KendoChart}
     */
    this.draw = function(data, config) {
      options_ = kendoMapper.getOptions(config['chart_name']);
      if (config['chart_area']) {
        options_.chartArea = config['chart_area'];
      }
      if (config['valueAxis']) {
        options_.valueAxis = config['valueAxis'];
      }
      if (!data || data.length == 0) {
        var $erorr = $('<div/>', { text: '没有记录返回.' }).addClass('validation_message');
        $erorr.appendTo($container);

      } else {
        options_.dataSource.data = data;
        $container.kendoChart(options_);
      }

    };

    /**
     * The reference to container.
     * @type {jQuery} Jquery object
     * @private
     */
    var $container = $(container);

    /**
     * @private
     * @type {Object}
     */
    var options_ = { };

    /**
     * Contains charts configurations
     * @type {KendoMapper}
     */
    var kendoMapper = new A.KendoMapper();

    /**
     * The reference to current class instance. Used in private methods.
     * @type {KendoChart}
     * @private
     */
    var self_ = this;
  }

  A.KendoChart = KendoChart;
}, '0.0.1', {requires: ['kendo-configs']});

