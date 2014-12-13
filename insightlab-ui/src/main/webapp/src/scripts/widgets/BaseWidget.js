
/**
 * @fileoverview Event-driven widgets implementation is based on W3C DOM
 * Level 3 Events Specification.  All widgets must inherit BaseWidget
 * implementation and comply all sections of this specification.  For
 * inter-widget communication widgets should define events with prefix in format
 * "widget.event" and bind events to top-level DOM <HTML> element.
 *
 * @link https://docs.google.com/a/sethq.com/document/d/1lEG
 *       QWV8BHWUgWk5CV1QJkxEGirBYUWqcken-8N0_724/edit
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 *
 */



/**
 * The BaseWidget class implements W3C EventTarget and EventListener interfaces.
 * BaseWidget is the foundation of all widgets.
 * All widgets need to be derived from this BaseWidget class.
 *
 * @constructor
 * @extends {EventTargetListener}
 * @param {Node|Element|string} container Widget container or its ID.
 */
function BaseWidget(container) {
  EventTargetListener.apply(this, arguments);

  // TODO: WIDGET_NAME and JSON_URI should not be constant-like style.
  // TODO: Rename WIDGET_NAME to widgetName.
  // TODO: Rename JSON_URI to jsonUri.

  /**
   * Specifies widget name.
   * @type {string}
   * @protected
   */
  this['WIDGET_NAME'] = '';

  /**
   * Specifies widget json URL.
   * @type {string}
   * @see BaseWidget#loadData
   * @protected
   */
  this['JSON_URI'] = '';

  /**
   * The application name, used as referer parameter for LUA API calls.
   * @type {string}
   */
  this['APP_NAME'] = 'acxm-ilab';

  /**
   * @type {boolean|number}
   */
    this['DEBUG_MODE'] =  location.href.indexOf('debug') + 1 ||
                          location.protocol == 'file:';

  /**
   * Specifies API context path.
   * @type {string}
   * @see BaseWidget#addContextPath_
   * @protected
   */
  this['API_CONTEXT_PATH'] = '/srv';

  /**
   * @return {Node|Element|undefined} Returns reference to widget container.
   */
  this.getContainer = function () {
    return container_;
  };

  // Export for closure compiler.
  this['getContainer'] = this.getContainer;

  /**
   * Returns the value of a request parameter as a String, or null if the
   * parameter does not exist.
   * @param {string} name - a <code>String</code> specifying the name of the
   *     parameter.
   * @return {string} a <code>String</code> representing the single value of the
   *     parameter.
   */
  this.getParameter = function(name) {
    return servlet_.getParameter(name);
  };

  // Export for closure compiler.
  this['getParameter'] = this.getParameter;

  /**
   * Loads JSON-encoded data from the server using a GET HTTP request.
   * @param {!function(Object.<string, Object>)} callback The callback function.
   * @param {string=} opt_params Optional URL parameters.
   * @return {XMLHttpRequest} Returns reference to HttpRequest.
   */
  this.loadData = function(callback, opt_params) {
    /** @type {XMLHttpRequest} */ var req = null;
    if (self_['JSON_URI']) {
      /** @type {string} */
      var url = addRequiredParams_(addContextPath_(self_['JSON_URI']));
      req = loader_.loadData(url, callback, opt_params);
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
      url = addRequiredParams_(addContextPath_(url));
      req = loader_.postData(url, data, callback);
    } else {
      callback(null);
    }
    return req;
  };

  // Export for closure compiler.
  this['postData'] = this.postData;

  /**
   * @param {!Object.<string, Object>|Object} data Data.
   * @return {string} Returns extracted error message from <code>data</code>.
   */
  this.getErrorMessage = function(data) {
    /** @type {string} */ var error = '';
    /** @type {string} */ var message = 'Some error occurred.';
      if (!data || (data['rows'] && !data['rows'].length) || (data instanceof Array && data.length==0)) {
      error = 'No data available.';
    } else if (data['errors']) {
      if (data['errors'] instanceof Array) {
        for (/** @type {number} */ var i = 0; i < data['errors'].length; i++) {
          if (data['errors'][i]['message']) {
            error += data['errors'][i]['message'] + '<br>';
          }
        }
      } else {
        error = data['errors']['message'] || message;
      }
    } else if (data['error']) {
      if (typeof data['error'] == 'string')
        error = /** @type {string} */ (data['error']);
      else
        error = data['error']['message'] || message;
    } else if (data['status'] == 500 && data['message']) {
      error = /** @type {string} */ (data['message']);
    }
    return error;
  };

  // Export for closure compiler.
  this['getErrorMessage'] = this.getErrorMessage;

  /**
   * Adds a contenxt path to the URI depending on current environment.
   * @param {string} uri The URI.
   * @return {string} Returns updated URI.
   * @private
   */
  function addContextPath_(uri) {
    if (uri.indexOf('/') == 0 && uri.indexOf('/bknd') != 0)
      uri = self_['API_CONTEXT_PATH'] + uri;

    return uri;
  }

  /**
   * Adds a required parameters to the URI such as "referer".
   * @param {string} uri The URI.
   * @return {string} Returns updated URI.
   * @private
   */
  function addRequiredParams_(uri) {
    return uri + (uri.indexOf('?') + 1 ? '&' : '?') +
           'referer=' + self_['APP_NAME'];
  }

  /**
   * The widget container.
   * @type {Node|Element|undefined}
   * @see BaseWidget#getContainer
   * @private
   */
  var container_ = typeof container == 'string' ?
      document.getElementById(container) : container;

  /**
   * @return {number} Returns current model  id. 
   */
  this.getModelId = function () {
    return window.location.hash.match(/#+(\w+)/)[1];
  }

  /**
   * The reference to current class instance. Used in private methods.
   * @type {BaseWidget}
   * @private
   */
  var self_ = this;

  /**
   * @type {DataLoader}
   * @see BaseWidget#loadData
   * @private
   */
  var loader_ = new DataLoader;

  /**
   * @type {HttpServletRequest}
   * @see BaseWidget#getParameter
   * @private
   */
  var servlet_ = new HttpServletRequest;
}

// Export for closure compiler.
window['BaseWidget'] = BaseWidget;

