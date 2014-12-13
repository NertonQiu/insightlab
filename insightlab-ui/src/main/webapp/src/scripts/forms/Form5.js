
/**
 * @fileoverview Emulates HTML5 forms behavior for non-HTML5 browsers.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * Emulates HTML5 placeholders and validations functionality.
 * Usage: <code>Form5.getInstance().apply(container)</code>
 * @constructor
 */
function Form5() {

  /**
   * @type {Array}
   */
  var TAGS = ['INPUT', 'TEXTAREA'];

  /**
   * Applyes HTML5 placeholders and validations functionality to all form's
   * fields in specified <code>container</code>.
   * @param {Node|Element} container The HTML container.
   * @this {Form5}
   */
  this.apply = function(container) {
    this.setPlaceholders(container);
    this.setValidations(container);
  };

  /**
   * Applyes HTML5 placeholders functionality to all form's
   * fields in specified <code>container</code>.
   * @param {Node|Element} container The HTML container.
   */
  this.setPlaceholders = function(container) {
    PlaceHolder['getInstance']().apply(container);
  };

  // Export for closure compiler.
  this['setPlaceholders'] = this.setPlaceholders;

  /**
   * Applyes HTML5 validations functionality to all form's
   * fields in specified <code>container</code>.
   * @param {Node|Element} container The HTML container.
   */
  this.setValidations = function(container) {
    validator_.apply(container);
  };

  // Export for closure compiler.
  this['setValidations'] = this.setValidations;

  /**
   * @private
   */
  var validator_ = new (/** @constructor */ function() {
    this.apply = function(container) {
      if (!isSupported_)
        for (/** @type {number} */ var i = 0; i < TAGS.length;)
          setValidation_(container.getElementsByTagName(TAGS[i++]));
    };

    /**
     * @param {Array|NodeList} nodes List of HTML Form elements.
     * @private
     */
    function setValidation_(nodes) {
      for (/** @type {number} */ var i = 0; i < nodes.length;) {
        /** @type {Node|Element} */
        var node = nodes[i++];
        initFieldValidation_(node);
        initFormValidation_(node.form);
      }
    }

    /**
     * @param {Node|Element} node HTML input or textarea element.
     * @private
     */
    function initFieldValidation_(node) {
      /**
       * A string containing the standard or custom error message that would
       * be displayed if the user submitted at this time.
       * @type {string}
       * @link http://msdn.microsoft.com/en-us/library/ie/hh772950
       */
      node['validationMessage'] = '';

      /**
       * Sets custom validity message.
       * @param {string} message String containing a custom message.
       * @link http://msdn.microsoft.com/en-us/library/ie/hh772949
       */
      node.setCustomValidity = function(message) {
        node['validationMessage'] = message;
      };

      /**
       * @return {boolean} Returns true if the form passes validation,
       *     false otherwise.
       * @link http://msdn.microsoft.com/en-us/library/ie/hh772948
       */
      node.checkValidity = function() {
        if (node.value == node.getAttribute('placeholder')) {
          node.value = '';
        }

        if (node.hasAttribute('required') && !node.value) {
          node['validationMessage'] = 'Please fill out this field';
          return false;
        }

        if (node.getAttribute('type') == 'email' && node.value) {
          /** @type {RegExp} */
          var re = /^[\w\.\-]+@[\w\.\-]+\.[a-zA-Z]{2,4}$/g;
          if (!re.test(node.value)) {
            node['validationMessage'] = 'Please enter a valid email address';
            return false;
          }
        }

        if (node.hasAttribute('pattern') && node.value) {
          var re = new RegExp(node.getAttribute('pattern'), 'g');
          if (!re.test(node.value)) {
            node['validationMessage'] = 'The pattern is mismatched';
            return false;
          }
        }

        node['validationMessage'] = '';
        return true;
      };

      if (node.hasAttribute('required')) {
        node.className += ' required';
      }
    }

    /**
     * @param {Node|Element} form The form element to init validation.
     */
    function initFormValidation_(form) {
      if (form) {
        if (!form.checkValidity) {
          /**
           * @link http://msdn.microsoft.com/en-us/library/ie/hh772948
           */
          form.checkValidity = function() {
            /** @type {Node|Element} */
            var invalid = null;

            /** @type {number} */
            var i = 0;

            for (; i < form.elements.length; i++) {
              /** @type {Node|Element} */
              var element = form.elements[i];

              if ('checkValidity' in element && !element.checkValidity()) {
                if (!invalid) invalid = element;
              }

              if (!element.tooltip) {
                element.tooltip = document.createElement('SPAN');
                element.tooltip.className = 'validation-tooltip';
                element.parentNode.insertBefore(element.tooltip, element);
                element.parentNode.insertBefore(element, element.tooltip);
              }
              element.tooltip.style.display = 'none';
            }

            if (invalid) {
              showValidationTooltip_(invalid);
              return false;
            }
            return true;
          };
        }

        if (!form.isAttached_) {
          form.attachEvent('onsubmit', function() {
            /** @type {boolean} */
            var valid = form.checkValidity && form.checkValidity();
            if (!valid) {
              event.cancelBubble = true;
              event.returnValue = false;
              return false;
            }
            return true;
          });
          form.isAttached_ = true;
        }
      }
    }

    /**
     * @param {Node|Element} element HTML element.
     */
    function showValidationTooltip_(element) {
      if (!arguments.callee.isAttached) {
        document.attachEvent('onmousedown', function() {
          if (invalidElement_ && invalidElement_.tooltip) {
            invalidElement_.tooltip.style.display = 'none';
          }
        });
        arguments.callee.isAttached = true;
      }
      element.tooltip.innerHTML = element['validationMessage'];
      element.tooltip.style.display = 'block';
      element.focus();
      invalidElement_ = element;
    }

    /** @type {boolean} */
    var isSupported_ = ('required' in document.createElement('INPUT'));

    /** @type {Node|Element} */
    var invalidElement_ = null;
  });

  /**
   * The reference to self class.
   * @type {Function}
   */
  var __class__ = arguments.callee;

  /**
   * @return {Form5} Returns reference to Form5 instance.
   */
  __class__['getInstance'] = function() {
    return __class__.instance_ || (__class__.instance_ = new __class__);
  };

  if (__class__.instance_) throw new Error('The instance already exists.');
}

// Singleton initialization.
(new Form5());

// Export for closure compiler.
window['Form5'] = Form5;

