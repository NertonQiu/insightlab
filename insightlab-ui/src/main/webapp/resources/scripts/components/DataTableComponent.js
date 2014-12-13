/**
 * Datatable component.
 *
 * @module datatable-component
 **/
ACX.add('datatable-component', function (A) {
  /**
   * @constructor
   * @param {Node|Element|string|jQuery} container Widget container or its ID.
   * @extends BaseComponent
   */
  function DataTableComponent(container) {
    BaseComponent.apply(this, arguments);

    /**
     * Initialize {DataTableComponent}
     * @param {Object} config
     * @protected
     * @this {DataTableComponent}
     */
    this.initWidget = function (config) {
      config_ = config;
      self_.setOptions(config_);
      if (config_['tableoptions'] && config_['tableoptions']['bServerSide']) {
        self_.draw();
      } else if (config_['self-load']) {
        self_.handleEvent();
      }
    };

    /**
     * This method is called whenever an event occurs of the type for which the
     * EventListener interface was registered.
     * @param {Object} opt_params
     */
    this.handleEvent = function (opt_params) {
      /**@type {string}*/
      var params = '';
      config_['params'] = mergeConfig(config_['params'], opt_params);
      if (config_['params']) {
        params = '?' + $.param(config_['params']);
      }
      self_.loadData(function (data) {
        self_.setData(data);
        dataMapper.setSettings(data['settings']);
        self_.draw();
      }, params);
    };

    /**
     * Draws the button component.
     * @this {DataTableComponent}
     */
    this.draw = function () {
      // get dataTable options
      componentOptions = getComponentOptions();
      var optOptions = componentOptions['config'];
      //table template
      table_ = table_ || self_.getContainer().querySelector('TABLE');
      if (!table_) {
        self_.getContainer().innerHTML = optOptions['use-footer'] ?
          '<table><thead><tr></tr></thead><tbody></tbody><tfoot><tr></tr></tfoot></table>' :
          '<table><thead><tr></tr></thead><tbody></tbody></table>';
        table_ = self_.getContainer().querySelector('TABLE');
      }
      // create dataTable
      oTable = $(table_).dataTable(componentOptions);
      //set plug-ins
      oTable.fnSetFilteringPressEnter(); //.fnResetFilterButton();

      /**
       * set additional filtering
       */
      if (optOptions['custom-filter-column']) {
        oTable.fnSettings()['custom-filtering'] =
          optOptions['custom-filter-column'];
        //oTable.fnDraw();
      }

      /**
       * set additional columns filters
       */
      if (optOptions['column-filters']) {
        oTable.fnSettings()['column-filters'] =
          optOptions['column-filters'];
        oTable.fnSettings()['use-column-filters'] = optOptions['use-column-filters'] ? true : false;
        if (optOptions['default-ui-filter']) {
          oTable.fnFilter(optOptions['default-ui-filter']['default-value'], optOptions['default-ui-filter']['column-index']);
        }
      }

      /**
       * set default search
       */
      if (optOptions['default-search-param']) {
        oTable.fnSettings()['oPreviousSearch'].sSearch = optOptions['default-search-param'];
      }

      if (optOptions['role']) {
        oTable.fnSettings()['role'] = optOptions['role'];
      }

      // handle filter event
      oTable.off('filter').on('filter', function (e) {
        data_['filtered-rows-count'] = oTable
          ._('tr', { 'filter': 'applied' }).length;
        data_['sIds'] = [];
        self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME +
          '.filtered', self_));
      });

      //handle pagination event
      oTable.off('page').on('page', function () {
        data_['sIds'] = [];
        self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME +
          '.change-page', self_));
      });

      if (optOptions['uncheck-all']) {
        if (oTable.fnSettings()['oFixedColumns']) {
          var cloneTable = oTable
            .fnSettings()['oFixedColumns']['dom']['clone']['left'];
          uncheckColumn = $(cloneTable['header']).find('tr th:eq(1)');
          uncheckColumn.on('change', 'input', this.uncheckAll);
        }
        else {
          uncheckColumn = oTable.fnSettings()
            .aoColumns[optOptions['uncheck-all']];
          $(uncheckColumn['nTh']).on('change', 'input', this.uncheckAll);
        }
      }
      if (optOptions['check-all']) {
        checkColumn = oTable.fnSettings()
          .aoColumns[optOptions['check-all']];
        $(checkColumn['nTh']).on('change', 'input', this.checkAll);
      }

      // handle select row evt
      $(table_).find('tbody').on('change', 'input', objectSelect);

      if (this['options']['drawerMenu']) {
        addDrawerMenu();
      }
      self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.loaded', self_));
      if (optOptions['update-status']) {
        self_.srvUpdateRow(optOptions['update-status']);
      }
    };
    /**
     * Add placeholder to table.
     */
    this.holderTable = function () {
      if (/MSIE (\d+\.\d+);/.test(navigator.userAgent)) {
        var ieversion = new Number(RegExp.$1);
        $('input.search').each(function () {
          var $this = $(this);
          if ((ieversion == 8 || ieversion == 9) &&
            $this.attr('placeholder')) {
            var hold = $this.parent().find('.holder');
            if (hold.length) hold.remove();
            var holdertext = $this.attr('placeholder');
            $this.parent().append('<span class="holder">' + holdertext + '</span>');
            var holder = $this.parent().find('.holder');
            if ($this.val() !== '') holder.hide();
            $this.focus(function () {
              holder.hide();
            });
            $this.focusout(function () {
              if ($this.val() === '') holder.show();
            });
          }
        });
      }
    };

    /**
     * Redraws table.
     * @protected
     * @this {DataTableComponent}
     */
    this.redrawTable = function () {
      oTable.fnDraw();
    };

    /**
     * Reloads table.
     * @protected
     * @this {DataTableComponent}
     */
    this.reloadTable = function () {
      oTable.fnReloadAjax();
    };

    /**
     * Standing reloads table.
     * @param {boolean|undefined} isDeleteAction
     * @param {number|undefined} count
     * @param {boolean} firstPage for pagination
     * @param {number} sort for sorting
     * @protected
     * @this {DataTableComponent}
     */
    this.standingRedrawTable = function (isDeleteAction, count, firstPage ,sort) {
      var settings = oTable.fnSettings();

     if(sort){
       settings.aaSorting[0][0] = sort;
       settings.aaSorting[0][1] = 'desc';
     }

      var displayed = settings['_iDisplayStart'];
      if(firstPage) settings['_iDisplayStart'] = 0;
      if (count && displayed >= (settings['_iRecordsDisplay'] - count) && isDeleteAction) {
        settings['_iDisplayStart'] = settings['_iDisplayStart'] - settings['_iDisplayLength'];
      }
      oTable.fnStandingRedraw();
      self_.deleteRow(data_['sIds']);
    };

    /**
     * Get table state params.
     * @protected
     * @return {Object}
     * @this {DataTableComponent}
     */
    this.getTableState = function () {
      return oTable.fnSettings();
    };

    /**
     * Set table scrollY height.
     * @protected
     * @param {string|int} height
     * @this {DataTableComponent}
     */
    this.setTableHeight = function (height) {
      oTable.fnSettings()['oScroll']['sY'] = height + 'px';
      oTable.fnDraw();
    };

    /**
     * Sets filtered value.
     * @param {string|number|Object|undefined} value
     * @param {boolean|undefined} redraw
     * @protected
     * @this {DataTableComponent}
     */
    this.setFilterValue = function (value, redraw) {
      oTable.fnSettings()['custom-filtering']['value'] = value;
      if (redraw)oTable.fnDraw();
    };
    /**
     * Sets data.
     * @param {Object|undefined} result
     * @protected
     * @this {DataTableComponent}
     */
    this.setData = function (result) {
      this['dataList'] = result['rows'];
      dataMapper.setSettings(result['settings']);
      for (var prop in result) {
        if (prop !== 'rows' && prop !== 'settings')
          data_['info'][prop] = result[prop];
      }
    };

    this.handleResult = function (data) {
      var error = self_.getErrorMessage(data);
      if (error) {
        data = { 'rows': [] };
      }
      if (!oTable) {
        self_.setData(data);
        self_.draw();
      } else {
        oTable.fnSettings()['oInit']['aaData'] = data['rows'];
        oTable.fnSettings()['ext-sort'] = null;
        self_.setData(data);
        oTable.fnClearTable(false);
        oTable.fnAddData(self_['dataList']);
        oTable.fnDraw();
      }
    };

    /**
     * Check all rows. Uses as public method.
     * @protected
     * @this {DataTableComponent}
     */
    this.checkAll = function () {
      if (checkColumn) {
        if (this.checked) {
          var $checkboxVal = $(table_).find('tbody input[disabled!=disabled]');
          $checkboxVal.prop('checked', true);
          data_.sIds = [];
          for (var i = 0; i < $checkboxVal.length; i++) {
            data_.sIds.push($checkboxVal[i]['value']);
          }
          var ids = data_.sIds;
          var objects = self_.dataList.length ? self_.dataList : oTable.fnGetData();
          data_.sObjects = objects.filter(function (key) {
            return ids.indexOf(key[componentOptions['config']['id_column_name']]) !== -1;
          });
          self_.dispatchEvent(new WidgetEvent(self_['options']['name'] + '.checked', self_));
        } else {
          $(table_).find('tbody input').prop('checked', false);
          data_['sObjects'] = [];
          data_['sIds'] = [];
          self_.dispatchEvent(new WidgetEvent(self_['options']['name'] + '.checked', self_));
        }
      }
    };

    /**
     * Uncheck all selected rows. Uses as public method.
     * @protected
     * @this {DataTableComponent}
     */
    this.uncheckAll = function () {
      var fixedCol = oTable.fnSettings()['oFixedColumns'];
      if (fixedCol) {
        if (uncheckColumn) {
          $(uncheckColumn).find('label').hide();
        }
        var cloneBody = fixedCol['dom']['clone']['left']['body'];
        $(cloneBody).find('tbody input').prop('checked', false);
        data_['sObjects'] = [];
        data_['sIds'] = [];
        self_.dispatchEvent(new WidgetEvent(self_['options']['name'] +
          '.checked', self_));
        $(uncheckColumn).find('label').hide();
      }
      else {
        if (uncheckColumn) {
          $(uncheckColumn['nTh']).find('label').hide();
        }
        $(table_).find('tbody input').prop('checked', false);
        data_['sObjects'] = [];
        data_['sIds'] = [];
        self_.dispatchEvent(new WidgetEvent(self_['options']['name'] +
          '.checked', self_));
        $(this).parent('label').hide();
      }
    };

    /**
     * Group table
     * @param {Object} groupingSettings Settings which used for grouping.
     * @this {DataTableComponent}
     */
    this.groupTable = function (groupingSettings) {
      var groupedData = groupData(oTable._('tr', { 'filter': 'applied' }),
        groupingSettings['columnName']);
      var prevCount = 0;
      if (!groupingSettings['isFixedColumns']) {
        oTable.find('tbody tr').find('td:first').hide();
        $.each(groupedData, function (index, element) {
          var groupedTd = oTable
            .find('tbody tr:eq(' + prevCount + ') td:first')
            .addClass('td-row')
            .attr('rowspan', (groupedData[index].length)).show();
          //hide title from first(grouped) td
          var groupedTr = groupedTd.parent();
          var title = groupedTr.attr('title');
          groupedTr.on('hover', function (e) {
            if (e.target === groupedTd[0]) {
              groupedTr.attr('title', '');
            } else {
              groupedTr.attr('title', title);
            }
          });
          groupedTr.on('focusout', function () {
            groupedTr.attr('title', title);
          });
          prevCount += groupedData[index].length;
        });
      }
      else if (oTable.fnSettings()['oFixedColumns']) {
        var cloneTable = oTable
          .fnSettings()['oFixedColumns']['dom']['clone'];
        $(cloneTable.left.body).find('tbody tr').find('td:first').hide();
        if (groupingSettings['page-state'] === 'build-target') {
          ($(cloneTable.left.body).find('table tr td.td-hide')
            .removeClass('td-check') && $(cloneTable.left['header'])
            .find('thead tr th:eq(1)').removeClass('td-check'));
          data_['sIds'] = groupingSettings['sIds'];
          $.each(groupingSettings['sIds'], function (index, element) {
            $(cloneTable.left.body)
              .find('input[value=' + element + ']')
              .prop('checked', true);
          });

        } else {
          ($(cloneTable.left.body).find('tbody tr td.td-hide')
            .addClass('td-check') && $(cloneTable.left['header'])
            .find('thead tr th:eq(1)').addClass('td-check'));
        }
        $.each(groupedData, function (index, element) {
          $(cloneTable.left.body)
            .find('tbody tr:eq(' + prevCount + ') td:first')
            .addClass('td-row')
            .attr('rowspan', (groupedData[index].length)).show();
          prevCount += groupedData[index].length;
        });
      }
    };

    this.setSelectedRows = function (ids) {
      $(table_).find('input[type="radio"]').prop('checked', false);
      data_['sIds'] = ids;
      data_['sObjects'] = self_.dataList.filter(function (key) {
        return ids.indexOf(key[componentOptions['config']['id_column_name']]) !== -1;
      });
      for (var i = ids.length; i; i--) {
        $('input[value=' + ids[i - 1] + ']').prop('checked', true);
      }
      self_.dispatchEvent(new WidgetEvent(self_['options']['name'] + '.checked', self_));
    };

    /**
     * Resize table.
     * @this {DataTableComponent}
     */
    this.columnSizing = function () {
      oTable.fnAdjustColumnSizing();
    };

    /**
     * Recolor column with bar.
     * @param {string} column Name of column with bar for recolor.
     * @this {DataTableComponent}
     * @return {Object|undefined}
     */
    this.recolorBar = function (column) {
      var categories;
      if (column) {
        categories = {};
        var currentColor;
        $.each(oTable.fnSettings()['aoData'], function () {
          //In case of column age, ageText should be used
          if (column == 'age') column = 'ageText';
          var segment = this['_aData'][column];
          if (!categories[segment]) {
            currentColor = getColor(currentColor);
            categories[segment] = currentColor;
          }
          /**@type{string}*/
          var selector = 'div.progress-left :first-child, ' +
            'div.progress-right :first-child';
          /**@type{jQuery}*/
          var divColored = $(this['nTr']).find(selector);
          divColored.removeAttr('class').css('background', categories[segment]);
        });
      } else if ($('div.line-color-red').length === 0) {
        var redbars = $(table_).find('div.progress-left :first-child');
        redbars.addClass('line-color-red');
        var bluebars = $(table_).find('div.progress-right :first-child');
        bluebars.addClass('line-color-blue');
      }
      return categories;
    };

    /**
     * Gets component data.
     * @return {Object} Returns component data as dictionary representation.
     * @this {DataTableComponent}
     */
    this.getData = function () {
      return data_;
    };

    /**
     * Showing and hiding columns.
     * @param {Array.<Object>} columns List of items.
     */
    this.setColumns = function fnShowHide(columns) {
      for (var i = 0; i < columns.length; i++) {
        var element = columns[i];
        var columnIndex = oTable.fnGetColumnIndex(element.column);
        oTable.fnSetColumnVis(columnIndex, element.vizible, i === columns.length - 1);
      }
      oTable.fnAdjustColumnSizing();
    };

    /**
     * Updates value in row
     * @param {Object} item Item for update.
     * @this {DataTableComponent}
     */
    this.updateRow = function (item) {
      var columnIndex = oTable.fnGetColumnIndex(item['column']);
      oTable.fnUpdate(item['value'], item['row'], columnIndex, false, false);
    };

    /**
     * Refresh data in table
     * @param {Object} data new data for refresh.
     * @this {DataTableComponent}
     */
    this.refreshTableData = function (data) {
      oTable.fnClearTable();
      oTable.fnAddData(data);
      self_['dataList'] = data;
      data_['sIds'] = [];
      data_['sObjects'] = [];
    };
    /**
     * Delete specific row
     * @param {Array} selectedIDs Array of selected ids.
     * @this {DataTableComponent}
     */
    this.deleteRow = function (selectedIDs) {
      var tableRows = oTable.fnGetNodes();
      $.each(selectedIDs, function (i, data) {
        var rowForDelete = $('input[value=' + data + ']', tableRows).closest('tr');
        if (rowForDelete[0]) {
          oTable.fnDeleteRow(rowForDelete[0]);
        }
      });
      data_['sIds'] = [];
      data_['sObjects'] = [];
      data_['after-delete-data'] = self_.getTableData();
      self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.delete-row', self_));
    };
    /**
     * Add data to table
     * @param {Array.<Object>} data Data to add.
     * @this {DataTableComponent}
     */
    this.addData = function (data) {
      oTable.fnAddData(data);
    };
    /**
     * Return current table data
     * @return {Array.<Object>} current table data.
     * @this {DataTableComponent}
     */
    this.getTableData = function () {
      return oTable.fnGetData();
    };
    /**
     * Return all id column values of table data
     * @return {Array}
     * @this {DataTableComponent}
     */
    this.getAllIdValues = function () {
      var data = self_.getTableData(), ids = [], len = data.length, i = 0;
      for (; i < len; i++) {
        ids.push(data[i][componentOptions['config']['id_column_name']]);
      }
      return ids;
    };

    this.dataLoader = new DataLoader();

    var loadServerData = function (sSource, aoData, fnCallback) {
      /** @type {Object} */
      var params = getParams(aoData, this.fnSettings().aoColumns, this);
      self_['JSON_URI'] = sSource;
      self_.loadData(function (result) {
        var dtresult = {
          'sEcho': result['sEcho'],
          'iTotalRecords': result.totalCount,
          'iTotalDisplayRecords': result.totalCount,
          'aaData': result.rows ? result.rows : []
        };
        self_.setData(result);
        fnCallback(dtresult);
        self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.updated-data', self_));
      }, '?' + $.param(params));
    };

    /**
     * Returns parameters for datatable server side processing
     * @param {Object} aoData
     * @param {Array.<Object>} columns
     * @param {DataTable} table
     * @return {Object} params.
     */
    var getParams = function (aoData, columns, table) {
      /**@type{Object}*/
      var initConfig = table.fnSettings()['oInit'];
      var params = $.extend({}, config_.params);
      var filters;
      if (config_['default-filters'] && !isDefault) {
        filters = $.extend({}, true, config_['default-filters']);
      }
      $.each(aoData, function () {
        if (this.name === 'iDisplayLength' && this.value !== -1) {
          params['limit'] = this.value;
        } else if (this.name == 'sSearch') {
          if (this.value) {
            var validFilter = this.value.replace(/^\s+|\s+$/g, '');
            params['searchterm'] = validFilter;
          }
          else if (componentOptions['config']['default-search-param'] && !bDefaultSearch) {
            params['searchterm'] = componentOptions['config']['default-search-param'].replace(/^\s+|\s+$/g, '');
            bDefaultSearch = true;
          }
        } else if (this.name == 'iDisplayStart') {
          params['skipcount'] = this.value;
        } else if (this.name == 'iSortCol_0') {
          params['sortcolumn'] = columns[this.value]['mDataProp'] ==
            'userID' ? 'username' :
            columns[this.value]['mDataProp'].toLowerCase();
        } else if (this.name == 'sSortDir_0') {
          params['sortdirection'] = this.value == 'asc' ?
            'ascending' : 'descending';
        } else if (this.name == 'sEcho') {
          params['sEcho'] = this.value;
        } else if (table.fnSettings()['column-filters']) {
          var columnsconfig = table.fnSettings()['column-filters'];
          filters = filters || {};
          for (var i = 0; i < columnsconfig.length; i++) {
            var param =
              columns[columnsconfig[i]['column-index']]['mDataProp']
                .toLowerCase();
            if (this.name == 'sSearch_' +
              columnsconfig[i]['column-index']) {
              if (columnsconfig[i]['type'] === 'select' && this.value) {
                filters[param] = {
                  'operator': '=',
                  'operands': {
                    'value': this.value
                  }
                };
              } else if (columnsconfig[i]['type'] === 'user-select' && this.value) {
                filters['username'] = {
                  'operator': '=',
                  'operands': {
                    'value': this.value
                  }
                };
              } else if (columnsconfig[i]['type'] === 'date-range' && this.value) {
                var value = this.value.split('&&');
                filters[param] = { 'operator': 'between',
                  'operands': {
                    'lower': value[0],
                    'upper': value[1] || ''
                  }
                };
              } else if (columnsconfig[i]['type'] === 'number-range' && this.value) {
                var rangevalue = this.value.split('&&');
                filters[param] = { 'operator': 'between',
                  'operands': {
                    'lower': rangevalue[0],
                    'upper': rangevalue[1] || ''
                  }
                };
              }
            }
          }
        } else if (initConfig['config']['column-filters']) {
          var initColumnsConfig = initConfig['config']['column-filters'];
          filters = filters || {};
          for (var i = 0; i < initColumnsConfig.length; i++) {
            var param =
              columns[initColumnsConfig[i]['column-index']]['mDataProp']
                .toLowerCase();
            if (this.name == 'sSearch_' +
              initColumnsConfig[i]['column-index']) {
              if (initColumnsConfig[i]['type'] === 'user-select') {
                this.value = initConfig['config']['username'] || '';
                table.fnSettings()['aoPreSearchCols'][initColumnsConfig[i]['column-index']]['sSearch'] = this.value;
                filters['username'] = {
                  'operator': '=',
                  'operands': {
                    'value': this.value
                  }
                };
              }
            }
          }
        }
      });
      if (componentOptions['config'] && componentOptions['config']['source-filter']) {
        params['source'] = componentOptions['config']['source-filter'];
      }
      if (filters) {
        params.filters = JSON.stringify({ 'filters': filters });
      }
      var isDefault = true;
      return params;
    };

    /**
     * Gets dataList item by id
     * @param {number||string} id item id property.
     * @return {Object} returns dataList item by id.
     */
    var getObjectById = function (id) {
      /**@type {Array.<Object>}*/
      var currentFullData = self_['dataList'].length ? self_['dataList'] :
        oTable.fnGetData();
      /**@type {Array.<Object>}*/
      var result = $.grep(currentFullData, function (key) {
        return key[componentOptions['config']['id_column_name']] == id;
      });
      return result[0];
    };

    /**
     * Adds drawer menu for table
     * @private
     */
    var addDrawerMenu = function () {
      var selector = 'tbody tr td:not(:first-child,:nth-child(6))';
      $(table_).on('click', selector, function () {
        var nTr = $(this).parent()[0];
        if (oTable.fnIsOpen(nTr)) {
          /* This row is already open - close it */
          oTable.fnClose(nTr);
          $(nTr).removeClass('selected_tr');
        }
        else {
          /* Open this row */
          oTable.fnOpen(nTr, fnFormatDetails(nTr), 'details');
          $(nTr).addClass('selected_tr');
          $(nTr).next().addClass('info');
        }
      });
    };

    /**
     * Merge configurations for datatable component
     * @return {Object} Merged options.
     */
    var getComponentOptions = function () {
      // get datable options for current table by mapped name
      var mapper = self_['options']['name'];
      var options = dataMapper.getOptions(mapper);
      options['config'] = self_['options'];
      options = mergeConfig(options, self_['options']['tableoptions']);
      // set collapse for all tables that don't use drawer menu
      if (!self_['options']['drawerMenu']) {
        options.bScrollCollapse = true;
      }

      if (!options.oLanguage) {
        options.oLanguage = { sEmptyTable: '没有记录返回.',
          sSearch: ''
        };
      }
      if (options['bServerSide']) {
        options['fnServerData'] = loadServerData;
      } else {
        options['aaData'] = self_['dataList'];
      }
      return options;
    };

    /**
     * Merge config into options.
     * @param {Object} options
     * @param {Object} config
     * @return {Object} Merged object.
     */
    function mergeConfig(options, config) {
      options = options || {};
      for (var prop in config) {
        if (config.hasOwnProperty(prop)) {
          options[prop] = config[prop];
        }
      }
      return options;
    }

    /**
     * Handle select row event. Saves selected row value in data_
     * @this {Element|Node}
     */
    var objectSelect = function () {
      if (this.type == 'radio') {
        data_['sObjects'] = [];
        data_['sIds'] = [];
      }
      var id = $(this).val().toString();
      if (this.checked) {
        if (componentOptions['config']['isObjectSelect']) {
          data_['sObjects'].push(getObjectById(id));
        }
        data_['sIds'].push($(this).val());
      } else {
        if (componentOptions['config']['check-all']) {
          var head = oTable.fnSettings().nTHead;
          var input = $(head).find('input')[0];
          if (input.checked) {
            input.checked = false;
          }
        }
        data_['sObjects'] = $.grep(data_['sObjects'], function (key) {
          return key[componentOptions['config']['id_column_name']] != id;
        });
        var index = $.inArray($(this).val(), data_['sIds']);
        if (index > -1) data_['sIds'].splice(index, 1);
      }
      self_.dispatchEvent(new WidgetEvent(self_['options']['name'] + '.checked', self_));
      if (uncheckColumn) {
        var header = oTable.fnSettings()['oFixedColumns'] ?
          $(uncheckColumn) : $(uncheckColumn['nTh']);
        var $row = $('#portraitChart-widget').find('td.td-row');
        if (data_['sIds'].length) {
          header.find('label').show().css('left', $row.outerWidth(true) + 7);
          header.find('input').prop('checked', false);
        } else {
          header.find('label').hide();
        }
      }
    };

    /**
     * Get used color
     * @return {string} Next color name.
     * @param {?string} color Currently used color.
     * @private
     */
    var getColor = (function () {
      /** @type {Array.<string>} */
      var colors = [
        '#4A89DC', '#A0D468', '#D770AD', '#967ADC', '#8CC152',
        '#5D9CEC', '#F6BB42', '#DA4453', '#656D78', '#d4b275',
        '#AC92EC', '#4FC1E9', '#48CFAD', '#FC6E51', '#a17477',
        '#EC87C0', '#69b2bb', '#2498c3', '#FFCE54', '#ED5565'
      ];
      return function (color) {
        if (!color) {
          return colors[0];
        } else {
          return colors[$.inArray(color, colors) + 1];
        }
      };
    })();

    /**
     * Group array of objects by object's property
     * @return {Object.<Array.<Object>>} grouped arrays of objects.
     * @param {Array.<Object>} myArray Items list.
     * @param {string} prop Name of property used for grouping.
     */
    var groupData = function (myArray, prop) {
      /***@type {Object.<Array.<Object>>}*/
      var grouped = {};
      /**@type {string}*/
      var letter;
      for (/**@type{number}*/var i = 0; i < myArray.length; i++) {
        letter = myArray[i][prop];
        // if other doesn't already have a property for the current letter
        // create it and assign it to a new empty array
        if (!(letter in grouped))
          grouped[letter] = [];
        grouped[letter].push(myArray[i]);
      }
      return grouped;
    };

    /**
     * Formatting function for row details.
     * @param {Element} nTr DataTable row.
     * @return {string|undefined} html string.
     */
    var fnFormatDetails = function (nTr) {
      var sOut;
      var aData = oTable.fnGetData(nTr);
      if (!aData) {
        return sOut;
      }
      if ('INSIGHT_COMPLETED' === aData['status-val']) {
        sOut = '<h5>Reports</h5>' + "<ul class='unstyled'>" +
          drawerMenuSegments(self_['options']['drawerMenu'],
            aData['id_value'], aData['propensitiesExist']) +
          '</ul>';
      } else if ('INSIGHT_FAILED_TO_INITIATE_ENHANCEMENT' === aData['status-val'] ||
        'INSIGHT_FAILED_DURING_ENHANCEMENT' === aData['status-val'] ||
        'INSIGHT_FAILED_DURING_VERIFICATION' === aData['status-val'] ||
        'INSIGHT_HELD' === aData['status-val'] ||
        'INSIGHT_FAILED_NO_MATCHES' === aData['status-val'] ||
        'INSIGHT_FAILED_DURING_AGGREGATION' === aData['status-val']) {
        sOut = '<h5>Analytic Dataset Failed.</h5>';
      } else {
        sOut = '<h5>This Analytic Dataset has not been completely ' +
          ' processed. Please try again when it is completed.</h5>';
      }
      return sOut;
    };

    /**
     * Draws drawer menu for table
     * @param {Object} drawerMenuOpt options for drawer menu.
     * @param {number} idCol Id parameter.
     * @param {Object} visibleImage
     * @return {string} Htmlstring.
     */
    var drawerMenuSegments = function (drawerMenuOpt, idCol, visibleImage) {
      /**@type{string}*/
      var htmlresult = '';
      for (var i = 0; i < drawerMenuOpt.length; i++) {
        if (!visibleImage && drawerMenuOpt[i]['title'] == 'Propensities') {
          htmlresult += '';
        }
        else {
          htmlresult += "<li><a href='" +
            drawerMenuOpt[i]['href'].replace('unique_param', idCol) +
            "' title=" + drawerMenuOpt[i]['title'] + "><img src='" +
            drawerMenuOpt[i]['img_srv'] + "'></a></li>";
        }
      }
      return htmlresult;
    };

    /**
     * @type {Object | null} dataTable column with uncheck functionality
     */
    var uncheckColumn = null;
    var checkColumn = null;

    /**
     * @type {Node|Element}
     * @private
     */
    var table_ = null;

    /**
     * The reference to dataTable options
     * @type {Object}
     * @private
     */
    var componentOptions = {};

    /**
     * The reference to current class instance. Used in private methods.
     * @type {Object}
     * @private
     */
    var data_ = { 'sIds': [], 'sObjects': [], 'info': {} };

    /**
     * The reference to current class instance. Used in private methods.
     * @type {DataTableComponent}
     * @private
     */
    var self_ = this;

    /**
     * dataTables configurations object.
     * @type {DataMapper}
     * @private
     */
    var dataMapper = new A.DataMapper(self_);

    /**
     * Storage for configuration data.
     * @type {Object|undefined}
     * @private
     */
    var config_ = null;

    /**
     * dataTables object.
     * @type {DataTable}
     * @private
     */
    var oTable = null;
    /**
     * @type {boolean}
     * @private
     */
    var bDefaultSearch = false;
    /**
     * @type {DataStorage}
     * @private
     */
    var storage_ = new DataStorage;
  }

  A.DataTableComponent = DataTableComponent;
}, '0.0.1', {
  requires: ['datatable-configs']
});