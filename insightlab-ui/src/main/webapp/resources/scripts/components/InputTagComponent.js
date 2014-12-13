;/**
 * Input field component.
 *
 * @module input-component
 **/
ACX.add('input-component', function(A) {
  /**
   * @constructor
   * @extends {BaseComponent}
   * @param {Node|Element|string|jQuery} container Widget container or its ID.
   */
  function InputTagComponent(container) {
    BaseComponent.apply(this, arguments);

    /**
     * Initialize {InputTagComponent}
     * @param {Object} config
     * @protected
     * @this {InputTagComponent}
     */
    this.initWidget = function(config) {
      config_ = config;
      config_['type'] = config_['type'] || 'text-field';
      self_.WIDGET_NAME = config_['name'];
      //update default validator
      if (config_['validator']) {
        for (/** @type {string} */var prop in config_['validator']) {
          validator[config_['type']][prop] = config_['validator'][prop];
        }
      }
      self_.draw();
    };

    /**
     * Draws the input type text.
     * @this {InputTagComponent}
     */
    this.draw = function() {
      $input = $('<input/>', { type: 'text' }).addClass('span12 acxiom-input');
      if (config_['cssClass']) {
        $input.addClass(config_['cssClass']);
      }
      if (config_['default-value']) {
        $input.val(config_['default-value']);
        data_['text'] = config_['default-value'];
      }
      if (validator[config_['type']]['tooltip']) {
        /**@type{jQuery}*/
        var $tooltip = $('<div/>').addClass('acxiom-tooltip');
        $tooltip.css('margin-top', validator[config_['type']]['marginTop']);
        $tooltip.text(validator[config_['type']]['tooltip']);
      }
      $input.off('keyup');
      $input.on('keyup', function(e) {
        var text = e.target.value;
        data_['valid'] = validateLength(e.target.value);
        var tmp = (e.target.value).replace(/\s{2,}/g, ' ');
        tmp = tmp.replace(/^[ \s]+|[ \s]+$/g, '');
        if (tmp === text) {
          data_['trim'] = false;
          data_['trim-text'] = '';
        } else {
          data_['trim'] = true;
          data_['trim-text'] = validator['text-field']['trim-message'];
        }
        data_['text'] = tmp;
        if (data_['date-text']) data_['date-text'] = null;
        var key = e.keyCode;
        if (key < 9 || key > 45 || key == 32) {
          self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME +
            '.input-changed', self_));
        }
        else if (e.keyCode == 27) {
          self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME +
            '.input-cancel', self_));
        }
      });

      if ($tooltip) {
        var isFocus;
        $input.off('mouseover');
        $input.on('mouseover', function(evt) {
          if (!isFocus)$tooltip.hide();
        });

        $input.off('focus');
        $input.on('focus', function(evt) {
          isFocus = true;
          $tooltip.show();
        });
      }

      $input.off('focusout');
      $input.on('focusout', function(evt) {
        if ($tooltip) $tooltip.hide();
        if (evt.originalEvent) {
          if (config_['check-length'] && data_['text']) checkValidLength(data_['text']);
          self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME +
            '.input-focusout', self_));
        }
      });
      $input.off('keypress');
      $input.on('keypress', function(evt) {
        switch (evt.keyCode) {
          case 8:
          case 37:
          case 46:
          case 39:
            break;
          case 13:
            if (config_['check-length'] && data_['text']) checkValidLength(data_['text']);
            evt.preventDefault();
            self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME +
              '.input-focusout', self_));
            break;
          default:
            if (data_['text'].length >= 80) {
              evt.preventDefault();
            }
            break;
        }
      });

      $input.appendTo($container);
      if (config_['default-focus']) {
        $input.focus();
      }
      if (validator[config_['type']]['tooltip'] && $tooltip) {
        $tooltip.appendTo($container);
      }
      if (config_['label']) {
        $('<label class="label-field">' + config_['label'] + '</label>').insertBefore($input);
      }
      if (config_['placeholder']) {
        $input.attr('placeholder', config_['placeholder']);
        if (/MSIE (\d+\.\d+);/.test(navigator.userAgent)) {
          var ieversion = new Number(RegExp.$1);
          if (ieversion == 8 || ieversion == 9) {
            $input.removeAttr('placeholder').wrap('<div class="wrap-input"></div>').parent().append('<span class="holder">' + config_['placeholder'] + '</span>');
            var $hold = $input.parent().find('.holder');
            if ($input.val() !== '') $hold.hide();
            $hold.off('click').on('click', function() {
              $input.trigger('focus');
            });
            $input.focus(function() {
              $hold.hide();
            });
            $input.focusout(function() {
              if ($input.val() === '') $hold.show();
            });
          }
        }
      }

      data_['input-type'] = config_['type'];

      if (config_['type'] === 'date-field') {
        $input.calendar({ onClick: function(el, cell, date, data) {
          self_.setData(date, true);
          self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME +
            '.input-changed', self_));
        }, onShow: function(calendar) {
          calendar.show();
          $('.gldp-default:visible').addClass('vis');
          $(window).trigger('resize');
        },
          dowOffset: 0
        });
      }
    };

    this.validate = function() {
      if (!checkInput()) {
        data_['valid'] = false;
        if (config_['type'] == 'number-field' && parseInt(data_['text']) < 0 && data_['text'].search(/\s/g) === -1) {
          ACXM.Dialog.show({ 'id': 'validationError', 'type': 'error',
            'title': '',
            'message': 'Enter valid number range'
          });
        }
        else {
          ACXM.Dialog.show({ 'id': 'validationError', 'type': 'error',
            'title': '',
            'message': validator[config_['type']]['message']
          });
        }

        if (config_['default-focus']) {
          $('.dialogueBox .xbtn').click(function() {
            $input.focus();
          });
        }

        return false;
      } else {
        data_['valid'] = true;
        return true;
      }
    };

    /**
     * @param {string|Date} value input text.
     * @param {boolean|undefined} isDate input text.
     * @param {boolean|undefined} fromUtc formatting to utc date.
     * @protected
     * @this {InputTagComponent}
     */
    this.setData = function(value, isDate, fromUtc) {
      data_['date-text'] = null;
      if ('date-field' === config_['type'] && config_['date-settings'] && value) {
        if (isDate) {
          data_['date-text'] = value;
          value = value.format(config_['date-settings']['dateFormat']);
        } else {
          value = new Date(value).format(config_['date-settings']['dateFormat']);
        }
      }
      data_['text'] = value;
      data_['valid'] = validateLength(value);
      $input.val(value);

    };

    /**
     * Gets data of input component
     * @protected
     * @this {InputTagComponent}
     * @return {Object}
     */
    this.getData = function() {
      return data_;
    };

    /**
     * @return {jQuery} Returns jQuery input object.
     * @protected
     * @this {InputTagComponent}
     */
    this.getElement = function() {
      return $input;
    };

    /**
     * @param {string|Date} value input text.
     * @param {boolean|undefined} fromUtc formatting to utc date.
     * @return {string|Date} value formatted date.
     */
    this.formatToUtc = function(value, fromUtc) {
      var lower, upper, start, end, lastDate;
      var utcFormatted = config_['date-settings']['utcOffset'] * 60 * 60 * 1000;
      if (config_['characteristic'] && value) {
        if ('start-date' === config_['characteristic']) {
          if (!fromUtc) {
            lower = new Date(value);
            lower.setHours(0);
            start = new Date(lower.getTime() - utcFormatted);
            return start.format('yyyy-MM-dd HH:mm:ss');
          } else {
            lower = parseDate(value);
            start = new Date(lower.getTime() + utcFormatted);
            lastDate = start.format(config_['date-settings']['dateFormat']);
            $input.val(lastDate);
            data_['text'] = lastDate;
            return lastDate;
          }
        } else if ('end-date' === config_['characteristic']) {
          if (!fromUtc) {
            upper = new Date(value);
            upper.setHours(24);
            end = new Date(upper.getTime() - utcFormatted);
            return end.format('yyyy-MM-dd HH:mm:ss');
          } else {
            upper = parseDate(value);
            end = new Date(upper.getTime() + (utcFormatted - 1));
            lastDate = end.format(config_['date-settings']['dateFormat']);
            $input.val(lastDate);
            data_['text'] = lastDate;
            return lastDate;
          }
        }
      } else {
        return value;
      }
    };
    /**
     * @param {string} inputDate server-side param date.
     * @return {Date} value date.
     */
    var parseDate = function(inputDate) {
      var date = inputDate.substring(-inputDate.indexOf(' '));
      var time = inputDate.substring(inputDate.indexOf(' ') + 1);
      var dateTimeArray = (date.replace(/-/g, '/') + ' ').concat(time);
      var returnValue = new Date(Date.parse(dateTimeArray));
      return new Date(new Date(returnValue.setMinutes(0)).setSeconds(0));
    };

    /**
     * Verify if input string is valid
     * @param {string} text
     * @return {boolean} Returns is valid text.
     */
    var validateLength = function(text) {
      if (text) {
        /**@type{string}*/
        var clearText =
          (text).replace(validator[config_['type']]['regExp'], '');
        clearText = clearText.replace(/\s{2,}/g, ' ');
        return (clearText.length >= validator[config_['type']]['minlength'] &&
          clearText.length <= validator[config_['type']]['maxlength']);
      }
    };

    /**
     * Verify if text of input has valid length
     * @param {string} text
     */
    var checkValidLength = function(text) {
      text = text.replace(/\s{2,}/g, ' ');
      if (!validateLength(text)) {
        ACXM.Dialog.show({ 'id': 'validationError', 'type': 'error',
          'title': '',
          'message': '模型名称>3个字,  < 20个字。'
        });
      }
    };

    /**
     * The reference to container. Used in private methods.
     * @type {jQuery} jQuery object of container tag.
     * @private
     */
    var $container = $(this.getContainer());

    /**
     * The reference to current input. Used in private methods.
     * @type {jQuery} jQuery input object
     * @private
     */
    var $input;

    /**
     * The validation properties of component.
     * @type {Object}
     * @private
     */
    var validator = {
      'number-field': {
        minlength: 1,
        maxlength: 80,
        /** @type {!RegExp} */
        regExp: /\s/g,
        marginTop: '-80px',
        validName: /^\s*\d+\s*$/,
        validEndName: /((\s_)|(\s)|(_)|(-))$/,
        message: 'Enter valid record range'
      },
      'text-field': {
        minlength: 3,
        maxlength: 20,
        /** @type {!RegExp} */
        regExp: /^[ \s]+|[ \s]+$/g,
        tooltip: '模型名称>3个字,  < 20个字。',
        'trim-message': 'Audience Portrait will trim all white space' +
          ' except internal, single spaces.',
        marginTop: '-80px',
        validName: /^(([A-Za-z0-9\s])(_?)(-?)){3,80}$/,
        validEndName: /((\s_)|(\s)|(_)|(-))$/,
        message: 'We do not support the following characters:<br />' +
          ' ~, !, @, #, $, %, ^, &, *, {, }, (, ), [, ], <, >, ?, /, |,' +
          ' \\, ‘, “, +, :, ;, `, =, ., ,.' +
          '<br /><br />Text cannot start or end with hyphen or underline. '
      },
      'date-field': {
        minlength: 4,
        maxlength: 20,
        /** @type {!RegExp} */
        regExp: /\s/g,
        marginTop: '-80px',
        validName: /^(19|20)\d\d[-]([1-9]|0[1-9]|1[012])[-]([1-9]|0[1-9]|[12][0-9]|3[01])$/,
        //validName: /^([0-9]{4})-([1-9]|0[1-9]|1[012])-([[1-9]|0[1-9]|[12][0-9]|3[01])\s([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$/,
        validEndName: /((\s_)|(\s)|(_)|(-))$/,
        message: 'Enter valid date range'
      }
    };

    var checkInput = function() {
      /**@type {boolean}*/var isValid = true;
      if ('date-field' === config_['type']) {
        var input = data_['text'] || data_['date-text'],
          format = config_['date-settings'].dateFormat,
          validDate,
          patterns = {
            'MM/dd/yyyy': /^((0[1-9])|(1[012]))\/((0[1-9])|([12]\d)|3[01])\/([0-9]{4})$/,
            'yyyy/MM/dd': /^([0-9]{4})\/((0[1-9])|(1[012]))\/((0[1-9])|([12]\d)|3[01])$/,
            'yyyy-MM-dd': /^([0-9]{4})-((0[1-9])|(1[012]))-((0[1-9])|([12]\d)|3[01])$/,
            'MM-dd-yyyy': /^((0[1-9])|(1[012]))-((0[1-9])|([12]\d)|3[01])-([0-9]{4})$/,
            'dd MMM yyyy': /^(((0[1-9])|([12]\d)|(3[01]))\s(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct'|Nov|Dec)\s\d{4})$/,
            'MMM dd, yyyy': /^((Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct'|Nov|Dec)\s((0[1-9])|([12]\d)|(3[01])),?\s\d{4})$/,
            'ddd, MMM dd yyyy': /^((Sun|Mon|Tue|Wed|Thu|Fri|Sat)(,?)\s(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct'|Nov|Dec)\s((0[1-9])|([12]\d)|(3[01]))\s\d{4})$/
      };
        if ('dd-MM-yyyy' === format) {
          var pattern = /^([0-9]{2})-([0-9]{2})-([0-9]{4})$/;
          var formatting = formatDate(pattern, '-');
          isValid = formatting['validDate'];
          input = formatting['input'];
        } else if ('dd/MM/yyyy' === format) {
          pattern = /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
          formatting = formatDate(pattern, '/');
          isValid = formatting['validDate'];
          input = formatting['input'];
        } else {
          pattern = patterns[format];
          isValid = pattern.test(input);
        }
      } else {

        if (!validator[config_['type']]['validName'].test(data_['text'])) {
          isValid = false;
        }
        if (validator[config_['type']]['validEndName'].test(data_['text'])) {
          isValid = false;
        }
      }
      return isValid;

    };

    var formatDate = function(pattern, separator) {
      var input = $input.val(),
        validDate = pattern.test(input),
        arr = input.split(separator);
      input = arr[1] + '/' + arr[0] + '/' + arr[2];
      data_['text'] = input;
      data_['date-text'] = input;
      return {'validDate': validDate, 'input': input};
    };
    /**
     * The configuration of component.
     * @type {Object|undefined}
     * @private
     */
    var config_ = null;

    /**
     * Storage for settings data.
     * @type {Object}
     * @private
     */
    var data_ = { 'valid': false, 'text': '' };

    /**
     * The reference to current class instance. Used in private methods.
     * @type {InputTagComponent}
     * @private
     */
    var self_ = this;
  }
  A.InputTagComponent = InputTagComponent;
});
