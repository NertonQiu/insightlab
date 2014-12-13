(function() {

  ACXM.Table = {

    init: function() {
      var $tr = $('.acxiom-table tr');
      $tr.on('click', function() {
        $tr.removeClass('active-row');
        $(this).addClass('active-row');

        $(this).toggleClass('row_selected');
      });

      $('.star-icon').on('click', function() {
        var activeClass = 'star-active';

        if ($(this).hasClass(activeClass)) {
          $(this).html('inactive');
          $(this).removeClass("star-active");
        } else {
          $(this).html('active');
          $(this).addClass("star-active");
        }
      });
    }
  };

})();
