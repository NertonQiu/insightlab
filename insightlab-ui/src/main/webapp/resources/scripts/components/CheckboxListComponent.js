/**
 * CheckboxList component.
 *
 * @module checkbox-list-component
 **/
ACX.add('checkbox-list-component', function (A) {
  /**
   * @constructor
   * @param {jQuery} $container Container for component.
   * @extends {BaseWidget}
   */
  function CheckboxListComponent($container) {
    BaseWidget.apply(this, arguments);
    /**
     * @param {Object} config Configuration object.
     * @protected
     */
    this.initWidget = function (config) {
      widget.JSON_URI = config['json-url'];
      widget.WIDGET_NAME = config['name'];
      config_ = config;
      var $viewlink = $('<a/>', { text: config_['title'] })
        .addClass(config_['cssClass']).appendTo($container);
      $viewlink.on('click', function () {
        if (isOpen) {
          $listOfTargets.hide();
          isOpen = false;
          $(this).removeClass('active-state');
        } else {
          if (!isLoad) widget.handleEvent();
          $listOfTargets.show();
          isOpen = true;
          $(this).addClass('active-state');
        }
      });
      $container.on('change', 'input', function () {
        selectedTarget(data.dataList, this.value, this.checked);
        widget.dispatchEvent(new WidgetEvent(widget.WIDGET_NAME + '.changed', widget));
      });
    };

    /**@type {boolean}*/var isOpen = false;
    /**@type {boolean}*/var isLoad = false;

    /**
     * @return {boolean}
     */
    this.isOpen = function () {
      return isOpen;
    };

    this.handleEvent = function () {
      widget.loadData(function (response) {
        data.dataList = [];
        data.selectedIds = [];
        data.selectedItems = [];
        if (response['error']) {
          response['rows'] = [];
        }
        widget.handleResult(response);
        isLoad = true;
      }, widget.getParams());
    };

    /**
     * @protected
     */
    this.clearData = function () {
      data['selectedIds'] = [];
      data['selectedItems'] = [];
      $listOfTargets.find('input:checked').prop('checked', false);
    };

    /**
     * @param {Object} item Adds new item to list.
     * @protected
     */
    this.addListItem = function (item) {
      $listOfTargets.find('span.empty-list').remove();
      addItem(item, -1);
      data.dataList.push(item);
    };

    /**
     * Handles data rendering
     * @param {Object} result
     */
    this.handleResult = function (result) {
      $listOfTargets = $listOfTargets || $('<div></div>')
        .addClass('checkbox-list').hide().appendTo($container);
      $listOfTargets.html('');
      if (!result['rows'].length) {
        $listOfTargets.html('<span class="empty-list">No data exists</span>');
      } else {
        data.dataList = result.rows;
        /** @type {number}*/var i = data.dataList.length;
        for (; 0 < i--;) {
          addItem(data.dataList[i], i);
        }
        if (data['selectedIds'].length > 4) {
          $container.find('input').not(':checked').prop('disabled', true);
        }
      }
    };

    /**
     * Adds item to checbox list.
     * @param {Object} item
     * @param {number} index
     */
    function addItem(item, index) {
      /**@type {jQuery}*/
      var $div = $('<div/>').prependTo($listOfTargets);
      /**@type {jQuery}*/
      var $label = $('<label><input type="checkbox" value=' +
        item['id'] + '><span>' + item['name'] +
        '</span></label>').appendTo($div);
      $label.find('input').prop('disabled', data['selectedIds'].length > 4);
      if (data['selectedIds'] && data['selectedIds'].indexOf(item['id']) > -1) {
        $label.find('input').prop('checked', true);
      }
      (function (row) {
        $label.hover(function () {
          $tooltip.appendTo('body');
          $tooltip.html('<p>Percent: ' +
            setFormattedValue_('percents', row['percent']) + '</p><p>Count: ' + setFormattedValue_('number', row['count']) + '</p>');
          var offset = $(this).offset();
          var width = $tooltip.outerWidth(true) + 20;
          $tooltip.css({top: offset.top - 10, left: offset.left - width, 'display': 'block'});
        }, function () {
          $tooltip.detach();
        });
      })(item);
      if (config_['colored']) {
        var len = colors.length;
        item['color'] = colors[len - (data.dataList.length - index)];
        $('<div style="background-color: ' + item['color'] + '" class="tar-mark"></div><div class="clr"></div>').appendTo($div);
      }
    }

    this.getParams = function () {
    };

    /**
     * Returns data
     * @return {Object}
     */
    this.getData = function () {
      return data;
    };

    /** @type {Array.<string>} */
    var colors = [
      '#4A89DC', '#A0D468', '#D770AD', '#967ADC', '#8CC152',
      '#5D9CEC', '#F6BB42', '#DA4453', '#656D78', '#d4b275',
      '#AC92EC', '#4FC1E9', '#48CFAD', '#FC6E51', '#a17477',
      '#EC87C0', '#69b2bb', '#2498c3', '#FFCE54', '#ED5565'
    ];

    /**
     * Select target
     * @param {Array.<Object>} result
     * @param {string} id
     * @param {boolean} checked
     */
    var selectedTarget = function (result, id, checked) {
      result = $.grep(result, function (key) {
        return key['id'] == id;
      });
      if (result.length) data['selectedItem'] = result[0];
      if (checked && result.length) {
        data['selectedItems'].push(result[0]);
        data['selectedIds'].push(result[0]['id']);
        data['selectedItem']['selected'] = true;
        if (data['selectedIds'].length > 4) {
          $container.find('input').not(':checked').prop('disabled', true);
        }
      } else {
        data['selectedItem']['selected'] = false;
        data['selectedItems'] = $.grep(data['selectedItems'], function (key) {
          return key['id'] != id;
        });
        data['selectedIds'] = $.grep(data['selectedIds'], function (key) {
          return key != id;
        });
        if (data['selectedIds'].length < 5) {
          $container.find('input:disabled').prop('disabled', false);
        }
      }
    };

    /** @type {jQuery}*/
    var $listOfTargets;

    /** @type {jQuery}*/
    var $tooltip = $('<div class="my-tooltip right-arrow"></div>');

    var data = {
      'selectedIds': [],
      'selectedItems': [],
      'dataList': []
    };
    var config_;
    var widget = this;
  }

  A.CheckboxListComponent = CheckboxListComponent;
});