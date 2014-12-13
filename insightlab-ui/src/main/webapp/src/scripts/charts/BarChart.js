
/**
 * @fileoverview Simple bar chart implementation.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * BarChart constructor.
 * <br><b>Usage:</b><br>
 * <code>var chart = new BarChart(document.getElementById("div"));<br>
 * chart.draw([['Year', 'Sales', 'Expenses'],<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ['2004', 1000, 400],<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ['2005', 1170, 460],<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ['2006', 660, 1120],<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ['2007', 1030, 540]]);</code>
 * <br><b>Example:</b><br>
 * <div id="chart"></div>
 * <script src="../../bin/scripts/compiled.js"></script>
 * <script>
 * var chart = new BarChart(document.getElementById("chart"));
 * chart.draw([['Year', 'Sales', 'Expenses'],
 *             ['2004',  1000,    400],
 *             ['2005',  1170,    460],
 *             ['2006',  660,     1120],
 *             ['2007',  1030,    540]]);
 * </script>
 * @constructor
 * @extends {BaseChart}
 * @param {Node|Element} container The chart container.
 * @see https://developers.google.com/chart/interactive/docs/gallery/barchart
 */
function BarChart(container) {
  BaseChart.apply(this, arguments);

  /**
   * Defaults chat options.
   * @type {!Object.<string, Object>}
   * @see BaseChart#getOptions
   * @link https://developers.google.com/chart/interactive/docs/gallery/barchart
   *      #Configuration_Options
   */
  this.defaults = {
    'animation': {'duration': 500, 'easing': 'inAndOut'},
    'hAxis': {'minValue': 0}
  };

  /**
   * Draws the bar chart based on google.visualization.BarChart.
   * @param {Array} data A data array to use to draw the chart.
   * @param {Object.<string, Object>=} opt_options A map of name/value pairs.
   * @see https://developers.google.com/chart/interactive/docs/gallery/barchart
   */
  this.draw = function(data, opt_options) {
    (function() {
      if (self_.isLoaded('BarChart')) {
        opt_options = self_.getOptions(opt_options);
        self_.data = self_.convertData(data);
        self_.chart = new google.visualization.BarChart(container);
        if (opt_options['animation'] && opt_options['animation']['duration']) {
          self_.chart.draw(self_.convertData(data, true), opt_options);
          setTimeout(function() {self_.chart.draw(self_.data, opt_options)},
                     opt_options['animation']['duration']);
        } else {
          self_.chart.draw(self_.data, opt_options);
        }
      } else {
        setTimeout(arguments.callee, 250);
      }
    })();
  };

  /**
   * @inheritDoc
   * @param {Array} data The data to convert.
   * @param {boolean=} opt_empty Creates empty data table.
   * @return {google.visualization.DataTable} Returns converted data.
   */
  this.convertData = function(data, opt_empty) {
    /** @type {Array|undefined} */
    var stub;
    if (opt_empty) {
      stub = [];
      for (/** @type {number} */ var i = 0; i < data.length; i++) {
        /** @type {Array} */
        var row = data[i];
        stub[i] = [];
        for (/** @type {number} */ var j = 0; j < row.length; j++) {
          stub[i][j] = typeof row[j] == 'number' ? 0 : row[j];
        }
      }
    }
    return google.visualization.arrayToDataTable(stub || data);
  };

  /**
   * The reference to current class instance. Used in private methods.
   * @type {!BarChart}
   * @private
   */
  var self_ = this;
}

// Export for closure compiler.
window['BarChart'] = BarChart;

