;
var hashFactory = function() {
    /**
     * @private
     * @param {hashRoute} routeConfig
     * Defines route hash and handler.
     * @return {hashContext} Route object.
     */
    var parseRoute_ = function(routeConfig) {
        /** @type  {hashContext}*/
        var hashContext = { route: [], params: {} };
        /**@type{Array.<string>}*/
        var routeParams = routeConfig['route'].split('/');
        /**@type{Array.<string>}*/
        var hashParams = location.hash.replace('#', '').split('/');
        if (routeParams.length !== hashParams.length) return null;
        for (/**@type{number} */var i = 0; i < routeParams.length; i++) {
            if (routeParams[i].indexOf(':') !== -1) {
                hashContext.params[routeParams[i].replace(':', '')] = hashParams[i];
            } else if (routeParams[i] === hashParams[i]) {
                hashContext.route.push(hashParams[i]);
            } else {
                return null;
            }
        }
        return hashContext;
    };
    /**
     * Handle hash change
     */
    var handleHashChange = function() {
        var length = routes_.length;
        for (/**@type{number} */var i = 0; i < length; i++) {
            /** @type {hashRoute}*/
            var route = routes_[i];
            /** @type {hashContext}*/
            var hashContext = parseRoute_(route);
            if (!hashContext) continue;
            route.callback(hashContext);
            return;
        }
        hashUtil.unhandledHash();
    };

    /**
     * Returns hash context.
     * @return {hashContext}
     */
    var getHashContext = function() {
        /**@type{number} */var length = routes_.length;
        for (/**@type{number} */var i = 0; i < length; i++) {
            /** @type {hashRoute}*/
            var route = routes_[i];
            /** @type {hashContext}*/
            var hashContext = parseRoute_(route);
            if (hashContext) return hashContext;
        }
        return null;
    };

    var hashUtil = {
        run: function() {
            if (started_) return;
            if (window.removeEventListener && window.addEventListener) {
                window.removeEventListener('hashchange', handleHashChange, false);
                window.addEventListener('hashchange', handleHashChange, false);
            }
            else if (window.detachEvent && window.attachEvent) {
                window.detachEvent('onhashchange', handleHashChange);
                window.attachEvent('onhashchange', handleHashChange);
            }
            handleHashChange();
            started_ = true;
        },
        addHashRoute: function(route, callback) {
            if (typeof route !== 'string' || typeof callback !== 'function') {
                return;
            }
            routes_.push({ route: route, callback: callback });
        },
        unhandledHash: function() {
        },
        getHashContext: getHashContext,
        isStarted: function() {
            return started_;
        }
    };

    /**
     * @private
     * @type {Array.<hashRoute>}
     */
    var routes_ = [];

    /**
     * Defines if hash router is running.
     * @type {boolean}
     */
    var started_ = false;

    return hashUtil;
};

// Export for closure compiler.
window['HashUtil'] = hashFactory;

function encodeHTML(input) {
    var div = document.createElement('div');
    $(div).text(input);
    return $(div).html();
}

function encodeTag(input) {
    return input.replace(/</g, '&lt;').replace(/>/g, '&gt;');
}

function decodeTag(input) {
    return input.replace(/&lt;/g, '<').replace(/&gt;/g, '>');
}

/**
 * Returns parameter from query string by name
 * @param {string} name query parameter name.
 * @return {string} value of query string param.
 */
function getParameterByName(name) {
    name = name.replace(/[\[]/, '\\\[').replace(/[\]]/, '\\\]');
    /** @type {RegExp} */
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}
