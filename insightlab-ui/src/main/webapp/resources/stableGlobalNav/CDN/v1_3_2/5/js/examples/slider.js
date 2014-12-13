$(function() {

  (function() {
    var slider = new ACXM.Slider({
      selector: "#slider1",
      min: 1,
      max: 100,
      value: 40,
      step: 20,
      title: "SELECT REACH",
      smooth: false
    });
  })();

  (function() {
    var slider = new ACXM.Slider({
      selector: "#slider2",
      min: 0,
      max: 10000,
      value: 3000,
      step: 1000,
      title: "0-10000, step: 1000",
      smooth: false
    });
  })();

  (function() {
    var slider = new ACXM.Slider({
      selector: "#slider3",
      min: 1,
      max: 100,
      value: 40,
      step: 1,
      scaleStep: 10,
      title: "Percent format",
      scaleFormat: "{{val}}%"
    });
  })();

  (function() {
    var scale = [];
    for (var i = 0; i <= 300; i += 10) {
      scale.push(i);
    }

    $("#percentage-switch").change(function() {
      var value = $("#slider4").slider("value");
      var percentage = parseInt(($("#slider4").slider("value") / 300) * 100) + "%";

      var isChecked = $("#percentage-switch").is(":checked");

      $("#slider4 + .jslider .jslider-value:first span").text(isChecked ? percentage : value);
      $("#slider4 + .jslider .jslider-pointer").attr("title", isChecked ? value : percentage);
    });

    $("#label-switch").change(function() {
      scale = [];

      if ($("#label-switch").is(":checked")) {
        for (var i = 0; i <= 100; i += 10) {
          scale.push(i + "%");
        }
      } else {
        for (var i = 0; i <= 300; i += 10) {
          scale.push(i);
        }
      }

      $("#slider4").data("jslider", null).next().remove();

      initSlider();
    });

    function initSlider() {
      $("#slider4").slider({
        from: 0,
        to: 300,
        step: 10,
        scale: scale,
        skin: "plastic",
        calculate: function(value) {
          var isChecked = $("#percentage-switch").is(":checked");
          var percentage = parseInt((value / 300) * 100) + "%";

          $("#slider4 + .jslider .jslider-pointer").attr("title", isChecked ? value : percentage);
          return isChecked ? percentage : value;
        }
      });
    }

    initSlider();

  })();

  (function() {
    var sliders = [];
    for (var k = 1; k <= 10; ++k) {
      var slider = new ACXM.Slider({
        selector: "#slider-t-" + k,
        min: 0,
        max: 10000,
        value: 3000,
        step: 1,
        scaleStep: 1000,
        title: "All Together"
      });

      sliders.push(slider);
    }

    new ACXM.Slider.together({
      sliders: sliders,
      checkFn: function() {
        return $(".acxm-checkbox input").is(":checked")
      }
    });
  })();

});
