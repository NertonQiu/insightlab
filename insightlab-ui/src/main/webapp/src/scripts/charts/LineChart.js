
/**
 * @fileoverview Simple Line chart implementation.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * LineChart constructor.
 * <br><b>Usage:</b><br>
 * <code>var chart = new LineChart(document.getElementById("div"));<br>
 * chart.draw([['Year', 'Sales', 'Expenses'],<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ['2004', 1000, 400],<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ['2005', 1170, 460],<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ['2006', 660, 1120],<br>
 * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ['2007', 1030, 540]]);</code>
 * <br><b>Example:</b><br>
 * <div id="chart"></div>
 * <script src="../../bin/scripts/compiled.js"></script>
 * <script>
 * var chart = new LineChart(document.getElementById("chart"));
 * chart.draw([['Year', 'Sales', 'Expenses'],
 *             ['2004',  1000,    400],
 *             ['2005',  1170,    460],
 *             ['2006',  660,     1120],
 *             ['2007',  1030,    540]]);
 * </script>
 * @constructor
 * @extends {BaseChart}
 * @param {Node|Element} container The chart container.
 * @link https://developers.google.com/chart/interactive/docs/gallery/linechart
 */
function LineChart(container) {
  BaseChart.apply(this, arguments);

  /**
   * Defaults chat options.
   * @type {!Object.<string, Object>}
   * @see BaseChart#getOptions
   * @link https://developers.google.com/chart/interactive/docs/gallery/
   *       linechart#Configuration_Options
   */
  this.defaults = {
    'animation': {'duration': 500, 'easing': 'inAndOut'},
    'chartArea': {'width': '170', 'height': '130'},
    'legend': {'position': 'none'},
    'fontSize': 13,
    'titleTextStyle': {'fontSize': 13},
    'tooltip': {'textStyle': {'fontSize': 13}}
  };

  /**
   * Draws the funnel chart based on google.visualization.LineChart.
   * @param {!Array} data A data array to use to draw the chart.
   * @param {Object.<string, Object>=} opt_options A map of name/value pairs.
   * @link http://developers.google.com/chart/interactive/docs/gallery/linechart
   */
  this.draw = function (data, opt_options) {
      (function () {
          if (self_.isLoaded('LineChart')) {
              opt_options = self_.getOptions(opt_options);
              self_.data = data; //self_.convertData(data);

              /** @type {!Array} */
              var stub = [].concat(data);
              for (/** @type {number} */var i = 0; i < stub.length; i++)
                  for (/** @type {number} */var j = 1; j < stub[i].length; j++)
                      stub[i][j] = 0;

              self_.chart = self_.chart ||
                        new google.visualization.LineChart(container);
              if (opt_options['animation'] && opt_options['animation']['duration']) {
                  self_.chart.draw(self_.convertData(stub), opt_options);
                  setTimeout(function () { self_.chart.draw(self_.data, opt_options) },
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
   * The reference to current class instance. Used in private methods.
   * @type {!LineChart}
   * @private
   */
  var self_ = this;
}

// Export for closure compiler.
window['LineChart'] = LineChart;

