/**
 * ViewPortrait widget.
 *
 * @module view-portrait
 **/
ACX.add('view-portrait', function(A) {
  function ViewPortrait() {
    BaseWidget.apply(this, arguments);

    this.initWidget = function(config) {
      config_ = config;
      widget_.WIDGET_NAME = config_['name'];
      var mainContainer = this.getContainer();
      $(mainContainer).empty();
      var viewTemplate = new Template('view-portrait');
      viewTemplate.renderTo({},mainContainer);
      $chartBody = $container_.find('.chart-view');
      $tableBody = $container_.find('.table-view');
      $tabs = $container_.find('.tabPortrait a');

      //added event for navigation tabs
      $tabs.on('click', handleTabs);

      //added event for changed inputs
      $container_.find('.acxiom-action-container span').on('click', function () {
        var input = $(this).find('input')[0];
        input.checked = true;
        data_['targettype'] = input.value;
        if(config_['method']){
          loadPostPortrait()
        }else{
          loadGetPortrait();
        }
      });
    };

    /**
     * @override
     */
    this.handleEvent = function(params) {
      $container_.find('.title-text').html('Segment Name: <b>' + (params['segmentName'] ? params['segmentName']  : params['name']) + '</b> </br> Reference: National')
      resetState();
      if(config_['method']){
        delete params.name;
        delete params.description;
        params_ = params;
        loadPostPortrait(params_)
      } else {
        data_['targetid'] = params['id'];
        loadGetPortrait();
      }
    };


    function loadPostPortrait() {
      $container_.find('#progress-load').width('100%');
      if (arguments.callee.jqxhr_) arguments.callee.jqxhr_.abort();
      params_.portraitType = data_.targettype;
      loader.postData(config_['json-url'], params_, function(response) {
        widget_.handleResult(response);
      });
    }

    function loadGetPortrait() {
      $('#progress-load').width('100%');
      if (arguments.callee.jqxhr_) arguments.callee.jqxhr_.abort();
      var params = {
        'targettype': data_['targettype'],
        'targetid': data_['targetid'],
        'referencetype': 'National',
        'referenceid': 0
      };
      params = '?' + $.param(params);
      loader.loadData(config_['json-url'], function(response) {
        widget_.handleResult(response);
      }, params);

    }

    /**
     * Draws result
     * @this {ViewPortrait}
     * @param {Object} result Segment data for display.
     */
    this.handleResult = function(result) {
      $container_.find('#progress-load').width(0);
      var error = widget_.getErrorMessage(result);
      if (error) {
        var errorContainerId = 'error-container-id';
        var errorContainer = document.getElementById(errorContainerId) ||
          document.getElementById('view-portrait').appendChild(document.createElement('DIV'));
        errorContainer.id = errorContainerId;
        errorContainer.innerHTML = '<div class="alert">' + error + '</div>';
      } else {
        var dataTable = {};
        $.extend(true, dataTable, result); // copy data for table view
        if (result.rows) {
          result.rows = dataFormatter.filterPersonicxChartData(result.rows, 'targetCount');
        }
        if (tableChart && table) {
          tableChart.handleResult(result);
          tableChart.groupTable(groupingSettings);
          table.handleResult(dataTable);
          table.standingRedrawTable(null, null, null, 7);
        } else {
          createChartView(result);
          createTableView(dataTable);
        }
      }
    };

    function resetState() {
      $tableBody.hide();
      $chartBody.show();
      data_.targettype = 'SegmentHousehold';
      $container_.find('span input:eq(0)').prop('checked', true);
      $tabs.parents('li.active').removeClass('active');
      $tabs.parents('li:eq(0)').addClass('active');
    }

    function handleTabs(e) {
      $tabs.parents('li.active').removeClass('active');
      $(this).parent('li').addClass('active');
      e = e || window.event;
      var target = e.target || e.srcElement;
      var val = target.getAttribute('data-value');
      if (val === 'chart-tab') {
        $chartBody.show();
        $tableBody.hide();
        tableChart.columnSizing();
        tableChart.groupTable(groupingSettings);
      } else if (val === 'table-tab') {
        $tableBody.show();
        table.columnSizing();
        $chartBody.hide();
      }
    }

    var groupingSettings = {
      'columnName': 'probabilityIndex',
      'isFixedColumns': false
    };

    /**
     * Draws chart.
     * @private
     * @param {Array.<Object>} result Data to display on chart.
     */
    function createChartView(result) {
      if (!tableChart) {
      tableChart = new A.DataTableComponent($chartBody[0]);
      tableChart.WIDGET_NAME = config_.component[0]['name'];
      var portraitoptions = {
        'name': config_.component[0]['name']
      };

      tableChart.setOptions(portraitoptions);
      }
      tableChart.setData(result);
      tableChart.draw();
      tableChart && tableChart.groupTable(groupingSettings);
    }

    /**
     * Draws data table.
     * @private
     * @param {Array.<Object>} result Data to display on table.
     */
    function createTableView(result) {
      if (!table) {
        table = new A.DataTableComponent($tableBody[0]);
        table.WIDGET_NAME = config_.component[1]['name'];
        table.setOptions(config_.component[1]);
      }
      table.setData(result);
      table.draw();
    }

    /** @type {Element} */
    var $chartBody;
    /** @type {Element} */
    var $tableBody;
    /** @type {Element} */
    var $tabs;
    /**
     * Widget configuration
     * @type {Object}
     * @private
     */
    var config_ = null;

    var $container_ = $(this.getContainer());

    /**
     * @type {DataFormatter}
     * @private
     */
    var dataFormatter = new A.DataFormatter();

    /**
     * @type {DataTableComponent}
     * @private
     */
    var table = null;

    var params_;

    /**
     * @type {DataTableComponent}
     * @private
     */
    var tableChart = null;

    /**
     * The reference to current class instance. Used in private methods.
     * @type {!BaseWidget}
     * @private
     */
    var widget_ = this;

    /**
     * @type {Object}
     * @private
     */
    var data_ = {'targettype': 'SegmentHousehold'};

    /**
     * @type {DataLoader}
     * @private
     */
    var loader = new DataLoader();

  }
  A.ViewPortrait = ViewPortrait;
}, '0.0.1', {requires: ['datatable-component',
  'dataFormatter-component']});