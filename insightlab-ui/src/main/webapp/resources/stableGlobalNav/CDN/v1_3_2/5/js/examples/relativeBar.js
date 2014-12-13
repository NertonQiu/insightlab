(function() {
  $('#output-for-html').html('<pre class="brush:js">' + $(".acxiom-sg-section").html().replace(/&/g, '&amp;').replace(/</g, '&lt;') + '</pre>');

  ACXM.Chart.RelativeBar({
    dom: $("#example-1"),
    scale: "value",
    value: 300,
    max: 1000,
    title: "Asian"
  });

  ACXM.Chart.RelativeBar({
    dom: $("#example-2"),
    value: 87,
    color: "#C75106",
    title: "African American"
  });

  ACXM.Chart.RelativeBar({
    dom: $("#example-3"),
    value: 100,
    color: "#439D05",
    title: "Ukraines"
  });

  ACXM.Chart.RelativeBar({
    dom: $("#example-4"),
    scale: "value",
    value: 87,
    max: 1000,
    color: "#C75106",
    title: "African American"
  });

  var i = 0;
  $(".acxiom-sg-section button[data-id='example-1']").click(function(e) {
    e.preventDefault();

    var options = {};

    if (i % 2 === 0) {
      options = {
        dom: $("#example-1"),
        value: 30,
        title: "Asian"
      };
    } else {
      options = {
        dom: $("#example-1"),
        scale: "value",
        value: 300,
        max: 1000,
        title: "Asian"
      };
    }

    ++i;

    ACXM.Chart.RelativeBar(options);
  });

})();
