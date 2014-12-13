/**
 * @fileoverview Template.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 * @link https://developers.google.com/closure/compiler/docs/js-for-compiler
 */



/**
 * @constructor
 * @param {?string=} opt_param template url, template string or template id.
 * Template string should begin with 'data:'.
 * Url to template should begin with 'url:'.
 */
function Template(opt_param) {

  /**
   * Renders parsed template into specified container.
   * @param {Array|Object} data The list of key-value pairs.
   * @param {Node} container The HTML container.
   * @param {function(...)=} opt_onRender Callback function.
   */
  this.renderTo = function(data, container, opt_onRender) {
    if (!(data instanceof Array)) data = [data];

    /** @type {string} */ var result = '';
    for (/** @type {number} */ var i = 0; i < data.length; i++) {
      /** @type {string} */ var template = '\n' + template_;
      for (/** @type {string} */ var key in data[i]) {
        /** @type {!RegExp} */
        var pattern = new RegExp('{{\\s*' + key + '\\s*}}', 'img');
        template = template.replace(pattern, data[i][key]);
      }
      result += template;
    }

    /** @type {string} */ var tagName = container.tagName;
    if (DomUtils.IE && (tagName == 'THEAD' || tagName == 'TBODY')) {
      /** @type {Node} */
      var div = container.ownerDocument.createElement('DIV');
      div.innerHTML = '<table><' + tagName + '>' + result +
                      '</' + tagName + '></table>';
      container.parentNode.replaceChild(
          DomUtils.getElementsByTagName(div, tagName)[0], container);
    } else {
      container.innerHTML += result;
    }
    opt_onRender && opt_onRender();
  };

  /**
   * Load template from given URL.
   *
   * @param {string} url URL to template.
   */
  this.load = function(url) {
    loadTemplate_(url);
  };

  /**
   * Rewrite default settings.
   *
   * @param {Object} newConfig New config.
   */
  this.configure = function(newConfig) {
    Utils.merge(config_, newConfig);
    if ('openTag' in newConfig || 'closeTag' in newConfig) {
      searchTagsRe_ = createSearchRe_();
    }
  };

  /**
   * Assing data to this template.
   *
   * @param {string|Object} key A key to assign or object
   * containing key:value pairs.
   * @param {string=} opt_value Optional parameter if key is given.
   */
  this.assign = function(key, opt_value) {
    if (Utils.isArray(parameters_)) {
      parameters_ = {};
    }
    if (Utils.isObject(key)) {
      Utils.merge(parameters_, /** @type {Object} */ (key));
    } else {
      parameters_[key] = opt_value;
    }
  };

  /**
   * Assign collection of elements to this template.
   *
   * @param {Array} iterable Array with values.
   */
  this.assignAll = function(iterable) {
    if (Utils.isArray(iterable)) {
      parameters_ = /** @type {Array} */ (iterable);
    }
  };

  /**
   * Asynchronous render template.
   * If template is not loaded yet - wait until load and
   * render.
   *
   * @param {Element} parentElement string with id of element or element.
   * @param {function(...)=} opt_onRender callback function.
   */
  this.render = function(parentElement, opt_onRender) {
    if (template_) {
      render_(parentElement, opt_onRender);
    } else {
      callbacks_.push(function() {
        render_(parentElement, opt_onRender);
      });
    }
  };

  /**
   * Return this template compiled to HTML string.
   * Return empty string if template is not loaded.
   *
   * @return {string} HTML representation of compiled template.
   */
  this.toHTML = function() {
    return toHTMLString_();
  };

  /**
   * Initialize template.
   *
   * @param {?string} param Template basic parameter.
   */
  function init_(param) {
    if (Utils.isString(param)) {
      if (param.indexOf('data:') === 0) {
        template_ = param.substring(5);
      } else if (param.indexOf('url:') === 0) {
        var templateUrl = param.substring(4);
        loadTemplate_(templateUrl);
      } else {
        var templateElement = DomUtils.getElement(param);
        if (templateElement) {
          template_ = templateElement.innerHTML;
        }
      }
    }
  }

  function createSearchRe_() {
    return RegExp(
        config_['openTag'] + '([\\w\\d\\s]+?)' + config_['closeTag'], 'gi'
    );
  }

  function loadFromCache_(url) {
    return Template._cache[url];
  }

  function putToCache_(url, template) {
    Template._cache[url] = template;
  }

  function loadTemplate_(url) {
    var fullUrl = config_['path'] + '/' + url + config_['extension'];
    var cached = loadFromCache_(fullUrl);
    if (cached) {
      updateTemplate_(cached);
    } else {
      template_ = '';
      request_.doGet(fullUrl, function(response) {
        putToCache_(fullUrl, response.responseText);
        updateTemplate_(response.responseText);
      });
    }
  }

  function updateTemplate_(text) {
    var i = 0, l = callbacks_.length;
    template_ = text;
    for (; i < l; i++) {
      Utils.isFunction(callbacks_[i]) && callbacks_[i]();
    }
    callbacks_ = [];
  }

  function compileTemplate_(item) {
    return template_.replace(searchTagsRe_, function(found) {
      var key = StringUtils.trimWhitespace(found.replace(config_['openTag'], '')
                                .replace(config_['closeTag'], ''));
      return item[key] || '';
    });
  }

  /**
   * Return compiled HTML string or empty string if
   * template is not loaded.
   *
   * @return {string} Compiled HTML string or empty string.
   */
  function toHTMLString_() {
    var compiled = '';
    var items = Utils.isArray(parameters_) ? parameters_ : [parameters_];
    var i = 0, l = items.length;
    for (; i < l; i++) {
      compiled += compileTemplate_(items[i]);
    }
    return compiled;
  }

  /**
   * Render this template to given element.
   *
   * @param {Element} element Parent element.
   * @param {function(...)=} opt_onRender Optional on render callback.
   */
  function render_(element, opt_onRender) {
    var compiled = toHTMLString_();
    /**
     * TBODY works ok both with table rows and other elements like DIV
     * @type {Element}
     */
    var c = document.createElement('tbody');
    c.innerHTML = compiled;
    while (c.firstChild) {
      var el = c.firstChild;
      if (el.nodeType != 3) {
        element.appendChild(c.firstChild);
      } else {
        c.removeChild(el);
      }
    }
    opt_onRender && opt_onRender();
  }

  /**
   * Template string.
   *
   * @type {string}
   * @private
   */
  var template_ = '';
  /**
   * Template parameters.
   *
   * @type {Array|Object}
   * @private
   */
  var parameters_ = {};
  /**
   * Template configurations.
   *
   * @type {Object}
   * @private
   */
  var config_ = {
    'extension': '.tpl',
    'path': 'templates/',
    'openTag': '{{',
    'closeTag': '}}'
  };
  /**
   * Regexp to match tags inside template.
   * Constructed from configuration.
   *
   * @type {string}
   * @private
   */
  var searchTagsRe_ = createSearchRe_();
  /**
   * An HttpRequest instance.
   *
   * @type {!HttpRequest}
   * @private
   */
  var request_ = new HttpRequest;
  /**
   * Callback list to call on render.
   *
   * @type {Array.<Function>}
   * @private
   */
  var callbacks_ = [];

  // initialize template
  init_(/** @type {string} */ (opt_param));
}


/**
 * Cache to store templates.
 *
 * @type {Object}
 */
Template._cache = {};

