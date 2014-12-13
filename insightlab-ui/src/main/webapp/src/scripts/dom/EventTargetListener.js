
/**
 * @fileoverview Event-driven widgets implementation is based on W3C DOM
 * Level 3 Events Specification.  For inter-widget communication widgets should
 * define events with prefix in format "widget.event" and bind events to
 * top-level DOM <HTML> element.
 *
 * @link https://docs.google.com/a/sethq.com/document/d/
 *       1lEGQWV8BHWUgWk5CV1QJkxEGirBYUWqcken-8N0_724/edit
 * @link http://google-styleguide.googlecode.com/svn/trunk/javascriptguide.xml
 *
 * @requires jQuery
 */



/**
 * The EventTargetListener class implements W3C EventTarget and EventListener
 * interfaces.
 *
 * @constructor
 */
function EventTargetListener() {

  /**
   * This method allows the registration of event listeners on the event target.
   * @param {string} type The event type for which the user is registering.
   * @param {Function} listener The listener parameter takes an interface
   *     implemented by the user which contains the methods to be called when
   *     the event occurs.
   * @param {boolean?=} opt_useCapture If true, useCapture indicates that the
   *     user wishes to initiate capture. Not used in current implementation.
   */
  this.addEventListener = function(type, listener, opt_useCapture) {
    // google.visualization.events.addListener(document, type, listener);
    $(root_).bind(type, listener);
  };

  // Export for closure compiler.
  this['addEventListener'] = this.addEventListener;

  /**
   * This method allows the removal of event listeners from the event target.
   * @param {string} type Specifies the event type of the EventListener being
   *     removed.
   * @param {Function} listener The EventListener parameter indicates the
   *     EventListener to be removed.
   * @param {boolean?=} opt_useCapture Specifies whether the EventListener being
   *     removed was registered as a capturing listener or not. Not used in
   *     current implementation.
   */
  this.removeEventListener = function(type, listener, opt_useCapture) {
    $(root_).unbind(type, listener);
    // google.visualization.events.removeListener(listener);
  };

  // Export for closure compiler.
  this['removeEventListener'] = this.removeEventListener;

  /**
   * This method allows the dispatch of events into the implementations event
   * model.
   * @param {WidgetEvent} evt Specifies the event type, behavior, and contextual
   *     information to be used in processing the event.
   * @return {boolean} The return value of dispatchEvent indicates whether any
   *     of the listeners which handled the event called preventDefault. If
   *     preventDefault was called the value is false, else the value is true.
   * @throws {WidgetEventException} Raised if the WidgetEvent's type was not
   *     specified by initializing the event before dispatchEvent was called.
   */
  this.dispatchEvent = function(evt) {
    // google.visualization.events.trigger(document, evt['type'],
    // {'type': evt['type'], 'widget': evt['target']});

    //try {
    $(root_).trigger({'type': evt['type'], 'widget': evt['target']});
    //} catch (ex) {
    //  throw new WidgetEventException(ex.message || ex);
    //}
    return true;
  };

  // Export for closure compiler.
  this['dispatchEvent'] = this.dispatchEvent;

  /**
   * This method is called whenever an event occurs of the type for which the
   * EventListener interface was registered.
   * @param {WidgetEvent} evt The WidgetEvent contains contextual information
   *     about the event.
   * @protected
   */
  this.handleEvent = function(evt) {};

  // Export for closure compiler.
  this['handleEvent'] = this.handleEvent;

  /**
   * Specifies top-level element selector.
   * @type {string}
   * @private
   */
  var root_ = 'html';
}

// Export for closure compiler.
window['EventTargetListener'] = EventTargetListener;



/**
 * The WidgetEvent class implements W3C Event interface.
 *
 * @param {string} type The name of the event (case-insensitive).
 * @param {BaseWidget} target Used to indicate the BaseWidget to which the event
 *     was originally dispatched.
 * @constructor
 */
function WidgetEvent(type, target) {

  /**
   * The case-insensitive event type.
   * @type {string|undefined}
   */
  this['type'] = type;

  /**
   * The reference to BaseWidget to which the event was originally dispatched.
   * @type {BaseWidget|undefined}
   */
  this['target'] = target;

  /**
   * The stopPropagation method is used prevent further propagation of an event
   * during event flow.
   */
  this.stopPropagation = function() {};

  /**
   * If an event is cancelable, the preventDefault method is used to signify
   * that the event is to be canceled, meaning any default action normally taken
   * by the implementation as a result of the event will not occur.
   */
  this.preventDefault = function() {};
}

// Export for closure compiler.
window['WidgetEvent'] = WidgetEvent;



/**
 * The WidgetEventException class implements W3C EventException interface.
 *
 * @constructor
 * @extends {Error}
 */
function WidgetEventException() {
  Error.apply(this, arguments);

  /**
   * @type {number}
   */
  this['code'] = 0;
}

// Export for closure compiler.
window['WidgetEventException'] = WidgetEventException;

