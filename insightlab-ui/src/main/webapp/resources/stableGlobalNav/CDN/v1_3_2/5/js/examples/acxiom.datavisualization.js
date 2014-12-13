(function() {
/* ===============================================================
        BAR CHAR
=============================================================== */
ACXM.Example.BarChart = function() {
    $(function() {
        //Kendo options
        var chartOpt = {
            theme: 'acxiomTheme', //default theme
            seriesDefaults: {
                type: 'bar',
                stack: true,
                padding: 100
            },
            legend : {
                position: "bottom",
                visible : false
            },
            plotArea:  { margin: {top:50} },
            valueAxis : {
                labels : {
                    format : "N0"
                },
                majorUnit : 250
            },
            categoryAxis : {
                categories : ["ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "KIDS", "ITEM7", "ITEM8", "ITEM9", "ITEM10", "ITEM11", "ITEM12", "ITEM13", "ITEM14", "ITEM15", "ITEM16", "ITEM17", "ITEM18"]
            },
            chartArea : {
                background : ""
            },
            tooltip : {
                visible : true,
                template : "<div class='tooltip'><span class='header'> ${ dataItem.name } </span><span class='row1'>${ dataItem.impressions } Impressions</span><span class='row1'> ${ dataItem.spots } spots</span></div>",
                color : "black",
                opacity : 1,
                width: 0,
                height: 0,
                padding: 100,
                border : {
                    width : 0,
                    color : "#111111"
                }
            }
        };

        //Array with for BarChart
        var chartData = [
            {
                name : "ITEM1",
                value : 605.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM2",
                value : 760.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM3",
                value : 260.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM4",
                value : 230.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM5",
                value : 260.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "KIDS",
                value : 230.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM7",
                value : 315.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM8",
                value : 416.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM9",
                value : 520.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM10",
                value : 423.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM11",
                value : 326.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM12",
                value : 660.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM13",
                value : 415.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM14",
                value : 716.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM15",
                value : 270.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM16",
                value : 323.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM17",
                value : 566.7,
                impressions : "376",
                spots : "13"
            },
            {
                name : "ITEM18",
                value : 663.7,
                impressions : "376",
                spots : "13"
            }
        ];

        /* BarChart initialization
         * params:
         *  container: @selector
         *  data: array with data
         *  options: available all bar chart kendo ui options
         * */
        ACXM.Chart.Bar({
            container: '#chart',
            data: chartData,
            options: chartOpt
        });

        /* Auto complete plugin
        * jquery.plugin.autocomplete.js
        * */
        $(".acxiom-autocomplete").autocomplete({
            lookup: ["Armenia", "Canada", "USA"],
            isLocal: true,
            onSelect: function(obj) {
                //obj.value
                ACXM.Chart.Bar({
                    container: '#chart',
                    data: chartData,
                    options: chartOpt
                });
            }
        });

        /* Spinner plugin
        *  jquery.plugin.spinner.js
        * */
        $("#view").spinit({ min: 1, initValue: 1, callback: spinOnChange });

        //callback function for spinner (onChange)
        function spinOnChange(val) {
            ACXM.Chart.Bar({
                container: '#chart',
                data: chartData,
                options: chartOpt
            });
        }
    });
}
})();

/* =============================================================== 
		GEOSPACIAL 											 
=============================================================== */
function initGeospatial() {
    var chart;
	
	function updateData(newData) {
		createChart({
			containerId: "chart",
			data: newData
		});
	};

	function createChart(options) {
		var series = [];
		var maxSize = Number.NEGATIVE_INFINITY;
		var bubbleSizes = [];
	
		for (var i = 0; i < options.data.length; i++) {
			if (options.data[i].size > maxSize) {
	
				maxSize = options.data[i].size;
			}
		}
	
		for (var i = 0; i < options.data.length; i++) {
			var arr = []
			arr.push(options.data[i]);
			series.push({
				data : arr
			});
			bubbleSizes.push(70 * Math.sqrt(options.data[i].size / maxSize));
		}
	
		function dataBound(e) {
			var series = e.sender.options.series;
			for (var i = 0; i < series.length; i++) {
				series[i].maxSize = bubbleSizes[i];
			}
		}
	
		chart = $("#" + options.containerId).kendoChart({
			theme : "acxiomTheme",
			title : {
				text : ""
			},
		
			seriesDefaults : {
				type : 'bubble'
			},
			plotArea : {
				margin : {
					left: 100,
					top : 130
				}
				
			},
			chartArea: {
				opacity: 0
			},
			legend : {
				visible : true
	
			},
			dataSource : {
				data : options.data,
				group : {
					field : "year"
				}
			},
	
			series : series,
			xAxis : {
				labels : {
					format : "{0:N0}",
					skip : 1,
					visible : false
				},
				axisCrossingValue : -5000,
				majorUnit : 2000,
				line : {
					width : 0
				},
				plotBands : [{
					from : -5000,
					to : 0,
					color : "#00f",
					opacity : 0
				}],
				majorGridLines : {
					visible : false
				}
			},
			yAxis : {
				labels : {
					format : "{0:N0}",
					visible : false
				},
				line : {
					width : 0
				},
				majorGridLines : {
					visible : false
				}
			},
			tooltip : {
				visible : true,
				template : "<div class='tooltip'><span class='header'> ${ dataItem.name } </span><span class='row1'>${ dataItem.impressions } Impressions</span><span class='row1'> ${ dataItem.spots } spots</span></div>",
				color : "black",
				opacity : 1,
				width: 0,
				height: 0,
				padding: 100,
				border : {
					width : 0,
					color : "#111111"
				}
			},
			dataBound : dataBound
		});
	
	};

	/* Initialize Geospacial */
	var data1 = [{
		x : -2500,
		y : 50000,
		size : 500000,
		name : "Dodge City",
		impressions : "683",
		spots: "8"
	}, {
		x : 500,
		y : 110000,
		size : 760000,
		name : "Dodge City",
		impressions : "983",
		spots: "3"
	}, {
		x : 7000,
		y : 39000,
		size : 700000,
		name : "Dodge City",
		impressions : "383",
		spots: "3"
	}, {
		x : 1400,
		y : 150000,
		size : 700000,
		name : "Dodge City",
		impressions : "483",
		spots: "6"
	}, {
		x : 4400,
		y : 30000,
		size : 300000,
		name : "Dodge City",
		impressions : "123",
		spots: "3"
	}, {
		x : 3300,
		y : 40000,
		size : 450000,
		name : "Dodge City",
		impressions : "483",
		spots: "6"
	}, {
		x : 3000,
		y : 55000,
		size : 900000,
		name : "Dodge City",
		category : "Whole Foods Market",	impressions : "483",
		spots: "6"
	}], 
	data2 = jQuery.extend(true, [], data1),
	data3 = jQuery.extend(true, [], data2);
	
	var delta = [600000, 800000, 1200000];
	
	for(var i = 0; i < data1.length; i++) {
		data2[i].size += delta[i%3];
		data3[i].size += delta[(i+1)%3];
		data2[i].x += 10000;
		data3[i].y += 30000;
	}

    $(function() {

        var countryList = ["KANSAS", "CALIFORNIA", "FLORIDA"];

        $(".acxiom-autocomplete, .acxiom-visual-search").autocomplete({
            lookup: countryList,
            isLocal: true,
            onSelect: function(obj) {
                switch(obj.value) {
                    case countryList[0]:
                        updateData(data1);
                        break;
                    case countryList[1]:
                        updateData(data2);
                        break;
                    case countryList[2]:
                        updateData(data3);
                        break;
                }
            }
        });

        function spinOnChange(val) {
            switch(parseInt(val) % 3) {
                case 0:
                    updateData(data1);
                    break;
                case 1:
                    updateData(data2);
                    break;
                case 2:
                    updateData(data3);
            }
        }

        $("#view").spinit({ min: 1, initValue: 1, callback: spinOnChange });

    });

	createChart({
		containerId : "chart",
		data : data1
	});


	
	/* End of Initialize Geospacial */
}

/* =============================================================== 
		OVERLAYED CONTROLS										 
=============================================================== */
/*function initOverlayedControls() {
	$("#spinner1").spinit();

	$('.showall').click(function() {
		$(this).parents('fieldset:eq(0)').find(':checkbox').attr('checked', true);
		$(this).removeClass("active");
		$(".hideall").addClass("active");
	});
	$('.hideall').click(function() {
		$(this).parents('fieldset:eq(0)').find(':checkbox').attr('checked', false);
		$(this).removeClass("active");
		$(".showall").addClass("active");
	});
}*/

/* =============================================================== 
		LINE CHART 											 
=============================================================== */
function initLineChart() {
	
	var options, chart;
	
	function updateData(newData) {
		options.data = newData;
		createChart(options);
	};
	
	function createChart(options) {
	
		var dataModelId;
		var checkForMouseOutTimer;
	
		chart = $("#" + options.containerId).kendoChart({
			theme : "acxiomTheme",
			title : {
				text : ""
			},
			legend : {
				position : "bottom",
				visible : false
			},
			chartArea : {
				background : ""
			},
			seriesDefaults : {
				type : "line"
			},
			series : options.data,
			valueAxis : {
				labels : {
					format : "N0"
				},
				majorUnit : 500
			},
			categoryAxis : {
				categories : ["May", "July", "September", "November", "December", "January"]
			},
			tooltip : {
				visible : true,
				template : "<div class='tooltip'><span class='header'> ${ dataItem.name } </span><span class='row1'>${ dataItem.impressions } Impressions</span><span class='row1'> ${ dataItem.spots } spots</span></div>",
				color : "black",
				opacity : 0.92,
				border : {
					width : 0,
					color : "#DBDAD9"
				}
			}
		});
	};

	/* Initialize of Line Chart */
    var data = [{
        name : "World",
        aggregate : "sum",
        data : [{
            name : "O MAGAZINE 1",
            value : 3998.7,
            impressions : "883",
            spots : "43"
        }, {
            name : "O MAGAZINE 2",
            value : 3816.7,
            impressions : "213",
            spots : "43"
        }, {
            name : "O MAGAZINE 3",
            value : 4120,
            impressions : "876",
            spots : "43"
        }, {
            name : "O MAGAZINE 4",
            value : 4900.5,
            impressions : "213",
            spots : "4843"
        }, {
            name : "O MAGAZINE 5",
            value : 4300.5,
            impressions : "213",
            spots : "4843"
        }, {
            name : "O MAGAZINE 6",
            value : 4500.5,
            impressions : "213",
            spots : "4843"
        }]

    }, {
        name : "United States",
        data : [{
            name : "O MAGAZINE 1",
            value : 1000.7,
            impressions : "376",
            spots : "13"
        }, {
            name : "O MAGAZINE 2",
            value : 1208.7,
            impressions : "575",
            spots : "43"
        }, {
            name : "O MAGAZINE 3",
            value : 2205,
            impressions : "575",
            spots : "23"
        }, {
            name : "O MAGAZINE 4",
            value : 1500.5,
            impressions : "767",
            spots : "233"
        }, {
            name : "O MAGAZINE 5",
            value : 1500.5,
            impressions : "213",
            spots : "4843"
        }, {
            name : "O MAGAZINE 6",
            value : 2043.5,
            impressions : "213",
            spots : "4843"
        }]
    }];
	
	data1 = $.extend(true, {}, data),
	data2 = $.extend(true, {}, data),
	data3 = $.extend(true, {}, data);
	
	data2[0].data[0].value += 500;
	data2[0].data[2].value -= 700;
	data2[0].data[4].value += 300;
	data2[0].data[5].value -= 500;
	
	data2[1].data[1].value -= 900;
	data2[1].data[3].value -= 400;
	data2[1].data[5].value += 200;
	
	data3[0].data[0].value -= 100;
	data3[0].data[2].value += 300;
	data3[0].data[4].value -= 670;
	data3[0].data[5].value += 300;
	
	data3[1].data[1].value += 300;
	data3[1].data[3].value -= 400;
	data3[1].data[5].value += 700;
		
	options = {
		containerId: "chart",
		data: data
	};

	createChart(options);

	$(function() {

		var countryList = ["Armenia", "Canada", "USA"];

        $(".acxiom-autocomplete").autocomplete({
            lookup: countryList,
            isLocal: true,
            onSelect: function(obj) {
                switch(obj.value) {
                    case countryList[0]:
                        updateData(data1);
                        break;
                    case countryList[1]:
                        updateData(data2);
                        break;
                    case countryList[2]:
                        updateData(data3);
                        break;
                }
            }
        });

        $("#view").spinit({ min: 1, initValue: 1, callback: spinOnChange });

        function spinOnChange(val) {
            switch(parseInt(val) % 3) {
                case 0:
                    updateData(data1);
                    break;
                case 1:
                    updateData(data2);
                    break;
                case 2:
                    updateData(data3);
                    break;
            }
        }
	});
	/* End of Initialize Line Chart */
}

/* =============================================================== 
		PIE CHART 											 
=============================================================== */
function initPie() {
    var chart;

	var data = dataArmenia = [{
		category : "WOMEN",
		value : 50,
		impressions : "10,883",
		spots : "43"
	}, {
		category : "VIEWS",
		value : 25,
		impressions : "5,546",
		spots : "31"
	}, {
		category : "KIDS",
		value : 25,
		impressions : "4,234",
		spots : "27"
	}], dataCanada = [{
		category : "WOMEN",
		value : 30,
		impressions : "10,883",
		spots : "43"
	}, {
		category : "VIEWS",
		value : 45,
		impressions : "5,546",
		spots : "31"
	}, {
		category : "KIDS",
		value : 25,
		impressions : "4,234",
		spots : "27"
	}], dataUSA = [{
		category : "WOMEN",
		value : 40,
		impressions : "10,883",
		spots : "43"
	}, {
		category : "VIEWS",
		value : 15,
		impressions : "5,546",
		spots : "31"
	}, {
		category : "KIDS",
		value : 40,
		impressions : "4,234",
		spots : "27"
	}];

	// create chart
	chart = AcxiomPieChart.create({

		containerId : "chart",
		data : [{
			category : "WOMEN",
			value : 50,
			impressions : "10,883",
			spots : "43"
		}, {
			category : "VIEWS",
			value : 25,
			impressions : "5,546",
			spots : "31"
		}, {
			category : "KIDS",
			value : 25,
			impressions : "4,234",
			spots : "27"
		}],
		template : "<div class='tooltip'><span class='header'> ${ category } (${ value }%)</span><span class='row1'>${ dataItem.impressions } Impressions</span><span class='row1'> ${ dataItem.spots } spots</span></div>",

		// configure chart size
		plotArea : {// default is 30
			margin : 30
		},
		padding : 120		// default is 100
	});

	$(function() {

		var countryList = ["Armenia", "Canada", "USA"];

        $(".acxiom-autocomplete").autocomplete({
            lookup: countryList,
            isLocal: true,
            onSelect: function(obj) {
                switch(obj.value) {
                    case countryList[0]:
                        updateData(data1);
                        break;
                    case countryList[1]:
                        updateData(data2);
                        break;
                    case countryList[2]:
                        updateData(data3);
                        break;
                }
            }
        });

        $("#view").spinit({ min: 1, initValue: 1, callback: spinOnChange });

        function spinOnChange(val) {
            data[0].value += 30 + parseInt(val);
            chart.updateData(data);
        }

	});

};

function AcxiomPieChart() {
	var chartSettings, chart;
	this.init = function( settings ) {
		if(!settings || !settings.containerId) {
			throw new Error("Invalid arguments");
		}
		chartSettings = settings;
		createChart(settings);
	};
	
	this.updateData = function( newData ) {
		chartSettings.data = newData;
		createChart();
	};
	
	this.getData = function() {
		return chartSettings.data;
	};

	function createChart() {
		var dataModelId;
		var checkForMouseOutTimer;
		
		chart = $("#" + chartSettings.containerId).kendoChart({
			theme : "acxiomTheme",
			title: {
				text : ""
			},
			legend: { position: "bottom", visible: false },
			plotArea:  chartSettings.plotArea || { margin: 30 },
			seriesDefaults : {
				padding: chartSettings.padding || 100,
				labels : {
					visible : true,
					position : "outsideEnd",
					font: "bold 14px Arial,Helvetica,sans-serif",
					template: "#= category # (#= value #%)"
				}
			},
			dataSource: new kendo.data.DataSource({
				data : chartSettings.data
	   		}),
			series : [ {
				type : "donut",
				data : chartSettings.data,
				tooltip : {
					visible : true,
					template : chartSettings.template,
					color: "black",
		
					border: {
						width: 0,
						color: "#DBDAD9"
					}
				},
			}],
	
			seriesHover: function(e) {
				
				var id = "#" + chartSettings.containerId;
				
				if(dataModelId) {
					
					$($( id + " path[data-model-id='" + dataModelId + "']")[0]).css("display", "block");
					clearInterval(checkForMouseOutTimer);	
				}
				
				dataModelId = $(e.element[0]).attr('data-model-id');
					
				$($( id + " path[data-model-id='" + dataModelId + "']")[0]).css("display", "none");
				$($( id + " .k-tooltip")[0]).css("display", "block");
	
				checkForMouseOutTimer = setInterval(function() {
					
					if(($($( id + " .k-tooltip")[0]).css("display")) == "none") {
						
						$($( id + " path[data-model-id='" + dataModelId + "']")[0]).css("display", "block");
						clearInterval(checkForMouseOutTimer);
					} 
				}, 50);
			}
		});
		
	}
};

AcxiomPieChart.create = function(settings) {
	var chart = new AcxiomPieChart();
	chart.init(settings);
	return chart;
};







/* =============================================================== 
		SCATTERPLOT 											 
=============================================================== */
function initScatterplot() {
    var chart;
	
	function updateData(newData) {
	
		createChart({
			containerId : "chart",
			data : newData
		});
	};
	
	function createChart(options) {
	
		var series = [];
	
		var maxSize = Number.NEGATIVE_INFINITY;
	
		for (var i = 0; i < options.data.length; i++) {
			if (options.data[i].size > maxSize) {
	
				maxSize = options.data[i].size;
			}
		}
	
		for (var i = 0; i < options.data.length; i++) {
	
			var arr = []
			arr.push(options.data[i]);
			series.push({
				data : arr,
				color: "#7F3361",
				opacity: Math.pow(options.data[i].size / maxSize, 0.8)
			});
		}
	
		function dataBound(e) {
	
			var series = e.sender.options.series;
	
			for (var i = 0; i < series.length; i++) {
	
				series[i].maxSize = 22;
			}
		}
	
		chart = $("#" + options.containerId).kendoChart({
			theme : "acxiomTheme",
			title : {
				text : ""
			},
	
			seriesDefaults : {
				type : 'bubble',
	
			},
			plotArea : {
				margin : {
					top : 0
				}
	
			},
			chartArea : {
				opacity : 0
			},
			legend : {
				visible : true
	
			},
			dataSource : {
				data : options.data,
				group : {
					field : "year"
				}
			},
	
			series : series,
			xAxis : {
				labels : {
					format : "{0:N0}",
					skip : 1,
					visible : true
				},
				axisCrossingValue : -5000,
				majorUnit : 2000,
				line : {
					width : 1
				},
				plotBands : [{
					from : -5000,
					to : 0,
					color : "#00f",
					opacity : 0
				}],
				majorGridLines : {
					visible : true
				}
			},
			yAxis : {
				labels : {
					format : "{0:N0}",
					visible : true
				},
				line : {
					width : 1
				},
				majorGridLines : {
					visible : true
				}
			},
			tooltip : {
				visible : true,
				template : "<div class='tooltip'><span class='header'> ${ dataItem.name } </span><span class='row1'>${ dataItem.impressions } Impressions</span><span class='row1'> ${ dataItem.spots } spots</span></div>",
				color : "black",
				opacity : 1,
				width : 0,
				height : 0,
				padding : 100,
				border : {
					width : 0,
					color : "#111111"
				}
			},
			dataBound : dataBound
		});
	};

	/* Initialize Scatterplot */

	var data1 = [{
		x : 2500,
		y : 50000,
		size : 500,
		name : "O MAGAZINE 1",
		impressions : "683",
		spots : "8"
	}, {
		x : 700,
		y : 110000,
		size : 760,
		name : "O MAGAZINE 2",
		impressions : "983",
		spots : "3"
	}, {
		x : 7000,
		y : 39000,
		size : 700,
		name : "O MAGAZINE 3",
		impressions : "383",
		spots : "3"
	}, {
		x : 1400,
		y : 150000,
		size : 700,
		name : "O MAGAZINE 4",
		impressions : "483",
		spots : "6"
	}, {
		x : 4400,
		y : 30000,
		size : 300,
		name : "O MAGAZINE 5",
		impressions : "123",
		spots : "3"
	}, {
		x : 3300,
		y : 30000,
		size : 1000,
		name : "O MAGAZINE 6",
		impressions : "483",
		spots : "6"
	}, {
		x : 3000,
		y : 40000,
		size : 900,
		name : "O MAGAZINE 7",
		impressions : "483",
		spots : "6"
	}], data2 = jQuery.extend(true, [], data1), data3 = jQuery.extend(true, [], data2);

	
	var data2Sizes = [900, 834, 405, 100, 1000, 400, 800];
	var data3Sizes = [100, 834, 305, 800, 2000, 700, 200];
	var coordinates = 
	[{x: 4000, y: 30000}, {x: 2000, y: 8000}, {x: 1000, y: 35000}, {x: 6600, y: 15000},
	 {x: 5900, y: 150000}, {x: 1800, y: 40000}, {x: 2290, y: 20000} ];

	for (var i = 0; i < data1.length; i++) {

		data2[i].size = data2Sizes[i];
		data3[i].size =  data3Sizes[i];
		
		data2[i].x = coordinates[i].x;
		data2[i].y = coordinates[i].y;
		
		data3[i].x = coordinates[data1.length - i - 1].x;
		data3[i].y = coordinates[data1.length - i - 1].y;
	}

	createChart({
		containerId : "chart",
		data : data1
	});

	$(function() {

		var countryList = ["Armenia", "Canada", "USA"];

        $(".acxiom-autocomplete").autocomplete({
            lookup: countryList,
            isLocal: true,
            onSelect: function(obj) {
                switch(obj.value) {
                    case countryList[0]:
                        chart.updateData(data);
                        break;
                    case countryList[1]:
                        chart.updateData(data);
                        break;
                    case countryList[2]:
                        chart.updateData(data);
                        break;
                }
            }
        });

        $("#view").spinit({ min: 1, initValue: 1, callback: spinOnChange });

        function spinOnChange(val) {
            switch(parseInt(val) % 3) {
                case 0:
                    updateData(data1);
                    break;
                case 1:
                    updateData(data2);
                    break;
                case 2:
                    updateData(data3);
                    break;
            }
        }
	});
	/* End of Initialize Scatterplot */
}

