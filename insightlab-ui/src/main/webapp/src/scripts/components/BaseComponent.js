/**
 * @fileoverview BaseComponent is the foundation of all components.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * BaseComponent is the foundation of all components.
 * All components need to be derived from this BaseComponent class.
 *
 * @param {Node|Element|string} container Widget container or its ID.
 * @constructor
 * @extends {BaseWidget}
 */
function BaseComponent(container) {
  BaseWidget.apply(this, arguments);

  /**
   * Draws the component.
   * @protected
   */
  this.draw = function() {};

  /**
   * Sets component data.
   * @param {Array} data The component data.
   * @this {BaseComponent}
   */
  this.setData = function(data) {
    this['dataList'] = data;
  };

  /**
   * Sets component options.
   * @param {Object} options The component options.
   * @this {BaseComponent}
   */
  this.setOptions = function(options) {
    this['options'] = options;
  };

  /**
   * Storage for inputted data.
   * @type {Array|undefined}
   * @protected
   * TODO: rename to "data".
   */
  this['dataList'] = [];

  /**
   * Storage for component options.
   * @type {Object|undefined}
   * @protected
   */
  this['options'] = {};
}

// Export for closure compiler.
window['BaseComponent'] = BaseComponent;

