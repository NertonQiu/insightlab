
/**
 * @fileoverview Simple implementation of HTML5 place holder.
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 */



/**
 * @constructor
 */
function PlaceHolder() {

  /**
   * @param {Node|Element} container The HTML container.
   */
  this.apply = function(container) {
    if (!isSupported_) {
      setPlaceHolder_(container.getElementsByTagName('INPUT'));
      setPlaceHolder_(container.getElementsByTagName('TEXTAREA'));
    }
  };

  /**
   * @param {Event} e Event.
   */
  function onplaceholder_(e) {
    e = e || event;
    /** @type {Node|Element} */
    var node = e.srcElement || e.target;

    /** @type {string|undefined} */
    var placeholder = node.getAttribute('placeholder');

    if (e.type == 'focus') {
      if (node.value == placeholder) {
        node.value = '';
        node.className = node.className.replace(/\s*placeholder/, '');
      }
    } else if (e.type == 'blur') {
      if (node.value == '') {
        node.value = placeholder;
        node.className += ' placeholder';
      }
    }
  }

  /**
   * @param {Array|NodeList} nodes Form elements.
   */
  function setPlaceHolder_(nodes) {
    for (var i = 0; i < nodes.length;) {
      /** @type {Node|Element} */
      var node = nodes[i++];

      /** @type {string|undefined} */
      var placeholder = node.getAttribute('placeholder');

      if (placeholder) {
        if (node.value == '') {
          node.value = placeholder;
          node.className += ' placeholder';
        }
        if (node.attachEvent) {
          node.attachEvent('onfocus', onplaceholder_);
          node.attachEvent('onblur', onplaceholder_);
        } else {
          node.addEventListener('focus', onplaceholder_, false);
          node.addEventListener('blur', onplaceholder_, false);
        }
      }
    }
  }

  /**
   * @type {boolean}
   */
  var isSupported_ = ('placeholder' in document.createElement('INPUT'));

  /**
   * The reference to self class.
   * @type {Function}
   */
  var __class__ = arguments.callee;

  if (__class__['instance_']) throw new Error('The instance already exists.');
}


/**
 * @return {PlaceHolder} Returns reference to PlaceHolder instance.
 */
PlaceHolder.getInstance = function() {
  return PlaceHolder['instance_'] ||
         (PlaceHolder['instance_'] = new PlaceHolder);
};

// Export for closure compiler.
window['PlaceHolder'] = PlaceHolder;
PlaceHolder['getInstance'] = PlaceHolder.getInstance;

