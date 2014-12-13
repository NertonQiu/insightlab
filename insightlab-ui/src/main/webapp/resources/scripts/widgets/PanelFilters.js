/**
 * Panel filters.
 *
 * @module targets-list
 **/
ACX.add('panel-filters', function (A) {

  /**
   * @constructor
   * @extends {BaseWidget}
   * @param {Node|Element|string|jQuery} container Widget container or its ID.
   **/
  function PanelFilters(container) {
    BaseWidget.apply(this, arguments);

    /** @type {DropDownComponent}*/var dropDownComponent_;
    /** @type {SliderComponent}*/var sliderComponent_;
    /** @type {CheckboxComponent}*/var checkboxComponent_;
    /** @type {String}*/var authenticatedUrl = '/srv/authtoken/authenticated';

    /**
     * Initialize PanelFilters widget.
     * @param {Object || null || undefined} config Configurations of widget.
     */
    this.initWidget = function (config) {
      widget_.WIDGET_NAME = config['name'];
      sliderComponent_ = new A.SliderComponent($('<div/>').appendTo(container));
      sliderComponent_.setData(config['zscore']['json-data']);
      sliderComponent_.initWidget(config['zscore']);

      widget_.addEventListener(sliderComponent_.WIDGET_NAME + '.changed', function (evt) {
        var data = evt.widget.getData();
        widget_.eventName = sliderComponent_.WIDGET_NAME;
        widget_.dispatchEvent(new WidgetEvent(widget_.WIDGET_NAME + '.changed', widget_));
      });

      checkboxComponent_ = new A.CheckboxComponent($('<div/>').appendTo(container));
      var demoColumns = storage_.get('DemographicColumns') || [];
      data_.demographic = demoColumns;
      config['demographic']['default_value'] = (demoColumns.length != 0);
      checkboxComponent_.initWidget(config['demographic']);
      checkboxComponent_.handleEvent = columnChooser;
      checkboxComponent_.addEventListener(config['demographic']['name'] + '.selected', function (evt) {
        checkboxComponent_.handleEvent(evt);
      });

      dropDownComponent_ = new A.DropDownComponent($('<div/>').appendTo(container));
      dropDownComponent_.initWidget(config['segment']);
      var sc = dropDownComponent_.getData()['segment-characteristic'];
      if (sc) {
        config['default'] = sc.val;
      }
      dropDownComponent_.handleState = handleSegmentState;
      //listen table changes
      dropDownComponent_.addEventListener(config['handled-table'] + '.filtered', function (evt) {
        dropDownComponent_.handleState(evt);
      });
      widget_.addEventListener(dropDownComponent_.WIDGET_NAME + '.changed', function (evt) {
        widget_.eventName = dropDownComponent_.WIDGET_NAME;
        widget_.dispatchEvent(new WidgetEvent(widget_.WIDGET_NAME + '.changed', widget_));
      });
      container.append(document.getElementById((config['legend']['container-id'])));
    };

    this.getData = function () {
      data_[sliderComponent_.WIDGET_NAME] = sliderComponent_.getData();
      data_[checkboxComponent_.WIDGET_NAME] = checkboxComponent_.getData();
      data_[dropDownComponent_.WIDGET_NAME] = dropDownComponent_.getData();
      return data_;
    };


    //handle demographic column choose
    var columnChooser = function (evt) {
      var checkbox = this;
      var load = new DataLoader();
      if (checkbox.getData()) {
        load.loadData(authenticatedUrl, function (response) {
          $('#dialogChoose').appendTo('body');
          ACXM.Modal.toggle('dialogChoose', {
            modalwidth: 300
          });
          if (response && response['authenticated']) {
            /**@type {Element|Node}*/
            var demographic_body = $('#dialogChoose-body').children('div.span12')[0];
            if (demographic_body) {
              //body
              var $chooseBody = $(demographic_body);
              /**@type{Array.<string>}*/
              var columns = ['age', 'income', 'maritalStatus', 'homeOwnership', 'kids', 'urbanicity', 'netWorth'];
              /**@type{Array.<string>}*/
              var columnsTitle = ['Age', 'Income', 'Marital Status', 'Home Ownership', 'Kids', 'Urbanicity', 'Net Worth'];
              var savedColumns = data_.demographic || [];//storage_.get('DemographicColumns') || [];
              var length = columns.length;
              var columnsLabels = [];
              for (var i = 0; i < length; i++) {
                /**@type{string}*/var label = '<label>' +
                  '<input type="checkbox" value="' + columns[i] + '" name="group-a" checked="">' +
                  '<span>' + columnsTitle[i] + '</span>' +
                  '</label><br/>';
                columnsLabels.push(label);
              }
              $chooseBody.append($(columnsLabels.join(''))).wrapInner('<div class="check-list"></div> ');
              var checker = false;
              var buttonOk = document.querySelector('#dialogChoose .acxiom-button.primary');
              buttonOk.onclick = function (e) {
                savedColumns = [];
                $(demographic_body).find('input:checked').each(function () {
                  savedColumns.push($(this).val());
                });
                if (!savedColumns.length) {
                  checkbox.setValue(false);
                } else {
                  data_.demographic = savedColumns;
                  storage_.set('DemographicColumns', savedColumns);
                  widget_.eventName = checkbox.WIDGET_NAME;
                  widget_.dispatchEvent(new WidgetEvent(widget_.WIDGET_NAME + '.changed', widget_));
                  checker = true;
                }
                ACXM.Modal.toggle('dialogChoose');
                closeEvent(e);
              };
              var x_button = document.querySelector('#dialogChoose .acxiom-modal-close');
              x_button.onclick = function (e) {
                checkbox.setValue(false);
                closeEvent(e);
              };
              var button_Cancel = document.querySelector('#dialogChoose .acxiom-button.clear.acxiom-modal-close');
              button_Cancel.onclick = function (e) {
                checkbox.setValue(false);
                ACXM.Modal.toggle('dialogChoose');
                closeEvent(e);
              };
              function closeEvent(e) {
                e = e || window.event;
                e.preventDefault ? e.preventDefault() : e.returnValue = false;
                $('#dialogChoose-body div.span12').empty();
              }
            }
          }
        });
      } else {
        data_.demographic = [];
        storage_.remove('DemographicColumns');
        widget_.eventName = checkbox.WIDGET_NAME;
        widget_.dispatchEvent(new WidgetEvent(widget_.WIDGET_NAME + '.changed', widget_));
      }
    };

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

    /** @type {PanelFilters} */var widget_ = this;
    /** @type {DataStorage} */var storage_ = new DataStorage;
    var data_ = {};

  }

  A.PanelFilters = PanelFilters;
}, '0.0.1', {requires: ['slider-component',
  'checkbox-component']});
