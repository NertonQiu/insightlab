$(function() {
  var $mainContainer = $('.acxiom-page-container');

  $mainContainer.css('min-height', $(window).height() - 34 + 'px');

  $(window).on('resize', function() {
    $mainContainer.css('min-height', $(window).height() - 34 + 'px');
  });

  $(document).on("change", ".acxiom-select select", function(e) {
    $(this).blur();
    $(this).data("selected", true);
  });

  $(document).on("click", ".acxiom-select select", function(e) {
    if (!$(this).data("selected")) {
      $(this)[0].selectedIndex = -1;
    }

    $(this).data("selected", false);
  });

  $(document).on("focus", ".acxiom-select select", function(e) {
    $(this).css({
      "min-width": $(this).parent().width()
    });
  });

  $(document).on('mouseover', '.acxm-disabled',function(e) {
    var el = document.elementFromPoint(e.clientX, e.clientY);

    var overlay = $("<div id='pointer-overlay' />");

    overlay.css({
      width: $(el).outerWidth(),
      height: $(el).outerHeight(),
      top: $(el).offset().top,
      left: $(el).offset().left
    });

    $("body").append(overlay);
  }).on("mouseleave", '.acxm-disabled, #pointer-overlay', function(e) {
        $("#pointer-overlay").remove();
      });
});


