(function() {

  ACXM.Slider = function(options) {
    var resultOptions = $.extend({}, _defaults, options);

    var scale = [];
    for (var i = resultOptions.min; i <= resultOptions.max; i += resultOptions.scaleStep || resultOptions.step) {
      var result = i;

      if (resultOptions.scaleFormat) {
        result = resultOptions.scaleFormat.replace("{{val}}", i);
      }

      scale.push(result);
    }

    if (scale.indexOf(resultOptions.max) === -1) {
      scale.push(resultOptions.max);
    }

    var slider = $(options.selector);

    slider.attr("value", resultOptions.value);

    if (resultOptions.title) {
      var titleEl = $("<h1></h1>").text(resultOptions.title).attr("data-title", resultOptions.titleLocation);
      $(options.selector).before(titleEl);
    }

    return slider.slider({
      from: resultOptions.min,
      to: resultOptions.max,
      step: resultOptions.step,
      scale: scale,
      skin: "plastic",
      smooth: resultOptions.smooth
    });
  };

  ACXM.Slider.together = function(options) {
    var changing = -1;
    var isChanging = false;

    $.each(options.sliders, function(key, value) {
      var slider = value.slider();

      slider.onstatechange = function(value) {
        var _this = this;

        if (options.checkFn() && (!isChanging || changing === this.inputNode.context.id)) {
          changing = this.inputNode.context.id;
          isChanging = true;

          var sliders = [];
          $.each(options.sliders, function(k, v) {
            var id = $(v).attr("id");

            if ($(v).attr("id") !== _this.inputNode.context.id) {
              sliders.push($(v));
            }

          });

          $(sliders).slider("value", value);
        }
      };

      slider.settings.callback = function(value) {
        isChanging = false;
      }
    });
  };

  var _defaults = {
    min: 0,
    max: 100,
    step: 1,
    titleLocation: "top left",
    smooth: true
  };

})();
