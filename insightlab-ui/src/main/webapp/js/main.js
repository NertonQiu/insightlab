function initLocalVersion() {
    var pathStorage = {
        'js' : 'js/',
        'component' : 'src/scripts/components/',
        'widget' : 'src/scripts/widgets/',
        'net' : 'src/scripts/net/',
        'dom' : 'src/scripts/dom/',
        'chart' : 'src/scripts/charts/',
        'ilwidget' : 'resources/scripts/widgets/',
        'page' : 'resources/scripts/pages/',
        'ilcomponent' : 'resources/scripts/components/',
        'utils' : 'resources/scripts/utils/',
        'compiled' : 'resources/scripts/',
        'mappers' : 'resources/scripts/mappers/',
        'datatablePlugins': 'resources/scripts/datatable-plugins/'
    };

    var jsFileList = {
        'utils': ['DomUtils', 'StringUtils', 'Utils', 'Template', 'DateUtils', 'HashUtil'],
        'mappers' : ['DataFormatter', 'DataMapper', 'KendoMapper'],
        'ilwidgets': ['BaseChartsContainer', 'BaseWidgetsContainer', 'PanelWidget', 'FilterWidget', 'PanelFilters', 'RecentRequestsWidget', 'TargetGroupsList'],
        'widgets' : ['BaseWidget', 'DataLoader'],
        'ilcomponents': ['Breadcrumb', 'DataTableComponent', 'DataTableComponentExt', 'DropDownComponent', 'SliderComponent', 'ProgressBarComponent', 'KendoChart', 'CheckboxComponent', 'ButtonComponent', 'InputTagComponent', 'LegendComponent', 'CheckboxListComponent'],
        'components' : ['BaseComponent', 'TableComponent'],
        'scripts': ['jquery.dblclick'],
        'datatablePlugins': ['dataTable.plug-ins'],
        'dom' : ['EventTargetListener', 'DataStorage'],
        'net' : ['HttpRequest', 'ServletRequest', 'HttpServletRequest'],
        'charts' : [],//['BaseChart', 'BarChart'],
        'pages': ['InsightsPage', 'HomePage', 'ModelPage', 'FileLoaderPage', 'Scorecard', 'ScorecardDetails', 'PersonicxPage', 'ScattergramReport', 'PortraitReport', 'TargetGroupReport', 'ProfileAppPage', 'DataMapperPage', 'COPage', 'BuildSegmentPage'],
        'compiled' : ['main_compiled']
    };

    this.init = function() {
        pagesConfig.webapp = {
            name: 'HomePage',
            redirect: {
                isActive: false,
                url: ''
            }
        };

        GlobalNav.events.addListener('navClick', function(e) {
            log(window.location.href);
            log(e.detail.url);
            if (window.location.href != e.detail.url) {
                window.location.href = e.detail.url;
            }
        }, false);

        for (var i = 0; i < jsFileList.net.length; i++)
            jsFileList.net[i] = self_.getPath('net') + jsFileList.net[i];

        for (var i = 0; i < jsFileList.dom.length; i++)
            jsFileList.dom[i] = self_.getPath('dom') + jsFileList.dom[i];

        for (var i = 0; i < jsFileList.widgets.length; i++)
            jsFileList.widgets[i] = self_.getPath('widget') + jsFileList.widgets[i];

        for (var i = 0; i < jsFileList.components.length; i++)
            jsFileList.components[i] = self_.getPath('component') + jsFileList.components[i];

        for (var i = 0; i < jsFileList.scripts.length; i++)
            jsFileList.scripts[i] = self_.getPath('js') + jsFileList.scripts[i];

        for (var i = 0; i < jsFileList.datatablePlugins.length; i++)
            jsFileList.datatablePlugins[i] = self_.getPath('datatablePlugins') + jsFileList.datatablePlugins[i];

        for (var i = 0; i < jsFileList.charts.length; i++)
            jsFileList.charts[i] = self_.getPath('chart') + jsFileList.charts[i];

        for (var i = 0; i < jsFileList.ilcomponents.length; i++)
            jsFileList.ilcomponents[i] = self_.getPath('ilcomponent') + jsFileList.ilcomponents[i];

        for (var i = 0; i < jsFileList.ilwidgets.length; i++)
            jsFileList.ilwidgets[i] = self_.getPath('ilwidget') + jsFileList.ilwidgets[i];

        for (var i = 0; i < jsFileList.pages.length; i++)
            jsFileList.pages[i] = self_.getPath('page') + jsFileList.pages[i];

        for (var i = 0; i < jsFileList.utils.length; i++)
            jsFileList.utils[i] = self_.getPath('utils') + jsFileList.utils[i];

        for (var i = 0; i < jsFileList.compiled.length; i++)
            jsFileList.compiled[i] = self_.getPath('compiled') + jsFileList.compiled[i];

        for (var i = 0; i < jsFileList.mappers.length; i++)
            jsFileList.mappers[i] = self_.getPath('mappers') + jsFileList.mappers[i];

        var sources = [].concat(self_.getPath('js') + 'stubby',
            jsFileList.net,
            jsFileList.utils,
            jsFileList.mappers,
            jsFileList.dom,
            jsFileList.charts,
            jsFileList.widgets,
            jsFileList.ilcomponents,
            jsFileList.components,
            jsFileList.ilwidgets,
            jsFileList.scripts,
            jsFileList.datatablePlugins,
            jsFileList.pages
                                );

        self_.load(sources);
    };



    this.correctPath = (function() {
        var pathPrefix = (function() {
            var locationHref = window.location.href,
                webapp = 'webapp',
                webappHref = locationHref.substring(0, locationHref.indexOf(webapp) + webapp.length);

            return webappHref;
        })();

        return function(path) {
            if (path.indexOf('/') !== 0) {
                path = '/' + path;
            }

            if (pathPrefix.lastIndexOf('/') === (pathPrefix.length - 1)) {
                pathPrefix = pathPrefix.substring(0, pathPrefix.length - 1);
            }

            return pathPrefix + path;
        };
    })();

    this.getPath = function(opt_type) {
        return self_.correctPath(pathStorage[opt_type]);
    };

    this.load = function(source) {
        if (!source.length) {
            $(function() {
                var component = initPage(window.location.pathname);
                new window[component.name](component.container);
            });
            return;
        }
        var head = document.getElementsByTagName('HEAD')[0];
        var script = document.createElement('SCRIPT');
        script.type = 'text/javascript';
        script.async = false;
        script.src = source.shift() + '.js';
        script.onload = function() {
            self_.load(source);
        };
        script.onreadystatechange = function() {
            if (script.readyState == 'complete') script.onload();
        };

        head.appendChild(script);
    };

    var self_ = this;

    self_.init();
}
