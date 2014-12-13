/**
 * Checkbox component.
 *
 * @module checkbox-component
 **/
ACX.add('checkbox-component', function (A) {
/**
 * @constructor
 * @extends {BaseComponent}
 * @param {Node|Element|string} container Widget container or its ID.
 */
function CheckboxComponent(container) {
    BaseComponent.apply(this, arguments);

    /**
     * Initialize {CheckboxComponent}
     * @param {Object} config Configuration object.
     * @protected
     * @this {CheckboxComponent}
     */
    this.initWidget = function(config) {
        config_ = config;
        self_.WIDGET_NAME = config_['name'];
        self_.draw();
    };

    /**
     * Draws the checkbox.
     * @this {CheckboxComponent}
     */
    this.draw = function() {
        /**@type {jQuery}*/
        var $checkBoxLabel = $('<label/>').attr('title', config_['title']).addClass('checkbox');

        $checkBoxInput = $('<input/>', { type: 'checkbox' }).appendTo($checkBoxLabel);

        if (config_['default_value']) {
            $checkBoxInput.prop('checked', config_['default_value']);
        }

        $('<span/>').appendTo($checkBoxLabel).text(config_['title']);

        $checkBoxInput.on('change', function() {
            self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.selected'));
        });

        $checkBoxLabel.appendTo($container);
    };

    /**
     * Sets checkboxes value
     * @param {boolean} value
     * @protected
     * @this {CheckboxComponent}
     */
    this.setValue = function(value) {
        $checkBoxInput.prop('checked', value);
    };

    /**
     * Gets state of checkbox
     * @protected
     * @this {CheckboxComponent}
     * @return {boolean}
     */
    this.getData = function() {
        return $checkBoxInput.is(':checked');
    };

    /**
     * @return {jQuery} Returns jQuery input type checkbox object.
     * @protected
     * @this {CheckboxComponent}
     */
    this.getElement = function() {
        return $checkBoxInput;
    };

    /**
     * The reference to current checkbox. Used in private methods.
     * @type {jQuery}
     * @private
     */
    var $checkBoxInput;

    /**
     * The reference to container. Used in private methods.
     * @type {jQuery} jQuery object.
     * @private
     */
    var $container = $(this.getContainer());

    /**
     * The configuration of component.
     * @type {Object}
     * @private
     */
    var config_;

    /**
     * The reference to current class instance. Used in private methods.
     * @type {CheckboxComponent}
     * @private
     */
    var self_ = this;
}

  A.CheckboxComponent = CheckboxComponent;
});