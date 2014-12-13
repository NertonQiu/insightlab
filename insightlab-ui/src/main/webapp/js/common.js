var pagesConfig = {
    hash: window.location.hash,
    container: 'widget-content',
    insightlab: {
        name: 'HomePage',
        title: '概览',
        redirect: {
            isActive: false,
            url: ''
        }
    },
    models: {
        name: 'ModelPage',
        title: '模型列表',
        redirect: {
            isActive: false,
            url: ''
        }
    },
    scorecards: {
        name: 'Scorecard',
        title: '模型详情',
        redirect: {
            isActive: true,
            url: '../index.html'
        }
    },
    co: {
        name: 'COPage',
        title: 'COPage',
        redirect: {
            isActive: false,
            url: ''
        }
    },
    'scorecard-details' : {
        name: 'ScorecardDetails',
        title: '模型详情',
        redirect: {
            isActive: true,
            url: '../index.html'
        }
    },
    dpa: {
        name: 'ProfileAppPage',
        title: 'Audience Portrait',
        redirect: {
            isActive: true,
            url: '../index.html'
        }
    },
    insights: {
        name: 'InsightsPage',
        title: 'Analytic Datasets',
        redirect: {
            isActive: false,
            url: ''
        }
    },
    personicx: {
        name: 'PersonicxPage',
        title: 'Personicx',
        redirect: {
            isActive: true,
            url: '../index.html'
        }
    },
    'build-segment': {
        name: 'BuildSegmentPage',
        title: 'BuildSegmentPage',
        redirect: {
            isActive: false,
            url: ''
        }
    }
};

function initMenu_() {
    var $links = $('.acxiom-topnav-tabs a'),
        $li = $links.find('div'),
        url = window.location.href;

    $li.first().addClass('active');

    $.each($links, function(key, value) {
        if (url.indexOf(value.getAttribute('href')) !== -1) {
            $li.removeClass('active');
            $(value).find('div').addClass('active');
        }
    });

    $links.click(function() {
        if (url == $(this).attr('href')) return false;
    });
}

/**
* Deprecated method used for sub navigation
* @deprecated
*/
function initMenu_DEP() {
    var $links = $('.s-feature-bar a'),
        $li = $links.parent(),
        url = window.location.href;
    $li.removeClass('active');

    $.each($links, function(key, value) {
        if (url.indexOf(value.getAttribute('href')) !== -1) {
            $(value).parent().addClass('active');
        }
    });
}

function log(text) {
    window.console && console.log(arguments.length === 1 ? arguments[0] : arguments);
}

function setVersion() {
    var loader_ = new DataLoader();
    loader_.loadData('version', function(dataResult) {
        var html = '<div class="version-style">' + dataResult['Implementation-Version'];
        html += '-' + dataResult['Build-Number'];
        $('div.span12.footer').html(html);
    });
}

function initPage(href) {
    GlobalNav.events.addListener('navClick', function(e) {
        if (window.location.href != e.detail.url) {
            window.location.href = e.detail.url;
        }
    }, false);

    var url = [],
        currentPage;

    $.each(href.split('/'), function(key, value) {
        var val = value.toLowerCase();
        if (val != '' && val != 'index.html') url.push(val);
    });

    currentPage = url[url.length - 1];

    log('Page: ' + currentPage + ', Component: ' + pagesConfig[currentPage].name + ', Container: ' + pagesConfig.container);

    document.title = '安客诚安众通 | 模型 | ' + pagesConfig[currentPage].title;

    if (pagesConfig[currentPage].redirect.isActive && pagesConfig.hash.length <= 1) {
        window.location = pagesConfig[currentPage].redirect.url;
    }

    return { name: pagesConfig[currentPage].name, container: pagesConfig.container };
}
window['initPage'] = initPage;

// From https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/keys
if (!Object.keys) {
    Object.keys = (function() {
        'use strict';
        var hasOwnProperty = Object.prototype.hasOwnProperty,
        hasDoNotEnumBug = !({ toString: null }).propertyIsEnumerable('toString'),
        doNotEnums = [
          'toString',
          'toLocaleString',
          'valueOf',
          'hasOwnProperty',
          'isPrototypeOf',
          'propertyIsEnumerable',
          'constructor'
        ],
        doNotEnumsLength = doNotEnums.length;

        return function(obj) {
            if (typeof obj !== 'object' && (typeof obj !== 'function' || obj === null)) {
                throw new TypeError('Object.keys called on non-object');
            }

            var result = [], prop, i;

            for (prop in obj) {
                if (hasOwnProperty.call(obj, prop)) {
                    result.push(prop);
                }
            }

            if (hasDoNotEnumBug) {
                for (i = 0; i < doNotEnumsLength; i++) {
                    if (hasOwnProperty.call(obj, doNotEnums[i])) {
                        result.push(doNotEnums[i]);
                    }
                }
            }
            return result;
        };
    } ());
}

if (!Array.prototype.filter) {
    Array.prototype.filter = function(fn, context) {
        var i,
        value,
        result = [],
        length;

        if (!this || typeof fn !== 'function' || (fn instanceof RegExp)) {
            throw new TypeError();
        }

        length = this.length;

        for (i = 0; i < length; i++) {
            if (this.hasOwnProperty(i)) {
                value = this[i];
                if (fn.call(context, value, i, this)) {
                    result.push(value);
                }
            }
        }
        return result;
    };
}

if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function(searchElement, fromIndex) {
        var i,
        pivot = (fromIndex) ? fromIndex : 0,
        length;

        if (!this) {
            throw new TypeError();
        }

        length = this.length;

        if (length === 0 || pivot >= length) {
            return -1;
        }

        if (pivot < 0) {
            pivot = length - Math.abs(pivot);
        }

        for (i = pivot; i < length; i++) {
            if (this[i] === searchElement) {
                return i;
            }
        }
        return -1;
    };
}
