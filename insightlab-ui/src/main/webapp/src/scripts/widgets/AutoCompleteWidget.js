
/**
 * @fileoverview Simple auto complete widget.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * @constructor
 * @extends {BaseWidget}
 * @param {Node|Element|string} container Widget container or its ID.
 */
function AutoCompleteWidget(container) {
  BaseWidget.apply(this, arguments);

  /**
   * Gets widget's data.
   * Accessible by 'evt.widget.getData()' in BaseWidget.handleEvent(evt) method.
   * @return {Object} Returns widget's data as dictionary representation.
   * @protected
   */
  this.getData = function() {
    return data_;
  };

  // Export for closure compiler.
  this['getData'] = this.getData;

  /**
   * @return {string} Returns query string parameters.
   * @protected
   */
  this.getParams = function() {
    return '';
  };

  // Export for closure compiler.
  this['getParams'] = this.getParams;

  /**
   * @return {Object|undefined} Returns saved configuration map.
   * @protected
   */
  this.getConfig = function() {
    return config_;
  };

  // Export for closure compiler.
  this['getConfig'] = this.getConfig;

  /**
   * @param {Array|Object} data The data object.
   * @return {Array|Object|undefined} data The data object.
   * @protected
   */
  this.loadDataHandler = function(data) {return data;};

  // Export for closure compiler.
  this['loadDataHandler'] = this.loadDataHandler;

  /**
   * Initialize widget.
   *
   * <br><code>initWidget({<br>
   * &nbsp; 'name': 'string',<br>
   * &nbsp; 'label': 'boolean',<br>
   * &nbsp; 'label-title': 'string',<br>
   * &nbsp; 'placeholder': 'string',<br>
   * &nbsp; 'collapsible': 'boolean',<br>
   * &nbsp; 'multiple': 'boolean',<br>
   * &nbsp; 'plugin_options': 'Object'<br>
   * })</code>
   * @param {Object} config The config object.
   * @see AutoCompleteWidget#getConfig
   * @protected
   */
  this.initWidget = function(config) {
    config_ = config;
    /** @type {Node|Element} */
    var div = self_.getContainer().appendChild(document.createElement('DIV'));

    self_['loadData'](function(data) {
      var id = config_['name'] + '-autocomlete';
      if (config['label'] == true) {
        var filterLabel = div.appendChild(document.createElement('LABEL'));
        filterLabel.setAttribute('for', id);
        filterLabel.innerHTML = config['label-title'];
      }
      input_ = div.appendChild(document.createElement('INPUT'));
      input_.id = id;
      input_.setAttribute('placeholder', config['placeholder'] || 'Filter');
      input_.className = 'acxm-text-input';
      list_ = div.appendChild(document.createElement('UL'));
      list_.className = 'unstyled';

      data = self_['loadDataHandler'](data);
      initAutocomplete_(data);

      input_['source'] = data;
      $(self_.getContainer()).accordion({'header': 'h3',
        'collapsible': config['collapsible']});
      self_.getContainer().className += config['collapsible'] ?
                                        ' collapsible' : '';
      self_.initDefaults(data);
      self_.dispatchEvent(
          new WidgetEvent(self_['WIDGET_NAME'] + '.loaded', self_));
      PlaceHolder.getInstance().apply(div);
    }, self_['getParams']());
  };

  // Export for closure compiler.
  this['initWidget'] = this.initWidget;

  /**
   * @param {Array|Object} data The data object.
   * @protected
   */
  this.initDefaults = function(data) {};

  // Export for closure compiler.
  this['initDefaults'] = this.initDefaults;

  /**
   * @param {Array|Object} data The data object.
   * @return {Function} Returns callback function.
   * @protected
   */
  this.getSuggestionsCallback = function(data) {
    return (function(req, add) {
      /** @type {Array} */
      var suggestions = [];
      for (/** @type {number} */ var i = 0; i < data.length;) {
        /** @type {Object} */
        var item = data[i++];
        if (self_['isMatchedSuggestion'](item, req)) {
          item['label'] = item['name'] || item['label'];
          suggestions.push(item);
        }
      }
      add(suggestions);
    });
  };

  this['getSuggestionsCallback'] = this.getSuggestionsCallback;

  /**
   * @param {Object} item The data item.
   * @param {Object} req The data req.
   * @return {boolean} Returns true if items is mattched to suggestion.
   * @protected
   */
  this.isMatchedSuggestion = function(item, req) {
    /** @type {string} */
    var name = (item['name'] || item['label'] || '').toLowerCase();
    return name.indexOf((req['term'] || '').toLowerCase()) == 0;
  };

  this['isMatchedSuggestion'] = this.isMatchedSuggestion;

  /**
   * @param {Object} event The event.
   * @param {Object} ui The jQuery object.
   * @return {boolean} Returns false if no custom handler.
   * @protected
   */
  this.customEventHandler = function(event, ui) {
    return false;
  };

  this['customEventHandler'] = this.customEventHandler;

  /**
   * @param {Object} event The event.
   * @param {Object} ui The jQuery object.
   * @protected
   * @this {AutoCompleteWidget}
   */
  this.selectEventHandler = function(event, ui) {
    if (!self_['customEventHandler'](event, ui)) {
      var li = document.createElement('LI'),
          key = ui['item']['id'],
          label = ui['item']['name'] || ui['item']['label'];
      if (config_['multiple']) {
        li.innerHTML = '<label class="checkbox" title="' + label + '">' +
                       '<input type="checkbox" value="' +
                       (ui['item']['id'] || 0) + '" class="checked" checked>' +
                       '<span>' + label + '</span></label>';
      } else {
        list_.innerHTML = '';
        li.innerHTML = '<label class="radio"><input type="radio" value="' +
                       (ui['item']['id'] || 0) + ' class="checked"' +
                       '" id="' + key + '" checked name="' +
                       self_['WIDGET_NAME'] +
                       '"><span>' + label + '</span></label>';
        li.firstChild.style.visibility = 'hidden';
      }
      list_.appendChild(li);
      setTimeout(function() {
        input_.value = '';
        PlaceHolder.getInstance().apply(input_.parentNode);
      }, 0);
      if (!config_['multiple']) data_ = {};
      data_[ui['item']['id']] = label;
      li.getElementsByTagName('INPUT')[0].onclick = itemEventHandler_;
      list_.parentNode.className = 'accordion-body in';
      list_.parentNode.id = 'campaigns';
      list_.parentNode.style.height = 'auto';
      if (event) {
        self_.dispatchEvent(
            new WidgetEvent(self_['WIDGET_NAME'] + '.changed', self_));
      }
    }
  };

  this['selectEventHandler'] = this.selectEventHandler;

  /**
   * Sets widget title.
   * @param {string} content Widget title content.
   * @return {Node|Element} Returns reference to created HTML element.
   */
  this.setTitle = function(content) {
    var title = self_.getContainer().appendChild(
        document.createElement('DIV'));
    title.innerHTML = '<a href="#' + self_['WIDGET_NAME'] + '" ' +
                      'class="accordion-toggle" data-toggle="collapse" ' +
                      'data-parent="autocomplete-widget-multiple">' +
                      content + '<span class="dropdown-icon"></span></a>';
    title.className = 'accordion-heading';
    return title;
  };

  this['setTitle'] = this.setTitle;

  /**
   * @param {Array|Object} data The data object.
   * @private
   */
  function initAutocomplete_(data) {
    config_['plugin_options'] = config_['plugin_options'] || {};
    $['extend'](config_['plugin_options'], {
      'source': self_['getSuggestionsCallback'](data),
      'select': self_['selectEventHandler'],
      'open': function(a, b) {
        var widget = $(input_).autocomplete('widget')[0];
        widget.className = 'typeahead dropdown-menu';
        widget.style.width = input_.offsetWidth + 'px';
        var links = widget.getElementsByTagName('A');
        for (var i = 0; i < links.length; i++)
          links[i].title = links[i].innerText || links[i].textContent;
      }
    });
    $(input_).autocomplete(config_['plugin_options']);
  }

  /**
   * @private
   * @this {Node|Element}
   */
  function itemEventHandler_() {
    // "this" is input.checkbox or input.radio element;
    this.className = this.className.replace('checked', '');
    if (this.checked) {
      if (!config_['multiple']) data_ = {};
      data_[this.value] = this.nextSibling.innerHTML;
      this.className += 'checked';
    } else {
      delete data_[this.value];
      /*var parentElement = this.parentNode;
      while (parentElement.tagName != 'LI') {
        parentElement = parentElement.parentNode;
      }
      list_.removeChild(parentElement);*/
    }
    storage_.set(self_['WIDGET_NAME'], data_);
    input_.focus();
    self_.dispatchEvent(
        new WidgetEvent(self_['WIDGET_NAME'] + '.checked', self_));
  }

  /**
   * The reference to current class instance. Used in private methods.
   * @type {BaseWidget}
   * @private
   */
  var self_ = this;

  /**
   * @private
   */
  var input_ = null;

  /**
   * @private
   */
  var list_ = null;

  /**
   * @private
   */
  var data_ = {};

  /**
   * @type {Object|undefined}
   * @see AutoCompleteWidget#initWidget
   * @see AutoCompleteWidget#getConfig
   * @private
   */
  var config_ = {};

  /**
   * @type {DataStorage}
   * @private
   */
  var storage_ = new DataStorage;
}

// Export for closure compiler.
window['AutoCompleteWidget'] = AutoCompleteWidget;

