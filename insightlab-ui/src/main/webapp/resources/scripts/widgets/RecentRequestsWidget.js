/**
 * Recent requests widget.
 *
 * @module recent-request-widget
 **/
ACX.add('recent-requests-widget', function (A) {
  /**
   * @constructor
   * @extends BaseWidget
   * @param {Node|Element|string|jQuery} container Widget container or its ID.
   */
  function RecentRequestsWidget(container) {
    BaseWidget.apply(this, arguments);
    /**
     * Initialize {RecentRequestsWidget}
     * @param {Object} config
     * @protected
     */
    this.initWidget = function (config) {
      config_ = config;
      self_.WIDGET_NAME = config_['name'];
      self_.JSON_URI = config_['json-url'];
      $ulContainer = $('<ul></ul>').appendTo($container);
      deleteButton = new A.ButtonComponent($container);
      deleteButton.initWidget(config_['delete-button']);
      self_.removeEventListener(deleteButton.WIDGET_NAME + '.clicked');
      self_.addEventListener(deleteButton.WIDGET_NAME + '.clicked', deletePortraits);
    };

    /**
     * This method handles change of dependent context
     * @param {Object} context
     * @protected
     */
    this.handleChange = function (context) {
      config_.insightId = context.targetid || config_.insightId;
      if (arguments.callee.jqxhr_) arguments.callee.jqxhr_.abort();
      /**@type {?string}*/ var params = null;
      if ('SurveyResponse' === context['targettype']) {
        params = '?' + $.param({'targettype': 'SurveyResponse', 'referencetype': 'SurveyUniverse'});
      } else if (context['targettype']) {
        params = '?' + $.param({'targettype': context['targettype'], 'targetid': context['targetid'] });
      }
      arguments.callee.jqxhr_ = self_.loadData(function (data) {
        data_.recents = data['results'] || [];
        if (data.settings) {
          dateSetting = data.settings['dateFormat'] + ' ' + data.settings['timeFormat'];
          _utcoffset = data.settings['utcOffset'] * 60 * 60 * 1000; //  to milliseconds
        }
        if ('SurveyResponse' !== context['targettype'] && config_.insightId) {
          data_.recents = data_.recents.filter(function (key) {
            return key.target.id === config_.insightId &&
              key.reference.type !== 'SurveyUniverse';
          });
        } else if ('SurveyResponse' !== context['targettype']) {
          data_.recents = data_.recents.filter(function (key) {
            return key.reference.type === 'SurveyUniverse';
          });
        }
        if (data_.recents instanceof Array && data_.recents.length) {
          data_.recents.sort(function (a, b) {
            a = new Date(a.createdDate.replace(/-/g, '/'));
            b = new Date(b.createdDate.replace(/-/g, '/'));
            return a < b ? 1 : a > b ? -1 : 0;
          });
        }
        data_['delete'] = [];
        deleteButton.getElement().prop('disabled', true);
        self_.handleResult(data_.recents);
        self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.loaded', self_));
        self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
      }, params);
    };

    /**
     * Draws RecentPortraits widget
     * @protected
     * @param {Array.<Object>} results Array of portraits.
     */
    this.handleResult = function (results) {
      $ulContainer.empty();
      if (!results || !results.length) {
        $container.find('ul').append('<li>No data exists</li>');
        $container.find('button').hide();
      } else {
        $container.find('button').show();
        /** @type {number} */ var i = (results.length - 1 || 0) >>> 0;
        for (; 0 <= i; i--) {
          var item = results[i];
          addPortrait(item);
        }
      }
    };

    /**
     * Returns filters data for specific portrait
     * @param {string} portraitid Portrait ID.
     * @return {?Object} filters data.
     * @protected
     */
    this.getFiltersByPortrait = function (portraitid) {
      /**@type {?Object}*/var filter = null;
      /**@type {?Object}*/var reference = null;
      /**@type {number}*/ var length = data_['recents'].length;
      if (length > 0) {
        if (portraitid && portraitid !== 'recent') {
          /**@type {number}*/ var i = 0;
          for (; i < length; i++) {
            var item = data_['recents'][i];
            if (portraitid === item['id']) {
              filter = item;
              break;
            }
          }
          if (!filter) return reference;
        } else {
          filter = data_['recents'].filter(function (key) {
            return key.reference.type !== 'SurveyUniverse';
          })[0];
        }
        reference = $.extend(true, { }, filter['reference']);
        reference['responseID'] = filter['reference']['id'];
        reference['targettype'] = filter['target']['type'];
        reference['id'] = filter['id'];
        currentPortrait_ = reference['id'];
        self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
      }
      return reference;
    };

    /**
     * Resets selected portrait.
     */
    this.resetSelected = function () {
      $('#recent-requests li').removeClass('act-link');
      currentPortrait_ = null;
    };

    /**
     * Check if national portrait is saved for current AD.
     * @return {boolean} Has national.
     */
    this.hasNational = function () {
      var national = $.grep(data_['recents'], function (key) {
        return key['reference']['response'] === null;
      });
      return national.length !== 0;
    };

    /**
     * Save current filter.
     * @param {WidgetEvent} evt The WidgetEvent contains contextual information about the event.
     * @protected
     * @this {RecentRequestsWidget}
     */
    this.savePortrait = function (evt) {
      /**@type {Object}*/
      var data = evt['widget'].getData();
      var portraitData = data['portraitType']['val'];
      var filtersData = data['reportFilters'];
      /**@type {Object}*/
      var params = {
        'targettype': portraitData['targettype'],
        'referencetype': portraitData['referencetype'],
        'targetid': 'survey-national' === portraitData['type'] ? filtersData['responseID'].val : data['insights'].val,
        'referenceid': filtersData ? filtersData['responseID'].val : 0,
        'name': getDefaultDescription(data)
      };
      loader.loadData('/srv' + config_['save-url'], function (response) {
        if (response['error']) {
          ACXM.Dialog.show({
            'id': 'feedbackMessage',
            'type': 'error',
            'title': response['error']['message'],
            'message': ''
          });
          return;
        }
        response['description'] = params['name'];
        /**@type {Array.<string>}*/
        var hashParts = window.location.hash.replace('#', '').split('/');
        $container.show();
        currentPortrait_ = response['id'];
        window.location.hash = hashParts[0] + '/portrait/' + currentPortrait_;
        config_.insightId = data['insights'].val;
        if (data_['recents'].length === 0) {
          $container.find('ul').html('');
          deleteButton.getElement().show();
        }
        data_['recents'].push(response);
        $ulContainer.find('li.act-link').removeClass('act-link');
        addPortrait(response);
        self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
      }, '?' + $.param(params));
    };

    /**
     * @protected
     * @return {Object}
     */
    this.getData = function () {
      data_.hasNational = self_.hasNational();
      return data_;
    };

    /**
     * Handles check/uncheck portrait
     * @private
     * @param {Object} evt
     * @this {Element}
     */
    var checkboxHandle = function (evt) {
      /** @type {string} */ var portraitId = evt.target.value;
      if ($(this).is(':checked')) {
        data_['delete'].push(portraitId);
      } else {
        data_['delete'].splice($.inArray(portraitId, data_['delete']), 1);
      }
      deleteButton.getElement().prop('disabled', (data_['delete'].length <= 0));
    };

    /**
     * Handles delete portraits
     * @private
     */
    var deletePortraits = function () {
      /**@type {string}*/
      var params = '?portraitid=' + data_['delete'].join('&portraitid=');
      loader.loadData('/srv' + config_['delete-url'], function (result) {
        var length = data_['delete'].length;
        for (var index = 0; index < length; index++) {
          var item = data_['delete'][index];
          $('input[value=' + item + ']').parents('li').remove();
          data_['recents'] = $.grep(data_['recents'], function (key) {
            return key['id'] != item;
          });
        }
        deleteButton.getElement().prop('disabled', true);
        if ($.inArray(currentPortrait_, data_['delete']) != -1) {
          window.location.hash = window.location.hash.replace(window.location.hash.split('/')[2], '');
          /**@type {jQuery}*/
          var $scatterLink = $('li[data-tabid="scattergram"] a');
          $scatterLink.attr('href', '#' + config_.insightId + '/scattergram');
          self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
        }
        data_['delete'] = [];
        if (data_['recents'].length === 0) {
          $container.find('ul').html('');
          $container.find('ul').append('<li>No data exists</li>');
          $container.find('button').hide();
        }
        self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.deleted', self_));
      }, params);
    };

    /**
     * Adds portrait to widget
     * @param {Object} result Portrait data.
     */
    var addPortrait = function (result) {
      /**@type {jQuery}*/
      var $filter = $('<li></li>');
      /**@type {jQuery}*/
      var $checkBoxLabel = $('<label/>').addClass('checkbox').appendTo($filter);
      /**@type {jQuery}*/
      var $checkBoxInput = $('<input/>', { type: 'checkbox', value: result['id'] }).appendTo($checkBoxLabel);
      $('<span/>').appendTo($checkBoxLabel);
      $checkBoxInput.on('change', checkboxHandle);
      /**@type {jQuery}*/
      var $filterLink = $('<a></a>', { 'id': result['id'], 'data-toggle': 'tooltip' }).appendTo($filter);
      $filterLink.off('single_double_click');
      $filterLink.single_double_click(function () {
        loadPortrait.call(this, result);
      }, function () {
        editPortrait.call(this);
      });
      $filterLink.text(result['description'] || result['type']);
      /**@type {jQuery}*/
      var $tooltip = $('<div class="my-tooltip right-arrow"></div>');
      $tooltip.html(createTooltip(result));
      $filterLink.hover(function () {
        $tooltip.appendTo('body');
        var offset = $(this).offset();
        var width = $tooltip.outerWidth(true) + 40;
        $tooltip.css({top: offset.top - 10, left: offset.left - width, 'display': 'block'});
      }, function () {
        $tooltip.detach();
      });
      $filter.prependTo($container.find('ul'));
      if (currentPortrait_ === result.id) {
        $filter.addClass('act-link');
      }
    };

    /**
     * Returns html for tooltip.
     * @param {Object} result Data of current portrait.
     * @return {string}
     * */
    var createTooltip = function (result) {
      /**@type {string}*/var html = '';
      var reference = result['reference'];
      var targetName = result['target']['question'];
      var createdDate = setFormattedValue_('date', result['createdDate'], _utcoffset, dateSetting);
      if (reference['categoryID']) {
        /**@type {string}*/var question = reference['question'];
        /**@type {string}*/var response = reference['response'];
        /**@type {string}*/var reftype = reference['type'];
        if ('SurveyUniverse' === reftype) {
          html = '<p><b>Survey Question:</b> ' + question +
            '</p><p><b>Response:</b> ' + response +
            '<p><b>Reference:</b> National</p>' +
            '<p><b>Created Date:</b> ' + createdDate;
        } else {
          html = '<p><b>Analytic Dataset:</b> ' + targetName + '</p>' +
            '<p><b>Survey Question:</b> ' + question +
            '</p><p><b>Response:</b> ' + response +
            '<p><b>Created Date:</b> ' + createdDate;
        }
      } else {
        html = '<p><b>Analytic Dataset:</b> ' + targetName + '</p>' +
          '<p><b>Reference:</b> National</p>' + '<p><b>Created Date:</b> ' + createdDate;
      }
      return html;
    };

    /**
     * Handle click on portrait
     * @param {Object} result Portrait data.
     * @this {Element} Portrait link.
     */
    var loadPortrait = function (result) {
      /**@type {Array.<String>}*/
      var currentHash = window.location.hash.split('/');
      $('.my-tooltip.right-arrow').detach();
      /**@type {?string}*/
      var currentHashId = currentHash.length === 3 ? currentHash[2] : null;
      if (result['id'] === currentPortrait_ && window.location.hash.indexOf('scattergram') === -1 && currentHashId === currentPortrait_) return;
      window.location.hash = config_.insightId + '/portrait' + ('/' + result['id'] || '');
      //Update tab links
      /**@type {jQuery}*/
      var $scatterLink = $('li[data-tabid="scattergram"] a');
      $scatterLink.attr('href', '#' + config_.insightId + '/scattergram' +
        ((result.id && result.reference.type === 'SurveyResponse') ? ('/' + result.id) : ''));
      $scatterLink.parent().removeClass('active');
      /**@type {jQuery}*/
      var $portraitLink = $('li[data-tabid="portrait"] a');
      $portraitLink.attr('href', '#' + config_.insightId + '/portrait' + ('/' + result['id'] || ''));
      /**@type {jQuery}*/
      var $targetLink = $('li[data-tabid="target-group"] a');
      $targetLink.attr('href', '#' + config_.insightId + '/target' + ('/' + result['id'] || ''));
      currentPortrait_ = result['id'] || '';
      self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
      $ulContainer.find('li.act-link').removeClass('act-link');
      $(this).parent().addClass('act-link');
    };

    /**
     * Edit portrait name
     * @this{Element|Node}
     */
    var editPortrait = function () {
      /**@type {jQuery}*/ var $this = $(this);
      $this.parent().children().hide();
      var inputConfig = {
        'name': 'check-name',
        'default-value': $this.text(),
        'default-focus': true,
        'check-length': true,
        'validator': {
          'marginTop': '-120px'
        }
      };
      /**@type{InputTagComponent}*/
      var input = new A.InputTagComponent($this.parent()[0].appendChild(document.createElement('div')));
      input.initWidget(inputConfig);
      self_.removeEventListener(input.WIDGET_NAME + '.input-focusout');
      self_.addEventListener(input.WIDGET_NAME + '.input-focusout', function (evt) {
        var inputWidget = evt['widget'];
        var inputVal = inputWidget.getData()['text'];
        var $inputContainer = $(inputWidget.getContainer());
        if (!inputVal) {
          $inputContainer.find('input').remove();
          $this.parent().children().show();
          $this.siblings('div.acxiom-tooltip').hide();
          $inputContainer.remove();
        } else if (input.validate()) {
          handleUpdate(evt);
          $this.parent().children().show();
          $inputContainer.remove();
          $this.siblings('div.acxiom-tooltip').hide();
        }
      });
      self_.removeEventListener(input.WIDGET_NAME + '.input-cancel');
      self_.addEventListener(input.WIDGET_NAME + '.input-cancel', function (evt) {
        var $inputContainer = $(evt['widget'].getContainer());
        $inputContainer.find('input').remove();
        $this.parent().children().show();
        $this.siblings('div.acxiom-tooltip').hide();
        $inputContainer.remove();
      });
    };

    /**
     * Update Portrait
     * @param {WidgetEvent} evt
     */
    var handleUpdate = function (evt) {
      var input = evt['widget'].getData();
      var $inputContainer = $(evt['widget'].getContainer());
      if (input['valid']) {
        var $link = $inputContainer.parent().find('a');
        var params = { 'portraitid': $link.attr('id'), 'description': encodeURI(input['text']) };
        loader.loadData('/srv' + config_['update-url'], function (data) {
          $link.text(input['text']);
          $inputContainer.parent().show();
          for (var i = 0; i < data_['recents'].length; i++) {
            var portrait = data_['recents'][i];
            if (portrait['id'] === params['portraitid']) {
              portrait['name'] = params['description'];
              portrait['description'] = params['description'];
            }
          }
          $inputContainer.find('input').remove();
          self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.changed', self_));
        }, '?portraitid=' + $link.attr('id') + '&description=' + encodeURI(input['text'])); //encoding text + $.param(params));
      } else {
        $inputContainer.find('input').remove();
        $inputContainer.parent().show();
      }
    };

    /**
     * Returns default name for new portrait
     * @return {string}
     * @param {Object} data
     */
    var getDefaultDescription = function (data) {
      if (!data['reportFilters']) {
        return 'National';
      }
      var recentCats = $.grep(data_['recents'], function (key) {
        return key['reference']['categoryID'] == data['reportFilters']['categoryID'].val;
      });
      var maxNumber = 1;
      if (recentCats.length > 0)
        $(recentCats).each(function (index, element) {
          var nameParts = $(element['description'].split(' '));
          var number = parseInt(nameParts.last()[0], 10);
          if (number && !isNaN(number) && number >= maxNumber) {
            maxNumber = number + 1;
          }
        });
      return data['reportFilters']['categoryID'].abbreviation + ' ' + maxNumber;
    };

    /**
     * @type {DataLoader}
     * @private
     */
    var loader = new DataLoader();

    /**
     * @type {ButtonComponent}
     * @private
     */
    var deleteButton = null;

    /**
     * @type {Object}
     * @private
     */
    var data_ = { /**@type {Array}*/'delete': [],
      /**@type {Array}*/'recents': [],
      'selected': function () {
        if (currentPortrait_) {
          return this.recents.filter(function (key) {
            return key.id === currentPortrait_;
          })[0];
        }
        return;
      }
    };

    /**
     * The reference to currently viewed portrait.
     * @type {string|number}
     * @private
     */
    var currentPortrait_;

    /**
     * The reference to current class instance. Used in private methods.
     * @type {RecentRequestsWidget}
     * @private
     */
    var self_ = this;

    /**
     * The reference to container.
     * @type {jQuery}
     * @private
     */
    var $container = $(self_.getContainer());

    /**
     * The reference to container.
     * @type {jQuery}
     * @private
     */
    var $ulContainer;

    /**
     * The reference to current date settings. Used in private methods.
     * @type {string | null}
     * @private
     */
    var dateSetting;
    /**
     * The reference to current utc. Used in private methods.
     * @type {?number}
     * @private
     */
    var _utcoffset;

    /**
     * Configuration of widget
     * @type {Object}
     * @private
     */
    var config_ = null;
  }

  A.RecentRequestsWidget = RecentRequestsWidget;
}, '0.0.1', {requires: ['button-component']});
