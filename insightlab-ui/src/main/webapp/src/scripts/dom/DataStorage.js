/**
 * @fileoverview Provides data persistence using HTML5 local storage
 * mechanism. Local storage must be available under window.localStorage,
 * see: http://www.w3.org/TR/webstorage/#the-localstorage-attribute.
 *
 */



/**
 * Provides a storage mechanism that uses HTML5 local storage.
 *
 * @param {string=} opt_type Storage type (cookie, auto).
 * @constructor
 */
function DataStorage(opt_type) {

    /**
     * @const
     * @type {string}
     */
    var LOCALSTORAGE = 'localStorage';

    /**
     * @const
     * @type {string}
     */
    var GLOBALSTORAGE = 'globalStorage';

    /**
     * Sets a value for a key.
     *
     * @param {string} key The key to set.
     * @param {string|Object} value The string to save.
     */
    this.set = function(key, value) {
        if (value === undefined) {
            self_.remove(key);
            return;
        }
        if (opt_type == 'cookie') {
            cookie_.set(key, value);
        } else if (localStorage_) {
            localStorage_.setItem(key, serialize_(value));
        } else if (globalStorage_) {
            globalStorage_[key] = serialize_(value);
        } else if (userData_) {
            userData_.setAttribute(encodeURIComponent(key), serialize_(value));
            userData_['save'](userData_.tagName);
        } else {
            data_[key] = value;
        }
    };

    /**
     * Gets the value stored under a key.
     *
     * @param {string} key The key to get.
     * @return {?string} The corresponding value, null if not found.
     */
    this.get = function(key) {
        var value = data_[key];

        if (opt_type == 'cookie') {
            value = cookie_.get(key);
        } else if (localStorage_) {
            value = localStorage_.getItem(key);
        } else if (globalStorage_) {
            value = globalStorage_[key] && globalStorage_[key].value;
        } else if (userData_) {
            value = userData_.getAttribute(decodeURIComponent(key));
        }
        return deserialize_(value);
    };

    /**
     * Removes a key and its value.
     *
     * @param {string} key The key to remove.
     */
    this.remove = function(key) {
        if (opt_type == 'cookie') {
            cookie_.remove(key);
        } else if (localStorage_) {
            localStorage_.removeItem(key);
        } else if (globalStorage_) {
            delete globalStorage_[key];
        } else if (userData_) {
            userData_.removeAttribute(encodeURIComponent(key));
            userData_['save'](userData_.tagName);
        } else {
            delete data_[key];
        }
    };

    /**
     * Removes all key-value pairs.
     */
    this.clear = function() {
        if (localStorage_) {
            localStorage_.clear();
        } else if (globalStorage_) {
            /** @type {string} */ var key;
            for (key in globalStorage_) self_.remove(key);
        } else if (userData_) {
            /** @type {number} */ var left = userData_.attributes.length;
            for (; left > 0; left--)
                userData_.removeAttribute(userData_.attributes[left - 1].nodeName);
            userData_['save'](userData_.tagName);
        } else {
            data_ = {};
        }
    };

    /**
     * @param {string|Object} value Object to serialize.
     * @return {string|Object} Returns serialized object as string.
     * @private
     */
    function serialize_(value) {
        return window.JSON && opt_type != 'cookie' ? JSON.stringify(value) :
          encodeURIComponent('' + value);
    }

    /**
     * @param {string} value String to parse.
     * @return {?string} Returns parsed object from string.
     * @private
     */
    function deserialize_(value) {
        if (typeof value != 'string') return null;
        return window.JSON && opt_type != 'cookie' ? JSON.parse(value) :
          decodeURIComponent(value);
    }

    /**
     * The reference to current class instance. Used in private methods.
     * @type {DataStorage}
     * @private
     */
    var self_ = this;

    /**
     * @type {Object}
     * @private
     */
    var data_ = {};

    /**
     * @type {Object}
     * @private
     */
    var localStorage_ = (function() {
        /** @preserveTry */
        try {
            return window.JSON && LOCALSTORAGE in window && window[LOCALSTORAGE];
        } catch (e) {
            return null;
        }
    })();

    /**
     * @type {Object}
     * @private
     */
    var globalStorage_ = (function() {
        /** @preserveTry */
        try {
            return window.JSON && GLOBALSTORAGE in window &&
              window[GLOBALSTORAGE] &&
              window[GLOBALSTORAGE][location.hostname];
        } catch (e) {
            return null;
        }
    })();

    /**
     * @type {Object}
     * @private
     */
    var userData_ = (function() {
        /** @preserveTry */
        try {
            var node = document.createElement('userdata');
            node['addBehavior']('#default#userData');
            document.body.appendChild(node);
            node['load'](node.tagName);
            return node;
        } catch (e) {
            return null;
        }
    })();

    /**
     * @type {Object}
     * @private
     */
    var cookie_ = {
        /**
         * Sets a cookie.
         * @param {string} key The name of the cookie.
         * @param {string|Object} value The value of the cookie.
         * @param {number=} opt_days Optional, days after the cookie expires.
         * @param {string=} opt_domain Optional, domain that the cookie is
         *     available to.
         */
        set: function(key, value, opt_days, opt_domain) {
            /** @type {string} */
            var expires = '';
            /** @type {string} */
            var domain = opt_domain ? ' domain=' + opt_domain : '';

            if (opt_days) {
                /** @type {Date} */ var now = new Date;
                now.setTime(now.getTime() + opt_days * 24 * 60 * 60 * 1000);
                expires = '; expires=' + now.toGMTString();
            }
            document.cookie = escape(key) + '=' + (value || '') + expires +
              '; path=/;' + domain;
        },

        /**
         * Returns the value for the first cookie with the given name.
         * @param {string} key The name of the cookie to get.
         * @param {string=} opt_default The optional default value.
         * @return {string} The value of the cookie. If no cookie is set this
         *     returns opt_default or undefined if opt_default is not provided.
         */
        get: function(key, opt_default)  {
            /** @type {Array} */ var parts = (document.cookie || '').split(/\s*;\s*/);
            /** @type {string} */ var name = key + '=';
            for (/** @type {number} */ var i = 0; i < parts.length; i++) {
                if (parts[i].indexOf(name) == 0) {
                    return parts[i].substr(name.length);
                }
            }
            return opt_default || '';
        },

        /**
         * Removes and expires a cookie.
         * @param {string} key The cookie name.
         * @return {boolean} Whether the cookie existed before it was removed.
         */
        remove: function(key) {
            /** @type {string} */ var value = cookie_.get(key);
            cookie_.set(key, '', 0);
            return !!value;
        }
    };
}

// Export for closure compiler.
window['DataStorage'] = DataStorage;

