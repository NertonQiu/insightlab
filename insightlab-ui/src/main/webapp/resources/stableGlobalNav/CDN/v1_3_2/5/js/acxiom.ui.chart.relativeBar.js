(function() {

  ACXM.Chart.RelativeBar = function(params) {
    var isValue = params.scale === "value";

    params.dom.kendoChart({
      title: {
        align: "left",
        text: params.title,
        font: "11px sans-serif",
        margin: {
          top: 0,
          bottom: -5,
          left: 10
        }
      },
      seriesDefaults: {
        type: "bar",
        labels: {
          visible: true,
          background: "transparent",
          format: isValue ? "{0}" : "{0}%"
        },
        tooltip: {
          visible: true,
          background: "#fff",
          format: (isValue ? "{0}" : "{0}%") + " of " + (isValue ? params.max : "100%"),
          border: {
            width: 1,
            color: "#ccc"
          }
        }
      },
      series: [
        {
          data: [params.value],
          color: params.color || "#4580D1"
        }
      ],
      valueAxis: {
        max: isValue ? params.max : 100,
        majorGridLines: {
          visible: false
        },
        visible: false,

        plotBands: [
          {
            from: 0, to: isValue ? params.max : 100, color: "#ccc"
          }
        ]
      },
      categoryAxis: {
        categories: [0],

        majorGridLines: {
          visible: false
        },
        line: {
          visible: false
        }
      }
    });
  }

})();
