
/**
 * @fileoverview Simple Funnel chart implementation.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * FunnelChart constructor.
 * <br><b>Usage:</b><br>
 * <code>var chart = new FunnelChart(document.getElementById("div"));<br>
 * chart.draw({'views': 1000, 'users': 500, 'clicks': 250},<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;{'views': 'Views', 'users': 'Users',
 *                                           'clicks': 'Clicks'},<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;{'legend': {'position': 'right',
 *                                           'alignment': 'start',<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 'textStyle': {'fontSize': 13}},
 *                                           'height': 254,<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 'chartArea': {<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 'height': 240,
 *                                                  'width': '70%',
 *                                                  'top': 15,
 *                                                  'left': '-20%'}});
 * </code>
 * <br><b>Example:</b><br>
 * <div id="chart"></div>
 * <script src="../../bin/scripts/compiled.js"></script>
 * <script>
 * var chart = new FunnelChart(document.getElementById("chart"));
 * chart.draw({'views': 1000, 'users': 500, 'clicks': 250},
 *            {'views': 'Views', 'users': 'Users', 'clicks': 'Clicks'},
 *            {'legend': {'position': 'right', 'alignment': 'start',
 *                        'textStyle': {'fontSize': 13}},
 *             'height': 254,
 *             'chartArea': {'height': 240, 'width': '70%', 'top': 15,
 *                           'left': '-20%'}});
 * </script>
 * @constructor
 * @extends {BaseChart}
 * @param {Node|Element} container The chart container.
 * @link http://developers.google.com/chart/interactive/docs/gallery/bubblechart
 */
function FunnelChart(container) {
  BaseChart.apply(this, arguments);

  /**
   * Defaults chat options.
   * @type {!Object.<string, Object>}
   * @see BaseChart#getOptions
   * @link https://developers.google.com/chart/interactive/docs/gallery/
   *       bubblechart#Configuration_Options
   */
  this.defaults = {
    'animation': {'duration': 500, 'easing': 'inAndOut'},
    'legend': {'position': 'in'},
    'chartArea': {'left': '-30%', 'width': '70%', 'height': '100%'},
    'hAxis': {
      'baseline': 0, 'maxValue': 200, 'gridlines' : {'count': 0},
      'baselineColor': 'white'
    },
    'vAxis': {
      'baseline': 0, 'maxValue': 200, 'gridlines' : {'count': 0},
      'viewWindowMode': 'explicit', 'viewWindow': {'min': 0}
      //,'logScale': false
    },
    'sizeAxis': {'minSize': 0, 'minValue': 0}
  };

  /**
   * Draws the funnel chart based on google.visualization.BubbleChart.
   * @param {!Object.<string, number>} data A data array used to draw the chart.
   * @param {!Object.<string, string>} mapping A map of key/value pairs of
   *   bubbles options.
   * @param {Object.<string, Object>=} opt_options A map of name/value pairs of
   *   custom options.
   * @link https://developers.google.com/chart/interactive/docs/gallery/table
   */
  this.draw = function(data, mapping, opt_options) {
    (function() {
      if (self_.isLoaded('BubbleChart')) {
        /** @type {!Object.<string, string>} */
        var options = self_.getOptions(opt_options);
        /** @type {number} */ var maxValue = 0;
        /** @type {number} */ var viewPortHeight = +options['height'];
        /** @type {number} */ var maxSize = viewPortHeight * 0.4;

        for (/** @type {string} */ var key in mapping) {
          maxValue = Math.max(maxValue, data[key] || 0);
        }
        options['vAxis']['viewWindow']['max'] =
            Math.sqrt(maxValue / Math.PI) * viewPortHeight / maxSize;
        options['sizeAxis']['maxSize'] = maxSize;

        self_.data = self_.convertData(data, mapping);
        self_.chart = self_.chart ||
                      new google.visualization.BubbleChart(container);
        if (options['animation'] && options['animation']['duration']) {
          self_.chart.draw(self_.convertData(data, mapping, true), options);
          setTimeout(function() {self_.chart.draw(self_.data, options)},
                     options['animation']['duration']);
        } else {
          self_.chart.draw(self_.data, options);
        }
      } else {
        setTimeout(arguments.callee, 250);
      }
    })();
  };

  /**
   * @inheritDoc
   * @param {!Object.<string, number>} data The data to convert.
   * @param {!Object.<string, string>} mapping The data mapping.
   * @param {boolean=} opt_empty Creates empty data table.
   * @return {google.visualization.DataTable} Returns converted data.
   */
  this.convertData = function(data, mapping, opt_empty) {
    /** @type {!google.visualization.DataTable} */
    var table = new google.visualization.DataTable();
    /** @type {!google.visualization.NumberFormat} */
    var formatter = new google.visualization.NumberFormat({'pattern': '#,###'});
    table.addColumn('string', 'ID');
    table.addColumn('number', '');
    table.addColumn('number', '');
    table.addColumn('string', 'Label');
    table.addColumn('number', 'Value');
    for (/** @type {string} */ var key in mapping) {
      /** @type {number} */ var value = opt_empty ? 0 : data[key];
      table.addRow(['', {'v': 100, 'f': ''},
            {'v': opt_empty ? 0 : Math.sqrt(value / Math.PI), 'f': ''},
            mapping[key],
            {'v': value, 'f': formatter.formatValue('' + value)}
          ]);
    }
    return table;
  };

  /**
   * The reference to current class instance. Used in private methods.
   * @type {!FunnelChart}
   * @private
   */
  var self_ = this;
}

// Export for closure compiler.
window['FunnelChart'] = FunnelChart;

