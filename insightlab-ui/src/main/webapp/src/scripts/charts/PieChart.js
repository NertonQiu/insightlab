
/**
 * @fileoverview Simple Pie chart implementation.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * PieChart constructor.
 * <br><b>Usage:</b><br>
 * <code>var chart = new PieChart(document.getElementById("div"));<br>
 * chart.draw([['Year', 'Sales', 'Expenses'],<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ['2004', 1000, 400],<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ['2005', 1170, 460],<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ['2006', 660, 1120],<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ['2007', 1030, 540]]);</code>
 * <br><b>Example:</b><br>
 * <div id="chart"></div>
 * <script src="../../bin/scripts/compiled.js"></script>
 * <script>
 * var chart = new PieChart(document.getElementById("chart"));
 * chart.draw([['Year', 'Sales', 'Expenses'],
 *             ['2004',  1000,    400],
 *             ['2005',  1170,    460],
 *             ['2006',  660,     1120],
 *             ['2007',  1030,    540]]);
 * </script>
 * @constructor
 * @extends {BaseChart}
 * @param {Node|Element} container The chart container.
 * @link https://developers.google.com/chart/interactive/docs/gallery/piechart
 */
function PieChart(container) {
  BaseChart.apply(this, arguments);

  /**
   * Defaults chat options.
   * @type {!Object.<string, Object>}
   * @see BaseChart#getOptions
   * @link https://developers.google.com/chart/interactive/docs/gallery/
   *       piechart#Configuration_Options
   */
  this.defaults = {
    'animation': {'duration': 500, 'easing': 'inAndOut'},
    'chartArea': {'width': '100%', 'height': '90%'},
    'legend': {'position': 'none'}
  };

  /**
   * Draws the pie chart based on google.visualization.PieChart.
   * @param {!Array} data A data array to use to draw the chart.
   * @param {Object.<string, Object>=} opt_options A map of name/value pairs.
   * @link https://developers.google.com/chart/interactive/docs/gallery/piechart
   */
  this.draw = function(data, opt_options) {
    (function() {
      if (self_.isLoaded('PieChart')) {
        self_.data = self_.convertData(data);
        self_.chart = new google.visualization.PieChart(container);
        setTimeout(function() {
          self_.chart.draw(self_.data, self_.getOptions(opt_options));
        }, 500);
      } else {
        setTimeout(arguments.callee, 250);
      }
    })();
  };

  /**
   * The reference to current class instance. Used in private methods.
   * @type {!PieChart}
   * @private
   */
  var self_ = this;
}

// Export for closure compiler.
window['PieChart'] = PieChart;

