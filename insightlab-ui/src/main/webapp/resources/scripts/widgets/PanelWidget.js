/**
 * Recent requests widget.
 *
 * @module recent-request-widget
 **/
ACX.add('panel-widget', function (A) {
  /**
   * @constructor
   * @extends {BaseWidget}
   * @param {Node|Element|string} container Widget container or its ID.
   */
  function PanelWidget(container) {
    BaseWidget.apply(this, arguments);

    /**
     * This method is called whenever an event occurs of the type for which the
     * EventListener interface was registered.
     *
     * @param {WidgetEvent} evt The WidgetEvent contains contextual information about the
     *            event.
     * @protected
     */
    this.handleEvent = function (evt) {
      if (config_ && config_['sections']) {
        buildContainer(config_['sections']);
      }
    };

    /**
     * Initialize Panel Widget
     * @param {Object} config Configurations for widget.
     * @protected
     */
    this.initWidget = function (config) {
      config_ = config;
      if (config_['name']) {
        self_.WIDGET_NAME = config_['name'];
      }

      if (config['prevWidget']) {
        self_.addEventListener(config['prevWidget'] + '.loaded',
          self_.handleEvent);
        self_.addEventListener(config['prevWidget'] + '.changed',
          self_.handleEvent);
      } else {
        self_.handleEvent();
      }
    };

    /**
     * Sets data from depended widgets
     * @protected
     * @param {Object} data
     */
    this.setFilterData = function (data) {
      targetSection.handleEvent(data);
      if (data_['page-state'] === 'build-target') {
        data_['redraw'] = true;
      }
    };

    /**
     * Returns states of all panel elements
     * @protected
     * @return {Object} Panel state.
     */
    this.getData = function () {
      var length = panelComponents_.length;
      for (var i = 0; i < length; i++) {
        var component = panelComponents_[i];
        if (component) {
          data_[component.WIDGET_NAME] = component.getData();
        }
      }
      return data_;
    };

    /**
     * @param {Array.<string>} sections
     */
    this.disableSections = function (sections) {
      if (!sections) {
        $container.find('a.disabled-link').removeClass('disabled-link');
        return;
      }
      var /**@type{number}**/ index = 0,
        /**@type{number}**/ length = sections.length,
        sectionLink;
      for (; index < length; index++) {
        sectionLink = _sections[sections[index]].addClass('disabled-link');
        if (sectionLink.hasClass('active')) {
          $container.find('a.side-icon.side-arr.active-tog').click();
          sectionLink.removeClass('active');
        }
      }
      if (sections.indexOf(data_.selected) !== -1) {
        data_.selected = null;
      }
    };

    this.resetPanel = function () {
      closePanel();
      var targetWidget = getComponentByName('view-saved-targets');
      targetWidget.resetData();
      data_['page-state'] = 'view-target';
      data_['active-panel'] = false;
      buildTarget();
    };

    var closePanel = function () {
      /**@type{jQuery}*/
      var $tog = $container.find('.side-arr');
      $tog.removeClass('active-tog');
      $container.find('a.side-icon').removeClass('active');
      $container.find('div.panel-box').hide();
    };

    /**
     * Creates control components for panel section
     * @private
     * @param {Object} config - contains configuration for panel components.
     * @param {jQuery} $section - section for component.
     * @return {?BaseWidget}
     */
    var createComponent = function (config, $section) {
      var divContainer = $('<div></div>').appendTo($section);
      /**type{?BaseWidget}*/var widget;
      if (config['control'] === 'filters-list') {
        filterSection = widget = new A.PanelFilters(divContainer);
        self_.addEventListener(config['handled-table'] + '.checked', function (evt) {
          widget.handleTableChange(evt);
        });
      }
      else if (config['control'] === 'target-group-list') {
        targetSection = widget = new A.TargetGroupsList(divContainer);
        self_.addEventListener(config['handled-table'] + '.checked', function (evt) {
          widget.handleTableChange(evt);
        });
      } else {
        return null;
      }
      widget['page-state'] = config['page-state'];
      widget.data_ = config['json-data'];
      widget.initWidget(config);
      /*add listener for current widget*/
      self_.addEventListener(widget.WIDGET_NAME + '.changed', function (evt) {
        data_['filter'] = widget.eventName || widget.WIDGET_NAME;
        self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
      });
      return widget;
    };

    /**
     * This method builds panel widget
     * @param {Array.<Object>} sections Array of sections.
     * @private
     */
    function buildContainer(sections) {
      /**@type{jQuery}*/
      var $tog = $container.find('.side-arr');
      /**@type {number}*/
      var length = sections.length;
      createPanelToggle($tog);
      for (var /**@type{number}*/ i = 0; i < length; i++) {
        /**@type{jQuery}*/
        var $div = createSectionLink(sections[i], $tog);
        var /**@type{number}*/ cLength = sections[i].components.length;
        for (var /**@type{number}*/ j = 0; j < cLength; j++) {
          /**@type{Object}*/
          var component = sections[i].components[j];
          if (component.control === 'element') {
            /**@type{jQuery}*/
            var $element = $('#' + component['container-id']);
            $div.append($element);
          }
          else {
            var widget = createComponent(component, $div);
            if (widget) {
              panelComponents_.push(widget);
            }
          }
        }
      }
      self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.loaded', self_));
    }


    /**
     * @param {Object} config Configuration object.
     * @param {jQuery} $tog Toggle button container.
     * @return {jQuery} Section link.
     */
    function createSectionLink(config, $tog) {
      /**@type {jQuery} */
      var $link = $('<a/>').appendTo($container.find('div.panel-buttons'))
        .addClass(config['button-class']);
      /**@type {jQuery} */
      var $tooltip = $('<div class="my-tooltip right-arrow"></div>');
      /**@type {jQuery} */
      var $section = $('<div/>').appendTo($container.find('div.panel-box'))
        .addClass('panel-content').css('display', 'none');
      $link.off('click');
      $link.attr('add-title', config['title']);
      $link.on('click', function () {
        if ($link.hasClass('disabled-link')) {
          return;
        }
        $container.find('.panel-buttons a.active').removeClass('active');
        var $this = $(this).addClass('active');
        $container.find('div.panel-content').hide();
        $section.show();
        $container.find('h3.panel-title').text(config.title);
        if (config.name === 'build-target') {
          if (!$tog.hasClass('active-tog')) {
            $tog.addClass('active-tog');
            $container.find('div.panel-box').show();
            data_['active-panel'] = true;
          }
          data_['page-state'] = 'build-target';
          buildTarget();
        } else if (data_['page-state'] !== 'view-target') {
          if (!$tog.hasClass('active-tog')) {
            $tog.addClass('active-tog');
            $container.find('div.panel-box').show();
            data_['active-panel'] = true;
          }
          data_['page-state'] = 'view-target';
          buildTarget();
        } else if (!$tog.hasClass('active-tog')) {
          $tog.addClass('active-tog');
          $container.find('div.panel-box').show();
          data_.filter = 'resize';
          data_['active-panel'] = true;
          self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
        }
        data_.selected = config.name;
      });
      $link.hover(function () {
        var $this = $(this);
        $tooltip.appendTo('body');
        $tooltip.text($this.attr('add-title'));
        var offset = $this.offset();
        var width = $tooltip.outerWidth(true) + 20;
        $tooltip.css({top: offset.top - 10, left: offset.left - width, 'display': 'block'});
      }, function () {
        $tooltip.detach();
      });
      _sections[config.name] = $link;
      return $section;
    }

    /**
     * Creates Toggle click element.
     * @param {jQuery} $tog Toggle button container.
     * @private
     * */
    var createPanelToggle = function ($tog) {
      $tog.off('click');
      $tog.on('click', function () {
        if ($tog.hasClass('active-tog')) {
          $tog.removeClass('active-tog');
          $container.find('div.panel-box').hide();
          data_['filter'] = 'resize';
          data_['active-panel'] = false;
          self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
        } else {
          $tog.addClass('active-tog');
          $container.find('div.panel-box').show();
          if (!$container.find('.panel-buttons a.active').length) {
            $container.find('.side-icon:not(.disabled-link):not(.side-arr):first').trigger('click');
          }
          data_['filter'] = 'resize';
          data_['active-panel'] = true;
          self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
        }
      });
    };


    /**
     * Handle progress bar
     * @param {WidgetEvent} evt Contains contextual information about the event.
     * @this {ProgressBarComponent}
     * */
    var progressHandler = function (evt) {
      if (evt) {
        var data = evt['widget'].getData();
        var value = 0;
        var length = data['sObjects'].length;
        for (/**@type{number}*/var index = 0; index < length; index++) {
          value += data['sObjects'][index]['basePercent'];
        }
        this.setData(value);
      }
    };


    /**
     * Returns panel component by name
     * @param {string} componentName Component's name.
     * @return {?BaseWidget}
     */
    var getComponentByName = function (componentName) {
      var length = panelComponents_.length;
      for (var index = 0; index < length; index++) {
        if (panelComponents_[index].WIDGET_NAME === componentName) {
          return panelComponents_[index];
        }
      }
      return null;
    };

    /**
     * handle event for build target button
     */
    var buildTarget = function () {
      if (data_['page-state'] === 'build-target') {
        $('table').addClass('table-checkable').closest('.dataTables_scrollBody').scrollLeft(0);

      }
      data_['filter'] = 'resize';
      self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
    };

    /**@type{jQuery}*/ var $container = $(container);
    /**@type{?Object}*/ var config_ = null;
    /**@type{Array.<BaseWidget>}*/ var panelComponents_ = [];
    /**
     * Storage for panel data.
     * @type {Object}
     * @private
     */
    var data_ = { 'page-state': 'view-target'};
    var filterSection;
    var targetSection;

    /**
     * @type Object
     * **/
    var _sections = {};

    /** @type {DataStorage} */
    var storage_ = new DataStorage;

    /**
     * The reference to current class instance. Used in private methods.
     *
     * @type {PanelWidget}
     * @private
     */
    var self_ = this;
  }

  var handleSegmentState = function (evt) {
    var filteredCount = evt['widget'].getData()['filtered-rows-count'];
    var dropdown = $(this.getContainer()).find('div.dropdown');
    if (filteredCount > 0) {
      dropdown.removeClass('disabled');
      dropdown.find('a:first').attr('data-toggle', 'dropdown');
    } else {
      dropdown.addClass('disabled');
      dropdown.find('a:first').attr('data-toggle', '#');
    }
  };
  A.PanelWidget = PanelWidget;
}, '0.0.1', {requires: ['panel-filters',
'targets-list']});

