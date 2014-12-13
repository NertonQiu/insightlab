
/**
 * @fileoverview Simple implementation of javax.servlet.ServletRequest.
 * @see http://docs.oracle.com/javaee/5/api/javax/servlet/ServletRequest.html
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * Simple implementation of javax.servlet.ServletRequest.
 * @constructor
 * @see http://docs.oracle.com/javaee/5/api/javax/servlet/ServletRequest.html
 */
function ServletRequest() {

  /**
   * Returns the value of a request parameter as a String, or null if the
   * parameter does not exist.
   * @param {string} name - a <code>String</code> specifying the name of the
   *     parameter.
   * @return {string} a <code>String</code> representing the single value of the
   *     parameter.
   * @this {ServletRequest}
   */
  this.getParameter = function(name) {
    /** @type {Object.<string, string>} */ var map = this.getParameterMap();
    return map[/** @type {string} */ (name)] || '';
  };

  this['getParameter'] = this.getParameter;

  /**
   * Returns a map of the parameters of this request.
   * @return {Object.<string, string>} Map containing parameter names as keys
   *     and parameter values as map values.
   */
  this.getParameterMap = function() {
    /** @type {Object.<string, string>} */ var map = {};
    /** @type {Array.<string>} */ var pair;
    /** @type {Array.<string>} */
    var pairs = location.search.substr(1).split('&');
    for (/** @type {number} */ var i = 0; i < pairs.length;) {
      pair = pairs[i++].split('=');
      map[pair[0]] = (decodeURIComponent || unescape)(pair[1]);
    }
    return map;
  };

  this['getParameterMap'] = this.getParameterMap;
}

window['ServletRequest'] = ServletRequest;
