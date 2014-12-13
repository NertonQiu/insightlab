(function() {
    ACXM.Chart.Bar = function(param) {
        function getItemColor(value) {
            var hslColor = [216 / 240, 82 / 240, 1];
            hslColor[2] -= Math.pow(value / (maxValue + 200), 1.8);

            var color = hslToRgb(hslColor[0], hslColor[1], hslColor[2] );

            for(var i = 0; i < color.length; i++) {
                color[i] = parseInt(color[i]);
            }

            return "rgb(" + color +")";
        }

        function hslToRgb(h, s, l){
            var r, g, b;

            if(s == 0) {
                r = g = b = l; //achromatic
            } else {
                function hue2rgb(p, q, t){
                    if(t < 0) t += 1;
                    if(t > 1) t -= 1;
                    if(t < 1/6) return p + (q - p) * 6 * t;
                    if(t < 1/2) return q;
                    if(t < 2/3) return p + (q - p) * (2/3 - t) * 6;
                    return p;
                }

                var q = l < 0.5 ? l * (1 + s) : l + s - l * s,
                    p = 2 * l - q;

                r = hue2rgb(p, q, h + 1/3);
                g = hue2rgb(p, q, h);
                b = hue2rgb(p, q, h - 1/3);
            }

            return [r * 255, g * 255, b * 255];
        }


        var series = [],
            maxValue = Number.NEGATIVE_INFINITY;

        for(var i = 0; i < param.data.length; i++) {
            if(param.data[i].value > maxValue){
                maxValue = param.data[i].value;
            }
        }

        maxValue += param.options.valueAxis.majorUnit;
        maxValue -= maxValue % param.options.valueAxis.majorUnit;

        for(var i=0; i < param.data.length; i++) {

            var arr = new Array(param.data.length);
            arr[i] = param.data[i];

            series.push({ data: arr, color: getItemColor(param.data[i].value) });;
        }

        param.options.series = series;

        $(param.container).kendoChart(param.options);
    }
})();