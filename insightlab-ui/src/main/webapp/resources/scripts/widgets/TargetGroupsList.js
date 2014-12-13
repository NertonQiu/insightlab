/**
 * Targets list widget.
 *
 * @module targets-list
 **/
ACX.add('targets-list', function (A) {
  /**
   * @constructor
   * @extends {BaseWidget}
   * @param {Node|Element|string|jQuery} container Widget container or its ID.
   * */
  function TargetGroupsList(container) {
    BaseWidget.apply(this, arguments);

    /**@type{InputTagComponent}*/ var inputComponent_;
    /**@type{ButtonComponent}*/ var saveButton_;
    /**@type{CheckboxListComponent}*/ var viewTarget_;
    /**@type{ProgressBarComponent}*/var progressBar_;


    this.initWidget = function (config_) {
      widget_.WIDGET_NAME = config_['name'];
      inputComponent_ = new A.InputTagComponent($('<div/>').appendTo(container));
      inputComponent_.initWidget(config_['input']);
      progressBar_ = new A.ProgressBarComponent($('<div/>').appendTo(container));
      progressBar_.initWidget(config_['selected-audience']);
      saveButton_ = new A.ButtonComponent($('<div/>').appendTo(container));
      saveButton_.initWidget(config_['save-button']);
      widget_.addEventListener(saveButton_.WIDGET_NAME + '.clicked', saveTarget);
      widget_.addEventListener(inputComponent_.WIDGET_NAME + '.input-changed', handleSaveButton);
      viewTarget_ = new A.CheckboxListComponent($('<div/>').appendTo(container));
      viewTarget_.initWidget(config_['targets-list']);
      widget_.addEventListener(viewTarget_.WIDGET_NAME + '.changed', function () {
        widget_.eventName = viewTarget_.WIDGET_NAME;
        widget_.dispatchEvent(new WidgetEvent(widget_.WIDGET_NAME + '.changed', widget_));
      });

      viewTarget_.getParams = function () {
        var filters = context['current-filter'];
        var portraitData = filters['portraitType']['val'];
        var targettype = portraitData['targettype'];
        var referencetype = portraitData['referencetype'];
        var insightid = 'survey-national' === portraitData['type'] ? filters['reportFilters']['responseID']['val'] : context['current-filter']['insights']['val'];
        var referenceid = filters['reportFilters'] ? filters['reportFilters']['responseID']['val'] : 0;
        return '?targetid=' + insightid + '&referenceid=' + referenceid +
          '&referencetype=' + referencetype + '&targettype=' + targettype;
      };
    };

    this.handleEvent = function (data) {
      context['current-filter'] = data;
      viewTarget_.handleEvent();
      inputComponent_.setData('');
    };

    this.handleTableChange = function (evt) {
      if (evt) {
        var data = evt['widget'].getData();
        data_['selected-rows'] = data['sIds'];
        handleSaveButton();
        progressHandler(data);
      }
    };

    this.getData = function () {
      data_[inputComponent_.WIDGET_NAME] = inputComponent_.getData();
      data_[viewTarget_.WIDGET_NAME] = viewTarget_.getData();
      return data_;
    };

    this.resetData = function () {
      inputComponent_.setData('');
      viewTarget_.clearData();
      data_['selected-rows'] = [];
    };

    function handleSaveButton() {
      var inputData = inputComponent_.getData();
      var isActive = inputData['valid'] && data_['selected-rows'] && data_['selected-rows'].length > 0;
      saveButton_.getElement().prop('disabled', !isActive);
    }

    /**
     * Handle progress bar
     * @param {data} data Contains contextual information about the event.
     * @this {ProgressBarComponent}
     * */
    function progressHandler(data) {
      var value = 0;
      var length = data['sObjects'].length;
      for (/**@type{number}*/var index = 0; index < length; index++) {
        value += data['sObjects'][index]['basePercent'];
      }
      progressBar_.setData(value);
    }


    /**
     * handle event for save target
     */
    var saveTarget = function () {
      var len = viewTarget_.getData()['dataList'].length;
      if (len > 19) {
        ACXM.Dialog.show({
          'id': 'feedbackMessage',
          'type': 'error',
          'title': '',
          'message': 'You have reached the limit of 20 saved Target Groups. You will need to delete any unused Target Groups before saving a new one.'
        });
        return;
      }
      var targetData = widget_.getData();
      if (!inputComponent_.validate()) {
        data_['selected-rows'].length ? targetData['target-name']['valid'] = true : targetData['target-name']['valid'] = false;
        return;
      }
      var filters = context['current-filter'];
      var portraitData = filters['portraitType']['val'];

      var referenceid = filters['reportFilters'] ? filters['reportFilters']['responseID']['val'] : 0;
      var insightid = 'survey-national' === portraitData['type'] ? referenceid : filters['insights']['val'];

      var params = 'segment=' + data_['selected-rows'].join('&segment=') + '&name=' +
        encodeURI(targetData['target-name']['text']) + '&targetid=' + insightid + '&referenceid=' + referenceid +
        '&targettype=' + portraitData['targettype'] +
        '&referencetype=' + portraitData['referencetype'];
      widget_.postData('/reportingService/savePersonicxTargetGroup', params, function (feedbackMessage) {
        if (feedbackMessage) {
          var errorText = '';
          var successText = '';
          if (feedbackMessage.hasOwnProperty('error')) {
            if (feedbackMessage['error'].hasOwnProperty('message')) {
              errorText = feedbackMessage['error']['message'];
            } else {
              errorText = feedbackMessage['error'];
            }
          } else {
            successText = 'Target Group saved';
            inputComponent_.setData('');
            data_['selected-rows'] = [];
            viewTarget_.addListItem(feedbackMessage);
            data_['filter'] = 'save-target';
            widget_.eventName = saveButton_.WIDGET_NAME;
            widget_.dispatchEvent(new WidgetEvent(widget_.WIDGET_NAME + '.changed', widget_));
          }
          ACXM.Dialog.show({
            'id': 'feedbackMessage',
            'type': errorText ? 'error' : 'success',
            'title': errorText || successText + (targetData['target-name']['trim'] ? '.<br />' + targetData['target-name']['trim-text'] : ''),
            'message': ''
          });
        }
      });
    };

    var context = { };
    var data_ = {
      'selected-rows': []
    };
    var widget_ = this;
  }

  A.TargetGroupsList = TargetGroupsList;
}, '0.0.1', {requires: ['input-component',
  'slider-component',
  'checkbox-list-component',
  'progressbar-component']});
