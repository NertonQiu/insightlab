(function() {

    ACXM.Chart = {};

    // use as theme: 'acxiomTheme'
    kendo.dataviz.ui.registerTheme('acxiomTheme', {
        "chart" : {
            "title" : {
                "color" : "#8e8e8e"
            },
            "legend" : {
                "labels" : {
                    "color" : "#232323"
                }
            },
            "chartArea" : {},
            "seriesDefaults" : {
                "labels" : {
                    "color" : "#000"
                }
            },
            "axisDefaults" : {
                "line" : {
                    "color" : "#8e8e8e"
                },
                "labels" : {
                    "color" : "#232323"
                },
                "minorGridLines" : {
                    "color" : "#f0f0f0"
                },
                "majorGridLines" : {
                    "color" : "#dfdfdf"
                },
                "title" : {
                    "color" : "#232323"
                }
            },
            "seriesColors" : ["#7F3361", "#8099AD", "#175997", "#92A23D", "#8099AD", "#4C7B88"],
            "tooltip" : {
                "background" : "#fff",
                "color" : "#000",
                "opacity" : 0.92
            }
        },
        "gauge" : {
            "pointer" : {
                "color" : "#ea7001"
            },
            "scale" : {
                "rangePlaceholderColor" : "#dedede",
                "labels" : {
                    "color" : "#2e2e2e"
                },
                "minorTicks" : {
                    "color" : "#2e2e2e"
                },
                "majorTicks" : {
                    "color" : "#2e2e2e"
                },
                "line" : {
                    "color" : "#2e2e2e"
                }
            }
        }
    });

})();
