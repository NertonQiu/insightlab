;/**
 * Legend component for personicx page.
 *
 * @module legend-component
 **/
ACX.add('legend-component', function(A) {
  /**
   * @constructor
   * @param {Node|Element|string} container Widget container or its ID.
   * @extends BaseComponent
   */
  function LegendComponent(container) {
    BaseComponent.apply(this, arguments);

    /**
     * Initialize {LegendComponent}
     * @param {Object} config
     * @protected
     * @this {LegendComponent}
     */
    this.initWidget = function(config) {
      config_ = config;
      self_.WIDGET_NAME = config_['name'];
      /** @type {jQuery}*/
      var $legend = $('<div/>', { id: 'colored-list' }).appendTo($container);
      $ulList = ($('<ul></ul>').addClass('age-list unstyled')).appendTo($legend);
    };

    /**
     * Draws the legend component.
     * @this {LegendComponent}
     */
    this.draw = function() {
      $ulList.empty();
      if (data_) {
        $container.css('display', 'block');
        /** @type {Array.<Object>}*/ var categoryArray = mapData(data_);
        buildCategories(categoryArray);
      }
      else {
        $container.css('display', 'none');
      }
    };

    /**
     * Sets data for legend
     * @param {Object} legendData
     * @protected
     * @this {LegendComponent}
     */
    this.setData = function(legendData) {
      data_ = legendData;
    };

    /**
     * Gets current data
     * @protected
     * @this {LegendComponent}
     * @return {Object}
     */
    this.getData = function() {
      return data_;
    };

    /**
     * mapping object to specific array of object
     * @param {Object} categories
     * @private
     * @return {Array.<Object>} Specific array of objects.
     */
    var mapData = function(categories) {
      /** type {Array}*/var mapArray = [];
      for (/** type {string}*/ var c in categories) {
        mapArray.push({ 'name': c, 'colour': categories[c] });
      }
      mapArray.sort(function(a, b) {
        if (a.name > b.name) return 1;
        if (a.name < b.name) return -1;
        return 0;
      });
      return mapArray;
    };

    /**
     * build legend according to data
     * @param {Array.<Object>} array
     * @private
     */
    var buildCategories = function(array) {
      /** @type {number}*/ var index = 0;
      /** @type {number}*/ var len = array.length;
      /** @type {DocumentFragment}*/
      var frag = document.createDocumentFragment();
      for (; index < len; index++) {
        /**@type {jQuery}*/
        var $colorLi = ($('<li></li>').css('clear', 'both')).appendTo(frag);
        /** TODO create style for legends elements in less*/
        $('<div></div>').css({ 'float': 'left', 'margin-right': '5px', 'width': '15px', 'height': '15px', 'background-color': array[index].colour }).appendTo($colorLi);
        $('<div></div>').css({ 'float': 'left', 'line-height': '18px' }).text(decodeTag(array[index].name)).appendTo($colorLi);
      }
      $ulList.append(frag);
    };

    /**
     * The reference to container.
     * @type {jQuery} Jquery object
     * @private
     */
    var $container = $(this.getContainer());

    var config_;

    var $ulList;

    var self_ = this;

    var data_ = {};
  }

  A.LegendComponent = LegendComponent;
});
