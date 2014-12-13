(function() {

  ACXM.Tabs = {
    init: function() {
      $(".acxiom-tabs li").click(function() {
        $(this).parent().find("li").removeClass("active");
        $(this).addClass("active");
        var tabItemID = $(this).attr("data-tab-id");
        $(this).parent().parent().find(".acxiom-tabs-container .active").removeClass("active");
        $(this).parent().parent().find(".acxiom-tabs-container #" + tabItemID).addClass("active");
      });
    }
  };

})();
