
/**
 * @fileoverview Simple implementation of XMLHttpRequest.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * The constructor initiates a XMLHttpRequest.
 * @constructor
 */
function HttpRequest() {

  /**
   * Performs GET request.
   * @param {string} url The URL to which to send the request.
   * @param {function(XMLHttpRequest)} callback A JavaScript function object
   *     that is called whenever the <code>readyState</code> attribute equals
   *     to 4 (DONE) and <code>status</code> equals to 200.
   * @return {!XMLHttpRequest} Returns instance of XMLHttpRequest.
   * @this {HttpRequest}
   */
  this.doGet = function(url, callback) {
    // @link http://tools.ietf.org/html/rfc2616#section-3.2.1
    if (url.length > 2550) {
      /** @type {Array} */
      var parts = url.split('?');
      return this.doPost(parts[0], callback, parts[1]);
    }
    return doRequest_('GET', url, callback);
  };

  // Export for closure compiler.
  this['doGet'] = this.doGet;

  /**
   * Performs POST request.
   * @param {string} url The URL to which to send the request.
   * @param {function(XMLHttpRequest)} callback A JavaScript function object
   *     that is called whenever the <code>readyState</code> attribute equals
   *     to 4 (DONE) and <code>status</code> equals to 200.
   * @param {string} data Provides the request entity body.
   * @return {!XMLHttpRequest} Returns instance of XMLHttpRequest.
   */
  this.doPost = function(url, callback, data) {
    return doRequest_('POST', url, callback, data);
  };

  // Export for closure compiler.
  this['doPost'] = this.doPost;

  /**
   * Performs HEAD request.
   * @param {string} url The URL to which to send the request.
   * @param {function(XMLHttpRequest)} callback A JavaScript function object
   *     that is called whenever the <code>readyState</code> attribute equals
   *     to 4 (DONE) and <code>status</code> equals to 200.
   * @return {!XMLHttpRequest} Returns instance of XMLHttpRequest.
   */
  this.doHead = function(url, callback) {
    return doRequest_('HEAD', url, callback);
  };

  // Export for closure compiler.
  this['doHead'] = this.doHead;

  /**
   * Gets count of active requests.
   * @return {!number} Returns count of active requests.
   */
  this.getCount = function() {
    return HttpRequest.count_ || 0;
  };

  // Export for closure compiler.
  this['getCount'] = this.getCount;

  /**
   * Performs HTTP request.
   * @param {string} method The HTTP method to use, such as "GET", "POST",
   *     "PUT", "DELETE", etc. Ignored for non-HTTP(S) URLs.
   * @param {string} url The URL to which to send the request.
   * @param {function(XMLHttpRequest)} callback A JavaScript function object
   *     that is called whenever the <code>readyState</code> attribute equals
   *     to 4 (DONE) and <code>status</code> equals to 200.
   * @param {string|Object=} opt_data Provides the request entity body.
   * @return {!XMLHttpRequest} Returns instance of XMLHttpRequest.
   * @private
   */
  function doRequest_(method, url, callback, opt_data) {
    url = url.replace(/\/+/g, '/');
    url += (url.indexOf('?') + 1 ? '&' : '?') + 'nocache=' + (+new Date);
    HttpRequest.count_++;

    /** @type {XMLHttpRequest} */
    var req = window.XMLHttpRequest ? new XMLHttpRequest() :
                                      new ActiveXObject('Microsoft.XMLHTTP');
    req.onreadystatechange = function() {
      if (req.readyState == 4 /*&& req.status == 200*/) {
        HttpRequest.count_--;
        callback(req);
      }
    };
    req.open(method, url, true);
    req.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    if (method == 'POST' && opt_data && typeof opt_data != 'object') {
      req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    }
    if (opt_data && typeof opt_data == 'object') {
      req.setRequestHeader('Content-Type', 'application/json');
      opt_data = JSON.stringify(opt_data);
  }
    req.send(opt_data || null);
    return req;
  }

  /**
   * The count of active requests.
   * @type {number}
   * @see HttpRequest#getCount
   * @private
   */
  HttpRequest.count_ = HttpRequest.count_ || 0;
}

// Export for closure compiler.
window['HttpRequest'] = HttpRequest;

