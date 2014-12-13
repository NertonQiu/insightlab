
//Creating DOM for tests running on phantomjs

document.write('<div class="row-fluid"> \
                        <div class="single-container span12" id="widget-content"></div> \
                    </div>');

var templates = ['<script type="text/x-template" id="insight-template"> \
                <div class="widget">\
                    <h5> <a class="status" href="insights/personicx/index.html#/{{id}}/portrait" title="{{description}}">{{description}}</a><span class="status"></span></h5>\
                                    <div class="row-fluid">\
                                     <div class="span6">\
                                        <p>Created: {{createdDate}}</p>\
							            <a style="margin-left: 5px" href="insights/personicx/index.html#/{{id}}/portrait" title="View Insight">View Insight</a>\
                                      </div>\
                                      <div class="span6">\
                                        <p>Record Count: {{recordCount}}<br/><b></b></p>\
                                      </div>\
                                    </div>\
                 </div>\
            </script>',
    '<script type="text/x-template" id="home-template">\
        <div class="span7">\
        <div class="banner"></div>\
        <div class="text">\
            <h5>Acxiom Analytics</h5>\
            <p>Unleash the power of analytics to model your perfect customer</p>\
            <p>\
                <a href="insights/index.html" title="Create an Insight">Create an Insight &raquo;</a>\
            </p>\
        </div>\
        <div class="row-fluid">\
            <div class="span4">\
                <div class="widget widget-small">\
                    <h5>Creating Insights</h5>\
                    <p>View and analyze customer data using pre-built consumer\
                        propensity models, analytic reports, and predictive analytic\
                        tools.</p>\
                    <div class="footer">\
                        <a href="insights/index.html" title="Create an Insight Now">Create an Insight Now</a>\
                    </div>\
                </div>\
            </div>\
            <div class="span4">\
                <div class="widget widget-small">\
                    <h5>Building Models</h5>\
                    <p>Create a look-alike model or a specific propensity model\
                        for the consumer behavior of your choice.</p>\
                    <div class="footer">\
                        <a href="resources/documentation/Modeling Service in Insight Lab Final.pdf"\
                            target="_blank" title="Learn About Models">Learn About\
                            Models</a>\
                    </div>\
                </div>\
            </div>\
            <div class="span4">\
                <div class="widget widget-small">\
                    <h5>WHAT\'S NEW</h5>\
                    <p>Acxiom Audience Propensities: Essential purchase propensities, pre-built and ready to go.</p>\
                    <div class="footer">\
                        <a href="resources/documentation/aud_prop_slip_sheet.pdf" target="_blank"\
                            title="Details">Details</a>\
                    </div>\
                </div>\
            </div>\
        </div>\
	</div>\
	<div class="span5">\
        <h5>Storage Used</h5>\
        <div id="progressbar_container">\
        </div>\
        <h3>Recent Insights</h3>\
        <div id="recent-insights">\
    </div>\
</script>',
'<script type="text/x-template" id="scorecards-template">\
                            <div class="span12">\
                            <h2>Models</h2>\
                            <div class="acxiom-breadcrumb-container"></div>\
                            <div class="row-fluid">\
                                <div class="span6">\
                                    <ul id="list1" class="dragable" data-listidx="0">\
                                </div>\
                                <div class="span6">\
                                    <ul id="list2" class="dragable" data-listidx="1">\
                                </div>\
                            </div>\
                        </div>\
                    </script>',
                    '<script type="text/x-template" id="model-widget">\
                        <div class="widget">\
                            <div class="widget-header"></div>\
                            <div class="widget-body"></div>\
                        </div>\
                    </script>',
'<script type="text/x-template" id="model-widget">\
    <div class="widget">\
        <div class="widget-header"></div>\
        <div class="widget-body"></div>\
    </div>\
</script>',
'<script type="text/template" id="profilepage-template">\
    <div class="single-container span12">\
        <h2>Audience Portrait</h2>\
        <div class="acxiom-breadcrumb-container"></div>\
        <div class="row-fluid dashboard">\
            <div class="dashboard-filters">\
                <div class="filter-widget-container"></div>\
            </div>\
        </div>\
        <div class="row-fluid">\
            <div class="span12">\
                <ul id="list1" class="dragable" data-listidx=0>\
                    <li id="widget1" data-itemidx=0></li>\
                </ul>\
            </div>\
            <div class="span12">\
                 <ul id="list2" class="dragable" data-listidx=1>\
                    <li id="widget2" data-itemidx=0></li>\
                 </ul>\
            </div>\
        </div>\
    </div>\
</script>',
'<script type="text/template" id="widget-template1">\
    <div class="widget">\
        <div class="widget-header">\
            <span>Category Overview</span>\
            <a href="#" class="caret to"></a>\
        </div>\
        <div class="widget-body"><ul class="params-list">\
            <li><span><strong>Target: </strong>{{Target}}</span></li>\
            <li><span><strong>Reference: </strong>{{Reference}}</span></li>\
            <li><span><strong>Category: </strong>{{Category}}</span></li>\
        </ul>\
            <div id="chart1"></div>\
        </div>\
    </div>\
</script>',
'<script type="text/template" id="widget-template2">\
    <div class="widget">\
        <div class="widget-header">\
            <span style="cursor: pointer;">Question Detail</span>\
            <a href="#" class="caret to"></a>\
        </div>\
        <div class="widget-body"><ul class="params-list">\
            <li><span><strong>Target: </strong>{{Target}}</span></li>\
            <li><span><strong>Reference: </strong>{{Reference}}</span></li>\
            <li><span><strong>Question: </strong>{{Question}}</span></li>\
        </ul>\
            <div id="table_div3"></div>\
            <div id="chart_div3"></div>\
        </div>\
    </div>\
</script>',
'<script type="text/template" id="widget-template3">\
    <div class="widget">\
        <div class="widget-header">\
            <span style="cursor: pointer;">Question Detail</span>\
            <a href="#" class="caret to"></a>\
            <!--<a class="icon-widget icon-fullscreen pull-right" href="models-widgets-details.html" title="Widget #2"></a>-->\
        </div>\
        <div class="widget-body"><ul class="params-list">\
            <li><span><strong>Target: </strong>{{Target}}</span></li>\
            <li><span><strong>Reference: </strong>{{Reference}}</span></li>\
            <li><span><strong>Sub-Category: </strong>{{Question}}</span></li>\
        </ul>\
            <div id="table_div3"></div>\
            <div id="chart_div3"></div>\
        </div>\
    </div>\
</script>',
 // DPA Page end--> 
 // Personicx-template
    '<script type="text/x-template" id="personicx-template">\
    <div id ="breadcrumb" class="acxiom-breadcrumb-container"></div>\
    <div class="tabbable tabs-left ">\
        <ul class="nav nav-tabs span2">\
            <li data-tabId="portrait"><a href="#/portrait" title="Portrait">Portrait</a></li>\
            <li data-tabId="scattergram"><a href="#/scattergram" title="Scattergram">Scattergram</a></li>\
        </ul>\
        <div id="recent-requests"></div>\
        <div id="chart-legend" class="filter-legend"></div>\
        <div class="tab-content span10" id="content"></div>\
    </div>\
</script>',
'<script type="text/x-template" id="portrait-report-template">\
    <div class="row-fluid dashboard">\
        <div class="dashboard-filters">\
            <div id="portrait-subfilters" class="subfilters-row-top" style="display: none;"></div>\
            <div class="row-fluid" id="portrait-filters">\
                <div class="span2"></div>\
            </div>\
        </div>\
    </div>\
    <div class="row-fluid">\
        <div class="span12" id="portrait-title"></div>\
    </div>\
    <div class="row-fluid item-container">\
        <div id="grid-container" class="span12">\
        </div>\
    </div>\
</script>',
'<script type="text/x-template" id="legend-template">\
<h4>Legend</h4>\
<div>\
    <h5 style="float: left;">Count</h5>\
    <ul class="count-list unstyled">\
        <li>1<span class="small"></span></li>\
        <li>5<span class="medium"></span></li>\
        <li>10<span class="big"></span></li>\
    </ul>\
</div>\
</script>',
// <!-- Question-Scattergram-Template--> 
'<script type="text/x-template" id="question-scattergram-template">\
        <div class="span12"><ul class="params-list">\
            <li><span><strong>Insight: </strong>{{insightText}}</span></li>\
            <li><span><strong>Reference Question: </strong>{{questionText}}</span></li>\
            <li><span><strong>Response: </strong>{{responseText}}</span></li>\
        </ul><div>\
</script>'
];

for (var i = 0; i < templates.length; i++) {
    document.write(templates[i]);
}