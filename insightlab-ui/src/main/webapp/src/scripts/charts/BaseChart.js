
/**
 * @fileoverview A base class of all Google Visualization Chart Wrappers.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * A base class of all Google Visualization Chart Wrappers.<br>
 * Exported for Google closure compiler.
 * @constructor
 */
function BaseChart() {

  /**
   * Google API URL.
   * @type {!string}
   * @const
   * @see <a href="https://developers.google.com/loader/#intro">Google API</a>
   */
  var JSAPI_URL = 'https://www.google.com/jsapi';
  var JSAPI_VIS_URL = 'https://www.google.com/uds/api/visualization/';

  /**
   * List of Google visualization modules which will be added to autoload.
   * @type {Object}
   * @see <a href="https://developers.google.com/loader/#AutoLoading">
   *     AutoLoading</a>
   * @see <a href="https://developers.google.com/loader/#visualization">
   *     Google Visualization API</a>
   */
  var JSAPI_MODULES = {
    'modules': [
      {'name': 'visualization', 'version': '1.0',
        'packages': ['geochart', 'corechart', 'table']}
    ]
  };

  /**
   * Defaults chat options.
   * @type {!Object.<string, Object>}
   * @see BaseChart#getOptions
   */
  this.defaults = {};

  /**
   * Gets chart's options.
   * @param {Object.<string, Object>=} opt_options Options map.
   * @return {!Object.<string, Object>} A map of name/value pairs.
   * @this {BaseChart}
   */
  this.getOptions = function(opt_options) {
    return mergeOptions_(this.defaults, opt_options || {});
  };

  /**
   * Checks if the specified <code>chartType</code> is already loaded.
   * @param {string} chartType The chart type.
   * @return {boolean} Returns <code>true</code> if <code>chartType</code> is
   *     already loaded, <code>false</code> otherwise.
   */
  this.isLoaded = function(chartType) {
    return !!(window['google'] && window['google']['visualization'] &&
              window['google']['visualization'][chartType]);
  };

  // Export for closure compiler.
  this['isLoaded'] = this.isLoaded;

  /**
   * Converts JSON data to google.visualization.DataTable.
   * @param {!Array} data The data to convert.
   * @return {google.visualization.DataTable} Returns converted data.
   * @see <a href="
   *   https://developers.google.com/chart/interactive/docs/reference#DataTable"
   *   >google.visualization.DataTable</a>
   * @protected
   */
  this.convertData = function(data) {
    return google.visualization.arrayToDataTable(data);
  };

  // Export for closure compiler.
  this['convertData'] = this.convertData;

  /**
   * Merges charts options with defaults options.
   * @param {!Object.<string, Object>} defaults Options map.
   * @param {!Object.<string, Object>} options Options map.
   * @return {!Object.<string, Object>} A map of key/value pairs of options.
   * @private
   */
  function mergeOptions_(defaults, options) {
    for (/** @type {string} */ var key in options) {
      if (options[key] instanceof Array) {
        defaults[key] = [].concat(options[key]);
      } else if (typeof options[key] == 'object') {
        defaults[key] = mergeOptions_(
            /** @type {!Object.<string, Object>} */(defaults[key] || {}),
            /** @type {!Object.<string, Object>} */(options[key]));
      } else {
        defaults[key] = options[key];
      }
    }
    return /** @type {!Object.<string, Object>} */(defaults);
  };

  /**
   * Gets current chart's data as <code>google.visualization.DataTable</code>.
   * @return {google.visualization.DataTable|google.visualization.DataView}
   *         Returns chart data.
   * @see <a href="
   *   https://developers.google.com/chart/interactive/docs/reference#DataTable"
   *   >google.visualization.DataTable</a>
   * @this {BaseChart}
   */
  this.getData = function() {
    return this.data;
  };

  // Export for closure compiler.
  this['getData'] = this.getData;

  /**
   * Gets reference to current Google visualization chart instance.
   * @return {Object} Returns reference to Google visualization chart instance.
   * @this {BaseChart}
   */
  this.getChart = function() {
    return this.chart;
  };

  // Export for closure compiler.
  this['getChart'] = this.getChart;

  /**
   * Reference to current Google visualization chart instance.
   * @type {Object}
   * @see BaseChart#getChart
   * @protected
   */
  this.chart = null;

  /**
   * Storage for current chart's data.
   * @type {google.visualization.DataTable|google.visualization.DataView}
   * @see BaseChart#getData
   * @protected
   */
  this.data = null;

  /**
   * Initialize Google visualization API.
   * @private
   */
  function init_() {
    /** @type {Object|undefined} */
    var goog = window['google'];

    /** @type {Object|undefined} */
    var gviz = goog && goog['visualization'];
    if (!gviz) {
      /** @type {string} */
      var src = JSAPI_URL + '?autoload=' + encodeURIComponent(JSON.stringify(JSAPI_MODULES));

        (function load(src, listen) {
            /** @type {Node|Element} */
            var head = document.getElementsByTagName('HEAD')[0];

            /** @type {Node|Element} */
            var script = document.createElement('SCRIPT');
            script.type = 'text/javascript';
            script.src = src;

            if(listen) {
                script.onload = function() {
                    load(JSAPI_VIS_URL + google.visualization.Version + '/' + google.visualization.JSHash + '/format+en,default,' + JSAPI_MODULES.modules[0].packages.toString() + '.I.js', false);
                }
            }

            head.appendChild(script);
        })(src, true);
    }
  }

  init_();
}

// Initialize first time Google API.
new BaseChart;


// Export for closure compiler.
window['BaseChart'] = BaseChart;

