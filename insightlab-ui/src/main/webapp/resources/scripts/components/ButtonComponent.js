/**
 * Button component.
 *
 * @module button-component
 **/
ACX.add('button-component', function (A) {
  /**
   * @constructor
   * @param {Node|Element|string|jQuery} container Widget container or its ID.
   * @extends {BaseComponent}
   */
  function ButtonComponent(container) {
    BaseComponent.apply(this, arguments);

    /**
     * Initialize {ButtonComponent}
     * @param {Object} config Configuration object.
     * @protected
     */
    this.initWidget = function (config) {
      config_ = config;
      self_.WIDGET_NAME = config_['name'];
      if (config_['parentCss']) {
        $container.addClass(config_['parentCss']);
      }
      self_.draw();
    };

    /**
     * Draws the button component.
     * @protected
     */
    this.draw = function () {
      $button = $('<button/>').addClass(config_['specificClass'] ? config_['specificClass'] : ('btn ' + (config_['cssClass'] || '')));

      $button.text(config_['title'] || '');
      $button.attr('type', config_['type'] || 'button');
      if (config_['id']) {
        $button.prop('id', config_['id']);
      }

      $button.prop('disabled', config_['disabled']);
      if (config_['hide']) {
        $button.hide();
      }
      $button.off('click').on('click', function () {
        self_.dispatchEvent(new WidgetEvent(self_.WIDGET_NAME + '.clicked', self_));
      });

      $button.appendTo($container);
    };

    /**
     * The reference to container.
     * @type {jQuery}
     * @private
     */
    var $container = $(this.getContainer());

    /**
     * The reference to current button.
     * @type {jQuery}
     * @private
     */
    var $button;

    /**
     * @this {ButtonComponent}
     */
    this.getData = function () {
    };

    /**
     * @return {jQuery} Returns jQuery object.
     * @protected
     * @this {ButtonComponent}
     */
    this.getElement = function () {
      return $button;
    };

    /**
     * @type {Object}
     * @private
     */
    var data_ = {};

    /**
     * The configuration of component.
     * @type {Object|undefined|null}
     * @private
     */
    var config_;

    /**
     * The reference to current class instance. Used in private methods.
     * @type {ButtonComponent}
     * @private
     */
    var self_ = this;
  }

  A.ButtonComponent = ButtonComponent;
});
