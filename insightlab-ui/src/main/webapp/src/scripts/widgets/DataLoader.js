


/**
 * @constructor
 */
function DataLoader() {
  /**
   * @type {boolean|number}
   */
    this['DEBUG_MODE'] = location.href.indexOf('debug') + 1 ||
                          location.protocol == 'file:' || location.href.indexOf('localhost') + 1;

  /**
   * Loads JSON-encoded data from the server using a GET HTTP request.
   * @param {string} url The URL to which to send the request.
   * @param {!function(Object.<string, Object, Array.<Object>>)} callback The callback function.
   * @param {string=} opt_params Optional URL parameters.
   * @return {XMLHttpRequest} Returns reference to HttpRequest.
   */
  this.loadData = function(url, callback, opt_params) {
    /** @type {XMLHttpRequest} */ var req = null;
    if (url) {
      if (opt_params && url.indexOf('?') + 1) {
        opt_params = opt_params.replace(/^\?/, '&');
      }
      url = servlet_.getContextPath() + url + (opt_params || '');
      checkLoading_(request_.getCount() || 1);
      if (self_['DEBUG_MODE']) {
        loadStubbyData_(url, callback, opt_params);
      } else {
        req = request_.doGet(url, function(request) {
          loadComplete_(/** @type {!XMLHttpRequest} */(request), callback);
        });
      }
    } else {
      callback(null);
    }
    return req;
  };

  // Export for closure compiler.
  this['loadData'] = this.loadData;

  /**
   * Posts data to the server using a POST HTTP request.
   * @param {string} url The end point url.
   * @param {string} data Data to post.
   * @param {!function(Object.<string, Object>)} callback The callback function.
   * @return {XMLHttpRequest} Returns reference to HttpRequest.
   */
  this.postData = function(url, data, callback) {
    /** @type {XMLHttpRequest} */ var req = null;
    if (url) {
      url = servlet_.getContextPath() + url;
      checkLoading_(request_.getCount() || 1);
      if (self_['DEBUG_MODE']) {
        loadStubbyData_(url, callback, data);
      } else {
        req = request_.doPost(url, function(request) {
          loadComplete_(/** @type {!XMLHttpRequest} */(request), callback);
        }, data);
      }
    } else {
      callback(null);
    }
    return req;
  };

  // Export for closure compiler.
  this['postData'] = this.postData;

  /**
   * @param {!XMLHttpRequest} request Reference to XMLHttpRequest instance.
   * @param {!function(Object.<string, Object>)} callback The callback function.
   * @private
   */
  function loadComplete_(request, callback) {
    /** @type {!Object.<string, Object>} */ var result = parseResult_(request);
    if (checkResponse_(result)) callback(result);
    checkLoading_(request_.getCount());
  }

  /**
   * Loads JSON-encoded data from stubby.js file.
   * @param {string} url The URL to which to send the request.
   * @param {!function(Object.<string, Object>)} callback The callback function.
   * @param {string=} opt_params Optional URL parameters.
   * @private
   */
  function loadStubbyData_(url, callback, opt_params) {
    /** @type {Object} */ var result;
    /** @type {string} */
    var stubbyKey = url.split('/').pop().split('?')[0];
    if (window['JSON_STUBBY'] && window['JSON_STUBBY'][stubbyKey]) {
      result = window['JSON_STUBBY'][stubbyKey];
      setTimeout(function() {
        if (typeof result == 'object') result = JSON.stringify(result);
        loadComplete_(/** @type {!XMLHttpRequest} */({'responseText': result}),
                      callback);
      }, 500);
    } else {
      callback(null);
    }
    window.console && console.log([stubbyKey, url, opt_params, typeof result]);
  }

  /**
   * @param {!XMLHttpRequest} request XMLHttpRequest object to parse.
   * @return {!Object.<string, Object>} Returns parsed response as JSON object.
   * @private
   */
  function parseResult_(request) {
    /** @type {!Object.<string, Object>} */ var result = {};
    if (request.status == 404 || request.status == 500) {
      result['errors'] = {'message': request.statusText};
    } else {
      try {
          result = JSON.parse(encodeTag(request.responseText));
      } catch (ex) {
        result['errors'] = {'message': ex['message'] || ex};
      }
    }
    return result;
  }

  /**
   * @param {!Object.<string, Object>} response The response to check.
   * @return {boolean} Returns <code>true</code> if no errors in response.
   * @private
   */
  function checkResponse_(response) {
    /** @type {boolean} */ var result = true;
    if (response['status'] == 401) {
      if (response['redirect']) {
        result = false;
        var pageURL = location.pathname.replace(CONTEXT_PATH, '/');
        pageURL += location.hash;
        pageURL = encodeURIComponent(pageURL);
        var redirectURL = response['redirect'].replace(/ReturnUrl=(.*)/, 'ReturnUrl=' + pageURL);
        location.replace(/** @type {string} */(redirectURL));
      }
    }
    return result;
  }

  /**
   * Shows progress loading indicator.
   * @param {number} count Count.
   * @private
   */
  function checkLoading_(count) {
    /** @type {string} */ var id = 'progress-loader';
    /** @type {Node|Element} */ var loader = document.getElementById(id) ||
        document.body.appendChild(document.createElement('DIV'));
    loader.id = id;
    loader.style.display = count ? 'block' : 'none';
  }

  /**
   * @type {!HttpRequest}
   * @see DataLoader#loadData
   * @private
   */
  var request_ = new HttpRequest;

  /**
   * @type {!HttpServletRequest}
   * @see DataLoader#loadData
   * @private
   */
  var servlet_ = new HttpServletRequest;

  /**
   * The reference to current class instance. Used in private methods.
   * @type {!DataLoader}
   * @private
   */
  var self_ = this;

}

// Export for closure compiler.
window['DataLoader'] = DataLoader;

