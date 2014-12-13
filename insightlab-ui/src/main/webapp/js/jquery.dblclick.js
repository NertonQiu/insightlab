;/**
 * JQuery extention for handling click, dblclick.
 * @param {function(Event):event} clickCallback Callback for click.
 * @param {function(Event):event} doubleclickCallback Callback for slow double click.
 * @param {number} timeout
 * @this {node}
 * @return {void}
 */
jQuery.fn.single_double_click = function(clickCallback, doubleclickCallback, timeout) {
    var that = this;
    return that.each(function() {
        /**@type{number}*/var clicks = 0;
        var self = this;
        jQuery(this).off('click');
        jQuery(this).on('click', function(event) {
            if (/MSIE (\d+\.\d+);/.test(navigator.userAgent)) {
                var ieversion = new Number(RegExp.$1);
                if (ieversion <= 8) {
                    this.ondblclick = function() {
                        clicks++;
                    };
                }
            }
            clicks++;
            if (clicks == 1) {
                setTimeout(function() {
                    if (clicks == 1) {
                        clickCallback.call(self, event);
                    } else {
                        doubleclickCallback.call(self, event);
                    }
                    clicks = 0;
                }, timeout || 500);
            }
        });
    });
};
