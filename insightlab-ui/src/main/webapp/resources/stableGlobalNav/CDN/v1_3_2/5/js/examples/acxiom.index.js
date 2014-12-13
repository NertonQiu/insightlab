(function() {

  ACXM.Example.Utils = {
    resizeIframeWindow: resizeIframeWindow,
    loadPageIframe: loadPageIframe
  };

  function resizeIframeWindow() {
      var wHeight = $(window).height();
      var $iframe = $('#acxiom-load-iframe');
      $iframe.css('height', wHeight - 164 + 'px');

      $(window).off('resize').on('resize', resizeIframeWindow);
  }

  function loadPageIframe() {
    $('.acxiom-index-container a, [data-href], .MenuListID').on('click', function(e) {
        e.preventDefault();
        var source = $(this).attr('href') || $(this).attr("data-href");
        $("#acxiom-load-iframe").attr('src', source);
    });

    $('[data-top-tab-id]').on('click', function(e) {
        //e.preventDefault();
        var id = $(this).data('top-tab-id'),
            href = $(this).attr('href'),
            el = window.parent.document.querySelector('.acxiom-topnav-tabs div[data-tab-id="' + id + '"]'),
            subEl = window.parent.document.querySelector('.acxiom-subnavSec-tabs-div > ul li a[href="style-guide/' + href + '"]');

        $(el).click();
        if(href && subEl) {
            $(subEl).parents('li[data-tab-id]').addClass('active');
            $(subEl).click();
        }
    });
  }

})();
