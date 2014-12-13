(function() {

  ACXM.Combo = {
    init: function(val) {
      if ($(val).hasClass('disabled')) {
        // do nothing!
      } else {
        $(val).toggleClass('acxiom-select acxiom-combobox');

        $(val + " select").attr('id', val.slice(1));

        $(val + " select").flexselect({ hideDropdownOnEmptyInput: false });
        $(val + " input").val("");
        $(val).click(function() {
          $(val + " input").val("");
          $(val + '_flexselect_dropdown').css('display', 'block');
        });
      }
    }
  }

})();
